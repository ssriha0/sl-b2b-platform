/**
 * 
 */
package com.newco.marketplace.mobile.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;
import com.newco.marketplace.business.businessImpl.lookup.LookupBO;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.lookup.LookupDaoImpl;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.vo.mobile.v2_0.MobileSORejectVO;
import com.newco.marketplace.vo.mobile.v2_0.ResourceIds;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;

/**
 * @author Infosys
 *
 */
public class MobileRejectOrderTest {
	private MobileGenericBOImpl  mobileBO;
	private OrderManagementService  managementService;
	private LookupBO lookupBO;
	private LookupDao lookupDAO;
	private IMobileGenericDao genericDao;

	@Before
	public void setUp() {
		mobileBO = new MobileGenericBOImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		managementService = mock(OrderManagementService.class);
		lookupBO = new LookupBO();
		lookupDAO = mock(LookupDaoImpl.class);
		lookupBO.setLookupDao(lookupDAO);
		mobileBO.setLookupBO(lookupBO);
		mobileBO.setManagementService(managementService);
	}
	

	public MobileGenericBOImpl getMobileBO() {
		return mobileBO;
	}


	public void setMobileBO(MobileGenericBOImpl mobileBO) {
		this.mobileBO = mobileBO;
	}


	public LookupDao getLookupDAO() {
		return lookupDAO;
	}


	public void setLookupDAO(LookupDao lookupDAO) {
		this.lookupDAO = lookupDAO;
	}


