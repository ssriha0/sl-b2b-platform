package com.newco.marketplace.vo.ordermanagement.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("childBidSoList")
public class ChildBidOrderListType {
	@XStreamImplicit(itemFieldName="childBidOrder")
	private List<ChildBidOrder> childBidOrder;

	public List<ChildBidOrder> getChildBidOrder() {
		return childBidOrder;
	}

	public void setChildBidOrder(List<ChildBidOrder> childBidOrder) {
		this.childBidOrder = childBidOrder;
	}

	
	
	
}
