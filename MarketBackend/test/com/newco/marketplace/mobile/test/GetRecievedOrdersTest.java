/**
 * 
 */
package com.newco.marketplace.mobile.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;

/**
 * @author Infosys
 *
 */
public class GetRecievedOrdersTest {
	private static final Logger LOGGER = Logger.getLogger(GetRecievedOrdersTest.class);
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	ReasonCodeVO outPut;
	List<ReasonCodeVO> reasonCodeList;
	List<String> reasonCodeType;
	String reasonType;
	@Before
	public void setUp() {
		mobileBO = new MobileGenericBOImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		//Setting Details for getReasonCodes()
	    reasonCodeList =new ArrayList<ReasonCodeVO>();
		outPut = new ReasonCodeVO();
	    outPut.setReasonCode("TestCode");
		outPut.setReasonCodeId(1);
		outPut.setReasonCodeType("Reject");
		reasonCodeList.add(outPut);
		//Setting Details for getReasonCodeType() 
		reasonCodeType = new ArrayList<String>();
		reasonCodeType.add("Reject");
		reasonCodeType.add("TypeOfProblem");
		reasonCodeType.add("Reschedule");
		reasonType ="Reject";
	}
	
	@Test
	public void getReasonCodeType(){
		List<String> result = null;
		try {
			when(genericDao.getProviderRespReason()).thenReturn(reasonCodeType);
			result = mobileBO.getResonCodeType();
			System.out.println("Testing"+result);
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for Get Reason Codes"+ e.getMessage());
		}
		Assert.assertNotNull(result);
	}
	
	@Test
	public void getReasonCodes(){
		List<ReasonCodeVO> result = null;
		try {
			when(genericDao.getRespReasonCodes(reasonType)).thenReturn(reasonCodeList);
			result = mobileBO.getProviderReasoncodes(reasonType);
			System.out.println("Testing "+ result.size());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for Get Reason Codes");
		}
		Assert.assertNotNull(result);
	}
}
