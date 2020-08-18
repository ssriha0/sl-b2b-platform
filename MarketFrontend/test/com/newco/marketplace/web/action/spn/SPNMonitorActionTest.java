package com.newco.marketplace.web.action.spn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.spn.SPNMonitorBOImpl;
import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;

public class SPNMonitorActionTest {
	
	private SPNMonitorBOImpl spnMonitorBO;

	@Test
	public void getExportToCSVCommaTest(){
		
		spnMonitorBO = new SPNMonitorBOImpl();
		List<BackgroundInfoProviderVO> bckgdInfoVO = new ArrayList<BackgroundInfoProviderVO>();
		BackgroundInfoProviderVO bkgnVO = new BackgroundInfoProviderVO();
		bkgnVO.setResourceId(10254);
		bkgnVO.setProviderFirstName("Brian");
		bkgnVO.setProviderLastName("Sc Brian");
		bkgnVO.setVerificationDate(new Date());
		bkgnVO.setReverificationDate(new Date());
		bkgnVO.setCriteriaBg(30);
		bkgnVO.setRecertificationStatus("7");
		bckgdInfoVO.add(bkgnVO);
		
		StringBuffer buffer = spnMonitorBO.getExportToCSVComma(bckgdInfoVO);
		Assert.assertNotNull(buffer);	
		
	}
}