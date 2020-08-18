/*
 ** AuditBusinessBean.java     1.0     2007/06/05
 */
package com.newco.marketplace.business.businessImpl.audit;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.audit.IAuditBusinessBean;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.audit.AuditDao;
import com.newco.marketplace.persistence.iDao.provider.IWorkflowDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.WorkflowStateVO;

/**
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 00:40:37 $
 */
public class AuditBusinessBeanImpl  implements IAuditBusinessBean {
    private AuditDao auditDao;
    private IWorkflowDao workflowDao;
    private List<WorkflowStateVO> workflowStates = null;
    
    
    
    private final static Logger logger = Logger
	.getLogger(AuditBusinessBeanImpl.class.getName());

  
    public void createAudit(String wfEntity, AuditVO auditVO) throws AuditException {
    	logger.info("[AuditBusinessBean] ***** createAudit: " + auditVO);
        for (WorkflowStateVO wfState : getWorkflowStates( )) {
            if (wfState.getWfEntity().equals(wfEntity) && wfState.getWfState().equals(auditVO.getWfState())) {
                auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());
            }
        }
 
        try {
            getAuditDao().insert(auditVO);
        } catch (DataServiceException dse) {
            logger.error("[DataServiceException] " + StackTraceHelper.getStackTrace(dse));
            final String error =("Audit select Failed");
            throw new AuditException(error, dse);
        }        
    }// end method

    public void auditVendorHeader(int vendorId, String state) {

        AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR, state);
        auditVO.setAuditKeyId(vendorId);
        try {
            auditVendorHeader(auditVO);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    public void auditVendorHeaderByUserId(String userName, String state) {

        int vendorId = 0;
        try {
            vendorId = getAuditDao().getVendorIdByUserName(userName);
        } catch (DataServiceException e) {
            logger.info("DataServiceException [auditVendorHeader]" + StackTraceHelper.getStackTrace(e));
        }
        AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR, state);
        try {
            auditVendorHeader(auditVO);
        } catch (AuditException e) {
            logger.info("AuditException: DataServiceException [auditVendorHeader]" + StackTraceHelper.getStackTrace(e));
        }
    }

    public void auditVendorHeader(AuditVO auditVO) throws AuditException {

        if (auditVO.getWfState() == null) {
            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
        }
        if (auditVO.getAuditKeyId() == null) {
            auditVO.setAuditKeyId(auditVO.getVendorId());
        }
        for (WorkflowStateVO wfState : getWorkflowStates()) {
            if (wfState.getWfEntity().equals(VENDOR) && wfState.getWfState().equals(auditVO.getWfState())) {
                auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());
            }
        }
        // update audit task if present else create one
        try {
            int state = getAuditDao().updateByVendorId(auditVO);
            if (state == 0) {
                // there was no row for this vendor id
                this.createAudit(VENDOR, auditVO);
            }
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        // update the table state
        try {
            getAuditDao().updateStatusVendorHeader(auditVO);
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]****************************************************auditVendorHeader\n" + auditVO);
    } // auditVendorHeader

    public void auditVendorResource(int resourceId, String state) {

        int vendorId = 0;
        try {
            vendorId = getAuditDao().getVendorIdByResourceId(resourceId);
        } catch (DataServiceException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
        AuditVO auditVO = new AuditVO(vendorId, resourceId, RESOURCE, state);
        auditVO.setAuditKeyId(resourceId);
        try {
            auditVendorResource(auditVO);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    public void auditVendorResource(AuditVO auditVO) throws AuditException {

        if (auditVO.getWfState() == null) {
            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
        }
        if (auditVO.getAuditKeyId() == null) {
            auditVO.setAuditKeyId(auditVO.getResourceId());
        }
        for (WorkflowStateVO wfState : getWorkflowStates()) {
            if (wfState.getWfEntity().equals(RESOURCE) && wfState.getWfState().equals(auditVO.getWfState())) {
                auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());
            }
        }
        if (auditVO.getWfState() == null) {
            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
        }
        try {
            int rows_updated = getAuditDao().updateByResourceId(auditVO);
            if (rows_updated == 0) {
                // there was no row for this vendor id
                auditVO.setWfEntity(RESOURCE);
                this.createAudit(RESOURCE, auditVO);
            }
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        try {
            getAuditDao().updateStatusVendorResource(auditVO);
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]- auditVendorResource*********************************************\n" + auditVO);
    } // auditVendorResource

    public void auditVendorCredentials(int vendorId, int vendorCredId, String state) {

        AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR_CREDENTIAL, state);
        auditVO.setAuditKeyId(vendorCredId);
        try {
            auditVendorCredentials(auditVO);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    public void auditVendorCredentials(AuditVO auditVO) throws AuditException {

        if (auditVO.getWfState() == null) {
            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
        }

        for (WorkflowStateVO wfState : getWorkflowStates()) {
            if (wfState.getWfEntity().equals(VENDOR_CREDENTIAL) && wfState.getWfState().equals(auditVO.getWfState())) {
                auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());
            }
        }

        // update audit task if present else create one
        try {
            int rowsUpdated = getAuditDao().updateByVendorId(auditVO);
            if (rowsUpdated == 0) {
                // there was no row for this vendor id
                auditVO.setWfEntity(VENDOR_CREDENTIAL);
                this.createAudit(VENDOR_CREDENTIAL, auditVO);
            }
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        try {
            getAuditDao().updateStatusVendorCredential(auditVO);
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]- auditVendorCredentials*********************************************\n" + auditVO);
    }// auditVendorCredentials()

    public void auditResourceCredentials(int resourceId, int resourceCredId, String state) {

        AuditVO auditVO = new AuditVO(0, resourceId, RESOURCE_CREDENTIAL, state);
        auditVO.setAuditKeyId(resourceCredId);
        try {
            auditResourceCredentials(auditVO);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    public void auditResourceCredentials(AuditVO auditVO) throws AuditException {

        if (auditVO.getWfState() == null) {
            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
        }
        try {
            auditVO.setVendorId(getAuditDao().getVendorIdByResourceId(auditVO.getResourceId()));
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
        }

        for (WorkflowStateVO wfState : getWorkflowStates()) {
            if (wfState.getWfEntity().equals(RESOURCE_CREDENTIAL) && wfState.getWfState().equals(auditVO.getWfState())) {
                auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());
            }
        }
        // update audit task if present else create one
        try {
            int rowsUpdated = getAuditDao().updateByResourceId(auditVO);
            if (rowsUpdated == 0) {
                // there was no row for this vendor id
                auditVO.setWfEntity(RESOURCE_CREDENTIAL);
                this.createAudit(RESOURCE_CREDENTIAL, auditVO);
            }
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        try {
            getAuditDao().updateStatusResourceCredential(auditVO);
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]- auditResourceCredentials**********************************************************\n" + auditVO);
    }// auditResourceCredentials

    public void auditResourceBackgroundCheck(int resourceId, String state) throws AuditException {

        AuditVO auditVO = new AuditVO(0, resourceId, RESOURCE_BACKGROUND_CHECK, state);
        auditVO.setAuditKeyId(resourceId);
        try {
            auditResourceBackgroundCheck(auditVO);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    public void auditResourceBackgroundCheck(AuditVO auditVO) throws AuditException {

        if (auditVO.getWfState() == null) {
            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
        }
        if (auditVO.getAuditKeyId() == null) {
            auditVO.setAuditKeyId(auditVO.getResourceId());
        }

        try {
            auditVO.setVendorId(getAuditDao().getVendorIdByResourceId(auditVO.getResourceId()));
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        for (WorkflowStateVO wfState : getWorkflowStates()) {
            if (wfState.getWfEntity().equals(RESOURCE_BACKGROUND_CHECK) && wfState.getWfState().equals(auditVO.getWfState())) {
                auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());
            }
        }

        try {
            int rows_updated = getAuditDao().updateBackGroundCheckByResourceId(auditVO);
            if (rows_updated == 0) {
                // there was no row for this vendor id
                auditVO.setWfEntity(RESOURCE_BACKGROUND_CHECK);
                this.createAudit(RESOURCE_BACKGROUND_CHECK, auditVO);
            }
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        try {
            getAuditDao().updateResourceBackgroundCheckStatus(auditVO);
        } catch (DataServiceException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]- auditResourceBackgroundCheck*********************************************\n" + auditVO);
    }

    public AuditVO getResourceAudit(AuditVO auditVO) throws AuditException {

        try {
            return (getAuditDao().getResourceAudit(auditVO));
        } catch (DataServiceException e) {
            throw (new AuditException(e.getMessage(), e));
        }
    }// getAudit

    public AuditVO getVendorAudit(AuditVO auditVO) throws AuditException {

        try {
            return (getAuditDao().getVendorAudit(auditVO));
        } catch (DataServiceException e) {
            throw (new AuditException(e.getMessage(), e));
        }
    }// getAudit

    public AuditDao getAuditDao() {

        return auditDao;
    }

    public void setAuditDao(AuditDao auditDao) {

        this.auditDao = auditDao;
    }

    public IWorkflowDao getWorkflowDao() {

        return workflowDao;
    }

    public void setWorkflowDao(IWorkflowDao workflowDao) {

        this.workflowDao = workflowDao;
    }

    public List<WorkflowStateVO> getWorkflowStates() {

        if (workflowStates == null) {
            try {
                workflowStates = getWorkflowDao().getStateTableMappings();
            } catch (DBException e) {
                logger.info("[DataServiceException] " + StackTraceHelper.getStackTrace(e));
                new AuditException("[AuditBusinessBean] Could not cache the work flow states");
            }
        }
        return workflowStates;
    }// getWorkflowStates()

    public String getVendorComplianceStatus(int vendorId) throws AuditException {

        String state = null;
        try {
            state = getAuditDao().getVendorComplianceStatus(vendorId);
        } catch (DataServiceException e) {
            logger.info("[DataServiceException] " + StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        return (state);
    }

    public String submitVendorForCompliance(int vendorId) throws AuditException {

        try {
            //AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR_PENDING_COMPLIANCE_APPROVAL);
            AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR_PENDING_APPROVAL);
            for (WorkflowStateVO wfState : getWorkflowStates()) {
                if (wfState.getWfEntity().equals(VENDOR) && wfState.getWfState().equals(auditVO.getWfState())) {
                    auditVO.setWfStateId(wfState.getWfStateId());
                    auditVO.setAuditLinkId(wfState.getAuditLinkId());
                }
            }
            this.auditVendorHeader(auditVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (getVendorComplianceStatus(vendorId));
    }// submitVendorForCompliance

}// end class
