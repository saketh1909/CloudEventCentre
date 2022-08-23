package com.VMS.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserVaccinations.class)
public abstract class UserVaccinations_ {

	public static volatile SingularAttribute<UserVaccinations, Integer> dosesLeft;
	public static volatile SingularAttribute<UserVaccinations, Integer> vaccinationId;
	public static volatile SingularAttribute<UserVaccinations, Integer> id;
	public static volatile SingularAttribute<UserVaccinations, Integer> userId;
	public static volatile SingularAttribute<UserVaccinations, String> nextAppointmentTime;

	public static final String DOSES_LEFT = "dosesLeft";
	public static final String VACCINATION_ID = "vaccinationId";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String NEXT_APPOINTMENT_TIME = "nextAppointmentTime";

}

