package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author ndixit
 *
 */
@XSD(name="reassignSORequest.xsd", path="/resources/schemas/so/")
@XStreamAlias("reassignSORequest")
public class ReassignSORequest extends UserIdentificationRequest {
	
	@XStreamAlias("providerResource")
	private Integer resourceId;
	
	@XStreamAlias("reassignComment")
	private String reassignComment;
		
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getReassignComment() {
		return reassignComment;
	}

	public void setReassignComment(String reassignComment) {
		this.reassignComment = reassignComment;
	}

}
