package com.newco.marketplace.web.action.spn;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.dto.vo.spn.SPNCompanyProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;

public class SPNMonitorCustomAction extends SPNMonitorAction {

	public SPNMonitorCustomAction(ISPNMonitorBO spnMonitorBO,ISelectProviderNetworkBO spnCreateUpdateBO) {
		super(spnMonitorBO, spnCreateUpdateBO);
		// TODO Auto-generated constructor stub
	}
	
	
}
