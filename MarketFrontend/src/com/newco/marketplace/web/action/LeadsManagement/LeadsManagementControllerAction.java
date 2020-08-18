package com.newco.marketplace.web.action.LeadsManagement;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.io.File;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteResponse;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadNoteType;
import com.newco.marketplace.api.common.Identification;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.EligibleProviderForLead;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetIndividualLeadDetailsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.IndividualLeadDetail;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadDetail;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadFirmStatus;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadInfoResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Price;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateLeadStatusRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateLeadStatusResponse;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CallCustomerReasonCodeVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.leadmanagement.lead.Attachment;
import com.newco.marketplace.vo.leadmanagement.lead.Attachments;
import com.newco.marketplace.vo.leadmanagement.lead.History;
import com.newco.marketplace.vo.leadmanagement.lead.Historys;
import com.newco.marketplace.vo.leadmanagement.lead.LeadInfoDetail;
import com.newco.marketplace.vo.leadmanagement.lead.Note;
import com.newco.marketplace.vo.leadmanagement.lead.Notes;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.LeadManagementTabDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.ordermanagement.api.services.LeadManagementRestClient;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.providerleadmanagement.services.ProviderLeadManagementService;
import flexjson.JSONSerializer;

/**
 * This class is created for Leads Management NS
 *
 */
/**
 * @author Gireesh_Thadayil
 *
 */
public class LeadsManagementControllerAction extends SLBaseAction implements Preparable, ModelDriven<LeadManagementTabDTO>{

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger
            .getLogger(LeadsManagementControllerAction.class);

    private LeadManagementTabDTO lmTabDTO = new LeadManagementTabDTO();
    private LeadManagementRestClient leadClient;
    private ProviderLeadManagementService providerLeadManagementService;
    private IApplicationProperties applicationProperties;

    public static final String S_JSON_RESULT = "jsonResult";
    public static final String HAS_ERROR = "hasError";
    private static final String DOCUMENT_DESC = "Lead Upload Document";
    private static final String PNG = ".png";
    private static final String JPG = ".jpg";
    private static final String GIF = ".gif";
    private static final String DOC = ".doc";
    private static final String DOCX = ".docx";
    private static final String PDF = ".pdf";
    private static final String TXT = ".txt";
    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";
    private static final String ZIP = ".zip";
    private static final String JPEG = ".jpeg";
    private static final String PJPEG = ".pjpeg";
    private static final String TIFF = ".tiff";
    private static final String BMP = ".bmp";
    private static final String XML = ".xml";
    private static final String SAME_DAY = "SAME_DAY";
    private static final String SAME_DAY_MAPPING = "urgent";
    private static final String NEXT_DAY= "NEXT_DAY";
    private static final String NEXT_DAY_DESC = "urgent";
    private static final String AFTER_TOMORROW= "AFTER_TOMORROW";
    private static final String AFTER_TOMORROW_DESC = "after tomorrow";
    private static final String EMPTY_STRING = "";
    private static final String NEW = "new";
    private static final String WORKING = "working";
    private static final String SCHEDULED = "scheduled";
    private static final String SCHEDULED_PROVIDER = "scheduledProvider";
    private static final String COMPLETED = "completed";
    private static final String CANCELLED = "cancelled";
    private static final String STALE = "stale";
    private static final String ACTIVE = "active";
    private static final String INACTIVE = "inactive";
    private static final String DEFAULT_COUNT = "20";
    private static final String HISTORY_STATUS_WORKING="Status Changed: working";
    private static final String HISTORY_STATUS_SCHEDULED="Status Changed: scheduled";
    private static final String HISTORY_STATUS_CANCELLED="cancelled";
    private static final String HISTORY_STATUS_COMPLETED="completed";
    public static final String PROVIDER_LEAD_MANAGEMENT_DEFAULT_LEAD_COUNT = "provider_lead_management_lead_count";
    private static final String XML_DATE_FORMAT="yyyy-MM-dd";
    private static final String READ_DATE_FORMAT="MM/dd/yyyy";
    private static final String READ_TIME_FORMAT= "hh:mm a";
    private String resultUrl = "";

    /*
     * TODO
     *
     * Create the DTO - As of now using the OMDTO for testing:Done
     * Create the client for calling the API:Going to use OM client
     * D-serialize and set the data in the dto
     * Use the data to display in front end (leads_dashboard.jsp)
     */
   
     public void prepare() throws Exception {
    	 this.clearFieldErrors();
    	 this.clearActionErrors();
         createCommonServiceOrderCriteria();
     }
     
     public String execute() throws Exception {
          if(getRequest().getParameter("activeLoad")!=null){
                getSession().setAttribute("tabName", "active");
              String return1 = viewActiveLeads();
          }
          else if(getRequest().getParameter("inactiveLoad")!=null){
              String return2 = viewInactiveLeads();
                getSession().setAttribute("tabName", "inactive");
          }
          else{
                getSession().setAttribute("tabName", "active");
                loadLeadManagementTabInformation();
          }
          return SUCCESS;
    }

     /**
     *
     */
    private void loadLeadManagementTabInformation() {
        // Get the active leads and display them
        String roleType = this._commonCriteria.getRoleType();
        if (NEWCO_ADMIN.equals(roleType)) {
            Integer roleId = this._commonCriteria.getSecurityContext()
                    .getRoleId();
            if (roleId != null && roleId.equals(PROVIDER_ROLEID)) {
                roleType = PROVIDER;
            }
        }
        getSession().setAttribute("roleType",
                this._commonCriteria.getSecurityContext().getRoleId());
        Integer vendorId = this._commonCriteria.getCompanyId();
        String status = getRequest().getParameter("status");
        String countRequired = getRequest().getParameter("count");
        if(StringUtils.isBlank(status)){
            status = ACTIVE;
        }
        if(StringUtils.isBlank(countRequired)){
            // TBD
            //countRequired = DEFAULT_COUNT;
            try{
            countRequired=applicationProperties.getPropertyValue(PROVIDER_LEAD_MANAGEMENT_DEFAULT_LEAD_COUNT);
            }
            catch (DataNotFoundException e) {
                logger.info("Exception thrown while getting default count for provider lead management");
                e.printStackTrace();
               
            }
            }
       
        List<LeadInfoDetail> leadDetailsList = new ArrayList<LeadInfoDetail>();
        LeadInfoResponse response = leadClient.getResponseForLeadDetails(
                vendorId, status, countRequired);
        if (null != response) {
            if (null != response.getLeadDetails()&& null != response.getLeadDetails().getLeadDetail()
                    && response.getLeadDetails().getLeadDetail().size() == 0) {
                // No data
                logger.info("no leads are available for " + vendorId);
            } else if (null != response.getLeadDetails()&& null != response.getLeadDetails().getLeadDetail()
                    && response.getLeadDetails().getLeadDetail().size() > 0) {
                leadDetailsList = mapLeadDetails(response
                        .getLeadDetails().getLeadDetail(), ACTIVE);
            }
        } else {
            logger.info("no leads are available for " + vendorId);
        }
        getRequest().setAttribute("activeLeadDetails", leadDetailsList);
        getSession().setAttribute("activeLeadDetailsCount", leadDetailsList.size());
        getSession().setAttribute("countRequired", countRequired);
        getSession().setAttribute("totalActiveLeadCount", response.getTotalLeadCount());
        logger.info("Size of the list::" + leadDetailsList.size());
    }
   
    /**
     * ViewActiveleads
     * @return
     */
    @SkipValidation
    public String viewActiveLeads(){
       
        // Get the active leads and display them
        String roleType = this._commonCriteria.getRoleType();
        if (NEWCO_ADMIN.equals(roleType)) {
            Integer roleId = this._commonCriteria.getSecurityContext()
                    .getRoleId();
            if (roleId != null && roleId.equals(PROVIDER_ROLEID)) {
                roleType = PROVIDER;
            }
        }
        getSession().setAttribute("roleType",
                this._commonCriteria.getSecurityContext().getRoleId());
        Integer vendorId = this._commonCriteria.getCompanyId();
        String status = getRequest().getParameter("status");
        // Count should be incremented based on the data set in the properties, not base on the
        // data passed from front end
        String countRequired = getRequest().getParameter("count");
        try{
            countRequired=applicationProperties.getPropertyValue(PROVIDER_LEAD_MANAGEMENT_DEFAULT_LEAD_COUNT);
            }
            catch (DataNotFoundException e) {
                logger.info("Exception thrown while getting default count for provider lead management");
                e.printStackTrace();
               
            }
       
        String viewMoreInd = getRequest().getParameter("viewMore");
        Integer activeLeadDetailsCount = null;
        if(StringUtils.isBlank(countRequired)){
            if(null!= getSession().getAttribute("countRequired")){
                if(StringUtils.isNotBlank((String)getSession().getAttribute("countRequired"))){
                    countRequired = (String)getSession().getAttribute("countRequired");
                }
            }
        }
        if(StringUtils.isNotBlank(viewMoreInd)){
            if(viewMoreInd.equals("true")){
                if(null!=getSession().getAttribute("activeLeadDetailsCount")){
                    activeLeadDetailsCount = (Integer)getSession().getAttribute("activeLeadDetailsCount");
                    countRequired = String.valueOf(Integer.parseInt(countRequired)+activeLeadDetailsCount.intValue());
                }

            }
        }
        List<LeadInfoDetail> leadDetailsList = new ArrayList<LeadInfoDetail>();
        LeadInfoResponse response = leadClient.getResponseForLeadDetails(
                vendorId, status, countRequired);
        if (null != response) {
            if (null != response.getLeadDetails()&& null != response.getLeadDetails().getLeadDetail()
                    && response.getLeadDetails().getLeadDetail().size() == 0) {
                // No data
                logger.info("no leads are available for " + vendorId);
            } else if (null != response.getLeadDetails() && null != response.getLeadDetails().getLeadDetail()
                    && response.getLeadDetails().getLeadDetail().size() > 0) {
                leadDetailsList = mapLeadDetails(response
                        .getLeadDetails().getLeadDetail(), ACTIVE);
            }
        } else {
            logger.info("no leads are available for " + vendorId);
        }
        getRequest().setAttribute("activeLeadDetails", leadDetailsList);
        getSession().setAttribute("activeLeadDetailsCount", leadDetailsList.size());
        getSession().setAttribute("countRequired", countRequired);
        getSession().setAttribute("totalActiveLeadCount", response.getTotalLeadCount());
        logger.info("Size of the list::" + leadDetailsList.size());
        return SUCCESS;
    }
   
