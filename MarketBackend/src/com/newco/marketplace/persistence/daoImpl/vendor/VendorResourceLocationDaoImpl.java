package com.newco.marketplace.persistence.daoImpl.vendor;

import com.newco.marketplace.persistence.iDao.vendor.VendorResourceLocationDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class VendorResourceLocationDaoImpl extends ABaseImplDao implements VendorResourceLocationDao {
	/**
	 * 
	 * This should delete every record in the table
	 * 
	 * @return int
	 */
	public int clearTable(){
		return update("vendorresourcelocation.all.delete", null);
	}

	/**
	 * This will create all the records for this table.
	 */
	public void populateTable(){
		insert("vendorresourcelocation.all.insert", null);
	}

	/**
	 * 
	 * This should delete every record in the table vendor_resource_service_area.
	 * 
	 * @return int
	 */
	public int clearServiceAreaTable() {
		return update("vendorresourceservicearea.all.delete", null);
	}

	/**
	 * This will create all the records for this table vendor_resource_service_area.
	 */
	public void populateServiceAreaTable() {
		insert("vendorresourceservicearea.all.insert", null);
	}
}
