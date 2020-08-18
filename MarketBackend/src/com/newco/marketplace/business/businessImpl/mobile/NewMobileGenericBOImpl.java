package com.newco.marketplace.business.businessImpl.mobile;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.updateTimeWindow.UpdateTimeWindowVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.dashboard.IDashboardDisplayBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.mobile.IManageScheduleBO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.business.iBusiness.provider.ICompanyProfileBO;
import com.newco.marketplace.business.iBusiness.provider.ITeamProfileBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.dao.ordermanagement.OrderManagementDao;
import com.newco.marketplace.persistence.iDao.mobile.IAuthenticateUserDao;
import com.newco.marketplace.persistence.iDao.mobile.INewMobileGenericDao;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.UserDetailsVO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.TeamMemberDocumentVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * BO layer for Mobile phase v3.1 API's
 */

public class NewMobileGenericBOImpl implements  INewMobileGenericBO {

	private static final Logger LOGGER = Logger.getLogger(NewMobileGenericBOImpl.class);
	private INewMobileGenericDao newMobileGenericDao;
	private OrderManagementDao managementDao;
	private ICompanyProfileBO companyProfileBO;
	private ITeamProfileBO teamProfileBO;
	private IManageScheduleBO manageScheduleBO;
	private OrderManagementService managementService;

	private IDashboardDisplayBO dashboardDisplay;
	private ISPNMonitorBO spnMonitorBO;
	private  IAuthenticateUserDao authenticateUserDao;
	private IServiceOrderBO serviceOrderBo;
	private ILookupBO lookupBO = null;

	/**
	 * @param soId
	 * @param action
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateServiceOrderStatus(String soId, String action) throws BusinessServiceException{

		boolean isValidStatus = false;
		try{
			isValidStatus = newMobileGenericDao.validateServiceOrderStatus(soId,action);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return isValidStatus;
	}

	/**
	 * @param soId
	 * @return List<Integer>
	 * 
	 */
	public List<Integer> fetchProviderList(String soId, String firmId)  {

		return managementDao.fetchProviderList(soId,firmId);
	}