    /**
     * Viee Inacitve leads
     * @return
     */
    @SkipValidation
    public String viewInactiveLeads(){
       
        // Get the active leads and display them
        String roleType = this._commonCriteria.getRoleType();
        if (NEWCO_ADMIN.equals(roleType)) {
            Integer roleId = this._commonCriteria.getSecurityContext()
                    .getRoleId();
            if (roleId != null && roleId.equals(PROVIDER_ROLEID)) {
                roleType = PROVIDER;
            }
        }
        getSession().setAttribute("roleType",
                this._commonCriteria.getSecurityContext().getRoleId());
        Integer vendorId = this._commonCriteria.getCompanyId();
        String status = getRequest().getParameter("status");
        String countRequired = getRequest().getParameter("count");
        try{
            countRequired=applicationProperties.getPropertyValue(PROVIDER_LEAD_MANAGEMENT_DEFAULT_LEAD_COUNT);
            }
            catch (DataNotFoundException e) {
                logger.info("Exception thrown while getting default count for provider lead management");
                e.printStackTrace();
               
            }
       
        String viewMoreInd = getRequest().getParameter("viewMore");
        Integer inactiveLeadDetailsCount = null;
        if(StringUtils.isBlank(countRequired)){
            if(null!= getSession().getAttribute("countRequired")){
                if(StringUtils.isNotBlank((String)getSession().getAttribute("countRequired"))){
                    countRequired = (String)getSession().getAttribute("countRequired");
                }
            }
        }
        if(StringUtils.isNotBlank(viewMoreInd)){
            if(viewMoreInd.equals("true")){
                if(null!=getSession().getAttribute("inactiveLeadDetailsCount")){
                    inactiveLeadDetailsCount = (Integer)getSession().getAttribute("inactiveLeadDetailsCount");
                    countRequired = String.valueOf(Integer.parseInt(countRequired)+inactiveLeadDetailsCount.intValue());
                }

            }
        }
        List<LeadInfoDetail> leadDetailsList = new ArrayList<LeadInfoDetail>();
        LeadInfoResponse response = leadClient.getResponseForLeadDetails(
                vendorId, status, countRequired);
        if (null != response) {
            if (null != response.getLeadDetails() && null != response.getLeadDetails().getLeadDetail()
                    && response.getLeadDetails().getLeadDetail().size() == 0) {
                // No data
                logger.info("no leads are available for " + vendorId);
            } else if (null != response.getLeadDetails() && null != response.getLeadDetails().getLeadDetail()
                    && response.getLeadDetails().getLeadDetail().size() > 0) {
                leadDetailsList = mapLeadDetails(response
                        .getLeadDetails().getLeadDetail(), INACTIVE);
            }
        } else {
            logger.info("no leads are available for " + vendorId);
        }
        getRequest().setAttribute("inActiveLeadDetails", leadDetailsList);
        getSession().setAttribute("inactiveLeadDetailsCount", leadDetailsList.size());
        getSession().setAttribute("countRequired", countRequired);
        getSession().setAttribute("totalInActiveLeadCount", response.getTotalLeadCount());
        logger.info("Size of the list::" + leadDetailsList.size());
        return SUCCESS;
    }
   
    /**
     * Map the data to DTO list
     *
     * @param leadDetail
     * @return
     */
    private List<LeadInfoDetail> mapLeadDetails(List<LeadDetail> leadDetail, String status) {
        List<LeadInfoDetail> leadDetailsList = new ArrayList<LeadInfoDetail>();
        if (null != leadDetail && !leadDetail.isEmpty()) {
            for (LeadDetail leadData : leadDetail) {
                LeadInfoDetail leadDTOData = new LeadInfoDetail();
                leadDTOData = mapLeadDetail(leadData,leadDTOData,status);
                leadDetailsList.add(leadDTOData);
            }
        }
            return leadDetailsList;
    }
   
    private LeadInfoDetail mapLeadDetail(LeadDetail leadData, LeadInfoDetail leadDTOData, String status) {
        leadDTOData.setLeadId(leadData.getSlLeadId());
        leadDTOData.setLmsLeadId(leadData.getLmsLeadId());
        // A 'Stale' lead is one that was either New or Working, but over [x] days old
        if(INACTIVE.equalsIgnoreCase(status)
                && (NEW.equalsIgnoreCase(leadData.getLeadStatus())
                        || WORKING.equalsIgnoreCase(leadData.getLeadStatus()))){
            leadDTOData.setLeadStatus(STALE);
        }else{
            leadDTOData.setLeadStatus(leadData.getLeadStatus());
        }

        leadDTOData.setProjectType(leadData.getProjectType());
        if(StringUtils.isNotBlank(leadData.getSkill())){
        	String skill=leadData.getSkill();
        	skill=skill.toLowerCase();
        	skill=StringUtils.capitalize(skill);
        	leadDTOData.setSkill(skill);
        }
        if (SAME_DAY.equalsIgnoreCase(leadData.getUrgency())) {
            leadDTOData.setUrgency(SAME_DAY_MAPPING);
        }else if (NEXT_DAY.equalsIgnoreCase(leadData.getUrgency())) {
            leadDTOData.setUrgency(NEXT_DAY_DESC);
        }
        else if (AFTER_TOMORROW.equalsIgnoreCase(leadData.getUrgency())) {
            leadDTOData.setUrgency(AFTER_TOMORROW_DESC);
        } else {
            leadDTOData.setUrgency(EMPTY_STRING);
        }

        leadDTOData.setCustFirstName(leadData.getCustFirstName());
        leadDTOData.setCustLastName(leadData.getCustLastName());
        leadDTOData.setCustPhoneNo(formatPhoneNumber(leadData
                .getCustPhoneNo()));
        leadDTOData.setCity(leadData.getCustCity());
        leadDTOData.setLeadDescription(leadData.getDescription());

        /*
         * Format created date to 1. 2013-11-21T13:37:17
         * yyyy-MM-dd'T'HH:mm:ss 2. 11/21/13 1:36 PM "MM/dd/YY hh:mm a"
         */
        leadDTOData.setFormattedDreatedDate(leadData
                .getCreatedDateFormatted());
        leadDTOData.setCreatedDate(leadData.getCreatedDate());
        leadDTOData.setZip(leadData.getCustZip());
        return leadDTOData;
    }

    /**
     * Format the date
     *
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
     * Method to format the phone number(0000000000) to 000-000-0000
     * @param phoneNumber
     * @return
     */
    private static String formatPhoneNumber(String phoneNumber){
            String formattedPhone = StringUtils.EMPTY;
            if(null!=phoneNumber){
                if(phoneNumber.length()>2 && !phoneNumber.contains(SOPDFConstants.HYPHEN)){
                    formattedPhone=phoneNumber.substring(0, 3);
                    if(phoneNumber.length()>5){
                        formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(3, 6);
                    }else if(phoneNumber.length()>3){
                        formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(3);
                    }
                    if(phoneNumber.length()>6){
                        formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(6);
                    }
                }else{
                    formattedPhone=phoneNumber;
                }
            }
            return formattedPhone;
    }
   
    /**
     * View Lead Details
     * @return
     */
    @SkipValidation
    public String viewLeadDetails(){
       
        // Get the active leads and display them
        String roleType = this._commonCriteria.getRoleType();
        if (NEWCO_ADMIN.equals(roleType)) {
            Integer roleId = this._commonCriteria.getSecurityContext()
                    .getRoleId();
            if (roleId != null && roleId.equals(PROVIDER_ROLEID)) {
                roleType = PROVIDER;
            }
        }
        getSession().setAttribute("roleType",
                this._commonCriteria.getSecurityContext().getRoleId());
        Integer vendorId = this._commonCriteria.getCompanyId();
        String leadId = getRequest().getParameter("leadId");
        
        if(null==leadId || (null!=leadId && leadId.equals(""))){
            leadId = (String)getSession().getAttribute("leadId");
        }
        LeadInfoDetail leadDetail = new LeadInfoDetail();        
        GetIndividualLeadDetailsResponse getIndividualLeadDetailsResponse = leadClient.getResponseForIndividualLeadDetails(vendorId,leadId);
        if (null != getIndividualLeadDetailsResponse) {
            if (null == getIndividualLeadDetailsResponse.getIndividualLeadDetail()) {
                // No data
                logger.info("no leads are available for " + vendorId);
            } else if (null != getIndividualLeadDetailsResponse.getIndividualLeadDetail()) {
                leadDetail = mapLeadDetail(getIndividualLeadDetailsResponse
                        .getIndividualLeadDetail(),leadDetail);
                leadDetail.setVendorId(vendorId.toString());
            }
        } else {
            logger.info("no leads are available for " + vendorId);
        }
        lmTabDTO.setLead(leadDetail);
       
        if(!(NEW.equalsIgnoreCase(leadDetail.getLeadStatus()))){
            getEligibleProviders();
        }
        if(NEW.equalsIgnoreCase(leadDetail.getLeadStatus())
                || WORKING.equalsIgnoreCase(leadDetail.getLeadStatus())
                || STALE.equalsIgnoreCase(leadDetail.getLeadStatus())){
            //Get the reasoncodes to display in the call customer widget.
            getReasoncodesForCallCustomer(leadDetail.getLeadId(),leadDetail.getVendorId());
        }
        if(!(CANCELLED.equalsIgnoreCase(leadDetail.getLeadStatus()) && COMPLETED.equalsIgnoreCase(leadDetail.getLeadStatus()) )){
            cancelLeadReason();
        }
        if(NEW.equalsIgnoreCase(leadDetail.getLeadStatus())){
            return NEW;
        }
        else if(WORKING.equalsIgnoreCase(leadDetail.getLeadStatus())){
            return WORKING;
        }
        else if(SCHEDULED.equalsIgnoreCase(leadDetail.getLeadStatus())&& leadDetail.getResourceAssigned()!=null){
            return SCHEDULED_PROVIDER;

        }
        else if(SCHEDULED.equalsIgnoreCase(leadDetail.getLeadStatus())){
            return SCHEDULED;
        }
        else if(COMPLETED.equalsIgnoreCase(leadDetail.getLeadStatus())){
            return COMPLETED;
        }
        else if(CANCELLED.equalsIgnoreCase(leadDetail.getLeadStatus())){
            return CANCELLED;
        }
        else if(STALE.equalsIgnoreCase(leadDetail.getLeadStatus())){
            return STALE;
        }
        return ERROR;
    }
   
   

