package com.newco.marketplace.persistence.daoImpl.logging;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.dto.vo.logging.SoAutoCloseDetailVo;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.logging.SoChangeTypeVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo2;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.logging.ILoggingDao;

/**
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 00:40:36 $
 */
public class LoggingDaoImpl extends MPBaseDaoImpl implements ILoggingDao {

	private static final Logger logger = Logger.getLogger(LoggingDaoImpl.class
			.getName());

	public void insertLog(SoLoggingVo soLoggingVo) throws DataServiceException {

		try {
			this.insert("soLogging.insert", soLoggingVo);
		} catch (Exception ex) {
			final String error = getMessages().getMessage("dataaccess.insert");
			throw new DataServiceException(error, ex);
		}
	}// insertSO ()

	public List<SoChangeTypeVo> getSOChangeTypes() throws DataServiceException {

		List<SoChangeTypeVo> ctList = new ArrayList<SoChangeTypeVo>();
		try {
			ctList = queryForList("SOLogging.getSOChangeTypes", null);
		} catch (DataAccessException ex) {

			final String error = getMessages().getMessage("dataaccess.insert");
			throw new DataServiceException(error, ex);
		}
		return (ctList);
	}// getSOChangeTypes()

	public List<SoChangeDetailVo> getSOChangeDetail(String soID)
			throws DataServiceException {
		List<SoChangeDetailVo> list = new ArrayList<SoChangeDetailVo>();
		try {

			list = queryForList("so.getSOChangeDetails", soID);
		} catch (Exception ex) {
			logger.info("Caught Exception and ignoring",ex);
		}
		return list;
	}
	
	public List<SoAutoCloseDetailVo> getSoAutoCloseCompletionList(String soID)
	throws DataServiceException {
		List<SoAutoCloseDetailVo> list = new ArrayList<SoAutoCloseDetailVo>();
		try {

	list = queryForList("so.getSOAutoCloseInformation", soID);
		} catch (Exception ex) {
	logger.info("Caught Exception and ignoring",ex);
		}
		return list;
	}

	public List<SoLoggingVo> getSoLogDetails(String soID)
			throws DataServiceException {
		List<SoLoggingVo> list = new ArrayList<SoLoggingVo>();
		try {

			list = queryForList("so.getLoggingDetails", soID);
		} catch (Exception ex) {
			logger.info("Caught Exception and ignoring", ex);
		}
		return list;
	}
	
	public List<SoLoggingVo> getSoRescheduleLogDetails(String soID)
			throws DataServiceException {
		List<SoLoggingVo> list = new ArrayList<SoLoggingVo>();
		try {

			list = queryForList("so.getRescheduleLoggingDetails", soID);
		} catch (Exception ex) {
			logger.info("Caught Exception and ignoring", ex);
		}
		return list;
	}
	
	public String getBuyerUserName(Integer roleId, Integer resourceId)
			throws DataServiceException {
		String userName = null;
		try {

			userName = (String) queryForObject("so.getBuyerUserName",
					resourceId);
		} catch (Exception ex) {
			logger.info("Caught Exception and ignoring", ex);
		}
		return userName;
	}
	
	
	public String getProviderUserName(Integer roleId, Integer resourceId)
			throws DataServiceException {
		String userName = null;
		try {

			userName = (String) queryForObject("so.getProviderUserName",
					resourceId);
		} catch (Exception ex) {
			logger.info("Caught Exception and ignoring", ex);
		}
		return userName;
	}
	
	public List<SoLoggingVo2> getSoRescheduleLogDetailsAnyRoles(String soID)
			throws DataServiceException {
		List<SoLoggingVo2> list = new ArrayList<SoLoggingVo2>();
		try {

			list = queryForList("so.getRescheduleLoggingDetailsAnyRoles", soID);
		} catch (Exception ex) {
			logger.info("getSoRescheduleLogDetailsAnyRoles:Caught Exception and ignoring", ex);
		}
		return list;
	}

}
