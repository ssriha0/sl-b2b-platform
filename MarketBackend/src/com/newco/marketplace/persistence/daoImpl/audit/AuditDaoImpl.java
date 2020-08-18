package com.newco.marketplace.persistence.daoImpl.audit;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.persistence.iDao.audit.AuditDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;

public class AuditDaoImpl extends MPBaseDaoImpl implements AuditDao,
        AuditStatesInterface {
    private static final Logger logger = Logger.getLogger(AuditDaoImpl.class.getName());
    private static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");

    public AuditVO getResourceAudit(AuditVO auditVO)
            throws DataServiceException {
        AuditVO rvAuditVO = null;
        try {
            rvAuditVO = (AuditVO) queryForObject(
                    "audit_task.getAuditStatusForResource", auditVO);
        } catch (DataAccessException e) {

            throw new DataServiceException ("[AuditDaoImpl] - getResourceAudit", e);
        }
        return (rvAuditVO);
    }// getAudit

    public String getResourceBackgroundCheckStatus(AuditVO auditVO)
            throws DataServiceException {
        String rvString = (String) queryForObject(
                "audit_task.getResourceBackgroundCheckStatus", auditVO);
        if (null == rvString) {
            throw new DataServiceException(
                    "[AuditDaoImpl]Select query returned null for method getVendorBackgroundCheckStatus");
        }
        return (rvString);
    }// getBackgroundCheckStatus

    public String getStatusByResourceId(AuditVO auditVO)
            throws DataServiceException {

        return (String) queryForObject("audit_task.getStatusByResourceId",
                auditVO);
    }

    public String getStatusByTaskId(AuditVO auditVO)
            throws DataServiceException {

        return (String) queryForObject("audit_task.getStatusByTaskId",
                auditVO);
    }

    public String getStatusByVendorId(AuditVO auditVO)
            throws DataServiceException {

        return (String) queryForObject("audit_task.getStatusByVendorId",
                auditVO);
    }

    public String getStatusResourceCredential(AuditVO auditVO)
            throws DataServiceException {

        String rvString = (String) queryForObject(
                "audit_task.getStatusResourceCredential", auditVO);
//        if (null == rvString) {
//            throw new DataServiceException(
//                    "[AuditDaoImpl]Select query returned null for method selectStatus Resource credential.");
//        }
        return (rvString);
    }

    public String getStatusVendorCredential(AuditVO auditVO)
            throws DataServiceException {

        String rvString = (String) queryForObject(
                "audit_task.getStatusVendorCredential", auditVO);
//        if (null == rvString) {
//            throw new DataServiceException(
//                    "[AuditDaoImpl]Select query returned null for method.");
//        }
        return (rvString);
    }

    public String getStatusVendorResource(AuditVO auditVO)
            throws DataServiceException {

        String rvString = (String) queryForObject(
                "audit_task.getStatusVendorResource", auditVO);
        if (null == rvString) {
            throw new DataServiceException(
                    "[AuditDaoImpl]Select query returned null for method getStatusVendorResource.");
        }
        return (rvString);
    }

    public AuditVO getVendorAudit(AuditVO auditVO)
            throws DataServiceException {

        AuditVO rvAuditVO = null;
        rvAuditVO = (AuditVO) queryForObject(
                "audit_task.getAuditStatusForVendor", auditVO);
        return (rvAuditVO);
    }

    public int getVendorIdByResourceId(int resourceId)
            throws DataServiceException {
        Integer rvInteger  = (Integer) queryForObject(
                "audit_task.getVendorIdByResourceId", resourceId);
        if (null == rvInteger) {
            throw new DataServiceException(
                    "[AuditDaoImpl] Select query returned null for method getVendorIdByResourceId");
        }
        return (rvInteger.intValue());
    }// getVendorIdByResourceId

    public String getVendorStatusDocument(AuditVO auditVO)
            throws DataServiceException {

        String rvString = (String) queryForObject(
                "audit_task.getVendorStatusDocument", auditVO);
        if (null == rvString) {
            throw new DataServiceException(
                    "[AuditDaoImpl]Select query returned null for method getVendorStatusDocument.");
        }
        return (rvString);
    }// getVendorStatusDocument

    public AuditVO insert(AuditVO auditVO) throws DataServiceException {
    	logger.info( "Calling audit_task.insert: " + auditVO );
        return ((AuditVO) insert("audit_task.insert", auditVO));
    }// insert

    public int submitForVendorCompliance(int vendorId)
            throws DataServiceException {

        return update("audit_task.submitForVendorComplianceStatus",
                new AuditVO(vendorId, 0,
                        VENDOR_PENDING_COMPLIANCE_APPROVAL));
    }

    public int updateByResourceId(AuditVO auditVO)
            throws DataServiceException {
        return update("audit_task.updateByResourceId", auditVO);
    }// update

    public int updateByVendorId(AuditVO auditVO) throws DataServiceException {

        return update("audit_task.updateByVendorId", auditVO);
    }// update

    public int updateResourceBackgroundCheckStatus(AuditVO auditVO)
            throws DataServiceException {

        return (update("audit_task.updateResourceBackgroundCheckStatus",
                auditVO));
    }

    public int updateStatusByResourceId(AuditVO auditVO)
            throws DataServiceException {

        return update("audit_task.updateStatusByResourceId", auditVO);
    }// updateStatusByResourceId(AuditVO)

    public int updateStatusByTaskId(AuditVO auditVO)
            throws DataServiceException {

        return update("audit_task.updateStatusByTaskId", auditVO);
    }// updateStatusByResourceId(AuditVO)

    public int updateStatusByVendorId(AuditVO auditVO)
            throws DataServiceException {
        return update("audit_task.updateStatusByVendorId", auditVO);
    }// updateStatusByResourceId(AuditVO)

    public int updateStatusResourceCredential(AuditVO auditVO)
            throws DataServiceException {

        return update("audit_task.updateStatusResourceCredential", auditVO);
    }

    public int updateStatusVendorCredential(AuditVO auditVO)
            throws DataServiceException {

        return update("audit_task.updateStatusVendorCredential", auditVO);
    }

    public int updateStatusVendorHeader(AuditVO auditVO)
            throws DataServiceException {

        return update("audit_task.updateStatusVendorHeader", auditVO);
    }

    public int updateStatusVendorResource(AuditVO auditVO)
            throws DataServiceException {
        return update("audit_task.updateStatusVendorResource", auditVO);
    }

    public String getVendorComplianceStatus(int vendorId)
            throws DataServiceException {

        return (String) queryForObject(
                "audit_task.getVendorComplianceStatus", vendorId);
    }

    public Integer getVendorIdByUserName (String userName) throws DataServiceException {
        Integer rvInteger  = (Integer) queryForObject(
                "audit_task.getVendorIdByUserName", userName);
        if (null == rvInteger) {
            throw new DataServiceException(
                    "[AuditDaoImpl] Select query returned null for method getVendorIdByResourceId");
        }
        return (rvInteger.intValue());
    } //getVendorIdByUserName

    public int setAuditPendingStatusForVendorHdr (int vendorId) throws DataServiceException {
        return update("audit_task.setAuditPendingStatusForVendorHdr", vendorId);
    }

    public int unsetAuditPendingStatusForVendorHdr (int vendorId) throws DataServiceException {
        return update("audit_task.unsetAuditPendingStatusForVendorHdr", vendorId);
    }

    public int getAuditPendingStatusForVendorHdr (int vendorId) throws DataServiceException {
        Integer rvInteger = null;
        rvInteger = ((Integer) queryForObject("audit_task.getAuditPendingStatusForVendorHdr", vendorId));
        return (rvInteger);
    }

    public int updateStateReasonCdForResource(AuditVO auditVO) throws DataServiceException {
    	logger.info( "Calling audit_task.updateStateReasonCdForResource: " + auditVO );
    	return update("audit_task.updateStateReasonCdForResource", auditVO);
    }

    public int insertReasonCdForResource(AuditVO auditVO) throws DataServiceException {
    	logger.info( "Calling audit_task.insertReasonCdForResource: " + auditVO );
        return update("audit_task.insertReasonCdForResource", auditVO);
    }

    public int deleteReasonCdForResource(AuditVO auditVO) throws DataServiceException {
    	logger.info( "Calling audit_task.deleteReasonCdForResource: " + auditVO );
        return update("audit_task.deleteReasonCdForResource", auditVO);
    }



    public AuditVO queryWfStateReasonCd(AuditVO auditVO) throws DataServiceException
    {
        try {
        	auditVO = (AuditVO) queryForObject("audit_task.queryWfStateReasonCd", auditVO);
        }
        catch (Exception ex) {
        	logger.info("[AuditDaoImpl.query- Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }

        return auditVO;
    }

    public AuditVO queryWfStateReasonCdTM(AuditVO auditVO) throws DataServiceException
    {
        try {
        	logger.debug("In the queryWfStateReasonCdTM WORKFLOW ENTITY: " + auditVO.getWfEntity() );
        	auditVO = (AuditVO) queryForObject("audit_task.queryWfStateReasonCdTM", auditVO);
        }
        catch (Exception ex) {
        	logger.info("[AuditDaoImpl.query- Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }

        return auditVO;
    }


    public int updateBackGroundCheckByResourceId(AuditVO auditVO) throws DataServiceException {
        return update("audit_task.updateBGCheckByResourceId", auditVO);
    }// update

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.audit.AuditDao#updateReviewDate(com.newco.marketplace.vo.audit.AuditVO)
	 */
	public int updateReviewDate(AuditVO auditVO) throws DataServiceException {
		 return update("audit_task.update_review_date", auditVO);
	}

	public ApproveFirmsVO getReasonCodesForStatus(ApproveFirmsVO approveFirmsVO) throws DataServiceException {
		ApproveFirmsVO resultVO = null;
		try{
			resultVO = (ApproveFirmsVO) queryForObject("getReasoncodesStatusChange.query", approveFirmsVO);
			if(null!= resultVO && null!= resultVO.getReasonCodeVoList() && !(resultVO.getReasonCodeVoList().isEmpty())){
				//set values to return object.
				approveFirmsVO.setWfStatus(resultVO.getWfStatus());
				approveFirmsVO.setReasonCodeVoList(resultVO.getReasonCodeVoList());
				
			}
		}catch (Exception e) {
			logger.error("Exception in fetching reasonCodes for the status change"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return approveFirmsVO;
	}
	public ApproveProvidersVO getProviderReasonCodesForStatus(ApproveProvidersVO approveProvidersVO) throws DataServiceException {
		ApproveProvidersVO resultVO = null;
		try{
			resultVO = (ApproveProvidersVO) queryForObject("getProviderReasoncodesStatusChange.query", approveProvidersVO);
			if(null!= resultVO && null!= resultVO.getReasonCodeVoList() && !(resultVO.getReasonCodeVoList().isEmpty())){
				//set values to return object.
				approveProvidersVO.setWfStatus(resultVO.getWfStatus());
				approveProvidersVO.setReasonCodeVoList(resultVO.getReasonCodeVoList());
				
			}
		}catch (Exception e) {
			logger.error("Exception in fetching reasonCodes for the status change"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return approveProvidersVO;
	}
	/**
	 * R16_0: SL-21003 method added to fetch the reason code of a resource credential
	 */
	public String getReasonCdResourceCredential(AuditVO auditVO)throws DataServiceException {

        String rvString = (String) queryForObject(
                "audit_task.getReasonCdResourceCredential", auditVO);
        return (rvString);
    }
	/**
	 * R16_0: SL-21003 method added to fetch the reason code of a vendor credential
	 */
    public String getReasonCdVendorCredential(AuditVO auditVO)throws DataServiceException {

        String rvString = (String) queryForObject(
                "audit_task.getReasonCdVendorCredential", auditVO);
        return (rvString);
    }
}// AuditDaoImpl
