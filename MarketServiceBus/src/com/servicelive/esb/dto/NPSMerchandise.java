package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Merchandise")
public class NPSMerchandise {

	@XStreamAlias("Brand")
	private String brand;

	@XStreamAlias("Model")
	private String model;

	@XStreamAlias("SerialNumber")
	private String slNo;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

}
