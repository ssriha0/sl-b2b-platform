package com.newco.marketplace.dto.vo.survey;

import java.util.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * This VO represents summary of imported ratings from one single west survey file 
 */
public class WestImportSummaryVO extends SerializableBaseVO {

	private static final long serialVersionUID = 2684794988975538553L;
	
	private String westFileName;
	private int totalRecords;
	private int successCount;
	private int skipCount;
	private int failureCount;
	private Date processedTime;
	
	private List<WestSurveyErrorVO> erroredSurveyObjects;
	private List<String> soIds;
	
	public WestImportSummaryVO(String westFileName, int totalRecords, int successCount, int skipCount, int failureCount, List<WestSurveyErrorVO> erroredSurveyObjects, List<String> soIds) {
		this.westFileName = westFileName;
		this.totalRecords = totalRecords;
		this.successCount = successCount;
		this.skipCount = skipCount;
		this.failureCount = failureCount;
		this.processedTime = new Date(System.currentTimeMillis());
		this.erroredSurveyObjects = erroredSurveyObjects;
		this.soIds = soIds;
	}
	
	public String getWestFileName() {
		return westFileName;
	}
	
	public void setWestFileName(String westFileName) {
		this.westFileName = westFileName;
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}
	
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public int getSuccessCount() {
		return successCount;
	}
	
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
	public int getSkipCount() {
		return skipCount;
	}
	
	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}
	
	public int getFailureCount() {
		return failureCount;
	}
	
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	
	public Date getProcessedTime() {
		return processedTime;
	}
	
	public void setProcessedTime(Date processedTime) {
		this.processedTime = processedTime;
	}

	public List<WestSurveyErrorVO> getErroredSurveyObjects() {
		return erroredSurveyObjects;
	}

	public void setErroredSurveyObjects(List<WestSurveyErrorVO> erroredSurveyObjects) {
		this.erroredSurveyObjects = erroredSurveyObjects;
	}

	public List<String> getSoIds() {
		return soIds;
	}

	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}
	
}
