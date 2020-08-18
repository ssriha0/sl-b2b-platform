package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerSOTemplateVO extends SerializableBaseVO {

	private Integer buyerID;
	private Integer templateID;
	private String templateName;
	private Integer mainServiceCategory;
	private String templateData;
	private Boolean newSpn;
	private Integer autoAccept;
	private Integer autoAcceptDays;
	private Integer autoAcceptTimes;	
	
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public Integer getTemplateID() {
		return templateID;
	}
	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getMainServiceCategory() {
		return mainServiceCategory;
	}
	public void setMainServiceCategory(Integer mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}
	public String getTemplateData() {
		return templateData;
	}
	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}
	public Boolean getNewSpn() {
		return newSpn;
	}
	public void setNewSpn(Boolean newSpn) {
		this.newSpn = newSpn;
	}
	public Integer getAutoAccept() {
		return autoAccept;
	}
	public void setAutoAccept(Integer autoAccept) {
		this.autoAccept = autoAccept;
	}
	public Integer getAutoAcceptDays() {
		return autoAcceptDays;
	}
	public void setAutoAcceptDays(Integer autoAcceptDays) {
		this.autoAcceptDays = autoAcceptDays;
	}
	public Integer getAutoAcceptTimes() {
		return autoAcceptTimes;
	}
	public void setAutoAcceptTimes(Integer autoAcceptTimes) {
		this.autoAcceptTimes = autoAcceptTimes;
	}
	
}