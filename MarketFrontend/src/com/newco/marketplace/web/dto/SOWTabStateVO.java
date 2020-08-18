package com.newco.marketplace.web.dto;

public class SOWTabStateVO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2072706824197100684L;
	private String tabIdentifier;
	private String tabState;
	private String tabErrorState;
	private String tabWarningState;
	private String tabCompleteState;
	private boolean tabShowIcon =true;
	
	public boolean isTabShowIcon() {
		return tabShowIcon;
	}
	public void setTabShowIcon(boolean tabShowIcon) {
		this.tabShowIcon = tabShowIcon;
	}
	public String getTabIdentifier() {
		return tabIdentifier;
	}
	public void setTabIdentifier(String tabIdentifier) {
		this.tabIdentifier = tabIdentifier;
	}
	public String getTabState() {
		return tabState;
	}
	public void setTabState(String tabState) {
		this.tabState = tabState;
	}
	public String getTabErrorState() {
		return tabErrorState;
	}
	public void setTabErrorState(String tabErrorState) {
		this.tabErrorState = tabErrorState;
	}
	public String getTabWarningState() {
		return tabWarningState;
	}
	public void setTabWarningState(String tabWarningState) {
		this.tabWarningState = tabWarningState;
	}
	public String getTabCompleteState() {
		return tabCompleteState;
	}
	public void setTabCompleteState(String tabCompleteState) {
		this.tabCompleteState = tabCompleteState;
	}
	

}
