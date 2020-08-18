package com.newco.marketplace.web.action.provider;



import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.delegates.provider.ITermsAndCondDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.TermsAndCondDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

@Validation
public class TermsAndCondAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private ITermsAndCondDelegate iTermsAndCondDelegate;
	private TermsAndCondDto termsAndCondDto;
	private Boolean termsFlag = false;
	
	@Override
	public void validate() {
		super.validate();
		if(hasFieldErrors()){
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.setDtObject(termsAndCondDto);
		baseTabDto.getFieldsError().putAll(getFieldErrors());
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.TERMSANDCOND,"errorOn");
		}
	}
	
	/**
	 * @param businessinfoDelegate
	 * @param businessinfoDto
	 */
	public TermsAndCondAction(ITermsAndCondDelegate termsAndCondDelegate, TermsAndCondDto termsAndCondDto) {
		iTermsAndCondDelegate = termsAndCondDelegate;
		this.termsAndCondDto = termsAndCondDto;
	}

	public String doSave() throws Exception {
		if(localIsValidFormData() == false ) {
			whatAidotMethodIsthis();
		}
		if (hasFieldErrors() || hasActionErrors()) {
			return INPUT;
		}
		String vendorID = (String)ActionContext.getContext().getSession().get("vendorId");
		if (vendorID!=null)
		termsAndCondDto.setVendorId(Integer.parseInt(vendorID));
		//call save only when you need to call. i.e the disable checkbox would not be available anyways and there is nothing to save if the termsFalg is true
		if(Boolean.FALSE.equals(this.termsFlag)){
			iTermsAndCondDelegate.save(termsAndCondDto);
			addActionMessage("Successfully updated data!");
			ActionContext.getContext().getSession().put("termsLegalNoticeChecked",false);
			ActionContext.getContext().getSession().remove("isSLAdmin");
			ActionContext.getContext().getSession().remove("isPrimaryResource");
		
		}
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.TERMSANDCOND,"complete");
		baseTabDto.getActionMessages().addAll(getActionMessages());
		baseTabDto.getFieldsError().putAll(getFieldErrors());
		return SUCCESS;
	}
	
	public String doprevious() throws Exception {
		if(localIsValidFormData() == false ) {
			whatAidotMethodIsthis();
		}
		if (hasFieldErrors() || hasActionErrors()) {
			
			return INPUT;
		}
		String vendorID = (String)ActionContext.getContext().getSession().get("vendorId");
		if (vendorID!=null)
		termsAndCondDto.setVendorId(Integer.parseInt(vendorID));
		
		 //call save only when you need to call. i.e the disable checkbox would not be available anyways and there is nothing to save if the termsFalg is true
		if(Boolean.FALSE.equals(this.termsFlag)){
			iTermsAndCondDelegate.save(termsAndCondDto);
		}
	
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.TERMSANDCOND,"complete");
		baseTabDto.getActionMessages().addAll(getActionMessages());
		baseTabDto.getFieldsError().putAll(getFieldErrors());
		return "previous";
	}
	/**
	 * author: G.Ganapathy
	 * Method to updateProfile
	 * @return
	 * @throws Exception
	 */
	public String updateProfile() throws Exception {
		if(localIsValidFormData() == false ) {
			whatAidotMethodIsthis();
		}
		if (hasFieldErrors() || hasActionErrors()) {
			
			return INPUT;
		}
		String vendorID = (String)ActionContext.getContext().getSession().get("vendorId");
		if (vendorID!=null)
		termsAndCondDto.setVendorId(Integer.parseInt(vendorID));
		iTermsAndCondDelegate.save(termsAndCondDto);
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.TERMSANDCOND,"complete");
		baseTabDto.getActionMessages().addAll(getActionMessages());
		return "updateProfile";
	}
		
    @SkipValidation
	public String doLoad() throws Exception {
    	termsAndCondDto.setVendorId(Integer.parseInt((String)ActionContext.getContext().getSession().get("vendorId")));					
		termsAndCondDto = iTermsAndCondDelegate.getData(termsAndCondDto);
		if(termsAndCondDto !=null)
		{
			if(termsAndCondDto.getAcceptTerms() && termsAndCondDto.getAcceptBucksTerms() && termsAndCondDto.getAcceptNewBucksTerms())
			{
				this.setTermsFlag(true);
			}
		}
		
		getSessionMessages();
			
		return "load";
	}

	@SkipValidation
	public String doInput() throws Exception {
		String vendorID = (String)ActionContext.getContext().getSession().get("vendorId");
		boolean terms = termsAndCondDto.getAcceptTerms(); 
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		getSessionMessages();
		if (baseTabDto!= null) {
			termsAndCondDto = (TermsAndCondDto)baseTabDto.getDtObject();
			termsAndCondDto = iTermsAndCondDelegate.getData(termsAndCondDto);
			if (vendorID!=null) termsAndCondDto.setVendorId(Integer.parseInt(vendorID));
			baseTabDto.setDtObject(termsAndCondDto);
			termsAndCondDto.setAcceptTerms(terms);
		}
		return "load";
	}
	
	//I should have refrain from using this.. I tried to manually callthe validate() instead, this new homegrown... validate appreas not to be caling the the validation.xml I dotn know why 
	//May be it is calling but the field validator it has used is incorrect.. I tried using the expression validator.. no luck.. back to old fashioned way of validating...
	//need this get out ASAP...
	private boolean localIsValidFormData(){
		if(termsFlag) return true;
		boolean result = true;
		if(termsAndCondDto.getAcceptTermsInd()!=1){
			addFieldError("termsAndCondDto.acceptTerms", getText("termsAndCondDto.acceptTerms.error"));
		}
		//This code is to set the AcceptTerms even when the field is disabled in the front end
		else{
			termsAndCondDto.setAcceptTerms(true);
		}
		if(termsAndCondDto.getAcceptBucksTermsInd()!=1){
			addFieldError("termsAndCondDto.acceptBucksTerms", getText("termsAndCondDto.acceptBucksTerms.error"));
		}
		//This code is to set the AcceptBucksTerms even when the field is disabled in the front end
		else{
			termsAndCondDto.setAcceptBucksTerms(true);
		}
		//SLT-2236:New checkbox --starts
		if(termsAndCondDto.getAcceptNewBucksTermsInd()!=1){
			addFieldError("termsAndCondDto.acceptNewBucksTerms", getText("Please accept all the terms and conditions"));
		}
		//This code is to set the NewAcceptBucksTerms even when the field is disabled in the front end
		else{
			termsAndCondDto.setAcceptNewBucksTerms(true);
		}
		//SLT-2236 --ends
		if(hasFieldErrors()){
			result=false;
		}
		return result;
	}
	
	//I had to do.. as I could not figure out wht the validation.xml didnt get triggered.... ??
	private void whatAidotMethodIsthis(){
		if (hasFieldErrors() || hasActionErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(termsAndCondDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.TERMSANDCOND,"errorOn");
		}
		 
	}


	private String type=null;
	
	/**
	 * @return the termsAndCondDto
	 */
	public TermsAndCondDto getTermsAndCondDto() {
		return termsAndCondDto;
	}
	/**
	 * @param termsAndCondDto the termsAndCondDto to set
	 */
	public void setTermsAndCondDto(TermsAndCondDto termsAndCondDto) {
		this.termsAndCondDto = termsAndCondDto;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		if(type==null)
			type="";
		
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	
	public Boolean isTermsFlag() {
		return termsFlag;
	}

	public Boolean getTermsFlag() {
		return termsFlag;
	}

	public void setTermsFlag(Boolean termsFlag) {
		this.termsFlag = termsFlag;
	}
}
