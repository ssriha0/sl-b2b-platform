package com.newco.marketplace.persistence.iDao.leadsmanagement;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteLeadsRequestVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmCredentialVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadContactInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadCustomerDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadMatchingProVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadPostedProVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadReminderVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadStatisticsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleAppointmentMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleRequestVO;
import com.newco.marketplace.dto.vo.leadsmanagement.UpdateLeadStatusRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.Campaign;
import com.newco.marketplace.vo.provider.Cdr;
import com.newco.marketplace.vo.provider.Employee;
import com.newco.marketplace.vo.provider.Impressions;

public interface FetchProviderDao {

	// Saving lead Object into servicelive
	public void saveLeadInfo(SLLeadVO leadInfoVO) throws DataServiceException;

	// Updating leadInfo and inserting leadContact Info
	public SLLeadVO savePostLeadInfo(SLLeadVO leadInfoVO,
			LeadContactInfoVO contactInfoVO, LeadStatisticsVO statisticsVO)
			throws DataServiceException;

	public List<FirmDetailsVO> getFirmDetails(
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException;

	public void saveFirmResponse(List<LeadPostedProVO> matchProviders,
			LeadStatisticsVO statisticsVO, SLLeadVO leadVO)
			throws DataServiceException;

	public String validateLaunchZip(String zip) throws DataServiceException;

	public FetchProviderFirmRequest validatePrimaryProject(String project,
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException;

	// Inserting selected providers info
	public void saveMatchedProviders(List<LeadMatchingProVO> matchedProviders,
			LeadStatisticsVO statisticsVO) throws DataServiceException;

	// method for getting lead and firm details to send reminder mail to lead
	// owner
	public List<String> getAllProvidersPosted(String leadId)
			throws DataServiceException;

	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			LeadRequest leadRequest) throws DataServiceException;

	// For fetching the Credential details of firm
	public List<FirmCredentialVO> getFirmCredentials(List<String> firmIds)
			throws DataServiceException;

	// For fetching provider firm details
	public List<FirmDetailsVO> getProviderFirmDetails(List<Integer> firmIdList)
			throws DataServiceException;

	public List<FirmDetailsVO> getFirmDetailsPost(LeadRequest leadRequest)
			throws DataServiceException;

	// for getting the Services of firm
	public List<FirmServiceVO> getFirmServices(List<String> firmIds)
			throws DataServiceException;

	// for getting lead details of a firm
	public List<LeadInfoVO> getAllLeadDetails(String firmId, String status,
			String count, String staleAfter) throws DataServiceException;

	public void saveLeadStats(LeadStatisticsVO statisticsVO)
			throws DataServiceException;

	public String validateLeadId(String leadId) throws DataServiceException;

	public void updateMembershipInfo(
			UpdateMembershipInfoRequest updateMembershipInfoRequest);

	// Getting spnId available for firms.
	public List<FirmDetailsVO> getSpnMapForFirms(List<Integer> firmIdList)
			throws DataServiceException;

	// for fetching the details of given lead
	public LeadInfoVO getIndividualLeadDetails(Map<String, String> reqMap)
			throws DataServiceException;

	// for validating firmId
	public String validateFirmId(String firmId) throws DataServiceException;

	// for validating slLeadId
	public String validateSlLeadId(String slLeadId) throws DataServiceException;

	// for validating firmid for given lead
	public LeadInfoVO validateFirmForLead(Map<String, String> reqMap)
			throws DataServiceException;

	// For getting ranking criteria weigthtage
	public Map getRankCriteriaWeightage() throws DataServiceException;

	// fetching leadstatus and status id
	public UpdateLeadStatusRequestVO validateLeadStatus(String leadStatus)
			throws DataServiceException;

	// fetching firmstatus and status id
	public UpdateLeadStatusRequestVO validateFirmStatus(String firmStatus)
			throws DataServiceException;

	// updating firmstatus
	public void updateMatchedFirmStatus(UpdateLeadStatusRequestVO requestVO)
			throws DataServiceException;

	// updating lead status
	public void updateLeadStatus(UpdateLeadStatusRequestVO requestVO)
			throws DataServiceException;

	// For getting CompletesPerWeek
	public List<CompleteVO> getCompletesPerWeek(List<Integer> firmIdList)
			throws DataServiceException;

	// updating schedule information
	public void updateScheduleInfo(ScheduleRequestVO scheduleRequestVO);

	// inserting schedule history
	public void insertScheduleInfoHistory(ScheduleRequestVO scheduleRequestVO);

	// method for getting lead and firm details to send reminder mail to lead
	// owner
	public List<LeadReminderVO> getDetailsForRemindMail(String serviceDate)
			throws DataServiceException;

	public String validateLeadNoteCategoryId(String leadNoteCategoryId)
			throws DataServiceException;

	public Integer saveLeadNotes(SLLeadNotesVO leadNotesVO)
			throws DataServiceException;

	// For validating ResourceId for the firmId
	public String validateResourceId(Map<String, String> validateMap)
			throws DataServiceException;

	// For validating firmId
	public String toValidateFirmId(String firmId) throws DataServiceException;

	// For updating provider
	public void updateProvider(
			AssignOrReassignProviderRequest assignOrReassignProviderRequest)
			throws DataServiceException;

	// For Fetching ResourceId
	public String checkProvider(Map<String, String> checkMap)
			throws DataServiceException;

	// For Fetching firmId
	public String toValidateFirmId(Map<String, String> firmLeadMap)
			throws DataServiceException;

	public List<String> getProviderEmailIdsForLead(String leadId)
			throws DataServiceException;

	// For Fetching spnId,Zip,slNodeID mapped to the leadId,
	public SLLeadVO getLeadZipAndSpnForLead(String leadId)
			throws DataServiceException;;

	public String getSupportMailId() throws DataServiceException;

	public Integer validateBuyerForLead(Map<String, String> reqMap)
			throws DataServiceException;

	public void updateResourceId(CompleteLeadsRequestVO completeLeadsVO)
			throws DataServiceException;

	// to update complete lead details
	public void updateLeadDetails(CompleteLeadsRequestVO completeLeadsVOt)
			throws DataServiceException;

	// to update complete firm details
	public void updateMatchedFirmDetails(CompleteLeadsRequestVO completeLeadsVOt)
			throws DataServiceException;

	// Fetching eligible providers
	public List<ProviderDetailsVO> getEligibleProvidersForLead(
			LocationVO locationVO) throws DataServiceException;

	public List<SLLeadNotesVO> getNoteList(String LeadId)
			throws DataServiceException;

	// to get lead history details
	public List<LeadHistoryVO> getHistoryList(String LeadId)
			throws DataServiceException;

	// To get lead documents details
	public List<LeadAttachmentVO> getAttachmentsList(String LeadId)
			throws DataServiceException;

	// Fetching leadStatus for the given lead id
	public String getLeadStatusToUpdate(String slLeadId)
			throws DataServiceException;

	// Fetching lead Firmstatus for the lead
	public String getLeadFirmStatus(Map<String, String> reqMap)
			throws DataServiceException;

	// Fetching mail details for scheduleAppointment
	public ScheduleAppointmentMailVO getScheduleMailDeatils(
			ScheduleRequestVO scheduleVO) throws DataServiceException;

	// Fetching all available lead Firm status
	public List<String> getAllLeadFirmStatus() throws DataServiceException;

	/**
	 * Description: This method checks whether the given firm id is associated
	 * with given lead id
	 * 
	 * @param ScheduleAppointmentRequest
	 * @throws DataServiceException
	 */
	public ScheduleRequestVO validateLeadAssosiationWithFirm(
			ScheduleAppointmentRequest scheduleAppointmentRequest)
			throws DataServiceException;

	/**
	 * Description: This method gets all the details related to lead
	 * 
	 * @param SLLeadVO
	 * @throws DataServiceException
	 */
	public SLLeadVO getLead(String slLeadId) throws DataServiceException;

	public boolean getResourceIdForFirm(Map<String, String> firmLeadMap)
			throws DataServiceException;

	/**
	 * Description: This method will insert into lead history
	 * 
	 * @param LeadLoggingVO
	 * @throws DataServiceException
	 */
	public void insertLeadLogging(LeadLoggingVO leadLoggingVO)
			throws DataServiceException;

	/**
	 * Description: This method will get user name of firm
	 * 
	 * @param String
	 * @throws DataServiceException
	 */
	public String getUserName(String firmId) throws DataServiceException;

	/**
	 * Description: This method will get resource name
	 * 
	 * @param String
	 * @throws DataServiceException
	 */
	public String getResourceName(String resourceId)
			throws DataServiceException;

	/**
	 * @param leadNotesVO
	 * @throws DataServiceException
	 * 
	 * update notes
	 */
	public void updateLeadNotes(SLLeadNotesVO leadNotesVO)
			throws DataServiceException;

	public Integer validateLeadNoteIdForLeadId(Integer noteId, String leadId,
			Integer roles, Integer firmId) throws DataServiceException;

	/**
	 * @param resourceAssigned
	 * @return
	 * 
	 * get assigned resource rating
	 */
	public Double getProviderRating(Integer resourceAssigned);

	/**
	 * @param leadVO
	 * @throws DataServiceException
	 */
	public void updateLmsLeadIdAndWfStatus(SLLeadVO leadVO)
			throws DataServiceException;

	/**
	 * @param leadId
	 * @return
	 * @throws DataServiceException
	 */
	public String getSlLeadForPostLead(String leadId)
			throws DataServiceException;

	/**
	 * @param leadId
	 * @return SLLeadVO
	 * @throws DataServiceException
	 */
	public SLLeadVO getleadDetailsForRewardPonits(String leadId)
			throws DataServiceException;

	/**
	 * @param resourceId
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO getProviderImageDocument(String resourceId)
			throws DataServiceException;

	/**
	 * @param mailMap
	 * @return CompleteLeadsRequestVO
	 * @throws DataServiceException
	 */
	public CompleteLeadsRequestVO getCompleteMailDetails(
			Map<String, String> mailMap) throws DataServiceException;

	/**
	 * @param contactId
	 * @return LeadHistoryVO
	 * @throws DataServiceException
	 */
	public LeadHistoryVO getBuyerName(Integer buyerId)
			throws DataServiceException;

	// To get BusinessName
	public String getBusinessName(Integer vendorId) throws DataServiceException;

	public Map<Integer, Integer> getInactiveVendors()
			throws DataServiceException;

	public void updateVendorStatus() throws DataServiceException;

	public List<Integer> getInactiveVendorList() throws DataServiceException;

	/**
	 * This method will remove the firms from response if they don't have a lead
	 * profile or if they don't have exclusive or competitive price is not
	 * available for the project selected
	 * 
	 * @param List
	 *            <FirmDetails>
	 * @param FetchProviderFirmRequest
	 * @return List<FirmDetails>
	 * @throws BusinessServiceException
	 */
	public List<FirmDetailsVO> filterFirmsWithLeadProfileAndPrice(
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException;

	/* Added for SL-19727 */
	public Integer validateLeadBuyerAssociation(String leadId, Integer buyerId);

	public Integer validateLeadBuyerId(Integer buyerId);
	
	/**
	 * Description: This method gets all the details related to lead
	 * 
	 * @param SLLeadVO
	 * @throws DataServiceException
	 */
	public LeadCustomerDetailsVO getLeadCustomerDetails(String slLeadId) throws DataServiceException;
	public List<FirmDetailsVO> getLeadFirmDetails(String slLeadId) throws DataServiceException;
	
	/************************************************B2C*****************************************/
	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) throws DataServiceException;
	public List<FirmDetailsVO> getFirmDetailsPost(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
			throws DataServiceException;
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest validatePrimaryProject(String project,
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException;
	public List<FirmDetailsVO> getFirmDetails(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException;
	public List<FirmDetailsVO> filterFirmsWithLeadProfileAndPrice(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException;
	public boolean isLeadNonLaunchSwitch() throws DataServiceException;
	public String validateAllZip(String zip) throws DataServiceException;
	public List<Integer> getFirmIds(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest) throws DataServiceException;

	public List<String> validateLeadMatchedFirmIds(String leadId) throws DataServiceException;
	public void saveMatchedProviders(List<LeadMatchingProVO> matchedProviders, String leadId) throws DataServiceException;

	public List<String> validateFirmsIds(List<String> firmId) throws DataServiceException;

	public List<String> validateFirmsIdsPosted(String leadId) throws DataServiceException;
	
	public void saveEmployeesList(List<Employee> empList) throws DataServiceException;
	
	public void saveCampaignList(List<Campaign> campList) throws DataServiceException;
	
	public void saveCdrList(List<Cdr> cdrList) throws DataServiceException;
	
	public void saveImpressionsList(List<Impressions> impsList) throws DataServiceException;
	
	public Integer getLatestId(Integer type) throws DataServiceException;
	
	public void truncateData(Integer type) throws DataServiceException;
}