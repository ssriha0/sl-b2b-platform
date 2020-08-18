package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Part information.
 * @author Infosys
 *
 */

@XStreamAlias("part")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartTrack {
	
	@XStreamAlias("partId")
	private Integer partId; 

	@XStreamAlias("manufacturer")
	private String manufacturer;	
	
	@XStreamAlias("partName")
	private String partName;
	
	@XStreamAlias("modelNumber")
	private String modelNumber;
	
	@XStreamAlias("returnCarrier")
	private String returnCarrier;
	
	@XStreamAlias("returnTrackingNumber")
	private String returnTrackingNumber;
	
	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getReturnCarrier() {
		return returnCarrier;
	}

	public void setReturnCarrier(String returnCarrier) {
		this.returnCarrier = returnCarrier;
	}

	public String getReturnTrackingNumber() {
		return returnTrackingNumber;
	}

	public void setReturnTrackingNumber(String returnTrackingNumber) {
		this.returnTrackingNumber = returnTrackingNumber;
	}

}
