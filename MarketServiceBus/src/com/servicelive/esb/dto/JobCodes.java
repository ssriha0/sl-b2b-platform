package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("JobCodes")
public class JobCodes implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 2225641959899557218L;
	@XStreamImplicit(itemFieldName="JobCode")
	private List<JobCode> jobCodeList;

	public List<JobCode> getJobCodeList() {
		return jobCodeList;
	}

	public void setJobCodeList(List<JobCode> jobCodeList) {
		this.jobCodeList = jobCodeList;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( JobCode jc : jobCodeList )
		{
			sb.append( jc.toString() );
		}
		return sb.toString();
	}
	
}
