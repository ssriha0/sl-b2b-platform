package com.newco.marketplace.api.beans.leadprofile.leadprofilerequest;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("projects")
public class Projects {
	
	@XStreamImplicit(itemFieldName="project")
	private List<ProjectVO> project;

	public List<ProjectVO> getProject() {
		return project;
	}

	public void setProject(List<ProjectVO> project) {
		this.project = project;
	}


}
