
/*
 * This is a bean class for storing response information for 
 * the SOUpdateService
 * @author Infosys
 */

package com.newco.marketplace.api.beans.so.update;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("soEstimate")
public class SOUpdateEstimateRequest extends UserIdentificationRequest{
	
	@XStreamAlias("estimationId")
	private String estimationId;
	
	@XStreamAlias("action")
	private String action;
	
	@OptionalParam
	@XStreamAlias("comments")
	private String comments;
	
	@XStreamAlias("customerName")
	private String customerName;

	public String getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(String estimationId) {
		this.estimationId = estimationId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
