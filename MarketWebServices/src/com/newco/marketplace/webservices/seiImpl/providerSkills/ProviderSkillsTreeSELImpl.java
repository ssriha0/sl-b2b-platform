package com.newco.marketplace.webservices.seiImpl.providerSkills;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.newco.marketplace.dto.request.providerSkills.GetProviderSkillTreeRequest;
import com.newco.marketplace.dto.response.providerSkills.GetProviderSkillTreeResponse;
import com.newco.marketplace.exception.webservices.CommonServiceException;
import com.newco.marketplace.exception.webservices.GetMarketSkillsTreeException;
import com.newco.marketplace.webservices.base.AbstractServiceInvoker;
import com.newco.marketplace.webservices.dispatcher.providerSkills.ProviderSkillTreeDispatchRequestor;
import com.newco.marketplace.webservices.sei.providerSkills.ProviderSkillsTreeSEI;

/**
 *  Description of the Class
 *
 *@author     dmill03
 *@created    August 4, 2007
 */
@WebService(endpointInterface="com.newco.services.market.sei.ProviderSkillsTreeSEI", serviceName="ProviderSkillsTreeService")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class ProviderSkillsTreeSELImpl extends AbstractServiceInvoker implements
		ProviderSkillsTreeSEI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -207666783517071525L;
	ProviderSkillTreeDispatchRequestor requestDispatcher = new ProviderSkillTreeDispatchRequestor();
	
	/**
	 *  Description of the Method
	 *
	 *@return                             Description of the Return Value
	 *@exception  CommonServiceException  Description of the Exception
	 */
	public boolean pingMarketService() throws CommonServiceException {
		// TODO Auto-generated method stub
		return true;
	}



	/**
	 *  Description of the Method
	 *
	 *@param  marketRequest                     Description of the Parameter
	 *@return                                   Description of the Return Value
	 *@exception  GetMarketSkillsTreeException  Description of the Exception
	 */
	public GetProviderSkillTreeResponse invokeGetSkillsTree(
			GetProviderSkillTreeRequest marketRequest) throws GetMarketSkillsTreeException {
		
		GetProviderSkillTreeResponse response = null;
		response = 	getRequestDispatcher().invokeGetSkillsTree(marketRequest);	
		return response;
	}



	public ProviderSkillTreeDispatchRequestor getRequestDispatcher() {
		return requestDispatcher;
	}



	public void setRequestDispatcher(
			ProviderSkillTreeDispatchRequestor requestDispatcher) {
		this.requestDispatcher = requestDispatcher;
	}
}