	/**
	 * 
	 */
	public MobileSOReleaseVO fetchAssignmentTypeAndStatus(MobileSOReleaseVO releaseRequestVO) throws BusinessServiceException{
		try {
			releaseRequestVO = newMobileGenericDao.fetchAssignmentTypeAndStatus(releaseRequestVO);
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return releaseRequestVO;
	}

	/**
	 * 
	 */
	public String fetchReasonDesc(String reasonCode) throws BusinessServiceException {
		String reasonDescription=null;
		try{
			reasonDescription=newMobileGenericDao.fetchReasonDesc(reasonCode);
		}
		catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return reasonDescription;
	}

	/**
	 * @Description:Method to fetch the company details
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public CompanyProfileVO getCompleteProfile(String providerId)throws BusinessServiceException{
		CompanyProfileVO companyProfileVO=null;
		try{
			companyProfileVO=companyProfileBO.getCompleteProfile(Integer.parseInt(providerId));
		}
		catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return companyProfileVO;
	}

	/**
	 * @Description:Method to check for the Manage User Profile permission
	 * @param providerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean isManageTeamPermission(Integer resourceId) throws BusinessServiceException {
		boolean isManageTeamPermission=false;
		try{
			isManageTeamPermission = newMobileGenericDao.isManageTeamPermission(resourceId);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return isManageTeamPermission;
	}

	/**
	 * @Description:Method to fetch the the resource activity status to check whether any resource is under this vendor
	 * @param vendorId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	@SuppressWarnings("unchecked")
	public boolean isResourceActive(String vendorId) throws BusinessServiceException {
		List<Integer> resourceActKeyList = null;
		boolean isResourceActive=false;
		try
		{
			resourceActKeyList = teamProfileBO.getResourceActivityStatus(Integer.parseInt(vendorId));
			if(null != resourceActKeyList && !resourceActKeyList.isEmpty()){
				isResourceActive=true;
			}
		}
		catch (Exception ex) {
			throw new BusinessServiceException(ex);	
		}
		return isResourceActive;
	}

	/**
	 * @Description:Method to fetch the team member list
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	public List <TeamMemberVO> getTeamMemberList(String vendorId)throws BusinessServiceException {
		List <TeamMemberVO> teamMemberList= null;
		try{
			UserProfile profile = new UserProfile();
			profile.setVendorId(Integer.parseInt(vendorId));
			teamMemberList=teamProfileBO.getTeamMemberList(profile);
		}
		catch (Exception ex) {
			throw new BusinessServiceException(ex);	
		}
		return teamMemberList;
	}

	/**
	 * @Description:Method to fetch the documentId list 
	 * @param teamMemberVOList
	 * @return List<TeamMemberDocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<TeamMemberDocumentVO> getDocumentIdList(List<Integer> resourceIdList) throws BusinessServiceException {
		List<TeamMemberDocumentVO> teamMemberResourceIdList = null;
		try{
			teamMemberResourceIdList=newMobileGenericDao.getDocumentIdList(resourceIdList);
		}
		catch (Exception ex) {
			LOGGER.error("error occured in getDocumentIdList()"+ex.getMessage());
			throw new BusinessServiceException(ex);	
		}
		return teamMemberResourceIdList;
	}


	public UpdateTimeWindowVO fetchServiceDatesAndTimeWndw(String soId, UpdateTimeWindowVO timeWindowVO) throws BusinessServiceException{
		UpdateApptTimeVO scheduleVO = null;
		try{
			scheduleVO = manageScheduleBO.fetchServiceDatesAndTimeWndw(timeWindowVO.getSoId());
			if(null == scheduleVO){
				throw new BusinessServiceException("No data exists with the soId, cannot proceed");
			}
			timeWindowVO = mapScheduleVOToTimeWindowVO(timeWindowVO, scheduleVO);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}

		return timeWindowVO;

	}

	/**
	 * map the scheduleVO details to timeWindowVO
	 * @param timeWindowVO
	 * @param scheduleVO
	 * @return timeWindowVO
	 */
	private UpdateTimeWindowVO mapScheduleVOToTimeWindowVO(UpdateTimeWindowVO timeWindowVO,UpdateApptTimeVO scheduleVO){
		if(null != timeWindowVO && null != scheduleVO){
			if(null != scheduleVO.getStartDate()){
				timeWindowVO.setStartDateInGMT(scheduleVO.getStartDate().substring(0, scheduleVO.getStartDate().indexOf(" ")));
			}
			if(null != scheduleVO.getEndDate()){
				timeWindowVO.setEndDateInGMT(scheduleVO.getEndDate().substring(0, scheduleVO.getEndDate().indexOf(" ")));
			}
			timeWindowVO.setServiceTimeStart(scheduleVO.getServiceTimeStart());
			timeWindowVO.setServiceTimeEnd(scheduleVO.getServiceTimeEnd());
			timeWindowVO.setTimeZone(scheduleVO.getZone());
			timeWindowVO.setMinTimeWindow(scheduleVO.getMinTimeWindow());
			timeWindowVO.setMaxTimeWindow(scheduleVO.getMaxTimeWindow());
			timeWindowVO.setServiceDateType(scheduleVO.getServiceDateType());
		}
		return timeWindowVO;
	}


	public void updateTimeWindow(UpdateScheduleVO scheduleVO, ProviderHistoryVO hisVO) throws BusinessServiceException{
		try{
			managementService.editSOAppointmentTime(scheduleVO);
			historyLogging(hisVO);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
	}

	private void historyLogging(ProviderHistoryVO hisVO){
		try{
			managementService.historyLogging(hisVO);
		}
		catch (Exception ex) {
			LOGGER.error("Exception Occured while logging history in historyLogging()");
		}
	}


	public ServiceOrder getServiceOrderAssignmentAndScheduleDetails(String soId) throws BusinessServiceException{
		ServiceOrder serviceOrder = null;
		try{
			serviceOrder = newMobileGenericDao.getServiceOrderAssignmentAndScheduleDetails(soId);
		}
		catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return serviceOrder;
	}

	/**
	 * @param soId
	 * @param action
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateScheduleStatus(String soId, String action) throws BusinessServiceException{

		boolean isValidStatus = false;
		try{
			isValidStatus = newMobileGenericDao.validateScheduleStatus(soId,action);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return isValidStatus;
	}

	/**
	 * @param soId
	 * @return Boolean
	 * @throws BusinessServiceException
	 */
	public boolean isAppoinmentIn3Day(String soId) throws BusinessServiceException{
		boolean isAppoinmentIn3Day = false;
		try{
			isAppoinmentIn3Day = newMobileGenericDao.isAppoinmentIn3Day(soId);
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return isAppoinmentIn3Day;
	}

	/**
	 * @param updateScheduleVO
	 * @throws BusinessServiceException
	 * 
	 * method to update schedule details
	 * 
	 */
	public void updateScheduleDetails(UpdateScheduleVO updateScheduleVO) throws BusinessServiceException {
		try{
			if(null!=updateScheduleVO){
				managementService.updateScheduleDetails(updateScheduleVO);
				// for updating so substatus to schedule confirmd when schedule status is pre call completed confirmed
				if(updateScheduleVO.getScheduleStatusId() == OrderConstants.PRE_CALL_COMPLETED && 
						(updateScheduleVO.getCustomerConfirmedInd()!=null && updateScheduleVO.getCustomerConfirmedInd().intValue()==1)){
					managementService.updateSOSubstatusToScheduleConfirmed(updateScheduleVO.getSoId());
				}
			}
		}
		catch(Exception ex){
			throw new BusinessServiceException(ex);
		}
	}

	public UpdateApptTimeVO fetchServiceDatesAndTimeWndw(String soId) throws BusinessServiceException{
		UpdateApptTimeVO apptTimeVO = null;
		try{
			apptTimeVO = manageScheduleBO.fetchServiceDatesAndTimeWndw(soId);
			if(null == apptTimeVO){
				throw new BusinessServiceException("No data exists with the soId, cannot proceed");
			}
		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}

		return apptTimeVO;

	}

	/**
	 * @Description:Method to fetch the additional details in the dashboard i.e. performance statistics,spn details,lead order details,available balance etc.
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SODashboardVO getAdditionalDetailsInDashboard(MobileDashboardVO mobileDashboardVO)throws BusinessServiceException{
		SODashboardVO dashboardVO=null;
		try{
			//In the below mentioned method,survey ratings is setting as null,manageSoflag as true because it is used for grouped orders and we don't consider grouped orders for mobile API.
			//leadflag is setting true,because if this flag is true only,the leaddetails should be fetched.
			dashboardVO = dashboardDisplay.getDashBoardWidgetDetails(mobileDashboardVO.getFirmId().toString(),
					mobileDashboardVO.getResourceId().toString(),OrderConstants.PROVIDER_ROLEID,null,true,true);
		}
		catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return dashboardVO;
	}

	/**@Description:Method to fetch the details of firm status,count of approved and unapproved  firms
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 * 
	 */
	public SODashboardVO getApprovedUnapprovedCountsAndFirmStatus(Integer firmId)throws BusinessServiceException{
		SODashboardVO dashboardVO=null;
		try{
			dashboardVO=dashboardDisplay.getApprovedUnapprovedCountsAndFirmStatus(firmId);
		}
		catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return dashboardVO;
	}

	/**@Description:Method to fetch the details of the spn monitor details
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 * 
	 */
	public List<SPNMonitorVO> getSpnMonitorDetails(Integer firmId)throws BusinessServiceException{
		Integer isSPNMonitorAvailable = 0;
		List<SPNMonitorVO> spnList =null;
		try{
			isSPNMonitorAvailable = spnMonitorBO.isVendorSPNApplicant(firmId);
			if(isSPNMonitorAvailable > 0){
				spnList = spnMonitorBO.getSPNMonitorList(firmId);	
			}	
		}
		catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return spnList;

	}

	/**
	 * @Description:Method to fetch user permission details
	 * @param resourceId
	 * @return List<UserDetailsVO>
	 * @throws BusinessServiceException
	 */
	public List<UserDetailsVO> getUserPermissionDetails(Integer resourceId) throws BusinessServiceException{
		List<UserDetailsVO> userDetailList = null;
		try{
			userDetailList = authenticateUserDao.getUserRoleDetails(null, resourceId);
		}catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return userDetailList;
	}
	/**
	 * @Description:Method to validate the release reason code
	 * @param soId
	 * @param reason 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public boolean validateReleaseReasonCode(String soId,String reason) throws BusinessServiceException {
		String  assignmentType="";
		LuProviderRespReasonVO luReasonVO = new LuProviderRespReasonVO();
		ArrayList<LuProviderRespReasonVO> releaseReasons = new ArrayList<LuProviderRespReasonVO>();
		ArrayList<String>releaseReason = new ArrayList<String>();
		try{
			assignmentType = serviceOrderBo.getAssignmentType(soId);	
			if(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM.equals(assignmentType)){
				luReasonVO.setSearchByResponse(MPConstants.PROVIDER_RESP_RELEASE_BY_FIRM);
			}else{
				luReasonVO.setSearchByResponse(MPConstants.PROVIDER_RESP_RELEASE_BY_PROVIDER);
			}
			releaseReasons = lookupBO.getProviderRespReason(luReasonVO);
			for(LuProviderRespReasonVO list :releaseReasons){
				releaseReason.add(String.valueOf(list.getRespReasonId()));
			}
			if(releaseReason.contains(reason)){
				return true;
			}
			else{
				return false;
			}
		}
		catch(BusinessServiceException be){
			throw new BusinessServiceException(be);
		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}

	}
	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public INewMobileGenericDao getNewMobileGenericDao() {
		return newMobileGenericDao;
	}

	public void setNewMobileGenericDao(INewMobileGenericDao newMobileGenericDao) {
		this.newMobileGenericDao = newMobileGenericDao;
	}

	public OrderManagementDao getManagementDao() {
		return managementDao;
	}

	public void setManagementDao(OrderManagementDao managementDao) {
		this.managementDao = managementDao;
	}

	public ICompanyProfileBO getCompanyProfileBO() {
		return companyProfileBO;
	}

	public void setCompanyProfileBO(ICompanyProfileBO companyProfileBO) {
		this.companyProfileBO = companyProfileBO;
	}

	public IManageScheduleBO getManageScheduleBO() {
		return manageScheduleBO;
	}

	public void setManageScheduleBO(IManageScheduleBO manageScheduleBO) {
		this.manageScheduleBO = manageScheduleBO;
	}

	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}

	public ITeamProfileBO getTeamProfileBO() {
		return teamProfileBO;
	}

	public void setTeamProfileBO(ITeamProfileBO teamProfileBO) {
		this.teamProfileBO = teamProfileBO;
	}

	public IDashboardDisplayBO getDashboardDisplay() {
		return dashboardDisplay;
	}

	public void setDashboardDisplay(IDashboardDisplayBO dashboardDisplay) {
		this.dashboardDisplay = dashboardDisplay;
	}

	public ISPNMonitorBO getSpnMonitorBO() {
		return spnMonitorBO;
	}

	public void setSpnMonitorBO(ISPNMonitorBO spnMonitorBO) {
		this.spnMonitorBO = spnMonitorBO;
	}

	public IAuthenticateUserDao getAuthenticateUserDao() {
		return authenticateUserDao;
	}

	public void setAuthenticateUserDao(IAuthenticateUserDao authenticateUserDao) {
		this.authenticateUserDao = authenticateUserDao;
	}
	/**
	 * @Description:Method to check role 2 resource is accessing only the self assigned orders
	 * @param soId
	 * @param resId
	 * @return
	 */
	public boolean canRoleTwoAccess(String soId, Integer resId) {
		EligibleProvider eligibleProvider = managementDao.getAssignedResource(soId);
		if(null != eligibleProvider && null!=resId && resId.equals(eligibleProvider.getResourceId())){
			return true;
		}
		return false;

	}


}
