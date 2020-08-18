package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("cancellationReasonCodes")
@XmlAccessorType(XmlAccessType.FIELD)
public class CancellationReasonCodes {
	
	@XStreamImplicit(itemFieldName="cancellationReasonCode")
	private List<CancellationReasonCode> cancellationReasonCode;

	public List<CancellationReasonCode> getCancellationReasonCode() {
		return cancellationReasonCode;
	}

	public void setCancellationReasonCode(
			List<CancellationReasonCode> cancellationReasonCode) {
		this.cancellationReasonCode = cancellationReasonCode;
	}

}
