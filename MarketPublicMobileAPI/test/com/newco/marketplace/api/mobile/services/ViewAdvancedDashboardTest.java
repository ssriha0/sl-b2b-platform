package com.newco.marketplace.api.mobile.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.so.viewDashboard.DashBoardCountVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.viewDashboard.LeadDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderBackgroundDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderRegistrationDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ServiceLiveStatusMonitor;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnBuyerDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.Tab;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewAdvancedDashboardResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;

public class ViewAdvancedDashboardTest {
	private MobileDashboardVO mobileDashboardVO;
	private MobileDashboardVO mobileDashboardVO1;
	private APIRequestVO apiVO;
	private NewMobileGenericMapper mapper;
	private IMobileGenericBO mobileBO;
	private INewMobileGenericBO newMobileBO;

	DashBoardCountVO countVO = null;
	private ViewAdvancedDashboardResponse response = new ViewAdvancedDashboardResponse();

	@Before
	public void setUp() {
		mobileDashboardVO = null;
		apiVO = new APIRequestVO(null);
		apiVO.setRoleId(2);
		apiVO.setProviderId("10202");
		apiVO.setProviderResourceId(10254);
		mapper = new NewMobileGenericMapper();

		List<DashBoardCountVO> countVOList = populateCountVOList();
		mobileDashboardVO1 = new MobileDashboardVO();
		mobileDashboardVO1.setCountVO(countVOList);

		mobileBO =mock(MobileGenericBOImpl.class);
		newMobileBO = mock(NewMobileGenericBOImpl.class);

		SODashboardVO dashboardVOForAdditionalDetails = getMockDashboardVOForAdditionalDetails();

		SODashboardVO dashboardVOForStatusMonitor = getMockDashboardVOForStatusMonitor();
		
		List<SPNMonitorVO> SPNMonitorVOList = getMockSPNMonitorDetails();
		try {
			when(mobileBO.getDashboardCount(mobileDashboardVO)).thenReturn(mobileDashboardVO1);
			when(newMobileBO.getAdditionalDetailsInDashboard(mobileDashboardVO)).thenReturn(dashboardVOForAdditionalDetails);
			when(newMobileBO.getApprovedUnapprovedCountsAndFirmStatus(10202)).thenReturn(dashboardVOForStatusMonitor);
			when(newMobileBO.getSpnMonitorDetails(10202)).thenReturn(SPNMonitorVOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<DashBoardCountVO>  populateCountVOList(){
		List<DashBoardCountVO> countVOList = new ArrayList<DashBoardCountVO>();
		countVO = new DashBoardCountVO();
		countVO.setTabName("Received");
		countVO.setTabCount(84);
		countVOList.add(countVO);

		countVO = new DashBoardCountVO();
		countVO.setTabName("Accepted");
		countVO.setTabCount(53);
		countVOList.add(countVO);

		countVO = new DashBoardCountVO();
		countVO.setTabName("Active");
		countVO.setTabCount(9);
		countVOList.add(countVO);

		countVO = new DashBoardCountVO();
		countVO.setTabName("Problem");
		countVO.setTabCount(3);
		countVOList.add(countVO);
		return countVOList;
	}

	private SODashboardVO getMockDashboardVOForAdditionalDetails(){

		SODashboardVO dashboardVO = new SODashboardVO();
		dashboardVO.setStatusNew(10);
		dashboardVO.setWorking(101);
		dashboardVO.setScheduled(121);
		dashboardVO.setCompleted(1);
		dashboardVO.setCancelled(19);
		dashboardVO.setStale(9);

		dashboardVO.setAvailableBalance(new Double(10.78));

		dashboardVO.setNumRatingsReceived(10);
		dashboardVO.setCurrentRating(new Double(4.77));
		dashboardVO.setLifetimeRating(new Double(3.76));

		return dashboardVO;
	}	

	private SODashboardVO getMockDashboardVOForStatusMonitor(){

		SODashboardVO dashboardVO = new SODashboardVO();
		dashboardVO.setNumTechniciansApproved(21);
		dashboardVO.setNumTechniciansUnapproved(13);
		dashboardVO.setFirmStatus("ServiceLive Approved");

		dashboardVO.setBcClear(18);
		dashboardVO.setBcInProcess(3);
		dashboardVO.setBcPendingSubmission(1);
		dashboardVO.setBcNotCleared(5);
		dashboardVO.setBcRecertificationDue(1);
		dashboardVO.setBcNotStarted(3);

		return dashboardVO;
	}
	
	private List<SPNMonitorVO>  getMockSPNMonitorDetails(){
		List<SPNMonitorVO> spnMonitorVOList = new ArrayList<SPNMonitorVO>();
		SPNMonitorVO monitorVO;
		
		monitorVO = new SPNMonitorVO();
		monitorVO.setSpnId(1);
		monitorVO.setSpnName("Test SPN 1");
		monitorVO.setBuyerId(1000);
		monitorVO.setBuyerName("First Buyer");
		monitorVO.setMembershipStatus("Member");
		monitorVO.setMessage("SPN Member");
		spnMonitorVOList.add(monitorVO);
		
		monitorVO = new SPNMonitorVO();
		monitorVO.setSpnId(2);
		monitorVO.setSpnName("Test SPN 2");
		monitorVO.setBuyerId(1002);
		monitorVO.setBuyerName("Second Buyer");
		monitorVO.setMembershipStatus("Membership Inactive");
		monitorVO.setMessage("Firm Out Of Compliancer");
		spnMonitorVOList.add(monitorVO);
		
		monitorVO = new SPNMonitorVO();
		monitorVO.setSpnId(3);
		monitorVO.setSpnName("Test SPN 3");
		monitorVO.setBuyerId(1003);
		monitorVO.setBuyerName("Third Buyer");
		monitorVO.setMembershipStatus("Pending Approval");
		monitorVO.setMessage("SPN Applicant");
		spnMonitorVOList.add(monitorVO);
		
		monitorVO = new SPNMonitorVO();
		monitorVO.setSpnId(4);
		monitorVO.setSpnName("Test SPN 4");
		monitorVO.setBuyerId(1000);
		monitorVO.setBuyerName("First Buyer");
		monitorVO.setMembershipStatus("Membership Inactive");
		monitorVO.setMessage("Firm Out Of Compliance");
		spnMonitorVOList.add(monitorVO);
		
		return spnMonitorVOList;
	}
	@Test
	public void testMapApiVOToMobileDashboardVO(){
		try {
			mobileDashboardVO = mapper.mapDashBoardcountRequest(apiVO);
			Assert.assertNotNull(mobileDashboardVO);
			Assert.assertEquals(mobileDashboardVO.getFirmId(), 10202);
			Assert.assertEquals(mobileDashboardVO.getResourceId(), 10254);
			Assert.assertEquals(mobileDashboardVO.getRoleId(), 2);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@Test
	public void testGetDashboardCountMethod(){
		try {

			mobileDashboardVO1 = mobileBO.getDashboardCount(mobileDashboardVO);
			Assert.assertNotNull(mobileDashboardVO1.getCountVO());
			Assert.assertEquals(mobileDashboardVO1.getCountVO().size(), 4);
			for(DashBoardCountVO dashBoardCountVO: mobileDashboardVO1.getCountVO()){
				if(dashBoardCountVO.getTabName().equals("Received")){
					Assert.assertEquals(dashBoardCountVO.getTabCount(), 84);
				}
				if(dashBoardCountVO.getTabName().equals("Accepted")){
					Assert.assertEquals(dashBoardCountVO.getTabCount(), 53);
				}
				if(dashBoardCountVO.getTabName().equals("Active")){
					Assert.assertEquals(dashBoardCountVO.getTabCount(), 9);
				}
				if(dashBoardCountVO.getTabName().equals("Problem")){
					Assert.assertEquals(dashBoardCountVO.getTabCount(), 3);
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}


	@Test
	public void testGetDashboardTabCount(){

		try {
			mobileDashboardVO = mapper.mapDashBoardcountRequest(apiVO);
			Assert.assertNotNull(mobileDashboardVO);
			Assert.assertEquals(mobileDashboardVO.getFirmId(), 10202);
			Assert.assertEquals(mobileDashboardVO.getResourceId(), 10254);
			Assert.assertEquals(mobileDashboardVO.getRoleId(), 2);

			mobileDashboardVO1 = mobileBO.getDashboardCount(null);
			Assert.assertNotNull(mobileDashboardVO1.getCountVO());
			Assert.assertEquals(mobileDashboardVO1.getCountVO().size(), 4);

			mobileDashboardVO.setCountVO(mobileDashboardVO1.getCountVO());
			response = mapper.mapViewDashboardResponseOld(mobileDashboardVO,apiVO.getRoleId(), null);

			List<Tab> tabList = response.getTabs().getTab();
			for(Tab tab: tabList){
				if(tab.getTabName().equals("Received")){
					Assert.assertEquals(tab.getTabCount(), 84);
				}
				if(tab.getTabName().equals("Accepted")){
					Assert.assertEquals(tab.getTabCount(), 53);
				}
				if(tab.getTabName().equals("Active")){
					Assert.assertEquals(tab.getTabCount(), 9);
				}
				if(tab.getTabName().equals("Problem")){
					Assert.assertEquals(tab.getTabCount(), 3);
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}


	@Test
	public void testAdditionalDetailsInDashboard(){
		try {
			SODashboardVO dashboardVOForAdditionalDetails = newMobileBO.getAdditionalDetailsInDashboard(null);
			response =mapper.mapAdditionalDetailsToResponse(response,dashboardVOForAdditionalDetails,null,null,true);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getLeadOrderStatistics());
			Assert.assertNotNull(response.getLeadOrderStatistics().getLeadDetails());
			Assert.assertNotNull(response.getLeadOrderStatistics().getLeadDetails().getLeadDetail());
			Assert.assertEquals(6,response.getLeadOrderStatistics().getLeadDetails().getLeadDetail().size());
			List<LeadDetail> leadDetailList = response.getLeadOrderStatistics().getLeadDetails().getLeadDetail();

			for(LeadDetail leadDetail: leadDetailList){
				if(leadDetail.getLeadOrderStatus().equals("New")){
					Assert.assertEquals(leadDetail.getLeadOrderCount(), 10);
				}
				if(leadDetail.getLeadOrderStatus().equals("Working")){
					Assert.assertEquals(leadDetail.getLeadOrderCount(), 101);
				}

				if(leadDetail.getLeadOrderStatus().equals("Scheduled")){
					Assert.assertEquals(leadDetail.getLeadOrderCount(), 121);
				}
				if(leadDetail.getLeadOrderStatus().equals("Completed")){
					Assert.assertEquals(leadDetail.getLeadOrderCount(), 1);
				}
				if(leadDetail.getLeadOrderStatus().equals("Cancelled")){
					Assert.assertEquals(leadDetail.getLeadOrderCount(), 19);
				}
				if(leadDetail.getLeadOrderStatus().equals("Stale")){
					Assert.assertEquals(leadDetail.getLeadOrderCount(), 9);
				}
			}

			Assert.assertEquals(response.getAvailableWalletBalance(), "$10.78");
			Assert.assertNotNull(response.getPerformanceStatistics());
			Assert.assertNotNull(response.getPerformanceStatistics().getNumberOfRatingsReceived());
			Assert.assertNotNull(response.getPerformanceStatistics().getCurrentRating());
			Assert.assertNotNull(response.getPerformanceStatistics().getLifetimeRating());


			Assert.assertEquals(response.getPerformanceStatistics().getNumberOfRatingsReceived(), 10);
			Assert.assertEquals(response.getPerformanceStatistics().getCurrentRating(), 4.77);
			Assert.assertEquals(response.getPerformanceStatistics().getLifetimeRating(), 3.76);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testStatusMonitorInDashboard(){
		SODashboardVO dashboardVOForStatusMonitor;
		try {
			dashboardVOForStatusMonitor = newMobileBO.getApprovedUnapprovedCountsAndFirmStatus(10202);
			response =mapper.mapAdditionalDetailsToResponse(response,null,dashboardVOForStatusMonitor,null,true);

			ServiceLiveStatusMonitor serviceLiveStatusMonitor=response.getServiceLiveStatusMonitor();
			Assert.assertEquals(serviceLiveStatusMonitor.getFirmRegistrationStatus(), "ServiceLive Approved");

			Assert.assertNotNull(serviceLiveStatusMonitor.getProviderRegistrationStatus());
			Assert.assertNotNull(serviceLiveStatusMonitor.getProviderRegistrationStatus().getProviderRegistrationDetails());
			Assert.assertNotNull(serviceLiveStatusMonitor.getProviderRegistrationStatus().getProviderRegistrationDetails().getProviderRegistrationDetail());
			List<ProviderRegistrationDetail> providerRegistrationDetailList=serviceLiveStatusMonitor.getProviderRegistrationStatus().
					getProviderRegistrationDetails().getProviderRegistrationDetail();
			for(ProviderRegistrationDetail providerRegistrationDetail:providerRegistrationDetailList){
				if(providerRegistrationDetail.getRegistrationStatus().equals(MPConstants.PROVIDER_REGISTRATION_STATUS_APPROVED)){
					Assert.assertEquals(providerRegistrationDetail.getRegistrationStatusCount(), 21);
				}
				if(providerRegistrationDetail.getRegistrationStatus().equals(MPConstants.PROVIDER_REGISTRATION_STATUS_UNAPPROVED)){
					Assert.assertEquals(providerRegistrationDetail.getRegistrationStatusCount(), 13);
				}
			}

			Assert.assertNotNull(serviceLiveStatusMonitor.getProviderBackgroundCheck());
			Assert.assertNotNull(serviceLiveStatusMonitor.getProviderBackgroundCheck().getProviderBackgroundDetails());
			Assert.assertNotNull(serviceLiveStatusMonitor.getProviderBackgroundCheck().getProviderBackgroundDetails().getProviderBackgroundDetail());

			List<ProviderBackgroundDetail> providerBackgroundDetailList=serviceLiveStatusMonitor.getProviderBackgroundCheck().
					getProviderBackgroundDetails().getProviderBackgroundDetail();

			for(ProviderBackgroundDetail providerBackgroundDetail:providerBackgroundDetailList){
				if(providerBackgroundDetail.getBackgroundStatus().equals(MPConstants.SL_STATUS_NOT_STARTED)){
					Assert.assertEquals(providerBackgroundDetail.getBackgroundStatusCount(), 3);
				}
				if(providerBackgroundDetail.getBackgroundStatus().equals(MPConstants.SL_STATUS_PENDING_SUBMISSION)){
					Assert.assertEquals(providerBackgroundDetail.getBackgroundStatusCount(), 1);
				}
				if(providerBackgroundDetail.getBackgroundStatus().equals(MPConstants.SL_STATUS_IN_PROGRESS)){
					Assert.assertEquals(providerBackgroundDetail.getBackgroundStatusCount(), 3);
				}
				if(providerBackgroundDetail.getBackgroundStatus().equals(MPConstants.SL_STATUS_NOT_CLEARED)){
					Assert.assertEquals(providerBackgroundDetail.getBackgroundStatusCount(), 5);
				}
				if(providerBackgroundDetail.getBackgroundStatus().equals(MPConstants.SL_STATUS_CLEAR)){
					Assert.assertEquals(providerBackgroundDetail.getBackgroundStatusCount(), 18);
				}
				if(providerBackgroundDetail.getBackgroundStatus().equals(MPConstants.SL_STATUS_RE_CERTIFICATION_DUE)){
					Assert.assertEquals(providerBackgroundDetail.getBackgroundStatusCount(), 1);
				}
			}
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSpnMonitorDetailsInDashboard(){
		try {
			List<SPNMonitorVO> SPNMonitorVOList = newMobileBO.getSpnMonitorDetails(10202);
			response =mapper.mapAdditionalDetailsToResponse(response,null,null,SPNMonitorVOList,true);
			
			Assert.assertNotNull(response.getSpnMonitor());
			Assert.assertNotNull(response.getSpnMonitor().getSpnBuyerDetailsList());
			Assert.assertNotNull(response.getSpnMonitor().getSpnBuyerDetailsList().getSpnBuyerDetail());
			
			List<SpnBuyerDetail> spnBuyerDetailList = response.getSpnMonitor().getSpnBuyerDetailsList().getSpnBuyerDetail();
			
			for(SpnBuyerDetail spnBuyerDetail: spnBuyerDetailList){
				Assert.assertNotNull(spnBuyerDetail.getBuyerId());
				Assert.assertNotNull(spnBuyerDetail.getBuyerName());
				
				if(spnBuyerDetail.getBuyerId().equals(1000)){
					Assert.assertEquals(spnBuyerDetail.getBuyerName(), "First Buyer");
					Assert.assertNotNull(spnBuyerDetail.getSpnDetails());
					Assert.assertNotNull(spnBuyerDetail.getSpnDetails().getSpnDetail());
					Assert.assertEquals(spnBuyerDetail.getSpnDetails().getSpnDetail().size(), 2);
					List<SpnDetail> spnDetailList = spnBuyerDetail.getSpnDetails().getSpnDetail();
					
					for(SpnDetail spnDetail: spnDetailList){
						if(spnDetail.getSpnId().equals(1)){
							Assert.assertEquals(spnDetail.getSpnName(), "Test SPN 1");
							Assert.assertEquals(spnDetail.getSpnMembershipStatus(), "Member");
						}else if(spnDetail.getSpnId().equals(4)){
							Assert.assertEquals(spnDetail.getSpnName(), "Test SPN 4");
							Assert.assertEquals(spnDetail.getSpnMembershipStatus(), "Membership Inactive");
						}else{
							Assert.assertTrue(false);
						}
					}
					
				}else if(spnBuyerDetail.getBuyerId().equals(1002)){
					Assert.assertEquals(spnBuyerDetail.getBuyerName(), "Second Buyer");
					Assert.assertNotNull(spnBuyerDetail.getSpnDetails());
					Assert.assertNotNull(spnBuyerDetail.getSpnDetails().getSpnDetail());
					Assert.assertEquals(spnBuyerDetail.getSpnDetails().getSpnDetail().size(), 1);
					List<SpnDetail> spnDetailList = spnBuyerDetail.getSpnDetails().getSpnDetail();
					
					for(SpnDetail spnDetail: spnDetailList){
						if(spnDetail.getSpnId().equals(2)){
							Assert.assertEquals(spnDetail.getSpnName(), "Test SPN 2");
							Assert.assertEquals(spnDetail.getSpnMembershipStatus(), "Membership Inactive");
						}else{
							Assert.assertTrue(false);
						}
						
					}
				}else if(spnBuyerDetail.getBuyerId().equals(1003)){
					Assert.assertEquals(spnBuyerDetail.getBuyerName(), "Third Buyer");
					Assert.assertNotNull(spnBuyerDetail.getSpnDetails());
					Assert.assertNotNull(spnBuyerDetail.getSpnDetails().getSpnDetail());
					Assert.assertEquals(spnBuyerDetail.getSpnDetails().getSpnDetail().size(), 1);
					List<SpnDetail> spnDetailList = spnBuyerDetail.getSpnDetails().getSpnDetail();
					
					for(SpnDetail spnDetail: spnDetailList){
						if(spnDetail.getSpnId().equals(3)){
							Assert.assertEquals(spnDetail.getSpnName(), "Test SPN 3");
							Assert.assertEquals(spnDetail.getSpnMembershipStatus(), "Pending Approval");
						}else{
							Assert.assertTrue(false);
						}
					}
				}
			}
			
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
	}
}


