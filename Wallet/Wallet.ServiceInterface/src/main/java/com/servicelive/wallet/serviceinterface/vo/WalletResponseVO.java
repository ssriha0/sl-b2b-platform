package com.servicelive.wallet.serviceinterface.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.vo.SLCreditCardVO;

/**
 * Class WalletResponseVO.
 */
@XmlRootElement()
@XmlSeeAlso(ReceiptVO.class)
public class WalletResponseVO {

	/** creditCard. */
	private SLCreditCardVO creditCard;

	/** errorMessages. */
	private List<String> errorMessages = new ArrayList<String>();

	/** ledgerEntryId. */
	private Long ledgerEntryId;

	/** transactionId. */
	private Long transactionId;

    private Long buyerTransactionId;

    private Long providerTransactionId;
	
	private Object result;
	
	private String messageId;

	private String jmsMessageId;

	/**
	 * addErrorMessage.
	 * 
	 * @param errorMessage 
	 * 
	 * @return void
	 */
	public void addErrorMessage(String errorMessage) {

		errorMessages.add(errorMessage);
	}

	/**
	 * clearErrorMessages.
	 * 
	 * @return void
	 */
	public void clearErrorMessages() {

		errorMessages.clear();
	}

	/**
	 * getCreditCard.
	 * 
	 * @return CreditCardVO
	 */
	public SLCreditCardVO getCreditCard() {

		return creditCard;
	}

	/**
	 * getErrorMessages.
	 * 
	 * @return List<String>
	 */
	public List<String> getErrorMessages() {

		return errorMessages;
	}

	/**
	 * getLedgerEntryId.
	 * 
	 * @return Long
	 */
	public Long getLedgerEntryId() {

		return ledgerEntryId;
	}

	/**
	 * getTransactionId.
	 * 
	 * @return Long
	 */
	public Long getTransactionId() {

		return transactionId;
	}

	/**
	 * isError.
	 * 
	 * @return boolean
	 */
	public boolean isError() {

		return (errorMessages.size() > 0);
	}

	/**
	 * setCreditCard.
	 * 
	 * @param creditCard 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setCreditCard(SLCreditCardVO creditCard) {

		this.creditCard = creditCard;
	}

	/**
	 * setLedgerEntryId.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return void
	 */
	@XmlTransient()
	public void setLedgerEntryId(Long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	/**
	 * setTransactionId.
	 * 
	 * @param transactionId 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setTransactionId(Long transactionId) {

		this.transactionId = transactionId;
	}

	/**
	 * validateCreditCard.
	 * 
	 * @return void
	 */
	public void validateCreditCardResponse() {
		if (getCreditCard() == null) return;

		if (StringUtils.isNotBlank(getCreditCard().getAnsiResponseCode())) {
			addErrorMessage(getCreditCard().getAnsiResponseCode());
		}
		if (StringUtils.isNotBlank(getCreditCard().getCidResponseCode())) {
			addErrorMessage(getCreditCard().getCidResponseCode());
		}
	}
    
	/**
	 * validateCreditCard.
	 * @param response 
	 * 
	 * @return void
	 */
	public void validateHSCreditCardResponse() {
		if (getCreditCard() == null) {
			addErrorMessage(CommonConstants.ERROR_MESSAGE);
		}
        if(StringUtils.isNotBlank(getCreditCard().getAuthorizationCode())){
        	if(!getCreditCard().isAuthorized()){
        		addErrorMessage(CommonConstants.ERROR_MESSAGE);
        	}
        }else{
        	addErrorMessage(CommonConstants.ERROR_MESSAGE);
        }
	}
	
	@XmlElementWrapper()
	@XmlElement(name = "error")
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	@XmlElement()
	public void setResult(Object result) {
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

	@XmlElement()
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}

	
	@XmlElement()
	public void setJmsMessageId(String jmsMessageId) {
		this.jmsMessageId = jmsMessageId;
	}
	public String getJmsMessageId() {
		return this.jmsMessageId;
	}

    public Long getBuyerTransactionId() {
        return buyerTransactionId;
    }

    @XmlElement()
    public void setBuyerTransactionId(Long buyerTransactionId) {
        this.buyerTransactionId = buyerTransactionId;
    }

    public Long getProviderTransactionId() {
        return providerTransactionId;
    }

    @XmlElement()
    public void setProviderTransactionId(Long providerTransactionId) {
        this.providerTransactionId = providerTransactionId;
    }

	@Override
	public String toString() {
		return "WalletResponseVO [errorMessages=" + errorMessages + ", ledgerEntryId=" + ledgerEntryId + ", transactionId=" + transactionId
				+ ", buyerTransactionId=" + buyerTransactionId + ", providerTransactionId=" + providerTransactionId + ", result=" + result
				+ ", messageId=" + messageId + ", jmsMessageId=" + jmsMessageId + "]";
	}
}
