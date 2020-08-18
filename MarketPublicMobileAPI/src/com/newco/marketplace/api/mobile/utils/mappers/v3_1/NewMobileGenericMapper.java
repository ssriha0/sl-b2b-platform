package com.newco.marketplace.api.mobile.utils.mappers.v3_1;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reportAProblem.ReportProblemVO;
import com.newco.marketplace.api.beans.so.updateTimeWindow.UpdateTimeWindowVO;
import com.newco.marketplace.api.beans.so.viewDashboard.DashBoardCountVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.companyProfile.BusinessAddress;
import com.newco.marketplace.api.mobile.beans.companyProfile.BusinessInformation;
import com.newco.marketplace.api.mobile.beans.companyProfile.CompanyOverview;
import com.newco.marketplace.api.mobile.beans.companyProfile.CompleteProfile;
import com.newco.marketplace.api.mobile.beans.companyProfile.Credential;
import com.newco.marketplace.api.mobile.beans.companyProfile.Credentials;
import com.newco.marketplace.api.mobile.beans.companyProfile.InsurancePoliciesOnFile;
import com.newco.marketplace.api.mobile.beans.companyProfile.LicenseAndCertificationsFile;
import com.newco.marketplace.api.mobile.beans.companyProfile.PolicyDetail;
import com.newco.marketplace.api.mobile.beans.companyProfile.PolicyDetails;
import com.newco.marketplace.api.mobile.beans.companyProfile.PrimaryContactInformation;
import com.newco.marketplace.api.mobile.beans.companyProfile.PublicProfile;
import com.newco.marketplace.api.mobile.beans.companyProfile.ViewCompanyProfileResponse;
import com.newco.marketplace.api.mobile.beans.companyProfile.WarrantyInformation;
import com.newco.marketplace.api.mobile.beans.companyProfile.WorkPolicyInformation;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.TeamMemberDetail;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.TeamMemberDetails;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.ViewTeamMembersResponse;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSORequest;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSOResponse;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemRequest;
import com.newco.marketplace.api.mobile.beans.resolveProblem.ResolveProblemOnSORequest;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsRequest;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdatetimeWindowRequest;
import com.newco.marketplace.api.mobile.beans.viewDashboard.LeadDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.LeadDetails;
import com.newco.marketplace.api.mobile.beans.viewDashboard.LeadOrderStatistics;
import com.newco.marketplace.api.mobile.beans.viewDashboard.PerformanceStatistics;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderBackgroundCheck;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderBackgroundDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderBackgroundDetails;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderRegistrationDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderRegistrationDetails;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ProviderRegistrationStatus;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ServiceLiveStatusMonitor;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnBuyerDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnBuyerDetailsList;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnDetail;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnDetails;
import com.newco.marketplace.api.mobile.beans.viewDashboard.SpnMonitor;
import com.newco.marketplace.api.mobile.beans.viewDashboard.Tab;
import com.newco.marketplace.api.mobile.beans.viewDashboard.Tabs;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewAdvancedDashboardResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.TimestampUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.TeamMemberDocumentVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * Request/Response Mapper class for Mobile phase v3.1 API's
 *
 */
public class NewMobileGenericMapper extends MobileGenericMapper{

	private static final Logger LOGGER = Logger.getLogger(NewMobileGenericMapper.class);

	private INewMobileGenericBO newMobileGenericBO;


	public ReportProblemVO mapReportProblemRequest(ReportProblemRequest request, String soId, Integer resourceId, Integer roleId) {
		ReportProblemVO problemVO = null;
		if(null != request){
			problemVO = new ReportProblemVO();
			problemVO.setSoId(soId);
			problemVO.setResourceId(resourceId);
			problemVO.setProblemDescriptionComments(request.getProblemDescription());
			problemVO.setProblemReasonCode(request.getProblemCode());
			problemVO.setType(MPConstants.REPORT_PROBLEM_v3_1);
			problemVO.setRoleId(roleId);
		}
		return problemVO;
	}

	public ReportProblemVO mapResolveProblemRequest(ResolveProblemOnSORequest request, String soId, Integer resourceId, Integer roleId) {
		ReportProblemVO problemVO = null;
		if(null != request){
			problemVO = new ReportProblemVO();
			problemVO.setSoId(soId);
			problemVO.setResourceId(resourceId);
			problemVO.setResolutionComments(request.getResolutionComments());
			problemVO.setType(MPConstants.RESOLVE_PROBLEM_v3_1);
			problemVO.setRoleId(roleId);
		}
		return problemVO;
	}


	/**
	 * Mapper method for add Note in Report Problem and Resolve problem
	 * @param securityContext
	 * @param vo
	 * @return OrderFulfillmentRequest
	 */
	public OrderFulfillmentRequest mapSONoteForReportAndResolveProblem(SecurityContext securityContext, ReportProblemVO vo){
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();

		SONote note = new SONote();
		note.setEntityId(new Long(securityContext.getVendBuyerResId()));
		note.setCreatedByName(securityContext.getRoles().getLastName() + ", " + securityContext.getRoles().getFirstName());
		note.setCreatedDate(new Date(System.currentTimeMillis()));
		note.setModifiedBy(securityContext.getRoles().getUsername());
		note.setModifiedDate(null);

		note.setRoleId(securityContext.getRoles().getRoleId());
		note.setSendEmail(false);
		note.setReadInd(0);
		note.setNoteTypeId(2);
		note.setPrivate(false);

		if(MPConstants.REPORT_PROBLEM_v3_1.equals(vo.getType())){
			note.setSubject(vo.getProblemReasonCodeDescription());
			note.setNote(vo.getProblemDescriptionComments());
		}else if(MPConstants.RESOLVE_PROBLEM_v3_1.equals(vo.getType())){
			note.setSubject(MPConstants.RESOLVE_PBM_NOTE_COMMENT);
			note.setNote(vo.getResolutionComments());
		}
		request.setElement(note);
		return request;
	}
	/**
	 * @Description Method to map release request to a VO class
	 * @param request
	 * @param soId
	 * @param firmId
	 * @param resourceId
	 * @param roelId 
	 * @return
	 */
	public MobileSOReleaseVO mapReleaseRequestToVO(MobileReleaseSORequest request,String soId,Integer firmId,Integer resourceId, Integer roleId) {
		MobileSOReleaseVO releaseVO = new MobileSOReleaseVO();
		releaseVO.setSoId(soId);
		releaseVO.setFirmId(String.valueOf(firmId));
		releaseVO.setResourceId(resourceId);
		releaseVO.setRoleId(roleId);
		if (StringUtils.isNotBlank(request.getReason())) {
			releaseVO.setReason(request.getReason());
		}
		if (StringUtils.isNotBlank(request.getComments())) {
			releaseVO.setComments(request.getComments());
		}
		if (null != request.getReleaseByFirmInd()) {
			releaseVO.setReleaseByFirmInd(request.getReleaseByFirmInd());
		}
		else{
			releaseVO.setReleaseByFirmInd(false);
		}
		return releaseVO;
	}

	/**
	 * @Description Method to create the response for the release order
	 * @param releaseResponse
	 * @param processResponse
	 * @return
	 */
	public MobileReleaseSOResponse createReleaseResponse(MobileReleaseSOResponse releaseResponse, ProcessResponse processResponse) {
		//Results results = new Results();
		if(processResponse == null) {
			//Create error response when ProcessResponse is null.
			releaseResponse = MobileReleaseSOResponse.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
			//Create Success response when Valid code is set in process response 
			releaseResponse = new MobileReleaseSOResponse(Results.getSuccess(ResultsCode.RELEASE_SO_SUCCESS.getMessage()));					
		} else {
			LOGGER.error("Error occured in Releasing SO, error code: " + processResponse.getCode() + ", message: " + processResponse.getMessage());
			releaseResponse = new MobileReleaseSOResponse(Results.getError(processResponse.getMessage(), processResponse.getCode()));
		}
		//releaseResponse.setResults(results);
		return releaseResponse;
	}

	/**
	 * @Description :Method to map complete profile details from companyProfile VO 
	 * @param companyProfileVO
	 * @param viewCompanyProfileResponse
	 * @return
	 */
	public ViewCompanyProfileResponse mapCompleteProfileVOToResponse(CompanyProfileVO companyProfileVO, ViewCompanyProfileResponse viewCompanyProfileResponse) {
		CompleteProfile completeProfile=new CompleteProfile();
		CompanyOverview companyOverview=new CompanyOverview();

		//Business Information tab
		completeProfile = mapBusinessInfo(completeProfile,companyProfileVO);
		//Company Overview tab
		companyOverview.setCompanyDescription(companyProfileVO.getBusinessDesc());
		completeProfile.setCompanyOverview(companyOverview);
		//Business Address tab
		completeProfile = mapBusinessAddress(completeProfile,companyProfileVO);
		//Primary Contact Information tab
		completeProfile = mapPrimaryContactInformation(completeProfile,companyProfileVO);
		//warranty Information tab
		completeProfile = mapWarrantyInformation(completeProfile,companyProfileVO);
		//Workplace Policy Information tab
		completeProfile = mapWorkPolicyInformation(completeProfile,companyProfileVO);
		//Licenses & Certifications on File  tab
		completeProfile = mapLicenseAndCertification(completeProfile,companyProfileVO);
		//Insurance & policies tab
		completeProfile=mapInsuranceAndPolicies(completeProfile,companyProfileVO);
		//Setting it into profileResponse
		viewCompanyProfileResponse.setCompleteProfile(completeProfile);

		return viewCompanyProfileResponse;
	}

