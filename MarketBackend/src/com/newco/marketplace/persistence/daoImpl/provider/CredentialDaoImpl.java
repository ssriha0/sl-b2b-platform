package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.ICredentialDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.CredentialProfile;

public class CredentialDaoImpl extends SqlMapClientDaoSupport implements
		ICredentialDao {
	private static final Logger localLogger = Logger.getLogger(CredentialDaoImpl.class);

	public int update(CredentialProfile credentialProfile) throws DBException {
		Integer result = null;
		try {

			result = getSqlMapClient().update("credentialProfile.update",
					credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @CredentialDaoImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public CredentialProfile query(CredentialProfile credentialProfile)
			throws DBException {
		CredentialProfile vo = null;
		try {
			vo = (CredentialProfile) getSqlMapClient().queryForObject(
					"credentialProfile.query", credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.query() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.query() due to", ex);
		}
		return vo;
	}

	public CredentialProfile insert(CredentialProfile credentialProfile)
			throws DBException {
		try {

			Integer result = (Integer) getSqlMapClient().insert(
					"credentialProfile.insert", credentialProfile);
			credentialProfile.setVendorCredId(result.intValue());
			localLogger
					.info("**********************************************************************************"
							+ result);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.insert() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.insert() due to", ex);
		}
		return credentialProfile;

	}

	// ====================================== To query the list of the
	// credential
	// Types
	public List queryList(CredentialProfile credentialProfile)
			throws DBException {
		List list = null;
		try {
			list = getSqlMapClient().queryForList("credentialProfile.query",
					credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryList() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryList() due to",
					ex);
		}

		return list;
	}

	// to find the list of the credential Types
	public List queryList(String str, CredentialProfile credentialProfile)
			throws DBException {
		List list = null;
		try {
			list = getSqlMapClient().queryForList(
					"credentialProfile.queryList", credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryList() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryList() due to",
					ex);
		}

		return list;

	}

	// / For the credential Category Id's and also to find the id of the types
	public List queryList1(String str, CredentialProfile credentialProfile1)
			throws DBException {
		List list = null;
		try {
			list = getSqlMapClient().queryForList(
					"credentialProfile.queryList1", credentialProfile1);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.queryList1() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryList1() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryList1() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryList1() due to",
					ex);
		}

		return list;
	}

	public int queryForCredCategoryId(String str,
			CredentialProfile credentialProfile) throws DBException {
		List list = null;
		int credCategoryId = 0;
		try {
			list = getSqlMapClient().queryForList("credentialCategoryId.query",
					credentialProfile);
			java.util.Iterator i = list.iterator();

			while (i.hasNext()) {
				credCategoryId = (Integer) i.next();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryForCredCategoryId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryForCredCategoryId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryForCredCategoryId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryForCredCategoryId() due to",
					ex);
		}

		return credCategoryId;
	}

	public int queryForCredTypeId(String str,
			CredentialProfile credentialProfile) throws DBException {
		List list = null;
		int credCategoryId = 0;
		try {
			list = getSqlMapClient().queryForList("credentialTypeId.query",
					credentialProfile);
			java.util.Iterator i = list.iterator();

			while (i.hasNext()) {
				credCategoryId = (Integer) i.next();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryForCredTypeId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryForCredTypeId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryForCredTypeId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryForCredTypeId() due to",
					ex);
		}

		return credCategoryId;
	}

	public List queryForInsuranceType(String str,
			CredentialProfile credentialProfile) throws DBException {
		List list = null;
		try {
			list = getSqlMapClient().queryForList("insuranceType.queryList",
					credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryForCredTypeId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryForCredTypeId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryForCredTypeId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryForCredTypeId() due to",
					ex);
		}

		return list;

	}

	public CredentialProfile query1(CredentialProfile credentialProfile)
			throws DBException {
		CredentialProfile result = null;
		try {
			result = (CredentialProfile) getSqlMapClient().queryForObject(
					"credentialProfile.query1", credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.query1() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.query1() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.query1() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.query1() due to", ex);
		}

		return result;
	}

	public CredentialProfile insertInsuranceTypes(
			CredentialProfile credentialProfile) throws DBException {
		try {
			credentialProfile.setCredentialId(((Integer) getSqlMapClient()
					.insert("insuranceTypes.insert", credentialProfile))
					.intValue());
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.insertInsuranceTypes() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.insertInsuranceTypes() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.insertInsuranceTypes() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.insertInsuranceTypes() due to",
					ex);
		}

		return credentialProfile;
	}

	public CredentialProfile queryInsurance(CredentialProfile credentialProfile)
			throws DBException {
		CredentialProfile vo = null;
		try {
			vo = (CredentialProfile) getSqlMapClient().queryForObject(
					"credentialProfile.queryInsurance", credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryInsurance() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryInsurance() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryInsurance() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryInsurance() due to",
					ex);
		}
		return vo;
	}

	public int updateInsurance(CredentialProfile credentialProfile)
			throws DBException {
		Integer result = null;
		try {

			result = getSqlMapClient().update("insuranceProfile.update",
					credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.updateInsurance() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.updateInsurance() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.updateInsurance() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.updateInsurance() due to",
					ex);
		}
		return result;
	}

	public CredentialProfile queryInsuranceSec(
			CredentialProfile credentialProfile) throws DBException {
		CredentialProfile vo = null;
		try {
			vo = (CredentialProfile) getSqlMapClient().queryForObject(
					"credentialProfile.queryInsuranceSec", credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryInsuranceSec() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryInsuranceSec() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryInsuranceSec() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryInsuranceSec() due to",
					ex);
		}
		return vo;
	}
	
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public List getVendorUploadedDocuments(CredentialProfile credentialProfile)throws DBException {
		List list = null;
		try {
			list = getSqlMapClient().queryForList(
					"credentialProfile.documentInfo.queryList", credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @CredentialDaoImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryList() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryList() due to",
					ex);
		}

		return list;
	}
	
	//SL-21233: Document Retrieval Code Ends
	
	

	public List queryForInsuranceType(CredentialProfile credentialProfile)
			throws DBException {
		List list = null;
		System.out.println("------COming Here----------");
		try {
			list = getSqlMapClient().queryForList("insuranceType.queryList",
					credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryForCredTypeId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryForCredTypeId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryForCredTypeId() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryForCredTypeId() due to",
					ex);
		}

		return list;
	}
	
	public void delete(CredentialProfile credentialProfile) throws DBException {

		System.out.println("------COming Here----------");
		try {
			 getSqlMapClient().delete("insuranceProfile.delete",
					credentialProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.delete() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.delete() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.delete() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.delete() due to",
					ex);
		}
		
	}
	
	/**
	 * Method to fetch the credential details associated with the selected insurance certificate
	 * @param CredentialProfile credentialProfileVo
	 * @param Integer docId
	 * @return CredentialProfile
	 * @throws Exception
	 */
	public CredentialProfile queryInsuranceDetailsForSelectedDocument(CredentialProfile credentialProfileVo,
			Integer docId) throws DBException {
		try {
			credentialProfileVo = (CredentialProfile) getSqlMapClient().queryForObject(
					"credentialProfile.queryInsuranceDetailsForSelectedDocument", docId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to",
					ex);
		}
		return credentialProfileVo;
	}
	//Changes related to SL-20301
	public CredentialProfile queryAdditonalInsuranceDetailsForSelectedDocument(CredentialProfile credentialProfileVo,
			Integer docId) throws DBException {
		try {
			credentialProfileVo = (CredentialProfile) getSqlMapClient().queryForObject(
					"credentialProfile.queryAdditonalInsuranceDetailsForSelectedDocument", docId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.queryInsuranceDetailsForSelectedDocument() due to",
					ex);
		}
		return credentialProfileVo;
	}
	
	public CredentialProfile isInsuranceExist(CredentialProfile credentialProfileVo) throws DBException  {

		try {
			credentialProfileVo = (CredentialProfile) getSqlMapClient().queryForObject("insurance.isAvailable", credentialProfileVo);

		}  catch (SQLException ex) {
			logger
					.info("SQL Exception @CredentialDaoImpl.isInsuranceExist() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @CredentialDaoImpl.isInsuranceExist() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @CredentialDaoImpl.isInsuranceExist() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @CredentialDaoImpl.isInsuranceExist() due to",
					ex);
		}
		return credentialProfileVo;
	}
	

}
