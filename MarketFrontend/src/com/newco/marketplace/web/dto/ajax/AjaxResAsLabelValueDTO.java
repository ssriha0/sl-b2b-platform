package com.newco.marketplace.web.dto.ajax;

import java.io.Serializable;

public class AjaxResAsLabelValueDTO implements Serializable {

	private String label;
	private String value;

	public AjaxResAsLabelValueDTO() {
		super();
	}
	public AjaxResAsLabelValueDTO(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
