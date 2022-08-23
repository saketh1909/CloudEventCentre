package com.cmpe275.term.service;

import com.cmpe275.term.entity.Address;
import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.EventResponse;
import com.cmpe275.term.model.ParticipantResponse;
import com.cmpe275.term.model.UsersRes;
import com.cmpe275.term.model.newParticipantResponse;
import com.cmpe275.term.repository.EventRepository;
import com.cmpe275.term.repository.ParticipantRepository;
import com.cmpe275.term.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EmailService emailService;

    public EventResponse createEvent(Long userId, String title, String description, String street, String city, String state, String zip,
                                     java.sql.Timestamp startDate, java.sql.Timestamp endDate, java.sql.Timestamp deadline, int minParticipants,
                                     int maxParticipants, double fees, String policy) throws  Exception{
        Event event = new Event();
        if(userId!=null){
            Optional<User> t = userRepository.findById(userId);
            if (!t.isPresent()) {
                throw new Exception("User not found");
            }
            event.setUser(t.get());
        }
        event.setTitle(title);
        event.setDescription(description);
        Address address=new Address();
        if(street!=null) {
            address.setStreet(street);
        }
        if(city!=null) {
            address.setCity(city);
        }
        if(state!=null) {
            address.setState(state);
        }
        if(zip!=null) {
            address.setZip(zip);
        }
        event.setAddress(address);
        event.setStart_date(startDate);
        event.setEnd_date(endDate);
        event.setDeadline(deadline);
        event.setMinParticipants(minParticipants);
        event.setMaxParticipants(maxParticipants);
        event.setFees(fees);
        event.setPolicy(policy);
        event.setStatus(0);
        event.setParticipantsCount(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        event.setCreationDate(timestamp);
        repository.save(event);
        EventResponse res = this.modelMapper.map(event,EventResponse.class);
        if(userId!=null){
            Optional<User> u = userRepository.findById(userId);
            if(u.isPresent()){
                UsersRes usersResponse = new UsersRes();
                usersResponse.setEmail(u.get().getEmail());
                usersResponse.setDescription(u.get().getDescription());
                usersResponse.setAccountType(u.get().getAccountType());
                usersResponse.setName(u.get().getName());
                usersResponse.setScreenName(u.get().getScreenName());
                usersResponse.setId(u.get().getId());
                usersResponse.setGender(u.get().getGender());
                res.setUser(usersResponse);
            }
        }
        String message="Hi."+event.getTitle()+" Event has been successfully created";
        String header="Event Creation Successful";
        emailService.sendMailToRecipients("",event.getUser().getEmail(),message,header);
        return res;
    }

    public EventResponse getEvent(long eventId) throws  NotFoundException{
        Optional<Event> e =this.repository.findById(eventId);
        if(!e.isPresent()) {
            throw new NotFoundException();
        }
        EventResponse res = this.modelMapper.map(e.get(),EventResponse.class);
        Long userId=e.get().getUser().getId();
        if(userId!=null){
            Optional<User> u = userRepository.findById(userId);
            if(u.isPresent()){
                UsersRes usersResponse = new UsersRes();
                usersResponse.setEmail(u.get().getEmail());
                usersResponse.setDescription(u.get().getDescription());
                usersResponse.setAccountType(u.get().getAccountType());
                usersResponse.setName(u.get().getName());
                usersResponse.setScreenName(u.get().getScreenName());
                usersResponse.setId(u.get().getId());
                usersResponse.setGender(u.get().getGender());
                res.setUser(usersResponse);
            }
        }
        List<newParticipantResponse> ps = new ArrayList<>();
        for(Participant p : e.get().getParticipants()){
            newParticipantResponse newP = new newParticipantResponse();
            User  u = p.getUser();
            newP.setEvent(p.getEvent());
            newP.setAmountPaid(p.getAmountPaid());
            newP.setApproval(p.getApproval());
            newP.setId(p.getId());
            newP.setUser(u);
            ps.add(newP);
        }
        res.setParticipants(ps);
        return res;
    }

    public List<EventResponse> getEventsCreatedByUser(Long userId){
        List<Event> list=repository.findEventsCreatedByUserId(userId);
        List<EventResponse> ans=new ArrayList<>();
        for(Event e : list){
            EventResponse eventRes=this.modelMapper.map(e,EventResponse.class);
            List<Participant> participants = participantRepository.findByEventAndApproval(e,0);
            eventRes.setPendingRequestCount(participants.size());
            ans.add(eventRes);

        }
        return ans;
    }

//    public List<EventResponse> getEventsRegisteredByUser(Long userId){
//
//    }
    public String cancelEvent(long eventId) throws NotFoundException{
        Optional<Event> e =this.repository.findById(eventId);
        if(!e.isPresent()) {
            throw new NotFoundException();
        }

        Event existingEvent = e.get();
        if(existingEvent.getStatus()==1){
            return "Event Already Cancelled";
        }
        existingEvent.setStatus(1);
        this.repository.save(existingEvent);
        return "Event Cancelled Successfully";
    }

    public List<ParticipantResponse> getEventsRegisteredByUser(long userId) {
        User u = userRepository.findById(userId);
        List<Participant> participants = participantRepository.findByUser(u);
        List<ParticipantResponse> res = new ArrayList<>();
        for(Participant p : participants){
            ParticipantResponse pRes = this.modelMapper.map(p,ParticipantResponse.class);
            res.add(pRes);
        }
        return res;
    }
}
