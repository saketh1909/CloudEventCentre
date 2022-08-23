package com.cmpe275.term.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private int noOfEventsCreated;
    private int noOfPaidEvents;
    private int noOfCancelledEvents;
    private int totalNoOfParticipationRequestsForCancelledEvents;
    private int totalNoOfMinimumParticipantsForCancelledEvents;
    private int totalNoOfFinishedEvents;
    private double averageNoOfParticipantsForFinishedEvents;
}
