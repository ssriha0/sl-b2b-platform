package com.servicelive.spn.services.network;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_INTERESTED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.lookup.LookupSPNOptOutReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.domain.spn.network.SPNApprovalCriteria;
import com.servicelive.domain.spn.network.SPNBuyer;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNProviderFirmStatePk;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import com.servicelive.domain.spn.network.SPNServiceProviderStatePk;
import com.servicelive.spn.auditor.vo.SPNMeetAndGreetStateVO;
import com.servicelive.spn.auditor.vo.SPNProviderFirmStateVO;
import com.servicelive.spn.auditor.vo.SPNServiceProviderStateVO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.network.SPNHeaderDao;
import com.servicelive.spn.dao.network.SPNProviderFirmStateDao;
import com.servicelive.spn.dao.network.SPNProviderFirmStateUsingIbatisDao;
import com.servicelive.spn.dao.network.SPNServiceProviderStateDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.auditor.ServiceProviderService;
import com.servicelive.spn.services.email.EmailService;
import com.servicelive.spn.services.email.EmailTemplateService;
import com.servicelive.spn.services.email.MailQueryStringFormatter;

/**
 * 
 * @author svanloon
 * 
 */
public class EditNetworkService extends BaseServices {

	private SPNHeaderDao spnheaderDao;
	private SPNProviderFirmStateDao spnProviderFirmStateDao;
	private SPNServiceProviderStateDao spnServiceProviderStateDao;
	private EmailTemplateService emailTemplateServices;
	private EmailService emailService;
	private ServiceProviderService serviceProviderService;
	private EmailAlertBO emailAlertBO;
	private MailQueryStringFormatter mailQueryStringFormatter;
	private SPNProviderFirmStateUsingIbatisDao spnProviderFirmStateUsingIbatisDao;
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void cleanAliases() throws Exception {
		// find spnet_hdr where isAlias = true and effective date is in the past
		List<SPNHeader> headers = spnheaderDao.findExpiredSPNNetworks();
		// for each record
		for (SPNHeader alias : headers) {
			deleteAlias(alias);
		}

	}

