/*
 ** VendorContactLocationDaoImpl.java    v1.0    Jun 14, 2007
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IResourceLocationDao;
import com.newco.marketplace.vo.provider.ResourceLocation;

/**
 * Spring/Ibatis implementation of IVendorContactLocationDao
 * 
 * @version
 * @author blars04
 * 
 */
public class ResourceLocationDaoImpl extends SqlMapClientDaoSupport implements
		IResourceLocationDao {
	private static final Logger logger = Logger.getLogger(ResourceLocationDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.provider.registration.dao.ResourceLocation#insert(com.newco.provider.registration.dao.resourceLocation)
	 */
	public ResourceLocation insert(ResourceLocation resourceLocation)
			throws DBException {
		try {
			resourceLocation = (ResourceLocation) getSqlMapClient().insert(
					"resourceLocation.insert", resourceLocation);

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceLocationDaoImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ResourceLocationDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ResourceLocationDaoImpl.update() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ResourceLocationDaoImpl.insert() due to "
							+ ex.getMessage());
		}

		return resourceLocation;

	}// end method insert(VendorContactLocation)

}// end class
