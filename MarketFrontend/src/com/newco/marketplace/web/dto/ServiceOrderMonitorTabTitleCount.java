package com.newco.marketplace.web.dto;


public class ServiceOrderMonitorTabTitleCount extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4120318435350519982L;
	private String tabTitle;
	private Integer tabCount = 0;
	
	public ServiceOrderMonitorTabTitleCount() {
	}

	public Integer getTabCount() {
		return tabCount;
	}
	public void setTabCount(Integer tabCount) {
		this.tabCount += tabCount;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tabTitle + "\t" + tabCount;
	}
	//protected void

}