	/**
	 * @Description: Method to map Business Information
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return completeProfile
	 */
	private CompleteProfile mapBusinessInfo(CompleteProfile completeProfile,CompanyProfileVO companyProfileVO){
		BusinessInformation businessInformation=new BusinessInformation();
		
		businessInformation.setLegalBusinessName(companyProfileVO.getBusinessName());
		businessInformation.setDoingBusinessAs(companyProfileVO.getDbaName());
		businessInformation.setBusinessPhone(companyProfileVO.getBusinessPhone());
		businessInformation.setBusinessFax(companyProfileVO.getBusinessFax());
		businessInformation.setTaxPayerId(companyProfileVO.getEinNo());
		businessInformation.setDunsNo(companyProfileVO.getDunsNo());
		businessInformation.setBusinessStructure(companyProfileVO.getBusinessType());
		if(companyProfileVO.getBusinessStartDate()!=null){
			businessInformation.setBusinessStartedDate(formatDate(companyProfileVO.getBusinessStartDate()));
		}
		businessInformation.setPrimaryIndustry(companyProfileVO.getPrimaryIndustry());
		businessInformation.setWebsiteAddress(companyProfileVO.getWebAddress());
		businessInformation.setCompanySize(companyProfileVO.getCompanySize());
		businessInformation.setAnnualSalesRevenue(companyProfileVO.getAnnualSalesVolume());
		if(null!=companyProfileVO.getForeignOwnedInd() && MPConstants.FOREIGN_IND_TRUE.intValue() == companyProfileVO.getForeignOwnedInd().intValue()){
			businessInformation.setForeignOwnedInd(true);
			businessInformation.setForeignOwnedPercent(companyProfileVO.getForeignOwnedPct());
		}
		else{
			businessInformation.setForeignOwnedInd(false);
		}	
		completeProfile.setBusinessInformation(businessInformation);
		return completeProfile;
	}
	
	/**
	 * @Description: Method to map Business Address
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return
	 */
	private CompleteProfile mapBusinessAddress(CompleteProfile completeProfile,CompanyProfileVO companyProfileVO) {
		BusinessAddress businessAddress=new BusinessAddress();
		
		businessAddress.setBusinessStreet1(companyProfileVO.getBusStreet1());
		businessAddress.setBusinessStreet2(companyProfileVO.getBusStreet2());
		businessAddress.setBusinessCity(companyProfileVO.getBusCity());
		businessAddress.setBusinessState(companyProfileVO.getBusStateCd());
		businessAddress.setBusinessZip(companyProfileVO.getBusZip());
		completeProfile.setBusinessAddress(businessAddress);
		return completeProfile;
	}
    
	/**
	 * @Description: Method to map Primary Contact Information
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return
	 */
	private CompleteProfile mapPrimaryContactInformation(CompleteProfile completeProfile, CompanyProfileVO companyProfileVO) {
		PrimaryContactInformation primaryContactInformation=new PrimaryContactInformation();
		
		primaryContactInformation.setRoleWithinCompany(companyProfileVO.getRole());
		primaryContactInformation.setJobTitle(companyProfileVO.getTitle());
		primaryContactInformation.setFirstName(companyProfileVO.getFirstName());
		primaryContactInformation.setMiddleName(companyProfileVO.getMi());
		primaryContactInformation.setLastName(companyProfileVO.getLastName());
		primaryContactInformation.setSuffix(companyProfileVO.getSuffix());
		primaryContactInformation.setEmail(companyProfileVO.getEmail());
		primaryContactInformation.setAlternateEmail(companyProfileVO.getAltEmail());
		completeProfile.setPrimaryContactInformation(primaryContactInformation);
		return completeProfile;
	}
	
	/**
	 * @Description: Method to map warranty Information
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return
	 */
	private CompleteProfile mapWarrantyInformation(CompleteProfile completeProfile, CompanyProfileVO companyProfileVO) {
		WarrantyInformation warrantyInformation=new WarrantyInformation();
		
		if(StringUtils.isNotBlank(companyProfileVO.getFreeEstimate()) && companyProfileVO.getFreeEstimate().equals(MPConstants.ONE)){
			warrantyInformation.setProjectEstimatesChargeInd(true);
		}
		else{
			warrantyInformation.setProjectEstimatesChargeInd(false);
		}
		if(StringUtils.isNotBlank(companyProfileVO.getWarrOfferedLabor()) && companyProfileVO.getWarrOfferedLabor().equals(MPConstants.ONE)){
			warrantyInformation.setWarrantyOnLaborInd(true);
			warrantyInformation.setWarrantyOnLabor(companyProfileVO.getWarrPeriodLabor());
		}
		else{
			warrantyInformation.setWarrantyOnLaborInd(false);
		}
		if(StringUtils.isNotBlank(companyProfileVO.getWarrOfferedParts()) && companyProfileVO.getWarrOfferedParts().equals(MPConstants.ONE) ){
			warrantyInformation.setWarrantyOnPartsInd(true);
			warrantyInformation.setWarrantyOnParts(companyProfileVO.getWarrPeriodParts());
		}
		else{
			warrantyInformation.setWarrantyOnPartsInd(false);
		}
		
		completeProfile.setWarrantyInformation(warrantyInformation);
		return completeProfile;
	}
	
	/**
	 * @Description: Method to map Workplace Policy Information
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return
	 */
	private CompleteProfile mapWorkPolicyInformation(CompleteProfile completeProfile, CompanyProfileVO companyProfileVO) {
		WorkPolicyInformation workPolicyInformation=new WorkPolicyInformation();
		
		if(MPConstants.ONE.equals(companyProfileVO.getConductDrugTest())){
			workPolicyInformation.setDrugTestingPolicyInd(true);
		}
		else{
			workPolicyInformation.setDrugTestingPolicyInd(false);		
		}
		if(MPConstants.ZERO.equals(companyProfileVO.getConductDrugTest())){
			if(MPConstants.ONE.equals(companyProfileVO.getConsiderDrugTest())){
				 workPolicyInformation.setConsiderDrugTestInd(true);
			 }
			 else{
				 workPolicyInformation.setConsiderDrugTestInd(false);
			 }	
		}
		if(MPConstants.ONE.equals(companyProfileVO.getRequireUsDoc())){
			workPolicyInformation.setEmployeeCitizenShipProofInd(true);
		}
		else{
			workPolicyInformation.setEmployeeCitizenShipProofInd(false);	
		}
		if(MPConstants.ZERO.equals(companyProfileVO.getRequireUsDoc())){
			
			if(MPConstants.ONE.equals(companyProfileVO.getConsiderImplPolicy())){
				 workPolicyInformation.setConsiderImplPolicyInd(true);
			 }
			 else{
				 workPolicyInformation.setConsiderImplPolicyInd(false);
			 }
		}
		
		if(MPConstants.ONE.equals(companyProfileVO.getHasEthicsPolicy())){
			workPolicyInformation.setWorkEnvironmentPolicyInd(true);
		}
		if(MPConstants.ZERO.equals(companyProfileVO.getHasEthicsPolicy())){
			workPolicyInformation.setWorkEnvironmentPolicyInd(false);
			
			if(MPConstants.ONE.equals(companyProfileVO.getConsiderEthicPolicy())){
				 workPolicyInformation.setConsiderEthicPolicyInd(true);
			 }
			 else{
				 workPolicyInformation.setConsiderEthicPolicyInd(false);
			 }
		}
		if(MPConstants.ONE.equals(companyProfileVO.getRequireBadge())){
			workPolicyInformation.setRequireBadgeInd(true);
		}
		else{
			workPolicyInformation.setRequireBadgeInd(false);	
		}
		if(MPConstants.ZERO.equals(companyProfileVO.getRequireBadge())){
			if(MPConstants.ONE.equals(companyProfileVO.getConsiderBadge())){
				 workPolicyInformation.setConsiderBadgeInd(true);
			 }
			 else{
				 workPolicyInformation.setConsiderBadgeInd(false);
			 }	
		}
		 	
		completeProfile.setWorkPolicyInformation(workPolicyInformation);
		return completeProfile;
	}
	
