package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("businessPhone")
public class BusinessPhone {

	@XStreamAlias("number")
	private String number;

	@XStreamAlias("extension")
	private String extension;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
