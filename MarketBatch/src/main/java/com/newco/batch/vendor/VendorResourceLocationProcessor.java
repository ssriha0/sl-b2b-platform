package com.newco.batch.vendor;

import com.newco.marketplace.persistence.iDao.vendor.VendorResourceLocationDao;

public class VendorResourceLocationProcessor {

	private VendorResourceLocationDao vendorLocationResourceDao;
	
	public int execute() {
		vendorLocationResourceDao.clearTable();
		vendorLocationResourceDao.populateTable();

		vendorLocationResourceDao.clearServiceAreaTable();
		vendorLocationResourceDao.populateServiceAreaTable();

		return 1;
	}

	/**
	 * @param vendorLocationResourceDao the vendorLocationResourceDao to set
	 */
	public void setVendorLocationResourceDao(VendorResourceLocationDao vendorLocationResourceDao) {
		this.vendorLocationResourceDao = vendorLocationResourceDao;
	}
}
