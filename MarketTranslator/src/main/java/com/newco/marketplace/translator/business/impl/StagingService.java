package com.newco.marketplace.translator.business.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.oms.OMSStagingBridge;
import com.newco.marketplace.translator.business.BuyerUpsellService;
import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.dao.IBuyerRetailPriceDAO;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.webservices.dao.AbstractShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.SpecialtyAddOn;
import com.newco.marketplace.webservices.dto.StagingDetails;

public class StagingService implements IStagingService {

	private Logger logger = Logger.getLogger(StagingService.class);
	private IBuyerRetailPriceDAO buyerRetailPriceDAO;
	//private StageOrderSEILocator stagingServiceLocator;
	private OMSStagingBridge omsStagingBridge;
	private BuyerUpsellService buyerUpsellService;
	private BuyerSkuService buyerSKUService;
	private IApplicationPropertiesService applicationPropertiesService;

	//Constructors

	
	public StagingService() {
		// intentionally blank
	}


	public StagingService(OMSStagingBridge omsStagingBridge) {
		//String stagingServiceEndPointUrl  = appPropService.getStagingWebServiceEndPoint();
		//Please do not remove following lines because they are required for debugging QA/Stress environment
		//stagingServiceEndPointUrl = "http://localhost/omsstaging/services/StageOrderSEI";
		//stagingServiceEndPointUrl = "http://localhost:8080/omsstaging/services/StageOrderSEI";
		//stagingServiceLocator.setStageOrderSEIHttpPortEndpointAddress(stagingServiceEndPointUrl);
		this.omsStagingBridge = omsStagingBridge;
	}

