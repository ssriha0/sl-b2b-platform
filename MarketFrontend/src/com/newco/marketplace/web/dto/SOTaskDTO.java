package com.newco.marketplace.web.dto;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.interfaces.OrderConstants;

public class SOTaskDTO extends SOWBaseTabDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9126132079779801957L;
	private String taskName;
	private String title;
	private String category;
	private String subCategory;
	private String skill;	
	private ArrayList<LabelValueBean> questionList = new ArrayList<LabelValueBean>();
	private String comments;
	private Integer skillId = -1;
	private Integer categoryId = -1;
	private Integer subCategoryId = -1;
	private String soId;
	private Double price;
	private int autoInjected;
	private Integer sortOrder;
	private boolean primaryTask;
		
	private Boolean isSaved = false;
	private Boolean isFreshTask = true;
	
	//added for SL-8937
	private Double sellingPrice;
	private Double retailPrice;
	private Double finalPrice;
	private Double custCharge;
	private Integer taskType;
	private Integer permitType;
	private Integer sequenceNumber;
	private String permitTaskDesc;
	private String sku;
	private boolean priceChangedInd;
	//Using the same variable for sku as sku id
	//Start of creating variable for sku to fetch documents after delete of primary sku
	private Integer skuIdForSku;
	private Integer templateIdForSku;
	//SL-20884: holding partsSuppliedBy in case of multiple sku added.
	private Integer partsSuppliedBy;
	//SL-21355 : Holding the buyerDocument logo selected for the SKU -template association
	private Integer buyerDocumentLogo;
	private Integer selectedSkuCategoryNew;
	
	public Integer getSkuIdForSku() {
		return skuIdForSku;
	}
	public void setSkuIdForSku(Integer skuIdForSku) {
		this.skuIdForSku = skuIdForSku;
	}
	public Integer getTemplateIdForSku() {
		return templateIdForSku;
	}
	public void setTemplateIdForSku(Integer templateIdForSku) {
		this.templateIdForSku = templateIdForSku;
	}
	
	//End of creating variable for sku to fetch documents after delete of primary sku
	
	private Integer taskId;
	private String taskStatus;
	private ArrayList<SOTaskPriceHistoryDTO> priceHistoryList = new ArrayList<SOTaskPriceHistoryDTO>();
	
	//Do not persist this. This is for Frontend only
	private ArrayList<SkillNodeVO> subCategoryList = new ArrayList<SkillNodeVO>();

	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
		dirtyFlag=true;
		
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
		dirtyFlag=true;
		
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
		dirtyFlag=true;
		
	}
	public ArrayList<LabelValueBean> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(ArrayList<LabelValueBean> questionList) {
		this.questionList = questionList;
		dirtyFlag=true;
		
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
		dirtyFlag=true;
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		dirtyFlag=true;
		
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
		dirtyFlag=true;
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validate() {
		
		if(getComments().length()> 255){
			addError(getTheResourceBundle().getString("New_Task_Comment"), 
					getTheResourceBundle().getString("Comment_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getTaskName()==null || getTaskName().length() > 35){
			addError(getTheResourceBundle().getString("Task_Name"), 
					getTheResourceBundle().getString("Task_Name_Empty_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getCategory() == null){
			addError(getTheResourceBundle().getString("Category"), 
					getTheResourceBundle().getString("Category_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getSkill() == null ){
			addError(getTheResourceBundle().getString("Skill"),
					getTheResourceBundle().getString("Skill_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		//return getErrors();
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
	
	public ArrayList<SkillNodeVO> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(ArrayList<SkillNodeVO> subCategoryList) {
		this.subCategoryList = subCategoryList;
		dirtyFlag=true;
	}
	public Boolean getIsSaved() {
		return isSaved;
	}
	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}
	public Boolean getIsFreshTask() {
		return isFreshTask;
	}
	public void setIsFreshTask(Boolean isFreshTask) {
		this.isFreshTask = isFreshTask;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getAutoInjected() {
		return autoInjected;
	}
	public void setAutoInjected(int autoInjected) {
		this.autoInjected = autoInjected;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public boolean isPrimaryTask() {
		return primaryTask;
	}
	public void setPrimaryTask(boolean primaryTask) {
		this.primaryTask = primaryTask;
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
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public boolean isPriceChangedInd() {
		return priceChangedInd;
	}
	public void setPriceChangedInd(boolean priceChangedInd) {
		this.priceChangedInd = priceChangedInd;
	}

	public ArrayList<SOTaskPriceHistoryDTO> getPriceHistoryList() {
		return priceHistoryList;
	}

	public void setPriceHistoryList(
			ArrayList<SOTaskPriceHistoryDTO> priceHistoryList) {
		this.priceHistoryList = priceHistoryList;
	}
	public Integer getPermitType() {
		return permitType;
	}
	public void setPermitType(Integer permitType) {
		this.permitType = permitType;
	}
	
	public String getPermitTaskDesc() {
		return permitTaskDesc;
	}
	public void setPermitTaskDesc(String permitTaskDesc) {
		this.permitTaskDesc = permitTaskDesc;
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
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Double getCustCharge() {
		return custCharge;
	}
	public void setCustCharge(Double custCharge) {
		this.custCharge = custCharge;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Integer getPartsSuppliedBy() {
		return partsSuppliedBy;
	}
	public void setPartsSuppliedBy(Integer partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}
	public Integer getSelectedSkuCategoryNew() {
		return selectedSkuCategoryNew;
	}
	public void setSelectedSkuCategoryNew(Integer selectedSkuCategoryNew) {
		this.selectedSkuCategoryNew = selectedSkuCategoryNew;
	}
	public Integer getBuyerDocumentLogo() {
		return buyerDocumentLogo;
	}
	public void setBuyerDocumentLogo(Integer buyerDocumentLogo) {
		this.buyerDocumentLogo = buyerDocumentLogo;
	}
	

}
