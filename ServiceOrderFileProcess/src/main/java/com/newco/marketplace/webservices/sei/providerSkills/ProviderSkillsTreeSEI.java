package com.newco.marketplace.webservices.sei.providerSkills;



import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.newco.marketplace.dto.request.providerSkills.GetProviderSkillTreeRequest;
import com.newco.marketplace.dto.response.providerSkills.GetProviderSkillTreeResponse;
import com.newco.marketplace.exception.webservices.CommonServiceException;
import com.newco.marketplace.exception.webservices.GetMarketSkillsTreeException;



/**
 *  Description of the Interface
 *
 *@author     dmill03
 *@created    August 4, 2007
 */
@WebService(name="ProviderSkillsTreeService", targetNamespace="http://market.serviceorder.soup.com")
public interface ProviderSkillsTreeSEI {

	/**
	 *  Description of the Method
	 *
	 *@param  marketRequest                     Description of the Parameter
	 *@return                                   Description of the Return Value
	 *@exception  GetMarketSkillsTreeException  Description of the Exception
	 */
	@WebMethod(operationName = "invokeGetSkillsTree")
	@WebResult(name = "GetProviderSkillTreeResponse")
	public GetProviderSkillTreeResponse
			invokeGetSkillsTree(GetProviderSkillTreeRequest marketRequest) throws GetMarketSkillsTreeException;


	/**
	 *  Description of the Method
	 *
	 *@return                             Description of the Return Value
	 *@exception  CommonServiceException  Description of the Exception
	 */
	public boolean pingMarketService() throws CommonServiceException;

}

