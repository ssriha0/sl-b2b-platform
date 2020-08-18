package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.domain.SOTask;

public class IncreaseSpendLimitDTO extends SOWBaseTabDTO implements OrderConstants{
	
	/**
         *
	 */
	private static final long serialVersionUID = 2808062814706614261L;
        private String buyerId;
	private String selectedSO;
	private String groupId;
	private String currentSpendLimit;
	private String currentLimitLabor;
	private String currentLimitParts;
	private String totalSpendLimit;
	private String totalSpendLimitParts;
	private String increaseLimit;
	private String increaseLimitParts;
	private String increasedSpendLimitComment;
	private String increasedSpendLimitReasonId;
	public String getIncreasedSpendLimitReasonId() {
		return increasedSpendLimitReasonId;
	}
	public void setIncreasedSpendLimitReasonId(String increasedSpendLimitReasonId) {
		this.increasedSpendLimitReasonId = increasedSpendLimitReasonId;
	}
	public String getIncreasedSpendLimitReason() {
		return increasedSpendLimitReason;
	}
	public void setIncreasedSpendLimitReason(String increasedSpendLimitReason) {
		this.increasedSpendLimitReason = increasedSpendLimitReason;
	}
	private String increasedSpendLimitReason;
	private Long accountID;
	private Boolean autoACH;
	private boolean taskLevelPriceInd = false;
	private Integer errorInd;
	
	private ArrayList<SOTaskDTO> taskList = new ArrayList<SOTaskDTO>();
	
