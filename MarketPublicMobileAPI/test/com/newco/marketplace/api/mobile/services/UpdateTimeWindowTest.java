package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.so.updateTimeWindow.UpdateTimeWindowVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdateTimeWindowResponse;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdatetimeWindowRequest;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;

public class UpdateTimeWindowTest {

	private NewMobileGenericMapper mobileMapper;
	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericBOImpl newMobileGenericBO;
	UpdateTimeWindowVO timeWindowVO;
	UpdateTimeWindowVO timeWindowVO1;
	APIRequestVO apiVO;
	
	
	private UpdatetimeWindowRequest request;
	private UpdateTimeWindowResponse response;
	@Before
	public void setUp() {
		mobileValidator=new NewMobileGenericValidator();
		mobileMapper=new NewMobileGenericMapper();
		
		request = new UpdatetimeWindowRequest();
		response = new UpdateTimeWindowResponse();
		request.setStartTime("17:10:00");
		request.setEndTime("21:00:00");
		request.setEta("19:31:00");
		request.setCustomerConfirmedInd(true);
		
		apiVO = new APIRequestVO(null);
		apiVO.setSOId("598-6460-6811-16");
		apiVO.setProviderResourceId(10254);
		apiVO.setProviderId("10202");
	}
	
	@Test
	public void testMapUpdateTimeWindowRequest(){
		timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
		
		Assert.assertNotNull(timeWindowVO);
		Assert.assertEquals(timeWindowVO.getSoId(), "598-6460-6811-16");
		Assert.assertEquals(timeWindowVO.getVendorId(), 10202);
		Assert.assertEquals(timeWindowVO.getResourceId(), 10254);
		Assert.assertEquals(timeWindowVO.getStartTime(), "17:10:00");
		Assert.assertEquals(timeWindowVO.getEndTime(), "21:00:00");
		Assert.assertEquals(timeWindowVO.getEta(), "19:31:00");
		
		Assert.assertEquals(timeWindowVO.getStartTimeIn12hrformat(), "05:10 PM");
		Assert.assertEquals(timeWindowVO.getEndTimeIn12hrformat(), "09:00 PM");
		Assert.assertEquals(timeWindowVO.getEtaIn12hrformat(), "07:31 PM");
	}
	
	
	@Test
	public void testNoStartTime(){
		request.setStartTime(null);
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3101");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Start Time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidStartTime(){
		request.setStartTime("invalid time");
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3101");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Start Time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidEndTime(){
		request.setEndTime("invalid time");
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3102");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid End Time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidEta(){
		request.setEta("invalid time");
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3103");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid value for ETA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidSOStatus(){
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(timeWindowVO.getSoId(),MPConstants.UPDATE_TIME_WINDOW_v3_1)).thenReturn(false);
			
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3121");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Service Order Status.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidRequest(){
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(timeWindowVO.getSoId(),MPConstants.UPDATE_TIME_WINDOW_v3_1)).thenReturn(true);
			
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);
			Assert.assertNull(response.getResults());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*@Test
	public void testGetDateAndTimeInGMTToSave(){
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(timeWindowVO.getSoId(),MPConstants.UPDATE_TIME_WINDOW_v3_1)).thenReturn(true);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);

			timeWindowVO1 = fetchServiceDatesAndTimeWndw(timeWindowVO);
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(),null)).thenReturn(timeWindowVO1);
			timeWindowVO =  newMobileGenericBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(), null);
			timeWindowVO = mobileMapper.mapDateAndTimeToSave(timeWindowVO);
			
			Assert.assertNotNull(timeWindowVO.getStartDateToSave());
			Assert.assertNotNull(timeWindowVO.getStartTimeToSave());
			Assert.assertNotNull(timeWindowVO.getEndDateToSave());
			Assert.assertNotNull(timeWindowVO.getEndTimeToSave());
			
			Assert.assertEquals(timeWindowVO.getStartDateToSave(),"2014-05-22 00:00:00.0");
			Assert.assertEquals(timeWindowVO.getStartTimeToSave(),"10:10 PM");
			
			Assert.assertEquals(timeWindowVO.getEndDateToSave(),"2014-05-23 00:00:00.0");
			Assert.assertEquals(timeWindowVO.getEndTimeToSave(),"02:00 AM");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	@Test
	public void testEndTimeMandatoryValidation(){
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(timeWindowVO.getSoId(),MPConstants.UPDATE_TIME_WINDOW_v3_1)).thenReturn(true);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);

			timeWindowVO1 = fetchServiceDatesAndTimeWndw(timeWindowVO);
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(),null)).thenReturn(timeWindowVO1);
			timeWindowVO =  newMobileGenericBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(), null);
			timeWindowVO = mobileMapper.mapDateAndTimeToSave(timeWindowVO);
			timeWindowVO.setEndTime(null);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,true);
			
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3104");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"End time is mandatory for the Service Order having date type Range");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testTimeWindowMismatchValidation(){
		try {
			timeWindowVO = mobileMapper.mapUpdateTimeWindowRequest(request,apiVO, null);
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(timeWindowVO.getSoId(),MPConstants.UPDATE_TIME_WINDOW_v3_1)).thenReturn(true);
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,false);

			timeWindowVO1 = fetchServiceDatesAndTimeWndw(timeWindowVO);
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(),null)).thenReturn(timeWindowVO1);
			timeWindowVO =  newMobileGenericBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId(), null);
			timeWindowVO = mobileMapper.mapDateAndTimeToSave(timeWindowVO);

			timeWindowVO.setEndTime("23:00:00");
			response = mobileValidator.validateUpdateTimeWindowRequest(timeWindowVO,true);
			
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3105");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"The time provided does not satisfy the time window set by buyer");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private UpdateTimeWindowVO fetchServiceDatesAndTimeWndw(UpdateTimeWindowVO updateTimeWindowVO1){
		updateTimeWindowVO1.setStartDateInGMT("2014-05-22");
		updateTimeWindowVO1.setEndDateInGMT("2014-05-22");
		
		updateTimeWindowVO1.setServiceTimeStart("01:00 PM");
		updateTimeWindowVO1.setServiceTimeEnd("04:00 PM");
		updateTimeWindowVO1.setTimeZone("CST6CDT");
		updateTimeWindowVO1.setMinTimeWindow(2);
		updateTimeWindowVO1.setMaxTimeWindow(4);
		updateTimeWindowVO1.setServiceDateType(2);
		return updateTimeWindowVO1;
	}
}
