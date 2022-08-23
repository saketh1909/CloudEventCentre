package com.cmpe275.term.service;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.repository.EventRepository;
import com.cmpe275.term.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class TimeChangeService {

    @Autowired
    public EventRepository eventRepository;


    @Transactional
    public String makeModificationToDatabase(String time) throws AddressException, MessagingException, IOException,Exception  {
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(time);
        cancelEventPassedDeadlineWithLessThanMinParticipants(timestamp);
        changeEventsToActiveIfInProgress(timestamp);
        changeEventsToPastStatus(timestamp);
        return "Time Change Successful";

    }

    @Transactional
    public void changeEventsToPastStatus(Timestamp time){
           LocalDateTime newTime=time.toLocalDateTime().minusDays(3);
           Timestamp stamp = Timestamp.valueOf(newTime);
            System.out.println(stamp);
            List<Event> events = eventRepository.findAllEventsPassedEnddate(stamp);
            for(Event e : events){
                e.setForumCloseReason("Event has finished");
                e.setStatus(3);
                eventRepository.save(e);
            }
    }

    @Transactional
    public void changeEventsToActiveIfInProgress(Timestamp timestamp) throws MessagingException, IOException {
        LocalDateTime newTime=timestamp.toLocalDateTime().minusDays(3);
        List<Event> events = eventRepository.findAllEventsInActiveStatus(timestamp,Timestamp.valueOf(newTime));
        for(Event e : events){
            e.setStatus(2);
            List<Participant> participants = e.getParticipants();
            for(Participant p : participants){
                sendMailToRecipients(p.getUser().getName(),p.getUser().getEmail(),e.getTitle(),1);
            }
            eventRepository.save(e);
        }
    }

    @Transactional
    public void cancelEventPassedDeadlineWithLessThanMinParticipants( java.sql.Timestamp timestamp)  throws AddressException, MessagingException, IOException{
        List<Event> events = eventRepository.findAllEventPassedDeadlineWithMinParticipants(timestamp);
        for(Event e : events){
            List<Participant> participants = e.getParticipants();
            for(Participant p : participants){
                sendMailToRecipients(p.getUser().getName(),p.getUser().getEmail(),e.getTitle(),0);
            }
            e.setForumCloseReason("Event Closed due to less no of participants");
            e.setStatus(1);
            e.setParticipantsCount(0);
            eventRepository.save(e);
        }
    }
    public void sendMailToRecipients(String name,String email,String eventName,int check) throws AddressException, MessagingException, IOException{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("CloudEventCenter@gmail.com", "Cmpe275@CEC");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("CloudEventCenter@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        String messageContent="Hi "+name+",  the event "+eventName+" has been cancelled due to less number of participants. Sorry for the inconvenience!";
        if(check==1) messageContent="Hi "+name+", the event is active. Please participate!";
        msg.setSubject("Event Cancellation");
        if(check==1) msg.setSubject("Event is Live");
        msg.setContent(messageContent, "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
