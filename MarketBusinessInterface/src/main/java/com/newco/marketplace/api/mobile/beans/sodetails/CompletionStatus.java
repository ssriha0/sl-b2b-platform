package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of status.
 * @author Infosys
 *
 */


@XStreamAlias("completionStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompletionStatus {
	
	@XStreamAlias("resolutionComments")
	private String resolutionComments;
	
	@XStreamAlias("photos")
	private String photos;
	
	@XStreamAlias("customReferences")
	private String customReferences;
	
	@XStreamAlias("permits")
	private String permits;
	
	@XStreamAlias("additionalServices")
	private String additionalServices;
	
	@XStreamAlias("customerPayment")
	private String customerPayment;
	
	@XStreamAlias("parts")
	private String parts;
	
	@XStreamAlias("signature")
	private String signature;
	
	@XStreamAlias("partsTracking")
	private String partsTracking;

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getCustomReferences() {
		return customReferences;
	}

	public void setCustomReferences(String customReferences) {
		this.customReferences = customReferences;
	}

	public String getPermits() {
		return permits;
	}

	public void setPermits(String permits) {
		this.permits = permits;
	}

	public String getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(String additionalServices) {
		this.additionalServices = additionalServices;
	}

	public String getCustomerPayment() {
		return customerPayment;
	}

	public void setCustomerPayment(String customerPayment) {
		this.customerPayment = customerPayment;
	}

	public String getParts() {
		return parts;
	}

	public void setParts(String parts) {
		this.parts = parts;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPartsTracking() {
		return partsTracking;
	}

	public void setPartsTracking(String partsTracking) {
		this.partsTracking = partsTracking;
	}
	
}
