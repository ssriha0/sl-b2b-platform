package com.newco.marketplace.dto.vo.fullfillment;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class FullfillmentEntryVO extends SerializableBaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6729272647297236103L;
	private boolean resendRequest = false;
	private String messageIdentifier;
	private Long fullfillmentGroupId;
	private Long fullfillmentEntryIdOrig;
	private Long fullfillmentEntryId;
	private Long nextFullfillmentEntryId;
	private Integer busTransId;
	private Integer fullfillmentEntryRuleId;
	private Long ledgerEntityId;
	private Integer entityTypeId;
	private String entityTypeDesc;
	private Integer entryTypeId;
	private Timestamp entryDate;
	private String referenceNo;
	private String entryRemark;
	private Integer reconsiledInd;
	private Timestamp reconsiledDate;
	private Integer ptdReconsiledInd;
	private Timestamp ptdReconsiledDate;
	private Integer achReconsiledInd;
	private Timestamp achReconsiledDate;
	private Boolean ccAuthorizedInd;
	private Timestamp ccAuthorizedDate;
	private String ccAuthorizationNo;
	private String soId;
	private Double transAmount;
	private String storeDeviceNumber;
	private String additionalResponse;
	private String additionalAmount;
	private Long v1AccountNo;
	private Long v2AccountNo;
	private Long primaryAccountNumber;
	private Integer sortOrder;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	private Integer promoCodeId;
	private String promoCode;
	private String actionCode;
	private String actionCodeDesc;
	private String authorizationId;
	private String stanId;
	private String retrievalRefId;
	private Integer messageTypeId;
	private String timeStamp;
	private Long fullfillmentGroupIdNew;
	private Double partiallyAppvdAmount;
	private Long pan;
	private Double vlBalance;
	private Long originatingPan;
	private Long destinationPan;
	private String origStateCode;
	private String destStateCode;
	private boolean fetchFromListFlag=false;
	private int messageDescId;
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAuthorizationId() {
		return authorizationId;
	}

	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}

	public String getStanId() {
		return stanId;
	}

	public void setStanId(String stanId) {
		this.stanId = stanId;
	}

	public String getRetrievalRefId() {
		return retrievalRefId;
	}

	public void setRetrievalRefId(String retrievalRefId) {
		this.retrievalRefId = retrievalRefId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Timestamp getAchReconsiledDate() {
		return achReconsiledDate;
	}

	public void setAchReconsiledDate(Timestamp achReconsiledDate) {
		this.achReconsiledDate = achReconsiledDate;
	}

	public Integer getAchReconsiledInd() {
		return achReconsiledInd;
	}

	public void setAchReconsiledInd(Integer achReconsiledInd) {
		this.achReconsiledInd = achReconsiledInd;
	}

	public Integer getBusTransId() {
		return busTransId;
	}

	public void setBusTransId(Integer busTransId) {
		this.busTransId = busTransId;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryRemark() {
		return entryRemark;
	}

	public void setEntryRemark(String entryRemark) {
		this.entryRemark = entryRemark;
	}

	public Long getFullfillmentEntryId() {
		return fullfillmentEntryId;
	}

	public void setFullfillmentEntryId(Long fullfillmentEntryId) {
		this.fullfillmentEntryId = fullfillmentEntryId;
	}

	public Integer getFullfillmentEntryRuleId() {
		return fullfillmentEntryRuleId;
	}

	public void setFullfillmentEntryRuleId(Integer fullfillmentEntryRuleId) {
		this.fullfillmentEntryRuleId = fullfillmentEntryRuleId;
	}

	public Long getLedgerEntityId() {
		return ledgerEntityId;
	}

	public void setLedgerEntityId(Long ledgerEntityId) {
		this.ledgerEntityId = ledgerEntityId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Timestamp getPtdReconsiledDate() {
		return ptdReconsiledDate;
	}

	public void setPtdReconsiledDate(Timestamp ptdReconsiledDate) {
		this.ptdReconsiledDate = ptdReconsiledDate;
	}

	public Integer getPtdReconsiledInd() {
		return ptdReconsiledInd;
	}

	public void setPtdReconsiledInd(Integer ptdReconsiledInd) {
		this.ptdReconsiledInd = ptdReconsiledInd;
	}

	public Timestamp getReconsiledDate() {
		return reconsiledDate;
	}

	public void setReconsiledDate(Timestamp reconsiledDate) {
		this.reconsiledDate = reconsiledDate;
	}

	public Integer getReconsiledInd() {
		return reconsiledInd;
	}

	public void setReconsiledInd(Integer reconsiledInd) {
		this.reconsiledInd = reconsiledInd;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	public Long getFullfillmentGroupId() {
		return fullfillmentGroupId;
	}

	public Long getV1AccountNo() {
		return v1AccountNo;
	}

	public void setV1AccountNo(Long accountNo) {
		v1AccountNo = accountNo;
	}

	public Long getV2AccountNo() {
		return v2AccountNo;
	}

	public void setV2AccountNo(Long accountNo) {
		v2AccountNo = accountNo;
	}

	public void setFullfillmentGroupId(Long fullfillmentGroupId) {
		this.fullfillmentGroupId = fullfillmentGroupId;
	}

	public Integer getEntryTypeId() {
		return entryTypeId;
	}

	public void setEntryTypeId(Integer entryTypeId) {
		this.entryTypeId = entryTypeId;
	}

	public String getMessageIdentifier() {
		return messageIdentifier;
	}

	public void setMessageIdentifier(String messageIdentifier) {
		this.messageIdentifier = messageIdentifier;
	}

	public Long getPrimaryAccountNumber() {
		return primaryAccountNumber;
	}

	public void setPrimaryAccountNumber(Long primaryAccountNumber) {
		this.primaryAccountNumber = primaryAccountNumber;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getNextFullfillmentEntryId() {
		return nextFullfillmentEntryId;
	}

	public void setNextFullfillmentEntryId(Long nextFullfillmentEntryId) {
		this.nextFullfillmentEntryId = nextFullfillmentEntryId;
	}

	public String getCcAuthorizationNo() {
		return ccAuthorizationNo;
	}

	public void setCcAuthorizationNo(String ccAuthorizationNo) {
		this.ccAuthorizationNo = ccAuthorizationNo;
	}

	public Timestamp getCcAuthorizedDate() {
		return ccAuthorizedDate;
	}

	public void setCcAuthorizedDate(Timestamp ccAuthorizedDate) {
		this.ccAuthorizedDate = ccAuthorizedDate;
	}

	public Boolean getCcAuthorizedInd() {
		return ccAuthorizedInd;
	}

	public void setCcAuthorizedInd(Boolean ccAuthorizedInd) {
		this.ccAuthorizedInd = ccAuthorizedInd;
	}

	public Integer getMessageTypeId() {
		return messageTypeId;
	}

	public void setMessageTypeId(Integer messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	public Integer getPromoCodeId() {
		return promoCodeId;
	}

	public void setPromoCodeId(Integer promoCodeId) {
		this.promoCodeId = promoCodeId;
	}

	public String getStoreDeviceNumber() {
		return storeDeviceNumber;
	}

	public void setStoreDeviceNumber(String storeDeviceNumber) {
		this.storeDeviceNumber = storeDeviceNumber;
	}

	public String getAdditionalResponse() {
		return additionalResponse;
	}

	public void setAdditionalResponse(String additionalResponse) {
		this.additionalResponse = additionalResponse;
	}

	public String getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(String additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	public Long getFullfillmentGroupIdNew() {
		return fullfillmentGroupIdNew;
	}

	public void setFullfillmentGroupIdNew(Long fullfillmentGroupIdNew) {
		this.fullfillmentGroupIdNew = fullfillmentGroupIdNew;
	}

	public String getActionCodeDesc() {
		return actionCodeDesc;
	}

	public void setActionCodeDesc(String actionCodeDesc) {
		this.actionCodeDesc = actionCodeDesc;
	}

	public String getEntityTypeDesc() {
		return entityTypeDesc;
	}

	public void setEntityTypeDesc(String entityTypeDesc) {
		this.entityTypeDesc = entityTypeDesc;
	}

	public boolean isResendRequest() {
		return resendRequest;
	}

	public void setResendRequest(boolean resendRequest) {
		this.resendRequest = resendRequest;
	}

	public Double getPartiallyAppvdAmount() {
		return partiallyAppvdAmount;
	}

	public void setPartiallyAppvdAmount(Double partiallyAppvdAmount) {
		this.partiallyAppvdAmount = partiallyAppvdAmount;
	}

	public Long getFullfillmentEntryIdOrig() {
		return fullfillmentEntryIdOrig;
	}

	public void setFullfillmentEntryIdOrig(Long fullfillmentEntryIdOrig) {
		this.fullfillmentEntryIdOrig = fullfillmentEntryIdOrig;
	}

	public Long getPan() {
		return pan;
	}

	public void setPan(Long pan) {
		this.pan = pan;
	}

	public Double getVlBalance() {
		return vlBalance;
	}

	public void setVlBalance(Double vlBalance) {
		this.vlBalance = vlBalance;
	}

	/**
	 * @param resendRequest
	 * @param messageIdentifier
	 * @param fullfillmentGroupId
	 * @param fullfillmentEntryIdOrig
	 * @param fullfillmentEntryId
	 * @param nextFullfillmentEntryId
	 * @param busTransId
	 * @param fullfillmentEntryRuleId
	 * @param ledgerEntityId
	 * @param entityTypeId
	 * @param entityTypeDesc
	 * @param entryTypeId
	 * @param entryDate
	 * @param referenceNo
	 * @param entryRemark
	 * @param reconsiledInd
	 * @param reconsiledDate
	 * @param ptdReconsiledInd
	 * @param ptdReconsiledDate
	 * @param achReconsiledInd
	 * @param achReconsiledDate
	 * @param ccAuthorizedInd
	 * @param ccAuthorizedDate
	 * @param ccAuthorizationNo
	 * @param soId
	 * @param transAmount
	 * @param storeDeviceNumber
	 * @param additionalResponse
	 * @param additionalAmount
	 * @param accountNo
	 * @param accountNo2
	 * @param primaryAccountNumber
	 * @param sortOrder
	 * @param createdDate
	 * @param modifiedDate
	 * @param modifiedBy
	 * @param promoCodeId
	 * @param promoCode
	 * @param actionCode
	 * @param actionCodeDesc
	 * @param authorizationId
	 * @param stanId
	 * @param retrievalRefId
	 * @param messageTypeId
	 * @param timeStamp
	 * @param fullfillmentGroupIdNew
	 * @param partiallyAppvdAmount
	 * @param pan
	 * @param vlBalance
	 */
	public FullfillmentEntryVO(FullfillmentEntryVO feVO) {
		super();
		this.resendRequest = feVO.isResendRequest();
		this.messageIdentifier = feVO.getMessageIdentifier();
		this.fullfillmentGroupId = feVO.getFullfillmentGroupId();
		this.fullfillmentEntryIdOrig = feVO.getFullfillmentEntryIdOrig();
		this.fullfillmentEntryId = feVO.getFullfillmentEntryId();
		this.nextFullfillmentEntryId = feVO.getNextFullfillmentEntryId();
		this.busTransId = feVO.getBusTransId();
		this.fullfillmentEntryRuleId = feVO.getFullfillmentEntryRuleId();
		this.ledgerEntityId = feVO.getLedgerEntityId();
		this.entityTypeId = feVO.getEntityTypeId();
		this.entityTypeDesc = feVO.getEntityTypeDesc();
		this.entryTypeId = feVO.getEntryTypeId();
		this.entryDate = feVO.getEntryDate();
		this.referenceNo = feVO.getReferenceNo();
		this.entryRemark = feVO.getEntryRemark();
		this.reconsiledInd = feVO.getReconsiledInd();
		this.reconsiledDate = feVO.getReconsiledDate();
		this.ptdReconsiledInd = feVO.getPtdReconsiledInd();
		this.ptdReconsiledDate = feVO.getPtdReconsiledDate();
		this.achReconsiledInd = feVO.getAchReconsiledInd();
		this.achReconsiledDate = feVO.getAchReconsiledDate();
		this.ccAuthorizedInd = feVO.getCcAuthorizedInd();
		this.ccAuthorizedDate = feVO.getCcAuthorizedDate();
		this.ccAuthorizationNo = feVO.getCcAuthorizationNo();
		this.soId = feVO.getSoId();
		this.transAmount = feVO.getTransAmount();
		this.storeDeviceNumber = feVO.getStoreDeviceNumber();
		this.additionalResponse = feVO.getAdditionalResponse();
		this.additionalAmount = feVO.getAdditionalAmount();
		this.primaryAccountNumber = feVO.getPrimaryAccountNumber();
		this.sortOrder = feVO.getSortOrder();
		this.createdDate = feVO.getCreatedDate();
		this.modifiedDate = feVO.getModifiedDate();
		this.modifiedBy = feVO.getModifiedBy();
		this.promoCodeId = feVO.getPromoCodeId();
		this.promoCode = feVO.getPromoCode();
		this.actionCode = feVO.getActionCode();
		this.actionCodeDesc = feVO.getActionCodeDesc();
		this.authorizationId = feVO.getAuthorizationId();
		this.stanId = feVO.getStanId();
		this.retrievalRefId = feVO.getRetrievalRefId();
	}
	
	public FullfillmentEntryVO() {}

	public Long getOriginatingPan() {
		return originatingPan;
	}

	public void setOriginatingPan(Long originatingPan) {
		this.originatingPan = originatingPan;
	}

	public Long getDestinationPan() {
		return destinationPan;
	}

	public void setDestinationPan(Long destinationPan) {
		this.destinationPan = destinationPan;
	}

	public String getOrigStateCode() {
		return origStateCode;
	}

	public void setOrigStateCode(String origStateCode) {
		this.origStateCode = origStateCode;
	}

	public String getDestStateCode() {
		return destStateCode;
	}

	public void setDestStateCode(String destStateCode) {
		this.destStateCode = destStateCode;
	}

	public boolean isFetchFromListFlag() {
		return fetchFromListFlag;
	}

	public void setFetchFromListFlag(boolean fetchFromListFlag) {
		this.fetchFromListFlag = fetchFromListFlag;
	}

	public int getMessageDescId() {
		return messageDescId;
	}

	public void setMessageDescId(int messageDescId) {
		this.messageDescId = messageDescId;
	}
}// end class SecretQuectionVO
