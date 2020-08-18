package com.newco.marketplace.business.businessImpl.advancedprovidersomanagement;

import com.newco.marketplace.business.iBusiness.advancedprovidersomanagement.IAdvancedProviderSoManagementBO;
import com.newco.marketplace.persistence.iDao.advancedprovidersomanagement.AdvancedProviderSoManagementDao;

public class AdvancedProviderSoManagementBO implements IAdvancedProviderSoManagementBO {
 private AdvancedProviderSoManagementDao soManagementDao;

public AdvancedProviderSoManagementDao getSoManagementDao() {
	return soManagementDao;
}

public void setSoManagementDao(AdvancedProviderSoManagementDao soManagementDao) {
	this.soManagementDao = soManagementDao;
}
}
