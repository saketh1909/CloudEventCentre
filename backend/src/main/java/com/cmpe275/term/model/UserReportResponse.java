package com.cmpe275.term.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserReportResponse {
    private int noOfSignedUpEventsByUser;
    private int NoOfRejectsForUser;
    private int noOfApprovalsForUser;
    private int finishedEventsForUser;
    private int totalEventsCreated;
    private int totalPaidEvents;
    private int totalCancelledEvents;
    private int totalMinimumParticipationForCancelledEvents;
    private int totalRequestForCancelledEvents;
    private int totalFinishedEvents;
    private int totalParticipantsInFinishedEvents;
    private int totalRevenueFromFinishedEvents;

}
