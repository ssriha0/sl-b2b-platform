/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.UserDetails;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.v2_0.SecQuestAnsRequestVO;
/**
 * @author Infosys
 *
 */
public class ValidateSecurityQuestAnsTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private IMobileGenericBO mobileGenericBO;

	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
		mobileGenericBO =mock(MobileGenericBOImpl.class);
		mapper.setMobileGenericBO(mobileGenericBO);
		
	}
	@Test
	public void mapCriteriaAndValidateUserId(){
		ValidateSecQuestAnsRequest request = formValidateSecQuestAnsRequestForInvalidUserId();
		SecQuestAnsRequestVO questAnsRequestVO = formSecVO(request);
		Assert.assertNotNull(questAnsRequestVO);
		Assert.assertEquals("UserName", questAnsRequestVO.getRequestFor());
		ValidateSecQuestAnsResponse questAnsResponse = validator.validateSecQuestAnsRequest(questAnsRequestVO);
		Assert.assertNotNull(questAnsResponse);
		Assert.assertNotNull(questAnsResponse.getResults());		
		Assert.assertNotNull(questAnsResponse.getResults().getError());		
		Assert.assertNotNull(questAnsResponse.getResults().getError().get(0));	
		Assert.assertNotNull(questAnsResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3117",questAnsResponse.getResults().getError().get(0).getCode());	
	}
	private SecQuestAnsRequestVO formSecVO(ValidateSecQuestAnsRequest request) {
		SecQuestAnsRequestVO ansRequestVO = new SecQuestAnsRequestVO();
		ansRequestVO.setRequestFor(request.getRequestFor());
		ansRequestVO.setEmail(request.getEmail());
		ansRequestVO.setUserId(request.getUserDetails().getUserId());
		ansRequestVO.setUserName(request.getUserDetails().getUserName());
		return ansRequestVO;
	}
	private ValidateSecQuestAnsRequest formValidateSecQuestAnsRequestForInvalidUserId() {
		
		ValidateSecQuestAnsRequest questAnsRequest = new ValidateSecQuestAnsRequest();
		questAnsRequest.setRequestFor("UserName");
		questAnsRequest.setEmail("bjarard@sss.com");
		UserDetails userDetails = new UserDetails();
		questAnsRequest.setUserDetails(userDetails);
		return questAnsRequest;
	}
	private ValidateSecQuestAnsRequest formValidateSecQuestAnsRequestForInvalidUsername() {
		
		ValidateSecQuestAnsRequest questAnsRequest = new ValidateSecQuestAnsRequest();
		questAnsRequest.setRequestFor("Password");
		questAnsRequest.setEmail("bjarard@sss.com");
		UserDetails userDetails = new UserDetails();
		questAnsRequest.setUserDetails(userDetails);
		return questAnsRequest;
	}
	@Test
	public void mapCriteriaAndValidateUserName(){
		ValidateSecQuestAnsRequest request = formValidateSecQuestAnsRequestForInvalidUsername();
		SecQuestAnsRequestVO questAnsRequestVO = formSecVO(request);
		Assert.assertNotNull(questAnsRequestVO);
		Assert.assertEquals("Password", questAnsRequestVO.getRequestFor());
		ValidateSecQuestAnsResponse questAnsResponse = validator.validateSecQuestAnsRequest(questAnsRequestVO);
		Assert.assertNotNull(questAnsResponse);
		Assert.assertNotNull(questAnsResponse.getResults());		
		Assert.assertNotNull(questAnsResponse.getResults().getError());		
		Assert.assertNotNull(questAnsResponse.getResults().getError().get(0));	
		Assert.assertNotNull(questAnsResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3102",questAnsResponse.getResults().getError().get(0).getCode());	
	}

	
}
