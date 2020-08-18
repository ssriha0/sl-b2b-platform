package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("statusList")
public class StatusFilterList {
	
	@XStreamImplicit(itemFieldName="status")
	private List<StatusFilterVO> status;
	
	public List<StatusFilterVO> getStatus() {
		return status;
	}

	public void setStatus(List<StatusFilterVO> status) {
		this.status = status;
	}

	

}
