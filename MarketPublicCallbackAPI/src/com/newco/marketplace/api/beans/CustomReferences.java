package com.newco.marketplace.api.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("customReferences")
public class CustomReferences {

	@XStreamImplicit(itemFieldName = "customRef")
	private List<CustomReference> customRefList;

	public List<CustomReference> getCustomRefList() {
		return customRefList;
	}

	public void setCustomRefList(List<CustomReference> customRefList) {
		this.customRefList = customRefList;
	}

}