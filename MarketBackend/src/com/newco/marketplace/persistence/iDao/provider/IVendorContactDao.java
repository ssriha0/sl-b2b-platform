/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.provider.VendorContact;

/**
 * @author sahmad7
 */
public interface IVendorContactDao
{
    /**
     * 
     * @param vendorPrincipal
     * @return
     * @throws DataServiceException
     */
    public int update(VendorContact vendorPrincipal)throws DBException ;
    
    
    /**
     * 
     * @param vendorContact
     * @return
     * @throws DataServiceException
     */
    public VendorContact queryForResource(VendorContact vendorContact)throws DBException;
    
    /**
     * 
     * @param vendorContact
     * @return
     * @throws DataServiceException
     */
    public VendorContact query(VendorContact vendorContact)throws DBException;
    
    /**
     * Insert Vendor Contact into datastore
     * 
     * @param vendorPrincipal
     * @return
     * @throws DataServiceException
     */
    public VendorContact insert(VendorContact vendorContact)throws DBException;
    public List queryList(VendorContact vendorPrincipal)throws DBException;
    public VendorContact queryVendorContactId(VendorContact vendorContact)throws DBException;
    /**
     * Updates only the ResourceId and derives the resourceIndicator for a VendorContact
     * 
     * @param vendorContact
     * @return an int for the number of rows affected
     * @throws DataServiceException
     */
    public int updateResource(VendorContact vendorContact)throws DBException;
    
    
}//end interface