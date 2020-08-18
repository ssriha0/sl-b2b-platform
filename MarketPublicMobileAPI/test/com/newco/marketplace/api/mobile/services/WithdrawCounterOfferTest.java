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

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferResponse;
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
public class WithdrawCounterOfferTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	private MobileGenericBOImpl  mobileBO;
	private ServiceOrderDaoImpl soGenericDao;
	private ServiceOrderBO serviceOrderBo;
	String soId;
	ServiceOrder so;
	WithdrawCounterOfferResponse response ;
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
		   
		  response = new WithdrawCounterOfferResponse();
		   results = null;
		
	}
	
	@Test
	public void mapCounterOfferInvalidRoleErrorTest() {
		so = new ServiceOrder();
		CounterOfferVO vo = formCounterOfferVONoError(soId,10254,"10202",1);
		Assert.assertNotNull(vo);
		Assert.assertEquals("10202", vo.getFirmId());
		try {
			response = validator.validateWithdrawCounterOffer(vo, response);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResults());		
		Assert.assertNotNull(response.getResults().getError());		
		Assert.assertNotNull(response.getResults().getError().get(0));	
		Assert.assertNotNull(response.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3044",response.getResults().getError().get(0).getCode());	
	}
	
	@Test
	public void mapCounterOfferInvalidWfStateErrorTest() {
		so = new ServiceOrder();
		so.setWfStateId(150);
		CounterOfferVO vo = formCounterOfferVONoError(soId,10254,"10202",3);
		Assert.assertNotNull(vo);
		Assert.assertEquals("10202", vo.getFirmId());
		try {
			response = validator.validateWithdrawCounterOffer(vo, response);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResults());		
		Assert.assertNotNull(response.getResults().getError());		
		Assert.assertNotNull(response.getResults().getError().get(0));	
		Assert.assertNotNull(response.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3020",response.getResults().getError().get(0).getCode());	
	}
	
	private CounterOfferVO formCounterOfferVONoError(String soId,Integer providerResourceId, String providerId,Integer roleId) {
		List<Integer> resourceId = new ArrayList<Integer>();
		CounterOfferResources resourceIds = new CounterOfferResources();
		resourceId.add(23768);
		resourceId.add(104411);
		resourceIds.setResourceId(resourceId);
		CounterOfferVO offerVO = new CounterOfferVO();
		offerVO.setSoId(soId);
		offerVO.setSo(so);
		offerVO.setProviderResourceId(providerResourceId);
		offerVO.setResourceIds(resourceIds);
		offerVO.setFirmId(providerId);
		offerVO.setRoleId(roleId);
		return offerVO;
	}
	
	
}
