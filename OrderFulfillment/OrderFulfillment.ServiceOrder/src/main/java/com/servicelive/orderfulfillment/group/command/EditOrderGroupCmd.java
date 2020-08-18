package com.servicelive.orderfulfillment.group.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOChild;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
/**
 * User: Yunus Burhani
 * Date: Apr 15, 2010
 * Time: 11:18:36 AM
 */
public class EditOrderGroupCmd extends GroupSignalSOCmd {
    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
    	logger.info("Entering EditOrderGroupCmd:handleGroup method");
        ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
        //change all the service orders to have same
        for(ServiceOrder so : soGroup.getServiceOrders()){
            if (!so.getSoId().equals(eSO.getSoId())){
                changeServiceOrder(so, eSO);
            }
        }
        
        //change search criteria for the effective serviceOrder
        changeSearchCriteria(eSO.getSoId(), eSO.getServiceLocation(), eSO.getSchedule().getServiceDate1());

        //check if the price was changed and update it
        if (processVariables.containsKey(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_LABOR_SPEND_LIMIT) &&
        		processVariables.get(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_LABOR_SPEND_LIMIT) != null){
        	BigDecimal groupLaborSpendLimit = new BigDecimal((String)processVariables.get(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_LABOR_SPEND_LIMIT)).setScale(2);
        	BigDecimal groupPartsSpendLimit = PricingUtil.ZERO;
        	if (processVariables.containsKey(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_PARTS_SPEND_LIMIT) &&
        			processVariables.get(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_PARTS_SPEND_LIMIT) != null){
        		groupPartsSpendLimit = new BigDecimal((String)processVariables.get(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_PARTS_SPEND_LIMIT)).setScale(2);
        	
        		logger.info("groupPartsSpendLimit:" + groupPartsSpendLimit);
        	}
        	calculateGroupPricing(eSO.getSoId(), soGroup, groupLaborSpendLimit, groupPartsSpendLimit, processVariables);
        }
        
        //change group routed providers
        changeRoutedProviders(soGroup, eSO.getRoutedResources(), processVariables);

