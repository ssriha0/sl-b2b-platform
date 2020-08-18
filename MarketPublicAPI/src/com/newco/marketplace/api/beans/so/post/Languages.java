package com.newco.marketplace.api.beans.so.post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing language information for 
 * the SOPostService
 * @author Infosys
 *
 */
@XStreamAlias("languages")
public class Languages {

	
	@XStreamImplicit(itemFieldName="language")
	private  List<String> languages;

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	
	
}
