package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ConditionalOfferReason;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

/**
 * @author Mustafa Motiwala
 *
 */
public class CreateConditionalOfferSignal extends RoutedProviderSignal {

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
    	List<String> returnVal=new ArrayList<String>(); 
    	SOElementCollection soeCollection=(SOElementCollection)soe;
    	List<SOElement> soeList=soeCollection.getElements();
    	for(SOElement soElement: soeList){
    		returnVal = super.validate(soElement, soTarget);
        if(!returnVal.isEmpty()){
            //Parent failed validation - Should not continue with specialized validation.
            return returnVal;
        }
         
        RoutedProvider input = (RoutedProvider) soElement;
        RoutedProvider rpTarget = soTarget.getRoutedResource(input.getProviderResourceId());
        if(canNotCreateConditionalOffer(rpTarget)){
            returnVal.add("Can not create Counter Offer.");
        }
        
        if (returnVal.isEmpty()) {
        	RoutedProvider routedProvider = (RoutedProvider) soElement;
        	Integer serviceLocationTimeZoneOffset = soTarget.getServiceDateTimezoneOffset();
        	Calendar currentDateTime = Calendar.getInstance();
        	
        	int currentDateTimeOffset = currentDateTime.getTimeZone().getOffset(currentDateTime.getTimeInMillis()) / 1000 / 3600;  // convert from milliseconds to hours
        	currentDateTime.add(Calendar.HOUR_OF_DAY, serviceLocationTimeZoneOffset - currentDateTimeOffset);  // convert to service location's current time
        	if (routedProvider.getOfferExpirationDate() != null && routedProvider.getOfferExpirationDate().before(currentDateTime.getTime())) {
        		returnVal.add("Please enter an expiration date in the future.");
            }
        }
    	}
        return returnVal;
    }

    @Override
    protected void processRoutedProvider(RoutedProvider source, RoutedProvider target) {
        target.setProviderResponse(source.getProviderResponse());
        target.setProviderRespReasonId(source.getProviderRespReasonId());
        target.setProviderRespComment(source.getProviderRespComment());
        // Convert OfferExpirationDate to GMT
        Date gmtExpirationDate = TimeChangeUtil.convertDateBetweenTimeZones(source.getOfferExpirationDate(), TimeZone.getTimeZone(target.getServiceOrder().getServiceLocationTimeZone()),
        		TimeZone.getTimeZone("GMT"));
		target.setOfferExpirationDate(gmtExpirationDate);
		
        target.setSchedule(source.getSchedule());
        target.setTotalHours(source.getTotalHours());
        target.setTotalLabor(source.getTotalLabor());
        target.setPartsMaterials(source.getPartsMaterials());
        //logger.debug("Routed Provider object is = " + target.toString());

        //Only do this if the response reason is related to spend limit. 
        ConditionalOfferReason reason = ConditionalOfferReason.fromId(target.getProviderRespReasonId());
        if(ConditionalOfferReason.SERVICE_DATE_AND_SPEND_LIMIT == reason || ConditionalOfferReason.SPEND_LIMIT == reason){
            target.setIncreaseSpendLimit(source.getIncreaseSpendLimit());
        }
        Set<SOCounterOfferReason> setCounterOffers = new HashSet<SOCounterOfferReason>();
        for(SOCounterOfferReason srcReason:source.getCounterOffers()){
            SOCounterOfferReason dstReason = new SOCounterOfferReason();
            dstReason.setResponseReasonId(srcReason.getResponseReasonId());
            dstReason.setProviderResponse(srcReason.getProviderResponse());
            dstReason.setProviderResponseReasonId(srcReason.getProviderResponseReasonId());
            dstReason.setRoutedProvider(target);
            setCounterOffers.add(dstReason);
        }
        target.setCounterOffers(setCounterOffers);
    }

    /**
     * Helper method for Validate. Should not be called from anywhere else.
     * @param target
     * @return
     */
    private boolean canNotCreateConditionalOffer(RoutedProvider target) {
        return (target.getCounterOffers().size()>0)?true:false;
    }
    
    
	protected HashMap<String,Object> getLogMap(SOElement request, Identification id, ServiceOrder target) throws ServiceOrderException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		SOElementCollection soeCollection= (SOElementCollection)request;
		List<SOElement> soeList=soeCollection.getElements();
		SOElement soe=soeList.get(0);
		if(soe instanceof RoutedProvider){
			RoutedProvider rp = (RoutedProvider)soe;
			if (rp.getIncreaseSpendLimit() !=  null) {
				map.put("SPEND_LIMIT_TO_LABOR", rp.getIncreaseSpendLimit());
			}else{
                map.put("SPEND_LIMIT_TO_LABOR", "NA");
            }
			if (rp.getSchedule() != null) {
				map.put("SCHEDULE_FROM", rp.getSchedule().getServiceDate1());
				map.put("SCHEDULE_TO", rp.getSchedule().getServiceDate2());
				map.put("SCHEDULE_START_TIME", rp.getSchedule().getServiceTimeStart());
				map.put("SCHEDULE_END_TIME", rp.getSchedule().getServiceTimeEnd());
			}else{
                map.put("SCHEDULE_FROM", "NA");
                map.put("SCHEDULE_TO", "NA");
                map.put("SCHEDULE_START_TIME", "NA");
                map.put("SCHEDULE_END_TIME", "NA");                
            }
		}
		 
		return map;
	}

}
