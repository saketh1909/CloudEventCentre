package com.cmpe275.term.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Participant_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    @JsonBackReference
    private Event event;

    @Column(name="Approval")
    private int approval;

    @Column(name="AmountPaid")
    private int amountPaid;

    @Column(name="CREATION_DATE")
    private java.sql.Timestamp creationDate;

}