	/**
	 * @Description: Method to map Licenses & Certifications on File
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CompleteProfile mapLicenseAndCertification(CompleteProfile completeProfile, CompanyProfileVO companyProfileVO) {
		
		List<Credential> credentialList=new ArrayList<Credential>();
		Credentials credentials=new Credentials();
		LicenseAndCertificationsFile licenseAndCertificationsDetails=new LicenseAndCertificationsFile();
		
		if(null !=companyProfileVO.getLicensesList() && ! companyProfileVO.getLicensesList().isEmpty()){
			
			List<LicensesAndCertVO> licenseList=(List<LicensesAndCertVO>)companyProfileVO.getLicensesList();
			for(LicensesAndCertVO licensesAndCertVO:licenseList){
				Credential credential=new Credential();
				credential.setCredentialType(licensesAndCertVO.getCredTypeDesc());
				credential.setLicenseCertName(licensesAndCertVO.getLicenseName());
				if(null !=licensesAndCertVO.getExpirationDate()){
					credential.setCredentialExpirationDate(formatDate(licensesAndCertVO.getExpirationDate()));
				}
				if(licensesAndCertVO.getWfStateId()== MPConstants.PENDING_APPROVAL_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.PENDING_APPROVAL);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.APPROVED_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.APPROVED);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.OUT_OF_COMPLIANCE_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.OUT_OF_COMPLIANCE);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.REVIEWED_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.REVIEWED);
				}
				credentialList.add(credential);
			}
			
			credentials.setCredential(credentialList);
			licenseAndCertificationsDetails.setCredentials(credentials);
			if(licenseAndCertificationsDetails.getCredentials() != null){
				licenseAndCertificationsDetails.setLicenseCertificationInd(true);
			}
			completeProfile.setLicenseAndCertificationsFile(licenseAndCertificationsDetails);
		}
		return completeProfile;
	}
	
	/**
	 * @Description: Method to map Insurance & policies
	 * @param completeProfile
	 * @param companyProfileVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CompleteProfile mapInsuranceAndPolicies(CompleteProfile completeProfile, CompanyProfileVO companyProfileVO) {
		
		List<PolicyDetail> policyDetailList=new ArrayList<PolicyDetail>();
		PolicyDetails policyDetails=new PolicyDetails();
		InsurancePoliciesOnFile insurancePolicyDetails=new InsurancePoliciesOnFile();
		
		if(null !=companyProfileVO.getInsuranceList() && ! companyProfileVO.getInsuranceList().isEmpty()){
			List<LicensesAndCertVO> insuranceList=(List<LicensesAndCertVO>)companyProfileVO.getInsuranceList();
			for(LicensesAndCertVO licensesAndCertVO:insuranceList){
				PolicyDetail policyDetail=new PolicyDetail();
				policyDetail.setPolicyType(licensesAndCertVO.getCredTypeDesc());
				policyDetail.setCarrierName(licensesAndCertVO.getSource());
				if(null !=licensesAndCertVO.getExpirationDate()){
					policyDetail.setExpirationDate(formatDate(licensesAndCertVO.getExpirationDate()));
				}
				policyDetail.setDocumentId(String.valueOf(licensesAndCertVO.getCurrentDocumentID()));
				if(licensesAndCertVO.getWfStateId()== MPConstants.PENDING_APPROVAL_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.PENDING_APPROVAL);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.APPROVED_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.APPROVED);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.OUT_OF_COMPLIANCE_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.OUT_OF_COMPLIANCE);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.REVIEWED_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.REVIEWED);
				}
				policyDetailList.add(policyDetail);
			}
			
			policyDetails.setPolicyDetail(policyDetailList);
			insurancePolicyDetails.setPolicyDetails(policyDetails);
			if(insurancePolicyDetails.getPolicyDetails() !=null){
				insurancePolicyDetails.setInsurancePoliciesInd(true);
			}
			completeProfile.setInsurancePoliciesOnFile(insurancePolicyDetails);
		}
		return completeProfile;
	}
	
	
	/**
	 * @Description: Method to map public profile details from companyProfile VO 
	 * @param companyProfileVO
	 * @param viewCompanyProfileResponse
	 * @return
	 */
	public ViewCompanyProfileResponse mapPublicProfileVOToResponse(CompanyProfileVO companyProfileVO, ViewCompanyProfileResponse viewCompanyProfileResponse) {
		PublicProfile publicProfile=new PublicProfile();
		CompanyOverview companyOverview=new CompanyOverview();
		
		//Business Information tab
		publicProfile = mapBusinessInfo(publicProfile,companyProfileVO);
		//Company Overview tab
		companyOverview.setCompanyDescription(companyProfileVO.getBusinessDesc());
		publicProfile.setCompanyOverview(companyOverview);
		//Business Address tab
		publicProfile = mapBusinessAddress(publicProfile,companyProfileVO);
		//warranty Information tab
		publicProfile = mapWarrantyInformation(publicProfile,companyProfileVO);
		//Licenses & Certifications on File  tab
		publicProfile = mapLicenseAndCertification(publicProfile,companyProfileVO);
		//Insurance & policies
		publicProfile=mapInsuranceAndPolicies(publicProfile,companyProfileVO);
		//Setting it into profileResponse
		viewCompanyProfileResponse.setPublicProfile(publicProfile);
			
		return viewCompanyProfileResponse;
	}

	/**
	 * @Description: Method to map Business Information
	 * @param publicProfile
	 * @param companyProfileVO
	 * @return
	 */
	private PublicProfile mapBusinessInfo(PublicProfile publicProfile,CompanyProfileVO companyProfileVO){
		BusinessInformation businessInformation=new BusinessInformation();
		
		businessInformation.setCompanyId(String.valueOf(companyProfileVO.getVendorId()));
		businessInformation.setBusinessStructure(companyProfileVO.getBusinessType());
		if(companyProfileVO.getBusinessStartDate()!=null){
			businessInformation.setBusinessStartedDate(formatDate(companyProfileVO.getBusinessStartDate()));
		}
		if(companyProfileVO.getMemberSince() !=null){
			businessInformation.setMemberSinceDate(formatDate(companyProfileVO.getMemberSince()));
		}
		businessInformation.setCompanySize(companyProfileVO.getCompanySize());
		businessInformation.setPrimaryIndustry(companyProfileVO.getPrimaryIndustry());
		publicProfile.setBusinessInformation(businessInformation);
		return publicProfile;
	}
    /**
     * @Description: Method to map business Address
     * @param publicProfile
     * @param companyProfileVO
     * @return
     */
	private PublicProfile mapBusinessAddress(PublicProfile publicProfile,CompanyProfileVO companyProfileVO) {
		BusinessAddress businessAddress=new BusinessAddress();
		
		businessAddress.setBusinessCity(companyProfileVO.getBusCity());
		businessAddress.setBusinessState(companyProfileVO.getBusStateCd());
		businessAddress.setBusinessZip(companyProfileVO.getBusZip());
		
		publicProfile.setBusinessAddress(businessAddress);

		return publicProfile;
	}
	
	/**
	 * @Description: Method to map warranty information
	 * @param publicProfile
	 * @param companyProfileVO
	 * @return
	 */
	private PublicProfile mapWarrantyInformation(PublicProfile publicProfile,CompanyProfileVO companyProfileVO) {
		WarrantyInformation warrantyInformation=new WarrantyInformation();
		if(StringUtils.isNotBlank(companyProfileVO.getFreeEstimate()) && companyProfileVO.getFreeEstimate().equals(MPConstants.ONE)){
			warrantyInformation.setProjectEstimatesChargeInd(true);
		}
		else{
			warrantyInformation.setProjectEstimatesChargeInd(false);
		}
		if(StringUtils.isNotBlank(companyProfileVO.getWarrOfferedLabor()) && companyProfileVO.getWarrOfferedLabor().equals(MPConstants.ONE)){
			warrantyInformation.setWarrantyOnLaborInd(true);
			warrantyInformation.setWarrantyOnLabor(companyProfileVO.getWarrPeriodLabor());
		}
		else{
			warrantyInformation.setWarrantyOnLaborInd(false);
		}
		if(StringUtils.isNotBlank(companyProfileVO.getWarrOfferedParts()) && companyProfileVO.getWarrOfferedParts().equals(MPConstants.ONE) ){
			warrantyInformation.setWarrantyOnPartsInd(true);
			warrantyInformation.setWarrantyOnParts(companyProfileVO.getWarrPeriodParts());
		}
		else{
			warrantyInformation.setWarrantyOnPartsInd(false);
		}
		
		publicProfile.setWarrantyInformation(warrantyInformation);
		return publicProfile;
	}
	
