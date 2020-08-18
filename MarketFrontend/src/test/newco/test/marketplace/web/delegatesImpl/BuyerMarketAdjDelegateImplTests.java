/*
 * @(#)BuyerMarketAdjDelegateImplTest.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package test.newco.test.marketplace.web.delegatesImpl;

import java.math.BigDecimal;
import java.util.List;

import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.test.BaseSpringTestCase;
import com.newco.marketplace.web.delegates.IBuyerMarketAdjDelegate;

/**
 * This class tests the implementation of Buyer Market Adjustment Delegates
 * 
 * @author Mahmud Khair
 *
 */
public class BuyerMarketAdjDelegateImplTests extends BaseSpringTestCase {
	
	private IBuyerMarketAdjDelegate buyerMarketAdjDelegate;
	
	/**
	 * Sets the delegate
	 * 
	 * @param buyerMarketAdjDelegate
	 */
	public void setBuyerMarketAdjDelegate(IBuyerMarketAdjDelegate buyerMarketAdjDelegate) {
		this.buyerMarketAdjDelegate= buyerMarketAdjDelegate;
	}
	/**
	 * Test method for {@link com.newco.marketplace.web.delegatesImpl.BuyerMarketAdjDelegateImpl#getBuyerMarkets(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws BusinessServiceException 
	 */
	public void testGetBuyerMarkets() throws BusinessServiceException {
		List<BuyerMarketAdjVO> buyerMarkets = buyerMarketAdjDelegate.getBuyerMarkets("22", "marketId,marketName,stateName", "ASC");
			assertEquals(331, buyerMarkets.size());
			assertEquals(3, buyerMarkets.get(2).getMarketId().intValue());
			assertEquals("Worcester", buyerMarkets.get(2).getMarketName());
			assertEquals("Connecticut", buyerMarkets.get(2).getStateName());
			assertEquals(0.1,Double.parseDouble(buyerMarkets.get(2).getAdjustment().toString()), 0);
	}
	
	/**
	 * Test the order ascending order of the list
	 * @throws BusinessServiceException 
	 */
	public void testGetBuyerMarketsASC() throws BusinessServiceException {
		List<BuyerMarketAdjVO> buyerMarkets = buyerMarketAdjDelegate.getBuyerMarkets("22", "marketId,marketName,stateName", "ASC");
		assertEquals(331, buyerMarkets.size());
		assertEquals(1, buyerMarkets.get(0).getMarketId().intValue());
		assertEquals("Nassau-Suffolk", buyerMarkets.get(0).getMarketName());
		assertEquals("New York", buyerMarkets.get(0).getStateName());
		assertEquals(2, buyerMarkets.get(1).getMarketId().intValue());
		assertEquals("Springfield, Ma", buyerMarkets.get(1).getMarketName());
		assertEquals("Massachusetts", buyerMarkets.get(1).getStateName());
		assertEquals(3, buyerMarkets.get(2).getMarketId().intValue());
		assertEquals("Worcester", buyerMarkets.get(2).getMarketName());
		assertEquals("Connecticut", buyerMarkets.get(2).getStateName());
	}
	
	/**
	 * Test the order descending order of the list
	 * @throws BusinessServiceException 
	 */
	public void testGetBuyerMarketsDESC() throws BusinessServiceException {
		List<BuyerMarketAdjVO> buyerMarkets = buyerMarketAdjDelegate.getBuyerMarkets("22", "marketId DESC,marketName DESC,stateName", "DESC");
		assertEquals(331, buyerMarkets.size());
		assertEquals(331, buyerMarkets.get(0).getMarketId().intValue());
		assertEquals("Anchorage, Ak", buyerMarkets.get(0).getMarketName());
		assertEquals("Alaska", buyerMarkets.get(0).getStateName());
		assertEquals(330, buyerMarkets.get(1).getMarketId().intValue());
		assertEquals("Richland--Kennewick", buyerMarkets.get(1).getMarketName());
		assertEquals("Washington", buyerMarkets.get(1).getStateName());
		assertEquals(329, buyerMarkets.get(2).getMarketId().intValue());
		assertEquals("Spokane, Wa", buyerMarkets.get(2).getMarketName());
		assertEquals("Washington", buyerMarkets.get(2).getStateName());
		
	}
	

	/**
	 * Test method for {@link com.newco.marketplace.web.delegatesImpl.BuyerMarketAdjDelegateImpl#updateAdjustmentRate(com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO)}.
	 * @throws DataServiceException 
	 * @throws BusinessServiceException 
	 */
	public void testUpdateAdjustmentRate() throws DataServiceException, BusinessServiceException {
		BuyerMarketAdjVO buyerMarketAdjVO = new BuyerMarketAdjVO();
		buyerMarketAdjVO.setBuyerId(Integer.parseInt("22"));
		buyerMarketAdjVO.setMarketId(1);
		buyerMarketAdjVO.setAdjustment(new BigDecimal(0.3));
		BuyerMarketAdjVO buyerMarketAdjVO1 = new BuyerMarketAdjVO();
		buyerMarketAdjVO1.setBuyerId(Integer.parseInt("22"));
		buyerMarketAdjVO1.setMarketId(2);
		buyerMarketAdjVO1.setAdjustment(new BigDecimal(0.25));
		
		buyerMarketAdjDelegate.updateAdjustmentRate(buyerMarketAdjVO);
		buyerMarketAdjDelegate.updateAdjustmentRate(buyerMarketAdjVO1);
		List<BuyerMarketAdjVO> buyerMarkets = buyerMarketAdjDelegate.getBuyerMarkets("22", "marketId", "ASC");
		assertEquals(0.3,Double.parseDouble(buyerMarkets.get(0).getAdjustment().toString()), 0.0);
		assertEquals(0.25,Double.parseDouble(buyerMarkets.get(1).getAdjustment().toString()), 0.0);
	}		
}
