package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.RelayNetPriceCalculatorUtil;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAdditionalPaymentHistory;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.SOPartLaborPriceReason;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SoProviderInvoicePartDocument;
import com.servicelive.orderfulfillment.domain.SoProviderInvoicePartLocation;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class CompleteOrderSignal extends Signal {
	
	private IMarketPlatformPromoBO marketPlatformBO;
	private Identification id;
	private Map<Integer,SoProviderInvoicePartLocation> partLocationMap = new HashMap<Integer,SoProviderInvoicePartLocation>();
	private Map<Integer,List<SoProviderInvoicePartDocument>> invoiceDocumentListMap = new HashMap<Integer,List<SoProviderInvoicePartDocument>>();
	
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		this.id = id;	
	}
    
	@Override
    protected void update(SOElement soe, ServiceOrder serviceOrder) {
        ServiceOrder inputSO = (ServiceOrder) soe;

        serviceOrder.setResolutionDs(inputSO.getResolutionDs());
        serviceOrder.setFinalPriceParts(inputSO.getFinalPriceParts());
        serviceOrder.setFinalPriceLabor(inputSO.getFinalPriceLabor());
        if( SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
        	serviceOrder.setTasks(inputSO.getTasks());
        }
        for (SOTask task : serviceOrder.getTasks()) {
			if(null!=task.getTaskHistory()){
				for(SOTaskHistory history : task.getTaskHistory()){
					history.setTask(task);
				}
				task.setTaskHistory(task.getTaskHistory());
			}
        }
        updateAddons(inputSO, serviceOrder);
		logger.info("BEFORE HSR BUYER");
		
	    if(inputSO.getSoProviderInvoiceParts() != null && inputSO.getSoProviderInvoiceParts().size() > 0)
	    {
	            	    logger.info("*******list parts size--"+inputSO.getSoProviderInvoiceParts().size());
	    
	    }	
	    logger.info("BEFORE HSR BUYER + buyer id "+serviceOrder.getBuyerId());
	    logger.info("SETTTTTTTTTTTTTTTTTTTTTTTTT");

	    
	//	serviceOrder.setSoProviderInvoiceParts(inputSO.getSoProviderInvoiceParts());
	  //  logger.info("COMPLTEDDDDDDDDDDDDDDDDDDDDDDDD");

        // HSR 
        if(3000==serviceOrder.getBuyerId().intValue()){
        	
        	if(serviceOrder.getSoProviderInvoiceParts() == null){
        		logger.info("Setting parts data in complete order signal");
        	    if(inputSO.getSoProviderInvoiceParts() != null && inputSO.getSoProviderInvoiceParts().size() > 0) {
        	        logger.info("list parts size--"+inputSO.getSoProviderInvoiceParts().size());
        	        addInvoiceParts(serviceOrder,inputSO);
        	      //R12_0 Sprint 4
            		serviceOrder.getSOWorkflowControls().setInvoicePartsInd("PARTS_ADDED");
            		logger.info("Set parts data in complete order signal");
        	       }else{
        	         logger.info("PARTS LIST EMPTY in complete order signal");
        	     }
        	   
        		
        	}else{
        		logger.info("Entered Delete parts data in complete order signal");
                //This will save the invoice part location,and documents associated with invoiceId
        		List<Integer> invoiceIdList= new ArrayList<Integer>();
            	if(null!= serviceOrder.getSoProviderInvoiceParts() && !(serviceOrder.getSoProviderInvoiceParts().isEmpty())){
            		for(SOProviderInvoiceParts invoicePart:serviceOrder.getSoProviderInvoiceParts()){
            			invoiceIdList.add(invoicePart.getPartsInvoiceId());
            		}
            	}
        		if(!invoiceIdList.isEmpty()){
        		setInvoicePartLocationAndDocumentList(serviceOrder);
        		}
        		deleteInvoiceParts(serviceOrder);
        		logger.info("Adding invoice Parts to Service Order");
                addInvoiceParts(serviceOrder,inputSO);
        	    if(inputSO.getSoProviderInvoiceParts() != null && inputSO.getSoProviderInvoiceParts().size() > 0) {
        	    	//R12_0 Sprint 4
            		serviceOrder.getSOWorkflowControls().setInvoicePartsInd("PARTS_ADDED");
        	    }
            	setPartsLocationAndDocumentList(serviceOrder,invoiceIdList);
        	}
////        	else
////        	{
                // updatePartsInvoice(inputSO, serviceOrder);
//        	}
            if( serviceOrder.getSoPartLaborPriceReason()!=null && serviceOrder.getSoPartLaborPriceReason().size()>0 ){
            	//Updating Reason codes
        		logger.info("Updating Reason for lesser than maximum prize");
            	updateReason(serviceOrder,inputSO);
            }else{
            	//Setting the Reason codes for part and labor
        		logger.info("Inserting Reason codes for the first time");
            	serviceOrder.setSoPartLaborPriceReason(inputSO.getSoPartLaborPriceReason());
            }
        	
        }
        updateCustomRefs(inputSO, serviceOrder);
        updateParts(inputSO, serviceOrder);
         logger.info("inserting sOAdditionalPaymentHistory --");
        SOAdditionalPaymentHistory sOAdditionalPaymentHistory = null;
        String paymentType = null;
        if (inputSO.getAdditionalPayment() != null) {
        	if (serviceOrder.getAdditionalPayment() != null) {
        		logger.info("if there is data from form as well as in table");
        		//PCI Vault Changes : Setting History Objects
        		sOAdditionalPaymentHistory = setAdditionalPaymentHistory(inputSO, serviceOrder);
        		serviceOrder.getAdditionalPayment().setAuthorizationNumber(inputSO.getAdditionalPayment().getAuthorizationNumber());
        		serviceOrder.getAdditionalPayment().setCardType(inputSO.getAdditionalPayment().getCardType());
        		serviceOrder.getAdditionalPayment().setCheckNumber(inputSO.getAdditionalPayment().getCheckNumber());
        		serviceOrder.getAdditionalPayment().setDescription(inputSO.getAdditionalPayment().getDescription());
        		serviceOrder.getAdditionalPayment().setExpirationDateMonth(inputSO.getAdditionalPayment().getExpirationDateMonth());
        		serviceOrder.getAdditionalPayment().setExpirationDateYear(inputSO.getAdditionalPayment().getExpirationDateYear());
        		serviceOrder.getAdditionalPayment().setPaymentType(inputSO.getAdditionalPayment().getPaymentType());
        		serviceOrder.getAdditionalPayment().setPaymentAmount(inputSO.getAdditionalPayment().getPaymentAmount());
        		paymentType = inputSO.getAdditionalPayment().getPaymentType().getShortName();
        		if(StringUtils.isNotBlank(paymentType) && !StringUtils.equals(paymentType, "CA")){
	        		//PCI Vault II Changes
        			if(StringUtils.isNotBlank(inputSO.getAdditionalPayment().getCreditCardNumber())
            				&& !StringUtils.contains(inputSO.getAdditionalPayment().getCreditCardNumber(),CommonConstants.SPECIAL_CHARACTER)){
            		     serviceOrder.getAdditionalPayment().setCreditCardNumber(inputSO.getAdditionalPayment().getCreditCardNumber());
            		}
	        		if(null!=inputSO.getAdditionalPayment().getMaskedAccountNumber()){
	        			serviceOrder.getAdditionalPayment().setMaskedAccountNumber(inputSO.getAdditionalPayment().getMaskedAccountNumber());
	        		}
	        		if(null!=inputSO.getAdditionalPayment().getToken()){
	        			serviceOrder.getAdditionalPayment().setToken(inputSO.getAdditionalPayment().getToken());
	        		}
        		}else{
        			serviceOrder.getAdditionalPayment().setMaskedAccountNumber(null);
        			serviceOrder.getAdditionalPayment().setToken(null);
        			serviceOrder.getAdditionalPayment().setCreditCardNumber(null);
        		}
        		
        	}else {
        		logger.info("if there is data from form but no data in table");
        		//PCI Vault Changes : Creating  History Object 
        		sOAdditionalPaymentHistory = setAdditionalPaymentHistory(inputSO, serviceOrder);
        		serviceOrder.setAdditionalPayment(inputSO.getAdditionalPayment());
        		
        	}
        } else if (serviceOrder.getAdditionalPayment() != null) {
        	logger.info("if there is no data from form and no data in table");
        	serviceOrderDao.delete(serviceOrder.getAdditionalPayment());
        	serviceOrder.setAdditionalPayment(null);
        }
        updateTaxDiscount(inputSO, serviceOrder);
        updateSOPriceWithFinalLaborAmt(serviceOrder);
        if("PROVIDER".equals(inputSO.getAssignmentType())){
        	serviceOrder.setAcceptedProviderResourceId(inputSO.getAcceptedProviderResourceId());
        	serviceOrder.setAssignmentType("PROVIDER");
        	serviceOrder.addContact(inputSO.getVendorResourceContact());
        	serviceOrder.addLocation(inputSO.getVendorResourceLocation());
        }
       
        updateSoPriceChangeHistory(serviceOrder);
        serviceOrderDao.update(serviceOrder);
        // new table update
        logger.info("before updating additional payment history table");
        serviceOrderDao.updateHistory(sOAdditionalPaymentHistory);
    }
	
	
	/**
	 * SL-18007 setting so price change history during completion of an SO
	 */
	private void updateSoPriceChangeHistory(ServiceOrder serviceOrder){
		if (null != serviceOrder.getSoPriceChangeHistory()) {
			logger.info("18007: so price change history contins: " + serviceOrder.getSoPriceChangeHistory());
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			// set specific components before passing to the generic method
			newSOPriceChangeHistory.setReasonComment(null);
			serviceOrder.setFromCompletionFlow(true);
			// call generic method to save so price change history
			PricingUtil.addSOPriceChangeHistory(serviceOrder, newSOPriceChangeHistory,
					id.getProviderResourceId().toString(), id.getFullname());
		}
	}
	/**
	 * This method sets all the invoice parts entered by user to the service order
	 * @param serviceOrder
	 * @param inputSO
	 */
	private void addInvoiceParts(ServiceOrder serviceOrder, ServiceOrder inputSO) {
        
		List<SOProviderInvoiceParts> targetList=new ArrayList<SOProviderInvoiceParts>();
		for (SOProviderInvoiceParts providerInvoiceParts : inputSO.getSoProviderInvoiceParts()) {
           if(providerInvoiceParts!=null && providerInvoiceParts.getFinalPrice()!=null){
        	   targetList.add(providerInvoiceParts);
           }
		}
		serviceOrder.setSoProviderInvoiceParts(targetList);
	
	}
