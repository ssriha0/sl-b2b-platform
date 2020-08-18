package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.deleteFilter.DeleteFilterResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f recieved orders
*/
/**
 * Service class to delete the mobile filter from table search_filters_mobile
 *
 */
@APIResponseClass(DeleteFilterResponse.class)
public class DeleteFilterService  extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(DeleteFilterService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	
	
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		DeleteFilterResponse response = null;
		int deletedFilters = 0;
		Integer resourceId = apiVO.getProviderResourceId();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		String filterId = requestMap.get(MPConstants.DELETE_FILTER_FILTER_ID);
		
		response = mobileGenericValidator.validateDeleteFilterParam(filterId);
		
		
		if(null == response){
			try{
				deletedFilters = mobileGenericBO.deleteFilter(resourceId,filterId);
				if(deletedFilters==0){
					LOGGER.error("No filters with filterName "+filterId+" is associated with resource "+resourceId);
					response= DeleteFilterResponse.getInstanceForError(ResultsCode.DELETE_FILTER_NO_FILTER_ERROR);
				}else{
					response = new DeleteFilterResponse(Results.getSuccess(
							ResultsCode.DELETE_FILTER_SUCCESS.getMessage()));
				}
			}catch (Exception e) {
				LOGGER.error("Severe Error occured while deleting the filter "+filterId+" for resource "+resourceId);
				response= DeleteFilterResponse.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
			}
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

}
