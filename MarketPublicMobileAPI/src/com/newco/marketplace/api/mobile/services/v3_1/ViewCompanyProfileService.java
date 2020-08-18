package com.newco.marketplace.api.mobile.services.v3_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.companyProfile.ViewCompanyProfileResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.provider.CompanyProfileVO;

/**
 * 
 * This class would act as a Servicer class for View Company Profile
 *
 */
@APIResponseClass(ViewCompanyProfileResponse.class)
public class ViewCompanyProfileService extends BaseService {
	
	private static final Logger LOGGER = Logger.getLogger(ViewCompanyProfileService.class);
    
	private INewMobileGenericBO newMobileBO;
	private NewMobileGenericMapper newMobileMapper;
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		
		CompanyProfileVO companyProfileVO=null;
		ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
		
		try{
			//fetching the company details 
			companyProfileVO=newMobileBO.getCompleteProfile(apiVO.getProviderId());
			if(companyProfileVO !=null){
				//mapping the complete profile from VO
				viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
				//mapping the public profile from VO
				viewCompanyProfileResponse=newMobileMapper.mapPublicProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
				Results results = Results.getSuccess(ResultsCode.COMPANY_PROFILE_SUCCESS.getCode(),ResultsCode.COMPANY_PROFILE_SUCCESS.getMessage());
				viewCompanyProfileResponse.setResults(results);
			}
			else{
			    throw new Exception("Database fetch is null");
			   }
		}
		catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Exception Occured in ViewCompanyProfileService-->execute()--> Exception-->", ex);
			Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			viewCompanyProfileResponse.setResults(results);
		}	
		return viewCompanyProfileResponse;
	}
	
	public INewMobileGenericBO getNewMobileBO() {
		return newMobileBO;
	}

	public void setNewMobileBO(INewMobileGenericBO newMobileBO) {
		this.newMobileBO = newMobileBO;
	}

	public NewMobileGenericMapper getNewMobileMapper() {
		return newMobileMapper;
	}

	public void setNewMobileMapper(NewMobileGenericMapper newMobileMapper) {
		this.newMobileMapper = newMobileMapper;
	}
}
