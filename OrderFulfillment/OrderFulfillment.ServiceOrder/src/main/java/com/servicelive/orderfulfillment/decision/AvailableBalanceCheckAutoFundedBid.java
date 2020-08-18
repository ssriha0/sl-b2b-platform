/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

/**
 * 
 *
 */
public class AvailableBalanceCheckAutoFundedBid extends AbstractServiceOrderDecision {
    private static final long serialVersionUID = 100102764695148267L;
    protected final Logger logger = Logger.getLogger(getClass());
	private IWalletGateway walletGateway;
	public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		double availableBalance = walletGateway.getBuyerAvailableBalance(so.getBuyerId());
		if(null!= so.getFundingTypeId() && 
				(so.getFundingTypeId().intValue() == 40 || so.getFundingTypeId().intValue() == 90)){
			return "autofunded";
		}
		else {
			String orderType=null;
			if(null!=so.getPriceModel() && null!=so.getPriceModel().name()){
				orderType=so.getPriceModel().name();
                logger.info("orderType: "+orderType);
				logger.info("SpendLimit: "+so.getTotalSpendLimit());
				logger.info("SpendLimit with Posting Fee: "+ so.getTotalSpendLimitWithPostingFee());
				if (orderType.equalsIgnoreCase(OrderfulfillmentConstants.ZERO_PRICE_BID)){
					if (availableBalance >= so.getTotalSpendLimitWithPostingFee().doubleValue()){
						return "true";
					}
				 }
			}
			else if (availableBalance >= so.getTotalSpendLimit().doubleValue()){
				return "true";
			}
		}
		   return "false";	
	
	}
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
