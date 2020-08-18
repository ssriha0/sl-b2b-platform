package com.newco.marketplace.business.businessImpl.staging;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.oms.OMSStagingBridge;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderAddonDAO;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.MoneyUtil;


public class StagingBO implements IStagingBO, OrderConstants {

	private static final Logger logger = Logger.getLogger(StagingBO.class.getName());
	private OMSStagingBridge omsStagingBridge;
	private IServiceOrderAddonDAO serviceOrderAddonDAO;

	public void updateFinalPrice(ServiceOrder serviceOrder, Double finalPrice, Double permitPrice, Double totalAddOnServiceFee) throws BusinessServiceException
	{
		String orderNo = ServiceOrderUtil.getCustomReferenceValueByType(serviceOrder, CUSTOM_REF_ORDER_NUM);
		String unitNo = ServiceOrderUtil.getCustomReferenceValueByType(serviceOrder, CUSTOM_REF_UNIT_NUM);
		try {
			double serviceFeePercentage = serviceOrder.getServiceFeePercentage();
			double totalMainSkuPrice = ServiceOrderUtil.calcMainSkuFinalAmt(serviceOrder);
			double totalAddOnPrice = serviceOrder.getUpsellAmt();
			double mainSkuServiceFee = MoneyUtil.getRoundedMoney( serviceFeePercentage * totalMainSkuPrice);
			double addOnServiceFee = MoneyUtil.getRoundedMoney( serviceFeePercentage * totalAddOnPrice);
			double totalServiceFee =  MoneyUtil.add( mainSkuServiceFee,addOnServiceFee) ;
			//totalAddOnService is from so_addon table which matches with shc_order_sku for addons.
			//addOnServiceFee is calculated  addon service fee that matches frontend and finance
			//Recomputing serviceFeeForMainSkus to match frontend and finance and adjust values on staging
			Double serviceFeeForMainSkus = MoneyUtil.subtract(totalServiceFee, totalAddOnServiceFee);
			omsStagingBridge.updateFinalPrice(orderNo, unitNo, finalPrice, permitPrice, serviceFeeForMainSkus);
		} catch(Exception e) {
			String error = "Update final price failed during staging.";
			logger.error(error, e);
			throw new BusinessServiceException(error, e);
		}
	}
	
	/**
  * 
  * @return
	 */
	public OMSStagingBridge getOmsStagingBridge() {
		return omsStagingBridge;
	}
	/**
 * 
 * @param omsStagingBridge
	 */
	public void setOmsStagingBridge(OMSStagingBridge omsStagingBridge) {
		this.omsStagingBridge = omsStagingBridge;
	}


	/**
	 * @return the serviceOrderAddonDAO
	 */
	public IServiceOrderAddonDAO getServiceOrderAddonDAO() {
		return serviceOrderAddonDAO;
	}

	/**
	 * @param serviceOrderAddonDAO the serviceOrderAddonDAO to set
	 */
	public void setServiceOrderAddonDAO(IServiceOrderAddonDAO serviceOrderAddonDAO) {
		this.serviceOrderAddonDAO = serviceOrderAddonDAO;
	}
}