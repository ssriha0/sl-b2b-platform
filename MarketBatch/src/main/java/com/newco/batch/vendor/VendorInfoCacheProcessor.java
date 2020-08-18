package com.newco.batch.vendor;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.iBusiness.provider.IServiceProviderBO;

/**
 * @author jkahng
 */
public class VendorInfoCacheProcessor extends ABatchProcess {

	private IServiceProviderBO serviceProviderBO;

	@Override
	public int execute() {
		try {
			serviceProviderBO.updateVendorInfoCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public void setArgs(String[] args) {
		// do nothing
	}

	public void setServiceProviderBO(IServiceProviderBO serviceProviderBO) {
		this.serviceProviderBO = serviceProviderBO;
	}

}
