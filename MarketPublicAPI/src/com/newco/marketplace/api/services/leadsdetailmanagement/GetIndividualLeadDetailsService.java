/**
 * 
 */
package com.newco.marketplace.api.services.leadsdetailmanagement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Address;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Attachment;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Attachments;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetIndividualLeadDetailsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.History;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Historys;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.IndividualLeadDetail;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Note;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Notes;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.utils.DocumentUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.TimezoneMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * @author Infosys
 * 
 */
public class GetIndividualLeadDetailsService{

	private static Logger logger = Logger
			.getLogger(GetIndividualLeadDetailsService.class);
	private ILeadProcessingBO leadProcessingBO;
	private static final String FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String FORMAT_WITHOUT_TIMEZONE = "MM/dd/yy hh:mm a";
    private static final String FORMAT_WITHOUT_TIMEZONE_DISPLAY = "MMM d, yyyy h:mm a";
    private static final String FORMAT_WITHOUT_TIMEZONE_FOR_HISTORY = "MM/dd/yyyy hh:mm a";
    
    private static final String FORMAT_WITH_TIMEZONE_DEFAULT = "yyyy-MM-dd hh:mm:ss";	
	boolean errorOccured;
     
	public GetIndividualLeadDetailsService(){
		this.errorOccured=false;
	}
	public GetIndividualLeadDetailsResponse execute(Map<String, String> reqMap){
		
		GetIndividualLeadDetailsResponse response = new GetIndividualLeadDetailsResponse();
		LeadInfoVO leadInfoVO=null;		
		//In order to avoid Cache problem of API.
		if(errorOccured){
			this.errorOccured=false;
		}
		try{
			response = validate(reqMap, response);
			if(errorOccured){
				return response;
			}			
			leadInfoVO=leadProcessingBO.getIndividualLeadDetails(reqMap);
			if(null != leadInfoVO){
				IndividualLeadDetail individualLeadDetail =new IndividualLeadDetail();
				individualLeadDetail.setSlLeadId(leadInfoVO.getSlLeadId());
				individualLeadDetail.setLeadSource(leadInfoVO.getLeadSource());
				individualLeadDetail.setLeadStatus(leadInfoVO.getFirmStatus());
				individualLeadDetail.setLeadType(leadInfoVO.getLeadType());
				individualLeadDetail.setLeadPrice(leadInfoVO.getLeadPrice());
				individualLeadDetail.setLeadCategory(leadInfoVO.getLeadCategory());
				individualLeadDetail.setFirstName(leadInfoVO.getFirstName());
				individualLeadDetail.setAdditionalProjects(leadInfoVO.getAdditionalProjects());
				individualLeadDetail.setLmsLeadId(leadInfoVO.getLmsLeadId());
				Address address=new Address();
				address.setCity(leadInfoVO.getCity());
				address.setState(leadInfoVO.getState());
				address.setStreet(leadInfoVO.getConcatStreet());
				address.setZip(leadInfoVO.getZip());
				individualLeadDetail.setAddress(address);
				individualLeadDetail.setDescription(leadInfoVO.getDescription());
				individualLeadDetail.setEmail(leadInfoVO.getEmail());
				individualLeadDetail.setLastName(leadInfoVO.getLastName());
				if(StringUtils.isNotEmpty(leadInfoVO.getPhoneNumber())){
					individualLeadDetail.setPhoneNumber(leadInfoVO.getPhoneNumber());
				}
				if(leadInfoVO.getPreferredAppointment()!=null){
					individualLeadDetail.setPreferredAppointment(leadInfoVO.getPreferredAppointment().toString());
				}
				individualLeadDetail.setCreatedDate(formatDate(FORMAT_WITHOUT_TIMEZONE_DISPLAY,leadInfoVO.getCreatedDate()));
				individualLeadDetail.setCreatedDateFormatted(formatDate(FORMAT_WITH_TIMEZONE,leadInfoVO.getCreatedDate()));
				individualLeadDetail.setProjectType(leadInfoVO.getProjectType());
				individualLeadDetail.setSkill(leadInfoVO.getSkill());
				if(leadInfoVO.getCreatedDate()!=null){
					individualLeadDetail.setPostedDate(leadInfoVO.getCreatedDate().toString());
				}
				individualLeadDetail.setUrgencyOfService(leadInfoVO.getUrgencyOfService());	
				
				if((null!=leadInfoVO.getPreferredAppointment())&& (StringUtils.isNotBlank(leadInfoVO.getStartTime()))&&(StringUtils.isNotBlank(leadInfoVO.getServiceTimeZone()))){
					individualLeadDetail.setStartTime(leadInfoVO.getStartTime());
					individualLeadDetail.setEndTime(leadInfoVO.getEndTime());
					individualLeadDetail.setFormattedServiceTime1();
					individualLeadDetail.setServiceTimeZone(convertTimeZone(leadInfoVO.getServiceTimeZone(), leadInfoVO.getDlSavingFlag(),leadInfoVO.getPreferredAppointment(), individualLeadDetail.getFormattedServiceTime1()));
				}
				if((null!=leadInfoVO.getScheduledDate())&& (StringUtils.isNotBlank(leadInfoVO.getScheduledStartTime()))&&(StringUtils.isNotBlank(leadInfoVO.getScheduledEndTime()))){
					individualLeadDetail.setScheduledDate(leadInfoVO.getScheduledDate().toString());
					individualLeadDetail.setFormattedScheduledDate();
					individualLeadDetail.setScheduledStartTime(leadInfoVO.getScheduledStartTime());
					individualLeadDetail.setScheduledEndTime(leadInfoVO.getScheduledEndTime());
					//individualLeadDetail.setFormattedScheduleTime1();
					//individualLeadDetail.setFormattedScheduleTime2();
				}

				individualLeadDetail.setResFirstName(leadInfoVO.getResFirstName());
				individualLeadDetail.setResLastName(leadInfoVO.getResLastName());
				individualLeadDetail.setResourceAssigned(leadInfoVO.getResourceAssigned());
				if(leadInfoVO.getResourceAssigned()!=null){
					individualLeadDetail.setResRating(leadProcessingBO.getProviderRating(leadInfoVO.getResourceAssigned()));
				}
				if(leadInfoVO.getCompletionDate()!=null){
					individualLeadDetail.setCompletionDate(leadInfoVO.getCompletionDate().toString());
					
				}
				if(leadInfoVO.getCompletionTime()!=null){
				individualLeadDetail.setCompletionTime(leadInfoVO.getCompletionTime());
				}
				individualLeadDetail.setNumberOfTrips(leadInfoVO.getNumberOfTrips());
				individualLeadDetail.setCompletionComments(leadInfoVO.getCompletionComments());
				individualLeadDetail.setLeadFinalPrice(leadInfoVO.getLeadFinalPrice());
				individualLeadDetail.setLeadLaborPrice(leadInfoVO.getLeadLaborPrice());
				individualLeadDetail.setLeadMaterialPrice(leadInfoVO.getLeadMaterialPrice());
				
				if(leadInfoVO.getCancelledDate() !=null){
				individualLeadDetail.setCancelledDate(leadInfoVO.getCancelledDate().toString());
				}
				individualLeadDetail.setCancelledBy(leadInfoVO.getCancelledBy());
				individualLeadDetail.setCancelledReason(leadInfoVO.getCancelledReason());
				
				if(leadInfoVO.getFirmStatus()!=NewServiceConstants.NEW_LEAD_STATUS){					
					Notes notes=new Notes();
					List<Note> noteList=new ArrayList<Note>();	
					List<SLLeadNotesVO > leadNotesList=null;
					try{
						leadNotesList= leadProcessingBO.getNoteList(leadInfoVO.getSlLeadId());
						if(null!=leadNotesList){
												
							for(SLLeadNotesVO e:leadNotesList){
						    Note note=new Note();
						    note.setMessage(e.getNote());
						    note.setNoteBy(e.getCreatedBy());
						    if(null!=e.getModifiedBy()){
						    	note.setModifiedBy(e.getModifiedBy());
						    }
						    note.setNoteId(e.getLeadNoteId());
						    if(e.getCreatedDate()!=null){						    	
						    	note.setNoteDate(formatDate(FORMAT_WITHOUT_TIMEZONE_DISPLAY,e.getCreatedDate()));
						    }
						    note.setSubject(e.getSubject());
						    note.setRoleId(e.getRoleId());
						    noteList.add(note);
						   		}
							
						}
					   }
					catch (Exception e) {
						logger.error("exception in getting notes: " + e.getMessage());
						
					}
					notes.setNoteList(noteList);
					individualLeadDetail.setNotes(notes);
				}
				if(leadInfoVO.getFirmStatus()!=NewServiceConstants.NEW_LEAD_STATUS){					
					Historys historys=new Historys();
					List<History> historyList=new ArrayList<History>();	
					List<LeadHistoryVO > historyVOList=null;
					try{
						historyVOList= leadProcessingBO.getHistoryList(leadInfoVO.getSlLeadId());
						if(null!=historyVOList){
												
							for(LeadHistoryVO vo:historyVOList){
						   History history=new History();
							if(vo.getCreatedDate()!=null){
								history.setHistoryDate(formatDate(FORMAT_WITHOUT_TIMEZONE_DISPLAY,vo.getCreatedDate()));
						    }
							history.setDescription(vo.getDescription());
							history.setHistoryAction(vo.getActionName());
							history.setHistoryBy(vo.getCreatedBy());
							history.setModifiedBy(vo.getModifiedBy());
							history.setRoleId(vo.getRoleId());
							history.setActionId(vo.getActionId());
							history.setEntityId(vo.getEnitityId());
							historyList.add(history);
						   		
							}
							
						}
					   }
					catch (Exception e) {
						logger.error("exception in getting History: " + e.getMessage());
						
					}
					historys.setHistoryList(historyList);
					individualLeadDetail.setHistorys(historys);
				}
				if(leadInfoVO.getFirmStatus()!=NewServiceConstants.NEW_LEAD_STATUS){					
					Attachments attachments=new Attachments();
					List<Attachment> attachmentList=new ArrayList<Attachment>();	
					List<LeadAttachmentVO > attachmentVOList=null;
					try{
						attachmentVOList= leadProcessingBO.getAttachmentsList(leadInfoVO.getSlLeadId());
						if(null!=attachmentVOList){
												
							for(LeadAttachmentVO vo:attachmentVOList){
						   Attachment attachment=new Attachment();
						   attachment.setDocumentId(vo.getDocumentId());
						   attachment.setDocumentName(vo.getDocumentName());
						   attachment.setFirstName(vo.getFirstName());
						   attachment.setLastName(vo.getLastName());
						   if(null!=vo.getCreatedDate()){
							   attachment.setCreatedDate(vo.getCreatedDate());
						   }
						   Double docSize = DocumentUtils.convertBytesToKiloBytes(
									vo.getDocumentSize().intValue(), true);
						   attachment.setDocumentSize(docSize);
						   attachment.setDocCategoryId(vo.getDocCategoryId());
						   attachment.setDocPath(vo.getDocPath());
						   attachmentList.add(attachment);
							}
							
						}
					   }
					catch (Exception e) {
						logger.error("exception in getting Attachments: " + e.getMessage());
						
					}
					attachments.setAttachmentList(attachmentList);
					individualLeadDetail.setAttachments(attachments);
				}
				
				response.setIndividualLeadDetail(individualLeadDetail);
				response.setResults(Results.getSuccess(ResultsCode.SUCCESS.getMessage()));
				String responseString=serializeRequest(response, GetIndividualLeadDetailsResponse.class);
				logger.info("Response As String :"+responseString);
			}
			else{
				logger.error("GetIndividualLeadDetailsService exception");
				}
		}
		catch(Exception e) {
			logger.error("GetIndividualLeadDetailsService exception detail: " + e.getMessage());
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return response;
	}
   private GetIndividualLeadDetailsResponse validate(Map<String, String> reqMap,GetIndividualLeadDetailsResponse response) {
	     String frimId = null;
		 String slLeadId=null;
		boolean firmValid= false;
		boolean leadValid=false;
		//for validating firm id for given lead
		boolean match=false;
		try{
			if(null != reqMap && !reqMap.isEmpty()){
				frimId = reqMap.get(PublicAPIConstant.FIRM_ID);
				slLeadId=reqMap.get(PublicAPIConstant.SL_LEAD_ID);
				if(!StringUtils.isBlank(frimId )){
					//Changes for BLM/PLM--START
					//firmValid= leadProcessingBO.validateFirmId(frimId );
					String firmIdReturned= leadProcessingBO.toValidateFirmId(frimId);
					if (!StringUtils.isBlank(firmIdReturned) && firmIdReturned.equals(frimId)) {
						firmValid = true;
					}
					//Changes for BLM/PLM--END
					if(!firmValid){
						response.setResults(Results.getError(ResultsCode.FIRM_NOT_FOUND.getMessage(), 
								ResultsCode.FIRM_NOT_FOUND.getCode()));
						errorOccured = true;
						return response;
					}
				}
				else{
					response.setResults(Results.getError(ResultsCode.INVALID_VENDOR.getMessage(), 
							ResultsCode.INVALID_VENDOR.getCode()));
					errorOccured = true;
					return response;
				}
				if(!StringUtils.isBlank(slLeadId)){
					leadValid= leadProcessingBO.validateSlLeadId(slLeadId);
					if(!leadValid){
						response.setResults(Results.getError(ResultsCode.LEAD_NOT_FOUND.getMessage(), 
								ResultsCode.LEAD_NOT_FOUND.getCode()));
						errorOccured = true;
						return response;
					}
				}
				else{
					response.setResults(Results.getError(ResultsCode.INVALID_LEAD_ID.getMessage(), 
							ResultsCode.INVALID_LEAD_ID.getCode()));
					errorOccured = true;
					return response;
					}
				match=leadProcessingBO.validateFirmForLead(reqMap);
				if(!match){
					response.setResults(Results.getError(ResultsCode.UNMATCHED1.getMessage(), 
									ResultsCode.UNMATCHED1.getCode()));
					errorOccured = true;
					return response;					
				}
			}
			if(errorOccured){
				response.setResults(Results.getError(ResultsCode.NOENTRY.getMessage(), 
						ResultsCode.NOENTRY.getCode()));
			}
		}catch(Exception e){
			logger.error("GetIndividualLeadDetailsService validate exception detail: " + e.getMessage());
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			errorOccured = true;
		}
		
		return response;
	}
    private String serializeRequest(Object request, Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		// String requestXml = xstream.toXML(request).toString();
		return xstream.toXML(request).toString();
	}

	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream
				.registerConverter(new DateConverter("yyyy-MM-dd",
						new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}
	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	/**
	 * @param zone
	 * @param flag
	 * @param date1
	 * @param time1
	 * @return
	 */
	private static String convertTimeZone(String zone, String dLflag, Date date1, String time1){
		String timeZone = "";
		if ("Y".equals(dLflag)) {
			TimeZone tz = TimeZone.getTimeZone(zone);
			Timestamp timeStampDate = null;
			try {
				if (null != zone
						&& (StringUtils.isNotBlank(zone))) {
					java.util.Date dt = (java.util.Date) TimeUtils
							.combineDateTime(date1,time1);
					timeStampDate = new Timestamp(dt.getTime());
				}
			} catch (ParseException pe) {
				logger.error(pe.getMessage());
			}
			if (null != timeStampDate) {
				boolean isDLSActive = tz.inDaylightTime(timeStampDate);
				if (isDLSActive) {
					timeZone = TimezoneMapper.getDSTTimezone(zone);
				} else {
					timeZone = TimezoneMapper.getStandardTimezone(zone);
				}
			}
		} else {
			timeZone = TimezoneMapper.getStandardTimezone(zone);
		}
		return timeZone;
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
}
