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
@Table(name="EventForumAnswer")
public class EventForumAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Answer_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name="QUESTION_ID")
    @JsonBackReference
    private EventForumQuestion question;

    @Column(name="Answer")
    private String answer;

    @Column(name="ImageURL")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    @JsonBackReference
    private User user;

}
