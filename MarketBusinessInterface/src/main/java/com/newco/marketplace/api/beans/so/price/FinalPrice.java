package com.newco.marketplace.api.beans.so.price;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("finalprice")
public class FinalPrice {
	
	@XStreamAlias("finalLaborPrice")
	private BigDecimal finalPriceForLabor;
	
	@XStreamAlias("finalPartPrice")
	private BigDecimal finalPriceForParts;
	
	@XStreamAlias("finalAddonPrice")
	private BigDecimal finalPriceForAddon;
	
	@XStreamAlias("finalInvoicePartPrice")
	private BigDecimal finalPriceForPartsInvoice;

	public BigDecimal getFinalPriceForLabor() {
		return finalPriceForLabor;
	}

	public void setFinalPriceForLabor(BigDecimal finalPriceForLabor) {
		this.finalPriceForLabor = finalPriceForLabor;
	}

	public BigDecimal getFinalPriceForParts() {
		return finalPriceForParts;
	}

	public void setFinalPriceForParts(BigDecimal finalPriceForParts) {
		this.finalPriceForParts = finalPriceForParts;
	}

	public BigDecimal getFinalPriceForAddon() {
		return finalPriceForAddon;
	}

	public void setFinalPriceForAddon(BigDecimal finalPriceForAddon) {
		this.finalPriceForAddon = finalPriceForAddon;
	}

	public BigDecimal getFinalPriceForPartsInvoice() {
		return finalPriceForPartsInvoice;
	}

	public void setFinalPriceForPartsInvoice(BigDecimal finalPriceForPartsInvoice) {
		this.finalPriceForPartsInvoice = finalPriceForPartsInvoice;
	}

	
	
}
