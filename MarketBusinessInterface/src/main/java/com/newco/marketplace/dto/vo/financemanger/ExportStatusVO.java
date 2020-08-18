package com.newco.marketplace.dto.vo.financemanger;

import java.util.Date;

import com.newco.marketplace.vo.provider.BaseVO;

public class ExportStatusVO extends BaseVO{


	private static final long serialVersionUID = 1L;
	
	private Integer reportId;
	private String reportName;
	private String reportType;
	private Date reportDate;
	private String reportStatus;
	private String filePath;
	private Integer totalRecords;
	private Integer resourceId;
	private boolean reportByPaymentDate;
	
	private boolean allBuyers;
	private boolean allProviders;
	private String buyerIds;
	private String providerIds;
	private Date fromDate;
	private Date toDate;
	private boolean isAdopted;
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public boolean isReportByPaymentDate() {
		return reportByPaymentDate;
	}
	public void setReportByPaymentDate(boolean reportByPaymentDate) {
		this.reportByPaymentDate = reportByPaymentDate;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isAllBuyers() {
		return allBuyers;
	}
	public void setAllBuyers(boolean allBuyers) {
		this.allBuyers = allBuyers;
	}
	public boolean isAllProviders() {
		return allProviders;
	}
	public void setAllProviders(boolean allProviders) {
		this.allProviders = allProviders;
	}
	public String getBuyerIds() {
		return buyerIds;
	}
	public void setBuyerIds(String buyerIds) {
		this.buyerIds = buyerIds;
	}
	public String getProviderIds() {
		return providerIds;
	}
	public void setProviderIds(String providerIds) {
		this.providerIds = providerIds;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public boolean isAdopted() {
		return isAdopted;
	}
	public void setAdopted(boolean isAdopted) {
		this.isAdopted = isAdopted;
	}

}
