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
@Table(name="EventForumQuestion")
public class EventForumQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Question_ID")
    private long id;


    @Column(name="question")
    private String question;

    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    @JsonBackReference
    private Event event;


    @ManyToOne
    @JoinColumn(name="USER_ID")
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<EventForumAnswer> answers;

    @Column(name="ForumType")
    private int forumType;
}
