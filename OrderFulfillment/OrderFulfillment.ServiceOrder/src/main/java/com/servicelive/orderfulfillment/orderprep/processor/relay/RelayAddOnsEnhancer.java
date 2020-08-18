package com.servicelive.orderfulfillment.orderprep.processor.relay;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuCategory;
import com.servicelive.domain.so.BuyerSkuAddon;

import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderEnhancer;

public class RelayAddOnsEnhancer extends AbstractOrderEnhancer {

	protected final Logger logger = Logger.getLogger(getClass());

	IServiceOrderDao serviceOrderDao;
	IOrderBuyerDao orderBuyerDao;

	public void enhanceOrder(ServiceOrder serviceOrder,
			OrderEnhancementContext orderEnhancementContext) {

		boolean taskPresent = false;
		taskPresent = serviceOrder.isTasksPresent();

		if (!taskPresent) {
			List<SOAddon> newAddOnList = createAddOnsFromInput(serviceOrder,
					orderEnhancementContext.getOrderBuyer());
			serviceOrder.getAddons().clear();
			serviceOrder.setAddons(newAddOnList);
		}
	}

	private List<SOAddon> createAddOnsFromInput(ServiceOrder serviceOrder,
			IOrderBuyer orderBuyer) {
		List<SOAddon> newAddOnList = new ArrayList<SOAddon>();

		BuyerOrderSkuCategory buyerOrderSkuCategory = getBuyerSkusForPrimaryCategory(
				serviceOrder, orderBuyer);
		BuyerOrderSku mainSku = orderBuyer.getBuyerSkuMap().getBuyerSku(
				serviceOrder.getPrimaryTask().getExternalSku());

		if (buyerOrderSkuCategory != null) {
			logger.info("CategoryId is " +buyerOrderSkuCategory.getCategoryId());
			List<BuyerSkuAddon> buyerSkuAddonList= getBuyerSkuList(buyerOrderSkuCategory.getCategoryId(),serviceOrder.getPrimaryTask().getExternalSku(),buyerOrderSkuCategory.getBuyerId());
					for (BuyerSkuAddon buyerOrderSku :buyerSkuAddonList){
					SOAddon addOn = createAddonFromBuyerOrderSku(buyerOrderSku);
					newAddOnList.add(addOn);
				}
			
		}

		if (mainSku != null) {
			
				List<BuyerSkuAddon> buyerSkuAddonCommonList= getCommonSkuList(buyerOrderSkuCategory.getBuyerId());
				for (BuyerSkuAddon buyerOrderSku :buyerSkuAddonCommonList){
					SOAddon addOn = createAddonFromBuyerOrderSku(buyerOrderSku);
					newAddOnList.add(addOn);
				
			}
		}

		return newAddOnList;
	}

	private BuyerOrderSkuCategory getBuyerSkusForPrimaryCategory(
			ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
		BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
		BuyerOrderSkuCategory primarySkuCategory = getPrimaryBuyerSkuCategory(
				serviceOrder, buyerSkuMap);
		if (primarySkuCategory != null) {
			
			return primarySkuCategory;

		}
		logger.warn("AddOnsEnhancer.getBuyerSkusForPrimaryCategory() returned null for sku "
				+ serviceOrder.getPrimaryTask().getExternalSku());
		return null;
	}

	private BuyerOrderSkuCategory getPrimaryBuyerSkuCategory(
			ServiceOrder serviceOrder, BuyerSkuMap buyerSkuMap) {
		BuyerOrderSku buyerOrderSku = buyerSkuMap.getBuyerSku(serviceOrder
				.getPrimaryTask().getExternalSku());
		return buyerOrderSku != null ? buyerOrderSku.getBuyerSkuCategory()
				: null;
	}

	private SOAddon createAddonFromBuyerOrderSku(BuyerSkuAddon buyerOrderSku) {
		SOAddon addOn = new SOAddon();
		addOn.setQuantity(0);
		//addOn.setDescription(buyerOrderSku.getSkuDescription());
		//addOn.setMargin(buyerOrderSku.getBidMargin().doubleValue());
		//addOn.setMiscInd(buyerOrderSku.getPriceType() == PriceType.VARIABLE);
		//addOn.setRetailPrice(buyerOrderSku.getRetailPrice());
		addOn.setSku(buyerOrderSku.getSku());

		addOn.setServiceFee(BigDecimal.ZERO);
		return addOn;
	}


	private List<BuyerSkuAddon> getCommonSkuList( Long buyerId) {

		List<BuyerSkuAddon> buyerSkuAddon = null;
		try {
			buyerSkuAddon = serviceOrderDao
					.getBuyerSkuAddOnList("ADDONCOM", buyerId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buyerSkuAddon;

	}

	private List<BuyerSkuAddon> getBuyerSkuList(Long  catId, String primarySku, Long buyerId) {

		List<BuyerSkuAddon> buyerSkuAddon = null;
		logger.info(" Relay Service getBuyerSkuList");
		try {
			buyerSkuAddon = serviceOrderDao
					.getBuyerSkuAddOnList(catId, primarySku, buyerId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return buyerSkuAddon;
	}
	


	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
		this.orderBuyerDao = orderBuyerDao;
	}
}
