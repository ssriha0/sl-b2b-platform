package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("substatusList")
public class SubStatusFilterList {
	
	@XStreamImplicit(itemFieldName="substatus")
	private List<SubStatusFilterVO> substatus;

	public List<SubStatusFilterVO> getSubstatus() {
		return substatus;
	}

	public void setSubstatus(List<SubStatusFilterVO> substatus) {
		this.substatus = substatus;
	}


	

}
