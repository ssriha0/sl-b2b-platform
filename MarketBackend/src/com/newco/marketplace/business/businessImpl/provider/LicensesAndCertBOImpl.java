package com.newco.marketplace.business.businessImpl.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.ILicensesAndCertBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.ILicensesAndCertDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDocumentDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 *
 * $Revision: 1.13 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 *
 */
public class LicensesAndCertBOImpl extends ABaseBO implements ILicensesAndCertBO {

	private ILicensesAndCertDao iLicensesAndCertDao;
	private IAuditBO auditBusinessBean;
	private IVendorCredentialsDocumentDAO myVendorCredentialsDocumentDAO;
	private IDocumentBO documentBO;

	private static final Logger logger = Logger
	.getLogger(LicensesAndCertBOImpl.class.getName());

	private IAuditBO getAuditBusinessBean() {

		if (auditBusinessBean == null) {
		auditBusinessBean = (IAuditBO) MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.BUSINESSBEAN_AUDIT);
		}
		return (auditBusinessBean);
		}



	/**
	 *
	 */
	public LicensesAndCertBOImpl(ILicensesAndCertDao licensesAndCertDao,
								IVendorCredentialsDocumentDAO myVendorCredentialsDocumentDAO,
								IDocumentBO documentBO) {
		iLicensesAndCertDao = licensesAndCertDao;
		this.myVendorCredentialsDocumentDAO = myVendorCredentialsDocumentDAO;
		this.documentBO = documentBO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesAndCertBO#getData(com.newco.marketplace.vo.LicensesAndCertVO)
	 */
	public LicensesAndCertVO getData(LicensesAndCertVO objLicensesAndCertVO)
			throws Exception {

		try
		{
			LicensesAndCertVO dbLicensesAndCertVO = iLicensesAndCertDao.getData(objLicensesAndCertVO);

			/**
			 * Functionalities related to the Document Upload
			 */
			if (dbLicensesAndCertVO != null)
			{
				VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();
				myVCredentialsDocumentVO.setVendorCredId(dbLicensesAndCertVO.getVendorCredId());
				logger.info("looking for a document cred_id = ------>"
						+ dbLicensesAndCertVO.getVendorCredId());
				VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.
														getMaxVendorCredentialDocument(myVCredentialsDocumentVO);

				if (avo != null) {
					dbLicensesAndCertVO.setCurrentDocumentID(avo.getDocumentId());

					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					logger.info("loaded document for edit ------>"
							+ avo.getDocumentId());
					aDoc = documentBO.retrieveDocumentMetadataByDocumentId(aDoc);

					dbLicensesAndCertVO.setCredentialDocumentFileName(aDoc.getFileName());
					dbLicensesAndCertVO.setCredentialDocumentExtention(aDoc.getFormat());
					dbLicensesAndCertVO.setCredentialDocumentFileSize(aDoc.getDocSize());

					logger.info(aDoc.getLastUpdateTimestamp()
							+ " is the timestamp I got for document "
							+ aDoc.getDocumentId());
				} else {
					dbLicensesAndCertVO.setCurrentDocumentID(0);
				}
			}

			return dbLicensesAndCertVO ;

		}catch(Exception a_Ex)
		{
			throw a_Ex;
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesAndCertBO#save(com.newco.marketplace.vo.LicensesAndCertVO)
	 */
	public LicensesAndCertVO save(LicensesAndCertVO objLicensesAndCertVO)
			throws Exception {

		VendorCredentialsDocumentVO  vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		LicensesAndCertVO dbLicensesAndCertVO = null;
		int docId = 0;

		try
		{
			dbLicensesAndCertVO = iLicensesAndCertDao.save(objLicensesAndCertVO);
			 /**
			 * Set the Vendor Credential Id.
			 */
			vendorCredentialsDocumentVO.setVendorCredId(objLicensesAndCertVO.getVendorCredId());
			vendorCredentialsDocumentVO = myVendorCredentialsDocumentDAO.
														isVendorDocumentExists(vendorCredentialsDocumentVO);

			if (vendorCredentialsDocumentVO == null)
			{
				//If the document does not exists
				if (objLicensesAndCertVO != null
				&&  objLicensesAndCertVO.getCredentialDocumentFileName() != null
				&&  objLicensesAndCertVO.getCredentialDocumentFileName().trim().length() > 0)
				{
					docId = InsertDocumentDetails(objLicensesAndCertVO);
				}
			}
			else
			{
				//If the document exists
				//Sets the already existing document ID for insertion
				objLicensesAndCertVO.setCurrentDocumentID(vendorCredentialsDocumentVO.getDocumentId());
				if (objLicensesAndCertVO.getCredentialDocumentFileName() != null && objLicensesAndCertVO.getCredentialDocumentFileSize() != 0)
				{
					docId = updateDocumentDetails(objLicensesAndCertVO);
				}
			}

			// Insert auditing

			AuditVO auditVO = null;
			try
			{
				 auditVO = new AuditVO(objLicensesAndCertVO.getVendorId(), 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
				 //defect 60672 when insertinnew stuff here alway set the reviewed by to null so that power Auditor can look at the Audit TASK
				 auditVO.setReviewedBy("");

				//Modified by bnatara to allow Audit update
				//Sears00045656
				 if (docId > 0)
				 {
					 auditVO.setDocumentId(docId);
				 }

				if (dbLicensesAndCertVO != null && dbLicensesAndCertVO.getVendorCredId() > 0) {
					auditVO.setAuditKeyId(dbLicensesAndCertVO.getVendorCredId());
					logger.info("audit key id in license is+++++"+auditVO.getAuditKeyId());
				}
				getAuditBusinessBean().auditVendorCredentials(auditVO);

			} catch (Exception e) {
				logger.info("[LicensesAndCertBOImpl] - save() - Audit Exception Occured for audit record:" + auditVO.toString(), e);
				throw new BusinessServiceException(e);
			}



		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			throw new BusinessServiceException(a_Ex);
		}
		return dbLicensesAndCertVO;

	}

	public LicensesAndCertVO getDataList(LicensesAndCertVO objLicensesAndCertVO)
	throws Exception {
		LicensesAndCertVO dbLicensesAndCertVO = iLicensesAndCertDao.getDataList(objLicensesAndCertVO);

		LicensesAndCertVO temp= new LicensesAndCertVO();
		temp.setVendorId(objLicensesAndCertVO.getVendorId());
		temp=get(temp);
		objLicensesAndCertVO.setAddCredentialToFile(temp.getAddCredentialToFile());
		return dbLicensesAndCertVO ;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesBO#update(com.newco.marketplace.vo.LicensesVO)
	 */
	public void update(LicensesAndCertVO licensesAndCertVO) {
		VendorCredentialsDocumentVO  vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		try
		{
			iLicensesAndCertDao.update(licensesAndCertVO);
			/**
			 * Set the Vendor Credential Id.
			 */
			vendorCredentialsDocumentVO.setVendorCredId(licensesAndCertVO.getVendorCredId());
			vendorCredentialsDocumentVO = myVendorCredentialsDocumentDAO.
														isVendorDocumentExists(vendorCredentialsDocumentVO);

			if (vendorCredentialsDocumentVO == null)
			{
				//If the document does not exists
				InsertDocumentDetails(licensesAndCertVO);
			}
			else

			{
				//If the document exists
				//Sets the already existing document ID for insertion
				licensesAndCertVO.setCurrentDocumentID(vendorCredentialsDocumentVO.getDocumentId());
				updateDocumentDetails(licensesAndCertVO);
			}

		}catch(Exception e)
		{
			logger.info("Caught Exception and ignoring",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesBO#get(com.newco.marketplace.vo.LicensesVO)
	 */
	private  LicensesAndCertVO get(LicensesAndCertVO licensesAndCertVO) {
		//call DAO layer
		licensesAndCertVO=iLicensesAndCertDao.get(licensesAndCertVO);
		return licensesAndCertVO;
	}

	public LicensesAndCertVO viewDocument(
			LicensesAndCertVO licencesVO)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException {

		try {

			DocumentVO documentVO = new DocumentVO();
			documentVO.setDocumentId(licencesVO.getCurrentDocumentID());

			Integer documentID = new Integer(licencesVO.getCurrentDocumentID());

			documentVO = documentBO.retrieveVendorDocumentByDocumentId(documentID);

			if (documentVO != null)
			{
				licencesVO.setCredentialDocumentBytes(documentVO.getBlobBytes());
				licencesVO.setCredentialDocumentFileName(documentVO.getFileName());
				licencesVO.setCredentialDocumentFileSize(documentVO.getDocSize());
				licencesVO.setCredentialDocumentExtention(documentVO.getFormat());
			}

		}catch (Exception ex) {
			logger.info("General Exception @InsurancePolicyBOImpl.deleteInsurance() due to", ex);
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.deleteInsurance() due to ", ex);
		}
		return licencesVO;
	}

	public void removeDocument(LicensesAndCertVO licencesVO)
				throws BusinessServiceException, FileNotFoundException,
				IOException, ParseException {

		try {
				VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();
				myVCredentialsDocumentVO.setVendorCredId(licencesVO.getVendorCredId());
				VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.
													getMaxVendorCredentialDocument(myVCredentialsDocumentVO);

				if (avo != null) {
					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					avo.setVendorCredId(licencesVO.getVendorCredId());

					Integer documentID = new Integer(avo.getDocumentId());

					AuditVO auditVO = null;
					try
					{
						auditVO = new AuditVO(licencesVO.getVendorId(), 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);

						//Modified by bnatara to allow Audit update
						//Sears00045656
						auditVO.setDocumentId(null);

						if (licencesVO != null && licencesVO.getVendorCredId() > 0) {
							auditVO.setAuditKeyId(licencesVO.getVendorCredId());
							logger.info("audit key id in license is+++++"+auditVO.getAuditKeyId());
						}
						getAuditBusinessBean().auditVendorCredentials(auditVO);

					} catch (Exception e) {
						logger.info("[LicensesAndCertBOImpl] - save() - Audit Exception Occured for audit record:" + auditVO.toString()
								+ StackTraceHelper.getStackTrace(e));
					}

					myVendorCredentialsDocumentDAO.removeCredentialVendorDocument(avo);
					documentBO.deleteVendorDocument(documentID);
				}
		}catch (Exception ex) {
			logger.info("General Exception @InsurancePolicyBOImpl.deleteInsurance() due to",ex);
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.deleteInsurance() due to "
							+ ex.getMessage());
		}
	}

	private int InsertDocumentDetails(LicensesAndCertVO licencesVO) throws BusinessServiceException
	{
		DocumentVO documentVO = new DocumentVO();
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		ProcessResponse processResponse = new ProcessResponse();
		int documentId = 0;
		try
		{
			vendorCredentialsDocumentVO.setVendorCredId(licencesVO.getVendorCredId());
			/**
			 * Insert into the Document table
			 */
			documentVO.setVendorId(licencesVO.getVendorId());
			documentVO.setDescription(licencesVO.getCredTypeDesc());
			documentVO.setTitle(licencesVO.getLicenseName());
			documentVO.setFileName(licencesVO.getCredentialDocumentFileName());
			documentVO.setFormat(licencesVO.getCredentialDocumentExtention());
			documentVO.setBlobBytes(licencesVO.getCredentialDocumentBytes());
			documentVO.setDocSize(licencesVO.getCredentialDocumentFileSize());

//			documentVO = documentBO.insertVendorDoc(documentVO);
			processResponse = documentBO.insertVendorDocument(documentVO);
			if (processResponse != null)
			{
				Integer integer = (processResponse.getObj() != null ) ?
										(Integer) processResponse.getObj() : null;
				if (integer == null)
					return 0;
				else
					documentId = integer.intValue();
			}
			else
				return 0;

			//Adds the Document ID to the DocumentVO
			documentVO.setDocumentId(documentId);

			vendorCredentialsDocumentVO.setDocumentId(documentVO.getDocumentId());

			/**
			 * Insert into the vendor_credential_document table.
			 */
			myVendorCredentialsDocumentDAO.insertVendorCredentialDocument(vendorCredentialsDocumentVO);
			licencesVO.setCurrentDocumentID(vendorCredentialsDocumentVO.getDocumentId());

		}catch (Exception ex) {
			logger.info("General Exception @InsurancePolicyBOImpl.saveInsurance() due to", ex);
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.saveInsurance() due to "
							+ ex.getMessage());
		}
		return documentVO.getDocumentId();
	}

	private int updateDocumentDetails(LicensesAndCertVO licencesVO) throws BusinessServiceException
	{
		DocumentVO documentVO = new DocumentVO();
		try
		{
			/**
			 * Set Document details for insertion into the Document table
			 */
			documentVO.setVendorId(licencesVO.getVendorId());
			documentVO.setDescription(licencesVO.getCredTypeDesc());
			documentVO.setTitle(licencesVO.getLicenseName());
			documentVO.setFileName(licencesVO.getCredentialDocumentFileName());
			documentVO.setFormat(licencesVO.getCredentialDocumentExtention());
			documentVO.setBlobBytes(licencesVO.getCredentialDocumentBytes());			
			documentVO.setDocSize(licencesVO.getCredentialDocumentFileSize());
			
			ProcessResponse processResponse = new ProcessResponse();
			processResponse = documentBO.insertVendorDocument(documentVO);
			int documentId = 0;
			if (processResponse != null)
			{
				Integer integer = (processResponse.getObj() != null ) ?
										(Integer) processResponse.getObj() : null;
				if (integer == null)
					return 0;
				else
					 documentId = integer.intValue(); 
			}
			else
				return 0;	
			licencesVO.setCurrentDocumentID(documentId);
			myVendorCredentialsDocumentDAO.updateDocumentInLicenseCredentialDocument(licencesVO);

		}catch (Exception ex) {
			logger.info("General Exception @InsurancePolicyBOImpl.saveInsurance() due to",ex);
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.saveInsurance() due to "
							+ ex.getMessage());
		}
		return documentVO.getDocumentId();
	}

}
