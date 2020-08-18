/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.common.ResultsCode;
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
		Results results = new Results();
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results=Results.getSuccess(ResultsCode.NOTE_ADDED.getMessage());
		} else {
			logger.info("AddNote operation failed. Setting result and message as Failure");
			results= Results.getError(pResponse.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}
		
		addNoteResponse.setResults(results);
		logger.info("Leaving SOAddNoteMapper.mapAddNoteResponse()");
		return addNoteResponse;
	}

}
