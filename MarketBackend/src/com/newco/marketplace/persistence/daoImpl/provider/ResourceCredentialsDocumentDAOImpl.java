package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IResourceCredentialsDocumentDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.ResourceCredentialsDocumentVO;

public class ResourceCredentialsDocumentDAOImpl extends SqlMapClientDaoSupport
		implements IResourceCredentialsDocumentDAO {

	private static final Logger logger = Logger.getLogger(ResourceCredentialsDocumentDAOImpl.class);

	public Integer insert(
			ResourceCredentialsDocumentVO myResourceCredentialsDocument)
			throws DBException {
		Integer rvInteger = null;
		try {
			rvInteger = (Integer) getSqlMapClient().insert(
					"resource_credentials_document.insert",
					myResourceCredentialsDocument);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.insert() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @ResourceCredentialsDocumentDAOImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.insert() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @ResourceCredentialsDocumentDAOImpl.insert() due to "
							+ ex.getMessage());
		}
		return (rvInteger);

	}

	public java.util.List getResourceCredentialDocuments(
			ResourceCredentialsDocumentVO myResourceCredentialsDocument)
			throws DBException {
		// TODO Auto-generated method stub
		try {
			return getSqlMapClient().queryForList(
					"resource_credentials_document.query",
					myResourceCredentialsDocument);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.getResourceCredentialDocuments() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @ResourceCredentialsDocumentDAOImpl.getResourceCredentialDocuments() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.getResourceCredentialDocuments() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @ResourceCredentialsDocumentDAOImpl.getResourceCredentialDocuments() due to "
							+ ex.getMessage());
		}

	}

	public ResourceCredentialsDocumentVO getMaxResourceCredentialDocument(
			ResourceCredentialsDocumentVO myResourceCredentialsDocument)
			throws DBException {

		try {
			Integer docId = (Integer) getSqlMapClient().queryForObject(
					"resource_credentials_document.getMaxDocId",
					myResourceCredentialsDocument);
			logger.info(" getMaxResourceCredentialDocument docId " + docId);
			if (docId == null)
				return null;
			myResourceCredentialsDocument.setDocumentId(docId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.getMaxResourceCredentialDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @ResourceCredentialsDocumentDAOImpl.getMaxResourceCredentialDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.getMaxResourceCredentialDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @ResourceCredentialsDocumentDAOImpl.getMaxResourceCredentialDocument() due to "
							+ ex.getMessage());
		}

		return myResourceCredentialsDocument;

	}

	public int deleteResourceCredentialDocument(
			ResourceCredentialsDocumentVO resourceCredentialsDocumentVo)
			throws DBException {
		
		Integer result = null;

		try {
			result = (Integer) getSqlMapClient().delete(
					"resource_credentials_documentId.delete",
					resourceCredentialsDocumentVo);
			logger.info(" resource_credentials_documentId-->result " + result);
			return result;

		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentialDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentialDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentialDocument() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentialDocument() due to "
							+ ex.getMessage());
		}

	}

	public int deleteResourceCredentials(
			ResourceCredentialsDocumentVO resourceCredentialsDocumentVo)
			throws DBException {
		Integer result =null;
		try {
			result = (Integer) getSqlMapClient().delete(
					"resource_credentials_document.delete",
					resourceCredentialsDocumentVo);
			logger.info(" deleteResourceCredentials result " + result);
			
			return result;
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentials() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentials() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentials() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @ResourceCredentialsDocumentDAOImpl.deleteResourceCredentials() due to "
							+ ex.getMessage());
		}
	}
	
	public ResourceCredentialsDocumentVO isVendorDocumentExists(ResourceCredentialsDocumentVO credentialsDocumentVO) throws DBException
	{	
		ResourceCredentialsDocumentVO resourceVO = null;
		try
		{
			resourceVO = (ResourceCredentialsDocumentVO) getSqlMapClient().
								queryForObject("resource_cred_document.isDocumentExists", credentialsDocumentVO);
			
		}catch(SQLException exception)
		{
			exception.printStackTrace();
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.isVendorDocumentExists() due to "
							+ exception.getMessage());
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.isVendorDocumentExists() due to "
							+ exception.getMessage());
		}
		return resourceVO;
	}
	
	/**
	 * Updates the resource credential document
	 * @param credentialsDocumentVO
	 * @throws DBException
	 */
	public void updateResourceCredentialDocument(ResourceCredentialsDocumentVO credentialsDocumentVO) throws DBException{
		Integer result = null;
		try
		{
			result = (Integer) getSqlMapClient().update("resource_cred_document.updateCredDocument", credentialsDocumentVO);			
		}catch(SQLException ex)
		{
			throw new DBException("General Exception @ResourceCredentialsDocumentDAOImpl.updateResourceCredentialDocument() due to ::",ex);
		}
		catch(Exception exception)
		{
			throw new DBException("General Exception @ResourceCredentialsDocumentDAOImpl.updateResourceCredentialDocument() due to :: ",exception);
		}
	} 

}