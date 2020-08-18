/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IVendorLocationDao;
import com.newco.marketplace.vo.provider.VendorLocation;

/**
 * @author sahmad7
 */
public class VendorLocationDaoImpl extends SqlMapClientDaoSupport implements
		IVendorLocationDao {
	private static final Logger localLogger = Logger.getLogger(ContactDaoImpl.class);

	public int update(VendorLocation vendorLocation) throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update("vendor_location.update",
					vendorLocation);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger
					.info("SQL Exception @VendorLocationDaoImpl.update() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorLocationDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("General Exception @VendorLocationDaoImpl.update() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorLocationDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;

		// return getSqlMapClient().update("vendor_location.update",
		// vendorLocation);
	}

	public VendorLocation query(VendorLocation vendorLocation)
			throws DBException {

		VendorLocation result = null;
		try {
			result = (VendorLocation) getSqlMapClient().queryForObject(
					"vendor_location.query", vendorLocation);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger
					.info("SQL Exception @VendorLocationDaoImpl.query() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorLocationDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("General Exception @VendorLocationDaoImpl.query() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorLocationDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;

	}

	public VendorLocation insert(VendorLocation vendorLocation)
			throws DBException {

		VendorLocation result = null;
		try {
			result = (VendorLocation) getSqlMapClient().insert(
					"vendor_location.insert", vendorLocation);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger
					.info("SQL Exception @VendorLocationDaoImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorLocationDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("General Exception @VendorLocationDaoImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorLocationDaoImpl.insert() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public List queryList(VendorLocation vendorLocation) throws DBException {
		List result = null;
		try {
			result = getSqlMapClient().queryForList("vendor_location.query",
					vendorLocation);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger
					.info("SQL Exception @VendorLocationDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorLocationDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger
					.info("General Exception @VendorLocationDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorLocationDaoImpl.queryList() due to "
							+ ex.getMessage());
		}
		return result;

	}
}
