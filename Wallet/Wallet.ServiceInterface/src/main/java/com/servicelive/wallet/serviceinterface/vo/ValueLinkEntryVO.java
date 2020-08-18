package com.servicelive.wallet.serviceinterface.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkEntryVO.
 */
public class ValueLinkEntryVO implements Serializable, Cloneable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -6729272647297236103L;

	/** achReconsiledDate. */
	private Timestamp achReconsiledDate;

	/** achReconsiledInd. */
	private Integer achReconsiledInd;

	/** actionCode. */
	private String actionCode;

	/** actionCodeDesc. */
	private String actionCodeDesc;

	/** additionalAmount. */
	private String additionalAmount;

	/** additionalResponse. */
	private String additionalResponse;

	/** authorizationId. */
	private String authorizationId;

	/** busTransId. */
	private Long busTransId;

	/** ccAuthorizationNo. */
	private String ccAuthorizationNo;

	/** ccAuthorizedDate. */
	private Timestamp ccAuthorizedDate;

	/** ccAuthorizedInd. */
	private Boolean ccAuthorizedInd;

	/** createdDate. */
	private Timestamp createdDate;

	/** destinationPan. */
	private Long destinationPan;

	/** destStateCode. */
	private String destStateCode;

	/** entityTypeDesc. */
	private String entityTypeDesc;

	/** entityTypeId. */
	private Long entityTypeId;

	/** entryDate. */
	private Timestamp entryDate;

	/** entryRemark. */
	private String entryRemark;

	/** entryTypeId. */
	private Long entryTypeId;

	/** fetchFromListFlag. */
	private boolean fetchFromListFlag = false;

	/** fullfillmentEntryId. */
	private Long fullfillmentEntryId;

	/** fullfillmentEntryIdOrig. */
	private Long fullfillmentEntryIdOrig;

	/** fullfillmentEntryRuleId. */
	private Long fullfillmentEntryRuleId;

	/** fullfillmentGroupId. */
	private Long fullfillmentGroupId;

	/** fullfillmentGroupIdNew. */
	private Long fullfillmentGroupIdNew;

	/** ledgerEntityId. */
	private Long ledgerEntityId;

	/** originatingEntityId. */
	private Long originatingEntityId;
	
	/** messageDescId. */
	private Long messageDescId;

	/** messageIdentifier. */
	private String messageIdentifier;

	/** messageTypeId. */
	private Long messageTypeId;

	/** modifiedBy. */
	private String modifiedBy;

	/** modifiedDate. */
	private Timestamp modifiedDate;

	/** nextFullfillmentEntryId. */
	private Long nextFullfillmentEntryId;

	/** originatingPan. */
	private Long originatingPan;

	/** origStateCode. */
	private String origStateCode;

	/** pan. */
	private Long pan;

	/** partiallyAppvdAmount. */
	private Double partiallyAppvdAmount;

	/** primaryAccountNumber. */
	private Long primaryAccountNumber;

	/** promoCode. */
	private String promoCode;

	/** promoCodeId. */
	private Long promoCodeId;

	/** ptdReconsiledDate. */
	private Timestamp ptdReconsiledDate;

	/** ptdReconsiledInd. */
	private Integer ptdReconsiledInd;

	/** reconsiledDate. */
	private Timestamp reconsiledDate;

	/** reconsiledInd. */
	private Integer reconsiledInd;

	/** referenceNo. */
	private String referenceNo;

	/** resendRequest. */
	private boolean resendRequest = false;

	/** retrievalRefId. */
	private String retrievalRefId;

	/** soId. */
	private String soId;

	/** sortOrder. */
	private Long sortOrder;

	/** stanId. */
	private String stanId;

	/** storeDeviceNumber. */
	private String storeDeviceNumber;

	/** timeStamp. */
	private String timeStamp;

	/** transAmount. */
	private Double transAmount;

	/** v1AccountNo. */
	private Long v1AccountNo;

	/** v2AccountNo. */
	private Long v2AccountNo;

	/** vlBalance. */
	private Double vlBalance;

	/**
	 * ValueLinkEntryVO.
	 */
	public ValueLinkEntryVO() {
		this.defaultTimeStamp();
	}

    /** fundingTypeId. */ 	 
    private Long fundingTypeId; 

    /**
	 * ValueLinkEntryVO.
	 * 
	 * @param feVO 
	 */
	public ValueLinkEntryVO(ValueLinkEntryVO feVO) {

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
		this.defaultTimeStamp();
	}
	
	private void defaultTimeStamp() {
		Date d = new Date();
		DateFormat f = new SimpleDateFormat("yyMMyyHHmmss");
		this.setTimeStamp(f.format(d));
	}

	/**
	 * getAchReconsiledDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getAchReconsiledDate() {

		return achReconsiledDate;
	}

	/**
	 * getAchReconsiledInd.
	 * 
	 * @return Integer
	 */
	public Integer getAchReconsiledInd() {

		return achReconsiledInd;
	}

	/**
	 * getActionCode.
	 * 
	 * @return String
	 */
	public String getActionCode() {

		return actionCode;
	}

	/**
	 * getActionCodeDesc.
	 * 
	 * @return String
	 */
	public String getActionCodeDesc() {

		return actionCodeDesc;
	}

	/**
	 * getAdditionalAmount.
	 * 
	 * @return String
	 */
	public String getAdditionalAmount() {

		return additionalAmount;
	}

	/**
	 * getAdditionalResponse.
	 * 
	 * @return String
	 */
	public String getAdditionalResponse() {

		return additionalResponse;
	}

	/**
	 * getAuthorizationId.
	 * 
	 * @return String
	 */
	public String getAuthorizationId() {

		return authorizationId;
	}

	/**
	 * getBusTransId.
	 * 
	 * @return Long
	 */
	public Long getBusTransId() {

		return busTransId;
	}

	/**
	 * getCcAuthorizationNo.
	 * 
	 * @return String
	 */
	public String getCcAuthorizationNo() {

		return ccAuthorizationNo;
	}

	/**
	 * getCcAuthorizedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getCcAuthorizedDate() {

		return ccAuthorizedDate;
	}

	/**
	 * getCcAuthorizedInd.
	 * 
	 * @return Boolean
	 */
	public Boolean getCcAuthorizedInd() {

		return ccAuthorizedInd;
	}

	/**
	 * getCreatedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getCreatedDate() {

		return createdDate;
	}

	/**
	 * getDestinationPan.
	 * 
	 * @return Long
	 */
	public Long getDestinationPan() {

		return destinationPan;
	}

	/**
	 * getDestStateCode.
	 * 
	 * @return String
	 */
	public String getDestStateCode() {

		return destStateCode;
	}

	/**
	 * getEntityTypeDesc.
	 * 
	 * @return String
	 */
	public String getEntityTypeDesc() {

		return entityTypeDesc;
	}

	/**
	 * getEntityTypeId.
	 * 
	 * @return Long
	 */
	public Long getEntityTypeId() {

		return entityTypeId;
	}

	/**
	 * getEntryDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getEntryDate() {

		return entryDate;
	}

	/**
	 * getEntryRemark.
	 * 
	 * @return String
	 */
	public String getEntryRemark() {

		return entryRemark;
	}

	/**
	 * getEntryTypeId.
	 * 
	 * @return Long
	 */
	public Long getEntryTypeId() {

		return entryTypeId;
	}

	/**
	 * getFullfillmentEntryId.
	 * 
	 * @return Long
	 */
	public Long getFullfillmentEntryId() {

		return fullfillmentEntryId;
	}

	/**
	 * getFullfillmentEntryIdOrig.
	 * 
	 * @return Long
	 */
	public Long getFullfillmentEntryIdOrig() {

		return fullfillmentEntryIdOrig;
	}

	/**
	 * getFullfillmentEntryRuleId.
	 * 
	 * @return Long
	 */
	public Long getFullfillmentEntryRuleId() {

		return fullfillmentEntryRuleId;
	}

	/**
	 * getFullfillmentGroupId.
	 * 
	 * @return Long
	 */
	public Long getFullfillmentGroupId() {

		return fullfillmentGroupId;
	}

	/**
	 * getFullfillmentGroupIdNew.
	 * 
	 * @return Long
	 */
	public Long getFullfillmentGroupIdNew() {

		return fullfillmentGroupIdNew;
	}


	/**
	 * getLedgerEntityId.
	 * 
	 * @return Long
	 */
	public Long getLedgerEntityId() {

		return ledgerEntityId;
	}

	/**
	 * getMessageDescId.
	 * 
	 * @return Long
	 */
	public Long getMessageDescId() {

		return messageDescId;
	}

	/**
	 * getMessageIdentifier.
	 * 
	 * @return String
	 */
	public String getMessageIdentifier() {

		return messageIdentifier;
	}

	/**
	 * getMessageTypeId.
	 * 
	 * @return Long
	 */
	public Long getMessageTypeId() {

		return messageTypeId;
	}

	/**
	 * getModifiedBy.
	 * 
	 * @return String
	 */
	public String getModifiedBy() {

		return modifiedBy;
	}

	/**
	 * getModifiedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getModifiedDate() {

		return modifiedDate;
	}

	/**
	 * getNextFullfillmentEntryId.
	 * 
	 * @return Long
	 */
	public Long getNextFullfillmentEntryId() {

		return nextFullfillmentEntryId;
	}

	/**
	 * getOriginatingPan.
	 * 
	 * @return Long
	 */
	public Long getOriginatingPan() {

		return originatingPan;
	}

	/**
	 * getOrigStateCode.
	 * 
	 * @return String
	 */
	public String getOrigStateCode() {

		return origStateCode;
	}

	/**
	 * getPan.
	 * 
	 * @return Long
	 */
	public Long getPan() {

		return pan;
	}

	/**
	 * getPartiallyAppvdAmount.
	 * 
	 * @return Double
	 */
	public Double getPartiallyAppvdAmount() {

		return partiallyAppvdAmount;
	}

	/**
	 * getPrimaryAccountNumber.
	 * 
	 * @return Long
	 */
	public Long getPrimaryAccountNumber() {

		return primaryAccountNumber;
	}

	/**
	 * getPromoCode.
	 * 
	 * @return String
	 */
	public String getPromoCode() {

		return promoCode;
	}

	/**
	 * getPromoCodeId.
	 * 
	 * @return Long
	 */
	public Long getPromoCodeId() {

		return promoCodeId;
	}

	/**
	 * getPtdReconsiledDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getPtdReconsiledDate() {

		return ptdReconsiledDate;
	}

	/**
	 * getPtdReconsiledInd.
	 * 
	 * @return Integer
	 */
	public Integer getPtdReconsiledInd() {

		return ptdReconsiledInd;
	}

	/**
	 * getReconsiledDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getReconsiledDate() {

		return reconsiledDate;
	}

	/**
	 * getReconsiledInd.
	 * 
	 * @return Integer
	 */
	public Integer getReconsiledInd() {

		return reconsiledInd;
	}

	/**
	 * getReferenceNo.
	 * 
	 * @return String
	 */
	public String getReferenceNo() {

		return referenceNo;
	}

	/**
	 * getRetrievalRefId.
	 * 
	 * @return String
	 */
	public String getRetrievalRefId() {

		return retrievalRefId;
	}

	/**
	 * getSoId.
	 * 
	 * @return String
	 */
	public String getSoId() {

		return soId;
	}

	/**
	 * getSortOrder.
	 * 
	 * @return Long
	 */
	public Long getSortOrder() {

		return sortOrder;
	}

	/**
	 * getStanId.
	 * 
	 * @return String
	 */
	public String getStanId() {

		return stanId;
	}

	/**
	 * getStoreDeviceNumber.
	 * 
	 * @return String
	 */
	public String getStoreDeviceNumber() {

		return storeDeviceNumber;
	}

	/**
	 * getTimeStamp.
	 * 
	 * @return String
	 */
	public String getTimeStamp() {

		return timeStamp;
	}

	/**
	 * getTransAmount.
	 * 
	 * @return Double
	 */
	public Double getTransAmount() {

		return transAmount;
	}

	/**
	 * getV1AccountNo.
	 * 
	 * @return Long
	 */
	public Long getV1AccountNo() {

		return v1AccountNo;
	}

	/**
	 * getV2AccountNo.
	 * 
	 * @return Long
	 */
	public Long getV2AccountNo() {

		return v2AccountNo;
	}

	/**
	 * getVlBalance.
	 * 
	 * @return Double
	 */
	public Double getVlBalance() {

		return vlBalance;
	}

	/**
	 * isFetchFromListFlag.
	 * 
	 * @return boolean
	 */
	public boolean isFetchFromListFlag() {

		return fetchFromListFlag;
	}

	/**
	 * isResendRequest.
	 * 
	 * @return boolean
	 */
	public boolean isResendRequest() {

		return resendRequest;
	}

	/**
	 * setAchReconsiledDate.
	 * 
	 * @param achReconsiledDate 
	 * 
	 * @return void
	 */
	public void setAchReconsiledDate(Timestamp achReconsiledDate) {

		this.achReconsiledDate = achReconsiledDate;
	}

	/**
	 * setAchReconsiledInd.
	 * 
	 * @param achReconsiledInd 
	 * 
	 * @return void
	 */
	public void setAchReconsiledInd(Integer achReconsiledInd) {

		this.achReconsiledInd = achReconsiledInd;
	}

	/**
	 * setActionCode.
	 * 
	 * @param actionCode 
	 * 
	 * @return void
	 */
	public void setActionCode(String actionCode) {

		this.actionCode = actionCode;
	}

	/**
	 * setActionCodeDesc.
	 * 
	 * @param actionCodeDesc 
	 * 
	 * @return void
	 */
	public void setActionCodeDesc(String actionCodeDesc) {

		this.actionCodeDesc = actionCodeDesc;
	}

	/**
	 * setAdditionalAmount.
	 * 
	 * @param additionalAmount 
	 * 
	 * @return void
	 */
	public void setAdditionalAmount(String additionalAmount) {

		this.additionalAmount = additionalAmount;
	}

	/**
	 * setAdditionalResponse.
	 * 
	 * @param additionalResponse 
	 * 
	 * @return void
	 */
	public void setAdditionalResponse(String additionalResponse) {

		this.additionalResponse = additionalResponse;
	}

	/**
	 * setAuthorizationId.
	 * 
	 * @param authorizationId 
	 * 
	 * @return void
	 */
	public void setAuthorizationId(String authorizationId) {

		this.authorizationId = authorizationId;
	}

	/**
	 * setBusTransId.
	 * 
	 * @param busTransId 
	 * 
	 * @return void
	 */
	public void setBusTransId(Long busTransId) {

		this.busTransId = busTransId;
	}

	/**
	 * setCcAuthorizationNo.
	 * 
	 * @param ccAuthorizationNo 
	 * 
	 * @return void
	 */
	public void setCcAuthorizationNo(String ccAuthorizationNo) {

		this.ccAuthorizationNo = ccAuthorizationNo;
	}

	/**
	 * setCcAuthorizedDate.
	 * 
	 * @param ccAuthorizedDate 
	 * 
	 * @return void
	 */
	public void setCcAuthorizedDate(Timestamp ccAuthorizedDate) {

		this.ccAuthorizedDate = ccAuthorizedDate;
	}

	/**
	 * setCcAuthorizedInd.
	 * 
	 * @param ccAuthorizedInd 
	 * 
	 * @return void
	 */
	public void setCcAuthorizedInd(Boolean ccAuthorizedInd) {

		this.ccAuthorizedInd = ccAuthorizedInd;
	}

	/**
	 * setCreatedDate.
	 * 
	 * @param createdDate 
	 * 
	 * @return void
	 */
	public void setCreatedDate(Timestamp createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * setDestinationPan.
	 * 
	 * @param destinationPan 
	 * 
	 * @return void
	 */
	public void setDestinationPan(Long destinationPan) {

		this.destinationPan = destinationPan;
	}

	/**
	 * setDestStateCode.
	 * 
	 * @param destStateCode 
	 * 
	 * @return void
	 */
	public void setDestStateCode(String destStateCode) {

		this.destStateCode = destStateCode;
	}

	/**
	 * setEntityTypeDesc.
	 * 
	 * @param entityTypeDesc 
	 * 
	 * @return void
	 */
	public void setEntityTypeDesc(String entityTypeDesc) {

		this.entityTypeDesc = entityTypeDesc;
	}

	/**
	 * setEntityTypeId.
	 * 
	 * @param entityTypeId 
	 * 
	 * @return void
	 */
	public void setEntityTypeId(Long entityTypeId) {

		this.entityTypeId = entityTypeId;
	}

	/**
	 * setEntryDate.
	 * 
	 * @param entryDate 
	 * 
	 * @return void
	 */
	public void setEntryDate(Timestamp entryDate) {

		this.entryDate = entryDate;
	}

	/**
	 * setEntryRemark.
	 * 
	 * @param entryRemark 
	 * 
	 * @return void
	 */
	public void setEntryRemark(String entryRemark) {

		this.entryRemark = entryRemark;
	}

	/**
	 * setEntryTypeId.
	 * 
	 * @param entryTypeId 
	 * 
	 * @return void
	 */
	public void setEntryTypeId(Long entryTypeId) {

		this.entryTypeId = entryTypeId;
	}

	/**
	 * setFetchFromListFlag.
	 * 
	 * @param fetchFromListFlag 
	 * 
	 * @return void
	 */
	public void setFetchFromListFlag(boolean fetchFromListFlag) {

		this.fetchFromListFlag = fetchFromListFlag;
	}

	/**
	 * setFullfillmentEntryId.
	 * 
	 * @param fullfillmentEntryId 
	 * 
	 * @return void
	 */
	public void setFullfillmentEntryId(Long fullfillmentEntryId) {

		this.fullfillmentEntryId = fullfillmentEntryId;
	}

	/**
	 * setFullfillmentEntryIdOrig.
	 * 
	 * @param fullfillmentEntryIdOrig 
	 * 
	 * @return void
	 */
	public void setFullfillmentEntryIdOrig(Long fullfillmentEntryIdOrig) {

		this.fullfillmentEntryIdOrig = fullfillmentEntryIdOrig;
	}

	/**
	 * setFullfillmentEntryRuleId.
	 * 
	 * @param fullfillmentEntryRuleId 
	 * 
	 * @return void
	 */
	public void setFullfillmentEntryRuleId(Long fullfillmentEntryRuleId) {

		this.fullfillmentEntryRuleId = fullfillmentEntryRuleId;
	}

	/**
	 * setFullfillmentGroupId.
	 * 
	 * @param fullfillmentGroupId 
	 * 
	 * @return void
	 */
	public void setFullfillmentGroupId(Long fullfillmentGroupId) {

		this.fullfillmentGroupId = fullfillmentGroupId;
	}

	/**
	 * setFullfillmentGroupIdNew.
	 * 
	 * @param fullfillmentGroupIdNew 
	 * 
	 * @return void
	 */
	public void setFullfillmentGroupIdNew(Long fullfillmentGroupIdNew) {

		this.fullfillmentGroupIdNew = fullfillmentGroupIdNew;
	}

	/**
	 * setLedgerEntityId.
	 * 
	 * @param ledgerEntityId 
	 * 
	 * @return void
	 */
	public void setLedgerEntityId(Long ledgerEntityId) {

		this.ledgerEntityId = ledgerEntityId;
	}

	/**
	 * setMessageDescId.
	 * 
	 * @param messageDescId 
	 * 
	 * @return void
	 */
	public void setMessageDescId(Long messageDescId) {

		this.messageDescId = messageDescId;
	}

	/**
	 * setMessageIdentifier.
	 * 
	 * @param messageIdentifier 
	 * 
	 * @return void
	 */
	public void setMessageIdentifier(String messageIdentifier) {

		this.messageIdentifier = messageIdentifier;
	}

	/**
	 * setMessageTypeId.
	 * 
	 * @param messageTypeId 
	 * 
	 * @return void
	 */
	public void setMessageTypeId(Long messageTypeId) {

		this.messageTypeId = messageTypeId;
	}

	/**
	 * setModifiedBy.
	 * 
	 * @param modifiedBy 
	 * 
	 * @return void
	 */
	public void setModifiedBy(String modifiedBy) {

		this.modifiedBy = modifiedBy;
	}

	/**
	 * setModifiedDate.
	 * 
	 * @param modifiedDate 
	 * 
	 * @return void
	 */
	public void setModifiedDate(Timestamp modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/**
	 * setNextFullfillmentEntryId.
	 * 
	 * @param nextFullfillmentEntryId 
	 * 
	 * @return void
	 */
	public void setNextFullfillmentEntryId(Long nextFullfillmentEntryId) {

		this.nextFullfillmentEntryId = nextFullfillmentEntryId;
	}

	/**
	 * setOriginatingPan.
	 * 
	 * @param originatingPan 
	 * 
	 * @return void
	 */
	public void setOriginatingPan(Long originatingPan) {

		this.originatingPan = originatingPan;
	}

	/**
	 * setOrigStateCode.
	 * 
	 * @param origStateCode 
	 * 
	 * @return void
	 */
	public void setOrigStateCode(String origStateCode) {

		this.origStateCode = origStateCode;
	}

	/**
	 * setPan.
	 * 
	 * @param pan 
	 * 
	 * @return void
	 */
	public void setPan(Long pan) {

		this.pan = pan;
	}

	/**
	 * setPartiallyAppvdAmount.
	 * 
	 * @param partiallyAppvdAmount 
	 * 
	 * @return void
	 */
	public void setPartiallyAppvdAmount(Double partiallyAppvdAmount) {

		this.partiallyAppvdAmount = partiallyAppvdAmount;
	}

	/**
	 * setPrimaryAccountNumber.
	 * 
	 * @param primaryAccountNumber 
	 * 
	 * @return void
	 */
	public void setPrimaryAccountNumber(Long primaryAccountNumber) {

		this.primaryAccountNumber = primaryAccountNumber;
	}

	/**
	 * setPromoCode.
	 * 
	 * @param promoCode 
	 * 
	 * @return void
	 */
	public void setPromoCode(String promoCode) {

		this.promoCode = promoCode;
	}

	/**
	 * setPromoCodeId.
	 * 
	 * @param promoCodeId 
	 * 
	 * @return void
	 */
	public void setPromoCodeId(Long promoCodeId) {

		this.promoCodeId = promoCodeId;
	}

	/**
	 * setPtdReconsiledDate.
	 * 
	 * @param ptdReconsiledDate 
	 * 
	 * @return void
	 */
	public void setPtdReconsiledDate(Timestamp ptdReconsiledDate) {

		this.ptdReconsiledDate = ptdReconsiledDate;
	}

	/**
	 * setPtdReconsiledInd.
	 * 
	 * @param ptdReconsiledInd 
	 * 
	 * @return void
	 */
	public void setPtdReconsiledInd(Integer ptdReconsiledInd) {

		this.ptdReconsiledInd = ptdReconsiledInd;
	}

	/**
	 * setReconsiledDate.
	 * 
	 * @param reconsiledDate 
	 * 
	 * @return void
	 */
	public void setReconsiledDate(Timestamp reconsiledDate) {

		this.reconsiledDate = reconsiledDate;
	}

	/**
	 * setReconsiledInd.
	 * 
	 * @param reconsiledInd 
	 * 
	 * @return void
	 */
	public void setReconsiledInd(Integer reconsiledInd) {

		this.reconsiledInd = reconsiledInd;
	}

	/**
	 * setReferenceNo.
	 * 
	 * @param referenceNo 
	 * 
	 * @return void
	 */
	public void setReferenceNo(String referenceNo) {

		this.referenceNo = referenceNo;
	}

	/**
	 * setResendRequest.
	 * 
	 * @param resendRequest 
	 * 
	 * @return void
	 */
	public void setResendRequest(boolean resendRequest) {

		this.resendRequest = resendRequest;
	}

	/**
	 * setRetrievalRefId.
	 * 
	 * @param retrievalRefId 
	 * 
	 * @return void
	 */
	public void setRetrievalRefId(String retrievalRefId) {

		this.retrievalRefId = retrievalRefId;
	}

	/**
	 * setSoId.
	 * 
	 * @param soId 
	 * 
	 * @return void
	 */
	public void setSoId(String soId) {

		this.soId = soId;
	}

	/**
	 * setSortOrder.
	 * 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	public void setSortOrder(Long sortOrder) {

		this.sortOrder = sortOrder;
	}

	/**
	 * setStanId.
	 * 
	 * @param stanId 
	 * 
	 * @return void
	 */
	public void setStanId(String stanId) {

		this.stanId = stanId;
	}

	/**
	 * setStoreDeviceNumber.
	 * 
	 * @param storeDeviceNumber 
	 * 
	 * @return void
	 */
	public void setStoreDeviceNumber(String storeDeviceNumber) {

		this.storeDeviceNumber = storeDeviceNumber;
	}

	/**
	 * setTimeStamp.
	 * 
	 * @param timeStamp 
	 * 
	 * @return void
	 */
	public void setTimeStamp(String timeStamp) {

		this.timeStamp = timeStamp;
	}

	/**
	 * setTransAmount.
	 * 
	 * @param transAmount 
	 * 
	 * @return void
	 */
	public void setTransAmount(Double transAmount) {

		this.transAmount = transAmount;
	}

	/**
	 * setV1AccountNo.
	 * 
	 * @param accountNo 
	 * 
	 * @return void
	 */
	public void setV1AccountNo(Long accountNo) {

		v1AccountNo = accountNo;
	}

	/**
	 * setV2AccountNo.
	 * 
	 * @param accountNo 
	 * 
	 * @return void
	 */
	public void setV2AccountNo(Long accountNo) {

		v2AccountNo = accountNo;
	}

	/**
	 * setVlBalance.
	 * 
	 * @param vlBalance 
	 * 
	 * @return void
	 */
	public void setVlBalance(Double vlBalance) {

		this.vlBalance = vlBalance;
	}

	public ValueLinkEntryVO clone() {
		try {
			return (ValueLinkEntryVO)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setFundingTypeId(Long fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	public Long getFundingTypeId() {
		return fundingTypeId;
	}
	
	public Long getOriginatingEntityId() {
		return originatingEntityId;
	}

	public void setOriginatingEntityId(Long originatingEntityId) {
		this.originatingEntityId = originatingEntityId;
	}
}// end class SecretQuectionVO
