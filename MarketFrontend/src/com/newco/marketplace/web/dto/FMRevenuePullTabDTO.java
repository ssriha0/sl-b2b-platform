package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

public class FMRevenuePullTabDTO extends SerializedBaseDTO
{
	private String tab;
	private String calendarOnDate;
	private String roleType;
	private Integer companyId;
	private String successMsg;
	private boolean slAdminInd;
	private String revenuePullAmount;
	private String revenuePullNote;
	private List<IError> errors = new ArrayList<IError>();
	
	
	
	public String getRevenuePullNote() {
		return revenuePullNote;
	}
	public void setRevenuePullNote(String revenuePullNote) {
		this.revenuePullNote = revenuePullNote;
	}
	public String getRevenuePullAmount() {
		return revenuePullAmount;
	}
	public void setRevenuePullAmount(String revenuePullAmount) {
		this.revenuePullAmount = revenuePullAmount;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getCalendarOnDate() {
		return calendarOnDate;
	}
	public void setCalendarOnDate(String calendarOnDate) {
		this.calendarOnDate = calendarOnDate;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public boolean isSlAdminInd() {
		return slAdminInd;
	}
	public void setSlAdminInd(boolean slAdminInd) {
		this.slAdminInd = slAdminInd;
	}
	public List<IError> getErrors() {
		return errors;
	}
	public void setErrors(List<IError> errors) {
		this.errors = errors;
	}
	}
