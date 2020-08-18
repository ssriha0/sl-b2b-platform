package com.newco.marketplace.persistence.daoImpl.provider;


import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ledger.ReceivedAmountVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.ProviderDao;
import com.sears.os.dao.impl.ABaseImplDao;


/**
 * @author zizrale
 */
public class ProviderDaoImpl extends ABaseImplDao implements ProviderDao {
	
	/** (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.ledger.TransactionDao#getReceivedAmount(int)
	 */
	public ReceivedAmountVO getReceivedAmount(AjaxCacheVO vendorInfo) throws DataServiceException{
		return (ReceivedAmountVO)this.queryForObject("received_amt.query", vendorInfo);
	}

	/**
	 * Runs batch job to update vendor_info_cache
	 */
	public void updateVendorInfoCache() throws DataServiceException {
		try {
			queryForList("provider.updateVendorInfoCache");
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Runs batch job to update vendor_kpi
	 */
	public void updateVendorKPI() throws DataServiceException {
		try {
			queryForList("provider.updateVendorKPI");
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(), e);
		}
	}
}