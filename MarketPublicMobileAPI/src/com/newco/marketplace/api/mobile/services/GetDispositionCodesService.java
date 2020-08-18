package com.newco.marketplace.api.mobile.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.beans.depositioncode.DispositionCodeList;
import com.newco.marketplace.api.mobile.beans.depositioncode.GetDispositionCodeResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.techtalk.ITeclTalkBuyerPortalBO;
import com.newco.marketplace.exception.core.BusinessServiceException;

@APIResponseClass(GetDispositionCodeResponse.class)
public class GetDispositionCodesService extends BaseService {
	
	private static final Logger LOGGER = Logger.getLogger(GetDispositionCodesService.class);

	private ITeclTalkBuyerPortalBO techTalkBuyerBO;
	private MobileGenericMapper mobileGenericMapper;
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("inside execute of GetDispositionCodesService");
		GetDispositionCodeResponse getDispositionCodeResponse = new GetDispositionCodeResponse();
		Results results = new Results();
		try {
			List<DepositionCodeDTO> dispositionCodes = techTalkBuyerBO.getAllDipositionCodes();
			DispositionCodeList dispositionCodeList = mobileGenericMapper.mapToDispositoinCodeListVo(dispositionCodes);
			getDispositionCodeResponse.setDispositionCodeList(dispositionCodeList);
			results = Results
					.getSuccess(ResultsCode.GET_DESPOSITION_CODES_SUCCESS
							.getMessage());
		} catch (BusinessServiceException e) {
			LOGGER.error("Error inside execute of GetDispositionCodesService "+e);
			results = Results.getError(ResultsCode.GET_DESPOSITION_CODES_ERROR.getMessage(), 
					ResultsCode.GET_DESPOSITION_CODES_ERROR.getCode());
		}
		getDispositionCodeResponse.setResults(results);
		return getDispositionCodeResponse;
	}

	public ITeclTalkBuyerPortalBO getTechTalkBuyerBO() {
		return techTalkBuyerBO;
	}

	public void setTechTalkBuyerBO(ITeclTalkBuyerPortalBO techTalkBuyerBO) {
		this.techTalkBuyerBO = techTalkBuyerBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	
}
