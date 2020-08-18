package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.newco.marketplace.web.delegates.provider.ILicensesDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.LicensesDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author MTedder
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:50 $
 */
public class LicensesAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 3805705871875329838L;
	private static final Logger logger = Logger.getLogger(LicensesAction.class.getName());
	private ILicensesDelegate licensesDelegate = null;
	private LicensesDto ldto =null;
	HttpServletRequest request = null;

	/**
	 * @param licensesDelegate
	 * @param wdto
	 */
	public LicensesAction(ILicensesDelegate licensesDelegate, LicensesDto ldto) {	
		this.licensesDelegate = licensesDelegate;
		this.ldto = ldto;
	}
	
	public String doLoad() throws Exception{
		logger.debug("LicensesAction.doLoad()");
		getSessionMessages();
		ldto.setVendorID((String)ActionContext.getContext().getSession().get("vendorId"));//UNCOMMENT FOR PRODUCTION
		ldto.setAddCredentialToFile(licensesDelegate.get(ldto).getAddCredentialToFile());			
		if(ldto.getAddCredentialToFile()==0)
		{
			setNoCredIndString("false");
		}else if(ldto.getAddCredentialToFile()==1){
			setNoCredIndString("true");
		}
		return INPUT;
	}
	
	private void save() throws Exception {
		logger.debug("LicensesAction.save()");
		ldto.setVendorID((String)ActionContext.getContext().getSession().get("vendorId"));//UNCOMMENT FOR PRODUCTION
		if(ldto.getVendorID() != null){//pre existing vendor - then update DB
			if(getNoCredIndString().equals("false"))
			{
				ldto.setAddCredentialToFile(0);
			}else if(getNoCredIndString().equals("true")){
				ldto.setAddCredentialToFile(1);
			}
			logger.debug("SAVE2!!!!");
			licensesDelegate.update(ldto); 
			addActionMessage(getText("success.save"));
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.getActionMessages().addAll(getActionMessages());
		}
	}
	
	public String doNext() throws Exception{
		logger.debug("LicensesAction.doNext()");
		save();
		return "next";
		
	}
	
	public String doPrev() throws Exception{
		logger.debug("LicensesAction.doPrev()");
		save();
		return "prev";
		
	}
	
	public String doSave() throws Exception{
		logger.debug("LicensesAction.doSave()");
		save();
		return SUCCESS;
		
	}
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		logger.debug("LicensesAction.execute()");
		save();		
		return SUCCESS;
	}
		

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * @return the ldto
	 */
	public LicensesDto getLdto() {
		return ldto;
	}

	/**
	 * @param ldto the ldto to set
	 */
	public void setLdto(LicensesDto ldto) {
		this.ldto = ldto;
	}
	private String noCredIndString = "false";//string from jsp form
	/**
	 * @return the noCredIndString
	 */
	public String getNoCredIndString() {
		return noCredIndString;
	}

	/**
	 * @param noCredIndString the noCredIndString to set
	 */
	public void setNoCredIndString(String noCredIndString) {
		if(noCredIndString == null){
			noCredIndString = "";
		}
		this.noCredIndString = noCredIndString;
	}
	
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
}
/*
 * Maintenance History
 * $Log: LicensesAction.java,v $
 * Revision 1.8  2008/04/26 01:13:50  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.6.1  2008/04/23 11:41:40  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:19:35  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.6  2008/02/26 18:18:05  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.5.2.1  2008/02/25 20:01:55  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */