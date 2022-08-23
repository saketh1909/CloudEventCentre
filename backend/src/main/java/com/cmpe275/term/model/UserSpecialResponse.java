package com.cmpe275.term.model;

import com.cmpe275.term.entity.Address;
import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.EventForumAnswer;
import com.cmpe275.term.entity.EventForumQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecialResponse {
    private long id;
    private String email;
    private String accountType;
    private String name;
    private String screenName;
    private String gender;
    private String description;
    private double averageRating;
    private int totalRatingsReceived;
    private int accountValidated;
    private Address address;
    private List<Event> events;
    private List<ParticipantRes> participants;
    private List<EventForumQuestion> questions;
    private List<EventForumAnswer> answers;
}
