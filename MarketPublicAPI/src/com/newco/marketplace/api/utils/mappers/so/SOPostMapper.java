/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 11-Jun-2009	KMSTRSUP   Infosys				1.0
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
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
/**
 * This class would act as a Mapper class for mapping service order
 * to SOPostResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOPostMapper {
	private Logger logger = Logger.getLogger(SOPostMapper.class);

	/**
	 * This method is for mapping Mapping service order post result to
	 * SOPostResponse Object.
	 * 
	 * @param pResponse ProcessResponse
	 * @param serviceOrder ServiceOrder         
	 * @throws DataException
	 * @return SOPostResponse
	 */
	public SOPostResponse mapServiceOrder(ProcessResponse pResponse,
			ServiceOrder serviceOrder) throws DataException {
		logger.info("Entering SOPostMapper.mapServiceOrder()");
		SOPostResponse soPostResponse = new SOPostResponse();
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		logger
		.info("Setting service order details to OrderStatus object of Response object");
		orderStatus.setSoId(serviceOrder.getSoId());
		if (null != serviceOrder.getStatus()) {
			orderStatus.setStatus(serviceOrder.getStatus());
		} else {
			orderStatus.setStatus("");
		}
		if (null != serviceOrder.getSubStatus()) {
			orderStatus.setSubstatus((serviceOrder.getSubStatus()));
		} else {
			orderStatus.setSubstatus("");
		}
		if (null != serviceOrder.getCreatedDate()) {

			orderStatus.setCreatedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getCreatedDate()));
		} else {
			orderStatus.setCreatedDate("");
		}
		if (null != serviceOrder.getRoutedDate()) {

			orderStatus.setPostedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getRoutedDate()));
		}
		if (null != serviceOrder.getAcceptedDate()) {

			orderStatus.setAcceptedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getAcceptedDate()));
		}
		if (null != serviceOrder.getActivatedDate()) {

			orderStatus.setActiveDate(CommonUtility.sdfToDate
					.format(serviceOrder.getActivatedDate()));
		}
		if (null != serviceOrder.getCompletedDate()) {

			orderStatus.setCompletedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getCompletedDate()));
		}
		if (null != serviceOrder.getClosedDate()) {
			orderStatus.setClosedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getClosedDate()));
		}

		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {

			result.setMessage(pResponse.getMessages().get(0));
			result.setCode(PublicAPIConstant.ONE);
			errorResult.setCode(PublicAPIConstant.ZERO);
			errorResult.setMessage("");
		} else {
			logger
					.info("Setting result and message as Failure when the Post operation is failure");
			result.setMessage("");
			result.setCode(PublicAPIConstant.ZERO);
			errorResult.setCode(pResponse.getCode());
			errorResult.setMessage(pResponse.getMessages().get(0));
		}
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soPostResponse.setOrderstatus(orderStatus);
		soPostResponse.setResults(results);
		soPostResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soPostResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soPostResponse
				.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soPostResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving SOPostMapper.mapServiceOrder()");
		return soPostResponse;

	}

}
