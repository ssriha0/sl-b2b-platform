package com.newco.marketplace.web.dto;

public class TabNavigationDTO extends SerializedBaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7681921019343444183L;
	private String comingFromTab; // Which tab are you coming from
	private String actionPerformed; // Can be SAVE AS DRAFT, PREVIOUS, NEXT, SAVE AND ROUTE
	private String sowMode; // CREATE or EDIT mode
	private String startingPoint; // from where

	public String getSowMode() {
		return sowMode;
	}
	public void setSowMode(String sowMode) {
		this.sowMode = sowMode;
	}
	public String getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(String startingPoint) {
		this.startingPoint = startingPoint;
	}
	public String getComingFromTab() {
		return comingFromTab;
	}
	public void setComingFromTab(String comingFromTab) {
		this.comingFromTab = comingFromTab;
	}
	public String getActionPerformed() {
		return actionPerformed;
	}
	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

}
