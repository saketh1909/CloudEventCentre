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
@Table(name="Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Participant_ID")
    private long id;

    @Column(name="reviewText")
    private String reviewText;

    @Column(name="rating")
    private int rating;

    @Column(name="reviewerId")
    private long reviewerId;

    @Column(name="revieweeId")
    private long revieweeId;

    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    @JsonBackReference
    private Event event;


}
