package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("laborTasks")
public class LaborTasks {

	@XStreamImplicit(itemFieldName="labortask")
	private List<Labortask> labortask;

	public List<Labortask> getLabortask() {
		return labortask;
	}

	public void setLabortask(List<Labortask> labortask) {
		this.labortask = labortask;
	}

}
