package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IVendorCredentialBO;
import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsLookupVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDocumentDAO;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.AuditVO;

public class VendorCredentialsBOImpl extends ABaseBO implements IVendorCredentialBO {

	private final IVendorCredentialsDao iVendorCredentialsDao;
	private final IActivityRegistryDao activityRegistryDao;
	private IAuditBO auditBusinessBean;
	private final IVendorCredentialsDocumentDAO iVendorCredentialsDocumentDAO;
	
	private static final Logger logger = Logger
			.getLogger(VendorCredentialsBOImpl.class.getName());
	
	
	
	
	public VendorCredentialsBOImpl(IVendorCredentialsDao iVendorCredentialsDao,
			IActivityRegistryDao activityRegistryDao,
			IVendorCredentialsDocumentDAO iVendorCredentialsDocumentDAO) {
		super();
		this.iVendorCredentialsDao = iVendorCredentialsDao;
		this.activityRegistryDao = activityRegistryDao;
		this.iVendorCredentialsDocumentDAO = iVendorCredentialsDocumentDAO;
	}



	public boolean uploadLMSCreadentials(String fileName)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return false;
	}



	public List<VendorCredentialsLookupVO> getCatListByTypeId(
			VendorCredentialsLookupVO vendorCredentialsLookupVO)
			throws BusinessServiceException {
		List<VendorCredentialsLookupVO> typeList = new ArrayList<VendorCredentialsLookupVO>();

		try {
			typeList.addAll(iVendorCredentialsDao
					.queryCatByTypeId(vendorCredentialsLookupVO.getTypeId()));
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] "
					+ StackTraceHelper.getStackTrace(dse));
			throw new BusinessServiceException(dse);
		}
		return typeList;
	}
	
	public List<VendorCredentialsLookupVO> getCatListByType(
			VendorCredentialsLookupVO vendorCredentialsLookupVO)
			throws BusinessServiceException {
		List<VendorCredentialsLookupVO> typeList = new ArrayList<VendorCredentialsLookupVO>();

		try {
			typeList.addAll(iVendorCredentialsDao
					.queryCatByType(vendorCredentialsLookupVO.getTypeDesc()));
		} catch (DataServiceException dse) {
			logger.info("[DataServiceException] "
					+ StackTraceHelper.getStackTrace(dse));
			throw new BusinessServiceException(dse);
		}
		return typeList;
	}



	public List<VendorCredentialsLookupVO> getTypeList()
			throws BusinessServiceException {
		List<VendorCredentialsLookupVO> typeList = new ArrayList<VendorCredentialsLookupVO>();
		try {
			typeList.addAll(iVendorCredentialsDao.queryAllType());
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
        return typeList;
	}
	
	public Integer checkVendorCredExists(VendorCredentialsVO vendorCredentialsVO) throws BusinessServiceException{
		Integer vendorCredID = null;
		Integer result = null;
		try{
			vendorCredID =iVendorCredentialsDao.queryForCredExists(vendorCredentialsVO);
			if(vendorCredID != null && vendorCredID >0){
				result = vendorCredID;
			}
		}catch (DataServiceException e) {
			logger.error("Exception in checking provider credential exists: "+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return result;
	}
	
	
	public VendorCredentialsVO insertCredentials(VendorCredentialsVO vo)
			throws BusinessServiceException {
		try {
			VendorCredentialsVO credentialsVO = new VendorCredentialsVO();
			credentialsVO = iVendorCredentialsDao.insert(vo);

			int docID = 0;
			logger.debug("insertCredentials: ResourceCred"
					+ vo.getVendorCredId());
			activityRegistryDao.updateResourceActivityStatus(vo.getVendorId()
					+ "", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
			/**
			 * Inserting Document details
			 */
			// Modified by bnatara to allow Audit insert
			// Sears00045656
			if (credentialsVO != null)
				vo.setVendorCredId(credentialsVO.getVendorCredId());

			/*if (credentialsVO != null
					&& vo.getCredentialDocumentFileName() != null
					&& vo.getCredentialDocumentFileName().trim().length() > 0) {
				docID = InsertDocumentDetails(vo);
				vo.setCredentialDocumentId(docID);
			}*/

			AuditVO auditVO = null;
			try {

				// Modified by bnatara to allow Audit insert
				// Sears00045656
				auditVO = new AuditVO(
						vo.getVendorId(),
						0,
						AuditStatesInterface.VENDOR_CREDENTIAL,
						AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
				// defect 60672 when insertinnew stuff here alway set the
				// reviewed by to null so that power Auditor can look at the
				// Audit TASK
				auditVO.setReviewedBy("");
				// Modified by bnatara to allow Audit insert
				// Sears00045656
				if (vo != null && vo.getCredentialDocumentId() > 0) {
					auditVO.setDocumentId(vo.getCredentialDocumentId());
				} else {
					auditVO.setDocumentId(null);
				}

				if (vo.getVendorCredId() > 0) {
					auditVO.setAuditKeyId(vo.getVendorCredId());
				}

				if (vo != null) {
					auditVO.setResourceId(new Integer(0));
				}
				//SL -21142 lms file upload integration.
				if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					//auto approving vendor credentials
					auditVO.setWfState(AuditStatesInterface.VENDOR_CREDENTIAL_APPROVED);
					auditVO.setViaAPI(true); // setting viaAPI flag to call updateStatusVendorCredentialById to avoid race condition
                    auditVO.setReasonId(new Integer(68));
				}
				// Modified by offshore to update proper audit link id and audit
				// link key in audit task table
				getAuditBusinessBean().auditVendorCredentials(auditVO);
                if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					//set reason code and
					getAuditBusinessBean().saveVendorOrResourceCredReasonCd(auditVO);
				 }
			} catch (Exception e) {
				logger.info("[VendorCredentialsBOImpl] - insertCredentials() - Audit Exception Occured for audit record:"
						+ auditVO.toString() + e.getMessage());
				throw new BusinessServiceException(e);
			}
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		} catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
		return vo;
	}// end method
	
	public void updateCredentials(VendorCredentialsVO vo)
			throws BusinessServiceException {
		VendorCredentialsDocumentVO vendorCredentialsDocumentVO = new VendorCredentialsDocumentVO();
		int docId = 0;
		try {
			iVendorCredentialsDao.update(vo);
			activityRegistryDao.updateResourceActivityStatus(vo.getVendorId()
					+ "", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
			vendorCredentialsDocumentVO.setVendorCredId(vo
					.getVendorCredId());
			vendorCredentialsDocumentVO = iVendorCredentialsDocumentDAO 
					.getMaxVendorCredentialDocument(vendorCredentialsDocumentVO);
			

			// Modified by bnatara to allow Audit update
			// Sears00045656
			/*if (resourceCredentialsDocumentVO == null) {
				if (vo != null
						&& vo.getCredentialDocumentFileName() != null
						&& vo.getCredentialDocumentFileName().trim().length() > 0) {
					docId = InsertDocumentDetails(vo);
				}
			} else {
				if (vo != null
						&& vo.getCredentialDocumentFileName() != null
						&& vo.getCredentialDocumentFileName().trim().length() > 0) {
					vo.setCredentialDocumentId(resourceCredentialsDocumentVO
							.getDocumentId());
					docId = updateDocumentDetails(vo);
				}
			}*/

			// Modified by bnatara to allow Audit update
			// Sears00045656
			AuditVO auditVO = null;
			try {
				auditVO = new AuditVO(
						vo.getVendorId(),
						0,
						AuditStatesInterface.VENDOR_CREDENTIAL,
						AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
				if (docId > 0) {
					auditVO.setDocumentId(docId);
				}
				if (vo.getVendorCredId() > 0) {
					auditVO.setAuditKeyId(vo.getVendorCredId());
				}

				if (vo != null)
					auditVO.setResourceId(new Integer(0));
				//SL -21142 lms file upload integration.
				if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					//auto approving vendor credentials
					auditVO.setWfState(AuditStatesInterface.VENDOR_CREDENTIAL_APPROVED);
					auditVO.setViaAPI(true); // setting viaAPI flag to call updateStatusVendorCredentialById to avoid race condition
                    auditVO.setReasonId(new Integer(68));
				}
				// Modified by offshore to update proper audit link id and audit
				// link key in audit task table
				getAuditBusinessBean().auditVendorCredentials(auditVO);
                if(vo.getIsFileUploaded()!=null && Boolean.TRUE.equals(vo.getIsFileUploaded())){
					//to insert reason code as verified
					getAuditBusinessBean().deleteVendorOrResourceCredReasonCd(auditVO);
					getAuditBusinessBean().saveVendorOrResourceCredReasonCd(auditVO);
				 }
			} catch (Exception e) {
				logger.info("[VendorCredentialsBOImpl] - updateCredentials() - Audit Exception Occured for audit record:"
						+ auditVO.toString()
						+ StackTraceHelper.getStackTrace(e));
			}

		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		} catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
	}// end method
	
	public VendorCredentialsLookupVO getVendorCredLookup(String credDesc) throws BusinessServiceException{
		VendorCredentialsLookupVO vo = null;
		try {
			vo = iVendorCredentialsDao.getVendorCredLookup(credDesc);} 
		catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		} catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
		
		return vo;
	}

	
	private IAuditBO getAuditBusinessBean() {

		if (auditBusinessBean == null) {
			auditBusinessBean = (IAuditBO) MPSpringLoaderPlugIn.getCtx()
					.getBean(MPConstants.BUSINESSBEAN_AUDIT);
		}
		return (auditBusinessBean);
	}

}
