package com.newco.marketplace.web.action.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.AdminToolConstants;
import com.newco.marketplace.web.delegates.IAdminToolDelegate;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.dto.FulfillmentAdminDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

/**
 * @author nsanzer
 * 
 */
public class AdminToolAction extends SLBaseAction implements Preparable, ModelDriven<FulfillmentAdminDTO>, ServletRequestAware {

	private static final long serialVersionUID = -220481286093757476L;
	private IAdminToolDelegate adminToolDelegate;
	protected IFinanceManagerDelegate financeManagerDelegate;
	private FulfillmentAdminDTO dto = new FulfillmentAdminDTO();
	private static final Logger logger = Logger.getLogger(AdminToolAction.class.getName());
	private HttpServletRequest request;
	private List<ExportStatusVO> exportStatus = new ArrayList<ExportStatusVO>();

	public AdminToolAction() {
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		setAttribute("todaysDate", getDateString());
		resetDto();
		setAttribute("todaysDate", getDateString());
		setAttribute("glRunMessage", "");
		setAttribute("nachaRunMessage", "");
		setAttribute("balanceInquiryRunMessage", "");
	}
	
	private void getPendingServiceOrders() throws SLBusinessServiceException {
		List<ServiceOrder> pending = adminToolDelegate.getPendingServiceOrders();
		List<ServiceOrder> pendingWithJmsId = new ArrayList<ServiceOrder>();
		for( ServiceOrder so : pending ) {
			if( so.getServiceOrderProcess().getJmsMessageId() != null) {
				pendingWithJmsId.add(so);
			}
		}
		dto.setPendingServiceOrders( pendingWithJmsId );
	}

	@Override
	public String execute() throws Exception {
		this.getPendingServiceOrders();
		getReportStatus();
		return SUCCESS;
	}

