package com.cmpe275.term.service;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.EventForumAnswer;
import com.cmpe275.term.entity.EventForumQuestion;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.EventAnswerResponse;
import com.cmpe275.term.model.EventForumResponse;
import com.cmpe275.term.model.EventResponse;
import com.cmpe275.term.model.UsersRes;
import com.cmpe275.term.repository.EventForumAnswersRepository;
import com.cmpe275.term.repository.EventForumQuestionsRepository;
import com.cmpe275.term.repository.EventRepository;
import com.cmpe275.term.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventForumService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventForumQuestionsRepository repository;

    @Autowired
    private EventForumAnswersRepository answersRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmailService emailService;
    public String createForumEventQuestion(Long eventId, Long userId, String question, int forumType) throws ChangeSetPersister.NotFoundException, MessagingException, IOException {
        EventForumQuestion eventFormQuestion = new EventForumQuestion();
        eventFormQuestion.setForumType(forumType);
        eventFormQuestion.setQuestion(question);
        if(userId!=null){
            Optional<User> t = userRepository.findById(userId);
            if (!t.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            eventFormQuestion.setUser(t.get());
        }
        if(eventId!=null){
            Optional<Event> e = eventRepository.findById(eventId);
            if (!e.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            eventFormQuestion.setEvent(e.get());
        }
        this.repository.save(eventFormQuestion);
        String message="Hi. A message has been posted in the"+eventFormQuestion.getEvent().getTitle()+". Please check it out.";
        String header="Message posted in the forum";
        emailService.sendMailToRecipients("",eventFormQuestion.getEvent().getUser().getEmail(),message,header);
        return "Question Created Successfully";
    }

    public String createForumEventAnswer(Long questionId,Long userId, String answer, String imageURL) throws ChangeSetPersister.NotFoundException, MessagingException, IOException {
        EventForumAnswer eventForumAnswer = new EventForumAnswer();
        eventForumAnswer.setAnswer(answer);
        eventForumAnswer.setImageURL(imageURL);
        if(questionId!=null){
            Optional<EventForumQuestion> e = repository.findById(questionId);
            if (!e.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            eventForumAnswer.setQuestion(e.get());
        }
        if(userId!=null){
            Optional<User> t = userRepository.findById(userId);
            if (!t.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            eventForumAnswer.setUser(t.get());
        }
        answersRepository.save(eventForumAnswer);
        String message="Hi. A message has been posted in the"+eventForumAnswer.getQuestion().getEvent().getTitle()+". Please check it out.";
        String header="Message posted in the forum";
        emailService.sendMailToRecipients("",eventForumAnswer.getQuestion().getEvent().getUser().getEmail(),message,header);
        return "Answer Created Successfully";
    }

    public List<EventForumResponse> getEventForums(Long eventId, int forumType) throws ChangeSetPersister.NotFoundException, IOException {
        if(eventId!=null){
            Optional<Event> e = eventRepository.findById(eventId);
            if (!e.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
        }
        List<EventForumQuestion> list = repository.findByEventIdAndForumType(eventId,forumType);
        List<EventForumResponse> res=new ArrayList<>();
        for(EventForumQuestion e : list){
            // System.out.println(e.getUser().getId()+"::"+e.getUser().getEmail());
            EventForumResponse eventRes=new EventForumResponse();
            eventRes.setForumType(e.getForumType());
            eventRes.setId(e.getId());
            eventRes.setQuestion(e.getQuestion());
            UsersRes user = this.mapper.map(e.getUser(),UsersRes.class);
            eventRes.setUser(user);
            EventResponse event = this.mapper.map(e.getEvent(),EventResponse.class);
            eventRes.setEvent(event);
            List<EventAnswerResponse> answers=new ArrayList<>();
            for(EventForumAnswer answer : e.getAnswers()){
                EventAnswerResponse ans=this.mapper.map(answer,EventAnswerResponse.class);
                answers.add(ans);
            }
            eventRes.setAnswers(answers);
            res.add(eventRes);

        }
        return res;
    }

    public String closeParticipantForum(Long eventId) throws ChangeSetPersister.NotFoundException {
        if(eventId!=null){
            Optional<Event> e = eventRepository.findById(eventId);
            if (!e.isPresent()) {
                throw new ChangeSetPersister.NotFoundException();
            }
            e.get().setForumCloseReason("Oraganizer closed the forum");
            eventRepository.save(e.get());
        }
        return "Forum Closed Successfully";
    }
}
