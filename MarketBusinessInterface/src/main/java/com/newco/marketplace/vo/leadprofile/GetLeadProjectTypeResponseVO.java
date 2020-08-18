package com.newco.marketplace.vo.leadprofile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetLeadProjectTypeResponseVO {

	@XStreamAlias("categoryID")
	private Integer categoryID;
	
	@XStreamAlias("category")
	private String category;
	
	@XStreamAlias("projectId")
	private Integer projectId;
	
	@XStreamAlias("projectName")
	private String projectName;
	
	@XStreamAlias("projectDescription")
	private String projectDescription;
	
	@XStreamAlias("exclusivePrice")
	private Double exclusivePrice;
	
	@XStreamAlias("competitivePrice")
	private Double competitivePrice;
	
	@XmlTransient
	private Integer launchMarket;
	
	@XmlTransient
	private Integer mappingId;

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getExclusivePrice() {
		return exclusivePrice;
	}

	public void setExclusivePrice(Double exclusivePrice) {
		this.exclusivePrice = exclusivePrice;
	}

	public Double getCompetitivePrice() {
		return competitivePrice;
	}

	public void setCompetitivePrice(Double competitivePrice) {
		this.competitivePrice = competitivePrice;
	}

	public Integer getLaunchMarket() {
		return launchMarket;
	}

	public void setLaunchMarket(Integer launchMarket) {
		this.launchMarket = launchMarket;
	}

	public Integer getMappingId() {
		return mappingId;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
}
