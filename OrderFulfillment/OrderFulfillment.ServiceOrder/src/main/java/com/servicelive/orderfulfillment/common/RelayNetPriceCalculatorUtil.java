package com.servicelive.orderfulfillment.common;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.servicelive.common.util.MoneyUtil;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class RelayNetPriceCalculatorUtil {
	
	private static final Logger logger = Logger.getLogger(RelayNetPriceCalculatorUtil.class);

	public static BigDecimal getServiceFeeForLaborAndParts(ServiceOrder serviceOrder, BigDecimal serviceFeePercentage) {
    	BigDecimal laborTaxPercentage= BigDecimal.ZERO;
    	BigDecimal partsTaxPercentage= BigDecimal.ZERO;
    	BigDecimal orginalFinalPrice=BigDecimal.ZERO; 
    	BigDecimal orginalLaborPrice=BigDecimal.ZERO; 
    	BigDecimal orginalPartsPrice=BigDecimal.ZERO; 
    	if(null!= serviceOrder.getPrice().getTaxPercentLaborSL()){
    		laborTaxPercentage = serviceOrder.getPrice().getTaxPercentLaborSL();
    		logger.info("laborTaxPercentage: "+laborTaxPercentage);
    	}
    	orginalLaborPrice= BigDecimal.valueOf(serviceOrder.getFinalPriceLabor().doubleValue() *(1.0000/(1.0000+laborTaxPercentage.doubleValue())));
   		logger.info("orginalLaborPrice: "+orginalLaborPrice);
       	
    	if(null!= serviceOrder.getPrice().getTaxPercentPartsSL()){
    		partsTaxPercentage=serviceOrder.getPrice().getTaxPercentPartsSL();
    		logger.info("partsTaxPercentage: "+partsTaxPercentage);
    	}
    	if( null!=serviceOrder.getSpendLimitParts() && null!=serviceOrder.getFinalPriceParts()){
    		orginalPartsPrice= BigDecimal.valueOf(serviceOrder.getFinalPriceParts().doubleValue() *(1.0000/(1.0000+partsTaxPercentage.doubleValue())));
    		logger.info("orginalPartsPrice: "+orginalPartsPrice);
    	}
    	
    	orginalFinalPrice = orginalLaborPrice.add(orginalPartsPrice);
    	
    	BigDecimal laborAndPartsServiceFee =  MoneyUtil.getRoundedMoneyCustomBigDecimal(serviceFeePercentage.doubleValue() * orginalFinalPrice.doubleValue());
    	logger.info("relay serviceFee: "+laborAndPartsServiceFee);
    	logger.info("orginalFinalPrice.doubleValue()="+orginalFinalPrice.doubleValue());
        return laborAndPartsServiceFee;
    }
	
	public static BigDecimal getServiceFeeForAddons(ServiceOrder serviceOrder, BigDecimal serviceFeePercentage) {
		BigDecimal originalAddonPrice = BigDecimal.ZERO;
		if(null!=serviceOrder.getAddons()){
    		logger.info("addons size"+ serviceOrder.getAddons().size());
    		for(SOAddon addon : serviceOrder.getAddons()){
    			logger.info("addon: "+addon.getTypeName());
    			logger.info("addon Retail price: "+addon.getRetailPrice());
    			logger.info("addon Margin: "+addon.getMargin());
    			logger.info("addon tax:"+addon.getTaxPercentage());
    			logger.info("addon qty: "+addon.getQuantity());
    			logger.info("addon original price: "+addon.getAddonPriceWithoutTax());
    			originalAddonPrice = originalAddonPrice.add(addon.getAddonPriceWithoutTax());
    		}
    		logger.info("final addon original price: "+originalAddonPrice);
    	}
    	BigDecimal addOnServiceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal(serviceFeePercentage.doubleValue() * originalAddonPrice.doubleValue());
    	logger.info("relay addOnServiceFee: "+addOnServiceFee);
    	logger.info("orginalFinalPrice.doubleValue()="+originalAddonPrice.doubleValue());
        return addOnServiceFee;
    }

	public static BigDecimal getServiceFeeForAddon(SOAddon addon, BigDecimal serviceFeePercentage) {
		BigDecimal originalAddonPrice = BigDecimal.ZERO;
		if(addon != null){
			logger.info("addon: "+addon.getTypeName());
			logger.info("addon Retail price: "+addon.getRetailPrice());
			logger.info("addon Margin: "+addon.getMargin());
			logger.info("addon tax:"+addon.getTaxPercentage());
			logger.info("addon qty: "+addon.getQuantity());
			logger.info("addon original price: "+addon.getAddonPriceWithoutTax());
			originalAddonPrice = originalAddonPrice.add(addon.getAddonPriceWithoutTax());
		}
		
		BigDecimal addOnServiceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal(serviceFeePercentage.doubleValue() * originalAddonPrice.doubleValue());
		logger.info("relay addOnServiceFee: "+addOnServiceFee);
		return addOnServiceFee;
	}
	
	public static BigDecimal getNetServiceFee(ServiceOrder serviceOrder, BigDecimal serviceFeePercentage){
		BigDecimal totalServiceFee = BigDecimal.ZERO;
		BigDecimal serviceFeeForLaborAndParts = getServiceFeeForLaborAndParts(serviceOrder,serviceFeePercentage);
		BigDecimal serviceFeeForAddons = getServiceFeeForAddons(serviceOrder,serviceFeePercentage);
		totalServiceFee = serviceFeeForLaborAndParts.add(serviceFeeForAddons);
		logger.info("Relay total servicefee"+totalServiceFee);
		return totalServiceFee;
		
	}
}
