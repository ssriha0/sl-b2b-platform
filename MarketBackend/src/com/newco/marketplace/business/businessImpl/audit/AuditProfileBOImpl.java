package com.newco.marketplace.business.businessImpl.audit;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.audit.IAuditProfileBO;
import com.newco.marketplace.business.iBusiness.audit.email.IAuditEmailBO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.AdminConstants;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.persistence.iDao.audit.AuditDao;
import com.newco.marketplace.persistence.iDao.provider.ITeamCredentialsDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorNotesDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class AuditProfileBOImpl implements IAuditProfileBO {

	 private final static Logger logger = Logger.getLogger(AuditProfileBOImpl.class.getName());

	 private AuditDao auditDao;
	 private IVendorHdrDao vendorHdrDao;
	 private IVendorResourceDao vendorResourceDao;
	 private IVendorCredentialsDao vendorCredentialsDao;
	 private ITeamCredentialsDao teamCredentialsDao;
	 private IAuditEmailBO auditEmailBean = null;
	 private IVendorNotesDao vendorNotesDao;

	 public void updateWFStatusAndReasonCodes(AuditVO auditVo, boolean isBatchProcess)
			throws AuditException {
		logger.info("updateWFStatusAndReasonCodes() " + auditVo.toString() + " isBatchJob: " + isBatchProcess);
    	//LEGEND for AUDIT LINK ID:
    	//2 = vendor_resource
    	//3 = vendor_credentials (Company / Insurance)
    	//4 = resource_credentials

    	int vendorId = -1;
    	int resourceId = -1;
    	int auditLinkId = -1;

    	vendorId = auditVo.getVendorId();
    	resourceId = auditVo.getResourceId();
    	auditLinkId = auditVo.getAuditLinkId();

    	//1.  Double check if the audit record exists.  If it does
    	//	  update into the Audit Table and into the corresponding
    	//	  audit_link table
    	AuditVO chkRecordExists = new AuditVO();
    	StringBuilder noteBody = new StringBuilder();

    	chkRecordExists.setVendorId(auditVo.getVendorId());
    	chkRecordExists.setResourceId(auditVo.getResourceId());
    	chkRecordExists.setAuditLinkId(auditVo.getAuditLinkId());
    	chkRecordExists.setAuditKeyId(auditVo.getAuditKeyId());

    	chkRecordExists = this.getAuditWfStateReasonCd(chkRecordExists);

    	if (chkRecordExists !=null){
		  if (chkRecordExists.getAuditTaskId() > -1){
		    	AuditVO updateAuditTask = new AuditVO();
		    	VendorResource updateVendorResource = new VendorResource();
				VendorCredentialsVO updateVendorCredentials = new VendorCredentialsVO();
				TeamCredentialsVO updateTeamCredentials = new TeamCredentialsVO();
				VendorHdr vendorHdr = new VendorHdr();

		    	logger.debug("AUDITPROFILE: UpdateWFStateusAndReasonCodes AUDIT KEY ID:" + auditVo.getAuditLinkId());

		    	updateAuditTask.setResourceId(auditVo.getResourceId());
		    	updateAuditTask.setVendorId(auditVo.getVendorId());
		    	updateAuditTask.setAuditLinkId(auditVo.getAuditLinkId());
		    	updateAuditTask.setAuditKeyId(auditVo.getAuditKeyId());
		    	updateAuditTask.setWfStateId(auditVo.getWfStateId());

		    	updateAuditTask.setReviewedBy(auditVo.getReviewedBy());
		    	//Ensures that we are only updating 1 row.  This is needed for Team Member.
		    	//Team member inserts 2 records upon adding a new team member.
		    	updateAuditTask.setAuditTaskId(chkRecordExists.getAuditTaskId());
		    	if(auditVo.getReviewComments() == null ){
		    		auditVo.setReviewComments("");
		    	}
		    	updateAuditTask.setReviewComments(auditVo.getReviewComments());
                logger.info("AuditTaskID: " + chkRecordExists.getAuditTaskId());

		    	if (auditLinkId ==1){
		    		logger.debug("AUDITPROFILE: UpdateVendor SET");
		    		//VENDOR UPDATE
		    		vendorHdr.setVendorId(vendorId);
		    		vendorHdr.setVendorStatusId(auditVo.getWfStatusId());
		    		updateAuditTask.setWfStateId(auditVo.getWfStatusId());
		    	}else if (auditLinkId ==2){
		        	logger.debug("AUDITPROFILE: UpdateVendorResource SET");
		    		//VENDOR_RESOURCE UPDATE
			    	updateVendorResource.setVendorId(vendorId);
			    	updateVendorResource.setResourceId(resourceId);
			    	updateVendorResource.setWfStateId(auditVo.getWfStateId());

		    	}else if(auditLinkId == 3){
		        	logger.debug("AUDITPROFILE: UpdateVendorCredentials SET");
		    		//VENDOR_CREDENTIALS UPDATE (Company / Insurance)
		        	updateVendorCredentials.setVendorCredId(auditVo.getVendorCredentialId());
		    		updateVendorCredentials.setVendorId(auditVo.getVendorId());
		    		updateVendorCredentials.setWfStateId(auditVo.getWfStateId());

		    	}else if(auditLinkId == 4){
		        	logger.debug("AUDITPROFILE: UpdateTeamCredentials SET");
		    		//RESOURCE_CREDENTIALS UPDATE
 		    		updateTeamCredentials.setResourceCredId(auditVo.getResourceCredentialId());
		    		updateTeamCredentials.setResourceId(resourceId);
		    		updateTeamCredentials.setWfStateId(auditVo.getWfStateId());
		    	}

		    	try{
		    		//2. Save the wf_state_id, auditor_name and reason_cd in the audit tasks table
			    	//3. Save the wf_state_id in the vendor_resource table
		        	logger.debug("AUDITPROFILE: UPDATE AUDIT TASKS TRUE");
		        				//getting multiple reason codes from form
                                //delete all records with audit_task_id from audit_task_reason_cd
                                //insert all records with audit_task_id into audit_task_reason_cd
                                String[] reasonCodes = auditVo.getReasonCodeIds();
                                if(reasonCodes != null) {
                                    this.auditDao.deleteReasonCdForResource(updateAuditTask);
                                    for(int i=0; i<reasonCodes.length; i++) {
                                    	// Ignore the N/A selection (-1) for reason codes.
                                        if(Integer.parseInt(reasonCodes[i]) != -1)
                                        {
                                            updateAuditTask.setReasonId(Integer.parseInt(reasonCodes[i]));                                        	
                                        	this.auditDao.insertReasonCdForResource(updateAuditTask);
                                        }
                                    }
                                }
                                this.auditDao.updateStateReasonCdForResource(updateAuditTask);
		    		if (auditLinkId == 1){
		    			logger.debug("AUDITPROFILE: UpdateVendor TRUE");
		    			this.vendorHdrDao.updateWFStateId(vendorHdr);
		    			//fetching company name and setting it to AuditVO
		    			auditVo.setAuditBusinessName(this.vendorHdrDao.getCompanyName(vendorHdr.getVendorId()));
		    			//send an audit email for this auditLinkId

		    			noteBody.append("Changed provider firm status To "+auditVo.getWfState());
		    			if(isBatchProcess){
		    				noteBody.append(" by batch process ");
		    			}
		    			if(auditVo.isSendEmailNotice()){
		    				getAuditEmailBean().sendAuditEmail (AuditStatesInterface.VENDOR, auditVo);
		    				noteBody.append(" Email sent");
		    			}else{
		    				noteBody.append(" Email not sent");
		    			}
		    		}else if (auditLinkId == 2){
		            	logger.debug("AUDITPROFILE: UpdateVendorResource TRUE");

		            	if(isBatchProcess){
		            		updateVendorResource.setMktPlaceInd(1);
		            		this.vendorResourceDao.updateWFStateAndMarketInd(updateVendorResource);
		            	}else{
		            		this.vendorResourceDao.updateWFState(updateVendorResource);
		            	}
		    			noteBody.append("Changed team member status to "+auditVo.getWfState());
		    			if(isBatchProcess){
		    				noteBody.append(" by batch process ");
		    			}
		    			noteBody.append(" Resource Name "+getVendorResourceDao().getResourceName(resourceId));

		    			if(auditVo.isSendEmailNotice()){
		    				getAuditEmailBean().sendAuditEmail (AuditStatesInterface.RESOURCE, auditVo);
		    				noteBody.append(" Email sent");
		    			}else{
		    				noteBody.append(" Email not sent");
		    			}
		    		}else if(auditLinkId == 3){
		            	logger.debug("AUDITPROFILE: UpdateVendorCredentials TRUE");
		    			this.vendorCredentialsDao.updateWfStateId(updateVendorCredentials);
		    			VendorCredentialsVO vendorCredentialsVO = new VendorCredentialsVO();
		    			vendorCredentialsVO.setVendorCredId(auditVo.getVendorCredentialId());

		    			noteBody.append("Changed Vendor Credential Status to "+auditVo.getWfState());
		    			noteBody.append(" Credential Name ");

		    			vendorCredentialsVO = getVendorCredentialsDao().queryCredById(vendorCredentialsVO);
		    			if(vendorCredentialsVO != null){
		    				noteBody.append(vendorCredentialsVO.getName());
		    			}else{
		    				noteBody.append("N/A");
		    			}

		    			if(auditVo.isSendEmailNotice())
		    			{
		    				getAuditEmailBean().sendAuditEmail (AuditStatesInterface.VENDOR_CREDENTIAL, auditVo);
		    				noteBody.append(" Email sent");
		    			}else{
		    				noteBody.append(" Email not sent");
		    			}
		    		}else if(auditLinkId == 4){
		            	logger.debug("AUDITPROFILE: UpdateTeamCredentials TRUE");
		    			this.teamCredentialsDao.updateWfStateId(updateTeamCredentials);
		    			TeamCredentialsVO teamCredentialsVO = new TeamCredentialsVO();
		    			teamCredentialsVO.setResourceCredId(auditVo.getResourceCredentialId());

		    			noteBody.append("Changed team credential status to "+auditVo.getWfState());
		    			noteBody.append(" Resource Name "+getVendorResourceDao().getResourceName(resourceId));
		    			noteBody.append(" Credential Name ");

		    			teamCredentialsVO = getTeamCredentialsDao().queryCredById(teamCredentialsVO);
		    			if(teamCredentialsVO != null){
		    				noteBody.append(teamCredentialsVO.getLicenseName());
		    			}else{
		    				noteBody.append("N/A");
		    			}

		    			if(auditVo.isSendEmailNotice())
		    			{
		    				getAuditEmailBean().sendAuditEmail (AuditStatesInterface.RESOURCE_CREDENTIAL, auditVo);
		    				noteBody.append(" Email sent");
		    			}else{
		    				noteBody.append(" Email not sent");
		    			}
		    		}
		    		// Add a note in the vendor notes table
		    		VendorNotesVO vendorNotesVO = new VendorNotesVO();
		    		vendorNotesVO.setVendorId(vendorId);
		    		vendorNotesVO.setNote(noteBody.toString());
		    		vendorNotesVO.setModifiedBy(auditVo.getReviewedBy());

		    		addVendorNote(vendorNotesVO);
				}catch(Exception e){
//					logger.error(e);
					throw new AuditException("Error in AuditProfileBOImpl.updateWFStatusAndReasonCodes()", e);
				}

    		}else{
    			throw new AuditException("The Audit Task Record does not exist.");
    		}
    	}else{
    			throw new AuditException("The Audit Task Record does not exist.");
    	}

	}

	public AuditVO getAuditWfStateReasonCd (AuditVO auditVO) throws AuditException{

    	AuditVO queryAuditTasks = new AuditVO();
    	int auditLinkId = auditVO.getAuditLinkId();

    	try{
    		queryAuditTasks.setVendorId(auditVO.getVendorId());
    		queryAuditTasks.setResourceId(auditVO.getResourceId());
    		queryAuditTasks.setAuditLinkId(auditLinkId);
    		queryAuditTasks.setAuditKeyId(auditVO.getAuditKeyId());

    		logger.debug("getAuditClaimedBy");

    		if (auditLinkId == 2){
    			logger.debug("TEAMMEBER QUERY WILL BE INVOKED");
    		//2 signifies Team Member.  There are 2 records being inserted when the audit
    		//process kicks off for a new Team Member.  Need to only update the record
    		//for the Team Member not the background chk record.
    			queryAuditTasks.setWfEntity(AdminConstants.TEAM_MEMBER);
    			queryAuditTasks = getAuditDao().queryWfStateReasonCdTM(queryAuditTasks);
    		}else{
    			queryAuditTasks = getAuditDao().queryWfStateReasonCd(queryAuditTasks);

    		}

	    	if (queryAuditTasks !=null ){
	    		logger.debug("queryVEndorHdr is not null");
	    	}
    	}catch(Exception e){
    		logger.error("[Exception] Error in AuditProfileBOImpl --> getAuditWfStateReasonCd" + e.getMessage());
			throw new AuditException("Error in AuditProfileBOImpl --> getAuditWfStateReasonCd", e);
    	}
    return queryAuditTasks;
    }


	public String getVendorCurrentStatus(Integer vendorId) throws BusinessServiceException {
		String status = null;
		try {
			status = getVendorHdrDao().getVendorCurrentStatus(vendorId);
		} catch (DBException e) {
			logger.error("Error in AuditProfileBOImpl --> getVendorCurrentStatus() gettting current status "
					+ e.getMessage());
			throw new BusinessServiceException(
					"Error in AuditProfileBOImpl --> getVendorCurrentStatus() gettting current status "+
					e.getMessage());
		}
		return status;
	}

	public String getResourceCredentialStatus(Integer resourceId, Integer keyId) throws BusinessServiceException {
		String status = null;
		AuditVO auditVO = new AuditVO();
		auditVO.setResourceId(resourceId);
		if (keyId != null)
		{
			auditVO.setAuditKeyId(keyId);
		}
		try {
			status = getAuditDao().getStatusResourceCredential(auditVO);
		} catch (DataServiceException e) {
			logger.error("Error in AuditProfileBOImpl --> getResourceCredentialStatus() gettting current status "
					+ e.getMessage());
			throw new BusinessServiceException(
					"Error in AuditProfileBOImpl --> getResourceCredentialStatus() gettting current status "+
					e.getMessage());
		}
		return status;
	}

	public String getResourceCredentialStatus(Integer resourceId) throws BusinessServiceException {

		return getResourceCredentialStatus( resourceId, null);
	}

	public String getVendorCredentialStatus(Integer vendorId, Integer keyId) throws BusinessServiceException {
		String status = null;
		AuditVO auditVO = new AuditVO();
		auditVO.setVendorId(vendorId);
		if (keyId != null)
		{
			auditVO.setAuditKeyId(keyId);
		}
		try {
			status = getAuditDao().getStatusVendorCredential(auditVO);
		} catch (DataServiceException e) {
			logger.error("Error in AuditProfileBOImpl --> getVendorCredentialStatus() gettting current status "
					+ e.getMessage());
			throw new BusinessServiceException(
					"Error in AuditProfileBOImpl --> getVendorCredentialStatus() gettting current status "+
					e.getMessage());
		}
		return status;
	}

	public String getVendorCredentialStatus(Integer vendorId) throws BusinessServiceException {

		return getVendorCredentialStatus( vendorId, null);
	}

	public ProcessResponse addVendorNote(VendorNotesVO vendorNotesVO) throws BusinessServiceException
	{
		ProcessResponse response = new ProcessResponse();
		response.setCode(ServiceConstants.USER_ERROR_RC);
		try {
			if(vendorNotesVO.getNote()!=null && vendorNotesVO.getNote().length()!=0 && !vendorNotesVO.getNote().equals("") && vendorNotesVO.getNote().length()<=8000)
			{
				logger.info("The vendor note is not null so going to add into the database");

				getVendorNotesDao().insert(vendorNotesVO);

				response.setCode(ServiceConstants.VALID_RC);
				response.setMessage("The insertion done successfully");
				logger.info("insertion has been done successfully");

			}else{
				response.setCode(ServiceConstants.USER_ERROR_RC);
				response.setMessage("Note is either null or greater than 8000 characters");
			}

			return response;
		} catch (DataServiceException dse)
        {
            logger.error("[DataServiceException] Error inserting vendor note" + dse.getMessage());
            throw new BusinessServiceException("[DataServiceException] Error inserting vendor note", dse);
        }
	}

	public List<VendorResource> retrieveResourceForApproval() throws BusinessServiceException{
		logger.debug("Entering AuditProfileBOImpl --> retrieveResourceForApproval() ");
		List<VendorResource> resourceList = null;
		try {
			resourceList = getVendorResourceDao().getResourcesReadyForApproval();
		} catch (DBException e) {
			logger.error("[DataServiceException] Error retrieve Resources ready for approval " + e.getMessage());
			throw new BusinessServiceException("Error retrieve Resources ready for approval", e);
		}
		logger.debug("Leaving AuditProfileBOImpl --> retrieveResourceForApproval() ");
		return resourceList;
	}

	public List<VendorHdr> retrieveCompaniesForApproval() throws BusinessServiceException {
		logger.debug("Entering AuditProfileBOImpl --> retrieveCompaniesForApproval() ");
		List<VendorHdr> vendorList = null;
		try {
			vendorList = getVendorHdrDao().queryCompaniesReadyForApproval();
		} catch (DBException e) {
			logger.error("[DataServiceException] Error retrieving companies ready for approval " + e.getMessage());
			throw new BusinessServiceException("Error retrieving companies ready for approval", e);
		}
		logger.debug("Leaving AuditProfileBOImpl --> retrieveCompaniesForApproval() ");
		return vendorList;
	}

	public String getResourceStatus(Integer resourceId) throws BusinessServiceException {
		String currentStatus = null;

		try {
			currentStatus = getVendorHdrDao().getStatusForResource(resourceId);
		} catch (DBException e) {
			logger.error("[DataServiceException] Error getting resource and status" + e.getMessage());
            throw new BusinessServiceException("[DataServiceException] Error  getting resource and status", e);
		}
		return currentStatus;
	}
	
	public void handleVendorResourceUpdateError(AuditVO auditVO)
	{
		StringBuilder errorMsg = new StringBuilder("Error Auditing Vendor Resource");
		getAuditEmailBean().sendAuditErrorEmail(auditVO, errorMsg.toString());
		return;
	}
	public void handleVendorUpdateError(AuditVO auditVO)
	{
		StringBuilder errorMsg = new StringBuilder("Error Auditing Vendor Firm");
		getAuditEmailBean().sendAuditErrorEmail(auditVO, errorMsg.toString());
		return;
	}

	/**
	 * @return the auditDao
	 */
	public AuditDao getAuditDao() {
		return auditDao;
	}

	/**
	 * @param auditDao the auditDao to set
	 */
	public void setAuditDao(AuditDao auditDao) {
		this.auditDao = auditDao;
	}

	/**
	 * @return the teamCredentialsDao
	 */
	public ITeamCredentialsDao getTeamCredentialsDao() {
		return teamCredentialsDao;
	}

	/**
	 * @param teamCredentialsDao the teamCredentialsDao to set
	 */
	public void setTeamCredentialsDao(ITeamCredentialsDao teamCredentialsDao) {
		this.teamCredentialsDao = teamCredentialsDao;
	}

	/**
	 * @return the vendorCredentialsDao
	 */
	public IVendorCredentialsDao getVendorCredentialsDao() {
		return vendorCredentialsDao;
	}

	/**
	 * @param vendorCredentialsDao the vendorCredentialsDao to set
	 */
	public void setVendorCredentialsDao(IVendorCredentialsDao vendorCredentialsDao) {
		this.vendorCredentialsDao = vendorCredentialsDao;
	}

	/**
	 * @return the vendorHdrDao
	 */
	public IVendorHdrDao getVendorHdrDao() {
		return vendorHdrDao;
	}

	/**
	 * @param vendorHdrDao the vendorHdrDao to set
	 */
	public void setVendorHdrDao(IVendorHdrDao vendorHdrDao) {
		this.vendorHdrDao = vendorHdrDao;
	}

	/**
	 * @return the vendorResourceDao
	 */
	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	/**
	 * @param vendorResourceDao the vendorResourceDao to set
	 */
	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

	/**
	 * @return the auditEmailBean
	 */
	public IAuditEmailBO getAuditEmailBean() {
		return auditEmailBean;
	}

	/**
	 * @param auditEmailBean the auditEmailBean to set
	 */
	public void setAuditEmailBean(IAuditEmailBO auditEmailBean) {
		this.auditEmailBean = auditEmailBean;
	}

	/**
	 * @return the vendorNotesDao
	 */
	public IVendorNotesDao getVendorNotesDao() {
		return vendorNotesDao;
	}

	/**
	 * @param vendorNotesDao the vendorNotesDao to set
	 */
	public void setVendorNotesDao(IVendorNotesDao vendorNotesDao) {
		this.vendorNotesDao = vendorNotesDao;
	}
	
	/**
	 * R16_0: SL-21003 method added to fetch the reason code of a resource credential
	 */
	public String getResourceCredentialReasonCd(Integer resourceId, Integer keyId) throws BusinessServiceException {
		String status = null;
		AuditVO auditVO = new AuditVO();
		auditVO.setResourceId(resourceId);
		if (keyId != null)
		{
			auditVO.setAuditKeyId(keyId);
		}
		try {
			status = getAuditDao().getReasonCdResourceCredential(auditVO);
		} catch (DataServiceException e) {
			logger.error("Error in AuditProfileBOImpl --> getResourceCredentialStatus() gettting current status "
					+ e.getMessage());
			throw new BusinessServiceException(
					"Error in AuditProfileBOImpl --> getResourceCredentialStatus() gettting current status "+
					e.getMessage());
		}
		return status;
	}
	
	/**
	 * R16_0: SL-21003 method added to fetch the reason code of a vendor credential
	 */
	public String getVendorCredentialReasonCd(Integer vendorId, Integer keyId) throws BusinessServiceException {
		String status = null;
		AuditVO auditVO = new AuditVO();
		auditVO.setVendorId(vendorId);
		if (keyId != null)
		{
			auditVO.setAuditKeyId(keyId);
		}
		try {
			status = getAuditDao().getReasonCdVendorCredential(auditVO);
		} catch (DataServiceException e) {
			logger.error("Error in AuditProfileBOImpl --> getVendorCredentialStatus() gettting current status "
					+ e.getMessage());
			throw new BusinessServiceException(
					"Error in AuditProfileBOImpl --> getVendorCredentialStatus() gettting current status "+
					e.getMessage());
		}
		return status;
	}

}
