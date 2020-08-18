/*
 ** TeamProfileBusinessBean.java     1.0     2007/06/05
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.ITeamCredentialBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IResourceCredentialsDocumentDAO;
import com.newco.marketplace.persistence.iDao.provider.ITeamCredentialsDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.ResourceCredentialsDocumentVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class TeamCredentialsBOImpl extends ABaseBO implements ITeamCredentialBO {
	private IVendorResourceDao iVendorResourceDao;
	private ITeamCredentialsDao iTeamCredentialsDao;
	private IResourceCredentialsDocumentDAO iResourceCredentialsDocumentDAO;
	private ILookupDAO iLookupDAO;
	private final IActivityRegistryDao activityRegistryDao;
	private final IDocumentBO documentBO;
	private IAuditBO auditBusinessBean;

	public TeamCredentialsBOImpl(IVendorResourceDao iVendorResourceDao,
			ITeamCredentialsDao iTeamCredentialsDao,
			IResourceCredentialsDocumentDAO iResourceCredentialsDocumentDAO,
			ILookupDAO iLookupDAO,
			IActivityRegistryDao activityRegistryDao,
			IDocumentBO documentBO) {
		this.iVendorResourceDao = iVendorResourceDao;
		this.iTeamCredentialsDao = iTeamCredentialsDao;
		this.iResourceCredentialsDocumentDAO = iResourceCredentialsDocumentDAO;
		this.iLookupDAO = iLookupDAO;
		this.activityRegistryDao=activityRegistryDao;
		this.documentBO = documentBO;
	}

	private static final Logger logger = Logger
			.getLogger(TeamCredentialsBOImpl.class.getName());

	public List<TeamCredentialsLookupVO> getTypeList()
			throws BusinessServiceException {
		List<TeamCredentialsLookupVO> typeList = new ArrayList();
		try {
			typeList.addAll(iTeamCredentialsDao.queryAllType());
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
        return typeList;
	}// end method

	public List<TeamCredentialsLookupVO> getCatListByTypeId(
			TeamCredentialsLookupVO teamCredentialsLookupVO)
			throws BusinessServiceException {
		List<TeamCredentialsLookupVO> typeList = new ArrayList();

		try {
			typeList.addAll(iTeamCredentialsDao
					.queryCatByTypeId(teamCredentialsLookupVO.getTypeId()));
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] "
					+ StackTraceHelper.getStackTrace(dse));
			throw new BusinessServiceException(dse);
		}
		return typeList;
	}// end method

	public List<TeamCredentialsVO> getCredListByResourceId(
			TeamCredentialsVO teamCredentialsVO)
			throws BusinessServiceException {
		List<TeamCredentialsVO> result = null;
		try {
			result = iTeamCredentialsDao
					.queryCredByResourceId(teamCredentialsVO.getResourceId());
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return result;
	}// end method

	public TeamCredentialsVO getCredById(TeamCredentialsVO vo)
			throws BusinessServiceException {
		TeamCredentialsVO responseVO = new TeamCredentialsVO();
		logger.debug(" getCredById retrieving vo by resourceCredId: "
				+ vo.getResourceCredId());
		logger.debug(" getCredById retrieving vo by resourceId: "
				+ vo.getResourceId());

		try {
			responseVO = iTeamCredentialsDao.queryCredByIdSec(vo);
			logger
					.info("getCredByID retrieving document id for ResourceCredId: "
							+ responseVO.getResourceCredId());
			/**
			 * Variable added for Document Upload
			 */
			ResourceCredentialsDocumentVO resourceCredentialsDocumentVO = new ResourceCredentialsDocumentVO();
			resourceCredentialsDocumentVO.setResourceCredId(vo.getResourceCredId());
			ResourceCredentialsDocumentVO credentialsDocumentVO = iResourceCredentialsDocumentDAO.
															getMaxResourceCredentialDocument(resourceCredentialsDocumentVO);

			if (credentialsDocumentVO != null)
			{
				responseVO.setCredentialDocumentId(credentialsDocumentVO.getDocumentId());
				DocumentVO documentVO = new DocumentVO();
				documentVO.setDocumentId(credentialsDocumentVO.getDocumentId());

				documentVO = documentBO.retrieveResourceDocMetadataByDocumentId(documentVO);
				if(null!=documentVO){
					responseVO.setCredentialDocumentFileName(documentVO.getFileName());
					responseVO.setCredentialDocumentExtention(documentVO.getFormat());
					responseVO.setCredentialDocumentFileSize(documentVO.getDocSize());
				}else{
					responseVO.setCredentialDocumentId(0);
				}
			}
			else
			{
				responseVO.setCredentialDocumentId(0);
			}

		} catch (DBException dbe) {
			throw new BusinessServiceException(dbe);
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] "
					+ StackTraceHelper.getStackTrace(dse));
			throw new BusinessServiceException(dse);
		}catch(Exception a_Ex){
			throw new BusinessServiceException(a_Ex);
		}

		return responseVO;
	}// end method

	public TeamCredentialsVO insertCredentials(TeamCredentialsVO vo)
			throws BusinessServiceException {
		try {
			TeamCredentialsVO credentialsVO = new TeamCredentialsVO();
			credentialsVO = iTeamCredentialsDao.insert(vo);
			
			int docID = 0;
			logger.debug("insertCredentials: ResourceCred"
					+ vo.getResourceCredId());
			activityRegistryDao.updateResourceActivityStatus(vo.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
			/**
			 * Inserting Document details
			 */
			//Modified by bnatara to allow Audit insert
			//Sears00045656
			if (credentialsVO != null)
				vo.setResourceCredId(credentialsVO.getResourceCredId());

			if (credentialsVO != null
			&&	vo.getCredentialDocumentFileName() != null
			&&	vo.getCredentialDocumentFileName().trim().length() > 0)
			{
				docID = InsertDocumentDetails(vo);
				vo.setCredentialDocumentId(docID);
			}
			
			AuditVO auditVO = null;
			try {

				//Modified by bnatara to allow Audit insert
				//Sears00045656
				auditVO = new AuditVO(vo.getVendorId(), 0, AuditStatesInterface.RESOURCE_CREDENTIAL, AuditStatesInterface.RESOURCE_CREDENTIAL_PENDING_APPROVAL);
				 //defect 60672 when insertinnew stuff here alway set the reviewed by to null so that power Auditor can look at the Audit TASK
				 auditVO.setReviewedBy("");
				//Modified by bnatara to allow Audit insert
				//Sears00045656
				if (vo != null && vo.getCredentialDocumentId() > 0){
				    auditVO.setDocumentId(vo.getCredentialDocumentId());
				}
				else{
					auditVO.setDocumentId(null);
				}

				if (vo.getResourceCredId() > 0) {
					auditVO.setAuditKeyId(vo.getResourceCredId());
				}

				if (vo != null)
				{
					auditVO.setResourceId(vo.getResourceId());
				}
				//SL -21142 lms file upload integration.
				if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					 //auto approving lms file uploaded credentials.
					 auditVO.setWfState(AuditStatesInterface.RESOURCE_CREDENTIAL_APPROVED);
                     auditVO.setReasonId(new Integer(70));
				 }
				
					//Modified by offshore to update proper audit link id and audit link key in audit task table
					getAuditBusinessBean().auditResourceCredentials(auditVO);
                    if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
						//set reason code and
						getAuditBusinessBean().saveVendorOrResourceCredReasonCd(auditVO);
					 }
				} catch (Exception e) {
					logger.info("[TeamCredentialsBOImpl] - insertCredentials() - Audit Exception Occured for audit record:" + auditVO.toString()
							+ e.getMessage());
					throw new BusinessServiceException(e);
				}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
		return vo;
	}// end method

	public TeamCredentialsVO removeCredentials(TeamCredentialsVO vo)
			throws BusinessServiceException {
		try {

			/**
			 * Code Added to remove the Document details in resource credentials document table.
			 */
			ResourceCredentialsDocumentVO resourceDoc = new ResourceCredentialsDocumentVO();
			resourceDoc.setResourceCredId(vo.getResourceCredId());
			ResourceCredentialsDocumentVO avo = iResourceCredentialsDocumentDAO.
													getMaxResourceCredentialDocument(resourceDoc);

			if (avo != null) {
				avo.setResourceCredId(vo.getResourceCredId());
				iResourceCredentialsDocumentDAO.deleteResourceCredentialDocument(avo);
				documentBO.deleteResourceDocument(avo.getDocumentId());
			}
			vo = iTeamCredentialsDao.delete(vo);
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}catch(Exception a_Ex){
			throw new BusinessServiceException(a_Ex);
		}
		return vo;
	}// end method

	public void updateCredentials(TeamCredentialsVO vo)
			throws BusinessServiceException {
		ResourceCredentialsDocumentVO resourceCredentialsDocumentVO = new ResourceCredentialsDocumentVO();
		int docId=0;
		try {
			iTeamCredentialsDao.update(vo);
			activityRegistryDao.updateResourceActivityStatus(vo.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
			resourceCredentialsDocumentVO.setResourceCredId(vo.getResourceCredId());
			resourceCredentialsDocumentVO = iResourceCredentialsDocumentDAO.getMaxResourceCredentialDocument(resourceCredentialsDocumentVO);
			
			//Modified by bnatara to allow Audit update
			//Sears00045656
			if (resourceCredentialsDocumentVO == null)
			{
				if (vo != null
				&&	vo.getCredentialDocumentFileName() != null
				&&  vo.getCredentialDocumentFileName().trim().length() > 0)
				{
					docId = InsertDocumentDetails(vo);
				}
			}
			else
			{
				if (vo != null
				&&	vo.getCredentialDocumentFileName() != null
				&&  vo.getCredentialDocumentFileName().trim().length() > 0)
				{
					vo.setCredentialDocumentId(resourceCredentialsDocumentVO.getDocumentId());
					docId = updateDocumentDetails(vo);
				}
			}

			//Modified by bnatara to allow Audit update
			//Sears00045656
			AuditVO auditVO = null;
			try {
				 auditVO = new AuditVO(vo.getVendorId(), 0, AuditStatesInterface.RESOURCE_CREDENTIAL, AuditStatesInterface.RESOURCE_CREDENTIAL_PENDING_APPROVAL);
				if (docId > 0){
				    auditVO.setDocumentId(docId);
				}
				if (vo.getResourceCredId() > 0) {
				auditVO.setAuditKeyId(vo.getResourceCredId());
				}

				if (vo != null)
					auditVO.setResourceId(vo.getResourceId());
				//SL -21142 lms file upload integration.
				if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					 //auto approving lms file uploaded credentials.
					 auditVO.setWfState(AuditStatesInterface.RESOURCE_CREDENTIAL_APPROVED);
                     auditVO.setReasonId(new Integer(70));
				 }
				//Modified by offshore to update proper audit link id and audit link key in audit task table
			    getAuditBusinessBean().auditResourceCredentials(auditVO);
				if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					//set reason code and
					getAuditBusinessBean().deleteVendorOrResourceCredReasonCd(auditVO);
					getAuditBusinessBean().saveVendorOrResourceCredReasonCd(auditVO);
				 }
				} catch (Exception e) {
				logger.info("[TeamCredentialsBOImpl] - updateCredentials() - Audit Exception Occured for audit record:" + auditVO.toString()
				+ StackTraceHelper.getStackTrace(e));
				}



		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
	}// end method

	public VendorResource queryResourceById(VendorResource vendorResource)
			throws BusinessServiceException {
		logger.debug("queryResourceById retrieving Resource ID: "
				+ vendorResource.getResourceId());
		VendorResource vdrResource = null;
		try {
			vdrResource = iVendorResourceDao.query(vendorResource);


		} catch (DBException dbe) {
			throw new BusinessServiceException(dbe);
		}
		return vdrResource;
	}

	public void updateResource(VendorResource vendorResource)
			throws BusinessServiceException {
		logger.debug("updateResource saving Resource ID: "
				+ vendorResource.getResourceId());
		try {
			iVendorResourceDao.update(vendorResource);

			/**
			 * Added to Update Incomplete status.
			 * Fix for Sears00045965.
			 */
			//If will be executed when the User Checks the 'I do not wish..'
			//Else will be executed when the user UnChecks the 'I do not wish..'
			if (vendorResource.getNoCredInd())
				activityRegistryDao.updateResourceActivityStatus(vendorResource.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
			else
				activityRegistryDao.updateResourceActivityStatus(vendorResource.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS, ActivityRegistryConstants.ACTIVITY_STATUS_INCOMPLETE);

		} catch (DBException dbe) {
			throw new BusinessServiceException(dbe);
		}catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}

	}


	public TeamCredentialsVO viewDocument(
			TeamCredentialsVO teamVO)
			throws BusinessServiceException, FileNotFoundException,
			IOException {

		try {

			DocumentVO documentVO = new DocumentVO();
			documentVO.setDocumentId(teamVO.getCredentialDocumentId());

			Integer documentID = new Integer(teamVO.getCredentialDocumentId());

			documentVO = documentBO.retrieveResourceDocumentByDocumentId(documentID);

			if (documentVO != null)
			{
				teamVO.setCredentialDocumentBytes(documentVO.getBlobBytes());
				teamVO.setCredentialDocumentFileName(documentVO.getFileName());
				teamVO.setCredentialDocumentFileSize(documentVO.getDocSize());
				teamVO.setCredentialDocumentExtention(documentVO.getFormat());
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
		return teamVO;
	}

	public void removeDocument(TeamCredentialsVO teamVO)
				throws BusinessServiceException, FileNotFoundException,
				IOException{

		try {
				ResourceCredentialsDocumentVO resourceDoc = new ResourceCredentialsDocumentVO();
				resourceDoc.setResourceCredId(teamVO.getResourceCredId());
				ResourceCredentialsDocumentVO avo = iResourceCredentialsDocumentDAO.
														getMaxResourceCredentialDocument(resourceDoc);

				if (avo != null) {
					DocumentVO aDoc = new DocumentVO();
					aDoc.setDocumentId(avo.getDocumentId());
					avo.setResourceCredId(teamVO.getResourceCredId());

					Integer documentID = new Integer(avo.getDocumentId());

					//Modified by bnatara to allow Audit update
					//Sears00045656
					AuditVO auditVO = null;
					try
					{
						auditVO = new AuditVO(teamVO.getVendorId(), 0, AuditStatesInterface.RESOURCE_CREDENTIAL, AuditStatesInterface.RESOURCE_CREDENTIAL_PENDING_APPROVAL);

						auditVO.setDocumentId(null);

						if (teamVO.getResourceCredId() > 0) {
							auditVO.setAuditKeyId(teamVO.getResourceCredId());
						}
						//modified by offshore to add resource id in the audit task table
						if (teamVO != null)
							auditVO.setResourceId(teamVO.getResourceId());

						//Modified by offshore to update proper audit link id and audit link key in audit task table
						getAuditBusinessBean().auditResourceCredentials(auditVO);


					}catch (Exception e) {
						logger.info("[TeamCredentialsBOImpl] - updateCredentials() - Audit Exception Occured for audit record:" + auditVO.toString()
						+ StackTraceHelper.getStackTrace(e));
					}

					iResourceCredentialsDocumentDAO.deleteResourceCredentialDocument(avo);
					documentBO.deleteResourceDocument(documentID);

				}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.removeDocument() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.removeDocument() due to "
							+ ex.getMessage());
		}
	}

	private int InsertDocumentDetails(TeamCredentialsVO teamVO) throws BusinessServiceException
	{
		DocumentVO documentVO = new DocumentVO();
		ResourceCredentialsDocumentVO resourceVO = new ResourceCredentialsDocumentVO();
		ProcessResponse processResponse = new ProcessResponse();
		int documentId = 0;
		try
		{
			resourceVO.setResourceCredId(teamVO.getResourceCredId());
			/**
			 * Insert into the Document table
			 */
			documentVO.setVendorId(teamVO.getVendorId());
			documentVO.setFileName(teamVO.getCredentialDocumentFileName());
			documentVO.setFormat(teamVO.getCredentialDocumentExtention());
			documentVO.setBlobBytes(teamVO.getCredentialDocumentBytes());
			documentVO.setDocSize(teamVO.getCredentialDocumentFileSize());

//			documentVO = documentBO.insertVendorDoc(documentVO);
			processResponse = documentBO.insertResourceDocument(documentVO);
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

			resourceVO.setDocumentId(documentVO.getDocumentId());
			teamVO.setCredentialDocumentId(documentVO.getDocumentId());

			/**
			 * Insert into the vendor_credential_document table.
			 */

			iResourceCredentialsDocumentDAO.insert(resourceVO);

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

	private int updateDocumentDetails(TeamCredentialsVO teamVO) throws BusinessServiceException
	{
		DocumentVO documentVO = new DocumentVO();
		ResourceCredentialsDocumentVO resourceVO = new ResourceCredentialsDocumentVO();
		ProcessResponse processResponse = new ProcessResponse();
		int documentId = 0;
		try
		{
			/**
			 * Set Document details for insertion into the Document table
			 */
			documentVO.setVendorId(teamVO.getVendorId());
			documentVO.setFileName(teamVO.getCredentialDocumentFileName());
			documentVO.setFormat(teamVO.getCredentialDocumentExtention());
			documentVO.setBlobBytes(teamVO.getCredentialDocumentBytes());
			documentVO.setDocumentId(teamVO.getCredentialDocumentId());
			documentVO.setDocSize(teamVO.getCredentialDocumentFileSize());
			processResponse = documentBO.insertResourceDocument(documentVO);
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
			
			documentVO.setDocumentId(documentId);
			resourceVO.setResourceCredId(teamVO.getResourceCredId());
			resourceVO.setDocumentId(documentId);
			iResourceCredentialsDocumentDAO.updateResourceCredentialDocument(resourceVO);
			
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

	public List<LookupVO> getStateList() throws BusinessServiceException {

		List<LookupVO> stateList = new ArrayList<LookupVO>();
		try {
			stateList = iLookupDAO.loadStates();
		} catch (DBException dbe) {
			throw new BusinessServiceException(dbe);
		}
		return stateList;
	}

	public IVendorResourceDao getIVendorResourceDao() {
		return iVendorResourceDao;
	}

	public void setIVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		iVendorResourceDao = vendorResourceDao;
	}

	public ITeamCredentialsDao getITeamCredentialsDao() {
		return iTeamCredentialsDao;
	}

	public void setITeamCredentialsDao(ITeamCredentialsDao teamCredentialsDao) {
		iTeamCredentialsDao = teamCredentialsDao;
	}

	public IResourceCredentialsDocumentDAO getIResourceCredentialsDocumentDAO() {
		return iResourceCredentialsDocumentDAO;
	}

	public void setIResourceCredentialsDocumentDAO(
			IResourceCredentialsDocumentDAO resourceCredentialsDocumentDAO) {
		iResourceCredentialsDocumentDAO = resourceCredentialsDocumentDAO;
	}

	public ILookupDAO getILookupDAO() {
		return iLookupDAO;
	}

	public void setILookupDAO(ILookupDAO lookupDAO) {
		iLookupDAO = lookupDAO;
	}

	private IAuditBO getAuditBusinessBean() {

		if (auditBusinessBean == null) {
		auditBusinessBean = (IAuditBO) MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.BUSINESSBEAN_AUDIT);
		}
		return (auditBusinessBean);
		}

	public TeamCredentialsVO getResourceName(TeamCredentialsVO vo){
		TeamCredentialsVO dbTeamCredentialsVO = new TeamCredentialsVO();

		try {
			dbTeamCredentialsVO =iTeamCredentialsDao.getResourceName(vo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
			.info("General Exception @TeamCredentialsBOImpl.getResourceName() due to"
					+ ex.getMessage());
		}

		return dbTeamCredentialsVO;
	}
	
	//21142 - lms credential upload
	public Integer checkTeamCredExists(TeamCredentialsVO teamCredentialsVO) throws BusinessServiceException{
		Integer teamCredID = null;
		Integer result = null;
		try{
			teamCredID =iTeamCredentialsDao.queryForCredExists(teamCredentialsVO);
			if(teamCredID != null && teamCredID > 0){
				result = teamCredID;
			}
		}catch (DataServiceException e) {
			logger.error("Exception in chekcing resource credential exists: "+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return result;
	}
	
	public TeamCredentialsLookupVO getTeamCredLookup(String credDesc) throws BusinessServiceException{
		TeamCredentialsLookupVO vo = null;
		try {
			vo = iTeamCredentialsDao.getTeamCredLookup(credDesc);} 
		catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		} catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
		
		return vo;
	}
	
	public List<TeamCredentialsLookupVO> getCatListByType(TeamCredentialsLookupVO teamCredentialsLookupVO) throws BusinessServiceException {
		List<TeamCredentialsLookupVO> typeList = new ArrayList<TeamCredentialsLookupVO>();

		try {
			typeList.addAll(iTeamCredentialsDao.queryCatByType(teamCredentialsLookupVO.getTypeDesc()));
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] " + StackTraceHelper.getStackTrace(dse));
			throw new BusinessServiceException(dse);
		}
		return typeList;
	}
	
	//method ends

}// end class
