package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class CloseOrderCmd extends SOCommand {

	private IMarketPlatformPromoBO marketPlatformBO;
	
	@Override 
	public void execute(Map<String, Object> processVariables) {
        logger.info("Starting CloseOrderCmd.execute");
        long startTime = System.currentTimeMillis();
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		String userName = getUserName(processVariables);
		String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
        String providerState = ProcessVariableUtil.extractProviderState(processVariables);
        logger.info(String.format("Before calling JMS Queues with CloseOrderCmd.execute method. Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		closeServiceOrder(serviceOrder, userName, buyerState, providerState, processVariables);
        logger.info(String.format("Finished with CloseOrderCmd.execute method. Time taken is %1$d ms", System.currentTimeMillis() - startTime));
        
        
        logger.info("hsrautoClose  "+processVariables.get("hsrautoClose"));
        if(null!=processVariables.get("hsrautoClose")){
        	if("true".equals(processVariables.get("hsrautoClose").toString())){    		
        		processVariables.put(ProcessVariableUtil.AUTO_CLOSE,  Integer.valueOf(1));
        	}
        }
        
	}

	private void closeServiceOrder(ServiceOrder serviceOrder, String userName, String buyerState, String providerState, Map<String, Object> processVariables) {
		
		WalletResponseVO resp;
		if(3000==serviceOrder.getBuyerId().intValue())
		{
			
			double addonPrice = 0.0d;
			addonPrice = serviceOrder.getTotalAddon(true).doubleValue();
            logger.info("ADDONTT"+addonPrice);
			if (addonPrice > 0) {
				resp = walletGateway.closeServiceOrder(userName, serviceOrder,
						buyerState, providerState,
						getAddonPartsPrice(serviceOrder),
						getServiceFeePercentage(serviceOrder));

			} else {
				resp = walletGateway.closeServiceOrder(userName, serviceOrder,
						buyerState, providerState, getPartsPrice(serviceOrder),
						getServiceFeePercentage(serviceOrder));
			}
		}
		else
		{
		 resp = walletGateway.closeServiceOrder(userName, serviceOrder, buyerState, providerState, getAddonPrice(serviceOrder), getServiceFeePercentage(serviceOrder));
		}
		String messageId = resp.getMessageId();
		String jmsMessageId = resp.getJmsMessageId();
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
		processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
	}

	private BigDecimal getServiceFeePercentage(ServiceOrder serviceOrder) {
		double percent = marketPlatformBO.getPromoFee(serviceOrder.getSoId(), serviceOrder.getBuyerId(), FeeType.PromoServiceFee);
		return new BigDecimal(percent);
	}

    /**
     * This is created as a separate function for HSR to override
     * @param serviceOrder
     * @return
     */
    protected BigDecimal getAddonPrice(ServiceOrder serviceOrder){
         return serviceOrder.getTotalAddon(false);
    }
    protected BigDecimal getAddonPartsPrice(ServiceOrder serviceOrder){
    	 BigDecimal addOnSpendLimit =  serviceOrder.getTotalAddon(true);
    	 return addOnSpendLimit.add(serviceOrder.getPartsPrice());
   }
    protected BigDecimal getPartsPrice(ServiceOrder serviceOrder){
   	 
   	 return serviceOrder.getPartsPrice();
  }
    
	public void setMarketPlatformBO(IMarketPlatformPromoBO marketPlatformBO) {
		this.marketPlatformBO = marketPlatformBO;
	}
}