package com.VMS.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Disease.class)
public abstract class Disease_ {

	public static volatile SingularAttribute<Disease, String> diseaseName;
	public static volatile SingularAttribute<Disease, Integer> diseaseId;
	public static volatile SingularAttribute<Disease, String> diseaseDesc;

	public static final String DISEASE_NAME = "diseaseName";
	public static final String DISEASE_ID = "diseaseId";
	public static final String DISEASE_DESC = "diseaseDesc";

}

