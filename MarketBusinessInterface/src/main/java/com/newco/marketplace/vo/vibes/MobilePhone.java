package com.newco.marketplace.vo.vibes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
//R16_1: SL-18979: New Request class to store request details for Add Participant API for Vibes

@XStreamAlias("mobile_phone")
public class MobilePhone {

	@XStreamAlias("mdn")
	private String mdn;

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	
	
}
