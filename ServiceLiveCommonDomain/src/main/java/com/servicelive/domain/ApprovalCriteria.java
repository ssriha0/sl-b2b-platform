/*
 * @(#)ApprovalCriteria.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.domain;

import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;

/**
 * @author Mahmud Khair
 *
 */

public interface ApprovalCriteria  {

	/**
	 * 
	 * @return LookupSPNApprovalCriteria
	 */
	public LookupSPNApprovalCriteria getCriteriaId();


	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(LookupSPNApprovalCriteria criteriaId);


	/**
	 * @return the value
	 */
	public String getValue();


	/**
	 * @param value the value to set
	 */
	public void setValue(String value);


	/**
	 * 
	 * @param modifiedBy
	 */
	public void setModifiedBy(String modifiedBy);
}
