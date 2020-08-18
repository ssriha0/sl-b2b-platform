package com.newco.marketplace.web.action.provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IWarrantyDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.WarrantyDto;
import com.newco.marketplace.web.utils.ActivityRegistryConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.thoughtworks.xstream.XStream;

/**
 * <p> WarrantyAction. </p>
 * $Revision: 1.9 $ $Author: glacy $ $Date: 2008/04/26 01:13:50 $
 */
@Validation
public  class WarrantyAction  extends ActionSupport {
	
	private static final Logger logger = Logger.getLogger(WarrantyAction.class.getName());
	private static final long serialVersionUID = 2847851948039607639L;
	private IWarrantyDelegate iwarrantyDelegate = null;//changed from warrantyDelegate to iwarrantyDelegate by MTedder
	private IAuditLogDelegate auditLogDelegates;
	private WarrantyDto wdto =null;	
	private String type= null;
	private Map luWarrantyPeriods =null;//form dropdown box data	
	
	/**
	 * @param warrantyDelegate
	 * @param wdto
	 */
	public WarrantyAction(IWarrantyDelegate warrantyDelegate, WarrantyDto wdto) {
		
		this.iwarrantyDelegate = warrantyDelegate;
		this.wdto = wdto;
	}
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		logger.debug("WarrantyAction.validate()");
		super.validate();
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(wdto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.WARRANTY,"errorOn");
		}
	}

	private void save() throws Exception{
		logger.debug("WarrantyAction.save()");
		wdto.setVendorID((String)ActionContext.getContext().getSession().get("vendorId"));
		iwarrantyDelegate.saveWarrantyInfo(wdto);
		auditUserProfileLog(wdto);
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.WARRANTY,"complete");
		baseTabDto.getActionMessages().addAll(getActionMessages());
	}
	
	private void auditUserProfileLog(WarrantyDto wdto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {WarrantyDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(wdto);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_COMPANY_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		if(securityContext!=null)
		{
			auditUserProfileVO.setLoginCompanyId(securityContext.getCompanyId());
			auditUserProfileVO.setLoginResourceId(securityContext.getVendBuyerResId());
			auditUserProfileVO.setRoleId(securityContext.getRoleId());
			if(securityContext.isSlAdminInd())
				auditUserProfileVO.setIsSLAdminInd(1);
			auditUserProfileVO.setModifiedBy(securityContext.getUsername());
			auditUserProfileVO.setUserProfileData(xmlContent);
			auditLogDelegates.auditUserProfile(auditUserProfileVO);
		}
	}
	
    public String execute() throws Exception {
    	logger.debug("WarrantyAction.execute()");
    	String action = wdto.getAction();
		if("Next".equals(action)){
			return doNext();
		}
		if("Prev".equals(action)){
			return doPrev();
		}
		
		if("Update".equals(action)){
			return updateProfile();
		}
    	save();//added MTedder
    	//load();//Comment Mtedder
    	this.wdto = iwarrantyDelegate.getWarrantyData(wdto);//added MTedder
    	luWarrantyPeriods = sortedLuWarrantyPeriods(iwarrantyDelegate.getMapLuWarrantyPeriods());//added MTedder  
    	return SUCCESS;//change from "load" to Success by MTedder
    }
    
	@SkipValidation
	public String doLoad() throws Exception {
		logger.debug("WarrantyAction.doLoad()");
		getSessionMessages();
		wdto.setVendorID((String)ActionContext.getContext().getSession().get("vendorId"));//added MTedder
		this.wdto = iwarrantyDelegate.getWarrantyData(wdto);//added MTedder
		luWarrantyPeriods = sortedLuWarrantyPeriods(iwarrantyDelegate.getMapLuWarrantyPeriods());//added MTedder		 
		//load();//comment by MTedder
		// getting messages from session
		//added MTedder
		return "load";
	}
	
	@SkipValidation
	public String doInput() throws Exception {
		logger.debug("WarrantyAction.doInput()");
		//load();//Comment by MTedder		
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		getSessionMessages();//added by MTedder
		if (baseTabDto!= null) {
			wdto = (WarrantyDto)baseTabDto.getDtObject();//added MTedder
			baseTabDto.setDtObject(null);
			//this.wdto = warrantyDelegate.getWarrantyData(wdto);//comment MTedder	
			WarrantyDto dto = iwarrantyDelegate.getWarrantyData(wdto);//added MTedder
			luWarrantyPeriods = sortedLuWarrantyPeriods(iwarrantyDelegate.getMapLuWarrantyPeriods());//added MTedder
			wdto.setWarrPeriodLabor(dto.getWarrPeriodLabor());//added MTedder
			wdto.setWarrPeriodParts(dto.getWarrPeriodParts());//added MTedder
		}
		return "load";
	}
		
	public String doNext() throws Exception {
		logger.debug("WarrantyAction.doNext()");
		save();		
		return "next";
	}
	
	public String doPrev() throws Exception {
		logger.debug("WarrantyAction.doPrev()");
		save();		
		return "prev";
	}
	/**
	 * author: G.Ganapathy
	 * Method to updateProfile
	 * @return
	 * @throws Exception
	 */
	public String updateProfile() throws Exception {
		logger.debug("WarrantyAction.updateProfile()");
		save();		
		return "updateProfile";
	}
	public String doSave() throws Exception{
		logger.debug("WarrantyAction.doSave()");
		save();
		return SUCCESS;
	}
        
	/**
	 * @return the wdto
	 */
	public WarrantyDto getWdto() {
		return wdto;
	}

	/**
	 * @param wdto the wdto to set
	 */
	public void setWdto(WarrantyDto wdto) {
		this.wdto = wdto;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the luWarrantyPeriods
	 */
	public Map getLuWarrantyPeriods() {
		return luWarrantyPeriods;
	}

	/**
	 * @param luWarrantyPeriods the luWarrantyPeriods to set
	 */
	public void setLuWarrantyPeriods(Map luWarrantyPeriods) {
		this.luWarrantyPeriods = luWarrantyPeriods;
	}
	/*
	 * Set session data in request.
	 */
	private void getSessionMessages(){
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap());
			setActionErrors(baseTabDto.getActionErrors());
			baseTabDto.setActionErrors(new ArrayList<String>());
			setActionMessages(baseTabDto.getActionMessages());
			baseTabDto.setActionMessages(new ArrayList<String>());
		}
	}
	/*
	 * Converts a HashMap list of LuWarrantyPeriods to a sorted TreeMap
	 *  for ordered display of LuWarrantyPeriods in dropdown boxes
	 */
	private Map sortedLuWarrantyPeriods(Map sourceMap){
		return new TreeMap<String, String>(sourceMap);
	}

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}
}
/*
 * Maintenance History
 * $Log: WarrantyAction.java,v $
 * Revision 1.9  2008/04/26 01:13:50  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.7.6.1  2008/04/23 11:41:40  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.8  2008/04/23 05:19:37  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.7  2008/02/26 18:18:07  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.6.2.2  2008/02/25 18:58:49  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */