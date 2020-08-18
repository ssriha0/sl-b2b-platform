package com.servicelive.notification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the In Home Out
 * bound Notification Service
 * 
 * @author Infosys
 */
@XStreamAlias("userDefFields")
public class UserDefFields {

	@XStreamAlias("fieldIDNum")
	private String fieldIDNum;

	@XStreamAlias("facCatCd")
	private String facCatCd;

	@XStreamAlias("fieldLnItmXref")
	private String fieldLnItmXref;

	@XStreamAlias("fieldLnItmId")
	private String fieldLnItmId;

	@XStreamAlias("ruleId")
	private String ruleId;

	@XStreamAlias("profileFl")
	private String profileFl;

	@XStreamAlias("fieldName")
	private String fieldName;

	@XStreamAlias("fieldValue")
	private String fieldValue;

	public String getFieldIDNum() {
		return fieldIDNum;
	}

	public void setFieldIDNum(String fieldIDNum) {
		this.fieldIDNum = fieldIDNum;
	}

	public String getFacCatCd() {
		return facCatCd;
	}

	public void setFacCatCd(String facCatCd) {
		this.facCatCd = facCatCd;
	}

	public String getFieldLnItmXref() {
		return fieldLnItmXref;
	}

	public void setFieldLnItmXref(String fieldLnItmXref) {
		this.fieldLnItmXref = fieldLnItmXref;
	}

	public String getFieldLnItmId() {
		return fieldLnItmId;
	}

	public void setFieldLnItmId(String fieldLnItmId) {
		this.fieldLnItmId = fieldLnItmId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getProfileFl() {
		return profileFl;
	}

	public void setProfileFl(String profileFl) {
		this.profileFl = profileFl;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