    private LeadInfoDetail mapLeadDetail(IndividualLeadDetail leadData,LeadInfoDetail leadDTOData) {
    	String businessName = "";
    	Integer vendorId = this._commonCriteria.getCompanyId();
    	Integer resourceId = this._commonCriteria.getVendBuyerResId();
    	try{
            businessName = providerLeadManagementService.getBusinessNameOfVendor(vendorId);
           }
           catch(Exception e){
        	   logger.info("Exception while getting business name:"+e.getMessage());
           }
        leadDTOData.setLeadId(leadData.getSlLeadId());
        leadDTOData.setLmsLeadId(leadData.getLmsLeadId());
        // A 'Stale' lead is one that was either New or Working, but over [x] days old
        leadDTOData.setLeadStatus(leadData.getLeadStatus());
        leadDTOData.setProjectType(leadData.getProjectType());
        //capitilizing skill(Install/Repair)
        if(StringUtils.isNotBlank(leadData.getSkill())){
        	String skill=leadData.getSkill();
        	skill=skill.toLowerCase();
        	skill=StringUtils.capitalize(skill);
        	leadDTOData.setSkill(skill);
        }
        if (SAME_DAY.equalsIgnoreCase(leadData.getUrgencyOfService())) {
            leadDTOData.setUrgency(SAME_DAY_MAPPING);
        } else if (NEXT_DAY.equalsIgnoreCase(leadData.getUrgencyOfService())) {
            leadDTOData.setUrgency(NEXT_DAY_DESC);
        }
        else if (AFTER_TOMORROW.equalsIgnoreCase(leadData.getUrgencyOfService())) {
            leadDTOData.setUrgency(AFTER_TOMORROW_DESC);
        }
        else {
            leadDTOData.setUrgency(EMPTY_STRING);
        }
        leadDTOData.setCustFirstName(leadData.getFirstName());
        leadDTOData.setCustLastName(leadData.getLastName());
        leadDTOData.setFormattedCustPhoneNo(formatPhoneNumber(leadData
                .getPhoneNumber()));
        leadDTOData.setCustPhoneNo(leadData.getPhoneNumber());
        leadDTOData.setEmail(leadData.getEmail());
        leadDTOData.setLeadCategory(leadData.getLeadCategory());
        leadDTOData.setLeadSource(leadData.getLeadSource());
        if(leadData.getAddress()!=null){
            leadDTOData.setCity(leadData.getAddress().getCity());
            leadDTOData.setStreet(leadData.getAddress().getStreet());
            leadDTOData.setState(leadData.getAddress().getState());
            leadDTOData.setZip(leadData.getAddress().getZip());
        }
        if(leadData.getPreferredAppointment()!=null){
            leadData.setFormattedServiceDate();
            leadDTOData.setServiceDate(leadData.getFormattedServiceDate());
            if(null != leadData.getStartTime() && null != leadData.getEndTime()){
            	leadData.setFormattedServiceTime1();
                leadData.setFormattedServiceTime2();
                leadDTOData.setServiceStartTime(leadData.getFormattedServiceTime1());
                leadDTOData.setServiceEndTime(leadData.getFormattedServiceTime2());
            }
            else{
            	leadDTOData.setServiceStartTime(null);
            	leadDTOData.setServiceEndTime(null);
            }
            leadDTOData.setTimeZone(leadData.getServiceTimeZone());
           
        }
        if(leadData.getScheduledDate()!=null){
            leadData.setFormattedScheduledDate();
            //leadData.setFormattedScheduleTime1();
            //leadData.setFormattedScheduleTime2();
            leadDTOData.setScheduledDate(leadData.getFormattedScheduledDate());
            leadDTOData.setScheduledStartTime(leadData.getScheduledStartTime());
            leadDTOData.setScheduledEndTime(leadData.getScheduledEndTime());
       
        }
        leadDTOData.setLeadDescription(leadData.getDescription());
        leadDTOData.setIndustry(leadData.getLeadCategory());       
        leadDTOData.setLeadType(leadData.getLeadType());
        leadDTOData.setLeadPrice("$"+PricingUtil.getRoundedMoneyBigDecimal(leadData.getLeadPrice()).toString());
        leadDTOData.setFormattedDreatedDate(leadData.getCreatedDateFormatted());
        leadDTOData.setCreatedDate(leadData.getCreatedDate());
        leadDTOData.setResFirstName(leadData.getResFirstName());
        leadDTOData.setResLastName(leadData.getResLastName());
        leadDTOData.setResourceAssigned(leadData.getResourceAssigned());
        if(leadData.getResRating()!=null){
            leadDTOData.setRating(UIUtils.calculateScoreNumber(leadData.getResRating()));
        }
        else {
            leadDTOData.setRating(0);
        }
        if(leadData.getCompletionDate() != null){
            leadDTOData.setCompletionDate(leadData.getCompletionDate());
            leadDTOData.setCompletionDate();
        }
        if(leadData.getCompletionTime() != null){
            leadDTOData.setCompletionTime(leadData.getCompletionTime());
        }
       
        leadDTOData.setNumberOfTrips(leadData.getNumberOfTrips());
        leadDTOData.setCompletionComments(leadData.getCompletionComments());
        if(leadData.getLeadFinalPrice() != null){
            leadDTOData.setLeadFinalPrice("$"+PricingUtil.getRoundedMoneyBigDecimal(leadData.getLeadFinalPrice()).toString());
        }
        if(leadData.getLeadLaborPrice() != null){
            leadDTOData.setLeadLaborPrice("$"+PricingUtil.getRoundedMoneyBigDecimal(leadData.getLeadLaborPrice()).toString());
        }
        if(leadData.getLeadMaterialPrice() != null){
            leadDTOData.setLeadMaterialPrice("$"+PricingUtil.getRoundedMoneyBigDecimal(leadData.getLeadMaterialPrice()).toString());
        }
       
        if(leadData.getCancelledDate() != null){
            leadDTOData.setCancelledDate(leadData.getCancelledDate());
            leadDTOData.setCancelledDate();
            }
        leadDTOData.setCancelledBy(leadData.getCancelledBy());
        leadDTOData.setCancelledReason(leadData.getCancelledReason());
       
        Notes notes = new Notes();
        if(leadData.getNotes()!=null && leadData.getNotes().getNoteList()!=null && !(leadData.getNotes().getNoteList().isEmpty())){
            List<Note> notesList = new ArrayList<Note>();
            SecurityContext leadContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
            String providerName = leadContxt.getUsername();
            for(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Note note2:leadData.getNotes().getNoteList()){
            	if(null!= note2 && note2.getRoleId() == 1 && null!=note2.getModifiedBy() && 
            			note2.getModifiedBy().equalsIgnoreCase(providerName)){
            	Note note = new Note();
                note.setMessage(note2.getMessage());
                note.setNoteBy(note2.getNoteBy());
                note.setNoteDate(note2.getNoteDate());
                //note.setFormattedNoteDate();
                //  note.setNoteDate();
                note.setSubject(note2.getSubject());
                note.setNoteId(note2.getNoteId());
                note.setRoleId(note2.getRoleId());
                notesList.add(note);
            }
            	else if(null!= note2 && note2.getRoleId() == 3){
            		Note note = new Note();
                    note.setMessage(note2.getMessage());
                    note.setNoteBy(note2.getNoteBy());
                    note.setNoteDate(note2.getNoteDate());
                   // note.setFormattedNoteDate();
                   // note.setNoteDate();
                    note.setSubject(note2.getSubject());
                    note.setNoteId(note2.getNoteId());
                    note.setRoleId(note2.getRoleId());
                    notesList.add(note);
            	}
            }
            notes.setNoteList(notesList);
        }
        leadDTOData.setNotes(notes);
        Historys  historys= new Historys();
        if(leadData.getHistorys()!=null && leadData.getHistorys().getHistoryList()!=null && !(leadData.getHistorys().getHistoryList().isEmpty())){
           
            List<History> historysList = new ArrayList<History>();
            SecurityContext leadContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
            String providerName = leadContxt.getUsername();
           
            for(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.History history1:leadData.getHistorys().getHistoryList()){
            	
           if(null!=history1 && null!=history1.getActionId() && (history1.getActionId()!=1 && history1.getActionId()!=2 && history1.getActionId()!=9)){
            	if(null!= history1 && history1.getRoleId() == 1 && null!=history1.getModifiedBy() && 
               		 history1.getModifiedBy().equalsIgnoreCase(providerName)){
                History history = new History();
                history.setDescription(history1.getDescription());
                //from history table the whole data is getting here it is splitting according to
                //required firm status for displaying particular status as a template
                if(history.getDescription().contains(HISTORY_STATUS_WORKING)){
                    history=getHistoryDetailsOfStatusChange(history,WORKING);
                   
                }
                else if(history.getDescription().contains(HISTORY_STATUS_SCHEDULED)){
                    history=getHistoryDetailsOfStatusChange(history,SCHEDULED);
                   
                }   
                else if(history.getDescription().contains(HISTORY_STATUS_CANCELLED)){
                    history=getHistoryDetailsOfStatusChange(history,CANCELLED);
                    
                 
                }
                else if(history.getDescription().contains(HISTORY_STATUS_COMPLETED)){
                    history=getHistoryDetailsOfStatusChange(history,COMPLETED);
                   
                }
               
                history.setHistoryAction(history1.getHistoryAction());
                history.setHistoryBy(history1.getHistoryBy());
               
               /* if(null!=history1.getFormattedHistoryDate()){
                    history.setHistoryDate(history1.getFormattedHistoryDate());
                    history.setFormattedHistoryDate();
                }else{*/
                    history.setHistoryDate(history1.getHistoryDate());
                   // history.setFormattedHistoryDate();
                  /*  }*/
                         
                historysList.add(history);
            } else if(null!= history1 && history1.getRoleId() == 1 && 
            		null!=history1.getModifiedBy() && null!=history1.getEntityId() && (history1.getModifiedBy().
            				equalsIgnoreCase("System")) && history1.getEntityId().equals(resourceId)){	
            	
            	 History history = new History();
                 history.setDescription(history1.getDescription());
                 //from history table the whole data is getting here it is splitting according to
                 //required firm status for displaying particular status as a template
                 if(history.getDescription().contains(HISTORY_STATUS_WORKING)){
                     history=getHistoryDetailsOfStatusChange(history,WORKING);
                    
                 }
                 else if(history.getDescription().contains(HISTORY_STATUS_SCHEDULED)){
                     history=getHistoryDetailsOfStatusChange(history,SCHEDULED);
                    
                 }   
                 else if(history.getDescription().contains(HISTORY_STATUS_CANCELLED)){
                     history=getHistoryDetailsOfStatusChange(history,CANCELLED);
                     
                  
                 }
                 else if(history.getDescription().contains(HISTORY_STATUS_COMPLETED)){
                     history=getHistoryDetailsOfStatusChange(history,COMPLETED);
                    
                 }
                
                 history.setHistoryAction(history1.getHistoryAction());
                 history.setHistoryBy(history1.getHistoryBy());
                
               /*  if(null!=history1.getFormattedHistoryDate()){
                     history.setHistoryDate(history1.getFormattedHistoryDate());
                     history.setFormattedHistoryDate();
                 }else{*/
                     history.setHistoryDate(history1.getHistoryDate());
                   //  history.setFormattedHistoryDate();
                    // }
                          
                 historysList.add(history);
             }
              else if(history1.getRoleId() == 3){
                  History history = new History();
                  
                  if (history1.getDescription().contains(HISTORY_STATUS_CANCELLED)){
                     if(history1.getDescription().contains(businessName)){
                     history.setDescription(history1.getDescription());
                     history.setHistoryAction(history1.getHistoryAction());
                     history.setHistoryBy(history1.getHistoryBy());
                    
                   /*  if(null!=history1.getFormattedHistoryDate()){
                         history.setHistoryDate(history1.getFormattedHistoryDate());
                         history.setFormattedHistoryDate();
                     }else{*/
                         history.setHistoryDate(history1.getHistoryDate());
                        // history.setFormattedHistoryDate();
                   //  }
                     
                       
                     historysList.add(history);
                         }
                      
                  }else{ 
                      history.setDescription(history1.getDescription());
                       history.setHistoryAction(history1.getHistoryAction());
                      history.setHistoryBy(history1.getHistoryBy());
                     
                     /* if(null!=history1.getFormattedHistoryDate()){
                          history.setHistoryDate(history1.getFormattedHistoryDate());
                          history.setFormattedHistoryDate();
                      }else{*/
                          history.setHistoryDate(history1.getHistoryDate());
                         // history.setFormattedHistoryDate();
                    //  }
                      
                        
                      historysList.add(history);
                  }
                  
                  
                  }
          
            }
                historys.setHistoryList(historysList);    
            }
           }
        leadDTOData.setHistory(historys);

        Attachments  attachments= new Attachments();
        if(leadData.getAttachments()!=null && leadData.getAttachments().getAttachmentList()!=null && !(leadData.getAttachments().getAttachmentList().isEmpty())){
           
            List<Attachment> attachmentsList = new ArrayList<Attachment>();
            String providerName = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
            for(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Attachment attachment1:leadData.getAttachments().getAttachmentList()){
            	if(null!= attachment1){
            	String createdBy=attachment1.getFirstName()+" "+attachment1.getLastName();
            	if(null != createdBy && createdBy.equalsIgnoreCase(providerName)){
                Attachment attachment = new Attachment();
                attachment.setDocumentId(attachment1.getDocumentId());
                attachment.setDocumentName(attachment1.getDocumentName());
                attachment.setDocumentSize(attachment1.getDocumentSize());
                attachment.setFirstName(attachment1.getFirstName());
                attachment.setLastName(attachment1.getLastName());
               
                if(null!=attachment1.getCreatedDate()){
                attachment.setCreatedDate(attachment1.getCreatedDate());
                attachment.setCreatedDate();
                //attachment.setFormatedCreatedDate();
                }
                attachment.setDocCategoryId(attachment1.getDocCategoryId());
                attachment.setDocPath(attachment1.getDocPath());
                attachmentsList.add(attachment);
            	}
            }
            }
            attachments.setAttachmentList(attachmentsList);
        }
       
        leadDTOData.setAttachments(attachments);
       
       
        return leadDTOData;
    }
   
