package com.cmpe275.term.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class EventAnswerResponse {
    private Long id;
    private String answer;
    private UsersRes user;
    private String imageURL;
}
