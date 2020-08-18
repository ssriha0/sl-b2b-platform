package com.newco.marketplace.api.services.leadsdetailmanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadDetail;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadInfoResponse;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
	
public class GetAllLeadService {

	private static Logger logger = Logger.getLogger(GetAllLeadService.class);
	private ILeadProcessingBO leadProcessingBO;
    private static final String FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String FORMAT_WITHOUT_TIMEZONE = "MM/dd/yy hh:mm a";
    private static final String FORMAT_WITHOUT_TIMEZONE_DISPLAY = "MMM dd, yyyy h:mm a";
    
    private String staleAfter;
	
	boolean errorOccured = false;

	public GetAllLeadService() {
		this.errorOccured = false;
	}
		
	/**
	 * Get the response
	 * @param reqMap
	 * @return
	 */	
	public String execute(Map<String, String> reqMap) {

		LeadInfoResponse response = new LeadInfoResponse();
		List<LeadInfoVO> leadInfo = null;
		String firmId = reqMap.get(PublicAPIConstant.FIRM_ID);
		String status = null;
		String count = null;
		// Optional parameters
		if (!StringUtils.isBlank(reqMap.get(PublicAPIConstant.LEAD_STATUS))) {
			status=reqMap.get(PublicAPIConstant.LEAD_STATUS);
		}

		if (!StringUtils.isBlank(reqMap.get(PublicAPIConstant.COUNT))) {
			count=reqMap.get(PublicAPIConstant.COUNT);
		}
		//In order to remove cache issue
		if(errorOccured){
			this.errorOccured=false;
		}
		try {

			response = validateFirmId(reqMap, response);
			
			if (errorOccured) {
				return getResponseXML(response);
			}
			// get the leads for the firm
			leadInfo = leadProcessingBO.getAllLeadDetails(firmId,status,count,getStaleAfter());
			Integer totalLeadCount = 0;
			List<LeadDetail> leadDetailsList = new ArrayList<LeadDetail>();
			LeadDetails leadDetails = new LeadDetails();
			if (null != leadInfo) {
				if(leadInfo.size()>0){
					if(leadInfo.get(0)!=null){
						totalLeadCount = leadInfo.get(0).getTotalLeadCount();
					}
				}
				for (LeadInfoVO leadInfoVO : leadInfo) {
					LeadDetail leadDetail = new LeadDetail();
					leadDetail.setSlLeadId(leadInfoVO.getSlLeadId());
					leadDetail.setLmsLeadId(leadInfoVO.getLmsLeadId());
					if(null!=leadInfoVO.getSkill()){
						leadDetail.setSkill(leadInfoVO.getSkill().toLowerCase());
					}
					leadDetail.setProjectType(leadInfoVO.getProjectType());
					leadDetail.setCustFirstName(leadInfoVO.getFirstName());
					leadDetail.setCustLastName(leadInfoVO.getLastName());
					leadDetail.setCustPhoneNo(leadInfoVO.getPhoneNumber());
					leadDetail.setLeadStatus(leadInfoVO.getFirmStatus());
					leadDetail.setUrgency(leadInfoVO.getUrgencyOfService());
					leadDetail.setCustCity(leadInfoVO.getCity());
					leadDetail.setCustZip(leadInfoVO.getZip());
					leadDetail.setDescription(leadInfoVO.getDescription());
					if(leadInfoVO.getPreferredAppointment()!=null){
						leadDetail.setServicePreferedDate(leadInfoVO.getPreferredAppointment().toString());
					}
					leadDetail.setCreatedDate(formatDate(FORMAT_WITHOUT_TIMEZONE_DISPLAY,leadInfoVO.getCreatedDate()));
					leadDetail.setCreatedDateFormatted(formatDate(FORMAT_WITH_TIMEZONE,leadInfoVO.getCreatedDate()));
					leadDetailsList.add(leadDetail);
				}
			} else {
				logger.info("No lead present");
			}
			leadDetails.setLeadDetail(leadDetailsList);
			response.setLeadDetails(leadDetails);
			response.setTotalLeadCount(totalLeadCount);
			response.setResults(Results.getSuccess(ResultsCode.SUCCESS
					.getMessage()));
		}

		catch (Exception e) {
			logger.error("GetAllLeadDetailsService exception detail: "
					+ e.getMessage());
			response.setResults(Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return getResponseXML(response);
	}	
	
	/**
	 * Format the date
	 * @param format
	 * @param date
	 * @return
	 */
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
		
	}
	/**
	 * Validate the firm
	 * 
	 * @param reqMap
	 * @param response
	 * @return
	 */
	private LeadInfoResponse validateFirmId(Map<String, String> reqMap,
			LeadInfoResponse response) {
		String frimId = null;
		boolean isValid = false;
		try {
			if (null != reqMap && !reqMap.isEmpty()) {
				frimId = reqMap.get(PublicAPIConstant.FIRM_ID);
				if (!StringUtils.isBlank(frimId)) {
					//BLM/PLM Changes --START
					//isValid = leadProcessingBO.validateFirmId(frimId);
					String firmIdReturned= leadProcessingBO.toValidateFirmId(frimId);
					if (!StringUtils.isBlank(firmIdReturned) && firmIdReturned.equals(frimId)) {
						isValid = true;
					}
					//BLM/PLM Changes --END
					if (!isValid) {
						errorOccured = true;
					}
				} else {
					errorOccured = true;
				}
			} else {
				errorOccured = true;
			}
			if (errorOccured) {
				response.setResults(Results.getError(ResultsCode.FIRM_NOT_FOUND
						.getMessage(), ResultsCode.FIRM_NOT_FOUND.getCode()));
			}
		} catch (Exception e) {
			logger.error("LeadInfoResponse validateFirmId exception detail: "
					+ e.getMessage());
			response.setResults(Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			errorOccured = true;
		}

		return response;

	}
	/**
	 * Serialize the request
	 * @param request
	 * @param classz
	 * @return
	 */
	private String serializeRequest(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		// String requestXml = xstream.toXML(request).toString();
		return xstream.toXML(request).toString();
	}
	
	/**
	 * Get the Xstream
	 * @param classz
	 * @return
	 */
	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss",
						new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	
	
	private String getResponseXML(LeadInfoResponse leadInfoResponse) {					
		logger.info("Entering BaseService getResponseXML");
		XStream xStream = getXstream(LeadInfoResponse.class);
		String xml  = (String) xStream.toXML(leadInfoResponse);
        logger.info(xml);
        
		logger.info("Exiting getResponseXML");		
		return xml;
	}
	
	
	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}
	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public String getStaleAfter() {
		return staleAfter;
	}

	public void setStaleAfter(String staleAfter) {
		this.staleAfter = staleAfter;
	}
	
	
}
