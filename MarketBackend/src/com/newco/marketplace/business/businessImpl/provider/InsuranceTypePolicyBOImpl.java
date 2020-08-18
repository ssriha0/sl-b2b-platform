package com.newco.marketplace.business.businessImpl.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.ICredentialDao;
import com.newco.marketplace.persistence.iDao.provider.IInsuranceTypesDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDocumentDAO;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class InsuranceTypePolicyBOImpl extends ABaseBO implements
		IInsuranceTypePolicyBO {
	private IVendorHdrDao vendorHdrDao;
	private ICredentialDao credentialDao;
	private IUserProfileDao userProfileDao;
	private IInsuranceTypesDao insuranceTypesDao;
	private IVendorCredentialsDocumentDAO myVendorCredentialsDocumentDAO;
	private ILookupDAO lookupDAO;
	private IDocumentBO documentBO;
	private IAuditBO auditBusinessBean;

	/**
	 * @param vendorHdrDao
	 * @param credentialDao
	 * @param userProfileDao
	 * @param insuranceTypesDao
	 * @param myVendorCredentialsDocumentDAO
	 * @param documentBO
	 * @param lookupDAO
	 */
	public InsuranceTypePolicyBOImpl(IVendorHdrDao vendorHdrDao,
			ICredentialDao credentialDao, IUserProfileDao userProfileDao,
			IInsuranceTypesDao insuranceTypesDao,
			IVendorCredentialsDocumentDAO myVendorCredentialsDocumentDAO,
			IDocumentBO documentBO, ILookupDAO lookupDAO) {

		this.vendorHdrDao = vendorHdrDao;
		this.credentialDao = credentialDao;
		this.userProfileDao = userProfileDao;
		this.insuranceTypesDao = insuranceTypesDao;
		this.myVendorCredentialsDocumentDAO = myVendorCredentialsDocumentDAO;
		this.lookupDAO = lookupDAO;
		this.documentBO = documentBO;
	}

	private static final Logger logger = Logger.getLogger(InsuranceTypePolicyBOImpl.class);


	/**
	 *
	 * @param cProfile
	 * @return
	 * @throws BusinessServiceException
	 */
	public List getInsuranceTypeList(CredentialProfile cProfile)
		throws BusinessServiceException{
		List insuranceTypeList = new ArrayList();
		try {
		 insuranceTypeList = credentialDao.queryForInsuranceType(cProfile);
		} catch (DBException ex) {
			logger.info("DB Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.getInsuranceType() due to "
							+ ex.getMessage());
		}
		return insuranceTypeList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO#getStateList()
	 */
	public List getStateList() throws BusinessServiceException {
		List stateList = new ArrayList();
		try {
			stateList = lookupDAO.loadStates();
		} catch (DBException ex) {
			logger.info("DB Exception @InsurancePolicyBOImpl.getStateList() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @InsurancePolicyBOImpl.getStateList() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.getStateList() due to "
							+ ex.getMessage());
		}
		return stateList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO#loadInsuranceDetails(com.newco.marketplace.vo.provider.CredentialProfile)
	 */
	public CredentialProfile loadInsuranceDetails(
			CredentialProfile cProfile)
			throws BusinessServiceException {

		try {
			
			List additionalInsuranceList=new ArrayList();
			int vendorId = cProfile.getVendorId();
			if(vendorId!=0)
			{
				additionalInsuranceList=insuranceTypesDao.getAdditionalInsuranceList(cProfile);
				cProfile.setAdditionalInsuranceList(additionalInsuranceList);
			}
			
			if (vendorId != 0 && cProfile.getCredentialId() > 0) {

				int credId=cProfile.getCredentialId();
				cProfile = credentialDao
						.queryInsuranceSec(cProfile);
				cProfile.setCredentialId(credId);
				cProfile.setMapCredentialType(insuranceTypesDao.getAdditionalMapCategory(cProfile));
				VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();

				myVCredentialsDocumentVO.setVendorCredId(cProfile.getCredentialId());
				logger.info("looking for a document cred_id = ------>"
						+ cProfile.getVendorCredId());
				VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.getMaxVendorCredentialDocument(myVCredentialsDocumentVO);
														

				if (avo != null) {
					cProfile.setCurrentDocumentID(avo.getDocumentId());

					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					logger.info("loaded document for edit ------>"
							+ avo.getDocumentId());
					aDoc = documentBO.retrieveDocumentMetadataByDocumentId(aDoc);
					if(null!=aDoc){
						cProfile.setCurrentDocumentTS(aDoc
								.getLastUpdateTimestamp());
						cProfile.setCredentialDocumentFileName(aDoc.getFileName());
						cProfile.setCredentialDocumentExtention(aDoc.getFormat());
						cProfile.setCredentialDocumentFileSize(aDoc.getDocSize());
	
						logger.info(aDoc.getLastUpdateTimestamp()
								+ " is the timestamp I got for document "
								+ aDoc.getDocumentId());
				} else {
					cProfile.setCurrentDocumentID(0);
				}

			}
		}
		}catch (DBException ex) {
			logger.info("DB Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to "
							+ ex.getMessage());
		}
		return cProfile;
	}
	
	/* 
	 * method to load Insurance Details and last updated Document for the vendor
	 * 
	 */
	public CredentialProfile loadInsuranceDetailsWithMaxVendorDocument(
			CredentialProfile cProfile, String buttonType)
			throws BusinessServiceException {

		try {
			int vendorId = cProfile.getVendorId();
			if(OrderConstants.BUTTON_TYPE_EDIT.equals(buttonType)){			


			if (vendorId != 0 && cProfile.getCredentialId() > 0) {

				int credId=cProfile.getCredentialId();
				cProfile = credentialDao
						.queryInsuranceSec(cProfile);
				cProfile.setCredentialId(credId);

				VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();

				myVCredentialsDocumentVO.setVendorCredId(cProfile.getCredentialId());
				logger.info("looking for a document cred_id = ------>"
						+ cProfile.getVendorCredId());
				VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.getLastUploadedDocument(vendorId);
				
				
				cProfile.setMapCredentialType(insuranceTypesDao.getAdditionalMapCategory(cProfile));										

				if (avo != null) {
					cProfile.setCurrentDocumentID(avo.getDocumentId());

					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					logger.info("loaded document for edit ------>"
							+ avo.getDocumentId());
					aDoc = documentBO.retrieveDocumentMetadataByDocumentId(aDoc);
					if( aDoc!= null){
					cProfile.setCurrentDocumentTS(aDoc
							.getLastUpdateTimestamp());
					cProfile.setCredentialDocumentFileName(aDoc.getFileName());
					cProfile.setCredentialDocumentExtention(aDoc.getFormat());
					cProfile.setCredentialDocumentFileSize(aDoc.getDocSize());

					logger.info(aDoc.getLastUpdateTimestamp()
							+ " is the timestamp I got for document "
							+ aDoc.getDocumentId());
					}
				} else {
					cProfile.setCurrentDocumentID(0);
				}				

			}
			}else if(OrderConstants.BUTTON_TYPE_ADD.equals(buttonType)){
				VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.getLastUploadedDocument(vendorId);
				cProfile.setMapCredentialType(insuranceTypesDao.getAdditionalMapCategory(cProfile));

				if (avo != null) {
					cProfile.setCurrentDocumentID(avo.getDocumentId());

					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					logger.info("loaded document for edit ------>"
							+ avo.getDocumentId());
					aDoc = documentBO.retrieveDocumentMetadataByDocumentId(aDoc);
					
					if(aDoc!=null){
					cProfile.setCurrentDocumentTS(aDoc
							.getLastUpdateTimestamp());
					cProfile.setCredentialDocumentFileName(aDoc.getFileName());
					cProfile.setCredentialDocumentExtention(aDoc.getFormat());
					cProfile.setCredentialDocumentFileSize(aDoc.getDocSize());
					
					
					logger.info(aDoc.getLastUpdateTimestamp()
							+ " is the timestamp I got for document "
							+ aDoc.getDocumentId());
					}
				} else {
					cProfile.setCurrentDocumentID(0);
				}
			}
		} catch (DBException ex) {
			logger.info("DB Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to "
							+ ex.getMessage());
		}
		return cProfile;
	}
	
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public ArrayList<CredentialProfile> loadInsuranceDetailsWithVendorDocuments(
			CredentialProfile cProfile, String buttonType)
			throws BusinessServiceException {
		
		ArrayList<CredentialProfile> cProfile1 = new ArrayList<CredentialProfile>();

		try {
			int vendorId = cProfile.getVendorId();
			
			if(OrderConstants.BUTTON_TYPE_EDIT.equals(buttonType)){		
				
				if (vendorId != 0 && cProfile.getCredentialId() > 0) {

					int credId=cProfile.getCredentialId();
					cProfile = credentialDao.queryInsuranceSec(cProfile);
					
					cProfile.setCredentialId(credId);
					cProfile.setVendorId(vendorId);
					
				List cProfile3 = new ArrayList();
					
				cProfile3 = credentialDao.getVendorUploadedDocuments(cProfile);
				
				if (cProfile3 != null) {
						
				for(int i=0; i<cProfile3.size(); i++){
					
					CredentialProfile cProfile2 = new CredentialProfile();
					
					
						
						CredentialProfile cProfile4 = new CredentialProfile();
						
						cProfile4 = (CredentialProfile) cProfile3.get(i);
					
					
					cProfile2.setCurrentDocumentID(cProfile4.getCurrentDocumentID());
					cProfile2.setCredAmount(cProfile4.getCredAmount());
					cProfile2.setCredentialTypeId(cProfile4.getCredentialTypeId());
					cProfile2.setCredentialCategoryId(cProfile4.getCredentialCategoryId());
					cProfile2.setCredCategory(cProfile4.getCredCategory());
					cProfile2.setCredentialSource(cProfile4.getCredentialSource());
					cProfile2.setCredentialName(cProfile4.getCredentialName());
					cProfile2.setCredentialIssueDate(cProfile4.getCredentialIssueDate());
					cProfile2.setCredentialExpirationDate(cProfile4.getCredentialExpirationDate());
					cProfile2.setCredentialNumber(cProfile4.getCredentialNumber());
					cProfile2.setCredentialCounty(cProfile4.getCredentialCounty());
					cProfile2.setCredentialState(cProfile4.getCredentialState());
					cProfile2.setCredentialCategoryDesc(cProfile4.getCredentialCategoryDesc());
					cProfile2.setCreatedDate(cProfile4.getCreatedDate());
					cProfile2.setStatus(cProfile4.getStatus());
					cProfile2.setCredentialId(credId);

					DocumentVO aDoc1 = new DocumentVO();
					aDoc1.setDocumentId(cProfile4.getCurrentDocumentID());
					logger.info("loaded document for edit ------>"
							+ cProfile4.getCurrentDocumentID());
					aDoc1 = documentBO.retrieveDocumentsInfoByDocumentId(aDoc1);
					if( aDoc1!= null){
					cProfile2.setCurrentDocumentTS(aDoc1.getLastUpdateTimestamp());
					cProfile2.setCredentialDocumentFileName(aDoc1.getFileName());
					cProfile2.setCredentialDocumentExtention(aDoc1.getFormat());
					cProfile2.setCredentialDocumentFileSize(aDoc1.getDocSize());

					logger.info(aDoc1.getLastUpdateTimestamp()
							+ " is the timestamp I got for document "
							+ aDoc1.getDocumentId());
					}
					cProfile1.add(cProfile2);
				}
			}
				
			}else {
				
				CredentialProfile cProfile2 = new CredentialProfile();
				
				cProfile2.setCurrentDocumentID(0);
				cProfile1.add(cProfile2);
			}
			}
			
		} catch (DBException ex) {
			logger.info("DB Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.loadInsuranceDetails() due to "
							+ ex.getMessage());
		}
		return cProfile1;
	}
	
	//SL-21233: Document Retrieval Code Ends
	
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO#saveInsurance(com.newco.marketplace.vo.provider.CredentialProfile)
	 */
	public CredentialProfile saveInsurance(
			CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException {
		VendorCredentialsDocumentVO  vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		int docId = 0;
		try {
				int credentialId = credentialProfile.getCredentialId();
				if (credentialId > 0) {

					credentialDao.updateInsurance(credentialProfile);
					updateInsuranceAmountInVendorHdr(credentialProfile);
					vendorCredentialsDocumentVO.setVendorCredId(credentialId);

					vendorCredentialsDocumentVO = myVendorCredentialsDocumentDAO.
																		isVendorDocumentExistsForInsurance(vendorCredentialsDocumentVO);

					if (vendorCredentialsDocumentVO == null) {
						//If the document does not exists
						if(credentialProfile != null
						&& credentialProfile.getCredentialDocumentFileName() != null
						&& credentialProfile.getCredentialDocumentFileName().trim().length() > 0) {
							docId = InsertDocumentDetails(credentialProfile);
							credentialProfile.setCurrentDocumentID(docId);
						} else if(credentialProfile != null && credentialProfile.getCurrentDocumentID()>0){
							VendorCredentialsDocumentVO vendorCredentialsDocumentVO1 = new VendorCredentialsDocumentVO();
							vendorCredentialsDocumentVO1.setDocumentId(credentialProfile.getCurrentDocumentID());
							vendorCredentialsDocumentVO1.setVendorCredId(credentialId);
							myVendorCredentialsDocumentDAO.insertVendorCredentialDocument(vendorCredentialsDocumentVO1);
						}
					} else {
						//If the document exists
						//Sets the already existing document ID for insertion
						if (credentialProfile.getCredentialDocumentFileName() != null && credentialProfile.getCredentialDocumentFileSize() != 0) {
							credentialProfile.setCurrentDocumentID(vendorCredentialsDocumentVO.getDocumentId());
							docId = updateDocumentDetails(credentialProfile);	
							credentialProfile.setCurrentDocumentID(docId);
						} else if(credentialProfile != null && credentialProfile.getCurrentDocumentID()>0) {
							if(credentialProfile.getCurrentDocumentID()!=vendorCredentialsDocumentVO.getDocumentId()){
								VendorCredentialsDocumentVO vendorCredentialsDocumentVO1 = new VendorCredentialsDocumentVO();
								vendorCredentialsDocumentVO1.setDocumentId(credentialProfile.getCurrentDocumentID());
								vendorCredentialsDocumentVO1.setVendorCredId(credentialId);
								myVendorCredentialsDocumentDAO.insertVendorCredentialDocument(vendorCredentialsDocumentVO1);
							}
						}
					}
				} else {
					/**
					 * Insert Insurance Details
					 */
					CredentialProfile credentialProfile2 = new CredentialProfile();
					credentialProfile2 = credentialDao.insertInsuranceTypes(credentialProfile);
					updateInsuranceAmountInVendorHdr(credentialProfile);
					//Sets the new Credential ID for insertion
					if (credentialProfile2 != null) {
						credentialProfile.setCredentialId(credentialProfile2.getCredentialId());
					}
					
					//set the latest doc id, insert to vendor credentials document
					if(credentialProfile.getCurrentDocumentID()>0 && credentialProfile2 != null){
						VendorCredentialsDocumentVO vendorCredentialsDocumentVO1 = new VendorCredentialsDocumentVO();
						vendorCredentialsDocumentVO1.setDocumentId(credentialProfile.getCurrentDocumentID());
						vendorCredentialsDocumentVO1.setVendorCredId(credentialProfile2.getCredentialId());
						myVendorCredentialsDocumentDAO.insertVendorCredentialDocument(vendorCredentialsDocumentVO1);
					}else{
						if (credentialProfile2 != null
								&&	credentialProfile.getCredentialDocumentFileName() != null
								&& 	credentialProfile.getCredentialDocumentFileName().trim().length() > 0) {
							docId=InsertDocumentDetails(credentialProfile);
							credentialProfile.setCurrentDocumentID(docId);
							vendorCredentialsDocumentVO.setDocumentId(docId);
						}
					}
				}				
				auditVendorCredentials(credentialProfile.getVendorId(), credentialProfile.getCurrentDocumentID(), credentialProfile.getCredentialId());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.saveInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.saveInsurance() due to "
							+ ex.getMessage());
		}

		return credentialProfile;
	}

	private int InsertDocumentDetails(CredentialProfile credentialProfile) throws BusinessServiceException
	{
		DocumentVO documentVO = new DocumentVO();
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		ProcessResponse processResponse = new ProcessResponse();
		int documentId = 0;
		try
		{
			vendorCredentialsDocumentVO.setVendorCredId(credentialProfile.getCredentialId());
			/**
			 * Insert into the Document table
			 */
			documentVO.setVendorId(credentialProfile.getVendorId());
			documentVO.setDescription(credentialProfile.getCredentialName());
			documentVO.setTitle(credentialProfile.getCredentialSource());
			documentVO.setFileName(credentialProfile.getCredentialDocumentFileName());
			documentVO.setFormat(credentialProfile.getCredentialDocumentExtention());
			documentVO.setBlobBytes(credentialProfile.getCredentialDocumentBytes());
			documentVO.setDocSize(credentialProfile.getCredentialDocumentFileSize());

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
			credentialProfile.setCurrentDocumentID(vendorCredentialsDocumentVO.getDocumentId());

		}catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.saveInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.saveInsurance() due to "
							+ ex.getMessage());
		}
		return documentVO.getDocumentId();
	}

	private int updateDocumentDetails(CredentialProfile credentialProfile) throws BusinessServiceException
	{
		DocumentVO documentVO = new DocumentVO();
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		try
		{
			/**
			 * Set Document details for insertion into the Document table
			 */
			documentVO.setVendorId(credentialProfile.getVendorId());
			documentVO.setDescription(credentialProfile.getCredentialName());
			documentVO.setTitle(credentialProfile.getCredentialSource());
			documentVO.setFileName(credentialProfile.getCredentialDocumentFileName());
			documentVO.setFormat(credentialProfile.getCredentialDocumentExtention());
			documentVO.setBlobBytes(credentialProfile.getCredentialDocumentBytes());
			documentVO.setDocumentId(credentialProfile.getCurrentDocumentID());
			documentVO.setDocSize(credentialProfile.getCredentialDocumentFileSize());

			//documentBO.updateDocumentByDocumentId(documentVO);
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
					
			//Adds the Document ID to the DocumentVO
			documentVO.setDocumentId(documentId);
			

			/**
			 * Insert into the vendor_credential_document table.
			 */
			vendorCredentialsDocumentVO.setVendorCredId(credentialProfile.getCredentialId());
			vendorCredentialsDocumentVO.setDocumentId(documentVO.getDocumentId());
			myVendorCredentialsDocumentDAO.updateDocumentInInsuranceCredentialDocument(vendorCredentialsDocumentVO);
			credentialProfile.setCurrentDocumentID(vendorCredentialsDocumentVO.getDocumentId());


		}catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.saveInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.saveInsurance() due to "
							+ ex.getMessage());
		}
		return documentVO.getDocumentId();
	}
	
	public void auditVendorCredentials(int vendorId, int docId, int credentialId) {
		AuditVO auditVO = null;
		try {
			auditVO = new AuditVO(vendorId, 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
			 //defect 60672 when insertinnew stuff here alway set the reviewed by to null so that power Auditor can look at the Audit TASK
			 auditVO.setReviewedBy("");
			//Modified by bnatara to allow Audit update
			//Sears00045656
			if (docId > 0) {
			    auditVO.setDocumentId(docId);
			} else {
				auditVO.setDocumentId(null);
			}

			if (credentialId > 0) {
				auditVO.setAuditKeyId(credentialId);
			}

			getAuditBusinessBean().auditVendorCredentials(auditVO);
		} catch (Exception e) {
			logger.info("[Registration Request Dispatcher] - updateInsurance() - Audit Exception Occured for audit record:" + auditVO.toString()
					+ StackTraceHelper.getStackTrace(e));
		}
	}

	
	
	public void auditVendorCredentialsInsurance(int vendorId, int docId, int credentialId) {
		AuditVO auditVO = null;
		try {
			auditVO = new AuditVO(vendorId, 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
			 //defect 60672 when insertinnew stuff here alway set the reviewed by to null so that power Auditor can look at the Audit TASK
			 auditVO.setReviewedBy("");
			//Modified by bnatara to allow Audit update
			//Sears00045656
			if (docId > 0) {
			    auditVO.setDocumentId(docId);
			} else {
				auditVO.setDocumentId(null);
			}

			if (credentialId > 0) {
				auditVO.setAuditKeyId(credentialId);
			}
			auditVO.setViaAPI(true);
			getAuditBusinessBean().auditVendorCredentials(auditVO);
		} catch (Exception e) {
			logger.info("[Registration Request Dispatcher] - updateInsurance() - Audit Exception Occured for audit record:" + auditVO.toString()
					+ StackTraceHelper.getStackTrace(e));
		}
	}
	
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}
	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO#deleteInsurance(com.newco.marketplace.vo.provider.CredentialProfile)
	 */
	public void deleteInsurance(
			CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException {

		try {

			VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();
			myVCredentialsDocumentVO.setVendorCredId(credentialProfile.getCredentialId());
			VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.
												getMaxVendorCredentialDocument(myVCredentialsDocumentVO);
			if (avo != null) {
				avo.setVendorCredId(credentialProfile.getCredentialId());
				myVendorCredentialsDocumentDAO.removeCredentialVendorDocument(avo);
				documentBO.deleteVendorDocument(avo.getDocumentId());
			}
			if(credentialProfile.getCredentialId()>0){
				credentialDao.delete(credentialProfile);
			}
		} catch (DBException ex) {
			logger
					.info("DB Exception @InsurancePolicyBOImpl.deleteInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.deleteInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.deleteInsurance() due to "
							+ ex.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO#viewDocument(com.newco.marketplace.vo.provider.CredentialProfile)
	 */
	public CredentialProfile viewDocument(
			CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException {

		try {

			DocumentVO documentVO = new DocumentVO();
			documentVO.setDocumentId(credentialProfile.getCurrentDocumentID());

			Integer documentID = new Integer(credentialProfile.getCurrentDocumentID());

			documentVO = documentBO.retrieveVendorDocumentByDocumentId(documentID);

			if (documentVO != null)
			{
				credentialProfile.setCredentialDocumentBytes(documentVO.getBlobBytes());
				credentialProfile.setCredentialDocumentFileName(documentVO.getFileName());
				credentialProfile.setCredentialDocumentFileSize(documentVO.getDocSize());
				credentialProfile.setCredentialDocumentExtention(documentVO.getFormat());
			}

		}catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.deleteInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.deleteInsurance() due to "
							+ ex.getMessage());
		}
		return credentialProfile;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO#removeDocument(com.newco.marketplace.vo.provider.CredentialProfile)
	 */
	public void removeDocument(CredentialProfile credentialProfile)
				throws BusinessServiceException, FileNotFoundException,
				IOException, ParseException {

		try {
				VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();
				myVCredentialsDocumentVO.setVendorCredId(credentialProfile.getCredentialId());
				VendorCredentialsDocumentVO avo = myVendorCredentialsDocumentDAO.
													getMaxVendorCredentialDocument(myVCredentialsDocumentVO);

				if (avo != null) {
					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					avo.setVendorCredId(credentialProfile.getCredentialId());

					Integer documentID = new Integer(avo.getDocumentId());

					AuditVO auditVO = null;
					try
					{
						auditVO = new AuditVO(credentialProfile.getVendorId(), 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
						//Modified by bnatara to allow Audit update
						//Sears00045656

						auditVO.setDocumentId(null);

						if (credentialProfile.getCredentialId() > 0) {
							auditVO.setAuditKeyId(credentialProfile.getCredentialId());
						}
						getAuditBusinessBean().auditVendorCredentials(auditVO);
					} catch (Exception e) {
						logger.info("[Registration Request Dispatcher] - updateInsurance() - Audit Exception Occured for audit record:" + auditVO.toString()
								+ StackTraceHelper.getStackTrace(e));
					}

					myVendorCredentialsDocumentDAO.removeCredentialVendorDocument(avo);
					documentBO.deleteVendorDocument(documentID);
				}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.deleteInsurance() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.deleteInsurance() due to "
							+ ex.getMessage());
		}
	}

	/**
	 * Method to fetch the credential details associated with the selected insurance certificate
	 * @param CredentialProfile cProfile
	 * @param Integer docId
	 * @return CredentialProfile
	 * @throws Exception
	 */
	public CredentialProfile loadInsuranceDetailsForSelectedDocument(CredentialProfile cProfile,Integer docId)
	throws BusinessServiceException {
		try{
			cProfile = credentialDao.queryInsuranceDetailsForSelectedDocument(cProfile,docId);
		}catch (DBException ex) {
			logger.info("DB Exception @InsurancePolicyBOImpl.loadInsuranceDetailsForSelectedDocument() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			logger.info("General Exception @InsurancePolicyBOImpl.loadInsuranceDetailsForSelectedDocument() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @InsurancePolicyBOImpl.loadInsuranceDetailsForSelectedDocument() due to "
					+ ex.getMessage());
		}
		return cProfile;
	}
	//Changes related to SL-20301
	public CredentialProfile loadAdditonalInsuranceDetailsForSelectedDocument(CredentialProfile cProfile,Integer docId)
			throws BusinessServiceException {
				try{
					cProfile = credentialDao.queryAdditonalInsuranceDetailsForSelectedDocument(cProfile,docId);
				}catch (DBException ex) {
					logger.info("DB Exception @InsurancePolicyBOImpl.loadInsuranceDetailsForSelectedDocument() due to"
							+ ex.getMessage());
					throw new BusinessServiceException(ex.getMessage());
				} catch (Exception ex) {
					logger.info("General Exception @InsurancePolicyBOImpl.loadInsuranceDetailsForSelectedDocument() due to"
							+ ex.getMessage());
					throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.loadInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
				}
				return cProfile;
			}
	
	private void updateInsuranceAmountInVendorHdr(CredentialProfile credentialProfile)throws BusinessServiceException
	{
		if(credentialProfile.getCredentialCategoryId()>0){
		if(credentialProfile.getCredentialCategoryId()==OrderConstants.GL_CREDENTIAL_CATEGORY_ID){
			try{
				vendorHdrDao.updateCBGLInsurance(credentialProfile);
			} catch (DBException ex) {
				logger
						.info("DB Exception @InsurancePolicyBOImpl.updateInsuranceAmountInVendorHdr() due to"
								+ ex.getMessage());
				throw new BusinessServiceException(ex.getMessage());
			}	
		}
		if(credentialProfile.getCredentialCategoryId()==OrderConstants.AL_CREDENTIAL_CATEGORY_ID){
			try{
				vendorHdrDao.updateVLInsurance(credentialProfile);
			} catch (DBException ex) {
				logger
						.info("DB Exception @InsurancePolicyBOImpl.updateInsuranceAmountInVendorHdr() due to"
								+ ex.getMessage());
				throw new BusinessServiceException(ex.getMessage());
			}	
		}
		if(credentialProfile.getCredentialCategoryId()==OrderConstants.WC_CREDENTIAL_CATEGORY_ID){
			try{
				vendorHdrDao.updateWCInsurance(credentialProfile);
			} catch (DBException ex) {
				logger
						.info("DB Exception @InsurancePolicyBOImpl.updateInsuranceAmountInVendorHdr() due to"
								+ ex.getMessage());
				throw new BusinessServiceException(ex.getMessage());
			}	
		}
	}
	}
	        
	        

	private IAuditBO getAuditBusinessBean() {

		if (auditBusinessBean == null) {
		auditBusinessBean = (IAuditBO) MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.BUSINESSBEAN_AUDIT);
		}
		return (auditBusinessBean);
		}


}
