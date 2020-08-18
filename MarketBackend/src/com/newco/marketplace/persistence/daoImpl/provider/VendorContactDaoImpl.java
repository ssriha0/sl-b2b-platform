/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IVendorContactDao;
import com.newco.marketplace.vo.provider.VendorContact;

/**
 * @author sahmad7
 */
public class VendorContactDaoImpl extends SqlMapClientDaoSupport implements
		IVendorContactDao {
	private static final Logger logger = Logger.getLogger(VendorContactDaoImpl.class);

	public int update(VendorContact vendorContact) throws DBException {
		// logger.debug("Updating vendor contact");
		int result = 0;
		try {
			result = getSqlMapClient().update("vendor_contacts.update",
					vendorContact);

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorContactDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.update() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
	}// end method update()

	public VendorContact queryForResource(VendorContact vendorContact)
			throws DBException {
		// logger.debug("Querying vendor contact for resource(" +
		// vendorContact.toString() + ").");
		VendorContact result = null;
		try {
			result = (VendorContact) getSqlMapClient().queryForObject(
					"vendor_contactsForResource.query", vendorContact);

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorContactDaoImpl.queryForResource() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.queryForResource() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.queryForResource() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.queryForResource() due to "
							+ ex.getMessage());
		}
		return result;
	}// end query(VendorContact)

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.newco.provider.registration.dao.IVendorContactDao#query(com.newco.provider.registration.dao.VendorContact)
	 */
	public VendorContact query(VendorContact vendorContact) throws DBException {
		// logger.debug("Querying vendor contact(" + vendorContact.toString() +
		// ").");
		VendorContact result = null;
		try {
			result = (VendorContact) getSqlMapClient().queryForObject(
					"vendor_contacts.query", vendorContact);

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorContactDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.query() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;
	}// end query(VendorContact)

	/*
	 * 
	 */
	public VendorContact insert(VendorContact vendorContact) throws DBException {
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient().insert("vendor_contacts.insert",
					vendorContact);

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorContactDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.insert() due to "
							+ ex.getMessage());
		}

		vendorContact.setVendorContactId(id);
		return vendorContact;

	}// end method insert

	public List queryList(VendorContact vendorPrincipal) throws DBException {
		List result = null;
		try {
			result = getSqlMapClient().queryForList("vendor_contacts.query",
					vendorPrincipal);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorContactDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.queryList() due to "
							+ ex.getMessage());
		}
		return result;
		// return getSqlMapClient().queryForList("vendor_contacts.query",
		// vendorPrincipal);

	}

	public VendorContact queryVendorContactId(VendorContact vendorContact)
			throws DBException {
		VendorContact result = null;
		try {
			result = (VendorContact) getSqlMapClient().queryForObject(
					"vendor_contacts.queryVendorContactId", vendorContact);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorContactDaoImpl.queryVendorContactId() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.queryVendorContactId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.queryVendorContactId() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.queryVendorContactId() due to "
							+ ex.getMessage());
		}
		return result;

		// return (VendorContact)
		// getSqlMapClient().queryForObject("vendor_contacts.queryVendorContactId",
		// vendorContact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.provider.registration.dao.VendorContactDao#updateResource(com.newco.provider.registration.dao.VendorContact)
	 */
	public int updateResource(VendorContact vendorContact) throws DBException {
		int rowsAffected = -1;

		// logger.debug("Entering updateResource method with vendor_contact_id("
		// + vendorContact.getVendorContactId() + ") and resource_id(" +
		// vendorContact.getResourceId()+ ").");
		try {
			rowsAffected = getSqlMapClient().update(
					"vendor_contact.updateResourceId", vendorContact);
			// logger.debug("VendorContact.resourceId and resourceIndication has
			// been updated, rows affected(" + rowsAffected + ").");

		}// end try
		catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorContactDaoImpl.updateResource() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorContactDaoImpl.updateResource() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorContactDaoImpl.updateResource() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorContactDaoImpl.updateResource() due to "
							+ ex.getMessage());
		}

		return rowsAffected;

	}// end method updateResource

}// end class
