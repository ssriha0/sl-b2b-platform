package com.newco.marketplace.api.services.hi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderBO;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;

@Namespace("http://www.servicelive.com/namespaces/approveFirm")
@APIRequestClass(ApproveFirmsRequest.class)
@APIResponseClass(ApproveFirmsResponse.class)
public class ApproveFirmsService extends BaseService {
    
	private static final Logger LOGGER= Logger.getLogger(ApproveFirmsService.class);
	private IProviderBO providerBO;
    private ProviderMapper providerMapper;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		//Declaring variables
		List<ApproveFirmsVO> approveFirmsVOList = null;
		List<ApproveFirmsVO> validFirmsList = new ArrayList<ApproveFirmsVO>();
		List<ApproveFirmsVO> invalidFirms =  new ArrayList<ApproveFirmsVO>();
		ApproveFirmsResponse response = null;
		try{
			ApproveFirmsRequest request =(ApproveFirmsRequest) apiVO.getRequestFromPostPut();
			approveFirmsVOList = providerMapper.mapApproveFirmRequest(request);
			if(null!= approveFirmsVOList){
				approveFirmsVOList = providerBO.validateFirms(approveFirmsVOList,request.getAdminResourceId());
				  if(null!= approveFirmsVOList && !approveFirmsVOList.isEmpty()){
					  validFirmsList = providerBO.removeInvalidOrValidFirms(approveFirmsVOList,ProviderConstants.VALID_FIRM);
					  invalidFirms = providerBO.removeInvalidOrValidFirms(approveFirmsVOList,ProviderConstants.IN_VALID_FIRM);
					    //Updating Provider Firm Status and ReasonCodes.
					    if(null!= validFirmsList&& !validFirmsList.isEmpty()){
					      validFirmsList = providerBO.updateWFStatusAndReasonCodes(validFirmsList,request.getAdminResourceId());
					     }
					     //Valid and Invalid Firms
					      if(null!= validFirmsList && !(validFirmsList.isEmpty()) && null!= invalidFirms &&!(invalidFirms.isEmpty())){
					    	  response = providerMapper.createResponse(validFirmsList,invalidFirms);
					      } 
					      //All Firms are valid and Updated Successfully
					      else if(null!=validFirmsList &&  !validFirmsList.isEmpty()){
					    	  response = providerMapper.createResponse(validFirmsList,null);
					      } //All Firms  are invalid
					      else if(null!= invalidFirms && !invalidFirms.isEmpty()){
					    	  response = providerMapper.createResponse(null,invalidFirms);
					      }// Error Response.
					      else{
					    	  response = providerMapper.createErrorResponse(null);
					      }
				  }//Empty iff admin resource id is invalid
				  else if(null!= approveFirmsVOList && approveFirmsVOList.isEmpty()){
					  response = providerMapper.createErrorResponse(ResultsCode.INVALID_ADMIN_RESOURCE_ID);
				  }else{
					  response = providerMapper.createErrorResponse(null);
				  }
			}else{
				response = providerMapper.createErrorResponse(null);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in Changing the status of the Firm"+ e.getMessage());
			//This code has to removed after completing UT.
			//response = providerMapper.setTestResponse();
			response = providerMapper.createErrorResponse(null);
		}
		return response;
	}

	public IProviderBO getProviderBO() {
		return providerBO;
	}

	public void setProviderBO(IProviderBO providerBO) {
		this.providerBO = providerBO;
	}

	public ProviderMapper getProviderMapper() {
		return providerMapper;
	}

	public void setProviderMapper(ProviderMapper providerMapper) {
		this.providerMapper = providerMapper;
	}

}
