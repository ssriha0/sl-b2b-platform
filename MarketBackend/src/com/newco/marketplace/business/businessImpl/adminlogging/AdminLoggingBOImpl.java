package com.newco.marketplace.business.businessImpl.adminlogging;

import com.newco.marketplace.business.iBusiness.adminlogging.IAdminLoggingBean;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.adminLogging.IAdminLoggingDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;
import com.newco.marketplace.vo.provider.LookupVO;

public class AdminLoggingBOImpl implements IAdminLoggingBean{
	private IAdminLoggingDao adminLoggingDao;
	private ILookupDAO lookupDAO;
	public AdminLoggingBOImpl(IAdminLoggingDao adminLoggingDao, ILookupDAO lookupDAO){
		this.adminLoggingDao = adminLoggingDao;
		this.lookupDAO =lookupDAO; 
	}
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public AdminLoggingVO insertAdminLogging(AdminLoggingVO adminLoggingVO) throws BusinessServiceException{
		AdminLoggingVO dbAdminLoggingVO = new AdminLoggingVO();
		int result = 0;
		try {
			result = adminLoggingDao.insertAdminLogging(adminLoggingVO);
			dbAdminLoggingVO.setLoggId(result);
			
		} catch (DBException ex) {
//			logger
//			.info("DB Exception @AdminLoggingBOImpl.insertAdminLogging() due to"
//					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
//			logger
//			.info("General Exception @AdminLoggingBOImpl.insertAdminLogging() due to"
//					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @AdminLoggingBOImpl.insertAdminLogging() due to "
					+ ex.getMessage());
}
		
		return dbAdminLoggingVO;
	}
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public AdminLoggingVO updateAdminLogging(AdminLoggingVO adminLoggingVO) throws BusinessServiceException{
		AdminLoggingVO dbAdminLoggingVO = new AdminLoggingVO();
		int result = 0;
		try {
			result = adminLoggingDao.updateAdminLogging(adminLoggingVO);
			dbAdminLoggingVO.setLoggId(result);
		} catch (DBException ex) {
//			logger
//			.info("DB Exception @AdminLoggingBOImpl.updateAdminLogging() due to"
//					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
//			logger
//			.info("General Exception @AdminLoggingBOImpl.updateAdminLogging() due to"
//					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @AdminLoggingBOImpl.updateAdminLogging() due to "
					+ ex.getMessage());
}
		
		return dbAdminLoggingVO;
	}
	
	
	public LookupVO getActivityId(LookupVO lookupVO) throws BusinessServiceException{
		LookupVO dblookupVO = new LookupVO();
		int result = 0;
		try {
			dblookupVO = lookupDAO.getActivityId(lookupVO);
			
		} catch (DBException ex) {
//			logger
//			.info("DB Exception @AdminLoggingBOImpl.getActivityId() due to"
//					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
//			logger
//			.info("General Exception @AdminLoggingBOImpl.getActivityId() due to"
//					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @AdminLoggingBOImpl.getActivityId() due to "
					+ ex.getMessage());
}
		
		return dblookupVO;
	}
	
	
}
