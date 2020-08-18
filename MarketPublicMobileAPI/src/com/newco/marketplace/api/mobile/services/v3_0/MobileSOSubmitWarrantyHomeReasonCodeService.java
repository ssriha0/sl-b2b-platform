package com.newco.marketplace.api.mobile.services.v3_0;



import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;

import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.MobileSOSubmitWarrantyHomeReasonCodeResponse;
import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.MobileSOSubmitWarrantyHomeReasonRequest;
import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.WarrantyHomeReasonInfo;
import com.newco.marketplace.api.mobile.beans.vo.WarrantyHomeReasonInfoVO;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;


/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/05/04
 * for validating phase 2 mobile APIs
 *
 */
@APIRequestClass(MobileSOSubmitWarrantyHomeReasonRequest.class)
@APIResponseClass(MobileSOSubmitWarrantyHomeReasonCodeResponse.class)
public class MobileSOSubmitWarrantyHomeReasonCodeService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(MobileSOSubmitWarrantyHomeReasonCodeService.class);
	private MobileGenericValidator mobileGenericValidator;
	private MobileGenericMapper mobileGenericMapper;
	//changes
	private IServiceOrderBO serviceOrderBo;
	
	

	public MobileSOSubmitWarrantyHomeReasonCodeService() {
		// calling the BaseService constructor to initialize
		super();
	}
	/**
	 * Method to execute submitWarrantyHome reason codes
	 */
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("Entering MobileSOSubmitRescheduleService.execute()");
		MobileSOSubmitWarrantyHomeReasonCodeResponse warrantyHomeReasonCodeResponse = new MobileSOSubmitWarrantyHomeReasonCodeResponse();
		MobileSOSubmitWarrantyHomeReasonRequest submitWarrantyHomeReasonRequest =(MobileSOSubmitWarrantyHomeReasonRequest)apiVO.getRequestFromPostPut();	
		String soId = apiVO.getSOId();
		WarrantyHomeReasonInfo WarrantyReasonCodeInfo = submitWarrantyHomeReasonRequest.getWarrantyReasonCodeInfo();
		WarrantyHomeReasonInfoVO warrantyHomeReasonInfoVO = mobileGenericMapper.mapWarrantyHomeReason(WarrantyReasonCodeInfo);
		if(null != warrantyHomeReasonInfoVO && null != warrantyHomeReasonInfoVO.getReasonCode() && null != soId){
			String msg = "success";
			warrantyHomeReasonInfoVO.setSoId(soId);
			try{
				serviceOrderBo.saveWarrantyHomeReasons(warrantyHomeReasonInfoVO);


			}catch(Exception ex){
				LOGGER.error("Exception Occurred inside MobileSOSubmitWarrantyHomeService.execute"+ ex);
				ex.printStackTrace();
				msg = ex.getMessage();

			}

			warrantyHomeReasonCodeResponse = mobileGenericMapper.mapSaveWarrantyHomeReasonsResponse(msg);
		}
		LOGGER.info("Exiting MobileSOSubmitRescheduleService.execute()");

		return warrantyHomeReasonCodeResponse;
	}	
	
	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}
	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}
	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}
	
	
	
	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}
	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}
	
	
}