package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsRequest;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;

public class UpdateScheduleConfirmApptTest {

	private NewMobileGenericMapper mobileMapper;
	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericBOImpl newMobileGenericBO;
	UpdateScheduleVO updateScheduleVO;
	APIRequestVO apiVO;
	
	
	private UpdateScheduleDetailsRequest request;
	private UpdateScheduleDetailsResponse response;
	@Before
	public void setUp() {
		mobileValidator=new NewMobileGenericValidator();
		mobileMapper=new NewMobileGenericMapper();
		
		request = new UpdateScheduleDetailsRequest();
		response = new UpdateScheduleDetailsResponse();
		updateScheduleVO = new UpdateScheduleVO();
		
		request.setSource("CONFIRM_APPOINTMENT");
		request.setCustomerAvailableFlag(false);
		request.setCustNotAvailableReasonCode(4);
		request.setEta("10:00:00");
		request.setSoLocNotes("my notes");
		request.setSpecialInstructions("my special instructions");
		request.setCustResponseReasonCode(2);
		request.setStartTime("09:00:00");
		request.setEndTime("11:00:00");
		
		apiVO = new APIRequestVO(null);
		apiVO.setSOId("598-6460-6811-16");
		apiVO.setProviderResourceId(10254);
		apiVO.setProviderId("10202");
	}
	
	@Test
	public void testMapUpdateTimeWindowRequestForNoCustomer(){
		updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
		
		Assert.assertNotNull(updateScheduleVO);
		Assert.assertEquals(updateScheduleVO.getSoId(), "598-6460-6811-16");
		Assert.assertEquals(updateScheduleVO.getProviderId(), 10202);
		Assert.assertEquals(updateScheduleVO.getResourceId(), 10254);
		Assert.assertEquals(updateScheduleVO.getSource(), "CONFIRM_APPOINTMENT");
		Assert.assertEquals(updateScheduleVO.getCustomerAvailableFlag(), false);
		Assert.assertEquals(updateScheduleVO.getCustNotAvailableReasonCode(), 4);
		
		Assert.assertEquals(updateScheduleVO.getScheduleStatusId(),MPConstants.CONFIRM_APPOINTMENT_ATTEMPTED);
		Assert.assertEquals(updateScheduleVO.getReasonId(), "4");
		Assert.assertEquals(updateScheduleVO.getCustomerConfirmedInd(),MPConstants.CUSTOMER_NOT_AVAILABLE);
		
	}
	
	@Test
	public void testMapperForCustomerAvblConfirmAppt(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(1);
		updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
		
		Assert.assertNotNull(updateScheduleVO);
		Assert.assertEquals(updateScheduleVO.getSoId(), "598-6460-6811-16");
		Assert.assertEquals(updateScheduleVO.getProviderId(), 10202);
		Assert.assertEquals(updateScheduleVO.getResourceId(), 10254);
		Assert.assertEquals(updateScheduleVO.getSource(), "CONFIRM_APPOINTMENT");
		Assert.assertEquals(updateScheduleVO.getCustomerAvailableFlag(), true);
		Assert.assertEquals(updateScheduleVO.getCustAvailableRespCode(), 1);
		Assert.assertEquals(updateScheduleVO.getEtaOriginalValue(),"10:00:00");
		Assert.assertEquals(updateScheduleVO.getEta(),"10:00 AM");
		Assert.assertEquals(updateScheduleVO.getSoNotes(),"my notes");
		Assert.assertEquals(updateScheduleVO.getSpecialInstructions(),"my special instructions");
		
		Assert.assertEquals(updateScheduleVO.getScheduleStatusId(),MPConstants.CONFIRM_APPOINTMENT_COMPLETED);
		Assert.assertEquals(updateScheduleVO.getReasonId(), MPConstants.TIME_WINDOW_CALL_COMPLETED_REASON);
		
		Assert.assertEquals(updateScheduleVO.getCustomerConfirmedInd(),MPConstants.CUSTOMER_AVAILABLE);
		Assert.assertNull(updateScheduleVO.getStartTimeFromRequest());
		Assert.assertNull(updateScheduleVO.getEndTimeFromRequest());
	}
	
