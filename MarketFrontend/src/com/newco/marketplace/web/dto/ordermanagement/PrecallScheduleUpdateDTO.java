package com.newco.marketplace.web.dto.ordermanagement;

import com.newco.marketplace.web.dto.RescheduleDTO;
import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class PrecallScheduleUpdateDTO extends SerializedBaseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String soId;
	private String customerAvailableFlag;
	private String customerNotAvalableReasonCode;
	private String eta;
	private String assignedResourceId;
	private int timeResponseWindow;
	private String startTime;
	private String endTime;
	private boolean  specialInstructionEditFlag;
	private boolean  locationNotesFlag;
	private String specialInstructions;
	private String locationNotes;
	private boolean specificOrDateRange;
	private String source;
	private String startDate;
	private String endDate;
	private String timeZone;
	
	private boolean reAssign;
	
	//private RescheduleDTO rescheduleDTO;
	
	
	public String getSource() {
		return source;
	}

	public boolean isReAssign() {
		return reAssign;
	}

	public void setReAssign(boolean reAssign) {
		this.reAssign = reAssign;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getCustomerAvailableFlag() {
		return customerAvailableFlag;
	}

	public void setCustomerAvailableFlag(String customerAvailableFlag) {
		this.customerAvailableFlag = customerAvailableFlag;
	}

	public String getCustomerNotAvalableReasonCode() {
		return customerNotAvalableReasonCode;
	}

	public void setCustomerNotAvalableReasonCode(
			String customerNotAvalableReasonCode) {
		this.customerNotAvalableReasonCode = customerNotAvalableReasonCode;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getAssignedResourceId() {
		return assignedResourceId;
	}

	public void setAssignedResourceId(String assignedResourceId) {
		this.assignedResourceId = assignedResourceId;
	}

	public int getTimeResponseWindow() {
		return timeResponseWindow;
	}

	public void setTimeResponseWindow(int timeResponseWindow) {
		this.timeResponseWindow = timeResponseWindow;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/*
	public RescheduleDTO getRescheduleDTO() {
		return rescheduleDTO;
	}

	public void setRescheduleDTO(RescheduleDTO rescheduleDTO) {
		this.rescheduleDTO = rescheduleDTO;
	}
	*/
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getLocationNotes() {
		return locationNotes;
	}

	public void setLocationNotes(String locationNotes) {
		this.locationNotes = locationNotes;
	}

	public boolean isSpecialInstructionEditFlag() {
		return specialInstructionEditFlag;
	}

	public void setSpecialInstructionEditFlag(boolean specialInstructionEditFlag) {
		this.specialInstructionEditFlag = specialInstructionEditFlag;
	}

	public boolean isLocationNotesFlag() {
		return locationNotesFlag;
	}

	public void setLocationNotesFlag(boolean locationNotesFlag) {
		this.locationNotesFlag = locationNotesFlag;
	}

	public boolean getSpecificOrDateRange() {
		return specificOrDateRange;
	}

	public void setSpecificOrDateRange(boolean specificOrDateRange) {
		this.specificOrDateRange = specificOrDateRange;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	
}
