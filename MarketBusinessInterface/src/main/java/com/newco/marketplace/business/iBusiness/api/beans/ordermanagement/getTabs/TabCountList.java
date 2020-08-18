package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getTabs;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("tabCountLists")
public class TabCountList {
	@XStreamImplicit(itemFieldName="tabCount")
	private List<Tab> tabCountList;

	public List<Tab> getTabCountList() {
		return tabCountList;
	}

	public void setTabCountList(List<Tab> tabCountList) {
		this.tabCountList = tabCountList;
	}
	
}
