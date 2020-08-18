package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of PermitTasks.
 * @author Infosys
 *
 */

@XStreamAlias("permitTasks")
public class PermitTasks {
	

	@XStreamImplicit(itemFieldName="permitTask")
	private List<PermitTask> permitTask;

	public List<PermitTask> getPermitTask() {
		return permitTask;
	}

	public void setPermitTask(List<PermitTask> permitTask) {
		this.permitTask = permitTask;
	}

}
