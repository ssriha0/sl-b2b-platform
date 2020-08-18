/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.SOAcceptRejectReleaseRequest;
import com.newco.marketplace.api.beans.so.accept.SOAcceptRejectReleaseResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOAcceptMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class is a service class for cancel Service Order
 * 
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAcceptRejectReleaseService extends BaseService {

	private Logger logger = Logger
			.getLogger(SOAcceptRejectReleaseService.class);
	private IServiceOrderBO serviceOrderBO;
	private SOAcceptMapper acceptMapper;

	/**
	 * Constructor
	 */

	public SOAcceptRejectReleaseService() {
		super(
				PublicAPIConstant.acceptRejectReleaseSO.ACCEPTREJECTRELEASEREQUEST_XSD,
				PublicAPIConstant.acceptRejectReleaseSO.ACCEPTREJECTRELEASERESPONSE_XSD,
				PublicAPIConstant.SOACCEPTREJECTRELEASERESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SOACCEPTREJECTRELEASERESPONSE_SCHEMALOCATION,
				SOAcceptRejectReleaseRequest.class,
				SOAcceptRejectReleaseResponse.class);
	}

	/**
	 * This method dispatches the cancel Service order request.
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering SOAcceptService.execute()");
		SOAcceptRejectReleaseRequest soAcceptRejectReleaseRequest=new SOAcceptRejectReleaseRequest();
		SOAcceptRejectReleaseResponse soAcceptRejectReleaseResponse=new SOAcceptRejectReleaseResponse();
		soAcceptRejectReleaseRequest = (SOAcceptRejectReleaseRequest)apiVO.getRequestFromPostPut();
		Results results = null;
		ServiceOrder serviceOrder = null;
		String providerId = (String) apiVO.getProviderId();
		String soId = (String)apiVO.getSOId();
		Integer  providerResourceId =new Integer(soAcceptRejectReleaseRequest.getIdentification().getId());
		ArrayList<Object> argumentList = new ArrayList<Object>();
		SecurityContext securityContext = null;
		ProcessResponse processResponse = null;
		try {
			securityContext = getSecurityContextForVendorAdmin(new Integer(
					providerId));
			

				serviceOrder = serviceOrderBO.getServiceOrder(soId);
			
				if(PublicAPIConstant.ACCEPT.equals(soAcceptRejectReleaseRequest.getType())){
				if (null != providerResourceId && null != serviceOrder) {

					ProcessResponse pResp = new ProcessResponse();

					pResp = serviceOrderBO.processAcceptServiceOrder(soId,
							providerResourceId, securityContext
									.getCompanyId(), 9, true, false, true,
							securityContext);
					soAcceptRejectReleaseResponse = acceptMapper.mapServiceOrder(pResp);
				} else {
					logger.info("No Service Order exists for this soId");
					argumentList.add("(" + PublicAPIConstant.retrieveSO.SO_ID
							+ ")");
					results = Results.getError(
							ResultsCode.INVALID_OR_MISSING_PARAM
									.getMessage(argumentList),
							ResultsCode.INVALID_OR_MISSING_PARAM.getCode());
					soAcceptRejectReleaseResponse.setResults(results);
					return soAcceptRejectReleaseResponse;
				}  }
				else if(PublicAPIConstant.REJECT.equals(soAcceptRejectReleaseRequest.getType()))
				{
					
						Integer reasonId =soAcceptRejectReleaseRequest.getRejectreasonId();
						String reasonDesc = soAcceptRejectReleaseRequest.getDescription();
						int responseId = PublicAPIConstant.THREE;
						String modifiedBy = securityContext.getUsername();
						if(null==reasonId){

							logger.info("No Service Order exists for this soId");
							argumentList.add("(" + PublicAPIConstant.acceptRejectReleaseSO.REASONID
									+ ")");
							results = Results.getError(
									ResultsCode.INVALID_OR_MISSING_PARAM
											.getMessage(argumentList),
									ResultsCode.INVALID_OR_MISSING_PARAM.getCode());
							soAcceptRejectReleaseResponse.setResults(results);
							return soAcceptRejectReleaseResponse;
						
						}else {
						processResponse = serviceOrderBO.rejectServiceOrder(
								providerResourceId.intValue(), soId, reasonId.intValue(), 
								responseId,modifiedBy,reasonDesc, securityContext);	
						}
					if(processResponse == null) {
						soAcceptRejectReleaseResponse.setResults(Results.getError(
								ResultsCode.REJECT_FAILURE.getMessage(), 
							ResultsCode.REJECT_FAILURE.getCode()));
					} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
						soAcceptRejectReleaseResponse.setResults(Results.getSuccess(
								ResultsCode.REJECT_SUCCESSFUL.getMessage()));					
					} else {
						List<String> eMsgs = processResponse.getMessages();
						StringBuilder eMsg = new StringBuilder();
						for(String msg: eMsgs) {
							eMsg = eMsg.append("; " + msg);
						}
						logger.error("Error occured in rejecting SO, error code: " 
									+ processResponse.getCode() 
							+ ", messages: " + eMsg);
						soAcceptRejectReleaseResponse.setResults(Results.getError(eMsgs.get(0), 
									ResultsCode.FAILURE.getCode()));			
					}
					
				}
				else if(PublicAPIConstant.RELEASE.equals(soAcceptRejectReleaseRequest.getType()))
				{
					ProcessResponse success = null;
					
					if (serviceOrder.getWfStateId()==OrderConstants.ACCEPTED_STATUS) {
						try {
							success = serviceOrderBO
									.releaseServiceOrderInAccepted(soId,
											OrderConstants.RELEASE_SO_REASON_CODE, soAcceptRejectReleaseRequest.getDescription(),
											providerResourceId, securityContext);
						} catch (BusinessServiceException bse) {
							logger.error("Requested release unsuccessful", bse);
						}
						if (success.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
							serviceOrderBO.releaseSOProviderAlert(soId,
									securityContext);
						}
					} else if (serviceOrder.getWfStateId()== OrderConstants.ACTIVE_STATUS) {
						try {
							success = serviceOrderBO
									.releaseServiceOrderInActive(soId, 
											OrderConstants.RELEASE_SO_REASON_CODE,
											soAcceptRejectReleaseRequest.getDescription(), 
											providerResourceId,
											securityContext);
						} catch (BusinessServiceException bse) {
							logger.error("Requested release unsuccessful", bse);
						}
					} else if (serviceOrder.getWfStateId()== OrderConstants.PROBLEM_STATUS) {
						try {
							success = serviceOrderBO
									.releaseServiceOrderInProblem(soId,
											OrderConstants.RELEASE_SO_REASON_CODE,
											soAcceptRejectReleaseRequest.getDescription(),
											providerResourceId,
											securityContext);	
					
				} catch (BusinessServiceException bse) {
					logger.error("Requested release unsuccessful", bse);
				} }
					
					if(success == null) {
						soAcceptRejectReleaseResponse.setResults(Results.getError(
								ResultsCode.SO_RELEASE_ERROR.getMessage(), 
							ResultsCode.SO_RELEASE_ERROR.getCode()));
					} else if (success.getCode() == ServiceConstants.VALID_RC) {
						soAcceptRejectReleaseResponse.setResults(Results.getSuccess(
								ResultsCode.RELEASE_SUCCESSFUL.getMessage()));					
					} else {
						List<String> eMsgs = success.getMessages();
						StringBuilder eMsg = new StringBuilder();
						for(String msg: eMsgs) {
							eMsg = eMsg.append("; " + msg);
						}
						logger.error("Error occured in rejecting SO, error code: " 
									+ success.getCode() 
							+ ", messages: " + eMsg);
						soAcceptRejectReleaseResponse.setResults(Results.getError(eMsgs.get(0), 
									ResultsCode.FAILURE.getCode()));			
					}
				
				
				}
				
			
			
		} catch (Exception ex) {
			logger.error("Exception Occurred inside SOCancelService.execute"
					+ ex);
			results = Results.getError(ex.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soAcceptRejectReleaseResponse.setResults(results);
		}

		return soAcceptRejectReleaseResponse
		;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SOAcceptMapper getAcceptMapper() {
		return acceptMapper;
	}

	public void setAcceptMapper(SOAcceptMapper acceptMapper) {
		this.acceptMapper = acceptMapper;
	}

}
