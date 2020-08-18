package com.newco.marketplace.api.mobile.services.v3_1;


import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewAdvancedDashboardResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.UserDetailsVO;

@APIResponseClass(ViewAdvancedDashboardResponse.class)
public class ViewDashboardService extends BaseService{

	private static final Logger LOGGER = Logger.getLogger(ViewDashboardService.class);

	private NewMobileGenericMapper mobileMapper;
	private IMobileGenericBO mobileBO;
	private INewMobileGenericBO newMobileBO;

	public IAPIResponse execute(APIRequestVO apiVO) {
		//Declaring Variables
		MobileDashboardVO dashboardVO  = null;
		ViewAdvancedDashboardResponse response = null;
		SODashboardVO dashboardVOForAdditionalDetails=null;
		SODashboardVO dashboardVOForStatusMonitor=null;
		List<SPNMonitorVO> SPNMonitorVOList=null;
		List<UserDetailsVO> userDetailsList = null;
		boolean viewOrderPricing = false;
		boolean viewSPN = false;
		Results results = null;
		try {
			dashboardVO = mobileMapper.mapDashBoardcountRequest(apiVO);
			dashboardVO = mobileBO.getDashboardCount(dashboardVO);
			//SL-21451: fetching bid Request count
			Integer bidRequests = null;
			if (!(MPConstants.ROLE_LEVEL_ONE.equals(dashboardVO.getRoleId()))){
				bidRequests = mobileBO.getBidRequestCount(dashboardVO);
			}
			//fetch user permission details for displaying Price info and SPN info
			//Display price info only if the user has View order Pricing Permission (59)
			//Display SPN details only of the user has SPN invitation Permission (66)
			userDetailsList = newMobileBO.getUserPermissionDetails(dashboardVO.getResourceId());
			if(null != userDetailsList && !userDetailsList.isEmpty()){
				for(UserDetailsVO userDetails: userDetailsList){
					if(null != userDetails.getActivityId()){
						if(MPConstants.VIEW_ORDER_PRICING_PERMISSION == userDetails.getActivityId().intValue()){
							viewOrderPricing = true;
						}
						if(MPConstants.SPN_INVITE_PERMISSION == userDetails.getActivityId().intValue()){
							viewSPN = true;
						}
					}
				}
			}
			
			//Fetching Additional details to the existing dashboard API
			dashboardVOForAdditionalDetails=newMobileBO.getAdditionalDetailsInDashboard(dashboardVO);
			//Fetching the details of the status monitor
			dashboardVOForStatusMonitor=newMobileBO.getApprovedUnapprovedCountsAndFirmStatus(dashboardVO.getFirmId());
			//Fetching the details of the spn monitor details
			//fetch spn details only if the user has spn invite permission
			if(viewSPN){
				SPNMonitorVOList=newMobileBO.getSpnMonitorDetails(dashboardVO.getFirmId());
			}
	
			//R16_2_0_1:SL-21266: Modifying the mapper to include Bid requests count
			response = mobileMapper.mapViewDashboardResponseOld(dashboardVO,apiVO.getRoleId(),bidRequests);
			//Mapping the Additional details from the VO class to response object
			response=mobileMapper.mapAdditionalDetailsToResponse(response,dashboardVOForAdditionalDetails,dashboardVOForStatusMonitor,SPNMonitorVOList, viewOrderPricing);
			
		} catch (Exception e) {			
			LOGGER.error("Exception in Retrieving Count of service orders"+ e.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}
		return response;
	}

	public NewMobileGenericMapper getMobileMapper() {
		return mobileMapper;
	}

	public void setMobileMapper(NewMobileGenericMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
	}

	public IMobileGenericBO getMobileBO() {
		return mobileBO;
	}

	public void setMobileBO(IMobileGenericBO mobileBO) {
		this.mobileBO = mobileBO;
	}

	public INewMobileGenericBO getNewMobileBO() {
		return newMobileBO;
	}

	public void setNewMobileBO(INewMobileGenericBO newMobileBO) {
		this.newMobileBO = newMobileBO;
	}


}
