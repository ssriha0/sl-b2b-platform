package com.newco.marketplace.web.delegatesImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.iBusiness.security.ISecurityBO;
import com.newco.ofac.vo.ContactOfacVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.ofac.vo.BuyerOfacVO;
import com.newco.ofac.vo.ProviderOfacVO;

/**
 * $Revision: 1.26 $ $Author: agupt02 $ $Date: 2008/06/03 00:03:29 $
 */
public class SecurityDelegateImpl implements ISecurityDelegate {

	private SecurityDAO securityAccess = null;

	private ISecurityBO securityBO = null;


	public LoginCredentialVO getUserRoles(LoginCredentialVO lvo)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getRoles(lvo.getUsername());
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String,Long> getBuyerId(String userName){
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getBuyerId(userName);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean getRegComplete(int vendorId){
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getRegComplete(vendorId);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Map<String,Long> getVendorId(String userName){
		SecurityDAO securityBean = getSecurityAccess();
		try {
			return securityBean.getVendorId(userName);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SecurityDAO getSecurityAccess() {
		return securityAccess;
	}

	public void setSecurityAccess(SecurityDAO securityAccess) {
		this.securityAccess = securityAccess;
	}

	public boolean simpleAuthenticate(String username, String password) {
		try {
			return getSecurityBO().simpleAuthenticate(username,password);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean authenticate(String username, String password) {
		try {
			return getSecurityBO().authenticate(username,password);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return false;
	}


	public ISecurityBO getSecurityBO() {
		return securityBO;
	}

	public void setSecurityBO(ISecurityBO securityBO) {
		this.securityBO = securityBO;
	}

	public AccountProfile getAccountProfile(String username) {
		try {
			return securityBO.getAccountProfile(username);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * get the roleactivity id list for given username
	 *
	 */

	public List getRoleActivityIdList(String userName)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getRoleActivityIdList(userName);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * retrieve the provider indicators for the given resource id
	 * @param resourceId
	 * @return
	 */
	public HashMap getProviderIndicators(Integer resourceId)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getProviderIndicators(resourceId);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *
	 */
	public String getAdminUserName(Integer vendor_id)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getAdminUserName(vendor_id);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *
	 * @param userName
	 * @return
	 */
	public List<UserActivityVO> getUserActivityRolesList(String userName)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getUserActivityRolesList(userName);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;

	}
	public List<ActionActivityVO> getActionActivities(){

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

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISecurityDelegate#getLifetimeRatings(com.newco.marketplace.auth.SecurityContext)
	 */
	public SurveyRatingsVO getLifetimeRatings(SecurityContext securityCtx){
		try {
			return securityBO.getLifetimeRatings(securityCtx);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * check if logged in buyer is admin or not
	 * @param resourceId
	 * @return
	 */
	public boolean checkForBuyerAdmin(String userName)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.checkForBuyerAdmin(userName);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * retrieve the buyer ofacs indicators for the given resource id
	 * @param resourceId
	 * @return
	 */
	public BuyerOfacVO getBuyerOfacIndicators(int resourceId)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getBuyerOfacIndicators(resourceId);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * retrieve the buyer ofacs indicators for the given resource id
	 * @param resourceId
	 * @return
	 */
	public ProviderOfacVO getProviderOfacIndicators(int resourceId)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getProviderOfacIndicators(resourceId);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateBuyerOfacDbFlag(BuyerOfacVO buyerOfacVO)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			securityBean.updateBuyerOfacDbFlag(buyerOfacVO);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}

	}
	public void updateProviderOfacDbFlag(ProviderOfacVO providerOfacVo)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			securityBean.updateProviderOfacDbFlag(providerOfacVo);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}

	}



	public ContactOfacVO getBuyerContactIdEin(Integer resourceID)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getBuyerContactIdEin(resourceID);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}


	public ContactOfacVO getProviderContactIdEin(Integer resourceID)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.getProviderContactIdEin(resourceID);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void addAdminDOBForOfac(ContactOfacVO contact)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			 securityBean.addAdminDOBForOfac(contact);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
	}

	public void addBuyerTaxID(ContactOfacVO contact)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			 securityBean.addBuyerTaxID(contact);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}


	}
	public void addProviderTaxID(ContactOfacVO contact)
	{
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			 securityBean.addProviderTaxID(contact);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}


	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISecurityDelegate#hasResourceAdminPermission(java.lang.Integer)
	 */
	public Boolean hasResourceAdminPermission(Integer resourceId) {
		SecurityDAO securityBean = getSecurityAccess();
		try
		{
			return securityBean.hasResourceAdminPermission(resourceId);
		}
		catch(DataServiceException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}


	public Location getProviderLocation(String userName)
	{		
		try {
			return getSecurityBO().getProviderLocation(userName);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//SL-15642 check for firm id in order_management_permission
	public Integer getOmPermissionForFirm(Integer vendorId){
		Integer result = null;
		try {
			result = getSecurityBO().getOmPermissionForFirm(vendorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result; 
	}
	
	@Override
	public boolean isFeatureAvailable(int buyerId) {
		SecurityDAO securityBean = getSecurityAccess();
		boolean isFeatureAvailable = false;
		try {
			String foundFeature = securityBean.getFeatureAvailable(buyerId);
			if (StringUtils.isNotBlank(foundFeature)) {
				isFeatureAvailable = true;
			}
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return isFeatureAvailable;

	}
}
