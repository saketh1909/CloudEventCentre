package com.cmpe275.term.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EventForumQuestion.class)
public abstract class EventForumQuestion_ {

	public static volatile SingularAttribute<EventForumQuestion, String> question;
	public static volatile SingularAttribute<EventForumQuestion, Integer> forumType;
	public static volatile ListAttribute<EventForumQuestion, EventForumAnswer> answers;
	public static volatile SingularAttribute<EventForumQuestion, Long> id;
	public static volatile SingularAttribute<EventForumQuestion, Event> event;
	public static volatile SingularAttribute<EventForumQuestion, User> user;

	public static final String QUESTION = "question";
	public static final String FORUM_TYPE = "forumType";
	public static final String ANSWERS = "answers";
	public static final String ID = "id";
	public static final String EVENT = "event";
	public static final String USER = "user";

}

