package com.newco.marketplace.web.action.reasoncode;

import static org.apache.commons.lang.StringUtils.replaceOnce;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.reasoncodemgr.ReasonCodeTypes;
import com.servicelive.domain.reasoncodemgr.ReasonCodeHist;
import com.servicelive.reasoncode.services.ManageReasonCodeService;

public class ReasonCodeManagerAction extends SLBaseAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String LIST_All_CANCEL_CODE = "allCnclCodeList";
	private static final String LIST_All_SCOPE_CODE = "allScpCodeList";
	private static final String LIST_ARCHIVED_CANCEL_CODE = "archivedCnclCodeList";
	private static final String LIST_ARCHIVED_SCOPE_CODE = "archivedScpCodeList";
	private static final String LIST_REASON_TYPES = "reasonTypeList";	
		
	Logger logger = Logger.getLogger(ReasonCodeManagerAction.class);
	private ManageReasonCodeService manageReasonCodeService;
	private ReasonCode rc = new ReasonCode();
	Date date = new Date();
	private static final String TYPE_COUNT = "typeCount";
	private static final String MAP_ACTIVE_CODES = "activeReasonMap";
	private static final String MAP_ARCHIVED_CODES = "archivedReasonMap";
	private static final String DATE_FORMAT = "MM/dd/yyyy hh:mma zzz";
	public ReasonCodeManagerAction() {
		// Do nothing for now
	}
	
	/*public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		
	}	
	
	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT 
	public String execute() throws Exception
	{
		return SUCCESS;
	}*/
	
	/**
	 * Method to display the reason code manager page
	 * @return
	 */
	
	public String displayPage() 
	{
		Map<String,List<ReasonCode>> activeReasonMap = new LinkedHashMap<String, List<ReasonCode>>();
		Map<String,List<ReasonCode>> archivedReasonMap = new LinkedHashMap<String, List<ReasonCode>>();

		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = soContxt.getCompanyId();
		
		List<ReasonCodeTypes> reasonTypeList = manageReasonCodeService.getReasonTypes();
		List<ReasonCode> reasonList = manageReasonCodeService.getAllReasonCodes(buyerId);
		List<ReasonCodeHist> historytList = manageReasonCodeService.getHistory(buyerId);
		setCreatedDate(reasonList);
		
		//changing the status of deleted & archived general reasons for a specific buyer
		if(null != historytList && null != reasonList){
			Iterator histIt = historytList.iterator();	
			while(histIt.hasNext()){
				Object[] reasonCodeHist = (Object[])histIt.next();
				Integer reasonId = (Integer)reasonCodeHist[1];
				String action = reasonCodeHist[3].toString();
				Date modifiedDate = (Date)reasonCodeHist[4];
				Iterator<ReasonCode> reasonIt = reasonList.iterator();
				while(reasonIt.hasNext()){
					ReasonCode reasonCode = reasonIt.next();
					if(reasonId.intValue() == reasonCode.getReasonCodeId()){
						if(OrderConstants.DELETED_CODE.equals(action)){
							reasonCode.setReasonCodeStatus(OrderConstants.DELETED_CODE);
						}
						else if(OrderConstants.ARCHIVED_CODE.equals(action)){
							reasonCode.setReasonCodeStatus(OrderConstants.ARCHIVED_CODE);
							reasonCode.setModifiedDate(modifiedDate);
						}
					}
				}
			}
		}
		
		if(null != reasonTypeList && null != reasonList){
			setAttribute(TYPE_COUNT, reasonTypeList.size());

			for(ReasonCodeTypes reasonType : reasonTypeList){
				List<ReasonCode> activeList = new ArrayList<ReasonCode>();
				List<ReasonCode> archivedList = new ArrayList<ReasonCode>();			
				
				for(ReasonCode reasonCode : reasonList){
					if (reasonType.getReasonCodeType().equals(reasonCode.getReasonCodeType())					
							&& OrderConstants.ACTIVE_CODE.equals(reasonCode.getReasonCodeStatus())) {
						activeList.add(reasonCode);
					}
					else if (reasonType.getReasonCodeType().equals(reasonCode.getReasonCodeType())					
							&& OrderConstants.ARCHIVED_CODE.equals(reasonCode.getReasonCodeStatus())) {
						archivedList.add(reasonCode);
					}
				}
				activeReasonMap.put(reasonType.getReasonCodeType(), activeList);
				archivedReasonMap.put(reasonType.getReasonCodeType(), archivedList);
			}				
		}
		setAttribute(LIST_REASON_TYPES, reasonTypeList);
		setAttribute(MAP_ACTIVE_CODES, activeReasonMap);
		setAttribute(MAP_ARCHIVED_CODES, archivedReasonMap);
		
		return SUCCESS;
	}
	
	/**
	 * Method to format created date
	 * @param reasonList
	 */
	public void setCreatedDate(List<ReasonCode> reasonList)
	{
		try{
		if (null != reasonList) {
			for (ReasonCode reasonCode : reasonList) {
				if (null != reasonCode) {
					DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
					TimeZone gmtTime = TimeZone.getTimeZone("CST");
					formatter.setTimeZone(gmtTime);
					if(null != reasonCode.getCreatedDate()){
					String dateStr = formatter.format(reasonCode.getCreatedDate());
					dateStr = replaceOnce(dateStr, "am ", "AM ");
					dateStr = replaceOnce(dateStr, "pm ", "PM ");
					if (gmtTime.inDaylightTime(reasonCode.getCreatedDate())) {
						dateStr = dateStr.replace("CST", "CDT");
					}
					reasonCode.setFmtCreatedDate(dateStr);
					}
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ManageReasonCodeService getManageReasonCodeService() {
		return manageReasonCodeService;
	}

	public void setManageReasonCodeService(
			ManageReasonCodeService manageReasonCodeService) {
		this.manageReasonCodeService = manageReasonCodeService;
	}
	
	/**
	 * Method to add reason code
	 * @return
	 */
	public String addReasonCode() {
		
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = soContxt.getCompanyId();
		String userName = !StringUtils.isBlank(soContxt.getSlAdminUName()) ? soContxt.getSlAdminUName() : (String) soContxt.getUsername();
		setAttribute("code", null);
		setAttribute("type", "-1");
		String reasonCode = rc.getReasonCode().trim();
		reasonCode = reasonCode.replaceAll("\\s+", " ");
		rc.setReasonCode(reasonCode);
		if(this.validate_fields(rc)){	
			
			rc.setBuyerId(buyerId);		
			rc.setCreatedDate(date);
			rc.setModifiedDate(date);
			rc.setGeneralInd(0);
			rc.setModifiedBy(userName);
			rc.setReasonCodeStatus(OrderConstants.ACTIVE_CODE);
			
			String save = manageReasonCodeService.add(rc);
						
			if(null == save){
				this.addActionError(OrderConstants.SAVE_ERROR);
			}
			else if(OrderConstants.EXISTS == save){
				setAttribute("code", rc.getReasonCode());
				setAttribute("type", rc.getReasonCodeType());
				String typeId =getRequest().getParameter("reasonTypeIdAdded");
				setAttribute("reasonTypeId", typeId);
				this.addActionError(OrderConstants.ADD_ERROR);
			}
			else{
				String typeId =getRequest().getParameter("reasonTypeIdAdded");
				setAttribute("reasonTypeId", typeId);
				this.addActionMessage(OrderConstants.SAVE_SUCCESS);
			}
		}
		return this.displayPage();
	}

	//validating reason code and type
	private Boolean validate_fields(ReasonCode rc) {
		
		Boolean isFalse = Boolean.TRUE;
		
		String reasonCode = rc.getReasonCode();
		String reasonType = rc.getReasonCodeType();
				
		if(StringUtils.isBlank(reasonCode)){
			
			this.addFieldError("rc.reasonCode",OrderConstants.REASON_CODE_ERROR);
			setAttribute("code", rc.getReasonCode());
			setAttribute("type", rc.getReasonCodeType());
			isFalse = Boolean.FALSE;
		}
		
		if(StringUtils.isBlank(reasonType) || "-1".equals(reasonType)){
			
			this.addFieldError("rc.reasonCodeType",OrderConstants.REASON_TYPE_ERROR);
			setAttribute("code", rc.getReasonCode());
			setAttribute("type", rc.getReasonCodeType());
			isFalse = Boolean.FALSE;
		}		
		return isFalse;
	}
	
	/**
	 * Method to delete/archive reason code
	 * @return
	 */
	public String deleteReasonCode()  {	
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = soContxt.getCompanyId();
		String userName = !StringUtils.isBlank(soContxt.getSlAdminUName()) ? soContxt.getSlAdminUName() : (String) soContxt.getUsername();
		String action = rc.getReasonCodeStatus();
		String delete = null;
	
		try
		{
			String code = getRequest().getParameter("code");			
			code = URLDecoder.decode( code , "UTF-8");
			code = code.replaceAll("-prcntg-", "%");
			String type = getRequest().getParameter("type");			
			type = URLDecoder.decode( type , "UTF-8");
			type = type.replaceAll("-prcntg-", "%"); 
			rc.setReasonCode(code);
			rc.setReasonCodeType(type);
			rc.setBuyerId(buyerId);				
			rc.setModifiedBy(userName);
			rc.setModifiedDate(date);
			String typeId = getRequest().getParameter("reasonTypeId");
			setAttribute("reasonTypeId",typeId);
			
			if(OrderConstants.DELETE_STATUS.equals(action)) {				
				rc.setReasonCodeStatus(OrderConstants.DELETED_CODE);			
				delete = manageReasonCodeService.delete(rc);
			}			
			else if(OrderConstants.ARCHIVE_STATUS.equals(action)) {
				rc.setReasonCodeStatus(OrderConstants.ARCHIVED_CODE);						
				delete = manageReasonCodeService.delete(rc);
			}	

			if(OrderConstants.DELETE_STATUS.equals(delete)){
				this.addActionMessage(OrderConstants.DELETE_SUCCESS);
			}
			else if(OrderConstants.ARCHIVE_STATUS.equals(delete)){
				this.addActionMessage(OrderConstants.ARCHIVE_SUCCESS);
				
			}
		}
		catch(Exception e) {		
			this.addActionError(OrderConstants.DELETE_ERROR);
		}	
		setAttribute("action",null);
		return this.displayPage();		
	}
	
	/**
	 * Method to check whether reason code is used by existing SO
	 * @return
	 */
	public String checkInSO() {
		try{
			setAttribute("code", null);
			SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
			int buyerId = soContxt.getCompanyId();
			int id = Integer.parseInt(getRequest().getParameter("id"));
			String typeId =getRequest().getParameter("reasonTypeId");
			String code = getRequest().getParameter("code");			
			code = URLDecoder.decode( code , "UTF-8");
			code = code.replaceAll("-prcntg-", "%");
			String type = getRequest().getParameter("type");
			type = URLDecoder.decode( type , "UTF-8");
			type = type.replaceAll("-prcntg-", "%");
			String action = manageReasonCodeService.checkInSO(id,type,buyerId,code);
			
			if(OrderConstants.DELETE_STATUS.equals(action)) {
				setAttribute("action",OrderConstants.DELETE_STATUS);
			}
			else if(OrderConstants.ARCHIVE_STATUS.equals(action)) {
				setAttribute("action",OrderConstants.ARCHIVE_STATUS);
			}
			setAttribute("reasonCode",code);
			setAttribute("reasonCodeId",id);
			setAttribute("reasonCodeType",type);
			setAttribute("reasonTypeId",typeId);
		} catch (Exception e) {
			logger.error("ReasonCodeManagerAction.checkInSO Exception");
		}
		return SUCCESS;
	}
	
	public ReasonCode getRc() {
		return rc;
	}

	public void setRc(ReasonCode rc) {
		this.rc = rc;
	}

}

