/**
 * 
 */
package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;

/**
 * @author hoza
 *
 */
public class ProviderMatchingCountsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long providerCounts;
	private Long providerFirmCounts;
	/**
	 * @return the providerCounts
	 */
	public Long getProviderCounts() {
		return providerCounts;
	}
	/**
	 * @param providerCounts the providerCounts to set
	 */
	public void setProviderCounts(Long providerCounts) {
		this.providerCounts = providerCounts;
	}
	/**
	 * @return the providerFirmCounts
	 */
	public Long getProviderFirmCounts() {
		return providerFirmCounts;
	}
	/**
	 * @param providerFirmCounts the providerFirmCounts to set
	 */
	public void setProviderFirmCounts(Long providerFirmCounts) {
		this.providerFirmCounts = providerFirmCounts;
	}
	
}
