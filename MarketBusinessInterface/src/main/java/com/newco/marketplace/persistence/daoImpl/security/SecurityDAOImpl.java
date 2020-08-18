package com.newco.marketplace.persistence.daoImpl.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.RoleActivityPermissionVO;
import com.newco.marketplace.dto.vo.UserActivityPermissionVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.buyer.BuyerUserProfile;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.vo.api.APISecurityVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.ofac.vo.BuyerOfacVO;
import com.newco.ofac.vo.ContactOfacVO;
import com.newco.ofac.vo.ProviderOfacVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file
 */
public class SecurityDAOImpl extends ABaseImplDao implements SecurityDAO {

	public boolean getRegComplete(int vendorId) throws DataServiceException {
		Integer returnInt = (Integer)queryForObject("security.getRegComplete", new Integer(vendorId));
		if((returnInt != null )&&( returnInt.intValue() > 0)){
			return true;
		}else{
			return false;
		}
	}


	private static final Logger logger = Logger.getLogger(SecurityDAOImpl.class
			.getName());

	public String getPassword(LoginCredentialVO lc) throws DataServiceException {
		String rvString;
		logger.debug("[SecurityDAOImpl] getUserPassword ");

		try {
			rvString = (String) queryForObject("security.getUserPassword", lc
					.getUsername());
			if (rvString == null) {
				rvString = "";
			}
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		logger.debug(rvString);
		return ((rvString).trim());
	}

	/*
	 * public int updatePassword(ChangePasswordVO changePasswordVO) throws
	 * DataServiceException { return update("security.updatePassword",
	 * changePasswordVO); } // updatePassword
	 */
	public String getSecretQuestion(String username)
			throws DataServiceException {
		String rvString;
		logger.debug("[SecurityDAOImpl] getSecretQuestion : " + username);

		try {
			rvString = (String) queryForObject("security.getSecretQuestion",
					username);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ",ex);
			throw new DataServiceException("", ex);
		}

		if (rvString == null) {
			rvString = "";
		}

		logger.debug("*********************************** Get SecretQuestion"
				+ rvString);
		return ((rvString).trim());
	}

	public String getPassword(String username) throws DataServiceException {
		String rvString;
		logger.debug("[SecurityDAOImpl] getPassword : " + username);

		try {
			rvString = (String) queryForObject("security.getUserPassword", username);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ",ex);
			throw new DataServiceException("", ex);
		}

		if (rvString == null) {
			rvString = "";
		}

		logger.debug("**************************************** Get getPassword"
				+ rvString);
		return ((rvString).trim());
	}

	public String getSecretAnswer(String username) throws DataServiceException {
		String rvString = "";
		logger.debug("[SecurityDAOImpl;GetSecretAnswer ---- ] getSecretAnswer  : "
						+ username);

		try {
			rvString = (String) queryForObject("security.getSecretAnswer",
					username.trim());
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ",ex);
			throw new DataServiceException("", ex);
		}

		logger.debug("[SecurityDAOImpl] getUsername :-----------------------------got value form database: for username: Secret answer. *"
						+ rvString + "*");
		if (rvString == null) {
			rvString = "";
		}

		return (rvString.trim());
	}

	public Map<String,Long> getBuyerId(String username) throws DataServiceException {

		Map<String,Long> buyer;

		try {
			buyer = (Map<String,Long>) queryForObject("security.queryBuyerId", username.trim());
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}


		return buyer;
	}
	
	/**
	 * Just verify if entity (BUYER or PROVIDER) exist
	 * @param entityId, entityType (Valid values are BUYER or PROVIDER)
	 * @return
	 * @throws DataServiceException
	 */
	public Map<String,Long> verifyEntity(Integer entityId, String entityType) throws DataServiceException {

		Map<String,Long> buyer = null;
		List list = null;
		
		try {
			if (entityType.equalsIgnoreCase("BUYER"))
			   list =  queryForList("security.verifyBuyerId", entityId);
			else
				list =  queryForList("security.verifyVendorId", entityId);
			if (list != null && list.size() > 0) {
				buyer = (Map<String,Long>)list.get(0);
			}
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return buyer;
	}
	
	public String getBuyerUserName(Integer buyerResourceId) throws DataServiceException {
		String userName;
		try {
			userName = (String) queryForObject("security.queryBuyerName", buyerResourceId);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return userName;
	}
	public String getBuyerAdminUserName(Integer buyerId)
																		throws DataServiceException {
		String userName;
		try {
			userName = (String) queryForObject("" +
													"security.queryBuyerAdminName", buyerId);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return userName;
	}	
	public String getVendorAdminName(Integer vendorId) throws DataServiceException {
		String userName;
		try {
			userName = (String) queryForObject("security.queryVendorAdminName", vendorId);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return userName;
	}
	public String getVendorUserName(Integer vendorResourceId) throws DataServiceException {
		String userName;
		try {
			userName = (String) queryForObject("security.queryVendorName", vendorResourceId);
			if(null==userName)
			{
				userName = (String) queryForObject("security.queryVendorResourceName", vendorResourceId);					
			}
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return userName;
	}

	public Map<String,Long> getVendorId(String username) throws DataServiceException {
		Map<String,Long> vendor;
		try {
			vendor = (Map<String,Long>) queryForObject("security.queryVendorId",
					username.trim());
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}


		return vendor;
	}
	

	public LoginCredentialVO getRoles(String userName) throws DataServiceException {
		logger.info("[Entering] getRoles ");
		List loginCredentialVOList = null;
		LoginCredentialVO lvo = null;

		try {
			lvo = (LoginCredentialVO) queryForObject("security.queryRoleList",
					userName);
			if (lvo.roleId.intValue() == OrderConstants.NEWCO_ADMIN_ROLEID) {
				Integer cRoleId = (Integer)queryForObject("security.companyRole", userName);
				lvo.setRoleInCompany(cRoleId);
			}
			logger.info("[loginCredentialVOList] > " + loginCredentialVOList);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.getRoles - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		logger.debug("[Exiting] getRoles ");
		return lvo;
	}
	public Location getProviderLocation(String userName) throws DataServiceException {
		logger.info("[Entering] getProviderLocation ");	
		Location locn = null;
		try {
			locn = (Location) queryForObject("security.queryLocation",
					userName);
			
			logger.info("[loginCredentialVO] > " + locn);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.getProviderLocation - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		logger.debug("[Exiting] getProviderLocation ");
		return locn;
	}
	/**
	 * get the list of activities for the user
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	public List<UserActivityVO> getUserActivityRolesList(String userName) throws DataServiceException {

		List<UserActivityVO> returnList = null;

		try {

			   returnList = queryForList("activity_security.query", userName);

			logger.info("[list] > " + returnList);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.getUserActivityRolesList - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		logger.debug("[Exiting] getRoles ");
		return returnList;
	}

	public ArrayList<ActivityVO> getActivitiesByUserName(String username) {
		return (ArrayList) this.queryForList("activities.query", username);
	}

	public void assignActivitiesToUser(
			ArrayList<UserActivityPermissionVO> userActivityPermissions) {
		if (userActivityPermissions != null
				&& userActivityPermissions.size() != 0) {

			userActivityPermissions = filerOutDuplicateUserActivities(userActivityPermissions);

			Iterator iter = userActivityPermissions.iterator();
			UserActivityPermissionVO userActivityPermissionVO = null;
			while (iter.hasNext()) {
				userActivityPermissionVO = (UserActivityPermissionVO) iter.next();
				this.insert("user_activity.insert", userActivityPermissionVO);
			}
		}
	}


	public void removeActivitiesFromUser(ArrayList<UserActivityPermissionVO> userActivityPermissions){
		if (userActivityPermissions != null
				&& userActivityPermissions.size() != 0) {

			Iterator iter = userActivityPermissions.iterator();
			UserActivityPermissionVO userActivityPermissionVO = null;
			while (iter.hasNext()) {
				userActivityPermissionVO = (UserActivityPermissionVO) iter.next();
				this.delete("user_activity.delete", userActivityPermissionVO);
			}
		}
	}


	public void removeActivitiesFromUserGroup(ArrayList<RoleActivityPermissionVO> roleActivityPermissions){
		if (roleActivityPermissions != null
				&& roleActivityPermissions.size() != 0) {

			Iterator iter = roleActivityPermissions.iterator();
			while (iter.hasNext()) {
				RoleActivityPermissionVO roleActivityPermissionVO = (RoleActivityPermissionVO) iter
						.next();
				this.delete("role_activity.delete", roleActivityPermissionVO);
			}
		}

	}

	public ArrayList<ActivityVO> getActivitiesForUserGroup(int roleId) {
		return (ArrayList<ActivityVO>) this.queryForList("activities_role.query", roleId);
	}

	public void assignActivitiesToUserGroup(
			ArrayList<RoleActivityPermissionVO> roleActivityPermissions) {
		if (roleActivityPermissions != null
				&& roleActivityPermissions.size() != 0) {

			roleActivityPermissions = filerOutDuplicateRoleActivities(roleActivityPermissions);

			Iterator iter = roleActivityPermissions.iterator();
			while (iter.hasNext()) {
				RoleActivityPermissionVO roleActivityPermissionVO = (RoleActivityPermissionVO) iter
						.next();
				this.insert("role_activity.insert", roleActivityPermissionVO);
			}
		}
	}

	private ArrayList<RoleActivityPermissionVO> filerOutDuplicateRoleActivities(ArrayList<RoleActivityPermissionVO> roleActivityPermissions){
		ArrayList<RoleActivityPermissionVO> roleActivityPermissionVONew = new ArrayList<RoleActivityPermissionVO>();
		RoleActivityPermissionVO roleActivityPermissionVO = roleActivityPermissions.get(0);
		ArrayList<ActivityVO> activityVOs = getActivitiesForUserGroup(roleActivityPermissionVO.getRoleId());
		if(activityVOs != null){
			ActivityVO activityVO;
			Iterator iter = activityVOs.iterator();
			Iterator iterRoleActivityVOIter = roleActivityPermissions.iterator();
			while(iterRoleActivityVOIter.hasNext()){
				RoleActivityPermissionVO roleActivityPermissionVO1 = (RoleActivityPermissionVO)iterRoleActivityVOIter.next();
				boolean alreadyThere = false;
				while(iter.hasNext()){
					activityVO = (ActivityVO)iter.next();
					if(roleActivityPermissionVO1.getActivityId() == activityVO.getActivityId()){
						alreadyThere = true;
						break;
					}
				}
				if(alreadyThere == false){
					roleActivityPermissionVONew.add(roleActivityPermissionVO1);
				}
			}
		}
		return roleActivityPermissionVONew;
	}

	private ArrayList<UserActivityPermissionVO> filerOutDuplicateUserActivities(ArrayList<UserActivityPermissionVO> userActivityPermissions){
		ArrayList<UserActivityPermissionVO> userActivityPermissionVONew = new ArrayList<UserActivityPermissionVO>();
		UserActivityPermissionVO userActivityPermissionVO = userActivityPermissions.get(0);
		ArrayList<ActivityVO> activityVOs = getActivitiesByUserName(userActivityPermissionVO.getUserName());
		if(activityVOs != null){
			ActivityVO activityVO;
			Iterator iter = activityVOs.iterator();
			Iterator iterUserActivityVOIter = userActivityPermissions.iterator();
			while(iterUserActivityVOIter.hasNext()){
				UserActivityPermissionVO userActivityPermissionVO1 = (UserActivityPermissionVO)iterUserActivityVOIter.next();
				boolean alreadyThere = false;
				while(iter.hasNext()){
					activityVO = (ActivityVO)iter.next();
					if(userActivityPermissionVO1.getActivityId() == activityVO.getActivityId()){
						alreadyThere = true;
						break;
					}
				}
				if(alreadyThere == false){
					userActivityPermissionVONew.add(userActivityPermissionVO1);
				}
			}
		}
		return userActivityPermissionVONew;
	}

	/**
	 *
	 */
	public AccountProfile getAccountProfile(String username) throws DataServiceException {
		try {
			AccountProfile ap=username!=null?(AccountProfile)queryForObject("security.select.getAccountProfile", username):null;
			return ap= ap!=null?(AccountProfile)getSqlMapClientTemplate().queryForObject("security.select.getContactInfo", ap,ap):null;
		} catch (Exception ex) {
			logger.error("getAccountProfile failed for "+username, ex);
			throw new DataServiceException("Retrieve Account Failure", ex);
		}
	}

	public void insertAdminProfile(UserProfile aUserProfile)throws DataServiceException {
		insert("user_profile_permissions.admin_insert", aUserProfile);
	}

	public void insertBuyerAdminProfile(BuyerUserProfile aUserProfile)throws DataServiceException {
		insert("user_profile_buyer_permissions.admin_insert", aUserProfile);
	}
	/**
	 * to get the role activityid list for providers
	 *
	 */
	public List<Object> getRoleActivityIdList(String userName) throws DataServiceException
	{
		List<Object> roleActivityIdList = null;
		try
		{
			roleActivityIdList = getSqlMapClientTemplate().queryForList("security.select.getRoleActivityIdList", userName);
		}catch (Exception ex) {
			logger.error("getRoleActivityIdList failed for "+userName, ex);
			throw new DataServiceException("Retrieve RoleActivityIdList", ex);
		}
		return roleActivityIdList;
	}
	/**
	 * Retrieve the provider indicators
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

	public HashMap getProviderIndicators(Integer resourceId) throws DataServiceException
	{

		HashMap indicatorMap;
		try
		{
			indicatorMap = (HashMap) getSqlMapClientTemplate().queryForObject("security.select.getProviderRoleIndicators", resourceId);
		}
		catch (Exception ex) {
			logger.error("getProviderIndicators failed for "+resourceId, ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}
		return indicatorMap;
	}
	/**
	 * get the admin username of the vendor id
	 */
	public String getAdminUserName(Integer vendor_id) throws DataServiceException
	{
		String adminUserName = null;
		try
		{
			adminUserName = (String) getSqlMapClientTemplate().queryForObject("security.select.getAdminUserName",vendor_id);
		}
		catch (Exception ex) {
			logger.error("getAdminUserName failed for "+vendor_id, ex);
			throw new DataServiceException("Retrieve getAdminUserName", ex);
		}
		return adminUserName;
	}
	public List<ActionActivityVO> getActionActivities() throws DataServiceException
	{
		List<ActionActivityVO> actionActivityList = null;
		try
		{
			actionActivityList =  getSqlMapClientTemplate().queryForList("activity_action.query");
			if(null != actionActivityList)
			{
			logger.info("list size here is +*******"+actionActivityList.size());
			}
		}
		catch(Exception ex) {
			logger.error("getActionActivities failed ", ex);
			throw new DataServiceException("Retrieve getActionActivities", ex);
		}
		return actionActivityList;
	}


	/**
	 * check if logged in buyer is admin or not
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean checkForBuyerAdmin(String userName) throws DataServiceException
	{

		try
		{
		Integer returnInt = (Integer)queryForObject("security.checkForBuyerAdmin", userName);
			if((returnInt != null )&&( returnInt.intValue() > 0)){
				return true;
			}else{
				return false;
			}

		}
		catch (Exception ex) {
			logger.error("checkForBuyerAdmin failed for"+userName, ex);
			throw new DataServiceException("Retrieve checkForBuyerAdmin", ex);
		}

	}

	/**
	 * Retrieve the buyer ofac indicators
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

	public BuyerOfacVO getBuyerOfacIndicators(int resourceId) throws DataServiceException
	{

		BuyerOfacVO ofacIndicator;
		try
		{
			ofacIndicator = (BuyerOfacVO) getSqlMapClientTemplate().queryForObject("security.select.buyerOfacIndicators", resourceId);

		}
		catch (Exception ex) {
			logger.error("getProviderIndicators failed for "+resourceId, ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}
		return ofacIndicator;
	}
	/**
	 * update the buyer ofac db indicators
	 * @param resourceId
	 * @param BuyerOfacVO
	 * @return
	 * @throws DataServiceException
	 */

	public void updateBuyerOfacDbFlag(BuyerOfacVO buyerOfacVO)throws DataServiceException
	{

		try
		{
			getSqlMapClientTemplate().update("security.update.buyerOfacIndicators", buyerOfacVO); // update Provider's last Ofac CheckDt to present time
			getSqlMapClientTemplate().update("security.update.buyerOfacChkDt", buyerOfacVO.getBuyerID()); // update Buyer last Ofac CheckDt to present time
		}
		catch (Exception ex) {
			logger.error("getProviderIndicators failed for "+buyerOfacVO.getBuyerID(), ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}

	}
	/**
	 * Retrieve the provider ofac indicators
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

	public ProviderOfacVO getProviderOfacIndicators(int resourceId) throws DataServiceException
	{

		ProviderOfacVO ofacIndicator;
		try
		{
			ofacIndicator = (ProviderOfacVO) getSqlMapClientTemplate().queryForObject("security.select.providerOfacIndicators", resourceId);

		}
		catch (Exception ex) {
			logger.error("getProviderIndicators failed for "+resourceId, ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}
		return ofacIndicator;
	}
	/**
	 * update the provider ofac db indicators
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

	public void updateProviderOfacDbFlag(ProviderOfacVO providerOfacVO)throws DataServiceException
	{

		try
		{
			getSqlMapClientTemplate().update("security.update.providerOfacIndicators", providerOfacVO); // update Provider's last Ofac CheckDt to present time
			getSqlMapClientTemplate().update("security.update.providerOfacChkDt", providerOfacVO.getProviderID()); // update Provider's last Ofac CheckDt to present time
		}
		catch (Exception ex) {
			logger.error("getProviderIndicators failed for "+providerOfacVO.getProviderID(), ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}

	}

	public ContactOfacVO getBuyerContactIdEin(Integer resourceID)throws DataServiceException {
	{

			logger.debug("[SecurityDAOImpl] getContactID and EIN number: " + resourceID);

			try {
				return (ContactOfacVO)getSqlMapClientTemplate().queryForObject("security.getBuyerContactIdEin", resourceID);
			} catch (Exception ex) {
				logger.error("[SecurityDAOImpl.select - Exception] ",ex);
				throw new DataServiceException("", ex);
			}

		}
	}

	public ContactOfacVO getProviderContactIdEin(Integer resourceID)throws DataServiceException {
		{

				logger.debug("[SecurityDAOImpl] getContactID and EIN number: " + resourceID);

				try {
					return (ContactOfacVO)getSqlMapClientTemplate().queryForObject("security.getProviderContactIdEin", resourceID);
				} catch (Exception ex) {
					logger.error("[SecurityDAOImpl.select - Exception] ",ex);
					throw new DataServiceException("", ex);
				}

			}
		}


	public void addAdminDOBForOfac(ContactOfacVO contact)throws DataServiceException
	{
		try
		{
			getSqlMapClientTemplate().update("security.update.contactDOB", contact); // update Provider's last Ofac CheckDt to present time
		}
		catch (Exception ex) {
			//logger.error("getProviderIndicators failed for "+providerOfacVO.getProviderID(), ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}
	}

	public void addBuyerTaxID(ContactOfacVO contact)throws DataServiceException
	{
		try
		{
			getSqlMapClientTemplate().update("security.update.buyerTaxID", contact); // update Provider's last Ofac CheckDt to present time

		}
		catch (Exception ex) {
			//logger.error("getProviderIndicators failed for "+providerOfacVO.getProviderID(), ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}

	}
	public void addProviderTaxID(ContactOfacVO contact)throws DataServiceException
	{
		try
		{
			getSqlMapClientTemplate().update("security.update.providerTaxID", contact); // update Provider's last Ofac CheckDt to present time

		}
		catch (Exception ex) {
			//logger.error("getProviderIndicators failed for "+providerOfacVO.getProviderID(), ex);
			throw new DataServiceException("Retrieve getProviderIndicators", ex);
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.security.SecurityDAO#hasResourceAdminPermission(java.lang.Integer)
	 */
	public Boolean hasResourceAdminPermission(Integer resourceId)throws DataServiceException {
		logger.debug("[SecurityDAOImpl] getContactID and EIN number: " + resourceId);

			return (Boolean)getSqlMapClientTemplate().queryForObject("security.select.hasAdminPermission", resourceId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.security.SecurityDAO#hasPermissionForAction(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public Integer hasPermissionForAction(String actionName, String userName, String roleId) throws DataServiceException {
		 Integer userCount = null;
		 Map<String, String> paramMap = new HashMap<String, String>();
		 paramMap.put("actionName", actionName);
		 paramMap.put("userName", userName);
		 paramMap.put("roleId", roleId);
			 
		 if (getSqlMapClientTemplate().queryForObject("security.select.hasPermissionForAction", paramMap)!= null){
			 userCount = (Integer)getSqlMapClientTemplate().queryForObject("security.select.hasPermissionForAction", paramMap);
		 }
		 
		 return userCount;
	}
	
	public String getOAuthConsumerSecret(String consumerKey) throws DataServiceException {
		String secret;
		try {
			secret = (String) queryForObject("security.oauth.queryConsumerSecret", consumerKey);
		} catch (Exception ex) {
			logger.error("[SecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return secret;
	}

	public boolean isUserExisting(int userId, String consumerKey, int roleId) 
		throws DataServiceException {
		Integer count = null;
		if(roleId == 0) { // external application
			Map paramMap = new HashMap();
			paramMap.put("userId", userId);
			paramMap.put("consumerKey", consumerKey);
			if (getSqlMapClientTemplate().queryForObject("api.user.security", paramMap)!= null) {
				count = (Integer)getSqlMapClientTemplate().queryForObject("api.user.security", paramMap);
			}
		} else if(roleId == OrderConstants.BUYER_ROLEID) {// check against buyer company
			count = (Integer) queryForObject("api.user.security.buyer", userId);
		} else if(roleId == OrderConstants.PROVIDER_ROLEID) {//check against provider company
			count = (Integer) queryForObject("api.user.security.provider", userId);
		} else {
			throw new DataServiceException ("isUserExisting method: Unsupported consumerFlag");
		}
		return (count == 0 ? false: true);
	}
	
	
	public List<String> getAPISecurityFieldRules(String consumerKey) throws DataServiceException {
		List<String> list =  getSqlMapClientTemplate().queryForList("api.user.security.mask.field", consumerKey);
		return list;
	}
	
	public List<APISecurityVO> getAPIListForApplication(String consumerKey) throws DataServiceException {
		try {
			List<APISecurityVO> apiSecurityVO =  getSqlMapClientTemplate().queryForList("api.security", consumerKey);
			return apiSecurityVO;
		} catch(Exception e) {
			logger.error("[SecurityDaoImpl exception:api.security]" + e.getMessage());
			throw new DataServiceException(e.toString());
		}
	}
	//SL-15642 check for firm id in order_management_permission
	public Integer getOmPermissionForFirm(Integer vendorId){
		Integer result = null;
		try {
			result = (Integer) getSqlMapClientTemplate().queryForObject("security.getOmPermission", vendorId);
		} catch(Exception e) {
			logger.error("[SecurityDaoImpl exception:security.getOmPermission]" + e.getMessage());
		}
		return result;
	}
	
	public String getFeatureAvailable(int buyerId) throws DataServiceException{

		String foundFeature = null;
		try{
			Map<String, String> params = new HashMap<String, String>();
			params.put("buyerID", String.valueOf(buyerId));
			params.put("feature", "CONSUMER_EMAIL");
			foundFeature = (String) queryForObject("buyerFeatuerSet.getFeature", params);
    	}catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @SecurityDaoImpl.getFeature() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @SecurityDaoImpl.getFeatureAvailable() due to "+ex.getMessage());
      }
		return foundFeature;
	
	}
}
/*
 * Maintenance History
 * $Log: SecurityDAOImpl.java,v $
 * Revision 1.20  2008/06/03 00:05:33  agupt02
 * ofac
 *
 * Revision 1.19  2008/05/30 02:04:06  agupt02
 * *** empty log message ***
 *
 * Revision 1.18  2008/05/21 23:57:31  akashya
 * I21 Merged
 *
 * Revision 1.15.6.3  2008/05/19 23:47:05  kvuppal
 * OFAC changes
 *
 * Revision 1.15.6.2  2008/05/16 22:20:01  agupt02
 * ofac
 *
 *
 * Revision 1.15.6.2   2008/05/13 19:18:38  agupt02
 * Akhil: added ofac sdn check methods
 *
 * Revision 1.15.6.1  2008/05/08 17:04:23  mhaye05
 * attribute type clean up
 *
 * Revision 1.15  2008/04/26 00:40:10  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.12.1  2008/04/23 11:41:16  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.14  2008/04/23 07:04:28  glacy
 * Shyam: Re-merge of I19_FreeTab branch to HEAD.
 *
 * Revision 1.10.28.1  2008/04/16 04:26:52  paugus2
 * Buyer registration
 *
 * Revision 1.13  2008/04/23 05:17:08  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/02/08 19:18:38  mhaye05
 * updated exception handling
 *
 */