	public ArrayList<SOTaskDTO> getTaskList() {
		return taskList;
	}
	public void setTaskList(ArrayList<SOTaskDTO> taskList) {
		this.taskList = taskList;
	}
	public Long getAccountID() {
		return accountID;
	}
	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}
	public Boolean getAutoACH() {
		return autoACH;
	}
	public void setAutoACH(Boolean autoACH) {
		this.autoACH = autoACH;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getSelectedSO() {
		return selectedSO;
	}
	public void setSelectedSO(String selectedSO) {
		this.selectedSO = selectedSO;
	}
	public String getCurrentSpendLimit() {
		return currentSpendLimit;
	}
	public String getTotalSpendLimitParts() {
		return totalSpendLimitParts;
	}
	public void setTotalSpendLimitParts(String totalSpendLimitParts) {
		this.totalSpendLimitParts = totalSpendLimitParts;
	}
	public void setCurrentSpendLimit(String currentSpendLimit) {
		this.currentSpendLimit = currentSpendLimit;
	}
	public String getTotalSpendLimit() {
		return totalSpendLimit;
	}
	public void setTotalSpendLimit(String totalSpendLimit) {
		this.totalSpendLimit = totalSpendLimit;
	}
	public String getIncreaseLimit() {
		return increaseLimit;
	}
	public String getIncreaseLimitParts() {
		return increaseLimitParts;
	}
	public void setIncreaseLimitParts(String increaseLimitParts) {
		this.increaseLimitParts = increaseLimitParts;
	}
	public void setIncreaseLimit(String increaseLimit) {
		this.increaseLimit = increaseLimit;
	}
	public String getIncreasedSpendLimitComment() {
		return increasedSpendLimitComment;
	}
	public void setIncreasedSpendLimitComment(String incSpendLimitComment) {
		this.increasedSpendLimitComment = incSpendLimitComment;
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("buyerId", getBuyerId())
			.append("selectedSO", getSelectedSO())
			.append("currentSpendLimit", getCurrentSpendLimit())
			.append("totalSpendLimit", getTotalSpendLimit())	
			.append("totalSpendLimitParts", getTotalSpendLimitParts())
			.append("increaseLimit", getIncreaseLimit())
			.append("increaseLimitParts", getIncreaseLimitParts())
			.append("increasedSpendLimitComment", getIncreasedSpendLimitComment())
			.append("increasedSpendLimitReason", getIncreasedSpendLimitReason())
			.toString();
	}
	
	@Override
	public void validate() {
                Double increaseLimitDouble;
		System.out.println("Values : " + toString());
		
		if ((StringUtils.isEmpty(totalSpendLimit) && StringUtils.isEmpty(totalSpendLimitParts))){
			addError("increaseLimitLabor", INCREASE_IN_SPEND_LIMIT_REQUIRED , OrderConstants.SOW_TAB_ERROR);

		}
		if ((null == getErrors() || getErrors().isEmpty()) && (!(StringUtils.isEmpty(totalSpendLimit))) && (totalSpendLimit != null) && !(NumberUtils.isNumber(totalSpendLimit.toString()))){

			addError("increaseLimitLabor", NUMBER_INCREASE_LABOR_LIMIT , OrderConstants.SOW_TAB_ERROR);
		}
		if ((null == getErrors() || getErrors().isEmpty()) && (!(StringUtils.isEmpty(totalSpendLimitParts))) && (totalSpendLimitParts != null) && !(NumberUtils.isNumber(totalSpendLimitParts.toString()))){

			addError("increaseLimitParts", NUMBER_INCREASE_PARTS_LIMIT , OrderConstants.SOW_TAB_ERROR);
		}
		if(null ==getErrors() || getErrors().isEmpty() &&
				((StringUtils.equals(increasedSpendLimitReason,OTHER) 
						&& StringUtils.isEmpty(increasedSpendLimitComment)))
							||(StringUtils.equals(increasedSpendLimitReason, SELECT))){
			addError("increasedSpendLimitComment", REASON_REQUIRED, OrderConstants.SOW_TAB_ERROR); 
		}
		if (null == getErrors() || getErrors().isEmpty()) {
			// Make sure the total amount has increased
			Double currentLimitLaborDouble = null;
			if (!StringUtils.isEmpty(currentLimitLabor) && currentLimitLabor != null && NumberUtils.isNumber(currentLimitLabor.toString())){

				currentLimitLaborDouble = Double.parseDouble(currentLimitLabor);
			} else {
				currentLimitLaborDouble = 0D;
	}
			
			Double currentLimitPartsDouble = null;
			if (!StringUtils.isEmpty(currentLimitParts) && currentLimitParts != null && NumberUtils.isNumber(currentLimitParts.toString())){

				currentLimitPartsDouble = Double.parseDouble(currentLimitParts);
			} else {
				currentLimitPartsDouble = 0D;
			}
			
                        if(totalSpendLimit.equals("")|| totalSpendLimit == null){
                                increaseLimitDouble = Double.parseDouble(currentLimitLabor);
                        }
                        else{
                                increaseLimitDouble = Double.parseDouble(totalSpendLimit);
                        }
			Double increaseLimitPartsDouble = Double.parseDouble(totalSpendLimitParts);
			
			if (increaseLimitDouble + increaseLimitPartsDouble <= currentLimitLaborDouble + currentLimitPartsDouble) {

				addError("increaseLimitLabor", INCREASE_IN_SPEND_LIMIT_MUST_INCREASE, OrderConstants.SOW_TAB_ERROR);

			}
			
		}
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCurrentLimitLabor() {
		return currentLimitLabor;
	}
	public void setCurrentLimitLabor(String currentLimitLabor) {
		this.currentLimitLabor = currentLimitLabor;
	}
	public String getCurrentLimitParts() {
		return currentLimitParts;
	}
	public void setCurrentLimitParts(String currentLimitParts) {
		this.currentLimitParts = currentLimitParts;
	}
	public boolean isTaskLevelPriceInd() {
		return taskLevelPriceInd;
	}
	public void setTaskLevelPriceInd(boolean taskLevelPriceInd) {
		this.taskLevelPriceInd = taskLevelPriceInd;
	}
	public Integer getErrorInd() {
		return errorInd;
	}
	public void setErrorInd(Integer errorInd) {
		this.errorInd = errorInd;
	}
		
}
