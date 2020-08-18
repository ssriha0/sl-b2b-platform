/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 22-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.offer.CounterOfferResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
/**
 * This class would act as a Mapper class for mapping  ProcessResponse to
 * CounterOfferResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCreateConditionalOfferMapper {
	private Logger logger = Logger.getLogger(SOCreateConditionalOfferMapper.class);
	
	/**
	 * This method is for mapping ProcessResponse to
	 * CounterOfferResponse object
	 * 
	 * @param pResponse ProcessResponse
  	 * @throws DataException
	 * @return CounterOfferResponse
	 */
	public CounterOfferResponse mapSOCreateConditionalOfferMapper(ProcessResponse pResponse)
			throws DataException {
		logger.info("Entering SOCreateConditionalOfferMapper.mapSOCreateConditionalOfferMapper()");
		CounterOfferResponse counterOfferResponse= new CounterOfferResponse();
		Results results = new Results();
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results = Results.getSuccess(ResultsCode.COUNTER_OFFER_SUBMITTED.getMessage());
		} else {
			logger.info("SOCreateConditionalOfferMapper.mapSOCreateConditionalOfferMapper(): Create Conditional Offer Request Failed");
			results = Results.getError(pResponse.getMessages().get(0),
										ResultsCode.FAILURE.getCode());
		}
		counterOfferResponse.setResults(results);
		logger.info("Leaving SOCreateConditionalOfferMapper.mapSOCreateConditionalOfferMapper()");
		return counterOfferResponse;
	}
}
