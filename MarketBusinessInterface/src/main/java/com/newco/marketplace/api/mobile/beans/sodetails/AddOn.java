package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.Currency;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.math.BigDecimal;

/**
 * This is a generic bean class for storing AddOn information.
 * @author Infosys
 *
 */

@XStreamAlias("addon")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddOn {
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();	
	
	@XStreamAlias("soAddonId")
	private String soAddonId;
	
	@XStreamAlias("addonSKU")
	private String addonSKU;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("customerCharge")
	private Double customerCharge;
	
	@XStreamAlias("miscInd")
	private String miscInd;
	
	@XStreamAlias("qty")
	private Integer qty;
	
	@XStreamAlias("editable")
	private String editable;
	
	@XStreamAlias("taxPercentage")
	private Double taxPercentage;
	
	//Added for displaying provider payment 
	//Commenting to Remove from response as it is not in new updated API spec,april 4,2014	
	/*@XStreamAlias("providerPaid")
	private String providerPaid;*/
		
	@XStreamOmitField
	private Double providerPayment;
	
	@XStreamOmitField
	private Double margin;
	
	@XStreamOmitField
	private Integer quantity;
	
	@XStreamOmitField
	private String coverageType;
	
	/**
	 * @return the soAddonId
	 */
	public String getSoAddonId() {
		return soAddonId;
	}

	/**
	 * @param soAddonId the soAddonId to set
	 */
	public void setSoAddonId(String soAddonId) {
		this.soAddonId = soAddonId;
	}

	/**
	 * @return the addonSKU
	 */
	public String getAddonSKU() {
		return addonSKU;
	}

	/**
	 * @param addonSKU the addonSKU to set
	 */
	public void setAddonSKU(String addonSKU) {
		this.addonSKU = addonSKU;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the customerCharge
	 */
	public Double getCustomerCharge() {
		return customerCharge;
	}

	/**
	 * @param customerCharge the customerCharge to set
	 */
	public void setCustomerCharge(Double customerCharge) {
		this.customerCharge = customerCharge;
	}

	/**
	 * @return the miscInd
	 */
	public String getMiscInd() {
		return miscInd;
	}

	/**
	 * @param miscInd the miscInd to set
	 */
	public void setMiscInd(String miscInd) {
		this.miscInd = miscInd;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getProviderPayment() {
		return providerPayment;
	}

	public void setProviderPayment(Double providerPayment) {		
		this.providerPayment = providerPayment;
	}
   
	public void getAddonPrice(){
	      	BigDecimal addonPrice = this.getRoundedMoneyBigDecimal(this.qty * (this.getCustomerCharge() - (this.getCustomerCharge() * new BigDecimal(this.margin).doubleValue())));
	        this.setProviderPayment(addonPrice.doubleValue());
	    }
	
	//for calculating  addonPrice with given Quantity one
   /*	public void getAddonPrice(Integer qty){
      	BigDecimal addonPrice = this.getRoundedMoneyBigDecimal(qty * (this.getCustomerCharge() - (this.getCustomerCharge() * new BigDecimal(this.margin).doubleValue())));
        this.setProviderPaid("$"+addonPrice.toString());
    }    */
	
		/**
		 * Custom rounding function used to round up in cases where 
		 * third digit after decimal is 5, returns a BigDecimal
		 * 
		 * @param doubleNumber
		 * @return
		 */
	public  BigDecimal getRoundedMoneyBigDecimal(Double doubleNumber) {
			if (doubleNumber == null) {
				return null;
			}
			BigDecimal bigDecimal = new BigDecimal(doubleNumber);
			String numberAsString = Double.toString(doubleNumber);
			int decimalPos = numberAsString.indexOf(".");
			String afterDecimal = numberAsString.substring(decimalPos);
			if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
				bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_UP);
			}else{
				bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
			}
			return bigDecimal;
		}

		public Double getMargin() {
			return margin;
		}

		public void setMargin(Double margin) {
			this.margin = margin;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public String getCoverageType() {
			return coverageType;
		}

		public void setCoverageType(String coverageType) {
			this.coverageType = coverageType;
		}

		public String getEditable() {
			return editable;
		}

		public void setEditable(String editable) {
			this.editable = editable;
		}
		
		public Double getTaxPercentage() {
			return taxPercentage;
		}

		public void setTaxPercentage(Double taxPercentage) {
			this.taxPercentage = taxPercentage;
		}

		
		/*public String getProviderPaid() {
			return providerPaid;
		}

		public void setProviderPaid(String providerPaid) {
			this.providerPaid = providerPaid;
		}*/
	

}
