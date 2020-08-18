package com.newco.marketplace.api.beans.so;

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
	private List<Task> taskList;

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}


}
