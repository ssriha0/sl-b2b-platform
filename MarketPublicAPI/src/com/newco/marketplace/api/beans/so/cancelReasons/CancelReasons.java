package com.newco.marketplace.api.beans.so.cancelReasons;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("cancelReasons")
public class CancelReasons {

	@XStreamImplicit(itemFieldName="cancelReason")
	private List<CancelReason> cancelReason;

	public List<CancelReason> getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(List<CancelReason> cancelReason) {
		this.cancelReason = cancelReason;
	}	
}
