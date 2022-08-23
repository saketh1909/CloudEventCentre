package com.cmpe275.term.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class SystemReportResponse {
    private int NoOfEvents;
    private int NoOfPaidEvents;
    private int NoOfCancelledEvents;
    private int totalNoOfMinimumParticipantsForCancelledEvents;
    private int totalRequestsObtainedForCancelledEvents;
    private int NoOfFinishedEvents;
    private int totalParticipantsInFinishedEvents;
}
