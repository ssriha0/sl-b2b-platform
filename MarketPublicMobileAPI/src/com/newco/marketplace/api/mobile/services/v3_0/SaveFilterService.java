package com.newco.marketplace.api.mobile.services.v3_0;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterRequest;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f recieved orders
*/
/**
 * Service class to save the mobile filter 
 *
 */
@APIRequestClass(SaveFilterRequest.class)
@APIResponseClass(SaveFilterResponse.class)
public class SaveFilterService  extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(SaveFilterService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	
	
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		SaveFilterResponse response = null;
		FiltersVO filtersVO = new FiltersVO();
		
		final SaveFilterRequest request = (SaveFilterRequest) apiVO.getRequestFromPostPut();
		final Integer  providerResourceId = apiVO.getProviderResourceId();
		
		try{
			response = mobileGenericValidator.validateSaveFilter(request,providerResourceId);
			if(null == response){
				filtersVO = mobileGenericMapper.mapSaveFilterRequest(request,providerResourceId);
				mobileGenericBO.saveFilter(filtersVO);
				response = new SaveFilterResponse(Results.getSuccess(
						ResultsCode.SAVE_FILTER_SUCCESS.getMessage()));
			}
		}catch (Exception e) {
			LOGGER.error("Severe Error occured while saving the filter:"+filtersVO.getFilterName());
			response = SaveFilterResponse.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	
	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}


	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}


	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(final IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}


	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}


	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}

	
}
