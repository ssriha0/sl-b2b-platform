package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:38 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNInsuranceParameterBean extends RatingParameterBean {
	
	private static final long serialVersionUID = -7027325903419207259L;
	private Integer resourceId;
	private boolean generalInsuranceChecked;
	private Double generalInsuranceMinAmount;
	private boolean autoInsuranceChecked;
	private Double autoInsuranceMinAmount;
	private boolean workmanCompInsuranceChecked;
	private Double workmanCompInsuranceMinAmount;
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the generalInsuranceChecked
	 */
	public boolean isGeneralInsuranceChecked() {
		return generalInsuranceChecked;
	}
	/**
	 * @param generalInsuranceChecked the generalInsuranceChecked to set
	 */
	public void setGeneralInsuranceChecked(boolean generalInsuranceChecked) {
		this.generalInsuranceChecked = generalInsuranceChecked;
	}
	/**
	 * @return the generalInsuranceMinAmount
	 */
	public Double getGeneralInsuranceMinAmount() {
		return generalInsuranceMinAmount;
	}
	/**
	 * @param generalInsuranceMinAmount the generalInsuranceMinAmount to set
	 */
	public void setGeneralInsuranceMinAmount(Double generalInsuranceMinAmount) {
		this.generalInsuranceMinAmount = generalInsuranceMinAmount;
	}
	/**
	 * @return the autoInsuranceChecked
	 */
	public boolean isAutoInsuranceChecked() {
		return autoInsuranceChecked;
	}
	/**
	 * @param autoInsuranceChecked the autoInsuranceChecked to set
	 */
	public void setAutoInsuranceChecked(boolean autoInsuranceChecked) {
		this.autoInsuranceChecked = autoInsuranceChecked;
	}
	/**
	 * @return the autoInsuranceMinAmount
	 */
	public Double getAutoInsuranceMinAmount() {
		return autoInsuranceMinAmount;
	}
	/**
	 * @param autoInsuranceMinAmount the autoInsuranceMinAmount to set
	 */
	public void setAutoInsuranceMinAmount(Double autoInsuranceMinAmount) {
		this.autoInsuranceMinAmount = autoInsuranceMinAmount;
	}
	/**
	 * @return the workmanCompInsuranceChecked
	 */
	public boolean isWorkmanCompInsuranceChecked() {
		return workmanCompInsuranceChecked;
	}
	/**
	 * @param workmanCompInsuranceChecked the workmanCompInsuranceChecked to set
	 */
	public void setWorkmanCompInsuranceChecked(boolean workmanCompInsuranceChecked) {
		this.workmanCompInsuranceChecked = workmanCompInsuranceChecked;
	}
	/**
	 * @return the workmanCompInsuranceMinAmount
	 */
	public Double getWorkmanCompInsuranceMinAmount() {
		return workmanCompInsuranceMinAmount;
	}
	/**
	 * @param workmanCompInsuranceMinAmount the workmanCompInsuranceMinAmount to set
	 */
	public void setWorkmanCompInsuranceMinAmount(
			Double workmanCompInsuranceMinAmount) {
		this.workmanCompInsuranceMinAmount = workmanCompInsuranceMinAmount;
	}
	
	
}

/*
 * Maintenance History:
 * $Log: SPNInsuranceParameterBean.java,v $
 * Revision 1.2  2008/05/02 21:23:38  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.1  2008/04/18 00:23:52  mhaye05
 * added logic for inviting service pros to spn
 *
 */