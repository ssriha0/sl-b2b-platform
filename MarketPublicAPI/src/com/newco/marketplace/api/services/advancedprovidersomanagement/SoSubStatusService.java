package com.newco.marketplace.api.services.advancedprovidersomanagement;

import com.newco.marketplace.business.iBusiness.advancedprovidersomanagement.IAdvancedProviderSoManagementBO;

public class SoSubStatusService {
 private IAdvancedProviderSoManagementBO soManagementBO;


 public IAdvancedProviderSoManagementBO getSoManagementBO() {
	return soManagementBO;
}

public void setSoManagementBO(IAdvancedProviderSoManagementBO soManagementBO) {
	this.soManagementBO = soManagementBO;
}
}
