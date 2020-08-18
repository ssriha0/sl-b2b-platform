package com.newco.marketplace.web.dto.ajax;

import com.newco.marketplace.web.dto.AbstractAjaxResultsDTO;


public class AjaxSelectItemDTO extends AbstractAjaxResultsDTO{

	private StringBuffer sb = new StringBuffer();
	private String index = "-1";
	private String key;
	private String value;
	private String selectName = "defaultName";
	
	public AjaxSelectItemDTO() {
		super();
	}

	public AjaxSelectItemDTO(String index) {
		super();
		this.index = index;
	}

	public AjaxSelectItemDTO(String index, String key) {
		super();
		this.index = index;
		this.key = key;
	}

	public AjaxSelectItemDTO(String index, String key, String value) {
		super();
		this.index = index;
		this.key = key;
		this.value = value;
	}

	public String toJSON() {
		return super.toJSON();
	}

	public String toXml() {
		//getSb().append("<select_container>");
		getBuffer().append("<select_set selectName='").append(getSelectName()).append("'>");
		getBuffer().append("<key>").append(getKey()).append("</key>");
				getBuffer().append("<value>").append(getValue()).append("</value>");
				getBuffer().append("<idx>").append(getIndex()).append("</idx>");
				getBuffer().append("</select_set>");
		//getSb().append("</select_container>");
		return getBuffer().toString();
	}
	
	public String toXmlDeepCopy() {
		getBuffer().append("<select_container>");
		getBuffer().append("<select_set selectName='").append(getSelectName().trim()).append("'>");
		getBuffer().append("<key>").append(getKey()).append("</key>");
				getBuffer().append("<value>").append(getValue().trim()).append("</value>");
				getBuffer().append("<idx>").append(getIndex().trim()).append("</idx>");
				getBuffer().append("<sub_select>");
				getBuffer().append(getSubSelectListToXml());
				getBuffer().append("</sub_select>");
				getBuffer().append("</select_set>");
			getBuffer().append("</select_container>");
		return getBuffer().toString().trim();
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
	
	public static void main(String args[]){
		AjaxSelectItemDTO t = new AjaxSelectItemDTO();
		t.setKey("1");
		t.setValue("test");
		t.addSubSelectItem(new AjaxSubSelectItemDTO("1","test","this is a test"));
		t.addSubSelectItem(new AjaxSubSelectItemDTO("1","test","this is a test"));
		t.addSubSelectItem(new AjaxSubSelectItemDTO("1","test","this is a test"));
		t.addSubSelectItem(new AjaxSubSelectItemDTO("1","test","this is a test"));
		t.addSubSelectItem(new AjaxSubSelectItemDTO("1","test","this is a test"));
		System.out.println(t.toXmlDeepCopy());
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public StringBuffer getBuffer() {
		// TODO Auto-generated method stub
		return sb;
	}

}
