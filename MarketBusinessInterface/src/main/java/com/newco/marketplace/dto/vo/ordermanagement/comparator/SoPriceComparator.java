package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

/**
 * Comparator class for sorting list of {@link OMServiceOrder} object based on the<br>
 * price displayed in front end display. It considers grouped order and bid orders.
 * **/
public class SoPriceComparator implements Comparator<OMServiceOrder>{

	public SoPriceComparator(){
		super();
	}
	
	/**
	 * Compares two OMServiceOrder based on the Price.<br>
	 * For grouped order it considers the GroupSpendLimitLabor <br>
	 * for Bid order BidMaxSpendLimit.
	 * For status = 120, 160, 180 price will be SpendLimitParts + SpendLimitLabor
	 * For all other cases price will be final parts price + final labor price. 
	 * **/
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		BigDecimal objValue = calculatePrice(object);
		BigDecimal compareObjValue = calculatePrice(compareObject);
		return objValue.compareTo(compareObjValue);
	}

	/**
	 * This method calculates the price that will be displayed in front end.
	 * @param serviceOrder :  {@link OMServiceOrder}
	 * @return BigDecimal : Price
	 * */
	private BigDecimal calculatePrice(OMServiceOrder serviceOrder){
		BigDecimal price = BigDecimal.valueOf(0);
		String priceType = serviceOrder.getPriceModel();
		int status = StringUtils.isBlank(serviceOrder.getSoStatus()) ? 0 : Integer.valueOf(serviceOrder.getSoStatus());
		//For Group SO
		if(null != serviceOrder.getParentGroupId()){
			price = getPriceInBigDecimal(serviceOrder.getGroupSpendLimitLabor());
			price = price.add(getPriceInBigDecimal(serviceOrder.getGroupSpendLimitParts()));
		//For Bid Order
		}else if( 110 == status && (StringUtils.isNotBlank(priceType)) && ( priceType.equals("ZERO_PRICE_BID"))){
			price = getPriceInBigDecimal(serviceOrder.getBidMaxSpendLimit());
		}else{
			if( 120 == status || 160 == status || 180 == status){
				price = getPriceInBigDecimal(serviceOrder.getFinalLaborPrice());
				price = price.add(getPriceInBigDecimal(serviceOrder.getFinalPartsPrice()));
			}else{
				price = getPriceInBigDecimal(serviceOrder.getSpendLimit());
				price = price.add(getPriceInBigDecimal(serviceOrder.getSpendLimitParts()));
			}
		}
		return price;
	}
	
	/**
	 * Method which converts price in String format to BigDecimal.<br>
	 * In case the price is null this method sets the price to 0.0.
	 * @param priceValueInString
	 * @return  BigDecimal : Price
	 * **/
	private BigDecimal getPriceInBigDecimal(String priceValueInString){
		return BigDecimal.valueOf(StringUtils.isBlank(priceValueInString)? 0 : Double.valueOf(priceValueInString));
	}
}
