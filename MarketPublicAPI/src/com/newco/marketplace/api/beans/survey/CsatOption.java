package com.newco.marketplace.api.beans.survey;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("options")
public class CsatOption {
	
	@XStreamAlias("id")
    String id;
    @XStreamAlias("text")
    String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}    
}
