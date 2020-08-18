package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FMReportTabDTO extends SOWBaseTabDTO {


	private static final long serialVersionUID = 1L;
	private String reportYear;
	private Date dtFromDate;
	private Date dtToDate;
	private String fromDate;
	private String toDate;
	private String roleType;
	private String reportName;
	private String reportType;
	private boolean reportByCalendarYear;
	private boolean reportByDateRange;
	private boolean reportByPaymentDate;
	private boolean reportByCompletedDate;
	private boolean reportForSpecificBuyers;
	private boolean reportForSpecificProviders;
	private boolean reportForAllBuyers;
	private boolean reportForAllProviders;
	private boolean export;
	//Company id
	private Integer roleId;
	//1 for provider, 2 for admin, 3 for buyer
	private Integer Id;
	//Resource id
	private Integer resourceId;
	private String providers;
	private String buyers;
	private List<String> buyerList=new ArrayList<String>();
	private List<String> providerList=new ArrayList<String>();
	private int totalRecords;
	private int startIndex;
	private int numberOfRecords;
	private String requestRandom;
	private int reportId;
	private String reportFooter;
	private String filePath;
	private String fileName;
	private List<SOWError> reportErrors = new ArrayList<SOWError>();
	public boolean isReportByCalendarYear() {
		return reportByCalendarYear;
	}

	public void setReportByCalendarYear(boolean reportByCalendarYear) {
		this.reportByCalendarYear = reportByCalendarYear;
	}

	public boolean isReportByDateRange() {
		return reportByDateRange;
	}

	public void setReportByDateRange(boolean reportByDateRange) {
		this.reportByDateRange = reportByDateRange;
	}

	public boolean isReportByPaymentDate() {
		return reportByPaymentDate;
	}

	public void setReportByPaymentDate(boolean reportByPaymentDate) {
		this.reportByPaymentDate = reportByPaymentDate;
	}

	public boolean isReportByCompletedDate() {
		return reportByCompletedDate;
	}

	public void setReportByCompletedDate(boolean reportByCompletedDate) {
		this.reportByCompletedDate = reportByCompletedDate;
	}

	@Override
	public String getTabIdentifier() {
		return "";
	}

	@Override
	public void validate() {
		
		
		
	}

	public String getReportYear() {
		return reportYear;
	}

	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}


	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getProviders() {
		return providers;
	}

	public void setProviders(String providers) {
		this.providers = providers;
	}

	public String getBuyers() {
		return buyers;
	}

	public void setBuyers(String buyers) {
		this.buyers = buyers;
	}


	public List<String> getBuyerList() {
		return buyerList;
	}

	public void setBuyerList(List<String> buyerList) {
		this.buyerList = buyerList;
	}

	public List<String> getProviderList() {
		return providerList;
	}

	public void setProviderList(List<String> providerList) {
		this.providerList = providerList;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public boolean isReportForSpecificBuyers() {
		return reportForSpecificBuyers;
	}

	public void setReportForSpecificBuyers(boolean reportForSpecificBuyers) {
		this.reportForSpecificBuyers = reportForSpecificBuyers;
	}

	public boolean isReportForSpecificProviders() {
		return reportForSpecificProviders;
	}

	public void setReportForSpecificProviders(boolean reportForSpecificProviders) {
		this.reportForSpecificProviders = reportForSpecificProviders;
	}

	public boolean isReportForAllProviders() {
		return reportForAllProviders;
	}

	public void setReportForAllProviders(boolean reportForAllProviders) {
		this.reportForAllProviders = reportForAllProviders;
	}

	public boolean isReportForAllBuyers() {
		return reportForAllBuyers;
	}

	public void setReportForAllBuyers(boolean reportForAllBuyers) {
		this.reportForAllBuyers = reportForAllBuyers;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public String getReportType() {
		return reportType;
	}

	public List<SOWError> getReportErrors() {
		return reportErrors;
	}

	public void setReportErrors(List<SOWError> reportErrors) {
		this.reportErrors = reportErrors;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Date getDtFromDate() {
		return dtFromDate;
	}

	public void setDtFromDate(Date dtFromDate) {
		this.dtFromDate = dtFromDate;
	}

	public Date getDtToDate() {
		return dtToDate;
	}

	public void setDtToDate(Date dtToDate) {
		this.dtToDate = dtToDate;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getReportFooter() {
		return reportFooter;
	}

	public void setReportFooter(String reportFooter) {
		this.reportFooter = reportFooter;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRequestRandom() {
		return requestRandom;
	}

	public void setRequestRandom(String requestRandom) {
		this.requestRandom = requestRandom;
	}
}
