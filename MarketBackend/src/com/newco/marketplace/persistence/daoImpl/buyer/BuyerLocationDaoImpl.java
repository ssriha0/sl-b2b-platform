package com.newco.marketplace.persistence.daoImpl.buyer;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.daoImpl.provider.ContactDaoImpl;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerLocationDao;
import com.newco.marketplace.vo.buyer.BuyerLocation;

public class BuyerLocationDaoImpl extends SqlMapClientDaoSupport implements
		IBuyerLocationDao {
	private static final Logger localLogger = Logger.getLogger(ContactDaoImpl.class);
				
	public int update(BuyerLocation buyerLocation) throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update("vendor_location.update",
					buyerLocation);
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

	public BuyerLocation query(BuyerLocation buyerLocation)
			throws DBException {

		BuyerLocation result = null;
		try {
			result = (BuyerLocation) getSqlMapClient().queryForObject(
					"vendor_location.query", buyerLocation);
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

	public BuyerLocation insert(BuyerLocation buyerLocation)
			throws DBException {

		BuyerLocation result = null;
		try {
			result = (BuyerLocation) getSqlMapClient().insert(
					"vendor_location.insert", buyerLocation);
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

	public List queryList(BuyerLocation buyerLocation) throws DBException {
		List result = null;
		try {
			result = getSqlMapClient().queryForList("vendor_location.query",
					buyerLocation);
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
