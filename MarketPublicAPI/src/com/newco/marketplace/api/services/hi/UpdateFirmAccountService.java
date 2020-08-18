package com.newco.marketplace.api.services.hi;

import org.apache.log4j.Logger;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderBO;
import com.newco.marketplace.vo.hi.provider.ProviderRegistrationVO;

@Namespace("http://www.servicelive.com/namespaces/updateFirmAccount")
@APIRequestClass(UpdateFirmAccountRequest.class)
@APIResponseClass(UpdateFirmAccountResponse.class)
public class UpdateFirmAccountService extends BaseService {


	public UpdateFirmAccountService()
	{
	super();
	}

	private Logger logger = Logger.getLogger(UpdateFirmAccountService.class);
	
	private IProviderBO providerBO;
	private ProviderMapper providerMapper;

	public ProviderMapper getProviderMapper() {
		return providerMapper;
	}

	public void setProviderMapper(ProviderMapper providerMapper) {
		this.providerMapper = providerMapper;
	}

	public IProviderBO getProviderBO() {
		return providerBO;
	}

	public void setProviderBO(IProviderBO providerBO) {
		this.providerBO = providerBO;
	}

	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub

		String firmId = (String) apiVO.getProviderFirmId();
		
		UpdateFirmAccountResponse updateFirmAccountResponse = new UpdateFirmAccountResponse();
		
		ProviderRegistrationVO providerRegistrationVO =new ProviderRegistrationVO();
		
		UpdateFirmAccountRequest updateFirmAccountRequest = (UpdateFirmAccountRequest) apiVO
				.getRequestFromPostPut();
		
	
		//Mapping update firm account request to provider Registration VO
		try {
		providerRegistrationVO =providerMapper.mapUpdateFirmRequest(updateFirmAccountRequest);
		
		providerRegistrationVO.setVendorId(Integer.parseInt(firmId));
	
		//Validating update firm account request to provider Registration VO
		providerBO.validateUpdateFirmDetails(providerRegistrationVO);	
		
		if(null!=providerRegistrationVO.getResults() && null!=providerRegistrationVO.getResults().getError() && !providerRegistrationVO.getResults().getError().isEmpty())
		{
			updateFirmAccountResponse.setResults(providerRegistrationVO.getResults());
		}
		else{
			//Updating firm account request to provider Registration VO
			providerBO.updateFirm(providerRegistrationVO);
			updateFirmAccountResponse.setResults(providerRegistrationVO.getResults());
			if(null!=providerRegistrationVO.getVendorId()){
				updateFirmAccountResponse.setFirmId(providerRegistrationVO.getVendorId().toString());
				}
				if(null!=providerRegistrationVO.getVendorContactResourceId()){
				updateFirmAccountResponse.setProviderId(providerRegistrationVO.getVendorContactResourceId().toString());
				}
			
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Caught in UpdateFirmAccountService.java class :"+e);
			Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR
					.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR
					.getCode());
			providerRegistrationVO.setResults(results);
			updateFirmAccountResponse.setResults(providerRegistrationVO.getResults());
			return updateFirmAccountResponse;
		}
		
		return updateFirmAccountResponse;
	}

}
