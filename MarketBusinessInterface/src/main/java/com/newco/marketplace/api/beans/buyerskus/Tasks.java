package com.newco.marketplace.api.beans.buyerskus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tasks {
	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("category")
	private String category;
	
	@XStreamAlias("categoryId")
	private String categoryId;
	
	@XStreamAlias("subCategory")
	private String subCategory;
	
	@XStreamAlias("subCategoryId")
	private String subCategoryId;
	
	@XStreamAlias("skill")
	private String skill;
	
	
	@XStreamAlias("comments")
	private String comments;


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String getSubCategory() {
		return subCategory;
	}


	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}


	public String getSubCategoryId() {
		return subCategoryId;
	}


	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}


	public String getSkill() {
		return skill;
	}


	public void setSkill(String skill) {
		this.skill = skill;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
}
