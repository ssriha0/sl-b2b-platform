/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.VendorPolicy;

public interface IVendorPolicyDao {
	public int update(VendorPolicy vendorPolicy) throws DBException;

	public VendorPolicy query(VendorPolicy vendorPolicy) throws DBException;

	public void insert(VendorPolicy vendorPolicy) throws DBException;

	public List queryList(VendorPolicy vo) throws DBException;

}
