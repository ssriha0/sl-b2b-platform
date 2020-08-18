package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing scope of work information.
 * @author Infosys
 *
 */
@XStreamAlias("scopeOfWork")
public class ScopeOfWork {
	
	@XStreamAlias("mainCategoryID")
	private String mainCategoryID;
	
	@XStreamAlias("tasks")
	private Tasks tasks;
	
	@XStreamAlias("skus")
	private Skus skus;

	public String getMainCategoryID() {
		return mainCategoryID;
	}

	public void setMainCategoryID(String mainCategoryID) {
		this.mainCategoryID = mainCategoryID;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public Skus getSkus() {
		return skus;
	}

	public void setSkus(Skus skus) {
		this.skus = skus;
	}

}
