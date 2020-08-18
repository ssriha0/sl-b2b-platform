package com.newco.marketplace.api.mobile.services.v3_0;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.UpdateServiceOrderFlag.UpdateServiceOrderFlagRequest;
import com.newco.marketplace.api.mobile.beans.UpdateServiceOrderFlag.UpdateServiceOrderFlagResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;

/**
 * This class would act as a Servicer class for Update Service Order Flag
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(UpdateServiceOrderFlagRequest.class)
@APIResponseClass(UpdateServiceOrderFlagResponse.class)
public class UpdateServiceOrderFlagService extends BaseService{
	private Logger LOGGER = Logger.getLogger(UpdateServiceOrderFlagService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		final UpdateServiceOrderFlagRequest request = (UpdateServiceOrderFlagRequest) apiVO.getRequestFromPostPut();
		UpdateServiceOrderFlagResponse response=new UpdateServiceOrderFlagResponse();
		String firmId = (String) apiVO.getProviderId();	
		String soId = (String) apiVO.getSOId();
		try{	
			mobileGenericBO.updateServiceOrderFlag(firmId,soId,request.getFollowupFlag());
			response = new UpdateServiceOrderFlagResponse(Results.getSuccess(
					ResultsCode.UPDATE_SO_FLAG.getMessage()));
			
		}catch (Exception e) {
			LOGGER.error("Severe Error occured while updating the so flag for soId:"+soId);
			response = UpdateServiceOrderFlagResponse.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		}
		return response;
	}


	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}


	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}


	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}


	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	
}
