package com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the warranty home reason codes
 * @author Suresh
 *
 */
@XSD(name = "submitWarrantyHomeReasonCodeRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "submitWarrantyHomeReasonCodeRequest")
@XStreamAlias("submitWarrantyHomeReasonCodeRequest")
public class MobileSOSubmitWarrantyHomeReasonRequest implements IAPIResponse {
	
	
	@XStreamAlias("warrantyReasonCodeInfo")
	private WarrantyHomeReasonInfo warrantyReasonCodeInfo;

	

	public WarrantyHomeReasonInfo getWarrantyReasonCodeInfo() {
		return warrantyReasonCodeInfo;
	}

	public void setWarrantyReasonCodeInfo(
			WarrantyHomeReasonInfo warrantyReasonCodeInfo) {
		this.warrantyReasonCodeInfo = warrantyReasonCodeInfo;
	}

	public void setResults(Results results) {
		// TODO Auto-generated method stub
		
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}
}
