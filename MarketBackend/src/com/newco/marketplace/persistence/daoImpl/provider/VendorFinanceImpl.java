/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IVendorFinanceDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.FinanceProfile;
import com.newco.marketplace.vo.provider.VendorFinance;



public class VendorFinanceImpl extends SqlMapClientDaoSupport implements
		IVendorFinanceDao {

	public int update(VendorFinance vendorFinance) throws DBException {
		try {
			return getSqlMapClient().update("vendorFinance.update",
					vendorFinance);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorFinanceImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException( 
					"SQL Exception @VendorFinanceImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorFinanceImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorFinanceImpl.update() due to "
							+ ex.getMessage());
		}

	}

	public VendorFinance query(VendorFinance vendorFinance) throws DBException {
		try {
			return (VendorFinance) getSqlMapClient().queryForObject(
					"vendorFinance.query", vendorFinance);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorFinanceImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorFinanceImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorFinanceImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorFinanceImpl.query() due to "
							+ ex.getMessage());
		}
		
	}

	public VendorFinance insert(FinanceProfile vendorFinance) throws DBException {
		try {
			return (VendorFinance) getSqlMapClient().insert(
					"FinanceProfile.insert", vendorFinance);

		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorFinanceImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorFinanceImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorFinanceImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorFinanceImpl.insert() due to "
							+ ex.getMessage());
		}

	}

	public List queryList(VendorFinance vendorFinance) throws DBException {
		try {
			return getSqlMapClient().queryForList("vendorFinance.query",
					vendorFinance);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorFinanceImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorFinanceImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorFinanceImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorFinanceImpl.queryList() due to "
							+ ex.getMessage());
		}

	}
}
