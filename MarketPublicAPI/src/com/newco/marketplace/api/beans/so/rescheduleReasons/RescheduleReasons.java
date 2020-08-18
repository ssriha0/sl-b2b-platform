package com.newco.marketplace.api.beans.so.rescheduleReasons;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("rescheduleReasons")
public class RescheduleReasons {

	@XStreamImplicit(itemFieldName="rescheduleReason")
	private List<RescheduleReason> rescheduleReason;

	public List<RescheduleReason> getRescheduleReason() {
		return rescheduleReason;
	}

	public void setRescheduleReason(List<RescheduleReason> rescheduleReason) {
		this.rescheduleReason = rescheduleReason;
	}
}
