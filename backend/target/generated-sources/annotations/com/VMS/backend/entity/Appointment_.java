package com.VMS.backend.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Appointment.class)
public abstract class Appointment_ {

	public static volatile SingularAttribute<Appointment, Date> createdDate;
	public static volatile ListAttribute<Appointment, Vaccination> vaccinations;
	public static volatile SingularAttribute<Appointment, String> appointmentTimeStr;
	public static volatile SingularAttribute<Appointment, Integer> appointmentId;
	public static volatile SingularAttribute<Appointment, Date> appointmentDateTime;
	public static volatile SingularAttribute<Appointment, Clinic> clinic;
	public static volatile SingularAttribute<Appointment, String> appointmentDateStr;
	public static volatile SingularAttribute<Appointment, User> user;
	public static volatile SingularAttribute<Appointment, Integer> isChecked;

	public static final String CREATED_DATE = "createdDate";
	public static final String VACCINATIONS = "vaccinations";
	public static final String APPOINTMENT_TIME_STR = "appointmentTimeStr";
	public static final String APPOINTMENT_ID = "appointmentId";
	public static final String APPOINTMENT_DATE_TIME = "appointmentDateTime";
	public static final String CLINIC = "clinic";
	public static final String APPOINTMENT_DATE_STR = "appointmentDateStr";
	public static final String USER = "user";
	public static final String IS_CHECKED = "isChecked";

}

