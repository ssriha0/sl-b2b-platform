package com.newco.marketplace.dto.vo.provider;



/**
 * This class would act as the VO class for search Firms API for standard service offerings
 *
 */
public class ServiceOfferingPriceVO {
	
	private Double price;
	private String zip;

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

}
