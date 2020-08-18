package com.newco.marketplace.persistence.daoImpl.adminlogging;

import java.sql.SQLException;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.adminLogging.IAdminLoggingDao;
import com.newco.marketplace.vo.adminLogging.AdminLoggingVO;

public class AdminLoggingDaoImpl  extends SqlMapClientDaoSupport implements IAdminLoggingDao {
	/**
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public int insertAdminLogging(AdminLoggingVO adminLoggingVO) throws DBException{
		int result = 0;
		try
        {
			result =  (Integer) getSqlMapClient().insert("adminLogging.insert", adminLoggingVO);
			logger.info("result --- "+ result);
			logger.info("--adminLoggingVO.getLoggId()--"+adminLoggingVO.getLoggId());

        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @AdminLoggingDaoImpl.insertAdminLogging() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @AdminLoggingDaoImpl.insertAdminLogging() due to "+ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
		     logger.info("General Exception @AdminLoggingDaoImpl.insertAdminLogging() due to"+ex.getMessage());
		     throw new DBException("General Exception @AdminLoggingDaoImpl.insertAdminLogging() due to "+ex.getMessage());
       }
		
		return result;
	}
	
	/***
	 * 
	 * @param adminLoggingVO
	 * @return
	 */
	public int updateAdminLogging(AdminLoggingVO adminLoggingVO) throws DBException{
		int result = 0;
		try
        {
			result =  (Integer) getSqlMapClient().update("adminLogging.update", adminLoggingVO);

        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @AdminLoggingDaoImpl.updateAdminLogging() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @AdminLoggingDaoImpl.updateAdminLogging() due to "+ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
		     logger.info("General Exception @AdminLoggingDaoImpl.updateAdminLogging() due to"+ex.getMessage());
		     throw new DBException("General Exception @AdminLoggingDaoImpl.updateAdminLogging() due to "+ex.getMessage());
       }
		return result;
	}
}
