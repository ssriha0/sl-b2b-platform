/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleRespondRequest;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleResponse;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchCriteria;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchResponse;
import com.newco.marketplace.api.mobile.beans.so.search.SearchStringElement;
import com.newco.marketplace.api.mobile.beans.vo.CounterOfferVO;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.daoImpl.so.order.ServiceOrderDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.sun.jmx.snmp.Timestamp;
/**
 * @author Infosys
 *
 */
public class RespondRescheduleTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	String soId;
	ServiceOrder so;
	RescheduleResponse response ;
	Results results;
	APIRequestVO apiVO;
	RescheduleVO loggingVo;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mobileBO = new MobileGenericBOImpl();
		mapper = new MobileGenericMapper();
		validator.setMobileGenericBO(mobileBO);
		genericDao = new MobileGenericDaoImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		
		 soId="555-6444-4185-52";
		  so = new ServiceOrder(); 
		  response = new RescheduleResponse();
		   results = null;
	}
	@Test
	public void mapRespondRescheduleNoErrorAcceptTest() {
	loggingVo = new RescheduleVO();
		String responseType ="Accept";
		Integer resourceId = 10254;
		   Integer roleId = 3;
		   String providerId = "10202";
		   loggingVo.setRoleId(3);
		   loggingVo.setRescheduleServiceDate1(new Date());
		RescheduleVO rescheduleVo = formRespondRescheduleVONoError(soId,responseType,resourceId,roleId,providerId);
		Assert.assertNotNull(rescheduleVo);
		Assert.assertEquals(3, rescheduleVo.getRoleId());
		Assert.assertEquals("10202", rescheduleVo.getFirmId());
		
		try {
			when(genericDao.checkIfRescheduleRequestExists(rescheduleVo)).thenReturn(loggingVo);
			when(genericDao.getVendorIdFromVendorResource(resourceId.toString())).thenReturn(providerId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		try {
			rescheduleVo = validator.validateRescheduleRespond(rescheduleVo);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(rescheduleVo);
		Assert.assertNotNull(rescheduleVo.getValidationCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());	
		Assert.assertEquals("0000",rescheduleVo.getValidationCode().getCode());
	}
	
	@Test
	public void mapRespondRescheduleWithErrorAcceptTest() {
		String responseType ="Accept";
		loggingVo = new RescheduleVO();
		loggingVo.setRoleId(1);
		   loggingVo.setRescheduleServiceDate1(new Date());
		Integer resourceId = 10254;
		   Integer roleId = 1;
		   String providerId = "10202";
		RescheduleVO rescheduleVo = formRespondRescheduleVONoError(soId,responseType,resourceId,roleId,providerId); 
		Assert.assertNotNull(rescheduleVo);
		Assert.assertEquals("10202", rescheduleVo.getFirmId());
		
		try {
			when(genericDao.checkIfRescheduleRequestExists(rescheduleVo)).thenReturn(loggingVo);
			when(genericDao.getVendorIdFromVendorResource(resourceId.toString())).thenReturn(providerId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		try {
			rescheduleVo = validator.validateRescheduleRespond(rescheduleVo);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(rescheduleVo);
		Assert.assertNotNull(rescheduleVo.getValidationCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());	
		Assert.assertEquals("1083",rescheduleVo.getValidationCode().getCode());
	}
	
	@Test
	public void mapRespondRescheduleWithErrorRejectTest() {
		String responseType ="Reject";
		loggingVo = new RescheduleVO();
		loggingVo.setRoleId(1);
		loggingVo.setRescheduleServiceDate1(new Date());
		Integer resourceId = 10254;
		   Integer roleId = 1;
		   String providerId = "10202";
		RescheduleVO rescheduleVo = formRespondRescheduleVONoError(soId,responseType,resourceId,roleId,providerId); 
		Assert.assertNotNull(rescheduleVo);
		Assert.assertEquals("10202", rescheduleVo.getFirmId());
		
		try {
			when(genericDao.checkIfRescheduleRequestExists(rescheduleVo)).thenReturn(loggingVo);
			when(genericDao.getVendorIdFromVendorResource(resourceId.toString())).thenReturn(providerId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		try {
			rescheduleVo = validator.validateRescheduleRespond(rescheduleVo);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(rescheduleVo);
		Assert.assertNotNull(rescheduleVo.getValidationCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());	
		Assert.assertEquals("1083",rescheduleVo.getValidationCode().getCode());
	}
	
	@Test
	public void mapRespondRescheduleWithNoErrorRejectTest() {
		String responseType ="Reject";
		loggingVo = new RescheduleVO();
		loggingVo.setRoleId(3);
		loggingVo.setRescheduleServiceDate1(new Date());
		Integer resourceId = 10254;
		   Integer roleId = 3;
		   String providerId = "10202";
		RescheduleVO rescheduleVo = formRespondRescheduleVONoError(soId,responseType,resourceId,roleId,providerId); 
		Assert.assertNotNull(rescheduleVo);
		Assert.assertEquals("10202", rescheduleVo.getFirmId());
		
		try {
			when(genericDao.checkIfRescheduleRequestExists(rescheduleVo)).thenReturn(loggingVo);
			when(genericDao.getVendorIdFromVendorResource(resourceId.toString())).thenReturn(providerId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		try {
			rescheduleVo = validator.validateRescheduleRespond(rescheduleVo);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(rescheduleVo);
		Assert.assertNotNull(rescheduleVo.getValidationCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());		
		Assert.assertNotNull(rescheduleVo.getValidationCode().getCode());	
		Assert.assertEquals("0000",rescheduleVo.getValidationCode().getCode());
	}
	private RescheduleVO formRespondRescheduleVONoError(String soId,String responseType,Integer resourceId,Integer roleId,String providerId){
	RescheduleVO rescheduleVO = new RescheduleVO();
	rescheduleVO.setSoId(soId);
	rescheduleVO.setRescheduleRespondType(responseType);
	rescheduleVO.setResourceId(resourceId);
	rescheduleVO.setRoleId(roleId);
	rescheduleVO.setFirmId(providerId);
	
	return rescheduleVO;
	}
}
