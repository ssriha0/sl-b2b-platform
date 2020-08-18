package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;

public class ZipDaoImpl extends SqlMapClientDaoSupport implements IZipDao {

	public List queryList(String id) {
		List retList = null;
		try
		{
			retList = (List) getSqlMapClient().queryForList("zip.query", id);
		}catch (SQLException ex) {
	        ex.printStackTrace();
		     logger.info("SQL Exception @ZipDaoImpl.query() due to"+ex.getMessage());
	   }catch (Exception ex) {
	       ex.printStackTrace();
		     logger.info("General Exception @ZipDaoImpl.query() due to"+ex.getMessage());
	  }
	   return retList;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IZipDao#queryZipCount(java.lang.String)
	 */
	public int queryZipCount(String zip) {
		Integer count = 0;
		try
		{
			count = (Integer) getSqlMapClient().queryForObject("zip_count.query", zip);
		}catch (SQLException ex) {
	        ex.printStackTrace();
		     logger.info("SQL Exception @ZipDaoImpl.query() due to"+ex.getMessage());
	   }catch (Exception ex) {
	       ex.printStackTrace();
		     logger.info("General Exception @ZipDaoImpl.query() due to"+ex.getMessage());
	  }
	   return count.intValue();
	}
	
	@Override
	public List<StateZipcodeVO> queryStateCdAndZip(List<String> zipList) throws DataAccessException {
		List<StateZipcodeVO> stateNameList = new ArrayList<StateZipcodeVO>();
		try
		{	
			getSqlMapClient().update("updateGroupConcatLength.query", null);
			stateNameList = (ArrayList<StateZipcodeVO>) getSqlMapClient().queryForList("state_name.query", zipList);
		
	   }catch (Exception ex) {
	       ex.printStackTrace();
		     logger.info("General Exception @ZipDaoImpl.query() due to"+ex.getMessage());
	  }
	   return stateNameList;
	}

}
