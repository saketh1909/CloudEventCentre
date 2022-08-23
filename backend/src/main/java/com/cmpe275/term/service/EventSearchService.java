package com.cmpe275.term.service;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.EventApprovalRequestResponse;
import com.cmpe275.term.model.EventResponse;
import com.cmpe275.term.model.UsersRes;
import com.cmpe275.term.repository.EventRepository;
import com.cmpe275.term.repository.EventSearchRepository;
import com.cmpe275.term.repository.ParticipantRepository;
import com.cmpe275.term.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventSearchService {

    @Autowired
    private EventSearchRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EmailService emailService;

    public List<EventResponse> eventSearch(String location, String status, String startTime, String endTime, String keyword, String organizer) {
        List<Event> list=repository.findAllEventsByPredicates(location,status,startTime,endTime,keyword,organizer);
        List<EventResponse> ans=new ArrayList<>();
        for(Event e : list){
           // System.out.println(e.getUser().getId()+"::"+e.getUser().getEmail());
            EventResponse eventRes=this.mapper.map(e,EventResponse.class);
            //System.out.println(eventRes);
            List<Participant> participants = participantRepository.findByEventAndApproval(e,0);
            eventRes.setPendingRequestCount(participants.size());
            ans.add(eventRes);

        }
        return ans;
    }

    public String eventSignUp(Long userId, Long eventId,int approval) throws ChangeSetPersister.NotFoundException, MessagingException, IOException {
        Participant participant=new Participant();
        if(userId!=null){
            Optional<User> t = userRepository.findById(userId);
            if (!t.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            participant.setUser(t.get());
        }
        if(eventId!=null){
            Optional<Event> e = eventRepository.findById(eventId);
            if (!e.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            participant.setEvent(e.get());
        }
        List<Participant> p = participantRepository.findByUserAndEvent(participant.getUser(),participant.getEvent());
        if (p.size()>0) {
            return "User Already registered for event";
        }
        participant.setApproval(approval);
        if(approval==1){
            participant.setAmountPaid(1);
            String message="Hi. You have successfully signed up for "+participant.getEvent().getTitle()+" Event.";
            String header="Event Sign Up Successful";
            emailService.sendMailToRecipients("",participant.getUser().getEmail(),message,header);
        }else{
            participant.setAmountPaid(0);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        participant.setCreationDate(timestamp);
        participantRepository.save(participant);
        if(approval==0)
            return "Pending for organizer approval";
        Event e = eventRepository.findById(eventId).get();
        e.setParticipantsCount(e.getParticipantsCount()+1);
        eventRepository.save(e);
        return "Event Sign Up Successful";
    }

    @Transactional
    public String eventSignUpApprovalRequest(Long participantId, int approval) throws ChangeSetPersister.NotFoundException, MessagingException, IOException {
        Optional<Participant> p = participantRepository.findById(participantId);
        if(!p.isPresent()){
            throw new ChangeSetPersister.NotFoundException();
        }
        Participant participant = p.get();
        participant.setApproval(approval);
        if(approval==1){
            participant.setAmountPaid(1);

        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        participant.setCreationDate(timestamp);
        participantRepository.save(participant);
        if(approval==1){
            Event e = eventRepository.findById(participant.getEvent().getId()).get();
            e.setParticipantsCount(e.getParticipantsCount()+1);
            eventRepository.save(e);
            String message="Hi. Your sign up request for"+participant.getEvent().getTitle()+" Event is approved.";
            String header="Event Sign Up Request Approved";
            emailService.sendMailToRecipients("",participant.getUser().getEmail(),message,header);
        }else{
            String message="Hi. Your sign up request for"+participant.getEvent().getTitle()+" Event is rejected.";
            String header="Event Sign Up Request Rejected";
            emailService.sendMailToRecipients("",participant.getUser().getEmail(),message,header);
        }

        return "Approval Request Updated Successfully";
    }

    public List<EventApprovalRequestResponse> getEventApprovalRequest(Long eventId) throws ChangeSetPersister.NotFoundException {
        Event eve=null;
        if(eventId!=null){
            Optional<Event> e = eventRepository.findById(eventId);
            if (!e.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            eve=e.get();
        }
        List<Participant> participants = participantRepository.findByEventAndApproval(eve,0);
        List<EventApprovalRequestResponse> res=new ArrayList<>();
        for(Participant p : participants){
            EventApprovalRequestResponse req=new EventApprovalRequestResponse();
            UsersRes user = this.mapper.map(p.getUser(),UsersRes.class);
            req.setUser(user);
            req.setApproval(p.getApproval());
            req.setId(p.getId());
            res.add(req);
        }
        return res;
    }
}
