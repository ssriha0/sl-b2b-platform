package com.newco.marketplace.vo.leadprofile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Errors;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "filter_set_competitive")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilterSetCompetitive {

	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("errors")
	private Errors errors;
	
	@XStreamAlias("filter_set_ID")
	private Integer filterSetID;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFilterSetID() {
		return filterSetID;
	}

	public void setFilterSetID(Integer filterSetID) {
		this.filterSetID = filterSetID;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}
	
	
	
	
}
