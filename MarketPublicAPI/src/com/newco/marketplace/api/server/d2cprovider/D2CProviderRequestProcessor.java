package com.newco.marketplace.api.server.d2cprovider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBException;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.firmDetail.FirmIds;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.D2CProviderPortalService.D2CFirmDetailsServiceAPI;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.d2cproviderportal.ID2CProviderPortalBO;
import com.newco.marketplace.exception.core.BusinessServiceException;

@Path("getFirm")
public class D2CProviderRequestProcessor {

	private final Logger logger = Logger.getLogger(D2CProviderRequestProcessor.class);

	private static final String JSON_MEDIA_TYPE = "application/json";

	protected ID2CProviderPortalBO d2CProviderPortalBO;
	private D2CFirmDetailsServiceAPI d2CFirmDetailsServiceAPI;

	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;

	@POST
	@Path("/skuId/{skuId}/zipCode/{zipCode}")
	@Consumes({ "application/xml", "application/json", "text/xml", "text/plain", "*/*" })
	@Produces({"application/json"})
	public D2CGetFirmDetailsResponse getFirmsBySKU(@PathParam("skuId") String skuId, @PathParam("zipCode") String zipCode, @QueryParam("date") String date,@QueryParam("firmId") String firmId)
 throws BusinessServiceException,
			JAXBException, ParseException {
		logger.info("Inside D2CProviderRequestProcessor.getFirmsBySKU");
		Results results = null;
		D2CPortalAPIVORequest d2CPortalAPIVO = new D2CPortalAPIVORequest();

		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		d2CPortalAPIVO.setRequestFromPost(reqMap);
		d2CPortalAPIVO.setSkuId(skuId);
		d2CPortalAPIVO.setZipCode(zipCode);
		logger.debug("D2CProviderRequestProcessor.getFirmsBySKU Date is : "+ date);
		if (!StringUtils.isEmpty(date)) {
			d2CPortalAPIVO.setDate(date);
		}
		
		if (!StringUtils.isEmpty(firmId)) {
			d2CPortalAPIVO.setFirmId(firmId);
		}

		if (!StringUtils.isEmpty(getHttpRequest().getContentType())) {
			String content = getHttpRequest().getContentType().split(";")[0];

			logger.debug("D2CProviderRequestProcessor.getFirmsBySKU content type is : "
					+ content);
			if (!JSON_MEDIA_TYPE.equalsIgnoreCase(content)) {
				D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
				results = Results.getError(
						ResultsCode.ONLY_JSON_FORMAT_IS_ACCEPTED.getMessage(),
						ResultsCode.ONLY_JSON_FORMAT_IS_ACCEPTED.getCode());
				firmDetailsXML.setResults(results);
				logger.info("Exiting D2CProviderRequestProcessor.getFirmsBySKU");
				return firmDetailsXML;
			}
		} else {
			logger.info(" D2CProviderRequestProcessor.getFirmsBySKU content type is : null");
			D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
			results = new Results();
			results = Results.getError(
					ResultsCode.CONTENT_TYPE_CAN_NOT_BE_BLANK.getMessage(),
					ResultsCode.CONTENT_TYPE_CAN_NOT_BE_BLANK.getCode());
			firmDetailsXML.setResults(results);
			logger.info("Exiting D2CProviderRequestProcessor.getFirmsBySKU");
			return firmDetailsXML;
		}
		try {
			List<D2CProviderAPIVO> d2CProviderAPIVO = d2CFirmDetailsServiceAPI
					.getFirmDetailsList(d2CPortalAPIVO);

			Map<String, D2CProviderAPIVO> d2CProviderAPIVOMap = new HashMap<String, D2CProviderAPIVO>();

			if (null == d2CProviderAPIVO || d2CProviderAPIVO.isEmpty()) {
				D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
				results = Results.getError(
						ResultsCode.NO_MATCHING_FOR_THE_REQUESTED_DATA
								.getMessage(),
						ResultsCode.NO_MATCHING_FOR_THE_REQUESTED_DATA
								.getCode());
				firmDetailsXML.setResults(results);
				logger.info("Exiting D2CProviderRequestProcessor.getFirmsBySKU");
				return firmDetailsXML;
			}

			logger.debug("received count of providers from getFirmDetailsList: "
					+ d2CProviderAPIVO.size());
			List<String> list = new ArrayList<String>();
			for (D2CProviderAPIVO d2CProviderAPIVOData : d2CProviderAPIVO) {
				Integer providerId = d2CProviderAPIVOData.getFirmId();
				String str = Integer.toString(providerId);
				d2CProviderAPIVOMap.put(str, d2CProviderAPIVOData);
				list.add(str);
			}

			GetFirmDetailsRequest getFirmDetailsRequest = new GetFirmDetailsRequest();

			FirmIds firmIds = new FirmIds();
			firmIds.setFirmId(list);
			getFirmDetailsRequest.setFirmIds(firmIds);

			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
			apiVO.setRequestFromPostPut(getFirmDetailsRequest);
			apiVO.setRequestType(RequestType.Post);
			apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);

			D2CGetFirmDetailsResponse firmDetailsXML = d2CFirmDetailsServiceAPI
					.getFirmsBySKU(apiVO, d2CProviderAPIVOMap);
			logger.debug("D2CProviderRequestProcessor.getFirmsBySKU reply is : "
					+ firmDetailsXML);
			if (null != firmDetailsXML && null != firmDetailsXML.getFirms()
					&& null != firmDetailsXML.getFirms().getFirmDetails()
					&& firmDetailsXML.getFirms().getFirmDetails().size() > 0) {
				logger.info("Exiting D2CProviderRequestProcessor.getFirmsBySKU");
                   return firmDetailsXML;
			} else {
				results = new Results();
				results = Results.getError(
						ResultsCode.NO_MATCHING_FOR_THE_REQUESTED_DATA
								.getMessage(),
						ResultsCode.NO_MATCHING_FOR_THE_REQUESTED_DATA
								.getCode());
				firmDetailsXML.setResults(results);
				logger.info("Exiting D2CProviderRequestProcessor.getFirmsBySKU");
				return firmDetailsXML;
				}
		} catch (Exception e) {
			logger.error("error during getting the firm details", e);
			D2CGetFirmDetailsResponse firmDetailsXML1 = new D2CGetFirmDetailsResponse();
			results = Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			e.printStackTrace();
			firmDetailsXML1.setResults(results);
			return firmDetailsXML1;
		}
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public ID2CProviderPortalBO getD2CProviderPortalBO() {
		return d2CProviderPortalBO;
	}

	public void setD2CProviderPortalBO(ID2CProviderPortalBO d2cProviderPortalBO) {
		d2CProviderPortalBO = d2cProviderPortalBO;
	}

	public D2CFirmDetailsServiceAPI getD2CFirmDetailsServiceAPI() {
		return d2CFirmDetailsServiceAPI;
	}

	public void setD2CFirmDetailsServiceAPI(D2CFirmDetailsServiceAPI d2cFirmDetailsServiceAPI) {
		d2CFirmDetailsServiceAPI = d2cFirmDetailsServiceAPI;
	}

}
