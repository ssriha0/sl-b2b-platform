package com.newco.marketplace.web.dto;

import java.util.List;
import java.util.Map;

public class JobCodeMappingDTO extends SerializedBaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9091586652173032265L;
	private String jobCode;
	private String specCode;
	private Integer mainCategoryId;
	private String majorHeadingDescr;
	private String subHeadingDescr;
	private String inclusionDescr;
	private Integer templateId;
	private SkuTaskDTO newSkuTask;
	private List<SkuTaskDTO> skuTaskList;
	private Map<Integer,String> templateList;
	private String taskName;
	private String taskComment;
	/**
	 * @return the inclusionDescr
	 */
	public String getInclusionDescr() {
		return inclusionDescr;
	}
	/**
	 * @param inclusionDescr the inclusionDescr to set
	 */
	public void setInclusionDescr(String inclusionDescr) {
		this.inclusionDescr = inclusionDescr;
	}
	/**
	 * @return the jobCode
	 */
	public String getJobCode() {
		return jobCode;
	}
	/**
	 * @param jobCode the jobCode to set
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	/**
	 * @return the mainCategoryId
	 */
	public Integer getMainCategoryId() {
		return mainCategoryId;
	}
	/**
	 * @param mainCategoryId the mainCategoryId to set
	 */
	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	/**
	 * @return the majorHeadingDescr
	 */
	public String getMajorHeadingDescr() {
		return majorHeadingDescr;
	}
	/**
	 * @param majorHeadingDescr the majorHeadingDescr to set
	 */
	public void setMajorHeadingDescr(String majorHeadingDescr) {
		this.majorHeadingDescr = majorHeadingDescr;
	}
	
	/**
	 * @return the newSkuTask
	 */
	public SkuTaskDTO getNewSkuTask() {
		return newSkuTask;
	}
	/**
	 * @param newSkuTask the newSkuTask to set
	 */
	public void setNewSkuTask(SkuTaskDTO newSkuTask) {
		this.newSkuTask = newSkuTask;
	}
	/**
	 * @return the skuTaskList
	 */
	public List<SkuTaskDTO> getSkuTaskList() {
		return skuTaskList;
	}
	/**
	 * @param skuTaskList the skuTaskList to set
	 */
	public void setSkuTaskList(List<SkuTaskDTO> skuTaskList) {
		this.skuTaskList = skuTaskList;
	}
	/**
	 * @return the subHeadingDescr
	 */
	public String getSubHeadingDescr() {
		return subHeadingDescr;
	}
	/**
	 * @param subHeadingDescr the subHeadingDescr to set
	 */
	public void setSubHeadingDescr(String subHeadingDescr) {
		this.subHeadingDescr = subHeadingDescr;
	}
	/**
	 * @return the templateId
	 */
	public Integer getTemplateId() {
		return templateId;
	}
	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	/**
	 * @return the specCode
	 */
	public String getSpecCode() {
		return specCode;
	}
	/**
	 * @param specCode the specCode to set
	 */
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	/**
	 * @return the taskComment
	 */
	public String getTaskComment() {
		return taskComment;
	}
	/**
	 * @param taskComment the taskComment to set
	 */
	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the templateList
	 */
	public Map<Integer, String> getTemplateList() {
		return templateList;
	}
	/**
	 * @param templateList the templateList to set
	 */
	public void setTemplateList(Map<Integer, String> templateList) {
		this.templateList = templateList;
	}
	
	
}
