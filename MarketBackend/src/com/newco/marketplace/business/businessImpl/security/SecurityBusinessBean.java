package com.newco.marketplace.business.businessImpl.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.security.ISecurityBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.dto.vo.AccessControlList;
import com.newco.marketplace.dto.vo.ActivityTemplate;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.RoleActivityPermissionVO;
import com.newco.marketplace.dto.vo.UserActivityPermissionVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.CryptoUtil.CannotPerformOperationException;
import com.newco.marketplace.utils.CryptoUtil.InvalidHashException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Location;
import com.sears.os.business.ABaseBO;

/**
 * $Revision: 1.17 $ $Author: akashya $ $Date: 2008/05/21 22:54:40 $
 */
public class SecurityBusinessBean extends ABaseBO implements ISecurityBO {

	private static final Logger logger = Logger
			.getLogger(SecurityBusinessBean.class.getName());

	private SecurityDAO securityAccess = null;
	private ISurveyBO surveyBO;
	
	public SecurityBusinessBean() {
		super();
	}

	public boolean authenticate(LoginCredentialVO lc)
			throws BusinessServiceException {

		boolean rvBoolean = false;
		logger.debug("[SecurityBusinessBean] Authenticate");
		try {
			logger.debug("[SecurityBusinessBean] Authenticate : "
					+ getSecurityAccess().getPassword(lc));
		
			try {
				if (CryptoUtil.verifyPassword(lc.getPassword().trim(), getSecurityAccess().getPassword(lc))) {
					rvBoolean = true;
				} else {
					rvBoolean = false;
				}
			} catch (CannotPerformOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidHashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (DataServiceException dse) {
			logger.info("[BusinessServiceException] "
					+ StackTraceHelper.getStackTrace(dse));
			// final String error = messages.getMessage("biz.select.failed");
			throw new BusinessServiceException("", dse);
		}

		logger.debug("[SecurityBusinessBean] got Result Authenticate : "
				+ rvBoolean);
		return (rvBoolean);
	}// authenticate()

	public SecurityDAO getSecurityAccess() {
		return securityAccess;
	}

	public void setSecurityAccess(SecurityDAO securityAccess) {
		this.securityAccess = securityAccess;
	}

	public AccessControlList getActivitiesByUserName(String username) {
		ArrayList activities = securityAccess.getActivitiesByUserName(username);
		AccessControlList acl = new AccessControlList();
		acl.setUserName(username);
		acl.setActivities(activities);
		return acl;
	}

	public void assignActivitiesToUser(String userName,
			ArrayList<ActivityVO> activityList) {
		if (activityList != null && activityList.size() != 0) {
			ActivityVO activityVO;
			ArrayList<UserActivityPermissionVO> userActivityPermissionVOs = new ArrayList<UserActivityPermissionVO>();
			Iterator iter = activityList.iterator();
			while (iter.hasNext()) {
				UserActivityPermissionVO userActivityPermissionVO = new UserActivityPermissionVO();
				userActivityPermissionVO.setUserName(userName);
				activityVO = (ActivityVO) iter.next();
				userActivityPermissionVO.setActivityId(activityVO
						.getActivityId());
				userActivityPermissionVOs.add(userActivityPermissionVO);
			}
			securityAccess.assignActivitiesToUser(userActivityPermissionVOs);
		}
	}

	public void removeActivitiesFromUser(String userName, ArrayList<ActivityVO> activityList) {
		if (activityList != null && activityList.size() != 0) {
			ActivityVO activityVO;
			ArrayList<UserActivityPermissionVO> userActivityPermissionVOs = new ArrayList<UserActivityPermissionVO>();
			Iterator iter = activityList.iterator();
			while (iter.hasNext()) {
				UserActivityPermissionVO userActivityPermissionVO = new UserActivityPermissionVO();
				userActivityPermissionVO.setUserName(userName);
				activityVO = (ActivityVO) iter.next();
				userActivityPermissionVO.setActivityId(activityVO
						.getActivityId());
				userActivityPermissionVOs.add(userActivityPermissionVO);
			}
			securityAccess.removeActivitiesFromUser(userActivityPermissionVOs);
		}
	}
	
	public void removeActivitiesFromUser(ArrayList<UserActivityPermissionVO> userActivityPermissionVOs) {
		if (userActivityPermissionVOs != null && userActivityPermissionVOs.size() != 0) {
			securityAccess.removeActivitiesFromUser(userActivityPermissionVOs);
		}
	}
	
	
	public ActivityTemplate getActivitiesForUserGroup(int roleId) {
		ArrayList activities = securityAccess.getActivitiesForUserGroup(roleId);
		ActivityTemplate at = new ActivityTemplate();
		at.setRoleId(roleId);
		at.setActivities(activities);
		return at;
	}

	public void assignActivitiesToUserGroup(int roleId,
			ArrayList<ActivityVO> activityList) {
		if (activityList != null && activityList.size() != 0) {
			ActivityVO activityVO;
			ArrayList<RoleActivityPermissionVO> roleActivityPermissionVOs = new ArrayList<RoleActivityPermissionVO>();
			Iterator iter = activityList.iterator();
			while (iter.hasNext()) {
				RoleActivityPermissionVO roleActivityPermissionVO = new RoleActivityPermissionVO();
				roleActivityPermissionVO.setRoleId(roleId);
				activityVO = (ActivityVO) iter.next();
				roleActivityPermissionVO.setActivityId(activityVO
						.getActivityId());
				roleActivityPermissionVOs.add(roleActivityPermissionVO);
			}
			securityAccess
					.assignActivitiesToUserGroup(roleActivityPermissionVOs);
		}
	}

	public void removeActivitiesFromUserGroup(int roleId, ArrayList<ActivityVO> activityList){
		if (activityList != null && activityList.size() != 0) {
			ActivityVO activityVO;
			ArrayList<RoleActivityPermissionVO> roleActivityPermissionVOs = new ArrayList<RoleActivityPermissionVO>();
			Iterator iter = activityList.iterator();
			while (iter.hasNext()) {
				RoleActivityPermissionVO roleActivityPermissionVO = new RoleActivityPermissionVO();
				roleActivityPermissionVO.setRoleId(roleId);
				activityVO = (ActivityVO) iter.next();
				roleActivityPermissionVO.setActivityId(activityVO
						.getActivityId());
				roleActivityPermissionVOs.add(roleActivityPermissionVO);
			}
			securityAccess
					.removeActivitiesFromUserGroup(roleActivityPermissionVOs);
		}
	}
	public boolean authenticate(String username, String password)
			throws BusinessServiceException {
		try {
			
			try {
				return password!=null?CryptoUtil.verifyPassword(password,(securityAccess.getPassword(username))):false;
			} catch (CannotPerformOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidHashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return false;
	}


	public boolean simpleAuthenticate(String username, String password)
			throws BusinessServiceException {
		try {
			return password!=null?password.equals(securityAccess.getPassword(username)):false;
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return false;
	}

	public AccountProfile getAccountProfile(String username)
			throws BusinessServiceException {
		try {
			return securityAccess.getAccountProfile(username);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return null;
	}
	
	/**
	 * @param securityCtx
	 * @return
	 * @throws BusinessServiceException
	 */
	public SurveyRatingsVO getLifetimeRatings(SecurityContext securityCtx) throws BusinessServiceException {
		
		String companyId = null;
		String resourceId = null;
		Integer roleIdInt=null;
		
		LoginCredentialVO context = securityCtx.getRoles();
		if(context != null) {
			companyId = securityCtx.getCompanyId() + "";
			resourceId = securityCtx.getVendBuyerResId() + "";
			roleIdInt = context.getRoleId();
		}
		
		//Get entityId, entityTypeId and resourceId from the AccountProfile and get ratings
		//information for the user that has logged in.
		Integer entityTypeId = 0;
		Integer entityId = companyId != null ? Integer.valueOf(companyId) : null;
		Integer resId = resourceId != null ? Integer.valueOf(resourceId) : null;
				
		if(context.getRoleId() == OrderConstants.BUYER_ROLEID) {//Buyer
			entityTypeId = SurveyConstants.ENTITY_BUYER_ID;
		}
		else if(context.getRoleId() == OrderConstants.SIMPLE_BUYER_ROLEID) {//Buyer
			entityTypeId = SurveyConstants.ENTITY_BUYER_ID;
		} 
		else if(context.getRoleId() == OrderConstants.PROVIDER_ROLEID) {//Provider
			entityTypeId = SurveyConstants.ENTITY_PROVIDER_ID;
		}
		
		try {
			SurveyRatingsVO surveyRatings = surveyBO.getRatings(entityTypeId.intValue(),
					((entityId != null)? entityId.intValue() : 0), 
					((resId != null)? resId : 0));
			
			return surveyRatings;
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return null;
	}
	
	public List<ActionActivityVO> getActionActivities() {
		
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getActionActivities();
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
		
	
	}

	public ISurveyBO getSurveyBO() {
		return surveyBO;
	}
	
	public void setSurveyBO(ISurveyBO survey) {
		this.surveyBO = survey;
	}

	public LoginCredentialVO getUserRoles(LoginCredentialVO lvo){
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getRoles(lvo.getUsername());
		} catch (DataServiceException e) {
			e.printStackTrace();
			
		}
		return null;
	}

	public Map<String,Long> getBuyerId(String userName) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getBuyerId(userName);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getVendorAdminName(Integer vendorId) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getVendorAdminName(vendorId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String getBuyerUserName(Integer resourceId) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getBuyerUserName(resourceId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getBuyerAdminUserName(Integer buyerId) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getBuyerAdminUserName(buyerId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}	
	public boolean verifyEntity(Integer id, String type) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			Map map = securityBean.verifyEntity(id, type);
			if (map == null)
				return false;
			return true;
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getVendorUserName(Integer resourceId) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getVendorUserName(resourceId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getOAuthConsumerSecret(String consumerKey) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getOAuthConsumerSecret(consumerKey);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String,Long> getVendorId(String userName) {
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getVendorId(userName);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Location getProviderLocation(String userName) throws DataServiceException{
		SecurityDAO securityDao = getSecurityAccess();
		try {			
			return securityDao.getProviderLocation(userName);
		} catch (DataServiceException e) {
			e.printStackTrace();
			
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.security.ISecurityBO#hasPermissionForAction(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean hasPermissionForAction(String actionName, String userName,
			String roleId) {
		boolean hasPermission = false;
		try{
			SecurityDAO securityDao = getSecurityAccess();
			Integer count = securityDao.hasPermissionForAction(actionName, userName, roleId);
			if(count > 0){
				hasPermission = true;
			}
		}catch(Exception e){
			String msg = "Error occurred in hasPermissionForAction due to " + e.getMessage();
			logger.error(msg,e);
		}
		return hasPermission;
		
	}
	
	//SL-15642 check for firm id in order_management_permission
	public Integer getOmPermissionForFirm(Integer vendorId){
		Integer result = null;
		SecurityDAO securityDao = getSecurityAccess();
		try {
			result = securityDao.getOmPermissionForFirm(vendorId);
		} catch (Exception e) {
			logger.error(e);
		}
		return result; 
	}
	
	public List<UserActivityVO> getUserActivityRolesList(String userName){
		List<UserActivityVO>  activityList  = null;
		SecurityDAO securityDao = getSecurityAccess();
		try {
			activityList = securityDao.getUserActivityRolesList(userName);
		} catch (Exception e) {
			logger.error(e);
		}
		return activityList; 
	}
	
}// SecurityBusinessBean
