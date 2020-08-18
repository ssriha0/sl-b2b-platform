package com.newco.marketplace.persistence.iDao.adminEmails;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.vo.adminEmails.BackgroundTickleVo;

public interface IAdminEmailDao {
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public List<BackgroundTickleVo> sendBackgroundEmailComplete() throws DBException;
	/***
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public List<BackgroundTickleVo> sendBackgroundEmailInComplete() throws DBException;
	
	
	public List<BackgroundTickleVo> sendBackgroundEmailPending() throws DBException;
	
	
}
