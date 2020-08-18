package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information on a list of customer references.
 * @author Infosys
 *
 */
@XStreamAlias("customReferences")
public class CustomReferences {

	@XStreamImplicit(itemFieldName="customRef")
	private  List<CustomReference> customRefList;

	public List<CustomReference> getCustomRefList() {
		return customRefList;
	}

	public void setCustomRefList(List<CustomReference> customRefList) {
		this.customRefList = customRefList;
	}

}
