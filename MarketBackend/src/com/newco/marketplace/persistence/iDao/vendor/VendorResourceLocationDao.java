package com.newco.marketplace.persistence.iDao.vendor;

/**
 * 
 * @author svanloon
 *
 */
public interface VendorResourceLocationDao {

	/**
	 * @return int
	 */
	public int clearTable();

	/**
	 * 
	 */
	public void populateTable();
	
	public int clearServiceAreaTable();

	public void populateServiceAreaTable();

}
