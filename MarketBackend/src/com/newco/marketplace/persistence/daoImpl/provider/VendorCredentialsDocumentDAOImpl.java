package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDocumentDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;

public class VendorCredentialsDocumentDAOImpl extends SqlMapClientDaoSupport implements
		IVendorCredentialsDocumentDAO {
	public void insert(VendorCredentialsDocumentVO myVendorCredentialsDocument)
			throws DBException {
		try {
			getSqlMapClient().insert("vendor_credentials_document.insert",
					myVendorCredentialsDocument);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorCredentialsDocumentDAOImpl.myVendorCredentialsDocument() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorCredentialsDocumentDAOImpl.myVendorCredentialsDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorCredentialsDocumentDAOImpl.myVendorCredentialsDocument() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorCredentialsDocumentDAOImpl.myVendorCredentialsDocument() due to "
							+ ex.getMessage());
		}
	}

	public java.util.List getVendorCredentialDocuments(
			VendorCredentialsDocumentVO myVendorCredentialsDocument)
			throws DBException {
		// TODO Auto-generated method stub
		try {
			return getSqlMapClient().queryForList("vendor_credentials_document.query",
					myVendorCredentialsDocument);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorCredentialsDocumentDAOImpl.getVendorCredentialDocuments() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorCredentialsDocumentDAOImpl.getVendorCredentialDocuments() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorCredentialsDocumentDAOImpl.getVendorCredentialDocuments() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorCredentialsDocumentDAOImpl.getVendorCredentialDocuments() due to "
							+ ex.getMessage());
		}
	}

	public VendorCredentialsDocumentVO getMaxVendorCredentialDocument(
			VendorCredentialsDocumentVO myVendorCredentialsDocument)
			throws DBException {
		// TODO Auto-generated method stub
		Integer docId ;
		try {
			docId = (Integer) getSqlMapClient().queryForObject(
					"vendor_cred_document.getMaxDocId",
					myVendorCredentialsDocument);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorCredentialsDocumentDAOImpl.getMaxVendorCredentialDocument() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorCredentialsDocumentDAOImpl.getMaxVendorCredentialDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorCredentialsDocumentDAOImpl.getMaxVendorCredentialDocument() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorCredentialsDocumentDAOImpl.getMaxVendorCredentialDocument() due to "
							+ ex.getMessage());
		}
		if (docId == null)
			return null;
		VendorCredentialsDocumentVO vo = new VendorCredentialsDocumentVO();
		vo.setDocumentId(docId);
		return vo;
	}
	
	public void removeCredentialVendorDocument(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException
	{
		try
		{
			getSqlMapClient().delete("vendor.credential.delete.documentId", credentialsDocumentVO);
		}catch(SQLException exception)
		{
			exception.printStackTrace();
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.removeCredentialVendorDocument() due to "
							+ exception.getMessage());
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.removeCredentialVendorDocument() due to "
							+ exception.getMessage());
		}
		
	}
	
	public void insertVendorCredentialDocument(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException
	{	
		try
		{
			getSqlMapClient().insert("vendor_cred_document.insert", credentialsDocumentVO);
			
		}catch(SQLException exception)
		{
			exception.printStackTrace();
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.insertDocument() due to "
							+ exception.getMessage());
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.insertDocument() due to "
							+ exception.getMessage());
		}
	}
	
	public VendorCredentialsDocumentVO isVendorDocumentExists(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException
	{	
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = null;
		try
		{
			vendorCredentialsDocumentVO = (VendorCredentialsDocumentVO) getSqlMapClient().
													queryForObject("vendor_cred_document.isDocumentExists", credentialsDocumentVO);
			
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
		return vendorCredentialsDocumentVO;
	}

	public VendorCredentialsDocumentVO getLastUploadedDocument(int vendorId) throws DBException{
		Integer docId;
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = null;
		try {
			docId = (Integer) getSqlMapClient().queryForObject(
					"vendor_cred_document.getMaxDocIdForVendor",
					vendorId);
			if (docId != null) {
				vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
				vendorCredentialsDocumentVO.setDocumentId(docId);
			}
		} catch (SQLException ex) {
			logger.info("SQL Exception @VendorCredentialsDocumentDAOImpl.getLastUploadedDocument() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorCredentialsDocumentDAOImpl.getLastUploadedDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			logger.info("General Exception @VendorCredentialsDocumentDAOImpl.getLastUploadedDocument() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorCredentialsDocumentDAOImpl.getLastUploadedDocument() due to "
							+ ex.getMessage());
		}
		return vendorCredentialsDocumentVO;
	}
	
	public VendorCredentialsDocumentVO isVendorDocumentExistsForInsurance(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException
	{	
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = null;
		Integer docId;
		try
		{
			docId = (Integer) getSqlMapClient().
									queryForObject("vendor_cred_document.isDocumentExistsForInsurance", credentialsDocumentVO);
			if(null != docId){
				vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
				vendorCredentialsDocumentVO.setDocumentId(docId);
			}
		}catch(SQLException exception)
		{
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.isVendorDocumentExistsForInsurance() due to "
							+ exception.getMessage());
		}
		catch(Exception exception)
		{
			throw new DBException(
					"General Exception @VendorDocumentDaoImpl.isVendorDocumentExistsForInsurance() due to "
							+ exception.getMessage());
		}
		return vendorCredentialsDocumentVO;
	}
	
	/**
	 * Updates the license credential document
	 * @param licensesAndCertVO
	 * @throws DBException
	 */
	public void updateDocumentInLicenseCredentialDocument(LicensesAndCertVO licensesAndCertVO)throws DBException
	{
		try
		{
			getSqlMapClient().update("vendor_license_document.updateDocument", licensesAndCertVO);
			
		}catch(SQLException exception)
		{
			throw new DBException("General Exception @VendorDocumentDaoImpl.updateDocumentInLicenseCredentialDocument() due to ",exception);
		}
		catch(Exception exception)
		{
			throw new DBException("General Exception @VendorDocumentDaoImpl.updateDocumentInLicenseCredentialDocument() due to ",exception);
		}
	}
	
	/**
	 * Updates the insurance credential document
	 * @param vendorCredentialsDocumentVO
	 * @throws DBException
	 */
	public void updateDocumentInInsuranceCredentialDocument(VendorCredentialsDocumentVO vendorCredentialsDocumentVO)throws DBException
	{
		try
		{
			getSqlMapClient().update("vendor_insurance_document.updateDocument", vendorCredentialsDocumentVO);
			
		}catch(SQLException exception)
		{
			throw new DBException("General Exception @VendorDocumentDaoImpl.updateDocumentInInsuranceCredentialDocument() due to ",exception);
		}
		catch(Exception exception)
		{
			throw new DBException("General Exception @VendorDocumentDaoImpl.updateDocumentInInsuranceCredentialDocument() due to ",exception);
		}
	}
}