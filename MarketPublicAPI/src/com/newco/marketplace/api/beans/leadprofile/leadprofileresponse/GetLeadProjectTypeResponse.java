package com.newco.marketplace.api.beans.leadprofile.leadprofileresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "getLeadProjectTypeResponse")
@XStreamAlias("getLeadProjectTypeResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetLeadProjectTypeResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XmlElement(name="projects")
	private ProjectDetailsList projectDetailsList;

	public ProjectDetailsList getProjectDetailsList() {
		return projectDetailsList;
	}

	public void setProjectDetailsList(ProjectDetailsList projectDetailsList) {
		this.projectDetailsList = projectDetailsList;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	
}
