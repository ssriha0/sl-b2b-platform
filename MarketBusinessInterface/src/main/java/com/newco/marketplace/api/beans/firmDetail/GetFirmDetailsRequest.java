package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This class would act as request for the getFirmDetails service
 * @author neenu_manoharan
 *
 */
@XSD(name = "getFirmDetailsRequest.xsd", path = "/resources/schemas/search/")
@XmlRootElement(name = "getFirmDetailsRequest")
@XStreamAlias("getFirmDetailsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFirmDetailsRequest {

	@XStreamAlias("firmIds")
	private FirmIds firmIds;

	public FirmIds getFirmIds() {
		return firmIds;
	}

	public void setFirmIds(FirmIds firmIds) {
		this.firmIds = firmIds;
	}
	
}
