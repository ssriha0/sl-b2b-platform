/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchCriteria;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchResponse;
import com.newco.marketplace.api.mobile.beans.so.search.SearchStringElement;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchResponse;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleRequest;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleResponse;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.SORescheduleInfo;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.servicelive.orderfulfillment.domain.SOSchedule;
/**
 * @author Infosys
 *
 */
public class SubmitRescheduleTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	SOSchedule soSchedule;
	String timeZone;
	String soId;
	MobileSOSubmitRescheduleResponse submitRescheduleResponse = null;
	Integer wfStateId;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mobileBO = new MobileGenericBOImpl();
		mapper = new MobileGenericMapper();
		validator.setMobileGenericBO(mobileBO);
		genericDao = new MobileGenericDaoImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		 soSchedule = null;
		 timeZone = "EDT5EST";
		 soId="555-6444-4185-52";
		 submitRescheduleResponse= new MobileSOSubmitRescheduleResponse();
		
	}
	@Test
	public void mapSubmitRescheduleNoErrorTest(){
		wfStateId = 150;
		MobileSOSubmitRescheduleRequest request = formMobileSubmitRescheduleRequestNoError();
		soSchedule = mapper.mapRescheduleRequest(request,timeZone);
		
		Assert.assertNotNull(soSchedule);
		Assert.assertEquals("1", soSchedule.getReason());
		try {
			when(genericDao.getServiceOrderStatus(soId)).thenReturn(wfStateId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		 submitRescheduleResponse = validator.validateSubmitReschedule(timeZone, soSchedule,submitRescheduleResponse,soId);
		Assert.assertNotNull(submitRescheduleResponse);
		Assert.assertNull(submitRescheduleResponse.getResults());
	}
	
	
	@Test
	public void mapSubmitRescheduleWithInvalidWFStateTest(){
		wfStateId = 110;
		MobileSOSubmitRescheduleRequest request = formMobileSubmitRescheduleRequestWithInvalidWFState();
		soSchedule = mapper.mapRescheduleRequest(request,timeZone);
		
		Assert.assertNotNull(soSchedule);
		Assert.assertEquals("1", soSchedule.getReason());
		try {
			when(genericDao.getServiceOrderStatus(soId)).thenReturn(wfStateId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		 submitRescheduleResponse = validator.validateSubmitReschedule(timeZone, soSchedule,submitRescheduleResponse,soId);
		Assert.assertNotNull(submitRescheduleResponse);
		Assert.assertNotNull(submitRescheduleResponse.getResults());		
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError());		
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError().get(0));	
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3061",submitRescheduleResponse.getResults().getError().get(0).getCode());	

	}
	
	@Test
	public void mapSubmitRescheduleWithInvalidEndDateTest(){
		wfStateId = 150;
		MobileSOSubmitRescheduleRequest request = formMobileSubmitRescheduleRequestWithInvalidEndDate();
		soSchedule = mapper.mapRescheduleRequest(request,timeZone);
		
		Assert.assertNotNull(soSchedule);
		Assert.assertEquals("1", soSchedule.getReason());
		try {
			when(genericDao.getServiceOrderStatus(soId)).thenReturn(wfStateId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		 submitRescheduleResponse = validator.validateSubmitReschedule(timeZone, soSchedule,submitRescheduleResponse,soId);
		Assert.assertNotNull(submitRescheduleResponse);
		Assert.assertNotNull(submitRescheduleResponse.getResults());		
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError());		
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError().get(0));	
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3062",submitRescheduleResponse.getResults().getError().get(0).getCode());	

	}
	
	@Test
	public void mapSubmitRescheduleWithInvalidStartDateTest(){
		wfStateId = 150;
		MobileSOSubmitRescheduleRequest request = formMobileSubmitRescheduleRequestWithInvalidStartDate();
		soSchedule = mapper.mapRescheduleRequest(request,timeZone);
		
		Assert.assertNotNull(soSchedule);
		Assert.assertEquals("1", soSchedule.getReason());
		try {
			when(genericDao.getServiceOrderStatus(soId)).thenReturn(wfStateId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		 submitRescheduleResponse = validator.validateSubmitReschedule(timeZone, soSchedule,submitRescheduleResponse,soId);
		Assert.assertNotNull(submitRescheduleResponse);
		Assert.assertNotNull(submitRescheduleResponse.getResults());		
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError());		
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError().get(0));	
		Assert.assertNotNull(submitRescheduleResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3055",submitRescheduleResponse.getResults().getError().get(0).getCode());	

	}
	private MobileSOSubmitRescheduleRequest formMobileSubmitRescheduleRequestNoError() {
		MobileSOSubmitRescheduleRequest mobileSubmitRescheduleRequest = new MobileSOSubmitRescheduleRequest();
		SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
		soRescheduleInfo.setComments("test 123");
		soRescheduleInfo.setReasonCode(1);
		soRescheduleInfo.setScheduleType("fixed");
		soRescheduleInfo.setServiceDateTime1("2020-08-14T13:00:00");
		mobileSubmitRescheduleRequest.setSoRescheduleInfo(soRescheduleInfo);
		return mobileSubmitRescheduleRequest;
	}
	
	private MobileSOSubmitRescheduleRequest formMobileSubmitRescheduleRequestWithInvalidWFState() {
		MobileSOSubmitRescheduleRequest mobileSubmitRescheduleRequest = new MobileSOSubmitRescheduleRequest();
		SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
		soRescheduleInfo.setComments("test 123");
		soRescheduleInfo.setReasonCode(1);
		soRescheduleInfo.setScheduleType("fixed");
		soRescheduleInfo.setServiceDateTime1("2020-08-14T13:00:00");
		mobileSubmitRescheduleRequest.setSoRescheduleInfo(soRescheduleInfo);
		return mobileSubmitRescheduleRequest;
	}
	
	private MobileSOSubmitRescheduleRequest formMobileSubmitRescheduleRequestWithInvalidEndDate() {
		MobileSOSubmitRescheduleRequest mobileSubmitRescheduleRequest = new MobileSOSubmitRescheduleRequest();
		SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
		soRescheduleInfo.setComments("test 123");
		soRescheduleInfo.setReasonCode(1);
		soRescheduleInfo.setScheduleType("range");
		soRescheduleInfo.setServiceDateTime1("2020-08-14T13:00:00");
		mobileSubmitRescheduleRequest.setSoRescheduleInfo(soRescheduleInfo);
		return mobileSubmitRescheduleRequest;
	}
	
	private MobileSOSubmitRescheduleRequest formMobileSubmitRescheduleRequestWithInvalidStartDate() {
		MobileSOSubmitRescheduleRequest mobileSubmitRescheduleRequest = new MobileSOSubmitRescheduleRequest();
		SORescheduleInfo soRescheduleInfo = new SORescheduleInfo();
		soRescheduleInfo.setComments("test 123");
		soRescheduleInfo.setReasonCode(1);
		soRescheduleInfo.setScheduleType("fixed");
		soRescheduleInfo.setServiceDateTime1("2010-08-14T13:00:00");
		mobileSubmitRescheduleRequest.setSoRescheduleInfo(soRescheduleInfo);
		return mobileSubmitRescheduleRequest;
	}
	
	
}
