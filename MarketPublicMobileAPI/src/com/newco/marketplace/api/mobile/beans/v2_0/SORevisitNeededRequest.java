package com.newco.marketplace.api.mobile.beans.v2_0;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the 
 * SORevisitNeeded Request
 * 
 * @author Infosys
 * 
 */
@XSD(name = "soRevisitNeededRequest.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "soRevisitNeededRequest")
@XStreamAlias("soRevisitNeededRequest")
public class SORevisitNeededRequest {
	
	@XStreamAlias("tripNumber")
	private Integer tripNumber;
	
	@XStreamAlias("nextApptDate")
	private String nextApptDate;
	
	@XStreamAlias("nextApptStartTime")
	private String nextApptStartTime;
	
	@XStreamAlias("nextApptEndTime")
	private String nextApptEndTime;
	
	@XStreamAlias("revisitReason")
	private String revisitReason;

	@XStreamAlias("workStartedInd")
	private Integer workStartedInd;

	@XStreamAlias("merchDeliveredInd")
	private Integer merchDeliveredInd;
	
	@XStreamAlias("revisitComments")
	private String revisitComments;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;

	public Integer getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(Integer tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getNextApptDate() {
		return nextApptDate;
	}

	public void setNextApptDate(String nextApptDate) {
		this.nextApptDate = nextApptDate;
	}

	public String getNextApptStartTime() {
		return nextApptStartTime;
	}

	public void setNextApptStartTime(String nextApptStartTime) {
		this.nextApptStartTime = nextApptStartTime;
	}

	public String getNextApptEndTime() {
		return nextApptEndTime;
	}

	public void setNextApptEndTime(String nextApptEndTime) {
		this.nextApptEndTime = nextApptEndTime;
	}

	public String getRevisitReason() {
		return revisitReason;
	}

	public void setRevisitReason(String revisitReason) {
		this.revisitReason = revisitReason;
	}

	public Integer getWorkStartedInd() {
		return workStartedInd;
	}

	public void setWorkStartedInd(Integer workStartedInd) {
		this.workStartedInd = workStartedInd;
	}

	public Integer getMerchDeliveredInd() {
		return merchDeliveredInd;
	}

	public void setMerchDeliveredInd(Integer merchDeliveredInd) {
		this.merchDeliveredInd = merchDeliveredInd;
	}

	public String getRevisitComments() {
		return revisitComments;
	}

	public void setRevisitComments(String revisitComments) {
		this.revisitComments = revisitComments;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}
	
	
}
