package com.newco.marketplace.api.beans.search.firms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This class would act as request for the searchFirms service
 * @author neenu_manoharan
 *
 */
@XSD(name = "searchFirmsRequest.xsd", path = "/resources/schemas/search/")
@XmlRootElement(name = "searchFirmsRequest")
@XStreamAlias("searchFirmsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFirmsRequest {

	@XStreamAlias("zip")
	private String zip;

	@XStreamAlias("mainCategory")
	private Integer mainCategory;

	@XStreamAlias("serviceDate1")
	private String serviceDate1;
	
	@XStreamAlias("serviceDate2")
	private String serviceDate2;
	
	@XStreamAlias("serviceTimeWindow")
	private String serviceTimeWindow;
	
	@XStreamAlias("radius")
	private String radius;

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(Integer mainCategory) {
		this.mainCategory = mainCategory;
	}

	public String getServiceDate1() {
		return serviceDate1;
	}

	public void setServiceDate1(String serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public String getServiceDate2() {
		return serviceDate2;
	}

	public void setServiceDate2(String serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	public String getServiceTimeWindow() {
		return serviceTimeWindow;
	}

	public void setServiceTimeWindow(String serviceTimeWindow) {
		this.serviceTimeWindow = serviceTimeWindow;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}
	
}
