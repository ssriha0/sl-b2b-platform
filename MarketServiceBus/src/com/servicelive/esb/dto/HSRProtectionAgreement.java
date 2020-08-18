package com.servicelive.esb.dto;

import java.io.Serializable;

public class HSRProtectionAgreement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String agreementNumber;
	
	private String agreementType;
	
	private String agreementExpDate;
	
	private String agreementPlanType;

	/**
	 * @return the agreementNumber
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * @param agreementNumber the agreementNumber to set
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	/**
	 * @return the agreementType
	 */
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @param agreementType the agreementType to set
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * @return the agreementExpDate
	 */
	public String getAgreementExpDate() {
		return agreementExpDate;
	}

	/**
	 * @param agreementExpDate the agreementExpDate to set
	 */
	public void setAgreementExpDate(String agreementExpDate) {
		this.agreementExpDate = agreementExpDate;
	}

	/**
	 * @return the agreementPlanType
	 */
	public String getAgreementPlanType() {
		return agreementPlanType;
	}

	/**
	 * @param agreementPlanType the agreementPlanType to set
	 */
	public void setAgreementPlanType(String agreementPlanType) {
		this.agreementPlanType = agreementPlanType;
	}
}
