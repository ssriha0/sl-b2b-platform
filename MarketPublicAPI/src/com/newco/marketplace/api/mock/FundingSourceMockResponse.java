package com.newco.marketplace.api.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;

/**
 * @author ndixit
 *
 */
public class FundingSourceMockResponse {
	
	private static Logger logger = Logger.getLogger(FundingSourceMockResponse.class);
	private static XStreamUtility conversionUtility = new XStreamUtility();

	/**
	 * This method returns a mock response for the create funding source.
	 * @return String stringResponse
	 */
	public static String getMockResponseForFundingSourceCreate() {
		logger.info("Entering SOMockResponse.getMockResponseForCreate()");
		SOCreateResponse soCreateResponse = new SOCreateResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId("154-5448-6303-33");
		orderStatus.setStatus(PublicAPIConstant.DRAFT);
		orderStatus.setSubstatus("");
		orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date()));
		result.setMessage(CommonUtility.getMessage(
								PublicAPIConstant.DRAFT_CREATED_RESULT_CODE));
		result.setCode(PublicAPIConstant.ONE);
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soCreateResponse.setOrderstatus(orderStatus);
		soCreateResponse.setResults(results);
		soCreateResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soCreateResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soCreateResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soCreateResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getCreateResponseXML(soCreateResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForCreate()");
		return stringResponse;
	}


}
