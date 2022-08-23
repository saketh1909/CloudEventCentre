package com.cmpe275.term.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="User_ID")
    private long id;
    @Column(name="Email")
    private String email;
    @Column(name="Password")
    private String password;
    @Column(name="Account_Type")
    private String accountType;
    @Column(name="User_Name")
    private String name;
    @Column(name="Screen_Name")
    private String screenName;
    @Column(name="Gender")
    private String gender;
    @Column(name="DESC")
    private String description;

    @Column(name="averageRating")
    private double averageRating;

    @Column(name="totalRatingsReceived")
    private int totalRatingsReceived;

    @Column(name="generatedOTP")
    private String OTP;

    @Column(name = "accountValidated")
    private int accountValidated;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "street")),
            @AttributeOverride( name = "city", column = @Column(name = "city")),
            @AttributeOverride( name = "state", column = @Column(name = "state")),
            @AttributeOverride( name = "zip", column = @Column(name = "zip"))
    })
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Event> events;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Participant> participants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<EventForumQuestion> questions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<EventForumAnswer> answers;

}
