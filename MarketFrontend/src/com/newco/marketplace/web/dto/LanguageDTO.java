package com.newco.marketplace.web.dto;


public class LanguageDTO extends SOWBaseTabDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7161201127142501415L;
	private Integer langId;
	private String langName;
	
	
	
	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		//return null;
	}

}
