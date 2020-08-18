package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing dimension information.
 * @author Infosys
 *
 */
@XStreamAlias("dimensions")
public class Dimensions {
	
	@XStreamAlias("measurementType")
	private String measurementType;
	
	@XStreamAlias("height")
	private String height;
	
	@XStreamAlias("width")
	private String width;
	
	@XStreamAlias("length")
	private String length;
	
	@XStreamAlias("weight")
	private String weight;

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

}
