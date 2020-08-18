package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IContactMethodPrefDao;
import com.newco.marketplace.vo.provider.ContactMethodPref;


public class ContactMethodPrefImpl extends SqlMapClientDaoSupport implements
		IContactMethodPrefDao {
	private static final Logger logger = Logger.getLogger(ContactMethodPrefImpl.class);

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IContactMethodPrefDao#update(com.newco.marketplace.vo.ContactMethodPref)
	 */
	public int update(ContactMethodPref contactMethodPref) throws DBException {
		// return update("contact_method_preference.update", contactMethodPref);
		int result = 0;

		try {
			result = getSqlMapClient().update(
					"contact_method_preference.update", contactMethodPref);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @ContactMethodPrefImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ContactMethodPrefImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ContactMethodPrefImpl.update() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ContactMethodPrefImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IContactMethodPrefDao#query(com.newco.marketplace.vo.ContactMethodPref)
	 */
	public ContactMethodPref query(ContactMethodPref contactMethodPref)
			throws DBException {
		// return (ContactMethodPref)
		// queryForObject("contact_method_preference.query", contactMethodPref);
		ContactMethodPref result = null;

		try {
			result = (ContactMethodPref) getSqlMapClient().queryForObject(
					"contact_method_preference.query", contactMethodPref);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @ContactMethodPrefImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ContactMethodPrefImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ContactMethodPrefImpl.query() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ContactMethodPrefImpl.query() due to "
							+ ex.getMessage());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IContactMethodPrefDao#insert(com.newco.marketplace.vo.ContactMethodPref)
	 */
	public ContactMethodPref insert(ContactMethodPref contactMethodPref)
			throws DBException {
		// return (ContactMethodPref) insert("contact_method_preference.insert",
		// contactMethodPref);
		ContactMethodPref result = null;

		try {
			result = (ContactMethodPref) getSqlMapClient().insert(
					"contact_method_preference.insert", contactMethodPref);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @ContactMethodPrefImpl.insert() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ContactMethodPrefImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ContactMethodPrefImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ContactMethodPrefImpl.insert() due to "
							+ ex.getMessage());
		}
		return result;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IContactMethodPrefDao#queryList(com.newco.marketplace.vo.ContactMethodPref)
	 */
	public List queryList(ContactMethodPref contactMethodPref) throws DBException{
		// return queryForList("contact_method_preference.query",
		// contactMethodPref);
		List result = null;

		try {
			result = getSqlMapClient().queryForList(
					"contact_method_preference.query", contactMethodPref);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @ContactMethodPrefImpl.queryList() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ContactMethodPrefImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ContactMethodPrefImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ContactMethodPrefImpl.queryList() due to "
							+ ex.getMessage());
		}
		return result;
	}
}
