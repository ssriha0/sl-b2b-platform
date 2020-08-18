package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class AddBuyerFundCmd extends SOCommand {
	protected final Logger logger = Logger.getLogger(getClass());
	@Override
	public void execute(Map<String, Object> processVariables) {
		
		ServiceOrder so = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
		Double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		BigDecimal amount = so.getTotalSpendLimit().subtract(new BigDecimal(availableBalance));
		if (amount.doubleValue() > 0){
			throw new ServiceOrderException("Your wallet does not have enough funding to cover this new combined maximum");
//			if (null != so.getAccountId()){
//				WalletResponseVO resp = walletGateway.addFundsToBuyerAccount(userName, so.getBuyerId(), so.getAccountId().longValue(), buyerState, so, amount);
//				String messageId = resp.getMessageId();
//				String jmsMessageId = resp.getJmsMessageId();
//				processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
//				processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
//			}else if(null == so.getAccountId()){
//				//Method to fetch accountId from DB
//				Long accountId=walletGateway.getAccountId(so.getBuyerId().longValue());
//				if(null!= accountId ){
//					logger.info("AccountId is:"+ accountId);
//					so.setAccountId(accountId.intValue());
//					WalletResponseVO resp = walletGateway.addFundsToBuyerAccount(userName, so.getBuyerId(), so.getAccountId().longValue(), buyerState, so, amount);
//					String messageId = resp.getMessageId();
//					String jmsMessageId = resp.getJmsMessageId();
//					processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
//					processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
//				}
//			 }else{
//				throw new ServiceOrderException("Cannot add funds since account Id is not provided");
//			}
		}
	}

	}

