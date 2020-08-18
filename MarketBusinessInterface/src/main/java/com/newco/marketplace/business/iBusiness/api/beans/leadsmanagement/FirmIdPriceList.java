package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Match_Partner_List")
public class FirmIdPriceList {

	@XStreamImplicit(itemFieldName = "Partner")  /* @XStreamConverter(FirmIdPriceConvertor.class)*/
    private List<FirmIdPrice> firmId;

	public List<FirmIdPrice> getFirmId() {
		return firmId;
	}

	public void setFirmId(List<FirmIdPrice> firmId) {
		this.firmId = firmId;
	}

	

	

}
