package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmReviews")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmReviews {
    
	@XStreamImplicit(itemFieldName = "FirmReview")
	@XmlElement(name="FirmReview")
	private List<FirmReview> firmReview;
	
	public List<FirmReview> getFirmReview() {
		return firmReview;
	}

	public void setFirmReview(List<FirmReview> firmReview) {
		this.firmReview = firmReview;
	}

	

}
