package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("scheduleFilterList")
public class ScheduleStatusFilterList {
	
	@XStreamImplicit(itemFieldName="schedulestatus")
	private List<ScheduleStatusFilterVO> schedulestatus;

	public List<ScheduleStatusFilterVO> getSchedulestatus() {
		return schedulestatus;
	}

	public void setSchedulestatus(List<ScheduleStatusFilterVO> schedulestatus) {
		this.schedulestatus = schedulestatus;
	}

	
	

}
