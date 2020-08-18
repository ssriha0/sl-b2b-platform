package com.newco.marketplace.web.dto.ajax;

import com.newco.marketplace.web.dto.AbstractAjaxResultsDTO;

public class AjaxSubSelectItemDTO extends AbstractAjaxResultsDTO{

	private StringBuffer sb = new StringBuffer();
	private String index = "-1";
	private String key;
	private String value;
	private boolean isSelected;
	
	
	
	public AjaxSubSelectItemDTO() {
		super();
	}

	public AjaxSubSelectItemDTO(String index) {
		super();
		this.index = index;
	}

	public AjaxSubSelectItemDTO(String index, String key) {
		super();
		this.index = index;
		this.key = key;
	}

	public AjaxSubSelectItemDTO(String index, String key, String value) {
		super();
		this.index = index;
		this.key = key;
		this.value = value;
	}
 
	public String toJSON() {
		// TODO Auto-generated method stub
		return super.toJSON();
	}

	public String toXml() {
		getBuffer().append("<ss_item>");
		getBuffer().append("<key>").append(getKey().trim()).append("</key>");
			getBuffer().append("<value>").append(getValue().trim()).append("</value>");
			getBuffer().append("<idx>").append(getIndex().trim()).append("</idx>");
			getBuffer().append("</ss_item>");
		return getBuffer().toString();
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public StringBuffer getBuffer() {
		// TODO Auto-generated method stub
		return sb;
	}
}
