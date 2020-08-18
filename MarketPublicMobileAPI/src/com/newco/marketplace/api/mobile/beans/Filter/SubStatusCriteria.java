package com.newco.marketplace.api.mobile.beans.Filter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("orderSubstatuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubStatusCriteria {

	@XStreamImplicit(itemFieldName="orderSubstatus")
	private List<SubStatusValue> subStatus;

	public List<SubStatusValue> getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(List<SubStatusValue> subStatus) {
		this.subStatus = subStatus;
	}
}
