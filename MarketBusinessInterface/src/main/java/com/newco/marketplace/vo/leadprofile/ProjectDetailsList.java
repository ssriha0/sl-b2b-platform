package com.newco.marketplace.vo.leadprofile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("projects")
@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDetailsList {
	
	@XmlElement(name="project")
	private List<GetLeadProjectTypeResponseVO> project;

	public List<GetLeadProjectTypeResponseVO> getProject() {
		return project;
	}

	public void setProject(List<GetLeadProjectTypeResponseVO> project) {
		this.project = project;
	}
	
	

}
