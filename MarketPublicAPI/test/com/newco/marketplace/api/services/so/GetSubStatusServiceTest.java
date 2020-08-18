package com.newco.marketplace.api.services.so;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.newco.marketplace.api.beans.substatusrespose.GetSubStatusResponse;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SoSubStatusMapper;
import com.newco.marketplace.api.validator.so.SoValidator;
import com.newco.marketplace.business.businessImpl.serviceorder.ServiceOrderMonitorBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class GetSubStatusServiceTest {
	
	private SoValidator soValidator;
	private SoSubStatusMapper substatusMapper;
	private ServiceOrderMonitorBO serviceOrderMonitorBO;
	List<String> soStausList = new ArrayList<String>();
	List<ServiceOrderStatusVO> soStatusListDB = new ArrayList<ServiceOrderStatusVO>();
	ServiceOrderStatusVO stausVO = new ServiceOrderStatusVO();

	@Before
	public void setUp() throws Exception {
		soValidator = new SoValidator();
		substatusMapper = new SoSubStatusMapper();
		serviceOrderMonitorBO = mock(ServiceOrderMonitorBO.class);
		soValidator.setServiceOrderMonitorBO(serviceOrderMonitorBO);
		soValidator.setSubstatusMapper(substatusMapper);
		soStausList.add("Posted");
		stausVO.setStatusName("Routed");
		stausVO.setStatusId(110);
		soStatusListDB.add(stausVO);
		
	}

	@Test
	public void testExecute() {
		try {
			when(serviceOrderMonitorBO.getAllSoStatus()).thenReturn(soStatusListDB);
			GetSubStatusResponse response = soValidator.validatesoSubStatus(soStausList);
			Assert.assertNotNull(response.getResults().getError());
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
