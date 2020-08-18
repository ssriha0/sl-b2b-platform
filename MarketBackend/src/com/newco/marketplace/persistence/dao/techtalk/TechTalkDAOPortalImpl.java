package com.newco.marketplace.persistence.dao.techtalk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class TechTalkDAOPortalImpl extends ABaseImplDao implements ITechTalkPortalDAO {

private static final Logger logger = Logger.getLogger(TechTalkDAOPortalImpl.class.getName());
	
	public String fetchDispositionCode(String orderID)  throws DataServiceException {
		String depositionCode = null;
		try{
			depositionCode = (String) queryForObject("fetchDepositionCode.query", orderID);
			return depositionCode;
		}
		catch (Exception e) {
			logger.info("[Exception thrown while fetching depositioncode] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);
		}	
	}
	
	public List<DepositionCodeDTO> fetchDispositionCode()throws DataServiceException{
		List<DepositionCodeDTO> depositionCodes = null;
		try{
			depositionCodes = (List<DepositionCodeDTO>) queryForList("fetchAllDepositionCodes.query");
		}
		catch (Exception e) {
			logger.info("[Exception thrown while fetching depositioncode] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);
		}	
		return depositionCodes;
	}
	
	public boolean insertOrUpdateDispositionCode(String depositionCode,String soID)throws DataServiceException{
		try{
			Map<String,String> params = new HashMap<String,String>();
			params.put("orderID", soID);
			params.put("depositionCode", depositionCode);
			insert("depositionCode.insertOrUpdate", params);
		}
		catch (Exception e) {
			logger.info("[Exception thrown while fetching depositioncode] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);
		}	
		return true;
	}
}