/**
 * This method is used to delete all the invoice parts for the given service order Id
 * @param serviceOrder
 */
	private void deleteInvoiceParts(ServiceOrder serviceOrder) {
        for (SOProviderInvoiceParts providerInvoiceParts : serviceOrder.getSoProviderInvoiceParts()) {
			serviceOrderDao.delete(providerInvoiceParts);
		}	
        logger.info("All invoice parts for "+serviceOrder.getSoId()+" are deleted");
	}
	
	/**
	 * This method is used to update reason for parts and labour for given soId
	 * @param serviceOrder
	 * @param inputSO
	 */
	private void updateReason(ServiceOrder serviceOrder, ServiceOrder inputSO) {
		if(null != serviceOrder.getSoPartLaborPriceReason() && null != inputSO.getSoPartLaborPriceReason()){
			for(SOPartLaborPriceReason target : serviceOrder.getSoPartLaborPriceReason()){
	        	for(SOPartLaborPriceReason source : inputSO.getSoPartLaborPriceReason()){
	        		if(null != source.getPriceType() && null != target.getPriceType()){
	        			if(source.getPriceType().equals(target.getPriceType())){
		        			if(source.getReasonCodeId() == target.getReasonCodeId()){
		        				if(null != source.getReasonComments() && null != target.getReasonComments()){
		        					if(source.getReasonComments().equals(target.getReasonComments()))
				        				continue; 
		        				}		        				
		        			}		        			
		        			else{
		        				target.setReasonCodeId(source.getReasonCodeId());
		        				target.setReasonComments(source.getReasonComments());
		        				target.setModifiedDate(new Date());
		        				target.setModifiedBy(source.getModifiedBy());
		        			}
	        			}
	        		}
		        }
	        }	        		
	    } 
	}

	@Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
		logger.info("Inside Complete Order Signal - validate()");
        List<String> returnVal = new ArrayList<String>();
        if (!(soe instanceof ServiceOrder)) {
            returnVal.add("Expecting a ServiceOrder instance in CompleteOrderSignal");
            return returnVal; //Abort - No use going further.
        }
        ServiceOrder inputSO = (ServiceOrder) soe;
        //Make sure All data elements are present:
        if (StringUtils.isBlank(inputSO.getResolutionDs())) {
            returnVal.add("Resolution description not provided on SO completion.");
        }
        
        boolean isError = false;
      	if(null != inputSO && !((null == inputSO.getAddons() || (null != inputSO.getAddons() && inputSO.getAddons().isEmpty())) && null == inputSO.getAdditionalPayment())){
      		isError = validateAddonsAndPayment(inputSO.getAddons(), inputSO.getAdditionalPayment());
      		if(isError){
            	returnVal.add(OrderConstants.ADDON_COMPLETE_ERROR);
            }
      	}
         
        return returnVal;
    }
	
	/**
	 * SL-20926 changes
	 * Check if addon issues exists for the order, if
	 * AddOn qty = 0 but having additional payment details entry
	 * AddOn qty > 0 but having no additional payment details
	 * AddOn qty > 0 but the payment type in additional payment details is blank
	 * @param addons
	 * @param additionalPayment
	 * @return boolean
	 */
    private boolean validateAddonsAndPayment(List<SOAddon> addons, SOAdditionalPayment additionalPayment) {
    	
    	boolean isError = false;
    	
		logger.info("Inside AutoCloseBuyerFeatureCheckInActive - validate()2");
		boolean hasQty = false;
		String paymentType = null;
			
		//check if any addon is having qty > 0
		if(null != addons && !addons.isEmpty()){
			for(SOAddon addon : addons){
				if(null != addon && null != addon.getQuantity() && addon.getQuantity() > 0){
					hasQty = true;
					break;
				}
			}
		}
			
		if(null != additionalPayment && null != additionalPayment.getPaymentType()){
			paymentType = additionalPayment.getPaymentType().getShortName();
		}
			
		//if addon qty = 0 but having additional payment details or
		//addon qty > 0 but having no additional payment details or
		//addon qty > 0 but the payment type in additional payment details is blank
		if((!hasQty && null != additionalPayment) || 
				(hasQty && null == additionalPayment) || 
				(hasQty && null != additionalPayment && StringUtils.isBlank(paymentType))){
			logger.info("Inside AutoCloseBuyerFeatureCheckInActive - if condition");	
			isError = true;
		}
		return isError;
	}


    private void updateSOPriceWithFinalLaborAmt(ServiceOrder serviceOrder) {
        SOPrice soPrice = serviceOrder.getPrice();
        
        soPrice.setDiscountedSpendLimitLabor(serviceOrder.getFinalPriceLabor());
        soPrice.setDiscountedSpendLimitParts(serviceOrder.getFinalPriceParts());
    	if(OFConstants.HSR_BUYER_ID.equals(serviceOrder.getBuyerId())){
    		soPrice.setTotalAddonPriceGL(serviceOrder.getTotalAddon(true));
		}else{
			soPrice.setTotalAddonPriceGL(serviceOrder.getTotalAddonWithoutPermits());
		}
        //serviceOrder.setPrice(soPrice);
        serviceOrderDao.update(soPrice);
    }
   
    private void updateParts(ServiceOrder source, ServiceOrder target) {
		if (source.getParts() != null && target.getParts() != null) {
			for (SOPart sPart : source.getParts()) {
				for (SOPart tPart : target.getParts()) {
					if (sPart.getPartId().equals(tPart.getPartId())) {
						if(sPart.getCoreReturnCarrierId()!= null)
							tPart.setCoreReturnCarrierId(sPart.getCoreReturnCarrierId());
						if(sPart.getReturnCarrierId()!= null)
							tPart.setReturnCarrierId(sPart.getReturnCarrierId());
						tPart.setCoreReturnTrackNo(sPart.getCoreReturnTrackNo());
						tPart.setReturnTrackNo(sPart.getReturnTrackNo());
						break;
					}
				}
			}
		}
	}
    //SL-21927
    public void  updateTaxDiscount(ServiceOrder source, ServiceOrder target){
    	logger.info("Entering into updateTaxDiscount method of CompleteOrderSignal class ");
    	
    	SOPrice soPriceTarget = target.getPrice();
    	if (soPriceTarget == null) {
            soPriceTarget = new SOPrice();
            soPriceTarget.setServiceOrder(target);
            serviceOrderDao.save(soPriceTarget);
        }
    	
    	if(null != source.getPrice()){
    		logger.info("so_id:"+source.getSoId());
    		logger.info("labor tax:"+source.getPrice().getTaxPercentLaborSL());
    		logger.info("labor discount:"+source.getPrice().getDiscountPercentLaborSL());
    		logger.info("parts tax:"+source.getPrice().getTaxPercentPartsSL());
    		logger.info("parts discount:"+source.getPrice().getDiscountPercentPartsSL());
    		  		
            soPriceTarget.setTaxPercentLaborSL(source.getPrice().getTaxPercentLaborSL());
            soPriceTarget.setDiscountPercentLaborSL(source.getPrice().getDiscountPercentLaborSL());
            soPriceTarget.setTaxPercentPartsSL(source.getPrice().getTaxPercentPartsSL());
            soPriceTarget.setDiscountPercentPartsSL(source.getPrice().getDiscountPercentPartsSL()); 
           
            logger.info("Exiting from updateTaxDiscount method of CompleteOrderSignal class ");
    		
			}
    }
    
    
