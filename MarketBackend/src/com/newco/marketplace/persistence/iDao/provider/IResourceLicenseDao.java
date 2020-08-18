/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.vo.provider.ResourceLicense;

public interface IResourceLicenseDao
{
    public int update(ResourceLicense resourceLicense);
    public ResourceLicense query(ResourceLicense resourceLicense);
    public ResourceLicense insert(ResourceLicense resourceLicense);
    public List queryList(ResourceLicense vo);
    
}
