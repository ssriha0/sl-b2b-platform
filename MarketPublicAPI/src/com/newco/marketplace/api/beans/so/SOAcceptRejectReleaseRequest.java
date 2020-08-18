/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 03-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("acceptRejectReleaseSORequest")
public class SOAcceptRejectReleaseRequest extends UserIdentificationRequest {
	@XStreamAlias("type")
	private String type;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("rejectReasonId")
	private Integer rejectReasonId;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRejectreasonId() {
		return rejectReasonId;
	}

	public void setRejectreasonId(Integer rejectreasonId) {
		this.rejectReasonId = rejectreasonId;
	}

	
	
}
