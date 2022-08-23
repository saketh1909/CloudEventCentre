package com.VMS.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Appointment> appointments;
	public static volatile SingularAttribute<User, Address> address;
	public static volatile SingularAttribute<User, String> gender;
	public static volatile SingularAttribute<User, String> dob;
	public static volatile SingularAttribute<User, Integer> mrn;
	public static volatile SingularAttribute<User, Boolean> verified;
	public static volatile SingularAttribute<User, Boolean> admin;
	public static volatile SingularAttribute<User, String> middleName;
	public static volatile SingularAttribute<User, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String APPOINTMENTS = "appointments";
	public static final String ADDRESS = "address";
	public static final String GENDER = "gender";
	public static final String DOB = "dob";
	public static final String MRN = "mrn";
	public static final String VERIFIED = "verified";
	public static final String ADMIN = "admin";
	public static final String MIDDLE_NAME = "middleName";
	public static final String EMAIL = "email";

}

