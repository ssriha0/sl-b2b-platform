package com.newco.marketplace.dto.vo;
/* VO class to store Main category ,category and sub category name if available  for sku id 
*  
* @author Infosys 
*/
public class TaskForSkuVO {
	private String mainServiceCatName;
	private String taskCatName;
	private String taskSubCatName;
	private Integer skillId = -1;
	private Integer categoryId = -1;
	private Integer subCategoryId = -1;
	private Integer mainCategoryId = -1;
	
	public Integer getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	public Integer getSkillId() {
		return skillId;
	}
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getMainServiceCatName() {
		return mainServiceCatName;
	}
	public void setMainServiceCatName(String mainServiceCatName) {
		this.mainServiceCatName = mainServiceCatName;
	}
	public String getTaskCatName() {
		return taskCatName;
	}
	public void setTaskCatName(String taskCatName) {
		this.taskCatName = taskCatName;
	}
	public String getTaskSubCatName() {
		return taskSubCatName;
	}
	public void setTaskSubCatName(String taskSubCatName) {
		this.taskSubCatName = taskSubCatName;
	}

}
