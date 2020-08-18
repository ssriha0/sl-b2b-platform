package com.newco.marketplace.persistence.daoImpl.adminEmails;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.adminEmails.IAdminEmailDao;
import com.newco.marketplace.persistence.vo.adminEmails.BackgroundTickleVo;

public class AdminEmailDaoImpl  extends SqlMapClientDaoSupport implements IAdminEmailDao {
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.adminEmails.IAdminEmailDao#sendBackgroundEmailComplete()
	 */
	public List<BackgroundTickleVo> sendBackgroundEmailComplete()
			throws DBException {
		// TODO Auto-generated method stub
		List<BackgroundTickleVo> result = null;
		
		try
        {
			result =  (List<BackgroundTickleVo> ) getSqlMapClient().queryForList("adminEmail.sendBackgroundEmailComplete",null);
			return result;
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.adminEmails.IAdminEmailDao#sendBackgroundEmailInComplete()
	 */
	public List<BackgroundTickleVo> sendBackgroundEmailInComplete()
			throws DBException {
		// TODO Auto-generated method stub
		List<BackgroundTickleVo> result = null;
		
		try
        {
			result =  (List<BackgroundTickleVo> ) getSqlMapClient().queryForList("adminEmail.sendBackgroundEmailInComplete",null);
			return result;
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.adminEmails.IAdminEmailDao#sendBackgroundEmailPending()
	 */
	public List<BackgroundTickleVo> sendBackgroundEmailPending()
			throws DBException {
		// TODO Auto-generated method stub
		List<BackgroundTickleVo> result = null;
		
		try
        {
			result =  (List<BackgroundTickleVo> ) getSqlMapClient().queryForList("adminEmail.sendBackgroundEmailPending",null);
			return result;
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }	}
	
}
