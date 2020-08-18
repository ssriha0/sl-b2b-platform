package com.newco.marketplace.api.beans.so.addEstimate;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("discount")
public class Discount {
	
	@XStreamAlias("discountType")
	private String discountType;
	
	@XStreamAlias("value")
	private Integer value;

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	

}
