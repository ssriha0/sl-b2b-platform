package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.provider.VendorCredentialsLookupVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.exception.DataServiceException;

public interface IVendorCredentialsDao {
	public List queryAllType() throws DataServiceException;  
    public List queryCatByTypeId(int typeId) throws DataServiceException;
    public List queryCredByVendorId(int vendorId) throws DataServiceException;
    public VendorCredentialsVO queryCredById(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException;
    public VendorCredentialsVO insert(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException;
    public void update(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException;
    public VendorCredentialsVO queryByCredIdSec(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException;
    public void updateWfStateId(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException;
    public Integer queryForCredExists(VendorCredentialsVO vendorCredentialsVO) throws DataServiceException;
    public List queryCatByType(String type) throws DataServiceException;
	public VendorCredentialsLookupVO getVendorCredLookup(String credDesc) throws DataServiceException;
}
