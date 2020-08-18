package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Merchandise information.
 * @author Infosys
 *
 */

@XStreamAlias("merchandise")
@XmlAccessorType(XmlAccessType.FIELD)
public class Merchandise {
	
	@XStreamAlias("partId")
	private Integer partId;
	
	@XStreamAlias("manufacturer")
	private String manufacturer;
	
	@XStreamAlias("modelNumber")
	private String modelNumber;
	
	@XStreamAlias("serialNumber")
	private String serialNumber;

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

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
