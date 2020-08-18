package com.newco.marketplace.buyeroutboundnotification.beans;


import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RESCHEDINFO")
public class RequestRescheduleInfo {
	
	@XStreamAlias("reschedCancelModificationDate")
	private String reschedCancelModificationDate;
	
	@XStreamAlias("reschedCancelModificationTime")
	private String reschedCancelModificationTime;
	
	@XStreamAlias("rescheduleReasonCode")
	private String rescheduleReasonCode;
	
	@XStreamAlias("rescheduleModificationID")
	private String rescheduleModificationID;
	
	@XStreamAlias("rescheduleModificationUnitNo")
	private String rescheduleModificationUnitNo;
	
	@XStreamAlias("rescheduleRsnCdDescription")
	private String rescheduleRsnCdDescription;
	
	

	public String getReschedCancelModificationDate() {
		return reschedCancelModificationDate;
	}

	public void setReschedCancelModificationDate(
			String reschedCancelModificationDate) {
		this.reschedCancelModificationDate = reschedCancelModificationDate;
	}

	public String getReschedCancelModificationTime() {
		return reschedCancelModificationTime;
	}

	public void setReschedCancelModificationTime(
			String reschedCancelModificationTime) {
		this.reschedCancelModificationTime = reschedCancelModificationTime;
	}

	public String getRescheduleReasonCode() {
		return rescheduleReasonCode;
	}

	public void setRescheduleReasonCode(String rescheduleReasonCode) {
		this.rescheduleReasonCode = rescheduleReasonCode;
	}

	public String getRescheduleModificationID() {
		return rescheduleModificationID;
	}

	public void setRescheduleModificationID(String rescheduleModificationID) {
		this.rescheduleModificationID = rescheduleModificationID;
	}

	public String getRescheduleModificationUnitNo() {
		return rescheduleModificationUnitNo;
	}

	public void setRescheduleModificationUnitNo(String rescheduleModificationUnitNo) {
		this.rescheduleModificationUnitNo = rescheduleModificationUnitNo;
	}

	public String getRescheduleRsnCdDescription() {
		return rescheduleRsnCdDescription;
	}

	public void setRescheduleRsnCdDescription(String rescheduleRsnCdDescription) {
		this.rescheduleRsnCdDescription = rescheduleRsnCdDescription;
	}

	
	
	
	
	
	
	
	

}