	/**
	 * 
	 * @param header
	 * @return SPNHeader
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public SPNHeader save(SPNHeader header, Boolean docEditted, Boolean auditRequired) throws Exception {

		Integer currentSpnId = header.getSpnId();
		SPNHeader originalSpnHeader = spnheaderDao.findById(currentSpnId);
		//originalSpnHeader.setExceptionsInd(header.getExceptionsInd());
		// step 2
		// backup approval criteria from spnet_approval_criteria for Orig SPN ID
		List<SPNApprovalCriteria> approvalCriteriasCopies = copyApprovalCriteria(originalSpnHeader.getApprovalCriterias());
		// backup Document Criteria from spnet_document for Original SPN ID
		List<SPNDocument> spnDocumentCopies = copySpnDocuments(originalSpnHeader.getSpnDocuments());
		
		//SL-19025 to make a copy of exceptions after deleting exceptions for deleted credential
		List<SPNExclusionsVO> spnOriginalExceptionCopies = copySpnOriginalExceptions(originalSpnHeader.getSpnExceptions(),header.getApprovalCriterias());
		// step 3
		//SL-19025 to make a copy of original exceptions to copy into alias on edit
		List<SPNExclusionsVO> spnAliasExceptionCopies = copySpnAliasExceptions(originalSpnHeader.getSpnExceptions());

		handleOldAliases(currentSpnId);

		// step 4
		// insert into spnet_hdr spnId = new spnId
		// @see step 3 part 5
		// step 5 a,b
		// insert into spnet_approval_criteria set alias spnId = new spnId
		// insert into spnet_document set alias spnId = new spnId
		Date effectiveDate = header.getEffectiveDate();
		//SL 18898 Adding one more parameter to pass the criteria related with meet and greet 
		SPNHeader newAliasSpnHeader = createNewSpn(originalSpnHeader, approvalCriteriasCopies, spnDocumentCopies, effectiveDate,spnAliasExceptionCopies);
		
		//Sl-19025: changed to update from save 
		spnheaderDao.save(newAliasSpnHeader); // Note: this time the spnId is
		//SL-18901 Adding code to consider meetNGreet criteria and backing up meetNGreet Criteria from spnet_document for Original SPN ID
		//First checking if the criteria has meet and greet condition selected
//		Integer meetAndGreetCriteriId=19;
		for(SPNApprovalCriteria approvalCriteria:approvalCriteriasCopies)
		{
			if(approvalCriteria.getCriteriaId().getId().equals(SPNBackendConstants.MEET_AND_GREET_CRITERIA_ID))
			{
			List<SPNMeetAndGreetStateVO> meetNGreet = spnProviderFirmStateUsingIbatisDao.findMeetAndGreetCriteriaBySpnId(currentSpnId);									// blank
			List<SPNMeetAndGreetStateVO> meetNGreetCopies=copySpnMeetAndGreetCriteria(meetNGreet,newAliasSpnHeader.getSpnId());
			spnProviderFirmStateUsingIbatisDao.insertMeetAndGreetCriteriaForAliasSPN(meetNGreetCopies);	
			}
		}
		header.setEffectiveDate(null); // the effective date is only stored on
										// the alias
		if(header.getExceptionsInd()){
			for (SPNExclusionsVO spnExclusionsVO : spnOriginalExceptionCopies) {
				spnExclusionsVO.setSpn(header);
			}
			header.setSpnExceptions(spnOriginalExceptionCopies);
			// to reset exceptions ind if no exceptions exist
			if(spnOriginalExceptionCopies.isEmpty()){
				header.setExceptionsInd(false);
			}
		}
		// step 5
		// insert into spnet_approval_criteria set spnId = new spnId
		// insert into spnet_document set spnId = new spnId
		spnheaderDao.removeExistingApprovalCriteria(currentSpnId);
		
		// to remove existing exceptions if there existed any which will be added again on update
		// to remove existing exceptions if the exception ind is unchecked on edit.
		if(originalSpnHeader.getExceptionsInd()|| !(header.getExceptionsInd())){
			spnheaderDao.removeExistingExceptions(currentSpnId);
		}
		// the insert actually happens back in the CreateNetworkService during
		// the spnheaderDao.update()

		// step 6
		// select provider firm id from spnet_provider_firm_state where
		// provider_wf_state in PF SPN...INTERESTED, APPLICANT, APPLICANT
		// INCOMPLETE, REAPPLICANT
		List<ServiceProvider> nonmemberList = serviceProviderService.findBySPNProviderFirmStates(currentSpnId, findProviderFirmNonMemberStates());

		// select provider firm id from spnet_provider_firm_state where
		// provider_wf_state in PF SPN MEMBER, PF FIRM OUT OF COMPLIANCE
		List<ServiceProvider> memberList = serviceProviderService.findBySPNProviderFirmStates(currentSpnId, findProviderFirmMemberStates());

		// step 7
		// Send email 1
		email(nonmemberList, SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE, newAliasSpnHeader);
		// Send email 2
		email(memberList, SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER, newAliasSpnHeader);

		// step 8 (new step not defined on doc)

		// step ??
		long startTime=System.currentTimeMillis();
		logger.info("SL 18780 Start time before going inside method handleProviderFirmAndServiceProviderStates is "+startTime);
		handleProviderFirmAndServiceProviderStates(currentSpnId, newAliasSpnHeader.getSpnId(), docEditted, auditRequired);
		long totalTimeTaken=System.currentTimeMillis()-startTime;
		logger.info("SL 18780 Total time taken is "+totalTimeTaken+"End time is "+System.currentTimeMillis());

		return header;
	}

	private List<SPNExclusionsVO> copySpnAliasExceptions(List<SPNExclusionsVO> spnExceptions) {
		List<SPNExclusionsVO> spnExceptionCopies = new ArrayList<SPNExclusionsVO>();
		for (SPNExclusionsVO originalSPNException : spnExceptions) {
			SPNExclusionsVO exclusionsVO = mapExceptions(originalSPNException);
			spnExceptionCopies.add(exclusionsVO);
		}
		return spnExceptionCopies;
	}

	/**
	 * @param spnExceptions
	 * @param approvalCriterias
	 * @return
	 * 
	 * method to create a copy of only the required exceptions
	 * 
	 */
	public List<SPNExclusionsVO> copySpnOriginalExceptions(List<SPNExclusionsVO> spnExceptions, List<SPNApprovalCriteria> approvalCriterias) {
		List<SPNExclusionsVO> spnExceptionCopies = new ArrayList<SPNExclusionsVO>();
		for (SPNExclusionsVO originalSPNException : spnExceptions) {
			for(SPNApprovalCriteria approvalCriteria : approvalCriterias){
				if((SPNBackendConstants.CRED_TYPE_VENDOR).equals(originalSPNException.getExceptionCredentialType())){
					// for criteria ProviderFirmCred
					if(((Integer)(approvalCriteria.getCriteriaId().getId())).intValue()==13 && null== originalSPNException.getCredentialCategoryId()){
						if((approvalCriteria.getValue()).equals(originalSPNException.getCredentialTypeId().toString())){
							SPNExclusionsVO exclusionsVO = mapExceptions(originalSPNException);
							spnExceptionCopies.add(exclusionsVO);
						}
					}
					// for criteria ProviderFirmCredCat
					else if(((Integer)(approvalCriteria.getCriteriaId().getId())).intValue()==14 && null!= originalSPNException.getCredentialCategoryId()){
						if((approvalCriteria.getValue()).equals(originalSPNException.getCredentialCategoryId().toString())){
							SPNExclusionsVO exclusionsVO = mapExceptions(originalSPNException);
							spnExceptionCopies.add(exclusionsVO);
						}
					}
				}
				else if((SPNBackendConstants.CRED_TYPE_RESOURCE).equals(originalSPNException.getExceptionCredentialType())){
					// for criteria ProviderCred
					if(((Integer)(approvalCriteria.getCriteriaId().getId())).intValue()==16 && null== originalSPNException.getCredentialCategoryId()){
						if((approvalCriteria.getValue()).equals(originalSPNException.getCredentialTypeId().toString())){
							SPNExclusionsVO exclusionsVO = mapExceptions(originalSPNException);
							spnExceptionCopies.add(exclusionsVO);
						}
					}
					// for  criteria ProviderCredCat
					else if(((Integer)(approvalCriteria.getCriteriaId().getId())).intValue()==17 && null!= originalSPNException.getCredentialCategoryId()){
						if((approvalCriteria.getValue()).equals(originalSPNException.getCredentialCategoryId().toString())){
							SPNExclusionsVO exclusionsVO = mapExceptions(originalSPNException);
							spnExceptionCopies.add(exclusionsVO);
						}
					}
				}
				
			}
			
		}
		return spnExceptionCopies;
	}

	/**
	 * @param originalSPNException
	 * @return
	 * to map original exceptions to alias
	 * 
	 */
	private SPNExclusionsVO mapExceptions(SPNExclusionsVO originalSPNException) {
		SPNExclusionsVO exclusionsVO = new SPNExclusionsVO();
		exclusionsVO.setActiveInd(originalSPNException.getActiveInd());
		exclusionsVO.setBuyerId(originalSPNException.getBuyerId());
		exclusionsVO.setComments(originalSPNException.getComments());
		exclusionsVO.setCreatedDate(originalSPNException.getCreatedDate());
		exclusionsVO.setCredentialCategoryId(originalSPNException.getCredentialCategoryId());
		exclusionsVO.setCredentialTypeId(originalSPNException.getCredentialTypeId());
		exclusionsVO.setExceptionTypeId(originalSPNException.getExceptionTypeId());
		exclusionsVO.setExceptionCredentialType(originalSPNException.getExceptionCredentialType());
		exclusionsVO.setExceptionValue(originalSPNException.getExceptionValue());
		exclusionsVO.setModifiedBy(originalSPNException.getModifiedBy());
		exclusionsVO.setModifiedDate(originalSPNException.getModifiedDate());
		return exclusionsVO;
		}

