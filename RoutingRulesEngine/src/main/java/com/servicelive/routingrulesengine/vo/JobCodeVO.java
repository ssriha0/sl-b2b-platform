package com.servicelive.routingrulesengine.vo;

public class JobCodeVO {
	
	String jobCode;
	Double price;
	
	public JobCodeVO(String jobCode, Double price) {
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