	@Test
	public void testMapperForCustomerAvblUpdateTimeWindow(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
		
		Assert.assertNotNull(updateScheduleVO);
		Assert.assertEquals(updateScheduleVO.getSoId(), "598-6460-6811-16");
		Assert.assertEquals(updateScheduleVO.getProviderId(), 10202);
		Assert.assertEquals(updateScheduleVO.getResourceId(), 10254);
		Assert.assertEquals(updateScheduleVO.getSource(), "CONFIRM_APPOINTMENT");
		Assert.assertEquals(updateScheduleVO.getCustomerAvailableFlag(), true);
		Assert.assertEquals(updateScheduleVO.getCustAvailableRespCode(), 2);
		Assert.assertEquals(updateScheduleVO.getEtaOriginalValue(),"10:00:00");
		Assert.assertEquals(updateScheduleVO.getEta(),"10:00 AM");
		Assert.assertEquals(updateScheduleVO.getSoNotes(),"my notes");
		Assert.assertEquals(updateScheduleVO.getSpecialInstructions(),"my special instructions");
		
		Assert.assertEquals(updateScheduleVO.getScheduleStatusId(),MPConstants.CONFIRM_APPOINTMENT_COMPLETED);
		Assert.assertEquals(updateScheduleVO.getReasonId(), MPConstants.TIME_WINDOW_CALL_COMPLETED_REASON);

		Assert.assertEquals(updateScheduleVO.getCustomerConfirmedInd(),MPConstants.CUSTOMER_AVAILABLE);
		Assert.assertEquals(updateScheduleVO.getStartTimeFromRequest(),"09:00:00");
		Assert.assertEquals(updateScheduleVO.getEndTimeFromRequest(),"11:00:00");
		
		
	}
	
	@Test
	public void testMapperForCustomerAvblReschedule(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(3);
		updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
		
		Assert.assertNotNull(updateScheduleVO);
		Assert.assertEquals(updateScheduleVO.getSoId(), "598-6460-6811-16");
		Assert.assertEquals(updateScheduleVO.getProviderId(), 10202);
		Assert.assertEquals(updateScheduleVO.getResourceId(), 10254);
		Assert.assertEquals(updateScheduleVO.getSource(), "CONFIRM_APPOINTMENT");
		Assert.assertEquals(updateScheduleVO.getCustomerAvailableFlag(), true);
		Assert.assertEquals(updateScheduleVO.getCustAvailableRespCode(), 3);
		Assert.assertEquals(updateScheduleVO.getEtaOriginalValue(),"10:00:00");
		Assert.assertEquals(updateScheduleVO.getEta(),"10:00 AM");
		Assert.assertEquals(updateScheduleVO.getSoNotes(),"my notes");
		Assert.assertEquals(updateScheduleVO.getSpecialInstructions(),"my special instructions");
		
		Assert.assertEquals(updateScheduleVO.getScheduleStatusId(),MPConstants.CONFIRM_APPOINTMENT_COMPLETED);
		Assert.assertEquals(updateScheduleVO.getReasonId(), MPConstants.TIME_WINDOW_CALL_COMPLETED_REASON);
		
		Assert.assertEquals(updateScheduleVO.getCustomerConfirmedInd(),MPConstants.CUSTOMER_NOT_AVAILABLE);
		Assert.assertNull(updateScheduleVO.getStartTimeFromRequest());
		Assert.assertNull(updateScheduleVO.getEndTimeFromRequest());
		
	}
	
