package com.newco.marketplace.api.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("jobCodes")
public class JobCodes {
	@XStreamImplicit(itemFieldName = "jobCode")
	private List<JobCode> jobCode;

	public List<JobCode> getJobCode() {
		return jobCode;
	}

	public void setJobCode(List<JobCode> jobCode) {
		this.jobCode = jobCode;
	}

}
