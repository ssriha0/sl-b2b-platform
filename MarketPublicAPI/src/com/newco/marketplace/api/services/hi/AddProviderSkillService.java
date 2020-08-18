package com.newco.marketplace.api.services.hi;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.addproviderskill.AddProviderSkillRequest;
import com.newco.marketplace.api.beans.hi.account.firm.addproviderskill.AddProviderSkillResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderResourceMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderResourceBO;
import com.newco.marketplace.vo.hi.provider.ProviderSkillVO;

@Namespace("http://www.servicelive.com/namespaces/addProviderSkills")
@APIRequestClass(AddProviderSkillRequest.class)
@APIResponseClass(AddProviderSkillResponse.class)
public class AddProviderSkillService extends BaseService{
	
	private static final Logger LOGGER= Logger.getLogger(AddProviderSkillService.class);
	private IProviderResourceBO providerResourceBO;
    private ProviderResourceMapper providerResourceMapper;

    @Override
	public IAPIResponse execute(APIRequestVO apiVO) {
    	//Declaring variables
    	ProviderSkillVO addProviderSkillVO=null;
    	AddProviderSkillResponse addProviderSkillResponse=new AddProviderSkillResponse();
   
    	try{
    		AddProviderSkillRequest addProviderSkillRequest=(AddProviderSkillRequest) apiVO.getRequestFromPostPut();
    		addProviderSkillVO=providerResourceMapper.mapAddProviderSkillRequest(addProviderSkillRequest);
    		if(null !=addProviderSkillVO){
    				 addProviderSkillVO=providerResourceBO.validateAddSkillProvider(addProviderSkillVO);
    				 if(null != addProviderSkillVO.getResults() && null !=addProviderSkillVO.getResults().getError() ){
    					 addProviderSkillResponse.setResults(addProviderSkillVO.getResults());
    				 }
    				 else{
    					 addProviderSkillVO=providerResourceBO.insertProviderSkill(addProviderSkillVO);
    					 if(null != addProviderSkillVO.getResults()){
    						 addProviderSkillResponse.setResults(addProviderSkillVO.getResults());
    					 }
    				 }
    		}
    	}
    	catch (Exception e) {
    		
    		LOGGER.error("Exception in adding the provider skills"+ e.getMessage());
    		addProviderSkillResponse.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
    		e.printStackTrace();
    	}
    	return addProviderSkillResponse;
	}

	public ProviderResourceMapper getProviderResourceMapper() {
		return providerResourceMapper;
	}

	public void setProviderResourceMapper(
			ProviderResourceMapper providerResourceMapper) {
		this.providerResourceMapper = providerResourceMapper;
	}

	public IProviderResourceBO getProviderResourceBO() {
		return providerResourceBO;
	}

	public void setProviderResourceBO(IProviderResourceBO providerResourceBO) {
		this.providerResourceBO = providerResourceBO;
	}

	

}
