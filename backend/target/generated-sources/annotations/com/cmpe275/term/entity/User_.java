package com.cmpe275.term.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Address> address;
	public static volatile SingularAttribute<User, String> gender;
	public static volatile SingularAttribute<User, String> accountType;
	public static volatile ListAttribute<User, EventForumQuestion> questions;
	public static volatile ListAttribute<User, EventForumAnswer> answers;
	public static volatile SingularAttribute<User, String> description;
	public static volatile SingularAttribute<User, Integer> totalRatingsReceived;
	public static volatile SingularAttribute<User, String> OTP;
	public static volatile SingularAttribute<User, String> screenName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Double> averageRating;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, Integer> accountValidated;
	public static volatile SingularAttribute<User, String> email;
	public static volatile ListAttribute<User, Event> events;
	public static volatile ListAttribute<User, Participant> participants;

	public static final String ADDRESS = "address";
	public static final String GENDER = "gender";
	public static final String ACCOUNT_TYPE = "accountType";
	public static final String QUESTIONS = "questions";
	public static final String ANSWERS = "answers";
	public static final String DESCRIPTION = "description";
	public static final String TOTAL_RATINGS_RECEIVED = "totalRatingsReceived";
	public static final String O_TP = "OTP";
	public static final String SCREEN_NAME = "screenName";
	public static final String PASSWORD = "password";
	public static final String AVERAGE_RATING = "averageRating";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String ACCOUNT_VALIDATED = "accountValidated";
	public static final String EMAIL = "email";
	public static final String EVENTS = "events";
	public static final String PARTICIPANTS = "participants";

}

