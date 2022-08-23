package com.cmpe275.term.repository;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("participants")
public interface ParticipantRepository extends JpaRepository<Participant,Long> {

    public Participant findById(long participantId);
    public List<Participant> findByUserAndEvent(User user, Event event);

    public List<Participant> findByEventAndApproval(Event event,int approval);

    public List<Participant> findByEvent(Event event);


    public List<Participant> findByUser(User user);

}
