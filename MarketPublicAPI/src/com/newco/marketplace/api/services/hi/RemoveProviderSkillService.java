package com.newco.marketplace.api.services.hi;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.removeproviderskill.RemoveProviderSkillRequest;
import com.newco.marketplace.api.beans.hi.account.firm.removeproviderskill.RemoveProviderSkillResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderResourceMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderResourceBO;
import com.newco.marketplace.vo.hi.provider.ProviderSkillVO;


@Namespace("http://www.servicelive.com/namespaces/addProviderSkills")
@APIRequestClass(RemoveProviderSkillRequest.class)
@APIResponseClass(RemoveProviderSkillResponse.class)
public class RemoveProviderSkillService extends BaseService {

	private static final Logger LOGGER= Logger.getLogger(RemoveProviderSkillService.class);
	private IProviderResourceBO providerResourceBO;
    private ProviderResourceMapper providerResourceMapper;

    @Override
	public IAPIResponse execute(APIRequestVO apiVO)  {
    	//Declaring variables
    	ProviderSkillVO removeProviderSkillVO=null;
    	RemoveProviderSkillResponse removeProviderSkillResponse=new RemoveProviderSkillResponse();
   
    	try{
    		RemoveProviderSkillRequest removeProviderSkillRequest=(RemoveProviderSkillRequest) apiVO.getRequestFromPostPut();
    		removeProviderSkillVO=providerResourceMapper.mapRemoveProviderSkillRequest(removeProviderSkillRequest);
    		
    		if(null !=removeProviderSkillVO){
    			
    			removeProviderSkillVO=providerResourceBO.validateRemoveSkillProvider(removeProviderSkillVO);
    			
    			if(null != removeProviderSkillVO.getResults() && null !=removeProviderSkillVO.getResults().getError() ){
        		
    				removeProviderSkillResponse.setResults(removeProviderSkillVO.getResults());
    			}
    			else{
        		
    				removeProviderSkillVO=providerResourceBO.removeProviderSkill(removeProviderSkillVO);
        	    	
    				if(null != removeProviderSkillVO.getResults()){
    					removeProviderSkillResponse.setResults(removeProviderSkillVO.getResults());
    				}
    		   }
    	    }
    		
    	}
    	catch (Exception e) {
    		
    		LOGGER.error("Exception in adding the provider skills"+ e.getMessage());
    		removeProviderSkillResponse.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
    		e.printStackTrace();
    	}
    	return removeProviderSkillResponse;
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
