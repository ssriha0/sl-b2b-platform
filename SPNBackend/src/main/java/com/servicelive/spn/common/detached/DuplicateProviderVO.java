/**
 * 
 */
package com.servicelive.spn.common.detached;
import java.io.Serializable;

public class DuplicateProviderVO  implements Serializable {
	private Integer providerId;
	private String providerStatus;
	
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getProviderStatus() {
		return providerStatus;
	}
	public void setProviderStatus(String providerStatus) {
		this.providerStatus = providerStatus;
	}
	
}