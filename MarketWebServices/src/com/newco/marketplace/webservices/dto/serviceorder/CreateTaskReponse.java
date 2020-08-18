package com.newco.marketplace.webservices.dto.serviceorder;

import java.io.Serializable;

public class CreateTaskReponse implements Serializable{


	private static final long serialVersionUID = 2905611147127723395L;

	private String jobCode;
	private Double laborPrice;
	
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public Double getLaborPrice() {
		return laborPrice;
	}
	public void setLaborPrice(Double laborPrice) {
		this.laborPrice = laborPrice;
	}
	
	
}
