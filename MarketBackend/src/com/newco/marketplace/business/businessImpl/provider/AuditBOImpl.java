package com.newco.marketplace.business.businessImpl.provider;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IAuditDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IWorkflowDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.WorkflowStateVO;

/**
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/25 21:00:57 $
 */
public class AuditBOImpl extends ABaseBO implements IAuditBO {
    private IAuditDao auditDao;
    private IWorkflowDao workflowDao;
    private IVendorResourceDao vendorResourceDao;
    private List<WorkflowStateVO> workflowStates = null;
    private static final Logger logger = Logger.getLogger(AuditBOImpl.class.getName());
    private static final MessageResources messages = MessageResources.getMessageResources("BizLocalStrings");

    
    
    public void createAudit(String wfEntity, AuditVO auditVO) throws AuditException {

        for (WorkflowStateVO wfState : getWorkflowStates()) {        
            if (wfState.getWfEntity().equals(wfEntity) && wfState.getWfState().equals(auditVO.getWfState())) {
            	auditVO.setWfStateId(wfState.getWfStateId());
                auditVO.setAuditLinkId(wfState.getAuditLinkId());                
            }
        }

        try {        	
            getAuditDao().insert(auditVO);
        } catch (DBException dse) {
            logger.info("[DBException] " + StackTraceHelper.getStackTrace(dse));
            final String error = messages.getMessage("biz.select.failed");
            throw new AuditException(error, dse);
        }

        logger.info("[AuditBusinessBean]*************************************************createAudit\n" + auditVO);
    }// end method

    public void auditVendorHeader(int vendorId, String state) {
    	boolean approvalFlag = false;
        AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR, state);
        auditVO.setAuditKeyId(vendorId);
        try {        	
            auditVendorHeader(auditVO,approvalFlag);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    public void auditVendorHeaderByUserId(String userName, String state) {
    	boolean approvalFlag = false;
        int vendorId = 0;
        try {        
            vendorId = getAuditDao().getVendorIdByUserName(userName);
        } catch (DBException e) {
            logger.info("DBException [auditVendorHeader]" + StackTraceHelper.getStackTrace(e));
        }
        AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR, state);
        try {        	
            auditVendorHeader(auditVO,approvalFlag);
        } catch (AuditException e) {
            logger.info("AuditException: DBException [auditVendorHeader]" + StackTraceHelper.getStackTrace(e));
        }
    }

    public void auditVendorHeader(AuditVO auditVO,boolean vendorApprovalFlag) throws AuditException {
    	
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
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        // update the table state
        try {        	
        	 if(!vendorApprovalFlag){
        		 getAuditDao().updateStatusVendorHeader(auditVO);
        	 }            
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]****************************************************auditVendorHeader\n" + auditVO);
    } // auditVendorHeader

