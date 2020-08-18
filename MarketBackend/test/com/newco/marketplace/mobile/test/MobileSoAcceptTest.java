package com.newco.marketplace.mobile.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class MobileSoAcceptTest {
	private static final Logger LOGGER = Logger.getLogger(MobileSoAcceptTest.class);
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	private IServiceOrderBO serviceOrderBo;
	AcceptVO acceptVo;
	ResultsCode validationCode;
	ProcessResponse processResponse;
	@Before
	public void setUp() {
		mobileBO = mock(MobileGenericBOImpl.class);
		genericDao =mock(MobileGenericDaoImpl.class);
		serviceOrderBo = mock(ServiceOrderBO.class);
		mobileBO.setMobileGenericDao(genericDao);
		mobileBO.setServiceOrderBo(serviceOrderBo);
		acceptVo = new AcceptVO();
		acceptVo.setFirmId(10202);
		acceptVo.setRoleId(2);
		acceptVo.setResourceIdRequest(10254);
		acceptVo.setResourceIdUrl(10254);
		acceptVo.setSoId("503-3453-4567-34");
		validationCode = ResultsCode.SUCCESS;
	}
	
	@Test
	public void validateSoAccept(){
		ResultsCode result =null;
		try {
			//Mocking all methods
			when(genericDao.getServiceOrderStatus(acceptVo.getSoId())).thenReturn(110);
			when(serviceOrderBo.determineAcceptabilityForMobile(true, null, 110)).thenReturn(true);
			when(mobileBO.determineAcceptablity(acceptVo.getSoId(), acceptVo.getGroupId())).thenReturn(true);
			when(mobileBO.validateRoleLevelAcceptance(acceptVo)).thenReturn(validationCode);
			when( mobileBO.validateSoAccept(acceptVo)).thenReturn(validationCode);
			result = mobileBO.validateSoAccept(acceptVo);
		} catch (Exception e) {
			LOGGER.error("Exception in Junit Execution for Accept Servioce Order"+ e.getMessage());
		}
		Assert.assertEquals("Validation for So Accept failed", "0000", result.getCode());
	}
}
