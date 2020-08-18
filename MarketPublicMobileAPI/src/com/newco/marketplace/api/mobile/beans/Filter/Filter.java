package com.newco.marketplace.api.mobile.beans.Filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("filter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {

	@XStreamAlias("filterId")
	private String filterId;
	@XStreamAlias("filterName")
	private String filterName;
	@XStreamAlias("searchCriterias")
	private FilterSearchCriteria searchCriterias;
	
	public FilterSearchCriteria getSearchCriterias() {
		return searchCriterias;
	}

	public void setSearchCriterias(FilterSearchCriteria searchCriterias) {
		this.searchCriterias = searchCriterias;
	}

	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
