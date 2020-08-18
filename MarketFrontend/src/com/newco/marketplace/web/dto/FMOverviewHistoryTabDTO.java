package com.newco.marketplace.web.dto;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.vo.PaginationVO;

public class FMOverviewHistoryTabDTO extends SerializedBaseDTO
{
	private String selectedCalInterval;
	private Integer size;
	private String calendarId;
	private List<TransactionDTO> transaction;
	private Integer radioButton;
	private String intervalChecked;
	private String dateRangeChecked;
	private String dateRangeMessage;
	private String tab;
	private Date calendarFromDate;
	private Date calendarToDate;
	private String dojoCalendarFromDate;
	private String dojoCalendarToDate;
	private String roleType;
	private Integer companyId;
	private String errorMsg;
	private boolean slAdminInd;
	private boolean buyerAdminInd;
	private Integer entityTypeId;
	private PaginationVO paginationVO;
	private String message;
	private String exportMessageCheck;
	private Integer totalCount;
	private Integer recordSize;
	private String searchType;
	
	
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public Integer getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getExportMessageCheck() {
		return exportMessageCheck;
	}
	public void setExportMessageCheck(String exportMessageCheck) {
		this.exportMessageCheck = exportMessageCheck;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public PaginationVO getPaginationVO() {
		return paginationVO;
	}
	public void setPaginationVO(PaginationVO paginationVO) {
		this.paginationVO = paginationVO;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getDateRangeChecked() {
		return dateRangeChecked;
	}
	public void setDateRangeChecked(String dateRangeChecked) {
		this.dateRangeChecked = dateRangeChecked;
	}
	public String getIntervalChecked() {
		return intervalChecked;
	}
	public void setIntervalChecked(String intervalChecked) {
		this.intervalChecked = intervalChecked;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
	public Integer getRadioButton() {
		return radioButton;
	}
	public void setRadioButton(Integer radioButton) {
		this.radioButton = radioButton;
	}
	public String getDojoCalendarFromDate() {
		return dojoCalendarFromDate;
	}
	public void setDojoCalendarFromDate(String dojoCalendarFromDate) {
		this.dojoCalendarFromDate = dojoCalendarFromDate;
	}
	public String getDojoCalendarToDate() {
		return dojoCalendarToDate;
	}
	public void setDojoCalendarToDate(String dojoCalendarToDate) {
		this.dojoCalendarToDate = dojoCalendarToDate;
	}
	public Date getCalendarFromDate() {
		return calendarFromDate;
	}
	public void setCalendarFromDate(Date calendarFromDate) {
		this.calendarFromDate = calendarFromDate;
	}
	public Date getCalendarToDate() {
		return calendarToDate;
	}
	public void setCalendarToDate(Date calendarToDate) {
		this.calendarToDate = calendarToDate;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public String getDateRangeMessage() {
		return dateRangeMessage;
	}
	public void setDateRangeMessage(String dateRangeMessage) {
		this.dateRangeMessage = dateRangeMessage;
	}

	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	public String getSelectedCalInterval() {
		return selectedCalInterval;
	}
	public void setSelectedCalInterval(String selectedCalInterval) {
		this.selectedCalInterval = selectedCalInterval;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public List<TransactionDTO> getTransaction() {
		return transaction;
	}
	public void setTransaction(List<TransactionDTO> transaction) {
		this.transaction = transaction;
	}
	public boolean isSlAdminInd() {
		return slAdminInd;
	}
	public void setSlAdminInd(boolean slAdminInd) {
		this.slAdminInd = slAdminInd;
	}
	public boolean isBuyerAdminInd() {
		return buyerAdminInd;
	}
	public void setBuyerAdminInd(boolean buyerAdminInd) {
		this.buyerAdminInd = buyerAdminInd;
	}
	}
