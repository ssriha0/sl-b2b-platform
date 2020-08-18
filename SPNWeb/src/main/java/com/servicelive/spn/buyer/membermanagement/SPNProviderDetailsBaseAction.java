package com.servicelive.spn.buyer.membermanagement;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.auditor.ServiceProviderStateService;
import com.servicelive.spn.services.buyer.BuyerNotesService;

/**
 * 
 */
@Validation
public class SPNProviderDetailsBaseAction extends SPNBaseAction {

	private static final long serialVersionUID = -20100202L;

	private ServiceProviderStateService serviceProviderStateService;
	private BuyerNotesService buyerNotesService;
	private LookupService lookupService;

	/**
	 * 
	 * @return HttpServletResponse
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**
	 * 
	 * @return HttpSession
	 */
	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 
	 * @return Integer
	 */
	public Integer getServiceProviderId() {
		String serviceProviderIdStr = getRequest().getParameter("vendorResourceId");
		if(StringUtils.isEmpty(serviceProviderIdStr)) {
			return null;
		}
		try {
			return Integer.valueOf(serviceProviderIdStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			//FIXME add logger call
		}
		return null;

	}
	/**
	 * 
	 * @param paramName
	 * @return String
	 */
	public String getString(String paramName) {
		return getRequest().getParameter(paramName);
	}
	/**
	 * 
	 * @param paramName
	 * @return Integer
	 */
	public Integer getInteger(String paramName) {
		String intStr = getRequest().getParameter(paramName);
		if(StringUtils.isNumeric(intStr)) {
			return Integer.valueOf(intStr);
		}
		return null;
	}

	/**
	 * 
	 * @return BuyerNotesService
	 */
	public BuyerNotesService getBuyerNotesService() {
		return buyerNotesService;
	}

	/**
	 * 
	 * @param buyerNotesService
	 */
	public void setBuyerNotesService(BuyerNotesService buyerNotesService) {
		this.buyerNotesService = buyerNotesService;
	}
	/**
	 * 
	 * @return ServiceProviderStateService
	 */
	public ServiceProviderStateService getServiceProviderStateService() {
		return serviceProviderStateService;
	}

	/**
	 * 
	 * @param serviceProviderStateService
	 */
	public void setServiceProviderStateService(ServiceProviderStateService serviceProviderStateService) {
		this.serviceProviderStateService = serviceProviderStateService;
	}

	/**
	 * @param lookupService the lookupService to set
	 */
	public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}
	/**
	 * @return the lookupService
	 */
	public LookupService getLookupService() {
		return lookupService;
	}
}
