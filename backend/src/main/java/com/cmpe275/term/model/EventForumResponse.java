package com.cmpe275.term.model;


import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.EventForumAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class EventForumResponse {
    private long id;
    private UsersRes user;
    private EventResponse event;
    private String question;
    private int forumType;
    private List<EventAnswerResponse> answers;

}