	/*private void setStagingWebServiceURL(StageOrderSEILocator stagingServiceLocator) {
		String stagingServiceEndPointUrl = Constants.STAGING_WS_URL;
		logger.info("[[[[[[===== Staging URL in StagingService = {" + stagingServiceEndPointUrl + "} =====]]]]]]");
		stagingServiceLocator.setStageOrderSEIHttpPortEndpointAddress(stagingServiceEndPointUrl);
	}*/

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IStagingService#stageDataAfterUnMarshalling(com.newco.marketplace.webservices.dto.StagingDetails)
	 */
	public void stageDataAfterUnMarshalling(StagingDetails stagingDetails, int buyerID) throws Exception {
		try {
			processAddOns( stagingDetails, buyerID);
			omsStagingBridge.stageDataAfterUnMarshalling(stagingDetails);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IStagingService#stageDataAfterTranslation(com.newco.marketplace.webservices.dto.StagingDetails, java.util.List, int)
	 */
	public void stageDataAfterTranslation(ShcOrder shcOrder, List<SkuPrice> skus,int buyerId) {
		
		try {
			shcOrder = this.mapPriceDetails(shcOrder, skus,buyerId);
			populateCallCollectAddOns(shcOrder, skus, buyerId);			
			omsStagingBridge.stageShcOrderAfterTranslation(shcOrder);
			
		} catch (Exception e) {
				logger.error(e.getMessage(), e);
		}
  	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IStagingService#stageDataAfterProcessing(com.newco.marketplace.webservices.dao.ShcOrder, java.util.List)
	 */
	public void stageDataAfterProcessing(ShcOrder shcOrder,List<ShcErrorLogging> shcErrorLoggingList) throws Exception {
		try {
			omsStagingBridge.stageDataAfterProcessing(shcOrder, shcErrorLoggingList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Method which sets the value of retailPrice and priceRatio in the
	 * StagingServiceOrder object
	 * @param StageServiceOrder
	 * @param List<SkuPrice>
	 * @return StageServiceOrder
	 */
	private ShcOrder mapPriceDetails(ShcOrder serviceOrder, List<SkuPrice> skus, int buyerId) {

		String storeCode = serviceOrder.getStoreNo();
		
		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		List<ShcOrderSku> orderSkus = new ArrayList<ShcOrderSku>(serviceOrder.getShcOrderSkus());// Arrays.asList(serviceOrder.getShcOrderSkus());
		double totalPriceAllSkus = 0.0;

		for (ShcOrderSku orderSku : orderSkus) {
					Double retailPrice = buyerSKUService.calculateRetailPrice(orderSku.getSku(), storeCode, Integer.valueOf(buyerId));
					orderSku.setRetailPrice(retailPrice);
					if (orderSku.getPermitSkuInd().shortValue() == Constants.PERMIT_SKU_IND_ZERO) {
							
						if (retailPrice.doubleValue() == 0.0) {
							if(!(null == orderSku.getSellingPrice())){
								totalPriceAllSkus = totalPriceAllSkus + orderSku.getSellingPrice().doubleValue();
							}else{
								orderSku.setPriceRatio(Double.valueOf(0.0));
							}
						} else {
							totalPriceAllSkus = totalPriceAllSkus + retailPrice.doubleValue();
						}
					} else {
						orderSku.setPriceRatio(Double.valueOf(0.0));
					}
		}		
		this.calculatePriceRatio(totalPriceAllSkus, orderSkus);
		
		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		serviceOrder.setShcOrderSkus(new HashSet<ShcOrderSku>(orderSkus));
		return serviceOrder;
	}

	/**This method computes the shc_order_addon for the CC sku - 
	 * Before, the add-on was part of the shcOrderSku, now that is removed
	 * @param serviceOrder
	 * @param skus
	 * @param buyerId
	 */
	private void populateCallCollectAddOns(ShcOrder serviceOrder,
			List<SkuPrice> skus, int buyerId) {
		String storeCode= serviceOrder.getStoreNo();
		Map<String,Integer> jobCodeMap= new HashMap<String,Integer>();
		Integer jobcodeCount = Integer.valueOf(0);
		for (SkuPrice skuPrice : skus) {
			if (Constants.CALL_COLLECT_COVERAGE_TYPE.equalsIgnoreCase(skuPrice.getCoverage())) {
				if(jobCodeMap.containsKey(skuPrice.getSku())){
					jobcodeCount = jobCodeMap.get(skuPrice.getSku());
					jobcodeCount = Integer.valueOf(jobcodeCount.intValue() + 1);
					jobCodeMap.remove(skuPrice.getSku());
					jobCodeMap.put(skuPrice.getSku(), jobcodeCount);				
				}else{
					jobCodeMap.put(skuPrice.getSku(), Integer.valueOf(Constants.DEFAULT_SKU_QTY));			
					Double retailPrice = buyerSKUService.calculateRetailPrice(skuPrice.getSku(), storeCode, Integer.valueOf(buyerId));
					addCallCollectAddon(serviceOrder, skuPrice,retailPrice);
				}
			}
		}
		//Increment the quantity from default if required
		Iterator<ShcOrderAddOn> addons = serviceOrder.getShcOrderAddOns().iterator();
		while(addons.hasNext()){
			ShcOrderAddOn shcOrderAddOn=addons.next();
			String sku=shcOrderAddOn.getSpecialtyAddOn().getStockNumber();				
			if(jobCodeMap.containsKey(sku) && jobCodeMap.get(sku).intValue() > 1){
				shcOrderAddOn.setQty(jobCodeMap.get(sku));
			}
		}
	}

	
	private void addCallCollectAddon(ShcOrder serviceOrder, SkuPrice skuPrice, Double retailPrice) {
		
		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		
		ShcMerchandise[] s = serviceOrder.getShcMerchandises().toArray(new ShcMerchandise[0]);
		
		String specialtyCode = s[0].getSpecialty();
		ShcOrderAddOn shcOrderAddon = new ShcOrderAddOn();
		SpecialtyAddOn specialityAddOn = null;
		// get the specialty add on by using the specialty code and stock number
		try {
			specialityAddOn = omsStagingBridge.getCallCollectAddon(specialtyCode, skuPrice.getSku());
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		shcOrderAddon.setSpecialtyAddOn(specialityAddOn);
		shcOrderAddon.setMargin( skuPrice.getMargin());
		shcOrderAddon.setQty(Integer.valueOf(Constants.DEFAULT_SKU_QTY)); 
		shcOrderAddon.setMiscInd(	getMiscSKUInd(specialityAddOn, 
									getDivisionCode(serviceOrder)) );
		shcOrderAddon.setRetailPrice(retailPrice);
		shcOrderAddon.setCoverage(Constants.CALL_COLLECT_COVERAGE_TYPE);

		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		ShcOrderAddOn[] addons = serviceOrder.getShcOrderAddOns().toArray(new ShcOrderAddOn[0]);
		ShcOrderAddOn[] shcaddons = null;
		if(addons != null && addons.length > 0)  {
			shcaddons = new ShcOrderAddOn[addons.length + 1];
		} else { 
			shcaddons = new ShcOrderAddOn[1];
		}
		
		for (int i = 0; i < addons.length; i++) {
			shcaddons[i] = addons[i];
		}
		shcaddons[(shcaddons.length - 1)] = shcOrderAddon;
		
		// Convert ShcOrderAddOn Array to ShcOrderAddOn Set
		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		List<ShcOrderAddOn> list = Arrays.asList(shcaddons);
	    Set<ShcOrderAddOn> shcaddonsSet = new HashSet<ShcOrderAddOn>(list);
		
		serviceOrder.setShcOrderAddOns(shcaddonsSet);
	}
	/**
	 * Method to get the price ratio of the given sku
	 * @param double totalPrice
	 * @param List<ShcOrderSku> orderSkus
	 * @return void
	 */
	private void calculatePriceRatio(double totalPrice, List<ShcOrderSku> orderSkus) {

		try {
			double reconcileValue = 0.0;
			double totalRatioValue = 0.0;
			for (ShcOrderSku sku : orderSkus) {
				if (totalPrice > 0.0 && sku.getPermitSkuInd().shortValue() == Constants.PERMIT_SKU_IND_ZERO) {
					Double priceRatio = Double.valueOf(Constants.DEFAULT_PRICE_RATIO_VALUE);
					if (sku.getRetailPrice().doubleValue() == 0.0) {
						if(!(null == sku.getSellingPrice())){
							priceRatio = Double.valueOf(sku.getSellingPrice().doubleValue() / totalPrice);
							}
					} else {
						priceRatio = Double.valueOf(sku.getRetailPrice().doubleValue() / totalPrice);
					}
					sku.setPriceRatio(Double.valueOf(new BigDecimal(priceRatio.doubleValue()).setScale(4, RoundingMode.HALF_UP).doubleValue()));
					totalRatioValue = totalRatioValue + sku.getPriceRatio().doubleValue();
				}
			}
		
			reconcileValue = new BigDecimal(1-totalRatioValue).setScale(4, RoundingMode.HALF_UP).doubleValue();
			if (reconcileValue != 0 ){
				for (ShcOrderSku sku : orderSkus) {
					if(sku.getPermitSkuInd().shortValue() == Constants.PERMIT_SKU_IND_ZERO){
						sku.setPriceRatio(Double.valueOf(new BigDecimal(sku.getPriceRatio().doubleValue() + reconcileValue).setScale(4, RoundingMode.HALF_UP).doubleValue()));
						break;
					}					
				}
			}

			
			
		} catch (Exception ex) {
			logger.error("Exception in calulating price ratio",ex);
		}
	}



	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IStagingService#persistErrors(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void persistErrors(String errorCode, String errorMessage,String orderNumber,String unitNumber) {
		try {
			omsStagingBridge.persistErrors(errorCode, errorMessage, orderNumber, unitNumber);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IStagingService#retrieveClosedOrders()
	 */
	public ShcOrder[] retrieveClosedOrders() {
		
		ShcOrder[] closedOrders = null;
		
		
		try {
			int npsNumOrdersToClose = applicationPropertiesService.getNPSOrdersToProcess(Constants.ApplicationPropertiesConstants.NPS_NUM_ORDERS_TO_CLOSE);
			// Convert ShcOrder list  to ShcOrder Array
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			closedOrders = omsStagingBridge.retrieveClosedOrders(npsNumOrdersToClose).toArray(new ShcOrder[0]);
		} catch (Exception e) {
			logger.error("Error in Staging Service while calling omsStagingBridge.retrieveClosedOrders " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return closedOrders;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IStagingService#createNPSProcessLog(com.newco.marketplace.translator.dto.StagingDetails, java.lang.String)
	 */
	public int createNPSProcessLog(StagingDetails stagingDetails, String npsCallCloseFileName) {
		int npsProcessId = -1;
		try {
			npsProcessId=omsStagingBridge.createNPSProcessLog(stagingDetails, npsCallCloseFileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return npsProcessId;

	}
	/**
	 *
	 * method to return the shc order information
	 * @param orderNo
	 * @param unitNo
	 * @return
	 */

	public ShcOrder getShcOrder(String orderNo, String unitNo){
		ShcOrder shcOrder = null;
		try {
			shcOrder=omsStagingBridge.getStageOrder(orderNo, unitNo);
		} catch	(Exception e){
			logger.error(e.getMessage(), e);
		}
		return shcOrder;
	}
	
	/**
	 * method to return list of staging orders
	 * @param unitAndOrderNo
	 * @return
	 */
	public ShcOrder[] findShcOrders(List<String> unitAndOrderNo) {
		return omsStagingBridge.findStagingOrders(unitAndOrderNo).toArray(new ShcOrder[0]);
	}

	public ShcOrder[] findLatestShcOrdersWithOrderNumberMatchingBeforeTestSuffix(List<String> unitAndOrderNumbers, String testSuffixNew) {
		return omsStagingBridge.findLatestStagingOrdersWithOrderNumberMatchingBeforeTestSuffix(unitAndOrderNumbers, testSuffixNew).toArray(new ShcOrder[0]);
	}
	
	private void processAddOns( StagingDetails stagingDetails, int buyerID )
	{
		for( ShcOrder shcOrder : stagingDetails.getStageServiceOrder() )
		{
			buyerUpsellService.processAddOnMerchandise( shcOrder, buyerID );
		}
		return;
	}
	
	private Integer getMiscSKUInd(final SpecialtyAddOn specialtyAddOn,
			final String divisionCode) {
		String s = specialtyAddOn.getStockNumber();
		try {
			if (s.substring(s.length() - 3).equals(divisionCode))
				return Constants.MISCELLANEOUS_SKU_INDICATOR;
		} catch (NullPointerException e) {
			//FIXME shouldn't this be handled?
		}
		return Constants.NOT_MISCELLANEOUS_SKU_INDICATOR;
	}
	
	private String getDivisionCode( final ShcOrder shcOrder )
	{
		try
		{

			// casting it to  AbstractShcMerchandise object before calling getDivisionCode().
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			
			return ((AbstractShcMerchandise) shcOrder.getShcMerchandises().toArray()[0]).getDivisionCode();
		}
		catch( NullPointerException e )
		{
			return "000";
		}
	}
	
	/**
	 * @return
	 */
	public IBuyerRetailPriceDAO getBuyerRetailPriceDAO() {
		return buyerRetailPriceDAO;
	}

	/**
	 * @param buyerRetailPriceDAO
	 */
	public void setBuyerRetailPriceDAO(IBuyerRetailPriceDAO buyerRetailPriceDAO) {
		this.buyerRetailPriceDAO = buyerRetailPriceDAO;
	}


	/**
	 * @return the buyerUpsellService
	 */
	public BuyerUpsellService getBuyerUpsellService() {
		return buyerUpsellService;
	}

	/**
	 * @param buyerUpsellService the buyerUpsellService to set
	 */
	public void setBuyerUpsellService(BuyerUpsellService buyerUpsellService) {
		this.buyerUpsellService = buyerUpsellService;
	}

	/**
	 * @return the buyerSKUService
	 */
	public BuyerSkuService getBuyerSKUService() {
		return buyerSKUService;
	}

	/**
	 * @param buyerSKUService the buyerSKUService to set
	 */
	public void setBuyerSKUService(BuyerSkuService buyerSKUService) {
		this.buyerSKUService = buyerSKUService;
	}


	/**
	 * @return the applicationPropertiesService
	 */
	public IApplicationPropertiesService getApplicationPropertiesService() {
		return applicationPropertiesService;
	}

	/**
	 * @param applicationPropertiesService the applicationPropertiesService to set
	 */
	public void setApplicationPropertiesService(
			IApplicationPropertiesService applicationPropertiesService) {
		this.applicationPropertiesService = applicationPropertiesService;
	}
}
