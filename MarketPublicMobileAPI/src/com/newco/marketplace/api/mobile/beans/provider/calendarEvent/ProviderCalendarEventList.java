package com.newco.marketplace.api.mobile.beans.provider.calendarEvent;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderCalendarEventList {

	
	@XmlElement(name="providerCalendarEventDetails")
	private List<ProviderCalendarEventDetails> providerCalendarEventDetails;

	public List<ProviderCalendarEventDetails> getProviderCalendarEventDetails() {
		return providerCalendarEventDetails;
	}

	public void setProviderCalendarEventDetails(
			List<ProviderCalendarEventDetails> providerCalendarEventDetails) {
		this.providerCalendarEventDetails = providerCalendarEventDetails;
	}
	
	
}