private void updatePartsInvoice(ServiceOrder source, ServiceOrder target){
		
		if (source.getSoProviderInvoiceParts()!= null && target.getSoProviderInvoiceParts() != null) {
			for (SOProviderInvoiceParts sPart : source.getSoProviderInvoiceParts()) {
				boolean partsInvoiceUpdated = false;
				for (SOProviderInvoiceParts tPart : target.getSoProviderInvoiceParts()) {
					if (sPart.getPartsInvoiceId().equals(tPart.getPartsInvoiceId())) {
						tPart.setPartCoverage(sPart.getPartCoverage());
						tPart.setSource(sPart.getSource());
						tPart.setPartNo(sPart.getPartNo());
						tPart.setDivisionNumber(sPart.getDivisionNumber());
						tPart.setSourceNumber(sPart.getSourceNumber());
						if (StringUtils.isBlank(sPart.getPartsUrl())) {
							tPart.setPartsUrl(null);
						} else {
							tPart.setPartsUrl(sPart.getPartsUrl());
						}
						tPart.setDescription(sPart.getDescription());
						tPart.setInvoiceNo(sPart.getInvoiceNo());
						tPart.setUnitCost(sPart.getUnitCost()); 
						tPart.setRetailPrice(sPart.getRetailPrice());
						tPart.setQty(sPart.getQty());
						tPart.setEstProviderPartsPpayment(sPart.getEstProviderPartsPpayment());
						tPart.setFinalPrice(sPart.getFinalPrice());
						tPart.setSourceNonSears(sPart.getSourceNonSears());
						partsInvoiceUpdated = true;							
						break;
					}
				}
				// if part invoice id  was not found in target, insert it
				if (partsInvoiceUpdated == false && sPart.getPartsInvoiceId() != null) {
				//	target.setSoProviderInvoiceParts(sPart);
				}
			}
		}
		
	}
    
    
    
	private void updateCustomRefs(ServiceOrder source, ServiceOrder target) {
		if (source.getCustomReferences() != null && target.getCustomReferences() != null) {
			for (SOCustomReference sRef : source.getCustomReferences()) {
				boolean customRefUpdated = false;
				for (SOCustomReference tRef : target.getCustomReferences()) {
					if (sRef.getBuyerRefTypeId().equals(tRef.getBuyerRefTypeId())) {
						tRef.setBuyerRefValue(sRef.getBuyerRefValue());
						customRefUpdated = true;
						break;
					}
				}
				// if custom reference was not found in target, add it
				if (customRefUpdated == false && sRef.getBuyerRefValue() != null) {
					target.addCustomReference(sRef);
				}
			}
		}
	}
	
    private void updateAddons(ServiceOrder source, ServiceOrder target){
    	//since this is a completion order signal if there are addons in incoming service order 
    	//there has to be addons in the target
    	//We may throw an error if the incoming has addons but target does not
    	int sourceIndex=0;
    	String scopeOfWorkForPermit ="";
    	String coverageForPermit ="";
    	List<SOAddon> permitAddons = new ArrayList<SOAddon>();
    	if (source.getAddons() != null && target.getAddons() != null){
    		for(SOAddon sadd : source.getAddons()){
    			if(sadd.getAddonId()==null  && sadd.getSku().equals("99888")  && sadd.getQuantity() > 0){
					
    	        	permitAddons.add(sadd);
    	        	
				}else if(sadd.getAddonId()!=null){
    			for(SOAddon tadd: target.getAddons()){
    				if((sadd.getAddonId()!=null && sadd.getAddonId().equals(tadd.getAddonId()))){
    					tadd.setQuantity(sadd.getQuantity());
    					if(tadd.getSku().equals("99888")){
    						scopeOfWorkForPermit = tadd.getScopeOfWork();
    						coverageForPermit = tadd.getCoverage();
    					}
    					if(tadd.isMiscInd() && sadd.getQuantity() > 0){
    						tadd.setDescription(sadd.getDescription());
    						tadd.setRetailPrice(sadd.getRetailPrice());
    					}
    					break;
    				}
    				
    			}
    		}
    		}
    		
    		for(SOAddon permitAddon: permitAddons){
    			permitAddon.setMiscInd(true);
    			permitAddon.setScopeOfWork(scopeOfWorkForPermit);
	        	if(null==permitAddon.getCoverage()||permitAddon.getCoverage()==""){
	        		permitAddon.setCoverage(coverageForPermit);
	        	}
    			target.getAddons().add(permitAddon);
    		}
    		
    		
    		//calculate service fee and distribute it so we do not have penny problem
    		BigDecimal totalAddon = target.getTotalAddonWithoutPermits();
    		BigDecimal serviceFee = getServiceFeePercentage(target);
    		//BigDecimal serviceFee = new BigDecimal("10");
    		
    		BigDecimal totalAddonServiceFee = BigDecimal.ZERO;
    		if(OFConstants.RELAY_SERVICES_BUYER.intValue()==target.getBuyerId().intValue()){    		
    			totalAddonServiceFee = RelayNetPriceCalculatorUtil.getServiceFeeForAddons(target, serviceFee);
    		}else{
    			totalAddonServiceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal(totalAddon.multiply(serviceFee).doubleValue());	
    		}
    		
    		int size = target.getAddons().size();
    		int count=0;
    		int lastAddonIndex = 0;
    		for(SOAddon tadd : target.getAddons()){
    			count++;
    			if(count <= size){
    				if(!tadd.getSku().equals("99888")){
    					if(OFConstants.RELAY_SERVICES_BUYER.intValue()==target.getBuyerId().intValue()){
    						tadd.setServiceFee(RelayNetPriceCalculatorUtil.getServiceFeeForAddon(tadd, serviceFee));
    					}else{
    						tadd.setServiceFee(MoneyUtil.getRoundedMoneyCustomBigDecimal(serviceFee.multiply(tadd.getAddonPrice()).doubleValue()));
    					}
    					
    					totalAddonServiceFee = totalAddonServiceFee.subtract(tadd.getServiceFee());
    					if(tadd.getQuantity()>0){
        					lastAddonIndex = count;
    					}
    				}else{
    					tadd.setServiceFee(new BigDecimal(0.00));
    				}
    			} 
    		}
    		if(lastAddonIndex>0)
    		{
    		SOAddon tadd = target.getAddons().get(lastAddonIndex - 1);
    		if(tadd.getServiceFee()!=null){
    			tadd.setServiceFee(tadd.getServiceFee().add(totalAddonServiceFee));
    		}else{
    			tadd.setServiceFee(totalAddonServiceFee);
    		}
    	}
    	}
    }
    
	private void setInvoicePartLocationAndDocumentList(ServiceOrder serviceOrder) {
		if(null!= serviceOrder.getSoProviderInvoiceParts() && !(serviceOrder.getSoProviderInvoiceParts().isEmpty())){
    		for(SOProviderInvoiceParts invoicePart:serviceOrder.getSoProviderInvoiceParts()){
    			if( null!= invoicePart.getInvoicePartLocation()){
    			   SoProviderInvoicePartLocation oldLocationObj = invoicePart.getInvoicePartLocation();
    			   SoProviderInvoicePartLocation newLocationObj = new SoProviderInvoicePartLocation();
    			   newLocationObj.setSupplierName(oldLocationObj.getSupplierName());
    			   newLocationObj.setStreet1(oldLocationObj.getStreet1());
    			   newLocationObj.setStreet2(oldLocationObj.getStreet2());
    			   newLocationObj.setCity(oldLocationObj.getCity());
    			   newLocationObj.setState(oldLocationObj.getState());
    			   newLocationObj.setZip(oldLocationObj.getZip());
    			   newLocationObj.setPhoneNo(oldLocationObj.getPhoneNo());
    			   newLocationObj.setFaxNo(oldLocationObj.getFaxNo());
    			   newLocationObj.setLatitude(oldLocationObj.getLatitude());
    			   newLocationObj.setLongitude(oldLocationObj.getLongitude());
    			   newLocationObj.setDistance(oldLocationObj.getDistance());
    			   partLocationMap.put(invoicePart.getPartsInvoiceId(),newLocationObj);
    			}
    			if(null!= invoicePart.getDocumentList() && !(invoicePart.getDocumentList().isEmpty())){
    			   List<SoProviderInvoicePartDocument>	newInvoicePartDocumentList = new ArrayList<SoProviderInvoicePartDocument>();
    			   List<SoProviderInvoicePartDocument> oldInvoicePartDocumentList = invoicePart.getDocumentList();
    			   for(SoProviderInvoicePartDocument invoiceDocument : oldInvoicePartDocumentList){
    				   SoProviderInvoicePartDocument newInvoiceDocument = new SoProviderInvoicePartDocument();
    				   newInvoiceDocument.setInvoiceDocumentId(invoiceDocument.getInvoiceDocumentId());
    				   newInvoicePartDocumentList.add(newInvoiceDocument);
    			   }
    			   invoiceDocumentListMap.put(invoicePart.getPartsInvoiceId(),newInvoicePartDocumentList);	
    			}
    		}
    	}
		
	}
    
	private void setPartsLocationAndDocumentList(ServiceOrder serviceOrder,List<Integer> invoiceIdList) {
		if(null!= serviceOrder.getSoProviderInvoiceParts() && !(serviceOrder.getSoProviderInvoiceParts().isEmpty() && null!= invoiceIdList)){
			  for(Integer partInvoiceId:invoiceIdList){
				  for(SOProviderInvoiceParts invoiceParts: serviceOrder.getSoProviderInvoiceParts()){
				    if(null!= partInvoiceId && null!= invoiceParts.getPartsInvoiceId() && partInvoiceId.equals(invoiceParts.getPartsInvoiceId()) ){
					   SoProviderInvoicePartLocation invoicePartLocation = null;
					   List<SoProviderInvoicePartDocument> documentList =null;
					    if(partLocationMap.size()>0){
					       invoicePartLocation = partLocationMap.get(partInvoiceId);
					       if(null!= invoicePartLocation){
					    	 invoicePartLocation.setInvoiceParts(invoiceParts);   
					         invoiceParts.setInvoicePartLocation(invoicePartLocation);
					       }
					   }
					    if(invoiceDocumentListMap.size() > 0){
						   documentList = invoiceDocumentListMap.get(partInvoiceId);
						   if(null != documentList && !(documentList.isEmpty())){
							     for(SoProviderInvoicePartDocument invoiceDoc:documentList){
							    	 invoiceDoc.setInvoiceParts(invoiceParts);
							      }
						       invoiceParts.setDocumentList(documentList);
						   }
					}
					
				}
			   }
			}
			
		}
	}
    private SOAdditionalPaymentHistory setAdditionalPaymentHistory(ServiceOrder inputSO,ServiceOrder servieOrderInDB){
    	SOAdditionalPaymentHistory sOAdditionalPaymentHistory =null;
    	String paymentType=null;
    	boolean isHistoryLoggingRequired = checkChangeInAddOnPayment(inputSO,servieOrderInDB);
    	if(isHistoryLoggingRequired){
    		logger.info("Creating Additional Payment History Object");
    		sOAdditionalPaymentHistory = new SOAdditionalPaymentHistory();
	    	paymentType = inputSO.getAdditionalPayment().getPaymentType().getShortName();	
	    	Timestamp dateTime = new Timestamp(System.currentTimeMillis());
			sOAdditionalPaymentHistory.setModifiedDate(dateTime);
			sOAdditionalPaymentHistory.setCreatedDate(dateTime);
			sOAdditionalPaymentHistory.setSoId(inputSO.getAdditionalPayment().getServiceOrder().getSoId());
			sOAdditionalPaymentHistory.setDescription(inputSO.getAdditionalPayment().getDescription());
			sOAdditionalPaymentHistory.setPaymentAmount(inputSO.getAdditionalPayment().getPaymentAmount());
			sOAdditionalPaymentHistory.setPaymentType(inputSO.getAdditionalPayment().getPaymentType());
	 		if(StringUtils.isNotBlank(paymentType) && !StringUtils.equals(paymentType, "CA")){
	 			 sOAdditionalPaymentHistory.setCardType(inputSO.getAdditionalPayment().getCardType());
		 		 sOAdditionalPaymentHistory.setCreditCardNumber(inputSO.getAdditionalPayment().getCreditCardNumber());
		 		 sOAdditionalPaymentHistory.setAuthorizationNumber(inputSO.getAdditionalPayment().getAuthorizationNumber());
				 sOAdditionalPaymentHistory.setExpirationDateMonth(inputSO.getAdditionalPayment().getExpirationDateMonth());
			 	 sOAdditionalPaymentHistory.setExpirationDateYear(inputSO.getAdditionalPayment().getExpirationDateYear());
                 sOAdditionalPaymentHistory.setMaskedAccountNumber(inputSO.getAdditionalPayment().getMaskedAccountNumber());
		         sOAdditionalPaymentHistory.setToken(inputSO.getAdditionalPayment().getToken());
		    	 sOAdditionalPaymentHistory.setResponse(inputSO.getAdditionalPayment().getResponseXML());
			}// Check Details
		 	else{
		 		 sOAdditionalPaymentHistory.setCheckNumber(inputSO.getAdditionalPayment().getCheckNumber());
		    }
			logger.info("inside OF leaving , setting paymnetInfo"+sOAdditionalPaymentHistory.getSoId());
    	}
    	return sOAdditionalPaymentHistory;
    	
    }
	
    private boolean checkChangeInAddOnPayment(ServiceOrder inputSO,ServiceOrder servieOrderInDB) {
    	boolean isChanged = false;
		SOAdditionalPayment requestAddPayment=null;
		SOAdditionalPayment dBAddPayment=null;
		if(null != inputSO && null!= inputSO.getAdditionalPayment()){
			requestAddPayment = inputSO.getAdditionalPayment();
			//Compare payment object from request to that present in DB
			if(null!= servieOrderInDB && null!= servieOrderInDB.getAdditionalPayment()){
				dBAddPayment = servieOrderInDB.getAdditionalPayment();
				String paymentTypeRequest = inputSO.getAdditionalPayment().getPaymentType().getShortName();	
				logger.info("Payment Type in Request :"+ inputSO.getAdditionalPayment().getPaymentType().getShortName());
				String paymentTypeDB = servieOrderInDB.getAdditionalPayment().getPaymentType().getShortName();
				logger.info("Payment Type in DB :"+ servieOrderInDB.getAdditionalPayment().getPaymentType().getShortName());
				if(!StringUtils.equals(paymentTypeRequest, paymentTypeDB)){
					isChanged =true;
					logger.info("Payment Type changed from "+ paymentTypeDB+"to"+ paymentTypeRequest);
				}//No Change in payment type.Hence checking other parameters
				else{
					//Check payment amount is changed or not.
					if(null!= requestAddPayment.getPaymentAmount() && null!= dBAddPayment.getPaymentAmount()){
						     String dbAmount = String.format("%.2f", dBAddPayment.getPaymentAmount());
						     String requestAmount =  String.format("%.2f", requestAddPayment.getPaymentAmount());
						     if(!StringUtils.equals(dbAmount, requestAmount)){
						    	 isChanged = true;
						    	 logger.info("payment amount changed from db amount"+dBAddPayment.getPaymentAmount()+ "to request amount"+ requestAddPayment.getPaymentAmount());
								}
				    }
				    //Payment through check.validate check no 
					else if(paymentTypeRequest.equals(paymentTypeDB) && paymentTypeRequest.equals("CA")){
						if(null!= requestAddPayment.getCheckNumber() && null!= dBAddPayment.getCheckNumber()
							&&!StringUtils.equals(requestAddPayment.getCheckNumber(), dBAddPayment.getCheckNumber())){
							isChanged = true;
						    logger.info("check number changed  from db "+dBAddPayment.getCheckNumber()+ "to request check no"+ requestAddPayment.getCheckNumber());
						}
					}else if(paymentTypeRequest.equals(paymentTypeDB) && !paymentTypeRequest.equals("CA")){
						//validate credit card is added or edited
						if(StringUtils.isNotBlank(requestAddPayment.getEditOrCancel())
						  && (requestAddPayment.getEditOrCancel().equals("added")
							 ||(requestAddPayment.getEditOrCancel().equals("edited")))){
							isChanged = true;
							logger.info("card added or edited EditOrCancel "+requestAddPayment.getEditOrCancel());
						}
						//validate credit card type
						if(null!= requestAddPayment.getCardType() && null!= dBAddPayment.getCardType()
							&& !requestAddPayment.getCardType().equals(dBAddPayment.getCardType())){
							isChanged = true;
							logger.info("card type changed  from db "+dBAddPayment.getCardType()+ "to request card type"+ requestAddPayment.getCardType());
						}
						//validate credit card no
						else if(null!= requestAddPayment.getCreditCardNumber() && null!= dBAddPayment.getCreditCardNumber()
							&& !StringUtils.equals(requestAddPayment.getCreditCardNumber(),dBAddPayment.getCreditCardNumber())){
							isChanged = true;
							logger.info("encrypted credit card  from db "+dBAddPayment.getCreditCardNumber()+ "to request en credit card no "+ requestAddPayment.getCreditCardNumber());
						}
						//validate expiration year
						else if(null!= requestAddPayment.getExpirationDateYear() && null!= dBAddPayment.getExpirationDateYear()
								&& !requestAddPayment.getExpirationDateYear().equals(dBAddPayment.getExpirationDateYear())){
							isChanged = true;
							logger.info("Expiration Year changed  from db "+dBAddPayment.getExpirationDateYear()+ "to request Expiration Year"+ requestAddPayment.getExpirationDateYear());
						}
						//validate expiration month
						else if(null!= requestAddPayment.getExpirationDateMonth() && null!= dBAddPayment.getExpirationDateMonth()
								&& !requestAddPayment.getExpirationDateMonth().equals(dBAddPayment.getExpirationDateMonth())){
							isChanged = true;
							logger.info("Expiration Month changed  from db "+dBAddPayment.getExpirationDateMonth()+ "to request Expiration month"+ requestAddPayment.getExpirationDateMonth());
						}
						//validate pre authorization number
						else if(null!= requestAddPayment.getAuthorizationNumber() && null!= dBAddPayment.getAuthorizationNumber()
								&& !requestAddPayment.getAuthorizationNumber().equals(dBAddPayment.getAuthorizationNumber())){
							isChanged = true;
							logger.info("authorization number changed  from db "+dBAddPayment.getAuthorizationNumber()+ "to request auth number"+ requestAddPayment.getAuthorizationNumber());
						}
					}
				}
			}else{
			//No additional information is present for the order in DB,Hence need to login history
		    logger.info("No additional information is present for the order in DB,Hence need to login history");
			isChanged = true;
			}
		}
		
		return isChanged;
	}

	public void setMarketPlatformBO(IMarketPlatformPromoBO marketPlatformBO) {
		this.marketPlatformBO = marketPlatformBO;
	}
	
	private BigDecimal getServiceFeePercentage(ServiceOrder serviceOrder) {
		double percent = marketPlatformBO.getPromoFee(serviceOrder.getSoId(), serviceOrder.getBuyerId(), FeeType.PromoServiceFee);
		return new BigDecimal(percent);
	}

}
