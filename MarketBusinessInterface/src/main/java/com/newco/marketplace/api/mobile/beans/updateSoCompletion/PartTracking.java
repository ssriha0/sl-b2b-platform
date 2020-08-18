package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part tracking information.
 * @author Infosys
 *
 */
@XStreamAlias("part")
public class PartTracking {
	
	@XStreamAlias("partId")
	private Integer partId;
	
	@XStreamAlias("partName")
	private String partName;
	
	@XStreamAlias("carrier")
	private String carrier;
	
	@XStreamAlias("trackingNumber")
	private String trackingNumber;

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}


	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	
	
	
}
