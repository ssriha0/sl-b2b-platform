package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("markets")
public class MarketFilterVO {
	private Integer id;
	private String code;
	private String descr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

}
