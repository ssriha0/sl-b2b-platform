package com.newco.marketplace.web.action.provider;

/**
 * @author sahmad7
 */

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.opensymphony.xwork2.ActionSupport;



//public class loadInsuranceAction extends ActionSupport implements ServletRequestAware{
public class DeleteInsuranceAction extends ActionSupport implements SessionAware {
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private static final long serialVersionUID = 1L;
	private InsurancePolicyDto insurancePolicyDto;
	private Map sSessionMap;
	private static final Logger logger = Logger
			.getLogger(ProviderRegistrationAction.class.getName());

	public DeleteInsuranceAction(IInsuranceTypeDelegate iInsuranceDelegate,
			InsurancePolicyDto insurancePolicyDto) {
		this.iInsuranceDelegate = iInsuranceDelegate;
		this.insurancePolicyDto = insurancePolicyDto;
	}

	public String doDelete() throws Exception {
		String return_val = SUCCESS;
		String isFromPA = (String)getSession().get("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}
			
		try {
			// get the below value from session
			if ((String) getSession().get("vendorId") != null) {
				insurancePolicyDto.setVendorId(new Integer(
						(String) getSession().get("vendorId")).intValue());
			}
			insurancePolicyDto.setUserId((String) getSession().get("username"));
			insurancePolicyDto = iInsuranceDelegate
					.deleteInsuranceDetails(insurancePolicyDto);
			return_val = "returnToNextCredential";
		} catch (DelegateException ex) {
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
		return return_val;
	}

	public InsurancePolicyDto getInsurancePolicyDto() {
		return insurancePolicyDto;
	}

	public void setInsurancePolicyDto(InsurancePolicyDto insurancePolicyDto) {
		this.insurancePolicyDto = insurancePolicyDto;
	}

	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap = ssessionMap;
	}

	public Map getSession() {
		// TODO Auto-generated method stub
		return this.sSessionMap;
	}

}