/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 03-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "rejectSORequest.xsd", path = "/resources/schemas/so/")
@XStreamAlias("rejectSORequest")
public class RejectSORequest {
	@XStreamAlias("reasonId")
	private String reasonId;
	
	@XStreamAlias("reasonDesc")
	private String reasonDesc;
	
	@XStreamAlias("reasonCommentDesc")
	private String reasonCommentDesc;	
	
	@XStreamAlias("resourceList")
	private Resource resourceList;
	

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public Resource getResourceList() {
		return resourceList;
	}

	public void setResourceList(Resource resourceList) {
		this.resourceList = resourceList;
	}

	public String getReasonCommentDesc() {
		return reasonCommentDesc;
	}

	public void setReasonCommentDesc(String reasonCommentDesc) {
		this.reasonCommentDesc = reasonCommentDesc;
	}
	
	
}
