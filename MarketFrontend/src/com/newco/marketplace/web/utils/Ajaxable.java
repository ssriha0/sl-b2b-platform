package com.newco.marketplace.web.utils;

public interface Ajaxable {
	public String toXml();
	
	public String toXmlDeepCopy();
	
	public String toJSON();
	
	public StringBuffer getBuffer();
}
