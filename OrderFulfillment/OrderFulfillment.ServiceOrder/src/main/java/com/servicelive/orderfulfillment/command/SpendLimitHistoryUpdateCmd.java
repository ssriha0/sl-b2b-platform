
/*
 *	Date        Project       Author         Version

 * -----------  --------- 	-----------  	---------
 * 28-Jun-2012	KMSRTVST   Infosys				1.0
 * 
 * 
 */

/*
 * This is the command for updating spend_limit_history table.
 * 
 */

package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SpendLimitHistory;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class SpendLimitHistoryUpdateCmd extends SOCommand {

	/* (non-Javadoc)
	 * @see com.servicelive.orderfulfillment.command.SOCommand#execute(java.util.Map)
	 */
	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM SpendLimitHistoryUpdateCmd Command ***");
		Date now=new Date();
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		BigDecimal maximumPrice= new BigDecimal(0.00);
		if(null!=serviceOrder && null!= serviceOrder.getSpendLimitIncrComment() ){
			SpendLimitHistory spendLimitHistory=new SpendLimitHistory();
			maximumPrice=serviceOrder.getSpendLimitLabor().add(serviceOrder.getSpendLimitParts());
			spendLimitHistory.setMaximumPrice(maximumPrice);
			spendLimitHistory.setModifiedDate(now);
			Object userId= processVariables
					.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
			if (userId != null) {
				spendLimitHistory.setCreatedBy(userId.toString());
			}
			String reasonCode =  (String)processVariables.get(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON);
			String oldPrice =  (String)processVariables.get(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_PRICE);
			if(StringUtils.isNotBlank(oldPrice)){
				BigDecimal oldSpendLimit = new BigDecimal(oldPrice);
				spendLimitHistory.setOldPrice(oldSpendLimit);
			}
			if(StringUtils.isNotBlank(reasonCode)){
				Integer reasonCodeId = Integer.parseInt(reasonCode);
				spendLimitHistory.setReasonCodeId(reasonCodeId);
			}
			spendLimitHistory.setModifyReason(serviceOrder.getSpendLimitIncrComment());
			spendLimitHistory.setSoId(serviceOrder.getSoId());
			Object userName = processVariables
					.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME);
			if (userName != null) {
				spendLimitHistory.setCreatedByName(userName.toString());
			}
			logger.info("Starting SLIHistoryUpdate.execute");
			serviceOrderDao.save(spendLimitHistory);
			
			
		}
	}

}
