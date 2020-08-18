package com.servicelive.orderfulfillment.signal; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.client.SimpleRestClient;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.so.TaskStatus;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.SOLoggingCmdHelper;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOChild;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOSalesCheckItems;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class EditSignal extends Signal {

	private static final String GMT_STRING = "GMT";
	protected ServiceOrderNoteUtil noteUtil;
    protected SOLoggingCmdHelper soLoggingCmdHelper;
	private Identification id;
	public static final BigDecimal ZERO = new BigDecimal("0.00");
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {	
		logger.info("inside accessMiscParams of EditSignal");
		this.id = id;
		logger.info("EditSignal setting IS_CURRENTLY_POSTED for SOID: "+ so.getSoId());
		if(OFConstants.POSTED_STATUS.equals(so.getWfStateId())){
			miscParams.put(ProcessVariableUtil.IS_CURRENTLY_POSTED, true);
		}
		else{
			miscParams.put(ProcessVariableUtil.IS_CURRENTLY_POSTED, false);
		}
	}
    
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if (!(soe instanceof ServiceOrder)){
			returnVal.add("Expected Service order for editing but found some other SOElement");
		}
        return returnVal;
    }

    @Override
	protected void update(SOElement soe, ServiceOrder so){
    	logger.info("SL-16136:Inside EditSignal.update() ");
		logger.debug("Inside EditSignal.update()");
		
		ServiceOrder source = (ServiceOrder)soe;
		BuyerFeatureLookup buyerFeatureLookup = quickLookup.getBuyerFeatureLookup();
		//source.setScopeChange(true);
		if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(
				BuyerFeatureSetEnum.SCOPE_CHANGE, so.getBuyerId())) {
			source.setScopeChange(true);
		}
		List<SOFieldsChangedType> changes = so.compareTo(source);
		/*
		
		if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(
				BuyerFeatureSetEnum.PERMIT, so.getBuyerId())) {
			if (null != so.getTasks()) {
				if ((!source.isUpdate())
						|| (changes
								.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
								|| changes
										.contains(SOFieldsChangedType.TASKS_ADDED) || changes
								.contains(SOFieldsChangedType.TASKS_DELETED))) {
					updateTask(source, so);
				}
			}
		}
		*/
		//SL-19728 Setting soworkflowcontrols
		
		
		if (so.getWfStateId() == null) {
			logger.info("service order state is null ");
			SOWorkflowControls soWorkflowControls = null;
        	if(so.getSOWorkflowControls() == null){
        		soWorkflowControls = new SOWorkflowControls();
        	}else{
        		soWorkflowControls = so.getSOWorkflowControls();
        	}
			if (null!=source.getSOWorkflowControls()) {
				logger.info("setting non Funded indicator ");
				soWorkflowControls.setNonFundedInd(source.getSOWorkflowControls().getNonFundedInd());
				logger.info("setting InvoicePartsInd indicator ");
				soWorkflowControls.setInvoicePartsInd(source.getSOWorkflowControls().getInvoicePartsInd());
				so.setSOWorkflowControls(soWorkflowControls);
			}
		}
		
		boolean relayServicesNotifyFlag = isRelayServicesNotificationNeeded(so);
		
		if (changes.contains(SOFieldsChangedType.SCHEDULE)){
			logger.info("changing schedule");
			
			// send webhook for expired to posted state
			if (relayServicesNotifyFlag && null != so.getWfStateId()
					&& so.getWfStateId().intValue() == OrderfulfillmentConstants.EXPIRED_STATUS) {
				logger.info("Order is in expired state : " + so.getSoId());
				Map<String, String> param = new HashMap<String, String>();

				logger.info("ServiceLocationTimeZone : " + so.getServiceLocationTimeZone());
				if (StringUtils.isNotBlank(so.getServiceLocationTimeZone()) && null != source.getSchedule()) {

					if (source.getSchedule().getServiceDate1() != null
							&& StringUtils.isNotBlank(source.getSchedule().getServiceTimeStart())) {
						Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(source.getSchedule().getServiceDate1(), source
								.getSchedule().getServiceTimeStart(), TimeZone.getTimeZone(so.getServiceLocationTimeZone()));
						String serviceDateGMT1 = sdf.format(TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone(GMT_STRING)));
						String serviceTimeStartGMT = TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone(GMT_STRING));

						param.put("serviceDateGMT1", serviceDateGMT1);

						param.put("serviceTimeStartGMT",serviceTimeStartGMT);

					}

					if (source.getSchedule().getServiceDate2() != null
							&& StringUtils.isNotBlank(source.getSchedule().getServiceTimeEnd())) {
						Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(source.getSchedule().getServiceDate2(), source
								.getSchedule().getServiceTimeEnd(), TimeZone.getTimeZone(so.getServiceLocationTimeZone()));
						String serviceDateGMT2 = sdf.format(TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone(GMT_STRING)));
						String serviceTimeEndGMT = TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone(GMT_STRING));

						param.put("serviceDateGMT2", serviceDateGMT2);
						param.put("serviceTimeEndGMT", serviceTimeEndGMT);
					}
				}

				sendNotification(so, "ORDER_CHANGED_TO_POSTED_STATE", param);
			}
			
			updateSchedule(source, so);
		}
		if (changes.contains(SOFieldsChangedType.SERVICE_CONTACT)){
			logger.info("changing service contact");
			updateServiceContact(source.getServiceContact(), so);
		}
		if(changes.contains(SOFieldsChangedType.BUYER_ASSOCIATE_CONTACT)){
			logger.info("changing buyer associate contact");
			updateOtherContact(source.getBuyerAssociateContact(), so.getBuyerAssociateContact(), so);
		}
		if(changes.contains(SOFieldsChangedType.BUYER_SUPPORT_CONTACT)){
			logger.info("changing buyer support contact");
			updateOtherContact(source.getBuyerSupportContact(), so.getBuyerSupportContact(), so);
		}
