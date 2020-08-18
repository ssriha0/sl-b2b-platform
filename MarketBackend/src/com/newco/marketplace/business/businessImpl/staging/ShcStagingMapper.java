package com.newco.marketplace.business.businessImpl.staging;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStagingVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.webservices.dao.ShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.ShcUpsellPayment;
import com.newco.marketplace.webservices.dto.StagingDetails;

public  class ShcStagingMapper {
	
	public static StagingDetails mapStagingVOToStagingDetails(ServiceOrderStagingVO soStagingVO){
		StagingDetails stagingDetails = null;
		
		
		stagingDetails = new StagingDetails();
		ShcOrder shcOrder = new ShcOrder();
		
		mapUpsoldSkusList(shcOrder, soStagingVO);

		mapUpsellPayment(shcOrder, soStagingVO);
		
		ServiceOrder serviceOrder = soStagingVO.getServiceOrder();
		
		mapServiceOrder(shcOrder, serviceOrder);
		
		mapCustomReferenceInfo(shcOrder,serviceOrder);
		
		mapMerchandiseInfo(shcOrder, soStagingVO);

		ShcOrder[] shcOrderArray = new ShcOrder[1];
		shcOrderArray[0] = shcOrder;
		
		
		// casting it to  AbstractShcMerchandise object before calling getDivisionCode().
		// Passing shcOrderArray
		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		
		stagingDetails.setStageServiceOrder(Arrays.asList(shcOrderArray));
		
		return stagingDetails;
		
		
		
	}

	private static void mapMerchandiseInfo(ShcOrder shcOrder, ServiceOrderStagingVO soStagingVO) {
		ShcMerchandise shcMerchandise = new ShcMerchandise();
		shcMerchandise.setSerialNumber(soStagingVO.getSerialNumber());
		shcMerchandise.setModel(soStagingVO.getModelNumber());

		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		
		
		HashSet <ShcMerchandise>shcMerchandiseSet = new HashSet<ShcMerchandise>();
		shcMerchandiseSet.add(shcMerchandise);
		shcOrder.setShcMerchandises(shcMerchandiseSet);
	}
	/**
	 * This method maps the order no and unit no to the shcOrder.
	 * @param shcOrder
	 * @param serviceOrder
	 */
	public static void mapCustomReferenceInfo(ShcOrder shcOrder,ServiceOrder serviceOrder){
		String orderNo = ServiceOrderUtil.getCustomReferenceValueByType(serviceOrder, OrderConstants.CUSTOM_REF_ORDER_NUM);
		String unitNo = ServiceOrderUtil.getCustomReferenceValueByType(serviceOrder, OrderConstants.CUSTOM_REF_UNIT_NUM);
		shcOrder.setOrderNo(orderNo);
		shcOrder.setUnitNo(unitNo);		
	}
	private static void mapServiceOrder(ShcOrder shcOrder,
			ServiceOrder serviceOrder) {
		
		Calendar calendar = Calendar.getInstance();
		// set completed dt
		calendar.setTimeInMillis(serviceOrder.getCompletedDate().getTime());
		shcOrder.setCompletedDate(calendar.getTime());
		// set routed date
		calendar.setTimeInMillis(serviceOrder.getRoutedDate().getTime());
		shcOrder.setRoutedDate(calendar.getTime());
		
		shcOrder.setResolutionDescr(serviceOrder.getResolutionDs());
		shcOrder.setWfStateId(serviceOrder.getWfStateId());
		shcOrder.setSoId(serviceOrder.getSoId());
		
	}

	private static void mapUpsellPayment(ShcOrder shcOrder,
			ServiceOrderStagingVO soStagingVO) {
		AdditionalPaymentVO additionalPaymentVO = soStagingVO.getSoAdditionalPayment();
		
		if(additionalPaymentVO!= null){
			Set<ShcUpsellPayment> shcUpsellPayment = new HashSet<ShcUpsellPayment>(0);
			ShcUpsellPayment upsellPayment = new ShcUpsellPayment();
			
			upsellPayment.setAmountCollected(additionalPaymentVO.getPaymentAmount());
			upsellPayment.setPaymentMethodInd(additionalPaymentVO.getPaymentType());
			if(!StringUtils.isBlank(additionalPaymentVO.getCardNo())){
				upsellPayment.setPaymentAccNo(additionalPaymentVO.getCardNo());
				upsellPayment.setAuthNo(additionalPaymentVO.getAuthNumber());
			}else{
				upsellPayment.setPaymentAccNo(null);
			}
//			}else if(!StringUtils.isBlank(additionalPaymentVO.getCheckNo())){
//				upsellPayment.setPaymentAccNo(additionalPaymentVO.getCheckNo());
//			}
			if(additionalPaymentVO.getCardExpireMonth()!= null && additionalPaymentVO.getCardExpireYear()!= null){
				String paymentExpDate = additionalPaymentVO.getCardExpireMonth().toString().concat(additionalPaymentVO.getCardExpireYear().toString());
				upsellPayment.setPaymentExpDate(paymentExpDate);
			}
			shcUpsellPayment.add(upsellPayment);
			ShcUpsellPayment [] shcUpsellArray = new ShcUpsellPayment[shcUpsellPayment.size()];
			shcUpsellArray = shcUpsellPayment.toArray(shcUpsellArray);
	
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			
			shcOrder.setShcUpsellPayments(shcUpsellPayment);
	
	
		}
	}

	private static void mapUpsoldSkusList(ShcOrder shcOrder,
			ServiceOrderStagingVO soStagingVO) {
		
		ShcOrderSku shcUpsoldSku = null;
		Set<ShcOrderSku> shcOrderSkus = new HashSet<ShcOrderSku>(0);
		List<ServiceOrderAddonVO> soAddonList = soStagingVO.getSoAddonSkusList();
		Double totalAddOnServiceFee = 0.0; 
		for(ServiceOrderAddonVO  soAddon :soAddonList){
			Double finalPrice = MoneyUtil.getRoundedMoney((1 - soAddon.getMargin()) * soAddon.getRetailPrice());
			shcUpsoldSku = new ShcOrderSku();
			shcUpsoldSku.setDescription(soAddon.getDescription());
			shcUpsoldSku.setSku(soAddon.getSku());
			shcUpsoldSku.setRetailPrice(soAddon.getRetailPrice());
			shcUpsoldSku.setQty(soAddon.getQuantity());
			shcUpsoldSku.setAddOnInd(new Integer(1).shortValue());
			shcUpsoldSku.setFinalPrice(finalPrice);
			shcUpsoldSku.setServiceFee(soAddon.getServiceFee());
			shcOrderSkus.add(shcUpsoldSku);
			totalAddOnServiceFee = MoneyUtil.add(totalAddOnServiceFee,soAddon.getServiceFee());
		}
		if(soAddonList!= null && soAddonList.size()> 0){
			ShcOrderSku[] arrayShcSku = new ShcOrderSku[shcOrderSkus.size()];
			arrayShcSku = shcOrderSkus.toArray(arrayShcSku);

			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			
			shcOrder.setShcOrderSkus(shcOrderSkus);
		}
		soStagingVO.setTotalAddOnServiceFee(totalAddOnServiceFee);
	}

}
