package com.newco.marketplace.dto.vo.dateTimeSlots;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceDatetimeSlots")
public class ServiceDateTimeSlots {

	@XStreamImplicit(itemFieldName="serviceDatetimeSlot")
	private List<ServiceDatetimeSlot> serviceDatetimeSlot;

	public List<ServiceDatetimeSlot> getServiceDatetimeSlot() {
		return serviceDatetimeSlot;
	}

	public void setServiceDatetimeSlot(List<ServiceDatetimeSlot> serviceDatetimeSlot) {
		this.serviceDatetimeSlot = serviceDatetimeSlot;
	}

}
