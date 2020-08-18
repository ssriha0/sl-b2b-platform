package com.newco.marketplace.web.dto;



public class PromoDto extends SerializedBaseDTO {

	public static final String PROMO_DTO = "PromoDto";

	/**
	 * Wee need to bake this out better. adding promo type and all
	 */
	boolean promoActive = false;
	String promoContent = "";
	public boolean isPromoActive() {
		return promoActive;
	}
	public void setPromoActive(boolean promoActive) {
		this.promoActive = promoActive;
	}
	public String getPromoContent() {
		return promoContent;
	}
	public void setPromoContent(String promoContent) {
		this.promoContent = promoContent;
	}
	
	
	
}