	//SL 18780 Modified whole method handleProviderFirmAndServiceProviderStates to move SPN from Hibernate to Ibatis persistence framework for performance tuning 
	@Transactional(propagation = Propagation.REQUIRED)
	private void handleProviderFirmAndServiceProviderStates(Integer currentSpnId, Integer aliasSpnId, Boolean docEditted, Boolean auditRequired) throws Exception {
		boolean isDocEditted = docEditted != null && docEditted.booleanValue(); // handle
																				// null

		List<String> memberState=new ArrayList<String>();
		memberState.add(WF_STATUS_PF_SPN_MEMBER);
		memberState.add(WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE);
		
		logger.info("docEditted                 : " + docEditted);
		logger.info("currentSpnId               : " + currentSpnId);
		logger.info("aliasSpnId                 : " + aliasSpnId);
		logger.info("auditRequired              : " + auditRequired);
		logger.info("List of memberState        : " + memberState);
		
		//Using Ibatis framework to fetch provider firm under current spn id and wf state WF_STATUS_PF_SPN_MEMBER and WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE
		long startTimefindProviderFirmBySpnId=System.currentTimeMillis();
		logger.info("Start time before going inside findProviderFirmBySpnIdAndSPNWorkFlowStates method is"+startTimefindProviderFirmBySpnId);
		List<SPNProviderFirmStateVO> memberProviderFirmStates = spnProviderFirmStateUsingIbatisDao.findProviderFirmBySpnIdAndSPNWorkFlowStates(currentSpnId, memberState);
		logger.info("End time after findProviderFirmBySpnIdAndSPNWorkFlowStates is "+System.currentTimeMillis());
		long totalTimeTakenfindProviderFirmBySpnId=System.currentTimeMillis()-startTimefindProviderFirmBySpnId;
		logger.info("Total time taken to complete the job in findProviderFirmBySpnIdAndSPNWorkFlowStates is"+totalTimeTakenfindProviderFirmBySpnId);
		logger.info("memberProviderFirmStates" + memberProviderFirmStates.size());
		//Copying alias spn id to memberProviderFirmStates
		List<SPNProviderFirmStateVO> copyProviderFirms = copyProviderFirm(memberProviderFirmStates,aliasSpnId);
		logger.info("copyProviderFirms" + copyProviderFirms.size());
		
		List<String> approvedState=new ArrayList<String>();
		approvedState.add(SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED);
		approvedState.add(SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
		
		logger.info("List of approvedState        : " + approvedState);
		
		if (isDocEditted) {
			logger.info("If isDocEditted   : " + isDocEditted);
			
			// Update wf state to APPLICANT INCOMPLETE for provider firm  with wf state MEMBERS and OOC 
			SPNProviderFirmStateVO sPNProviderFirmStateVO=new SPNProviderFirmStateVO();
			sPNProviderFirmStateVO.setSpnId(currentSpnId);
			sPNProviderFirmStateVO.setStates(memberState);
			sPNProviderFirmStateVO.setWfState(SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE);
			sPNProviderFirmStateVO.setAuditRequired(auditRequired);
			long startTimeUpdateSPNProviderFirm=System.currentTimeMillis();
			logger.info("Start time before going inside startTime updateSPNProviderFirmState method is"+startTimeUpdateSPNProviderFirm);
			spnProviderFirmStateUsingIbatisDao.updateSPNProviderFirmState(sPNProviderFirmStateVO);
			logger.info("End time after updateSPNProviderFirmState is "+System.currentTimeMillis());
			long totalTimeTakenUpdateSPNProviderFirm=System.currentTimeMillis()-startTimeUpdateSPNProviderFirm;
			logger.info("Total time taken to complete the job in updateSPNProviderFirmState is"+totalTimeTakenUpdateSPNProviderFirm);
			//To set the auditRequired indicator for the already incomplete members of the current SPN
			long startTimehandleIncompleteFirms=System.currentTimeMillis();
			logger.info("Start time before going inside startTime handleIncompleteFirmsForExistingSPN method is"+startTimehandleIncompleteFirms);
			handleIncompleteFirmsForExistingSPN(currentSpnId,aliasSpnId,auditRequired);
			logger.info("End time after handleIncompleteFirmsForExistingSPN is "+System.currentTimeMillis());
			long totalTimeTakenhandleIncompleteFirms=System.currentTimeMillis()-startTimehandleIncompleteFirms;
			logger.info("Total time taken to complete the job in handleIncompleteFirmsForExistingSPN is"+totalTimeTakenhandleIncompleteFirms);
			
			
			// Insert new state of MEMBER and OOC
			long startTimehandleinsertProviderFirms=System.currentTimeMillis();
			logger.info("Start time before going inside startTime insertProviderFirmsForAliasSPN method is"+startTimehandleinsertProviderFirms);
			spnProviderFirmStateUsingIbatisDao.insertProviderFirmsForAliasSPN(copyProviderFirms);
			logger.info("End time after insertProviderFirmsForAliasSPN is "+System.currentTimeMillis());
			long totalTimeTakeninsertProviderFirms=System.currentTimeMillis()-startTimehandleinsertProviderFirms;
			logger.info("Total time taken to complete the job in insertProviderFirmsForAliasSPN is"+totalTimeTakeninsertProviderFirms);
			

			/* All Service Providers that were APPROVED and OOC of old spnId
			 will be update with new Id. Update serivce provider with wf state WF_STATUS_SP_SPN_APPROVED and 
			 WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE with alias spn id*/
//			List<String> states=new  ArrayList<String>();
//			states.add(SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
//			states.add(SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED);
			long startTimehandleupdateSPNId=System.currentTimeMillis();
			logger.info("Start time before going inside startTime updateSPNId method is"+startTimehandleupdateSPNId);
			spnProviderFirmStateUsingIbatisDao.updateSPNId(currentSpnId, aliasSpnId, approvedState);
			logger.info("End time after insertProviderFirmsForAliasSPN is "+System.currentTimeMillis());
			long totalTimeTakenupdateSPNId=System.currentTimeMillis()-startTimehandleupdateSPNId;
			logger.info("Total time taken to complete the job in insertProviderFirmsForAliasSPN is"+totalTimeTakenupdateSPNId);
			//SL-19025 Fix for count of Removed provider firm on edit of spn
			//handling the removed provider firm on edit of spn
			handleRemovedProviderFirm(currentSpnId, aliasSpnId);
			long startTimehandlehandleRemoved=System.currentTimeMillis();
			logger.info("Start time before going inside startTime handleRemoved method is"+startTimehandlehandleRemoved);
			handleRemoved(currentSpnId, aliasSpnId);
			logger.info("End time after handleRemoved is "+System.currentTimeMillis());
			long totalTimeTakenhandleRemoved=System.currentTimeMillis()-startTimehandlehandleRemoved;
			logger.info("Total time taken to complete the job in handleRemoved is"+totalTimeTakenhandleRemoved);
		} else {
			logger.info("else isDocEditted   : " + isDocEditted);
			// CURRENT
			// Update all PF MEMBER to OOC
			// Update wf state to WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE for provider firm  with wf state MEMBERS and OOC 
			SPNProviderFirmStateVO sPNProviderFirmStateVO=new SPNProviderFirmStateVO();
			sPNProviderFirmStateVO.setSpnId(currentSpnId);
			sPNProviderFirmStateVO.setStates(memberState);
			sPNProviderFirmStateVO.setWfState(SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE);
			sPNProviderFirmStateVO.setAuditRequired(auditRequired);
			long startTimeupdateSPNProviderFirmState=System.currentTimeMillis();
			logger.info("Start time before going inside startTime updateSPNProviderFirmState method is"+startTimeupdateSPNProviderFirmState);
			spnProviderFirmStateUsingIbatisDao.updateSPNProviderFirmState(sPNProviderFirmStateVO);
			logger.info("End time after updateSPNProviderFirmState is "+System.currentTimeMillis());
			long totalTimeTakenupdateSPNProviderFirm=System.currentTimeMillis()-startTimeupdateSPNProviderFirmState;
			logger.info("Total time taken to complete the job in updateSPNProviderFirmState is"+totalTimeTakenupdateSPNProviderFirm);
			//To set the auditRequired indicator for the already incomplete members of the current SPN
			long startTimehandleIncompleteFirms=System.currentTimeMillis();
			logger.info("Start time before going inside startTime handleIncompleteFirmsForExistingSPN method is"+startTimehandleIncompleteFirms);
			handleIncompleteFirmsForExistingSPN(currentSpnId,aliasSpnId,auditRequired);
			logger.info("End time after handleIncompleteFirmsForExistingSPN is "+System.currentTimeMillis());
			long totalTimeTakenhandleIncompleteFirms=System.currentTimeMillis()-startTimehandleIncompleteFirms;
			logger.info("Total time taken to complete the job in handleIncompleteFirmsForExistingSPN is"+totalTimeTakenhandleIncompleteFirms);
			/* Update all SP APPROVED to OOC*/
			approvedState.add(SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED);
			logger.info("else if List of approvedState        : " + approvedState);
		  //Using Ibatis framework to fetch service provider under current spn id and wf state WF_STATUS_PF_SPN_MEMBER and WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE
			long startTimefindSericeProvider=System.currentTimeMillis();
			logger.info("Start time before going inside startTime findSericeProviderBySpnIdAndSPNWorkFlowStates method is"+startTimefindSericeProvider);
			List<SPNServiceProviderStateVO> memberServiceProviders = spnProviderFirmStateUsingIbatisDao.findSericeProviderBySpnIdAndSPNWorkFlowStates(currentSpnId, approvedState);
			logger.info("End time after findSericeProviderBySpnIdAndSPNWorkFlowStates is "+System.currentTimeMillis());
			long totalTimeTakenfindSericeProvider=System.currentTimeMillis()-startTimefindSericeProvider;
			logger.info("Total time taken to complete the job in findSericeProviderBySpnIdAndSPNWorkFlowStates is"+totalTimeTakenfindSericeProvider);
			List<SPNServiceProviderStateVO> copyServiceProvider = copyServiceProvider(memberServiceProviders,aliasSpnId);
			logger.info("else if List of copyServiceProvider        : " + copyServiceProvider.size());
			approvedState.remove(SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED);
			spnProviderFirmStateUsingIbatisDao.updateWfStateOnly(approvedState,SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE,currentSpnId);
			// ALIAS
			// Insert new state of whatever state provider firm was in for new
			// alias spn_id
			long startTimeinsertProviderFirms=System.currentTimeMillis();
			logger.info("Start time before going inside startTime insertProviderFirmsForAliasSPN method is"+startTimeinsertProviderFirms);
			spnProviderFirmStateUsingIbatisDao.insertProviderFirmsForAliasSPN(copyProviderFirms);
			logger.info("End time after insertProviderFirmsForAliasSPN is "+System.currentTimeMillis());
			long totalTimeTakeninsertProviderFirms=System.currentTimeMillis()-startTimeinsertProviderFirms;
			logger.info("Total time taken to complete the job in insertProviderFirmsForAliasSPN is"+totalTimeTakeninsertProviderFirms);
			long startTimeinsertServiceProvider=System.currentTimeMillis();
			logger.info("Start time before going inside startTime insertServiceProviderForAliasSPN method is"+startTimeinsertServiceProvider);
			spnProviderFirmStateUsingIbatisDao.insertServiceProviderForAliasSPN(copyServiceProvider);
			logger.info("End time after insertServiceProviderForAliasSPN is "+System.currentTimeMillis());
			long totalTimeTakeninsertServiceProvider=System.currentTimeMillis()-startTimeinsertServiceProvider;
			logger.info("Total time taken to complete the job in insertServiceProviderForAliasSPN is"+totalTimeTakeninsertServiceProvider);
			// Copy the Removed providers to new alias spnId. (They will all be in removed state for both SPNs)
			//handleRemoved(currentSpnId, aliasSpnId);
			//SL-19025 Fix for count of Removed provider firm on edit of spn
			//handling the removed provider firm on edit of spn
			handleRemovedProviderFirm(currentSpnId, aliasSpnId);
		}
		
		// SL-12300 : Following lines make sure that all the active overridden information of the current SPN will be copied to the new SPN.
		// It will fetch all active overridden providers/firms from spnet_provider_network_override/spnet_provider_firm_network_override table and insert it in the same table with the alias spnId.
		// It will also disable all overridden information for original SPN.
		spnProviderFirmStateUsingIbatisDao.insertSpnetProviderFirmNetworkOverride(currentSpnId, aliasSpnId);
		spnProviderFirmStateUsingIbatisDao.insertSpnetProviderNetworkOverride(currentSpnId, aliasSpnId);
	}
	//SL 18780 Created method to copy provider firm vo with alias spn id to a new provider firm VO
	private List<SPNProviderFirmStateVO> copyProviderFirm(List<SPNProviderFirmStateVO> states,Integer aliasSpnId){
		List<SPNProviderFirmStateVO> spnProviderFirmList = new ArrayList<SPNProviderFirmStateVO>();
		for (SPNProviderFirmStateVO original : states) {
			SPNProviderFirmStateVO copyFirm = new SPNProviderFirmStateVO();
			copyFirm.setAgreementInd(original.getAgreementInd());
			copyFirm.setApplicationSubmissionDate(original.getApplicationSubmissionDate());
			copyFirm.setComment(original.getComment());
			copyFirm.setCreatedDate(original.getCreatedDate());
			copyFirm.setModifiedBy(original.getModifiedBy());
			copyFirm.setModifiedDate(original.getModifiedDate());
			copyFirm.setOptOutComment(original.getOptOutComment());
			if(null!=original.getOptOutReason())
			{
			  String optOutReason=original.getOptOutReason().toString();
			  copyFirm.setOptOutReason(optOutReason);
			}
			else
			{
				copyFirm.setOptOutReason(null);
			}
			
			
			//SPNProviderFirmStatePk pk = new SPNProviderFirmStatePk(original.getProviderFirmStatePk().getSpnHeader().getSpnId(), original.getProviderFirmStatePk().getProviderFirm().getId());
			//copy.setProviderFirmStatePk(pk);
			copyFirm.setSpnId(aliasSpnId);
			if(null!=original.getFirmId())
				{
					copyFirm.setFirmId(original.getFirmId());
				}
				else
				{
					copyFirm.setFirmId(null);
				}
			
			
		
				copyFirm.setReviewedBy(original.getReviewedBy());
			copyFirm.setReviewedDate(original.getReviewedDate());
			if(null!=original.getWfState())
			{
				copyFirm.setWfState(original.getWfState());
			}
			else
			{
				copyFirm.setWfState(null);
			}
			
			copyFirm.setStatusActionId(original.getStatusActionId());
			copyFirm.setStatusOverrideReasonId(original.getStatusOverrideReasonId());
			
			if(null!=original.getStatusOverrideComments())
			{
			copyFirm.setStatusOverrideComments(original.getStatusOverrideComments());
			}
			else
			{
				copyFirm.setStatusOverrideComments(null);	
			}
			if(null!=original.getStatusOverrideState())
			{
			copyFirm.setStatusOverrideState(original.getStatusOverrideState());
			}
			else
			{
				copyFirm.setStatusOverrideState(null);
			}
			copyFirm.setPerformanceScore(original.getPerformanceScore());
			copyFirm.setOverrideInd(original.getOverrideInd());
			// FIXME when providerfirm state is changed, this will need to be
			// fixed also.
			spnProviderFirmList.add(copyFirm);
		}
		
		return spnProviderFirmList;
		
	}

	//SL-19025 Fix for count of Removed provider firm on edit of spn
	//handling the removed provider firm on edit of spn
	@Transactional(propagation = Propagation.REQUIRED)
	private void handleRemovedProviderFirm(Integer currentSpnId, Integer aliasSpnId) throws Exception {
		// Copy the Removed providers firms to new alias spnId. (They will all be in removed state for both SPNs)
		List<String> removedState=new ArrayList<String>();
		removedState.add(SPNBackendConstants.WF_STATUS_PF_SPN_REMOVED_FIRM);
	
		List<SPNProviderFirmStateVO> removedServiceProviders= spnProviderFirmStateUsingIbatisDao.findProviderFirmBySpnIdAndSPNWorkFlowStates(currentSpnId, removedState);
		
		List<SPNProviderFirmStateVO> copyProviderFirm = copyProviderFirm(removedServiceProviders,aliasSpnId);
		
		spnProviderFirmStateUsingIbatisDao.insertProviderFirmsForAliasSPN(copyProviderFirm);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void handleRemoved(Integer currentSpnId, Integer aliasSpnId) throws Exception {
		// Copy the Removed providers to new alias spnId. (They will all be in removed state for both SPNs)
		
		List<String> removedState=new ArrayList<String>();
		removedState.add(SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED);
	
		List<SPNServiceProviderStateVO> removedServiceProviders= spnProviderFirmStateUsingIbatisDao.findSericeProviderBySpnIdAndSPNWorkFlowStates(currentSpnId, removedState);
		
		List<SPNServiceProviderStateVO> copyServiceProvider = copyServiceProvider(removedServiceProviders,aliasSpnId);
		
		spnProviderFirmStateUsingIbatisDao.insertServiceProviderForAliasSPN(copyServiceProvider);
	}

	private List<SPNProviderFirmState> copyProviderFirmState(List<SPNProviderFirmState> states) {
		List<SPNProviderFirmState> results = new ArrayList<SPNProviderFirmState>();
		for (SPNProviderFirmState original : states) {
			SPNProviderFirmState copy = new SPNProviderFirmState();
			copy.setAgreementInd(original.getAgreementInd());
			copy.setApplicationSubmissionDate(original.getApplicationSubmissionDate());
			copy.setComment(original.getComment());
			copy.setCreatedDate(original.getCreatedDate());
			copy.setModifiedBy(original.getModifiedBy());
			copy.setModifiedDate(original.getModifiedDate());
			copy.setOptOutComment(original.getOptOutComment());
			copy.setOptOutReason(original.getOptOutReason());
			SPNProviderFirmStatePk pk = new SPNProviderFirmStatePk(original.getProviderFirmStatePk().getSpnHeader().getSpnId(), original.getProviderFirmStatePk().getProviderFirm().getId());
			copy.setProviderFirmStatePk(pk);
			copy.setReviewedBy(original.getReviewedBy());
			copy.setReviewedDate(original.getReviewedDate());
			copy.setWfState(original.getWfState());
			copy.setLookupSPNStatusActionMapper(original.getLookupSPNStatusActionMapper());
			copy.setLookupSPNStatusOverrideReason(original.getLookupSPNStatusOverrideReason());
			copy.setStatusOverrideComments(original.getStatusOverrideComments());
			copy.setStatusOverrideState(original.getStatusOverrideState());
			// FIXME when providerfirm state is changed, this will need to be
			// fixed also.
			results.add(copy);
		}
		return results;
	}
	//SL 18780 Created method to copy service provider vo with alias spn id to a new service provider VO
	//Newly created copy method to copy service provider with alias spn id
	private List<SPNServiceProviderStateVO> copyServiceProvider(List<SPNServiceProviderStateVO> states,Integer aliasSpnId) {
		List<SPNServiceProviderStateVO> results = new ArrayList<SPNServiceProviderStateVO>();
		for (SPNServiceProviderStateVO original : states) {
			SPNServiceProviderStateVO copy = new SPNServiceProviderStateVO();
			copy.setCreatedDate(original.getCreatedDate());
			copy.setModifiedBy(original.getModifiedBy());
			copy.setModifiedDate(original.getModifiedDate());
			copy.setServiceProviderId(original.getServiceProviderId());
			copy.setSpnId(aliasSpnId);
			if(null!=original.getPerformanceLevel())
			{
			copy.setPerformanceLevel(original.getPerformanceLevel());
			}
			else
			{
				copy.setPerformanceLevel(null);	
			}
			copy.setPerformanceLevelChangeComments(original.getPerformanceLevelChangeComments());
			copy.setProviderWfState(original.getProviderWfState());
			copy.setStatusOverrideReasonId(original.getStatusOverrideReasonId());
			copy.setStatusOverrideComments(original.getStatusOverrideComments());
	     	copy.setStatusUpdateActionId(original.getStatusUpdateActionId());
	     	copy.setPerformanceScore(original.getPerformanceScore());
	     	copy.setOverrideInd(original.getOverrideInd());
	     	results.add(copy);
		}
		return results;
	}
	
	private List<SPNServiceProviderState> copyServiceProviderState(List<SPNServiceProviderState> states) {
		List<SPNServiceProviderState> results = new ArrayList<SPNServiceProviderState>();
		for (SPNServiceProviderState original : states) {
			SPNServiceProviderState copy = new SPNServiceProviderState();
			copy.setCreatedDate(original.getCreatedDate());
			copy.setModifiedBy(original.getModifiedBy());
			copy.setModifiedDate(original.getModifiedDate());
			copy.setServiceProviderStatePk(new SPNServiceProviderStatePk(original.getServiceProviderStatePk().getSpnHeader().getSpnId(), original.getServiceProviderStatePk().getServiceProvider().getId()));
			copy.setWfState(original.getWfState());
			copy.setLookupSPNStatusActionMapper(original.getLookupSPNStatusActionMapper());
			copy.setLookupSPNStatusOverrideReason(original.getLookupSPNStatusOverrideReason());
			copy.setPerformanceLevel(original.getPerformanceLevel());
			copy.setStatusOverrideComments(original.getStatusOverrideComments());
			logger.info("SL 18780 Adding code to fix bug");
			results.add(copy);
		}
		return results;
	}

	private List<LookupSPNWorkflowState> findProviderFirmNonMemberStates() {
		List<LookupSPNWorkflowState> nonmemberStates = new ArrayList<LookupSPNWorkflowState>();
		nonmemberStates.add(new LookupSPNWorkflowState(WF_STATUS_PF_SPN_INTERESTED));
		nonmemberStates.add(new LookupSPNWorkflowState(WF_STATUS_PF_SPN_APPLICANT));
		nonmemberStates.add(new LookupSPNWorkflowState(WF_STATUS_PF_APPLICANT_INCOMPLETE));
		nonmemberStates.add(new LookupSPNWorkflowState(WF_STATUS_PF_SPN_REAPPLICANT));
		return nonmemberStates;
	}

	private List<LookupSPNWorkflowState> findProviderFirmMemberStates() {
		List<LookupSPNWorkflowState> memberStates = new ArrayList<LookupSPNWorkflowState>();
		memberStates.add(new LookupSPNWorkflowState(WF_STATUS_PF_SPN_MEMBER));
		memberStates.add(new LookupSPNWorkflowState(WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE));
		return memberStates;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void deleteAlias(SPNHeader aliasSpnHeader) throws Exception {

		Integer spnId = aliasSpnHeader.getSpnId();
		aliasSpnHeader.getApprovalCriterias().clear();
		aliasSpnHeader.getSpnDocuments().clear();
		aliasSpnHeader.getSpnExceptions().clear();
		spnheaderDao.save(aliasSpnHeader);
		// Delete all override information
		spnheaderDao.deleteNetworkOverrideInfo(spnId);
		// Delete all alias spn id from spnet_approval_criteria for orig spnId
		// if exists
		spnheaderDao.removeExistingApprovalCriteria(spnId);
		// delete all alias spn id from spnet_document for orig spnId if exists
		spnheaderDao.removeExistingDocuments(spnId);

		//SL-19025 to delete exceptions of alias after effective date expiry.
		spnheaderDao.removeExistingExceptions(spnId);
		
		// delete all alias spn id from spnet_serviceprovider_state from orig
		// spnId if exists
		spnProviderFirmStateDao.delete(spnId);
		spnProviderFirmStateUsingIbatisDao.deleteMeetAndGreet(spnId);
		
		spnServiceProviderStateDao.delete(spnId);

		// NOTE: There shouldn't exist any records in spnet_campaign_network
		// NOTE: The history tables shouldn't have Relation Integrity(RI) to
		// spnheader.

		// part 5 moved from step4
		// delete from spnet_hdr where spnet_hdr.is_alias is true and
		// spnet_hdr.alias_id = spnId
		spnheaderDao.delete(aliasSpnHeader);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void handleOldAliases(Integer spnId) throws Exception {
		List<SPNHeader> spnHeaderAliases = spnheaderDao.findAliases(spnId);
		for (SPNHeader aliasSpnHeader : spnHeaderAliases) {
			deleteAlias(aliasSpnHeader);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private SPNHeader createNewSpn(SPNHeader spnHeader, List<SPNApprovalCriteria> approvalCriteriasCopies, List<SPNDocument> spnDocumentCopies, Date effectiveDate, List<SPNExclusionsVO> spnAliasExceptionCopies) throws Exception {
		SPNHeader spn = new SPNHeader();
		spn.setAliasOriginalSpnId(spnHeader.getSpnId());
		// will do later in method spn.setApprovalCriterias(approvalCriterias);
		// will do later in method spn.setBuyer();
		spn.setComments(spnHeader.getComments());
		spn.setContactEmail(spnHeader.getContactEmail());
		spn.setContactName(spnHeader.getContactName());
		spn.setContactPhone(spnHeader.getContactPhone());
		spn.setCreatedDate(CalendarUtil.getNow());
		spn.setEffectiveDate(effectiveDate);
		spn.setIsAlias(Boolean.TRUE);
		spn.setModifiedBy(spnHeader.getModifiedBy());
		spn.setModifiedDate(CalendarUtil.getNow());
		spn.setSpnDescription(spnHeader.getSpnDescription());
		// will do later in method spn.setSpnDocuments(spnDocuments);
		// spn.setSpnId(spnId) // this is auto generated when saved
		spn.setSpnInstruction(spnHeader.getSpnInstruction());
		spn.setSpnName(spnHeader.getSpnName());
		spn.setExceptionsInd(spnHeader.getExceptionsInd());
		spn.setCriteriaLevel(spnHeader.getCriteriaLevel());
		spn.setCriteriaTimeframe(spnHeader.getCriteriaTimeframe());
		spn.setRoutingPriorityStatus(spnHeader.getRoutingPriorityStatus());
		spn.setMarketPlaceOverFlow(spnHeader.getMarketPlaceOverFlow());
		for (SPNApprovalCriteria approvalCriteria : approvalCriteriasCopies) {
			approvalCriteria.setSpnId(spn);
		}
		spn.setApprovalCriterias(approvalCriteriasCopies);

		for (SPNDocument spnDocument : spnDocumentCopies) {
			spnDocument.setSpn(spn);
		}
		
		spn.setSpnDocuments(spnDocumentCopies);

		for (SPNExclusionsVO spnExclusionsVO : spnAliasExceptionCopies) {
			spnExclusionsVO.setSpn(spn);
		}
		
		spn.setSpnExceptions(spnAliasExceptionCopies);
		
		if(spnAliasExceptionCopies.isEmpty()){
			spn.setExceptionsInd(false);
		}
		for (SPNBuyer orig : spnHeader.getBuyer()) {
			SPNBuyer buyer = new SPNBuyer();
			buyer.setCreatedDate(CalendarUtil.getNow());
			buyer.setModifiedDate(CalendarUtil.getNow());
			buyer.setModifiedBy(orig.getModifiedBy());
			buyer.setBuyerId(orig.getBuyerId());
			buyer.setSpnId(spn);
			spn.getBuyer().add(buyer);
		}

		return spn;
	}

	private void email(List<ServiceProvider> providerFirmAdmins, String state, SPNHeader header) throws Exception {
		List<Email> emails = new ArrayList<Email>();

		LookupSPNWorkflowState wfState = new LookupSPNWorkflowState();
		wfState.setId(state);
		AlertTask alertTask = null;
		List<LookupEmailTemplate> templates = emailTemplateServices.findEmailTemplatesByLookupSPNWorkflowState(wfState, SPNBackendConstants.EMAIL_ACTION_SPN_EDITED);
		for (ServiceProvider providerFirmAdmin : providerFirmAdmins) {
			for (LookupEmailTemplate template : templates) {
				Email email = new Email();
				email.setTemplate(template);
				email.addTo(providerFirmAdmin.getContact().getEmail());
				Map<String, String> params = new HashMap<String, String>();
				params.put("providerFirmContactName", providerFirmAdmin.getContact().getFirstName() + " " + providerFirmAdmin.getContact().getLastName());
				params.put("providerFirmContactEmail", providerFirmAdmin.getContact().getEmail());
				params.put("buyerAdminContact", header.getContactName());
				params.put("buyerAdminContactEmail", header.getContactEmail());
				params.put("spnName", header.getSpnName());
				params.put("providerFirmContact", providerFirmAdmin.getContact().getFirstName() + " " + providerFirmAdmin.getContact().getLastName());
				params.put("effectiveDate", CalendarUtil.formatDateForDisplay(header.getEffectiveDate()));
				params.put("spnContactName", header.getContactName());
				params.put("spnContactPhone", header.getContactPhone());
				params.put("spnOwnerCompanyName", header.getBuyer().iterator().next().getBuyerId().getBusinessName());
				params.put("buyerId", String.valueOf(header.getBuyer().iterator().next().getBuyerId().getBuyerId()));
				// params.put("spnCompanyContactAddress", header.get);
				params.put("spnCompanyContactPhone", header.getContactPhone());
				params.put("spnCompanyContactEmail", header.getContactEmail());

				email.setParams(params);
				emails.add(email);
			}
		}

		for (Email email : emails) {
			alertTask = mailQueryStringFormatter.insertAlert(email);
			emailAlertBO.insertToAlertTask(alertTask);
			// emailService.sendEmail(email);
		}

		return;
	}

	private List<SPNApprovalCriteria> copyApprovalCriteria(List<SPNApprovalCriteria> originalApprovalCriterias) {
		List<SPNApprovalCriteria> approvalCriteriasCopies = new ArrayList<SPNApprovalCriteria>();
		for (SPNApprovalCriteria originalApprovalCriteria : originalApprovalCriterias) {
			SPNApprovalCriteria approvalCriteriasCopy = new SPNApprovalCriteria();
			approvalCriteriasCopy.setCreatedDate(CalendarUtil.getNow());
			approvalCriteriasCopy.setCriteriaId(originalApprovalCriteria.getCriteriaId());
			// approvalCriteriasCopy.setId(originalApprovalCriteria.getId()); //
			// this should be auto set
			approvalCriteriasCopy.setModifiedBy(originalApprovalCriteria.getModifiedBy());
			approvalCriteriasCopy.setModifiedDate(CalendarUtil.getNow());
			// approvalCriteriasCopy.setSpnId(originalApprovalCriteria.getSpnId());
			approvalCriteriasCopy.setValue(originalApprovalCriteria.getValue());
			approvalCriteriasCopies.add(approvalCriteriasCopy);
		}
		return approvalCriteriasCopies;
	}

	private List<SPNDocument> copySpnDocuments(List<SPNDocument> originalSPNDocuments) {
		List<SPNDocument> spnDocumentCopies = new ArrayList<SPNDocument>();
		for (SPNDocument originalSPNDocument : originalSPNDocuments) {
			SPNDocument spnDocumentCopy = new SPNDocument();
			spnDocumentCopy.setCreatedDate(originalSPNDocument.getCreatedDate());
			spnDocumentCopy.setDocTypeId(originalSPNDocument.getDocTypeId());
			spnDocumentCopy.setDocument(originalSPNDocument.getDocument());
			// spnDocumentCopy.setId(originalSPNDocument.getId());
			spnDocumentCopy.setIsInActive(originalSPNDocument.getIsInActive());
			spnDocumentCopy.setModifiedBy(originalSPNDocument.getModifiedBy());
			spnDocumentCopy.setModifiedDate(originalSPNDocument.getModifiedDate());
			// spnDocumentCopy.setSpn(originalSPNDocument.getSpn());
			spnDocumentCopies.add(spnDocumentCopy);
		}
		return spnDocumentCopies;
	}
	//SL-18898 Copying the original data to alias spn
	private List<SPNMeetAndGreetStateVO> copySpnMeetAndGreetCriteria(List<SPNMeetAndGreetStateVO> meetNGreet,Integer aliasSpnId) {
		List<SPNMeetAndGreetStateVO> meetNGreetCopies = new ArrayList<SPNMeetAndGreetStateVO>();
		for (SPNMeetAndGreetStateVO originalSPNMeetNGreet : meetNGreet) {
			SPNMeetAndGreetStateVO meetNGreetCopy = new SPNMeetAndGreetStateVO();
			meetNGreetCopy.setCreatedDate(originalSPNMeetNGreet.getCreatedDate());
			meetNGreetCopy.setMeetAndGreetDate(originalSPNMeetNGreet.getMeetAndGreetDate());
			meetNGreetCopy.setComments(originalSPNMeetNGreet.getComments());
			// spnDocumentCopy.setId(originalSPNDocument.getId());
			meetNGreetCopy.setMeetAndGreetPerson(originalSPNMeetNGreet.getMeetAndGreetPerson());
			meetNGreetCopy.setModifiedBy(originalSPNMeetNGreet.getModifiedBy());
			meetNGreetCopy.setModifiedDate(originalSPNMeetNGreet.getModifiedDate());
			meetNGreetCopy.setProviderFirmId(originalSPNMeetNGreet.getProviderFirmId());
			meetNGreetCopy.setMeetAndGreetStateId(originalSPNMeetNGreet.getMeetAndGreetStateId());
			meetNGreetCopy.setSpnId(aliasSpnId);
			// spnDocumentCopy.setSpn(originalSPNDocument.getSpn());
			meetNGreetCopies.add(meetNGreetCopy);
		}
		return meetNGreetCopies;
	}
	/**
	 * This method is used
	 * 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeExistingCriteria(SPNHeader entity) throws Exception {
		spnheaderDao.removeExistingApprovalCriteria(entity.getSpnId());
		// added for SL-19025 to delete exceptions on edit execptions will be copied after
		spnheaderDao.removeExistingExceptions(entity.getSpnId());
		// spnheaderDao.refresh(entity);
	}
	/**
	 * This method is used update the auditRequired indicator for the incomplete members of a SPN while it is being edited
	 * @param Integer
	 * @param Integer
	 * @param Boolean
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void handleIncompleteFirmsForExistingSPN(Integer currentSpnId, Integer aliasSpnId,Boolean auditRequired) throws Exception {
		
		String oldMarketState=WF_STATUS_PF_APPLICANT_INCOMPLETE;
		spnProviderFirmStateUsingIbatisDao.updateAuditRequiredInd(currentSpnId, oldMarketState,auditRequired);
	}

	/**
	 * @param spnheaderDao
	 *            the spnheaderDao to set
	 */
	public void setSpnheaderDao(SPNHeaderDao spnheaderDao) {
		this.spnheaderDao = spnheaderDao;
	}

	/**
	 * @param spnProviderFirmStateDao
	 *            the spnProviderFirmStateDao to set
	 */
	public void setSpnProviderFirmStateDao(SPNProviderFirmStateDao spnProviderFirmStateDao) {
		this.spnProviderFirmStateDao = spnProviderFirmStateDao;
	}

	/**
	 * @param emailTemplateServices
	 *            the emailTemplateServices to set
	 */
	public void setEmailTemplateServices(EmailTemplateService emailTemplateServices) {
		this.emailTemplateServices = emailTemplateServices;
	}

	/**
	 * @param emailService
	 *            the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * @param serviceProviderService
	 *            the serviceProviderService to set
	 */
	public void setServiceProviderService(ServiceProviderService serviceProviderService) {
		this.serviceProviderService = serviceProviderService;
	}

	/**
	 * @param spnServiceProviderStateDao
	 *            the spnServiceProviderStateDao to set
	 */
	public void setSpnServiceProviderStateDao(SPNServiceProviderStateDao spnServiceProviderStateDao) {
		this.spnServiceProviderStateDao = spnServiceProviderStateDao;
	}

	public EmailAlertBO getEmailAlertBO() {
		return emailAlertBO;
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public MailQueryStringFormatter getMailQueryStringFormatter() {
		return mailQueryStringFormatter;
	}

	public void setMailQueryStringFormatter(MailQueryStringFormatter mailQueryStringFormatter) {
		this.mailQueryStringFormatter = mailQueryStringFormatter;
	}
	public SPNProviderFirmStateUsingIbatisDao getSpnProviderFirmStateUsingIbatisDao() {
		return spnProviderFirmStateUsingIbatisDao;
	}

	public void setSpnProviderFirmStateUsingIbatisDao(
			SPNProviderFirmStateUsingIbatisDao spnProviderFirmStateUsingIbatisDao) {
		this.spnProviderFirmStateUsingIbatisDao = spnProviderFirmStateUsingIbatisDao;
	}

	public List<SPNExclusionsVO> getSPNExclusions(
			Integer spnId) {
		return spnheaderDao.getSPNExclusions(spnId);
	}
}
