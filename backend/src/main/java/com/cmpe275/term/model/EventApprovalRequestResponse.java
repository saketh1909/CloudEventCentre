package com.cmpe275.term.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class EventApprovalRequestResponse {
    private Long id;
    private UsersRes user;
    private int approval;
}
