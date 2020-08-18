package com.newco.marketplace.api.mobile.beans.dashboard;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("tabs")
public class Tabs {
	
	@XStreamImplicit(itemFieldName ="tab")
	private List<Tab> tab;

	public List<Tab> getTab() {
		return tab;
	}

	public void setTab(List<Tab> tab) {
		this.tab = tab;
	}
}
