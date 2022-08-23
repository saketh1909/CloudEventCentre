package com.cmpe275.term.repository;

import com.cmpe275.term.entity.EventForumAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventForumAnswersRepository extends JpaRepository<EventForumAnswer,Long> {

    public List<EventForumAnswer> findById(long id);
}
