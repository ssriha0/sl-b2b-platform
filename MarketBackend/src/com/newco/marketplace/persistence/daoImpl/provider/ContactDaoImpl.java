/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;

public class ContactDaoImpl extends SqlMapClientDaoSupport implements IContactDao {
	private static final Logger localLogger = Logger.getLogger(ContactDaoImpl.class);
	// private static final MessageResources messages =
	// MessageResources.getMessageResources("DataAccessLocalStrings");

	public int update(Contact contact) throws DBException {
		int result = 0;

		try {
			result = getSqlMapClient().update("contact.updateP", contact);

		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.update() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.update() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.update() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.update() due to " + ex.getMessage());
		}
		return result;
	}

	public Contact query(Contact contact) throws DBException {
		try {
			contact = (Contact) getSqlMapClient().queryForObject("contact.queryP", contact);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		}

		return contact;
	}

	public Contact insert(Contact contact) throws DBException {
		// logger.debug("[insert] entering");
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient().insert("contact.insertP", contact);
			contact.setContactId(id.intValue());
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.insert() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.insert() due to " + ex.getMessage());
		}

		return contact;
	}

	public Contact registrationInsert(Contact contact) throws DBException {
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient().insert("registrationContact.insertP", contact);
			localLogger.info("GENERATE ID OF: " + id);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.registrationInsert() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.registrationInsert() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.registrationInsert() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.registrationInsert() due to " + ex.getMessage());
		}

		localLogger.info("here is the contact id number (" + id + ")");

		contact.setContactId(id.intValue());
		return contact;
	}

	public List queryList(Contact contact) throws DBException {
		List result = null;

		try {
			// result = getSqlMapClient().queryForList("contact.query",
			// contact);
			result = getSqlMapClient().queryForList("contact.queryP", contact);
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.queryList() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.queryList() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.queryList() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.queryList() due to " + ex.getMessage());
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#get(
	 * com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException {
		try {
			generalInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("generalInfo.contact.get", generalInfoVO);
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.get() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.get() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.get() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.get() due to " + ex.getMessage());
		}

		return generalInfoVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.persistence.iDao.contact.ContactDao#query(java.lang
	 * .Integer)
	 */
	public Contact getByBuyerId(Integer buyerId) throws DBException {

		Contact contact = null;
		try {
			contact = (Contact) getSqlMapClient().queryForObject("generalInfo.contact.query_by_buyer_id", buyerId);
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.getByBuyerId() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.getByBuyerId() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.getByBuyerId() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.getByBuyerId() due to " + ex.getMessage());
		}

		return contact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#
	 * insert(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient().insert("generalInfo.contact.insert", generalInfoVO);
			generalInfoVO.setContactId(id.intValue());
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.insert() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.insert() due to " + ex.getMessage());
		}

		return generalInfoVO;
	}

	public GeneralInfoVO insertContact(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("generalInfo.contactDetails.insert", generalInfoVO);
			generalInfoVO.setContactId(id.intValue());
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.insert() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.insert() due to " + ex.getMessage());
		}
		return generalInfoVO;
	}

	// insert into sl_pro_bkgnd_chk table
	public Integer insertBcCheck(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient().insert("generalInfo.bkgndchk.insert", generalInfoVO);
			return id;
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
		}

		return id;
	}

	// insert into sl_pro_bkgnd_chk table_history
	public void insertBcCheckHistory(GeneralInfoVO generalInfoVO) throws DBException {

		try {
			getSqlMapClient().insert("generalInfo.bkgndchkhistory.insert", generalInfoVO);

		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
		}

	}

	// sl-19667 get bc_check_id for the resource
	public Integer getBcCheckId(String resourceId) throws DBException {
		Integer bcCheckId;
		try {
			bcCheckId = (Integer) getSqlMapClient().queryForObject("generalInfo.bkgndchkId.get", resourceId);
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.getBcCheckId() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.getBcCheckId() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.getBcCheckId() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.getBcCheckId() due to " + ex.getMessage());
		}

		return bcCheckId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#
	 * update(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public int update(GeneralInfoVO generalInfoVO) throws DBException {
		int result = 0;

		try {
			result = getSqlMapClient().update("generalInfo.contact.update", generalInfoVO);

		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.update() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.update() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.update() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.update() due to " + ex.getMessage());
		}
		return result;
	}

	// Start of changes by Mayank for email validation
	public Contact queryPvalidateEmail(Contact contact) throws DBException {
		System.out.println("I am in ContactDAOImpl - isEmailIDFound()");
		System.out.println("I am in ContactDAOImpl - isEmailIDFound()");

		try {
			List<Contact> contactList = getSqlMapClient().queryForList("contact.queryPvalidateEmail", contact);// MTedder
			if (contactList == null)

				return null;
			if (contactList.size() > 0) {
				contact = (Contact) contactList.get(0);// MTedder
			} else {
				return null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		}

		return contact;
	}
	// End of changes by Mayank for email validation

	public Contact queryPvalidateEmailId(Contact contact) throws DBException {
		Contact resultContact = null;
		try {
			List<Contact> contactList = getSqlMapClient().queryForList("contact.queryPvalidateEmailId", contact);// MTedder

			if (contactList == null) {
				// localLogger.info("contct list here
				// is*************************"+contactList.size());
				return null;
			}
			if (contactList.size() > 0) {
				localLogger.info(" inside size>0*************************" + contactList.size());
				resultContact = new Contact();
				resultContact = (Contact) contactList.get(0);// MTedder
				return resultContact;
			} else {
				return null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			localLogger.info("SQL Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		}

	}

	public Integer getContactIdByUserName(String username) throws DBException {
		Integer contactId = null;
		try {
			contactId = (Integer) getSqlMapClient().queryForObject("getContactIdByUserName.query", username);
		} catch (Exception ex) {
			ex.printStackTrace();
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.query() due to " + ex.getMessage());
		}
		return contactId;
	}

	public Contact getByVendorId(Integer vendorId) throws DBException {
		Contact contact = null;
		try {
			contact = (Contact) getSqlMapClient().queryForObject("generalInfo.contact.query_by_vendor_id", vendorId);
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.getByVendorId() due to" + ex.getMessage());
			throw new DBException("SQL Exception @ContactDaoImpl.getByVendorId() due to " + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.getByVendorId() due to" + ex.getMessage());
			throw new DBException("General Exception @ContactDaoImpl.getByVendorId() due to " + ex.getMessage());
		}

		return contact;
	}

	// SL-19667 - get the contact with same details
	public Integer getBackgroundCheckIdWithSameContact(GeneralInfoVO generalInfoVO) throws DBException {
		Integer bcCheckId = null;
		try {
			bcCheckId = (Integer) getSqlMapClient().queryForObject("generalInfo.sameContact.get", generalInfoVO);
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
		}
		return bcCheckId;
	}

	// SL-19667 - update bg_check_id
	public int updateBcCheck(GeneralInfoVO generalInfoVO) throws DBException {
		int result = 0;

		try {
			result = getSqlMapClient().update("generalInfo.bcCheck.update", generalInfoVO);

		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.update() due to" + ex.getMessage());

		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.update() due to" + ex.getMessage());

		}
		return result;
	}

	// SL-19667 get background check information from background check Id
	public BackgroundCheckVO getBackgroundCheckInfo(Integer backgroundCheckId) throws DBException {
		BackgroundCheckVO backgroundCheckVo = null;
		try {
			backgroundCheckVo = (BackgroundCheckVO) getSqlMapClient().queryForObject("getBackgroundCheckInfo.ById.get",
					backgroundCheckId);
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.getBackgroundCheckInfo() due to" + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.getBackgroundCheckInfo() due to" + ex.getMessage());
		}

		return backgroundCheckVo;

	}

	// SL-19667 get background check Id of resource from vendor_resource
	public String getBackgroundCheckResourceInfo(String resourceId) throws DBException {
		String primaryAdmin = null;
		try {
			primaryAdmin = (String) getSqlMapClient().queryForObject("getBackgroundCheckResourceInfo.Id", resourceId);
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.etBackgroundCheckResourceInfo() due to" + ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @ContactDaoImpl.etBackgroundCheckResourceInfo due to" + ex.getMessage());
		}

		return primaryAdmin;

	}

	// R12_2
	// SL-20553
	public Integer getCountWithSameBackgroundCheckId(String bgCheckId) throws DBException {
		Integer countBcCheckId = null;
		try {
			countBcCheckId = (Integer) getSqlMapClient().queryForObject("getCountWithSameBackgroundCheckId.query",
					bgCheckId);
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
		}
		return countBcCheckId;
	}

	public Integer insertBcCheckDetails(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;

		try {
			id = (Integer) getSqlMapClient().insert("generalInfo.bkgndchkDetails.insert", generalInfoVO);
			return id;
		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.insert() due to" + ex.getMessage());
		}

		return id;
	}

	public int updateBackgroundDetails(TMBackgroundCheckVO tMBackgroundCheckVO) throws DBException {
		int result = 0;

		try {
			result = getSqlMapClient().update("bcCheck.Api.update", tMBackgroundCheckVO);

		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.updateBackgroundDetails() due to" + ex.getMessage());

		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.updateBackgroundDetails() due to" + ex.getMessage());

		}
		return result;
	}

	public BackgroundCheckVO getBackgroundCheckInfoByResourceId(String resourceId) throws DBException {
		BackgroundCheckVO backgroundCheckVo = null;
		try {
			backgroundCheckVo = (BackgroundCheckVO) getSqlMapClient()
					.queryForObject("getBackgroundCheckInfo.ByResourceId.get", resourceId);
		} catch (SQLException ex) {
			localLogger.info(
					"SQL Exception @ContactDaoImpl.getBackgroundCheckInfoByResourceId() due to" + ex.getMessage());
		} catch (Exception ex) {
			localLogger.info(
					"General Exception @ContactDaoImpl.getBackgroundCheckInfoByResourceId due to" + ex.getMessage());
		}

		return backgroundCheckVo;

	}

	// SL-976 - get the contact with same details
	public Integer getBackgroundCheckDetailsWithSameContact(GeneralInfoVO generalInfoVO) throws DBException {
		Integer bcCheckId = null;
		try {
			bcCheckId = (Integer) getSqlMapClient().queryForObject("generalInfo.sameBGDetails.get", generalInfoVO);
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
		}
		return bcCheckId;
	}

	public GeneralInfoVO getProviderDetailsWithSameContact(GeneralInfoVO generalInfoVO) throws DBException {
		try {
			generalInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("generalInfo.sameProvideDetails.get",
					generalInfoVO);
		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.query() due to" + ex.getMessage());
		}
		return generalInfoVO;
	}

	public int updateBGStateInfo(GeneralInfoVO generalInfoVO) throws DBException {
		int result = 0;

		try {
			result = getSqlMapClient().update("generalInfo.bgStateInfo.update", generalInfoVO);

		} catch (SQLException ex) {
			localLogger.info("SQL Exception @ContactDaoImpl.update() due to" + ex.getMessage());

		} catch (Exception ex) {
			localLogger.info("General Exception @ContactDaoImpl.update() due to" + ex.getMessage());

		}
		return result;
	}

}
