/**
 * 
 */
package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNServiceProviderState;

/**
 * @author hoza
 *
 */
public class MemberManageSearchResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6837789719030721820L;
	ProviderFirm providerFirm;
	ServiceProvider serviceProvider;
	List<SPNProviderFirmState> providerFirmNetworkInfo = new ArrayList<SPNProviderFirmState>(0);
	List<SPNServiceProviderState> serviceProviderNetworkInfo = new ArrayList<SPNServiceProviderState>(0);
	/**
	 * @return the providerFirm
	 */
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}
	/**
	 * @param providerFirm the providerFirm to set
	 */
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}
	/**
	 * @return the serviceProvider
	 */
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	/**
	 * @return the providerFirmNetworkInfo
	 */
	public List<SPNProviderFirmState> getProviderFirmNetworkInfo() {
		return providerFirmNetworkInfo;
	}
	/**
	 * @param providerFirmNetworkInfo the providerFirmNetworkInfo to set
	 */
	public void setProviderFirmNetworkInfo(
			List<SPNProviderFirmState> providerFirmNetworkInfo) {
		this.providerFirmNetworkInfo = providerFirmNetworkInfo;
	}
	/**
	 * @return the serviceProviderNetworkInfo
	 */
	public List<SPNServiceProviderState> getServiceProviderNetworkInfo() {
		return serviceProviderNetworkInfo;
	}
	/**
	 * @param serviceProviderNetworkInfo the serviceProviderNetworkInfo to set
	 */
	public void setServiceProviderNetworkInfo(
			List<SPNServiceProviderState> serviceProviderNetworkInfo) {
		this.serviceProviderNetworkInfo = serviceProviderNetworkInfo;
	}
	
	
}