	@Test
	public void mapCriteriaAndValidateReasonCode(){
		try {
			MobileSORejectVO mobileSORejectVO =formSOSearchRequestVO();
			mobileSORejectVO.setRoleId(2);
			mobileSORejectVO.setReasonId(10);
			LuProviderRespReasonVO luProviderRespReasonVOInput = new LuProviderRespReasonVO();
			luProviderRespReasonVOInput.setSearchByResponse(OrderConstants.PROVIDER_RESP_REJECTED);
			ArrayList<LuProviderRespReasonVO> listReasonCode = getProviderRespReason();
			when(lookupBO.getProviderRespReason(any(LuProviderRespReasonVO.class))).thenReturn(listReasonCode);
			List<ProviderResultVO> providerResultVOs = new ArrayList<ProviderResultVO>();
			ProviderResultVO resultVO = new ProviderResultVO();
			resultVO.setResourceId(10254);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(104411);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(23768);
			providerResultVOs.add(resultVO);
		//	when(managementService.getEligibleProviders(mobileSORejectVO.getFirmId(),mobileSORejectVO.getSoId())).thenReturn(providerResultVOs);
			ProcessResponse processResp = mobileBO.validateRejectRequest(mobileSORejectVO);
			Assert.assertNotNull(processResp);
			Assert.assertNotNull(processResp.getCode());
			Assert.assertEquals("2403", processResp.getCode());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void mapCriteriaAndValidateReasonComment(){
		try {
			MobileSORejectVO mobileSORejectVO =formSOSearchRequestVO();
			mobileSORejectVO.setRoleId(2);
			mobileSORejectVO.setReasonId(6);
			LuProviderRespReasonVO luProviderRespReasonVO = new LuProviderRespReasonVO();
			luProviderRespReasonVO.setSearchByResponse(OrderConstants.PROVIDER_RESP_REJECTED);
			ArrayList<LuProviderRespReasonVO> listReasonCode = getProviderRespReason();
			when(lookupBO.getProviderRespReason(any(LuProviderRespReasonVO.class))).thenReturn(listReasonCode);
			List<ProviderResultVO> providerResultVOs = new ArrayList<ProviderResultVO>();
			ProviderResultVO resultVO = new ProviderResultVO();
			resultVO.setResourceId(10254);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(104411);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(23768);
			providerResultVOs.add(resultVO);
		//	when(managementService.getEligibleProviders(mobileSORejectVO.getFirmId(),mobileSORejectVO.getSoId())).thenReturn(providerResultVOs);
			ProcessResponse processResp = mobileBO.validateRejectRequest(mobileSORejectVO);
			Assert.assertNotNull(processResp);
			Assert.assertNotNull(processResp.getCode());
			Assert.assertEquals("2405", processResp.getCode());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	@Test
	public void mapCriteriaAndValidateEmptyResIds(){
		try {
			MobileSORejectVO mobileSORejectVO =formSOSearchRequestVO();
			mobileSORejectVO.setRoleId(2);
			mobileSORejectVO.setReasonId(2);
			ResourceIds resourceIds = new ResourceIds();
			mobileSORejectVO.setResourceList(resourceIds);
			LuProviderRespReasonVO luProviderRespReasonVO = new LuProviderRespReasonVO();
			luProviderRespReasonVO.setSearchByResponse(OrderConstants.PROVIDER_RESP_REJECTED);
			ArrayList<LuProviderRespReasonVO> listReasonCode = getProviderRespReason();
			when(lookupBO.getProviderRespReason(any(LuProviderRespReasonVO.class))).thenReturn(listReasonCode);
			List<ProviderResultVO> providerResultVOs = new ArrayList<ProviderResultVO>();
			ProviderResultVO resultVO = new ProviderResultVO();
			resultVO.setResourceId(10254);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(104411);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(23768);
			providerResultVOs.add(resultVO);
		//	when(managementService.getEligibleProviders(mobileSORejectVO.getFirmId(),mobileSORejectVO.getSoId())).thenReturn(providerResultVOs);
			ProcessResponse processResp = mobileBO.validateRejectRequest(mobileSORejectVO);
			Assert.assertNotNull(processResp);
			Assert.assertNotNull(processResp.getCode());
			Assert.assertEquals("2404", processResp.getCode());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	@Test
	public void mapCriteriaAndValidateInvalidResId(){
		try {
			MobileSORejectVO mobileSORejectVO =formSOSearchRequestVO();
			mobileSORejectVO.setRoleId(2);
			mobileSORejectVO.setReasonId(2);
			LuProviderRespReasonVO luProviderRespReasonVO = new LuProviderRespReasonVO();
			luProviderRespReasonVO.setSearchByResponse(OrderConstants.PROVIDER_RESP_REJECTED);
			ArrayList<LuProviderRespReasonVO> listReasonCode = getProviderRespReason();
			when(lookupBO.getProviderRespReason(any(LuProviderRespReasonVO.class))).thenReturn(listReasonCode);
			List<ProviderResultVO> providerResultVOs = new ArrayList<ProviderResultVO>();
			ProviderResultVO resultVO = new ProviderResultVO();
			resultVO.setResourceId(10254);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(104411);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			resultVO.setResourceId(23768);
			providerResultVOs.add(resultVO);
			ProcessResponse processResp = mobileBO.validateRejectRequest(mobileSORejectVO);
			Assert.assertNotNull(processResp);
			Assert.assertNotNull(processResp.getCode());
			Assert.assertEquals("2402", processResp.getCode());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	@Test
	public void mapCriteriaAndValidateResIdInrouteList(){
		try {
			MobileSORejectVO mobileSORejectVO =formSOSearchRequestVO();
			mobileSORejectVO.setRoleId(3);
			mobileSORejectVO.setReasonId(2);
			ArrayList<LuProviderRespReasonVO> listReasonCode = getProviderRespReason();
			when(lookupBO.getProviderRespReason(any(LuProviderRespReasonVO.class))).thenReturn(listReasonCode);
			ArrayList<LuProviderRespReasonVO> luProviderRespReasonVOs = getProviderRespReason();
			List<ProviderResultVO> providerResultVOs = new ArrayList<ProviderResultVO>();
			ProviderResultVO resultVO = new ProviderResultVO();
			resultVO.setResourceId(10254);
			providerResultVOs.add(resultVO);
			resultVO = new ProviderResultVO();
			providerResultVOs.add(resultVO);
			when(managementService.getEligibleProviders(mobileSORejectVO.getFirmId(),mobileSORejectVO.getSoId())).thenReturn(providerResultVOs);
			ProcessResponse processResp = mobileBO.validateRejectRequest(mobileSORejectVO);
			Assert.assertNotNull(processResp);
			Assert.assertNotNull(processResp.getCode());
			Assert.assertEquals("2402", processResp.getCode());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private ArrayList<LuProviderRespReasonVO> getProviderRespReason() {
		ArrayList<LuProviderRespReasonVO> luProviderRespReasonVOs = new ArrayList<LuProviderRespReasonVO>();
		LuProviderRespReasonVO luProviderRespReasonVO = new LuProviderRespReasonVO();
		luProviderRespReasonVO.setRespReasonId(1);
		luProviderRespReasonVO.setDescr("Spend limit too low");
		luProviderRespReasonVOs.add(luProviderRespReasonVO);
		luProviderRespReasonVO = new LuProviderRespReasonVO();
		luProviderRespReasonVO.setRespReasonId(2);
		luProviderRespReasonVO.setDescr("Spend limit does not match scope");
		luProviderRespReasonVOs.add(luProviderRespReasonVO);
		luProviderRespReasonVO = new LuProviderRespReasonVO();
		luProviderRespReasonVO.setRespReasonId(3);
		luProviderRespReasonVO.setDescr("Service scope is unclear");
		luProviderRespReasonVOs.add(luProviderRespReasonVO);
		luProviderRespReasonVO = new LuProviderRespReasonVO();
		luProviderRespReasonVO.setRespReasonId(4);
		luProviderRespReasonVO.setDescr("Service location too far");
		luProviderRespReasonVOs.add(luProviderRespReasonVO);
		luProviderRespReasonVO = new LuProviderRespReasonVO();
		luProviderRespReasonVO.setRespReasonId(5);
		luProviderRespReasonVO.setDescr("Schedule conflict");
		luProviderRespReasonVOs.add(luProviderRespReasonVO);
		luProviderRespReasonVO = new LuProviderRespReasonVO();
		luProviderRespReasonVO.setRespReasonId(6);
		luProviderRespReasonVO.setDescr("Other");
		luProviderRespReasonVOs.add(luProviderRespReasonVO);
		return luProviderRespReasonVOs;
	}

	private MobileSORejectVO formSOSearchRequestVO() {
		MobileSORejectVO mobileSORejectVO = new MobileSORejectVO();
		mobileSORejectVO.setFirmId("10202");
		mobileSORejectVO.setReleaseByFirmInd(true);
		mobileSORejectVO.setSoId("546-6942-7903-66");
		List<Integer> resourceList = new ArrayList<Integer>();
		resourceList.add(10254);
		resourceList.add(104411);
		ResourceIds resourceIds = new ResourceIds();
		resourceIds.setResourceId(resourceList);
		mobileSORejectVO.setResourceList(resourceIds);
		return mobileSORejectVO;
	}

}
