package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;

import com.servicelive.marketplatform.common.vo.InvoicePartsVO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.vo.InvoicePartsPricingVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class CompleteOrderCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder serviceOrder = getServiceOrder(processVariables);

		//if there are add-ons then call wallet		
		if(3000 != serviceOrder.getBuyerId().intValue() && null != serviceOrder.getAddons() && serviceOrder.getAddons().size() > 0){
			String userName = getUserName(processVariables);
			String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
            Double spendLimit = walletGateway.getCurrentSpendingLimit(serviceOrder.getSoId());            
            BigDecimal totalSpendLimit = getTotalSpendLimit(serviceOrder);       
            logger.info("totalSpendLimit ************** "+totalSpendLimit.doubleValue());
            logger.info("spendLimit ************** "+spendLimit);
            if(totalSpendLimit.doubleValue() > spendLimit){            	
                double amount = totalSpendLimit.doubleValue() - spendLimit;
                processVariables.put(OrderfulfillmentConstants.AUTO_CLOSE_PENDING_TRANSACTION,"pending");
                walletGateway.increaseSpendOnCompletion(userName, serviceOrder, buyerState, amount);
                
            }else if (totalSpendLimit.doubleValue() < spendLimit){
                double amount = spendLimit - totalSpendLimit.doubleValue(); 
                processVariables.put(OrderfulfillmentConstants.AUTO_CLOSE_PENDING_TRANSACTION,"pending");
                walletGateway.decreaseSpendLimit(userName, serviceOrder, buyerState, amount);
		}
	}
				
		// for hsr		
		//if there are add-ons or provider invoice parts, then call wallet		
		if(3000 == serviceOrder.getBuyerId().intValue()){
			
			String userName = getUserName(processVariables);
			String buyerState = ProcessVariableUtil.extractBuyerState(processVariables);
            Double spendLimit = walletGateway.getCurrentSpendingLimit(serviceOrder.getSoId());   
            //double addonPrice=0.0d;
           //addonPrice =getTotalSpendLimit(serviceOrder).doubleValue()- spendLimit;
            BigDecimal totalSpendLmt =null;
                
            // recalculate the parts invoice price while completing the order.
            
            double totalFinalpaymentPartsInvoice=0.0d;
            double retailCostToInventoryTotal = 0.0d;
		    double retailReimbursementTotal = 0.0d;
		    double retailPriceSLGrossUpTotal = 0.0d;
		    double retailSLGrossUpTotal = 0.0d;           
        	boolean isUpdate=false;
        	
        	
        	
            if(null!=serviceOrder.getSoProviderInvoiceParts() && serviceOrder.getSoProviderInvoiceParts().size()>0){
            	          	
            	isUpdate=true;
            	
            	//cost plus model
            	
            	if(null!=serviceOrder.getSOWorkflowControls() && null!=serviceOrder.getSOWorkflowControls().getInvoicePartsPricingModel() && 
            			serviceOrder.getSOWorkflowControls().getInvoicePartsPricingModel().equals("COST_PLUS")){
            		
            		
            	 	
                    double costToInventory = MPConstants.HSR_COST_TO_INVENTORY;
            		double percentage = 10.0;
            		double commercialPrice=0.0;
            		
            		
            		Double reimbursementRate =0.0;
            		Double slGrossUpValue = 0.0;
            		// for adjudication Tolerance
            		Double adjTolerance = 0.0;
            		Double adjCommercialPriceConstant = 0.0;
            		
            		//SL-20647
            		Integer manualCount = 0;
             		Integer estMaxLimitCount = 0;
             		Integer partsCount = 0;
             		
            		Map<String, String> autoAdjudicationValuesMap=	serviceOrderDao.getAdjudicationConstants();
            		
        			String reimbursementRateValue=autoAdjudicationValuesMap.get("auto_adjudication_default_reimbursement_rate");
        			String slGrossUp=autoAdjudicationValuesMap.get("auto_adjudication_grossup_value");
        		    String adjToleranceValue=autoAdjudicationValuesMap.get("adjudication_tolerance");
        		    String adjCommercialPricePercentage=autoAdjudicationValuesMap.get("adjudication_commercial_discount_percentage"); 
        		    
        		    
        		    if (StringUtils.isNotEmpty(reimbursementRateValue)){
        				reimbursementRate = Double.parseDouble(reimbursementRateValue);
        			}
        			if (StringUtils.isNotEmpty(slGrossUp)){
        				slGrossUpValue = Double.parseDouble(slGrossUp);
        			}
        			if (StringUtils.isNotEmpty(adjToleranceValue)){
        				adjTolerance= Double.parseDouble(adjToleranceValue);
        			}
        			if (StringUtils.isNotEmpty(adjCommercialPricePercentage)){
        				adjCommercialPriceConstant= Double.parseDouble(adjCommercialPricePercentage);
        			}
        			
        			if(null!=serviceOrder.getAcceptedProviderId()){
        			Double reimbursementRateForVendor=serviceOrderDao.getReimbursementRate(serviceOrder.getAcceptedProviderId().intValue());
        			
        			if(null!=reimbursementRateForVendor){
        				reimbursementRate=reimbursementRateForVendor;
        			}
        			
        			}
            	
            	for(SOProviderInvoiceParts  invoicePart: serviceOrder.getSoProviderInvoiceParts()){          
            
			commercialPrice=MoneyUtil.getRoundedMoney(invoicePart.getRetailPrice().doubleValue() - (invoicePart.getRetailPrice().doubleValue()*(adjCommercialPriceConstant/100.00)));	
			
    		// provider cost= unit cost * quantity
    		double providerCost=MoneyUtil.getRoundedMoney(invoicePart.getUnitCost().doubleValue()*invoicePart.getQty().doubleValue());
    		// Est. Provider Payment = providerCost + (providerCost * Reimbursement Rate/100) 
    		double netPayment = MoneyUtil.getRoundedMoney(providerCost + (( (providerCost*(reimbursementRate/100.00)*100)/100))) ;	
    		//Final price= 	Est. Provider Payment  * 1.1111	
			double constantValue = 100.00/90.00 ;
    		double finalPayment=MoneyUtil.getRoundedMoney(netPayment * constantValue) ;  
    		    		
    		// retail cost to iventory
    		double retailCostToInventory = MoneyUtil.getRoundedMoney(netPayment * (70.00 / 100.00));
    		// retail reimbursement
    		double retailReimbursement = MoneyUtil.getRoundedMoney(netPayment * (30.00 / 100.00));
    		// retail price sl gross up
    		double retailPriceSLGrossUp = MoneyUtil.getRoundedMoney(finalPayment  * (10.00 / 100.0));
    		// retail sl gross up???
    		double retailSLGrossUp = MoneyUtil.getRoundedMoney(finalPayment); 
    						
    		// provider Margin= final price - provider cost
    		double providerMargin=MoneyUtil.getRoundedMoney(netPayment-providerCost);
    				
    		//Reimbursement Max = Retail price + (Retail Price * (Reimbursement Rate % + Adjudication Tolerance %))
    		double reimbursementMax = MoneyUtil.getRoundedMoney(( commercialPrice +(commercialPrice * ( (reimbursementRate/100.00) + (adjTolerance/100.00))))*invoicePart.getQty().doubleValue());
    				
    		//If Estimated Net Payment is less than or equal to Reimbursement Max then, the claim status = Eligible else Not Eligible
    		String claimStatus=""; 
    		 if( !("MANUAL".equalsIgnoreCase(invoicePart.getPartSource())) &&  netPayment<=reimbursementMax){
    				claimStatus="Approved";
    			}else{
    				claimStatus="Pending";
    			}
    		
            
    		 if (null != invoicePart.getPartStatus()
						&& invoicePart.getPartStatus().equalsIgnoreCase(
								"Installed")) {
    			 //To get the count of parts added manually and netPayment>reimbursementMax
    			if(("MANUAL".equalsIgnoreCase(invoicePart.getPartSource())) &&  netPayment<=reimbursementMax){
    				manualCount++;
    			}
    			else if(netPayment>reimbursementMax){
    				estMaxLimitCount++;
    			}  			
        			partsCount++;
   			      		       		 
    		totalFinalpaymentPartsInvoice=totalFinalpaymentPartsInvoice+finalPayment;
     		retailCostToInventoryTotal=retailCostToInventoryTotal+retailCostToInventory;
     		retailReimbursementTotal = retailReimbursementTotal+retailReimbursement;
  		    retailPriceSLGrossUpTotal =retailPriceSLGrossUpTotal+retailPriceSLGrossUp;
  		    retailSLGrossUpTotal = totalFinalpaymentPartsInvoice;// retailSLGrossUpTotal+retailSLGrossUp*invoicePart.getQty();
    		 }
  		  
    		 invoicePart.setClaimStatus(claimStatus);
    		 invoicePart.setEstProviderPartsPpayment(new BigDecimal(netPayment));
    		 invoicePart.setFinalPrice(new BigDecimal(finalPayment));
    		 invoicePart.setRetailCostToInventory(String.valueOf(retailCostToInventory));
    		 invoicePart.setRetailReimbursement(String.valueOf(retailReimbursement));
    		 invoicePart.setRetailPriceSLGrossUp(String.valueOf(retailPriceSLGrossUp));
    		 invoicePart.setRetailSLGrossUp(String.valueOf(retailSLGrossUp));
    		 invoicePart.setProviderMargin(new BigDecimal(providerMargin));
    		 invoicePart.setCommercialPrice(new BigDecimal(commercialPrice));
    		 invoicePart.setReimbursementRate(String.valueOf(reimbursementRate)) ;   		 
    		 
            	}
            	//SL-20647 set the process variables
            	processVariables.put(ProcessVariableUtil.PVKEY_MANUAL_COUNT, manualCount);
            	processVariables.put(ProcessVariableUtil.PVKEY_ESTMAXLIMIT_COUNT, estMaxLimitCount);
            	processVariables.put(ProcessVariableUtil.PVKEY_PARTS_COUNT, partsCount);
            	
            	logger.info("manualCount "+manualCount);
            	logger.info("estMaxLimitCount "+estMaxLimitCount);
            	logger.info("partsCount "+partsCount);
            	
            	logger.info("totalFinalpaymentPartsInvoice "+totalFinalpaymentPartsInvoice);
        		logger.info("retailCostToInventoryTotal "+retailCostToInventoryTotal);
        		logger.info("retailReimbursementTotal "+retailReimbursementTotal);
        		logger.info("retailPriceSLGrossUpTotal "+retailPriceSLGrossUpTotal);
        		logger.info("retailSLGrossUpTotal "+retailSLGrossUpTotal);
            	
            	
            	}
            	else{ // reimbursement model
            		  double costToInventory = MPConstants.HSR_COST_TO_INVENTORY;
              		double percentage = 10.0;
              		double commercialPrice=0.0;
              		
              		//Double reimbursementRate =80.00;
              		//Double slGrossUpValue = 0.8889;
              		Double reimbursementRate =0.00;
              		Double slGrossUpValue = 0.00;
              		// for adjudication Tolerance
              		Double adjCommercialPriceConstant = 0.0;
              		Double finalPriceConstant=0.0;
          			
          			
              		Map<String,String> buyerPriceCalcValues=serviceOrderDao.getPartsBuyerPriceCalcValues();     
            		
            		for(SOProviderInvoiceParts  invoicePart: serviceOrder.getSoProviderInvoiceParts()){
            			try{
                    	 if(null!=buyerPriceCalcValues){
                    		 String partSource="";
                    		 String partCoverage="";
                    		 partCoverage=invoicePart.getPartCoverage();
                			 partSource=invoicePart.getSource();
                			 if(("Truck Stock").equals(invoicePart.getSource())){
                					partSource="Non Sears";	
                				}
                			 
                			 if(null!=buyerPriceCalcValues.get(partCoverage+"-"+partSource)){
                				 String value=buyerPriceCalcValues.get(partCoverage+"-"+partSource);
                				 logger.info("value"+value);
              				 String values[]= value.split("-");	
                				 reimbursementRate=Double.parseDouble(values[0]);
                				 slGrossUpValue=Double.parseDouble(values[1]);
                					

                			 }
                    		 
                    	 }
            			}catch(Exception e){
            				logger.info("exception in buyerPriceCalcValues"+e);
            			}
            		   double netPayment = MoneyUtil.getRoundedMoney((invoicePart.getRetailPrice().doubleValue()*(reimbursementRate/100)*100)/100);
         		       netPayment = netPayment*invoicePart.getQty();
         		       double retailCostToInventory = MoneyUtil.getRoundedMoney(invoicePart.getRetailPrice().doubleValue() * (costToInventory / 100.0));
         		       double retailReimbursement = MoneyUtil.getRoundedMoney((invoicePart.getRetailPrice().doubleValue() * (reimbursementRate / 100.0)) - retailCostToInventory);
         		       double retailPriceSLGrossUp = MoneyUtil.getRoundedMoney(invoicePart.getRetailPrice().doubleValue() * slGrossUpValue * (percentage / 100.0));
         		       double retailSLGrossUp = MoneyUtil.getRoundedMoney(invoicePart.getRetailPrice().doubleValue() * slGrossUpValue);
         		       double finalPayment=MoneyUtil.getRoundedMoney((invoicePart.getRetailPrice().doubleValue()*slGrossUpValue*100)/100);
         		       finalPayment=finalPayment*invoicePart.getQty();
         		
         		      if (null != invoicePart.getPartStatus()
      						&& invoicePart.getPartStatus().equalsIgnoreCase(
      								"Installed")) { 		
                		totalFinalpaymentPartsInvoice=totalFinalpaymentPartsInvoice+finalPayment;
                		retailCostToInventoryTotal=retailCostToInventoryTotal+retailCostToInventory*invoicePart.getQty();
                		retailReimbursementTotal = retailReimbursementTotal+retailReimbursement*invoicePart.getQty();
             		    retailPriceSLGrossUpTotal =retailPriceSLGrossUpTotal+retailPriceSLGrossUp*invoicePart.getQty();
             		    retailSLGrossUpTotal =totalFinalpaymentPartsInvoice; //retailSLGrossUpTotal+retailSLGrossUp*invoicePart.getQty();
         		      }
         		      
                		logger.info("netPayment "+netPayment);
                		logger.info("finalPayment "+finalPayment);
                		logger.info("retailCostToInventory "+retailCostToInventory);
                		logger.info("retailReimbursement "+retailReimbursement);
                		logger.info("retailPriceSLGrossUp "+retailPriceSLGrossUp);
                		logger.info("retailSLGrossUp "+retailSLGrossUp);

       		
                		 invoicePart.setEstProviderPartsPpayment(new BigDecimal(netPayment));
                		 invoicePart.setFinalPrice(new BigDecimal(finalPayment));
                		 invoicePart.setRetailCostToInventory(String.valueOf(retailCostToInventory));
                		 invoicePart.setRetailReimbursement(String.valueOf(retailReimbursement));
                		 invoicePart.setRetailPriceSLGrossUp(String.valueOf(retailPriceSLGrossUp));
                		 invoicePart.setRetailSLGrossUp(String.valueOf(retailSLGrossUp)); 
                		 invoicePart.setReimbursementRate(String.valueOf(reimbursementRate)) ;   		 

                		 
                        	}
            		
            		logger.info("totalFinalpaymentPartsInvoice "+totalFinalpaymentPartsInvoice);
            		logger.info("retailCostToInventoryTotal "+retailCostToInventoryTotal);
            		logger.info("retailReimbursementTotal "+retailReimbursementTotal);
            		logger.info("retailPriceSLGrossUpTotal "+retailPriceSLGrossUpTotal);
            		logger.info("retailSLGrossUpTotal "+retailSLGrossUpTotal);           		
            		
            	}
            
            }
             
           
        	totalSpendLmt=getTotalSpendLimitForHSR(serviceOrder);	
        	
        	logger.info("totalSpendLmt "+totalSpendLmt);
        	
        	double previousPartsPrice=0.0d;
        	// SLM-59 get the previous parts price from the so_work_flow_controls
      	    if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsFinalPrice())
			{
      		previousPartsPrice=serviceOrder.getPrice().getInvoicePartsFinalPrice().doubleValue();
			}
        	logger.info("previousPartsPrice "+previousPartsPrice);

 			double previousAddOnPrice=0.0d;
        	Object addOnsPrice = processVariables.get(OrderfulfillmentConstants.PVKEY_ADDONPRICE);
   			if(addOnsPrice!=null && !addOnsPrice.toString().equals(""))
   			{
   				previousAddOnPrice=Double.parseDouble(addOnsPrice.toString());
   			}
   			
        	logger.info("previousAddOnPrice "+previousAddOnPrice);

   			
   			double spendLimitLaborParts=serviceOrder.getTotalSpendLimit().doubleValue();
   			
   			double amountForIncrease=0.0d;
   			
   			
   			amountForIncrease=spendLimitLaborParts-(spendLimit-(previousPartsPrice+previousAddOnPrice));
 			
   			amountForIncrease=MoneyUtil.getRoundedMoney(amountForIncrease);
   			
   			logger.info("amountForIncrease "+amountForIncrease);
   			
   			double retailCostToInventoryTotalInitial = 0.0d;
		    double retailReimbursementTotalInitial = 0.0d;
		    double retailPriceSLGrossUpTotalInitial = 0.0d;
		    double retailSLGrossUpTotalInitial = 0.0d;
		    
		    retailCostToInventoryTotalInitial =retailCostToInventoryTotal;
		    retailReimbursementTotalInitial = retailReimbursementTotal;
		    retailPriceSLGrossUpTotalInitial = retailPriceSLGrossUpTotal;
		    retailSLGrossUpTotalInitial = retailSLGrossUpTotal;
		    
   			if(amountForIncrease>0)
   			{
   				
   				spendLimit=spendLimit+amountForIncrease;
   				WalletResponseVO resp = walletGateway.increaseSpendLimit(userName, serviceOrder, buyerState, amountForIncrease);
   		        String messageId = resp.getMessageId();
   		        String jmsMessageId = resp.getJmsMessageId();
   		        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
   		        processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);
   				
   				
   			}
   			else if(amountForIncrease<0)
   			{
   				logger.info("amountForIncreaseTEST"+amountForIncrease);
   				spendLimit=spendLimit+amountForIncrease;
   				double amountForDecrease=0.0d;
   				amountForDecrease=amountForIncrease * -1.00d;
   				amountForDecrease=MoneyUtil.getRoundedMoney(amountForDecrease);
   				logger.info("amountForDecreaseTEST "+amountForDecrease);
   				WalletResponseVO resp = walletGateway.decreaseSpendLimit(userName, serviceOrder, buyerState, amountForDecrease);
   				String messageId = resp.getMessageId();
   				String jmsMessageId = resp.getJmsMessageId();
   				processVariables.put(ProcessVariableUtil.PVKEY_WALLET_MESSAGE_ID, messageId);
   				processVariables.put(ProcessVariableUtil.PVKEY_WALLET_JMS_MESSAGE_ID, jmsMessageId);

   			}
   			
            
                  logger.info("TTLSPENDLIMIT"+totalSpendLmt);
                  // spendlimit  is the wallet amount for service order
                  // totalSpendLmt is the amount for service order
            if(totalSpendLmt.doubleValue() > spendLimit){
            	
            		
                 
                 // for edit completion
                 
                 double previousRetlPrice=0.0d;
     			if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsRetailPrice())
     			{
     				previousRetlPrice=serviceOrder.getPrice().getInvoicePartsRetailPrice().doubleValue();
     				logger.info("previousRetlPrice"+ previousRetlPrice);

     			}
     			
     			
     			double previousRembRetlPrice=0.0d;
           	   
      			if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsRetailReimbursement())
      			{

      				previousRembRetlPrice=serviceOrder.getPrice().getInvoicePartsRetailReimbursement().doubleValue();
     				logger.info("previousRembRetlPrice"+ previousRembRetlPrice);

      			}
     			
      			
      			double previousPartsSLGrossUp=0.0d;
      			double previousRetlSLGrossUp=0.0d;
       			if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsFinalPrice())
       			{

       				previousPartsSLGrossUp=MoneyUtil.getRoundedMoneyBigDecimal(
       						serviceOrder.getPrice().getInvoicePartsFinalPrice().doubleValue()*(10.00/100.00)).doubleValue();
       				
     				logger.info("previousPartsSLGrossUp"+ previousPartsSLGrossUp); 
    				previousRetlSLGrossUp=serviceOrder.getPrice().getInvoicePartsFinalPrice().doubleValue();

     				logger.info("previousRetlSLGrossUp"+ previousRetlSLGrossUp); 

       			}
      			
     			
       			
    			// update price only if previous amount is greater than 0.  
    			
    			if(previousRetlPrice>0)
    			{
    				retailCostToInventoryTotal=retailCostToInventoryTotal-previousRetlPrice;	
    			}
    			
    			if(previousRembRetlPrice>0)
    			{
    				retailReimbursementTotal=retailReimbursementTotal-previousRembRetlPrice;
    			}
    			
    			if(previousPartsSLGrossUp>0)
    			{
    				retailPriceSLGrossUpTotal=retailPriceSLGrossUpTotal-previousPartsSLGrossUp;
    			} 
    			
    			if(previousRetlSLGrossUp>0)
    			{
    				retailSLGrossUpTotal=retailSLGrossUpTotal-previousRetlSLGrossUp;
    			}
    			
                 // if price is negative then update it as zero.
    			
    			if(retailCostToInventoryTotal<0)
    			{
    				retailCostToInventoryTotal=0.0d;
    			}
                 if(retailReimbursementTotal<0)
                 {
                	 retailReimbursementTotal=0.0d; 
                 }
                 if(retailPriceSLGrossUpTotal<0)
                 {
                	 retailPriceSLGrossUpTotal=0.0d; 
                 }
                 if(retailSLGrossUpTotal<0)
                 {
                	 retailSLGrossUpTotal=0.0d;
                 }
                 
                  logger.info("retailCostToInventoryTotal: "+retailCostToInventoryTotal);
                  logger.info("retailReimbursementTotal: "+retailReimbursementTotal);
                  logger.info("retailPriceSLGrossUpTotal: "+retailPriceSLGrossUpTotal);
                  logger.info("retailSLGrossUpTotal: "+retailSLGrossUpTotal);

                 
                 
                    double amount = totalSpendLmt.doubleValue() - spendLimit;//This amount will include both addons and part price
                    processVariables.put(OrderfulfillmentConstants.AUTO_CLOSE_PENDING_TRANSACTION,"pending");
                    
                    boolean hasAddon=false;
                    boolean hasParts=false;
                    double addOnAmount=0.0d;
                    double partsAmount=0.0d;
                   
                    
                   if( null != serviceOrder.getAddons() && serviceOrder.getAddons().size() > 0)
                   {
                	   hasAddon=true;
                	   
           			double previousParts=0.0d; 
        			if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsFinalPrice())
        			{
        				previousParts=serviceOrder.getPrice().getInvoicePartsFinalPrice().doubleValue();
        			}
        			logger.info("previousParts"+previousParts);
                	   addOnAmount= getTotalSpendLimit(serviceOrder).doubleValue()- (spendLimit-previousParts);
                	  
                   } 
                    
                	hasParts=true;
                	double previousAddOn=0.0d;
                	Object addOnPrice = processVariables.get(OrderfulfillmentConstants.PVKEY_ADDONPRICE);
           			if(addOnPrice!=null && !addOnPrice.toString().equals(""))
           			{
           				previousAddOn=Double.parseDouble(addOnPrice.toString());
           			}
           			
           			logger.info("previousAddOn"+previousAddOn);
                	   partsAmount=getTotalSpendLimitForHSRwithoutAddOn(serviceOrder).doubleValue()-(spendLimit-previousAddOn);
                	   
                  
                   logger.info("ADDPARTAMOUNT"+amount);
                   logger.info("ADDON1AMOUNT"+addOnAmount);
                   logger.info("PART2AMOUNT"+partsAmount);
                     
                    
                    walletGateway.increaseSpendOnCompletionForHSR(userName, serviceOrder, buyerState, amount, retailCostToInventoryTotal, retailReimbursementTotal, retailPriceSLGrossUpTotal, retailSLGrossUpTotal,hasAddon,hasParts,addOnAmount,partsAmount);
                      
            }else if (totalSpendLmt.doubleValue() < spendLimit){
                double amount = spendLimit - totalSpendLmt.doubleValue(); 
                processVariables.put(OrderfulfillmentConstants.AUTO_CLOSE_PENDING_TRANSACTION,"pending");
                walletGateway.decreaseSpendLimit(userName, serviceOrder, buyerState, amount);
		}
	
		
                                 
            serviceOrder.getPrice().setInvoicePartsRetailPrice(BigDecimal.valueOf(retailCostToInventoryTotalInitial));
            serviceOrder.getPrice().setInvoicePartsRetailReimbursement(BigDecimal.valueOf(retailReimbursementTotalInitial));
            serviceOrder.getPrice().setInvoicePartsRetailSlGrossUp(BigDecimal.valueOf(totalFinalpaymentPartsInvoice));
            serviceOrder.getPrice().setInvoicePartsFinalPrice(BigDecimal.valueOf(totalFinalpaymentPartsInvoice));
           
            try{
            	serviceOrderDao.update(serviceOrder);
            }catch(Exception e){
            	logger.info("exception in updating service order CompleteOrder Cmd"+e);
            }
            
		
		
		
		}
			
		
	}
	
	
    /**
     * This is separated out for the different methods of calculating the addons
     * @param serviceOrder
     * @return
     */
    protected BigDecimal getTotalSpendLimit(ServiceOrder serviceOrder) {
        //return serviceOrder.getTotalSpendLimitWithPostingFee().add(serviceOrder.getTotalAddon(false)).setScale(2, RoundingMode.HALF_EVEN);
    	logger.info("SpendLimitWithPostingFee ****************"+serviceOrder.getTotalSpendLimitWithPostingFee());
    	logger.info("SpendLimit ****************"+serviceOrder.getTotalSpendLimit());
        return MoneyUtil.getRoundedMoneyBigDecimal(serviceOrder.getTotalSpendLimit().add(serviceOrder.getTotalAddon(false)).doubleValue());
    }
    
    protected BigDecimal getTotalSpendLimitForHSR(ServiceOrder serviceOrder) {
        //return serviceOrder.getTotalSpendLimitWithPostingFee().add(serviceOrder.getTotalAddon(false)).setScale(2, RoundingMode.HALF_EVEN);
    	logger.info("SpendLimitWithPostingFee ****************"+serviceOrder.getTotalSpendLimitWithPostingFee());
    	logger.info("SpendLimit ****************"+serviceOrder.getTotalSpendLimit());
        BigDecimal addOnSpendLimit = serviceOrder.getTotalSpendLimit().add(serviceOrder.getTotalAddon(true));
        logger.info("ADDDddOnSpendLimit"+addOnSpendLimit);
        return MoneyUtil.getRoundedMoneyBigDecimal(addOnSpendLimit.add(serviceOrder.getPartsPrice()).doubleValue());
    }
    
    
    
    protected BigDecimal getTotalSpendLimitForHSRWithAdjudication(ServiceOrder serviceOrder,Double partsFinalPayment) {
        //return serviceOrder.getTotalSpendLimitWithPostingFee().add(serviceOrder.getTotalAddon(false)).setScale(2, RoundingMode.HALF_EVEN);
    	logger.info("SpendLimitWithPostingFee ****************"+serviceOrder.getTotalSpendLimitWithPostingFee());
    	logger.info("SpendLimit ****************"+serviceOrder.getTotalSpendLimit());
        BigDecimal addOnSpendLimit = serviceOrder.getTotalSpendLimit().add(serviceOrder.getTotalAddon(true));
        logger.info("ADDDddOnSpendLimit"+addOnSpendLimit);
        return MoneyUtil.getRoundedMoneyBigDecimal(addOnSpendLimit.add(new BigDecimal(partsFinalPayment)).doubleValue());
    }
    
    protected BigDecimal getTotalSpendLimitForHSRwithoutAddOn(ServiceOrder serviceOrder) {
        //return serviceOrder.getTotalSpendLimitWithPostingFee().add(serviceOrder.getTotalAddon(false)).setScale(2, RoundingMode.HALF_EVEN);
    	
    	
        return MoneyUtil.getRoundedMoneyBigDecimal(serviceOrder.getTotalSpendLimit().add(serviceOrder.getPartsPrice()).doubleValue());

    }
    
    
    
    
    
    
    

}
