package com.newco.marketplace.business.iBusiness.mobile;

import java.util.List;

import com.newco.marketplace.api.beans.so.updateTimeWindow.UpdateTimeWindowVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.UserDetailsVO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.TeamMemberDocumentVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;


/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * BO layer for Mobile phase v3.1 API's
 */
public interface INewMobileGenericBO {

	/**
	 * @Description: Method to validate the status of the soId
	 * @param soId
	 * @param action
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateServiceOrderStatus(String soId, String action) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the provider list based on soId and firmId
	 * @param soId
	 * @return List<Integer>
	 * 
	 */
	List<Integer> fetchProviderList(String soId, String firmId) ;
	/**
	 * @Description: Method to fetch the assignmenttype and status of the soId
	 * @param soId
	 * @return MobileSOReleaseVO
	 * @throws BusinessServiceException
	 */
	public MobileSOReleaseVO fetchAssignmentTypeAndStatus(MobileSOReleaseVO releaseRequestVO) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the description of the reason code
	 * @param reasonCode
	 * @return
	 * @throws BusinessServiceException
	 */
	public String fetchReasonDesc(String reasonCode) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the company details
	 * @param providerId
	 * @return String
	 * @throws BusinessServiceException
	 */
	public CompanyProfileVO getCompleteProfile(String providerId) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch service date and time of an soId
	 * @param soId
	 * @param timeWindowVO
	 * @return UpdateTimeWindowVO
	 * @throws BusinessServiceException
	 */
	public UpdateTimeWindowVO fetchServiceDatesAndTimeWndw(String soId, UpdateTimeWindowVO timeWindowVO) throws BusinessServiceException;

	/**
	 * @Description: Method to update the time window
	 * @param scheduleVO
	 * @param hisVO
	 * @throws BusinessServiceException
	 */
	public void updateTimeWindow(UpdateScheduleVO scheduleVO, ProviderHistoryVO hisVO) throws BusinessServiceException;


	/**
	 * @Description:Method to fetch the the resource activity status to check whether any resource is under this vendor
	 * @param vendorId
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	public boolean isResourceActive(String vendorId)throws BusinessServiceException;

	/**
	 * @Description:Method to check for the Manage User Profile permission
	 * @param providerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean isManageTeamPermission(Integer resourceId) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the team member list
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	public List <TeamMemberVO> getTeamMemberList(String vendorId)throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the documentId list 
	 * @param teamMemberVOList
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<TeamMemberDocumentVO> getDocumentIdList(List<Integer> resourceIdList) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the assignment and schedule details of an soId
	 * @param soId
	 * @return ServiceOrder
	 * @throws BusinessServiceException
	 */
	public ServiceOrder getServiceOrderAssignmentAndScheduleDetails(String soId) throws BusinessServiceException;

	/**
	 * @Description:Method to validate the status of the schedule
	 * @param soId
	 * @param action
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateScheduleStatus(String soId, String action) throws BusinessServiceException;

	/**
	 * @Description:Method to check whether the appointment is scheduled within 3 days 
	 * @param soId
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public boolean isAppoinmentIn3Day(String soId) throws BusinessServiceException;

	/**
	 * @Description: Method to update the schedule details
	 * @param updateScheduleVO
	 * @throws BusinessServiceException
	 */
	public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO)throws BusinessServiceException;

	/**
	 * @Description:Method to fetch the additional details in the dashboard i.e. performance statistics,spn details,lead order details,available balance etc.
	 * @param dashboardVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public SODashboardVO getAdditionalDetailsInDashboard(MobileDashboardVO dashboardVO)throws BusinessServiceException;

	/**
	 *  @Description:Method to fetch the details of the status monitor
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SODashboardVO getApprovedUnapprovedCountsAndFirmStatus(Integer firmId)throws BusinessServiceException;

	/**
	 *  @Description:Method to fetch the details of the spn monitor details
	 * @param firmId
	 * @return
	 */
	public List<SPNMonitorVO> getSpnMonitorDetails(Integer firmId)throws BusinessServiceException;

	/**
	 * @Description:Method to fetch service date and time of an soId
	 * @param soId
	 * @return UpdateApptTimeVO
	 * @throws BusinessServiceException
	 */
	public UpdateApptTimeVO fetchServiceDatesAndTimeWndw(String soId) throws BusinessServiceException;

	/**
	 * @Description:Method to fetch user permission details
	 * @param resourceId
	 * @return List<UserDetailsVO>
	 * @throws BusinessServiceException
	 */
	public List<UserDetailsVO> getUserPermissionDetails(Integer resourceId) throws BusinessServiceException;
	/**
	 * @Description:Method to check role 2 resource is accessing only the self assigned orders
	 * @param soId
	 * @param resId
	 * @return
	 */
	public boolean canRoleTwoAccess(String soId, Integer resId);
	/**
	 * @Description:Method to validate the release reason code
	 * @param soId
	 * @param reason 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean validateReleaseReasonCode(String soId, String reason) throws BusinessServiceException;


}
