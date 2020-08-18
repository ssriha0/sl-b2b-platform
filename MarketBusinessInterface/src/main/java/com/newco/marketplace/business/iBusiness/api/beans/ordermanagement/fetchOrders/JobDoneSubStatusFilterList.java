package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("jobDoneSubStatusFilterList")
public class JobDoneSubStatusFilterList {
	
	@XStreamImplicit(itemFieldName="jobDonesubstatus")
	private List<JobDoneSubStatusFilterVO> jobDonesubstatus;

	public List<JobDoneSubStatusFilterVO> getJobDonesubstatus() {
		return jobDonesubstatus;
	}

	public void setJobDonesubstatus(List<JobDoneSubStatusFilterVO> jobDonesubstatus) {
		this.jobDonesubstatus = jobDonesubstatus;
	}

	

	

	

}
