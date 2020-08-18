package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Discounts")
public class NPSDiscounts {

	@XStreamAlias("LaborAssociateDiscount")
	private String laborAssociateDiscount = "";

	@XStreamAlias("LaborPromotionDiscount")
	private String laborPromotionDiscount = "";

	@XStreamAlias("LaborCouponDiscount")
	private String laborCouponDiscount = "";

	@XStreamAlias("CouponNumber")
	private String couponNumber = "";

	@XStreamAlias("DiscountReasonCode1")
	private String discountReasonCode1 = "";

	@XStreamAlias("DiscountReasonCode2")
	private String discountReasonCode2 = "";

	public String getLaborAssociateDiscount() {
		return laborAssociateDiscount;
	}

	public void setLaborAssociateDiscount(String laborAssociateDiscount) {
		this.laborAssociateDiscount = laborAssociateDiscount;
	}

	public String getLaborPromotionDiscount() {
		return laborPromotionDiscount;
	}

	public void setLaborPromotionDiscount(String laborPromotionDiscount) {
		this.laborPromotionDiscount = laborPromotionDiscount;
	}

	public String getLaborCouponDiscount() {
		return laborCouponDiscount;
	}

	public void setLaborCouponDiscount(String laborCouponDiscount) {
		this.laborCouponDiscount = laborCouponDiscount;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public String getDiscountReasonCode1() {
		return discountReasonCode1;
	}

	public void setDiscountReasonCode1(String discountReasonCode1) {
		this.discountReasonCode1 = discountReasonCode1;
	}

	public String getDiscountReasonCode2() {
		return discountReasonCode2;
	}

	public void setDiscountReasonCode2(String discountReasonCode2) {
		this.discountReasonCode2 = discountReasonCode2;
	}

}
