package com.newco.marketplace.gwt.providersearch.client;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SimpleProviderSearchSkillNodeVO implements IsSerializable, Serializable {


	int skillNodeId;
	int parentSkillNodeId;
	String skillNodeDesc;
	String decriptionContent;
	boolean isSelected = false;
	public int getSkillNodeId() {
		return skillNodeId;
	}
	public void setSkillNodeId(int skillNodeId) {
		this.skillNodeId = skillNodeId;
	}
	public int getParentSkillNodeId() {
		return parentSkillNodeId;
	}
	public void setParentSkillNodeId(int parentSkillNodeId) {
		this.parentSkillNodeId = parentSkillNodeId;
	}
	public String getSkillNodeDesc() {
		return skillNodeDesc;
	}
	public void setSkillNodeDesc(String skillNodeDesc) {
		this.skillNodeDesc = skillNodeDesc;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	/**
	 * @return the decriptionContent
	 */
	public String getDecriptionContent() {
		return decriptionContent;
	}
	/**
	 * @param decriptionContent the decriptionContent to set
	 */
	public void setDecriptionContent(String decriptionContent) {
		this.decriptionContent = decriptionContent;
	}

}
