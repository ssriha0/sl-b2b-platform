package com.newco.marketplace.dto.vo.spn;

import java.util.Date;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class SPNMainMonitorVO extends SerializableBaseVO{
	private static final long serialVersionUID = 20090413L;
	private int spnId;
	private String buyerName;
	private String spnName;
	private String spnDescription;
	private String spnInstruction;
	private String membershipStatus;
	private Integer membershipStatusIndex;
	private String providerFirmState;
	private String message;
	private int qualifiedProviders;
	private int totalProviders;	
	private int buyerId;
	private int buyerDocId;
	private int vendorId;
	private String contactEmail;
	private String contactName;
	private String contactPhone;
	private int agreementInd;
	private Boolean isAlias;
	private Boolean hasAlias;
	private Date aliasEffectiveDate;
	private Integer aliasSPNId;
	private String aliasProviderFirmState;
	private Boolean isAliasEffective;
	private Boolean auditRequired;
	private List<SPNDocumentVO> spnDocuments;
	private List<SPNInvitationNoInterestVO> spnInvitationNoInterestVO;
	private List<SPNDocumentVO> spnInfoDocuments;
	private List<SPNAgreeDocumentVO> spnAgreeDocuments;
	private List<SPNDocumentVO> spnSignDocuments;
	private List<SPNSignAndReturnDocumentVO> spnSignAndReturnDocuments;
	private List<SPNApprovalCriteriaVO> spnApprovalCriteria;
	private List<SPNProvUploadedDocsVO> spnProvUploadedDocs;
	
	public List<SPNApprovalCriteriaVO> getSpnApprovalCriteria() {
		return spnApprovalCriteria;
	}
	public void setSpnApprovalCriteria(
			List<SPNApprovalCriteriaVO> spnApprovalCriteria) {
		this.spnApprovalCriteria = spnApprovalCriteria;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public String getMembershipStatus() {
		return membershipStatus;
	}
	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getQualifiedProviders() {
		return qualifiedProviders;
	}
	public void setQualifiedProviders(int qualifiedProviders) {
		this.qualifiedProviders = qualifiedProviders;
	}
	public int getTotalProviders() {
		return totalProviders;
	}
	public void setTotalProviders(int totalProviders) {
		this.totalProviders = totalProviders;
	}
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public List<SPNDocumentVO> getSpnDocuments() {
		return spnDocuments;
	}
	public void setSpnDocuments(List<SPNDocumentVO> spnDocuments) {
		this.spnDocuments = spnDocuments;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public List<SPNProvUploadedDocsVO> getSpnProvUploadedDocs() {
		return spnProvUploadedDocs;
	}
	public void setSpnProvUploadedDocs(
			List<SPNProvUploadedDocsVO> spnProvUploadedDocs) {
		this.spnProvUploadedDocs = spnProvUploadedDocs;
	}
	public List<SPNSignAndReturnDocumentVO> getSpnSignAndReturnDocuments() {
		return spnSignAndReturnDocuments;
	}
	public void setSpnSignAndReturnDocuments(
			List<SPNSignAndReturnDocumentVO> spnSignAndReturnDocuments) {
		this.spnSignAndReturnDocuments = spnSignAndReturnDocuments;
	}
	public int getAgreementInd() {
		return agreementInd;
	}
	public void setAgreementInd(int agreementInd) {
		this.agreementInd = agreementInd;
	}
	public String getSpnDescription() {
		return spnDescription;
	}
	public void setSpnDescription(String spnDescription) {
		this.spnDescription = spnDescription;
	}
	public String getSpnInstruction() {
		return spnInstruction;
	}
	public void setSpnInstruction(String spnInstruction) {
		this.spnInstruction = spnInstruction;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getProviderFirmState() {
		return providerFirmState;
	}
	public void setProviderFirmState(String providerFirmState) {
		this.providerFirmState = providerFirmState;
	}
	public int getBuyerDocId() {
		return buyerDocId;
	}
	public void setBuyerDocId(int buyerDocId) {
		this.buyerDocId = buyerDocId;
	}
	public List<SPNDocumentVO> getSpnInfoDocuments() {
		return spnInfoDocuments;
	}
	public void setSpnInfoDocuments(List<SPNDocumentVO> spnInfoDocuments) {
		this.spnInfoDocuments = spnInfoDocuments;
	}
	public List<SPNDocumentVO> getSpnSignDocuments() {
		return spnSignDocuments;
	}
	public void setSpnSignDocuments(List<SPNDocumentVO> spnSignDocuments) {
		this.spnSignDocuments = spnSignDocuments;
	}
	public List<SPNInvitationNoInterestVO> getSpnInvitationNoInterestVO() {
		return spnInvitationNoInterestVO;
	}
	public void setSpnInvitationNoInterestVO(
			List<SPNInvitationNoInterestVO> spnInvitationNoInterestVO) {
		this.spnInvitationNoInterestVO = spnInvitationNoInterestVO;
	}
	public List<SPNAgreeDocumentVO> getSpnAgreeDocuments() {
		return spnAgreeDocuments;
	}
	public void setSpnAgreeDocuments(List<SPNAgreeDocumentVO> spnAgreeDocuments) {
		this.spnAgreeDocuments = spnAgreeDocuments;
	}
	public Boolean getHasAlias() {
		return hasAlias;
	}
	public void setHasAlias(Boolean hasAlias) {
		this.hasAlias = hasAlias;
	}
	public Date getAliasEffectiveDate() {
		return aliasEffectiveDate;
	}
	public void setAliasEffectiveDate(Date aliasEffectiveDate) {
		this.aliasEffectiveDate = aliasEffectiveDate;
	}
	public Integer getAliasSPNId() {
		return aliasSPNId;
	}
	public void setAliasSPNId(Integer aliasSPNId) {
		this.aliasSPNId = aliasSPNId;
	}
	public String getAliasProviderFirmState() {
		return aliasProviderFirmState;
	}
	public void setAliasProviderFirmState(String aliasProviderFirmState) {
		this.aliasProviderFirmState = aliasProviderFirmState;
	}
	public Boolean getIsAlias() {
		return isAlias;
	}
	public void setIsAlias(Boolean isAlias) {
		this.isAlias = isAlias;
	}

	public void setIsAliasEffective(Boolean isAliasEffective) {
		this.isAliasEffective = isAliasEffective;
	}
	public Boolean getIsAliasEffective() {
		return isAliasEffective;
	}

	/**
	 * @return the membershipStatusIndex
	 */
	public Integer getMembershipStatusIndex() {
		return membershipStatusIndex;
	}
	/**
	 * @param membershipStatusIndex the membershipStatusIndex to set
	 */
	public void setMembershipStatusIndex(Integer membershipStatusIndex) {
		this.membershipStatusIndex = membershipStatusIndex;
	}
	public Boolean getAuditRequired() {
		return auditRequired;
	}
	public void setAuditRequired(Boolean auditRequired) {
		this.auditRequired = auditRequired;
	}

}