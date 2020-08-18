/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.document.DocumentUploadMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a Mapper class for Mapping Document Read Response to
 *  Object.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOReadDocMapper {
	private Logger logger = Logger.getLogger(DocumentUploadMapper.class);
	/**
	 * This method is for mapping Response for Read Document.
	 * 
	 * @param message String	 
	 * @return AddSODocResponse
	 */
	public AddSODocResponse mapReadResponse(String message){
		logger.info("Entering SOReadDocMapper.mapReadResponse()");
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();		
		AddSODocResponse readSODocResponse = new AddSODocResponse();
		if("Success".equals(message)){
			
			result.setMessage(CommonUtility
					.getMessage(PublicAPIConstant.DOCUMENT_CREATED_RESULT_CODE));
			result.setCode(PublicAPIConstant.ONE);
			errorResult.setCode(PublicAPIConstant.ZERO);
			errorResult.setMessage("");
		}
		else {
			result.setMessage(CommonUtility
					.getMessage(
							PublicAPIConstant.DOCUMENT_NOTCREATED_ERROR_CODE));
			errorResult.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			errorResult.setMessage("");
		}
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		readSODocResponse.setResults(results);
		readSODocResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		readSODocResponse
				.setSchemaLocation(
						PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		readSODocResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		readSODocResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving SOReadDocMapper.mapReadResponse()");
		return readSODocResponse;
	}
	
}

