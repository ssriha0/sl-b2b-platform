package com.newco.marketplace.persistence.iDao.piiThreshold;

import com.newco.marketplace.dto.vo.PIIThresholdVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IPIIThresholdDao {
	
	// Fetch buyer bitFlag and threshold limit
	public PIIThresholdVO getThreshold(String role)throws DataServiceException;
}
