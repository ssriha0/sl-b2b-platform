package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XmlRootElement(name = "Historys")
@XStreamAlias("Historys")
@XmlAccessorType(XmlAccessType.FIELD)
public class Historys {

	@XmlElement(name="History")
	@XStreamImplicit(itemFieldName = "History")
	private List<History> historyList;

	public List<History> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<History> historyList) {
		this.historyList = historyList;
	}
	


}
