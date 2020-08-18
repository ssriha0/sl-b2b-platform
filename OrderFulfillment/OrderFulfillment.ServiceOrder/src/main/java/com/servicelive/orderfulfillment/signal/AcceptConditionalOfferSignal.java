package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ConditionalOfferReason;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

/**
 * @author Mustafa Motiwala
 *
 */
public class AcceptConditionalOfferSignal extends AcceptSignal {
	
	private Identification id;
	
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		this.id = id;
	}
    @Override
	protected void update(SOElement soe, ServiceOrder so){
		ServiceOrder sourceSO = (ServiceOrder) soe;
		
		//update serviceorder for accepting provider
		super.update(soe, so);

        //Updating the reschedule info.
		RoutedProvider source = getAcceptedProvider(sourceSO);
        RoutedProvider target = so.getRoutedResource(source.getProviderResourceId());
		processRoutedProvider(source, target);
		
		serviceOrderDao.update(target);
	}

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = super.validate(soe, soTarget);
        if(!(soe instanceof ServiceOrder)){
			returnVal.add("Expected ServiceOrder but found " + soe.getTypeName());
            return returnVal;
		}
        ServiceOrder srcServiceOrder = (ServiceOrder)soe;
        //Is the Conditional Offer still open?
        RoutedProvider srcRoutedProvider = getAcceptedProvider(srcServiceOrder);
        RoutedProvider dstRoutedProvider = soTarget.getRoutedResource(srcRoutedProvider.getProviderResourceId());
        if (ProviderResponseType.CONDITIONAL_OFFER != dstRoutedProvider.getProviderResponse()){
            returnVal.add("Can not accept Conditional Offer. No Conditional Offer available from provider.");
        }

        return returnVal;
    }

    private RoutedProvider getAcceptedProvider(ServiceOrder sourceSO) {
		RoutedProvider provider = new RoutedProvider();
		provider.setRoutedProviderId(sourceSO.getAcceptedProviderId().intValue());
		provider.setProviderResourceId(sourceSO.getAcceptedProviderResourceId());
		return provider;
	}

	protected void processRoutedProvider(RoutedProvider provider, RoutedProvider target) {
        ConditionalOfferReason providerReason = ConditionalOfferReason.fromId(target.getProviderRespReasonId());
        switch (providerReason) {
        case RESCHEDULE_SERVICE_DATE:
            updateSchedule(target);
            break;
        case SERVICE_DATE_AND_SPEND_LIMIT:
            updateSchedule(target);
            updateSpendLimit(target);
            break;
        case SPEND_LIMIT:
            updateSpendLimit(target);
            break;
        }
    }
    
    private void updateSchedule(RoutedProvider target) {
        SOSchedule schedule = target.getServiceOrder().getSchedule();
        schedule.setServiceDate1(target.getSchedule().getServiceDate1());
        schedule.setServiceTimeStart(target.getSchedule().getServiceTimeStart());
        if(target.getSchedule().getServiceDate2() == null && target.getSchedule().getServiceTimeEnd() == null){
        	schedule.setServiceDateTypeId(1);
        	schedule.setServiceDateGMT2(null);
        	schedule.setServiceTimeEndGMT(null);
        }
        schedule.setServiceDate2(target.getSchedule().getServiceDate2());
        schedule.setServiceTimeEnd(target.getSchedule().getServiceTimeEnd());
    }

    private void updateSpendLimit(RoutedProvider target) {
    	  logger.info("Entering updateSpendLimit");
        //Conditional offer only allows to increase labor
        /*
         * Currently we assume that provider would not be increasing the spend limit for parts
         */
    	
    	ServiceOrder so = target.getServiceOrder();
        if (so.getPriceModel() == PriceModelType.NAME_PRICE){
        	Double oldTotal=so.getSpendLimitLabor().doubleValue();
        	 logger.info("so.getPriceModel()"+so.getPriceModel());
        	double totalFinalPriceOfTasks = 0.0;
        	int noOfTasks =0;
    		for (SOTask task : so.getActiveTasks()) {

    			if (task.getFinalPrice() != null && (task.getTaskType().equals(
    					OrderfulfillmentConstants.PRIMARY_TASK)
    					|| task.getTaskType().equals(
    							OrderfulfillmentConstants.NON_PRIMARY_TASK)
    					 )) {
    				 logger.info("task.getFinalPrice()"+task.getFinalPrice().doubleValue());
    				totalFinalPriceOfTasks = totalFinalPriceOfTasks
    						+ task.getFinalPrice().doubleValue();
    				noOfTasks +=1;
    			}
    		}
    		
    		 logger.info("noOfTasks"+noOfTasks);
    		 logger.info("totalFinalPriceOfTasks"+totalFinalPriceOfTasks);
        	Double newTotal=target.getIncreaseSpendLimit().subtract(so.getSpendLimitParts()).doubleValue();
        	logger.info("target.getIncreaseSpendLimit()"+target.getIncreaseSpendLimit().doubleValue());
       	 	logger.info("newTotal"+newTotal);
       	 	logger.info("so.getSpendLimitParts()"+so.getSpendLimitParts());
        	Double additionalAmount=0.0;
        	if(so.getSpendLimitLabor()!=null)
        	{
        		logger.info("so.getSpendLimitLabor()"+so.getSpendLimitLabor());
        		additionalAmount=newTotal-so.getSpendLimitLabor().doubleValue();
        		logger.info("additionalAmount"+additionalAmount);
        		
        	}
            so.setSpendLimitLabor(new BigDecimal(newTotal));
            if(so.getPrice()!=null)
            {
            so.getPrice().setDiscountedSpendLimitLabor(new BigDecimal(newTotal));
            }
            if (so.getTasks() != null || !so.getTasks().isEmpty()) {
				for (SOTask task : so.getActiveTasks()) {
					if (task.getFinalPrice() != null
							&& (task.getTaskType().equals(
									OrderfulfillmentConstants.PRIMARY_TASK) || task
									.getTaskType()
									.equals(
											OrderfulfillmentConstants.NON_PRIMARY_TASK))) {
						Double taskPrice = task.getFinalPrice().doubleValue();
						logger.info("taskPrice"+taskPrice);
						if (taskPrice != null) {
							//taskPrice = taskPrice * (newTotal / totalFinalPriceOfTasks);
							double amount = 0;
							boolean histoyInd=false;
							if(null!=task.getFinalPrice()  && totalFinalPriceOfTasks >0) {								
								double percentageShare = task.getFinalPrice().doubleValue() / totalFinalPriceOfTasks * 100;
								logger.info("percentageShare"+percentageShare);
								amount = additionalAmount * percentageShare / 100;
								logger.info("amount"+amount);
								amount = task.getFinalPrice().doubleValue() + amount;
								if(task.getFinalPrice()!=null && BigDecimal.valueOf(amount).compareTo(task.getFinalPrice()) != 0){
									histoyInd=true;	
								}
								task.setFinalPrice(BigDecimal.valueOf(amount));
								logger.info("task.setFinalPrice(BigDecimal.valueOf(amount))"+amount);
								if(histoyInd){
									PricingUtil.addTaskPriceHistory(task, "SYSTEM","SYSTEM");
								}
								
						}// divide additionalAmount equally among skus in case of the initial total task price is 0 
							else if(null!=task.getFinalPrice() && noOfTasks>0){
								logger.info("Inside Else");
								amount = additionalAmount /noOfTasks;
								logger.info("amount"+amount);
								amount = task.getFinalPrice().doubleValue() + amount;
								
								if(task.getFinalPrice()!=null && BigDecimal.valueOf(amount).compareTo(task.getFinalPrice()) != 0){
									histoyInd=true;	
								}
								task.setFinalPrice(BigDecimal.valueOf(amount));
								logger.info("task.setFinalPrice(BigDecimal.valueOf(amount))"+amount);
								if(histoyInd){
									PricingUtil.addTaskPriceHistory(task,"SYSTEM", "SYSTEM");
								}
							}
					}
				}
			}
		}
    }else{
        so.setSpendLimitLabor(target.getTotalLabor());
        logger.info("target.getTotalLabor()" + target.getTotalLabor());
        so.setSpendLimitParts(target.getPartsMaterials());
        logger.info("target.getPartsMaterials()" + target.getPartsMaterials());
	}
        /** 
		* SL-18007 setting so price change history if there is a change in the so spend limit labour 
		* during acceptance of counter offer 
		*/
		if (null != so.getSoPriceChangeHistory()){
			
			logger.info("18007: so price change history contins:" + so.getSoPriceChangeHistory());
								
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			//set specific components before passing to the generic method
			newSOPriceChangeHistory.setAction(OFConstants.ACCEPT_COUNTER_OFFER);
			
			// to set the provider response reason while placing counter offer in the reason comment field while persisting so price details history
			StringBuffer counterOfferProviderReason = new StringBuffer("");
			Set<SOCounterOfferReason> counterOffers = target.getCounterOffers();
			List<Long> counterOfferRespIdList = new ArrayList<Long>();
			List<String> counterOfferRespReasonList = new ArrayList<String>();
			
			//if bid order, set provider comment as reason 
			if(OrderfulfillmentConstants.ZERO_PRICE_BID.equals(target.getServiceOrder().getPriceModel().toString())){
				counterOfferProviderReason.append(target.getProviderRespComment());
			}
			else{
				for(SOCounterOfferReason counterOffer : counterOffers){
					Long counterOfferResponseId = counterOffer.getResponseReasonId();
					counterOfferRespIdList.add(counterOfferResponseId);
				}
				if (null != counterOfferRespIdList && counterOfferRespIdList.size()>0){
					counterOfferRespReasonList = serviceOrderDao.fetchCounterOfferProviderResponseReason(counterOfferRespIdList);
					if (null != counterOfferRespReasonList){
						int count = 0;
						for(String respReason : counterOfferRespReasonList){
							counterOfferProviderReason.append(respReason);
							count++;
							if (count<counterOfferRespReasonList.size()){
								counterOfferProviderReason.append(" - ");
							}
						}
					}
				}
			}
			newSOPriceChangeHistory.setReasonComment(counterOfferProviderReason.toString());
			
			// call generic method to save so price change history
			PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, id.getBuyerResourceId().toString(),id.getFullname());						
		}
}
}