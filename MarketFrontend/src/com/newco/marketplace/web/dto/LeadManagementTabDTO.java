package com.newco.marketplace.web.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.vo.leadmanagement.lead.CancelLeadDetail;
import com.newco.marketplace.vo.leadmanagement.lead.LeadInfoDetail;

public class LeadManagementTabDTO extends SerializedBaseDTO{
	
	private static final long serialVersionUID = 2650172279841075524L;
	private List<LeadInfoDetail> leadDetailList= new ArrayList<LeadInfoDetail>();
	private LeadInfoDetail lead= new LeadInfoDetail();
	private CancelLeadDetail cancelLeadDetail= new CancelLeadDetail();
    private String responseMessage;
	//For schedule/Reschedule Informations
	private String scheduledDate;
	private String scheduledStartTime;
	private String scheduledEndTime;
	private String reason;
	private File fileSelect;
	private String description;
	private String documentTitle;
	private String uploadContentType; 
    private String uploadFileName;
    private Integer docCategoryId;
    //Added for Call customer Start
    
    private String callCustReasonCode;
    private String callCustReasonCodeNotAvail;
    private String callCustComments;
    private String callCustNotAvailableComments;
    private Integer cancelInitiatedBy;
    private Integer scheduleIndicator;
    //Added for Call customer End
    
  //Added for cancel
    private Integer reasonCode=0;
	private String comments="";
	private String leadId;
	private boolean revokePointsInd=false;
	//private int status=0;
	private boolean chkAllProviderInd=false;
	private Integer leadCancelInitiatedBy;
	
    //For complete order START
    private String completedDate;
    private String completedTime;
    private String completedProvider;
    private Integer noOfOnsiteVisits;
    private Double feeForParts;
    private Double feeForLabour;
    private String completedComments;
  //For complete order END
    
    public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public boolean isRevokePointsInd() {
		return revokePointsInd;
	}
	public void setRevokePointsInd(boolean revokePointsInd) {
		this.revokePointsInd = revokePointsInd;
	}
	public boolean isChkAllProviderInd() {
		return chkAllProviderInd;
	}
	public void setChkAllProviderInd(boolean chkAllProviderInd) {
		this.chkAllProviderInd = chkAllProviderInd;
	}
	public Integer getLeadCancelInitiatedBy() {
		return leadCancelInitiatedBy;
	}
	public void setLeadCancelInitiatedBy(Integer leadCancelInitiatedBy) {
		this.leadCancelInitiatedBy = leadCancelInitiatedBy;
	}
	public CancelLeadDetail getCancelLeadDetail() {
		return cancelLeadDetail;
	}
	public void setCancelLeadDetail(CancelLeadDetail cancelLeadDetail) {
		this.cancelLeadDetail = cancelLeadDetail;
	}
	public Integer getDocCategoryId() {
		return docCategoryId;
	}
	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}
	public LeadInfoDetail getLead() {
		return lead;
	}
	public void setLead(LeadInfoDetail lead) {
		this.lead = lead;
	}
	public List<LeadInfoDetail> getLeadDetailList() {
		return leadDetailList;
	}
	public void setLeadDetailList(List<LeadInfoDetail> leadDetailList) {
		this.leadDetailList = leadDetailList;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getScheduledStartTime() {
		return scheduledStartTime;
	}
	public void setScheduledStartTime(String scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}
	public String getScheduledEndTime() {
		return scheduledEndTime;
	}
	public void setScheduledEndTime(String scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public File getFileSelect() {
		return fileSelect;
	}
	public void setFileSelect(File fileSelect) {
		this.fileSelect = fileSelect;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Integer getScheduleIndicator() {
		return scheduleIndicator;
	}
	public void setScheduleIndicator(Integer scheduleIndicator) {
		this.scheduleIndicator = scheduleIndicator;
	}
	public String getCallCustComments() {
		return callCustComments;
	}
	
	public void setCallCustComments(String callCustComments) {
		this.callCustComments = callCustComments;
	}
	public Integer getCancelInitiatedBy() {
		return cancelInitiatedBy;
	}
	public void setCancelInitiatedBy(Integer cancelInitiatedBy) {
		this.cancelInitiatedBy = cancelInitiatedBy;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public String getCompletedProvider() {
		return completedProvider;
	}
	public void setCompletedProvider(String completedProvider) {
		this.completedProvider = completedProvider;
	}
	public Integer getNoOfOnsiteVisits() {
		return noOfOnsiteVisits;
	}
	public void setNoOfOnsiteVisits(Integer noOfOnsiteVisits) {
		this.noOfOnsiteVisits = noOfOnsiteVisits;
	}
	public Double getFeeForParts() {
		return feeForParts;
	}
	public void setFeeForParts(Double feeForParts) {
		this.feeForParts = feeForParts;
	}
	public Double getFeeForLabour() {
		return feeForLabour;
	}
	public void setFeeForLabour(Double feeForLabour) {
		this.feeForLabour = feeForLabour;
	}
	public String getCompletedComments() {
		return completedComments;
	}
	public void setCompletedComments(String completedComments) {
		this.completedComments = completedComments;
	}
	public String getCallCustReasonCode() {
		return callCustReasonCode;
	}
	public void setCallCustReasonCode(String callCustReasonCode) {
		this.callCustReasonCode = callCustReasonCode;
	}
	public String getCallCustReasonCodeNotAvail() {
		return callCustReasonCodeNotAvail;
	}
	public void setCallCustReasonCodeNotAvail(String callCustReasonCodeNotAvail) {
		this.callCustReasonCodeNotAvail = callCustReasonCodeNotAvail;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getCallCustNotAvailableComments() {
		return callCustNotAvailableComments;
	}
	public void setCallCustNotAvailableComments(String callCustNotAvailableComments) {
		this.callCustNotAvailableComments = callCustNotAvailableComments;
	}

	
}