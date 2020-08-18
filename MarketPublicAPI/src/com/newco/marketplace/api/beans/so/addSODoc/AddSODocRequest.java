package com.newco.marketplace.api.beans.so.addSODoc;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing request information for 
 * the SOCreateService
 * @author Infosys
 *
 */
@XStreamAlias("soRequest")
public class AddSODocRequest {
	
	@XStreamImplicit(itemFieldName="serviceorderdoc")
	private List<AddSODocBean> serviceorderdoc;

	public List<AddSODocBean> getServiceorderdoc() {
		return serviceorderdoc;
	}

	public void setServiceorderdoc(List<AddSODocBean> serviceorderdoc) {
		this.serviceorderdoc = serviceorderdoc;
	}

	

}