//no need to change vendor contact since from UI it will never be changed
//and from batch update the source will never have the vendor contact		
//		if(changes.contains(SOFieldsChangedType.VENDOR_RESOURCE_CONTACT)){
//			logger.debug("changing vendor resource contact");
//			updateOtherContact(source.getVendorResourceContact(), so.getVendorResourceContact(), so);
//		}
		if(changes.contains(SOFieldsChangedType.END_USER_CONTACT)){
			logger.info("changing end user contact");
			updateOtherContact(source.getEndUserContact(), so.getEndUserContact(), so);
		}
		if (changes.contains(SOFieldsChangedType.SERVICE_LOCATION)){
			logger.info("changing service location");
			updateServiceLocation(source.getServiceLocation(), so, changes.contains(SOFieldsChangedType.SERVICE_LOCATION_ZIP_CHANGED));
	    }
		if (changes.contains(SOFieldsChangedType.BUYER_ASSOCIATE_LOCATION)){
			logger.info("changing buyer associate location");
			updateOtherLocation(source.getBuyerAssociateLocation(), so.getBuyerAssociateLocation(), so);
		}
		if (changes.contains(SOFieldsChangedType.BUYER_SUPPORT_LOCATION)){
			logger.info("changing buyer support location");
			updateOtherLocation(source.getBuyerSupportLocation(), so.getBuyerSupportLocation(), so);
		}
