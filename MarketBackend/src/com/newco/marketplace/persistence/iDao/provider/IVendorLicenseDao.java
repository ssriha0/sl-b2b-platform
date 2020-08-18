/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.vo.provider.VendorLicense;
/**
 * @author sahmad7
 */
public interface IVendorLicenseDao
{
    public int update(VendorLicense vendorLicense);
    public VendorLicense query(VendorLicense vendorLicense);
    public VendorLicense insert(VendorLicense vendorLicense);
    public List queryList(VendorLicense vendorLicense);
}
