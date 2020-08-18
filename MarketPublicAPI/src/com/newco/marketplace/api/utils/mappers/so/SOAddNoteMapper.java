/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Jun-2009	KMSTRSUP   Infosys				1.0
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
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
/**
 * This class would act as a Mapper class for mapping add note result to
 * AddNoteResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAddNoteMapper {
	private Logger logger = Logger.getLogger(SOAddNoteMapper.class);
	
	/**
	 * This method is for mapping Mapping service order add note result to
	 * AddNoteResponse Object.
	 * 
	 * @param pResponse ProcessResponse
  	 * @throws DataException
	 * @return AddNoteResponse
	 */
	public SOAddNoteResponse mapAddNoteResponse(ProcessResponse pResponse)
			throws DataException {
		logger.info("Entering SOAddNoteMapper.mapAddNoteResponse()");
		SOAddNoteResponse addNoteResponse = new SOAddNoteResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {

			result.setMessage(CommonUtility.getMessage(PublicAPIConstant.ADD_NOTE_RESULT_CODE));
			result.setCode(PublicAPIConstant.ONE);
			errorResult.setCode(PublicAPIConstant.ZERO);
			errorResult.setMessage("");
		} else {
			logger.info("AddNote operation failed. Setting result and message as Failure");
			result.setMessage(CommonUtility.getMessage(PublicAPIConstant.ADD_NOTE_ERROR_CODE));
			result.setCode(PublicAPIConstant.ZERO);
			errorResult.setCode(pResponse.getCode());
			if (null != pResponse.getMessages()&& null != pResponse.getMessages().get(0)) {
				errorResult.setMessage(pResponse.getMessages().get(0));
			} else {
				errorResult.setMessage("");
			}
		}
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		addNoteResponse.setResults(results);
		addNoteResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		addNoteResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		addNoteResponse
				.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		addNoteResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving SOAddNoteMapper.mapAddNoteResponse()");
		return addNoteResponse;
	}

}
