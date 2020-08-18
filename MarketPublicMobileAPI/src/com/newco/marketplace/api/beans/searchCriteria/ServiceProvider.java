package com.newco.marketplace.api.beans.searchCriteria;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/07/03
 * the response for SOGetSearchCriteria
 *
 */
@XStreamAlias("serviceProvider")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceProvider {
	
	@XStreamAlias("id")
	private Integer id;
	
	@XStreamAlias("descr")
	private String descr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
}
