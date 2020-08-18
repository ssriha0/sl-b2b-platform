package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("soDetails")
public class ServiceOrderList {
	
	@XStreamImplicit(itemFieldName="soDetail")
	private List<OMServiceOrder> soDetail;

	public List<OMServiceOrder> getSoDetail() {
		return soDetail;
	}

	public void setSoDetail(List<OMServiceOrder> soDetail) {
		this.soDetail = soDetail;
	}


}
