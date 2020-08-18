package com.newco.marketplace.business.iBusiness.adminlogging;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;
import com.newco.marketplace.vo.provider.LookupVO;

public interface IAdminLoggingBean {
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public AdminLoggingVO insertAdminLogging(AdminLoggingVO adminLoggingVO)throws BusinessServiceException;
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public AdminLoggingVO updateAdminLogging(AdminLoggingVO adminLoggingVO)throws BusinessServiceException;
	
	public LookupVO getActivityId(LookupVO lookupVO) throws BusinessServiceException;
}
