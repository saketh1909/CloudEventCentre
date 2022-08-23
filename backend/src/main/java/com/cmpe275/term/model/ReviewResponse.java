package com.cmpe275.term.model;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private long id;
    private int rating;
    private String reviewComment;
    private UsersRes reviewer;
    private UsersRes reviewee;
    private Event event;

}
