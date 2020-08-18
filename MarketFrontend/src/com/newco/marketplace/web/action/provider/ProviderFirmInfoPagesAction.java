package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.POPUP;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.PROVIDERINFO_OBJ;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.SUCCESS_EXTERNAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.SharedSecret;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.dto.provider.ProviderInfoPagesDto;
import com.newco.marketplace.web.security.NonSecurePage;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class ProviderFirmInfoPagesAction extends SLBaseAction implements
SessionAware,ServletRequestAware, Preparable  {
	
	
	private Map sSessionMap;
	private HttpSession session;
	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProviderInfoPagesAction.class.getName());
	
	
	private IProviderProfilePagesDelegate providerProfilePagesDelegate;
	
	
	public ProviderFirmInfoPagesAction(IProviderProfilePagesDelegate xproviderProfilePagesDelegate) {
		this.providerProfilePagesDelegate = xproviderProfilePagesDelegate;
		
	}
	
	public String execute() throws Exception{
		boolean isExternal = false;
		ProviderInfoPagesDto dataDto = new ProviderInfoPagesDto();
		List <String> errorList = new ArrayList <String>();
	
		String popup =  (String) ServletActionContext.getRequest().getParameter(POPUP);
			if("true".equalsIgnoreCase(popup)){
				isExternal = false;
			}
			else{
				isExternal = true;
			}
		String vendorIdStr =  (String) ServletActionContext.getRequest().getParameter(VENDOR_ID);
		Integer vendorId = null;
		if(vendorIdStr == null ) {
			//do something to return
			vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
			if(vendorIdStr == null) {
				return "fail";
			}
			logger.debug(" could not retrive the resouceid ");
		}
		
		if(StringUtils.isNumeric(vendorIdStr))
			vendorId = Integer.parseInt(vendorIdStr);
		
		String vendorName = null;
		
		logger.debug(" In ProviderInfo pages " + vendorIdStr);
		
		dataDto  = providerProfilePagesDelegate.getPublicFirmProfile(vendorId);
		Integer buyerId = null;		
		if(dataDto != null) {
		
				try {
					buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
					String userName = get_commonCriteria().getTheUserName();
					SharedSecret sharedSecret = new SharedSecret();
					sharedSecret.setBuyerId(buyerId);
					sharedSecret.setCreatedDate(new Date());
					sharedSecret.setIpAddress(request.getRemoteAddr());
					sharedSecret.setUserName(userName);
					String sharedSecretString = CryptoUtil.encryptObject(sharedSecret);
					dataDto.setSharedSecretString(sharedSecretString);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			dataDto.setIsExternal(isExternal);
			
			CompanyProfileVO companyProfileVO = dataDto.getCompanyPublicInfo();
			vendorName = companyProfileVO.getBusinessName();
			if(null != companyProfileVO ){
				errorList = companyProfileVO.getErrorList();
				if(null==errorList||errorList.isEmpty()){
					dataDto.setHasErrors(false);
				}else{
					dataDto.setHasErrors(true);
				}					
			}		
		}

		setAttribute("vendorId", vendorId);
		setAttribute("vendorName", vendorName);
		setAttribute("firstName", get_commonCriteria().getFName());
		setAttribute("lastName", get_commonCriteria().getLName());
		setAttribute("sharedSecret", dataDto.getSharedSecretString());
		
		if(buyerId != null){
			Boolean isSecuredInfoViewable = providerProfilePagesDelegate
					.isSPFirmNetworkTabViewable(buyerId.intValue(), vendorId);
			dataDto.setIsSecuredInfoViewable(isSecuredInfoViewable);
		}
		
		request.setAttribute(PROVIDERINFO_OBJ, dataDto);
		if(isExternal)	return SUCCESS_EXTERNAL;
		return SUCCESS;
	}
	
	
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
		
	}


	public void setSession(Map session) {
		// TODO Auto-generated method stub
		
	}


	public IProviderProfilePagesDelegate getProviderProfilePagesDelegate() {
		return providerProfilePagesDelegate;
	}


	public void setProviderProfilePagesDelegate(
			IProviderProfilePagesDelegate providerProfilePagesDelegate) {
		this.providerProfilePagesDelegate = providerProfilePagesDelegate;
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();		
	}

}
