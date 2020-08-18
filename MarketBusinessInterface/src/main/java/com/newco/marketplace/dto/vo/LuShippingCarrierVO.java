package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class LuShippingCarrierVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4520352262637080281L;
	private int shippingCarrierId;
	private String descr;
	public int getShippingCarrierId() {
		return shippingCarrierId;
	}
	public void setShippingCarrierId(int shippingCarrierId) {
		this.shippingCarrierId = shippingCarrierId;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	
}
