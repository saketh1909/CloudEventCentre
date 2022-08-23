package com.cmpe275.term.repository;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Review")
public interface ReviewRepository extends JpaRepository<Review,Long> {

    public List<Review> findByreviewerId(Long reviewerId);

    public List<Review> findByrevieweeId(Long revieweeId);

    public List<Review> findByEvent(Event e);
}
