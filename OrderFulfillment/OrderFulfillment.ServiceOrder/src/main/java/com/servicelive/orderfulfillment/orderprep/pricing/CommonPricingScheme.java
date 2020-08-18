package com.servicelive.orderfulfillment.orderprep.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.domain.so.BuyerOrderMarketAdjustment;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;

//includes the method to calculate market adjustment rate
public class CommonPricingScheme implements IBuyerPricingScheme{
	Long buyerId;
	BuyerSkuMap buyerSkuMap;
    IOrderBuyerDao orderBuyerDao;   
    private Logger logger = Logger.getLogger(getClass());
        
    public static final String SYSTEM = "System" ;
    final String subject = "Service Order Price Update";
    
    public CommonPricingScheme(){
    	//do nothing
    }

    public CommonPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
        this.buyerId = buyerId;
        this.buyerSkuMap = buyerSkuMap;
    }
    
	public IOrderBuyerDao getOrderBuyerDao() {
		return orderBuyerDao;
	}

	public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
		this.orderBuyerDao = orderBuyerDao;
	}
	
	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public BuyerSkuMap getBuyerSkuMap() {
		return buyerSkuMap;
	}

	public void setBuyerSkuMap(BuyerSkuMap buyerSkuMap) {
		this.buyerSkuMap = buyerSkuMap;
	}
	
	public void calculatePriceWithMAR(ServiceOrder serviceOrder) {
		logger.info("inside CommonPricingScheme to calculate price with Market adjustment for orders created via API and dont have an enhancer");
		//get the zip code
		String serviceLocationZip = null;
		if(null != serviceOrder.getServiceLocation()){
			serviceLocationZip = serviceOrder.getServiceLocation().getZip();
		}			
		
    	//get market adjustment rate
		if(null == buyerId){
			buyerId = serviceOrder.getBuyerId();
		}
		BigDecimal marketAdjRate = new BigDecimal("1.0000");
		if(null != serviceLocationZip){
			marketAdjRate = this.getMarketAdjustmentRate(buyerId, serviceLocationZip);
			if (null != buyerId && null != marketAdjRate){
			logger.info("MAR for buyer " + buyerId.toString()+ " is "+ marketAdjRate.toString());
			}
		}		
		
		// Check if tasks are present 
		boolean taskPresent = false;
    	taskPresent = serviceOrder.isTasksPresent();
		
		//if sku-based
		if(!taskPresent){
			logger.info("inside !taskpresent condition");
			BigDecimal totalRetailPrice = PricingUtil.ZERO;    			
			StringBuilder note = new StringBuilder();
			for (SOTask task : serviceOrder.getTasks()) {					
				//method to apply MAR to price
	            BigDecimal taskPrice = this.getRetailPriceWithMAR(task.getExternalSku(), marketAdjRate, note, task.getTaskName());
				if (taskPrice!=null) {
					logger.info("taskprice is "+taskPrice);
					task.setPrice(taskPrice);
					task.setFinalPrice(taskPrice);
					totalRetailPrice = totalRetailPrice.add(taskPrice);
				}
	        }
			//insert notes
			if(0 != note.length()){
				this.createNoteForSkuWithMAR(marketAdjRate, serviceOrder,note);
			}				
			PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
		}
		//if task-based
		else{
			logger.info("inside taskpresent condition");
			// Set the price coming from the request.
			BigDecimal oldPrice = PricingUtil.ZERO;
			BigDecimal labourSpendLimit = serviceOrder.getSpendLimitLabor();
			BigDecimal partsSpenLimit = serviceOrder.getSpendLimitParts();
			if(null != labourSpendLimit){
				oldPrice = labourSpendLimit;
				//new SOprice = old SOprice * MAR
				labourSpendLimit = labourSpendLimit.multiply(marketAdjRate);
			}	
			else{
				labourSpendLimit = PricingUtil.ZERO;
			}
			//insert notes
			this.createNoteForTaskWithMAR(oldPrice, labourSpendLimit, 
					marketAdjRate, serviceOrder);
			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpenLimit);
		}
	}
	
	public BigDecimal getRetailPriceWithMAR(String externalSku, BigDecimal marketAdjRate, StringBuilder note, String taskName) {
	        BuyerSkuPricingItem buyerSkuPricingItem = new BuyerSkuPricingItem(externalSku);
	        this.priceSkuItemWithMAR(buyerSkuPricingItem, marketAdjRate, note, taskName);
	        return buyerSkuPricingItem.getRetailPrice();
		}

	//this method will be overridden
	public void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem) {
		// TODO Auto-generated method stub
		
	}	

	public void priceSkuItemWithMAR(BuyerSkuPricingItem buyerSkuPricingItem,
			BigDecimal marketAdjRate, StringBuilder note, String taskName ) {
		BuyerOrderSku buyerSku = null;
		BigDecimal newPrice = PricingUtil.ZERO;
		if(null != buyerSkuMap){
			buyerSku = buyerSkuMap.getBuyerSku(buyerSkuPricingItem.getSku());
			if(null != buyerSku){				
				BigDecimal oldPrice = buyerSku.getBidPrice();	
				if(null != oldPrice){
					newPrice = oldPrice.multiply(marketAdjRate);
				}
				else{
					oldPrice = PricingUtil.ZERO;
				}	
				note.append(java.text.NumberFormat.getCurrencyInstance().format(oldPrice.setScale(2, RoundingMode.HALF_DOWN))+" to "+
						java.text.NumberFormat.getCurrencyInstance().format(newPrice.setScale(2, RoundingMode.HALF_DOWN))+" - "+buyerSku.getSku()+" : "+taskName+"<br>");
				buyerSkuPricingItem.setRetailPrice(newPrice.setScale(2, RoundingMode.HALF_DOWN));		
			}
		}		
	}

	//fetch market adjustment rate for a buyer
	public BigDecimal getMarketAdjustmentRate(Long buyerId,	String serviceLocationZip) {
		BigDecimal adjustment = new BigDecimal("1.0000");
		BuyerOrderMarketAdjustment marketAdjustment = orderBuyerDao.getBuyerOrderMarketAdjustmentWithZip(buyerId, serviceLocationZip);
	    if (null == marketAdjustment){
	    	return adjustment;
	    }
	    return marketAdjustment.getAdjustment();
	}

	//check whether MAR feature is on
	public boolean isMARfeatureOn(Long buyerId) {
		return orderBuyerDao.isMARfeatureOn(buyerId);
	}
	
	//insert note for task based order
	public void createNoteForTaskWithMAR(BigDecimal oldPrice,
			BigDecimal labourSpendLimit, BigDecimal marketAdjRate,
			ServiceOrder serviceOrder) {
		StringBuilder note = new StringBuilder();
		note.append("Price changed from "+java.text.NumberFormat.getCurrencyInstance().format(oldPrice.setScale(2, RoundingMode.HALF_DOWN))+" to "+
				java.text.NumberFormat.getCurrencyInstance().format(labourSpendLimit.setScale(2, RoundingMode.HALF_DOWN))+" with Market Rate Adjustment as "+marketAdjRate+".");
		this.insertNotes(serviceOrder, note);		
	}	

	//insert note for sku based order
	public void createNoteForSkuWithMAR(BigDecimal marketAdjRate,
			ServiceOrder serviceOrder, StringBuilder note) {
		StringBuilder newNote = new StringBuilder();
		if(0 != note.length()){
			newNote.append("Price changed with Market Rate Adjustment of "+marketAdjRate+"<br>"+note.toString());
			this.insertNotes(serviceOrder, newNote);
		}	
	}

	//insert notes for MAR applied order
	private void insertNotes(ServiceOrder serviceOrder,StringBuilder note) {
		SONote soNote = new SONote();
		soNote.setServiceOrder(serviceOrder);
    	soNote.setCreatedDate(new Date());
    	soNote.setSubject(subject);
    	soNote.setRoleId(3); 	
    	soNote.setNote(note.toString());
    	soNote.setCreatedByName(SYSTEM);
    	soNote.setModifiedDate(new Date());
    	soNote.setModifiedBy(SYSTEM);
    	soNote.setNoteTypeId(2);
    	soNote.setEntityId(serviceOrder.getBuyerId());
    	soNote.setPrivate(true);
    	soNote.setMARnote(true);
    	List<SONote> notes = serviceOrder.getNotes();
    	notes.add(soNote);
    	serviceOrder.setNotes(notes);
	}

	public void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem, 
			StringBuilder note, String taskName) {
		// TODO Auto-generated method stub
		
	}
	 /**
     * SL-18007 method to save so price change history during SO creation
     * @param: ServiceOrder serviceOrder
     */
	 public void setSOPriceChangeHistory(ServiceOrder serviceOrder){
	    	
	    	List<SOPriceChangeHistory> soPriceChangeHistoryList = new ArrayList<SOPriceChangeHistory>();
	    	SOPriceChangeHistory soPriceChangeHistory  = new SOPriceChangeHistory();
	    	BigDecimal soLabourPrice = PricingUtil.ZERO;
	    	BigDecimal soPartsPrice = PricingUtil.ZERO;
	    	BigDecimal permitTaskPrice = PricingUtil.ZERO;
	    	BigDecimal addOnPrice = PricingUtil.ZERO;
	    	BigDecimal partsInvoicePrice = PricingUtil.ZERO;
	    	Date createdDate = new Date();
	    	
	    	if (null != serviceOrder){
	    		 if(null != serviceOrder.getSpendLimitLabor() && StringUtils.isNotBlank(serviceOrder.getSpendLimitLabor().toString())){
	    		        soLabourPrice = serviceOrder.getSpendLimitLabor();
	    		}
	    		if(null != serviceOrder.getSpendLimitParts() && StringUtils.isNotBlank(serviceOrder.getSpendLimitParts().toString())){ 
	    		        soPartsPrice = serviceOrder.getSpendLimitParts();
	    		}
	    		        soPriceChangeHistory.setSoMaterialsPrice(soPartsPrice);
	    		
	    		// set the price of Permit tasks from so_tasks    		
	    		List<SOTask> soTasks = serviceOrder.getTasks();
	    		if (null != soTasks){
	    			for(SOTask task: soTasks){
	    			   if(null != task){
	    				if (1==task.getTaskType()){
	    					if(null != task.getPrice() && StringUtils.isNotBlank(task.getPrice().toString())){
	    					      permitTaskPrice = permitTaskPrice.add(task.getPrice());
	    				  }
	    			    }
	    			  }
	    			}
	    		}
	    		
	    		List<SOAddon> soAddons = serviceOrder.getAddons();
	    		if (null != soAddons){
	    			for (SOAddon soAddon: soAddons){
	    				if(null != soAddon && null != soAddon.getSku()){
	    				if(("99888").equals(soAddon.getSku()))
	    				{	
	    					logger.info("<<<<<addon*Quantity---"+soAddon.getAddonPrice());
	    					if(null != soAddon.getAddonPrice()){
	    					   permitTaskPrice = permitTaskPrice.add(soAddon.getAddonPrice());
	    					}
	    				}
	    			  }	
	    			}
	    		}    		
	    		logger.info("<<<<<<permitTaskPrice---"+permitTaskPrice);
	    		
	    		soPriceChangeHistory.setSoPermitPrice(permitTaskPrice);
	    		soPriceChangeHistory.setSoLabourPrice(soLabourPrice.subtract(permitTaskPrice));
	    		// set the price of addons from so_addons 
	    		if(null != serviceOrder.getPrice() && null != serviceOrder.getPrice().getTotalAddonPriceGL()){
	    		   addOnPrice = serviceOrder.getPrice().getTotalAddonPriceGL();
	    		}
	    		logger.info("<<<<<addOnPrice---"+addOnPrice);
	    		

//	    		List<SOAddon> soAddons = serviceOrder.getAddons();
//	    		if (null != soAddons){
//	    			for (SOAddon soAddon: soAddons){
//	    				addOnPrice = addOnPrice.add(soAddon.getRetailPrice().multiply(new BigDecimal(soAddon.getQuantity())));
//	    			}
//	    		}
	    		if(addOnPrice == null)
	    		{
	        		soPriceChangeHistory.setSoAddonPrice(new BigDecimal(0.00));

	    		}	
	    		else
	    		{	logger.info(">>>>>>addOnPrice--"+addOnPrice);
	    		    soPriceChangeHistory.setSoAddonPrice(addOnPrice);
	    		}
	    		//setting parts invoice price to zero as this is applicable only for buyer 3000 and only during completion of an SO
	    		soPriceChangeHistory.setSoPartsInvoicePrice(partsInvoicePrice);
	    		// Total SO Price = labour + parts price
	    		
	    		soPriceChangeHistory.setSoTotalPrice(soLabourPrice == null?PricingUtil.ZERO : soLabourPrice
	    				.add(soPartsPrice == null? PricingUtil.ZERO : soPartsPrice )
	    				.add(addOnPrice == null? PricingUtil.ZERO : addOnPrice)
	    				.add(partsInvoicePrice == null ? PricingUtil.ZERO : partsInvoicePrice));
	    		soPriceChangeHistory.setAction(OFConstants.ORDER_CREATION);
	    		soPriceChangeHistory.setReasonComment(null);
	    		soPriceChangeHistory.setCreatedDate(createdDate);
	    		soPriceChangeHistory.setModifiedDate(createdDate);
	    		soPriceChangeHistory.setModifiedBy(PricingUtil.SYSTEM);
	    		soPriceChangeHistory.setModifiedByName(PricingUtil.SYSTEM);
	    		
	    		soPriceChangeHistoryList.add(soPriceChangeHistory);
	    		
	    		serviceOrder.setSoPriceChangeHistory(soPriceChangeHistoryList);
	    	}
	    	
	    }

	 
		public BigDecimal getServiceOfferingPrice(Long buyerId,String sku,String zip,String firmId) {
			return orderBuyerDao.getServiceOfferingPrice(buyerId,sku,zip,firmId);
		}
}