//no need to change vendor location since from UI it will never be changed
//and from batch update the source will never have the vendor location		
//		if (changes.contains(SOFieldsChangedType.VENDOR_RESOURCE_LOCATION)){
//			logger.debug("changing vendor resource location");
//			updateOtherLocation(source.getVendorResourceLocation(), so.getVendorResourceLocation(), so);
//		}

		if (changes.contains(SOFieldsChangedType.PARTS)){
        	logger.info("part change");
        	/**SPM-1346:Compare Parts based on modelNumber,description,manufacturer
    		 * Parts from updateFile:source.getParts()
    		 * Parts existing in DB:so.getParts().
    		 * Compare both add new parts to the parts List in DB(so.getParts())
    		 *  */
        	 updateParts(source, so);
        	
        }
        if (changes.contains(SOFieldsChangedType.SO_DESCRIPTION)){
        	logger.info("description is changed");
             updateDescription(so, source);
        } 
        boolean reprice=false;
    	if (source.isScopeChange()&&((changes.contains(SOFieldsChangedType.SCHEDULE)&& source.isUpdate())
				|| changes
						.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
				|| changes
						.contains(SOFieldsChangedType.SERVICE_LOCATION_ZIP_CHANGED)) &&(so.getWfStateId()!=null && so.getWfStateId()<150))
			
		{
    		reprice=true;
    		so.setRepost(true);
    	}
    	
    	if (changes.contains(SOFieldsChangedType.TASKS_ADDED) 
        		|| changes.contains(SOFieldsChangedType.TASKS_DELETED)){
    	if(so.getWfStateId()!=null && so.getWfStateId()==165){
    		int noOfTasks =0;
    		double price=0.0d;
    		for (SOTask task : so.getActiveTasks()) {
    			if(task.getFinalPrice()!=null)
    			{
    				price=price+ task.getFinalPrice().doubleValue();
    			}			
    		}

    		// for service order task updation
    		if(so.getPriceType().equalsIgnoreCase(OrderfulfillmentConstants.TASK_LEVEL_PRICING)){

    			double totalFinalPriceOfTasks = 0.0;

    			for (SOTask task : so.getActiveTasks()) {

    				if (task.getFinalPrice() != null && (task.getTaskType().equals(
    						OrderfulfillmentConstants.PRIMARY_TASK)
    						|| task.getTaskType().equals(
    								OrderfulfillmentConstants.NON_PRIMARY_TASK)
    				)) {

    					totalFinalPriceOfTasks = totalFinalPriceOfTasks
    					+ task.getFinalPrice().doubleValue();
    					noOfTasks +=1;
    				}
    			}

    			double additionalAmount = 0.0;
    			BigDecimal soPrice = so.getSpendLimitLabor();

    			additionalAmount =soPrice.doubleValue()-price;


    			if (additionalAmount != 0) {
    				for (SOTask task : so.getActiveTasks()) {

    					if (task.getFinalPrice() != null && (task.getTaskType().equals(
    							OrderfulfillmentConstants.PRIMARY_TASK)
    							|| task.getTaskType().equals(
    									OrderfulfillmentConstants.NON_PRIMARY_TASK)
    					)) {
    						
    						double amount = 0;

    						// divide new somaxlabor(the total price for tasks alone
    						// excluding permit task) among tasks.
    						if(null!=task.getFinalPrice()  && totalFinalPriceOfTasks >0) {
    							double percentageShare = task.getFinalPrice().doubleValue() / totalFinalPriceOfTasks * 100;
    							amount = additionalAmount * percentageShare / 100;
    							amount = task.getFinalPrice().doubleValue() + amount;
    							task.setFinalPrice(BigDecimal.valueOf(amount));
    							PricingUtil.addTaskPriceHistory(task, "SYSTEM", "SYSTEM");
    						}// divide additionalAmount equally among skus in case of the initial total task price is 0 
    						else if(null!=task.getFinalPrice() && noOfTasks>0){
    							amount = additionalAmount /noOfTasks;
    							amount = task.getFinalPrice().doubleValue() + amount;
    							task.setFinalPrice(BigDecimal.valueOf(amount));
    							PricingUtil.addTaskPriceHistory(task, "SYSTEM", "SYSTEM");
    						}

    					}
    				}
    			}
    		}
             
    	}
    	
    }
    
    	    	
        if (changes.contains(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED) || changes.contains(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED)){
        	logger.info("pricing change");
        	logger.info("SL-16136:Inside EditSignal  pricing change check");
        	
        	
        	
        	updatePricing(source, so);
        }
        
        
        if (changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
        		|| changes.contains(SOFieldsChangedType.TASKS_ADDED) 
        		|| changes.contains(SOFieldsChangedType.TASKS_DELETED)){
        	logger.info("Scope changes");
        	updateScope(source, so, changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY), changes.contains(SOFieldsChangedType.TASKS_ADDED) 
        		|| changes.contains(SOFieldsChangedType.TASKS_DELETED));
        }
        else{
    		logger.info("Task Change");
      //To handle one to many mapping of tasks for a sku
        	
        	int targetSeqNo=-1;
    		int targetTaskNo=0;
    		for (SOTask task : so.getNonDeletedTasks()) {
    		
    			if(null!=task.getSequenceNumber() && task.getSequenceNumber().intValue()==targetSeqNo )
    			{    				
    				targetTaskNo=targetTaskNo +1;
    			
    			}else{
    				targetTaskNo=1;
    			}
    			task.setTaskNo(targetTaskNo);
    			if(null!=task.getSequenceNumber())
    			{
    			targetSeqNo=task.getSequenceNumber().intValue();
    			}
    		}
    		
    		int seqNo=-1;
    		int taskNo=0;
    		
    		for (SOTask task : source.getNonDeletedTasks()) {
        		
    			if(null!=task.getSequenceNumber() && task.getSequenceNumber().intValue()==seqNo )
    			{    				
    				taskNo=taskNo +1;
    			}else{
    				taskNo=1;
    			}
    			task.setTaskNo(taskNo);
    			if(null!=task.getSequenceNumber())
    			{
    			seqNo=task.getSequenceNumber().intValue();
    			}
    		}
    		
    		updateTask(source, so, false);
    	}
        
        if (changes.contains(SOFieldsChangedType.PROVIDER_INSTRUCTIONS)){
        	logger.info("provider instruction change");
            updateProviderInstruction(so, source);
        }
        
        if (changes.contains(SOFieldsChangedType.ADDON_ADDED)){
        	logger.info("addon change");
              if(source.isUpdate()){
        		   if(null != so.getAddons()&& null != source.getAddons()){
                	    for(SOAddon addOnsFromFile:source.getAddons()){
                	    	if(null!= addOnsFromFile && addOnsFromFile.isNewAddOne()){
                	    		/**Adding new addOns from update file to existing db addOnsList*/
                	    		so.getAddons().add(addOnsFromFile);
                	    	  }
                	    	}
        		   }else if(null == so.getAddons()&& null!= source.getAddons()){
                 		   so.setAddons(source.getAddons());
                        }
                 	if(null != so.getAddons() && null==source.getAddons()){
                 		/**No Need to delete the existing addOns*/
                 		// deleteChildren(so.getAddons());
                 	}
        		}/**Existing code to be invoked while completion*/
        		else{
        			if(so.getAddons() != null) deleteChildren(so.getAddons());
                	if(source.getAddons() != null) so.setAddons(source.getAddons());
        		}
        }
        if (changes.contains(SOFieldsChangedType.CUSTOM_REF)){
        	logger.info("custom reference change");
        	updateCustomRef(source, so);
        }
        if (changes.contains(SOFieldsChangedType.SO_TITLE)){
        	logger.info("title change");
        	so.setSowTitle(source.getSowTitle());
        }
        if (changes.contains(SOFieldsChangedType.BUYER_TERMS_COND)){
        	logger.debug("buyer term changes change");
        	so.setBuyerTermsCond(source.getBuyerTermsCond());
        }
		if (changes.contains(SOFieldsChangedType.ROUTED_RESOURCES)) {
			logger.info("routed provider change");

			// SL-21455 trigger order release to firm
			if (relayServicesNotifyFlag && null != so.getWfStateId()
					&& (so.getWfStateId() == OrderfulfillmentConstants.POSTED_STATUS 
						|| so.getWfStateId() == OrderfulfillmentConstants.EXPIRED_STATUS)) {

				List<Integer> newVendorIdList = new ArrayList<Integer>();
				List<Integer> oldVendorIdList = serviceOrderDao.getFirmIdNotReleaseSO(so.getSoId());
				// List<Integer> oldVendorIdNotSoReleaseList = serviceOrderDao.getFirmIdNotReleaseSO(so.getSoId());

				if (null == oldVendorIdList) {
					oldVendorIdList = new ArrayList<Integer>();
				}

				logger.info("oldVendorIdList size" + oldVendorIdList.size());

				List<RoutedProvider> sourceList = source.getRoutedResources();
				if (null != sourceList) {
					for (RoutedProvider sp : sourceList) {
						Integer vendorIdSource = sp.getVendorId();
						if (!(newVendorIdList.contains(vendorIdSource))) {
							newVendorIdList.add(vendorIdSource);
						}
					}
				}

				boolean newVendor = false;
				logger.info("newVendorIdList size " + newVendorIdList.size());

				List<Integer> uniqueNewVendorsList = new ArrayList<Integer>();
				for (Integer vendor : newVendorIdList) {
					if (!(oldVendorIdList.contains(vendor))) {
						newVendor = true;
						uniqueNewVendorsList.add(vendor);
					}
				}
				logger.info("newVendor " + newVendor);
				logger.info("uniqueNewVendorsList size " + uniqueNewVendorsList.size());

				boolean oldVendor = false;
				logger.info("oldVendorIdList size" + oldVendorIdList.size());

				List<Integer> uniqueOldVendorsList = new ArrayList<Integer>();
				for (Integer vendor : oldVendorIdList) {
					if (!(newVendorIdList.contains(vendor))) {
						oldVendor = true;
						uniqueOldVendorsList.add(vendor);
					}
				}
				logger.info("oldVendor " + oldVendor);
				logger.info("uniqueOldVendorsList size" + uniqueOldVendorsList.size());

				// notification for released firms
				if (oldVendor) {

					String vendorDetails = serviceOrderDao.getVendorBNameList(uniqueOldVendorsList);

					Map<String, String> param = new HashMap<String, String>();

					if (StringUtils.isNotEmpty(vendorDetails)) {
						param.put("firmsdetails", vendorDetails);
					}

					sendNotification(so, "ORDER_RELEASED_BY_FIRM", param);
				}

				// notification for new posted firm
				if (newVendor) {

					String vendorDetails = serviceOrderDao.getVendorBNameList(uniqueNewVendorsList);

					Map<String, String> param = new HashMap<String, String>();

					if (StringUtils.isNotEmpty(vendorDetails)) {
						param.put("firmsdetails", vendorDetails);
					}

					sendNotification(so, OrderfulfillmentConstants.ORDER_REPOSTED_TO_NEW_FIRM, param);
				}
			}

			changeRoutedProviders(source, so);
		}
        
        if (changes.contains(SOFieldsChangedType.TIER_ROUTED_RESOURCES)){
        	logger.info("tier routed provider change");
        	changeTierRoutedProviders(source, so);
        }
        if (changes.contains(SOFieldsChangedType.PROVIDER_SERVICE_CONFIRM_IND)) {
        	logger.info("provider service confirm indicator change");
        	so.setProviderServiceConfirmInd(source.getProviderServiceConfirmInd());
        }
        if (changes.contains(SOFieldsChangedType.PRICE_MODEL)) {
        	logger.info("pricing model change");
        	so.setPriceModel(source.getPriceModel());        	
        }
        if (changes.contains(SOFieldsChangedType.SHARE_CONTACT_IND)) {
        	logger.info("share contact indicator change");
        	so.setShareContactIndicator(source.getShareContactIndicator());
        }
        
        if (changes.contains(SOFieldsChangedType.SEALED_BID_ORDER_IND) || changes.contains(SOFieldsChangedType.TIER_ROUTE_IND) || changes.contains(SOFieldsChangedType.PERF_SCORE)) {
        	logger.info("sealed bid indicator change");
        	SOWorkflowControls soWorkflowControls = null;
        	if(so.getSOWorkflowControls() == null){
        		soWorkflowControls = new SOWorkflowControls();
        	}else{
        		soWorkflowControls = so.getSOWorkflowControls();
        	}
        	
        	soWorkflowControls.setSealedBidIndicator(source.getSOWorkflowControls().getSealedBidIndicator());
        	soWorkflowControls.setTierRouteInd((source.getSOWorkflowControls().getTierRouteInd()));
        	logger.info("TIER ROUTE IND::"+source.getSOWorkflowControls().getTierRouteInd());
        	soWorkflowControls.setPerformanceScore((source.getSOWorkflowControls().getPerformanceScore()));
        	logger.info("PERF SCORE::"+source.getSOWorkflowControls().getPerformanceScore());
        	so.setSOWorkflowControls(soWorkflowControls);
        }
        
        
        
        /** 
		 * SL-18007 setting so price change history if there is a change in the so spend limit labour 
		 * during order edition/scope change through file injection in all states  
		 */
        if ((ZERO.compareTo(source.getSpendLimitLabor())==0) ||(ZERO.compareTo(source.getSpendLimitParts())==0)|| changes.contains(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED) || changes.contains(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED) 
        		|| OrderfulfillmentConstants.ZERO_PRICE_BID.equals(source.getPriceModel().toString())){
        	
        	if (null != so.getSoPriceChangeHistory()){
    			
    			logger.info("18007: so price change history contins:" + so.getSoPriceChangeHistory());
    			    					
    			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
    			String modifiedBy = PricingUtil.SYSTEM;
    			String modifiedByName = PricingUtil.SYSTEM;
    			//set specific components before passing to the generic method
    			if (null != so.getWfStateId() && 165 == so.getWfStateId().intValue()){
    				newSOPriceChangeHistory.setAction(OFConstants.SCOPE_UPDATED_PENDING_CANCEL);
    			} 
    			newSOPriceChangeHistory.setReasonComment(null);
    			//newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);
    			if (source.isUpdate()){
    				modifiedBy = PricingUtil.SYSTEM;
        			modifiedByName = PricingUtil.SYSTEM;
        			if (null != so.getWfStateId() && 165 == so.getWfStateId().intValue()){
        				newSOPriceChangeHistory.setAction(OFConstants.SCOPE_UPDATED_PENDING_CANCEL);
        			} else{
        				newSOPriceChangeHistory.setAction(OFConstants.SCOPE_UPDATED);
        			}
    			}else{
    				logger.info("saveAsDraftFromFE : "+source.isSaveAsDraftFE());
    				logger.info("ispost::"+source.isPost());
    				if (source.isSaveAsDraftFE()){
    					newSOPriceChangeHistory.setAction(OFConstants.ORDER_EDITION);
    				}
    				if (source.isPost()){
    					newSOPriceChangeHistory.setAction(OFConstants.ORDER_POSTING);
    				}
    				/*
    				if(id!= null && id.getBuyerResourceId() != null){
    						modifiedBy = String.valueOf(id.getBuyerResourceId());
    					}
    				else if (StringUtils.isNotBlank(source.getModifiedBy())){
    						modifiedBy = source.getModifiedBy();
    					}
        			if(id!= null && id.getFullname() != null){
    						modifiedByName = id.getFullname();
    					}   
        			else if (StringUtils.isNotBlank(source.getModifiedByName())){
        					modifiedByName = source.getModifiedByName();
        				}
         			*/
        			if(StringUtils.isNotBlank(String.valueOf(id.getBuyerResourceId()))){
    					modifiedBy = String.valueOf(id.getBuyerResourceId());
    				}
    				else if (StringUtils.isNotBlank(source.getModifiedBy())){
    					modifiedBy = source.getModifiedBy();
    				}
        			if(StringUtils.isNotBlank(id.getFullname())){
    					modifiedByName = id.getFullname();
    				}   
        			else if(StringUtils.isNotBlank(source.getModifiedByName())){
        				modifiedByName = source.getModifiedByName();
        			}
        			logger.info("ModifiedBy : " + modifiedBy);
        			logger.info("ModifiedByName : " + modifiedByName);
        			
    			}
    			
    			if(0 == so.getSoPriceChangeHistory().size()){
    				PricingUtil.setSOPriceChangeHistory(so, modifiedBy, modifiedByName, source.isPost());
    			}else{
        			// call generic method to save so price change history
        			PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, modifiedBy, modifiedByName);
    			}

    		}
        }
                    
        //update buyer contact id if the source is not null
        if(source.getBuyerContactId()!=null) so.setBuyerContactId(source.getBuyerContactId());
        
        if(null != source.getPartsSupplier()) so.setPartsSupplier(source.getPartsSupplier());
        so.setLockEditInd(0); //Since we are already completing Edit - the indicator will always go in as false!
        if ( source != null && source.getSpnId()!= null && source.getSpnId() > 0){
        	so.setSpnId(source.getSpnId());
        }else{
        	so.setSpnId(null);
        }
        
		serviceOrderDao.update(so);
	}

    protected void updateDescription(ServiceOrder so, ServiceOrder source) {
        so.setSowDs(source.getSowDs());
        SONote note = noteUtil.getNewNote(so, "UpdateDescription", null);
        serviceOrderDao.save(note);
    }

    protected void updateProviderInstruction(ServiceOrder so, ServiceOrder source) {
        so.setProviderInstructions(source.getProviderInstructions());
    }

    protected void updateServiceContact(SOContact source, ServiceOrder so) {
		SOContact target = so.getServiceContact();
		if(target == null){
			so.addContact(source);
			//serviceOrderDao.update(so);
		}else{
			updateContact(source, target);
		}
	}
    
    //will never hard delete the tasks, update the tasks if found else mark status as deleted
    protected void updateTask(ServiceOrder source, ServiceOrder target, boolean updatePrice){
    	if(source.isUpdate()){
    		logger.info("SL-10863 : update file");
    		if(source.isScopeChange()){
    			logger.info("SL-10863 : scope change feature set on");
    			List<SOTask> tasks = target.getTasks();
    			List<SOTask> newTasks = source.getTasksForScopeChangeComparison();
    			List<SOTask> existingTasks = target.getTasksForScopeChangeComparison();
    			if(existingTasks != null && newTasks!= null){
    				int lastIteration = newTasks.size();
    				int iteration = 1;
    				for(SOTask newTask :newTasks){
    					boolean taskFound = false;
    					for(SOTask oldTask: existingTasks){
    						if(!oldTask.isMatched()){
    						//check for a match using SKU - Sequence Number 
    							if(newTask.getSequenceNumber().equals(oldTask.getSequenceNumber())
    									&& newTask.getExternalSku().equals(oldTask.getExternalSku()) && newTask.getTaskNo()==oldTask.getTaskNo() ){
    								oldTask.setMatched(true);
    								taskFound = true;						
    								if(updatePrice){  //price should be changed only if there is change in SKU
    									//copy task data including price
    									copyTask(oldTask,newTask,true,source.getModifiedBy(),source.getModifiedByName());
    								}else{
    									copyTask(oldTask,newTask,false,source.getModifiedBy(),source.getModifiedByName());
    								}
    							}else{
    								if(iteration == lastIteration ){
    									//if old task not found in new task list then set status as deleted
    									oldTask.setTaskStatus(TaskStatus.DELETED.name());
    									//SL-20167 : updating the purchase amount
    									logger.info("SL-20167 : updating purchase amount for deleted task");    									
    									for(SOSalesCheckItems item : source.getItems()){
    										if(null != oldTask.getExternalSku() && null != item.getItemNumber()){
    										
    											logger.info("SL-20167 :  oldTask.getExternalSku: " + oldTask.getExternalSku() + "--> item.getItemNumber: " +  item.getItemNumber());    											
    						        			if(oldTask.getExternalSku().equals(item.getItemNumber())){
    						        				BigDecimal purchaseAmt = oldTask.getPurchaseAmount();
    						        				logger.info("SL-20167 : old price for deleted " + oldTask.getExternalSku() + " : " + purchaseAmt);
    						        				if(null != item.getPurchaseAmt()){
    						        					purchaseAmt = new BigDecimal(item.getPurchaseAmt());
    						        					logger.info("SL-20167 : new price for deleted " + oldTask.getExternalSku() + " : " + purchaseAmt);
    						        				}
    						        				oldTask.setPurchaseAmount(purchaseAmt);
    						        			}
    						        		}
    									}
    									logger.info("SL-10863 : deleting task");
    								}
    							}
    						}
    					}
    					if(!taskFound){
    						//if new task not matched to any existing task, then add it to existing task
    						newTask.setMatched(true);
    						newTask.setTaskSeqNum(tasks.size() + 1);
    						newTask.setSortOrder(tasks.size());
    						newTask.setServiceOrder(target);
    						assignSoIdToTaskHistory(newTask);     						
    						tasks.add(newTask);
    						logger.info("SL-10863 : adding new task");
    					}
    					iteration +=1;
    				}
    			}
    			//This is on the assurance that only one delivery task will be present per SO, Need to change other wise.
    			SOTask existingDTask = target.getDeliveryTask();
    			//to check for canceled delivery task
    			SOTask canceledDTask = target.getCanceledDeliveryTask();
    			SOTask newDTask = source.getDeliveryTask();
    			if(newDTask != null){
    				if(existingDTask != null){
    					//if (!(target.getWfStateId()!=null && target.getWfStateId().intValue()==165))
    					//{
    					if(existingDTask.compareDeliveryTask(newDTask) != 0){
    						existingDTask.setTaskStatus(TaskStatus.DELETED.name());
    						newDTask.setTaskSeqNum(tasks.size() + 1);
    						tasks.add(newDTask);
    					}
    					//}
    				}else{
    					//if there are canceled delivery task,set the task status as deleted
    					if (null != canceledDTask){
    						canceledDTask.setTaskStatus(TaskStatus.DELETED.name());
    					}
    					newDTask.setTaskSeqNum(tasks.size() + 1);
    					newDTask.setServiceOrder(target);
    					tasks.add(newDTask);
    				}
    			}else{
    				//if (!(target.getWfStateId()!=null && target.getWfStateId().intValue()==165))
					//{
    				if(existingDTask != null){
    					existingDTask.setTaskStatus(TaskStatus.DELETED.name());
    				}
    				//if there are no delivery task in update, canceled delivery task should be deleted
    				if (null != canceledDTask){
						canceledDTask.setTaskStatus(TaskStatus.DELETED.name());
					}
					//}
    			}
    		}else
    		{
    			//Write code for buyers other than RI
    			logger.info("SL-10863 : scope change feature set off");
    			List<SOTask> tasks = target.getTasks();
    			List<SOTask> newTasks = source.getActiveTasks();
    			List<SOTask> existingTasks = target.getActiveTasks();
    			if(existingTasks != null && newTasks!= null){
    				int lastIteration = newTasks.size();
    				int iteration = 1;
    				for(SOTask newTask :newTasks){
    					boolean taskFound = false;
    					for(SOTask oldTask: existingTasks){
    						if(!oldTask.isMatched()){
    						//check for a match using SKU - Sequence Number 
    							if(newTask.compareTask(oldTask) == 0){
    								oldTask.setMatched(true);
    								taskFound = true;
    								copyTask(oldTask,newTask,true,source.getModifiedBy(),source.getModifiedByName());
    							}else{
    								if(iteration == lastIteration ){
    									//if old task not found in new task list then set status as deleted
    									oldTask.setTaskStatus(TaskStatus.DELETED.name());
    									logger.info("SL-10863 : deleting task");
    								}
    							}
    						}
    					}
    					if(!taskFound){
    						//if new task not matched to any existing task, then add it to existing task
    						newTask.setMatched(true);
    						newTask.setTaskSeqNum(tasks.size() + 1);
    						newTask.setSortOrder(tasks.size());
    						newTask.setServiceOrder(target);
    						tasks.add(newTask);
    						logger.info("SL-10863 : adding new task");
    					}
    					iteration +=1;
    				}
    			}
    		}
    	}else{
    		//Tasks updated through frontend
    		logger.info("SL-10863 : update through frontend");
    		List<SOTask> tasks = target.getTasks();
			List<SOTask> existingTasks = target.getActiveTasks();
			List<SOTask> newTasks = source.getActiveTasks();
		
			if(existingTasks != null && newTasks!= null){
				int lastIteration = newTasks.size();
				int iteration = 1;
				for(SOTask newTask :newTasks){
					boolean taskFound = false;
					for(SOTask oldTask: existingTasks){
						if(!oldTask.isMatched()){
						//check for a match using taskId
							if(newTask.getTaskId() != null && newTask.getTaskId().equals(oldTask.getTaskId())){
								oldTask.setMatched(true);
								taskFound = true;
								if(!newTask.compareFrontEndTask(oldTask)){									
									copyTask(oldTask,newTask,true,source.getModifiedBy(),source.getModifiedByName());
								}
							}else{
								if(iteration == lastIteration ){
									//if old task not found in new task list then set status as deleted
									oldTask.setTaskStatus(TaskStatus.DELETED.name());
								}
							}
						}
					}
					if(!taskFound){
						//if new task not matched to any existing task, then add it to existing task
						newTask.setMatched(true);
						newTask.setTaskSeqNum(tasks.size() + 1);
						newTask.setSortOrder(tasks.size());
						newTask.setServiceOrder(target);
						PricingUtil.addTaskPriceHistory(newTask, source.getModifiedBy(),source.getModifiedByName());
						tasks.add(newTask);
					}
					iteration +=1;
				}
			}
    	
    	}
    }
    
    private void copyTask(SOTask oldTask, SOTask newTask, boolean copyPrice, String modifiedBy, String modifiedByName){
    	oldTask.setTaskName(newTask.getTaskName());
		oldTask.setTaskComments(newTask.getTaskComments());
		oldTask.setSkillNodeId(newTask.getSkillNodeId());
		oldTask.setServiceTypeId(newTask.getServiceTypeId());
		oldTask.setSortOrder(newTask.getSortOrder());
		/**SL-19994:Retain permit type for completed permit task when update file in work started*/
		if(!("COMPLETED".equals(oldTask.getTaskStatus()))){
		   oldTask.setPermitType(newTask.getPermitType());
		}
		oldTask.setPrimaryTask(newTask.isPrimaryTask());  
		//SL-10267 
		oldTask.setPurchaseAmount(newTask.getPurchaseAmount());
		if(copyPrice){
			oldTask.setPrice(newTask.getPrice());
			oldTask.setRetailPrice(newTask.getRetailPrice());
			oldTask.setSellingPrice(newTask.getSellingPrice());
			if((oldTask.getFinalPrice() == null && newTask.getFinalPrice()!= null) || 
					(oldTask.getFinalPrice() != null && oldTask.getFinalPrice().compareTo(newTask.getFinalPrice()) != 0)){
				
				if(!oldTask.getTaskStatus().equals("CANCELED"))
				{
				oldTask.setFinalPrice(newTask.getFinalPrice());
				PricingUtil.addTaskPriceHistory(oldTask, modifiedBy, modifiedByName);
				}
			}
		}
    }
        
	private void updateContact(SOContact source, SOContact target) {
		target.setBusinessName(source.getBusinessName());
		target.setEmail(source.getEmail());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setMi(source.getMi());
		target.setSuffix(source.getSuffix());
		target.setContactTypeId(source.getContactTypeId()); //  Mapped for Contact id issue
		target.setEntityId(source.getEntityId());//  Mapped for Contact id issue
		target.setEntityType(source.getEntityType());//  Mapped for Contact id issue
		//delete phone and recreate them
		for(SOPhone phone : target.getPhones()){
			phone.setContact(null);
			serviceOrderDao.delete(phone);
		}
		target.getPhones().clear();
		target.setPhones(source.getPhones());
	}

	protected void updateOtherLocation(SOLocation source, SOLocation target, ServiceOrder so) {
    	if(target != null && source == null){
    		deleteLocation(target, so);
    	}else if(target == null && source != null){
    		so.addLocation(source);
    	} else {
    		updateLocation(source, target);
    	}	
	}

    protected void deleteLocation(SOLocation soc, ServiceOrder so){
		soc.setServiceOrder(null);
		so.getLocations().remove(soc);
		serviceOrderDao.delete(soc);
    }

    protected void deleteContact(SOContact soc, ServiceOrder so){
		soc.setServiceOrder(null);
		so.getContacts().remove(soc);
		serviceOrderDao.delete(soc);
    }
    
	protected void updateScope(ServiceOrder source, ServiceOrder target, boolean primaryCategoryChanged, boolean tasksChanged) {
		if (primaryCategoryChanged){
    		logger.debug("primary category change");
    		target.setPrimarySkillCatId(source.getPrimarySkillCatId());
    	}

    	if(tasksChanged){
    		logger.info("Update Scope");
    		// Price should not be updated for existing tasks after accepted.
    		
        	//To handle one to many mapping of tasks for a sku

    		int targetSeqNo=-1;
    		int targetTaskNo=0;
    		for (SOTask task : target.getNonDeletedTasks()) {
    		
    			if(null!=task.getSequenceNumber() && task.getSequenceNumber().intValue()==targetSeqNo )
    			{    				
    				targetTaskNo=targetTaskNo +1;
    			
    			}else{
    				targetTaskNo=1;
    			}
    			task.setTaskNo(targetTaskNo);
    			if(null!=task.getSequenceNumber())
    			{
    			targetSeqNo=task.getSequenceNumber().intValue();
    			}
    		}
    		
    		int seqNo=-1;
    		int taskNo=0;
    		
    		for (SOTask task : source.getNonDeletedTasks()) {
        		
    			if(null!=task.getSequenceNumber() && task.getSequenceNumber().intValue()==seqNo )
    			{    				
    				taskNo=taskNo +1;
    			}else{
    				taskNo=1;
    			}
    			task.setTaskNo(taskNo);
    			if(null!=task.getSequenceNumber())
    			{
    			seqNo=task.getSequenceNumber().intValue();
    			}
    		}
    		
    		// for pending cancel state task price split up.
    		
    		
    		
    		
        	// Final price should be the sum of active tasks.

    		
    		if(target.getWfStateId()!=null && target.getWfStateId()>=150 )
    		{
    	    	updateTask(source, target, false);
    	    	BigDecimal totalRetailPrice =PricingUtil.ZERO;
    	        for (SOTask soTask : target.getActiveTasks()) {
    	        	BigDecimal taskPrice = soTask.getFinalPrice();
    	        	
    	            if (taskPrice!=null) totalRetailPrice = totalRetailPrice.add(taskPrice);

    	        }
	             PricingUtil.updateLaborPrice(target, totalRetailPrice);


    		}
    		else
    		{
	    	updateTask(source, target, true);
    		}
	    	/*if(target.getTasks() != null) deleteTasks(target.getTasks());
	    	if(source.getTasks() != null) target.getTasks().addAll(source.getTasks());*/
    	}
    }

	protected String getCategories(ServiceOrder so) {
		StringBuilder sb = new StringBuilder();
		logger.info("so.getPrimarySkillCatId()::"+so.getPrimarySkillCatId());
		if(null != so.getPrimarySkillCatId()){
		sb.append(quickLookup.getSkillTreeLookup().getSkillNodeById(so.getPrimarySkillCatId().longValue()).getNodeName());
		}
		for(SOTask task : so.getTasks()){
			sb.append(", ").append(quickLookup.getSkillTreeLookup().getSkillNodeById(task.getSkillNodeId().longValue()).getNodeName());
		}
		return sb.toString();
	}

	protected void updateServiceLocation(SOLocation source, ServiceOrder so, boolean zipChanged) {
		SOLocation target = so.getServiceLocation();
		if(target == null){
			so.addLocation(source);
			serviceOrderDao.update(so);
		}else{
			updateLocation(source, target);
		}
        if (zipChanged){
        	logger.info("creating a note for zip change");
        	SONote note = noteUtil.getNewNote(so, "UpdateZipCode", null);
            serviceOrderDao.save(note);
            //also update the so_location_gis if this is a bulletin board order
            if(null != so.getPriceModel() && so.getPriceModel().equals(PriceModelType.BULLETIN)){
                serviceOrderDao.saveSOLocationGIS(so.getServiceLocation().getLocationId());
            }            
        }
    }

	protected void updateCustomRef(ServiceOrder source, ServiceOrder so) {
    	if(so.getCustomReferences() != null) {
    		//Logic added to filter out the custom references which are not editable by buyer as these should not be deleted when there is any change in Custom Reference fields
    		List<SOCustomReference> customReferences = new ArrayList<SOCustomReference>();
    		for(SOCustomReference custRef: so.getCustomReferences()) {
    			if(custRef.isBuyerInput()) {
    				customReferences.add(custRef);
    			}
    		}
    		so.setCustomReferences(customReferences);
    	}
    	deleteChildren(so.getCustomReferences());
    	if(source.getCustomReferences() != null) so.setCustomReferences(source.getCustomReferences());
	}

	protected void updateOtherContact(SOContact source, SOContact target, ServiceOrder so){
    	if(target != null && source == null){
    		deleteContact(target, so);
    	}else if(target == null && source != null){
    		so.addContact(source);
    	} else {
    		updateContact(source, target);
    	}
	}
	protected void updateParts(ServiceOrder source, ServiceOrder so) {
        if((null == so.getParts() || so.getParts().size() ==0) && source.getParts() != null){
            so.setParts(source.getParts());
        }else if((null == source.getParts()  || source.getParts().size() ==0) && so.getParts() != null){
                deleteChildren(so.getParts());
        }else{
            Map partFileGeneratedDateMap = new HashMap();
            List<SOPart> oldParts = new ArrayList<SOPart>();
            
            for(SOPart part : so.getParts()){
                String serialNum = part.getSerialNumber();
                Date partFileGeneratedDate = part.getPartFileGeneratedDate();
                partFileGeneratedDateMap.put(serialNum, partFileGeneratedDate);
               
                // Take a copy
                oldParts.add(part);
                
                //delete part and make sure that contact and location also gets deleted
                if(part.getPickupContact() != null){
                    so.getContacts().remove(part.getPickupContact());
                    part.getPickupContact().setServiceOrder(null);
                }
                if(part.getPickupLocation() != null){
                    so.getLocations().remove(part.getPickupLocation());
                    part.getPickupLocation().setServiceOrder(null);
                }
                part.setServiceOrder(null);
                serviceOrderDao.delete(part);
            }
           
            for(SOPart part : source.getParts()){               
                String serialNum = part.getSerialNumber();               
                Date partFileGeneratedDate = (Date) partFileGeneratedDateMap.get(serialNum);
                part.setPartFileGeneratedDate(partFileGeneratedDate);
                
                // Compare and set new values
                for(SOPart oldPart : oldParts){
                	 if(part.getManufacturer().equals(oldPart.getManufacturer()) &&
                			 part.getModelNumber().equals(oldPart.getModelNumber())){
                		 /**These values will present in oldPart if it is completed once*/
                		 if(source.isUpdate()){
                		        part.setCoreReturnCarrierId(oldPart.getCoreReturnCarrierId());
                		        part.setCoreReturnTrackNo(oldPart.getCoreReturnTrackNo());
                		     if(null!= oldPart.getReturnCarrierId()){
                		        part.setReturnCarrierId(oldPart.getReturnCarrierId());
                		     }
                		     part.setReturnTrackNo(oldPart.getReturnTrackNo());
                		 }
                	 }
                }
            }
            so.getParts().clear();
            so.setParts(source.getParts());
         }
     }

	protected void changeRoutedProviders(ServiceOrder source, ServiceOrder so) {
    	if(so.getRoutedResources() != null) deleteChildren(so.getRoutedResources());
    	if(source.getRoutedResources() != null) so.setRoutedResources(source.getRoutedResources());	
	}
	
	protected void changeTierRoutedProviders(ServiceOrder source, ServiceOrder so) {
		logger.info("Entering Tier routed pro");
    	if(so.getTierRoutedResources() != null) deleteChildren(so.getTierRoutedResources());
    	if(source.getTierRoutedResources() != null) so.setTierRoutedResources(source.getTierRoutedResources());	
    	logger.info("Exiting Tier routed pro>>listsize>>"+so.getTierRoutedResources().size());
	}
	
	private void updateLocation(SOLocation source, SOLocation target) {
		target.setAptNumber(source.getAptNumber());
		target.setCity(source.getCity());
		target.setCountry(source.getCountry());
		target.setLocationName(source.getLocationName());
		target.setLocationNote(source.getLocationNote());
		target.setSoLocationClassId(source.getSoLocationClassId());
		target.setState(source.getState());
		target.setStreet1(source.getStreet1());
		target.setStreet2(source.getStreet2());
		target.setZip(source.getZip());
		target.setZip4(source.getZip4());
		serviceOrderDao.update(target);
	}

	protected void updateSchedule(ServiceOrder source, ServiceOrder target) {
        if(StringUtils.isNotBlank(source.getServiceLocationTimeZone())) target.setServiceLocationTimeZone(source.getServiceLocationTimeZone());
        if(null != source.getServiceDateTimezoneOffset()) target.setServiceDateTimezoneOffset(source.getServiceDateTimezoneOffset());
        if(null != source.getSchedule()) target.setSchedule(source.getSchedule());
    }

    protected void updatePricing(ServiceOrder source, ServiceOrder target){
    	logger.info("SL-16136:Inside EditSignal.updatePricing() ");
		if (target.getPrice() == null) target.setPrice(source.getPrice());
		else if(source.getPrice() == null) target.setPrice(null);
		else updateSOPrice(source.getPrice(), target.getPrice());
		
		logger.info("SL-16136:Inside EditSignal.updatePricing() before:"+target.getSpendLimitLabor());
		target.setSpendLimitLabor(source.getSpendLimitLabor());
		logger.info("SL-16136:Inside EditSignal.updatePricing() after:"+target.getSpendLimitLabor());
		logger.info("SL-20527:Inside EditSignal" +target.getSoId()+"total Permit price in the source:"+source.getTotalPermitPrice());
		logger.info("SL-20527:Inside EditSignal" +target.getSoId()+"total Permit price before update:"+target.getTotalPermitPrice());
		target.setTotalPermitPrice(source.getTotalPermitPrice());
		logger.info("SL-20527:Inside EditSignal"+target.getSoId()+"total Permit price after update"+target.getTotalPermitPrice());
		target.setSpendLimitParts(source.getSpendLimitParts());
		target.setSpendLimitIncrComment(source.getSpendLimitIncrComment());
        if(null != source.getPostingFee())target.setPostingFee(source.getPostingFee());
        if(null != source.getFundingTypeId()) target.setFundingTypeId(source.getFundingTypeId());
		target.setPriceModel(source.getPriceModel());
	}
	
	protected void deleteChildren(List<? extends SOChild> children){
		for(SOChild element : children){
			element.setServiceOrder(null);
			serviceOrderDao.delete(element);
		}
		children.clear();
	}
	
	protected void deleteTasks(List<SOTask> tasks){
		for(SOTask element : tasks){
			element.setTaskStatus(TaskStatus.DELETED.name());	
		}
	}
	
	protected void updateSOPrice(SOPrice source, SOPrice target){
		target.setConditionalOfferPrice(source.getConditionalOfferPrice());
		target.setDiscountedSpendLimitLabor(source.getDiscountedSpendLimitLabor());
		target.setDiscountedSpendLimitParts(source.getDiscountedSpendLimitParts());
		target.setFinalServiceFee(source.getFinalServiceFee());
		target.setInitPostedSpendLimitLabor(source.getInitPostedSpendLimitLabor());
		target.setInitPostedSpendLimitParts(source.getInitPostedSpendLimitParts());
		target.setOrigSpendLimitLabor(source.getOrigSpendLimitLabor());
		target.setOrigSpendLimitParts(source.getOrigSpendLimitParts());
	}
	
	private void assignSoIdToTaskHistory(SOTask task){
		if(task.getTaskHistory() != null && !task.getTaskHistory().isEmpty()){
			for(SOTaskHistory taskHistory : task.getTaskHistory()){
				if(taskHistory.getSoId() == null){
					taskHistory.setSoId(task.getServiceOrder().getSoId());
				}
				//setting the new task sequence no.to task price history
				if(null != task.getTaskSeqNum()){
					taskHistory.setTaskSeqNum(task.getTaskSeqNum());
				}
			}
		}
	}
	// SL-21455 to check whether notification is needed
	public boolean isRelayServicesNotificationNeeded(
			ServiceOrder serviceOrder) {
		logger.info("Entering isRelayServicesNotificationNeeded()...");
		ApplicationFlagLookup applicationFlagLookup = quickLookup.getApplicationFlagLookup();
		if (!applicationFlagLookup.isInitialized()) {
			throw new ServiceOrderException(
					"Unable to lookup ApplicationFlags. ApplicationFlagLookup not initialized.");
		}
		String relayServicesNotifyFlag = applicationFlagLookup
				.getPropertyValue("relay_services_notify_flag");
		Long buyerId = serviceOrder.getBuyerId();
		if ((OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID == buyerId || 
				OrderfulfillmentConstants.TECHTALK_SERVICES_BUYER_ID == buyerId) && ("ON").equals(relayServicesNotifyFlag)) {
			return true;
		} else {
			return false;
		}
	}
	
	// SL-21455 send notification
	public void sendNotification(ServiceOrder serviceOrder, String eventType, Map<String, String> requestMap) {

		logger.info("Entering RelayOutboundNotification sendNotification.");
		String URL = null;
		SimpleRestClient client = null;
		int responseCode = 0;
		try {
			if (StringUtils.isNotBlank(serviceOrder.getCustomRefValue(OrderfulfillmentConstants.CALL_BACK_URL))) {
				URL = serviceOrder.getCustomRefValue(OrderfulfillmentConstants.CALL_BACK_URL);
				client = new SimpleRestClient(URL, "", "", false);

				logger.info("URL for Webhooks:" + URL);
				StringBuffer request = new StringBuffer();
				request.append(OrderfulfillmentConstants.SERVICEPROVIDER);
				request.append(OrderfulfillmentConstants.EQUALS);
				request.append(OrderfulfillmentConstants.SERVICELIVE);
				request.append(OrderfulfillmentConstants.AND);
				request.append(OrderfulfillmentConstants.EVENT);
				request.append(OrderfulfillmentConstants.EQUALS);
				request.append(eventType.toLowerCase());

				if (null != requestMap && requestMap.size() > 0) {
					for (Map.Entry<String, String> keyValue : requestMap.entrySet()) {
						request.append(OrderConstants.AND).append(keyValue.getKey()).append(OrderConstants.EQUALS)
								.append(keyValue.getValue());
					}
				}

				logger.info("Request for Webhooks with service order id:" + serviceOrder.getSoId());
				logger.info("Request for Webhooks:" + request);

				responseCode = client.post(request.toString());
				// logging the request, response and soId in db
				serviceOrderDao.loggingRelayServicesNotification(serviceOrder.getSoId(), request.toString(), responseCode);

				logger.info("Response for Webhooks with service order id:" + serviceOrder.getSoId());
				logger.info("Response Code from Webhooks:" + responseCode);
			}

		} catch (Exception e) {
			logger.error("Exception occurred in RelayOutboundNotificationCmd.execute() due to " + e);
		}

		logger.info("Leaving RelayOutboundNotification sendNotification.");

	}

	public void setNoteUtil(ServiceOrderNoteUtil noteUtil) {
		this.noteUtil = noteUtil;
	}
	
	public void setSoLoggingCmdHelper(SOLoggingCmdHelper soLoggingCmdHelper) {
        this.soLoggingCmdHelper = soLoggingCmdHelper;
    }
}
