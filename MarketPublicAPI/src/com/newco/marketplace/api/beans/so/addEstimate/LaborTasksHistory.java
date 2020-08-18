package com.newco.marketplace.api.beans.so.addEstimate;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("laborTasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class LaborTasksHistory {

	@XStreamImplicit(itemFieldName="laborTask")
	private List<LaborTaskHistory> laborTask;

	public List<LaborTaskHistory> getLabortask() {
		return laborTask;
	}

	public void setLabortask(List<LaborTaskHistory> laborTask) {
		this.laborTask = laborTask;
	}



}
