package com.servicelive.orderfulfillment.command;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class GetCreditCardDepositInfoCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		WalletResponseVO responseVO = walletGateway.getCreditCardInformation(Long.valueOf(so.getAccountId()));
		SLCreditCardVO cc = responseVO.getCreditCard();
		
		String cardNumber = "NA";
		String cardTypeName = "NA";
		if (cc != null) {
			cardNumber = maskCardNumber(cc.getCardNo());
			cardTypeName = getCardName(cc.getCardTypeId());
		}		
		
		processVariables.put(ProcessVariableUtil.PVKEY_CREDIT_CARD_TYPE, cardTypeName);
		processVariables.put(ProcessVariableUtil.PVKEY_CREDIT_CARD_NUMBER, cardNumber);
		
		// TransactionID and Transaction amount
		Object transactionIdObject = processVariables.get(ProcessVariableUtil.PVKEY_FUNDS_CONFIRMED_TX_ID);
		if (transactionIdObject != null) {
			String transactionIdString = (String) transactionIdObject;
			Long transactionId = Long.parseLong(transactionIdString);
			Double amount = walletGateway.getTransactionAmountById(transactionId);
			NumberFormat formatter = new DecimalFormat("#0.00");
			processVariables.put(ProcessVariableUtil.PVKEY_TRANSACTION_AMOUNT, formatter.format(amount));
		}
	}
	
	private String maskCardNumber(String cardNumber) {
		if (cardNumber == null) {
			return StringUtils.EMPTY;
		}
		if (cardNumber.length() <= 4) {
			return "****";
		}
		
		StringBuilder maskedCardNumber = new StringBuilder();
		for (int i = 0; i < cardNumber.length() - 4; i++) {
			maskedCardNumber.append("*");		
		}
		maskedCardNumber.append(cardNumber.substring(cardNumber.length() - 4));
		return maskedCardNumber.toString();
	}
	
	private String getCardName(Long cardTypeId) {
		if (cardTypeId == null)
			return "";
		
		switch (cardTypeId.intValue()) {
		case 0:
			return "Sears";
		case 3:
			return "Sears Commercial";
		case 4:
			return "Sears Master Card";
		case 6:
			return "Visa";
		case 7:
			return "Mastercard";
		case 8:
			return "American Express";
		default:
			return "";
		}
	}

}
