package com.newco.marketplace.web.delegates.adminlogging;

import com.newco.marketplace.web.dto.adminlogging.AdminLoggingDto;

public interface IAdminLoggingDelegate {
	/**
	 * 
	 * @param adminLoggingDto
	 * @return
	 */
	public AdminLoggingDto insertAdminLogging(AdminLoggingDto  adminLoggingDto);
	/***
	 * 
	 * @param adminLoggingDto
	 * @return
	 */
	public AdminLoggingDto updateAdminLogging(AdminLoggingDto adminLoggingDto);
	/**
	 * 
	 * @param adminLoggingDto
	 * @return
	 */
	public AdminLoggingDto getActivityId(AdminLoggingDto adminLoggingDto);
}
