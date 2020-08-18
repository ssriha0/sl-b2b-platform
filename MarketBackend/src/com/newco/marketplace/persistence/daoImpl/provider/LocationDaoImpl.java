package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.ResourceLocationType;

public class LocationDaoImpl extends SqlMapClientDaoSupport implements
		ILocationDao {
	private static final Logger logger = Logger.getLogger(LocationDaoImpl.class);

	// /private static final MessageResources messages =
	// MessageResources.getMessageResources("DataAccessLocalStrings");
	public int update(Location location) throws DBException {
		// return getSqlMapClient().update("location.update", location);
		int result = 0;
		try {
			result = getSqlMapClient().update("location.updateP", location);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @LocationDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LocationDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public Location query(Location location) throws DBException {
		// return (Location) getSqlMapClient().queryForObject("location.query",
		// location);
		Location result = null;
		try {
			result = (Location) getSqlMapClient().queryForObject(
					"location.queryP", location);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @LocationDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LocationDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public ResourceLocationType queryResourceLocationType(
			ResourceLocationType resourceLocationType) throws DBException {
		// return (ResourceLocationType)
		// getSqlMapClient().queryForObject("location.queryResourceLocationType",
		// resourceLocationType);
		ResourceLocationType result = null;
		try {
			result = (ResourceLocationType) getSqlMapClient().queryForObject(
					"location.queryResourceLocationTypeP", resourceLocationType);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @LocationDaoImpl.queryResourceLocationType() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.queryResourceLocationType() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @LocationDaoImpl.queryResourceLocationType() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.queryResourceLocationType() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public ResourceLocationType queryResourceWorkLocation(
			ResourceLocationType resourceLocationType) throws DBException {
		// return (ResourceLocationType)
		// getSqlMapClient().queryForObject("location.queryResourceWorkLocation",
		// resourceLocationType);
		ResourceLocationType result = null;
		try {
			result = (ResourceLocationType) getSqlMapClient().queryForObject(
					"location.queryResourceWorkLocationP", resourceLocationType);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @LocationDaoImpl.queryResourceWorkLocation() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.queryResourceWorkLocation() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @LocationDaoImpl.queryResourceWorkLocation() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.queryResourceWorkLocation() due to "
							+ ex.getMessage());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.provider.registration.dao.LocationDao#insert(com.newco.provider.registration.dao.Location)
	 */
	public Location insert(Location location) throws DBException {
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient()
					.insert("location.insertP", location);

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @LocationDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LocationDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.insert() due to "
							+ ex.getMessage());
		}

		location.setLocnId(id);
		return location;

	}// end method

	public List queryList(Location location) throws DBException {
		// return getSqlMapClient().queryForList("location.query", location);
		List result = null;
		try {
			result = getSqlMapClient().queryForList("location.queryP", location);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @LocationDaoImpl.queryList() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LocationDaoImpl.queryList() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.queryList() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public Location locationIdInsert(Location location) throws DBException {
		Integer id = null;

		try {

			id = (Integer) getSqlMapClient().insert("locationId.insertP",
					location);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @LocationDaoImpl.locationIdInsert() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.locationIdInsert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @LocationDaoImpl.locationIdInsert() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.locationIdInsert() due to "
							+ ex.getMessage());
		}

		System.out.println("here is the contact id number (" + id + ")");

		location.setLocnId(id.intValue());
		return location;

	}
	
	 /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#get(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException {
		try {
			generalInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("generalInfo.location.get", generalInfoVO);
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @LocationDaoImpl.get() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @LocationDaoImpl.get() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @LocationDaoImpl.get() due to"+ex.getMessage());
		     throw new DBException("General Exception @LocationDaoImpl.get() due to "+ex.getMessage());
        }

        return generalInfoVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#insert(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;

        try {
            id = (Integer)getSqlMapClient().insert("generalInfo.location.insert", generalInfoVO);
            generalInfoVO.setLocationId(id.intValue());
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @LocationDaoImpl.insert() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @LocationDaoImpl.insert() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @LocationDaoImpl.insert() due to"+ex.getMessage());
		     throw new DBException("General Exception @LocationDaoImpl.insert() due to "+ex.getMessage());
        }

        return generalInfoVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#update(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public int update(GeneralInfoVO generalInfoVO) throws DBException {
		 int result = 0;

	        try {
	            result = getSqlMapClient().update("generalInfo.location.update", generalInfoVO);

	        } catch (SQLException ex) {
				logger.info("SQL Exception @LocationDaoImpl.update() due to"
						+ ex.getMessage());
				throw new DBException(
						"SQL Exception @LocationDaoImpl.update() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				logger
						.info("General Exception @LocationDaoImpl.update() due to"
								+ ex.getMessage());
				throw new DBException(
						"General Exception @LocationDaoImpl.update() due to "
								+ ex.getMessage());
			}
		return result;
	}
	/**
	 * 
	 * @param location
	 * @return
	 * @throws DBException
	 */
	public Location queryVendorLocation(int vendorId) throws DBException {
		// return (Location) getSqlMapClient().queryForObject("location.query",
		// location);
		Location result = null;
		try {
			result = (Location) getSqlMapClient().queryForObject(
					"vendorlocation.queryP", vendorId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @LocationDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @LocationDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LocationDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @LocationDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;
	}
}