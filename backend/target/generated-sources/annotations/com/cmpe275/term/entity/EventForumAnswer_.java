package com.cmpe275.term.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EventForumAnswer.class)
public abstract class EventForumAnswer_ {

	public static volatile SingularAttribute<EventForumAnswer, EventForumQuestion> question;
	public static volatile SingularAttribute<EventForumAnswer, String> answer;
	public static volatile SingularAttribute<EventForumAnswer, String> imageURL;
	public static volatile SingularAttribute<EventForumAnswer, Long> id;
	public static volatile SingularAttribute<EventForumAnswer, User> user;

	public static final String QUESTION = "question";
	public static final String ANSWER = "answer";
	public static final String IMAGE_UR_L = "imageURL";
	public static final String ID = "id";
	public static final String USER = "user";

}

