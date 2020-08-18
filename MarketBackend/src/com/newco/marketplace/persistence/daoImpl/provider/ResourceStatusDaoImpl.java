package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IResourceStatusDao;
import com.newco.marketplace.vo.provider.VendorResource;

public class ResourceStatusDaoImpl extends SqlMapClientDaoSupport implements IResourceStatusDao {
	
	public String getResourceStatus(VendorResource vendorResource) throws DBException{
		
		logger.info("vendorResource.getResourceId() in dao impl"
				+ vendorResource.getResourceId());
		VendorResource dbVendorResource = new VendorResource();
		String resourceStatus = null;
		try {
			resourceStatus =  (String)getSqlMapClient().queryForObject("resourceStatus.query",
							vendorResource);
			logger
			.info("---------dbVendorResource.getWfStateId()---------"+resourceStatus);
					
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @IResourceStatusDaoImpl.getResourceStatus() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @IResourceStatusDaoImpl.getResourceStatus() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @IResourceStatusDaoImpl.getResourceStatus() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @IResourceStatusDaoImpl.getResourceStatus() due to "
							+ ex.getMessage());
		}

		return resourceStatus;
	}

}