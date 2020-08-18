package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.ReportProblemResponse;
import com.newco.marketplace.api.beans.so.ResolveProblemOnSORequest;
import com.newco.marketplace.api.beans.so.providerRetrieve.SOProviderRetrieveResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

@Namespace("http://www.servicelive.com/namespaces/pro/so")
@APIRequestClass(ResolveProblemOnSORequest.class)
@APIResponseClass(ReportProblemResponse.class)
/**
 * This class would act as a service class for resolving a issue in SO by provider.
 * 
 * @author Infosys
 * @version 1.0
 */
public class ResolveProblemByProviderService extends BaseService {
	private IServiceOrderBO serviceOrderBO;
	private Logger logger = Logger
			.getLogger(ResolveProblemByProviderService.class);

	/**
	 * Constructor
	 */

	public ResolveProblemByProviderService() {
		super(PublicAPIConstant.RESOLVE_PROBLEM_PRO_REQUEST_XSD,
				PublicAPIConstant.REPORT_PROBLEM_PRO_RESPONSE_XSD,
				PublicAPIConstant.PROBLEM_PRO_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.PROBLEM_PRO_SCHEMALOCATION,
				ResolveProblemOnSORequest.class,
				ReportProblemResponse.class);
		
		addRequiredURLParam(APIRequestVO.SOID, DataTypes.STRING);
	}

	/**
	 * This method is for resolving the issue in SO by the provider.
	 * 
	 * @param apiVO  APIRequestVO
	 * @return ReportProblemResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute() method of "
				+ "ResolveProblemByProviderService");
		ResolveProblemOnSORequest request;
		ReportProblemResponse response;
		ProcessResponse processResponse = null;
		try {
			response = new ReportProblemResponse();
			request = (ResolveProblemOnSORequest) apiVO.getRequestFromPostPut();

			String soId = (String) apiVO.getSOId();
			String providerId = (String) apiVO.getProviderId();
			int subStatusId = 0;
			SecurityContext sContext = getSecurityContextForVendorAdmin(new Integer(
					providerId));
			if (sContext == null) {
				response.setResults(Results.getError(
						ResultsCode.VENDOR_RESOURCE_ID_FAILURE.getMessage(),
						ResultsCode.VENDOR_RESOURCE_ID_FAILURE.getCode()));
			} else {
				int entityId = sContext.getCompanyId();
				int roleType = sContext.getRoleId();
				String userName = sContext.getUsername();
				String strComment = request.getResolveComments();
				if(StringUtils.isEmpty(strComment)){
					response.setResults(Results.getError(
							ResultsCode.RESOLVE_COMMENTS_EMPTY.getMessage(),
							ResultsCode.RESOLVE_COMMENTS_EMPTY.getCode()));
					return response;
				}
				String strPbDesc = "";
				String strPbDetails = "";

				processResponse = serviceOrderBO.reportResolution(soId,
						subStatusId, strComment, entityId, roleType, strPbDesc,
						strPbDetails, userName, sContext);
				if (processResponse == null) {
					response.setResults(Results.getError(
							ResultsCode.RESOLVE_FAILURE.getMessage(),
							ResultsCode.RESOLVE_FAILURE.getCode()));
				} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
					response.setResults(Results
							.getSuccess(ResultsCode.RESOLVE_SUCCESSFUL
									.getMessage()));
				} else {
					List<String> eMsgs = processResponse.getMessages();
					StringBuilder eMsg = new StringBuilder();
					for (String msg : eMsgs) {
						eMsg = eMsg.append("; " + msg);
					}
					logger
							.error("Error occured in resolving problem by provider,"
									+ " error code: "
									+ processResponse.getCode()
									+ ", messages: " + eMsg);
					response.setResults(Results.getError(eMsgs.get(0),
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
			}
		} catch (NumberFormatException nme) {
			logger.error("ResolveProblemByProviderService-->"
					+ "execute()--> Exception-->" + nme.getMessage(), nme);
			Results results = Results.getError(
					ResultsCode.INVALID_OPTIONAL_PARAMS.getMessage(),
					ResultsCode.INVALID_OPTIONAL_PARAMS.getCode());
			response = new ReportProblemResponse();
			response.setResults(results);
		} catch (Exception ex) {
			logger.error("ResolveProblemByProviderService-->"
					+ "execute()--> Exception-->" + ex.getMessage(), ex);
			Results results = Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response = new ReportProblemResponse();
			response.setResults(results);
		}
		logger.info("Leaving execute() method of "
				+ "ResolveProblemByProviderService");
		return response;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
}
