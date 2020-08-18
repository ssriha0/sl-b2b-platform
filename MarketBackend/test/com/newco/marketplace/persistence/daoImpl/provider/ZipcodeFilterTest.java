package com.newco.marketplace.persistence.daoImpl.provider;

import junit.framework.Assert;
import org.apache.log4j.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.zipcoverage.ZipCodeCoverageBOImpl;
import com.newco.marketplace.business.iBusiness.zipcoverage.IZipCodeCoverageBO;
import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;

public class ZipcodeFilterTest {
	
	private static final Logger logger = Logger.getLogger(GeneralInfoVO.class.getName());

	private IZipCodeCoverageBO zipCodeCoverageBOImpl;
	private String vendorId;
	private Integer firmId;
	private String stateCode, zipCode;
	private Integer providerId;
	private String spnId;
	private List<String> zips;
	private List<StateNameDTO> stateList;
	private List<ZipcodeDTO> zipList;
	private List<ProviderListDTO> serviceProvidersList;
	private List<BuyerSpnListDTO> spnIdList;
	private List<ProviderListDTO> spnProIdList;
	
	@Before
	public void setUp() {
		zipCodeCoverageBOImpl =  mock(ZipCodeCoverageBOImpl.class);
		zips = new ArrayList<String>();
		stateList = new ArrayList<StateNameDTO>();
		zipList = new ArrayList<ZipcodeDTO>();
		serviceProvidersList = new ArrayList<ProviderListDTO>();
		spnIdList = new ArrayList<BuyerSpnListDTO>();
		spnProIdList = new ArrayList<ProviderListDTO>();
	}

	@Test
	public void testGetSelectedZipCodesByFirmIdAndFilter() {
		
		firmId = 10202;
		zipCode = "60506";
		stateCode = "IL";
		providerId = 89460;
		Integer spnId = 125;
		Integer spnProId = 10254;

		try {
			String[] zip = new String[zips.size()];
			when(zipCodeCoverageBOImpl.getSelectedZipCodesByFirmIdAndFilter(firmId, stateCode, zipCode, providerId, spnId, spnProId, stateCode, zipCode)).thenReturn(zip);
			Assert.assertNotNull(zip);
		} catch (Exception e) {
			logger.error("Exception in Junit execution for ZipCodeCoverageBOImpl" + e.getMessage());
		}
	}
	
	@Test
	public void testGetStateNames() {
		vendorId = "10202";
		StateNameDTO stName1 = new StateNameDTO();
		stName1.setStateCode("IL, IN, IA, MI, MN, OH, WI");
		stateList.add(stName1);
		
		try {
			when(zipCodeCoverageBOImpl.getStateNames(vendorId)).thenReturn(stateList);
			Assert.assertNotNull(stateList);
			Assert.assertEquals("IL, IN, IA, MI, MN, OH, WI", stateList.get(0).getStateCode());
			
		} catch (Exception e) {
			logger.error("Exception in Junit execution for ZipCodeCoverageBOImpl" + e.getMessage());
		}
	}
	
	@Test
	public void testgetZipCodes() {
		vendorId = "10202";
		stateCode = "IL";
		ZipcodeDTO zipcodeDto = new ZipcodeDTO();
		zipcodeDto.setZipCode("60169, 60194, 60195, 60159");
		zipList.add(zipcodeDto);

		try {
			when(zipCodeCoverageBOImpl.getZipCodes(vendorId, stateCode)).thenReturn(zipList);
			Assert.assertNotNull(zipList);
			Assert.assertEquals("60169, 60194, 60195, 60159", zipList.get(0).getZipCode());
			
		} catch (Exception e) {
			logger.error("Exception in Junit execution for ZipCodeCoverageBOImpl" + e.getMessage());
		}
	}
	
	@Test
	public void testGetServiceProviders() {
		vendorId = "10202";
		zipCode = "60506";
		stateCode = "IL";
		
		ProviderListDTO providerListDto = new ProviderListDTO();
		providerListDto.setProviderId(10254);
		providerListDto.setProviderName("Schluter, Brian");
		serviceProvidersList.add(providerListDto);

		try {
			when(zipCodeCoverageBOImpl.getServiceProviders(vendorId, stateCode, zipCode)).thenReturn(serviceProvidersList);
			Assert.assertNotNull(serviceProvidersList);
			Assert.assertEquals(Integer.valueOf("10254"), serviceProvidersList.get(0).getProviderId());
			Assert.assertEquals("Schluter, Brian", serviceProvidersList.get(0).getProviderName());
			
		} catch (Exception e) {
			logger.error("Exception in Junit execution for ZipCodeCoverageBOImpl" + e.getMessage());
		}
		
	}
	
	@Test
	public void testGetBuyerSpnDetails() {
		vendorId = "10202";
		BuyerSpnListDTO buyerSpnListDTO = new BuyerSpnListDTO();
		buyerSpnListDTO.setSpnId(280);
		buyerSpnListDTO.setSpnName("Automation_29519");
		spnIdList.add(buyerSpnListDTO);
		
		try {
			when(zipCodeCoverageBOImpl.getBuyerSpnDetails(vendorId)).thenReturn(spnIdList);
			Assert.assertNotNull(spnIdList);
			Assert.assertEquals(Integer.valueOf("280"), spnIdList.get(0).getSpnId());
			Assert.assertEquals("Automation_29519", spnIdList.get(0).getSpnName());
		} catch (Exception e) {
			logger.error("Exception in Junit execution for ZipCodeCoverageBOImpl" + e.getMessage());
		}
		
	}
	
	@Test
	public void testGetBuyerSpnServiceProviders() {
		vendorId = "10202";
		spnId = "125";
		ProviderListDTO providerListDTO = new ProviderListDTO();
		providerListDTO.setProviderId(10254);
		providerListDTO.setProviderName("Brian Schluter");
		spnProIdList.add(providerListDTO);
		
		try {
			when(zipCodeCoverageBOImpl.getBuyerSpnServiceProviders(vendorId, spnId)).thenReturn(spnProIdList);
			Assert.assertNotNull(spnProIdList);
			Assert.assertEquals(Integer.valueOf("10254"), spnProIdList.get(0).getProviderId());
			Assert.assertEquals("Brian Schluter", spnProIdList.get(0).getProviderName());
		} catch (Exception e) {
			logger.error("Exception in Junit execution for ZipCodeCoverageBOImpl" + e.getMessage());
		}
		
	}

}
