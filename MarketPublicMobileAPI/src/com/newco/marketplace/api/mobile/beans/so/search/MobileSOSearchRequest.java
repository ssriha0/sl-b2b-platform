package com.newco.marketplace.api.mobile.beans.so.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
* for request for mobile SO Search
*
*/
@XSD(name="mobileSearchSORequest.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "mobileSearchSORequest")
@XStreamAlias("mobileSearchSORequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileSOSearchRequest{

/*	@XStreamAlias("sortOrder")
	private Integer sortOrder;
	@XStreamAlias("sortKey")
	private Integer sortKey;*/
	@XStreamAlias("pageNo")
	private Integer pageNo;
	@XStreamAlias("pageSize")
	private Integer pageSize;
	
	@XStreamAlias("searchCriteria")
	private MobileSOSearchCriteria searchCriteria;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	
	public MobileSOSearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(MobileSOSearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/*public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortKey() {
		return sortKey;
	}

	public void setSortKey(Integer sortKey) {
		this.sortKey = sortKey;
	}
*/
	


	


}
