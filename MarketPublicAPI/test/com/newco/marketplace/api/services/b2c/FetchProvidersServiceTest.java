/**
 * 
 */
package com.newco.marketplace.api.services.b2c;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.services.leadsmanagement.LeadManagementValidator;
import com.newco.marketplace.api.services.leadsmanagement.v2_0.FetchProvidersService;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest;

/**
 * @author Infosys
 *
 */
public class FetchProvidersServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(FetchProvidersServiceTest.class);
	private LeadManagementValidator validator;
	private FetchProvidersService service;
	private LeadManagementMapper mapper;
	private LeadProcessingBO leadProcessingBO;
	FetchProviderFirmRequest request = null;
	
	@Before
	public void setUp() {
		service = new FetchProvidersService();
		validator = new LeadManagementValidator();
		mapper = new LeadManagementMapper();
		leadProcessingBO =mock(LeadProcessingBO.class);
		validator.setLeadProcessingBO(leadProcessingBO);
		service.setLeadProcessingBO(leadProcessingBO);
		
		request = new FetchProviderFirmRequest();
		request.setCustomerZipCode("91901");
		request.setPrimaryProject("1700");
		request.setClientId("SHS");
		request.setUrgencyOfService("AFTER_TOMORROW");
		request.setLeadSource("MKT41");
		
		
	}
	@Test
	public void testValidateInvalidZipNonLaunchMarket(){
		request.setCustomerZipCode("99999");
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("0");
			request = validator.validate(request);

			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0007",request.getValidationCode().getCode());
			Assert.assertEquals("Invalid Zip Code",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateInvalidZipNonLaunchMarket() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidateInvalidZipNonLaunchMarket() executed successfully...");
	}
	
	@Test
	public void testValidateInvalidZipLaunchMarket(){
		request.setCustomerZipCode("99999");
		request.setLeadNonLaunchZipSwitch(true);
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("0");
			
			request = validator.validate(request);
			
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0007",request.getValidationCode().getCode());
			Assert.assertEquals("Invalid Zip Code",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateInvalidZipLaunchMarket() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidateInvalidZipLaunchMarket() executed successfully...");
	}
	
	@Test
	public void testValidateOutofMarketZipLaunchMarket(){
		request.setCustomerZipCode("60046");
		request.setLeadNonLaunchZipSwitch(true);
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("1");
			when(leadProcessingBO.validateLaunchZip(request.getCustomerZipCode())).thenReturn("0");
			request = validator.validate(request);
			
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0001",request.getValidationCode().getCode());
			Assert.assertEquals("The Provided ZipCode is not in the Launch Market",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateOutofMarketZipLaunchMarket() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidateOutofMarketZipLaunchMarket() executed successfully...");
	}
	
	@Test
	public void testValidateValidZipNonLaunchMarket(){
		request.setCustomerZipCode("60046");
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("1");
			
			request.setSlNodeId(1700);
			request.setLmsProjectDescription("Basic Service|Electrical");
			request.setLeadCategory("Electrical");
			when(leadProcessingBO.validatePrimaryProject(request.getPrimaryProject(),request)).thenReturn(request);
			
			
			request = validator.validate(request);
			
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0000",request.getValidationCode().getCode());
			Assert.assertEquals("Success",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateValidZipNonLaunchMarket() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidateValidZipNonLaunchMarket() executed successfully...");
	}
	
	@Test
	public void testValidateValidZipLaunchMarket(){
		request.setLeadNonLaunchZipSwitch(true);
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("1");
			when(leadProcessingBO.validateLaunchZip(request.getCustomerZipCode())).thenReturn("1");
			
			
			request.setSlNodeId(1700);
			request.setLmsProjectDescription("Basic Service|Electrical");
			request.setLeadCategory("Electrical");
			when(leadProcessingBO.validatePrimaryProject(request.getPrimaryProject(),request)).thenReturn(request);
			
			
			request = validator.validate(request);
			
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0000",request.getValidationCode().getCode());
			Assert.assertEquals("Success",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateValidZipLaunchMarket() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidateValidZipLaunchMarket() executed successfully...");
	}
	
	@Test
	public void testValidateInvalidProject(){
		request.setPrimaryProject("1701");
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("1");
			when(leadProcessingBO.validateLaunchZip(request.getCustomerZipCode())).thenReturn("1");
			
			
			request.setSlNodeId(null);
			request.setLmsProjectDescription(null);
			request.setLeadCategory(null);
			when(leadProcessingBO.validatePrimaryProject(request.getPrimaryProject(),request)).thenReturn(request);
			
			
			request = validator.validate(request);
			
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0003",request.getValidationCode().getCode());
			Assert.assertEquals("The Provided Project is not valid",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateInvalidProject() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidateInvalidProject() executed successfully...");
	}
	
	@Test
	public void testValidZipAndProject(){
		try {
			when(leadProcessingBO.validateAllZip(request.getCustomerZipCode())).thenReturn("1");
			when(leadProcessingBO.validateLaunchZip(request.getCustomerZipCode())).thenReturn("1");
			
			request.setSlNodeId(1700);
			request.setLmsProjectDescription("Basic Service|Electrical");
			request.setLeadCategory("Electrical");
			when(leadProcessingBO.validatePrimaryProject(request.getPrimaryProject(),request)).thenReturn(request);
			
			
			request = validator.validate(request);
			
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0000",request.getValidationCode().getCode());
			Assert.assertEquals("Success",request.getValidationCode().getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidZipAndProject() in FetchProvidersServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: FetchProvidersServiceTest.testValidZipAndProject() executed successfully...");
	}
	
}
