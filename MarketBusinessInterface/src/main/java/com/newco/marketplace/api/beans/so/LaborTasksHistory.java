package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("laborTasks")
public class LaborTasksHistory {

	@XStreamImplicit(itemFieldName="labortask")
	private List<LaborTaskHistory> labortask;

	public List<LaborTaskHistory> getLabortask() {
		return labortask;
	}

	public void setLabortask(List<LaborTaskHistory> labortask) {
		this.labortask = labortask;
	}



}
