package com.newco.marketplace.api.mobile.beans.saveFilter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
* for request for mobile SO Search
*
*/
@XSD(name="saveFilterRequest.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "saveFilterRequest")
@XStreamAlias("saveFilterRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaveFilterRequest{

	@XStreamAlias("filterId")
	private Integer filterId;
	@XStreamAlias("filterName")
	private String filterName;
	@XStreamAlias("filterCriterias")
	private FilterCriterias filterCriterias;
	
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public FilterCriterias getFilterCriterias() {
		return filterCriterias;
	}
	public void setFilterCriterias(FilterCriterias filterCriterias) {
		this.filterCriterias = filterCriterias;
	}
	

	


}
