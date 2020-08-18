package com.servicelive.routingrulesengine.vo;

public class JobPriceVO {
	
	String jobCode;
	String price;
	
	public JobPriceVO() {
	
	}
	
	public JobPriceVO(String jobCode, String price) {
		super();
		this.jobCode = jobCode;
		this.price = price;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
