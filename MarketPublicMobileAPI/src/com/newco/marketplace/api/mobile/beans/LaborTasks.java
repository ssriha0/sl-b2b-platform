package com.newco.marketplace.api.mobile.beans;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("laborTasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class LaborTasks {

	@XStreamImplicit(itemFieldName="laborTask")
	private List<LaborTask> laborTask;

	public List<LaborTask> getLaborTask() {
		return laborTask;
	}

	public void setLaborTask(List<LaborTask> laborTask) {
		this.laborTask = laborTask;
	}

	
}
