package com.cmpe275.term.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Review.class)
public abstract class Review_ {

	public static volatile SingularAttribute<Review, Long> revieweeId;
	public static volatile SingularAttribute<Review, Long> reviewerId;
	public static volatile SingularAttribute<Review, Integer> rating;
	public static volatile SingularAttribute<Review, Long> id;
	public static volatile SingularAttribute<Review, Event> event;
	public static volatile SingularAttribute<Review, String> reviewText;

	public static final String REVIEWEE_ID = "revieweeId";
	public static final String REVIEWER_ID = "reviewerId";
	public static final String RATING = "rating";
	public static final String ID = "id";
	public static final String EVENT = "event";
	public static final String REVIEW_TEXT = "reviewText";

}

