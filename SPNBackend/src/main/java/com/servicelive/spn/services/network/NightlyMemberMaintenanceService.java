/**
 * 
 */
package com.servicelive.spn.services.network;

import java.math.BigDecimal;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.newco.marketplace.utils.DateUtils;

import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.exception.spn.membermaintenance.IllegalProviderFirmSuppliedException;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.CredentialsCriteriaVO;
import com.servicelive.spn.common.detached.MemberMaintenanceCriteriaVO;
import com.servicelive.spn.common.detached.MemberMaintenanceDetailsVO;
import com.servicelive.spn.common.detached.MemberMaintenanceProviderFirmVO;
import com.servicelive.spn.common.detached.ProviderMatchApprovalCriteriaVO;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author hoza
 *
 */
public class NightlyMemberMaintenanceService extends MemberMaintenanceService {
	/**
	 * 
	 * @throws Exception
	 */
	private static Double ONE_HOUR = (double) (60 * 60 * 1000f);

	public void maintainCompliance() throws Exception {
		List<Integer> spnIdList = providerMatchService.getAllSpnList();
		long startDate = System.currentTimeMillis();
		try {
			// SL-12300
			providerMatchService.evaluteProviderAndFirmNetworkOverridedInfo();
			
			/*
			 * evaluate firm credentials,resource credentials,minimum Rating
			 * complaince, Completed SO Complaince & Language Complaince
			 */
			previousCriteriaMap=providerMatchService.getPreviousCriteriaOfAllSpn();

			for (Integer spnId : spnIdList) {
				
				try
				{
				ApprovalModel approvalModel = providerMatchService
						.getApprovalModelForSPN(spnId);
				// get previous criteria for all  spn in the credential if any
            	long start = System.currentTimeMillis();
            	// process firm credentials
				processFirmCredentialsData(spnId,approvalModel);
            	long end = System.currentTimeMillis();
            	logger.info("processing firm credentials time"+(end-start));
				// process resource credentials
            	long start1=System.currentTimeMillis();
				processProviderCredentialsData(spnId,approvalModel);
				long end1=System.currentTimeMillis();
            	logger.info("processing provider credentials time"+(end1-start1));

				// process main services
				//sl-19133 - no need to display main service category seperately
				//processMainServicesData(spnId,
				//	approvalModel.getSelectedMainServices());
				// process skills
            	long start2=System.currentTimeMillis();
            	processSkillsData(spnId, approvalModel.getSelectedSkills());
				long end2=System.currentTimeMillis();
            	logger.info("processSkillsData time"+(end2-start2));

				// process categories
            	long start3=System.currentTimeMillis();
				processCategoriesData(spnId, approvalModel.getSelectedSubServices1(),SPNBackendConstants.CATEGORY_CRITERIA_ID);
            	long end3=System.currentTimeMillis();
            	logger.info("processCategoriesData time"+(end3-start3));

				// process sub categories
            	long start4=System.currentTimeMillis();
				processCategoriesData(spnId, approvalModel.getSelectedSubServices2(),SPNBackendConstants.SUB_CATEGORY_CRITERIA_ID);
            	long end4=System.currentTimeMillis();
            	logger.info("process sub CategoriesData time"+(end4-start4));

				// process language
            	long start5=System.currentTimeMillis();
            	processLanguageData(spnId, approvalModel.getSelectedLanguages());
            	long end5=System.currentTimeMillis();
            	logger.info("process Language Data time"+(end5-start5));

				// process minimmum rating
            	long start6=System.currentTimeMillis();
            	processMinimumRatingComplainceData(spnId,approvalModel.getSelectedMinimumRating());
            	long end6=System.currentTimeMillis();
            	logger.info("process processMinimumRatingComplainceData time"+(end6-start6));

				// process completed so criteria
            	long start7=System.currentTimeMillis();
            	processCompletedSoComplainceData(spnId,approvalModel.getMinimumCompletedServiceOrders());
            	long end7=System.currentTimeMillis();
            	logger.info("process processCompletedSoComplainceData time"+(end7-start7));
            		
            	// process Background Check criteria
            	long start8=System.currentTimeMillis();
            	processBackgroundCheckComplainceData(spnId);
            	long end8=System.currentTimeMillis();
            	logger.info("process processBackgroundCheckComplainceData time"+(end8-start8));
            		
            	
				}
				catch(Exception e){
					logger.info("error occured in inserting credential data for SPN "+spnId+" "+ e);
				}
			}
		    
			
			updateChangeinExceptionApplied();
			updateVendorActivityRegistryForBackgroundCheck();
			

		} catch (Exception e) {
			logger.error("Error in updating credential status" + e.getMessage());
		}
		for (Integer spnId : spnIdList) {
			try {
				long start1=System.currentTimeMillis();
				handleSpn(getMMCriteriaVO(spnId, null));
				long end1=System.currentTimeMillis();
				logger.info(" time taken to handle spn"+(end1-start1));
			} catch (IllegalProviderFirmSuppliedException pe) {
				logger.error(
						"This is SERIOUS CODE Issue, provider Firm is NOT Supposed to be Supplied for NightlyMemberMaintenance Job."
								+ pe.getMessage(), pe);
			} catch (Exception e) {
				logger.warn("Couldn't complete updating of spnid[" + spnId
						+ "]", e);
			}
		}
		logger.debug("NightlyMemberMaintenanceService Total::"
				+ (System.currentTimeMillis() - startDate));
	}

	
	
