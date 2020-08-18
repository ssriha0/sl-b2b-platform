package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("tabCount")
public class Tab {

	@XStreamAlias("tabName")
	private String tabName;

	@XStreamAlias("soCount")
	private Integer soCount;


	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Integer getSoCount() {
		return soCount;
	}

	public void setSoCount(Integer soCount) {
		this.soCount = soCount;
	}	
}
