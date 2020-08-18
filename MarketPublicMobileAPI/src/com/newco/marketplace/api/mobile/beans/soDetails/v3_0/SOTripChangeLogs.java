package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("changeLogs")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOTripChangeLogs {
	
	@XStreamImplicit(itemFieldName="changeLog")
	private List<SOTripChangeLog> changeLog;

	public List<SOTripChangeLog> getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(List<SOTripChangeLog> changeLog) {
		this.changeLog = changeLog;
	}
	
	
}
