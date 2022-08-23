package com.VMS.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Clinic.class)
public abstract class Clinic_ {

	public static volatile ListAttribute<Clinic, Appointment> appointments;
	public static volatile SingularAttribute<Clinic, Address> address;
	public static volatile SingularAttribute<Clinic, Integer> endBussinessHour;
	public static volatile SingularAttribute<Clinic, Integer> startBussinessHour;
	public static volatile SingularAttribute<Clinic, String> name;
	public static volatile SingularAttribute<Clinic, Integer> id;
	public static volatile SingularAttribute<Clinic, Integer> noOfPhysician;

	public static final String APPOINTMENTS = "appointments";
	public static final String ADDRESS = "address";
	public static final String END_BUSSINESS_HOUR = "endBussinessHour";
	public static final String START_BUSSINESS_HOUR = "startBussinessHour";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String NO_OF_PHYSICIAN = "noOfPhysician";

}

