/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.VendorLocation;
/**
 * @author sahmad7
 */
public interface IVendorLocationDao
{
    public int update(VendorLocation vendorLocation) throws DBException;
    public VendorLocation query(VendorLocation vendorLocation)throws DBException;
    public VendorLocation insert(VendorLocation vendorLocation)throws DBException;
    public List queryList(VendorLocation vendorLocation)throws DBException;
}
