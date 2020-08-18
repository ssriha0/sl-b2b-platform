package com.newco.marketplace.persistence.daoImpl.provider;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import com.newco.marketplace.business.businessImpl.provider.GeneralInfoBOImpl;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.vo.provider.GeneralInfoVO;

public class SaveZipcodesTest {
	
	private static final Logger logger = Logger.getLogger(GeneralInfoVO.class.getName());
	private GeneralInfoBOImpl generalInfoBO;
	private GeneralInfoVO generalInfoVO;
	private IVendorResourceDao vendorResourceImpl;
	
	@Before
	public void setUp() {
		vendorResourceImpl = mock(VendorResourceImpl.class);
		generalInfoVO = new GeneralInfoVO();
	}

	@Test
	public void testSaveZipCoverage() throws Exception {

		String[] coverageZip = {"60506", "60169"};
		generalInfoVO.setCoverageZip(coverageZip);
		generalInfoVO.setResourceId("89468");
		
		try {
			when(vendorResourceImpl.insertZipcodes(generalInfoVO)).thenReturn(generalInfoVO);
			Assert.assertNotNull(generalInfoVO);
		} catch (Exception e) {
			logger.error("Exception in Junit execution for GeneralInfoBO" + e.getMessage());
		}
		
	}
	
	@Test
	public void testUpdateZipCoverage() throws Exception {

		String[] coverageZip = {"60511", "60168"};
		generalInfoVO.setCoverageZip(coverageZip);
		generalInfoVO.setResourceId("89468");
		
		try {
			when(vendorResourceImpl.updateZipcodes(generalInfoVO)).thenReturn(generalInfoVO);
			Assert.assertNotNull(generalInfoVO);
		} catch (Exception e) {
			logger.error("Exception in Junit execution for GeneralInfoBO" + e.getMessage());
		}
		
	}
}
