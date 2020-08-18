package com.newco.marketplace.web.action.provider;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.delegates.provider.ICompanyProfileDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.CompanyProfileDto;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;



public class CompanyProfileAction extends SLBaseAction implements Preparable, SessionAware, ServletRequestAware {
	private ICompanyProfileDelegate companyProfileDelegate;
	private CompanyProfileDto companyProfileDto;
	private ISPNBuyerDelegate spnDelegate;
	private Map sSessionMap;
	private HttpSession session;
	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CompanyProfileAction.class.getName());
	// This Has to be logged in resource_id's primary Index
	
	// This Has to be logged in resource_id's 

	private SecurityContext securityContext;
	private String resourceId;
	private BaseTabDto tabDto;

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	
	
	public BaseTabDto getTabDto() {
		return tabDto;
	}

	public void setTabDto(BaseTabDto tabDto) {
		this.tabDto = tabDto;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public CompanyProfileAction(ICompanyProfileDelegate companyProfileDelegate,
									CompanyProfileDto companyProfileDto,
									ISPNBuyerDelegate spnDelegate
									)
	{
		this.companyProfileDelegate = companyProfileDelegate;
		this.companyProfileDto = companyProfileDto;
		this.spnDelegate = spnDelegate;
	}
	
	public String doLoad() throws Exception
	{
		try{
	
			ActionContext.getContext().getSession().put("tabDto", tabDto);
			
			int vendorId = 0;
			setContextDetails();
			//Start --Flag to identify vendor id from manage rule and assigning value to vendor id 
			String getManageRuleProviderProfileDetail=(String) ServletActionContext.getRequest().getParameter("getManageRuleProviderProfileDetail");
			if((getManageRuleProviderProfileDetail!=null) &&(getManageRuleProviderProfileDetail.equalsIgnoreCase("true")))
			{
				String vendorIdFromManageRule = (String)ActionContext.getContext().getSession().get("vendorFirmId");
				vendorId=Integer.parseInt(vendorIdFromManageRule);
			}
			//End 
			else
			{
			vendorId = securityContext.getCompanyId();
			}
			if(vendorId !=0)
			{
				companyProfileDto = companyProfileDelegate.getCompleteProfile(vendorId);
				
				Integer providerId = get_commonCriteria().getVendBuyerResId();
				companyProfileDto.setBuyerSPNList(spnDelegate.getProviderProfileBuyers(providerId));
				
				if(companyProfileDto.getBuyerSPNList() != null && companyProfileDto.getBuyerSPNList().size() > 0)
				{
					setAttribute("resourceName", get_commonCriteria().getFName() + " " + get_commonCriteria().getLName());
				}				
			}
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured in load() of CompanyProfileAction while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured load() of CompanyProfileAction while processing the request due to"+ex.getMessage());
			return ERROR;
		}
		return "load";
	}
	
	public String displayLogo() throws Exception {
		
		String buyerIdStr = (String)getParameter("buyerID");
		Integer buyerIdInt = null;
		DocumentVO doc;
		
		if(SLStringUtils.isNullOrEmpty(buyerIdStr))
		{
			return SUCCESS;
		}
		if(SLStringUtils.IsParsableNumber(buyerIdStr) == false)
		{
			return SUCCESS;
		}
	
		buyerIdInt = Integer.parseInt(buyerIdStr);
			
		doc = spnDelegate.getLogoDoc(buyerIdInt, Constants.DocumentTypes.BUYER, 
		        OrderConstants.BUYER_ROLEID, -1);
		if(doc == null)
			return SUCCESS;
	    
		
		try{
			
			StringTokenizer fileName = new StringTokenizer(doc.getFileName(), ".");
			String extension = "";
			while (fileName.hasMoreTokens()) {
				extension = fileName.nextToken();
			}
			if (extension.equals("gif")) {
				getResponse().setContentType("image/gif");
			}else if(extension.equals("jpeg")|| extension.equals("jpg")){
				getResponse().setContentType("image/jpeg");
			}else if(extension.equals("png")){
				getResponse().setContentType("image/png");
			}else {
				getResponse().setContentType("text/html");
			}
			String header = "attachment;filename=\""
					+ doc.getFileName() + "\"";
			getResponse().setHeader("Content-Disposition", header);
			InputStream in = new ByteArrayInputStream(doc.getBlobBytes());
			ServletOutputStream outs = getResponse().getOutputStream();
			int bit = 256;
			while ((bit) >= 0) {
				bit = in.read();
				outs.write(bit);
			}
			outs.flush();
			outs.close();
			in.close();
		} catch (Exception e) {
			//logger.error("Error in DisplayBuyerDocumentAction --> displayBuyerDocument()");
		}
		
		// Need to clear 'logoDoc' out of session
		getSession().removeAttribute("logoDoc");
		return SUCCESS;
	}
	
	
	
	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}
	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap=ssessionMap;		
	}
	
//	public Map getSession() {
//		// TODO Auto-generated method stub
//		return this.sSessionMap;		
//	}

	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;
	}

	public String doEdit() throws Exception
	{
		return "editCompanyProfile"; 
	}

	public ICompanyProfileDelegate getCompanyProfileDelegate() {
		return companyProfileDelegate;
	}

	public void setCompanyProfileDelegate(
			ICompanyProfileDelegate companyProfileDelegate) {
		this.companyProfileDelegate = companyProfileDelegate;
	}

	public CompanyProfileDto getCompanyProfileDto() {
		return companyProfileDto;
	}

	public void setCompanyProfileDto(CompanyProfileDto companyProfileDto) {
		this.companyProfileDto = companyProfileDto;
	}

	
	
	
}