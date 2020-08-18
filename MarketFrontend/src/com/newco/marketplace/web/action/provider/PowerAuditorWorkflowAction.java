/**
 * 
 */
package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CATEGORY;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CREDENTIAL;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CATEGORY_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CREDENTIAL_CATEGORY;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CREDENTIAL_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CREDENTIAL_TYPES;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.HISTORY_DATA;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.HISTORY_NOTES;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.NOTES_DATA;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.POWER_AUDITOR_SEARCH_RESULT;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.POWER_AUDITOR_SESSION_FILTER;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.PRIMARY_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.PRIMARY_INDUSTRIES;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.ROLE_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.QUEUE_GRID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.RESOURCE_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.VENDOR_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.FIRM_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.FIRM_ERROR;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.INVALID_FIRM_ID;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.security.AdminPageAction;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author hoza
 *
 */
//Curretnlty this class is not model driven but in future when in need create this Action Model drive using the ProviderInfoPagesDto
@AdminPageAction
public class PowerAuditorWorkflowAction extends ActionSupport implements
		ServletRequestAware {
	
	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PowerAuditorWorkflowAction.class.getName());
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;
	public List<LookupVO> primaryIndustries;
	public List<LookupVO> credentialTypes;
	public List<LookupVO> credentialCategory;
	public List<PowerAuditorSearchResultVO> powerAuditorSearchResult;
	public List<PowerAuditorSearchResultVO> reasonCodeSearchResult;
	public List<AuditHistoryVO> auditHistory;
	public List<AuditNotesVO> auditNotes;
	
	public String firmError=null;
	/**
	 * @return the powerAuditorWorkflowDelegate
	 */
	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	/**
	 * @param powerAuditorWorkflowDelegate the powerAuditorWorkflowDelegate to set
	 */
	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}

	public String execute() throws Exception{
		PowerAuditorSearchVO initialSearchVO;
		
		initialSearchVO = (PowerAuditorSearchVO) request.getSession().getAttribute(POWER_AUDITOR_SESSION_FILTER);
		
		if (initialSearchVO == null)
		{
			initialSearchVO = new PowerAuditorSearchVO();
			initialSearchVO.setPrimaryIndustry(-1);
			initialSearchVO.setCredentialType(-1);
			initialSearchVO.setCategoryOfCredential(-1);
			initialSearchVO.setRoleType(-1);
			
			request.getSession().setAttribute(POWER_AUDITOR_SESSION_FILTER, initialSearchVO);
		}
		if (initialSearchVO.getRoleType() != null)
		{
			if (initialSearchVO.getRoleType() == 1)
			{
				this.credentialTypes = this.powerAuditorWorkflowDelegate.getCredentialTypes();
			}
			else if (initialSearchVO.getRoleType() == 2)
			{
				this.credentialTypes = this.powerAuditorWorkflowDelegate.getResourceCredentialTypes();
			}
		}
		
		if (initialSearchVO.getCredentialType() != null)
		{
			if (initialSearchVO.getRoleType() == 1)
			{
				this.credentialCategory = this.powerAuditorWorkflowDelegate.getCredentialCategoryByType(initialSearchVO.getCredentialType());
			}
			else if (initialSearchVO.getRoleType() == 2)
			{
				this.credentialCategory = this.powerAuditorWorkflowDelegate.getResourceCredentialCategoryByType(initialSearchVO.getCredentialType());
			}
		}
		
		this.primaryIndustries = this.powerAuditorWorkflowDelegate.getPrimaryIndustries(); 
		//SL-20645 modified the method to fetch the auditable items in the reasoncodes '10 Day Exception', 'Endorsement', 'Cancellation Notice'  
		this.powerAuditorSearchResult = this.powerAuditorWorkflowDelegate.getAuditableItemsCount(initialSearchVO);
		request.setAttribute(PRIMARY_INDUSTRIES, this.primaryIndustries);
		request.setAttribute(CREDENTIAL_TYPES, this.credentialTypes);
		request.setAttribute(CREDENTIAL_CATEGORY, this.credentialCategory);
		request.setAttribute(POWER_AUDITOR_SEARCH_RESULT, this.powerAuditorSearchResult);
		request.setAttribute(POWER_AUDITOR_SESSION_FILTER, initialSearchVO);
		
		request.getSession().setAttribute(POWER_AUDITOR_SEARCH_RESULT, this.powerAuditorSearchResult);
		
		return SUCCESS;
	}
	
	public String credentialByCat() throws Exception {		
		Integer category_id = Integer.valueOf(request.getParameter(CREDENTIAL_ID));
		Integer role_id = Integer.valueOf(request.getParameter(ROLE_ID));
		
		if (role_id == 1)
		{
			this.credentialCategory = this.powerAuditorWorkflowDelegate.getCredentialCategoryByType(category_id);
		}
		else if (role_id == 2)
		{
			this.credentialCategory = this.powerAuditorWorkflowDelegate.getResourceCredentialCategoryByType(category_id);
		}
		
		request.setAttribute(CREDENTIAL_CATEGORY, this.credentialCategory);
		
		return CATEGORY;
	}
	
	public String credentialByRole() throws Exception {		
		Integer role_id = Integer.valueOf(request.getParameter(ROLE_ID));
		
		if (role_id == 1)
		{
			this.credentialTypes = this.powerAuditorWorkflowDelegate.getCredentialTypes();
		}
		else if (role_id == 2)
		{
			this.credentialTypes = this.powerAuditorWorkflowDelegate.getResourceCredentialTypes();
		}
		
		request.setAttribute(CREDENTIAL_TYPES, this.credentialTypes);
		
		return CREDENTIAL;
	}
	
	public String getGridData() throws Exception {	
		PowerAuditorSearchVO filterSearchVO = new PowerAuditorSearchVO();
		String firm_id = null;
		Integer primary_industry_id = Integer.valueOf(request.getParameter(PRIMARY_ID));
		Integer role_id = Integer.valueOf(request.getParameter(ROLE_ID));
		Integer credential_id = Integer.valueOf(request.getParameter(CREDENTIAL_ID));
		Integer category_id = Integer.valueOf(request.getParameter(CATEGORY_ID));
		

		filterSearchVO.setPrimaryIndustry(primary_industry_id);
		filterSearchVO.setRoleType(role_id);
		filterSearchVO.setCredentialType(credential_id);
		filterSearchVO.setCategoryOfCredential(category_id);
		
		if(StringUtils.isNotBlank(request.getParameter(FIRM_ID)))
		{
			firm_id=request.getParameter(FIRM_ID).toString();
			
			filterSearchVO.setFirmId(firm_id);
			//SL-20645->Checking for valid firm id
			Integer validFirmIdOrNot=this.powerAuditorWorkflowDelegate.getValidFirmId(firm_id);	
			if(null == validFirmIdOrNot || validFirmIdOrNot == 0)
			{
				this.firmError=INVALID_FIRM_ID;
				ServletActionContext.getRequest().setAttribute(FIRM_ERROR, this.firmError);
				request.setAttribute(FIRM_ERROR, this.firmError);
			}else
			{
				this.firmError = null; 
				ServletActionContext.getRequest().setAttribute(FIRM_ERROR, this.firmError);
				request.setAttribute(FIRM_ERROR, this.firmError);
				//SL-20645 : modified the method to fetch the audit tasks under the new reason codes
				this.powerAuditorSearchResult = this.powerAuditorWorkflowDelegate.getAuditableItemsCount(filterSearchVO);
				request.getSession().setAttribute(POWER_AUDITOR_SEARCH_RESULT, this.powerAuditorSearchResult);
			}
		}else{
			//SL-20645 : modified the method to fetch the audit tasks under the new reason codes
			this.powerAuditorSearchResult = this.powerAuditorWorkflowDelegate.getAuditableItemsCount(filterSearchVO);
			request.getSession().setAttribute(POWER_AUDITOR_SEARCH_RESULT, this.powerAuditorSearchResult);
		}

	
		//request.setAttribute(POWER_AUDITOR_SEARCH_RESULT, this.credentialCategory);
		request.getSession().setAttribute(POWER_AUDITOR_SESSION_FILTER, filterSearchVO);
		
		return QUEUE_GRID;
	}
	
	public String getHistoryNotes() throws Exception {
		Integer vendor_id = Integer.valueOf(request.getParameter(VENDOR_ID));
		Integer resource_id = Integer.valueOf(request.getParameter(RESOURCE_ID));
		
		AuditNotesVO notesVO = new AuditNotesVO();
		AuditHistoryVO historyVO = new AuditHistoryVO();
		
		if (resource_id != -1)
		{
			notesVO.setResourceId(resource_id);
			historyVO.setResourceId(resource_id);
		}
		else
		{
			notesVO.setResourceId(null);
			historyVO.setResourceId(null);
		}
		
		if (vendor_id != -1)
		{
			notesVO.setVendorId(vendor_id);
			historyVO.setVendorId(vendor_id);
		}
		else
		{
			notesVO.setVendorId(null);
			historyVO.setVendorId(null);
		}
		
		this.auditHistory = this.powerAuditorWorkflowDelegate.getAuditHistoryForVendor(historyVO);
		this.auditNotes = this.powerAuditorWorkflowDelegate.getAuditNotesForVendor(notesVO);
		
		request.setAttribute(HISTORY_DATA, this.auditHistory);
		request.setAttribute(NOTES_DATA, this.auditNotes);
		
		return HISTORY_NOTES;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;		
	}
	
	
}
