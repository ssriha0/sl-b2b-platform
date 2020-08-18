package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.provider.VendorNotesVO;

public interface IVendorNotesDao 
{
	public void update(VendorNotesVO vo) throws DataServiceException;
    
    public VendorNotesVO query(VendorNotesVO vo) throws DataServiceException;
    
    public void insert(VendorNotesVO vo) throws DataServiceException;
    
    public List queryList(VendorNotesVO vo) throws DataServiceException;
	

}