	/**
	 * Description: Method called when doing adjustments.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adjust() {
		boolean adjustFlag = true;
		String fulfillmentEntryId = getModel().getFulfillmentEntryId();
		String adjustComment = getModel().getAdjustComments();
		Map ids = null;
		resetMessages();
		try {
			if(!StringUtils.isNotBlank(adjustComment))
			{
				adjustFlag = false;
				dto.setAdjustFulfillmentMessage(AdminToolConstants.ADJUSTMENT_COMMENTS_ERROR);
			}
			else if (!StringUtils.isNotBlank(fulfillmentEntryId))
			{
				adjustFlag = false;
				dto.setAdjustFulfillmentMessage(AdminToolConstants.ADJUSTMENT_IDS_ERROR);
			}
			if (adjustFlag) {
				ids = adminToolDelegate
						.processFullfillmentAdjustment(StringUtils
								.split(fulfillmentEntryId), adjustComment, get_commonCriteria().getTheUserName());
			}
		} catch (Exception e) {
			adjustFlag = false;
			dto.setAdjustFulfillmentMessage(e.getMessage());
		}
		if (adjustFlag) {
			setAttribute("success", true);
			Set keys = ids.keySet();
			Iterator keysIter = keys.iterator();
			StringBuilder sb = new StringBuilder();
			sb.append("The new off-setting Fulfillment Id(s) as per your request:<br>");
			while (keysIter.hasNext()) {
				String key = (String) keysIter.next();
				sb.append("<br>Original Fulfillment Id: " + key);
				sb.append(" ---> New Offset Fulfillment Id: " + ids.get(key));
			}
			sb.append("<br><br>Please make a note of it.<br>  And use these Id(s) to make a request to ensure these transactions have been reconciled.");
			dto.setAdjustFulfillmentMessage(sb.toString());
		} else {
			setAttribute("failure", true);
		}
		resetDto();
		return SUCCESS;
	}

	/**
	 * Description: Method called when creating new fulfillment records.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String create() {
		boolean createFlag = true;
		String fulfillmentEntryId = getModel().getFulfillmentEntryId();
		String copyComments = getModel().getCopyComments();
		Double newAmount = 0.0;
		newAmount = getModel().getAmount();
		Map ids = null;
		resetMessages();
		try {
			if ((newAmount != null) && (newAmount > 0)) {
				if(!StringUtils.isNotBlank(copyComments))
				{
					createFlag = false;
					dto.setCreateFulfillmentMessage(AdminToolConstants.COPY_COMMENTS_ERROR);
				}
				else if(!StringUtils.isNotBlank(fulfillmentEntryId)) {
					createFlag = false;
					dto.setCreateFulfillmentMessage(AdminToolConstants.COPY_IDS_ERROR);
				}
				if (createFlag) {
					ids = adminToolDelegate.createFullfillmentEntry(fulfillmentEntryId, newAmount, copyComments, get_commonCriteria().getTheUserName());
				}
			} else {
				createFlag = false;
				dto.setCreateFulfillmentMessage(AdminToolConstants.AMOUNT_ERROR);
				}
		} catch (Exception e) {
			e.printStackTrace();
			createFlag = false;
			dto.setCreateFulfillmentMessage(e.getMessage());
		}
		if (createFlag) {
			setAttribute("success", true);
			Set keys = ids.keySet();
			Iterator keysIter = keys.iterator();
			StringBuilder sb = new StringBuilder();
			sb.append("A new fulfillment entry record has been created as per your request:<br>");
			while (keysIter.hasNext()) {
				String key = (String) keysIter.next();
				sb.append("<br>Original Fulfillment Id: " + key);
				sb.append(" ---> New Fulfillment Id: " + ids.get(key));
			}
			dto.setCreateFulfillmentMessage(sb.toString());
		} else {
			setAttribute("failure", true);
		}
		resetDto();
		return SUCCESS;
	}

	/**
	 * Description: Method called when looking up fulfillment records.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String lookup() {
		boolean lookupFlag = true;
		String fulfillmentEntryId = getModel().getFulfillmentEntryId();
		Boolean groupId = getModel().getGroupId();
		List<ValueLinkEntryVO> vos = null;
		resetMessages();
		try {
			if (StringUtils.isNotBlank(fulfillmentEntryId)) {
				vos = adminToolDelegate.lookupFullfillmentEntry(StringUtils.split(fulfillmentEntryId), groupId);
			} else {
				lookupFlag = false;
				dto.setLookUpMessage(AdminToolConstants.SEARCH_IDS_ERROR);
			}
		} catch (Exception e) {
			lookupFlag = false;
			dto.setLookUpMessage(e.getMessage());
		}
		if (lookupFlag) {
			setAttribute("success", true);
			StringBuilder sb = new StringBuilder();
			sb.append("The Fulfillment records(s) as per your request:<br>");
			for (ValueLinkEntryVO vo : vos) {
				sb.append("Id: " + vo.getFullfillmentEntryId() + "&nbsp;&nbsp;&nbsp;&nbsp; Reconciled: " + vo.getReconsiledInd() + "<br>");
			}
			dto.setLookUpMessage(sb.toString());
			dto.setValueLinkEntryVOs((ArrayList<ValueLinkEntryVO>) vos);
			setAttribute("lookup", true);
		} else {
			setAttribute("failure", true);
		}
		resetDto();
		return SUCCESS;
	}

	/**
	 * Description: Method called when resending fufillments records by a group.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String resend() {
		boolean noError = true;
		String fulfillmentGroupIds = getModel().getFulfillmentEntryId();
		String resendComments = getModel().getResendComments();
		List<ValueLinkEntryVO> fullfillmentEntryVOs = null;
		resetMessages();
		try {
			if(!StringUtils.isNotBlank(resendComments))
			{
				noError = false;
				dto.setGroupResendMessage(AdminToolConstants.RESENT_COMMENTS_ERROR);
			}
			else if(!StringUtils.isNotBlank(fulfillmentGroupIds)) {
				noError = false;
				dto.setGroupResendMessage(AdminToolConstants.RESENT_IDS_ERROR);
			}
			if (noError) {
				fullfillmentEntryVOs = adminToolDelegate.processGroupResend(StringUtils.split(fulfillmentGroupIds,","), resendComments, get_commonCriteria().getTheUserName());
			}
		} catch (Exception e) {
			noError = false;
			dto.setGroupResendMessage(e.getMessage());
		}
		if (noError) {
			setAttribute("success", true);
			StringBuilder sb = new StringBuilder();
			sb.append("The groups entered have been resent<br>");
			dto.setGroupResendMessage(sb.toString());

		} else {
			setAttribute("failure", true);
		}
		resetDto();
		return SUCCESS;
	}
	
	private void resetDto()
	{
		dto.setFulfillmentEntryId(null);
		dto.setResendComments(null);
		dto.setCopyComments(null);
		dto.setAdjustComments(null);
		dto.setGroupId(false);
		dto.setAmount(null);
		dto.setNachaDate(null);
		dto.setGlDate(null);
	}
	
	private void resetMessages()
	{
		setAttribute("success",false);
		setAttribute("failure",false);
		setAttribute("lookup", false);
		dto.setGlRunMessage(null);
		dto.setNachaRunMessage(null);
		dto.setAdjustFulfillmentMessage(null);
		dto.setCreateFulfillmentMessage(null);
		dto.setLookUpMessage(null);
		dto.setGroupResendMessage(null);
	}
	
	
	/**
	 * Description: Method called when running GL Process through fulfillment admin tool
	 * 
	 * @return
	 * @throws Exception
	 */
	public String runGLProcess() {
		Date glDate = null;
		try
		{
			String glDateObj = getModel().getGlDate();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        glDate = (Date)formatter.parse(glDateObj);
	        adminToolDelegate.runGLProcess(glDate);
//			IGLProcessor glProcessor = (IGLProcessor)MPSpringLoaderPlugIn.getCtx().getBean("glProcessor");
//			glProcessor.writeGLFeed(glDate,glDate);
			dto.setGlRunMessage(AdminToolConstants.GL_SUCCESS_MESSAGE);
		}
		catch(Exception e)
		{
			logger.info("Exception while running glProcess for date " + glDate);
			dto.setGlRunMessage(AdminToolConstants.GL_FAILURE_MESSAGE);
		}
		return SUCCESS;
	}
	
