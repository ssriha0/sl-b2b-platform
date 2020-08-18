package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.POPUP;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


/**
 * @author KSudhanshu
 *
 */
public class CompanyTabAction extends ActionSupport implements ServletResponseAware {

	private static final long serialVersionUID = -7387109195358846373L;
	private BaseTabDto tabDto;
	private String tabView;
	private HttpServletResponse response;
	private String tabType = null;
	private String nexturl = null;
	

	/**
	 * @param tabDto
	 */
	public CompanyTabAction(BaseTabDto tabDto) {
		super();
		this.tabDto = tabDto;
	}
	
	public String getTabType() {
		return tabType;
	}
	public void setTabType(String tabType) {
		this.tabType = tabType;
	}
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		
	
		
		if (ActionContext.getContext().getSession().get("tabDto") == null || tabView == null){
			ActionContext.getContext().getSession().put("tabDto", tabDto);
			System.out.println("inside here after setting tabdto"+ActionContext.getContext().getSession().get("tabDto"));
			String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		
		tabDto = (BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		}
		String getManageRuleProviderProfileDetail=(String) ServletActionContext.getRequest().getParameter("getManageRuleProviderProfileDetail");
		ActionContext.getContext().getSession().put("providerInfoFromManageRule", getManageRuleProviderProfileDetail);
		if((getManageRuleProviderProfileDetail!=null) &&(getManageRuleProviderProfileDetail.equalsIgnoreCase("true")))
		{
			String vendorFirmId=ServletActionContext.getRequest().getParameter("vendorFirmId");
			if(vendorFirmId!=null)
			{
				ActionContext.getContext().getSession().put("vendorFirmId",vendorFirmId);
			}
		}
				
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0"); 
		return SUCCESS;
	}
	
	
	
	/**
	 * @return the tabView
	 */
	public String getTabView() {
		return tabView;
	}
	/**
	 * @param tabView the tabView to set
	 */
	public void setTabView(String tabView) {
		this.tabView = tabView;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	/**
	 * @return the tabDto
	 */
	public BaseTabDto getTabDto() {
		return tabDto;
	}
	
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the nexturl
	 */
	public String getNexturl() {
		return nexturl;
	}

	/**
	 * @param nexturl the nexturl to set
	 */
	public void setNexturl(String nexturl) {
		this.nexturl = nexturl;
	}
	
	
}