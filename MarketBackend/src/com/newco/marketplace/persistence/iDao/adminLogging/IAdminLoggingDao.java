package com.newco.marketplace.persistence.iDao.adminLogging;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;

public interface IAdminLoggingDao {
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public int insertAdminLogging(AdminLoggingVO adminLoggingVO) throws DBException;
	/***
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public int updateAdminLogging(AdminLoggingVO adminLoggingVO) throws DBException;
}
