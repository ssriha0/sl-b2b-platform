package com.newco.marketplace.web.dto.SkuCategoryTask;



public class TaskDTO extends BaseDTO {

	private static final long serialVersionUID = -2822787343504190712L;
	

	private String mainServiceCatName;
	private String taskCatName;
	private String taskSubCatName;
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
	public String getMainServiceCatName() {
		return mainServiceCatName;
	}
	public void setMainServiceCatName(String mainServiceCatName) {
		this.mainServiceCatName = mainServiceCatName;
	}
	
}
