package com.newco.marketplace.web.action.wallet;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 23:33:12 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class ServiceLiveWalletAction extends SLBaseAction implements Preparable {

	private static final long serialVersionUID = 4781452402648594752L;

	private static final Logger logger = Logger.getLogger(ServiceLiveWalletAction.class.getName());
	
	public ServiceLiveWalletAction(ISOMonitorDelegate delegate) {
		super.soMonitorDelegate = delegate;
	}
	
	public void prepare() throws Exception {
		try {
			logger.debug("ServiceLiveWalletAction-->prepare()");
			createCommonServiceOrderCriteria();
			loadAvailableAndCurrentBalance();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public String execute() {
		return SUCCESS;
	}
	
	public void loadAvailableAndCurrentBalance()  throws Exception {
		double availBalance = 0.0;
		double currentBalance = 0.0;	
		if(get_commonCriteria() == null) {
			return;
		}
		
		String roleType = this._commonCriteria.getRoleType();
		roleType = (roleType != null ? roleType.toUpperCase() : roleType);

		AjaxCacheVO ajax = new AjaxCacheVO();

		ajax.setCompanyId(get_commonCriteria().getCompanyId());
		ajax.setRoleType(roleType);

		availBalance = soMonitorDelegate.getAvailableBalance(ajax);
		String abFormat = java.text.NumberFormat.getCurrencyInstance().format(availBalance);
		setAttribute("AvailableBalance", abFormat);
		currentBalance = soMonitorDelegate.getCurrentBalance(ajax);
		String currBalanceFormatted = java.text.NumberFormat.getCurrencyInstance().format(currentBalance);
		setAttribute("CurrentBalance", currBalanceFormatted);
	}
	
}