	/**
	 * @Description: Method to map license details
	 * @param publicProfile
	 * @param companyProfileVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PublicProfile mapLicenseAndCertification(PublicProfile publicProfile, CompanyProfileVO companyProfileVO) {
		
		List<Credential> credentialList=new ArrayList<Credential>();
		Credentials credentials=new Credentials();
		LicenseAndCertificationsFile licenseAndCertificationsDetails=new LicenseAndCertificationsFile();
		
		if(null != companyProfileVO.getLicensesList() && ! companyProfileVO.getLicensesList().isEmpty()){
			
			List<LicensesAndCertVO> licenseList=(List<LicensesAndCertVO>)companyProfileVO.getLicensesList();
			for(LicensesAndCertVO licensesAndCertVO:licenseList){
				Credential credential=new Credential();
				credential.setCredentialType(licensesAndCertVO.getCredTypeDesc());
				credential.setLicenseCertName(licensesAndCertVO.getLicenseName());
				if(null !=licensesAndCertVO.getExpirationDate()){
					credential.setCredentialExpirationDate(formatDate(licensesAndCertVO.getExpirationDate()));
				}
				if(licensesAndCertVO.getWfStateId()== MPConstants.PENDING_APPROVAL_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.PENDING_APPROVAL);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.APPROVED_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.APPROVED);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.OUT_OF_COMPLIANCE_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.OUT_OF_COMPLIANCE);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.REVIEWED_STATUS_ID){
					credential.setServiceLiveVerificationStatus(MPConstants.REVIEWED);
				}
				credentialList.add(credential);
			}
			
			credentials.setCredential(credentialList);
			licenseAndCertificationsDetails.setCredentials(credentials);
			if(licenseAndCertificationsDetails.getCredentials() != null){
				licenseAndCertificationsDetails.setLicenseCertificationInd(true);
			}
			publicProfile.setLicenseAndCertificationsFile(licenseAndCertificationsDetails);
		}
		return publicProfile;
	}
	
	/**
	 * @Description: Method to map insurance details
	 * @param publicProfile
	 * @param companyProfileVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PublicProfile mapInsuranceAndPolicies(PublicProfile publicProfile,CompanyProfileVO companyProfileVO) {
		
		List<PolicyDetail> policyDetailList=new ArrayList<PolicyDetail>();
		PolicyDetails policyDetails=new PolicyDetails();
		InsurancePoliciesOnFile insurancePolicyDetails=new InsurancePoliciesOnFile();
		
		if(null !=companyProfileVO.getInsuranceList() && ! companyProfileVO.getInsuranceList().isEmpty()){
			List<LicensesAndCertVO> insuranceList=(List<LicensesAndCertVO>)companyProfileVO.getInsuranceList();
			for(LicensesAndCertVO licensesAndCertVO:insuranceList){
				PolicyDetail policyDetail=new PolicyDetail();
				policyDetail.setPolicyType(licensesAndCertVO.getCredTypeDesc());
				policyDetail.setCarrierName(licensesAndCertVO.getSource());
				if(null !=licensesAndCertVO.getExpirationDate()){
					policyDetail.setExpirationDate(formatDate(licensesAndCertVO.getExpirationDate()));
				}
				policyDetail.setDocumentId(String.valueOf(licensesAndCertVO.getCurrentDocumentID()));
				if(licensesAndCertVO.getWfStateId()== MPConstants.PENDING_APPROVAL_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.PENDING_APPROVAL);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.APPROVED_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.APPROVED);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.OUT_OF_COMPLIANCE_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.OUT_OF_COMPLIANCE);
				}
				else if(licensesAndCertVO.getWfStateId()== MPConstants.REVIEWED_STATUS_ID){
					policyDetail.setServiceLiveVerificationStatus(MPConstants.REVIEWED);
				}
				policyDetailList.add(policyDetail);
			}
			
			policyDetails.setPolicyDetail(policyDetailList);
			insurancePolicyDetails.setPolicyDetails(policyDetails);
			if(insurancePolicyDetails.getPolicyDetails() !=null){
				insurancePolicyDetails.setInsurancePoliciesInd(true);
			}
			publicProfile.setInsurancePoliciesOnFile(insurancePolicyDetails);
		}
		return publicProfile;
	}
	
	public UpdateTimeWindowVO mapUpdateTimeWindowRequest(UpdatetimeWindowRequest request, APIRequestVO apiVO, SecurityContext securityContext){
		UpdateTimeWindowVO timeWindowVO = null;
		if(null != request && null != apiVO){
			timeWindowVO = new UpdateTimeWindowVO();
			timeWindowVO.setSoId(apiVO.getSOId());
			timeWindowVO.setVendorId(Integer.parseInt(apiVO.getProviderId()));
			timeWindowVO.setResourceId(apiVO.getProviderResourceId());
			timeWindowVO.setRoleLevel(apiVO.getRoleId());
			if(securityContext!=null){
				timeWindowVO.setUserName(securityContext.getUsername());
				timeWindowVO.setEntityId(securityContext.getVendBuyerResId());
				timeWindowVO.setRoleId(securityContext.getRoleId());
				LoginCredentialVO lvRoles = securityContext.getRoles();
				timeWindowVO.setCreatedBy(lvRoles.getFirstName() +MPConstants.WHITE_SPACE+lvRoles.getLastName());
			}

			timeWindowVO.setStartTime(request.getStartTime());
			timeWindowVO.setEndTime(request.getEndTime());
			timeWindowVO.setEta(request.getEta());
			timeWindowVO.setCustomerConfirmedInd(request.getCustomerConfirmedInd());
			
			if(StringUtils.isNotBlank(timeWindowVO.getStartTime()) && validateTime(timeWindowVO.getStartTime())){
				timeWindowVO.setStartTimeIn12hrformat(convertTimeFormat(timeWindowVO.getStartTime()));
			}
			
			if(StringUtils.isNotBlank(timeWindowVO.getEndTime()) && validateTime(timeWindowVO.getEndTime()) ){
				timeWindowVO.setEndTimeIn12hrformat(convertTimeFormat(timeWindowVO.getEndTime()));
			}
			
			if(StringUtils.isNotBlank(timeWindowVO.getEta())  && validateTime(timeWindowVO.getEta())){
				timeWindowVO.setEtaIn12hrformat(convertTimeFormat(timeWindowVO.getEta()));
			}
		}
		return timeWindowVO;
	}

	public UpdateTimeWindowVO mapDateAndTimeToSave(UpdateTimeWindowVO timeWindowVO){
		//endTime and eta have relevance only if date type is Range
		if(!timeWindowVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE)){
			timeWindowVO.setEta(null);
			timeWindowVO.setEtaIn12hrformat(null);
			timeWindowVO.setEndTime(null);
			timeWindowVO.setEndTimeIn12hrformat(null);
		}
		
		// get the service start date & time on service order Time zone.
		if (StringUtils.isNotBlank(timeWindowVO.getStartTimeIn12hrformat()) && StringUtils.isNotBlank(timeWindowVO.getStartDateInGMT())){
			HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
			Timestamp startAppDate = TimestampUtils.getTimestampFromString(timeWindowVO.getStartDateInGMT(), MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
			startAppDate = (Timestamp) TimeUtils.convertGMTToGivenTimeZone(startAppDate, timeWindowVO.getServiceTimeStart(), 
					timeWindowVO.getTimeZone()).get(MPConstants.DATE_PARAMETER);
			dateTimeMap = TimeUtils.convertToGMT(startAppDate, timeWindowVO.getStartTimeIn12hrformat(), timeWindowVO.getTimeZone());
			
			timeWindowVO.setStartTimeToSave(dateTimeMap.get(MPConstants.TIME_PARAMETER).toString());
			timeWindowVO.setStartDateToSave(dateTimeMap.get(MPConstants.DATE_PARAMETER).toString());
			
			LOGGER.info("startDate:"+timeWindowVO.getStartDateToSave()+" startTime:"+timeWindowVO.getStartTimeToSave());
		}
		// get the service end date & time on service order Time zone.	
		if(MPConstants.SERVICE_TYPE_DATE_RANGE.equals(timeWindowVO.getServiceDateType())
				&& StringUtils.isNotBlank(timeWindowVO.getEndTimeIn12hrformat())){
			HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
			Timestamp endAppDate = TimestampUtils.getTimestampFromString(timeWindowVO.getEndDateInGMT(), MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
			endAppDate = (Timestamp) TimeUtils.convertGMTToGivenTimeZone(endAppDate, timeWindowVO.getServiceTimeEnd(), 
					timeWindowVO.getTimeZone()).get(MPConstants.DATE_PARAMETER);
			dateTimeMap = TimeUtils.convertToGMT(endAppDate, timeWindowVO.getEndTimeIn12hrformat(), timeWindowVO.getTimeZone());
			
			timeWindowVO.setEndTimeToSave(dateTimeMap.get(MPConstants.TIME_PARAMETER).toString());
			timeWindowVO.setEndDateToSave(dateTimeMap.get(MPConstants.DATE_PARAMETER).toString());
			
			LOGGER.info("endDate:"+timeWindowVO.getEndDateToSave()+" endTime:"+timeWindowVO.getEndTimeToSave());
		}
		return timeWindowVO;
	}
	
	public UpdateScheduleVO getVOForUpdateTime(UpdateTimeWindowVO timeWindowVO){
		SimpleDateFormat format = new SimpleDateFormat(MPConstants.DATE_FORMAT_APPENDED_WITH_TIME);
		UpdateScheduleVO scheduleVO = new UpdateScheduleVO();
		scheduleVO.setSoId(timeWindowVO.getSoId());
		scheduleVO.setProviderId(timeWindowVO.getResourceId());
		
		scheduleVO.setSource(MPConstants.SOURCE_UPDATE_TIME);
		
		scheduleVO.setServiceDateStart(timeWindowVO.getStartDateToSave());
		scheduleVO.setServiceTimeStart(timeWindowVO.getStartTimeToSave());
		scheduleVO.setServiceDateEnd(timeWindowVO.getEndDateToSave());
		scheduleVO.setServiceTimeEnd(timeWindowVO.getEndTimeToSave());
		
		scheduleVO.setEta(timeWindowVO.getEtaIn12hrformat());
		
		if(timeWindowVO.getCustomerConfirmedInd()){
			scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_CONFIRM_TRUE);
		}else{
			scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_CONFIRM_FALSE);
		}
		
		scheduleVO.setCreatedDate(Timestamp.valueOf(format.format(new Date())));
		scheduleVO.setModifiedDate(Timestamp.valueOf(format.format(new Date())));
		scheduleVO.setModifiedByName(timeWindowVO.getUserName());
		return scheduleVO;
	}
	
	public ProviderHistoryVO getHistoryVO(UpdateTimeWindowVO timeWindowVO){
		ProviderHistoryVO hisVO = new ProviderHistoryVO();
		Date d=new Date(System.currentTimeMillis());
		Timestamp date = new Timestamp(d.getTime());
		hisVO.setSoId(timeWindowVO.getSoId());
		hisVO.setActionId(MPConstants.UPDATE_TIME_ACTION_ID);
		hisVO.setDescription(populateHistoryDescription(timeWindowVO));
		hisVO.setCreatedDate(date);
		hisVO.setModifiedDate(date);
		hisVO.setCreatedBy(timeWindowVO.getCreatedBy());
		hisVO.setRoleId(timeWindowVO.getRoleId());
		hisVO.setModifiedBy(timeWindowVO.getUserName());
		hisVO.setEnitityId(timeWindowVO.getEntityId());
		return hisVO;
	}
	
	private String populateHistoryDescription(UpdateTimeWindowVO timeWindowVO){
		StringBuffer historyDesc = new StringBuffer();
		if(StringUtils.isNotBlank(timeWindowVO.getStartTimeIn12hrformat())){
			historyDesc.append(MPConstants.UPDATE_TIME_ACTION_DESC);
			historyDesc.append(MPConstants.WHITE_SPACE);
			historyDesc.append(timeWindowVO.getStartTimeIn12hrformat());
		}
		if(StringUtils.isBlank(timeWindowVO.getEndTimeIn12hrformat())){
			historyDesc.append(MPConstants.DOT);
		}else if(StringUtils.isNotBlank(timeWindowVO.getEndTimeIn12hrformat())){
			historyDesc.append(MPConstants.WHITE_SPACE);
			historyDesc.append(MPConstants.HYPHEN);
			historyDesc.append(MPConstants.WHITE_SPACE);
			historyDesc.append(timeWindowVO.getEndTimeIn12hrformat());
			historyDesc.append(MPConstants.DOT);
		}
		return historyDesc.toString();
	}
	
	/**
	 *  @Description:Map the team member details to the response 
	 * @param teamMemberVO
	 * @return ViewTeamMembersResponse
	 */
	public ViewTeamMembersResponse mapTeamMemberVOToResponse(List<TeamMemberVO> teamMemberVOList,List <TeamMemberDocumentVO> teamMemberDocumentIdList) {
		ViewTeamMembersResponse viewTeamMembersResponse=new ViewTeamMembersResponse() ;
		TeamMemberDetails teamMemberDetails=new TeamMemberDetails();
		List<TeamMemberDetail> teamMemberDetailsList=new ArrayList<TeamMemberDetail>();
		if(null !=teamMemberVOList && !teamMemberVOList.isEmpty()){
			for(TeamMemberVO teamMembersVO:teamMemberVOList){
				TeamMemberDetail teamMemberDetail=new TeamMemberDetail();
				teamMemberDetail.setResourceId(teamMembersVO.getResourceId());
				teamMemberDetail.setFirstName(teamMembersVO.getFirstName());
				teamMemberDetail.setLastName(teamMembersVO.getLastName());
				teamMemberDetail.setTitle(teamMembersVO.getTitle());
				teamMemberDetail.setPhoneNumber(teamMembersVO.getPhoneNumber());
				if(StringUtils.isNotBlank(teamMembersVO.getResourceState())){
					teamMemberDetail.setMemberStatus(teamMembersVO.getResourceState());
				}
				else{
					teamMemberDetail.setMemberStatus(MPConstants.NOT_APPLICABLE);
				}
				teamMemberDetail.setBackgroundcheckRecertifyInd(teamMembersVO.getBackgroundCheckRecertify());
				
				if(StringUtils.isNotBlank(teamMembersVO.getBackgroundCheckState()) && !teamMembersVO.getBackgroundCheckState().equals(MPConstants.BG_CHECK_STATUS_NOT_STARTED)){
					teamMemberDetail.setBackgroundCheckStatus(teamMembersVO.getBackgroundCheckState());
				}
				else{
					teamMemberDetail.setBackgroundCheckStatus(MPConstants.NOT_APPLICABLE);
				}
				if(StringUtils.isBlank(teamMembersVO.getMarketStatus()) || teamMembersVO.getMarketStatus().equals(MPConstants.ZERO)){
					teamMemberDetail.setMarketStatus(MPConstants.MEMBER_INACTIVE);
				}
				else{
					teamMemberDetail.setMarketStatus(MPConstants.MEMBER_ACTIVE);
				}
				//Adding document id to teamMemberDetail
				if(null !=teamMemberDocumentIdList && ! teamMemberDocumentIdList.isEmpty()){
					for(TeamMemberDocumentVO teamMemberDocumentVO:teamMemberDocumentIdList){
						if(null !=teamMemberDetail.getResourceId() && teamMemberDetail.getResourceId().equals(teamMemberDocumentVO.getResourceId())){
							teamMemberDetail.setDocumentId(teamMemberDocumentVO.getDocumentId());
						}
					}
				}
				teamMemberDetailsList.add(teamMemberDetail);	
			}
			teamMemberDetails.setTotalUsers(teamMemberVOList.size());
			teamMemberDetails.setTeamMemberDetail(teamMemberDetailsList);
			
		}else{
			teamMemberDetails.setTotalUsers(Integer.parseInt(MPConstants.ZERO));
		}
		viewTeamMembersResponse.setTeamMemberDetails(teamMemberDetails);
		return viewTeamMembersResponse;
	}
	
	
	public UpdateScheduleVO mapUpdateScheduleRequest(UpdateScheduleDetailsRequest request, APIRequestVO apiVO, SecurityContext securityContext){
		UpdateScheduleVO scheduleVO = null;
		if(null != request && null != apiVO){
			scheduleVO = new UpdateScheduleVO();
			scheduleVO = mapBasicDetails(scheduleVO,apiVO,request, securityContext);
			//PRE_CALL
			if(scheduleVO.getSource().equals(MPConstants.PRE_CALL)){
				if(!scheduleVO.getCustomerAvailableFlag()){//customer not available
					scheduleVO = mapPreCallCustomerNotAvailable(scheduleVO);
				}else{//customer available
					scheduleVO = mapPreCallCustomerAvailable(scheduleVO);
				}
			}
			//CONFIRM_APPOINTMENT
			else if(scheduleVO.getSource().equals(MPConstants.CONFIRM_APPOINTMENT)){
				if(!scheduleVO.getCustomerAvailableFlag()){//customer not available
					scheduleVO = mapConfirmAppointmentCustomerNotAvailable(scheduleVO);
				}else{//customer available
					scheduleVO = mapConfirmAppointmentCustomerAvailable(scheduleVO);
				}
			}
			//If CustAvailableRespCode= 2(Update Time Window), then map the time details. 
			if(scheduleVO.getCustomerAvailableFlag() 
					&& MPConstants.UPDATE_SERVICE_WINDOW.equals(scheduleVO.getCustAvailableRespCode())){
				scheduleVO.setStartTimeFromRequest(request.getStartTime());
				scheduleVO.setEndTimeFromRequest(request.getEndTime());
			}
		}
		return scheduleVO;
	}
	