	public void updateChangeinExceptionApplied()
	{
		
		if(exceptionGrace.size()>0){
			providerMatchService.updateProviderExceptionAppliedGrace(exceptionGrace);	
		}
		if(exceptionState.size()>0){
			providerMatchService.updateProviderExceptionAppliedState(exceptionState);	

		}
		if(exceptionCombined.size()>0){
			providerMatchService.updateProviderExceptionAppliedCombined(exceptionCombined);	

		}
		if(exceptionNull.size()>0){
			providerMatchService.updateProviderExceptionAppliedNull(exceptionNull);	

		}
		
		
		if(firmexceptionGrace.size()>0){
			providerMatchService.updateFirmExceptionAppliedGrace(firmexceptionGrace);	
		}
		if(firmexceptionState.size()>0){
			providerMatchService.updateFirmExceptionAppliedState(firmexceptionState);	

		}
		if(firmexceptionCombined.size()>0){
			providerMatchService.updateFirmExceptionAppliedCombined(firmexceptionCombined);	

		}
		if(firmexceptionNull.size()>0){
			providerMatchService.updateFirmExceptionAppliedNull(firmexceptionNull);	

		}
		
		

		
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.network.MemberMaintenanceService#deleteDuplicateMembdersofSPNs(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected void deleteDuplicateMembersOfSPNs(MemberMaintenanceCriteriaVO mmCriteriaVO) throws Exception {
		providerMatchService.deleteDuplicateMembersOfSpns(mmCriteriaVO);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.network.MemberMaintenanceService#processFirm(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected void processFirm(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {

		if (mmCriteriaVO.getProviderFirmId() != null) {
			throw new IllegalProviderFirmSuppliedException(" Illegal use of specificProviderFirmId Field : Supplied values is [" + mmCriteriaVO.getProviderFirmId() + "]");
		}
		Integer spnId = mmCriteriaVO.getSpnId();
		List<MemberMaintenanceProviderFirmVO> oocAndMemberProfileFirms = providerMatchService.getOutOfCompliantAndMemberProfileFirms(spnId);
		
		// step 1

		List<MemberMaintenanceProviderFirmVO> compliantProviderFirms = providerMatchService.getCompliantFirmsForMemberMaintenance(spnId,criteriaVO);
		logger.debug(" ***** Completed getCompliantFirmsForMemberMaintenance for : " + spnId + " : " + new java.util.Date());
		
		updateCompliantProviderFirms(compliantProviderFirms);
		updateNonCompliantProviderFirms(spnId, findOutOfCompliantProviderFirms(oocAndMemberProfileFirms, compliantProviderFirms));

	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.network.MemberMaintenanceService#processServiceProviders(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	protected void processServiceProviders(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		if (mmCriteriaVO.getProviderFirmId() != null) {
			throw new IllegalProviderFirmSuppliedException(" processServiceProviders  method MUST NOT supply specificProviderFirmId Field : Supplied value is [" + mmCriteriaVO.getProviderFirmId() + "]");
		}
		complyServiceProvider(mmCriteriaVO,criteriaVO);

	}
	
	
	public void processFirmCredentials(Integer spnetId) throws Exception {

		// fetch the details of all the firm credentials with their exceptions
		List<MemberMaintenanceDetailsVO> firmCredentailDetailList = providerMatchService
				.getFirmCredentialDetails(null);
		List<MemberMaintenanceDetailsVO> firmCredentailRemovalList = new ArrayList<MemberMaintenanceDetailsVO>();
		if (null != firmCredentailDetailList
				&& firmCredentailDetailList.size() > 0) {
			MemberMaintenanceDetailsVO firmCredentail = firmCredentailDetailList
					.get(SPNBackendConstants.ZERO);
			// fetching spnId and credentialId of first element
			Integer credentialId = firmCredentail.getCredentialId();
			Integer spnId = firmCredentail.getSpnId();
			// iterate throught the credentential details
			for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
				// add the element to another list which needs to be removed
				// from orginal list
				if (credentialId.equals(firmCredential.getCredentialId())
						&& spnId.equals(firmCredential.getSpnId())) {
					firmCredentailRemovalList.add(firmCredential);
				} else {
					// mark credential status as complaint if wf_state_id is not
					// 25
					if (null != firmCredential.getWfStateId()
							&& firmCredential.getWfStateId() != SPNBackendConstants.FIRM_CREDENTIAL_OUT_OF_COMPLAINT_STATE) {
						firmCredential
								.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_APPROVED);
					} else {// apply the exception if it is applicable for the
							// out of complaint firm credential.
						if (null != firmCredential.getExceptionInd()
								&& firmCredential.getExceptionInd()
								&& null != firmCredential.getActiveInd()
								&& firmCredential.getActiveInd()) {
							// if grace period exception is satisfied,update the
							// credential status as 'spn complaint due to buyer
							// override'
							if (null != firmCredential.getExceptionTypeId()
									&& firmCredential.getExceptionTypeId() == SPNBackendConstants.GRACE_PERIOD_EXCEPTION_ID) {
								if (evaluateGracePeriod(
										firmCredential.getCredExpiryDate(),
										firmCredential.getExceptionValue())) {
									firmCredential
											.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_COMPLIANT_DUE_TO_BUYER_OVERRIDE);
								} else {
									firmCredential
											.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_OUT_OF_COMPLIANCE);
								}
							}
							// if state exception is satisfied, update the
							// status as spn complaint due to buyer override
							else if (null != firmCredential
									.getExceptionTypeId()
									&& firmCredential.getExceptionTypeId() == SPNBackendConstants.STATE_EXCEPTION_ID) {
								if (evaluteStateException(
										firmCredential.getCredState(),
										firmCredential.getExceptionValue())) {
									firmCredential
											.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_COMPLIANT_DUE_TO_BUYER_OVERRIDE);
								} else {
									firmCredential
											.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_OUT_OF_COMPLIANCE);
								}
							}
						} else {
							firmCredential
									.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_OUT_OF_COMPLIANCE);
						}
					}
				}
				credentialId = firmCredential.getCredentialId();
				spnId = firmCredential.getSpnId();
			}
			// remove the duplicate elements from orginal list-credential having
			// criteria id 13 &14
			firmCredentailDetailList.removeAll(firmCredentailRemovalList);
			// insert the details of firm credentials after the exception are
			// applied.
			providerMatchService
					.insertFirmCredentialStatus(firmCredentailDetailList);
		}

	}

	public void processFirmCredentialsData(Integer spnetId,ApprovalModel approvalModel) throws Exception {
		logger.info("processing FIRM credentials for "+spnetId);
		
		//fetch exceptions for this SPN
		List<MemberMaintenanceDetailsVO> exceptionList=providerMatchService.getFirmCredentialExceptions(spnetId);
		Map<String,List<MemberMaintenanceDetailsVO>> exceptionMap=new HashMap<String,List<MemberMaintenanceDetailsVO>>();
		if(null!=exceptionList && exceptionList.size()>0)
		{
			for (MemberMaintenanceDetailsVO exception : exceptionList) {
				if(null== exception.getCredCategoryId())
				{
					if(exceptionMap.containsKey(exception.getCredTypeId().toString()))
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  exceptionMap.get(exception.getCredTypeId().toString());
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString(), exceptionArrayList);
					}
					else
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  new ArrayList<MemberMaintenanceDetailsVO>();
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString(), exceptionArrayList);
					}
				}
				else
				{
					if(exceptionMap.containsKey(exception.getCredTypeId().toString()+"|"+exception.getCredCategoryId().toString()))
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  exceptionMap.get(exception.getCredTypeId().toString()+"|"+exception.getCredCategoryId().toString());
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString(), exceptionArrayList);
					}
					else
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  new ArrayList<MemberMaintenanceDetailsVO>();
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString()+"|"+exception.getCredCategoryId().toString(), exceptionArrayList);
					}
				}
			}	
		}
		
		// fetch Commercial Liability and Vehicle Liability Amount for all spn
		// under this firm.
		List<Integer> removalListVehicleLiability=new ArrayList<Integer>();
		List<Integer> removalListCommercialLiability=new ArrayList<Integer>();
		//SL-20802
		boolean vehicleLiabilityVerified=false;
		boolean commercialLiabilityVerified=false;

		// if the  commercial liability criteria is present in an spn
		// then get the vendor declared insurance amount and the vendor credential status of firms which are part of this spn
		List<MemberMaintenanceDetailsVO> commercialLiabilityListInitial = null;
		if(null!=approvalModel.getCommercialGeneralLiabilitySelected() && approvalModel.getCommercialGeneralLiabilitySelected())
		{
			commercialLiabilityListInitial=providerMatchService
				.getCommercialLiabilityAmount(spnetId);
		}
		//create a map to set the amount and status to liabilityVerified if exist
		Map<Integer, MemberMaintenanceDetailsVO> commercialLiabilityMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
	if(null!=commercialLiabilityListInitial && commercialLiabilityListInitial.size()>0){
		for (MemberMaintenanceDetailsVO credential : commercialLiabilityListInitial) {
			commercialLiabilityMap.put(credential.getVendorId(), credential);
		}
	}
	// if the  vehicle liability criteria is present in an spn
	// then get the vendor declared insurance amount and the vendor credential status of firms which are part of this spn
		List<MemberMaintenanceDetailsVO> vehicleLiabilityListInitial =null;
				if(null!=approvalModel.getVehicleLiabilitySelected() && approvalModel.getVehicleLiabilitySelected())
				{
					vehicleLiabilityListInitial=providerMatchService
				.getVehicleLiabilityAmount(spnetId);
				}
		Map<Integer, MemberMaintenanceDetailsVO> vehicleLiabilityMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
		if(null!=vehicleLiabilityListInitial && vehicleLiabilityListInitial.size()>0){

		for (MemberMaintenanceDetailsVO credential : vehicleLiabilityListInitial) {
			vehicleLiabilityMap.put(credential.getVendorId(), credential);
		}
		}
		//evaluate the compliance for the commercial liability criteria
		  // Conditions : ins_gen_liability_ind should be 1,ins_gen_liability_amount >= criteria amount
		  // if credential exist in vendor_credential then it should be in 'Approved' status. 
		if(null!=commercialLiabilityListInitial && commercialLiabilityListInitial.size()>0){

		for (MemberMaintenanceDetailsVO firmCredential : commercialLiabilityListInitial) {
			if(evaluateLiabilatyAmountCompliance(firmCredential.getCriteriaValue(), firmCredential.getAmount(), firmCredential.getLiabilityInd())){
				firmCredential.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);	
				if (null == approvalModel.getCommercialGeneralLiabilityVerified()
						|| !approvalModel.getCommercialGeneralLiabilityVerified()) {
					// mark credential as out of compliant if credentials exist and the status of the credentials is not 'Approved' status.
					boolean credentialCompliance = evaluateLiabilatyCredentailCompliance(firmCredential.getSlWfStateId());					
					if (!credentialCompliance) {
						firmCredential
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
					}
				}
			}
			else{
				firmCredential.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);

			}
		}
		}
		//evaluate the compliance for the vehicle liability criteria
				// Conditions : ins_vehicle_liability_ind should be 1,ins_vehicle_liability_amount >= criteria amount,
		        // if credential exist in vendor_credential then it should be in 'Approved' status. 
		if(null!=vehicleLiabilityListInitial && vehicleLiabilityListInitial.size()>0){

		for (MemberMaintenanceDetailsVO firmCredential : vehicleLiabilityListInitial) {
			if(evaluateLiabilatyAmountCompliance(firmCredential.getCriteriaValue(), firmCredential.getAmount(), firmCredential.getLiabilityInd())){
				firmCredential.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);	
				if (null == approvalModel.getVehicleLiabilityVerified()
						|| !approvalModel.getVehicleLiabilityVerified()) {
					// mark credential as out of compliant if credentials exist and the status of the credentials is not 'Approved' status.
					boolean credentialCompliance = evaluateLiabilatyCredentailCompliance(firmCredential.getSlWfStateId());	
					if (!credentialCompliance) {
						firmCredential
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
					}
				}			
			}
			else{
				firmCredential.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);

			}
		}
		} 
		
		List<MemberMaintenanceDetailsVO> liabilityRemovalList =new ArrayList<MemberMaintenanceDetailsVO>();

		// fetch the details of all the firm credentials with their exceptions
		List<MemberMaintenanceDetailsVO> firmCredentailDetailList = new ArrayList<MemberMaintenanceDetailsVO>();
		
		if(approvalModel.getSelectedVendorCredCategories().size()>0 || approvalModel.getSelectedVendorCredTypes().size()>0
				|| (approvalModel.getCommercialGeneralLiabilityVerified()!=null && approvalModel.getCommercialGeneralLiabilityVerified())
				|| (approvalModel.getVehicleLiabilityVerified()!=null && approvalModel.getVehicleLiabilityVerified())
				|| (approvalModel.getWorkersCompensationVerified()!=null && approvalModel.getWorkersCompensationVerified())
				|| approvalModel.getVehicleLiabilityAmt()!=null
				|| approvalModel.getCommercialGeneralLiabilityAmt()!=null
				|| (approvalModel.getWorkersCompensationSelected()!=null && approvalModel.getWorkersCompensationSelected())
				){
		
			CredentialsCriteriaVO credentialsCriteriaVO=new CredentialsCriteriaVO();
			
			credentialsCriteriaVO.setSelectedVendorCredCategories(approvalModel.getSelectedVendorCredCategories());
			credentialsCriteriaVO.setSelectedVendorCredTypes(approvalModel.getSelectedVendorCredTypes());
			
			if(null!=approvalModel.getCommercialGeneralLiabilityVerified() && approvalModel.getCommercialGeneralLiabilityVerified())
			{
			credentialsCriteriaVO.setCommercialGeneralLiabilityVerified(true);
			commercialLiabilityVerified=true;
			}
			
			
			
			//credentialsCriteriaVO.setCommercialGeneralLiabilityAmt(approvalModel.getCommercialGeneralLiabilityAmt());

			if(null!=approvalModel.getVehicleLiabilityVerified() && approvalModel.getVehicleLiabilityVerified())
			{
			credentialsCriteriaVO.setVehicleLiabilityVerified(true);
			vehicleLiabilityVerified=true;
			}
			
			
			
			

			if((null!=approvalModel.getWorkersCompensationVerified() && approvalModel.getWorkersCompensationVerified())
					|| (null!=approvalModel.getWorkersCompensationSelected()&& approvalModel.getWorkersCompensationSelected())){
				credentialsCriteriaVO.setWorkersCompensationVerified(true);

			}
			
			credentialsCriteriaVO.setSpnId(spnetId);
			
		firmCredentailDetailList=providerMatchService
				.getFirmCredentialDetails(credentialsCriteriaVO);
		}
		
		List<Integer> mainCredTypesforCategoryIds=new ArrayList<Integer>();
		// fetch cred Type for categories.
		if(approvalModel.getSelectedVendorCredCategories().size()>0 )
		{
			mainCredTypesforCategoryIds=providerMatchService.
				getMainCredTypesforCategoryId(approvalModel.getSelectedVendorCredCategories());
		}
		
		// to handle duplicate entries in case of   credTypes
		
		Map<Integer,List<Integer>> credMap=new HashMap<Integer,List<Integer>>();
		List<MemberMaintenanceDetailsVO> credRemovalList=new ArrayList<MemberMaintenanceDetailsVO>();
		Integer vendorId=0;
		if (null != firmCredentailDetailList
				&& firmCredentailDetailList.size() > 0) {
			
			
			for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
				
				if(null!=firmCredential.getCriteriaId() && 14==firmCredential.getCriteriaId().intValue())
				{
					vendorId=firmCredential.getVendorId();
					break;
				}
				
			}
			}
		
		
		if (null != firmCredentailDetailList
				&& firmCredentailDetailList.size() > 0) {
			
			List<Integer> credTypes=new ArrayList<Integer>();
			
			for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
			// for criteria Id 14
			if(null!=firmCredential.getCriteriaId() && 14==firmCredential.getCriteriaId().intValue())
			{
				if(firmCredential.getVendorId().equals(vendorId)){
					credTypes.add(firmCredential.getCredTypeId());
					
					}
				else
				{
				   credMap.put(vendorId, credTypes);
				   credTypes=new ArrayList<Integer>();
				   credTypes.add(firmCredential.getCredTypeId());
				}
				 vendorId=firmCredential.getVendorId();
			}
			}
			
			 credMap.put(vendorId, credTypes);
		}
		
		logger.info("credMap size for spn "+spnetId+" "+credMap.size());
		
		
		if (null != firmCredentailDetailList
				&& firmCredentailDetailList.size() > 0) {
			
			// iterate throught the credentential details
			for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
				
					// mark credential status as complaint if wf_state_id is not
					// 25
					if (null != firmCredential.getWfStateId()
							&& firmCredential.getWfStateId() != SPNBackendConstants.FIRM_CREDENTIAL_OUT_OF_COMPLAINT_STATE) {
						firmCredential
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
						if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
						{
							// to handle dupliacte in case of cred type
							
							if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(firmCredential.getCredTypeId()))
							{
							
								credRemovalList.add(firmCredential)	;
								logger.info(" removing the credential Id which is "+firmCredential.getCredentialId());	
							}
							else if(credMap.containsKey(firmCredential.getVendorId())){
								if(null!=credMap.get(firmCredential.getVendorId()) &&
										credMap.get(firmCredential.getVendorId()).contains(firmCredential.getCredTypeId())){
									credRemovalList.add(firmCredential)	;
									logger.info(" removing the credential Id"+firmCredential.getCredentialId());
								}
								
							}	
						}
						// If the vehicle liability/commercial liability/ worksman compensation criteria 'Verified' is checked(true)
						// then vendor should be compliant if the status is 'Approved', otherwise the vendor should be out of compliance.
						if(null!=firmCredential.getCriteriaId() && (SPNBackendConstants.VEHICLE_LIABILITY_AMT_CRITERIA_ID==firmCredential.getCriteriaId().intValue() ||
								SPNBackendConstants.COMMERCIAL_LIABILITY_AMT_CRITERIA_ID==firmCredential.getCriteriaId().intValue()
								|| SPNBackendConstants.WORKMAN_COMPENSATION_CRITERIA_ID==firmCredential.getCriteriaId().intValue()) 	
								&& (null != firmCredential.getWfStateId()
							&& firmCredential.getWfStateId() != SPNBackendConstants.FIRM_CREDENTIAL_APPROVED)){
							firmCredential
							.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);	
							}						
						// set status as in compliant if complind=0 and criteria=10
						if(null!=firmCredential.getCriteriaId() && 10==firmCredential.getCriteriaId().intValue()
								&& null!=firmCredential.getWorkComplInd() && !firmCredential.getWorkComplInd()){
							logger.info(" workman comp=0");
							firmCredential
							.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
							
						} 
						
                       					
						
						
					} else {// apply the exception if it is applicable for the
							// out of complaint firm credential.
						if (null != firmCredential.getExceptionInd()
								&& firmCredential.getExceptionInd()
								) {
							// for criteria Id 13
							if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
							{
								
								List<MemberMaintenanceDetailsVO> exceptionApplyList= null;
										if(exceptionMap.containsKey(firmCredential.getCredTypeId().toString()))
										{
											exceptionApplyList=exceptionMap.get(firmCredential.getCredTypeId().toString());
										}
								
								if(null!=exceptionApplyList && exceptionApplyList.size()>0){
								for (MemberMaintenanceDetailsVO exception : exceptionApplyList) {
									
									// if grace period exception is satisfied,update the
									// credential status as 'spn complaint due to buyer
									// override'
									if (null != exception.getExceptionTypeId()
											&& exception.getExceptionTypeId() == SPNBackendConstants.GRACE_PERIOD_EXCEPTION_ID) {
										if (evaluateGracePeriod(
												firmCredential.getCredExpiryDate(),
												exception.getExceptionValue())) {
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OVERRIDE);
											if(null==firmCredential.getExceptionTypeIdApplied()){
											firmCredential.setExceptionTypeIdApplied(exception.getExceptionTypeId().toString());
											}
											else
											{
												firmCredential.setExceptionTypeIdApplied(firmCredential.getExceptionTypeIdApplied()+","+
														exception.getExceptionTypeId().toString());
	
											}
										} else {
											if(!SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential
													.getFirmCredentialStatus()))
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
											
										}
									}
									// if state exception is satisfied, update the
									// status as spn complaint due to buyer override
									else if (null != exception
											.getExceptionTypeId()
											&& exception.getExceptionTypeId() == SPNBackendConstants.STATE_EXCEPTION_ID) {
										if (evaluteStateException(
												firmCredential.getCredState(),
												exception.getExceptionValue())) {
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OVERRIDE);
										} else {
											if(!SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential
													.getFirmCredentialStatus()))
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
										}
									}
								}
							}
								else
								{
									firmCredential
									.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);	
								}
								// to handle dupliacte in case of cred type
								
							 if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(firmCredential.getCredTypeId()))
								{
								
									credRemovalList.add(firmCredential)	;
									logger.info(" removing the credential Id which is "+firmCredential.getCredentialId());	
								}
									
							 else if(credMap.containsKey(firmCredential.getVendorId())){
									if(null!=credMap.get(firmCredential.getVendorId()) &&
											credMap.get(firmCredential.getVendorId()).contains(firmCredential.getCredTypeId())){
										credRemovalList.add(firmCredential)	;
										logger.info(" removing the credential Id"+firmCredential.getCredentialId());
									}
									
									
								}
							}
							
							// for criteria Id 14
							
							else if(null!=firmCredential.getCriteriaId() && 14==firmCredential.getCriteriaId().intValue())
							{
								List<MemberMaintenanceDetailsVO> exceptionApplyList= null;
										if(exceptionMap.containsKey(firmCredential.getCredTypeId().toString()+"|"+firmCredential.getCredCategoryId().toString()))
										{
											exceptionApplyList=exceptionMap.get(firmCredential.getCredTypeId().toString()+"|"+firmCredential.getCredCategoryId().toString());
										}
								
								if(null!=exceptionApplyList && exceptionApplyList.size()>0){
								for (MemberMaintenanceDetailsVO exception : exceptionApplyList) {
									
									// if grace period exception is satisfied,update the
									// credential status as 'spn complaint due to buyer
									// override'
									if (null != exception.getExceptionTypeId()
											&& exception.getExceptionTypeId() == SPNBackendConstants.GRACE_PERIOD_EXCEPTION_ID) {
										if (evaluateGracePeriod(
												firmCredential.getCredExpiryDate(),
												exception.getExceptionValue())) {
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OVERRIDE);
											if(null==firmCredential.getExceptionTypeIdApplied()){
											firmCredential.setExceptionTypeIdApplied(exception.getExceptionTypeId().toString());
											}
											else
											{
												firmCredential.setExceptionTypeIdApplied(firmCredential.getExceptionTypeIdApplied()+","+
														exception.getExceptionTypeId().toString());
	
											}
										} else {
											if(!SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential
													.getFirmCredentialStatus()))
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
											
										}
									}
									// if state exception is satisfied, update the
									// status as spn complaint due to buyer override
									else if (null != exception
											.getExceptionTypeId()
											&& exception.getExceptionTypeId() == SPNBackendConstants.STATE_EXCEPTION_ID) {
										if (evaluteStateException(
												firmCredential.getCredState(),
												exception.getExceptionValue())) {
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OVERRIDE);
											if(null==firmCredential.getExceptionTypeIdApplied()){
												firmCredential.setExceptionTypeIdApplied(exception.getExceptionTypeId().toString());
												}
												else
												{
													firmCredential.setExceptionTypeIdApplied(firmCredential.getExceptionTypeIdApplied()+","+
															exception.getExceptionTypeId().toString());
		
												}
										} else {
											if(!SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential
													.getFirmCredentialStatus()))
											firmCredential
													.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
										}
									}
								}
							}
								else
								{
									firmCredential
									.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);	
								}
								
							
							
								
							}
							else
							{
								firmCredential
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);	
							}
							
							//
							
							
							
							
							
							
						} else {
							firmCredential
									.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
							
							
							
							
							if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
							{
								// to handle dupliacte in case of cred type
								
								if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(firmCredential.getCredTypeId()))
								{
								
									credRemovalList.add(firmCredential)	;
									logger.info(" removing the credential Id which is "+firmCredential.getCredentialId());	
								}
								else if(credMap.containsKey(firmCredential.getVendorId())){
									if(null!=credMap.get(firmCredential.getVendorId()) &&
											credMap.get(firmCredential.getVendorId()).contains(firmCredential.getCredTypeId())){
										credRemovalList.add(firmCredential)	;
										logger.info(" removing the credential Id"+firmCredential.getCredentialId());
									}
									
								}	
							}
						}
							
						
					// set status as in compliant if complind=0 and criteria=10
						
						if(null!=firmCredential.getCriteriaId() && 10==firmCredential.getCriteriaId().intValue()
								&& null!=firmCredential.getWorkComplInd() && !firmCredential.getWorkComplInd()){
							logger.info(" workman comp=0");

							firmCredential
							.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
							
						}
						
							
					}
					
					//vehicle/Auto Liability
					if(null!=firmCredential.getCriteriaId() && firmCredential.getCriteriaId().intValue()==8){
						if(null!=vehicleLiabilityMap && vehicleLiabilityMap.containsKey(firmCredential.getVendorId())){
							
							if(firmCredential.getFirmCredentialStatus()
									.equals(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)){
							firmCredential.setFirmCredentialStatus(vehicleLiabilityMap.get(firmCredential.getVendorId()).getFirmCredentialStatus());
							}
							firmCredential.setAmount(Double.valueOf(vehicleLiabilityMap.get(firmCredential.getVendorId()).getCriteriaValue()));
							liabilityRemovalList.add(vehicleLiabilityMap.get(firmCredential.getVendorId()));
							removalListVehicleLiability.add(firmCredential.getVendorId());
							
						}
					}
					//commercialLiability
	      if(null!=firmCredential.getCriteriaId() && firmCredential.getCriteriaId().intValue()==11){
	    	  if(null!=commercialLiabilityMap && commercialLiabilityMap.containsKey(firmCredential.getVendorId())){
	    		  
	    		  if(firmCredential.getFirmCredentialStatus()
					.equals(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)){
					firmCredential.setFirmCredentialStatus(commercialLiabilityMap.get(firmCredential.getVendorId()).getFirmCredentialStatus());
	    		  }
					firmCredential.setAmount(Double.valueOf(commercialLiabilityMap.get(firmCredential.getVendorId()).getCriteriaValue()));
					liabilityRemovalList.add(commercialLiabilityMap.get(firmCredential.getVendorId()));
					removalListCommercialLiability.add(firmCredential.getVendorId());
				}
					}
				
				
			}
	}
		
		//  remove the duplicate credTypeId from the list.
		logger.info("firmCredentailDetailList.size() before"+firmCredentailDetailList.size());
		if(null!=credRemovalList && credRemovalList.size()>0){
		firmCredentailDetailList.removeAll(credRemovalList);
		}
		logger.info("credRemovalList.size()"+credRemovalList.size());
		logger.info("firmCredentailDetailList.size() after"+firmCredentailDetailList.size());
		
		List<MemberMaintenanceDetailsVO> vehicleLiabilityList =new ArrayList<MemberMaintenanceDetailsVO>();
		List<MemberMaintenanceDetailsVO> commercialLiabilityList =new ArrayList<MemberMaintenanceDetailsVO>();
		
		if(null!=vehicleLiabilityListInitial && vehicleLiabilityListInitial.size()>0){
		for (MemberMaintenanceDetailsVO firmCredential : vehicleLiabilityListInitial) {
			if(!removalListVehicleLiability.contains(firmCredential.getVendorId())){
				firmCredential.setAmount(Double.valueOf(firmCredential.getCriteriaValue()));
				vehicleLiabilityList.add(firmCredential);
			}
			
		}
		}
		if(null!=commercialLiabilityListInitial && commercialLiabilityListInitial.size()>0){

		for (MemberMaintenanceDetailsVO firmCredential : commercialLiabilityListInitial) {
			if(!removalListCommercialLiability.contains(firmCredential.getVendorId())){
				firmCredential.setAmount(Double.valueOf(firmCredential.getCriteriaValue()));
				commercialLiabilityList.add(firmCredential);
			}
		}
		
		}
		
		
		
		// for giving out of compliant status for the those vendors having no credentials.
		
		
		// fetch the details of all the firm credentials with their exceptions
				List<MemberMaintenanceDetailsVO> firmCredentailDetailListNoCredentials = new ArrayList<MemberMaintenanceDetailsVO>();
				
				List<MemberMaintenanceDetailsVO> credRemovalListOutOfCompliant=new ArrayList<MemberMaintenanceDetailsVO>();

				if(approvalModel.getSelectedVendorCredCategories().size()>0 || approvalModel.getSelectedVendorCredTypes().size()>0
						
						|| (approvalModel.getWorkersCompensationVerified()!=null && approvalModel.getWorkersCompensationVerified())

						|| (approvalModel.getWorkersCompensationSelected()!=null && approvalModel.getWorkersCompensationSelected())
						){
				
					CredentialsCriteriaVO credentialsCriteriaVO=new CredentialsCriteriaVO();
					
					credentialsCriteriaVO.setSelectedVendorCredCategories(approvalModel.getSelectedVendorCredCategories());
					credentialsCriteriaVO.setSelectedVendorCredTypes(approvalModel.getSelectedVendorCredTypes());
					
					if((null!=approvalModel.getWorkersCompensationVerified() && approvalModel.getWorkersCompensationVerified())
							|| (null!=approvalModel.getWorkersCompensationSelected()&& approvalModel.getWorkersCompensationSelected())){
						credentialsCriteriaVO.setWorkersCompensationVerified(true);

					}
										
					credentialsCriteriaVO.setSpnId(spnetId);
					
					providerMatchService
					.deleteFirmCredentialStatusWorkersComp(spnetId);
					
				firmCredentailDetailListNoCredentials=providerMatchService.getFirmCredentialDetailsOutofCompliant(credentialsCriteriaVO);
				
				}
			
				
				
				if(null!=firmCredentailDetailListNoCredentials && firmCredentailDetailListNoCredentials.size()>0){
				for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailListNoCredentials) {
					
					if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
						{
							// to handle dupliacte in case of cred type
							
							if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(firmCredential.getCredTypeId()))
							{
							
								credRemovalListOutOfCompliant.add(firmCredential)	;
								logger.info(" removing the credential Id which is "+firmCredential.getCredentialId());	
							}
						}
					// set status as out of compliant if complind=0 and criteria=10
					
					if(null!=firmCredential.getCriteriaId() && 10==firmCredential.getCriteriaId().intValue()
							&& null!=firmCredential.getWorkComplInd() && !firmCredential.getWorkComplInd()){
						
						logger.info(" SETTING workman comp=0");

						firmCredential
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
						
					}
						
					
					
				}
				
				if(null!=credRemovalListOutOfCompliant && credRemovalListOutOfCompliant.size()>0){
					logger.info("credRemovalListOutOfCompliant size()"+credRemovalListOutOfCompliant.size());
				firmCredentailDetailListNoCredentials.removeAll(credRemovalListOutOfCompliant);
				}
				}
				
				
				// insert the firm CredentialsOutOfCompliant
				
				//providerMatchService.insertFirmCredentialStatus(firmCredentailDetailListNoCredentials)	;
				
				
				
				logger.info("processing the out of compliant comparision for spn " + spnetId);
				List<MemberMaintenanceDetailsVO> previousFirmCredentailDetailListNoCredential =null; 
						
				// if credential table contains criterias 14,13,11,8,10
				if(previousCriteriaMap.containsKey(new Long(spnetId)))
				{
					String value=previousCriteriaMap.get(new Long(spnetId));
					if(value!=null){
					String[] criteriaTypeList=value.split(",");
					
					List<Integer> criteriaList=new ArrayList<Integer>();
								for(int count=0;count<criteriaTypeList.length;count++){
									criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
								}
										
							if(criteriaList.contains(14) || criteriaList.contains(13)
									
									|| criteriaList.contains(10) || criteriaList.contains(27))
							{
								providerMatchService
								.deleteFirmCredentialStatusWorkersComp(spnetId);
								
								previousFirmCredentailDetailListNoCredential=providerMatchService
										.getPreviousFirmCredentialDetailsOutOfCompliant(spnetId);
							}
					}
				}
				// find new and previous crential status
				// compare them and update the status
				// add additional credential if any and remove the older ones if
				// not exist

				
				List<Integer> spnFirmCredentialDeleteListNoCredential = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedfirmCredentailListNoCredential = new ArrayList<MemberMaintenanceDetailsVO>();
				
				Map<String, MemberMaintenanceDetailsVO> newFirmCredentialMapNoCredential = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousFirmCredentialMapNoCredential = new HashMap<String, MemberMaintenanceDetailsVO>();
				
				if(null!=firmCredentailDetailListNoCredentials && firmCredentailDetailListNoCredentials.size()>0){
				
				for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailListNoCredentials) {
					if(null!=firmCredential.getCredCategoryId()){
					newFirmCredentialMapNoCredential.put(firmCredential.getVendorId() + "|"
							+ firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId(), firmCredential);
					}
					else
					{
						newFirmCredentialMapNoCredential.put(firmCredential.getVendorId() + "|"
								+ firmCredential.getCredTypeId()+"|", firmCredential);	
					}
				}
				}
				
				if(null!=previousFirmCredentailDetailListNoCredential && previousFirmCredentailDetailListNoCredential.size()>0){
					
					for (MemberMaintenanceDetailsVO firmCredential : previousFirmCredentailDetailListNoCredential) {
						if(null!=firmCredential.getCredCategoryId()){
							previousFirmCredentialMapNoCredential.put(firmCredential.getVendorId() + "|"
								+ firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId(), firmCredential);
						}
						else
						{
							previousFirmCredentialMapNoCredential.put(firmCredential.getVendorId() + "|"
									+ firmCredential.getCredTypeId()+"|", firmCredential);	
						}
					}
					}
				
				if(null!=firmCredentailDetailListNoCredentials && firmCredentailDetailListNoCredentials.size()>0){

				
				for (MemberMaintenanceDetailsVO firmCredentialNew : firmCredentailDetailListNoCredentials) {

					if(null!=firmCredentialNew.getCredCategoryId()){
					if (!previousFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getVendorId() + "|"
							+ firmCredentialNew.getCredTypeId()+"|"+firmCredentialNew.getCredCategoryId())) {
						addedfirmCredentailListNoCredential.add(firmCredentialNew);
					

							}
					}
					else
					{
						if (!previousFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getVendorId() + "|"
								+ firmCredentialNew.getCredTypeId()+"|")) {
							addedfirmCredentailListNoCredential.add(firmCredentialNew);
					}
					}
						}
						
				}
				
				
				if(null!=previousFirmCredentailDetailListNoCredential && previousFirmCredentailDetailListNoCredential.size()>0){

					
					for (MemberMaintenanceDetailsVO firmCredentialNew : previousFirmCredentailDetailListNoCredential) {

						if(null!=firmCredentialNew.getCredCategoryId()){
						if (!newFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getVendorId() + "|"
								+ firmCredentialNew.getCredTypeId()+"|"+firmCredentialNew.getCredCategoryId())) {
							spnFirmCredentialDeleteListNoCredential.add(firmCredentialNew.getComplianceId());
						

								}
						}
						else
						{
							if (!newFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getVendorId() + "|"
									+ firmCredentialNew.getCredTypeId()+"|")) {
								spnFirmCredentialDeleteListNoCredential.add(firmCredentialNew.getComplianceId());
						}
						}
							}
							
					}
				
				
				
				
				if(null!=addedfirmCredentailListNoCredential && addedfirmCredentailListNoCredential.size()>0){
					logger.info("addedfirmCredentailListNoCredential.size() "+addedfirmCredentailListNoCredential.size());
					providerMatchService.insertFirmCredentialStatus(addedfirmCredentailListNoCredential);
				}
				
				if(null!=spnFirmCredentialDeleteListNoCredential && spnFirmCredentialDeleteListNoCredential.size()>0){
					logger.info("spnFirmCredentialDeleteListNoCredential.size() "+spnFirmCredentialDeleteListNoCredential.size());
					providerMatchService.deletePreviousFirmCredentialStatus(spnFirmCredentialDeleteListNoCredential);
				}
				
		/*
		
		
		// for giving out of compliant status for the those vendors having no credentials.
		
		//finding initial vendor Id
		// need to find those vendors having no Cred Type/ Cred Category specified in the list
				List<Integer> spnetFirmIds=providerMatchService.getSpnetFirms(spnetId);
				
					if(approvalModel.getSelectedVendorCredCategories().size()>0)
					{
				Integer firmId=0;
				if (null != firmCredentailDetailList
						&& firmCredentailDetailList.size() > 0) {
					
					
					for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
						
						if(null!=firmCredential.getCriteriaId() && 14==firmCredential.getCriteriaId().intValue())
						{
							firmId=firmCredential.getVendorId();
							break;
						}
						
					}
					}
		
		

				
				
		// to handle out of compliant
		
		Map<Integer,List<Integer>> credCategoryMap=new HashMap<Integer,List<Integer>>();

		
		if (null != firmCredentailDetailList
				&& firmCredentailDetailList.size() > 0) {
			
			List<Integer> credCategoryTypes=new ArrayList<Integer>();
			
			for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
			// for criteria Id 14
			if(null!=firmCredential.getCriteriaId() && 14==firmCredential.getCriteriaId().intValue())
			{
				if(firmCredential.getVendorId().equals(firmId)){
					credCategoryTypes.add(firmCredential.getCredCategoryId());
					
					}
				else
				{
					credCategoryMap.put(firmId, credCategoryTypes);
				   credCategoryTypes=new ArrayList<Integer>();
				   credCategoryTypes.add(firmCredential.getCredCategoryId());
				}
				 vendorId=firmCredential.getVendorId();
			}
			}
			
			credCategoryMap.put(firmId, credCategoryTypes);
		}
		
		
	for(Integer credCategory	: approvalModel.getSelectedVendorCredCategories())
	{
		if(spnetFirmIds!=null && spnetFirmIds.size()>0)
		{
			
			for(Integer vendorIdentification	: spnetFirmIds){
				if(!(credCategoryMap.get(vendorIdentification)!=null &&
						
						credCategoryMap.get(vendorIdentification).contains(credCategory))){
					
					
					// form an Object for vendorCredential with Out of compliant status with credCategory
					// cred type is alos needed.
					
				}
				
			}
			
		}
	}
		
		
		
					}
		//
		
					if(approvalModel.getSelectedVendorCredTypes().size()>0)
					{
		// for giving out of compliant status for the those vendors having no credentials.(Cred Type)
		
				//finding initial vendor Id
				
						Integer firmIds=0;
						if (null != firmCredentailDetailList
								&& firmCredentailDetailList.size() > 0) {
							
							
							for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
								
								if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
								{
									firmIds=firmCredential.getVendorId();
									break;
								}
								
							}
							}
				
				
				// to handle out of compliant
				
				Map<Integer,List<Integer>> credMainTypeMap=new HashMap<Integer,List<Integer>>();

				
				if (null != firmCredentailDetailList
						&& firmCredentailDetailList.size() > 0) {
					
					List<Integer> credCategoryTypes=new ArrayList<Integer>();
					
					for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
					// for criteria Id 13
					if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
					{
						if(firmCredential.getVendorId().equals(firmIds)){
							credCategoryTypes.add(firmCredential.getCredTypeId());
							
							}
						else
						{
							credMainTypeMap.put(firmIds, credCategoryTypes);
						   credCategoryTypes=new ArrayList<Integer>();
						   credCategoryTypes.add(firmCredential.getCredTypeId());
						}
						 vendorId=firmCredential.getVendorId();
					}
					}
					
					credMainTypeMap.put(firmIds, credCategoryTypes);
				}
				
				for(Integer credCategory	: approvalModel.getSelectedVendorCredTypes())
				{
					if(spnetFirmIds!=null && spnetFirmIds.size()>0)
					{
						
						for(Integer vendorIdentification	: spnetFirmIds){
							if(!(credMainTypeMap.get(vendorIdentification)!=null &&
									
									credMainTypeMap.get(vendorIdentification).contains(credCategory))){
								
								
								// form an Object for vendorCredential with Out of compliant status with credType
								//cred category might not be needed
								
							}
							
						}
						
					}
				}
				
	}
				
				
	// need to write code for WorkMan compensation also.  			
				
		*/
		
			logger.info("liabilityRemovalList.size() "+liabilityRemovalList.size());
			// need to persist commercial & vehicle Liability.
			if(null!=vehicleLiabilityList && vehicleLiabilityList.size()>0){
			vehicleLiabilityList.removeAll(liabilityRemovalList);
			}
			if(null!=commercialLiabilityList && commercialLiabilityList.size()>0){
				commercialLiabilityList.removeAll(liabilityRemovalList);
			}
			
			
			// insert the details of firm credentials after the exception are
			// applied.
			

			// need to alter the logic so that for one firm only on criteria entry should be present.
			
			// for criteria 14 create a hash Map with vendor Id + cred Type Id + category Id . // may include 11,8,10 categories also.
			
			providerMatchService.deleteFirmCredentialStatus(spnetId);
			
			Map<String,List<MemberMaintenanceDetailsVO>> credentialCategoryMap= new HashMap<String,List<MemberMaintenanceDetailsVO>>();
			
			String categoryCriteriaKey="";

			if (null != firmCredentailDetailList
					&& firmCredentailDetailList.size() > 0) {
				
			
				for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
				// for criteria Id 14 
				if(null!=firmCredential.getCriteriaId() && (14==firmCredential.getCriteriaId().intValue() || 11==firmCredential.getCriteriaId().intValue()
						|| 8==firmCredential.getCriteriaId().intValue() || 10==firmCredential.getCriteriaId().intValue()))
				{
					categoryCriteriaKey=firmCredential.getVendorId()+"|"+firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId();
					break;
				}
				
				}
			}
			
			
			
			if (null != firmCredentailDetailList
					&& firmCredentailDetailList.size() > 0) {
				
				List<MemberMaintenanceDetailsVO> credCategoryList=new ArrayList<MemberMaintenanceDetailsVO>();
				
				for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
				// for criteria Id 14
				if(null!=firmCredential.getCriteriaId() && (14==firmCredential.getCriteriaId().intValue() || 11==firmCredential.getCriteriaId().intValue()
						|| 8==firmCredential.getCriteriaId().intValue() || 10==firmCredential.getCriteriaId().intValue()))
				{
					if((firmCredential.getVendorId()+"|"+firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId()).equals(categoryCriteriaKey)){
						credCategoryList.add(firmCredential);
						
						}
					else
					{
						credentialCategoryMap.put((categoryCriteriaKey), credCategoryList);
						credCategoryList=new ArrayList<MemberMaintenanceDetailsVO>();
						credCategoryList.add(firmCredential);
					}
					categoryCriteriaKey=(firmCredential.getVendorId()+"|"+firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId());
				   }
				}
				
				credentialCategoryMap.put(categoryCriteriaKey, credCategoryList);
			}
			
			
			// iterate throught the map an create an entry for each key and add to a list. This list can be persisted in the DB.
			List<MemberMaintenanceDetailsVO> credentialCategoryFilterList=new ArrayList<MemberMaintenanceDetailsVO>();
			
				if(credentialCategoryMap.size()>0){
			
			Set<String> keys = credentialCategoryMap.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				List<MemberMaintenanceDetailsVO> keyValue= credentialCategoryMap.get(key);
				
				// criteria should be compliant if any one credential is compliant/ buyer overide
				// criteria should be buyer overide if only one credential is buyer override and 
				int buyerOverrideCount=0;
				int complaintCount=0;
				int totalSize=keyValue.size();
				for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
					if(SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential.getFirmCredentialStatus())){
						buyerOverrideCount=buyerOverrideCount+1;
				}
					if(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getFirmCredentialStatus())){
						
						complaintCount=complaintCount+1;
					}
					}

				if(complaintCount>0)
				{
					// find an entry with complaint status
					for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
						if(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getFirmCredentialStatus())){
							credentialCategoryFilterList.add(firmCredential);	
							break;
						}
					}
					
				}
				else if(buyerOverrideCount>0){
					// find an entry with buyer override status
					for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
						if(SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential.getFirmCredentialStatus())){
							credentialCategoryFilterList.add(firmCredential);
							break;
						}
					}
					
				}
				else
				{
					// find an entry with out of compliant status
					for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
						if(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE.equals(firmCredential.getFirmCredentialStatus())){
							credentialCategoryFilterList.add(firmCredential);
							break;
						}
					}

				}
				
			}
				}
		
			if(null!=credentialCategoryFilterList && credentialCategoryFilterList.size()>0)
				{
			providerMatchService
			.insertFirmCredentialStatus(credentialCategoryFilterList);
				}
			
			
			
			
			
			
			// need to alter the logic so that for one firm only on criteria entry should be present.
			
						// for criteria 13 create a hash Map with vendor Id + cred Type only
						
						Map<String,List<MemberMaintenanceDetailsVO>> credentialCredTypeMap= new HashMap<String,List<MemberMaintenanceDetailsVO>>();
						
						String credTypeCriteriaKey="";

						if (null != firmCredentailDetailList
								&& firmCredentailDetailList.size() > 0) {
							
						
							for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
							// for criteria Id 13
							if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
							{
								credTypeCriteriaKey=firmCredential.getVendorId()+"|"+firmCredential.getCredTypeId()+"|";
								break;
							}
							
							}
						}
						
						
						
						if (null != firmCredentailDetailList
								&& firmCredentailDetailList.size() > 0) {
							
							List<MemberMaintenanceDetailsVO> credCredTypeList=new ArrayList<MemberMaintenanceDetailsVO>();
							
							for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
							// for criteria Id 13
							if(null!=firmCredential.getCriteriaId() && 13==firmCredential.getCriteriaId().intValue())
							{
								if((firmCredential.getVendorId()+"|"+firmCredential.getCredTypeId()+"|").equals(credTypeCriteriaKey)){
									firmCredential.setCredCategoryId(null);
									credCredTypeList.add(firmCredential);
									
									}
								else
								{
									credentialCredTypeMap.put((credTypeCriteriaKey), credCredTypeList);
									credCredTypeList=new ArrayList<MemberMaintenanceDetailsVO>();
									firmCredential.setCredCategoryId(null);
									credCredTypeList.add(firmCredential);
								}
								credTypeCriteriaKey=(firmCredential.getVendorId()+"|"+firmCredential.getCredTypeId()+"|");
							}
							}
							
							credentialCredTypeMap.put(credTypeCriteriaKey, credCredTypeList);
						}
						
						
						// iterate throught the map an create an entry for each key and add to a list. This list can be persisted in the DB.
						List<MemberMaintenanceDetailsVO> credentialCredTypeFilterList=new ArrayList<MemberMaintenanceDetailsVO>();
						
							if(credentialCredTypeMap.size()>0){
						
						Set<String> keys = credentialCredTypeMap.keySet();
						for (Iterator i = keys.iterator(); i.hasNext();) {
							String key = (String) i.next();
							List<MemberMaintenanceDetailsVO> keyValue= credentialCredTypeMap.get(key);
							
							// criteria should be compliant if any one credential is compliant/ buyer overide
							// criteria should be buyer overide if only one credential is buyer override and 
							int buyerOverrideCount=0;
							int complaintCount=0;
							int totalSize=keyValue.size();
							for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
								if(SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential.getFirmCredentialStatus())){
									buyerOverrideCount=buyerOverrideCount+1;
							}
								if(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getFirmCredentialStatus())){
									
									complaintCount=complaintCount+1;
								}
								}

							if(complaintCount>0)
							{
								// find an entry with complaint status
								for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
									if(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getFirmCredentialStatus())){
										credentialCredTypeFilterList.add(firmCredential);	
										break;
									}
								}
								
							}
							else if(buyerOverrideCount>0){
								// find an entry with buyer override status
								for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
									if(SPNBackendConstants.PF_SPN_CRED_OVERRIDE.equals(firmCredential.getFirmCredentialStatus())){
										credentialCredTypeFilterList.add(firmCredential);
										break;
									}
								}
								
							}
							else
							{
								// find an entry with out of compliant status
								for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
									if(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE.equals(firmCredential.getFirmCredentialStatus())){
										credentialCredTypeFilterList.add(firmCredential);
										break;
									}
								}

							}
							
						}
							}
					
						if(null!=credentialCredTypeFilterList && credentialCredTypeFilterList.size()>0)
							{
						providerMatchService
						.insertFirmCredentialStatus(credentialCredTypeFilterList);
							}
			
			// need to comment  the below code as comparision logic changed.
		/*	
			
			logger.info("processing the comparision for spn " + spnetId);
				List<MemberMaintenanceDetailsVO> previousFirmCredentailDetailList =null; 
						
				// if credential table contains criterias 14,13,11,8,10
				if(previousCriteriaMap.containsKey(new Long(spnetId)))
				{
					String value=previousCriteriaMap.get(new Long(spnetId));
					if(value!=null){
					String[] criteriaTypeList=value.split(",");
					
					List<Integer> criteriaList=new ArrayList<Integer>();
								for(int count=0;count<criteriaTypeList.length;count++){
									criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
								}
										
							if(criteriaList.contains(14) || criteriaList.contains(13)
									|| criteriaList.contains(11) ||  criteriaList.contains(8)
									|| criteriaList.contains(10))
							{
								previousFirmCredentailDetailList=providerMatchService
										.getPreviousProviderCredentialDetailsOutOfCompliant(spnetId);
							}
					}
				}
				// find new and previous crential status
				// compare them and update the status
				// add additional credential if any and remove the older ones if
				// not exist

				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnComplaintDueToBuyerOverride = new ArrayList<Integer>();
				List<Integer> spnFirmCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedfirmCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newFirmCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousFirmCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				
				if(null!=firmCredentailDetailList && firmCredentailDetailList.size()>0){
				
				for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailList) {
					newFirmCredentialMap.put(firmCredential.getVendorId() + "|"
							+ firmCredential.getCredentialId(), firmCredential);
				}
				}
				
				if(null!=previousFirmCredentailDetailList && previousFirmCredentailDetailList.size()>0){
				for (MemberMaintenanceDetailsVO firmCredential : previousFirmCredentailDetailList) {
					previousFirmCredentialMap.put(firmCredential.getVendorId()
							+ "|" + firmCredential.getCredentialId(),
							firmCredential);
				}
				}
				if(null!=firmCredentailDetailList && firmCredentailDetailList.size()>0){

				
				for (MemberMaintenanceDetailsVO firmCredentialNew : firmCredentailDetailList) {

					if (previousFirmCredentialMap.containsKey(firmCredentialNew
							.getVendorId()
							+ "|"
							+ firmCredentialNew.getCredentialId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousFirmCredentialMap
								.get(firmCredentialNew.getVendorId() + "|"
										+ firmCredentialNew.getCredentialId());
						if (!firmCredentialNew.getFirmCredentialStatus()
								.equalsIgnoreCase(
										firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							} else if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_OVERRIDE)) {
								spnComplaintDueToBuyerOverride
										.add(firmCredentialPrevious
												.getComplianceId());

							}
						}
						
						
						
						
// for updating exception Applied.
						
						if (!compare(firmCredentialNew.getExceptionTypeIdApplied(),firmCredentialPrevious.getExceptionTypeIdApplied())) {
							if (firmCredentialNew
									.getExceptionTypeIdApplied() ==  null) {
								firmexceptionNull.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getExceptionTypeIdApplied()
									.contains(",") ){
								firmexceptionCombined
										.add(firmCredentialPrevious
												.getComplianceId());

							} else if (firmCredentialNew
									.getExceptionTypeIdApplied()
									.equals("1")) {
								firmexceptionGrace
										.add(firmCredentialPrevious
												.getComplianceId());

							}else if (firmCredentialNew
									.getExceptionTypeIdApplied()
									.equals("2")) {
								firmexceptionState
										.add(firmCredentialPrevious
												.getComplianceId());

							}
						}
						
							//
						
						
						
						

					} else {
						addedfirmCredentailList.add(firmCredentialNew);
					}
				}
			}
				if(null!=previousFirmCredentailDetailList && previousFirmCredentailDetailList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : previousFirmCredentailDetailList) {
					if (!newFirmCredentialMap
							.containsKey(firmCredentialPrevious.getVendorId()
									+ "|"
									+ firmCredentialPrevious.getCredentialId())) {
						spnFirmCredentialDeleteList.add(firmCredentialPrevious
								.getComplianceId());
					}
				}
				}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnComplaintDueToBuyerOverride.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnFirmCredentialDeleteList.size()"
						+ spnFirmCredentialDeleteList.size());
				logger.info("addedfirmCredentailList.size()"
						+ addedfirmCredentailList.size());

				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}
				if (spnComplaintDueToBuyerOverride.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForCompliantDuetoException(spnComplaintDueToBuyerOverride);
				}
				// delete the unwanted elements
				if (spnFirmCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousFirmCredentialStatus(spnFirmCredentialDeleteList);
				}
				// insert the new ones.
				if (addedfirmCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertFirmCredentialStatus(addedfirmCredentailList);
				}
*/
			
			
			//need to write code to fetch previous liability & compare.
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setSpnId(spnetId);
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.VEHICLE_LIABILITY_AMT_CRITERIA_ID);
			// get previous elements for vehicle liability Amount
						List<MemberMaintenanceDetailsVO> vehicleLiabilityAmPrevioustList = null;
						
						if(previousCriteriaMap.containsKey(new Long(spnetId)))
						{
							
							String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnetId)).split(",");
							
							List<Integer> criteriaList=new ArrayList<Integer>();
										for(int count=0;count<criteriaTypeList.length;count++){
											criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
										}
												
									if(criteriaList.contains(SPNBackendConstants.VEHICLE_LIABILITY_AMT_CRITERIA_ID))
									{
						vehicleLiabilityAmPrevioustList=providerMatchService
								.getPreviousFirmCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
						}
						}
						// comparing previous and new elements
						
							logger.info("processing vehicel liability criteria comparision");
							List<Integer> spnApprovedVLList = new ArrayList<Integer>();
							List<Integer> spnOutOfComplianceVLList = new ArrayList<Integer>();
							List<Integer> spnProviderCredentialVLDeleteList = new ArrayList<Integer>();
							List<MemberMaintenanceDetailsVO> addedProviderCredentailVLList = new ArrayList<MemberMaintenanceDetailsVO>();
							Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
							Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
							
							if(null!=vehicleLiabilityList && vehicleLiabilityList.size()>0){
							for (MemberMaintenanceDetailsVO credential : vehicleLiabilityList) {
								//SL-20802
								credential.setCredTypeId(SPNBackendConstants.INSURANCECREDTYPE);
								credential.setCredCategoryId(SPNBackendConstants.VEHICLE_LAIBILITY_TYPE);
								credential.setCredentialId(SPNBackendConstants.CRED_ID);
								if(vehicleLiabilityVerified){
									credential.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);	
								}
								newProviderCredentialMap.put(credential.getVendorId()
										, credential);
							}
							}
							if(null!=vehicleLiabilityAmPrevioustList && vehicleLiabilityAmPrevioustList.size()>0){

							for (MemberMaintenanceDetailsVO credential : vehicleLiabilityAmPrevioustList) {
								previousProviderCredentialMap.put(credential
										.getVendorId(), credential);
							}
							}
							
							if(null!=vehicleLiabilityList && vehicleLiabilityList.size()>0){

							for (MemberMaintenanceDetailsVO firmCredentialNew : vehicleLiabilityList) {

								if (previousProviderCredentialMap
										.containsKey(firmCredentialNew.getVendorId())) {
									MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
											.get(firmCredentialNew.getVendorId());
									if (!firmCredentialNew.getFirmCredentialStatus().equalsIgnoreCase(
											firmCredentialPrevious.getWfState())) {
										if (firmCredentialNew
												.getFirmCredentialStatus()
												.equalsIgnoreCase(
														SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)) {
											spnApprovedVLList.add(firmCredentialPrevious
													.getComplianceId());
										} else if (firmCredentialNew
												.getFirmCredentialStatus()
												.equalsIgnoreCase(
														SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE)) {
											spnOutOfComplianceVLList
													.add(firmCredentialPrevious
															.getComplianceId());

										}

									}

								} else {
									addedProviderCredentailVLList.add(firmCredentialNew);
								}
							}
							}

							if(null!=vehicleLiabilityAmPrevioustList && vehicleLiabilityAmPrevioustList.size()>0){

							for (MemberMaintenanceDetailsVO firmCredentialPrevious : vehicleLiabilityAmPrevioustList) {
								if (!newProviderCredentialMap
										.containsKey(firmCredentialPrevious.getVendorId())) {
									spnProviderCredentialVLDeleteList
											.add(firmCredentialPrevious.getComplianceId());
								}
							}
							}
							// update the status
							logger.info("spnApprovedList.size" + spnApprovedVLList.size());
							logger.info("spnOutOfComplianceList.size()"
									+ spnOutOfComplianceVLList.size());
							logger.info("spnProviderCredentialDeleteList.size()"
									+ spnProviderCredentialVLDeleteList.size());
							logger.info("addedProviderCredentailList.size()"
									+ addedProviderCredentailVLList.size());
							if (spnApprovedVLList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.updateFirmCredentialStatusForSpnApproved(spnApprovedVLList);
							}
							if (spnOutOfComplianceVLList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.updateFirmCredentialStatusForOutOfCompliance(spnOutOfComplianceVLList);
							}

							// delete the unwanted elements
							if (spnProviderCredentialVLDeleteList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.deletePreviousFirmCredentialStatus(spnProviderCredentialVLDeleteList);
							}
							// insert the new ones.
							if (addedProviderCredentailVLList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.insertFirmCredentialStatus(addedProviderCredentailVLList);
							}
						
			
						MemberMaintenanceCriteriaVO memberMaintenanceCriteria=new MemberMaintenanceCriteriaVO();
						memberMaintenanceCriteria.setSpnId(spnetId);
						memberMaintenanceCriteria.setCriteriaId(SPNBackendConstants.COMMERCIAL_LIABILITY_AMT_CRITERIA_ID);
						// get previous elements for commercial liability Amount
						List<MemberMaintenanceDetailsVO> commercialLiabilityAmPrevioustList = null;
						if(previousCriteriaMap.containsKey(new Long(spnetId)))
						{
							
							String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnetId)).split(",");
							
							List<Integer> criteriaList=new ArrayList<Integer>();
										for(int count=0;count<criteriaTypeList.length;count++){
											criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
										}
												
									if(criteriaList.contains(SPNBackendConstants.COMMERCIAL_LIABILITY_AMT_CRITERIA_ID))
									{

						
						commercialLiabilityAmPrevioustList=providerMatchService
								.getPreviousFirmCredDetailsForCriteriaForSpn(memberMaintenanceCriteria);
						}
						}
						// comparing previous and new elements
						
							logger.info("processing commercial liability amt criteria comparision");
							List<Integer> spnApprovedCLList = new ArrayList<Integer>();
							List<Integer> spnOutOfComplianceCLList = new ArrayList<Integer>();
							List<Integer> spnProviderCredentialCLDeleteList = new ArrayList<Integer>();
							List<MemberMaintenanceDetailsVO> addedProviderCLCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
							Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMaps = new HashMap<Integer, MemberMaintenanceDetailsVO>();
							Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMaps = new HashMap<Integer, MemberMaintenanceDetailsVO>();
							
							
							if(null!=commercialLiabilityList && commercialLiabilityList.size()>0){
							for (MemberMaintenanceDetailsVO credential : commercialLiabilityList) {
								//SL-20802
								credential.setCredTypeId(SPNBackendConstants.INSURANCECREDTYPE);
								credential.setCredCategoryId(SPNBackendConstants.COMMERCIAL_LAIBILITY_TYPE);
								credential.setCredentialId(SPNBackendConstants.CRED_ID);
								if(commercialLiabilityVerified){
									credential.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);	
								}
								newProviderCredentialMaps.put(credential.getVendorId()
										, credential);
							}
							}
							if(null!=commercialLiabilityAmPrevioustList && commercialLiabilityAmPrevioustList.size()>0){

							for (MemberMaintenanceDetailsVO credential : commercialLiabilityAmPrevioustList) {
								previousProviderCredentialMaps.put(credential
										.getVendorId(), credential);
							}
							}
							if(null!=commercialLiabilityList && commercialLiabilityList.size()>0){

							for (MemberMaintenanceDetailsVO firmCredentialNew : commercialLiabilityList) {

								if (previousProviderCredentialMaps
										.containsKey(firmCredentialNew.getVendorId())) {
									MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMaps
											.get(firmCredentialNew.getVendorId());
									if (!firmCredentialNew.getFirmCredentialStatus().equalsIgnoreCase(
											firmCredentialPrevious.getWfState())) {
										if (firmCredentialNew
												.getFirmCredentialStatus()
												.equalsIgnoreCase(
														SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)) {
											spnApprovedCLList.add(firmCredentialPrevious
													.getComplianceId());
										} else if (firmCredentialNew
												.getFirmCredentialStatus()
												.equalsIgnoreCase(
														SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE)) {
											spnOutOfComplianceCLList
													.add(firmCredentialPrevious
															.getComplianceId());

										}

									}

								} else {
									addedProviderCLCredentailList.add(firmCredentialNew);
								}
							}
							}
							if(null!=commercialLiabilityAmPrevioustList && commercialLiabilityAmPrevioustList.size()>0){

							for (MemberMaintenanceDetailsVO firmCredentialPrevious : commercialLiabilityAmPrevioustList) {
								if (!newProviderCredentialMaps
										.containsKey(firmCredentialPrevious.getVendorId())) {
									spnProviderCredentialCLDeleteList
											.add(firmCredentialPrevious.getComplianceId());
								}
							}
							}
							
							// update the status
							logger.info("spnApprovedList.size" + spnApprovedCLList.size());
							logger.info("spnOutOfComplianceList.size()"
									+ spnOutOfComplianceCLList.size());
							logger.info("spnProviderCredentialDeleteList.size()"
									+ spnProviderCredentialCLDeleteList.size());
							logger.info("addedProviderCredentailList.size()"
									+ addedProviderCLCredentailList.size());
							if (spnApprovedCLList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.updateFirmCredentialStatusForSpnApproved(spnApprovedCLList);
							}
							if (spnOutOfComplianceCLList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.updateFirmCredentialStatusForOutOfCompliance(spnOutOfComplianceCLList);
							}

							// delete the unwanted elements
							if (spnProviderCredentialCLDeleteList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.deletePreviousFirmCredentialStatus(spnProviderCredentialCLDeleteList);
							}
							// insert the new ones.
							if (addedProviderCLCredentailList.size() > SPNBackendConstants.ZERO) {
								providerMatchService
										.insertFirmCredentialStatus(addedProviderCLCredentailList);
							}
						
					
		

	}

	public void processProviderCredentials(Integer spnetId) throws Exception {

		// fetch the details of all the provider credentials with their
		// exceptions
		List<MemberMaintenanceDetailsVO> providerCredentailDetailList = providerMatchService
				.getProviderCredentialDetails(spnetId);
		if (null != providerCredentailDetailList
				&& providerCredentailDetailList.size() > 0) {
			List<MemberMaintenanceDetailsVO> providerCredentailRemovalList = new ArrayList<MemberMaintenanceDetailsVO>();

			MemberMaintenanceDetailsVO providerCredentailDetail = providerCredentailDetailList
					.get(SPNBackendConstants.ZERO);
			// fetching spnId and credentialId of first element
			Integer credentialId = providerCredentailDetail.getCredentialId();
			Integer spnId = providerCredentailDetail.getSpnId();
			// iterate throught the credentential details
			for (MemberMaintenanceDetailsVO providerCredential : providerCredentailDetailList) {
				// add the element to another list which needs to be removed
				// from orginal list
				if (credentialId.equals(providerCredential.getCredentialId())
						&& spnId.equals(providerCredential.getSpnId())) {
					providerCredentailRemovalList.add(providerCredential);
				} else {
					// mark credential status as complaint if wf_state_id is not
					// 24
					if (null != providerCredential.getWfStateId()
							&& providerCredential.getWfStateId() != SPNBackendConstants.PROVIDER_CREDENTIAL_OUT_OF_COMPLAINT_STATE) {
						providerCredential
								.setWfState(SPNBackendConstants.SP_SPN_APPROVED);
					} else {// apply the exception if it is applicable for the
							// out of complaint provider credential.
						if (null != providerCredential.getExceptionInd()
								&& providerCredential.getExceptionInd()
								&& null != providerCredential.getActiveInd()
								&& providerCredential.getActiveInd()) {
							// if grace period exception is satisfied,update the
							// credential status as 'spn complaint due to buyer
							// override'
							if (providerCredential.getExceptionTypeId() == SPNBackendConstants.GRACE_PERIOD_EXCEPTION_ID) {
								if (evaluateGracePeriod(
										providerCredential.getCredExpiryDate(),
										providerCredential.getExceptionValue())) {
									providerCredential
											.setWfState(SPNBackendConstants.SP_SPN_COMPLIANT_DUE_TO_BUYER_OVERRIDE);
								} else {
									providerCredential
											.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_OUT_OF_COMPLIANCE);
								}
							}
							// if state exception is satisfied, update the
							// status as spn complaint due to buyer override
							else if (providerCredential.getExceptionTypeId() == SPNBackendConstants.STATE_EXCEPTION_ID) {
								if (evaluteStateException(
										providerCredential.getCredState(),
										providerCredential.getExceptionValue())) {
									providerCredential
											.setWfState(SPNBackendConstants.SP_SPN_COMPLIANT_DUE_TO_BUYER_OVERRIDE);
								} else {
									providerCredential
											.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_OUT_OF_COMPLIANCE);
								}
							}
						} else {
							providerCredential
									.setFirmCredentialStatus(SPNBackendConstants.SP_SPN_OUT_OF_COMPLIANCE);
						}
					}
				}
				credentialId = providerCredential.getCredentialId();
				spnId = providerCredential.getSpnId();

			}
			// remove the duplicate elements from orginal list-credential having
			// criteria id 16 &17
			providerCredentailDetailList
					.removeAll(providerCredentailRemovalList);
			// insert the details of provider credentials after the exception
			// are applied.
			providerMatchService
					.insertProviderCredentialStatus(providerCredentailDetailList);
		}
	}

	public void processProviderCredentialsData(Integer spnetId,ApprovalModel approvalModel)
			throws Exception {

		logger.info("processing processProviderCredentialsData for "+spnetId);
		//fetch exceptions for this SPN
		List<MemberMaintenanceDetailsVO> exceptionList=providerMatchService.getResourceCredentialExceptions(spnetId);
		Map<String,List<MemberMaintenanceDetailsVO>> exceptionMap=new HashMap<String,List<MemberMaintenanceDetailsVO>>();
		if(null!=exceptionList && exceptionList.size()>0)
		{
			for (MemberMaintenanceDetailsVO exception : exceptionList) {
				if(null== exception.getCredCategoryId())
				{
					if(exceptionMap.containsKey(exception.getCredTypeId().toString()))
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  exceptionMap.get(exception.getCredTypeId().toString());
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString(), exceptionArrayList);
					}
					else
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  new ArrayList<MemberMaintenanceDetailsVO>();
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString(), exceptionArrayList);
					}
				}
				else
				{
					if(exceptionMap.containsKey(exception.getCredTypeId().toString()+"|"+exception.getCredCategoryId().toString()))
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  exceptionMap.get(exception.getCredTypeId().toString()+"|"+exception.getCredCategoryId().toString());
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString(), exceptionArrayList);
					}
					else
					{
						List<MemberMaintenanceDetailsVO> exceptionArrayList=  new ArrayList<MemberMaintenanceDetailsVO>();
						exceptionArrayList.add(exception);
						exceptionMap.put(exception.getCredTypeId().toString()+"|"+exception.getCredCategoryId().toString(), exceptionArrayList);
					}
				}
			}	
		}
		
		
		// fetch the details of all the provider credentials with their
		// exceptions
		List<MemberMaintenanceDetailsVO> providerCredentailDetailList =null;
		if(approvalModel.getSelectedResCredCategories().size()>0 || approvalModel.getSelectedResCredTypes().size()>0){
			
			providerCredentailDetailList = providerMatchService
				.getProviderCredentialDetails(spnetId);
			
			
		}
		
		// to handle duplicate entries for cred Type
		
		List<Integer> mainCredTypesforCategoryIds=new ArrayList<Integer>();
		// fetch cred Type for categories.
		if(approvalModel.getSelectedResCredCategories().size()>0)
		{
			mainCredTypesforCategoryIds=providerMatchService.
					getMainCredTypesforProviderCategoryId(approvalModel.getSelectedResCredCategories());
		}
		
		// to handle duplicate entries in case of   credTypes
		
		Map<Integer,List<Integer>> credMap=new HashMap<Integer,List<Integer>>();
		List<MemberMaintenanceDetailsVO> credRemovalList=new ArrayList<MemberMaintenanceDetailsVO>();
		Integer resourceId=0;
		if (null != providerCredentailDetailList
				&& providerCredentailDetailList.size() > 0) {
			
			
			for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
				
				if(null!=firmCredential.getCriteriaId() && 17==firmCredential.getCriteriaId().intValue())
				{
					resourceId=firmCredential.getResourceId();
					break;
				}
				
			}
			}
		
		
		if (null != providerCredentailDetailList
				&& providerCredentailDetailList.size() > 0) {
			
			List<Integer> credTypes=new ArrayList<Integer>();
			
			for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
			// for criteria Id 17
			if(null!=firmCredential.getCriteriaId() && 17==firmCredential.getCriteriaId().intValue())
			{
				if(firmCredential.getResourceId().equals(resourceId)){
					credTypes.add(firmCredential.getCredTypeId());
					
					}
				else
				{
				   credMap.put(resourceId, credTypes);
				   credTypes=new ArrayList<Integer>();
				   credTypes.add(firmCredential.getCredTypeId());
				}
				resourceId=firmCredential.getResourceId();
			}
			}
			
			 credMap.put(resourceId, credTypes);
		}
		
		logger.info("credMap size for spn "+spnetId+" "+credMap.size());
		
		
		
			if(null!=providerCredentailDetailList && providerCredentailDetailList.size()>0){
			// iterate throught the credentential details
			for (MemberMaintenanceDetailsVO providerCredential : providerCredentailDetailList) {
				
					// mark credential status as complaint if wf_state_id is not
					// 24
					if (null != providerCredential.getWfStateId()
							&& providerCredential.getWfStateId() != SPNBackendConstants.PROVIDER_CREDENTIAL_OUT_OF_COMPLAINT_STATE) {
						providerCredential
								.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
						
						
						if(null!=providerCredential.getCriteriaId() && 16==providerCredential.getCriteriaId().intValue())
						{
							// to handle dupliacte in case of cred type
							
							if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(providerCredential.getCredTypeId()))
							{
							
								credRemovalList.add(providerCredential)	;
								logger.info(" removing the credential Id which is "+providerCredential.getCredentialId());	
							}
							else if(credMap.containsKey(providerCredential.getResourceId())){
								if(null!=credMap.get(providerCredential.getResourceId()) &&
										credMap.get(providerCredential.getResourceId()).contains(providerCredential.getCredTypeId())){
									credRemovalList.add(providerCredential)	;
									logger.info(" removing the credential Id"+providerCredential.getCredentialId());
								}
								
							}	
						}
						
						
						
					} else {// apply the exception if it is applicable for the
							// out of complaint provider credential.
						if (null != providerCredential.getExceptionInd()
								&& providerCredential.getExceptionInd()
								) {
							
							
							// for criteria Id 16
							if(null!=providerCredential.getCriteriaId() && 16==providerCredential.getCriteriaId().intValue())
							{
								List<MemberMaintenanceDetailsVO> exceptionApplyList= null;
										if(exceptionMap.containsKey(providerCredential.getCredTypeId().toString()))
										{
											exceptionApplyList=exceptionMap.get(providerCredential.getCredTypeId().toString());
										}
								
								if(null!=exceptionApplyList && exceptionApplyList.size()>0){
								for (MemberMaintenanceDetailsVO exception : exceptionApplyList) {
									
									
									// if grace period exception is satisfied,update the
									// credential status as 'spn complaint due to buyer
									// override'
									if (exception.getExceptionTypeId() == SPNBackendConstants.GRACE_PERIOD_EXCEPTION_ID) {
										if (evaluateGracePeriod(
												providerCredential.getCredExpiryDate(),
												exception.getExceptionValue())) {
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OVERRIDE);
											if(null==providerCredential.getExceptionTypeIdApplied()){
												providerCredential.setExceptionTypeIdApplied(exception.getExceptionTypeId().toString());
												}
												else
												{
													providerCredential.setExceptionTypeIdApplied(providerCredential.getExceptionTypeIdApplied()+","+
															exception.getExceptionTypeId().toString());
		
												}
										} else {
											if(!SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(providerCredential
													.getWfState()))
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
										}
									}
									// if state exception is satisfied, update the
									// status as spn complaint due to buyer override
									else if (exception.getExceptionTypeId() == SPNBackendConstants.STATE_EXCEPTION_ID) {
										if (evaluteStateException(
												providerCredential.getCredState(),
												exception.getExceptionValue())) {
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OVERRIDE);
											if(null==providerCredential.getExceptionTypeIdApplied()){
												providerCredential.setExceptionTypeIdApplied(exception.getExceptionTypeId().toString());
												}
												else
												{
													providerCredential.setExceptionTypeIdApplied(providerCredential.getExceptionTypeIdApplied()+","+
															exception.getExceptionTypeId().toString());
		
												}
										} else {
											if(!SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(providerCredential
													.getWfState()))
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
										}
									}
								}
								}
								else{
									providerCredential
									.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
								}
								
								
								// to handle dupliacte in case of cred type
								
								if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(providerCredential.getCredTypeId()))
								{
								
									credRemovalList.add(providerCredential)	;
									logger.info(" removing the credential Id which is "+providerCredential.getCredentialId());	
								}
								else if(credMap.containsKey(providerCredential.getResourceId())){
									if(null!=credMap.get(providerCredential.getResourceId()) &&
											credMap.get(providerCredential.getResourceId()).contains(providerCredential.getCredTypeId())){
										credRemovalList.add(providerCredential)	;
										logger.info(" removing the credential Id"+providerCredential.getCredentialId());
									}
									
								}	
								
								}
							
							

							// for criteria Id 17
							if(null!=providerCredential.getCriteriaId() && 17==providerCredential.getCriteriaId().intValue())
							{
								List<MemberMaintenanceDetailsVO> exceptionApplyList= null;
										if(exceptionMap.containsKey(providerCredential.getCredTypeId().toString()+"|"+providerCredential.getCredCategoryId()))
										{
											exceptionApplyList=exceptionMap.get(providerCredential.getCredTypeId().toString()+"|"+providerCredential.getCredCategoryId());
										}
								
								if(null!=exceptionApplyList && exceptionApplyList.size()>0){
								for (MemberMaintenanceDetailsVO exception : exceptionApplyList) {
									
									
									// if grace period exception is satisfied,update the
									// credential status as 'spn complaint due to buyer
									// override'
									if (exception.getExceptionTypeId() == SPNBackendConstants.GRACE_PERIOD_EXCEPTION_ID) {
										if (evaluateGracePeriod(
												providerCredential.getCredExpiryDate(),
												exception.getExceptionValue())) {
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OVERRIDE);
											if(null==providerCredential.getExceptionTypeIdApplied()){
												providerCredential.setExceptionTypeIdApplied(exception.getExceptionTypeId().toString());
												}
												else
												{
													providerCredential.setExceptionTypeIdApplied(providerCredential.getExceptionTypeIdApplied()+","+
															exception.getExceptionTypeId().toString());
		
												}
										} else {
											if(!SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(providerCredential
													.getWfState()))
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
										}
									}
									// if state exception is satisfied, update the
									// status as spn complaint due to buyer override
									else if (exception.getExceptionTypeId() == SPNBackendConstants.STATE_EXCEPTION_ID) {
										if (evaluteStateException(
												providerCredential.getCredState(),
												exception.getExceptionValue())) {
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OVERRIDE);
										} else {
											if(!SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(providerCredential
													.getWfState()))
											providerCredential
													.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
										}
									}
								}
								}
								else {
									providerCredential
											.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
								}
								
								}
							
						
						} else {
							providerCredential
									.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
						}
					}
				

			}
	}
	
			
			
		//  remove the duplicate credTypeId from the list.

			if(null!=credRemovalList && credRemovalList.size()>0 && null!=providerCredentailDetailList && providerCredentailDetailList.size()>0){
				providerCredentailDetailList.removeAll(credRemovalList);
				logger.info("credRemovalList.size()"+credRemovalList.size());
				logger.info("providerCredentailDetailList.size() after"+providerCredentailDetailList.size());
			}
			
			
			
			
			// insert the details of provider credentials after the exception
			// are applied.
			
			
			
			
			
	// need to alter the logic so that for one firm only on criteria entry should be present.
			
			// for criteria 17 create a hash Map with vendor Id + cred Type Id + category Id . // may include 11,8,10 categories also.
			
			providerMatchService.deleteProviderCredentialStatus(spnetId);

			
			Map<String,List<MemberMaintenanceDetailsVO>> credentialCategoryMap= new HashMap<String,List<MemberMaintenanceDetailsVO>>();
			
			String categoryCriteriaKey="";

			if (null != providerCredentailDetailList
					&& providerCredentailDetailList.size() > 0) {
				
			
				for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
				// for criteria Id 17 
				if(null!=firmCredential.getCriteriaId() && (17==firmCredential.getCriteriaId().intValue()))
				{
					categoryCriteriaKey=firmCredential.getResourceId()+"|"+firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId();
					break;
				}
				
				}
			}
			
			
			
			if (null != providerCredentailDetailList
					&& providerCredentailDetailList.size() > 0) {
				
				List<MemberMaintenanceDetailsVO> credCategoryList=new ArrayList<MemberMaintenanceDetailsVO>();
				
				for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
				// for criteria Id 17
				if(null!=firmCredential.getCriteriaId() && (17==firmCredential.getCriteriaId().intValue()))
				{
					if((firmCredential.getResourceId()+"|"+firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId()).equals(categoryCriteriaKey)){
						credCategoryList.add(firmCredential);
						
						}
					else
					{
						credentialCategoryMap.put((categoryCriteriaKey), credCategoryList);
						credCategoryList=new ArrayList<MemberMaintenanceDetailsVO>();
						credCategoryList.add(firmCredential);
					}
					categoryCriteriaKey=(firmCredential.getResourceId()+"|"+firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId());
				}
				}
				
				credentialCategoryMap.put(categoryCriteriaKey, credCategoryList);
			}
			
			
			// iterate throught the map an create an entry for each key and add to a list. This list can be persisted in the DB.
			List<MemberMaintenanceDetailsVO> credentialCategoryFilterList=new ArrayList<MemberMaintenanceDetailsVO>();
			
				if(credentialCategoryMap.size()>0){
			
			Set<String> keys = credentialCategoryMap.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				List<MemberMaintenanceDetailsVO> keyValue= credentialCategoryMap.get(key);
				
				// criteria should be compliant if any one credential is compliant/ buyer overide
				// criteria should be buyer overide if only one credential is buyer override and 
				int buyerOverrideCount=0;
				int complaintCount=0;
				int totalSize=keyValue.size();
				for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
					if(SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(firmCredential.getWfState())){
						buyerOverrideCount=buyerOverrideCount+1;
				}
					if(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getWfState())){
						
						complaintCount=complaintCount+1;
					}
					}

				if(complaintCount>0)
				{
					// find an entry with complaint status
					for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
						if(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getWfState())){
							credentialCategoryFilterList.add(firmCredential);	
							break;
						}
					}
					
				}
				else if(buyerOverrideCount>0){
					// find an entry with buyer override status
					for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
						if(SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(firmCredential.getWfState())){
							credentialCategoryFilterList.add(firmCredential);
							break;
						}
					}
					
				}
				else
				{
					// find an entry with out of compliant status
					for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
						if(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE.equals(firmCredential.getWfState())){
							credentialCategoryFilterList.add(firmCredential);
							break;
						}
					}

				}
				
			}
				}
		
			if(null!=credentialCategoryFilterList && credentialCategoryFilterList.size()>0)
				{
			providerMatchService
			.insertProviderCredentialStatus(credentialCategoryFilterList);
				}
			
			
			
			
			
			
			// need to alter the logic so that for one firm only on criteria entry should be present.
			
						// for criteria 13 create a hash Map with vendor Id + cred Type only
						
						Map<String,List<MemberMaintenanceDetailsVO>> credentialCredTypeMap= new HashMap<String,List<MemberMaintenanceDetailsVO>>();
						
						String credTypeCriteriaKey="";

						if (null != providerCredentailDetailList
								&& providerCredentailDetailList.size() > 0) {
							
						
							for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
							// for criteria Id 16
							if(null!=firmCredential.getCriteriaId() && 16==firmCredential.getCriteriaId().intValue())
							{
								credTypeCriteriaKey=firmCredential.getResourceId()+"|"+firmCredential.getCredTypeId()+"|";
								break;
							}
							
							}
						}
						
						
						
						if (null != providerCredentailDetailList
								&& providerCredentailDetailList.size() > 0) {
							
							List<MemberMaintenanceDetailsVO> credCredTypeList=new ArrayList<MemberMaintenanceDetailsVO>();
							
							for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
							// for criteria Id 16
							if(null!=firmCredential.getCriteriaId() && 16==firmCredential.getCriteriaId().intValue())
							{
								if((firmCredential.getResourceId()+"|"+firmCredential.getCredTypeId()+"|").equals(credTypeCriteriaKey)){
									firmCredential.setCredCategoryId(null);
									credCredTypeList.add(firmCredential);
									
									}
								else
								{
									credentialCredTypeMap.put((credTypeCriteriaKey), credCredTypeList);
									credCredTypeList=new ArrayList<MemberMaintenanceDetailsVO>();
									firmCredential.setCredCategoryId(null);
									credCredTypeList.add(firmCredential);
								}
								credTypeCriteriaKey=(firmCredential.getResourceId()+"|"+firmCredential.getCredTypeId()+"|");
							}
							}
							
							credentialCredTypeMap.put(credTypeCriteriaKey, credCredTypeList);
						}
						
						
						// iterate throught the map an create an entry for each key and add to a list. This list can be persisted in the DB.
						List<MemberMaintenanceDetailsVO> credentialCredTypeFilterList=new ArrayList<MemberMaintenanceDetailsVO>();
						
							if(credentialCredTypeMap.size()>0){
						
						Set<String> keys = credentialCredTypeMap.keySet();
						for (Iterator i = keys.iterator(); i.hasNext();) {
							String key = (String) i.next();
							List<MemberMaintenanceDetailsVO> keyValue= credentialCredTypeMap.get(key);
							
							// criteria should be compliant if any one credential is compliant/ buyer overide
							// criteria should be buyer overide if only one credential is buyer override and 
							int buyerOverrideCount=0;
							int complaintCount=0;
							int totalSize=keyValue.size();
							for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
								if(SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(firmCredential.getWfState())){
									buyerOverrideCount=buyerOverrideCount+1;
							}
								if(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getWfState())){
									
									complaintCount=complaintCount+1;
								}
								}

							if(complaintCount>0)
							{
								// find an entry with complaint status
								for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
									if(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE.equals(firmCredential.getWfState())){
										credentialCredTypeFilterList.add(firmCredential);	
										break;
									}
								}
								
							}
							else if(buyerOverrideCount>0){
								// find an entry with buyer override status
								for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
									if(SPNBackendConstants.SP_SPN_CRED_OVERRIDE.equals(firmCredential.getWfState())){
										credentialCredTypeFilterList.add(firmCredential);
										break;
									}
								}
								
							}
							else
							{
								// find an entry with out of compliant status
								for (MemberMaintenanceDetailsVO firmCredential : keyValue) {
									if(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE.equals(firmCredential.getWfState())){
										credentialCredTypeFilterList.add(firmCredential);
										break;
									}
								}

							}
							
						}
							}
					
						if(null!=credentialCredTypeFilterList && credentialCredTypeFilterList.size()>0)
							{
						providerMatchService
						.insertProviderCredentialStatus(credentialCredTypeFilterList);
							}
			
			
			
			
			// need to comment the below logic as the comparision logic change
		
