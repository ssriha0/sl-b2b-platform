package com.newco.marketplace.business.businessImpl.usermanagement;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.provider.EmailTemplateBOImpl;
import com.newco.marketplace.business.iBusiness.usermanagement.UserManagementBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.EmailVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.permission.PermissionSetVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.provider.TemplateDaoImpl;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.persistence.iDao.usermanagement.UserManagementDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.AchConstants;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.common.UserProfilePermissionSetVO;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.provider.TemplateVo;
import com.newco.marketplace.webservices.base.Template;
import com.sears.os.business.ABaseBO;
import com.newco.marketplace.aop.AOPHashMap;

public class UserManagementBOImpl extends ABaseBO implements UserManagementBO {

	private static final Logger logger = Logger.getLogger(UserManagementBOImpl.class.getName());
	private UserManagementDao userManageDao;
	private LookupDao lookupDao;
	private EmailTemplateBOImpl emailTemplateBean;
	private ITemplateDao templateDao;
	private AlertDao alertDao;


	public Boolean isUserAvailable(String username) {
		try {
			logger.debug("checking if username " + username + " is available");
			return userManageDao.isUserAvailable(username);

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());

		}
		return false;
	}

	public UserVO getUser(String username, Integer roleId) {

		try {
			logger.debug("retrieving username " + username + " roleId "
					+ roleId);

			return userManageDao.getUser(username, roleId);

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return new UserVO();
	}

	public List<UserVO> getSLAdminUserList() {

		try {
			logger.debug("retrieving SL admin userlist");
			return userManageDao.getServiceLiveAdmins();
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return new ArrayList<UserVO>();
	}

	public List<UserVO> getBuyerUserList(Integer buyerId) {

		try {
			logger.debug("retrieving buyer " + buyerId + " userlist");
			return userManageDao.getBuyerResources(buyerId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return new ArrayList<UserVO>();
	}

	public List<PermissionSetVO> getUserPermissionSets(
			UserProfilePermissionSetVO userProfilePermissionSetVO) {
		try {
			logger.debug("retrieving user permission sets for role "
					+ userProfilePermissionSetVO.getRoleId());
			return userManageDao.getPermissionSets(userProfilePermissionSetVO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return new ArrayList<PermissionSetVO>();
	}

	public Boolean removeUser(String username, String modifierUserName) {

		List<String> affectedServiceOrders = null;
		try {
			logger.debug("removing/disabling user " + username);
			// update disable flag in database
			userManageDao.removeUser(username);

			if (isUserBuyer(username)) {
				Integer buyerId = userManageDao.getBuyerAdminId(username);
				affectedServiceOrders = userManageDao.updateOpenServiceOrders(
						username, buyerId);

				if (affectedServiceOrders != null) {
					sendRemovedBuyerEmailConfirmation(username,
							affectedServiceOrders);
				}
			}
			// let's log this change and email the buyer admin
			userManageDao.logUserRemoval(username, modifierUserName);
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}
	public Boolean isSuperAdminUser(String modifierusername) {
		try {
			logger.debug("checking if username " + modifierusername + " is Super Admin");
			return userManageDao.isSuperAdminUser(modifierusername);

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());

		}
		return false;
	}
	public Boolean saveUser(UserVO user, String modifierUserName) {

		if (!isValidSaveUserRequest(user, modifierUserName)) {
			return false;
		}

		try {
			// check if this is a new user and if username is avail
			Boolean isNewUser = isUserAvailable(user.getUserName());
			Boolean isSuperAdmin = isSuperAdminUser(modifierUserName);  // JIRA
			if (isNewUser) {
				String password = AdminUtil.generatePassword();
				user.setPassword(password);
				user.setPassword(CryptoUtil.encrypt(password));

				if (saveNewUser(user)) {
					userManageDao.logUserCreation(user.getUserName(),modifierUserName);
					user.setPassword(password);
					return true;
				}
			} else if (userManageDao.saveUser(user, false,isSuperAdmin)) {
				userManageDao.logUserEdit(user.getUserName(), modifierUserName);
				return true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}

	/**
	 * @param userManageDao
	 *            the userManageDao to set
	 */
	public void setUserManageDao(UserManagementDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.business.iBusiness.usermanagement.UserManagementBO
	 * #getCompanyRoles(java.lang.Integer)
	 */
	public List<LookupVO> getCompanyRoles(Integer roleId) {
		try {
			return lookupDao.loadCompanyRole(roleId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return new ArrayList<LookupVO>();
	}

	private Boolean isUserBuyer(String username) {
		return userManageDao.isUserBuyerType(username);
	}

	private Boolean saveNewUser(UserVO user) {

		try {

			String password = AdminUtil.generatePassword();
			user.setPassword(CryptoUtil.encrypt(password));

			if (userManageDao.saveUser(user, true,false)) {
				user.setResourceId(userManageDao.getUserResourceId(user.getUserName()));
				sendNewUserEmailConfirmation(user, password);
				return true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}

	/**
	 * @param lookupDao
	 *            the lookupDao to set
	 */
	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	private AlertTask getAlertTaskForProRegistration(Map<String, Object> aopHashMap) {		
		Integer templateId  = (Integer)(aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID));
		Template template = (Template)aopHashMap.get(templateId.toString());//getTemplateDetailForAT(templateId);
		
		AlertTask alertTask = new AlertTask();

		Date date = new Date();

		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
 		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue((String)aopHashMap.get("InputValue"));
		return alertTask; 	

	}
	
	private void sendNewUserEmailConfirmation(UserVO user,String unEncryptedPassword) {
		if (user == null || unEncryptedPassword == null	|| unEncryptedPassword.equalsIgnoreCase("")) {
			logger.warn("Cannot send confirmation email because UserVO user or password are null or not valid");
			logger.warn("Exiting sendNewUserEmailConfirmation()");
			return;
		}
		try {
			
			int eid = 0;
			int resourceId=0;
			EmailVO email = new EmailVO();
			TemplateVo template = new TemplateVo();
			HashMap<String, Object> vc = new AOPHashMap();
			UserVO adminUserInfo = new UserVO();

			// Common VelocityContext mappings
			vc.put("USERNAME", user.getUserName());
			vc.put("PASSWORD", unEncryptedPassword);
			vc.put("WebsiteURL",PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SERVICELIVE_URL));
			email.setTo(user.getEmail());
			adminUserInfo = userManageDao.getBuyerAdminInfo(user.getBuyerId(),
					user.getResourceId());
			String tempidstr = null;
			if (user.getRoleTypeId() == OrderConstants.BUYER_ROLEID) {
				eid = AchConstants.EMAIL_PROFESSIONAL_FIRM_REGISTRATION;
				tempidstr = Constants.UserManagement.NEW_BUYER_USER_TEMPLATE_ID;

				vc.put("COMPANYNAME", adminUserInfo.getCompanyName());
				vc.put("COMPANYID", adminUserInfo.getBuyerId());
				vc.put("BUYERADMINFIRSTNAME", adminUserInfo.getFirstName());
				vc.put("BUYERADMINLASTNAME", adminUserInfo.getLastName());
				vc.put("BUYERADMINUSERID", adminUserInfo.getBuyerId());
				vc.put("BUYERADMINEMAIL", adminUserInfo.getEmail());
				vc.put("USERID", adminUserInfo.getResourceId());
			}else if (user.getRoleTypeId() == OrderConstants.NEWCO_ADMIN_ROLEID) {
				resourceId=userManageDao.getSLAdminUserId(user.getUserName());
				eid = AchConstants.EMAIL_SLADMIN_REGISTRATION;
				tempidstr = Constants.UserManagement.NEW_SL_ADMIN_USER_TEMPLATE_ID;
				vc.put("USERID", resourceId);
			}else if (user.getRoleTypeId() == OrderConstants.PROVIDER_ROLEID) {
				eid = AchConstants.EMAIL_PROVIDER_REGISTRATION;
				tempidstr = Constants.UserManagement.NEW_PROVIDER_USER_TEMPLATE_ID;
				vc.put("FIRSTNAME", adminUserInfo.getFirstName());
			}
			
			template = templateDao.getTemplate(tempidstr);
			Template templateAT = new Template();
			templateAT.setTemplateId(template.getTemplateId());
			templateAT.setTemplateTypeId(template.getTemplateTypeId());
			templateAT.setPriority(template.getPriority());
			templateAT.setEid(template.getTemplateEid());
			templateAT.setTemplateFrom(template.getTemplateFrom());
			templateAT.setSource(template.getTemplateSource());
			templateAT.setTemplateFrom(template.getTemplateFrom());
			
			vc.put(AOPConstants.AOP_USER_EMAIL, email.getTo());
			vc.put(AOPConstants.AOP_TEMPLATE_ID,template.getTemplateId());
			
			vc.put("InputValue",vc.toString());
			vc.put(template.getTemplateId().toString(),templateAT);
			
			AlertTask alert = getAlertTaskForProRegistration(vc);
			alertDao.addAlertToQueue(alert);
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
			logger.debug(e.getCause());
		}

	}
	
	private void sendRemovedBuyerEmailConfirmation(String username,
			List<String> affectedServiceOrders) {

		try {
			EmailVO email = new EmailVO();
			TemplateVo template = new TemplateVo();
			StringWriter sw = new StringWriter();
			HashMap<String, Object> vc = new AOPHashMap();

			UserVO user = userManageDao.getUser(username,OrderConstants.BUYER_ROLEID);
			UserVO buyerAdmin = userManageDao.getBuyerAdminInfo(user.getBuyerId(), user.getResourceId());

			vc.put("BUYERFIRSTNAME", user.getFirstName());
			vc.put("BUYERLASTNAME", user.getLastName());
			vc.put("USERID", user.getResourceId());
			vc.put("BUYERUSERNAME", buyerAdmin.getFirstName() + " "	+ buyerAdmin.getLastName());
			vc.put("USERNAME", user.getUserName());

			if (!affectedServiceOrders.isEmpty()) {
				StringBuffer serviceOrders = new StringBuffer();
				for (String so : affectedServiceOrders) {
					serviceOrders.append(so + "<br>");
				}
				vc.put("SERVICEORDERS", serviceOrders);
			} else {
				vc.put("SERVICEORDERS", "None");
			}
			
			int eid = AchConstants.TEMPLATE_BUYER_USER_REMOVED;
			
			Template templateAT = new Template();
			template = templateDao.getTemplate(Constants.UserManagement.REMOVE_BUYER_USER_TEMPLATE_ID);
			templateAT.setTemplateId(template.getTemplateId());
			templateAT.setTemplateTypeId(template.getTemplateTypeId());
			templateAT.setPriority(template.getPriority());
			templateAT.setEid(template.getTemplateEid());
			templateAT.setTemplateFrom(template.getTemplateFrom());
			templateAT.setSource(template.getTemplateSource());

			vc.put(AOPConstants.AOP_USER_EMAIL, buyerAdmin.getEmail());
			vc.put(AOPConstants.AOP_TEMPLATE_ID,template.getTemplateId());
			vc.put("InputValue",vc.toString());
			vc.put(template.getTemplateId().toString(),templateAT);
			
			AlertTask alert = getAlertTaskForProRegistration(vc);
			alertDao.addAlertToQueue(alert);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
	}

	/**
	 * @param emailTemplateBean
	 *            the emailTemplateBean to set
	 */
	public void setEmailTemplateBean(EmailTemplateBOImpl emailTemplateBean) {
		this.emailTemplateBean = emailTemplateBean;
	}

	/**
	 * @param templateDao
	 *            the templateDao to set
	 */
	public void setTemplateDao(TemplateDaoImpl templateDao) {
		this.templateDao = templateDao;
	}

	private Boolean isValidSaveUserRequest(UserVO user, String modifierUserName) {
		if (user == null) {
			logger.error("UserVO user is null. Exiting saveUser() with false");
			return false;
		}
		if (user.getRoleTypeId() == 0) {
			logger
					.error("UserVO.user.getRoleTypeId() is zero. This must be a valid roleTypeId. 1,2,3, etc.\n Exiting operation");
			return false;
		}
		if (user.getUserName() == null
				|| user.getUserName().equalsIgnoreCase("")) {
			logger
					.error("UserVO.user.getUserName() cannot be empty. Exiting operation");
			return false;
		}
		if (user.getUserActivities() == null || user.getUserRoles() == null) {
			logger
					.error("UserVO.user roles or activities are null. Exiting save operation");
			return false;
		}
		if (user.getRoleTypeId() == OrderConstants.BUYER_ROLEID
				&& (user.getBuyerId() == null || user.getBuyerId() <= 0)) {
			logger
					.error("UserVO.user buyerId cannot be null or 0. Exiting save operations");
			return false;
		}
		if (user.getRoleTypeId() == OrderConstants.BUYER_ROLEID
				&& user.getMaxSpendLimitPerSO() == null) {
			logger
					.error("UserVO.user maxSpendLimistPerSO cannot be null for a buyer");
			return false;
		}
		Integer latestTerms = new Integer(0);
		try {
			latestTerms = lookupDao.getTermsConditionsContent(
					Constants.UserManagement.BUYER_TERMS_CONDITIONS_AGREEMENT)
					.getTermsCondId();
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
		}
		if (user.getRoleTypeId() == OrderConstants.BUYER_ROLEID
				&& (user.getTerms_cond_id() == null || user.getTerms_cond_id()
						.intValue() != latestTerms.intValue())) {
			logger
					.error("UserVO.user terms & conditions cannot be null for a buyer");
			return false;
		}

		return true;
	}

	public String getSuperAdminUserName() {
		String userName = null;
		try {
			userName = userManageDao.getSuperAdminUserName();
		} catch (DataServiceException e) {
			logger.error("Unable to retrieve Super Admin user name", e);
		}

		return userName;
	}

	/**
	 * 
	 * @param userName
	 */
	public void unlockUser(String userName) {
		userManageDao.unlockUser(userName);
	}

	/*
	 * Re sending the welcome email if user hasn't changed password.Users other
	 * than simple user don't have an entry in alert table hence sending in line
	 * email.
	 */
	public boolean resendWelcomeMail(String userName, String emailAddress) {
		try {
			UserVO user = getUser(userName, new Integer(-1));
			if (user.getRoleTypeId() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				return alertDao.resendWelcomeMail(userName, emailAddress);
			} else {
				String password = AdminUtil.generatePassword();
				user.setPassword(CryptoUtil.encrypt(password));
				if (userManageDao.updatePassword(user)) {
					user.setResourceId(userManageDao.getUserResourceId(user
							.getUserName()));
					sendNewUserEmailConfirmation(user, password);
					return true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return false;
	}
	public boolean isUserAssociatedWithLoggedUser(String userName, Integer loggedInUserId){
		boolean result = false;
		try {
			Integer buyerId = userManageDao.getBuyerAdminId(userName);
			if(loggedInUserId >0 && buyerId > 0 && loggedInUserId.equals(buyerId)){
				result = true;
			}			
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return result;
	}
	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDaoImpl) {
		this.alertDao = alertDaoImpl;
	}
	//method called by our webservice after opting out user from sms
	public void optOutSMS(String smsNo,String firstName,String lastName){
		try{
			alertDao.optOutSMS(smsNo);
			ArrayList<String> email= new ArrayList<String>();
			String emails = "";
			email=alertDao.getEmailFromSmsNo(smsNo);
			if (email!=null){
				
				if(firstName == null||lastName == null){
					firstName = "";
					lastName = "";
				}
			
				Iterator emailIter= email.iterator();
				while(emailIter.hasNext()){
					String mail=(String)emailIter.next()+AlertConstants.EMAIL_DELIMITER;
					emails+=mail;
				}
				AlertTask alertTask = getAlertTaskForEmail(smsNo,firstName,lastName,emails);
				alertDao.addAlertToQueue(alertTask);
			}			
		} catch (DataServiceException e){
			logger.error("Opting out SMS as alternate contact method-->DataServiceException-->", e);
		}
	}
	
	private AlertTask getAlertTaskForEmail(String smsNo,String firstName,String lastName,String email){

		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		String templateInput = null;
		templateInput = AlertConstants.SMS_NO + AlertConstants.EQUALS + smsNo + AlertConstants.SMS_DELIMITOR + AlertConstants.FIRST_NAME + AlertConstants.EQUALS + firstName +AlertConstants.SMS_DELIMITOR + AlertConstants.LAST_NAME + AlertConstants.EQUALS + lastName;
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_EMAIL);
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(AlertConstants.TEMPLATE_SMS_EMAIL);
		alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo(email);
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(AlertConstants.SMS_PRIORITY);
		alertTask.setTemplateInputValue(templateInput);
		return alertTask;
	}
	
	
	public List<ActivityVO> getProviderActivityList(String userName)
	{
		
		try {
			logger.debug("retrieving provider activity list"
					+ userName);
			return userManageDao.getProviderActivityList(userName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
			
		}
		return null;
	}

	public void expireMobileTokenforFrontEnd (Integer resourceId,String tokenStatus)
	{
		try {
			logger.debug("expiring mobile token for provider"
					+ resourceId);
			userManageDao.expireMobileTokenforFrontEnd(resourceId,tokenStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
	}
	
}
