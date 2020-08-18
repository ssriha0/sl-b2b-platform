package com.servicelive.wallet.valuelink.sharp.iso.vo;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.servicelive.common.CommonConstants;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;


// TODO: Auto-generated Javadoc
/**
 * Class IsoMessageVO.
 */
public class IsoMessageVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -6729272647297236103L;


	/**
	 * @param valueLinkEntryVO
	 */
	public IsoMessageVO(ValueLinkEntryVO valueLinkEntryVO) {
		BeanUtils.copyProperties(valueLinkEntryVO, this);
	}
	
	public IsoMessageVO(){
		//do nothing
	}
	/**
	 * getMessageIdentifier.
	 * 
	 * @param messageTypeId 
	 * 
	 * @return String
	 * 
	 * @throws Exception 
	 */
	@Deprecated
	public static String getMessageIdentifier(long messageTypeId) throws Exception {

		switch ((int) messageTypeId) {
		case CommonConstants.MESSAGE_TYPE_ACTIVATION:
		case CommonConstants.MESSAGE_TYPE_RELOAD:
			return CommonConstants.ACTIVATION_RELOAD_REQUEST;
		case CommonConstants.MESSAGE_TYPE_REDEMPTION:
			return CommonConstants.REDEMPTION_REQUEST;
		case CommonConstants.MESSAGE_TYPE_BALANCE_ENQ:
			return CommonConstants.BALANCE_ENQUIRY_REQUEST;
		case CommonConstants.MESSAGE_TYPE_HEARTBEAT:
			return CommonConstants.SHARP_HEARTBEAT_REQUEST;
		case CommonConstants.MESSAGE_TYPE_VOID:
			return CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID;
		default:
			return null;
		}
	}

	/** actionCode. */
	private String actionCode;

	/** additionalAmount. */
	private String additionalAmount;

	/** additionalResponse. */
	private String additionalResponse;

	/** authorizationId. */
	private String authorizationId;

	/** fullfillmentEntryId. */
	private Long fullfillmentEntryId;

	/** fullfillmentGroupId. */
	private Long fullfillmentGroupId;

	/** ledgerEntityId. */
	private Long ledgerEntityId;

	/** messageIdentifier. */
	private String messageIdentifier;

	/** messageTypeId. */
	private Long messageTypeId;

	/** partiallyAppvdAmount. */
	private Double partiallyAppvdAmount;

	/** primaryAccountNumber. */
	private Long primaryAccountNumber;

	/** promoCode. */
	private String promoCode;

	/** referenceNo. */
	private String referenceNo;

	/** resendRequest. */
	private boolean resendRequest = false;

	/** retrievalRefId. */
	private String retrievalRefId;

	/** stanId. */
	private String stanId;

	/** storeDeviceNumber. */
	private String storeDeviceNumber;

	/** storeNo. */
	private String storeNo;

	/** timeStamp. */
	private String timeStamp;

	/** transAmount. */
	private Double transAmount;

	/**
	 * getActionCode.
	 * 
	 * @return String
	 */
	public String getActionCode() {

		return actionCode;
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
	 * getFullfillmentEntryId.
	 * 
	 * @return Long
	 */
	public Long getFullfillmentEntryId() {

		return fullfillmentEntryId;
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
	 * getLedgerEntityId.
	 * 
	 * @return Long
	 */
	public Long getLedgerEntityId() {

		return ledgerEntityId;
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
	 * getStoreNo.
	 * 
	 * @return String
	 */
	public String getStoreNo() {

		return storeNo;
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
	 * isBalanceInquiryResponse.
	 * 
	 * @return boolean
	 */
	public boolean isBalanceInquiryResponse() {

		//return (this.getMessageIdentifier() != null && this.getFullfillmentEntryId() != null && this.getFullfillmentEntryId() == 2345L);
		return (this.getMessageIdentifier() != null && this.getFullfillmentEntryId() != null && this.getMessageIdentifier().equals(CommonConstants.BALANCE_ENQUIRY_RESPONSE));
	}

	/**
	 * isHeartbeatResponse.
	 * 
	 * @return boolean
	 */
	public boolean isHeartbeatResponse() {
		
		return (this.getMessageIdentifier() != null &&
				this.getMessageIdentifier() == CommonConstants.SHARP_HEARTBEAT_RESPONSE);
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
	 * setStoreNo.
	 * 
	 * @param storeNo 
	 * 
	 * @return void
	 */
	public void setStoreNo(String storeNo) {

		this.storeNo = storeNo;
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

}
