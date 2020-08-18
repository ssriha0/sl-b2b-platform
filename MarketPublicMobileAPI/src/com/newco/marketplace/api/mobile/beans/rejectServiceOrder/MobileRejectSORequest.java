package com.newco.marketplace.api.mobile.beans.rejectServiceOrder;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.mobile.common.ResultsCode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a bean class for storing request information for the
 * MobileSORejectService 
 * @author Infosys
 * @version 1.0
 * @Date 2015/04/16
 */

@XSD(name = "mobileRejectSORequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "mobileRejectSORequest")
@XStreamAlias("mobileRejectSORequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileRejectSORequest {
	
	@XStreamAlias("reasonId")
	private Integer reasonId;

	@XStreamAlias("reasonCommentDesc")
	private String reasonCommentDesc;	
	
	@XStreamAlias("resourceList")
	private Resource resourceList;
	
	/**Result of validation*//*
	@XStreamOmitField
	private ResultsCode validationCode;	
	
	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}*/

	
	
	public String getReasonCommentDesc() {
		return reasonCommentDesc;
	}

	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public void setReasonCommentDesc(String reasonCommentDesc) {
		this.reasonCommentDesc = reasonCommentDesc;
	}

	public Resource getResourceList() {
		return resourceList;
	}

	public void setResourceList(Resource resourceList) {
		this.resourceList = resourceList;
	}

}
