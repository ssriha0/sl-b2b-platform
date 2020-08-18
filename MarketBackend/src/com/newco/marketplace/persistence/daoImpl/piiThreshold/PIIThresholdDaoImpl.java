package com.newco.marketplace.persistence.daoImpl.piiThreshold;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.PIIThresholdVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.piiThreshold.IPIIThresholdDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class PIIThresholdDaoImpl extends ABaseImplDao implements IPIIThresholdDao {

	private static final Logger logger = Logger.getLogger(PIIThresholdDaoImpl.class);
	
	// Fetch buyer bitFlag and threshold limit
	public PIIThresholdVO getThreshold(String role) throws DataServiceException {
		return (PIIThresholdVO) queryForObject( "get_pii_threshold_information.query", role);
	}

}
