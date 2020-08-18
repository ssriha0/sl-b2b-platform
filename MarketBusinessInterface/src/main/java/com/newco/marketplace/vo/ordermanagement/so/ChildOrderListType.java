package com.newco.marketplace.vo.ordermanagement.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("childSOList")
public class ChildOrderListType {
	@XStreamImplicit(itemFieldName="soDetail")
	private List<OMServiceOrder> childOrder;

	public List<OMServiceOrder> getChildOrder() {
		return childOrder;
	}

	public void setChildOrder(List<OMServiceOrder> childOrder) {
		this.childOrder = childOrder;
	}

	
}
