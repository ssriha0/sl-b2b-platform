package com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3;

import java.sql.Timestamp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing reschedule information for 
 * the SORescheduleService
 * @author Infosys
 *
 */
@XStreamAlias("WarrantyReasonCodeInfo")
public class WarrantyHomeReasonInfo {

	
	
	@XStreamAlias("reasonCode")
	private Integer reasonCode;
	
	@XStreamAlias("comments")
	private String comments;
	

	

	public Integer getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

	
	

	
}
