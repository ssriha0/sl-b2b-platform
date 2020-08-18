package com.servicelive.orderfulfillment.group.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ConditionalOfferReason;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class CreateGroupConditionalOfferSignal extends GroupSignal {

	Logger logger = Logger.getLogger(getClass());
	
	protected void update(SOElement soe, SOGroup sog) {
		List<SOElement> soeList=new ArrayList<SOElement>();
    	if(soe instanceof RoutedProvider){
    		soeList.add(soe);
    	}
    	else{
    		SOElementCollection soeCollection=(SOElementCollection)soe;
    		soeList=soeCollection.getElements();
    	}
    	for(SOElement soElement:soeList){
    		RoutedProvider source = (RoutedProvider) soElement;
    		logger.debug("conditional offer by resource " + source.getProviderResourceId());
    		GroupRoutedProvider target = sog.getRoutedProvider(source.getProviderResourceId());
        
    		if (target != null){
    			target.setCondOfferPrice(source.getIncreaseSpendLimit());
    			target.setOfferExpirationDate(source.getOfferExpirationDate());
	        	target.setOfferExpirationSDate(source.getOfferExpirationSDate());
	        	target.setProviderRespComment(source.getProviderRespComment());
		        target.setProviderRespDate(source.getProviderRespDate());
		        target.setProviderResponse(source.getProviderResponse());
		        target.setProviderRespReasonId(source.getProviderRespReasonId());
		        target.setSchedule(source.getSchedule());
	        
		        serviceOrderDao.save(target);
	        
    		} else {
    			throw new ServiceOrderException("Could not find the routed provider in group");
    		}
    	}
	}
	
	
	protected void sendSignalToOrders(SOElement soe, SOGroup sog, Identification id, Map<String, Serializable> miscParams) {
        if (sendSignalToOrders && orderSignal != SignalType.UNKNOWN){
        	Map<String, Serializable> params = new HashMap<String, Serializable>();
        	for (Entry<String, Serializable> entry : miscParams.entrySet()){
        		if (!groupProcessKeys.contains(entry.getKey()))
        			params.put(entry.getKey(), entry.getValue());
        	}
        	List<SOElement> soeList=new ArrayList<SOElement>();
        	if(soe instanceof RoutedProvider){
        		soeList.add(soe);
        	}
        	else{
        		SOElementCollection soeCollection=(SOElementCollection)soe;
        		soeList=soeCollection.getElements();
        	}
        	int groupSize = sog.getServiceOrders().size();
    		int i = 0;
    		HashMap<Long, BigDecimal> increaseRatioMap=new HashMap<Long, BigDecimal>();
    		HashMap<Long, BigDecimal> targetPriceMap=new HashMap<Long, BigDecimal>();
    		List<RoutedProvider> sourceList=new ArrayList<RoutedProvider>();
        	for(SOElement soElement:soeList){
        		RoutedProvider source = (RoutedProvider) soElement;
        		GroupRoutedProvider target = sog.getRoutedProvider(source.getProviderResourceId());
        		BigDecimal increaseRatio = PricingUtil.ZERO;
        		 // Code Added under JIRA Ticket SL-18095 SLTCSPRODSUPP
        		if (source.getProviderRespReasonId() == ConditionalOfferReason.SPEND_LIMIT.getId()){
        			BigDecimal priceWithoutParts = target.getCondOfferPrice()
        									.subtract(sog.getGroupPrice().getFinalSpendLimitParts());
        			logger.info("priceWithoutParts:"+priceWithoutParts+" ,target.getCondOfferPrice()"+target.getCondOfferPrice()+"sog.getGroupPrice().getFinalSpendLimitParts():"+sog.getGroupPrice().getFinalSpendLimitParts());
        			increaseRatio = getProportion(priceWithoutParts, sog.getGroupPrice().getFinalSpendLimitLabor());
        			logger.info("increaseRatio :"+increaseRatio+", sog.getGroupPrice().getFinalSpendLimitLabor():"+sog.getGroupPrice().getFinalSpendLimitLabor());
        		}
        		increaseRatioMap.put(source.getProviderResourceId(), increaseRatio);
        		targetPriceMap.put(target.getProviderResourceId(), target.getCondOfferPrice());
        		sourceList.add(source);
        	}
        	BigDecimal pennyAdjuster = PricingUtil.ZERO;	
        	Boolean pennyCheck;
        	for (ServiceOrder so : sog.getServiceOrders()){
        		i++;
        		pennyCheck=true;
        		List<RoutedProvider> retList=new ArrayList<RoutedProvider>();
        		for(RoutedProvider source:sourceList){
        			RoutedProvider ret = createRoutedProviderCopy(source);
        			// Code Added under JIRA Ticket SL-18095 SLTCSPRODSUPP
        			if (source.getProviderRespReasonId() == ConditionalOfferReason.SPEND_LIMIT.getId() ||
        					source.getProviderRespReasonId() == ConditionalOfferReason.SERVICE_DATE_AND_SPEND_LIMIT.getId()){
        				ret.setIncreaseSpendLimit(so.getSpendLimitLabor().multiply(increaseRatioMap.get(ret.getProviderResourceId())).add(so.getSpendLimitParts()));
        				logger.info("ret.setIncreaseSpendLimit:"+so.getSpendLimitLabor().multiply(increaseRatioMap.get(ret.getProviderResourceId())).add(so.getSpendLimitParts()));
        				if(pennyCheck){
        					pennyAdjuster = pennyAdjuster.add(ret.getIncreaseSpendLimit());
        				}
        				pennyCheck=false;//penny adjuster should iterate over SO only
        				//check if there is a penny difference and adjust last service order
        				if (i == groupSize){
        					BigDecimal diff = targetPriceMap.get(ret.getProviderResourceId()).subtract(pennyAdjuster);
        					if(diff.doubleValue() != 0.0){
        						ret.setIncreaseSpendLimit(ret.getIncreaseSpendLimit().add(diff));
        					}
        				}
        			}
        			retList.add(ret);
        		}
        		SOElementCollection soeColl=new SOElementCollection();
        		soeColl.addAllElements(retList);
        		serviceOrderBO.processOrderSignal(so.getSoId(), orderSignal, id, soeColl, params);
        	}
        	
        }		
	}

    private BigDecimal getProportion(BigDecimal number1, BigDecimal number2){
    	if(number1.doubleValue() == 0 || number2.doubleValue() == 0){
    		return PricingUtil.ZERO;
    	}
    	
    	BigDecimal proportion = new BigDecimal(number1.doubleValue() / number2.doubleValue());
    	
    	return proportion;
    }
    
	private RoutedProvider createRoutedProviderCopy(RoutedProvider source) {
		RoutedProvider target = new RoutedProvider();
		target.setProviderResourceId(source.getProviderResourceId());
		target.setVendorId(source.getVendorId());
        target.setProviderResponse(source.getProviderResponse());
        target.setProviderRespReasonId(source.getProviderRespReasonId());
        target.setOfferExpirationDate(source.getOfferExpirationDate());
        target.setSchedule(source.getSchedule());
        target.setTotalHours(source.getTotalHours());
        target.setTotalLabor(source.getTotalLabor());
        target.setPartsMaterials(source.getPartsMaterials());
        target.setCounterOffers(source.getCounterOffers());
        return target;
	}

}
