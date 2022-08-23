package com.cmpe275.term.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ {

	public static volatile SingularAttribute<Event, Timestamp> end_date;
	public static volatile SingularAttribute<Event, Integer> participantsCount;
	public static volatile SingularAttribute<Event, Double> fees;
	public static volatile SingularAttribute<Event, Address> address;
	public static volatile ListAttribute<Event, EventForumQuestion> questions;
	public static volatile SingularAttribute<Event, String> description;
	public static volatile SingularAttribute<Event, String> title;
	public static volatile SingularAttribute<Event, Timestamp> creationDate;
	public static volatile SingularAttribute<Event, Integer> minParticipants;
	public static volatile SingularAttribute<Event, Integer> maxParticipants;
	public static volatile SingularAttribute<Event, String> forumCloseReason;
	public static volatile ListAttribute<Event, Review> reviews;
	public static volatile SingularAttribute<Event, Long> id;
	public static volatile SingularAttribute<Event, Timestamp> deadline;
	public static volatile SingularAttribute<Event, User> user;
	public static volatile ListAttribute<Event, Participant> participants;
	public static volatile SingularAttribute<Event, Timestamp> start_date;
	public static volatile SingularAttribute<Event, String> policy;
	public static volatile SingularAttribute<Event, Integer> status;

	public static final String END_DATE = "end_date";
	public static final String PARTICIPANTS_COUNT = "participantsCount";
	public static final String FEES = "fees";
	public static final String ADDRESS = "address";
	public static final String QUESTIONS = "questions";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String CREATION_DATE = "creationDate";
	public static final String MIN_PARTICIPANTS = "minParticipants";
	public static final String MAX_PARTICIPANTS = "maxParticipants";
	public static final String FORUM_CLOSE_REASON = "forumCloseReason";
	public static final String REVIEWS = "reviews";
	public static final String ID = "id";
	public static final String DEADLINE = "deadline";
	public static final String USER = "user";
	public static final String PARTICIPANTS = "participants";
	public static final String START_DATE = "start_date";
	public static final String POLICY = "policy";
	public static final String STATUS = "status";

}

