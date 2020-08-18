package com.newco.marketplace.persistence.iDao.location;

import com.newco.marketplace.dto.vo.provider.ProviderLocationVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.core.DataServiceException;

public interface LocationDao {

    /**
     * 
     * @param vendorResource
     * @return
     * @throws DataServiceException
     */
    public int updateLocation(ProviderLocationVO locationVO) throws DataServiceException;
    
    public ProviderLocationVO retrieveLocation(VendorResource vendorResource)throws DataServiceException;
   
   
}
