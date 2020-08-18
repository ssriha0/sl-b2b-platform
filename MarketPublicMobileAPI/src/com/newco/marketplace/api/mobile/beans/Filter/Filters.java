package com.newco.marketplace.api.mobile.beans.Filter;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XStreamAlias("filters")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filters {

	@XStreamImplicit(itemFieldName="filter")
	private List<Filter> filter;

	public List<Filter> getFilter() {
		return filter;
	}

	public void setFilter(List<Filter> filter) {
		this.filter = filter;
	}

}