        clearEffectiveServiceOrder(processVariables);
    }

    private void changeRoutedProviders(SOGroup soGroup, List<RoutedProvider> routedResources, Map<String, Object> processVariables) { 	
    	//delete all the routed resources from group
		for (GroupRoutedProvider grp: soGroup.getGroupRoutedProviders()){
			grp.setSoGroup(null);
			serviceOrderDao.delete(grp);
		}
		
		soGroup.getGroupRoutedProviders().clear();
		
		for(RoutedProvider rp : routedResources){
			GroupRoutedProvider grp = new GroupRoutedProvider();
			grp.setProviderResourceId(rp.getProviderResourceId());
			grp.setVendorId(rp.getVendorId());
			grp.setSoGroup(soGroup);
			grp.setRoutedDate(new Date());
			soGroup.getGroupRoutedProviders().add(grp);
		}

		serviceOrderDao.update(soGroup);
		ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
        //change Routed providers for all orders
        if (soGroup.getGroupRoutedProviders().size() > 0){
        	for(ServiceOrder so : soGroup.getServiceOrders()){
	        	if (sendPostSignal(processVariables)){
                    if(skipPostForAffectingOrder(processVariables) && so.getSoId().equals(eSO.getSoId())) continue;
	    			this.sendSignalToSOProcess(so, SignalType.POST_ORDER, getSOElementForOrderSignal(soGroup.getGroupRoutedProviders()), getProcessVariableForSendEmail(false));
	        	}else {
	        		saveRoutedProviders(soGroup.getGroupRoutedProviders(), so);
	        	}
        	}
        }
	}

	private void calculateGroupPricing(String effectiveSOId, SOGroup soGroup, BigDecimal groupLaborSpendLimit, BigDecimal groupPartsSpendLimit, Map<String, Object> processVariables) {
		//check if there is a change in pricing at all or pricing specified is less than the current price
		
		logger.info("Entering EditOrderGroupCmd:calculateGroupPricing method");
		logger.info("effectiveSOId:" + effectiveSOId);
		logger.info("groupLaborSpendLimit:" + groupLaborSpendLimit);
		BigDecimal groupTotalPermitPrice =  soGroup.getGroupPrice().getTotalPermitPrice();
		logger.info("groupTotalPermitPrice:" + groupTotalPermitPrice);
    	if(groupTotalPermitPrice == null)
    		groupTotalPermitPrice= PricingUtil.ZERO;
    	logger.info("groupTotalPermitPrice:" + groupTotalPermitPrice);
    	
		if ((soGroup.getGroupPrice().getFinalSpendLimitLabor().subtract(groupTotalPermitPrice)).compareTo(groupLaborSpendLimit) >= 0 &&
    			soGroup.getGroupPrice().getFinalSpendLimitParts().compareTo(groupPartsSpendLimit) >= 0){
    		return;
    	}
    	int groupSize = soGroup.getServiceOrders().size();
    	
    	logger.info("groupSize:" + groupSize);
    	//BigDecimal groupLaborSpendLimitexcludesPermit = groupLaborSpendLimit.subtract(groupTotalPermitPrice);
    	BigDecimal groupLaborSpendLimitexcludesPermit = groupLaborSpendLimit;
    	logger.info("groupLaborSpendLimitexcludesPermit:" + groupLaborSpendLimitexcludesPermit);
    	int i = 0;
    	BigDecimal pennyAdjusterLabor = PricingUtil.ZERO;
    	BigDecimal pennyAdjusterParts = PricingUtil.ZERO;
    	for (ServiceOrder so : soGroup.getServiceOrders()){
    		i++;
    		BigDecimal totalPermitPrice = so.getTotalPermitPrice();
    		logger.info("totalPermitPrice:" + totalPermitPrice);
    		if(totalPermitPrice == null)
    			totalPermitPrice= PricingUtil.ZERO;
    		//BigDecimal orderToGroupLaborRatio = getProportion(so.getSpendLimitLabor(), soGroup.getGroupPrice().getFinalSpendLimitLabor());
    		BigDecimal orderToGroupLaborRatio = getProportion(so.getSpendLimitLabor().subtract(totalPermitPrice), soGroup.getGroupPrice().getFinalSpendLimitLabor().subtract(groupTotalPermitPrice));
    		logger.info("orderToGroupLaborRatio:" + orderToGroupLaborRatio);
    		BigDecimal orderToGroupPartsRatio = getProportion(so.getSpendLimitParts(), soGroup.getGroupPrice().getFinalSpendLimitParts());
    		logger.info("orderToGroupPartsRatio:" + orderToGroupPartsRatio);
    		BigDecimal orderLaborSpendLimitexcludesPermit =so.getSpendLimitLabor().subtract(totalPermitPrice);
    		logger.info("orderLaborSpendLimitexcludesPermit:" + orderLaborSpendLimitexcludesPermit);
    		BigDecimal ratioappliedOrderLaborSpendLimitexcludesPermit = orderToGroupLaborRatio.multiply(groupLaborSpendLimitexcludesPermit).setScale(2, RoundingMode.HALF_EVEN);
    		logger.info("ratioappliedOrderLaborSpendLimitexcludesPermit:" + ratioappliedOrderLaborSpendLimitexcludesPermit);
    		so.setSpendLimitLabor(ratioappliedOrderLaborSpendLimitexcludesPermit.add(totalPermitPrice));
    		so.setSpendLimitParts(orderToGroupPartsRatio.multiply(groupPartsSpendLimit).setScale(2, RoundingMode.HALF_EVEN));
    		//tally up difference so we can account for penny differences
    		pennyAdjusterLabor = pennyAdjusterLabor.add(so.getSpendLimitLabor());
    		pennyAdjusterParts = pennyAdjusterParts.add(so.getSpendLimitParts());
    		//adjust penny difference on the last order
    		if (i == groupSize){
    			BigDecimal laborDiff = (groupLaborSpendLimit.add(groupTotalPermitPrice)).subtract(pennyAdjusterLabor);
    			logger.info("laborDiff:" + laborDiff);
    			if(laborDiff.doubleValue() != 0.0){
    				so.setSpendLimitLabor(so.getSpendLimitLabor().add(laborDiff));
    			}

    			BigDecimal partsDiff = groupPartsSpendLimit.subtract(pennyAdjusterParts);
    			logger.info("partsDiff:" + partsDiff);
    			if(partsDiff.doubleValue() != 0.0){
    				so.setSpendLimitParts(so.getSpendLimitParts().add(partsDiff));
    			}
    		}
    		BigDecimal pennyAdjusterPermit = PricingUtil.ZERO;
    		logger.info("pennyAdjusterPermit" + pennyAdjusterPermit);
    		int taskSize = so.getActiveTasks().size();
    		logger.info("taskSize" + taskSize);
    		int j = 0;
    		for (SOTask soTask : so.getActiveTasks()) {
    			j++;
    			//logic for applying ratio to non-permit tasks
    			//soTask.is
    			if (!soTask.getTaskName().trim().startsWith(OFConstants.PERMIT_SKU)) {
    				BigDecimal taskPrice = 	soTask.getFinalPrice();
    				logger.info("taskPrice" + taskPrice);
    			if(taskPrice !=null && taskPrice.doubleValue() != 0.0)
    			{
    				//{
    				BigDecimal taskToOrdeLaborRatio =getProportion(taskPrice,orderLaborSpendLimitexcludesPermit);
    				soTask.setFinalPrice(taskToOrdeLaborRatio.multiply(ratioappliedOrderLaborSpendLimitexcludesPermit).setScale(2, RoundingMode.HALF_EVEN));
    				pennyAdjusterPermit = pennyAdjusterPermit.add(soTask.getFinalPrice());
    				
    				//setting task history data
    				if(soTask.getLatestPrice()!=null && soTask.getLatestPrice().compareTo(soTask.getFinalPrice()) != 0){
    					
    					String userName = null;
    					String userId = null;
    					if (processVariables.containsKey(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME) &&
				        		processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME) != null){
    						userName=(String)processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME);
    					}
    					
    					if (processVariables.containsKey(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID) &&
    							processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID) != null){
    							userId=(String)processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
    					}
						
						PricingUtil.addTaskPriceHistory(soTask, userId, userName);
    				}
    				//}
    				//adjust penny difference on the last task
    				/*if (j == taskSize){
        			BigDecimal taskDiff = ratioappliedOrderLaborSpendLimitexcludesPermit.subtract(pennyAdjusterPermit);
        			logger.info("taskDiff = " + taskDiff);
        			if(taskDiff.doubleValue() != 0.0){
        				soTask.setFinalPrice(soTask.getFinalPrice().add(taskDiff));
        			}
        			logger.info("soTask.getFinalPrice() with taskDiff = " + soTask.getFinalPrice());
    				}*/
        		
    			}
    			
    		  }
    		}

    		  /**
    		 * SL-18007 setting so price change history if there is a change in the
    		 * so spend limit labour during Edition of service order from a group order.
    		 */
    		if (null != so.getSoPriceChangeHistory()) {

    			logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());
    			
    			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
    					
    			// set specific components before passing to the generic method
    			newSOPriceChangeHistory.setAction(OFConstants.ORDER_EDITION);
    			newSOPriceChangeHistory.setReasonComment(null);
    			
    			String userName = null;
				String userId = null;
				if (processVariables.containsKey(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME) &&
		        		processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME) != null){
					userName=(String)processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME);
				}
				
				if (processVariables.containsKey(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID) &&
						processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID) != null){
						userId=(String)processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
				}
    			
    			// call generic method to save so price change history			
    			PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory,userId, userName);
    		}
    		logger.info("so.getSoId()" + so.getSoId());
    		serviceOrderDao.update(so);
    		
    		if (sendSpendLimitChange(processVariables)
    				&& !effectiveSOId.equals(so.getSoId()))
    			logger.info("effectiveSOId:" + effectiveSOId);
    		logger.info("so.getSoId():" + so.getSoId());
    		logger.info("processVariables:" + processVariables);
    			this.sendSignalToSOProcess(so, SignalType.SPEND_LIMIT_CHANGED, null, getProcessVariableForSendEmail(false));
    	}
    	//group pricing update
    	soGroup.getGroupPrice().setDiscountedSpendLimitLabor(groupLaborSpendLimit.add(groupTotalPermitPrice));
    	logger.info("groupTotalPermitPrice" + groupTotalPermitPrice);
    	soGroup.getGroupPrice().setDiscountedSpendLimitParts(groupPartsSpendLimit);
    	logger.info("groupPartsSpendLimit" + groupPartsSpendLimit);
    	soGroup.getGroupPrice().setFinalSpendLimitLabor(groupLaborSpendLimit.add(groupTotalPermitPrice));
      	logger.info("groupTotalPermitPrice" + groupTotalPermitPrice);
    	soGroup.getGroupPrice().setFinalSpendLimitParts(groupPartsSpendLimit);
    	
    	serviceOrderDao.update(soGroup.getGroupPrice());
    	logger.info("soGroup.getGroupPrice()" + soGroup.getGroupPrice());
    	
	}

    private BigDecimal getProportion(BigDecimal number1, BigDecimal number2){
    	if(number1.doubleValue() == 0 || number2.doubleValue() == 0){
    		
    		return PricingUtil.ZERO;
    	}
    	
    	BigDecimal proportion = new BigDecimal(number1.doubleValue() / number2.doubleValue());
    	
    	return proportion;
    }

    private void changeServiceOrder(ServiceOrder so, ServiceOrder eSO) {
        boolean changeCriteria = false;
        
        //1. service contact
        changeServiceContact(so.getServiceContact(), eSO.getServiceContact());
        //2. service location
        if (isServiceLocationChanged(eSO.getSoId(), eSO.getServiceLocation())){
            changeServiceLocation(so.getServiceLocation(), eSO.getServiceLocation());
            changeCriteria = true;
        }
        //3. service schedule
        if (isServiceScheduleChanged(eSO.getSoId(), eSO.getSchedule())|| 
        		so.getSchedule().getServiceDateTypeId() != eSO.getSchedule().getServiceDateTypeId() || 
        		!so.getSchedule().getServiceTimeStart().equals(eSO.getSchedule().getServiceTimeStart())){
            changeServiceSchedule(so.getSchedule(), eSO.getSchedule());
            // To fix intermittent behavior issue of the @PrePersist, @PreUpdate method call of ServiceOrder
            if(so.getSchedule()!= null && so.getServiceLocationTimeZone()!= null ){
            	so.getSchedule().populateGMT(so.getServiceLocationTimeZone());
            }
            changeCriteria = true;
        }
        //4. changes search criteria
        if (changeCriteria)
            changeSearchCriteria(so.getSoId(), eSO.getServiceLocation(), eSO.getSchedule().getServiceDate1());
        //save service order
        serviceOrderDao.update(so);
    }

	private SOElementCollection getSOElementForOrderSignal(Set<GroupRoutedProvider> routedProviders){
		SOElementCollection soec = new SOElementCollection();
		for (GroupRoutedProvider pi : routedProviders){
			RoutedProvider rp = new RoutedProvider();
			rp.setVendorId(pi.getVendorId());
			rp.setProviderResourceId(pi.getProviderResourceId());
			soec.addElement(rp);
		}
		return soec;
	}

	private void changeSearchCriteria(String soId, SOLocation serviceLocation, Date serviceDate1) {
        ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
        sop.setCity(serviceLocation.getCity());
        sop.setServiceDate1(serviceDate1);
        sop.setState(serviceLocation.getState());
        sop.setZip(serviceLocation.getZip());
        sop.setStreet1(serviceLocation.getStreet1());
        sop.setStreet2(serviceLocation.getStreet2());
        serviceOrderProcessDao.update(sop);
    }

    private void saveRoutedProviders(Set<GroupRoutedProvider> source, ServiceOrder serviceOrder){
        clearRoutedProviders(serviceOrder);
        List<RoutedProvider> routedProviders = serviceOrder.getRoutedResources();

        for (GroupRoutedProvider rp : source) {
            RoutedProvider routedProvider = new RoutedProvider();
            routedProvider.setRoutedDate(new Date());
            routedProvider.setProviderResourceId(rp.getProviderResourceId());
            routedProvider.setVendorId(rp.getVendorId());
            routedProvider.setSpnId(serviceOrder.getSpnId());
            routedProvider.setTierId(1);
            routedProvider.setPriceModel(serviceOrder.getPriceModel());
            routedProvider.setServiceOrder(serviceOrder);
            routedProviders.add(routedProvider);
        }
    }

    private void clearRoutedProviders(ServiceOrder serviceOrder){
        List<RoutedProvider> routedProviders = serviceOrder.getRoutedResources();
        for(RoutedProvider routedProvider : routedProviders){
            if(routedProvider instanceof SOChild){
                ((SOChild)routedProvider).setServiceOrder(null);
            }
            serviceOrderDao.delete(routedProvider);
        }
        routedProviders.clear();
    }

    protected boolean isServiceLocationChanged(String soId, SOLocation source){
        ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
        logger.info("Service location = " + source);
        logger.info("Service order process = " + sop);
        if (sop.getCity().equals(source.getCity())
                && sop.getStreet1().equals(source.getStreet1())
                && sop.getStreet2().equals(source.getStreet2())
                && sop.getState().equals(source.getState())
                && sop.getZip().equals(source.getZip())){
            return false;
        }
        return true;
    }
    
    private void changeServiceLocation(SOLocation target, SOLocation source) {
        target.setAptNumber(source.getAptNumber());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
        target.setLocationName(source.getLocationName());
        target.setLocationNote(source.getLocationNote());
        target.setState(source.getState());
        target.setStreet1(source.getStreet1());
        target.setStreet2(source.getStreet2());
        target.setZip(source.getZip());
        target.setZip4(source.getZip4());
    }

    protected boolean isServiceScheduleChanged(String soId, SOSchedule source){
        ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
        logger.info("Service Schedule = " + source.getServiceDate1() + "  ---  millis " + source.getServiceDate1().getTime());
        logger.info("Service order process = " + sop.getServiceDate1() + "  ---  millis " + sop.getServiceDate1().getTime());
        if (sop.getServiceDate1().compareTo(source.getServiceDate1())==0)
            return false;
        return true;
    }
    private void changeServiceSchedule(SOSchedule target, SOSchedule source) {
        target.setServiceDate1(source.getServiceDate1());
        target.setServiceDate2(source.getServiceDate2());
        target.setServiceDateTypeId(source.getServiceDateTypeId());
        target.setServiceTimeEnd(source.getServiceTimeEnd());
        target.setServiceTimeStart(source.getServiceTimeStart());
        if(source.getServiceDateTypeId()== SOScheduleType.SINGLEDAY && source.getServiceDate2()== null ){
        	target.setServiceDateGMT2(null); 
        	target.setServiceTimeEndGMT(null);
        }  

    }

    private void changeServiceContact(SOContact target, SOContact source) {
        target.setBusinessName(source.getBusinessName());
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setHonorific(source.getHonorific());
        target.setLastName(source.getLastName());
        target.setMi(source.getMi());
        target.setSuffix(source.getSuffix());
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        ServiceOrder eSO = getEffectiveServiceOrder(processVariables);
        //check if the criteria has changed and if it has than set the number of orders to zero
        //this will end the workflow
        if (isServiceLocationChanged(eSO.getSoId(), eSO.getServiceLocation())
                || isServiceScheduleChanged(eSO.getSoId(), eSO.getSchedule())){
            processVariables.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, Integer.valueOf(0));
        }
        clearEffectiveServiceOrder(processVariables);
    }

    @Override
    protected boolean sendSpendLimitChange(Map<String, Object> processVariables) {
        String spendLimit = SOCommandArgHelper.extractStringArg(processVariables, 2);
        logger.info("spendLimit" + spendLimit);
        return spendLimit.equals("spendLimit");
    }

    protected boolean skipPostForAffectingOrder(Map<String, Object> processVariables) {
        String affectingOrder = SOCommandArgHelper.extractStringArg(processVariables, 3);
        return !StringUtils.isBlank(affectingOrder);
    }

}
