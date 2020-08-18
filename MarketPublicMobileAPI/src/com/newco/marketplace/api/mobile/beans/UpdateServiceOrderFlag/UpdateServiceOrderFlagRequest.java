package com.newco.marketplace.api.mobile.beans.UpdateServiceOrderFlag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
* for request for mobile SO Search
*
*/
@XSD(name="updateFlagRequest.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "updateFlagRequest")
@XStreamAlias("updateFlagRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateServiceOrderFlagRequest{

	@XStreamAlias("followupFlag")
	private Boolean followupFlag;

	public Boolean getFollowupFlag() {
		return followupFlag;
	}

	public void setFollowupFlag(Boolean followupFlag) {
		this.followupFlag = followupFlag;
	}
}
