package com.newco.marketplace.api.mobile.services.v3_0;


import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewDashboardResponse;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;

@APIResponseClass(ViewDashboardResponse.class)
public class DashboardRetrieveCountService extends BaseService{

	private static final Logger LOGGER = Logger.getLogger(SOGetEligibleProvidersService.class);
	private IMobileGenericBO mobileGenericBO;	
	private MobileGenericMapper mobileGenericMapper;

	public IAPIResponse execute(APIRequestVO apiVO) {
		//Declaring Variables
		MobileDashboardVO dashboardVO  = null;
		ViewDashboardResponse dashboardResponse = null;
		try {
			dashboardVO = mobileGenericMapper.mapDashBoardcountRequest(apiVO);
			dashboardVO = mobileGenericBO.getDashboardCount(dashboardVO);
			dashboardResponse = mobileGenericMapper.mapViewDashboardResponse(dashboardVO,apiVO.getRoleId());
		} catch (Exception e) {			
			LOGGER.error("Exception in Retrieving Count of service orders"+ e.getMessage());
			dashboardResponse =mobileGenericMapper.createErrorResponseForViewDashBoard(null);
		}
		return dashboardResponse;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	

}
