package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SalesCheck")
public class SalesCheck implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -1469455517340296357L;

	@XStreamAlias("Date")
	private String date;
	
	@XStreamAlias("Number")
	private String number;
	
	@XStreamAlias("SalesCheckItems")
	private SalesCheckItems salesCheckItems;

	@XStreamAlias("SellingAssociate")
	private String sellingAssociate;
	
	@XStreamAlias("Time")
	private String time;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

//	public List<SalesCheckItem> getSalesCheckItems() {
//		return salesCheckItems;
//	}
//
//	public void setSalesCheckItems(List<SalesCheckItem> salesCheckItems) {
//		this.salesCheckItems = salesCheckItems;
//	}

	public String getSellingAssociate() {
		return sellingAssociate;
	}

	public void setSellingAssociate(String sellingAssociate) {
		this.sellingAssociate = sellingAssociate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public SalesCheckItems getSalesCheckItems() {
		return salesCheckItems;
	}

	public void setSalesCheckItems(SalesCheckItems salesCheckItems) {
		this.salesCheckItems = salesCheckItems;
	}
	
}
