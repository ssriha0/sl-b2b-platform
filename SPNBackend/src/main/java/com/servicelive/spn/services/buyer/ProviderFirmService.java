/**
 * 
 */
package com.servicelive.spn.services.buyer;

import com.servicelive.spn.dao.provider.ProviderFirmDao;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.spn.services.BaseServices;

/**
 * @author cgarcia
 *
 */
public class ProviderFirmService extends BaseServices {

	private ProviderFirmDao providerFirmDao;

	public ProviderFirm getProviderFirmForId(Integer id) throws Exception {
		return providerFirmDao.findById(id);
	}

	public ProviderFirmDao getProviderFirmDao() {
		return providerFirmDao;
	}

	public void setProviderFirmDao(ProviderFirmDao providerFirmDao) {
		this.providerFirmDao = providerFirmDao;
	}

	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

}
