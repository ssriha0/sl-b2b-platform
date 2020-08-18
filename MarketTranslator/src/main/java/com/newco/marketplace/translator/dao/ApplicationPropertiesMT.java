package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ApplicationProperties entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "application_properties",  uniqueConstraints = {})
public class ApplicationPropertiesMT extends AbstractApplicationProperties
		implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ApplicationPropertiesMT() {
		// intentionally blank
	}

	/** minimal constructor */
	public ApplicationPropertiesMT(String appKey, String appValue, String appDesc) {
		super(appKey, appValue, appDesc);
	}

	/** full constructor */
	public ApplicationPropertiesMT(String appKey, String appValue,
			String appDesc, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		super(appKey, appValue, appDesc, createdDate, modifiedDate, modifiedBy);
	}

}
