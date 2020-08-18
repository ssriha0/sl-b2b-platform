


/* This is introduced for the new API Call Update
 *It is the signal class for UPDATE_ORDER
 */



package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.SOLoggingCmdHelper;
import com.servicelive.orderfulfillment.common.SkuTaxCalculation;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOServiceDatetimeSlot;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class UpdateSignal extends Signal {
	private static final String CUST_REF_ESTIMATION = "ESTIMATION";
	private static final String RELAY_BUYER_3333 = "3333";
	private static final String TECHTALK_BUYER_7777 = "7777";
	protected QuickLookupCollection quickLookupCollection;
	private String spendLimitLogging = null;
	private String custRefLogging = null;
	private String updateServiceOrderLogging = null;
	private String updateSubstatusLogging = null;
	private String spendLimitDecrease=null;
	private String addedServiceSatetimeSlots=null;
	private Identification id;
	protected SOLoggingCmdHelper soLoggingCmdHelper;
	
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		String oldPrice = "";
		if(null!=so.getSpendLimitLabor() && null!=so.getSpendLimitParts()){
			BigDecimal spendLimit = so.getSpendLimitLabor().add(so.getSpendLimitParts());
			oldPrice = spendLimit.toString();
		}
		
		spendLimitLogging = (String)miscParams.get("UPDATE_API_SPEND_LOG_COMMENT");
		custRefLogging = (String)miscParams.get("UPDATE_API_CUST_LOG_COMMENT");
		updateServiceOrderLogging = (String)miscParams.get("UPDATE_API_SERVICE_LOC_CONTACT_LOG_COMMENT");
		updateSubstatusLogging = (String)miscParams.get("UPDATE_API_SO_SUBSTATUS_LOG_COMMENT");
		addedServiceSatetimeSlots=(String)miscParams.get("SO_ADDED_SERVICE_DATETIME_SLOTS");
		if(null!=miscParams.get("isSpendLimitIncreased")){
		spendLimitDecrease=(String)miscParams.get("isSpendLimitIncreased");
		}
		this.id = id;
		miscParams.put(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_PRICE, oldPrice);
	}
	/* (non-Javadoc)
	 * @see com.servicelive.orderfulfillment.signal.EditSignal#update(com.servicelive.orderfulfillment.domain.SOElement, com.servicelive.orderfulfillment.domain.ServiceOrder)
	 * method updates the spend limit increase and customer reference for new API call update
	 * 
	 */
	public SOLoggingCmdHelper getSoLoggingCmdHelper() {
		return soLoggingCmdHelper;
	}
	public void setSoLoggingCmdHelper(SOLoggingCmdHelper soLoggingCmdHelper) {
		this.soLoggingCmdHelper = soLoggingCmdHelper;
	}
	@Override
	protected void update(SOElement soe, ServiceOrder so){
		ServiceOrder source = (ServiceOrder)soe;
		
		// process discount only if it is relay - NS
		updateDiscount(source, so);
		
		// update tax in spendlimit - currently only for replay and techtalk buyers only
		updateLaborPartsTax(source, so);
		
		List<SOFieldsChangedType> changes = so.compareTo(source);
		/**SL-21086 : Code to update sub status for an AT& T order in 
		 * a)Accepted, b) Active c) Problem d) Completed
		 * b) update sub status id and description in so_hdr
		 * b) Log in so_logging table*/
		checkIfSubStatusChange(source.getWfSubStatusId(),changes);
		if (!changes.isEmpty()) {
			if (so.getPriceType().equalsIgnoreCase(OrderfulfillmentConstants.TASK_LEVEL_PRICING)) {
				updateSOTaskPrice(source, so);
			}
			HashMap<String, Object> soLogDataMap = new HashMap<String, Object>();
			soLogDataMap.put("id", this.id);
			if (changes.contains(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED)
					|| changes.contains(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED)) {
				updatePricing(source, so);

				/**
				 * SL-18007 setting so price change history if there is a change
				 * in the so spend limit labour during increase spend limit from
				 * API
				 */
				if (null != so.getSoPriceChangeHistory()) {

					logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());

					SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();

					// set specific components before passing to the generic
					// method
					newSOPriceChangeHistory.setAction(OFConstants.INCREASE_SPEND_LIMIT);
					newSOPriceChangeHistory.setReasonComment(so.getSpendLimitIncrComment());
					// newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);

					// call generic method to save so price change history
					PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory, id.getBuyerResourceId().toString(), id.getFullname());
				}

				if (StringUtils.isNotBlank(spendLimitLogging)) {

					soLogDataMap.put("UPDATE_API_SPEND_LOG_COMMENT", spendLimitLogging);
					if (StringUtils.isNotBlank(spendLimitDecrease)
							&& ("spendLimitDecreaseForRelay".equals(spendLimitDecrease) || "spendLimitDecreaseUpdateLocCon"
									.equals(spendLimitDecrease))) {
						soLoggingCmdHelper.logServiceOrderActivity(so, "spendLimitDecreaseAPILogging", soLogDataMap);

					} else {
						soLoggingCmdHelper.logServiceOrderActivity(so, "spendLimitUpdateAPILogging", soLogDataMap);
					}
				}
			}
			if (changes.contains(SOFieldsChangedType.CUSTOM_REF)) {
				updateCustomRef(source, so);
				if (StringUtils.isNotBlank(custRefLogging)) {
					soLogDataMap.put("UPDATE_API_CUST_LOG_COMMENT", custRefLogging);
					soLoggingCmdHelper.logServiceOrderActivity(so, "custRefAPIUpdateLogging", soLogDataMap);
				}
			}

			// Code for Priority 1
			if (!(OrderfulfillmentConstants.COMPLETED_STATUS == so.getWfStateId() || OrderfulfillmentConstants.CLOSED_STATUS == so
					.getWfStateId())) {

				if (changes.contains(SOFieldsChangedType.SO_DESCRIPTION)) { // Updating
																			// overview
					so.setSowDs(source.getSowDs());
				}

				if (changes.contains(SOFieldsChangedType.SERVICE_LOCATION)) {
					// Updating service location
					updateSoLocation(so, source);
				}

				if (changes.contains(SOFieldsChangedType.SERVICE_CONTACT)) {
					// Updating service contact
					updateSoContact(so, source);
				}

				if (changes.contains(SOFieldsChangedType.SO_DESCRIPTION) || changes.contains(SOFieldsChangedType.SERVICE_LOCATION)
						|| changes.contains(SOFieldsChangedType.SERVICE_CONTACT)) {
					soLogDataMap.put("UPDATE_API_SERVICE_LOC_CONTACT_LOG_COMMENT", updateServiceOrderLogging);
					soLoggingCmdHelper.logServiceOrderActivity(so, "updateServiceOrderLogging", soLogDataMap);
				}

			}

			// SL-21086
			// Updating substatus
			if (changes.contains(SOFieldsChangedType.WF_SUBSTATUS)
					&& (OrderfulfillmentConstants.ACCEPTED_STATUS == so.getWfStateId().intValue()
							|| OrderfulfillmentConstants.ACTIVE_STATUS == so.getWfStateId().intValue()
							|| OrderfulfillmentConstants.PROBLEM_STATUS == so.getWfStateId().intValue() || OrderfulfillmentConstants.COMPLETED_STATUS == so
							.getWfStateId().intValue())) {
				logger.info("Current So sub status " + source.getWfSubStatusId());
				if (null != source.getWfSubStatusId()) {
					if (OrderfulfillmentConstants.NO_SUBSTATUS == source.getWfSubStatusId().intValue()) {
						so.setWfSubStatusId(null);
					} else {
						so.setWfSubStatusId(source.getWfSubStatusId());
					}
				}
				soLogDataMap.put("UPDATE_API_SO_SUBSTATUS_LOG_COMMENT", updateSubstatusLogging);
				soLoggingCmdHelper.logServiceOrderActivity(so, "updateSubstatusLogging", soLogDataMap);
			}

			if (OrderfulfillmentConstants.DRAFT_STATUS == so.getWfStateId().intValue()
					|| OrderfulfillmentConstants.POSTED_STATUS == so.getWfStateId().intValue()) {
				if (changes.contains(SOFieldsChangedType.SCHEDULE)) {
					logger.info("Update schedule in UPDATE ORDER Signal");
					if (StringUtils.isNotBlank(source.getServiceLocationTimeZone())) {
						so.setServiceLocationTimeZone(source.getServiceLocationTimeZone());
					}

					if (null != source.getServiceDateTimezoneOffset()) {
						so.setServiceDateTimezoneOffset(source.getServiceDateTimezoneOffset());
					}
					// updateSchedule(source,so);

					SOSchedule schedule = source.getSchedule();
					// changing schedule of a service order
					so.setSchedule(schedule);
					logger.info("so.getSchedule()" + so.getSchedule());
					if (so.getSchedule() != null && so.getServiceLocationTimeZone() != null) {
						so.getSchedule().populateGMT(so.getServiceLocationTimeZone());
						if (null == so.getSchedule().getServiceDate2()) {
							so.getSchedule().setServiceDateGMT2(null);
						}
						if (null == so.getSchedule().getServiceTimeEnd()) {
							so.getSchedule().setServiceTimeEndGMT(null);
						}

					}
					logger.info("so.getSchedule() gmt" + so.getSchedule());
					logger.info("End of Update scehedule in UPDATE ORDER Signal");
					soLoggingCmdHelper.logServiceOrderActivity(so, "updateSOServiceDates", soLogDataMap);

				}
				
				if (changes.contains(SOFieldsChangedType.SCHEDULE_SLOTS)) {
					logger.info("Update schedule slots in UPDATE ORDER Signal");
					
					if (null != so.getSoServiceDatetimeSlot()) {
						logger.info("Exisitng slots length : " + so.getSoServiceDatetimeSlot().size());

						for (SOServiceDatetimeSlot dbSOServiceDatetimeSlot : so.getSoServiceDatetimeSlot()) {
							serviceOrderDao.delete(dbSOServiceDatetimeSlot);
						}
					}
					
					so.setSoServiceDatetimeSlot(source.getSoServiceDatetimeSlot());
					so.populateScheduleSlotsGMT();
					
					logger.info("New slots length : " + so.getSoServiceDatetimeSlot().size());
					logger.info("End of Update scehedule slots in UPDATE ORDER Signal");
					soLoggingCmdHelper.logServiceOrderActivity(so, "updateSOServiceDatesSlots", soLogDataMap);
				}
			}
			serviceOrderDao.update(so);
		}

	}

	/**
	 * 
	 * @param source
	 * @param so
	 */
	private void updateDiscount(ServiceOrder source, ServiceOrder so) {
		if (null != source.getPrice()) {
			
			Long buyerId = so.getBuyerId();
			if (buyerId.equals(new Long(RELAY_BUYER_3333)) && null != so.getCustomReferences() && !so.getCustomReferences().isEmpty()) {
			
				boolean isNS = false;
				for (SOCustomReference custRef : so.getCustomReferences()) {
					if (CUST_REF_ESTIMATION.equals(custRef.getBuyerRefValue())) {
						isNS = true;
						break;
					}
				}
				
				if (isNS) {
					if (null != source.getPrice().getDiscountPercentLaborSL()
							&& source.getPrice().getDiscountPercentLaborSL().compareTo(BigDecimal.ZERO) > 0) {
						
						source.setSpendLimitLabor(source.getSpendLimitLabor().multiply(
								BigDecimal.ONE.subtract(source.getPrice().getDiscountPercentLaborSL())));
						source.getPrice().setOrigSpendLimitLabor(source.getPrice().getOrigSpendLimitLabor().multiply(
								BigDecimal.ONE.subtract(source.getPrice().getDiscountPercentLaborSL())));
						source.getPrice().setDiscountedSpendLimitLabor(source.getPrice().getDiscountedSpendLimitLabor().multiply(
								BigDecimal.ONE.subtract(source.getPrice().getDiscountPercentLaborSL())));
					}
					
					if (null != source.getPrice().getDiscountPercentPartsSL()
							&& source.getPrice().getDiscountPercentPartsSL().compareTo(BigDecimal.ZERO) > 0) {
						
						source.setSpendLimitParts(source.getSpendLimitParts().multiply(
								BigDecimal.ONE.subtract(source.getPrice().getDiscountPercentPartsSL())));
						source.getPrice().setOrigSpendLimitParts(source.getPrice().getOrigSpendLimitParts().multiply(
								BigDecimal.ONE.subtract(source.getPrice().getDiscountPercentPartsSL())));
						source.getPrice().setDiscountedSpendLimitParts(source.getPrice().getDiscountedSpendLimitParts().multiply(
								BigDecimal.ONE.subtract(source.getPrice().getDiscountPercentPartsSL())));
					}
				} else {
					logger.info("so_id : " + so.getSoId() + " is not NS, so updating the discount to ZERO");
					source.getPrice().setDiscountPercentLaborSL(BigDecimal.ZERO);
					source.getPrice().setDiscountPercentPartsSL(BigDecimal.ZERO);
				}
			} else {
				logger.info("so_id : " + so.getSoId() + " is not relay-NS, so updating the discount to ZERO");
				source.getPrice().setDiscountPercentLaborSL(BigDecimal.ZERO);
				source.getPrice().setDiscountPercentPartsSL(BigDecimal.ZERO);
			}
		} 
	}
	
	/**
	 * 
	 * @param source
	 * @param so
	 */
	private void updateLaborPartsTax(ServiceOrder source, ServiceOrder so) {

		if (quickLookupCollection.getBuyerFeatureLookup().isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ALLOW_SKU_TAX,
				so.getBuyerId())) {

			BigDecimal taxRateForLabor = (null != so.getPrice() && null != so.getPrice().getTaxPercentLaborSL()) ? (so.getPrice()
					.getTaxPercentLaborSL().compareTo(BigDecimal.ZERO) > 0 ? so.getPrice().getTaxPercentLaborSL() : null) : null;
			BigDecimal taxRateForParts = (null != so.getPrice() && null != so.getPrice().getTaxPercentPartsSL()) ? (so.getPrice()
					.getTaxPercentPartsSL().compareTo(BigDecimal.ZERO) > 0 ? so.getPrice().getTaxPercentPartsSL() : null) : null;

			if (null != source.getSpendLimitLabor() && source.getSpendLimitLabor().compareTo(BigDecimal.ZERO) > 0) {
				taxRateForLabor = (null == taxRateForLabor) ? getTaxRateForLabor(so) : taxRateForLabor;
				source.setSpendLimitLabor(source.getSpendLimitLabor().multiply(taxRateForLabor.add(BigDecimal.ONE)));
			}

			if (null != source.getSpendLimitParts() && source.getSpendLimitParts().compareTo(BigDecimal.ZERO) > 0) {
				taxRateForParts = (null == taxRateForParts) ? getTaxRateForParts(so) : taxRateForParts;
				source.setSpendLimitParts(source.getSpendLimitParts().multiply(taxRateForParts.add(BigDecimal.ONE)));
			}

			if (null != source.getPrice()) {
				taxRateForLabor = (null == taxRateForLabor) ? getTaxRateForLabor(so) : taxRateForLabor;
				taxRateForParts = (null == taxRateForParts) ? getTaxRateForParts(so) : taxRateForParts;

				if (null != source.getPrice().getOrigSpendLimitLabor()
						&& source.getPrice().getOrigSpendLimitLabor().compareTo(BigDecimal.ZERO) > 0) {
					source.getPrice().setOrigSpendLimitLabor(
							source.getPrice().getOrigSpendLimitLabor().multiply(taxRateForLabor.add(BigDecimal.ONE)));
				}

				if (null != source.getPrice().getDiscountedSpendLimitLabor()
						&& source.getPrice().getDiscountedSpendLimitLabor().compareTo(BigDecimal.ZERO) > 0) {
					source.getPrice().setDiscountedSpendLimitLabor(
							source.getPrice().getDiscountedSpendLimitLabor().multiply(taxRateForLabor.add(BigDecimal.ONE)));
				}

				if (null != source.getPrice().getOrigSpendLimitParts()
						&& source.getPrice().getOrigSpendLimitParts().compareTo(BigDecimal.ZERO) > 0) {
					source.getPrice().setOrigSpendLimitParts(
							source.getPrice().getOrigSpendLimitParts().multiply(taxRateForParts.add(BigDecimal.ONE)));
				}

				if (null != source.getPrice().getDiscountedSpendLimitParts()
						&& source.getPrice().getDiscountedSpendLimitParts().compareTo(BigDecimal.ZERO) > 0) {
					source.getPrice().setDiscountedSpendLimitParts(
							source.getPrice().getDiscountedSpendLimitParts().multiply(taxRateForParts.add(BigDecimal.ONE)));
				}

				source.getPrice().setTaxPercentLaborSL(taxRateForLabor);
				source.getPrice().setTaxPercentPartsSL(taxRateForParts);
			}
		} else {
			logger.warn("Tax api is not active for buyer: "
					+ (so.getBuyerId() != null ? so.getBuyerId().longValue() : "[buyer id is not available]"));
			source.getPrice().setTaxPercentLaborSL(BigDecimal.ZERO);
			source.getPrice().setTaxPercentPartsSL(BigDecimal.ZERO);
		}
	}
	
	/**
	 * 
	 * @param so
	 * @return
	 */
	private BigDecimal getTaxRateForLabor(ServiceOrder so) {
		logger.info("Inside getTaxRateForSO method");
		BigDecimal taxRate = new BigDecimal(0);

		String taxServiceUrl = getTaxServiceUrl(OrderfulfillmentConstants.TAX_SERVICE_KEY);
		Long buyerId = so.getBuyerId();

		logger.info("Calculating the tax for Buyer" + buyerId);
		try {
			if (null == so.getTasks().get(0).getExternalSku()) {
				logger.info("Did not find sku");
				return taxRate;
			}
			String sku = so.getTasks().get(0).getExternalSku();
			String zip = so.getServiceLocation().getZip();
			String state = so.getServiceLocation().getState();
			SkuTaxCalculation taxUtil = new SkuTaxCalculation();

			logger.info("calling tax service...");

			return taxUtil.getTaxRateForSku(sku, zip, state, taxServiceUrl);
		} catch (Exception e) {
			logger.error("Error occurred while calculating the tax : " + e.getStackTrace());
		}

		logger.info("returning default tax 0 for buyer " + buyerId);
		return taxRate;
	}

	/**
	 * 
	 * @param so
	 * @return
	 */
	private BigDecimal getTaxRateForParts(ServiceOrder so) {
		logger.info("Inside getTaxRateForParts method");
		BigDecimal taxRate = new BigDecimal(0);

		String taxServiceUrl = getTaxServiceUrl(OrderfulfillmentConstants.TAX_SERVICE_KEY);
		Long buyerId = so.getBuyerId();

		logger.info("Calculating tax for Buyer" + buyerId);
		try {
			String sku = "partsSku";
			String zip = so.getServiceLocation().getZip();
			String state = so.getServiceLocation().getState();
			SkuTaxCalculation taxUtil = new SkuTaxCalculation();

			logger.info("calling tax service...");

			return taxUtil.getTaxRateForSku(sku, zip, state, taxServiceUrl);
		} catch (Exception e) {
			logger.error("Error occurred while calculating the tax : " + e.getStackTrace());
		}

		logger.info("returning default tax 0 for buyer " + buyerId);
		return taxRate;
	}
	
	private String getTaxServiceUrl(String applicatioKey) {
        return quickLookupCollection.getApplicationPropertyLookup().getPropertyValue(applicatioKey);
    }
	
	
	/**
	 * @param wfSubStatusIdReq
	 * @param wfSubStatusIdDB
	 * @param changes
	 */
	private void checkIfSubStatusChange(Integer wfSubStatusIdReq,List<SOFieldsChangedType> changes) {
		if(null!= wfSubStatusIdReq){
			changes.add(SOFieldsChangedType.WF_SUBSTATUS);
	    }
	}
	private void updateSoLocation(ServiceOrder so, ServiceOrder source) {
		SOLocation sourceLocation =source.getLocations().get(0);
		SOLocation target = so.getServiceLocation();
		
		target.setStreet1(sourceLocation.getStreet1());
		target.setStreet2(sourceLocation.getStreet2());
		target.setLocationName(sourceLocation.getLocationName());
		target.setSoLocationClassId(sourceLocation.getSoLocationClassId());
		target.setLocationNote(sourceLocation.getLocationNote());
		target.setCity(sourceLocation.getCity());
		target.setModifiedDate(new Date());
		serviceOrderDao.update(target);
	}

	private void updateSoContact(ServiceOrder so, ServiceOrder source) {
		SOContact sourceContact =source.getContacts().get(0);
		SOContact target = so.getServiceContact();
		
		target.setFirstName(sourceContact.getFirstName());
		target.setLastName(sourceContact.getLastName());
		target.setEmail(sourceContact.getEmail());

		boolean primaryInd =false;
		boolean alternateInd =false;
		
		if(null!=sourceContact.getPhones() && null!=target.getPhones())
		{
			for(SOPhone sourcePhone : sourceContact.getPhones()){
				for(SOPhone targetPhone : target.getPhones()){
					if(PhoneType.PRIMARY.equals(sourcePhone.getPhoneType()) && PhoneType.PRIMARY.equals(targetPhone.getPhoneType()))
					{
						targetPhone.setPhoneNo(sourcePhone.getPhoneNo());
						targetPhone.setPhoneExt(sourcePhone.getPhoneExt());
						targetPhone.setPhoneClass(sourcePhone.getPhoneClass());
						primaryInd = true;
	
					}else if(PhoneType.ALTERNATE.equals(sourcePhone.getPhoneType()) && PhoneType.ALTERNATE.equals(targetPhone.getPhoneType()))
					{
						targetPhone.setPhoneNo(sourcePhone.getPhoneNo());
						targetPhone.setPhoneExt(sourcePhone.getPhoneExt());
						targetPhone.setPhoneClass(sourcePhone.getPhoneClass());
						alternateInd = true;
					}
				}
			}
		}
		
		Set<SOPhone> soPhoneSet =null;
		if(!primaryInd && !alternateInd && null!=sourceContact.getPhones()){
			soPhoneSet =new HashSet<SOPhone>();
		}else if((!primaryInd || !alternateInd) && null!=sourceContact.getPhones()){	
			soPhoneSet=target.getPhones();
		}else if(primaryInd && alternateInd){
			soPhoneSet=target.getPhones();
		}
		
		if(!primaryInd && null!=sourceContact.getPhones()){
			for(SOPhone sourcePhone : sourceContact.getPhones()){
				if(PhoneType.PRIMARY.equals(sourcePhone.getPhoneType()))
				{
					soPhoneSet.add(sourcePhone);
				}
			}
		}
		
		if(!alternateInd && null!=sourceContact.getPhones()){
			for(SOPhone sourcePhone : sourceContact.getPhones()){
				if(PhoneType.ALTERNATE.equals(sourcePhone.getPhoneType()))
				{
					soPhoneSet.add(sourcePhone);
				}
			}
		}
		
		target.setPhones(soPhoneSet);
		target.setModifiedDate(new Date());
		serviceOrderDao.update(target);
	}
	

	/**
	 * @param source
	 * @param target
	 * updates price for new  update API call
	 */
	protected void updatePricing(ServiceOrder source, ServiceOrder target){
		target.setSpendLimitLabor(source.getSpendLimitLabor());
		target.setSpendLimitParts(source.getSpendLimitParts());
		target.setSpendLimitIncrComment(source.getSpendLimitIncrComment());

		if(null!=target.getPrice()){
			updateSOPrice(source.getPrice(), target.getPrice());
		}
	}



	/**
	 * @param source
	 * @param target
	 * updates SO Price for new update API call
	 */
	protected void updateSOPrice(SOPrice source, SOPrice target){
		target.setDiscountedSpendLimitLabor(source.getDiscountedSpendLimitLabor());
		target.setDiscountedSpendLimitParts(source.getDiscountedSpendLimitParts());
		target.setDiscountPercentLaborSL(source.getDiscountPercentLaborSL());
		target.setDiscountPercentPartsSL(source.getDiscountPercentPartsSL());
		target.setTaxPercentLaborSL(source.getTaxPercentLaborSL());
		target.setTaxPercentPartsSL(source.getTaxPercentPartsSL());
	}

	/**
	 * @param source
	 * @param target
	 * updates task level pricing for new  update API call
	 * 
	 */
	protected void updateSOTaskPrice(ServiceOrder source, ServiceOrder target){
		BigDecimal totalFinalPriceOfTasks = PricingUtil.ZERO;
		int noOfTasks = 0;
		for (SOTask task : target.getActiveTasks()) {

			if (task.getFinalPrice() != null){

				if(task.getTaskType().equals(OrderfulfillmentConstants.PRIMARY_TASK)
						|| task.getTaskType().equals(OrderfulfillmentConstants.NON_PRIMARY_TASK)){

					totalFinalPriceOfTasks = totalFinalPriceOfTasks.add(task.getFinalPrice());
					noOfTasks+=1;
				}
			}
		}
		double additionalAmount = 0.0;
		BigDecimal soPrice = target.getSpendLimitLabor();
		BigDecimal price = source.getSpendLimitLabor();
		additionalAmount =price.doubleValue()-soPrice.doubleValue();
		if (additionalAmount != 0 ) {
			for (SOTask task : target.getActiveTasks()) {

				if (task.getFinalPrice() != null && (task.getTaskType().equals(
						OrderfulfillmentConstants.PRIMARY_TASK)
						|| task.getTaskType().equals(
								OrderfulfillmentConstants.NON_PRIMARY_TASK)
						)) {

					double amount = 0;

					// divide new so max labor(the total price for tasks alone
					// excluding permit task) among tasks.
					if(null!=task.getFinalPrice()  && totalFinalPriceOfTasks.doubleValue() >0) {
						double percentageShare = task.getFinalPrice().doubleValue()/ totalFinalPriceOfTasks.doubleValue() * 100;
						amount = additionalAmount * percentageShare / 100;
						amount = task.getFinalPrice().doubleValue() + amount;
						task.setFinalPrice(BigDecimal.valueOf(amount));
						PricingUtil.addTaskPriceHistory(task, "SYSTEM", "SYSTEM");
					}else if(null!=task.getFinalPrice()  && totalFinalPriceOfTasks.doubleValue() ==0){
						amount = additionalAmount/noOfTasks;
						amount = task.getFinalPrice().doubleValue() + amount;
						task.setFinalPrice(BigDecimal.valueOf(amount));
						PricingUtil.addTaskPriceHistory(task, "SYSTEM", "SYSTEM");
					}

				}
			}
		}

	}




	/**
	 * @param source
	 * @param so
	 * updates customer reference for new update API call
	 * 
	 */
	protected void updateCustomRef(ServiceOrder source, ServiceOrder so) {

		List<SOCustomReference> soCustomReferences = new ArrayList<SOCustomReference>();
		List<SOCustomReference> sourceCustomReferences = new ArrayList<SOCustomReference>();
		boolean found = false;
		soCustomReferences=so.getCustomReferences();
		sourceCustomReferences=source.getCustomReferences();
		if (sourceCustomReferences!= null){
			for (SOCustomReference sourceRef : sourceCustomReferences){
				found = false;
				if (soCustomReferences!= null){
					for (SOCustomReference soRef : soCustomReferences){
						if (soRef.getBuyerRefTypeId().equals(sourceRef.getBuyerRefTypeId())) {
							found=true;
							if(soRef.isBuyerInput()){

								soRef.setBuyerRefValue(sourceRef.getBuyerRefValue());
							}
						}
					}
					if(!found){
						soCustomReferences.add(sourceRef);
					}

				}
			}

		}
		so.setCustomReferences(soCustomReferences);

	}



	@Override
	protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
		List<String> returnVal = new ArrayList<String>();
		if (!(soe instanceof ServiceOrder)){
			returnVal.add("Invalid type! Expected ServiceOrder, received:" + soe.getTypeName());
		}
		return returnVal;
	}


	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}
	
	protected void updateSchedule(ServiceOrder source, ServiceOrder target) {
        if(null != source.getSchedule()){
        	target.setSchedule(source.getSchedule());
        }
        serviceOrderDao.update(target);
    }
}
