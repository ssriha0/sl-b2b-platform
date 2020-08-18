package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.businessImpl.mobile.MobileSOManagementBOImpl;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;

public class GetSODetailsProTest {
	private MobileGenericValidator validator;
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	private IMobileSOManagementBO mobileSOManagementBO;
	private SOGetMobileDetailsResponse response;
	private SoDetailsVO detailsVO;
	String soId;
	Integer firmId;
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mobileBO = new MobileGenericBOImpl();
		mobileSOManagementBO = new MobileSOManagementBOImpl();
		validator.setMobileGenericBO(mobileBO);
		genericDao = new MobileGenericDaoImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileSOManagementBO=mock(MobileSOManagementBOImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		mobileBO.setMobileSOManagementBO(mobileSOManagementBO);
		detailsVO= new SoDetailsVO();			
		soId="555-6444-4185-52";
		firmId=10202;
	}
	@Test
	public void validateRolePermission(){
		response = new SOGetMobileDetailsResponse();
		detailsVO.setFirmId(firmId.toString());
		detailsVO.setProviderId(23768);
		detailsVO.setRoleId(2);
		detailsVO.setSoId(soId);
		try {
			when(mobileBO.validateProviderId(detailsVO.getProviderId())).thenReturn(firmId);
			when(mobileSOManagementBO.validateProviderId(detailsVO.getProviderId().toString())).thenReturn(firmId);
		    when(genericDao.getServiceOrderStatus(detailsVO.getSoId())).thenReturn(150);		
			when(mobileBO.isAuthorizedToViewSODetails(detailsVO)).thenReturn(false);
			response = validator.validateSODetails(detailsVO);
			Assert.assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
}
