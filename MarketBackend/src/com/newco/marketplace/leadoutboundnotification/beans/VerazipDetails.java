package com.newco.marketplace.leadoutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("veraziptable")
public class VerazipDetails {
	
	@XStreamAlias("verazipstate")	
	private String verazipState;
	
	@XStreamAlias("verazipstatetx")	
	private String verazipStateTx;
	
	@XStreamAlias("verazipzip1")	
	private String verazipZip1;
	
	@XStreamAlias("verazipzip2")	
	private String verazipZip2;
	
	@XStreamAlias("geocode")	
	private String geocode;
	
	@XStreamAlias("verazipzipext1")	
	private String verazipZipExt1;
	
	@XStreamAlias("verazipzipext2")	
	private String verazipZipExt2;
	
	@XStreamAlias("verazipcity")	
	private String verazipCity;
	
	@XStreamAlias("countycode")	
	private String countyCode;
	
	@XStreamAlias("countyname")	
	private String countyName;
	
	@XStreamAlias("inoutcitylimits")	
	private String inOutCityLimits;

	public String getVerazipState() {
		return verazipState;
	}

	public void setVerazipState(String verazipState) {
		this.verazipState = verazipState;
	}

	public String getVerazipStateTx() {
		return verazipStateTx;
	}

	public void setVerazipStateTx(String verazipStateTx) {
		this.verazipStateTx = verazipStateTx;
	}

	public String getVerazipZip1() {
		return verazipZip1;
	}

	public void setVerazipZip1(String verazipZip1) {
		this.verazipZip1 = verazipZip1;
	}

	public String getVerazipZip2() {
		return verazipZip2;
	}

	public void setVerazipZip2(String verazipZip2) {
		this.verazipZip2 = verazipZip2;
	}

	public String getGeocode() {
		return geocode;
	}

	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}

	public String getVerazipZipExt1() {
		return verazipZipExt1;
	}

	public void setVerazipZipExt1(String verazipZipExt1) {
		this.verazipZipExt1 = verazipZipExt1;
	}

	public String getVerazipZipExt2() {
		return verazipZipExt2;
	}

	public void setVerazipZipExt2(String verazipZipExt2) {
		this.verazipZipExt2 = verazipZipExt2;
	}

	public String getVerazipCity() {
		return verazipCity;
	}

	public void setVerazipCity(String verazipCity) {
		this.verazipCity = verazipCity;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getInOutCityLimits() {
		return inOutCityLimits;
	}

	public void setInOutCityLimits(String inOutCityLimits) {
		this.inOutCityLimits = inOutCityLimits;
	}
	
}
