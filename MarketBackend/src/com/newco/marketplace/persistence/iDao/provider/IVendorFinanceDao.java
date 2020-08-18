/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.FinanceProfile;
import com.newco.marketplace.vo.provider.VendorFinance;



public interface IVendorFinanceDao
{
    public int update(VendorFinance vendorFinance) throws DBException;
    
    public VendorFinance query(VendorFinance vendorFinance)
	    throws DBException;
    
    public VendorFinance insert(FinanceProfile vendorFinance)
	    throws DBException;
    
    public List queryList(VendorFinance vo) throws DBException;
    
}
