package com.newco.marketplace.api.utils.mappers.wallet.fundingsource;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.fundingsources.CreateFundingSourceResponse;

public class CreateFundingSourceMapper {
	
	private Logger logger = Logger.getLogger(CreateFundingSourceMapper.class);
	/**
	 * This method is for mapping for creating funding source. 
	 *  
	 * @param createFundignSourceResponseList List<CreateFundignSourceResponseVO>
	 * @return CreateFundingSourceResponse
	 */
	public CreateFundingSourceResponse mapCreateFundingSourceRequest(
			Map<String, String> createFundingSourceMap) {
		logger.info("Entering mapCreateFundingSourceRequest method");
		CreateFundingSourceResponse resp = new CreateFundingSourceResponse();
		logger.info("Exiting mapCreateFundingSourceRequest method");
		return resp;
	}
	
	public CreateFundingSourceResponse mapCreateFundingSourceResponse()
	{
		logger.info("Entering mapCreateFundingSourceRequest method");
		CreateFundingSourceResponse resp = new CreateFundingSourceResponse();
		logger.info("Exiting mapCreateFundingSourceRequest method");
		return resp;
	}

}
