/**
 * 
 */
package com.newco.marketplace.api.services.b2c;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.leadsmanagement.LeadManagementValidator;
import com.newco.marketplace.api.services.leadsmanagement.v2_0.FetchProvidersService;
import com.newco.marketplace.api.services.leadsmanagement.v2_0.PostLeadService;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.Contact;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.CustomerContact;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmIds;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.Result;
/**
 * @author Infosys
 *
 */
public class PostLeadServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(PostLeadServiceTest.class);
	private LeadManagementValidator validator;
	private PostLeadService service;
	private LeadManagementMapper mapper;
	private LeadProcessingBO leadProcessingBO;
	SLLeadVO slLeadVO = null;
	LeadRequest request = null;
	APIRequestVO apiVO = null;
	
	@Before
	public void setUp() {
		service = new PostLeadService();
		validator = new LeadManagementValidator();
		mapper = new LeadManagementMapper();
		leadProcessingBO =mock(LeadProcessingBO.class);
		validator.setLeadProcessingBO(leadProcessingBO);
		service.setLeadProcessingBO(leadProcessingBO);
		service.setLeadManagementValidator(validator);
		
		slLeadVO = new SLLeadVO();
		slLeadVO.setSlLeadId("676-5377-5508-17");
		slLeadVO.setLeadWfStatus("unmatched");
		slLeadVO.setLeadSource("MKT1");
		slLeadVO.setBuyerId(7000);
		slLeadVO.setCustomerZipCode("60046");
		slLeadVO.setClientId("SHS");
		slLeadVO.setProjectDescription("My project description");
		slLeadVO.setUrgencyOfService("SAME_DAY");
		slLeadVO.setSkill("INSTALL|REPAIR");
		slLeadVO.setServiceCategoryDesc("Electrical");
		
		
		request = new LeadRequest();
		request.setLeadId("676-5377-5508-17");
		
		FirmIds firmIds = new FirmIds();
		List<String>firmId = new ArrayList<String>();
		firmId.add("40927");
		firmId.add("10202");
		firmId.add("22876");
		firmIds.setFirmId(firmId);
		request.setFirmIds(firmIds);
		request.setProjectDescription("My project description");
		request.setSecondaryProjects("No projects");
		request.setServiceDate("2001-01-01");
		request.setServiceTimeZone("CST");
		request.setServiceStartTime("11:00:00");
		request.setServiceEndTime("12:00:00");
		request.setSYWRMemberId("63499");
		
		CustomerContact customerContact = new CustomerContact();
		customerContact.setFirstName("Karthik");
		customerContact.setLastName("Hariharan");
		
		Contact contact = new Contact();
		contact.setAddress("Address Line 1");
		contact.setCity("My City");
		contact.setState("IL");
		contact.setCustomerZipCode("60046");
		contact.setEmail("hkarthik86@gmail.com");
		
		customerContact.setContact(contact);
		request.setCustContact(customerContact);
		
		apiVO = new APIRequestVO(null);
		apiVO.setRequestFromPostPut(request);
	}
	
	@Test
	public void testValidatePostLeadForInvalidLeadId1(){
		slLeadVO = null;
		try {
			request = validator.validatePostLead(slLeadVO, request);
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0003",request.getValidationCode().getCode());
			Assert.assertEquals("The Provided Lead Id is invalid",request.getValidationCode().getMessage());
			
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidatePostLeadForInvalidLeadId1() in PostLeadServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: PostLeadServiceTest.testValidatePostLeadForInvalidLeadId1() executed successfully...");
	}
	
	@Test
	public void testValidatePostLeadForInvalidLeadId2(){
		slLeadVO.setSlLeadId("");
		try {
			request = validator.validatePostLead(slLeadVO, request);
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0003",request.getValidationCode().getCode());
			Assert.assertEquals("The Provided Lead Id is invalid",request.getValidationCode().getMessage());
			
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidatePostLeadForInvalidLeadId2() in PostLeadServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: PostLeadServiceTest.testValidatePostLeadForInvalidLeadId2() executed successfully...");
	}
	
	
	@Test
	public void testValidLeadId(){
		try {
			request = validator.validatePostLead(slLeadVO, request);
			Assert.assertNotNull(request.getValidationCode());
			Assert.assertEquals("0000",request.getValidationCode().getCode());
			Assert.assertEquals("Success",request.getValidationCode().getMessage());
			
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidLeadId() in PostLeadServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: PostLeadServiceTest.testValidLeadId() executed successfully...");
	}
	
	@Test
	public void testValidateLeadStatus(){
		slLeadVO.setLeadWfStatus("Unknown status");
		try {
			
			when(leadProcessingBO.getLead(request.getLeadId())).thenReturn(slLeadVO);
			FetchProviderFirmResponse response = (FetchProviderFirmResponse) service.execute(apiVO);
			Results results = response.getResults();
			
			List<ErrorResult> result = results.getError();
			
			Assert.assertNotNull(result);
			Assert.assertNotNull(result.get(0).getCode());
			Assert.assertNotNull(result.get(0).getMessage());
			
			Assert.assertEquals("0005",result.get(0).getCode());
			Assert.assertEquals("The Provided Lead Id is not in unmatched or matched status",result.get(0).getMessage());
			
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for " +
					"testValidateLeadStatus() in PostLeadServiceTest");
			e.printStackTrace();
		}
		//LOGGER.info("B2C Lead API TC: PostLeadServiceTest.testValidateLeadStatus() executed successfully...");
	}
}
