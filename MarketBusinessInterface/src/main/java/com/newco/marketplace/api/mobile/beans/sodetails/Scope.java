package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing scope information.
 * @author Infosys
 *
 */

@XStreamAlias("scope")
@XmlAccessorType(XmlAccessType.FIELD)
public class Scope {
	
	@XStreamAlias("mainServiceCategory")
	private String mainServiceCategory;
	
	@XStreamAlias("tasks")
	private Tasks tasks;

	public String getMainServiceCategory() {
		return mainServiceCategory;
	}

	public void setMainServiceCategory(String mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}
		
}
