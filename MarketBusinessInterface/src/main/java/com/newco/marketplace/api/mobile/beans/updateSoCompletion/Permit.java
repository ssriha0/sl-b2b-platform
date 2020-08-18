package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing Permit information.
 * @author Infosys
 *
 */
@XStreamAlias("permit")
public class Permit {
	
	@XStreamAlias("addOnPrePaid")
	private String addOnPrePaid;
	
	@XStreamAlias("autogenAddon")
	private Boolean autogenAddon;
	
	@XStreamAlias("permitType")
	private String permitType;
	
	@XStreamAlias("prePaidAmount")
	private String prePaidAmount;

	@XStreamAlias("customerCharge")
	private String customerCharge;
	
	@XStreamOmitField
	private Integer autogenAddonInd;
	
	@XStreamOmitField	
	private Integer permitTypeId;
	
	public String getAddOnPrePaid() {
		return addOnPrePaid;
	}

	public void setAddOnPrePaid(String addOnPrePaid) {
		this.addOnPrePaid = addOnPrePaid;
	}

	public Boolean getAutogenAddon() {
		return autogenAddon;
	}

	public void setAutogenAddon(Boolean autogenAddon) {
		this.autogenAddon = autogenAddon;
	}

	public String getPermitType() {
		return permitType;
	}

	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public String getPrePaidAmount() {
		return prePaidAmount;
	}

	public void setPrePaidAmount(String prePaidAmount) {
		this.prePaidAmount = prePaidAmount;
	}

	public String getCustomerCharge() {
		return customerCharge;
	}

	public void setCustomerCharge(String customerCharge) {
		this.customerCharge = customerCharge;
	}

	public int getQty() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getAutogenAddonInd() {
		return autogenAddonInd;
	}

	public void setAutogenAddonInd(Integer autogenAddonInd) {
		this.autogenAddonInd = autogenAddonInd;
	}

	public Integer getPermitTypeId() {
		return permitTypeId;
	}

	public void setPermitTypeId(Integer permitTypeId) {
		this.permitTypeId = permitTypeId;
	}

	
}