	public UpdateScheduleVO mapUpdateScheduleRequest(UpdateScheduleDetailsRequest request, UpdateScheduleVO scheduleVO, UpdateApptTimeVO apptTimeVO){
		scheduleVO.setStartTimeFromRequest(request.getStartTime());
		scheduleVO.setEndTimeFromRequest(request.getEndTime());
		if(null != scheduleVO && null != apptTimeVO){
			if(null != apptTimeVO.getStartDate()){
				scheduleVO.setServiceDateStart(apptTimeVO.getStartDate().substring(0, apptTimeVO.getStartDate().indexOf(" ")));
			}
			if(null != apptTimeVO.getEndDate()){
				scheduleVO.setServiceDateEnd(apptTimeVO.getEndDate().substring(0, apptTimeVO.getEndDate().indexOf(" ")));
			}
			scheduleVO.setServiceTimeStart(apptTimeVO.getServiceTimeStart());
			scheduleVO.setServiceTimeEnd(apptTimeVO.getServiceTimeEnd());
			scheduleVO.setServiceTimeZone(apptTimeVO.getZone());
			scheduleVO.setMinTimeWindow(apptTimeVO.getMinTimeWindow());
			scheduleVO.setMaxTimeWindow(apptTimeVO.getMaxTimeWindow());
			scheduleVO.setServiceDateType(apptTimeVO.getServiceDateType());
			//scheduleVO = mapDateAndTimeAfterConversion(scheduleVO);
		}
		return scheduleVO;
	}
	
