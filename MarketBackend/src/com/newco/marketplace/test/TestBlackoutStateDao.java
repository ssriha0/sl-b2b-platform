package com.newco.marketplace.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;

public class TestBlackoutStateDao extends TestCase {

	@Test
	public void testCheckBlackoutState() {
		ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx();
		BuyerDao buyerDao = (BuyerDao)ctx.getBean("buyerDao");
		try{
			boolean flag = buyerDao.checkBlackoutState("IN");
			assertTrue(flag == false);
		}catch(Exception e){
			fail("Not yet implemented");
		}
		
	}

	@Test
	public void testSaveBlackoutBuyerLead() {
		ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx();
		BuyerDao buyerDao = (BuyerDao)ctx.getBean("buyerDao");
		BuyerRegistrationVO buyerRegVO = new BuyerRegistrationVO();
		try{
			buyerRegVO.setEmail("abc@xyz.com");
			buyerRegVO.setFirstName("Test");
			buyerRegVO.setLastName("Internet");
			buyerRegVO.setBusinessState("IN");
			buyerRegVO.setBusinessZip("23432");
			buyerDao.saveBlackoutBuyerLead(buyerRegVO);
		}catch (Exception e) {
			e.printStackTrace();
			fail("SaveBlackoutBuyerLead");
		}
	}

	@Test
	public void testGetBlackoutStateCodes() {
		ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx();
		BuyerDao buyerDao = (BuyerDao)ctx.getBean("buyerDao");
		try {
			List<String> li = buyerDao.getBlackoutStateCodes();
			System.out.println("Hai"+li.size());
			assertTrue(li.size() > 0);			
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
