/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.common;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.common.IInterimPasswordDao;
import com.newco.marketplace.vo.common.InterimPasswordVO;

/**
 * $Revision: 1.0 $ $Author: Shekhar Nirkhe $ $Date: 2009/02/03 12:05:50 $
 */
public class InterimPasswordDaoImpl extends SqlMapClientDaoSupport implements
            IInterimPasswordDao {

    private static final Logger logger = Logger.getLogger(IInterimPasswordDao.class);

    public InterimPasswordVO getUserFromInterimPassword(InterimPasswordVO interimPasswordVO)throws DBException {
        try {
            return (InterimPasswordVO) getSqlMapClient().queryForObject("interim_password.query", interimPasswordVO);
        }
        catch (SQLException ex) {
		     logger.info("SQL Exception @InterimPasswordDaoImpl.getUserName()", ex);
		     throw new DBException("SQL Exception @InterimPasswordDaoImpl.getUserName()", ex);
        }catch (Exception ex) {
		     logger.info("General Exception @InterimPasswordDaoImpl.getUserName()", ex);
		     throw new DBException("General Exception @InterimPasswordDaoImpl.getUserName()", ex);
       }
    }

    
    public InterimPasswordVO resetPassword(String userName) throws DBException {
    	InterimPasswordVO interimPassword = null;    
    	InterimPasswordVO interimPasswordTmp = InterimPasswordVO.createNewInstance(userName, null);  
    	try{
    		interimPassword = 
    			(InterimPasswordVO)getSqlMapClient().queryForObject("interim_password_get_pwd.query", userName);    		
    		if (interimPassword == null) {    			
    			interimPassword = interimPasswordTmp;
    			getSqlMapClient().insert("interim_password.insert", interimPassword);
    		} else {
    			//update the timestamp
    			//Defect SL-6886
    			interimPassword.setPlainTextPassword(interimPasswordTmp.getPlainTextPassword());
    			getSqlMapClient().insert("interim_password.disable", interimPassword.getPassword());
    			getSqlMapClient().insert("interim_password.enable", interimPassword.getPassword());  
    		}
    		return interimPassword;
    	}catch (SQLException ex) {
            logger.info("SQL Exception @InterimPasswordDaoImpl.resetPassword()", ex);
		     throw new DBException("SQL Exception @InterimPasswordDaoImpl.resetPassword()", ex);
       }catch (Exception ex) {
		     logger.info("General Exception @InterimPasswordDaoImpl.resetPassword()", ex);
		     throw new DBException("General Exception @InterimPasswordDaoImpl.resetPassword()", ex);
      }
    }
    
    public void invalidatePassword(String interimPassword) throws DBException {
    	try{
    		getSqlMapClient().insert("interim_password.disable", interimPassword);    		 
    	}catch (SQLException ex) {
            logger.info("SQL Exception @InterimPasswordDaoImpl.invalidatePassword()", ex);
		     throw new DBException("SQL Exception @InterimPasswordDaoImpl.invalidatePassword()", ex);
       }catch (Exception ex) {
		     logger.info("General Exception @InterimPasswordDaoImpl.invalidatePassword()", ex);
		     throw new DBException("General Exception @InterimPasswordDaoImpl.invalidatePassword()", ex);
      }
    }
  
}
