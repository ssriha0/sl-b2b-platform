package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Data")
public class LMSPingData {

	@XStreamAlias("SRC")
	private String source;
	
	@XStreamAlias("Zip")
	private String custZip;
	
	@XStreamAlias("Email")
	private String email;
	
	@XStreamAlias("Residential_Or_Commercial")
	private String residenceOrCommercial;
	
	@XStreamAlias("Skill")
	private String skill;
	
	@XStreamAlias("Project")
	private String project;
	
	@XStreamAlias("Urgency_Of_Service")
	private String urgencyOfService;
	
	@XStreamAlias("How_Many_Pros")
	private String howManyPros;

	
	public LMSPingData() {
		// TODO Auto-generated constructor stub
	}

	public LMSPingData(String source, String custZip,
			String residenceOrCommercial, String skill, String project,
			String urgencyOfService, String howManyPros) {
		this.source = source;
		this.custZip = custZip;
		this.residenceOrCommercial = residenceOrCommercial;
		this.skill = skill;
		this.project = project;
		this.urgencyOfService = urgencyOfService;
		this.howManyPros = howManyPros;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCustZip() {
		return custZip;
	}

	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResidenceOrCommercial() {
		return residenceOrCommercial;
	}

	public void setResidenceOrCommercial(String residenceOrCommercial) {
		this.residenceOrCommercial = residenceOrCommercial;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUrgencyOfService() {
		return urgencyOfService;
	}

	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}

	public String getHowManyPros() {
		return howManyPros;
	}

	public void setHowManyPros(String howManyPros) {
		this.howManyPros = howManyPros;
	}	

}
