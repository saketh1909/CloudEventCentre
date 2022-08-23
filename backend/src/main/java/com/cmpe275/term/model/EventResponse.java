package com.cmpe275.term.model;


import com.cmpe275.term.entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private long id;
    private long userId;
    private String title;
    private String description;
    private java.sql.Timestamp start_date;
    private java.sql.Timestamp end_date;
    private java.sql.Timestamp deadline;
    private Address address;
    private int minParticipants;
    private int maxParticipants;
    private int participantsCount;
    private List<newParticipantResponse> participants;
    private int pendingRequestCount;
    private double fees;
    private String policy;
    private int status;
    private UsersRes user;
    private String forumCloseReason;
}
