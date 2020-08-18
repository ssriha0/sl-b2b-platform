package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("SalesCheckItems")
public class SalesCheckItems implements Serializable {
	
	/** generated serialVersionUID */
	private static final long serialVersionUID = 6383378225726019576L;
	@XStreamImplicit(itemFieldName="Item")
	private List<SalesCheckItem> salesCheckItemList;

	public List<SalesCheckItem> getSalesCheckItemList() {
		return salesCheckItemList;
	}

	public void setSalesCheckItemList(List<SalesCheckItem> salesCheckItemList) {
		this.salesCheckItemList = salesCheckItemList;
	}

}
