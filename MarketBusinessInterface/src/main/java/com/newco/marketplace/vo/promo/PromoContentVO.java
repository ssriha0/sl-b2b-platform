package com.newco.marketplace.vo.promo;

import com.sears.os.vo.SerializableBaseVO;

public class PromoContentVO extends SerializableBaseVO{

	private int promoID;
	private String contentLocation;
	private String content;
	public int getPromoID() {
		return promoID;
	}
	public void setPromoID(int promoID) {
		this.promoID = promoID;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentLocation() {
		return contentLocation;
	}
	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}
	
}
