package com.cmpe275.term.repository;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.model.EventResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventSearchRepositoryCustom {
    public List<Event> findAllEventsByPredicates(String location, String status, String startTime, String endTime, String keyword, String organizer);
}
