package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firmDetailList")
public class LeadFirmDetailsList {
	
	@XStreamImplicit(itemFieldName="firmDetail")
	private List<LeadFirmDetails> firmDetail;

	public List<LeadFirmDetails> getFirmDetail() {
		return firmDetail;
	}

	public void setFirmDetail(List<LeadFirmDetails> firmDetail) {
		this.firmDetail = firmDetail;
	}

	/*
	public List<LeadFirmDetails> getFirmDetailList() {
		return firmDetailList;
	}

	public void setFirmDetailList(List<LeadFirmDetails> firmDetailList) {
		this.firmDetailList = firmDetailList;
	}
	*/
	
	
}
