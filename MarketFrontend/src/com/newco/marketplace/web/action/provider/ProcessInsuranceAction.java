package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.dto.provider.AdditionalInsurancePolicyDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.common.CommonConstants;

/**
 * Action responsible for handling policy information modal dialog & document upload
 */
public class ProcessInsuranceAction extends SLAuditableBaseAction implements SessionAware,ServletRequestAware,
ServletResponseAware,ModelDriven<AdditionalInsurancePolicyDTO>{ 

	

	private static final long serialVersionUID = -5410000668792055670L;
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private InsurancePolicyDto insurancePolicyDto;
	private AdditionalInsurancePolicyDTO additionalInsurancePolicyDTO = new AdditionalInsurancePolicyDTO();
	private Map sSessionMap;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final Logger logger = Logger.getLogger(ProcessInsuranceAction.class.getName());
	
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;
	Integer auditTimeLoggingId;
	
	public ProcessInsuranceAction(IInsuranceTypeDelegate iInsuranceDelegate,InsurancePolicyDto insurancePolicyDto) {
		this.iInsuranceDelegate = iInsuranceDelegate;
		this.insurancePolicyDto = insurancePolicyDto;
	}

	/**
	 * Method to load the policy information modal
	 * @return String
	 * @throws Exception
	 */
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public String doLoad() throws Exception {
		ArrayList<AjaxResultsDTO> actionResults = new ArrayList<AjaxResultsDTO>();
		//actionResults.setActionState(0);
		
		StringBuffer sb = new StringBuffer();
		
		ArrayList<InsurancePolicyDto> insurancePolicyDto1 = new ArrayList<InsurancePolicyDto>();
		
		InsurancePolicyDto insurancePolicyDto2 = new InsurancePolicyDto();
		
		try{			
			determineSLAdminFeature();
			int vendorId=0;
			if((String)getSessionMap().get(OrderConstants.VENDOR_ID)!=null){		
				vendorId =new Integer((String)getSessionMap().get(OrderConstants.VENDOR_ID)).intValue();
				insurancePolicyDto.setVendorId(new Integer((String)getSessionMap().get(OrderConstants.VENDOR_ID)).intValue());
			}	
			String buttonType = getParameter(OrderConstants.BUTTON_TYPE);
			insurancePolicyDto.setUserId((String)getSessionMap().get(OrderConstants.USER_NAME));
			String credId = getParameter(OrderConstants.CREDENTIAL_ID);	
			if(StringUtils.isNotBlank(credId)){
				insurancePolicyDto.setVendorCredentialId(Integer.parseInt(credId));	
			}
			insurancePolicyDto.setProviderAdmin(true);
			
			
			//SL-21233: Document Retrieval Code Starts
			
			insurancePolicyDto1=iInsuranceDelegate.loadInsuranceDetailsWithVendorDocuments(insurancePolicyDto,buttonType);
			
			insurancePolicyDto2= insurancePolicyDto1.get(0);
			
			String category = getParameter(OrderConstants.CATEGORY_NAME);			
			insurancePolicyDto2.setInsuranceType(category);
			String currentDocId=getParameter(OrderConstants.CURRENT_DOC_ID);			
			String currentDocName=getParameter(OrderConstants.CURRENT_DOC_NAME);
			String policyAmount = getParameter(OrderConstants.POLICY_AMOUNT);
			policyAmount = policyAmount.replaceAll(",", "");	
			insurancePolicyDto2.setAmount(policyAmount);			
			insurancePolicyDto=insurancePolicyDto2;		
			
			//SL-21233: Document Retrieval Code Ends
			
			
			//Code added as part of Jira SL-20645 -To capture time while auditing insurance
			
			AuditTimeVO auditTimeSaveVo=null;
			if(getSecurityContext().isSlAdminInd() && StringUtils.isNotBlank(buttonType))
			{
				Integer auditLinkId = AuditStatesInterface.COMPANY_AUDIT_LINK_ID;
				boolean userInd = false;
				Integer tempId=0;
				String vendId = (String)ActionContext.getContext().getSession().get(OrderConstants.VENDOR_ID);
				if(StringUtils.isNotBlank(credId)){
					tempId =Integer.parseInt(credId);	
				}
				AuditTimeVO auditTimeVo=new AuditTimeVO();
				auditTimeVo.setStartTime(new Date());
				auditTimeVo.setAgentName(getSecurityContext().getSlAdminUName());
				
				if(buttonType.equals(OrderConstants.BUTTON_TYPE_EDIT)){
					
					Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(vendId,userInd,tempId,auditLinkId);
					
					//SL-21233: Document Retrieval Code Starts
					
					if (auditTaskId != null){
					auditTimeVo.setAuditTaskId(auditTaskId);
					}
					
					//SL-21233: Document Retrieval Code Ends
					
					auditTimeVo.setStartAction(CommonConstants.PROCESS_INSURANCE_START_ACTION);
					auditTimeVo.setCredId(Integer.parseInt(credId));
					
				}
				else{
					auditTimeVo.setStartAction(CommonConstants.COMPANY_NEW_INSURANCE_ADD_ACTION);
				}		
				auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);
			}
			

			ServletActionContext.getRequest().getSession().setAttribute(OrderConstants.CREDENTIAL_FILE_NAME, insurancePolicyDto.getCredentialDocumentFileName());
			ServletActionContext.getRequest().getSession().setAttribute(OrderConstants.CREDENTIAL_DOC_ID, insurancePolicyDto.getLastUploadedDocumentId());
			ServletActionContext.getRequest().getSession().setAttribute(OrderConstants.BUTTON_TYPE,buttonType );
        	
			
			//SL-21233: Document Retrieval Code Starts
			
        	for(int i=0; i<insurancePolicyDto1.size(); i++){
        		
        	AjaxResultsDTO actionResults1 = new AjaxResultsDTO();
        		
        	if(i == 0){
        	actionResults1.setActionState(1);
            actionResults1.setResultMessage(SUCCESS);
        	}
        	
        	actionResults1.setAddtionalInfo1(getHtmlFriendlyText(insurancePolicyDto1.get(i).getAgencyCountry()));        	
			actionResults1.setAddtionalInfo2(getHtmlFriendlyText(insurancePolicyDto1.get(i).getAgencyName()));		
			actionResults1.setAddtionalInfo3(insurancePolicyDto1.get(i).getAgencyState());		
			actionResults1.setAddtionalInfo4(insurancePolicyDto1.get(i).getAmount());			
			actionResults1.setAddtionalInfo5(getHtmlFriendlyText(insurancePolicyDto1.get(i).getCarrierName()));			
			actionResults1.setAddtionalInfo6(getHtmlFriendlyText(insurancePolicyDto1.get(i).getPolicyNumber()));
			actionResults1.setAddtionalInfo7(""+insurancePolicyDto1.get(i).getLastUploadedDocumentId());
			actionResults1.setAddtionalInfo8(getHtmlFriendlyText(insurancePolicyDto1.get(i).getCredentialDocumentFileName()));
			actionResults1.setAddtionalInfo9(insurancePolicyDto1.get(i).getPolicyIssueDate());
			actionResults1.setAddtionalInfo10(insurancePolicyDto1.get(i).getPolicyExpirationDate());
			actionResults1.setAddtionalInfo14(insurancePolicyDto1.get(i).getPolicyCreatedDate());
			actionResults1.setAddtionalInfo15(getHtmlFriendlyText(insurancePolicyDto1.get(i).getStatus()));
			
			if(i==0){
			actionResults1.setAddtionalInfo11(currentDocId); 
			actionResults1.setAddtionalInfo12(getHtmlFriendlyText(currentDocName));
			}
			
			if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
			{
				if(i==0){
				actionResults1.setAddtionalInfo13(String.valueOf(auditTimeSaveVo.getAuditTimeLoggingId()));
				}
				
			}
			
			actionResults.add(actionResults1);
			
        	}
        	
        		
        		sb.append("<message_result>");
        		sb.append("<message>").append(actionResults.get(0).getResultMessage()).append("</message>");
        		sb.append("<pass_fail>").append(actionResults.get(0).getActionState()).append("</pass_fail>");
        		
        		for(int j=0; j<actionResults.size(); j++){
        			
        			sb.append("<insurance_details>");
            		sb.append("<addtional_01"+j+">").append(actionResults.get(j).getAddtionalInfo1()).append("</addtional_01"+j+">");
            		sb.append("<addtional_02"+j+">").append(actionResults.get(j).getAddtionalInfo2()).append("</addtional_02"+j+">");
            		sb.append("<addtional_03"+j+">").append(actionResults.get(j).getAddtionalInfo3()).append("</addtional_03"+j+">");
            		sb.append("<addtional_04"+j+">").append(actionResults.get(j).getAddtionalInfo4()).append("</addtional_04"+j+">");
            		sb.append("<addtional_05"+j+">").append(actionResults.get(j).getAddtionalInfo5()).append("</addtional_05"+j+">");
            		sb.append("<addtional_06"+j+">").append(actionResults.get(j).getAddtionalInfo6()).append("</addtional_06"+j+">");
            		sb.append("<addtional_07"+j+">").append(actionResults.get(j).getAddtionalInfo7()).append("</addtional_07"+j+">");
            		sb.append("<addtional_08"+j+">").append(actionResults.get(j).getAddtionalInfo8()).append("</addtional_08"+j+">");
            		sb.append("<addtional_09"+j+">").append(actionResults.get(j).getAddtionalInfo9()).append("</addtional_09"+j+">");
            		sb.append("<addtional_10"+j+">").append(actionResults.get(j).getAddtionalInfo10()).append("</addtional_10"+j+">");
            		sb.append("<addtional_11"+j+">").append(actionResults.get(j).getAddtionalInfo11()).append("</addtional_11"+j+">"); 
            		sb.append("<addtional_12"+j+">").append(actionResults.get(j).getAddtionalInfo12()).append("</addtional_12"+j+">");
            		sb.append("<addtional_13"+j+">").append(actionResults.get(j).getAddtionalInfo13()).append("</addtional_13"+j+">");
            		sb.append("<addtional_14"+j+">").append(actionResults.get(j).getAddtionalInfo14()).append("</addtional_14"+j+">");
            		sb.append("<addtional_15"+j+">").append(actionResults.get(j).getAddtionalInfo15()).append("</addtional_15"+j+">");
            		sb.append("</insurance_details>");
        		
        		}
        		
        		sb.append("</message_result>");
        	
        	//SL-21233: Document Retrieval Code Ends
	
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());			
			return ERROR;
		}	
		
		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		String responseStr = sb.toString();
		logger.info(responseStr);
		response.getWriter().write(responseStr);
		return NONE;
	}
	
	//SL-21233: Document Retrieval Code Ends
	
	
	private String getHtmlFriendlyText(String fromDB) {
		String result = fromDB;
		if(fromDB != null){
			if( result.indexOf('&') > 0 ) {
				result = StringUtils.replace(result, "&", "&amp;");
			
			}
			if(result.indexOf("'") > 0){
				 result = StringUtils.replace(result, "'", "&#39;");
				
			}
			if(result.indexOf("<") > 0){
				result = StringUtils.replace(result, "<", "&lt;");
				
			}
			if(result.indexOf(">") > 0){
				 result = StringUtils.replace(result, ">", "&gt;");
				
			}
		}
		return result;
	}
	
	/**
	 * Method to load the policy details modal,on selecting use same certificate option
	 * @return String
	 * @throws Exception
	 */
	public String loadPolicyInfoForSelectedDocument() throws Exception {
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(0);
		try{
			int vendorId=0;
			if((String)getSessionMap().get(OrderConstants.VENDOR_ID)!=null){		
				vendorId =new Integer((String)getSessionMap().get(OrderConstants.VENDOR_ID)).intValue();
				insurancePolicyDto.setVendorId(new Integer((String)getSessionMap().get(OrderConstants.VENDOR_ID)).intValue());
			}
			insurancePolicyDto.setUserId((String)getSessionMap().get(OrderConstants.USER_NAME));
			insurancePolicyDto.setProviderAdmin(true); 
			String docId = getParameter(OrderConstants.DOC_ID);
			Integer documentId = Integer.parseInt(docId);
			String docName = getParameter(OrderConstants.SAME_DOC_NAME);
			InsurancePolicyDto insurancePolicyDto1=iInsuranceDelegate.loadInsuranceDetailsForSelectedDocument(insurancePolicyDto,documentId);
			insurancePolicyDto=insurancePolicyDto1;	
			actionResults.setActionState(1);
	        actionResults.setResultMessage(SUCCESS);
	        actionResults.setAddtionalInfo1(insurancePolicyDto.getAgencyCountry());        	
			actionResults.setAddtionalInfo2(getHtmlFriendlyText(insurancePolicyDto.getAgencyName()));		
			actionResults.setAddtionalInfo3(insurancePolicyDto.getAgencyState());		
			actionResults.setAddtionalInfo4(insurancePolicyDto.getAmount());			
			actionResults.setAddtionalInfo5(getHtmlFriendlyText(insurancePolicyDto.getCarrierName()));			
			actionResults.setAddtionalInfo6(insurancePolicyDto.getPolicyNumber());
			actionResults.setAddtionalInfo7(docId);
			actionResults.setAddtionalInfo8(getHtmlFriendlyText(docName));
			actionResults.setAddtionalInfo9(insurancePolicyDto.getPolicyIssueDate());
			actionResults.setAddtionalInfo10(insurancePolicyDto.getPolicyExpirationDate());
			}catch(DelegateException ex){
				ex.printStackTrace();
				logger.info("Exception Occured while processing the request due to"+ex.getMessage());
				addActionError("Exception Occured while processing the request due to"+ex.getMessage());			
				return ERROR;
			}		

		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		logger.info(responseStr);
		response.getWriter().write(responseStr);
		return NONE;
	}
	
	/*Changes related to SL-20301: Details based on LastUploadedDocument*/
	public String loadAdditionalInsPolicyInfoForSelectedDocument() throws Exception {
		int vendorId=0;
		//additionalInsurancePolicyDTO =  new AdditionalInsurancePolicyDTO();
		if((String)getSessionMap().get(OrderConstants.VENDOR_ID)!=null){		
			vendorId =new Integer((String)getSessionMap().get(OrderConstants.VENDOR_ID)).intValue();
			additionalInsurancePolicyDTO.setVendorId(new Integer((String)getSessionMap().get(OrderConstants.VENDOR_ID)).intValue());
		}
		additionalInsurancePolicyDTO.setUserId((String)getSessionMap().get(OrderConstants.USER_NAME));
		additionalInsurancePolicyDTO.setProviderAdmin(true); 
		//String docId = getParameter(OrderConstants.DOC_ID);
		String docId = getParameter(OrderConstants.DOC_ID);
		Integer documentId = Integer.parseInt(docId);
		AdditionalInsurancePolicyDTO additionalInsurancePolicyDTO1 = iInsuranceDelegate.loadAdditonalInsuranceDetailsForSelectedDocument(additionalInsurancePolicyDTO, documentId);
		additionalInsurancePolicyDTO = additionalInsurancePolicyDTO1;
		return SUCCESS;
		
	}
	
	
	@SkipValidation
	public boolean doInput() throws Exception {
		logger.info("Inside ProcessInsuranceAction==>doInput()");
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap()); 
			if (!getFieldErrors().isEmpty())
				insurancePolicyDto = (InsurancePolicyDto)baseTabDto.getDtObject();
		}
		if (getFieldErrors().isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * Method to view the document
	 * @return String
	 * @throws Exception
	 */
	public String displayTheDocument() throws Exception {	
		logger.info("Inside ProcessInsuranceAction ==> displayTheDocument()");
		String docId = getParameter(OrderConstants.DOCUMENT_ID);
		String documentId=docId.trim();
		if (null != documentId)
		{
			insurancePolicyDto.setCredentialDocumentId(Integer.parseInt(documentId));
			insurancePolicyDto = iInsuranceDelegate.viewDocumentDetails(insurancePolicyDto);
			
			String credentialDocumentFileName = insurancePolicyDto.getCredentialDocumentFileName();
			credentialDocumentFileName = StringUtils.replaceChars(credentialDocumentFileName, ' ', '_');
			byte[] imgData = (byte[]) insurancePolicyDto.getCredentialDocumentBytes();
			
			SecurityChecker sc = new SecurityChecker();
			String checked1=sc.securityCheck(insurancePolicyDto.getCredentialDocumentExtention());
			response.setContentType(checked1);
			
			response.setContentLength(imgData.length);
			
			credentialDocumentFileName = sc.fileNameCheck(credentialDocumentFileName);
			response.setHeader("Content-Disposition", "attachment; filename=\""+credentialDocumentFileName+"\"");
			
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			getResponse().setHeader("Pragma", "public");
			OutputStream o = response.getOutputStream();
			o.write(imgData);
			o.flush();
			return "docDownload";
		}
		return null;
	}
	
	
	/**
	 * Method to load the policy information modal
	 * @return String
	 * @throws Exception
	 */
	public String doLoadAdditional() throws Exception {
		
		String buttonType = getParameter(OrderConstants.BUTTON_TYPE);		
		String credId = getParameter(OrderConstants.CREDENTIAL_ID);	
		String policyAmount = getParameter(OrderConstants.POLICY_AMOUNT);
		ServletActionContext.getRequest().getSession().setAttribute("button_type",buttonType);
		ServletActionContext.getRequest().getSession().setAttribute("cred_id",credId);
		ServletActionContext.getRequest().getSession().setAttribute("policy_amount",policyAmount);

		//Code added as part of Jira SL-20645 -To capture time while auditing insurance
		if(getSecurityContext().isSlAdminInd() && StringUtils.isNotBlank(buttonType) && buttonType.equals(OrderConstants.BUTTON_TYPE_EDIT))
		{
			Integer auditLinkId = AuditStatesInterface.COMPANY_AUDIT_LINK_ID;
			boolean userInd = false;
			Integer tempId=0;
			String vendId = (String)ActionContext.getContext().getSession().get(OrderConstants.VENDOR_ID);
			if(StringUtils.isNotBlank(credId)){
				tempId =Integer.parseInt(credId);	
			}
			Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(vendId,userInd,tempId,auditLinkId);

			AuditTimeVO auditTimeVo=new AuditTimeVO();
			auditTimeVo.setAuditTaskId(auditTaskId);
			auditTimeVo.setStartTime(new Date());
			//auditTimeVo.setAgentId(getSecurityContext().getAdminResId());
			auditTimeVo.setAgentName(getSecurityContext().getSlAdminUName());
			auditTimeVo.setStartAction(CommonConstants.PROCESS_INSURANCE_ADDITIONAL_START_ACTION);
			auditTimeVo.setCredId(Integer.parseInt(credId));
			AuditTimeVO auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);

			if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
			{
			
				auditTimeLoggingId = auditTimeSaveVo.getAuditTimeLoggingId();
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			}
		}
		
		
		return "add";
	}
	
	
	public InsurancePolicyDto getInsurancePolicyDto() {
		return insurancePolicyDto;
	}

	public void setInsurancePolicyDto(InsurancePolicyDto insurancePolicyDto) {
		this.insurancePolicyDto = insurancePolicyDto;
	}
	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;		
	}	
	public Map getSessionMap() {
		return this.sSessionMap;		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;
	}
	
	public AdditionalInsurancePolicyDTO  getModel() {
		return additionalInsurancePolicyDTO;
	}
	
	public AdditionalInsurancePolicyDTO getAdditionalInsurancePolicyDTO() {
		return additionalInsurancePolicyDTO;
	}

	public void setAdditionalInsurancePolicyDTO(
			AdditionalInsurancePolicyDTO additionalInsurancePolicyDTO) {
		this.additionalInsurancePolicyDTO = additionalInsurancePolicyDTO;
	}
	
	public void setServletResponse(HttpServletResponse arg0)
	{
		response=arg0;
	}

	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}

	public Integer getAuditTimeLoggingId() {
		return auditTimeLoggingId;
	}

	public void setAuditTimeLoggingId(Integer auditTimeLoggingId) {
		this.auditTimeLoggingId = auditTimeLoggingId;
	}
	
	
}
