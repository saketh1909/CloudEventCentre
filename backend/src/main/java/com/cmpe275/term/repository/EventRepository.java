package com.cmpe275.term.repository;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {
    public Optional<Event> findById(long id);

    public List<Event> findEventsCreatedByUserId(Long userId);


    @Query("SELECT e from Event e where e.creationDate>=?1 and e.creationDate<=?2 and e.user=?3")
    public List<Event> findEventsCreatedByUser(Timestamp time, Timestamp current , User u);

    @Query("SELECT e from Event e where e.deadline<?1 and e.minParticipants>e.participantsCount and e.status=0")
    public List<Event> findAllEventPassedDeadlineWithMinParticipants(java.sql.Timestamp time);
    @Query("SELECT e from Event e where e.deadline<=?1 and e.end_date>=?2 and e.minParticipants<=e.participantsCount and e.status=0")
    public List<Event> findAllEventsInActiveStatus(Timestamp time,Timestamp endTime);

    @Query("SELECT e from Event e where e.end_date<?1 and (status=0 or status=2)")
    public List<Event> findAllEventsPassedEnddate(Timestamp time);

    @Query("SELECT COUNT(*) from Event e where e.creationDate>=?1 and e.creationDate<=?2")
    public int findCountOfAllEventCreatedInLast90Days(Timestamp time,Timestamp current);

    @Query("SELECT COUNT(*) from Event e where e.fees>0 and e.creationDate>=?1 and e.creationDate<=?2")
    public int findCountOfAllPaidEventCreatedInLast90Days(Timestamp time,Timestamp current);

    @Query("SELECT e from Event e where e.status=1 and e.creationDate>=?1 and e.creationDate<=?2 and e.forumCloseReason='Event Closed due to less no of participants'")
    public List<Event> findCountAllCancelledEvents(Timestamp time , Timestamp current);

    @Query("SELECT e from Event e where e.end_date>=?1 and e.end_date<=?2 and e.status!=1")
    public List<Event> findCountAllFinishedEvents(Timestamp time , Timestamp current);
}
