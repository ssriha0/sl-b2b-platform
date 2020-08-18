package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("product")
public class ProductDetailVO extends SerializableBaseVO{

	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("productStreet1")
	private String productStreet1;
	@XStreamAlias("productStreet2")
	private String productStreet2;
	@XStreamAlias("city")
	private String city;
	@XStreamAlias("state")
	private String state;
	@XStreamAlias("zip")
	private String zip;
	
	public String getProductStreet1() {
		return productStreet1;
	}
	public void setProductStreet1(String productStreet1) {
		this.productStreet1 = productStreet1;
	}
	public String getProductStreet2() {
		return productStreet2;
	}
	public void setProductStreet2(String productStreet2) {
		this.productStreet2 = productStreet2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
