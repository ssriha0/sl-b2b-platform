package com.newco.marketplace.api.mobile.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceResponse;
import com.newco.marketplace.api.mobile.beans.vo.ForgetUnamePwdVO;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;

public class ForgetUnamePwdTest {
	private MobileGenericValidator validator; 
	private MobileGenericMapper mapper;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
	}
	
	@Test
	public void validateForgotPasswordRequest(){
		ForgetUnamePwdServiceRequest request = formForgetPasswordRequest();
		ForgetUnamePwdServiceResponse forgetUnamePwdServiceResponse = new ForgetUnamePwdServiceResponse();
		forgetUnamePwdServiceResponse = validator.validateForgetUnamePwdServiceRequest(request, forgetUnamePwdServiceResponse);
		Assert.assertNotNull(forgetUnamePwdServiceResponse);
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults());
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults().getError());
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults().getError().get(0));
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults().getError().get(0).getCode());
		Assert.assertEquals("3102", forgetUnamePwdServiceResponse.getResults().getError().get(0).getCode());
	}
	
	@Test
	public void validateForgotUserNameRequest(){
		ForgetUnamePwdServiceRequest request = formForgetUnameRequest();
		ForgetUnamePwdServiceResponse forgetUnamePwdServiceResponse = new ForgetUnamePwdServiceResponse();
		forgetUnamePwdServiceResponse = validator.validateForgetUnamePwdServiceRequest(request, forgetUnamePwdServiceResponse);
		Assert.assertNotNull(forgetUnamePwdServiceResponse);
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults());
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults().getError());
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults().getError().get(0));
		Assert.assertNotNull(forgetUnamePwdServiceResponse.getResults().getError().get(0).getCode());
		Assert.assertEquals("3117", forgetUnamePwdServiceResponse.getResults().getError().get(0).getCode());
	}

	@Test
	public void mapForgotUserNameWithEmailRequest(){
		ForgetUnamePwdServiceRequest request = formForgetUnameRequestWithEmail();
		ForgetUnamePwdVO forgetUnamePwdVO = null;
		try {
			forgetUnamePwdVO = mapper.mapForgetUnamePwdService(request);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(forgetUnamePwdVO);
		Assert.assertEquals(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_EMAIL, forgetUnamePwdVO.getRequestFor());
		Assert.assertEquals("abc@gmail.com", forgetUnamePwdVO.getEmail());
	}
	
	@Test
	public void mapForgotUserNameWithUserIdRequest(){
		ForgetUnamePwdServiceRequest request = formForgetUnameRequestWithUserId();
		ForgetUnamePwdVO forgetUnamePwdVO = null;
		try {
			forgetUnamePwdVO = mapper.mapForgetUnamePwdService(request);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(forgetUnamePwdVO);
		Assert.assertEquals(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID, forgetUnamePwdVO.getRequestFor());
		Assert.assertEquals("abc@gmail.com", forgetUnamePwdVO.getEmail());
		Assert.assertEquals(10010, Integer.parseInt(forgetUnamePwdVO.getUserId()));
	}
	

	private ForgetUnamePwdServiceRequest formForgetPasswordRequest() {
		ForgetUnamePwdServiceRequest forgetUnamePwdServiceRequest = new ForgetUnamePwdServiceRequest();
		forgetUnamePwdServiceRequest.setRequestFor(PublicMobileAPIConstant.REQUEST_FOR_PASSWORD);
		forgetUnamePwdServiceRequest.setEmail("abc@gmail.com");
	    return forgetUnamePwdServiceRequest; 
	}
	
	private ForgetUnamePwdServiceRequest formForgetUnameRequest() {
		ForgetUnamePwdServiceRequest forgetUnamePwdServiceRequest = new ForgetUnamePwdServiceRequest();
		forgetUnamePwdServiceRequest.setRequestFor(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID);
		forgetUnamePwdServiceRequest.setEmail("abc@gmail.com");
	    return forgetUnamePwdServiceRequest; 
	}
	
	private ForgetUnamePwdServiceRequest formForgetUnameRequestWithEmail() {
		ForgetUnamePwdServiceRequest forgetUnamePwdServiceRequest = new ForgetUnamePwdServiceRequest();
		forgetUnamePwdServiceRequest.setRequestFor(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_EMAIL);
		forgetUnamePwdServiceRequest.setEmail("abc@gmail.com");
	    return forgetUnamePwdServiceRequest; 
	}
	 
	private ForgetUnamePwdServiceRequest formForgetUnameRequestWithUserId() {
		ForgetUnamePwdServiceRequest forgetUnamePwdServiceRequest = new ForgetUnamePwdServiceRequest();
		forgetUnamePwdServiceRequest.setRequestFor(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID);
		forgetUnamePwdServiceRequest.setEmail("abc@gmail.com");
		forgetUnamePwdServiceRequest.setUserId(10010);
	    return forgetUnamePwdServiceRequest; 
	}
	
}
