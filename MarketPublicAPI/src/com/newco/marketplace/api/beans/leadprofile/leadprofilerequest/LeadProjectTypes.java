package com.newco.marketplace.api.beans.leadprofile.leadprofilerequest;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("projectTypes")
public class LeadProjectTypes {


	@XStreamImplicit(itemFieldName = "projectType")
	private List<String> projectType;

	public List<String> getProjectType() {
		return projectType;
	}

	public void setProjectType(List<String> projectType) {
		this.projectType = projectType;
	}

	


}
