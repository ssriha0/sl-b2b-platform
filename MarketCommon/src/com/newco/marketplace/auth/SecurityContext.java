package com.newco.marketplace.auth;

import java.io.Serializable;
import java.util.Map;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;

public class SecurityContext implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = 17801617092379677L;

	// this is a dummy comment to test cruisecontrol setup
	public static final String VALID = "VALID";

	public static final String INVALID = "INVALID";
	
	private String username = "";

	private Integer roleId;

	private boolean regComplete;

	private String role;

	private Integer companyId = -1;

	private Integer vendBuyerResId = -1;
	
	private Integer clientId = -1;
	
	private String status;
	
	// this decides from where methods are being called , from webservice or Frontend
	private String actionSource;

    //private List roles;

	private LoginCredentialVO roles;
	
	private boolean adopted;

	//added for providers
	private Map<String , UserActivityVO> roleActivityIdList;
	private boolean primaryInd= false;
	private boolean mktPlaceInd = false;
	private Integer adminResId = -1;
	private boolean dispatchInd = false;
	private boolean providerAdminInd = false;

	private boolean slAdminInd = false;
	private Integer adminRoleId = -1;

	private String slAdminFName;
	private String slAdminLName;
	private String slAdminUName;
	private boolean autoACH;
	private boolean hasProviderAdminPermissions = false;
	private Long accountID;
	private Integer spnMonitorAvailable;
	private int loginAuditId = 0;
	private int activeSessionAuditId = 0;
	
	private String providerLocation;
	private boolean increaseSpendLimitInd = false;
	private double maxSpendLimitPerSO;
	private Integer buyerAdminContactId = null;
	boolean buyerLoggedInd = false;
	
	private int unArchivedCARRulesAvailable = 0;
	private int activePendingCARRulesPresent = 0;
	
	public boolean isBuyerLoggedInd() {
		return buyerLoggedInd;
	}

	public void setBuyerLoggedInd(boolean buyerLoggedInd) {
		this.buyerLoggedInd = buyerLoggedInd;
	}

	public Integer getSpnMonitorAvailable() {
		return spnMonitorAvailable;
	}

	public void setSpnMonitorAvailable(Integer spnMonitorAvailable) {
		this.spnMonitorAvailable = spnMonitorAvailable;
	}

	public boolean isBuyer() {
		LoginCredentialVO loginCredentialVo = getRoles();

		if(loginCredentialVo == null) {
			// this value is really not determined. 
			return false;
		}

		Integer roleId = loginCredentialVo.getRoleId();
		if(roleId != null && (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID)) {
			buyerLoggedInd=true;
			return true;
		}

		return false;
	}

	public boolean isSlAdminInd() {
		return slAdminInd;
	}

	// added for refection
	public boolean getSLAdminInd(){
		return isSlAdminInd();
	}

	public boolean getIsRegComplete() {
		return isRegComplete();
	}

	public void setSlAdminInd(boolean slAdminInd) {
		this.slAdminInd = slAdminInd;
	}

	public Integer getAdminResId() {
		return adminResId;
	}

	public void setAdminResId(Integer adminResId) {
		this.adminResId = adminResId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public SecurityContext(String username)
	{
		this.username = username;
	}

	public SecurityContext(String username, String role, String status) {
		super();
		this.username = username;
		this.role = role;
		this.status = status;
	}

    public LoginCredentialVO getRoles() {

        return roles;
    }

    public void setRoles(LoginCredentialVO roles) {

        this.roles = roles;
    }

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getVendBuyerResId() {
		return vendBuyerResId;
	}

	public void setVendBuyerResId(Integer vendBuyerResId) {
		this.vendBuyerResId = vendBuyerResId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Map<String ,UserActivityVO> getRoleActivityIdList() {
		return roleActivityIdList;
	}

	public void setRoleActivityIdList(Map<String ,UserActivityVO> roleActivityIdList) {
		this.roleActivityIdList = roleActivityIdList;
	}

	public boolean isPrimaryInd() {
		return primaryInd;
	}

	public void setPrimaryInd(boolean primaryInd) {
		this.primaryInd = primaryInd;
	}

	public boolean isMktPlaceInd() {
		return mktPlaceInd;
	}

	public void setMktPlaceInd(boolean mktPlaceInd) {
		this.mktPlaceInd = mktPlaceInd;
	}

	public boolean isRegComplete() {
		return regComplete;
	}

	public void setRegComplete(boolean regComplete) {
		this.regComplete = regComplete;
	}

	public Integer getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(Integer adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	public String getSlAdminLName() {
		return slAdminLName;
	}

	public void setSlAdminLName(String slAdminLName) {
		this.slAdminLName = slAdminLName;
	}

	public String getSlAdminFName() {
		return slAdminFName;
	}

	public void setSlAdminFName(String slAdminFName) {
		this.slAdminFName = slAdminFName;
	}

	public String getSlAdminUName() {
		return slAdminUName;
	}

	public void setSlAdminUName(String slAdminUName) {
		this.slAdminUName = slAdminUName;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getActionSource() {
		return actionSource;
	}

	public void setActionSource(String actionSource) {
		this.actionSource = actionSource;
	}
	

	public boolean isAutoACH() {
		return autoACH;
	}

	public void setAutoACH(boolean autoACH) {
		this.autoACH = autoACH;
	}

	/**
	 * @return the hasProviderAdminPermissions
	 */
	public boolean isHasProviderAdminPermissions() {
		return hasProviderAdminPermissions;
	}

	/**
	 * @param hasProviderAdminPermissions the hasProviderAdminPermissions to set
	 */
	public void setHasProviderAdminPermissions(boolean hasProviderAdminPermissions) {
		this.hasProviderAdminPermissions = hasProviderAdminPermissions;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public int getLoginAuditId() {
		return loginAuditId;
	}

	public void setLoginAuditId(int loginAuditId) {
		this.loginAuditId = loginAuditId;
	}

	public int getActiveSessionAuditId() {
		return activeSessionAuditId;
	}

	public void setActiveSessionAuditId(int activeSessionAuditId) {
		this.activeSessionAuditId = activeSessionAuditId;
	}

	public String getProviderLocation() {
		return providerLocation;
	}

	public void setProviderLocation(String providerLocation) {
		this.providerLocation = providerLocation;
	}

	public boolean isIncreaseSpendLimitInd() {
		return increaseSpendLimitInd;
	}

	public void setIncreaseSpendLimitInd(boolean increaseSpendLimitInd) {
		this.increaseSpendLimitInd = increaseSpendLimitInd;
	}

	public double getMaxSpendLimitPerSO() {
		return maxSpendLimitPerSO;
	}

	public void setMaxSpendLimitPerSO(double maxSpendLimitPerSO) {
		this.maxSpendLimitPerSO = maxSpendLimitPerSO;
	}

	public String toString() {
		StringBuffer toString = new StringBuffer();
		toString.append("SecurityContext:: username: ").append(username).
		append(", roleId: ").append(roleId).
		append(", role: ").append(role).
		append(", companyId: ").append(companyId).
		append(", vendBuyerResId: ").append(vendBuyerResId).
		append(", clientId: ").append(clientId).
		append(", status: ").append(status);
		return toString.toString();
	}

	public Integer getBuyerAdminContactId() {
		return buyerAdminContactId;
	}

	public void setBuyerAdminContactId(Integer buyerAdminContactId) {
		this.buyerAdminContactId = buyerAdminContactId;
	}

	public boolean isAdopted() {
		return adopted;
	}

	public void setAdopted(boolean adopted) {
		this.adopted = adopted;
	}
	
	public boolean isDispatchInd() {
		return dispatchInd;
	}

	public void setDispatchInd(boolean dispatchInd) {
		this.dispatchInd = dispatchInd;
	}
	
	public boolean isProviderAdminInd() {
		return providerAdminInd;
	}

	public void setProviderAdminInd(boolean providerAdminInd) {
		this.providerAdminInd = providerAdminInd;
	}

	public int getUnArchivedCARRulesAvailable() {
		return unArchivedCARRulesAvailable;
	}

	public void setUnArchivedCARRulesAvailable(int unArchivedCARRulesAvailable) {
		this.unArchivedCARRulesAvailable = unArchivedCARRulesAvailable;
	}
	
	public int getActivePendingCARRulesPresent() {
		return activePendingCARRulesPresent;
	}

	public void setActivePendingCARRulesPresent(int activePendingCARRulesPresent) {
		this.activePendingCARRulesPresent = activePendingCARRulesPresent;
	}



	
}// SecurityContext.
