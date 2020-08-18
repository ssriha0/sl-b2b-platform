

package com.newco.marketplace.api.beans.hi.account.update.provider;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("languages")
@XmlRootElement(name = "languages")
@XmlAccessorType(XmlAccessType.FIELD)
public class Languages {

	@XStreamImplicit(itemFieldName="language")
    private List<String> language;

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

	
   
 
}
