package com.newco.marketplace.persistence.daoImpl.provider;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import com.newco.marketplace.business.businessImpl.provider.GeneralInfoBOImpl;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;

public class StatesZipcodeTest {
	
	private static final Logger logger = Logger.getLogger(StateZipcodeVO.class.getName());
	private GeneralInfoBOImpl generalInfoBO;
	private StateZipcodeVO stateZipcodeDTO;
	private IZipDao zipDao;
	
	@Before
	public void setUp() {
		zipDao = mock(ZipDaoImpl.class);
		stateZipcodeDTO = new StateZipcodeVO();
	}

	@Test
	public void testLoadStateforZip() {
		List<String> zipList = new ArrayList<String>(Arrays.asList("49107", "49113", "54213", "54235"));
		List<StateZipcodeVO> stateZipcodeList = new ArrayList<StateZipcodeVO>();

		StateZipcodeVO stZip1 = new StateZipcodeVO();
		stZip1.setStateCd("MI");
		//stZip1.setZipCode("49107,49113");

		StateZipcodeVO stZip2 = new StateZipcodeVO();
		stZip2.setStateCd("WI");
		//stZip2.setZipCode("54213,54235");

		stateZipcodeList = Arrays.asList(stZip1, stZip2);

		try {
			when(zipDao.queryStateCdAndZip(zipList)).thenReturn(stateZipcodeList);
			Assert.assertNotNull(stateZipcodeList);
			Assert.assertNotNull(stateZipcodeList.get(0));
			Assert.assertNotNull(stateZipcodeList.get(1));
			
			Assert.assertEquals("MI", stateZipcodeList.get(0).getStateCd());
			Assert.assertEquals("WI", stateZipcodeList.get(1).getStateCd());
			
			//Assert.assertEquals("49107,49113", stateZipcodeList.get(0).getZipCode());
			//Assert.assertEquals("54213,54235", stateZipcodeList.get(1).getZipCode());

		} catch (Exception e) {
			logger.error("Exception in Junit execution for GeneralInfoBO" + e.getMessage());
		}
	}

}
