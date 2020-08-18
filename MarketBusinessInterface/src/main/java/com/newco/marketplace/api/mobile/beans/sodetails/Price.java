package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.utils.MoneyUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Price information.
 * @author Infosys
 *
 */

@XStreamAlias("price")
@XmlAccessorType(XmlAccessType.FIELD)
public class Price {	
	
	@XStreamAlias("totalLaborMaximumPrice")
	private Double totalLaborMaximumPrice;
	
	@XStreamAlias("totalPartsMaximumPrice")
	private Double totalPartsMaximumPrice;	
 	
   	@XStreamAlias("totalPrepaidPermitPrice")
   	private Double totalPrepaidPermitPrice;
   	
   	@XStreamAlias("totalNonPermitaddonprice")
   	private Double totalNonPermitaddonprice;   
   	
   	@XStreamAlias("totalPermitaddonprice")
   	private Double totalPermitaddonprice;
   	
	@XStreamAlias("finalLaborPriceByProvider")
	private Double finalLaborPriceByProvider;
	
	@XStreamAlias("finalPartsPriceByProvider")
	private Double finalPartsPriceByProvider;
	
	@XStreamAlias("totalInvoicePartsMaximumPrice")
	private Double totalInvoicePartsMaximumPrice ;
	
	@OptionalParam
	@XStreamAlias("partsTax")
	private String partsTax;
	
	@OptionalParam
	@XStreamAlias("partsDiscount")
	private String partsDiscount;
	
	@OptionalParam
	@XStreamAlias("laborTax")
	private String laborTax;
	
	@OptionalParam
	@XStreamAlias("laborDiscount")
	private String laborDiscount;
	
	@XStreamAlias("reasons")
	private PriceChangeReason reasons;
	
	@OptionalParam
	@XStreamAlias("laborPriceBeforeTax")
	private Double laborPriceBeforeTax;

	@OptionalParam
	@XStreamAlias("partsPriceBeforeTax")
	private Double partsPriceBeforeTax;
	
	
	/**
	 * @return the totalLaborMaximumPrice
	 */
	public Double getTotalLaborMaximumPrice() {
		return totalLaborMaximumPrice;
	}

	/**
	 * @param totalLaborMaximumPrice the totalLaborMaximumPrice to set
	 */
	public void setTotalLaborMaximumPrice(Double totalLaborMaximumPrice) {
		this.totalLaborMaximumPrice = totalLaborMaximumPrice;
	}

	/**
	 * @return the totalPartsMaximumPrice
	 */
	public Double getTotalPartsMaximumPrice() {
		return totalPartsMaximumPrice;
	}

	/**
	 * @param totalPartsMaximumPrice the totalPartsMaximumPrice to set
	 */
	public void setTotalPartsMaximumPrice(Double totalPartsMaximumPrice) {
		this.totalPartsMaximumPrice = totalPartsMaximumPrice;
	}


	/**
	 * @return the reasons
	 */
	public PriceChangeReason getReasons() {
		return reasons;
	}

	/**
	 * @param reasons the reasons to set
	 */
	public void setReasons(PriceChangeReason reasons) {
		this.reasons = reasons;
	}

	public Double getFinalLaborPriceByProvider() {
		return finalLaborPriceByProvider;
	}

	public void setFinalLaborPriceByProvider(Double finalLaborPriceByProvider) {
		this.finalLaborPriceByProvider = finalLaborPriceByProvider;
	}

	public Double getFinalPartsPriceByProvider() {
		return finalPartsPriceByProvider;
	}

	public void setFinalPartsPriceByProvider(Double finalPartsPriceByProvider) {
		this.finalPartsPriceByProvider = finalPartsPriceByProvider;
	}

	public Double getTotalPrepaidPermitPrice() {
		return totalPrepaidPermitPrice;
	}

	public void setTotalPrepaidPermitPrice(Double totalPrepaidPermitPrice) {
		this.totalPrepaidPermitPrice = MoneyUtil.getRoundedMoney(totalPrepaidPermitPrice);
	}

	public Double getTotalNonPermitaddonprice() {
		return totalNonPermitaddonprice;
	}

	public void setTotalNonPermitaddonprice(Double totalNonPermitaddonprice) {
		this.totalNonPermitaddonprice = MoneyUtil.getRoundedMoney(totalNonPermitaddonprice);
	}

	public Double getTotalPermitaddonprice() {
		return totalPermitaddonprice;
	}

	public void setTotalPermitaddonprice(Double totalPermitaddonprice) {
		this.totalPermitaddonprice =MoneyUtil.getRoundedMoney(totalPermitaddonprice);
	}
	

	public Double getTotalInvoicePartsMaximumPrice() {
		return totalInvoicePartsMaximumPrice;
	}

	public void setTotalInvoicePartsMaximumPrice(
			Double totalInvoicePartsMaximumPrice) {
		this.totalInvoicePartsMaximumPrice = MoneyUtil.getRoundedMoney(totalInvoicePartsMaximumPrice);
	}

	public String getPartsTax() {
		return partsTax;
	}

	public void setPartsTax(String partsTax) {
		this.partsTax = partsTax;
	}

	public String getPartsDiscount() {
		return partsDiscount;
	}

	public void setPartsDiscount(String partsDiscount) {
		this.partsDiscount = partsDiscount;
	}

	public String getLaborTax() {
		return laborTax;
	}

	public void setLaborTax(String laborTax) {
		this.laborTax = laborTax;
	}

	public String getLaborDiscount() {
		return laborDiscount;
	}

	public void setLaborDiscount(String laborDiscount) {
		this.laborDiscount = laborDiscount;
	}
	
	public Double getLaborPriceBeforeTax() {
		return laborPriceBeforeTax;
	}

	public void setLaborPriceBeforeTax(Double laborPriceBeforeTax) {
		this.laborPriceBeforeTax = laborPriceBeforeTax;
	}

	public Double getPartsPriceBeforeTax() {
		return partsPriceBeforeTax;
	}

	public void setPartsPriceBeforeTax(Double partsPriceBeforeTax) {
		this.partsPriceBeforeTax = partsPriceBeforeTax;
	}
}
