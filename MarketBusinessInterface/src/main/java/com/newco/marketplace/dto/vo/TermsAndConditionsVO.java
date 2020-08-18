package com.newco.marketplace.dto.vo;

import com.newco.marketplace.webservices.base.CommonVO;

public class TermsAndConditionsVO extends CommonVO{
	
 /**
	 * 
	 */
	private static final long serialVersionUID = 4504369792857567371L;
private  Integer termsCondId;
 private  String  termsCondContent;

 
 public Integer getTermsCondId() {
		return termsCondId;
	}
	public void setTermsCondId(Integer termsCondId) {
		this.termsCondId = termsCondId;
	}
	public String getTermsCondContent() {
		return termsCondContent;
	}
	public void setTermsCondContent(String termsCondContent) {
		this.termsCondContent =termsCondContent;
	}
	
	}
 
 
 
 
 
 

