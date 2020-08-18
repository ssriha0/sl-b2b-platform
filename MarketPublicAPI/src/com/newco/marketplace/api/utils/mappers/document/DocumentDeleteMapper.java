/*
 *	Date       		Project    	Author      Version
 * ----------- 	   ---------   -----------  ---------
 * 05-October-2009  KMSTRSUP   Infosys		1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.document;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.exception.DataException;
/**
 * This class would act as a Mapper class for Mapping AddSODocResponse 
 * Object.
 * 
 * @author Infosys
 * @version 1.0
 */
public class DocumentDeleteMapper {
	private Logger logger = Logger.getLogger(DocumentDeleteMapper.class);
	/**
	 * This method is for mapping result for delete document to
	 * AddSODocResponse Object. 
	 * @param boolean resultString
	 * @throws DataException
	 * @return AddSODocResponse
	 */
	public AddSODocResponse mapDeleteDocResponse(
			boolean resultString) {
		logger.info("Entering DocumentDeleteMapper.mapDeleteDocResponse()");
		AddSODocResponse docDeleteResponse = new AddSODocResponse();
		Results results = new Results();
			if(resultString){
				results=Results.getSuccess(ResultsCode.DOCUMENT_DELETE_SUCCESS.getMessage());
		}
		else {
			results=Results.getError(ResultsCode.DOCUMENT_DELETE_FAILURE.getMessage(),
					ResultsCode.DOCUMENT_DELETE_FAILURE.getCode());
		}
		docDeleteResponse.setResults(results);
		docDeleteResponse.setNamespace(
				PublicAPIConstant.DOC_DELETE_RESPONSE_NAMESPACE);
		docDeleteResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		docDeleteResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		logger.info("Leaving DocumentDeleteMapper.mapDeleteDocResponse()");
		return docDeleteResponse;
	}
}
