package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.xfire.aegis.type.java5.XmlElement;


public class RouteRequest extends ABaseWebserviceRequest {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2426355119586348217L;
	private String serviceOrderId;
	private Integer distance;
	private Integer backgroundCheck;
	private Double rating;
	private ArrayList<Integer> languageList;
	private Integer credentialID;
	private String templateName;
	/**
	 * lu_resource_credential_type.cred_type_id
	 */
	private Integer credentialTypeId;
	/**
	 * lu_resource_credential_category.cred_category_id
	 */
	private Integer credentialCatagoryId;
	
	
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("serviceOrderId", getServiceOrderId())
			//.append("providerList", getProviderList())
			.toString();
	}
	
	@XmlElement(minOccurs="1", nillable=false)
	public String getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	@XmlElement(minOccurs="1", nillable=false)
	public Integer getDistance() {
		return distance;
	}
	
	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	@XmlElement(minOccurs="1", nillable=false)
	public Integer getBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(Integer backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public ArrayList<Integer> getLanguageList() {
		return languageList;
	}
	
	public void setLanguageList(ArrayList<Integer> languageList) {
		this.languageList = languageList;
	}

	public Integer getCredentialID() {
		return credentialID;
	}

	public void setCredentialID(Integer credentialID) {
		this.credentialID = credentialID;
	}

	public Integer getCredentialTypeId() {
		return credentialTypeId;
	}

	public void setCredentialTypeId(Integer credentialTypeId) {
		this.credentialTypeId = credentialTypeId;
	}

	public Integer getCredentialCatagoryId() {
		return credentialCatagoryId;
	}

	public void setCredentialCatagoryId(Integer credentialCatagoryId) {
		this.credentialCatagoryId = credentialCatagoryId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
