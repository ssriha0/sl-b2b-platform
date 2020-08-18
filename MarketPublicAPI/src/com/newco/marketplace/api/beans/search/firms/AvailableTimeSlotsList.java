package com.newco.marketplace.api.beans.search.firms;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("availableTimeSlotsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailableTimeSlotsList {

	
	@XStreamAlias("availableTimeSlot")
	@XStreamImplicit(itemFieldName="availableTimeSlot")
	private List<AvailableTimeSlot> availableTimeSlot;

	public List<AvailableTimeSlot> getAvailableTimeSlot() {
		return availableTimeSlot;
	}

	public void setAvailableTimeSlot(List<AvailableTimeSlot> availableTimeSlot) {
		this.availableTimeSlot = availableTimeSlot;
	}
	
}
