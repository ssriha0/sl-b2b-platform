package com.newco.marketplace.dto.vo.feedback;

public interface Ajaxable {
	public String toXml();
	
	public String toXmlDeepCopy();
	
	public String toJSON();
	
	public StringBuffer getBuffer();
}
