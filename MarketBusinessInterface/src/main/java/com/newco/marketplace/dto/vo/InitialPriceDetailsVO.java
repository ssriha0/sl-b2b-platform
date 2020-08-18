package com.newco.marketplace.dto.vo;

/**
 * VO to store the initial labor price and parts price
 * @author SL Vishnu
 */
public class InitialPriceDetailsVO {
	private Double initialLaborPrice;
	private Double initialPartsPrice;
	
	public Double getInitialLaborPrice() {
		return initialLaborPrice;
	}
	public void setInitialLaborPrice(Double initialLaborPrice) {
		this.initialLaborPrice = initialLaborPrice;
	}
	public Double getInitialPartsPrice() {
		return initialPartsPrice;
	}
	public void setInitialPartsPrice(Double initialPartsPrice) {
		this.initialPartsPrice = initialPartsPrice;
	}
}
