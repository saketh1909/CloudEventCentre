package com.cmpe275.term.repository;

import com.cmpe275.term.entity.EventForumQuestion;
import com.cmpe275.term.model.EventForumResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventForumQuestionsRepository extends JpaRepository<EventForumQuestion,Long> {
    public Optional<EventForumQuestion> findById(Long id);

    public List<EventForumQuestion> findByEventIdAndForumType(Long eventId, int forumType);
}
