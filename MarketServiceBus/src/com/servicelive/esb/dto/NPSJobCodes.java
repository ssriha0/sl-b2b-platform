package com.servicelive.esb.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("JobCodes")
public class NPSJobCodes {

	@XStreamImplicit(itemFieldName="JobCode")
	private List<NPSJobCode> jobCodeList;

	public List<NPSJobCode> getJobCodeList() {
		return jobCodeList;
	}

	public void setJobCodeList(List<NPSJobCode> jobCodeList) {
		this.jobCodeList = jobCodeList;
	}

}
