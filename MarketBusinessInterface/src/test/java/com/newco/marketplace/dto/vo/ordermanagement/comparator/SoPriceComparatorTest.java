package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import org.junit.Test;

import junit.framework.Assert;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

public class SoPriceComparatorTest{
	
	private SoPriceComparator comparator;
	
	@Test
	public void testCalculatePrice(){
		comparator = new SoPriceComparator();
		OMServiceOrder object = new OMServiceOrder();
		object.setSoStatus("110");
		object.setParentGroupId("123-4567-8901-23");
		object.setPriceModel("NAME_YOUR_PRICE");
		object.setFinalLaborPrice("50.00");
		object.setFinalPartsPrice("10.00");
		object.setGroupSpendLimitLabor("60.00");
		object.setGroupSpendLimitParts("20.00");
		object.setBidMaxSpendLimit("100.00");
		
		OMServiceOrder compareObject = new OMServiceOrder();
		compareObject.setSoStatus("110");
		compareObject.setParentGroupId("123-4567-8901-23");
		compareObject.setPriceModel("NAME_YOUR_PRICE");
		compareObject.setFinalLaborPrice("50.00");
		compareObject.setFinalPartsPrice("10.00");
		compareObject.setGroupSpendLimitLabor("60.00");
		compareObject.setGroupSpendLimitParts("20.00");
		compareObject.setBidMaxSpendLimit("100.00");
		
		int result = comparator.compare(object, compareObject);
		Assert.assertEquals(0, result);
	}

	
}
