package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Firms")
public class FullFirmDetails {
	
@XStreamImplicit(itemFieldName ="FirmDetails")
private List<FirmCompleteDetails> firmCompleteDetails;

public List<FirmCompleteDetails> getFirmCompleteDetails() {
	return firmCompleteDetails;
}

public void setFirmCompleteDetails(List<FirmCompleteDetails> firmCompleteDetails) {
	this.firmCompleteDetails = firmCompleteDetails;
}




}
