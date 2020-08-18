/**
 *
 */
package com.servicelive.domain.spn.detached;

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
		private Long providerCounts = Long.valueOf(0);// Potential Service pros
		private Long providerFirmCounts = Long.valueOf(0);
		
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

		public void add(ProviderMatchingCountsVO vo ) {
			this.providerCounts = Long.valueOf(this.providerCounts.longValue() + vo.getProviderCounts().longValue());
			this.providerFirmCounts = Long.valueOf(this.providerFirmCounts.longValue() + vo.getProviderFirmCounts().longValue());
	}
		
}
