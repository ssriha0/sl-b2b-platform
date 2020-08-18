package com.newco.marketplace.api.beans.so.search;



import com.newco.marketplace.api.beans.so.CustomReferences;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing search information for 
 * the SOSearchService
 * @author Infosys
 *
 */
@XStreamAlias("soSearch")
public class SOSearch {
	
	@XStreamAlias("maxResultSet")
	@XStreamAsAttribute()   
	private String maxResultSet;
	
	@XStreamAlias("version")
	@XStreamAsAttribute()   
	private String version;
	
	@XStreamAlias("customerName")
	private String customerName;
	
	@XStreamAlias("serviceLocZipcode")
	private String serviceLocZipcode;
	
	@XStreamAlias("serviceLocPhone")
	private String serviceLocPhone;
	
	@XStreamAlias("customReferences")
	private CustomReferences customReferences;
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getServiceLocZipcode() {
		return serviceLocZipcode;
	}

	public void setServiceLocZipcode(String serviceLocZipcode) {
		this.serviceLocZipcode = serviceLocZipcode;
	}

	public String getServiceLocPhone() {
		return serviceLocPhone;
	}

	public void setServiceLocPhone(String serviceLocPhone) {
		this.serviceLocPhone = serviceLocPhone;
	}

	public CustomReferences getCustomReferences() {
		return customReferences;
	}

	public void setCustomReferences(CustomReferences customReferences) {
		this.customReferences = customReferences;
	}

	public String getMaxResultSet() {
		return maxResultSet;
	}

	public void setMaxResultSet(String maxResultSet) {
		this.maxResultSet = maxResultSet;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
