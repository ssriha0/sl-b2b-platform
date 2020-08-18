/**
 * 
 */
package com.newco.marketplace.dto.vo.cache;


/**
 * @author SALI030
 *
 */
public class ProviderCachedResult extends  CachedResultVO {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -7903394362092587721L;
	private Double availableBalance = 0.0;
	private Double totalReceivedAmount = 0.0;
	
	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getTotalReceivedAmount() {
		return totalReceivedAmount;
	}

	public void setTotalReceivedAmount(Double totalReceivedAmount) {
		this.totalReceivedAmount = totalReceivedAmount;
	}
	
	
	
//TODO 
}
