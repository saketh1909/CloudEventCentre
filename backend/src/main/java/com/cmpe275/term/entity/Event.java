package com.cmpe275.term.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="EVENT_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Participant> participants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<EventForumQuestion> questions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Review> reviews;

    @Column(name="TITLE")
    private String title;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="START_DATE")
    private java.sql.Timestamp start_date;
    @Column(name="END_DATE")
    private java.sql.Timestamp end_date;
    @Column(name="DEADLINE")
    private java.sql.Timestamp deadline;
    @Column(name="CREATION_DATE")
    private java.sql.Timestamp creationDate;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "street")),
            @AttributeOverride( name = "city", column = @Column(name = "city")),
            @AttributeOverride( name = "state", column = @Column(name = "state")),
            @AttributeOverride( name = "zip", column = @Column(name = "zip"))
    })
    private Address address;
    @Column(name="MinParticipants")
    private int minParticipants;
    @Column(name="MaxParticipants")
    private int maxParticipants;
    @Column(name="ParticipantsCount")
    private int participantsCount;
    @Column(name="Fees")
    private double fees;
    @Column(name="AdmissionPolicy")
    private String policy;
    @Column(name="Status")
    private int status;
    @Column(name="EventForumCloseReason")
    private String forumCloseReason;
}
