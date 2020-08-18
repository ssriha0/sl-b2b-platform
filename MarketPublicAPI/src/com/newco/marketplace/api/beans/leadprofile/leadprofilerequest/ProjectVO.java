package com.newco.marketplace.api.beans.leadprofile.leadprofilerequest;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("project")
public class ProjectVO {
	
	@XStreamAlias("categoryID")
	private Integer categoryID;
	
	@XStreamAlias("projectId")
	private Integer projectId;
	
	@XStreamAlias("exclusivePriceSelected")
	private String exclusivePriceSelected;
	
	@XStreamAlias("compPriceSelected")
	private String compPriceSelected;
	
	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getExclusivePriceSelected() {
		return exclusivePriceSelected;
	}

	public void setExclusivePriceSelected(String exclusivePriceSelected) {
		this.exclusivePriceSelected = exclusivePriceSelected;
	}

	public String getCompPriceSelected() {
		return compPriceSelected;
	}

	public void setCompPriceSelected(String compPriceSelected) {
		this.compPriceSelected = compPriceSelected;
	}



}
