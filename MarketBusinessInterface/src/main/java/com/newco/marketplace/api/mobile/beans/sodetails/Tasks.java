package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of tasks.
 * @author Infosys
 *
 */

@XStreamAlias("tasks")
public class Tasks {
	

	@XStreamImplicit(itemFieldName="task")
	private List<Task> task;

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}


}
