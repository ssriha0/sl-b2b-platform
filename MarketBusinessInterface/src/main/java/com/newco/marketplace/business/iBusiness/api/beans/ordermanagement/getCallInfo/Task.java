package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;



/*
 * Maintenance History: See bottom of file
 */
@XStreamAlias("scope")
public class Task {

	/**
	 * 
	 */
	private Integer taskId;
	private String soId;
	private Integer skillNodeId;
	private Integer serviceTypeId;
	private Double finalPrice;
	private Date createDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String taskName;
	private String taskComments;
	private Integer partsSuppliedId;
	@XStreamAlias("serviceType") 
	private String serviceType;

	private String categoryName;
	private String subCategoryName;

	private Integer level; // Needed when doing mapping. Is the skillNodeId a
							// category or a subcategory?
	private Integer parentId;
	private Integer sortOrder;
	private Double price;
	private Integer autoInjectedInd;
	@XStreamAlias("primaryTask")
	private boolean primaryTask;
	
	//added for SL-8937
	private Double sellingPrice;
	private Double retailPrice;
	private Integer taskType;
	private Integer sequenceNumber;
	private String permitTypeDesc;
	private String sku;
	@XStreamAlias("priceChangedInd")
	private boolean priceChangedInd;
	
	private Integer taskSeqNum;
	private String taskStatus;
	
	//added for SL-18071
	private Integer skuId;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getTaskSeqNum() {
		return taskSeqNum;
	}

	public void setTaskSeqNum(Integer taskSeqNum) {
		this.taskSeqNum = taskSeqNum;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}




	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public boolean isPrimaryTask() {
		return primaryTask;
	}

	public void setPrimaryTask(boolean primaryTask) {
		this.primaryTask = primaryTask;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType == null ? "" : serviceType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Integer getSkillNodeId() {
		return skillNodeId;
	}

	public void setSkillNodeId(Integer skillNodeId) {
		this.skillNodeId = skillNodeId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? "" : taskName;
	}

	public String getTaskComments() {
		return taskComments;
	}

	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments == null ? "" : taskComments;
	}

	public Integer getPartsSuppliedId() {
		return partsSuppliedId;
	}

	public void setPartsSuppliedId(Integer partsSuppliedId) {
		this.partsSuppliedId = partsSuppliedId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param subCategoryName
	 *            the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	//use two toStrings to compare
	public int compareTo(Task o) {
		if (!o.getCompareString().equals(this.getCompareString())) {
			return 1;
		}
		return 0;
	}
	
	public String getCompareString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getPartsSuppliedId() != null) ? Integer.toString(getPartsSuppliedId().intValue()) : "");
		sb.append((getServiceTypeId() != null) ? Integer.toString(getServiceTypeId()) : "");
		sb.append((getSkillNodeId() != null) ? Integer.toString(getSkillNodeId()) : "");
		sb.append((getTaskComments() != null) ? getTaskComments() : "");
		sb.append((getTaskName() != null) ? getTaskName() : "");
		return sb.toString();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAutoInjectedInd() {
		return autoInjectedInd;
	}

	public void setAutoInjectedInd(Integer autoInjectedInd) {
		this.autoInjectedInd = autoInjectedInd;
	}

	public String getPermitTypeDesc() {
		return permitTypeDesc;
	}

	public void setPermitTypeDesc(String permitTypeDesc) {
		this.permitTypeDesc = permitTypeDesc;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public boolean isPriceChangedInd() {
		return priceChangedInd;
	}

	public void setPriceChangedInd(boolean priceChangedInd) {
		this.priceChangedInd = priceChangedInd;
	}

}
/*
 * Maintenance History
 * $Log: ServiceOrderTask.java,v $
 * Revision 1.11  2008/05/02 21:23:59  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.10  2008/04/26 00:40:08  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.8.32.3  2008/04/29 15:53:12  gjacks8
 * added comparables
 *
 * Revision 1.8.32.2  2008/04/17 14:11:07  gjacks8
 * added comparable
 *
 * Revision 1.8.32.1  2008/04/14 15:28:36  gjacks8
 * changes for the OMS update story - retrieve service order via customref
 *
 * Revision 1.8.12.1  2008/04/23 11:41:14  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.9  2008/04/23 05:17:05  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.8  2008/02/14 23:44:31  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.7.12.1  2008/02/08 02:32:06  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.7  2007/12/11 01:37:29  mhaye05
 * formatted and added sortOrder
 *
 */