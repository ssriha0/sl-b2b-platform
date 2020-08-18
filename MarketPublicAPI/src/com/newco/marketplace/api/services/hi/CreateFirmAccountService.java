package com.newco.marketplace.api.services.hi;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.create.v1_0.CreateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.create.v1_0.CreateFirmAccountResponse;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderBO;
import com.newco.marketplace.vo.hi.provider.ProviderRegistrationVO;

@Namespace("http://www.servicelive.com/namespaces/createFirm")
@APIRequestClass(CreateFirmAccountRequest.class)
@APIResponseClass(CreateFirmAccountResponse.class)
public class CreateFirmAccountService extends BaseService {

	/**
	 * Constructor
	 */

	public CreateFirmAccountService() {
//		super(PublicMobileAPIConstant.PRO_LOGIN_REQ_XSD,
//				PublicMobileAPIConstant.PRO_LOGIN_REP_XSD,
//				PublicMobileAPIConstant.MOBILE_SERVICES_NAMESPACE,
//				PublicMobileAPIConstant.MOBILE_SERVICES_SCHEMA,
//				PublicMobileAPIConstant.LOGIN_PROVIDER_REP_SCHEMALOCATION,
//				LoginProviderRequest.class, LoginProviderResponse.class);
		 super();
	}

	private Logger logger = Logger.getLogger(CreateFirmAccountService.class);
	
	private IProviderBO providerBO;
	
	private ProviderMapper providerMapper;


	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		CreateFirmAccountResponse createFirmAccountResponse = new CreateFirmAccountResponse();
		try{

			ProviderRegistrationVO providerRegistrationVO=	providerMapper.mapCreateFirmRequest(apiVO);
			try{
				providerBO.validateFirmDetails(providerRegistrationVO);
			}catch(Exception e){
				logger.info("error "+e);
			}		
			
			if(null!=providerRegistrationVO.getResults() && null!=providerRegistrationVO.getResults().getError())
			{
				createFirmAccountResponse.setResults(providerRegistrationVO.getResults());
			}
			else{
				providerBO.createFirm(providerRegistrationVO);
				createFirmAccountResponse.setResults(providerRegistrationVO.getResults());
				if(null!=providerRegistrationVO.getVendorId()){
				createFirmAccountResponse.setFirmId(providerRegistrationVO.getVendorId().toString());
				}
				if(null!=providerRegistrationVO.getVendorContactResourceId()){
				createFirmAccountResponse.setProviderId(providerRegistrationVO.getVendorContactResourceId().toString());
				}
			}
			
			return createFirmAccountResponse;
		}catch(Exception e){
			return createFirmAccountResponse;
		}
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
