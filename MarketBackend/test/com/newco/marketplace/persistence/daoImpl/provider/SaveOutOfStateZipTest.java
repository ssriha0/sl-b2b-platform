package com.newco.marketplace.persistence.daoImpl.provider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.provider.GeneralInfoBOImpl;
import com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.vo.provider.GeneralInfoVO;

import junit.framework.Assert;

public class SaveOutOfStateZipTest {

	private static final Logger logger = Logger.getLogger(GeneralInfoVO.class.getName());
	private GeneralInfoVO generalInfoVO;
	private IGeneralInfoBO generalInfoBO;
	private StateZipcodeVO stateZipcodeVO;
	
	@Before
	public void setUp() {
		generalInfoBO = mock(GeneralInfoBOImpl.class);
		generalInfoVO = new GeneralInfoVO();
		stateZipcodeVO = new StateZipcodeVO();
	}

	@Test
	public void testZipConfirmedByProvider() throws Exception {
//		stateZipcodeVO.setStateCd("MI");
//		stateZipcodeVO.setZipCode("49017,49113");
//		generalInfoVO.getStateZipcodeVO().add(stateZipcodeVO);
//		generalInfoVO.setZipcodeConfirmed("yes");
//		generalInfoVO.setResourceId("89468");
//		
//		try {
//			when(generalInfoBO.saveUserInfo(generalInfoVO)).thenReturn(generalInfoVO);
//			Assert.assertNotNull(generalInfoVO);
//		} catch (Exception e) {
//			logger.error("Exception in Junit execution for GeneralInfoBO" + e.getMessage());
//		}
		
	}
	
	@Test
	public void testZipRejectedByProvider() throws Exception {
		generalInfoVO.setZipcodeConfirmed("no");
		
		try {
			when(generalInfoBO.saveUserInfo(generalInfoVO)).thenReturn(generalInfoVO);
			Assert.assertNotNull(generalInfoVO);
		} catch (Exception e) {
			logger.error("Exception in Junit execution for GeneralInfoBO" + e.getMessage());
		}
		
	}
}
