package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * This is a generic bean class for storing bid list information.
 * @author Infosys
 *
 */

@XStreamAlias("otherBidsFromMyCompany")
@XmlAccessorType(XmlAccessType.FIELD)
public class BidList {

	@XStreamImplicit(itemFieldName="bid")
	private List<Bid> bid;

	public List<Bid> getBid() {
		return bid;
	}

	public void setBid(List<Bid> bid) {
		this.bid = bid;
	}

	
	
}
