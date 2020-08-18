package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ledger.ReceivedAmountVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author zizrale
 *
 */
public interface ProviderDao
{    
    /**
     * @param vendorInfo
     * @return ReceivedAmountVO
     * @throws DataServiceException
     */
    public ReceivedAmountVO getReceivedAmount(AjaxCacheVO vendorInfo) throws DataServiceException;
    
    public void updateVendorInfoCache() throws DataServiceException;
    
    
    /**
     * Update vendor KPI
     */
    public void updateVendorKPI() throws DataServiceException;
}
