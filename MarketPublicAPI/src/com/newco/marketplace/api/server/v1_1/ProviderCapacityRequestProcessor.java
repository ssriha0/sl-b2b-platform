package com.newco.marketplace.api.server.v1_1;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.provider.capacity.AvailableTimeSlotsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.providre.capacity.v1_1.GetTimeSlotsForTechTalkService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Path("v1.1")
public class ProviderCapacityRequestProcessor {

	private Logger logger = Logger
			.getLogger(ProviderCapacityRequestProcessor.class);
	// final protected MediaType mediaType = MediaType.parse("text/xml");
	final protected String mediaType = MediaType.TEXT_XML;

	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	protected GetTimeSlotsForTechTalkService getTimeSlotsForTechTalkService;

	@GET
	@Path("/buyers/{buyer_id}/skuId/{sku_id}/availableSlotsForTechTalk")
	@Produces({ "application/xml", "application/json", "text/xml" })
	public AvailableTimeSlotsResponse getAvailableTimeSlotsForTechTalk(
			@PathParam("buyer_id") String buyerId,
			@PathParam("sku_id") String sku,
			@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		if (logger.isInfoEnabled()){
			logger.info("AvailableTimeSlotsResponse method started");
		}
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Get);
        apiVO.setBuyerId(buyerId);
        apiVO.setStartDate(startDate);
        apiVO.setEndDate(endDate);
        //apiVO.setStartTime(startTime);
        //apiVO.setEndTime(endTime);
        apiVO.setSku(sku);
        response = getTimeSlotsForTechTalkService.doSubmit(apiVO);
        response = PublicAPIConstant.XML_VERSION + response;
		return (AvailableTimeSlotsResponse) convertXMLStringtoRespObject(response,AvailableTimeSlotsResponse.class);
	}

	// converting XML to Object and vice versa
	protected String convertReqObjectToXMLString(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return xstream.toXML(request).toString();
	}

	protected Object convertXMLStringtoRespObject(String request,
			Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return (Object) xstream.fromXML(request);
	}

	protected XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter(
				PublicAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
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

	public GetTimeSlotsForTechTalkService getGetTimeSlotsForTechTalkService() {
		return getTimeSlotsForTechTalkService;
	}

	public void setGetTimeSlotsForTechTalkService(
			GetTimeSlotsForTechTalkService getTimeSlotsForTechTalkService) {
		this.getTimeSlotsForTechTalkService = getTimeSlotsForTechTalkService;
	}
}
