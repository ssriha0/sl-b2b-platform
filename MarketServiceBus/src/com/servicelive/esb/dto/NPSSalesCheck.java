package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SalesCheck")
public class NPSSalesCheck {
	
	@XStreamAlias("Number")
	private String number;
	
	@XStreamAlias("Date")
	private String date;

	public NPSSalesCheck() {
		// default constructor
	}

	public NPSSalesCheck(String number, String date) {
		this.number = number;
		this.date = date;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
