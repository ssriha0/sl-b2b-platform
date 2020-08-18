/**
 * 
 */
package com.newco.marketplace.dto.vo.cache;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 * @author SALI030
 *
 */
public class BuyerCachedResult extends CachedResultVO {
	
	private static final long serialVersionUID = 1890690846824382314L;
	private static final Logger logger = Logger.getLogger(BuyerCachedResult.class.getName());
	private Double availableBalance = 0.0;
	private Double currentBalance = 0.0;

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		if (logger.isDebugEnabled())
		  logger.debug("Setting available balance: " + availableBalance);
		this.availableBalance = availableBalance;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		if (logger.isDebugEnabled())
		  logger.debug("Setting current balance: " + currentBalance);
		this.currentBalance = currentBalance;
	}
	
	@Override
	public String toString() {
	     return new ToStringBuilder(this).
	      append("availableBalance", availableBalance).
	       append("currentBalance", currentBalance).
	       toString();
	   }

}
