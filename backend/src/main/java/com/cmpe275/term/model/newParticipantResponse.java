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
public class newParticipantResponse {
    private long id;
    private int amountPaid;
    private int approval;
    private Event event;
    private User user;
}