/*
				// find new and previous crential status
				// compare them and update the status
				// add additional credential if any and remove the older ones if
				// not exist

			List<MemberMaintenanceDetailsVO> previousProviderCredentailDetailList =null;
			if(previousCriteriaMap.containsKey(new Long(spnetId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnetId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(17)|| criteriaList.contains(16))
						{
			
		 previousProviderCredentailDetailList = providerMatchService.getPreviousProviderCredentialDetails(spnetId);
						}
			}
						
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnComplaintDueToBuyerOverride = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();

				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				if(null!=providerCredentailDetailList && providerCredentailDetailList.size()>0){
				for (MemberMaintenanceDetailsVO firmCredential : providerCredentailDetailList) {
					newProviderCredentialMap.put(firmCredential.getVendorId()
							+ "|" + firmCredential.getCredentialId(),
							firmCredential);
				}
				}
				if(null!=previousProviderCredentailDetailList && previousProviderCredentailDetailList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredential : previousProviderCredentailDetailList) {
					previousProviderCredentialMap.put(
							firmCredential.getVendorId() + "|"
									+ firmCredential.getCredentialId(),
							firmCredential);
				}
				}
				if(null!=providerCredentailDetailList && providerCredentailDetailList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : providerCredentailDetailList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getVendorId() + "|"
									+ firmCredentialNew.getCredentialId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getVendorId() + "|"
										+ firmCredentialNew.getCredentialId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OVERRIDE)) {
								spnComplaintDueToBuyerOverride
										.add(firmCredentialPrevious
												.getComplianceId());

							}
						}
						
						// for updating exception Applied.
						
						if (!compare(firmCredentialNew.getExceptionTypeIdApplied(),firmCredentialPrevious.getExceptionTypeIdApplied())) {
							if (firmCredentialNew
									.getExceptionTypeIdApplied() ==  null) {
								exceptionNull.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getExceptionTypeIdApplied()
									.contains(",") ){
								exceptionCombined
										.add(firmCredentialPrevious
												.getComplianceId());

							} else if (firmCredentialNew
									.getExceptionTypeIdApplied()
									.equals("1")) {
								exceptionGrace
										.add(firmCredentialPrevious
												.getComplianceId());

							}else if (firmCredentialNew
									.getExceptionTypeIdApplied()
									.equals("2")) {
								exceptionState
										.add(firmCredentialPrevious
												.getComplianceId());

							}
						}
						
							//
						
					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
	}
				
				if(null!=previousProviderCredentailDetailList && previousProviderCredentailDetailList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : previousProviderCredentailDetailList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getVendorId()
									+ "|"
									+ firmCredentialPrevious.getCredentialId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
				}
				
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnComplaintDueToBuyerOverride.size()"
						+ spnComplaintDueToBuyerOverride.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				
				
				// update the status

				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}
				if (spnComplaintDueToBuyerOverride.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForCompliantDuetoException(spnComplaintDueToBuyerOverride);
				}
				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}

				*/
				
				
				
				
				// for giving out of compliant status for the those vendors having no credentials.
				
				
				// fetch the details of all the firm credentials with their exceptions
						List<MemberMaintenanceDetailsVO> firmCredentailDetailListNoCredentials = new ArrayList<MemberMaintenanceDetailsVO>();
						
						List<MemberMaintenanceDetailsVO> credRemovalListOutOfCompliant=new ArrayList<MemberMaintenanceDetailsVO>();

						if(approvalModel.getSelectedResCredCategories().size()>0 || approvalModel.getSelectedResCredTypes().size()>0){
						
							
						firmCredentailDetailListNoCredentials=providerMatchService.getProviderCredentialDetailsOutOfCompliant(spnetId);
						
						}
					
						
						
						if(null!=firmCredentailDetailListNoCredentials && firmCredentailDetailListNoCredentials.size()>0){
						for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailListNoCredentials) {
							
							if(null!=firmCredential.getCriteriaId() && 16==firmCredential.getCriteriaId().intValue())
								{
									// to handle dupliacte in case of cred type
									
									if(null!=mainCredTypesforCategoryIds && mainCredTypesforCategoryIds.size()>0 && mainCredTypesforCategoryIds.contains(firmCredential.getCredTypeId()))
									{
									
										credRemovalListOutOfCompliant.add(firmCredential)	;
										logger.info(" removing the credential Id which is "+firmCredential.getCredentialId());	
									}
								}
						}
						
						if(null!=credRemovalListOutOfCompliant && credRemovalListOutOfCompliant.size()>0){
							logger.info("credRemovalListOutOfCompliant size()"+credRemovalListOutOfCompliant.size());
						firmCredentailDetailListNoCredentials.removeAll(credRemovalListOutOfCompliant);
						}
						}
						
						
						// insert the firm CredentialsOutOfCompliant
						
						//providerMatchService.insertFirmCredentialStatus(firmCredentailDetailListNoCredentials)	;
						
						
						
						logger.info("processing the out of compliant comparision for spn " + spnetId);
						List<MemberMaintenanceDetailsVO> previousFirmCredentailDetailListNoCredential =null; 
								
						// if credential table contains criterias 16,17
						if(previousCriteriaMap.containsKey(new Long(spnetId)))
						{
							String value=previousCriteriaMap.get(new Long(spnetId));
							if(value!=null){
							String[] criteriaTypeList=value.split(",");
							
							List<Integer> criteriaList=new ArrayList<Integer>();
										for(int count=0;count<criteriaTypeList.length;count++){
											criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
										}
												
									if(criteriaList.contains(16) || criteriaList.contains(17))
											
										
									{
										previousFirmCredentailDetailListNoCredential=providerMatchService
												.getPreviousProviderCredentialDetailsOutOfCompliant(spnetId);
									}
							}
						}
						// find new and previous crential status
						// compare them and update the status
						// add additional credential if any and remove the older ones if
						// not exist

						
						List<Integer> spnFirmCredentialDeleteListNoCredential = new ArrayList<Integer>();
						List<MemberMaintenanceDetailsVO> addedfirmCredentailListNoCredential = new ArrayList<MemberMaintenanceDetailsVO>();
						
						Map<String, MemberMaintenanceDetailsVO> newFirmCredentialMapNoCredential = new HashMap<String, MemberMaintenanceDetailsVO>();
						Map<String, MemberMaintenanceDetailsVO> previousFirmCredentialMapNoCredential = new HashMap<String, MemberMaintenanceDetailsVO>();
						
						if(null!=firmCredentailDetailListNoCredentials && firmCredentailDetailListNoCredentials.size()>0){
						
						for (MemberMaintenanceDetailsVO firmCredential : firmCredentailDetailListNoCredentials) {
							if(null!=firmCredential.getCredCategoryId()){
							newFirmCredentialMapNoCredential.put(firmCredential.getResourceId() + "|"
									+ firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId(), firmCredential);
							}
							else
							{
								newFirmCredentialMapNoCredential.put(firmCredential.getResourceId() + "|"
										+ firmCredential.getCredTypeId()+"|", firmCredential);	
							}
						}
						}
						
						if(null!=previousFirmCredentailDetailListNoCredential && previousFirmCredentailDetailListNoCredential.size()>0){
							
							for (MemberMaintenanceDetailsVO firmCredential : previousFirmCredentailDetailListNoCredential) {
								if(null!=firmCredential.getCredCategoryId()){
									previousFirmCredentialMapNoCredential.put(firmCredential.getResourceId() + "|"
										+ firmCredential.getCredTypeId()+"|"+firmCredential.getCredCategoryId(), firmCredential);
								}
								else
								{
									previousFirmCredentialMapNoCredential.put(firmCredential.getResourceId() + "|"
											+ firmCredential.getCredTypeId()+"|", firmCredential);	
								}
							}
							}
						
						if(null!=firmCredentailDetailListNoCredentials && firmCredentailDetailListNoCredentials.size()>0){

						
						for (MemberMaintenanceDetailsVO firmCredentialNew : firmCredentailDetailListNoCredentials) {

							if(null!=firmCredentialNew.getCredCategoryId()){
							if (!previousFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getResourceId() + "|"
									+ firmCredentialNew.getCredTypeId()+"|"+firmCredentialNew.getCredCategoryId())) {
								addedfirmCredentailListNoCredential.add(firmCredentialNew);
							

									}
							}
							else
							{
								if (!previousFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getResourceId() + "|"
										+ firmCredentialNew.getCredTypeId()+"|")) {
									addedfirmCredentailListNoCredential.add(firmCredentialNew);
							}
							}
								}
								
						}
						
						
						if(null!=previousFirmCredentailDetailListNoCredential && previousFirmCredentailDetailListNoCredential.size()>0){

							
							for (MemberMaintenanceDetailsVO firmCredentialNew : previousFirmCredentailDetailListNoCredential) {

								if(null!=firmCredentialNew.getCredCategoryId()){
								if (!newFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getResourceId() + "|"
										+ firmCredentialNew.getCredTypeId()+"|"+firmCredentialNew.getCredCategoryId())) {
									spnFirmCredentialDeleteListNoCredential.add(firmCredentialNew.getComplianceId());
								

										}
								}
								else
								{
									if (!newFirmCredentialMapNoCredential.containsKey(firmCredentialNew.getResourceId() + "|"
											+ firmCredentialNew.getCredTypeId()+"|")) {
										spnFirmCredentialDeleteListNoCredential.add(firmCredentialNew.getComplianceId());
								}
								}
									}
									
							}
						
						if(null!=addedfirmCredentailListNoCredential && addedfirmCredentailListNoCredential.size()>0){
							logger.info("addedfirmCredentailListNoCredential.size() "+addedfirmCredentailListNoCredential.size());
							providerMatchService.insertProviderCredentialStatus(addedfirmCredentailListNoCredential);
						}
						
						if(null!=spnFirmCredentialDeleteListNoCredential && spnFirmCredentialDeleteListNoCredential.size()>0){
							logger.info("spnFirmCredentialDeleteListNoCredential.size() "+spnFirmCredentialDeleteListNoCredential.size());
							providerMatchService.deletePreviousProviderCredentialStatus(spnFirmCredentialDeleteListNoCredential);
						}
				
				
				
				
			
		
			
	}
	public boolean compare(String str1, String str2) {
	    if (str1 == null || str2 == null)
	        return str1 == str2;

	    return str1.equals(str2);
	}



	public void processMinimumRatingComplainceData(Integer spnId,Integer rating) throws Exception {

		// fetch Minimum Rating details of all providers for every spn
		List<MemberMaintenanceDetailsVO> minimumRatingDetailsList = null;
		if(null!=rating){
			minimumRatingDetailsList=providerMatchService
				.getMinimumRatingDetails(spnId);
		}
		// firm Minimum Rating for each spn will be compliant only if all
		// providers under firm for that spn is compliant.
	
			
			if (null != minimumRatingDetailsList
					&& minimumRatingDetailsList.size() > SPNBackendConstants.ZERO) {
			for (MemberMaintenanceDetailsVO credential : minimumRatingDetailsList) {
				// if minimmum Rating compliance is satisfied,update the
					// credential status as 'spn approved' else 'out of
					// compliant'
				if (evaluateMinimumRatingComplaince(
						credential.getMinimumRating(),
						credential.getCriteriaValue())) {
					credential.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					credential
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
					
				}
			}
			}
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.MINIMUM_RATING_CRITERIA_ID);
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			List<MemberMaintenanceDetailsVO> minimumRatingPreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(SPNBackendConstants.MINIMUM_RATING_CRITERIA_ID))
						{
			// get previous elements for minimum rating criteraia for providers
			// if any
		 minimumRatingPreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
						}
			}
			// comparing previous and new elements
		
				logger.info("processing minimum rating comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				
				if(null!=minimumRatingDetailsList && minimumRatingDetailsList.size()>0){
				for (MemberMaintenanceDetailsVO credential : minimumRatingDetailsList) {
					newProviderCredentialMap.put( credential.getResourceId(), credential);
				}
				}
				if(null!=minimumRatingPreviousList && minimumRatingPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO credential : minimumRatingPreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId(), credential);
				}
				}
				if(null!=minimumRatingDetailsList && minimumRatingDetailsList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : minimumRatingDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				if(null!=minimumRatingPreviousList && minimumRatingPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : minimumRatingPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
				}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}

			
			
		}

	
	
	public void processMinimumRatingComplaince() throws Exception {

		// fetch Minimum Rating details of all providers for every spn
		List<MemberMaintenanceDetailsVO> minimumRatingDetailsList = null;
		//providerMatchService
		//		.getMinimumRatingDetails();
		// firm Minimum Rating for each spn will be compliant only if all
		// providers under firm for that spn is compliant.
		List<MemberMaintenanceDetailsVO> firmMinimumRatingDetailsList = new ArrayList<MemberMaintenanceDetailsVO>();
		if (null != minimumRatingDetailsList
				&& minimumRatingDetailsList.size() > SPNBackendConstants.ZERO) {
			// find the spn and vendor of first element in the list
			MemberMaintenanceDetailsVO vendorMinimumRating = minimumRatingDetailsList
					.get(SPNBackendConstants.ZERO);
			boolean minimumRating = false;
			Integer vendorId = vendorMinimumRating.getVendorId();
			Integer spnId = vendorMinimumRating.getSpnId();
			for (MemberMaintenanceDetailsVO credential : minimumRatingDetailsList) {
				if (!(vendorId.equals(credential.getVendorId()) && spnId
						.equals(credential.getSpnId()))) {
					// set the firm minimum rating compliance as true if the
					// minimum rating compliance of any of provider is true
					if (minimumRating) {
						vendorMinimumRating
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
					} else {
						vendorMinimumRating
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
					}
					// reset the boolean minimum Rating, vendorId and spn
					minimumRating = false;
					vendorId = credential.getVendorId();
					spnId = credential.getSpnId();
					firmMinimumRatingDetailsList.add(vendorMinimumRating);
				}// if minimmum Rating compliance is satisfied,update the
					// credential status as 'spn approved' else 'out of
					// compliant'
				if (evaluateMinimumRatingComplaince(
						credential.getMinimumRating(),
						credential.getCriteriaValue())) {
					credential.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
					minimumRating = true;
				} else {
					credential
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
					
				}
				vendorMinimumRating = credential;
			}
			// evaluate the firm minimum rating compliance of last vendor,spn
			// pattern
			if (minimumRating) {
				vendorMinimumRating
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
			} else {
				vendorMinimumRating
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
			}
			firmMinimumRatingDetailsList.add(vendorMinimumRating);

			// get previous elements for minimum rating criteraia for providers
			// if any
			List<MemberMaintenanceDetailsVO> minimumRatingPreviousList = providerMatchService
					.getPreviousProviderCredentialDetailsForCriteria(SPNBackendConstants.MINIMUM_RATING_CRITERIA_ID);
			// comparing previous and new elements
			if (null != minimumRatingPreviousList
					&& minimumRatingPreviousList.size() > 0) {
				logger.info("processing minimum rating comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : minimumRatingDetailsList) {
					newProviderCredentialMap.put(credential.getSpnId() + "|"
							+ credential.getResourceId(), credential);
				}
				for (MemberMaintenanceDetailsVO credential : minimumRatingPreviousList) {
					previousProviderCredentialMap.put(credential.getSpnId()
							+ "|" + credential.getResourceId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : minimumRatingDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getSpnId() + "|"
									+ firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getSpnId() + "|"
										+ firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : minimumRatingPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getSpnId()
									+ "|"
									+ firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}

			} else {
				// insert provider minimum rating complaince
				providerMatchService
						.insertProviderCredentialStatus(minimumRatingDetailsList);

			}
			// get previous elements for minimum rating criteria for firm if any

			List<MemberMaintenanceDetailsVO> minimumRatingFirmPreviousList = providerMatchService
					.getPreviousFirmCredentialDetailsForCriteria(SPNBackendConstants.MINIMUM_RATING_CRITERIA_ID);
			// comparing previous and new elements
			if (null != minimumRatingPreviousList
					&& minimumRatingPreviousList.size() > 0) {
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnFirmCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedFirmCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : firmMinimumRatingDetailsList) {
					newProviderCredentialMap.put(credential.getSpnId() + "|"
							+ credential.getVendorId(), credential);
				}
				for (MemberMaintenanceDetailsVO credential : minimumRatingFirmPreviousList) {
					previousProviderCredentialMap.put(credential.getSpnId()
							+ "|" + credential.getVendorId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : firmMinimumRatingDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getSpnId() + "|"
									+ firmCredentialNew.getVendorId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getSpnId() + "|"
										+ firmCredentialNew.getVendorId());
						if (!firmCredentialNew.getFirmCredentialStatus()
								.equalsIgnoreCase(
										firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedFirmCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : minimumRatingFirmPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getSpnId()
									+ "|"
									+ firmCredentialPrevious.getVendorId())) {
						spnFirmCredentialDeleteList.add(firmCredentialPrevious
								.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnFirmCredentialDeleteList.size()"
						+ spnFirmCredentialDeleteList.size());
				logger.info("addedFirmCredentailList.size()"
						+ addedFirmCredentailList.size());

				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnFirmCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousFirmCredentialStatus(spnFirmCredentialDeleteList);
				}
				// insert the new ones.
				if (addedFirmCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertFirmCredentialStatus(addedFirmCredentailList);
				}

			}

			else {
				// insert firm minimum rating complaince
				providerMatchService
						.insertFirmCredentialStatus(firmMinimumRatingDetailsList);
			}
		}

	}

	public void processCompletedSoComplaince() throws Exception {
		// fetch Completed SO details of all providers for every spn
		List<MemberMaintenanceDetailsVO> completedSoDetailsList = null;/*providerMatchService
				.getCompletedSoDetails();*/
		// firm Minimum Rating for each spn will be compliant only if all
		// providers under firm for that spn is compliant.
		List<MemberMaintenanceDetailsVO> firmcompletedSoDetailsList = new ArrayList<MemberMaintenanceDetailsVO>();
		if (null != completedSoDetailsList
				&& completedSoDetailsList.size() > SPNBackendConstants.ZERO) {
			// find the spn and vendor of first element in the list
			MemberMaintenanceDetailsVO vendorCompletedSo = completedSoDetailsList
					.get(SPNBackendConstants.ZERO);
			boolean completedSo = false;
			Integer vendorId = vendorCompletedSo.getVendorId();
			Integer spnId = vendorCompletedSo.getSpnId();
			for (MemberMaintenanceDetailsVO credential : completedSoDetailsList) {
				if (!(vendorId.equals(credential.getVendorId()) && spnId
						.equals(credential.getSpnId()))) {
					// set the firm completed SO compliance as true if the
					// completed SO compliance of any of provider is true
					if (completedSo) {
						vendorCompletedSo
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
					} else {
						vendorCompletedSo
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
					}
					// reset the boolean completedSo Rating, vendorId and spn
					completedSo = false;
					vendorId = credential.getVendorId();
					spnId = credential.getSpnId();
					firmcompletedSoDetailsList.add(vendorCompletedSo);
				}// if completed SO compliance is satisfied,update the
					// credential status as 'spn approved' else 'out of
					// compliant'
				if (evaluateCompleteSoComplaince(credential.getCompletedSo(),
						credential.getCriteriaValue())) {
					credential.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
					completedSo = true;
				} else {
					credential
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
					
				}
				vendorCompletedSo = credential;
			}
			// evaluate the firm completed SO compliance of last vendor,spn
			// pattern
			if (completedSo) {
				vendorCompletedSo
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
			} else {
				vendorCompletedSo
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
			}
			firmcompletedSoDetailsList.add(vendorCompletedSo);

			// get previous elements for completed SO criteraia for providers if
			// any
			List<MemberMaintenanceDetailsVO> completedSoPreviousList = providerMatchService
					.getPreviousProviderCredentialDetailsForCriteria(SPNBackendConstants.SO_COMPLETED_CRITERIA_ID);
			// comparing previous and new elements
			if (null != completedSoPreviousList
					&& completedSoPreviousList.size() > 0) {
				logger.info("processing completed so comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : completedSoDetailsList) {
					newProviderCredentialMap.put(credential.getSpnId() + "|"
							+ credential.getResourceId(), credential);
				}
				for (MemberMaintenanceDetailsVO credential : completedSoPreviousList) {
					previousProviderCredentialMap.put(credential.getSpnId()
							+ "|" + credential.getResourceId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : completedSoDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getSpnId() + "|"
									+ firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getSpnId() + "|"
										+ firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : completedSoPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getSpnId()
									+ "|"
									+ firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}

			} else {
				// insert provider completed SO complaince
				providerMatchService
						.insertProviderCredentialStatus(completedSoDetailsList);
			}

			// get previous elements for completed SO criteria for firm if any

			List<MemberMaintenanceDetailsVO> completesSoFirmPreviousList = providerMatchService
					.getPreviousFirmCredentialDetailsForCriteria(SPNBackendConstants.SO_COMPLETED_CRITERIA_ID);
			// comparing previous and new elements
			if (null != completesSoFirmPreviousList
					&& completesSoFirmPreviousList.size() > 0) {
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnFirmCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedFirmCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : firmcompletedSoDetailsList) {
					newProviderCredentialMap.put(credential.getSpnId() + "|"
							+ credential.getVendorId(), credential);
				}
				for (MemberMaintenanceDetailsVO credential : completesSoFirmPreviousList) {
					previousProviderCredentialMap.put(credential.getSpnId()
							+ "|" + credential.getVendorId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : firmcompletedSoDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getSpnId() + "|"
									+ firmCredentialNew.getVendorId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getSpnId() + "|"
										+ firmCredentialNew.getVendorId());
						if (!firmCredentialNew.getFirmCredentialStatus()
								.equalsIgnoreCase(
										firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedFirmCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : completesSoFirmPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getSpnId()
									+ "|"
									+ firmCredentialPrevious.getVendorId())) {
						spnFirmCredentialDeleteList.add(firmCredentialPrevious
								.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnFirmCredentialDeleteList.size()"
						+ spnFirmCredentialDeleteList.size());
				logger.info("addedFirmCredentailList.size()"
						+ addedFirmCredentailList.size());

				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnFirmCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousFirmCredentialStatus(spnFirmCredentialDeleteList);
				}
				// insert the new ones.
				if (addedFirmCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertFirmCredentialStatus(addedFirmCredentailList);
				}

			} else {
				// insert firm completed SO complaince
				providerMatchService
						.insertFirmCredentialStatus(firmcompletedSoDetailsList);
			}
		}

	}
	
	
	
	public void processCompletedSoComplainceData(Integer spnId,String completedSo) throws Exception {
		// fetch Completed SO details of all providers for every spn
		List<MemberMaintenanceDetailsVO> completedSoDetailsList = null;
		if(null!=completedSo)
		{
			completedSoDetailsList=providerMatchService
				.getCompletedSoDetails(spnId);
		}
		// firm Minimum Rating for each spn will be compliant only if all
		// providers under firm for that spn is compliant.
	
			// find the spn and vendor of first element in the list
			
			if(null!=completedSoDetailsList && completedSoDetailsList.size()>0){
			for (MemberMaintenanceDetailsVO credential : completedSoDetailsList) {
				// if completed SO compliance is satisfied,update the
					// credential status as 'spn approved' else 'out of
					// compliant'
				if (evaluateCompleteSoComplaince(credential.getCompletedSo(),
						credential.getCriteriaValue())) {
					credential.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					credential
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
					
				}
			}
			
			}
		
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.SO_COMPLETED_CRITERIA_ID);
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			List<MemberMaintenanceDetailsVO> completedSoPreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(SPNBackendConstants.SO_COMPLETED_CRITERIA_ID))
						{
			// get previous elements for completed SO criteraia for providers if
			// any
			completedSoPreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
						}
			}
			// comparing previous and new elements
			
				logger.info("processing completed so comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				if(null!=completedSoDetailsList && completedSoDetailsList.size()>0){
				for (MemberMaintenanceDetailsVO credential : completedSoDetailsList) {
					newProviderCredentialMap.put(credential.getResourceId(), credential);
				}
				}
				if(null!=completedSoPreviousList && completedSoPreviousList.size()>0){
				for (MemberMaintenanceDetailsVO credential : completedSoPreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId(), credential);
				}
				}
				if(null!=completedSoDetailsList && completedSoDetailsList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : completedSoDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				if(null!=completedSoPreviousList && completedSoPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : completedSoPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
			}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
		
		

	}

	public void processLanguageComplaince() throws Exception {
		// fetch Language details of all providers for every spn
		List<MemberMaintenanceDetailsVO> languageDetailsList = providerMatchService
				.getResourceLanguages(null);
		// firm Language for each spn will be compliant only if all providers
		// under firm for that spn is compliant.
		List<MemberMaintenanceDetailsVO> firmLanguageDetailsList = new ArrayList<MemberMaintenanceDetailsVO>();
		if (null != languageDetailsList
				&& languageDetailsList.size() > SPNBackendConstants.ZERO) {
			// find the spn and vendor of first element in the list
			MemberMaintenanceDetailsVO languageDetail = languageDetailsList
					.get(SPNBackendConstants.ZERO);
			boolean languageCompliance = false;
			Integer vendorId = languageDetail.getVendorId();
			Integer spnId = languageDetail.getSpnId();
			for (MemberMaintenanceDetailsVO credential : languageDetailsList) {
				if (!(vendorId.equals(credential.getVendorId()) && spnId
						.equals(credential.getSpnId()))) {
					// set the firm language Compliancee as true if the
					// language Compliance of any of provider is true
					if (languageCompliance) {
						languageDetail
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
					} else {
						languageDetail
								.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
					}
					// reset the boolean languageCompliance, vendorId and spn
					languageCompliance = false;
					vendorId = credential.getVendorId();
					spnId = credential.getSpnId();
					firmLanguageDetailsList.add(languageDetail);
				}// if language Compliance is satisfied,update the credential
					// status as 'spn approved' else 'out of compliant'
				if (evaluateLanguageComplaince(credential.getLanguageList(),
						credential.getCriteriaValue())) {
					credential.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
					languageCompliance = true;
				} else {
					credential
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
				
				}
				languageDetail = credential;
			}
			// evaluate the language Compliance of last vendor,spn pattern
			if (languageCompliance) {
				languageDetail
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE);
			} else {
				languageDetail
						.setFirmCredentialStatus(SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE);
			}
			firmLanguageDetailsList.add(languageDetail);

			// get previous elements for language criteraia for providers if any
			List<MemberMaintenanceDetailsVO> languagePreviousList = providerMatchService
					.getPreviousProviderCredentialDetailsForCriteria(SPNBackendConstants.LANGUAGE_COMPLIANCE_CRITERIA_ID);
			// comparing previous and new elements
			if (null != languagePreviousList && languagePreviousList.size() > 0) {
				logger.info("processing language criteria comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : languageDetailsList) {
					newProviderCredentialMap.put(credential.getSpnId() + "|"
							+ credential.getResourceId(), credential);
				}
				for (MemberMaintenanceDetailsVO credential : languagePreviousList) {
					previousProviderCredentialMap.put(credential.getSpnId()
							+ "|" + credential.getResourceId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : languageDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getSpnId() + "|"
									+ firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getSpnId() + "|"
										+ firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : languagePreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getSpnId()
									+ "|"
									+ firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
			} else {
				// insert provider language Compliance
				providerMatchService
						.insertProviderCredentialStatus(languageDetailsList);
			}

			// get previous elements for language criteria for firm if any

			List<MemberMaintenanceDetailsVO> languageFirmPreviousList = providerMatchService
					.getPreviousFirmCredentialDetailsForCriteria(SPNBackendConstants.LANGUAGE_COMPLIANCE_CRITERIA_ID);
			// comparing previous and new elements
			if (null != languageFirmPreviousList
					&& languageFirmPreviousList.size() > 0) {
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnFirmCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedFirmCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				Map<String, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<String, MemberMaintenanceDetailsVO>();
				for (MemberMaintenanceDetailsVO credential : firmLanguageDetailsList) {
					newProviderCredentialMap.put(credential.getSpnId() + "|"
							+ credential.getVendorId(), credential);
				}
				for (MemberMaintenanceDetailsVO credential : languageFirmPreviousList) {
					previousProviderCredentialMap.put(credential.getSpnId()
							+ "|" + credential.getVendorId(), credential);
				}

				for (MemberMaintenanceDetailsVO firmCredentialNew : firmLanguageDetailsList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getSpnId() + "|"
									+ firmCredentialNew.getVendorId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getSpnId() + "|"
										+ firmCredentialNew.getVendorId());
						if (!firmCredentialNew.getFirmCredentialStatus()
								.equalsIgnoreCase(
										firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getFirmCredentialStatus()
									.equalsIgnoreCase(
											SPNBackendConstants.PF_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedFirmCredentailList.add(firmCredentialNew);
					}
				}

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : languageFirmPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getSpnId()
									+ "|"
									+ firmCredentialPrevious.getVendorId())) {
						spnFirmCredentialDeleteList.add(firmCredentialPrevious
								.getComplianceId());
					}
				}

				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnFirmCredentialDeleteList.size()"
						+ spnFirmCredentialDeleteList.size());
				logger.info("addedFirmCredentailList.size()"
						+ addedFirmCredentailList.size());

				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateFirmCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnFirmCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousFirmCredentialStatus(spnFirmCredentialDeleteList);
				}
				// insert the new ones.
				if (addedFirmCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertFirmCredentialStatus(addedFirmCredentailList);
				}

			} else {
				// insert firm language Compliance
				providerMatchService
						.insertFirmCredentialStatus(firmLanguageDetailsList);
			}
		}
	}

	
	
	public void processBackgroundCheckComplainceData(Integer spnId) throws Exception {
		// fetch Background Check  details of all providers for every spn
		List<MemberMaintenanceDetailsVO> backgroundInformationList = null;
		
			backgroundInformationList=providerMatchService.getProviderBackgroundInformation(spnId);		
            // get background check grace period
			Integer gracePeriod=providerMatchService.getBackgroundCheckGracePeriod();
			if(null!=backgroundInformationList && backgroundInformationList.size()>0){
			for (MemberMaintenanceDetailsVO credential : backgroundInformationList) {
				// 
				credential.setCriteriaValueName("Background check - 2 year recertification");
				if (evaluateBackgroundCheckComplaince(credential,gracePeriod)) {
					credential.setWfState(SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE);
				} else {
					credential
							.setWfState(SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE);
					
				}
			}
			
			}
		
			MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO=new MemberMaintenanceCriteriaVO();
			memberMaintenanceCriteriaVO.setCriteriaId(SPNBackendConstants.BACKGROUND_CHECK_CRITERIA_ID);
			memberMaintenanceCriteriaVO.setSpnId(spnId);
			List<MemberMaintenanceDetailsVO> backgroundCheckPreviousList =null;
			if(previousCriteriaMap.containsKey(new Long(spnId)))
			{
				
				String[] criteriaTypeList=previousCriteriaMap.get(new Long(spnId)).split(",");
				
				List<Integer> criteriaList=new ArrayList<Integer>();
							for(int count=0;count<criteriaTypeList.length;count++){
								criteriaList.add(Integer.parseInt(criteriaTypeList[count]));
							}
									
						if(criteriaList.contains(SPNBackendConstants.BACKGROUND_CHECK_CRITERIA_ID))
						{
			// get previous elements for backgroundCheck criteraia for providers if
			// 
							backgroundCheckPreviousList = providerMatchService
					.getPreviousProviderCredDetailsForCriteriaForSpn(memberMaintenanceCriteriaVO);
						}
			}
			// comparing previous and new elements
			
				logger.info("processing completed so comparision");
				List<Integer> spnApprovedList = new ArrayList<Integer>();
				List<Integer> spnOutOfComplianceList = new ArrayList<Integer>();
				List<Integer> spnProviderCredentialDeleteList = new ArrayList<Integer>();
				List<MemberMaintenanceDetailsVO> addedProviderCredentailList = new ArrayList<MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> newProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				Map<Integer, MemberMaintenanceDetailsVO> previousProviderCredentialMap = new HashMap<Integer, MemberMaintenanceDetailsVO>();
				if(null!=backgroundInformationList && backgroundInformationList.size()>0){
				for (MemberMaintenanceDetailsVO credential : backgroundInformationList) {
					newProviderCredentialMap.put(credential.getResourceId(), credential);
				}
				}
				if(null!=backgroundCheckPreviousList && backgroundCheckPreviousList.size()>0){
				for (MemberMaintenanceDetailsVO credential : backgroundCheckPreviousList) {
					previousProviderCredentialMap.put(credential.getResourceId(), credential);
				}
				}
				if(null!=backgroundInformationList && backgroundInformationList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialNew : backgroundInformationList) {

					if (previousProviderCredentialMap
							.containsKey(firmCredentialNew.getResourceId())) {
						MemberMaintenanceDetailsVO firmCredentialPrevious = previousProviderCredentialMap
								.get(firmCredentialNew.getResourceId());
						if (!firmCredentialNew.getWfState().equalsIgnoreCase(
								firmCredentialPrevious.getWfState())) {
							if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_INCOMPLIANCE)) {
								spnApprovedList.add(firmCredentialPrevious
										.getComplianceId());
							} else if (firmCredentialNew
									.getWfState()
									.equalsIgnoreCase(
											SPNBackendConstants.SP_SPN_CRED_OUTOFCOMPLIANCE)) {
								spnOutOfComplianceList
										.add(firmCredentialPrevious
												.getComplianceId());

							}

						}

					} else {
						addedProviderCredentailList.add(firmCredentialNew);
					}
				}
				}
				if(null!=backgroundCheckPreviousList && backgroundCheckPreviousList.size()>0){

				for (MemberMaintenanceDetailsVO firmCredentialPrevious : backgroundCheckPreviousList) {
					if (!newProviderCredentialMap
							.containsKey(firmCredentialPrevious.getResourceId())) {
						spnProviderCredentialDeleteList
								.add(firmCredentialPrevious.getComplianceId());
					}
				}
			}
				// update the status
				logger.info("spnApprovedList.size" + spnApprovedList.size());
				logger.info("spnOutOfComplianceList.size()"
						+ spnOutOfComplianceList.size());
				logger.info("spnProviderCredentialDeleteList.size()"
						+ spnProviderCredentialDeleteList.size());
				logger.info("addedProviderCredentailList.size()"
						+ addedProviderCredentailList.size());
				if (spnApprovedList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForSpnApproved(spnApprovedList);
				}
				if (spnOutOfComplianceList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.updateProviderCredentialStatusForOutOfCompliance(spnOutOfComplianceList);
				}

				// delete the unwanted elements
				if (spnProviderCredentialDeleteList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.deletePreviousProviderCredentialStatus(spnProviderCredentialDeleteList);
				}
				// insert the new ones.
				if (addedProviderCredentailList.size() > SPNBackendConstants.ZERO) {
					providerMatchService
							.insertProviderCredentialStatus(addedProviderCredentailList);
				}
		
		

	}

	
	/**
	 * This method apply the state exceptions for credential state and returns
	 * true if it is satisfied.
	 */
	public boolean evaluteStateException(String credentialState,
			String exceptionValue) {
		logger.info("evaluating state exception for "+exceptionValue);
		if (null == credentialState && null != exceptionValue)
		{
			return true;	
		}
		if (null != credentialState && null != exceptionValue) {
			String stateExceptions[] = exceptionValue.split(",");

			for (String state : stateExceptions) {
				if (state.equals(credentialState)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method apply the grace period for the credential date and returns
	 * true if it is satisfied.
	 */
	public boolean evaluateGracePeriod(Date credentialExpirationDate,
			String exceptionValue) {
logger.info("evaluating grace exception "+exceptionValue);
logger.info("credentialExpirationDate "+credentialExpirationDate);
logger.info("new Date() "+new Date());
if(null!=exceptionValue){
		Date currentDate = new Date();
		DateUtils utils = new DateUtils();
		long days = 0L;
		int exceptionDays = Integer.parseInt(exceptionValue);
		if (null != credentialExpirationDate) {
			days = utils.getExactDaysBetweenDates(credentialExpirationDate,
					currentDate);
			System.out.println("days"+days);
			if (days >= 0 && days < exceptionDays) {
				return true;
			} else {
				return false;
			}
		}
}
		return false;
	}

	/**
	 * This method evaluate MinimumRating Complaince and returns true if it is
	 * satisfied.
	 */
	public boolean evaluateMinimumRatingComplaince(BigDecimal minimumRating,
			String criteriaValue) {
		if (null != minimumRating && null != criteriaValue) {
			BigDecimal ratingValue = new BigDecimal(criteriaValue);
			if (minimumRating.compareTo(ratingValue) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method evaluate CompleteSo Complaince and returns true if it is
	 * satisfied.
	 */
	public boolean evaluateCompleteSoComplaince(Integer completedSo,
			String criteriaValue) {
		if (null != completedSo && null != criteriaValue) {
			Integer completedSoCount = Integer.parseInt(criteriaValue);
			if (completedSoCount.compareTo(completedSoCount) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method evaluate Language Complaince and returns true if it is
	 * satisfied.
	 */
	public boolean evaluateLanguageComplaince(String languageList,
			String criteriaValueList) {
		if(null!=criteriaValueList && null!=languageList)
		{
		String[] criteriaValuesList=criteriaValueList.split("@");
		String[] languagesList=languageList.split("@");
		
		for (String criteriaValue : criteriaValuesList) {
			if(!Arrays.asList(languagesList).contains(criteriaValue))
			{
				return false;
			}
		}
		return true;
		}
		return false;
	}
	
	/**
	 * SL-19667 This method evaluate Provider Background Check Complaince and returns true if it is
	 * satisfied.
	 */
	public boolean evaluateBackgroundCheckComplaince(MemberMaintenanceDetailsVO credential,Integer gracePeriod) {
		boolean compliance=false;
		if(null!=credential){
			
			// CR SL-20289 if resource state is Not market Ready , mark as OOC.
			
		 if ((null!=credential.getSlWfStateId()) && (SPNBackendConstants.SL_MARKET_READY_WF_STATE_ID.equalsIgnoreCase(credential.getSlWfStateId()))) {
			
				// if status is Clear
				   if (SPNBackendConstants.BACKGROUND_STATE_CLEAR.equalsIgnoreCase(credential.getBackGroundState())) {
					   // if status Expired
					   if (SPNBackendConstants.BACKGROUND_CHECK_EXPIRED.equalsIgnoreCase(credential.getOverall())) {
						// check recertification date and current date
						   compliance=false;
					   }
					   else if (SPNBackendConstants.BACKGROUND_CHECK_RECERTIFICATION_APPLIED.equalsIgnoreCase(credential.getBackgroundRequestType())
							   &&  SPNBackendConstants.OVERALL_INPROCESS.equalsIgnoreCase(credential.getOverall()) && null!=credential.getRecertificationDate()
							   && null!=gracePeriod && ("Y").equals(credential.getRecertBeforeExpiry()) ) {
						   logger.info("applying grace period");
						    // give grace period before marking the provider as out of complaint
							java.util.Date currentDate = new java.util.Date();// todays date
							currentDate.setHours(0);
							currentDate.setMinutes(0);
							currentDate.setSeconds(0);
							
							Date recertificationDate=credential.getRecertificationDate();
							Calendar calendar = Calendar.getInstance(); 
							calendar.setTime(recertificationDate); 
							calendar.add(calendar.DATE, gracePeriod.intValue());
							recertificationDate=calendar.getTime();
							// get days between recertification date and current date
							double days = getDaysBetweenDates(currentDate,recertificationDate).floatValue();		
							int day=(int) days;
							//if difference greater than zero, mark as compliant.
						   if(days<0){
							   compliance=false;
						   }
						   else{
							   compliance=true;

						   }				    
					   }
					   else if (null!=credential.getRecertificationDate()) {
						    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
						    try {
						    	java.util.Date currentDate = new java.util.Date();	// todays date

							    Date dateToday=sdf.parse(sdf.format(currentDate));
								if(!dateToday.after(credential.getRecertificationDate())){
									compliance=true;
							    }
							} catch (Exception e) {	
							compliance=true;
							logger.error("Exception in parsisng date"+	e.getMessage(),e);
							}    
			           }
					    else {
					    	compliance=true;
					    }
				    }
				   else
				   {
					      // CR SL-20289 'Not started' , 'Pending submission','In Process',will be marked as COMPLAINT	
				    	  if (!SPNBackendConstants.BACKGROUND_STATE_ADVERSE_FINDINGS.equalsIgnoreCase(
						  credential.getBackGroundState())) {
				    		  compliance=true;
						    } 
						    
				   }
				   
		 }   
	     else {	
	    	 
	    	  compliance=false;
	    	  
		    }
		 
		}
		
		return compliance;
	}
    // evaluate liability amount criteria  conditions- liability_ind should be 1, liability_amount >= criteria value
	public boolean evaluateLiabilatyAmountCompliance(String criteriaValue,
			Double amount, Boolean liabilityInd) {
		if (null != criteriaValue) {
			if (Double.valueOf(criteriaValue).doubleValue() <= 0.00) {
				return true;
			}
			if (null != amount) {
				if (liabilityInd != null
						&& liabilityInd.booleanValue()
						&& amount.compareTo(Double.valueOf(criteriaValue)) != -1) {
					return true;
				}
			}
		}
		return false;
	}
	
   // evaluate liability amount criteria conditions- if the credential exist then it should be in 'Approved' status.
	public boolean evaluateLiabilatyCredentailCompliance(String credentialStateId)
	{
		boolean credentialCompliance=false;
		if (null == credentialStateId) {
			credentialCompliance= true;
		} else if (credentialStateId.equals(SPNBackendConstants.FIRM_CRED_APPROVED)) {
					credentialCompliance = true;
		}	
		return credentialCompliance;	
	}

	
	public void runSpn1() {
		
		Integer spnId = 0;
		boolean test = true;

		try {
			previousCriteriaMap=providerMatchService.getPreviousCriteriaOfAllSpn();
			ApprovalModel approvalModel = providerMatchService
					.getApprovalModelForSPN(spnId);
			
			
			processFirmCredentialsData(spnId,approvalModel);

			processProviderCredentialsData(spnId,approvalModel);

			processMainServicesData(spnId,
					approvalModel.getSelectedMainServices());

			processSkillsData(spnId, approvalModel.getSelectedSkills());
			processCategoriesData(spnId, approvalModel.getSelectedSubServices1(),SPNBackendConstants.CATEGORY_CRITERIA_ID);
			processCategoriesData(spnId, approvalModel.getSelectedSubServices2(),SPNBackendConstants.SUB_CATEGORY_CRITERIA_ID);

			processLanguageData(spnId, approvalModel.getSelectedLanguages());
			processMinimumRatingComplainceData(spnId,approvalModel.getSelectedMinimumRating());
			processCompletedSoComplainceData(spnId,approvalModel.getMinimumCompletedServiceOrders());
			
		} catch (IllegalProviderFirmSuppliedException pe) {
			logger.error(
					"This is SERIOUS CODE Issue, provider Firm is NOT Supposed to be Supplied for NightlyMemberMaintenance Job."
							+ pe.getMessage(), pe);
		} catch (Exception e) {
			logger.warn("Couldn't complete updating of spnid[" + spnId + "]", e);
		}
	}

	public void runSpn() {
		Integer spnId = 0;
		boolean test = true;

		try {
			handleSpn(getMMCriteriaVO(spnId, null));
		} catch (IllegalProviderFirmSuppliedException pe) {
			logger.error(
					"This is SERIOUS CODE Issue, provider Firm is NOT Supposed to be Supplied for NightlyMemberMaintenance Job."
							+ pe.getMessage(), pe);
		} catch (Exception e) {
			logger.warn("Couldn't complete updating of spnid[" + spnId + "]", e);
		}
	}
	
	public void updateVendorActivityRegistryForBackgroundCheck(){
		try{
		providerMatchService.updateVendorActivityRegistryForBackgroundCheck();
		}
		catch(Exception e){
			logger.info("error in updating vendor activity registry"+e);
		}
	}
	
	 public  Double getDaysBetweenDates(java.util.Date startDate, java.util.Date endDate) {
			return ((endDate.getTime() - startDate.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
		}
		

}
