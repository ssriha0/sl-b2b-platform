package com.newco.marketplace.web.dto.ordermanagement;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

// Service Order List DropDown in print paperwork
public class ServiceOrderListDTO extends SerializedBaseDTO
{

	private static final long serialVersionUID = 1L;
	
	private int value;
	private String desc;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	
	
}
