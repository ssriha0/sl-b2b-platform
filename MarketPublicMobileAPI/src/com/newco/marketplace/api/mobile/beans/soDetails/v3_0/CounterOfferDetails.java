package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing Reschedule information.
 * @author Infosys
 *
 */

@XStreamAlias("counterOfferDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class CounterOfferDetails {

	@XStreamAlias("counteredResources")
	private Integer counteredResources;
	
	@XStreamImplicit(itemFieldName="counteredResourceList")
	private List<CounterOfferDetail> counteredResourceList;

	public Integer getCounteredResources() {
		return counteredResources;
	}

	public void setCounteredResources(Integer counteredResources) {
		this.counteredResources = counteredResources;
	}

	public List<CounterOfferDetail> getCounteredResourceList() {
		return counteredResourceList;
	}

	public void setCounteredResourceList(
			List<CounterOfferDetail> counteredResourceList) {
		this.counteredResourceList = counteredResourceList;
	}
	
	
}
