package com.newco.marketplace.api.beans.search.soSearchTemplate;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class TemplateNames {
		
	@XStreamImplicit(itemFieldName="templateName")
	private List<String> templateName;

	public List<String> getTemplateName() {
		return templateName;
	}

	public void setTemplateName(List<String> templateName) {
		this.templateName = templateName;
	}

	
}
