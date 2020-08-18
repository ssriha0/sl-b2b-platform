package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptResponse;
import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class MobileSoAcceptTest {
	private static final Logger LOGGER = Logger.getLogger(MobileSoAcceptTest.class);
	private MobileGenericMapper mobileGenericMapper;
	private HttpServletRequest httpRequest;
	APIRequestVO apiVO;
	MobileSOAcceptResponse response;
	ProcessResponse processResponse;
	ResultsCode invalidCode;
	@Before
	public void setUp() {
		mobileGenericMapper = new MobileGenericMapper();
		httpRequest = mock(HttpServletRequest.class);
		apiVO = new APIRequestVO(httpRequest);
		apiVO.setProviderId("Non Numeric");
		apiVO.setProviderResourceId(10254);
		apiVO.setRoleId(2);
		invalidCode = ResultsCode.INTERNAL_SERVER_ERROR;
	}
	
	@Test
	public void mapAcceptRequest(){
		AcceptVO acceptVo =null; 
		try{
			acceptVo = mobileGenericMapper.mapAcceptSoRequest(apiVO, null, 23768, false);
		}catch (Exception e) {
			LOGGER.error("Exception in Junit Execution for Accept Servioce Order"+ e.getMessage());
		}
		Assert.assertNull("Expected firmId in the Object would be null", acceptVo.getFirmId());
	}
	
	@Test
	public void createAcceptResponse(){ 
		try{
			response = mobileGenericMapper.createAcceptResponse(processResponse);
		}catch (Exception e) {
			LOGGER.error("Exception in Junit Execution for Accept Servioce Order"+ e.getMessage());
		}
		ErrorResult error =response.getResults().getError().get(0);
		Assert.assertSame("EXpected Error Code for Internal Server Error","0004", error.getCode());
	}
	
}

