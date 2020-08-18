package com.newco.marketplace.api.beans.search.firms;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firms")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFirmsResponse {
	
	@XStreamAlias("firm")
	@XStreamImplicit(itemFieldName="firm")
	private List<Firm> firm;

	public List<Firm> getFirm() {
		return firm;
	}

	public void setFirm(List<Firm> firm) {
		this.firm = firm;
	}

}
