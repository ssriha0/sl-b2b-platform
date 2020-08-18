package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.post.SOPostFirmResponse;
import com.newco.marketplace.api.beans.so.post.SOPostRequest;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.DataException;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.newco.marketplace.exception.core.BusinessServiceException;
public class OFPostMapper extends OFMapper {
	private Logger logger = Logger.getLogger(OFPostMapper.class);
	private IProviderSearchBO provSearchObj;

	/**
	 * This method is for mapping Mapping service order post result to
	 * SOPostResponse Object.
	 * 
	 * @param pResponse ProcessResponse
	 * @param serviceOrder ServiceOrder         
	 * @throws DataException
	 * @return SOPostResponse
	 */
	public SOPostResponse mapServiceOrder(ServiceOrder serviceOrder, OrderFulfillmentResponse ofResponse) {
		logger.info("Entering OFPostMapper.mapServiceOrder()");
		SOPostResponse soPostResponse = new SOPostResponse();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		logger.info("Setting service order details to OrderStatus object of Response object");
		orderStatus.setSoId(serviceOrder.getSoId());
		if (null != serviceOrder.getWfStatus()) {
			orderStatus.setStatus(serviceOrder.getWfStatus());
		} else {
			orderStatus.setStatus("");
		}
		if (null != serviceOrder.getWfSubStatus()) {
			orderStatus.setSubstatus((serviceOrder.getWfSubStatus()));
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
		if (ofResponse.isError()){
			results = Results.getError(ofResponse.getErrorMessage(), 
					ServiceConstants.USER_ERROR_RC);
		}else {
			results = Results.getSuccess();
		}
		soPostResponse.setResults(results);
		soPostResponse.setOrderstatus(orderStatus);
		logger.info("Leaving OFPostMapper.mapServiceOrder()");
		return soPostResponse;

	}

	/**
	 * @param soec
	 * @param postRequest
	 * @return
	 * @throws BusinessServiceException
	 */

	public SOPostResponse mapRoutedProviders(SOElementCollection soec, SOPostRequest postRequest) throws BusinessServiceException{
		logger.info("mapRoutedProviders");
		SOPostResponse response = new SOPostResponse();
		if (postRequest.getProviderRouteInfo().getSpecificProviders() != null
				&& postRequest.getProviderRouteInfo().getSpecificProviders().getResourceId().size() > 0) {
			List<Integer> routedResourceIds = postRequest.getProviderRouteInfo()
					.getSpecificProviders().getResourceId();
			setProvidersInSoElement(soec,routedResourceIds);
		} else {
			logger.info("No Providers selected");
			Results result = Results.getError(CommonUtility.getMessage(PublicAPIConstant.NO_PROVIDERS_ERROR_CODE), 
					ServiceConstants.USER_ERROR_RC);
			response.setResults(result);
		}
		return response;
	}
	
	/**
	 * @param soec
	 * @param validResources
	 * @throws BusinessServiceException
	 */
	public void setProvidersInSoElementFromResourceObject(SOElementCollection soec,
			List<VendorResource> validResources) throws BusinessServiceException {
		logger.info("mapping the resource to OF");
		for (VendorResource resource: validResources) {
			RoutedProvider rp = new RoutedProvider();
			rp.setProviderResourceId(resource.getResourceId().longValue());
			rp.setVendorId(resource.getVendorId());
			soec.addElement(rp);
		}
	}

	/**
	 * @param soec
	 * @param routedResourceIds
	 * @throws BusinessServiceException
	 */
	public void setProvidersInSoElement(SOElementCollection soec,List<Integer> routedResourceIds)throws BusinessServiceException{

		for(Integer id : routedResourceIds){
			RoutedProvider rp = new RoutedProvider();
			rp.setProviderResourceId(id.longValue());
			VendorResource vendor = provSearchObj.getVendorFromResourceId(id);
			logger.info("searched for the vender id from resource id");
			rp.setVendorId(vendor.getVendorId());
			soec.addElement(rp);
		}


	}
	
	/**
	 * This method is for mapping Mapping service order post result to
	 * SOPostResponse Object.
	 * 
	 * @param pResponse ProcessResponse
	 * @param serviceOrder ServiceOrder         
	 * @throws DataException
	 * @return SOPostResponse
	 */
	public SOPostFirmResponse mapPostServiceOrderToFirm(ServiceOrder serviceOrder, OrderFulfillmentResponse ofResponse, Set<Integer> providerIds) {
		logger.info("Entering OFPostMapper.mapPostServiceOrderToFrim()");
		SOPostFirmResponse soPostFirmResponse = new SOPostFirmResponse();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		logger.info("Setting service order details to OrderStatus object of Response object");
		try{
			orderStatus.setSoId(serviceOrder.getSoId());
			if (null != serviceOrder.getWfStatus()) {
				orderStatus.setStatus(serviceOrder.getWfStatus());
			} else {
				orderStatus.setStatus("");
			}
			if (null != serviceOrder.getWfSubStatus()) {
				orderStatus.setSubstatus((serviceOrder.getWfSubStatus()));
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
			if (ofResponse.isError()){
				results = Results.getError(ofResponse.getErrorMessage(), 
						ServiceConstants.USER_ERROR_RC);
			}else {
				results = Results.getSuccess("Service Order routed to "+ providerIds.size() +" providers");
			}
			soPostFirmResponse.setResults(results);
			soPostFirmResponse.setOrderstatus(orderStatus);
		}catch (Exception e) {
			logger.error("Exception in OFPostMapper-->mapPostServiceOrderToFirm():"+ e.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soPostFirmResponse.setResults(results);
			return soPostFirmResponse;
		}
		
		
		logger.info("Leaving OFPostMapper.mapPostServiceOrderToFrim()");
		return soPostFirmResponse;

	}

	public void setProvSearchObj(IProviderSearchBO provSearchObj) {
		this.provSearchObj = provSearchObj;
	}

}
