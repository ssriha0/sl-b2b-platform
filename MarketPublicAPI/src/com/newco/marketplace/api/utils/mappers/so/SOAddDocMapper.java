/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Sep-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.exception.DataException;

public class SOAddDocMapper {
	private Logger logger = Logger.getLogger(SOAddDocMapper.class);

	/**
	 * This method is for mapping Mapping add so doc result to
	 * AddSODocResponse Object.
	 * 
	 * @param List invalidDocumentList
	 * @throws DataException
	 * @return AddSODocResponse
	 */
	public AddSODocResponse mapAddSODocResponse(List<String> invalidDocumentList)
			throws DataException {
		logger.info("Entering SOAddDocMapper.mapAddSODocResponse()");
		AddSODocResponse addSODocResponse = new AddSODocResponse();		
		Results results = new Results();
		if (!invalidDocumentList.isEmpty()) {
			logger.info("Mapping the Invalid Documents with error List");
			String errorMessage = CommonUtility
					.getMessage(PublicAPIConstant.DOCUMENT_ADD_ERROR_CODE);
			for (String fileName : invalidDocumentList) {
				errorMessage = errorMessage + fileName + "  ::  ";
			}
			results = Results.getError(errorMessage,
					ResultsCode.FAILURE.getCode());	
		} else {
			results = Results.getSuccess(
					ResultsCode.DOCUMENT_ADDED_TO_SO.getMessage());	
		}		
		addSODocResponse.setResults(results);
		logger.info("Leaving SOAddDocMapper.mapAddSODocResponse()");
		return addSODocResponse;
	}
}
