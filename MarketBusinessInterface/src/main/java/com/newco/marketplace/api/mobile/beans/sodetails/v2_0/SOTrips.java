package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("tripDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOTrips {
	
	@XStreamImplicit(itemFieldName="trip")
	private List<SOTrip> trip;

	/**
	 * @return the trip
	 */
	public List<SOTrip> getTrip() {
		return trip;
	}

	/**
	 * @param trip the trip to set
	 */
	public void setTrip(List<SOTrip> trip) {
		this.trip = trip;
	}

}
