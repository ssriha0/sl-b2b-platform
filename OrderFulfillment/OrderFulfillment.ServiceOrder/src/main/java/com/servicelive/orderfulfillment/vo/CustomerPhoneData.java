package com.servicelive.orderfulfillment.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("customerPhoneData")
public class CustomerPhoneData {

	@XStreamAlias("custPhoneNum")
	private String custPhoneNum;

	public String getCustPhoneNum() {
		return custPhoneNum;
	}

	public void setCustPhoneNum(String custPhoneNum) {
		this.custPhoneNum = custPhoneNum;
	}
	
	
}
