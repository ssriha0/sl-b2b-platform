package com.servicelive.wallet.serviceinterface.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class WalletMessageVO implements Serializable {

	private static final long serialVersionUID = 1874011623169807248L;

	public enum MethodName{
		CANCEL_SERVICE_ORDER,
		CANCEL_SERVICE_ORDER_WITHOUT_PENALTY,
		CLOSE_SERVICE_ORDER,
		DECREASE_PROJECT_SPEND_LIMIT,
		INCREASE_PROJECT_SPEND_LIMIT,
		INCREASE_PROJECT_SPEND_COMPLETION,
		POST_SERVICE_ORDER,
		VOID_SERVICE_ORDER,
		ADMIN_CREDIT_TO_BUYER, 
		ADMIN_CREDIT_TO_PROVIDER, 
		ADMIN_DEBIT_FROM_BUYER,
		ADMIN_ESCHEATMENT_FROM_BUYER,
		ADMIN_DEBIT_FROM_PROVIDER, 
		ADMIN_ESCHEATMENT_FROM_PROVIDER,
		DEPOSIT_BUYER_FUNDS_WITH_CASH, 
		DEPOSIT_BUYER_FUNDS_WITH_CREDIT_CARD, 
		DEPOSIT_BUYER_FUNDS_WITH_INSTANT_ACH, 
		DEPOSIT_OPERATION_FUNDS, 
		WITHDRAW_BUYER_CASH_FUNDS, 
		WITHDRAW_BUYER_CREDIT_CARD_FUNDS, 
		WITHDRAW_OPERATION_FUNDS, 
		WITHDRAW_PROVIDER_FUNDS,
		WITHDRAW_PROVIDER_FUNDS_REVERSAL,
		WITHDRAW_BUYER_DEBIT_REVERSAL;
	}
	
	private MethodName method;
	private String messageId;
	
	private WalletVO request;
	
	public WalletMessageVO(){
		messageId = UUID.randomUUID().toString();
	}
	
	public WalletMessageVO(MethodName method, WalletVO request) {
		super();
		this.method = method;
		this.request = request;
		messageId = UUID.randomUUID().toString();
	}
	
	public MethodName getMethod() {
		return method;
	}
	
	public WalletVO getRequest() {
		return request;
	}
	
	@XmlElement()
	public void setMethod(MethodName method) {
		this.method = method;
	}

	@XmlElement()
	public void setRequest(WalletVO request) {
		this.request = request;
	}
	
	@XmlElement()
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public String getMessageId() {
		return messageId;
	}

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
