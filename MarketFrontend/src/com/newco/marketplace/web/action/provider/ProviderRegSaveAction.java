package com.newco.marketplace.web.action.provider;

/**
 * @author Covansys
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.delegates.provider.IRegistrationDelegate;
import com.newco.marketplace.web.dto.provider.ProviderRegistrationDto;
import com.opensymphony.xwork2.ActionSupport;


public class ProviderRegSaveAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4181690592816703326L;
	private IRegistrationDelegate iRegistrationDelegate;
	private ProviderRegistrationDto registrationDto;
	private List serviceCallList ;
	private List tncList;     
	private LookupVO lookupVo;
	
	private List primaryIndList = new ArrayList();
	
	private static final Logger logger = Logger.getLogger(ProviderRegistrationAction.class
			.getName());
	
	
	public ProviderRegSaveAction(IRegistrationDelegate iRegistrationDelegate,ProviderRegistrationDto registrationDto) {
		
		this.iRegistrationDelegate = iRegistrationDelegate;
		this.registrationDto = registrationDto;
	}
	
		public String doInsert() throws Exception {
		try{
			
			iRegistrationDelegate.saveRegistration(registrationDto);
			
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			loadPage();
			return ERROR;
		}catch(DuplicateUserException dpe){
			dpe.printStackTrace();
			System.out.println("-----------------Hi------------------9999");
			logger.info("Exception Occured while processing the request due to"+dpe.getMessage());
			addActionError("Exception Occured while processing the request due to"+dpe.getMessage());
			loadPage();
			return "duplicate";
		}
		return SUCCESS;
	}
	
public String execute() throws Exception {
	try{
		System.out.println("-----------------Hi------------------9999");
		registrationDto=iRegistrationDelegate.saveRegistration(registrationDto);
		
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}catch(DuplicateUserException dpe){
			dpe.printStackTrace();
			logger.info("Exception Occured while processing the request due to"+dpe.getMessage());
			addActionError("Exception Occured while processing the request due to"+dpe.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}

	private void loadPage()throws DelegateException{
	serviceCallList = new ArrayList();
	serviceCallList.add("Yes");
	serviceCallList.add("No");
	tncList = new ArrayList();
	tncList.add("I accept the Terms & Conditions");
	tncList.add("I do not accept the Terms & Conditions.");
	try{
	registrationDto=iRegistrationDelegate.loadRegistration(registrationDto);
	}catch(DelegateException dl){
		throw dl;
	}
}
public ProviderRegistrationDto getRegistrationDto() {
	return registrationDto;
}

public void setRegistrationDto(ProviderRegistrationDto registrationDto) {
	this.registrationDto = registrationDto;
}

public List getServiceCallList() {
	return serviceCallList;
}

public void setServiceCallList(List serviceCallList) {
	this.serviceCallList = serviceCallList;
}

public List getTncList() {
	return tncList;
}

public void setTncList(List tncList) {
	this.tncList = tncList;
}

public List getPrimaryIndList() {
	return primaryIndList;
}

public void setPrimaryIndList(List primaryIndList) {
	this.primaryIndList = primaryIndList;
}
}	