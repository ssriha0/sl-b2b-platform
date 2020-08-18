package com.newco.marketplace.api.mobile.beans.so.search.advance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
* for request for mobile SO Search
*
*/
@XSD(name="mobileSOAdvanceSearchRequest.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "mobileSOAdvanceSearchRequest")
@XStreamAlias("mobileSOAdvanceSearchRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileSOAdvanceSearchRequest{

/*	@XStreamAlias("sortOrder")
	private Integer sortOrder;
	@XStreamAlias("sortKey")
	private Integer sortKey;*/
	@XStreamAlias("pageNo")
	private Integer pageNo;
	@XStreamAlias("pageSize")
	private Integer pageSize;
	
	@XStreamAlias("advanceSearchCriteria")
	private MobileSOAdvanceSearchCriteria advanceSearchCriteria;

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

	public MobileSOAdvanceSearchCriteria getAdvanceSearchCriteria() {
		return advanceSearchCriteria;
	}

	public void setAdvanceSearchCriteria(
			MobileSOAdvanceSearchCriteria advanceSearchCriteria) {
		this.advanceSearchCriteria = advanceSearchCriteria;
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
