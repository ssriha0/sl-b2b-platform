package com.newco.marketplace.webservices.dispatcher.providerSkills;

import java.util.ArrayList;

import com.newco.marketplace.business.businessImpl.skillassign.SkillAssignBusinessBean;
import com.newco.marketplace.dto.request.providerSkills.GetProviderSkillTreeRequest;
import com.newco.marketplace.dto.request.skillTree.SkillAssignRequest;
import com.newco.marketplace.dto.response.providerSkills.GetProviderSkillTreeResponse;
import com.newco.marketplace.dto.response.skillTree.SkillAssignResponse;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.webservices.CommonServiceException;
import com.newco.marketplace.exception.webservices.GetMarketSkillsTreeException;
import com.newco.marketplace.webservices.base.response.ABaseResponse;
import com.sears.os.service.ServiceConstants;

public class ProviderSkillTreeDispatchRequestor extends ABaseRequestDispatcher {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2012391818192379594L;

	public GetProviderSkillTreeResponse 
					invokeGetSkillsTree(GetProviderSkillTreeRequest marketRequest) throws GetMarketSkillsTreeException {
		
		GetProviderSkillTreeResponse response = new GetProviderSkillTreeResponse();
		
		SkillAssignBusinessBean providerBusinessBean =
				(SkillAssignBusinessBean)
					getBusinessBeanFacility(SKILL_TREE_BUSINESS_BEAN_REF);
		
		SkillAssignRequest skillsRequest = new SkillAssignRequest();
		if(marketRequest.getSkillsNodeId() == -1)
		{
			return (GetProviderSkillTreeResponse)getFailureResponse();
		}
		skillsRequest.setRootNodeId(marketRequest.getSkillsNodeId());
		skillsRequest.setNodeId(marketRequest.getSkillsNodeId());
			
		try {
			SkillAssignResponse skillResponse = providerBusinessBean.getSkills(skillsRequest);
			
			ArrayList skillTreeList = skillResponse.getSkillTreeList();
			
			response.setServiceResponse(skillTreeList);
			response.setCode(ServiceConstants.VALID_RC);
		} 
		catch (BusinessServiceException e)
		{
			ABaseResponse errorResponse = 
							getFailureResponse();
			errorResponse.setMessage(e.getMessage());
			return (GetProviderSkillTreeResponse)errorResponse;
		}
		return response;
	}

	public boolean pingMarketService() throws CommonServiceException {
		// TODO Auto-generated method stub
		return true;
	}

}
