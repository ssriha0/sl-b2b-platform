package com.newco.marketplace.web.action.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;

public class SLSimpleBaseAction extends SLBaseAction {

	private static final Logger logger = Logger.getLogger(SLSimpleBaseAction.class.getName());
	protected ISOWizardPersistDelegate isoWizardPersistDelegate;
	protected ISOWizardFetchDelegate fetchDelegate;
	protected String landingAction;
	private String appMode;
	private String view;
	
	public String getLandingAction() {
		return landingAction;
	}
	public void setLandingAction(String landingAction) {
		this.landingAction = landingAction;
	}
	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}
	public void setIsoWizardPersistDelegate(
			ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}
	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}
	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}
	public String getAppMode() {
		return appMode;
	}
	public void setAppMode(String appMode) {
		this.appMode = appMode;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
	protected Map<String, Object> loadBalance(ISOMonitorDelegate serviceOrderDelegate){
		Map<String, Object> balanceMap = new HashMap<String, Object>();
		if(get_commonCriteria() == null)
			return balanceMap;
		
		Integer buyerId = get_commonCriteria().getCompanyId();
		String roleType = this._commonCriteria.getRoleType();
		roleType = roleType != null ? roleType.toUpperCase() : roleType;

		AjaxCacheVO ajax = new AjaxCacheVO();

		ajax.setCompanyId(buyerId);
		ajax.setRoleType(roleType);

		Double availBalance = null;
		Double currentBalance = null;
		try {
			availBalance = serviceOrderDelegate.getAvailableBalance(ajax);
			currentBalance = serviceOrderDelegate.getCurrentBalance(ajax);
			
			balanceMap.put("availBalance", availBalance);
			balanceMap.put("currentBalance", currentBalance);
		}catch (BusinessServiceException e) {
			logger.error("Error getting buyer available and current balance");
		}
		return balanceMap;
	}

}
