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

import org.apache.log4j.Logger;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
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
import com.sun.jmx.snmp.Timestamp;
/**
 * @author Infosys
 *
 */
public class CounterOfferTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private MobileGenericBOImpl  mobileBO;
	private ServiceOrderDaoImpl soGenericDao;
	private ServiceOrderBO serviceOrderBo;
	String soId;
	ServiceOrder so;
	CounterOfferResponse response ;
	Results results;
	
	@Before
	public void setUp() {
		serviceOrderBo = new ServiceOrderBO();
		validator = new MobileGenericValidator();
		mobileBO = new MobileGenericBOImpl();
		mapper = new MobileGenericMapper();
		validator.setMobileGenericBO(mobileBO);
		soGenericDao = new ServiceOrderDaoImpl();
		soGenericDao =mock(ServiceOrderDaoImpl.class);
		mobileBO.setServiceOrderBo(serviceOrderBo);
		serviceOrderBo.setServiceOrderDao(soGenericDao);
		
		 soId="555-6444-4185-52";
		  so = new ServiceOrder(); 
		  response = new CounterOfferResponse();
		   results = null;
		
	}
	@Test
	public void mapCounterOfferNoErrorTest() {
		
		CounterOfferRequest request = formCounterOfferRequestNoError();
		CounterOfferVO vo = mapper.mapCounterOfferRequest(request, soId ,so,23768,"10202", 3);
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals("10202", vo.getFirmId());
		Assert.assertEquals("300", vo.getSpendLimit());
		Assert.assertEquals("2016-04-27T22:19:26", vo.getOfferExpirationDate());
		
		when(soGenericDao.checkNonFunded(soId)).thenReturn(false);
		when(soGenericDao.checkNonFunded(soId)).thenReturn(false);
		
		try {
			response = validator.validateCounterOffer(vo, response);
		} catch (BusinessServiceException e) {
			e.getMessage();
		}
			Assert.assertNotNull(response);
			//Assert.assertNull(response.getResults());
	}
	
	
	@Test
	public void mapCounterOfferInvalidSpendLimitTest() {
		
		CounterOfferRequest request = formCounterOfferRequestInvalidSpendLimit();
		CounterOfferVO vo = mapper.mapCounterOfferRequest(request, soId ,so,23768,"10202", 3);
		Assert.assertNotNull(vo);
		
		when(soGenericDao.checkNonFunded(soId)).thenReturn(false);
		when(soGenericDao.checkNonFunded(soId)).thenReturn(false);
		
		try {
			response = validator.validateCounterOffer(vo, response);
		} catch (BusinessServiceException e) {
			e.getMessage();
		}
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults());		
			Assert.assertNotNull(response.getResults().getError());		
			Assert.assertNotNull(response.getResults().getError().get(0));	
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());	
			//Assert.assertEquals("3009",response.getResults().getError().get(0).getCode());	
	}
	
	private CounterOfferRequest formCounterOfferRequestNoError() {
		List<Integer> reasonCode = new ArrayList<Integer>();
		OfferReasonCodes reasonCodes = new OfferReasonCodes();
		reasonCode.add(1);
		reasonCode.add(2);
		reasonCodes.setReasonCode(reasonCode);
		CounterOfferRequest counterOfferRequest = new CounterOfferRequest();
		counterOfferRequest.setReasonCodes(reasonCodes);
		counterOfferRequest.setServiceDateTime1("2016-04-27T22:19:26");
		counterOfferRequest.setServiceDateTime2("2017-04-27T22:19:26");
		counterOfferRequest.setOfferExpirationDate("2016-04-27T22:19:26");
		counterOfferRequest.setSpendLimit("300");
		return counterOfferRequest;
	}
	
	private CounterOfferRequest formCounterOfferRequestInvalidStartDate() {
		List<Integer> reasonCode = new ArrayList<Integer>();
		OfferReasonCodes reasonCodes = new OfferReasonCodes();
		reasonCode.add(1);
		reasonCode.add(2);
		reasonCodes.setReasonCode(reasonCode);
		CounterOfferRequest counterOfferRequest = new CounterOfferRequest();
		counterOfferRequest.setReasonCodes(reasonCodes);
		counterOfferRequest.setServiceDateTime1(null);
		counterOfferRequest.setServiceDateTime2("2017-04-27T22:19:26");
		counterOfferRequest.setOfferExpirationDate("2016-04-27T22:19:26");
		counterOfferRequest.setSpendLimit("300");
		return counterOfferRequest;
	}
	
	
	private CounterOfferRequest formCounterOfferRequestInvalidSpendLimit() {
		List<Integer> reasonCode = new ArrayList<Integer>();
		OfferReasonCodes reasonCodes = new OfferReasonCodes();
		reasonCode.add(1);
		reasonCode.add(2);
		reasonCodes.setReasonCode(reasonCode);
		CounterOfferRequest counterOfferRequest = new CounterOfferRequest();
		counterOfferRequest.setReasonCodes(reasonCodes);
		counterOfferRequest.setServiceDateTime1("2016-04-27T22:19:26");
		counterOfferRequest.setServiceDateTime2("2017-04-27T22:19:26");
		counterOfferRequest.setOfferExpirationDate("2016-04-27T22:19:26");
		counterOfferRequest.setSpendLimit("300abc");
		return counterOfferRequest;
	}
}
