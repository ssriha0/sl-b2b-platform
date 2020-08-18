package com.newco.marketplace.business.businessImpl.hi;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.vo.hi.provider.ProviderRegistrationVO;
import com.newco.marketplace.vo.provider.WarrantyVO;



public class ProviderBOImplTest {
	
	
	private ProviderBOImpl  providerBO;
	
	@Before
	public void setUp() {
		providerBO = new ProviderBOImpl();
		
	}
	
	@Test
	public void validateLicenseCreateFirm(){
		ProviderRegistrationVO providerRegistrationVO=new ProviderRegistrationVO();
		providerRegistrationVO.setLicensePresent(true);
		providerRegistrationVO=providerBO.validateLicenseCreateFirm(providerRegistrationVO);
		Assert.assertNotNull(providerRegistrationVO.getResults());
		
	}
	
	@Test
	public void validateGeneralLiabilityState(){
		ProviderRegistrationVO providerRegistrationVO=new ProviderRegistrationVO();
		providerRegistrationVO.setInsurancePresent(true); 
		providerRegistrationVO.setGeneralLInd(true);
		providerRegistrationVO=providerBO.validateGeneralLiabilityState(providerRegistrationVO,null);
		Assert.assertNotNull(providerRegistrationVO.getResults());
		
	}
	
	
	
	@Test
	public void validateUpdateFirmDetails(){
		ProviderRegistrationVO providerRegistrationVO=new ProviderRegistrationVO();
		providerRegistrationVO.setInsurancePresent(true); 
		providerRegistrationVO.setGeneralLInd(true);
		providerRegistrationVO=providerBO.validateUpdateFirmDetails(providerRegistrationVO);
		Assert.assertNull(providerRegistrationVO.getResults());
		
	}
	
	@Test
	public void validateWarrantyInfo(){
		ProviderRegistrationVO providerRegistrationVO=new ProviderRegistrationVO();
		WarrantyVO warrantyVO=new WarrantyVO();
		warrantyVO.setConductDrugTest("0");
		providerRegistrationVO.setWarrantyVO(warrantyVO);
		providerRegistrationVO=providerBO.validateWarrantyInfo(providerRegistrationVO);
		Assert.assertNotNull(providerRegistrationVO.getResults());
		
	}
	
	
	
	
}