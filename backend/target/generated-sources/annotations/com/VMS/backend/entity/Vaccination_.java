package com.VMS.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vaccination.class)
public abstract class Vaccination_ {

	public static volatile SingularAttribute<Vaccination, Integer> duration;
	public static volatile SingularAttribute<Vaccination, Integer> numberOfShots;
	public static volatile SingularAttribute<Vaccination, Integer> shotInternalVal;
	public static volatile SingularAttribute<Vaccination, String> vaccinationName;
	public static volatile SingularAttribute<Vaccination, Integer> vaccinationId;
	public static volatile ListAttribute<Vaccination, Disease> diseases;
	public static volatile SingularAttribute<Vaccination, String> manufacturer;

	public static final String DURATION = "duration";
	public static final String NUMBER_OF_SHOTS = "numberOfShots";
	public static final String SHOT_INTERNAL_VAL = "shotInternalVal";
	public static final String VACCINATION_NAME = "vaccinationName";
	public static final String VACCINATION_ID = "vaccinationId";
	public static final String DISEASES = "diseases";
	public static final String MANUFACTURER = "manufacturer";

}

