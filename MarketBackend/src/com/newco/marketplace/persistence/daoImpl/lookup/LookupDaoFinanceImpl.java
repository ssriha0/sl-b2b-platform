package com.newco.marketplace.persistence.daoImpl.lookup;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.lookup.LookupDaoFinance;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;


public class LookupDaoFinanceImpl extends ABaseImplDao implements LookupDaoFinance {
	private static final Logger logger = Logger.getLogger(LookupDaoFinanceImpl.class
			.getName());

	

	public ArrayList<LookupVO> getAccountTypeList() throws DataServiceException{
		ArrayList<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.accounttype", null);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getAccountTypeList() - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"LookupDao - lookup.getAccountTypeList() query failed", ex);

		}
		return al;
	}
	
	public ArrayList<LookupVO> getCreditCardTypeList() throws DataServiceException{
		ArrayList<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.creditcardtype", null);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getcreditcardTypeList() - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"LookupDao - lookup.getcreditcardTypeList() query failed", ex);

		}
		return al;
	}
	
	public ArrayList<LookupVO> getTransferReasonCodeList() throws DataServiceException{
		ArrayList<LookupVO> al = null;

		try {
			al = (ArrayList) queryForList("lookup.reasoncodes", null);
		} catch (Exception ex) {
			logger.info("[LookupDaoImpl.getTransferReasonCodeList() - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"LookupDao - lookup.getTransferReasonCodeList() query failed", ex);

		}
		return al;
	}
	
	public int getPostedStatus(String soId) {
		int postedStatus = (Integer) queryForObject("getPostedStatus.query", soId);
		return postedStatus; 
	}
}