	public UpdateScheduleVO mapDateAndTimeAfterConversion(UpdateScheduleVO scheduleVO){
		//endTime have relevance only if date type is Range
		if(!scheduleVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE)){
			scheduleVO.setEndTimeFromRequest(null);
		}
		// get the service start date & time on service order Time zone.
		if (StringUtils.isNotBlank(scheduleVO.getStartTimeFromRequest()) && StringUtils.isNotBlank(scheduleVO.getServiceDateStart())){
			HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
			Timestamp startAppDate = TimestampUtils.getTimestampFromString(scheduleVO.getServiceDateStart(), MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
			startAppDate = (Timestamp) TimeUtils.convertGMTToGivenTimeZone(startAppDate, scheduleVO.getServiceTimeStart(), 
					scheduleVO.getServiceTimeZone()).get(MPConstants.DATE_PARAMETER);
			dateTimeMap = TimeUtils.convertToGMT(startAppDate, convertTimeFormat(scheduleVO.getStartTimeFromRequest()), scheduleVO.getServiceTimeZone());
			
			scheduleVO.setServiceTimeStart(dateTimeMap.get(MPConstants.TIME_PARAMETER).toString());
			scheduleVO.setServiceDateStart(dateTimeMap.get(MPConstants.DATE_PARAMETER).toString());
			
		}
		// get the service end date & time on service order Time zone.	
		if(MPConstants.SERVICE_TYPE_DATE_RANGE.equals(scheduleVO.getServiceDateType())){
			HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
			Timestamp endAppDate = TimestampUtils.getTimestampFromString(scheduleVO.getServiceDateEnd(), MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
			endAppDate = (Timestamp) TimeUtils.convertGMTToGivenTimeZone(endAppDate, scheduleVO.getServiceTimeEnd(), 
					scheduleVO.getServiceTimeZone()).get(MPConstants.DATE_PARAMETER);
			dateTimeMap = TimeUtils.convertToGMT(endAppDate, convertTimeFormat(scheduleVO.getEndTimeFromRequest()), scheduleVO.getServiceTimeZone());
			
			scheduleVO.setServiceTimeEnd(dateTimeMap.get(MPConstants.TIME_PARAMETER).toString());
			scheduleVO.setServiceDateEnd(dateTimeMap.get(MPConstants.DATE_PARAMETER).toString());
			
		}
		return scheduleVO;
	}
	
	private UpdateScheduleVO mapBasicDetails(UpdateScheduleVO scheduleVO, APIRequestVO apiVO, UpdateScheduleDetailsRequest request, SecurityContext securityContext){
		scheduleVO.setSoId(apiVO.getSOId());
		scheduleVO.setProviderId(Integer.parseInt(apiVO.getProviderId()));
		scheduleVO.setResourceId(apiVO.getProviderResourceId());
		scheduleVO.setRoleId(apiVO.getRoleId());
		scheduleVO.setSource(request.getSource());
		scheduleVO.setCustomerAvailableFlag(request.getCustomerAvailableFlag());
		scheduleVO.setCustAvailableRespCode(request.getCustResponseReasonCode());
		
		//Issue fix for SL-21196 -- start
		if(StringUtils.isNotBlank(request.getEta())){
			scheduleVO.setEtaOriginalValue(request.getEta());
			if(validateTime(request.getEta())){
				scheduleVO.setEta(convertTimeFormat(request.getEta()));
			}
		}
		//Issue fix for SL-21196 -- stop
		if(!scheduleVO.getCustomerAvailableFlag()){
			scheduleVO.setCustNotAvailableReasonCode(request.getCustNotAvailableReasonCode());
		}else{
			if(StringUtils.isNotBlank(request.getSoLocNotes())){
				scheduleVO.setSoNotes(request.getSoLocNotes());
			}
			if(StringUtils.isNotBlank(request.getSpecialInstructions())){
				scheduleVO.setSpecialInstructions(request.getSpecialInstructions());
			}
		}
		if(null!=securityContext){
			scheduleVO.setModifiedByName(securityContext.getUsername());
		}
		return scheduleVO;
	}
	
	private UpdateScheduleVO mapPreCallCustomerNotAvailable(UpdateScheduleVO scheduleVO){
		scheduleVO.setScheduleStatusId(MPConstants.PRE_CALL_ATTEMPTED);
		if(null != scheduleVO.getCustNotAvailableReasonCode()){
			scheduleVO.setReasonId(scheduleVO.getCustNotAvailableReasonCode().toString());
		}
		scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_NOT_AVAILABLE);
		return scheduleVO;
		
	}
	