	@Test
	public void testMapperForCustomerAvblConfirmApptInvalidEta(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(1);
		request.setEta("invalid eta");
		
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3103");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid value for ETA");
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCustomerNotAblblReasonCodeMandatory(){
		request.setCustomerAvailableFlag(false);
		request.setCustNotAvailableReasonCode(0);
		
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3111");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Customer Not Available Reason Code is mandatory if customer is not available.");
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidSOStatus(){
		request.setCustomerAvailableFlag(false);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(false);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3114");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Service Order should be in Accepted/Active/Problem status for Confirming appointment.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidSOScheduleStatus(){
		request.setCustomerAvailableFlag(false);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(false);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3112");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Schedule Status.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAppointmentNotInNext3Day(){
		request.setCustomerAvailableFlag(false);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(false);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3115");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Service appointment date should fall in the next 3 days for Confirming appointment.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAppointmentTodayAndUnassign(){
		request.setCustomerAvailableFlag(false);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(null);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3116");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"The appointment is today. You must assign a provider.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAppointmentTodayAndCustomerResponseMandatory(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(0);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3117");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"The appointment is today. You must confirm the time window with the customer.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMapperForCustomerAvblUpdateTimeWindowNoStartTime(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		request.setStartTime(null);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3101");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Start Time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMapperForCustomerAvblUpdateTimeWindowInValidStartTime(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		request.setStartTime("invalid start time");
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3101");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid Start Time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMapperForCustomerAvblUpdateTimeWindowInValidEndTime(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		request.setEndTime("invalid end time");
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3102");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"Invalid End Time");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMapDateTimeForUpdateTimeWindowResponse(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			UpdateApptTimeVO apptTimeVO = fetchServiceDatesAndTimeWndw();
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(updateScheduleVO.getSoId())).thenReturn(apptTimeVO);
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request, updateScheduleVO, apptTimeVO);
			
			Assert.assertEquals(updateScheduleVO.getStartTimeFromRequest(),"09:00:00");
			Assert.assertEquals(updateScheduleVO.getEndTimeFromRequest(),"11:00:00");
			Assert.assertEquals(updateScheduleVO.getServiceDateStart(),"2014-05-22");
			Assert.assertEquals(updateScheduleVO.getServiceDateEnd(),"2014-05-22");
			Assert.assertEquals(updateScheduleVO.getServiceTimeStart(),"11:00 AM");
			Assert.assertEquals(updateScheduleVO.getServiceTimeZone(),"CST6CDT");
			Assert.assertEquals(updateScheduleVO.getMinTimeWindow(),2);
			Assert.assertEquals(updateScheduleVO.getMaxTimeWindow(),4);
			Assert.assertEquals(updateScheduleVO.getServiceDateType(),2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEndtimeMandatoryTimeForUpdateTimeWindowResponse(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		request.setEndTime(null);
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			UpdateApptTimeVO apptTimeVO = fetchServiceDatesAndTimeWndw();
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(updateScheduleVO.getSoId())).thenReturn(apptTimeVO);
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request, updateScheduleVO, apptTimeVO);
			
			response = mobileValidator.validateUpdateScheduleRequestForTime(updateScheduleVO);
			
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3104");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"End time is mandatory for the Service Order having date type Range");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTimeWindowMismatchForUpdateTimeWindowResponse(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		request.setEndTime("10:00:00");
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			UpdateApptTimeVO apptTimeVO = fetchServiceDatesAndTimeWndw();
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(updateScheduleVO.getSoId())).thenReturn(apptTimeVO);
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request, updateScheduleVO, apptTimeVO);
			
			response = mobileValidator.validateUpdateScheduleRequestForTime(updateScheduleVO);
			
			Assert.assertNotNull(response.getResults());
			Assert.assertNotNull(response.getResults().getError());
			Assert.assertEquals(response.getResults().getError().get(0).getCode(),"3105");
			Assert.assertEquals(response.getResults().getError().get(0).getMessage(),"The time provided does not satisfy the time window set by buyer");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*@Test
	public void testDateTimeConversionForUpdateTimeWindowResponse(){
		request.setCustomerAvailableFlag(true);
		request.setCustResponseReasonCode(2);
		
		request.setStartTime("17:00:00");
		request.setEndTime("21:10:00");
		
		
		try {
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request,apiVO, null);
			
			newMobileGenericBO = mock(NewMobileGenericBOImpl.class);
			mobileValidator.setNewMobileGenericBO(newMobileGenericBO);
			when(newMobileGenericBO.validateServiceOrderStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.validateScheduleStatus(updateScheduleVO.getSoId(),MPConstants.CONFIRM_APPOINTMENT_ACTION)).thenReturn(true);
			when(newMobileGenericBO.isAppoinmentIn3Day(updateScheduleVO.getSoId())).thenReturn(true);
			
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setServiceLocationTimeZone("CST6CDT");
			serviceOrder.setServiceDate1(new Timestamp((new Date()).getTime()));
			serviceOrder.setAcceptedResourceId(10254);
			when(newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(updateScheduleVO.getSoId())).thenReturn(serviceOrder);
			response = mobileValidator.validateUpdateScheduleRequest(updateScheduleVO);
		
			UpdateApptTimeVO apptTimeVO = fetchServiceDatesAndTimeWndw();
			when(newMobileGenericBO.fetchServiceDatesAndTimeWndw(updateScheduleVO.getSoId())).thenReturn(apptTimeVO);
			updateScheduleVO = mobileMapper.mapUpdateScheduleRequest(request, updateScheduleVO, apptTimeVO);
			
			response = mobileValidator.validateUpdateScheduleRequestForTime(updateScheduleVO);
			updateScheduleVO = mobileMapper.mapDateAndTimeAfterConversion(updateScheduleVO);
			
			Assert.assertEquals(updateScheduleVO.getServiceDateStart(),"2014-05-22 00:00:00.0");
			Assert.assertEquals(updateScheduleVO.getServiceDateEnd(),"2014-05-23 00:00:00.0");
			Assert.assertEquals(updateScheduleVO.getServiceTimeStart(),"10:00 PM");
			Assert.assertEquals(updateScheduleVO.getServiceTimeEnd(),"02:10 AM");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	private UpdateApptTimeVO fetchServiceDatesAndTimeWndw(){
		UpdateApptTimeVO apptTimeVO = new UpdateApptTimeVO();
		
		apptTimeVO.setStartDate("2014-05-22 00:00:00");
		apptTimeVO.setEndDate("2014-05-22 00:00:00");
		
		apptTimeVO.setServiceTimeStart("11:00 AM");
		apptTimeVO.setServiceTimeEnd("02:00 PM");
		apptTimeVO.setZone("CST6CDT");
		apptTimeVO.setMinTimeWindow(2);
		apptTimeVO.setMaxTimeWindow(4);
		apptTimeVO.setServiceDateType(2);
		return apptTimeVO;
	}
}
