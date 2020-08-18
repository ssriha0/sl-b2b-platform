package com.newco.marketplace.dto.vo.staging;

import com.newco.marketplace.webservices.base.CommonVO;

public class StagingOrderSkuVO 
	extends CommonVO
{

	private static final long serialVersionUID = 0L;

	private String sku;
	private Integer orderID;
	private Double priceRatio;
	private Double finalPrice;
	private Integer permitSkuInd;
	
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public Double getPriceRatio()
	{
		return priceRatio;
	}
	public void setPriceRatio(Double priceRatio)
	{
		this.priceRatio = priceRatio;
	}
	public Double getFinalPrice()
	{
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice)
	{
		this.finalPrice = finalPrice;
	}
	public Integer getPermitSkuInd()
	{
		return permitSkuInd;
	}
	public void setPermitSkuInd(Integer permitSkuInd)
	{
		this.permitSkuInd = permitSkuInd;
	}
	public Integer getOrderID()
	{
		return orderID;
	}
	public void setOrderID(Integer orderID)
	{
		this.orderID = orderID;
	}
	
	
}
