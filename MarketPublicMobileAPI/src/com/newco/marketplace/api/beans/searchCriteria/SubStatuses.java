package com.newco.marketplace.api.beans.searchCriteria;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/07/03
 * the response for SOGetSearchCriteria
 *
 */
@XStreamAlias("subStatuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubStatuses {
	
	@XStreamImplicit(itemFieldName="subStatus")
	private List<SubStatus>subStatus;

	public List<SubStatus> getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(List<SubStatus> subStatus) {
		this.subStatus = subStatus;
	}

	
	
}
