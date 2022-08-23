package com.cmpe275.term.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Participant.class)
public abstract class Participant_ {

	public static volatile SingularAttribute<Participant, Integer> amountPaid;
	public static volatile SingularAttribute<Participant, Integer> approval;
	public static volatile SingularAttribute<Participant, Long> id;
	public static volatile SingularAttribute<Participant, Event> event;
	public static volatile SingularAttribute<Participant, Timestamp> creationDate;
	public static volatile SingularAttribute<Participant, User> user;

	public static final String AMOUNT_PAID = "amountPaid";
	public static final String APPROVAL = "approval";
	public static final String ID = "id";
	public static final String EVENT = "event";
	public static final String CREATION_DATE = "creationDate";
	public static final String USER = "user";

}

