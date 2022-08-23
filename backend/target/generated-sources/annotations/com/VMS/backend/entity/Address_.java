package com.VMS.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, Integer> zipcode;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, String> state;
	public static volatile SingularAttribute<Address, String> aptNo;

	public static final String ZIPCODE = "zipcode";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String STATE = "state";
	public static final String APT_NO = "aptNo";

}

