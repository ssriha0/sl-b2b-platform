package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IResourceDao;
import com.newco.marketplace.vo.provider.ResourceVO;

public class ResourceDaoImpl extends SqlMapClientDaoSupport implements IResourceDao {
	
	private static final Logger logger = Logger.getLogger(ResourceDaoImpl.class);

	public ResourceVO getResourceName(ResourceVO resourceVO) throws DBException {
		try {
			return (ResourceVO)getSqlMapClient().queryForObject("getResource.FullName.query", resourceVO);
		} catch (SQLException ex) {
			logger.error("SQL Exception @SkillAssignDAOImpl.getResourceName() due to SQLException", ex);
			throw new DBException("SQL Exception @ResourceDaoImpl.getResourceName() due to SQLException", ex);
		} catch (Exception ex) {
			logger.error("General Exception @ResourceDaoImpl.getResourceName() due to Exception", ex);
			throw new DBException("General Exception @ResourceDaoImpl.getResourceName() due to Exception", ex);
		}
	}

	/**
	 * Fetch vendorId for a particular resourceId
	 * @param resourceId
	 * @return vendorId
	 * @throws DBException
	 */
	public Integer getVendorId(String resourceId) throws DBException {
		Integer vendorId;
		try {
			vendorId = (Integer)getSqlMapClient().queryForObject("getResource.vendorId.query", resourceId);
		} catch (SQLException ex) {
			logger.error("SQL Exception @ResourceDaoImpl.getVendorId() due to SQLException", ex);
			throw new DBException("SQL Exception @ResourceDaoImpl.getVendorId() due to SQLException", ex);
		} catch (Exception ex) {
			logger.error("General Exception @ResourceDaoImpl.getVendorId() due to Exception", ex);
			throw new DBException("General Exception @ResourceDaoImpl.getVendorId() due to Exception", ex);
		}
		return vendorId;
	}	

}
