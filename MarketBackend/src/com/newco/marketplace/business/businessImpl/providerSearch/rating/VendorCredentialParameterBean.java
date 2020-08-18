package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:37 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class VendorCredentialParameterBean extends RatingParameterBean {

	private static final long serialVersionUID = 5786328570635425122L;
	private Integer credentialCategoryId;
	private Integer credentialTypeId;
	


	/**
	 * @return the credentialCategoryId
	 */
	public Integer getCredentialCategoryId() {
		return credentialCategoryId;
	}

	/**
	 * @param credentialCategoryId the credentialCategoryId to set
	 */
	public void setCredentialCategoryId(Integer credentialCategoryId) {
		this.credentialCategoryId = credentialCategoryId;
	}

	public Integer getCredentialTypeId() {
		return credentialTypeId;
	}

	public void setCredentialTypeId(Integer credentialTypeId) {
		this.credentialTypeId = credentialTypeId;
	}

}

/*
 * Maintenance History:
 * $Log: VendorCredentialParameterBean.java,v $
 * Revision 1.2  2008/05/02 21:23:37  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.1  2008/04/23 14:01:00  mhaye05
 * backend for spn camaign batch job
 *
 */