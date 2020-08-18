package com.newco.marketplace.dto.vo.financemanger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.vo.provider.BaseVO;

public class FMReportVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private String reportYear;
	private Date fromDate;
	private Date toDate;
	private String providers;
	private String buyers;
	private String roleType;
	//Type of report
	private String reportName;
	//Name that will be Displayed on front end for scheduling
	private String reportNameForExport;
	private boolean reportByCalendarYear;
	private boolean reportByDateRange;
	private boolean reportByPaymentDate;
	private boolean reportByCompletedDate;
	private boolean reportForSpecificBuyers;
	private boolean reportForSpecificProviders;
	private boolean reportForAllBuyers;
	private boolean reportForAllProviders;
	
	/*VendorId for provider and buyerId for buyer*/
	private Integer roleId; //Company id
	/*1 for provider 3 for buyer and 2 for newCo*/
	private Integer role;
	//resource id
	private Integer resourceId;
	private List<String> buyerList=new ArrayList<String>();
	private List<String> providerList=new ArrayList<String>();
	private int startIndex;
	private int numberOfRecords;
	
	//adding variables for scheduler
	private Integer reportId;
	private Integer totalRecords;
	private Date reportRequestedDate;
	private Date reportGeneratedDate;	
	private String filePath;
	private String reportStatus;	
	private String exception;
	private Integer numberOfAttepmts;
	private boolean export;
	private String reportFooter;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
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
	public boolean isReportForAllBuyers() {
		return reportForAllBuyers;
	}
	public void setReportForAllBuyers(boolean reportForAllBuyers) {
		this.reportForAllBuyers = reportForAllBuyers;
	}
	public boolean isReportForAllProviders() {
		return reportForAllProviders;
	}
	public void setReportForAllProviders(boolean reportForAllProviders) {
		this.reportForAllProviders = reportForAllProviders;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
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

	public Date getReportRequestedDate() {
		return reportRequestedDate;
	}
	public void setReportRequestedDate(Date reportRequestedDate) {
		this.reportRequestedDate = reportRequestedDate;
	}
	public Date getReportGeneratedDate() {
		return reportGeneratedDate;
	}
	public void setReportGeneratedDate(Date reportGeneratedDate) {
		this.reportGeneratedDate = reportGeneratedDate;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public String getReportNameForExport() {
		return reportNameForExport;
	}
	public void setReportNameForExport(String reportNameForExport) {
		this.reportNameForExport = reportNameForExport;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public boolean isExport() {
		return export;
	}
	public void setExport(boolean export) {
		this.export = export;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Integer getNumberOfAttepmts() {
		return numberOfAttepmts;
	}
	public void setNumberOfAttepmts(Integer numberOfAttepmts) {
		this.numberOfAttepmts = numberOfAttepmts;
	}
	public String getReportFooter() {
		return reportFooter;
	}
	public void setReportFooter(String reportFooter) {
		this.reportFooter = reportFooter;
	}
 @Override
 public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("Report Name    "+this.reportName).append("\n");
		builder.append("Providers are   "+this.getProviders()).append("\n");
		builder.append("Buyers are   "+this.getBuyers()).append("\n");
		String fromDateStr=this.fromDate!=null?this.fromDate.toLocaleString():"";
		builder.append("From Date is   "+fromDateStr).append("\n");
		String toDateStr=this.toDate!=null?this.toDate.toLocaleString():"";
		builder.append("To Date is   "+toDateStr).append("\n");
		builder.append("Role Type is   "+this.roleType).append("\n");
		builder.append("ReportNameForExport  "+this.reportNameForExport).append("\n");
		builder.append("ReportByCalendarYear  "+this.reportByCalendarYear).append("\n");
		builder.append("ReportByCompletedDate  "+this.reportByCompletedDate).append("\n");
		builder.append("ReportForSpecificBuyers  "+this.reportForSpecificBuyers).append("\n");
		builder.append("ReportForSpecificProviders  "+this.reportForSpecificProviders).append("\n");
		builder.append("ReportForAllBuyers  "+this.reportForAllBuyers).append("\n");
		builder.append("ReportForAllProviders  "+this.reportForAllProviders).append("\n");
		builder.append("RoleId  "+this.roleId).append("\n");
		builder.append("Role  "+this.role).append("\n");
		builder.append("ResourceId  "+this.resourceId).append("\n");
		builder.append("StartIndex  "+this.startIndex).append("\n");
		builder.append("NumberOfRecords  "+this.numberOfRecords).append("\n");
		builder.append("ReportId  "+this.reportId).append("\n");
		String reportReqDateStr=this.reportRequestedDate!=null?this.reportRequestedDate.toLocaleString():"";
		builder.append("ReportRequestedDate  "+reportReqDateStr).append("\n");
		String reportGenerateDateStr=this.reportGeneratedDate!=null?this.reportGeneratedDate.toLocaleString():"";
		builder.append("ReportGeneratedDate  "+reportGenerateDateStr).append("\n");
		builder.append("FilePath  "+this.filePath).append("\n");
		builder.append("ReportStatus  "+this.reportStatus).append("\n");
		builder.append("NumberOfAttepmts  "+this.numberOfAttepmts).append("\n");
		builder.append("Export is  "+this.export).append("\n");
		builder.append("ReportFooter "+this.reportFooter).append("\n");
		return builder.toString();
 }
}
