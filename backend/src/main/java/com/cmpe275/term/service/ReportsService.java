package com.cmpe275.term.service;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.SystemReportResponse;
import com.cmpe275.term.model.UserReportResponse;
import com.cmpe275.term.repository.EventRepository;
import com.cmpe275.term.repository.ParticipantRepository;
import com.cmpe275.term.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportsService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UsersRepository userRepository;


    public SystemReportResponse generateSystemReports(String time) {
        Timestamp t = Timestamp.valueOf(time);
        LocalDateTime newTime=t.toLocalDateTime().minusDays(90);
        Timestamp stamp = Timestamp.valueOf(newTime);
        int totalNoOfEvents=eventRepository.findCountOfAllEventCreatedInLast90Days(stamp,t);
        int totalNoOfPaidEvents = eventRepository.findCountOfAllPaidEventCreatedInLast90Days(stamp,t);
        List<Event> events = eventRepository.findCountAllCancelledEvents(stamp,t);
        int totalNoOfMinimumParticipants=0;
        int totalRequestsObtainedForTheseEvents=0;
        for(Event e : events){
            totalNoOfMinimumParticipants+=e.getMinParticipants();
            totalRequestsObtainedForTheseEvents+=e.getParticipants().size();
        }
        List<Event> finishedEvents = eventRepository.findCountAllFinishedEvents(stamp,t);
        int totalParticipantsInFinishedEvents=0;
        for(Event e : finishedEvents){
            totalParticipantsInFinishedEvents+=e.getParticipantsCount();
        }
        System.out.println(totalNoOfEvents+" "+totalNoOfPaidEvents+" "+events.size());
        System.out.println(totalNoOfMinimumParticipants+" "+totalRequestsObtainedForTheseEvents);
        System.out.println(finishedEvents.size() + " " + totalParticipantsInFinishedEvents);
        SystemReportResponse res = new SystemReportResponse();
        res.setNoOfEvents(totalNoOfEvents);
        res.setNoOfPaidEvents(totalNoOfPaidEvents);
        res.setNoOfCancelledEvents(events.size());
        res.setTotalNoOfMinimumParticipantsForCancelledEvents(totalNoOfMinimumParticipants);
        res.setTotalRequestsObtainedForCancelledEvents(totalRequestsObtainedForTheseEvents);
        res.setNoOfFinishedEvents(finishedEvents.size());
        res.setTotalParticipantsInFinishedEvents(totalParticipantsInFinishedEvents);
        return res;
    }

    public UserReportResponse generateUserReports(String time, Long userId) {
        UserReportResponse res = new UserReportResponse();
        Timestamp t = Timestamp.valueOf(time);
        LocalDateTime newTime=t.toLocalDateTime().minusDays(90);
        Timestamp stamp = Timestamp.valueOf(newTime);
        User u = userRepository.findById(userId).get();
        List<Participant> participants = participantRepository.findByUser(u);
        int noOfSignedUpEvents= participants.size();
        int noOfRejects=0,noOfApprovals=0,finishedEvents=0;
        for(Participant p : participants){
            if(p.getApproval()==2) noOfRejects++;
            else if(p.getApproval()==1) noOfApprovals++;
            if(p.getEvent().getStatus()==3 && p.getApproval()==1) finishedEvents++;
        }
        System.out.println(noOfRejects+" "+noOfApprovals+" "+finishedEvents);
        //System.out.println(count);
        List<Event> events = eventRepository.findEventsCreatedByUser(stamp,t,u);
        int totalEventsCreated = events.size();
        int totalPaidEvents=0,totalCancelledEvents=0,totalMinimumParticipation=0,totalRequestForCancelledEvents=0;
        int totalFinishedEvents=0,totalParticipantsInFinishedEvents=0,totalRevenue=0;
        for(Event e : events){
            if(e.getFees()>0) totalPaidEvents++;
            if(e.getStatus()==1 && e.getForumCloseReason().equals("Event Closed due to less no of participants")){
                totalCancelledEvents++;
                totalMinimumParticipation+=e.getMinParticipants();
                totalRequestForCancelledEvents+=participantRepository.findByEvent(e).size();
            }
            if(e.getStatus()==3){
                totalFinishedEvents++;
                totalParticipantsInFinishedEvents+=participantRepository.findByEventAndApproval(e,1).size();
                totalRevenue+=e.getFees()*totalParticipantsInFinishedEvents;
            }

        }
        //Participant Report
        res.setNoOfSignedUpEventsByUser(noOfSignedUpEvents);
        res.setNoOfRejectsForUser(noOfRejects);
        res.setNoOfApprovalsForUser(noOfApprovals);
        res.setFinishedEventsForUser(finishedEvents);

        //Organizer Report

        res.setTotalEventsCreated(totalEventsCreated);
        res.setTotalPaidEvents(totalPaidEvents);
        res.setTotalCancelledEvents(totalCancelledEvents);
        res.setTotalMinimumParticipationForCancelledEvents(totalMinimumParticipation);
        res.setTotalRequestForCancelledEvents(totalRequestForCancelledEvents);
        res.setTotalFinishedEvents(totalFinishedEvents);
        res.setTotalParticipantsInFinishedEvents(totalParticipantsInFinishedEvents);
        res.setTotalRevenueFromFinishedEvents(totalRevenue);

        return res;
    }
}
