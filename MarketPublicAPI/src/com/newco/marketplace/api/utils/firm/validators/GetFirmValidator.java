/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 07-July-2016	  KMSLTVSZ   Infosys				1.1
 * 
 * 
 */

package com.newco.marketplace.api.utils.firm.validators;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
/**
 * This class would act as the validator class for getfirmDetails Service
 * 
 * @author Infosys
 * @version 1.0
 */
public class GetFirmValidator {
	private Logger logger = Logger.getLogger(GetFirmValidator.class);
	private IServiceOrderBO serviceOrderBO;

	/**
	 * method to validate the firmIds and response filters entered in getFirmDetails Details
	 * @param firmDetailRequestVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ErrorResult> validateFirmDetailsRequest(FirmDetailRequestVO firmDetailRequestVO) throws BusinessServiceException {
		List<String> vendorIdList = null;
		boolean errorOccured = false;
		List<ErrorResult> validationErrors = null;
		try {
			validationErrors = new ArrayList<ErrorResult>();
			if(null !=firmDetailRequestVO.getFirmId() && !firmDetailRequestVO.getFirmId().isEmpty()){
				//validating the firmIds passed in the request
				vendorIdList = serviceOrderBO.getValidProviderFirms(firmDetailRequestVO.getFirmId());
				
				if((null == vendorIdList)||
						(null != vendorIdList && !vendorIdList.isEmpty() && !vendorIdList.containsAll(firmDetailRequestVO.getFirmId()))
						||(null != vendorIdList && vendorIdList.isEmpty())){
					errorOccured =true;
					addError(ResultsCode.INVALID_VENDOR_ID.getMessage(),ResultsCode.INVALID_VENDOR_ID.getCode(),validationErrors);
				}
			}
			if(!errorOccured){
				//validating the response filters entered
				if(null!= firmDetailRequestVO.getFilter() && !firmDetailRequestVO.getFilter().isEmpty()){
					if(!PublicAPIConstant.getFirmFilters().containsAll(firmDetailRequestVO.getFilter())){
						errorOccured= true;	
						addError(ResultsCode.INVALID_RESPONSE_FILTER.getMessage(),ResultsCode.INVALID_RESPONSE_FILTER.getCode(),validationErrors);
					}
				}
			}
		}
		catch (BusinessServiceException e) {
			logger.error("Exception in validate method of getFirmDetails"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		} 

		return validationErrors;
	}

	/**
	 * method to add the error code and message to the response
	 * @param message
	 * @param code
	 * @param validationErrors
	 */
	private void addError(String message, String code,List<ErrorResult> validationErrors) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		validationErrors.add(result);
	}


	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}


	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

}
