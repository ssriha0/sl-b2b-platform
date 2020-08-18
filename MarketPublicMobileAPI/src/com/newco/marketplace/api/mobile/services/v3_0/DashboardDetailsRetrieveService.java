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
import com.newco.marketplace.exception.core.BusinessServiceException;

@APIResponseClass(ViewDashboardResponse.class)
public class DashboardDetailsRetrieveService extends BaseService{

	private static final Logger LOGGER = Logger.getLogger(SOGetEligibleProvidersService.class);
	private IMobileGenericBO mobileGenericBO;	
	private MobileGenericMapper mobileGenericMapper;

	public IAPIResponse execute(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		Results results = null;
		MobileDashboardVO dashboardVO  = new MobileDashboardVO();
		String firmId = apiVO.getProviderId();
		String resourceId = apiVO.getProviderResourceId().toString();		
		securityContext = getSecurityContextForVendor(Integer.parseInt(resourceId));
		ViewDashboardResponse dashboardResponse = null;
	
		try {
			//dashboardVO = mobileGenericBO.getDashboardDetails(firmId,resourceId,securityContext);
		//	dashboardResponse = mobileGenericMapper.mapViewDashboardResponse(dashboardVO);
		} catch (Exception e) {			
			LOGGER.error("Exception in getting dashboard details"+resourceId);	
			e.printStackTrace();
			results = Results.getError(e.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			dashboardResponse = new ViewDashboardResponse();
			dashboardResponse.setResults(results);
			return dashboardResponse;
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
