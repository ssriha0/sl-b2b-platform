package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("tab")
public class Tab {

	@XStreamAlias("tabName")
	private String tabName;

	@XStreamAlias("tabCount")
	private Integer tabCount;

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Integer getTabCount() {
		return tabCount;
	}

	public void setTabCount(Integer tabCount) {
		this.tabCount = tabCount;
	}

}