	/**
	 * Description: Method called when running Nacha Process through fulfillment admin tool
	 * 
	 * @return
	 * @throws Exception
	 */
	public String runNachaProcess() {
		Date nachaDate = null;
		try
		{
			String nachaDateObj = getModel().getNachaDate();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			nachaDate = (Date)formatter.parse(nachaDateObj);
			adminToolDelegate.runNachaProcess(nachaDate);
//	        INachaFile nachaFileImpl  = (INachaFile)MPSpringLoaderPlugIn.getCtx().getBean("nachaFileImpl");
//	        nachaFileImpl.writeNachaRecordsToFile(0,nachaDate);
			dto.setNachaRunMessage(AdminToolConstants.NACHA_SUCCESS_MESSAGE);
		}
		catch(Exception e)
		{
			logger.info("Exception while running nachaProcess for date " + nachaDate);
			dto.setNachaRunMessage(AdminToolConstants.NACHA_FAILURE_MESSAGE);
		}
		return SUCCESS;
	}
	
	/**
	 * Description: Method invoked to send a balance inquiry for a provider
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendBalanceInquiry() {
		Integer vendorId = 0;
		try
		{
			String vendorIdObj = getModel().getVendorId().trim();
			if(StringUtils.isNotBlank(vendorIdObj) && StringUtils.isNumeric(vendorIdObj))
			{
				vendorId = Integer.parseInt(vendorIdObj);
				adminToolDelegate.sendBalanceInquiryMessage(vendorId);
				dto.setBalanceInquiryRunMessage(AdminToolConstants.BALANCE_INQUIRY_SUCCESS_MESSAGE);
			}
			else
			{
				dto.setBalanceInquiryRunMessage(AdminToolConstants.BALANCE_INQUIRY_SEND_FAILURE_MESSAGE);
			}
		}
		catch(Exception e)
		{
			logger.info("Exception while sending a balance inquiry for Vendor " + vendorId);
			e.printStackTrace();
			dto.setBalanceInquiryRunMessage(AdminToolConstants.BALANCE_INQUIRY_SEND_FAILURE_MESSAGE);
		}
		return SUCCESS;
	}
	
	

	private SecurityContext getSecurityContext() {
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionMap = (Map<String, Object>) ActionContext
				.getContext().getSession();
		SecurityContext securityContext = null;
		if (sessionMap != null) {
			securityContext = (SecurityContext) sessionMap
					.get(Constants.SESSION.SECURITY_CONTEXT);
		}
		return securityContext;
	}
	
	public String cancelPendingWalletTransaction() {
		dto = new FulfillmentAdminDTO();
		String soId = this.request.getParameter("soId");
		SecurityContext ctx = (SecurityContext)this.getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>Request to cancel wallet transaction for " + soId);
			this.adminToolDelegate.cancelPendingWalletTransaction(soId, ctx);
			getPendingServiceOrders();
		} catch (SLBusinessServiceException e) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>FAILED to cancel wallet transaction for " + soId);
		}
		return SUCCESS;
	}	
	
	
	public FulfillmentAdminDTO getModel() {
		return dto;
	}

	public void setModel(FulfillmentAdminDTO dto) {
		this.dto = dto;
	}

	public FulfillmentAdminDTO getDto() {
		return dto;
	}

	public void setDto(FulfillmentAdminDTO dto) {
		this.dto = dto;
	}

	public IAdminToolDelegate getAdminToolDelegate() {
		return adminToolDelegate;
	}

	public void setAdminToolDelegate(IAdminToolDelegate adminToolDelegate) {
		this.adminToolDelegate = adminToolDelegate;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}
	public IFinanceManagerDelegate getFinanceManagerDelegate(){
		return financeManagerDelegate;
	}
	public void setFinanceManagerDelegate(IFinanceManagerDelegate financeManagerDelegate){
		this.financeManagerDelegate = financeManagerDelegate;
	}
	private void getReportStatus()  throws Exception {
		Integer slId = get_commonCriteria().getSecurityContext().getCompanyId();
		List<ExportStatusVO> exportStatus = new ArrayList<ExportStatusVO>();
		try{
			exportStatus = financeManagerDelegate.getReportStatus(slId);
		}catch (DelegateException e) {
			logger.debug("Error getting debug status",e);
			throw new Exception(e);
		}
		List<ExportStatusVO> listStatusProvSO = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusProvRev = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusByrSO = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusByrTax = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusAdmin = new ArrayList<ExportStatusVO>();
		Map theMap = ActionContext.getContext().getSession();
		for(ExportStatusVO exportStatusVO : exportStatus){
			String reportType = exportStatusVO.getReportType();
			if(PROVIDER_SO_REPORT.equalsIgnoreCase(reportType)){				
				listStatusProvSO.add(exportStatusVO);
			}else if(PROVIDER_REV_REPORT.equalsIgnoreCase(reportType)){
				listStatusProvRev.add(exportStatusVO);
			}else if (BUYER_SO_REPORT.equalsIgnoreCase(reportType)) {
				listStatusByrSO.add(exportStatusVO);
			}else if (BUYER_TAXID_REPORT.equalsIgnoreCase(reportType)) {
				listStatusByrTax.add(exportStatusVO);
			}else if (ADMIN_PAYMENT_REPORT.equalsIgnoreCase(reportType)){
				listStatusAdmin.add(exportStatusVO);
			}						
		}
		String msg = "All activity from ";
		StringBuilder statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusProvSO)).append(" to ").append(getMaxDateFromList(listStatusProvSO));		
		theMap.put(REPORT_MSG_EXPORT_PROV_SO, statusMsg.toString());	
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusProvRev)).append(" to ").append(getMaxDateFromList(listStatusProvRev));	
		theMap.put(REPORT_MSG_EXPORT_PROV_REV, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusByrSO)).append(" to ").append(getMaxDateFromList(listStatusByrSO));	
		theMap.put(REPORT_MSG_EXPORT_BUYER_SO, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusByrTax)).append(" to ").append(getMaxDateFromList(listStatusByrTax));	
		theMap.put(REPORT_MSG_EXPORT_BUYER_ID, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusAdmin)).append(" to ").append(getMaxDateFromList(listStatusAdmin));	
		theMap.put(REPORT_MSG_EXPORT_ADMIN_PAYMENT, statusMsg.toString());
		
		theMap.put(REPORT_EXPORT_PROV_SO, listStatusProvSO);
		theMap.put(REPORT_EXPORT_PROV_REV, listStatusProvRev);
		theMap.put(REPORT_EXPORT_BUYER_SO, listStatusByrSO);
		theMap.put(REPORT_EXPORT_BUYER_ID, listStatusByrTax);
		theMap.put(REPORT_EXPORT_ADMIN_PAYMENT, listStatusAdmin);
	}
	private Date getMaxDateFromList(List<ExportStatusVO> listStatus){
		if(null == listStatus || listStatus.size()==0){
			return DateUtils.getCurrentDate();
		}
		ExportStatusVO status = Collections.max(listStatus, new Comparator<ExportStatusVO>() {
			public int compare(ExportStatusVO recFir, ExportStatusVO recSec) {
				return recFir.getReportDate().compareTo(recSec.getReportDate() );
			}
		});		
		if(null == status){
			return DateUtils.getCurrentDate();			
		}else{
			return status.getReportDate();
		}
	}
	
	private Date getMinDateFromList(List<ExportStatusVO> listStatus){
		if(null == listStatus || listStatus.size()==0){
			return DateUtils.addDaysToDate(DateUtils.getCurrentDate(), -7);	
		}
		ExportStatusVO status = Collections.min(listStatus, new Comparator<ExportStatusVO>() {
			public int compare(ExportStatusVO recFir, ExportStatusVO recSec) {
				return recFir.getReportDate().compareTo(recSec.getReportDate() );
			}
		});	
		if(null == status){
			return DateUtils.addDaysToDate(DateUtils.getCurrentDate(), -7);			
		}else{
			return status.getReportDate();
		}
	}	
}
