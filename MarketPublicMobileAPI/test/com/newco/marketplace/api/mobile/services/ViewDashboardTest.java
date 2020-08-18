package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.so.viewDashboard.DashBoardCountVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewDashboardResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;

public class ViewDashboardTest {
	private MobileDashboardVO mobileDashboardVO;
	private MobileGenericMapper mapper;
	private List<DashBoardCountVO> dashBoardCountVO;
	private DashBoardCountVO activeCountVO;
	private DashBoardCountVO acceptedCountVO;
	private DashBoardCountVO problemCountVO;
	private ViewDashboardResponse response;
	
	@Before
	public void setUp() {
		mobileDashboardVO = new MobileDashboardVO();
		mapper = new MobileGenericMapper();
		dashBoardCountVO = new ArrayList<DashBoardCountVO>();
		activeCountVO= new DashBoardCountVO();
		acceptedCountVO= new DashBoardCountVO();
		problemCountVO= new DashBoardCountVO();
		response = null;
	}
	
	@Test
	public void validateViewDashboard(){
		
		try {
			activeCountVO.setTabCount(10);
			activeCountVO.setTabName("Active");
			
			acceptedCountVO.setTabCount(5);
			acceptedCountVO.setTabName("Accepted");
			
			problemCountVO.setTabCount(6);
			problemCountVO.setTabName("Problem");
			
			dashBoardCountVO.add(acceptedCountVO);
			dashBoardCountVO.add(activeCountVO);
			dashBoardCountVO.add(problemCountVO);
			
			mobileDashboardVO.setCountVO(dashBoardCountVO);
			mobileDashboardVO.setFirmId(10202);
			mobileDashboardVO.setResourceId(10254);
			mobileDashboardVO.setRoleId(3);
			
			response = mapper.mapViewDashboardResponse(mobileDashboardVO, 3);
			Assert.assertNotNull(response);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	
	}	

}
	

