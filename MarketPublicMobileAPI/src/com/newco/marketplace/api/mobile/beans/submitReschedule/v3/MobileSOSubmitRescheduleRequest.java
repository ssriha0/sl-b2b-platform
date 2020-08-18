package com.newco.marketplace.api.mobile.beans.submitReschedule.v3;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SORescheduleService
 * @author sesh
 *
 */
@XSD(name = "mobileSOSubmitRescheduleRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "submitRescheduleRequest")
@XStreamAlias("submitRescheduleRequest")
public class MobileSOSubmitRescheduleRequest implements IAPIResponse {
	
	
	@XStreamAlias("soRescheduleInfo")
	private SORescheduleInfo soRescheduleInfo;

	public SORescheduleInfo getSoRescheduleInfo() {
		return soRescheduleInfo;
	}

	public void setSoRescheduleInfo(SORescheduleInfo soRescheduleInfo) {
		this.soRescheduleInfo = soRescheduleInfo;
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
