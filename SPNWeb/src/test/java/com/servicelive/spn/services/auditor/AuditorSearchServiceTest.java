package com.servicelive.spn.services.auditor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.Assert;

import com.servicelive.spn.common.detached.BackgroundInformationVO;

public class AuditorSearchServiceTest {
	
	private AuditorSearchService auditorSearchService;

	@Test
	public void getExportToCSVCommaTest(){
		
		auditorSearchService = new AuditorSearchService();
		List<BackgroundInformationVO> bckgdInfoVO = new ArrayList<BackgroundInformationVO>();
		BackgroundInformationVO bkgnVO = new BackgroundInformationVO();
		bkgnVO.setVendorBusinessName("Home Sweet Home");
		bkgnVO.setVendorId(10202);
		bkgnVO.setResourceId(10254);
		bkgnVO.setProviderFirstName("Brian");
		bkgnVO.setProviderLastName("Sc Brian");
		bkgnVO.setVerificationDate(new Date());
		bkgnVO.setReverificationDate(new Date());
		bkgnVO.setCriteriaBg(30);
		bkgnVO.setRecertificationStatus("7");
		bckgdInfoVO.add(bkgnVO);
		
		StringBuffer buffer = auditorSearchService.getExportToCSVComma(bckgdInfoVO);
		Assert.assertNotNull(buffer);	
		
	}
}
