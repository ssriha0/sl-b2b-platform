package com.newco.marketplace.web.delegates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.ActionActivityVO;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.ofac.vo.ContactOfacVO;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.ofac.vo.BuyerOfacVO;
import com.newco.ofac.vo.ProviderOfacVO;

public interface ISecurityDelegate {

	public boolean authenticate(String username,String password);
	public LoginCredentialVO getUserRoles(LoginCredentialVO lvo);
	public AccountProfile getAccountProfile(String username);
	public Map<String,Long> getBuyerId(String userName);
	public Map<String,Long> getVendorId(String userName);
	public List getRoleActivityIdList(String userName);
	public HashMap getProviderIndicators(Integer resourceId);
	public boolean getRegComplete(int vendorId);
	public String getAdminUserName(Integer vendor_id);
	public List<ActionActivityVO> getActionActivities();
	public List<UserActivityVO> getUserActivityRolesList(String userName);
	public SurveyRatingsVO getLifetimeRatings(SecurityContext securityCtx);
	public BuyerOfacVO getBuyerOfacIndicators(int resourceId);
	public boolean checkForBuyerAdmin(String userName) ;
	public ProviderOfacVO getProviderOfacIndicators(int resourceId);
	public void updateBuyerOfacDbFlag(BuyerOfacVO buyerOfacVO);
	public void updateProviderOfacDbFlag(ProviderOfacVO providerOfacVo);

	public ContactOfacVO getBuyerContactIdEin(Integer resourceID);
	public ContactOfacVO getProviderContactIdEin(Integer resourceID);

	public void addAdminDOBForOfac(ContactOfacVO contact);

	public void addBuyerTaxID(ContactOfacVO contact);
	public void addProviderTaxID(ContactOfacVO contact);
	public Boolean hasResourceAdminPermission(Integer resourceId);
	public Location getProviderLocation(String userName);
	//SL-15642 check for firm id in order_management_permission
	public Integer getOmPermissionForFirm(Integer vendorId);
	public boolean simpleAuthenticate(String username, String password);
	public boolean isFeatureAvailable(int buyerId);
}
