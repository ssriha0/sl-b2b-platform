package com.servicelive.orderfulfillment.common;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

public class WalletGateway implements IWalletGateway {
	/** Number of decimals to retain. Also referred to as "scale". */
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();
	/** Defined centrally, to allow for easy changes to the rounding mode. */
	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final  Integer PERMIT_TASK = 1;
	protected final Logger logger = Logger.getLogger(getClass());
	protected QuickLookupCollection quickLookupCollection;
	private IWalletBO walletBO;
	private IServiceOrderDao serviceOrderDao;

	public WalletResponseVO postServiceOrder(String userName, ServiceOrder so, String buyerState) {
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		BigDecimal achAmount = so.getTotalSpendLimit().add(so.getPostingFee());
		WalletVO request = walletRequestBuilder.postServiceOrder(userName,
			so.getBuyerId(), buyerState, so.getSoId(), so.getFundingTypeId().longValue(), 
			null, so.getSpendLimitLabor().doubleValue(), so.getSpendLimitParts().doubleValue(), 
			so.getPostingFee().doubleValue(), achAmount.doubleValue() );
		WalletResponseVO walletResponseVO;
		try {
			walletResponseVO = walletBO.postServiceOrder(request);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
		return walletResponseVO;
	}

	public void voidServiceOrder(String userName, ServiceOrder serviceOrder, String buyerState) {
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		Double postingFee=serviceOrderDao.getRefundPostingFee(serviceOrder,CommonConstants.BUSINESS_TRANSACTION_VOID_SO);
        logger.info("Posting_FEE in voidServiceOrder ="+postingFee);
		WalletVO request = walletRequestBuilder.voidServiceOrder(userName,
				serviceOrder.getBuyerId(),
				buyerState,
				serviceOrder.getSoId(),
				serviceOrder.getFundingTypeId().longValue(),    // getFundingTypeId
				null,    // service.getTransactionNote() - old code did not set this
				serviceOrder.getSpendLimitLabor().doubleValue(),
				serviceOrder.getSpendLimitParts().doubleValue(),
				0.0, // addOnTotal seems to be always zero when voiding ???
				postingFee // pgupta SLT-1725
				
		);

		WalletResponseVO walletResponseVO;
		try {
			walletResponseVO = walletBO.voidServiceOrder(request);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
	}

    public WalletResponseVO cancelServiceOrderWithPenalty(String userName, ServiceOrder serviceOrder, String buyerState, String providerState) {
        IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
        Double postingFee=serviceOrderDao.getRefundPostingFee(serviceOrder,CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO);
        logger.info("Posting_FEE in cancelServiceOrderWithPenalty ="+postingFee);
        WalletVO request = walletRequestBuilder.cancelServiceOrder(
                userName,
                serviceOrder.getBuyerId(),
                buyerState,
                serviceOrder.getAcceptedProviderId(),
                providerState,
                serviceOrder.getSoId(),
                serviceOrder.getFundingTypeId().longValue(),
                null, // transactionNote,
                serviceOrder.getSpendLimitLabor().doubleValue(),
                serviceOrder.getSpendLimitParts().doubleValue(),
                0.0,  // addOnTotal
                serviceOrder.getCancellationFee().doubleValue(),
                postingFee  // As per SLT-1725
                
        );
        try {
            return walletBO.cancelServiceOrder(request);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

    public ReceiptVO getCancellationPenaltyReceipt(Long providerId, String serviceOrderId) {
        try {
            return walletBO.getTransactionReceipt(
                    providerId,
                    Long.valueOf(CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER).intValue(),
                    LedgerEntryType.LEDGER_ENTRY_RULE_ID_CANCELLATION_PENALTY,
                    serviceOrderId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

    public void cancelServiceOrderWithoutPenalty(String userName, ServiceOrder serviceOrder, String buyerState) {
        IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
        Double postingFee=serviceOrderDao.getRefundPostingFee(serviceOrder,CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY);
        logger.info("Posting_FEE in cancelServiceOrderWithoutPenalty ="+postingFee);        
        WalletVO request = walletRequestBuilder.cancelServiceOrderWithoutPenalty(
                userName,
                serviceOrder.getBuyerId(),
                buyerState,
                serviceOrder.getSoId(),
                serviceOrder.getFundingTypeId().longValue(),
                null, // transactionNote,
                serviceOrder.getSpendLimitLabor().doubleValue(),  // laborSpendLimit
                serviceOrder.getSpendLimitParts().doubleValue(),  // partsSpendLimit
                0.0,   // addOnTotal
                postingFee
        );
        try {
            walletBO.cancelServiceOrderWithoutPenalty(request);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

    public WalletResponseVO closeServiceOrder(String userName, ServiceOrder serviceOrder, String buyerState, String providerState, BigDecimal addOnTotal, BigDecimal serviceFeePercentage) {
        IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
        double serviceFee =0;
        double netServiceFee =0;
        double addonServiceFee=0;
        boolean permitInd = false;
        boolean hasParts = false;
        if(serviceOrder.getPrice()!=null && serviceOrder.getPrice().getFinalServiceFee()!=null){
        	serviceFee = serviceOrder.getPrice().getFinalServiceFee().doubleValue();
        }
        logger.info("serviceFee="+serviceFee);
        //BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
        //permitInd = buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TASK_LEVEL, serviceOrder.getBuyerId());
        logger.info("relay_tax");
        if(OFConstants.RELAY_SERVICES_BUYER.intValue()==serviceOrder.getBuyerId().intValue()){
            netServiceFee = RelayNetPriceCalculatorUtil.getNetServiceFee(serviceOrder, serviceFeePercentage).doubleValue();
            //serviceFee = netServiceFee;
        }else{
        netServiceFee = calculateNetServiceFee(serviceOrder, serviceFeePercentage).doubleValue();
        }
        logger.info("netServiceFee="+netServiceFee);
        //Consider so price type rather than buyer feature set.
        if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType()))
        	permitInd = true;
        addonServiceFee = netServiceFee - serviceFee;
        logger.info("addonServiceFee="+addonServiceFee);
        System.out.println("CMD@@@addonServiceFee:"+addonServiceFee);
        serviceFee = serviceFee + addonServiceFee;
        logger.info("serviceFee="+serviceFee);
        System.out.println("CMD@@@serviceFee:"+serviceFee);
        WalletVO request = walletRequestBuilder.closeServiceOrder(userName,
                serviceOrder.getBuyerId(),
                buyerState,
                serviceOrder.getAcceptedProviderId(),
                providerState,
                serviceOrder.getSoId(),
                serviceOrder.getFundingTypeId().longValue(), // fundingtypeId
                null, // transactionNote
                serviceOrder.getSpendLimitLabor().doubleValue(), // labor spend limit
                serviceOrder.getSpendLimitParts().doubleValue(), // parts spend limit
                serviceOrder.getFinalPriceLabor().doubleValue(), // labor cost
                serviceOrder.getFinalPriceParts().doubleValue(), // parts cost
                addOnTotal.doubleValue(),
                serviceFeePercentage.doubleValue(),serviceFee,addonServiceFee,permitInd
                );
        request.getOrderPricing().setCompletedDate(serviceOrder.getCompletedDate());
      //SL-18195 fire invoice parts rules while closure.
        if(3000==serviceOrder.getBuyerId().intValue()){
        	logger.info("3000 buyer...");
        	       	
            Double retailPrice = 0.00;
            Double reimbursementRetailPrice = 0.00;
            Double partsSLGrossup = 0.00;
            Double retailPriceSLGrossup = 0.00;            
            
            // need to remove this code.
           List <SOProviderInvoiceParts> invoiceParts = serviceOrder.getSoProviderInvoiceParts();
           /* Iterator<SOProviderInvoiceParts> iterator = invoiceParts.iterator();
            
            while(iterator.hasNext()){
            	SOProviderInvoiceParts provInvoiceParts = (SOProviderInvoiceParts) iterator.next();
            	// Sprint 5, SLM-85 To include parts status of "Installed". 
            	if(null!=provInvoiceParts.getPartStatus() &&  provInvoiceParts.getPartStatus().equalsIgnoreCase(OrderConstants.PART_STATUS_INSTALLED)){

            	
            	if(null != provInvoiceParts.getRetailPrice()){
            		if(null!=provInvoiceParts.getAutoAdjudicationInd() && provInvoiceParts.getAutoAdjudicationInd()){
                	retailPrice = retailPrice + (provInvoiceParts.getRetailPrice().doubleValue()* OrderConstants.DISCOUNT_PERCENTAGE * provInvoiceParts.getQty());
            		}
            		else{
                    	retailPrice = retailPrice + (provInvoiceParts.getRetailPrice().doubleValue() * provInvoiceParts.getQty());	
            		}
            	}                 	
            	if(null != provInvoiceParts.getRetailReimbursement() 
            			&& !StringUtils.isBlank(provInvoiceParts.getRetailReimbursement())){
                	reimbursementRetailPrice = reimbursementRetailPrice + (Double.parseDouble(provInvoiceParts.getRetailReimbursement()) * provInvoiceParts.getQty());
            	}
            	if(null != provInvoiceParts.getRetailPriceSLGrossUp() 
            			&& !StringUtils.isBlank(provInvoiceParts.getRetailPriceSLGrossUp())){
                	partsSLGrossup = partsSLGrossup + (Double.parseDouble(provInvoiceParts.getRetailPriceSLGrossUp()) * provInvoiceParts.getQty());
            	}
            	if(null != provInvoiceParts.getRetailSLGrossUp() 
            			&& !StringUtils.isBlank(provInvoiceParts.getRetailSLGrossUp())){
                	retailPriceSLGrossup = retailPriceSLGrossup + (Double.parseDouble(provInvoiceParts.getRetailSLGrossUp()) * provInvoiceParts.getQty());            
            	}
             }
            } 
            retailPrice = retailPrice * (OrderConstants.HSR_COST_TO_INVENTORY/100.0);
            retailPriceSLGrossup = retailPrice +  reimbursementRetailPrice;*/
            // end
                               
           // 2137
            if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsRetailPrice()){
           retailPrice = serviceOrder.getPrice().getInvoicePartsRetailPrice().doubleValue();
            }
            // 2138
            if(null!=serviceOrder.getPrice() && null!=serviceOrder.getPrice().getInvoicePartsRetailReimbursement()){
            	reimbursementRetailPrice = serviceOrder.getPrice().getInvoicePartsRetailReimbursement().doubleValue();
            }           
            //2139  && 2140          
            double estPaymentTotal=0.00;
            if(null!=serviceOrder.getSoProviderInvoiceParts() && serviceOrder.getSoProviderInvoiceParts().size()>0){           	
            	for(SOProviderInvoiceParts  invoicePart: serviceOrder.getSoProviderInvoiceParts()){
            		 if (null != invoicePart.getPartStatus()
       						&& invoicePart.getPartStatus().equalsIgnoreCase(
       								"Installed")) { 
            		estPaymentTotal=estPaymentTotal+invoicePart.getEstProviderPartsPpayment().doubleValue();
            		 }
            	}
            }            
            retailPriceSLGrossup=estPaymentTotal;
                      
                        
            if(retailPrice<=0.00 && reimbursementRetailPrice<=0.00 && partsSLGrossup<=0.00 && retailPriceSLGrossup<=0.00){
    			hasParts=false;
    		}else{
    			hasParts =true;
    		}
            
            //SL-18808
            if(null !=invoiceParts && invoiceParts.size()>0 && null != request.getOrderPricing()&& null !=request.getOrderPricing().getAddOnTotal() ){
            	partsSLGrossup = request.getOrderPricing().getAddOnTotal().doubleValue() - request.getOrderPricing().getSoServiceFee().doubleValue()+
								MoneyUtil.getRoundedMoneyCustomBigDecimal((serviceOrder.getFinalPriceLabor().doubleValue()+serviceOrder.getFinalPriceParts().doubleValue()
										)*serviceFeePercentage.doubleValue()).doubleValue();
            	
            	//R14.0:SL-20757 : fix for pending wallet issue
            	partsSLGrossup =MoneyUtil.getRoundedMoneyCustomBigDecimal(partsSLGrossup).doubleValue();
										
			logger.info("request.getOrderPricing().getAddOnTotal()="+request.getOrderPricing().getAddOnTotal());
        	logger.info("request.getOrderPricing().getSoServiceFee()="+request.getOrderPricing().getSoServiceFee());
            logger.info("serviceOrder.getFinalPriceLabor().doubleValue()="+serviceOrder.getFinalPriceLabor().doubleValue());
            logger.info("serviceOrder.getFinalPriceParts().doubleValue()="+serviceOrder.getFinalPriceParts().doubleValue());
            logger.info("serviceOrder.getTotalAddonWithoutPermits().doubleValue()="+serviceOrder.getTotalAddonWithoutPermits().doubleValue());
            
	            //R12_4
	            //SL-20708
	            if (null != request.getValueLink()) {
	            	// amount fired for 10005.
	            	// (final labor + final parts) - 10%(final labor + final parts)
	                double finalCost = MoneyUtil.getRoundedMoneyCustomBigDecimal((serviceOrder.getFinalPriceLabor().doubleValue() + 
	                						serviceOrder.getFinalPriceParts().doubleValue())).doubleValue() -
	                				   MoneyUtil.getRoundedMoneyCustomBigDecimal((serviceOrder.getFinalPriceLabor().doubleValue() + 
	                						serviceOrder.getFinalPriceParts().doubleValue())* serviceFeePercentage.doubleValue()).doubleValue(); 
	                
	                // amount fired for 2139 & 2140.
	                // finalCost + 90%(final price of invoice parts)
	                double estProvPaymentInvoiceParts = retailPriceSLGrossup; 
	                request.getValueLink().setProviderV1CreditAmount(
	                		MoneyUtil.getRoundedMoneyCustomBigDecimal(finalCost + estProvPaymentInvoiceParts).doubleValue());
	            }
            }
		
			request.getOrderPricing().setHSR(true);			
			request.getOrderPricing().setRetailPrices(retailPrice);
			request.getOrderPricing().setReimbursementRetailPrice(reimbursementRetailPrice);			
			request.getOrderPricing().setPartsSLGrossup(partsSLGrossup);
			request.getOrderPricing().setRetailPriceSLGrossup(retailPriceSLGrossup);
			request.getOrderPricing().setHasParts(hasParts);
        	
        }
        try {
            return walletBO.closeServiceOrder(request);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }
    
	private BigDecimal calculateNetServiceFee(ServiceOrder serviceOrder, BigDecimal serviceFeePercentage) {
    	
    	BigDecimal finalPriceLabor = serviceOrder.getFinalPriceLabor();
    	BigDecimal finalPriceParts = serviceOrder.getFinalPriceParts();
    	double totalPermitPrice = 0;
    	    /**SL-21291 : Mode code change to eliminate the probability of balance becoming 
    	       negative and order stuck in pending wallet if the order has a deleted permit task*/
	    	for (SOTask task : serviceOrder.getNonDeletedTasks()) {
				if(PERMIT_TASK.equals(task.getTaskType())){
					if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
					}
				
				}
	    	
	    	}
    	
    	finalPriceLabor = new BigDecimal(finalPriceLabor.doubleValue() - totalPermitPrice);
    	BigDecimal netServiceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal((finalPriceLabor.doubleValue()+serviceOrder.getFinalPriceParts().doubleValue()+
    			serviceOrder.getTotalAddonWithoutPermits().doubleValue()+serviceOrder.getPartsPrice().doubleValue())*serviceFeePercentage.doubleValue());

    	logger.info("finalPriceLabor.doubleValue()="+finalPriceLabor.doubleValue());
    	logger.info("serviceOrder.getFinalPriceParts().doubleValue()="+serviceOrder.getFinalPriceParts().doubleValue());
    	logger.info("serviceOrder.getTotalAddonWithoutPermits().doubleValue()="+serviceOrder.getTotalAddonWithoutPermits().doubleValue());
    	logger.info("serviceOrder.getPartsPrice().doubleValue()="+serviceOrder.getPartsPrice().doubleValue());
    	logger.info("serviceFeePercentage="+serviceFeePercentage);
    	//return finalPriceLabor.add(finalPriceParts).multiply(serviceFeePercentage).setScale(2, RoundingMode.HALF_EVEN);
    	//return MoneyUtil.getRoundedMoneyCustomBigDecimal(finalPriceLabor.add(finalPriceParts).multiply(serviceFeePercentage).doubleValue());
        return netServiceFee;
    }

	public double getCurrentSpendingLimit(String soId) {

		try {
			return walletBO.getCurrentSpendingLimit(soId);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
	}

	public double getBuyerAvailableBalance(Long buyerId) {
		try {
			return walletBO.getBuyerAvailableBalance(buyerId.longValue());
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
	}

    public ReceiptVO getBuyerPostingFeeReceipt(Long buyerId, String serviceOrderId) {
        try {
            return walletBO.getTransactionReceipt(
                    buyerId,
                    Long.valueOf(CommonConstants.LEDGER_ENTITY_TYPE_BUYER).intValue(),
                    LedgerEntryType.LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE,
                    serviceOrderId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

    public ReceiptVO getBuyerSpendLimitRsrvReceipt(Long buyerId, String serviceOrderId) {
        try {
            return walletBO.getTransactionReceipt(
                    buyerId,
                    Long.valueOf(CommonConstants.LEDGER_ENTITY_TYPE_BUYER).intValue(),
                    LedgerEntryType.LEDGER_ENTRY_RULE_ID_SO_SPEND_LIMIT,
                    serviceOrderId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

	public WalletResponseVO decreaseSpendLimit(String userName, ServiceOrder so, String buyerState, double amount ) {
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		WalletVO request = walletRequestBuilder.decreaseProjectSpendLimit(
				userName, so.getBuyerId(), buyerState,
				so.getSoId(), so.getFundingTypeId().longValue(), "", amount);
		WalletResponseVO walletResponseVO;
		try{
			walletResponseVO = walletBO.decreaseProjectSpendLimit(request);			
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO);  
		return walletResponseVO;
	}
	
	public WalletResponseVO increaseSpendLimit(String userName, ServiceOrder so, String buyerState, double amount ) {
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		WalletVO request = walletRequestBuilder.increaseProjectSpendLimit(
			userName, so.getBuyerId(), buyerState,
			so.getSoId(), so.getFundingTypeId().longValue(), "", amount, 0d);
		WalletResponseVO walletResponseVO;
		try{
			walletResponseVO = walletBO.increaseProjectSpendLimit(request);			
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
		return walletResponseVO;
	}

	public WalletResponseVO increaseSpendOnCompletion(String userName, ServiceOrder so, String buyerState, double amount){
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		WalletVO request = walletRequestBuilder.increaseProjectSpendLimit(
			userName, so.getBuyerId(), buyerState,
			so.getSoId(), so.getFundingTypeId().longValue(), "", 0d, amount);
		WalletResponseVO walletResponseVO;
		try{
			walletResponseVO = walletBO.increaseProjectSpendCompletion(request);			
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
		return walletResponseVO;
	}
	
	//for HSR
	public WalletResponseVO increaseSpendOnCompletionForHSR(String userName, ServiceOrder so, String buyerState, double amount,double retailPrice, double reimbursementRetailPrice, double partsSLGrossup, 
			double retailPriceSLGrossup,boolean hasAddon,boolean hasParts,double addOnAmount,double partsAmount){
		
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		WalletVO request = walletRequestBuilder.increaseProjectSpendLimitForHSR(
			userName, so.getBuyerId(), buyerState,
			so.getSoId(), so.getFundingTypeId().longValue(), "", 0d, amount, retailPrice, reimbursementRetailPrice, partsSLGrossup, retailPriceSLGrossup,hasAddon,hasParts,addOnAmount,partsAmount);
		WalletResponseVO walletResponseVO;
		try{
			walletResponseVO = walletBO.increaseProjectSpendCompletion(request);			
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
		return walletResponseVO;
	}
	
	private void handleError(WalletResponseVO walletResponseVO) throws ServiceOrderException {
		List<String> errorMsgs = walletResponseVO.getErrorMessages();
		if (errorMsgs != null && !errorMsgs.isEmpty()) {
			throw new ServiceOrderException(errorMsgs);
		}
	}
	
	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId,
			LedgerEntryType entryType, String serviceOrderId) {
		try{
			return walletBO.getTransactionReceipt(entityId, entityTypeId, entryType, serviceOrderId);
		} catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
	}

	public double getRoundedMoney(double doubleNumber) {
        return BigDecimal
                .valueOf(doubleNumber)
                .setScale(DECIMALS, ROUNDING_MODE)
                .doubleValue();
	}

	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public WalletResponseVO getWalletMessageResult(String messageId) {
		try {
			return walletBO.getWalletMessageResult(messageId);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		
	}

    public boolean areAllValueLinkTransactionsReconciled(String serviceOrderId) {
        try {
            return walletBO.checkValueLinkReconciledIndicator(serviceOrderId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }
    
    public boolean isACHTransPending(String serviceOrderId) {
        try {
            return walletBO.isACHTransPending(serviceOrderId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

    public boolean hasPreviousAddOn(String serviceOrderId){
    	try {
            return walletBO.hasPreviousAddOn(serviceOrderId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    
    	
     }
    
    

    public Boolean isBuyerAutoFunded(Long buyerId) {
        try {
            return walletBO.isBuyerAutoFunded(buyerId);
        } catch (SLBusinessServiceException e) {
            throw new ServiceOrderException(e);
        }
    }

	public WalletResponseVO addFundsToBuyerAccount(String userName, Long buyerId, Long accountId, String buyerState, ServiceOrder so, BigDecimal amount) {
		IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
		WalletVO request = walletRequestBuilder.depositBuyerFundsWithCreditCard(userName, accountId, buyerId, 
				buyerState, null, null, so.getSoId(), "", amount.doubleValue());

		SLCreditCardVO ccVO = new SLCreditCardVO();
		ccVO.setCardId(accountId);
		ccVO.setTransactionAmount(amount.doubleValue());
		request.setCreditCard(ccVO);
		WalletResponseVO walletResponseVO;
		try {
			walletResponseVO = walletBO.depositBuyerFundsWithCreditCard(request);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
		return walletResponseVO;
	}
	
	public WalletResponseVO getCreditCardInformation(Long accountId) {
		WalletResponseVO walletResponseVO;
		try {
			walletResponseVO = walletBO.getCreditCardInformation(accountId);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
		handleError(walletResponseVO); 
		return walletResponseVO;
	}
	public  long getAccountId(long buyerId){
		try {
			return walletBO.getAccountId(buyerId);
		}catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
	}
	public double getTransactionAmountById(Long transactionId) {
		try {
			return walletBO.getTransactionAmount(transactionId);
		} catch (SLBusinessServiceException e) {
			throw new ServiceOrderException(e);
		}
	}
	 public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
	        this.quickLookupCollection = quickLookupCollection;
	}
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}	 

}