	private UpdateScheduleVO mapPreCallCustomerAvailable(UpdateScheduleVO scheduleVO){
		scheduleVO.setScheduleStatusId(MPConstants.PRE_CALL_COMPLETED);
		scheduleVO.setReasonId(MPConstants.PRE_CALL_COMPLETED_REASON);
		if(null == scheduleVO.getCustAvailableRespCode() 
				|| MPConstants.ZERO_COUNT.equals(scheduleVO.getCustAvailableRespCode()) 
				|| MPConstants.RESCHEDULE_REASON.equals(scheduleVO.getCustAvailableRespCode())){
			scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_NOT_AVAILABLE);
		}
		else{
			scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_AVAILABLE);
		}
		return scheduleVO;
	}
	
	private UpdateScheduleVO mapConfirmAppointmentCustomerNotAvailable(UpdateScheduleVO scheduleVO){
		scheduleVO.setScheduleStatusId(MPConstants.CONFIRM_APPOINTMENT_ATTEMPTED);
		if(null != scheduleVO.getCustNotAvailableReasonCode()){
			scheduleVO.setReasonId(scheduleVO.getCustNotAvailableReasonCode().toString());
		}
		scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_NOT_AVAILABLE);
		return scheduleVO;
	}
	
	private UpdateScheduleVO mapConfirmAppointmentCustomerAvailable(UpdateScheduleVO scheduleVO){
		scheduleVO.setScheduleStatusId(MPConstants.CONFIRM_APPOINTMENT_COMPLETED);
		scheduleVO.setReasonId(MPConstants.TIME_WINDOW_CALL_COMPLETED_REASON);
		if(null == scheduleVO.getCustAvailableRespCode() 
				|| MPConstants.ZERO_COUNT.equals(scheduleVO.getCustAvailableRespCode())
				|| MPConstants.RESCHEDULE_REASON.equals(scheduleVO.getCustAvailableRespCode())){
			scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_NOT_AVAILABLE);
		}
		else{
			scheduleVO.setCustomerConfirmedInd(MPConstants.CUSTOMER_AVAILABLE);
		}
		return scheduleVO;
		
	}
	/**
	 * @Description: Method to format date
	 * @param dateString
	 * @return
	 */
	private String formatDate(Date dateString){
		
		 Format formatter = new SimpleDateFormat(MPConstants.REQUIRED_DATE_FORMAT);
       String stringDate1=null;
        if(dateString!=null) {
            stringDate1= formatter.format(dateString);
        }
		return stringDate1;
	}

	//convert 24 hr format to 12 hr format
	private String convertTimeFormat(String time){

		DateFormat f1 = new SimpleDateFormat("HH:mm:ss");
		Date d = null;
		try {
			d = f1.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat f2 = new SimpleDateFormat("hh:mm a");
		return f2.format(d).toUpperCase(); 
	}

	private boolean validateTime(String time){
		Pattern pattern;
		Matcher matcher;
		boolean valid = true;
		String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		matcher = pattern.matcher(time);
		valid = matcher.matches();
		return valid;		
	}
	
	public ViewAdvancedDashboardResponse mapViewDashboardResponseOld(MobileDashboardVO dashboardVO, Integer roleId, Integer bid) {
		Tabs tabs =  new Tabs();
		List<Tab> tabList = new ArrayList<Tab>();
		ViewAdvancedDashboardResponse response =new ViewAdvancedDashboardResponse();
		List<String> tabNamesAdded = new ArrayList<String>();
		if((null !=dashboardVO.getCountVO() && !dashboardVO.getCountVO().isEmpty()) || (null!=bid)){
			if(null !=dashboardVO.getCountVO() && !dashboardVO.getCountVO().isEmpty()){
				for(DashBoardCountVO countVO: dashboardVO.getCountVO()){
					Tab tab = new Tab();
					tabNamesAdded.add(countVO.getTabName());

					tab.setTabName(countVO.getTabName());
					tab.setTabCount( null!=countVO.getTabCount() ? countVO.getTabCount():0);
					tabList.add(tab);
				}
			}
		
			if (!roleId.equals(MPConstants.ROLE_LEVEL_ONE)){
				//R16_2_1: SL-21266: Including bid count in view dashboard service v3.1 for providers other than role 1
				Tab tab = new Tab();
				tabNamesAdded.add(MPConstants.BID_REQUESTS);
				tab.setTabName(MPConstants.BID_REQUESTS);
				tab.setTabCount(null!=bid ? bid:0);
				tabList.add(tab);
			}
			if((tabNamesAdded.size() < MPConstants.LEVEL_NOT_ONE_MAX_TAB_LIST && !roleId.equals(MPConstants.ROLE_LEVEL_ONE))
					||(tabNamesAdded.size() < MPConstants.LEVEL_ONE_MAX_TAB_LIST && roleId.equals(MPConstants.ROLE_LEVEL_ONE))){
				tabList = mapZeroTabCountswithBid(tabNamesAdded,tabList,roleId);
			}			
		}else{
			// Default all to 0
			tabList = mapZeroTabCountswithBid(tabNamesAdded,tabList,roleId);
		}
		sortTabName(tabList);
		tabs.setTab(tabList);
		response.setTabs(tabs);
		response.setResults(Results.getSuccess());
		return response;
	}
	
    /**
     * @Description: Method to map the additional details fetched for the dashboard,to the response object
     * @param dashboardResponse
     * @param dashboardVOForAdditionalDetails
     * @param dashboardVOForStatusMonitor
     * @param dashboardVOForSpnMonitor
     * @return
     */
	public ViewAdvancedDashboardResponse mapAdditionalDetailsToResponse(ViewAdvancedDashboardResponse dashboardResponse, 
			SODashboardVO dashboardVOForAdditionalDetails,SODashboardVO dashboardVOForStatusMonitor,
			List<SPNMonitorVO> dashboardVOForSpnMonitor, boolean viewOrderPricing) {
		if(null !=dashboardVOForAdditionalDetails){
			dashboardResponse=mapLeadOrderStatistics(dashboardResponse,dashboardVOForAdditionalDetails);
			//map price details only if the user has view order pricing permission
			if(viewOrderPricing){
				dashboardResponse=mapAvailableBalance(dashboardResponse,dashboardVOForAdditionalDetails);
			}
			dashboardResponse=mapPerformanceStatistics(dashboardResponse,dashboardVOForAdditionalDetails);
		}
		if(null !=dashboardVOForStatusMonitor){
			dashboardResponse=mapServiceLiveStatusMonitor(dashboardResponse,dashboardVOForStatusMonitor);
		}
		if((null !=dashboardVOForSpnMonitor && !dashboardVOForSpnMonitor.isEmpty())){
			dashboardResponse=mapSpnMonitorDetails(dashboardResponse,dashboardVOForSpnMonitor);
		}
		return dashboardResponse;
	}
	
	/**
	 * @Description: Method to map the lead order details 
	 * @param dashboardResponse
	 * @param soDashboardVO
	 * @return
	 */
	private ViewAdvancedDashboardResponse mapLeadOrderStatistics(ViewAdvancedDashboardResponse dashboardResponse, SODashboardVO soDashboardVO) {
		LeadDetail leadDetail=null;
		List<LeadDetail> leadDetailList=new ArrayList<LeadDetail>();
		LeadDetails leadDetails=new LeadDetails();
		LeadOrderStatistics leadOrderStatistics=new LeadOrderStatistics();
		
		if(null !=soDashboardVO.getStatusNew()){
			leadDetail=new LeadDetail();
			leadDetail.setLeadOrderStatus(MPConstants.LEAD_STATUS_NEW);
			leadDetail.setLeadOrderCount(soDashboardVO.getStatusNew());
			leadDetailList.add(leadDetail);
		}
		if(null !=soDashboardVO.getWorking()){
			leadDetail=new LeadDetail();
			leadDetail.setLeadOrderStatus(MPConstants.LEAD_STATUS_WORKING);
			leadDetail.setLeadOrderCount(soDashboardVO.getWorking());
			leadDetailList.add(leadDetail);
		}
		if(null !=soDashboardVO.getScheduled()){
			leadDetail=new LeadDetail();
			leadDetail.setLeadOrderStatus(MPConstants.LEAD_STATUS_SCHEDULED);
			leadDetail.setLeadOrderCount(soDashboardVO.getScheduled());
			leadDetailList.add(leadDetail);
		}
		if(null !=soDashboardVO.getCompleted()){
			leadDetail=new LeadDetail();
			leadDetail.setLeadOrderStatus(MPConstants.LEAD_STATUS_COMPLETED);
			leadDetail.setLeadOrderCount(soDashboardVO.getCompleted());
			leadDetailList.add(leadDetail);
		}
		if(null !=soDashboardVO.getCancelled()){
			leadDetail=new LeadDetail();
			leadDetail.setLeadOrderStatus(MPConstants.LEAD_STATUS_CANCELLED);
			leadDetail.setLeadOrderCount(soDashboardVO.getCancelled());
			leadDetailList.add(leadDetail);
		}
		if(null !=soDashboardVO.getStale()){
			leadDetail=new LeadDetail();
			leadDetail.setLeadOrderStatus(MPConstants.LEAD_STATUS_STALE);
			leadDetail.setLeadOrderCount(soDashboardVO.getStale());
			leadDetailList.add(leadDetail);
		}
		if(leadDetailList.size() > 0){
			leadDetails.setLeadDetail(leadDetailList);
			leadOrderStatistics.setLeadDetails(leadDetails);
			dashboardResponse.setLeadOrderStatistics(leadOrderStatistics);
		}
		return dashboardResponse;
	}
	
	/**
	 * @Description: Method to map the available balance
	 * @param dashboardResponse
	 * @param soDashboardVO
	 * @return
	 */
	private ViewAdvancedDashboardResponse mapAvailableBalance(ViewAdvancedDashboardResponse dashboardResponse, SODashboardVO soDashboardVO) {
		if(soDashboardVO.getAvailableBalance() > MPConstants.DOUBLE_ZERO){
			dashboardResponse.setAvailableWalletBalance(java.text.NumberFormat.getCurrencyInstance().format(soDashboardVO.getAvailableBalance()));
		}
		else{
			dashboardResponse.setAvailableWalletBalance(java.text.NumberFormat.getCurrencyInstance().format(MPConstants.DOUBLE_ZERO));
		}
		return dashboardResponse;
	}

	/**
	 * @Description: Method to map the ratings details
	 * @param dashboardResponse
	 * @param soDashboardVO
	 * @return
	 */
	private ViewAdvancedDashboardResponse mapPerformanceStatistics(ViewAdvancedDashboardResponse dashboardResponse, SODashboardVO soDashboardVO) {
		PerformanceStatistics performanceStatistics=new PerformanceStatistics();
		
		performanceStatistics.setNumberOfRatingsReceived(soDashboardVO.getNumRatingsReceived());
		if(null != soDashboardVO.getLifetimeRating()){
			performanceStatistics.setLifetimeRating(soDashboardVO.getLifetimeRating());
		}
		else{
			performanceStatistics.setLifetimeRating(MPConstants.DOUBLE_ZERO);
		}
		if(null !=  soDashboardVO.getCurrentRating()){
			performanceStatistics.setCurrentRating(soDashboardVO.getCurrentRating());
		}
		else{
			performanceStatistics.setCurrentRating(MPConstants.DOUBLE_ZERO);
		}
		dashboardResponse.setPerformanceStatistics(performanceStatistics);
		return dashboardResponse;
	}
	
	/**
	 * @Description: Method to map the firm registration status ,count of approved and unapproved firms
	 * @param dashboardResponse
	 * @param soDashboardVO
	 * @return
	 */
	private ViewAdvancedDashboardResponse mapServiceLiveStatusMonitor(ViewAdvancedDashboardResponse dashboardResponse, SODashboardVO soDashboardVO) {
		ServiceLiveStatusMonitor serviceLiveStatusMonitor=new ServiceLiveStatusMonitor();
		
		//Mapping firm status
		serviceLiveStatusMonitor.setFirmRegistrationStatus(soDashboardVO.getFirmStatus());
		//Mapping provider registration status
		mapProviderRegistrationStatus(soDashboardVO,serviceLiveStatusMonitor);
		//Mapping provider Background check
		mapProviderBackgroundCheck(soDashboardVO,serviceLiveStatusMonitor);
		
		dashboardResponse.setServiceLiveStatusMonitor(serviceLiveStatusMonitor);
		return dashboardResponse;
	}

	/**
	 * @Description: Method to map the background details of the provider
	 * @param soDashboardVO
	 * @param serviceLiveStatusMonitor
	 */
	private void mapProviderBackgroundCheck(SODashboardVO soDashboardVO,ServiceLiveStatusMonitor serviceLiveStatusMonitor) {
		ProviderBackgroundDetail providerBackgroundDetail=null;
		List<ProviderBackgroundDetail> providerBackgroundDetailList=new ArrayList<ProviderBackgroundDetail>();
		ProviderBackgroundDetails providerBackgroundDetails=new ProviderBackgroundDetails();
		ProviderBackgroundCheck providerBackgroundCheck=new ProviderBackgroundCheck();
		
		//Not Started status
		if(null !=soDashboardVO.getBcNotStarted()){
			providerBackgroundDetail=new ProviderBackgroundDetail();
			providerBackgroundDetail.setBackgroundStatus(MPConstants.SL_STATUS_NOT_STARTED);
			providerBackgroundDetail.setBackgroundStatusCount(soDashboardVO.getBcNotStarted());
			providerBackgroundDetailList.add(providerBackgroundDetail);
		}
		//Pending Submission status
		if(null !=soDashboardVO.getBcPendingSubmission()){
			providerBackgroundDetail=new ProviderBackgroundDetail();
			providerBackgroundDetail.setBackgroundStatus(MPConstants.SL_STATUS_PENDING_SUBMISSION);
			providerBackgroundDetail.setBackgroundStatusCount(soDashboardVO.getBcPendingSubmission());
			providerBackgroundDetailList.add(providerBackgroundDetail);
				}
		//In Process status
		if(null !=soDashboardVO.getBcInProcess()){
			providerBackgroundDetail=new ProviderBackgroundDetail();
			providerBackgroundDetail.setBackgroundStatus(MPConstants.SL_STATUS_IN_PROGRESS);
			providerBackgroundDetail.setBackgroundStatusCount(soDashboardVO.getBcInProcess());
			providerBackgroundDetailList.add(providerBackgroundDetail);
		}
		//Not Cleared status
		if(null !=soDashboardVO.getBcNotCleared()){
			providerBackgroundDetail=new ProviderBackgroundDetail();
			providerBackgroundDetail.setBackgroundStatus(MPConstants.SL_STATUS_NOT_CLEARED);
			providerBackgroundDetail.setBackgroundStatusCount(soDashboardVO.getBcNotCleared());
			providerBackgroundDetailList.add(providerBackgroundDetail);
		}
		//Clear status
		if(null !=soDashboardVO.getBcClear()){
			providerBackgroundDetail=new ProviderBackgroundDetail();
			providerBackgroundDetail.setBackgroundStatus(MPConstants.SL_STATUS_CLEAR);
			providerBackgroundDetail.setBackgroundStatusCount(soDashboardVO.getBcClear());
			providerBackgroundDetailList.add(providerBackgroundDetail);
		}
		//Re-certification Due status
		if(null !=soDashboardVO.getBcRecertificationDue()){
			providerBackgroundDetail=new ProviderBackgroundDetail();
			providerBackgroundDetail.setBackgroundStatus(MPConstants.SL_STATUS_RE_CERTIFICATION_DUE);
			providerBackgroundDetail.setBackgroundStatusCount(soDashboardVO.getBcRecertificationDue());
			providerBackgroundDetailList.add(providerBackgroundDetail);
		}
		
		providerBackgroundDetails.setProviderBackgroundDetail(providerBackgroundDetailList);
		providerBackgroundCheck.setProviderBackgroundDetails(providerBackgroundDetails);
		serviceLiveStatusMonitor.setProviderBackgroundCheck(providerBackgroundCheck);
	}

	/**
	 * @Description: Method to map the provider registration details
	 * @param soDashboardVO
	 * @param serviceLiveStatusMonitor
	 */
	private void mapProviderRegistrationStatus(SODashboardVO soDashboardVO,ServiceLiveStatusMonitor serviceLiveStatusMonitor) {
		ProviderRegistrationStatus providerRegistrationStatus=new ProviderRegistrationStatus();
		ProviderRegistrationDetails providerRegistrationDetails=new ProviderRegistrationDetails();
		List<ProviderRegistrationDetail> providerRegistrationDetailList=new ArrayList<ProviderRegistrationDetail>();
		ProviderRegistrationDetail providerRegistrationDetail=null;
		
		//Approved Provider count
		if(null !=soDashboardVO.getNumTechniciansApproved()){
			providerRegistrationDetail=new ProviderRegistrationDetail();
			providerRegistrationDetail.setRegistrationStatus(MPConstants.PROVIDER_REGISTRATION_STATUS_APPROVED);
			providerRegistrationDetail.setRegistrationStatusCount(soDashboardVO.getNumTechniciansApproved());
			providerRegistrationDetailList.add(providerRegistrationDetail);
		}
		//Unapproved Provider count
		if(null !=soDashboardVO.getNumTechniciansUnapproved()){
			providerRegistrationDetail=new ProviderRegistrationDetail();
			providerRegistrationDetail.setRegistrationStatus(MPConstants.PROVIDER_REGISTRATION_STATUS_UNAPPROVED);
			providerRegistrationDetail.setRegistrationStatusCount(soDashboardVO.getNumTechniciansUnapproved());
			providerRegistrationDetailList.add(providerRegistrationDetail);
		}
			providerRegistrationDetails.setProviderRegistrationDetail(providerRegistrationDetailList);
			providerRegistrationStatus.setProviderRegistrationDetails(providerRegistrationDetails);
			serviceLiveStatusMonitor.setProviderRegistrationStatus(providerRegistrationStatus);	
	}
	
	/**
	 * @Description: Method to map the spn monitor details.A map with {key,value} pair {buyerId|buyerName,List<spnDetail>} buyerSpnDetailMap is created.
	 * Then the map is iterated and set the value to the response object
	 * Map is used here because the spn monitor displays in the format buyer along with its SPN details.
	 * @param dashboardResponse
	 * @param dashboardVOForSpnMonitor
	 * @return
	 */
	private ViewAdvancedDashboardResponse mapSpnMonitorDetails(ViewAdvancedDashboardResponse dashboardResponse,List<SPNMonitorVO> SPNMonitorVOList) {
		SpnMonitor spnMonitor=new SpnMonitor();
		SpnBuyerDetailsList spnBuyerDetailsList=new SpnBuyerDetailsList();
		List<SpnBuyerDetail> spnBuyerDetailList=new ArrayList<SpnBuyerDetail>();
		List<SpnDetail> spnDetailList= null;
		HashMap<String,List<SpnDetail>> buyerSpnDetailMap=new HashMap<String, List<SpnDetail>>();
		String key=null;
		//buyerSpnDetailMap is populated by setting buyerId+buyerName as key and the spnDetailList contains the details of spn.
		if(null !=SPNMonitorVOList && ! SPNMonitorVOList.isEmpty()){
			for(SPNMonitorVO spnMonitorVO:SPNMonitorVOList){
				key=new Integer(spnMonitorVO.getBuyerId()).toString()+MPConstants.PIPE+spnMonitorVO.getBuyerName();
				SpnDetail spnDetail = new SpnDetail();
				spnDetail.setSpnId(spnMonitorVO.getSpnId());
				spnDetail.setSpnName(spnMonitorVO.getSpnName());
				spnDetail.setSpnMembershipStatus(spnMonitorVO.getMembershipStatus());
				if(!buyerSpnDetailMap.containsKey(key)){
					spnDetailList = new ArrayList<SpnDetail>();
					spnDetailList.add(spnDetail);
				}
				else{
					spnDetailList = buyerSpnDetailMap.get(key);
					spnDetailList.add(spnDetail);
				}
				buyerSpnDetailMap.put(key, spnDetailList);
			}
		}
		//From the map populated above,the key will be split into buyerId and buyerName to set these details into the response object.
		for(Entry<String,List<SpnDetail>> buyerSpnDetail: buyerSpnDetailMap.entrySet()){
			Integer buyerId=0;
			String buyerSpnDetailKey = buyerSpnDetail.getKey();
			String array[] = buyerSpnDetailKey.split(MPConstants.SPLIT_PIPE);
			try{
				buyerId = Integer.parseInt(array[0]);
			}catch(NumberFormatException ex){
				LOGGER.error("Invalid BuyerId"+buyerId);
			}
			
			String buyerName = array[1];
			SpnBuyerDetail spnBuyerDetail=new SpnBuyerDetail();
			
			spnBuyerDetail.setBuyerId(buyerId);
			spnBuyerDetail.setBuyerName(buyerName);
			
			List<SpnDetail> spnDetailsList = buyerSpnDetail.getValue();
			
			SpnDetails spnDetails = new SpnDetails();
			spnDetails.setSpnDetail(spnDetailsList);
			spnBuyerDetail.setSpnDetails(spnDetails);
			
			spnBuyerDetailList.add(spnBuyerDetail);
		}
		spnBuyerDetailsList.setSpnBuyerDetail(spnBuyerDetailList);
		spnMonitor.setSpnBuyerDetailsList(spnBuyerDetailsList);
		dashboardResponse.setSpnMonitor(spnMonitor);
		
		return dashboardResponse;
	}
	
	/**
	 * @param tabNamesAdded
	 * @param tabList
	 * @param roleId 
	 * @return
	 * map zero count for tabs 
	 */
	private List<Tab> mapZeroTabCountswithBid(List<String> tabNamesAdded,
			List<Tab> tabList, Integer roleId) {

		if(!tabNamesAdded.containsAll(MPConstants.LEVEL_NOT_ONE_TAB_LIST) && !roleId.equals(MPConstants.ROLE_LEVEL_ONE)){
			if(!tabNamesAdded.contains(MPConstants.RECIEVED_TAB)){
				mapZeroCount(tabList,MPConstants.RECIEVED_TAB);
			}
			if(!tabNamesAdded.contains(MPConstants.BID_REQUESTS)){
				mapZeroCount(tabList,MPConstants.BID_REQUESTS);
			}
			mapNonRecievedTabs(tabNamesAdded,tabList);
		}
		else if(!tabNamesAdded.containsAll(MPConstants.LEVEL_ONE_TAB_LIST) && roleId.equals(MPConstants.ROLE_LEVEL_ONE)){
			mapNonRecievedTabs(tabNamesAdded,tabList);
		}
		return tabList;
	}
	

	public INewMobileGenericBO getNewMobileGenericBO() {
		return newMobileGenericBO;
	}

	public void setNewMobileGenericBO(INewMobileGenericBO newMobileGenericBO) {
		this.newMobileGenericBO = newMobileGenericBO;
	}
}
