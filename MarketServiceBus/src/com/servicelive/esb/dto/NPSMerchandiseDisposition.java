package com.servicelive.esb.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MerchandiseDisposition")
public class NPSMerchandiseDisposition {

	@XStreamAlias("DateModified")
	private String dateModified = "";

	@XStreamAlias("MerchandisePickupStatus")
	private String merchandisePickupStatus = "";

	@XStreamAlias("MerchandisePickupDate")
	private String merchandisePickupDate = "";

	@XStreamAlias("FinalMerchandiseStatus")
	private String finalMerchandiseStatus;

	@XStreamAlias("FinalMerchandiseLocation")
	private String finalMerchandiseLocation = "";

	@XStreamAlias("MerchandisePrimaryDisposition")
	private String merchandisePrimaryDisposition = "";

	@XStreamAlias("MerchandiseDescriptiveDisposition")
	private String merchandiseDescriptiveDisposition = "";

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getMerchandisePickupStatus() {
		return merchandisePickupStatus;
	}

	public void setMerchandisePickupStatus(String merchandisePickupStatus) {
		this.merchandisePickupStatus = merchandisePickupStatus;
	}

	public String getMerchandisePickupDate() {
		return merchandisePickupDate;
	}

	public void setMerchandisePickupDate(String merchandisePickupDate) {
		this.merchandisePickupDate = merchandisePickupDate;
	}

	public String getFinalMerchandiseStatus() {
		return finalMerchandiseStatus;
	}

	public void setFinalMerchandiseStatus(String finalMerchandiseStatus) {
		this.finalMerchandiseStatus = finalMerchandiseStatus;
	}

	public String getFinalMerchandiseLocation() {
		return finalMerchandiseLocation;
	}

	public void setFinalMerchandiseLocation(String finalMerchandiseLocation) {
		this.finalMerchandiseLocation = finalMerchandiseLocation;
	}

	public String getMerchandisePrimaryDisposition() {
		return merchandisePrimaryDisposition;
	}

	public void setMerchandisePrimaryDisposition(
			String merchandisePrimaryDisposition) {
		this.merchandisePrimaryDisposition = merchandisePrimaryDisposition;
	}

	public String getMerchandiseDescriptiveDisposition() {
		return merchandiseDescriptiveDisposition;
	}

	public void setMerchandiseDescriptiveDisposition(
			String merchandiseDescriptiveDisposition) {
		this.merchandiseDescriptiveDisposition = merchandiseDescriptiveDisposition;
	}

}