    public void auditVendorResource(int resourceId, String state) {

        int vendorId = 0;
        try {
            vendorId = getAuditDao().getVendorIdByResourceId(resourceId);
        } catch (DBException e) {
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
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        try {
            getAuditDao().updateStatusVendorResource(auditVO);
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DBException e) {
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
                auditVO.setAuditTaskId(auditVO.getAuditTaskId());
            }
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        try {
        	
        	if(auditVO.isViaAPI()){
                getAuditDao().updateStatusVendorCredentialById(auditVO);
        	}else{
            getAuditDao().updateStatusVendorCredential(auditVO);
        	}
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DBException e) {
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
        	Integer vendorId;
        	vendorId = getAuditDao().getVendorIdByResourceId(auditVO.getResourceId()); // old replace with something that accesses vendor_resource
        	//vendorId = getVendorResourceDao().getVendorIdByResourceId(auditVO.getResourceId());
            auditVO.setVendorId(vendorId);
        } catch (DBException e) {
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
        	
            if (auditVO.getAuditKeyId() != null && auditVO.getAuditKeyId().intValue() > 0 )
			{
				int rowsUpdated = getAuditDao().updateByResourceId(auditVO);
				if (rowsUpdated == 0)
				{
					// there was no row for this vendor id
					auditVO.setWfEntity(RESOURCE_CREDENTIAL);
					this.createAudit(RESOURCE_CREDENTIAL, auditVO);
                    auditVO.setAuditTaskId(auditVO.getAuditTaskId());
				}
			}
            else
            {
            	throw new AuditException("auditResourceCredentials - No auditkey Found in auditVO");
            }
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
      /*  //only update if 
        if(auditVO.getIsInsert().booleanValue() == false) */
        if(auditVO.getAuditKeyId() != null && auditVO.getWfStateId() != null ) {
        	updateCredentialStatus(auditVO);
        }
        // update the vendor for auditor review
        try {
            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]- auditResourceCredentials**********************************************************\n" + auditVO);
    }// auditResourceCredentials

	/**
	 * @param auditVO
	 * @throws AuditException
	 */
	private void updateCredentialStatus(AuditVO auditVO) throws AuditException
	{
		try {
            getAuditDao().updateStatusResourceCredential(auditVO);
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
	}

    public void auditResourceBackgroundCheck(int resourceId, String state) throws AuditException {

        AuditVO auditVO = new AuditVO(0, resourceId, RESOURCE_BACKGROUND_CHECK, state);
        auditVO.setAuditKeyId(resourceId);
        try {
            auditResourceBackgroundCheck(auditVO);
        } catch (AuditException e) {
        	logger.info("Caught Exception and ignoring",e);
        }
    }

    //R11_0
    public void auditResourceBackgroundReCheck(int resourceId, String state,String recertificationIndParam) throws AuditException {

        AuditVO auditVO = new AuditVO(0, resourceId, RESOURCE_BACKGROUND_CHECK, state);
        auditVO.setAuditKeyId(resourceId);
        
        if(recertificationIndParam.equals("Y"))
        {
        	auditVO.setRecertificationInd(true);
        }
        else
        {
        	auditVO.setRecertificationInd(false);
        }
        
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
        } catch (DBException e) {
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
        } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }

        try {
        	
        	// R11_0 SL-19667 update
        	
        	if(null== auditVO.getRecertificationInd()){
        		getAuditDao().updateResourceBackgroundCheckStatus(auditVO);
        	}
        	else if( auditVO.getRecertificationInd())
            {
                int rows=vendorResourceDao.recertify(auditVO.getResourceId().toString());
                if(rows>0){
                    //insert background check history
                GeneralInfoVO generalInfoVO=new GeneralInfoVO();
                generalInfoVO.setChangedComment(MPConstants.RECERT_SUB);
                generalInfoVO.setResourceId(auditVO.getResourceId().toString());  
                vendorResourceDao.insertBcHistory(generalInfoVO);
                		}
            }
            else
            {
                // update background_state_id as '28'-Pending submission
                int rowsUpdated=vendorResourceDao.updateBackgroundCheckStatus(auditVO.getResourceId().toString());
                if(rowsUpdated>0){
                    //insert background check history
                GeneralInfoVO generalInfoVO=new GeneralInfoVO();
                generalInfoVO.setChangedComment(MPConstants.NEW_SCREENING);
                generalInfoVO.setResourceId(auditVO.getResourceId().toString());
                vendorResourceDao.insertBcHistory(generalInfoVO);
                				}
            }
                
          //  getAuditDao().updateResourceBackgroundCheckStatus(auditVO);
         } catch (DBException e) {
            logger.info(StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        logger.debug("[AuditBusinessBean]- auditResourceBackgroundCheck*********************************************\n" + auditVO);
    }

    public AuditVO getResourceAudit(AuditVO auditVO) throws AuditException {

        try {
            return (getAuditDao().getResourceAudit(auditVO));
        } catch (DBException e) {
            throw (new AuditException(e.getMessage(), e));
        }
    }// getAudit

    public AuditVO getVendorAudit(AuditVO auditVO) throws AuditException {

        try {
            return (getAuditDao().getVendorAudit(auditVO));
        } catch (DBException e) {
            throw (new AuditException(e.getMessage(), e));
        }
    }// getAudit
//sl-21142
    public void saveVendorOrResourceCredReasonCd(AuditVO auditVO) throws AuditException{
    	try {
    		auditDao.queryWfStateReasonCd(auditVO);
			auditDao.insertReasonCdForResource(auditVO);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			throw (new AuditException(e.getMessage(), e));
		}
    }

    public void deleteVendorOrResourceCredReasonCd(AuditVO auditVO) throws AuditException{
    	
    	try {
    		auditDao.queryWfStateReasonCd(auditVO);
			auditDao.deleteReasonCdForResource(auditVO);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			throw (new AuditException(e.getMessage(), e));
		}
    }

    public IAuditDao getAuditDao() {

        return auditDao;
    }

    public void setAuditDao(IAuditDao auditDao) {

        this.auditDao = auditDao;
    }

    public IWorkflowDao getWorkflowDao() {

        return workflowDao;
    }

    public void setWorkflowDao(IWorkflowDao workflowDao) {

        this.workflowDao = workflowDao;
    }

    
    

	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

	public List<WorkflowStateVO> getWorkflowStates() {

        if (workflowStates == null) {
            try {
                workflowStates = getWorkflowDao().getStateTableMappings();
            } catch (DBException e) {
                logger.info("[DBException] " + StackTraceHelper.getStackTrace(e));
                new AuditException("[AuditBusinessBean] Could not cache the work flow states");
            }
        }
        return workflowStates;
    }// getWorkflowStates()

    public String getVendorComplianceStatus(int vendorId) throws AuditException {

        String state = null;
        try {
            state = getAuditDao().getVendorComplianceStatus(vendorId);
        } catch (DBException e) {
            logger.info("[DBException] " + StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        return (state);
    }

    public String submitVendorForCompliance(int vendorId) throws AuditException {
    	boolean approvalFlag = false;
        try {
            //AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR_PENDING_COMPLIANCE_APPROVAL);
            AuditVO auditVO = new AuditVO(vendorId, 0, VENDOR_PENDING_APPROVAL);
            for (WorkflowStateVO wfState : getWorkflowStates()) {
                if (wfState.getWfEntity().equals(VENDOR) && wfState.getWfState().equals(auditVO.getWfState())) {
                    auditVO.setWfStateId(wfState.getWfStateId());
                    auditVO.setAuditLinkId(wfState.getAuditLinkId());
                }
            }
            this.auditVendorHeader(auditVO,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (getVendorComplianceStatus(vendorId));
    }// submitVendorForCompliance
    
    
    public String getVendorResourceStatus(String resourceId) throws AuditException{
    	
    	String state = null;
    	AuditVO auditVO = new AuditVO();
    	auditVO.setResourceId(Integer.parseInt(resourceId));
        try {
            state = getAuditDao().getStatusVendorResource(auditVO);
        } catch (DBException e) {
            logger.info("[DBException] " + StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        return (state);
    }

    public int updateStatusVendorResource(int resourceId, String state) throws AuditException{
        int updatedRow = 0;
        try {
        	AuditVO auditVO = new AuditVO();
	        auditVO.setResourceId(resourceId);
	        
	        for (WorkflowStateVO wfState : getWorkflowStates()) {
                if (wfState.getWfEntity().equals(RESOURCE) && wfState.getWfState().equals(state)) {
                	auditVO.setWfStateId(wfState.getWfStateId());                    
                }
            }
	        
	        updatedRow = getAuditDao().updateStatusVendorResource(auditVO);
        } catch (DBException e) {
            e.printStackTrace();
            throw new AuditException("Exception occured while updating vendor resource",e);
        }
        return updatedRow;
    }
    public String getVendorHeaderStatus(String vendorId) throws AuditException{
    	
    	String state = null;
    	AuditVO auditVO = new AuditVO();
    	auditVO.setVendorId(Integer.parseInt(vendorId));
        try {
            state = getAuditDao().getStatusVendorHeader(auditVO);
        } catch (DBException e) {
            logger.info("[DBException] " + StackTraceHelper.getStackTrace(e));
            throw new AuditException(e.getMessage(), e);
        }
        return (state);
    }

	
    
}