    public void getEligibleProviders() {
           Integer vendorId = this._commonCriteria.getCompanyId();
        String leadId = getRequest().getParameter("leadId");
        String firmId=vendorId.toString();
        GetEligibleProviderResponse response = leadClient.getResponseForEligibleProviders(firmId, leadId);
        List<EligibleProviderForLead> eligibleProvList=null;
        int resourceId = 0;
        String resFirstName="";
        String resLastName="";
        double distance =0;
        if (null != response &&
                null!= response.getProviders() &&
                null!=response.getProviders().getProvider()) {
            if (response.getProviders().getProvider().size() > 0) {
                eligibleProvList = response.getProviders().getProvider();
                if (null != eligibleProvList) {
                    for (EligibleProviderForLead eligibleProvider : eligibleProvList) {
                        if (eligibleProvider != null
                                && eligibleProvider.getResourceId() != null) {
                            resourceId = eligibleProvider.getResourceId();
                            resFirstName =eligibleProvider.getResFirstName();
                            resLastName = eligibleProvider.getResLastName();
                            distance =eligibleProvider.getProviderDistance();
                        }
                    }
                   
                }
                //Method to sort providers based on distance 
                if(null!=eligibleProvList && !eligibleProvList.isEmpty()){
                     Collections.sort(eligibleProvList,new Comparator<EligibleProviderForLead>() {
                     public int compare(EligibleProviderForLead o1,EligibleProviderForLead o2) {
                    	 Double distance1=new Double(o1.getProviderDistance());
                    	 Double distance2=new Double(o2.getProviderDistance());
                    	 if(distance1.equals(distance2)){
                    		 String fullName1=o1.getResFirstName()+o1.getResLastName();
                    		 String fullName2=o2.getResFirstName()+o2.getResLastName();
                    		    if(StringUtils.isNotBlank(fullName1)&& StringUtils.isNotBlank(fullName2)){
                    			    return fullName1.compareToIgnoreCase(fullName2);
                    		       }else{
                    			    o1.getResFirstName().compareToIgnoreCase(o2.getResFirstName());
                    		      }
                    		 
                    	 }else{
						    return distance1.compareTo(distance2);
                    	 }
					        return distance1.compareTo(distance2);
					}
                 });
                }
                getRequest().setAttribute("providerList",eligibleProvList);
                getSession().setAttribute("providerResourceId",resourceId);
                getSession().setAttribute("providerFirstName",resFirstName);
                getSession().setAttribute("providerLastName",resLastName);
                getSession().setAttribute("distance",distance);
            }   
        }
         else {
                logger.info("no eligible providers are available for " + vendorId);
            }
    }
    public String assignProvider(){
       SecurityContext secContxt = (SecurityContext) getSession().getAttribute(
                SECURITY_CONTEXT);
       String user_name = "";
       String full_name = "";
       Integer entityId = 0;
       if(secContxt.isAdopted()){
			
    	   full_name = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
    	   user_name = secContxt.getUsername();
    	   entityId = secContxt.getVendBuyerResId();
		}
		else{
			full_name = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
			user_name = secContxt.getUsername();
			entityId = secContxt.getVendBuyerResId();
		}
       	String resId=getRequest().getParameter("resourceId");
        Integer resourceId=Integer.parseInt(resId);
        Integer firmId=secContxt.getCompanyId();
        String leadId = getRequest().getParameter("leadId");
        
        if(null==leadId || (null!=leadId && leadId.equals(""))){
            leadId = (String)getSession().getAttribute("leadId");
        }
       
        AssignOrReassignProviderRequest assignProviderRequest=new AssignOrReassignProviderRequest();
        Identification identification=new Identification();
        identification.setType("ProviderResourceId");
        identification.setId(this._commonCriteria.getVendBuyerResId());
        identification.setFullName(full_name);
        identification.setUserName(user_name);
        identification.setEntityId(entityId);
        assignProviderRequest.setIdentification(identification);
        assignProviderRequest.setLeadId(leadId);
        assignProviderRequest.setFirmId(firmId.toString());
        assignProviderRequest.setResourceId(resourceId);
        AssignOrReassignProviderResponse assignOrReassignProviderResponse=leadClient.getResponseForAssignOrReassignProvider(assignProviderRequest);
       
        if (null == assignOrReassignProviderResponse) {
             logger.info("API call failed");
        } else if (null != assignOrReassignProviderResponse.getResults().getError()) {
           
            logger.info("Error while assigning provider");
             return "json";
        }
        else{
            logger.info("Success");
            getSession().setAttribute("respMsg", "Successfully Saved");
            }
        return "json";
    }
    
    
    public String scheduleLead(){
        lmTabDTO=getModel();
        String leadId=getRequest().getParameter("leadId");
        
        if(null==leadId || (null!=leadId && leadId.equals(""))){
            leadId = (String)getSession().getAttribute("leadId");
        }
        SecurityContext secContxt = (SecurityContext) getSession().getAttribute(
                SECURITY_CONTEXT);
        String user_name = "";
        String full_name = "";
        Integer entityId = 0;
        if(secContxt.isAdopted()){
 			
     	   full_name = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
     	   user_name = secContxt.getUsername();
     	   entityId = secContxt.getVendBuyerResId();
 		}
 		else{
 			full_name = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
 			user_name = secContxt.getUsername();
 			entityId = secContxt.getVendBuyerResId();
 		}
        if( null!= lmTabDTO){
            ScheduleAppointmentRequest request=new ScheduleAppointmentRequest();
            Identification identification=new Identification();
            identification.setType("ProviderResourceId");
            identification.setId(this._commonCriteria.getVendBuyerResId());
            identification.setFullName(full_name);
            identification.setUserName(user_name);
            identification.setEntityId(entityId);
            request.setIdentification(identification);
            request.setLeadId(leadId);
            request.setVendorId(this._commonCriteria.getCompanyId());
            String unFormattedDate=lmTabDTO.getScheduledDate().trim(); // 1/30/2014
            String formattedDateString = "";
            if(!StringUtils.isEmpty(unFormattedDate)){
            	Date unformattedDateValue = null;
				try {
					unformattedDateValue = new SimpleDateFormat(READ_DATE_FORMAT).parse(unFormattedDate); // Thu Jan 30 00:01:00 CST 2014
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(null!=unformattedDateValue){
					formattedDateString=new SimpleDateFormat(XML_DATE_FORMAT).format(unformattedDateValue); //2014-01-30
				}
            }
            request.setServiceDate(formattedDateString);
            if (StringUtils.isNotBlank(lmTabDTO.getScheduledStartTime())) {
				DateFormat timeFormatParse = new SimpleDateFormat(READ_TIME_FORMAT);
				String timeInput = lmTabDTO.getScheduledStartTime();
				String timeOutPut = "";
				Date timeString = null;
				try {
					timeString = timeFormatParse.parse(timeInput);
				} catch (ParseException e) {
					logger.info("Exception in parsing time");
				}
				logger.info("Time after parse:" + timeString);
				try {
					timeOutPut = timeFormatParse.format(timeString);
				} catch (Exception e) {
					logger.info("Exception in formatting time");
				}
				logger.info("Time after formatt:" + timeOutPut);
				request.setServiceStartTime(timeOutPut);
			}
            if (StringUtils.isNotBlank(lmTabDTO.getScheduledEndTime())) {
				DateFormat timeFormatParse = new SimpleDateFormat(READ_TIME_FORMAT);
				String timeInput  = lmTabDTO.getScheduledEndTime();
				String timeOutPut = "";
				Date timeString = null;
				try {
					timeString = timeFormatParse.parse(timeInput);
				} catch (ParseException e) {
					logger.info("Exception in parsing time");
				}
				logger.info("Time after parse:" + timeString);
				try {
					timeOutPut = timeFormatParse.format(timeString);
				} catch (Exception e) {
					logger.info("Exception in formatting time");
				}
				logger.info("Time after formatt:" + timeOutPut);
				request.setServiceEndTime(timeOutPut);
			}
            request.setResheduleReason(lmTabDTO.getReason());
            try {
                ScheduleAppointmentResponse response=leadClient.scheduleLead(request);
                if (null == response) {
                     logger.info("API call for scheduling the lead failed");
                }else if (null != response.getResults().getError()) {
                	String errorMessage=response.getResults().getError().get(0).getMessage();
                    lmTabDTO.setResponseMessage(errorMessage);
                }else{
                	String errorMessage="Your appointment has been saved.";
                	lmTabDTO.setResponseMessage(errorMessage);
                    getSession().setAttribute("respMsg", "Your appointment has been saved.");
                }
              } catch (Exception e) {
                logger.info("Exception in scheduling lead"+ e.getMessage());
               
            }
           
        }
        return "json";
       
    }
    
    

    /**
     * View Lead Details
     * @return
     */
    public String addNote(){
        String leadId = getRequest().getParameter("leadId");
        String noteSubject = getRequest().getParameter("noteSubject");
        String noteMessage = getRequest().getParameter("noteMessage");
        String leadNoteId = getRequest().getParameter("leadNoteId");
        Integer noteId = null;
        if(null!=leadNoteId && !leadNoteId.equals("null") && !leadNoteId.equals("")){
            noteId = Integer.parseInt(leadNoteId);
        }
        SecurityContext secContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
        String created_by = "";
        Integer entityId = secContxt.getVendBuyerResId();
        if(secContxt.isAdopted()){
 			
           created_by = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
        
 		}
 		else{
 			created_by = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
 		}
       
        LeadNoteType leadNoteType=new LeadNoteType();
        
        // Replace < &lt; > &gt;  & &amp; " &quot;
        
        
        leadNoteType.setNoteBody(replaceSpecialCharacters(noteMessage));
        leadNoteType.setNoteCategory(replaceSpecialCharacters(noteSubject));
        leadNoteType.setNoteType(NewServiceConstants.DEFAULT_NOTE_TYPE);
       
        LeadAddNoteRequest leadAddNoteRequest = new LeadAddNoteRequest();
        leadAddNoteRequest.setLeadId(leadId);
        leadAddNoteRequest.setLeadNoteId(noteId);
        leadAddNoteRequest.setRole(secContxt.getRole());
        leadAddNoteRequest.setVendorBuyerId(secContxt.getCompanyId());
        leadAddNoteRequest.setVendorBuyerResourceId(secContxt.getVendBuyerResId());
        leadAddNoteRequest.setLeadNote(leadNoteType);

        LeadAddNoteResponse addNoteResponse = leadClient.getResponseForLeadAddNote(leadAddNoteRequest);
        if(null==addNoteResponse){
            logger.info("ERROR!!");
        }else if(null!=addNoteResponse.getResults().getError()){
            lmTabDTO.setResponseMessage("Error while adding note: The error is "+addNoteResponse.getResults().getError());
        }
        else{
            logger.info("Success");
            String modifiedBy = secContxt.getUsername();
            LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 
            //TODO: Need to add a record in lu_lead_action table
            leadHistoryVO.setActionId(OrderConstants.BUYER_LEAD_ADD_NOTE);    
            leadHistoryVO.setSlLeadId(leadId);    
            leadHistoryVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
            leadHistoryVO.setCreatedBy(created_by); 
            leadHistoryVO.setModifiedBy(modifiedBy);
            leadHistoryVO.setEnitityId(entityId);
            if(null!=noteId){
                leadHistoryVO.setDescription(OrderConstants.PROVIDER_NOTE_EDIT_TEXT);
            }else{
                leadHistoryVO.setDescription(OrderConstants.PROVIDER_NOTE_TEXT);
            }
            try{
                providerLeadManagementService.insertHistoryForLead(leadHistoryVO);
                if(null==noteId){
                    getSession().setAttribute("respMsg", "Note Added Succesfully");
                }else{
                    getSession().setAttribute("respMsg", "Note Updated Succesfully");
                }
               
            }catch(Exception e){
                logger.error("LeadsManagementControllerAction:Exception while inserting history while adding note:", e);
            }
           
        }
        return "json";
    }

    /**
     * Replace special characters
     * @param value
     * @return
     */
    private String replaceSpecialCharacters(String value) {
    	// Replace < &lt; > &gt;  & &amp; " &quot;
		if(StringUtils.isNotBlank(value)){
			value=value.replaceAll("&","&amp;");
			value=value.replaceAll("<","&lt;");
			value=value.replaceAll(">","&gt;");
			value=value.replaceAll("\"","&quot;");
		}
		return value;
	}
    
    public String uploadDocument(){
       
        List<String> newActionErrors = new ArrayList <String>();
        lmTabDTO=getModel();
        String leadId=getRequest().getParameter("leadId");
        
        if(null==leadId || (null!=leadId && leadId.equals(""))){
            leadId = (String)getSession().getAttribute("leadId");
        }
        String fileName = getRequest().getParameter("fileName");    
        String fileType= null;
        SecurityContext secContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
        String created_by = "";
        Integer entityId = secContxt.getVendBuyerResId();
        if(secContxt.isAdopted()){
 			
           created_by = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
        
 		}
 		else{
 			created_by = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
 		}
        if (StringUtils.isEmpty(leadId)){
            logger.debug("LeadId not available");
            return ERROR;
        }
        try{
        		fileName = URLDecoder.decode( fileName , "UTF-8");
        		fileName = fileName.replaceAll("-prcntg-", "%");
        		File file = null;
                file = (File)lmTabDTO.getFileSelect();   
               
                //FileNameMap fileNameMap = URLConnection.getFileNameMap();
                //String fileType = fileNameMap.getContentTypeFor(fileName);
               
                if(null != file){
                    //validate doc size
                    Long docSize=file.length();
                    Integer size = docSize.intValue()/NewServiceConstants.SIZE_KB;
               
                    if(size>NewServiceConstants.TEN_MB){
                        /*newActionErrors.add("Please attach a file no larger than 5mb.");
                        setActionErrors(newActionErrors);*/
                        lmTabDTO.setResponseMessage("Please attach a file no larger than 10MB.");
                        //getSession().setAttribute("respMsg", "Please attach a file no larger than 10mb.");
                        String returnvalue = getJSON(lmTabDTO);
                        getRequest().setAttribute("returnvalue", returnvalue);
                        return SUCCESS;
                    }
                   
                    String fileExtenstion = "";
                    Integer fileExtensionIndex=fileName.lastIndexOf(".");
                    fileExtenstion = fileName.substring(fileExtensionIndex,fileName.length());
                    if(null!=fileName && !(OrderConstants.EMPTY_STR.equals(fileName.trim()))){
                        if(fileExtensionIndex==-1)
                        {
                            /*newActionErrors.add("Invalid File format. Please use a valid format ");
                            setActionErrors(newActionErrors);*/
                            lmTabDTO.setResponseMessage("Invalid File format. Please use a valid format ");
                            //getSession().setAttribute("respMsg", "Invalid File format. Please use a valid format");
                            String returnvalue = getJSON(lmTabDTO);
                            getRequest().setAttribute("returnvalue", returnvalue);
                            return SUCCESS;
                        }
                        else
                        {
                        if(!(fileExtenstion.equalsIgnoreCase(PNG) || fileExtenstion.equalsIgnoreCase(JPG)||
                                fileExtenstion.equalsIgnoreCase(GIF)|| fileExtenstion.equalsIgnoreCase(DOC)||
                                fileExtenstion.equalsIgnoreCase(DOCX)|| fileExtenstion.equalsIgnoreCase(PDF)|| 
                                fileExtenstion.equalsIgnoreCase(TXT)
                                || fileExtenstion.equalsIgnoreCase(XLS) ||  fileExtenstion.equalsIgnoreCase(XLSX) || 
                                fileExtenstion.equalsIgnoreCase(TIFF) ||  fileExtenstion.equalsIgnoreCase(ZIP) || 
                                fileExtenstion.equalsIgnoreCase(JPEG) ||  fileExtenstion.equalsIgnoreCase(BMP) || 
                                fileExtenstion.equalsIgnoreCase(XML) ||  fileExtenstion.equalsIgnoreCase(PJPEG))){
                               
                            /*newActionErrors.add("Invalid File format. Please use a valid format ");
                            setActionErrors(newActionErrors);*/
                            lmTabDTO.setResponseMessage("Invalid File format. Please use a valid format ");
                            //getSession().setAttribute("respMsg", "Invalid File format. Please use a valid format");
                            String returnvalue = getJSON(lmTabDTO);
                            getRequest().setAttribute("returnvalue", returnvalue);
                            return SUCCESS;
                        }
                        }
                    }       
                    if(fileExtenstion.equalsIgnoreCase(BMP)){
                        fileType = "image/bmp";
                    }else{
                        FileNameMap fileNameMap = URLConnection.getFileNameMap();
                        fileType = fileNameMap.getContentTypeFor(fileName);
                    }   
                   
                }
               
                DocumentVO documentVO = new DocumentVO();
                documentVO.setLeadId(leadId);
                documentVO.setDocument(file);
                documentVO.setFileName(fileName);
                documentVO.setFormat(fileType);
                documentVO.setVendorId(_commonCriteria.getCompanyId());
                documentVO.setRoleId(_commonCriteria.getRoleId());
                documentVO.setDocSize(file.length());
                documentVO.setEntityId(_commonCriteria.getVendBuyerResId());
                documentVO.setCompanyId(_commonCriteria.getCompanyId());
                documentVO.setDescription(DOCUMENT_DESC);
                documentVO.setCreatedBy(created_by);
                documentVO = providerLeadManagementService.uploadDocument(documentVO);
            }
           
       
        catch(Exception e){
            logger.info("Exception in uploading document"+e.getMessage());
            e.printStackTrace();
            lmTabDTO.setResponseMessage("Exception in uploading document"+e.getMessage());
            String returnvalue = getJSON(lmTabDTO);
            getRequest().setAttribute("returnvalue", returnvalue);
            return SUCCESS;
        }
        //resultUrl ="/leadsManagementController_viewLeadDetails.action?leadId="+leadId;
        //insert history
        
        String modifiedBy = _commonCriteria.getSecurityContext().getUsername();
        LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 

        leadHistoryVO.setActionId(OrderConstants.PROVIDER_ATTACH_DOC);    
        leadHistoryVO.setSlLeadId(leadId);    
        leadHistoryVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
        leadHistoryVO.setCreatedBy(created_by); 
        leadHistoryVO.setModifiedBy(modifiedBy); 
        leadHistoryVO.setEnitityId(entityId);
        leadHistoryVO.setDescription(OrderConstants.PROVIDER_ATTACH_DOC_TEXT);
        try{
            providerLeadManagementService.insertHistoryForLead(leadHistoryVO);
            lmTabDTO.setResponseMessage("Document Uploaded Succesfully");
            getSession().setAttribute("respMsg", "Document Uploaded Succesfully");
        }catch(Exception e){
            logger.info("Exception in inserting history while upload document"+e);
        }
        String returnvalue = getJSON(lmTabDTO);
        getRequest().setAttribute("returnvalue", returnvalue);
        return SUCCESS;

    }
    public String getJSON(Object obj) {
        String rval= "null";
        JSONSerializer serializer = new JSONSerializer();

        if (obj != null) try {
            rval= serializer.exclude("*.class").serialize(obj);
        } catch (Throwable e) {
            logger.error("Unable to generate JSON for object: " + obj.toString(), e);
        }               
        return rval;
    }

    public String viewDocument(){
        downloadDocument();       
        return viewLeadDetails();
    }
   
    private String downloadDocument(){
        Integer documentId = null;
        //String leadId=lmTabDTO.getLead().getLeadId();
        try {
            BuyerLeadAttachmentVO document = new BuyerLeadAttachmentVO();
           
            if(null != getRequest().getParameter("docId")){
                documentId = Integer.parseInt(getRequest().getParameter("docId"));
            }
       
            //Retrieving the Document
            if (null != documentId){
                document = providerLeadManagementService.retrieveDocumentByDocumentId(documentId);
   
                SecurityChecker sc = new SecurityChecker();
                String uploadType = sc.securityCheck(document.getFormat());
                getResponse().setContentType(uploadType);
               
                String docName = sc.fileNameCheck(document.getFileName());
                String header = "attachment;filename=\""
                              + docName + "\"";
                getResponse().setHeader("Content-Disposition", header);
                getResponse().setHeader("Cache-Control", "private"); // HTTP 1.1.
                getResponse().setHeader("Pragma", "token"); // HTTP 1.0.
                // getResponse().setDateHeader("Expires", -1); // Proxies.
                InputStream in = new ByteArrayInputStream(document.getBlobBytes());
   
                ServletOutputStream outs = getResponse().getOutputStream();
   
                int bit = 256;
   
                while ((bit) >= 0) {
                   bit = in.read();
                   outs.write(bit);
                }
   
                outs.flush();
                outs.close();
                in.close();
            }
           
           
      } catch (Exception e) {
          e.printStackTrace();
      }
       
        return viewLeadDetails();
    }
   
    public String deleteDocument(){
        Integer documentId = null;
        String leadId=getRequest().getParameter("leadId");
        
        if(null==leadId || (null!=leadId && leadId.equals(""))){
            leadId = (String)getSession().getAttribute("leadId");
        }
        try {
            DocumentVO document = new DocumentVO();
           
            if(null != getRequest().getParameter("docId")){
                documentId = Integer.parseInt(getRequest().getParameter("docId"));
            }
       
            //Retrieving the Document And Deleting it
            if (null != documentId){
               
                document.setEntityId(_commonCriteria.getVendBuyerResId());
                document.setCompanyId(_commonCriteria.getCompanyId());
                document.setRoleId(_commonCriteria.getRoleId());
                document.setLeadId(leadId);
                document.setVendorId(_commonCriteria.getCompanyId());
                document = providerLeadManagementService.removeDocumentByDocumentId(documentId);
                
   
            }
            SecurityContext secContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
            String created_by = "";
            Integer entityId = secContxt.getVendBuyerResId();
            if(secContxt.isAdopted()){
     			
               created_by = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
            
     		}
     		else{
     			created_by = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
     		}
            String modifiedBy = _commonCriteria.getSecurityContext().getUsername();
            LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 

            leadHistoryVO.setActionId(OrderConstants.PROVIDER_ATTACH_DOC);    
            leadHistoryVO.setSlLeadId(leadId);    
            leadHistoryVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
            leadHistoryVO.setCreatedBy(created_by); 
            leadHistoryVO.setModifiedBy(modifiedBy); 
            leadHistoryVO.setDescription(OrderConstants.PROVIDER_DELETE_ATTACH_DOC_TEXT);
            leadHistoryVO.setEnitityId(entityId);
            providerLeadManagementService.insertHistoryForLead(leadHistoryVO);
                
      } catch (Exception e) {
          e.printStackTrace();
      }
      getSession().setAttribute("respMsg", "Document Deleted Succesfully");
        //return viewLeadDetails();
        return "json";
       
    }

    // method added to get particular status from history description
    public History getHistoryDetailsOfStatusChange(History history,
            String status) {
        String description = history.getDescription();
        String splitStatus = status;
        String[] splitArray = null;
        try {
            //splitting description into first part,status and the rest as reason comments
            splitArray = description.split(splitStatus, 2);
        } catch (Exception e) {
            history.setDescription(history.getDescription());
            return history;
        }
        history.setFirmStatus(status);
        history.setDescription(splitArray[0]);
        history.setReasonComment(splitArray[1]);
        return history;
    }

    /**
     * @return
     *
     * method for provider lead cancellation
     */
    public String cancelLead() {
        SecurityContext leadContxt = (SecurityContext) getSession()
                .getAttribute(SECURITY_CONTEXT);
        lmTabDTO = getModel();
        String providerName = leadContxt.getUsername();
        int roleId = leadContxt.getRoleId();
        int vendorId = leadContxt.getCompanyId();
        String businessName = "";

        String leadId = getRequest().getParameter("leadId");
        
        if(null==leadId || (null!=leadId && leadId.equals(""))){
            leadId = (String)getSession().getAttribute("leadId");
        }
        Map<Integer,String>cancelReasonCodes= (Map<Integer, String>) getSession().getAttribute("cancelReasonCodes");
        try{
            businessName = providerLeadManagementService.getBusinessNameOfVendor(vendorId);
           }
           catch(Exception e){
        	   logger.info("Exception while getting business name:"+e.getMessage());
           }
        
        /* Call validation method */
       
        if(null!=lmTabDTO)
        {
        	try{
	            CancelLeadRequest cancelLeadRequest = new CancelLeadRequest();
	           	           
	            /* Format the resource Ids */
	            List<Integer> resourceIds = new ArrayList<Integer>();
	            resourceIds.add(new Integer(vendorId));
	            String comments="";
	            String providers = formatSelectedProviderIds(resourceIds);
				String cancelReason=cancelReasonCodes.get(lmTabDTO.getReasonCode());
	            // Reason code are set in session while loading the Reject Order Pop up.
	            cancelLeadRequest.setLeadId(leadId);
	            cancelLeadRequest.setProviders(providers);
	            cancelLeadRequest.setReasonCode(lmTabDTO.getReasonCode());
	            if(cancelReason.equals(NewServiceConstants.FIRM_OTHER)){
	            	comments= NewServiceConstants.FIRM_OTHER + NewServiceConstants.PIPE_SEPERATOR + lmTabDTO.getComments();
            	}else{
            	 	comments=cancelReason;
           	 	}
            	cancelLeadRequest.setComments(comments);
	            List<ProviderInfoVO> providerMatchedLead = providerLeadManagementService.getProviderInfoForLead(leadId);
	            List<ProviderInfoVO> providerCancelledLead = providerLeadManagementService.getCancelledProviderInfo(leadId);
	           
	            Integer cancelListSize=resourceIds.size()+providerCancelledLead.size();
	
	            if (providerMatchedLead.size() == cancelListSize) {
	                cancelLeadRequest.setChkAllProviderInd(true);
	                cancelLeadRequest.setRevokePointsIndicator(true);
	            } else {
	                cancelLeadRequest.setChkAllProviderInd(false);
	                cancelLeadRequest.setRevokePointsIndicator(false);
	            }
	            cancelLeadRequest.setVendorBuyerName(providerName);
	            cancelLeadRequest.setRoleId(roleId);
	       	    cancelLeadRequest.setCancelInitiatedBy(OrderConstants.FIRM_INITIATED);
	            /* Calls Cancel API */
	            CancelLeadResponse cancelLeadResponse =leadClient.cancelLead(cancelLeadRequest);
	            if (null == cancelLeadResponse) {
	                 logger.info("Cancel API call failed");
	            } else if (null != cancelLeadResponse.getResults().getError()) {
	                lmTabDTO.setResponseMessage("Error while cancelling a lead: The error is "+cancelLeadResponse.getResults().getError());
	                logger.info("Error while Cancel API");
	               
	            }
	            else{
	            	 SecurityContext secContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
	                 String created_by = "";
	                 Integer entityId = secContxt.getVendBuyerResId();
	                 if(secContxt.isAdopted()){
	          			
	                    created_by = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
	                 
	          		}
	          		else{
	          			created_by = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
	          		}
	                
	            String modifiedBy = leadContxt.getUsername();
	            LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 
	            //TODO: Need to add a record in lu_lead_action table
	            leadHistoryVO.setActionId(OrderConstants.PROVIDER_CANCEL_LEAD);    
	            leadHistoryVO.setSlLeadId(leadId);    
	            leadHistoryVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
	            leadHistoryVO.setCreatedBy(created_by); 
	            leadHistoryVO.setModifiedBy(modifiedBy);
	            leadHistoryVO.setEnitityId(entityId);
	            leadHistoryVO.setDescription(NewServiceConstants.STATUS_CHANGE_CANCELLED+" "+comments+" "+ NewServiceConstants.PIPE_SEPERATOR +" "+"Cancelled by the Provider Firm: "+businessName);
	                      
	            try{
	                providerLeadManagementService.insertHistoryForLead(leadHistoryVO);
	                lmTabDTO.setResponseMessage("Lead Cancelled Succesfully");
	                getSession().setAttribute("respMsg", "Lead Cancelled Succesfully by Provider");
	              	             
	            }catch(Exception e){
	                logger.error("LeadsManagementControllerAction:Exception while inserting history while cancelling:", e);
	            }
	        }   
       
        }catch(Exception e){
            lmTabDTO.setResponseMessage("Error while canceling the lead");
            logger.info("Error while Cancel API");
         }
        }
        String returnvalue = getJSON(lmTabDTO);
        getRequest().setAttribute("returnvalue", returnvalue);
        return SUCCESS;
    }
    public String callCustomer(){
    	SecurityContext secContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
        SecurityContext leadContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
        String oldStatus = getRequest().getParameter("oldStatus");
        String vendorIdString=leadContxt.getCompanyId().toString();
        lmTabDTO=getModel();
        String leadId = getRequest().getParameter("leadId");
        if(StringUtils.isBlank(leadId)){
            leadId = (String)getSession().getAttribute("leadId");
        }
        if(StringUtils.isNotBlank(oldStatus)&& STALE.equalsIgnoreCase(oldStatus)){
        	oldStatus=providerLeadManagementService.getLeadfirmStatus(leadId, vendorIdString);
        }
        String comments = "";
        String reasonCodeIdString = "";
        String commentsFromDTO = "";
        Map<Integer,String>reasonCodeMapCallCustomer=new HashMap<Integer, String>();
        Map<Integer,String>reasonCodeMap=new HashMap<Integer, String>();
        
        if(!StringUtils.isBlank(lmTabDTO.getCallCustReasonCode())){
                reasonCodeIdString = lmTabDTO.getCallCustReasonCode();
                
        }else if(!StringUtils.isBlank(lmTabDTO.getCallCustReasonCodeNotAvail())){
                reasonCodeIdString =  lmTabDTO.getCallCustReasonCodeNotAvail();
                
        }
        
        Integer reasonCodeId= Integer.parseInt(reasonCodeIdString);
       
        reasonCodeMapCallCustomer = (Map<Integer, String>) getSession().getAttribute("reasonCodeMapCallCustomer");
        reasonCodeMap = (Map<Integer, String>) getSession().getAttribute("reasonCodeMap");

       
        String newStatus = reasonCodeMapCallCustomer.get(reasonCodeId);
        String reason = reasonCodeMap.get(reasonCodeId);
        String user_name = "";
        String full_name = "";
        Integer entityId = 0;
        if(secContxt.isAdopted()){
 			
     	   full_name = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
     	   user_name = secContxt.getUsername();
     	   entityId = secContxt.getVendBuyerResId();
 		}
 		else{
 			full_name = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
 			user_name = secContxt.getUsername();
 			entityId = secContxt.getVendBuyerResId();
 		}

        //Populate reason code as comments if reasoncode other than other is selected
        if(reason.equals(NewServiceConstants.CALL_CUSTOMER_OTHER)){
        	if(!StringUtils.isBlank(lmTabDTO.getCallCustReasonCode())){
        		commentsFromDTO = lmTabDTO.getCallCustComments();
        	}else if(!StringUtils.isBlank(lmTabDTO.getCallCustReasonCodeNotAvail())){
        		commentsFromDTO = lmTabDTO.getCallCustNotAvailableComments();
        	}
        	comments= reason + NewServiceConstants.PIPE_SEPERATOR + commentsFromDTO;
        	
        }else{
        	comments=reason;
        }
        logger.info("Reason   : " + reason);
        logger.info("Comments : " + lmTabDTO.getCallCustComments());
        logger.info("Reason + Comments : " + comments);
        
                try {
                    if(newStatus.equalsIgnoreCase(NewServiceConstants.CANCELLED_STATUS)){
                       //call cancelLead Method
                        String providerName = leadContxt.getUsername();
                        int roleId = leadContxt.getRoleId();
                        int vendorId = leadContxt.getCompanyId();
                        String businessName = "";
                        try{
                            businessName = providerLeadManagementService.getBusinessNameOfVendor(vendorId);
                           }
                           catch(Exception e){
                        	   logger.info("Exception while getting business name:"+e.getMessage());
                           }
                        CancelLeadRequest cancelLeadRequest = new CancelLeadRequest();
                        /* Format the resource Ids */
                        List<Integer> resourceIds = new ArrayList<Integer>();
                        resourceIds.add(new Integer(vendorId));
                        String providers = formatSelectedProviderIds(resourceIds);
                        cancelLeadRequest.setReasonCode(reasonCodeId);
                       
                        cancelLeadRequest.setComments(comments);
                        cancelLeadRequest.setLeadId(leadId);
                        cancelLeadRequest.setProviders(providers);
                        List<ProviderInfoVO> providerMatchedLead = providerLeadManagementService.getProviderInfoForLead(leadId);
                        List<ProviderInfoVO> providerCancelledLead = providerLeadManagementService.getCancelledProviderInfo(leadId);
                        Integer cancelListSize=resourceIds.size()+providerCancelledLead.size();

                        if (providerMatchedLead.size() == cancelListSize) {
                            cancelLeadRequest.setChkAllProviderInd(true);
                            cancelLeadRequest.setRevokePointsIndicator(true);
                        } else {
                            cancelLeadRequest.setChkAllProviderInd(false);
                            cancelLeadRequest.setRevokePointsIndicator(false);
                        }
                        cancelLeadRequest.setVendorBuyerName(providerName);
                        cancelLeadRequest.setRoleId(roleId);
                        cancelLeadRequest.setCancelInitiatedBy(OrderConstants.CUSTOMER_INITIATED);
                        /* Calls Cancel API */
                        CancelLeadResponse cancelLeadResponse =leadClient.cancelLead(cancelLeadRequest);
                        if (null == cancelLeadResponse) {
                             logger.info("Cancel API call failed");
                        } else if (null != cancelLeadResponse.getResults().getError()) {
                            lmTabDTO.setResponseMessage("Error while cancelling a lead: The error is "+cancelLeadResponse.getResults().getError());
                            logger.info("Error while Cancel API");
                           
                        }
                        else{
                        String modifiedBy = leadContxt.getUsername();
                        LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 
                        //TODO: Need to add a record in lu_lead_action table
                        leadHistoryVO.setActionId(OrderConstants.PROVIDER_CANCEL_LEAD);    
                        leadHistoryVO.setSlLeadId(leadId);    
                        leadHistoryVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
                        leadHistoryVO.setCreatedBy(full_name); 
                        leadHistoryVO.setModifiedBy(modifiedBy);
                        leadHistoryVO.setDescription(NewServiceConstants.STATUS_CHANGE_CANCELLED + " " + comments + " " + NewServiceConstants.PIPE_SEPERATOR +" Cancelled by the Customer for the Provider Firm: "+businessName);
                        leadHistoryVO.setEnitityId(entityId);
                        try{
                            providerLeadManagementService.insertHistoryForLeadProvider(leadHistoryVO);
                            lmTabDTO.setResponseMessage("Lead Cancelled Succesfully");
                            getSession().setAttribute("respMsg", "Lead Cancelled Succesfully by Customer");
                                                     
                         
                        }catch(Exception e){
                            logger.error("LeadsManagementControllerAction:Exception while inserting history while cancelling:", e);
                        }
                      }
                    }else if(newStatus.equalsIgnoreCase(NewServiceConstants.STATUS_SCHEDULED)){
                            //Need to go through working and then to schedule
                            if(oldStatus.equalsIgnoreCase(NewServiceConstants.NEW_LEAD_STATUS)){
                                UpdateLeadStatusRequest updateLeadStatusRequest = new UpdateLeadStatusRequest();
                                LeadFirmStatus firmStatus = new LeadFirmStatus();
                                firmStatus.setFirmId(secContxt.getCompanyId().toString());
                                firmStatus.setFirmStatus(NewServiceConstants.WORKING_LEAD_STATUS);
                                updateLeadStatusRequest.setLeadId(leadId);
                                updateLeadStatusRequest.setReason(reason);
                                updateLeadStatusRequest.setLeadfirmStatus(firmStatus);
                                updateLeadStatusRequest.setFullName(full_name);
                                updateLeadStatusRequest.setUserName(user_name);
                                updateLeadStatusRequest.setEntityId(entityId);
                                UpdateLeadStatusResponse response = new UpdateLeadStatusResponse();
                                response = leadClient.updateLeadFirmStatus(updateLeadStatusRequest);
                                if (null == response) {
                                     logger.info("API call for updating the lead firm status failed");
                                }else if (null != response.getResults().getError()) {
                                    logger.info("API call for updating the lead firm status failed and error exists in client");
                                    }
                                else if(null == response.getResults().getError()){
                                    logger.info("APi executed successfully");
                                    //setting schedule indicator in session
                                    getSession().setAttribute("scheduleIndicator","true");
                                	}
                              	}else if(oldStatus.equalsIgnoreCase(NewServiceConstants.WORKING_LEAD_STATUS)){
                            	    getSession().setAttribute("scheduleIndicator","true");
                                  }
                             }else if(newStatus.equalsIgnoreCase(NewServiceConstants.WORKING_LEAD_STATUS)){
                            	if(oldStatus.equalsIgnoreCase(NewServiceConstants.NEW_LEAD_STATUS)){
                              //integrate with updateLeadFirmStatus API
	                            UpdateLeadStatusRequest updateLeadStatusRequest = new UpdateLeadStatusRequest();                       
	                            LeadFirmStatus firmStatus = new LeadFirmStatus();
	                            firmStatus.setFirmId(secContxt.getCompanyId().toString());
	                            firmStatus.setFirmStatus(newStatus);
	                            updateLeadStatusRequest.setLeadId(leadId);
	                            if(reason.equals(NewServiceConstants.CALL_CUSTOMER_OTHER)){
	                            	reason = comments;
	                            }
	                            logger.info("Reason : " + reason);
	                            updateLeadStatusRequest.setReason(reason);
	                            updateLeadStatusRequest.setFullName(full_name);
                                updateLeadStatusRequest.setUserName(user_name);
                                updateLeadStatusRequest.setEntityId(entityId);
	                            updateLeadStatusRequest.setLeadfirmStatus(firmStatus);
	                            UpdateLeadStatusResponse response = new UpdateLeadStatusResponse();
                                response = leadClient.updateLeadFirmStatus(updateLeadStatusRequest);
                                if (null == response) {
                                 logger.info("API call for updating the lead firm status failed");
                                 }else if (null != response.getResults().getError()) {
                                  logger.info("API call for updating the lead firm status failed and error exists in client");
                                 }
                            	}else if((oldStatus.equalsIgnoreCase(NewServiceConstants.WORKING_LEAD_STATUS))&&(newStatus.equalsIgnoreCase(NewServiceConstants.WORKING_LEAD_STATUS))){
                            	    //Here only log the entry in history for sub status change
                            		  //String updatecomments=NewServiceConstants.LEAD_FIRM_SUBSTATUS_CHANGED + comments;
                            		  
                            		  String updatecomments = NewServiceConstants.LEAD_CALL_CUSTOMER + comments;
                            		
                            		  String modifiedBy = leadContxt.getUsername();
                            	      LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 
                            	   
	                            	   leadHistoryVO.setActionId(NewServiceConstants.LEAD_FIRM_UPDATE);    
	                            	   leadHistoryVO.setSlLeadId(leadId);    
	                            	   leadHistoryVO.setRoleId(NewServiceConstants.ROLE_ID_PROVIDER);  
	                            	   leadHistoryVO.setCreatedBy(full_name); 
	                            	   leadHistoryVO.setModifiedBy(modifiedBy);
	                            	   leadHistoryVO.setDescription(updatecomments);
	                            	   leadHistoryVO.setEnitityId(entityId);
	                            	   
	                            	 try{
	                            	   providerLeadManagementService.insertHistoryForLead(leadHistoryVO);
	                            	 }catch (Exception e) {
	                                     logger.info("Exception in logging lead history");
	                                 }
                            		  
                            	}
                              }
                    } catch (Exception e) {
                    	logger.info("Exception in updating lead firm Status");
                }
                return "json";
            }
       
       
   
    public void  getReasoncodesForCallCustomer(String leadId, String vendorid) {
    Map<Integer,String>reasonCodeMapCallCustomer=new HashMap<Integer, String>();
    Map<Integer,String>reasonCodeMap=new HashMap<Integer, String>();
      
      List<CallCustomerReasonCodeVO> custInterested=new ArrayList<CallCustomerReasonCodeVO>();
      List<CallCustomerReasonCodeVO> custNotInterested=new ArrayList<CallCustomerReasonCodeVO>();
      List<CallCustomerReasonCodeVO> custNotResponded=new ArrayList<CallCustomerReasonCodeVO>();
      String LeadStatus="";
      try{
    	  LeadStatus=providerLeadManagementService.getLeadfirmStatus(leadId, vendorid);
          custInterested=providerLeadManagementService.getCustInterestedReasonCodes();
          custNotInterested=providerLeadManagementService.getCustNotInterestedReasonCodes();
          custNotResponded=providerLeadManagementService.getcustNotRespondedReasonCodes();
          //populating reasoncodes into a map for future actions.
          for(CallCustomerReasonCodeVO custInt: custInterested){
              //new--> schedule,new-->working
              if(LeadStatus.equals(NEW)){
                if(NewServiceConstants.SCHEDULE_REASON.equalsIgnoreCase(custInt.getReason())){
                    reasonCodeMapCallCustomer.put(custInt.getReasonCodeId(),SCHEDULE);
                }else{
                    reasonCodeMapCallCustomer.put(custInt.getReasonCodeId(),WORKING);
                }
              }else if(LeadStatus.equals(WORKING)){
            	       if(NewServiceConstants.SCHEDULE_REASON.equalsIgnoreCase(custInt.getReason())){
                        reasonCodeMapCallCustomer.put(custInt.getReasonCodeId(),SCHEDULE);
                       }else{
                        reasonCodeMapCallCustomer.put(custInt.getReasonCodeId(),WORKING);
                  }
              }
              reasonCodeMap.put(custInt.getReasonCodeId(), custInt.getReason());
          }
          for(CallCustomerReasonCodeVO custNotInt:custNotInterested){
              //new-->cancel,schedule-->cancel
              reasonCodeMapCallCustomer.put(custNotInt.getReasonCodeId(),CANCELLED);
              reasonCodeMap.put(custNotInt.getReasonCodeId(), custNotInt.getReason());
          }
          for(CallCustomerReasonCodeVO custNotResp:custNotResponded){
              //working-->working
              reasonCodeMapCallCustomer.put(custNotResp.getReasonCodeId(),WORKING);
              reasonCodeMap.put(custNotResp.getReasonCodeId(), custNotResp.getReason());
          }
      }catch(Exception e){
          logger.info("Exception in getting the available reason codes for Call customer");
      }
      //Setting the value in request
      if(!custInterested.isEmpty() && !custNotInterested.isEmpty() && !custNotResponded.isEmpty()){
          getRequest().setAttribute("custInterested", custInterested);
          getRequest().setAttribute("custNotInterested", custNotInterested);
          getRequest().setAttribute("custNotResponded", custNotResponded);
                   
      }
      getSession().setAttribute("reasonCodeMapCallCustomer",reasonCodeMapCallCustomer);
      getSession().setAttribute("reasonCodeMap",reasonCodeMap);
    }
   
   
    public String formatSelectedProviderIds(List<Integer> rejResources){
        String checkedResources = "";
            for(Integer provider:rejResources)
                {
                    if(provider!=null)
                    {
                        checkedResources+=provider+",";
                    }
                }
            return checkedResources.substring(0,checkedResources.length()-1);
            }
 
    public void cancelLeadReason()
    {
       try{
    	   Map<String,Integer> unsortedProviderLeadReasons=providerLeadManagementService.getProviderLeadReasons(OrderConstants.LEAD_REASON_CODE_TYPE_CANCELLATION,
                   OrderConstants.LEAD_ROLE_TYPE_PROVIDER);
           Map<String,Integer> leadProviderReasons=new TreeMap<String,Integer>(unsortedProviderLeadReasons);
           Map<String,Integer> sortedProviderLeadReasons= sortedProviderCancelLeadReasons(leadProviderReasons);
           getSession().setAttribute("leadProviderReasons",sortedProviderLeadReasons);
           setCancelReasonCode(unsortedProviderLeadReasons);
           
       }catch(Exception e){
    	   logger.info("Exception in cancelLeadReason() due to "+e.getMessage());
       }
       
    }
   
     private void setCancelReasonCode(Map<String, Integer> unsortedProviderLeadReasons) {
    	 
    	 Map<Integer,String>cancelReasonCodes=new HashMap<Integer, String>();
    	    
    	 if(null!= unsortedProviderLeadReasons && !unsortedProviderLeadReasons.isEmpty()){
    	 for(Map.Entry<String, Integer> entry:unsortedProviderLeadReasons.entrySet()){
    		 cancelReasonCodes.put(entry.getValue(), entry.getKey());
    	    }
    	 }
    	  getSession().setAttribute("cancelReasonCodes",cancelReasonCodes);
		
	}

	public  Map<String,Integer> sortedProviderCancelLeadReasons(Map<String, Integer> map)
        {
            Integer otherValue=-1;
            String otherKey="Other";
            for (Entry<String, Integer> entry : map.entrySet())
            {
                if(entry.getKey().equalsIgnoreCase(otherKey)){
                    otherValue=entry.getValue();
                }
            }
            if(otherValue!=-1)
            {
            map.remove(otherKey);
            }
            Map<String, Integer> sortedLinkedMap = new LinkedHashMap<String, Integer>();
            sortedLinkedMap.putAll(map);
            sortedLinkedMap.put(otherKey, otherValue);
            return sortedLinkedMap;
        }
   
     
     
        /**
         * Controller method that will be invoked when buyer clicks Submit button in
         * Provider Lead management- Complete Order Lead pop up.
         * It calls API v1.0/slleads/completeLeads to complete the Lead order
         * Error/warning messages are populated in the lead detail info page.
         *
         * */
        public String completeLead() {
            //get the dto object with the info from page
            lmTabDTO=getModel();
            SecurityContext leadContxt = (SecurityContext) getSession()
                    .getAttribute(SECURITY_CONTEXT);
              int vendorId = leadContxt.getCompanyId();
              String leadId = getRequest().getParameter("leadId");
              
              if(null==leadId || (null!=leadId && leadId.equals(""))){
                  leadId = (String)getSession().getAttribute("leadId");
              }  
           
            if( null!= lmTabDTO){
                //set the details from page to the request object and call the lead client
                CompleteLeadsRequest request = new CompleteLeadsRequest();
                request.setLeadId(leadId);
                request.setFirmId(this._commonCriteria.getCompanyId().toString());
                request.setResourceId(lmTabDTO.getCompletedProvider());
                String unFormattedDate=lmTabDTO.getCompletedDate().trim();
                String formattedDateString = "";
                if(!StringUtils.isEmpty(unFormattedDate)){
                	Date unformattedDateValue = null;
    				try {
    					unformattedDateValue = new SimpleDateFormat(READ_DATE_FORMAT).parse(unFormattedDate);
    				} catch (ParseException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				if(null!=unformattedDateValue){
    					formattedDateString=new SimpleDateFormat(XML_DATE_FORMAT).format(unformattedDateValue);
    				}
                }
                request.setCompletedDate(formattedDateString);
			if (StringUtils.isNotBlank(lmTabDTO.getCompletedTime())) {
				DateFormat timeFormatParse = new SimpleDateFormat(
						READ_TIME_FORMAT);
				String timeInput = lmTabDTO.getCompletedTime();
				String timeOutPut = "";
				Date timeString = null;
				try {
					timeString = timeFormatParse.parse(timeInput);
				} catch (ParseException e) {
					logger.info("Exception in parsing time");
				}
				logger.info("Time after parse:" + timeString);
				try {
					timeOutPut = timeFormatParse.format(timeString);
				} catch (Exception e) {
					logger.info("Exception in formatting time");
				}
				logger.info("Time after formatt:" + timeOutPut);
				request.setCompletedTime(timeOutPut);
			}
			else{
				request.setCompletedTime(null);
			}
			if (StringUtils.isNotBlank(lmTabDTO.getCompletedComments())) {
                request.setCompletionComment(lmTabDTO.getCompletedComments());
			}else{
				 request.setCompletionComment(null);
			}
                request.setNumberOfVisits(lmTabDTO.getNoOfOnsiteVisits());
                Price price = new Price();
                if(null!=lmTabDTO.getFeeForLabour()){
                price.setLaborPrice(lmTabDTO.getFeeForLabour());
                }else{
                	 price.setLaborPrice(0.00);
                }
                if(null!=lmTabDTO.getFeeForParts()){
                price.setMaterialPrice(lmTabDTO.getFeeForParts());
                }else{
                	 price.setMaterialPrice(0.00);
                }
                request.setPrice(price);
               //request.setDocument(document);
               
                String errorMessage = "";
                try {
                    CompleteLeadsResponse response=leadClient.completeLead(request);
                    if (null == response) {
                         logger.info("API call for completeLead failed");
                         errorMessage = "Internal Server Error";
                         getSession().setAttribute("errorMsg", errorMessage);
                    }else if (null != response.getResults().getError()) {
                        errorMessage = response.getResults().getError().get(0).getMessage();
                        lmTabDTO.setResponseMessage(errorMessage);
                    }else{
                     //API success
                    	historyLogging();
                   
                    }
                } catch (Exception e) {
                    logger.info("Exception in lead complete order "+ e.getMessage());
                    errorMessage = "Exception in lead complete order";
                    getSession().setAttribute("errorMsg", errorMessage);
                   }
               
            }
            getSession().setAttribute("respMsg", "Lead Completed successfully");
            return "json";
           
        }
       
    
       
    private void historyLogging() {
			// TODO Auto-generated method stub
    	  	 SecurityContext secContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
             String created_by = "";
             String user="System";
             Integer entityId = secContxt.getVendBuyerResId();
             
             String leadId = getRequest().getParameter("leadId");
             
             if(null==leadId || (null!=leadId && leadId.equals(""))){
                 leadId = (String)getSession().getAttribute("leadId");
             }
             String vendorId = secContxt.getCompanyId().toString();
             
             if(secContxt.isAdopted()){
       		    created_by = secContxt.getSlAdminFName() + " "+ secContxt.getSlAdminLName();
             } else{
       			created_by = this._commonCriteria.getFName() + " " +  this._commonCriteria.getLName();
       		}
              String modifiedBy = secContxt.getUsername();
              LeadHistoryVO leadHistoryVO= new LeadHistoryVO(); 
              //TODO: Need to add a record in lu_lead_action table
              leadHistoryVO.setActionId(OrderConstants.PROVIDER_COMPLETE_LEAD);    
              leadHistoryVO.setSlLeadId(leadId);    
              leadHistoryVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
              leadHistoryVO.setCreatedBy(created_by); 
              leadHistoryVO.setModifiedBy(modifiedBy);
              logger.info("Resource Id who completed the order : " + lmTabDTO.getCompletedProvider());
              leadHistoryVO.setDescription(NewServiceConstants.STATUS_CHANGE_COMPLETED);
              leadHistoryVO.setEnitityId(entityId);
             
              try{
                  providerLeadManagementService.insertHistoryForLead(leadHistoryVO);
                  lmTabDTO.setResponseMessage("Lead Completed Succesfully");
                  getSession().setAttribute("respMsg", "Lead Completed successfully");
              
               
              }catch(Exception e){
                  logger.error("LeadsManagementControllerAction:Exception while inserting history while completing:", e);
              }
             
             List<ProviderInfoVO> providerMatchedLead = providerLeadManagementService.getProviderInfoForLead(leadId);
             logger.info("Provider List Size : " + providerMatchedLead.size());
             //Need to fetch records from lead_history
             //List<LeadHistoryVO>  leadHistoryVOlst = providerLeadManagementService.getHistoryListForLead(leadId);
             //logger.info("History List Size : " + leadHistoryVOlst.size());
             if(null!=providerMatchedLead && providerMatchedLead.size() !=0){
            	for(ProviderInfoVO vo:providerMatchedLead){
            		String matchedProvider = vo.getProviderId();
            		logger.info("Logged in Vendor ID : " + vendorId);
            		logger.info("Vendor Id from the list : " + matchedProvider);
            		if(!(vendorId.equals(matchedProvider))){
            			boolean flag = false;
            			List<LeadHistoryVO>  leadHistoryVOlst = providerLeadManagementService.getHistoryListForLead(leadId);
            			for(LeadHistoryVO leadHistoryVOObj:leadHistoryVOlst){
            				logger.info("Resource Id from the History  List  : " + leadHistoryVOObj.getResourceId());
            				logger.info("Resource Id from the Provider List  : " + vo.getResourceId());
            				if(vo.getResourceId().equalsIgnoreCase(leadHistoryVOObj.getResourceId())){
            					// History is available for cancellation
            					flag = true;
            					break;
            				}	
            			}
            			logger.info("Flag : " + flag);
            			if(!flag){
            				//Insert Into History Table
            				LeadHistoryVO leadHisVO= new LeadHistoryVO(); 
                            //TODO: Need to add a record in lu_lead_action table
                			leadHisVO.setActionId(OrderConstants.PROVIDER_CANCEL_LEAD);    
                            leadHisVO.setSlLeadId(leadId);    
                            leadHisVO.setRoleId(OrderConstants.PROVIDER_ROLEID);  
                            leadHisVO.setCreatedBy(user); 
                            leadHisVO.setModifiedBy(user);
                            leadHisVO.setDescription(NewServiceConstants.STATUS_CHANGE_CANCELLED+" "+"Lead was completed by another provider");
                            leadHisVO.setEnitityId(Integer.parseInt(vo.getResourceId()));
                            try{
                                providerLeadManagementService.insertHistoryForLead(leadHisVO);
                            }catch(Exception e){
                                logger.error("LeadsManagementControllerAction:Exception while inserting history while completing:", e);
                            }
            			}            		
            				
            		}         		
            		
            	}
             }
            
		}

	public LeadManagementTabDTO getModel() {
        return lmTabDTO;
    }
    public void setModel(LeadManagementTabDTO lmTabDTO) {
        this.lmTabDTO = lmTabDTO;
    }
    public LeadManagementTabDTO getLmTabDTO() {
        return lmTabDTO;
    }

    public void setLmTabDTO(LeadManagementTabDTO lmTabDTO) {
        this.lmTabDTO = lmTabDTO;
    }
    public LeadManagementRestClient getLeadClient() {
        return leadClient;
    }
    public void setLeadClient(LeadManagementRestClient leadClient) {
        this.leadClient = leadClient;
    }   
    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public ProviderLeadManagementService getProviderLeadManagementService() {
        return providerLeadManagementService;
    }

    public void setProviderLeadManagementService(
            ProviderLeadManagementService providerLeadManagementService) {
        this.providerLeadManagementService = providerLeadManagementService;
    }

    public IApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    public void setApplicationProperties(
            IApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

   
}
