package com.newco.marketplace.business.iBusiness.leadsmanagement;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmIds;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteLeadsRequestVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadAttachmentVO;
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
import com.newco.marketplace.vo.provider.Campaign;
import com.newco.marketplace.vo.provider.Cdr;
import com.newco.marketplace.vo.provider.Employee;
import com.newco.marketplace.vo.provider.Impressions;

public interface ILeadProcessingBO {
	// This method will persist LeadObject information in SL.
	public String saveLeadInfo(FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;

	// This method will persist Lead stats information in SL.
	public void saveLeadStats(LeadStatisticsVO statisticsVO)
			throws BusinessServiceException;

	// This method will persist Lead information along with chosen firms and
	// their cotact info in SL
	public SLLeadVO savePostLeadFirmInfo(LeadRequest leadRequest)
			throws BusinessServiceException;

	// method for getting lead and firm details to send reminder mail to lead
	// owner

	// This method will fetch Firm Details
	public Map getFirmDetails(FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;

	public Map getFirmDetailsPost(LeadRequest leadRequest)
			throws BusinessServiceException;

	// Method to save the response from boberDoo
	public void saveResponseInfo(List<LeadPostedProVO> matchProviders,
			LeadStatisticsVO statisticsVO, SLLeadVO leadVO)
			throws BusinessServiceException;

	// Validating primary project
	public String validateLaunchZip(String zip) throws BusinessServiceException;

	// Validate lead id in post lead
	public String validateLeadId(String leadId) throws BusinessServiceException;

	// Validating primary project
	public FetchProviderFirmRequest validatePrimaryProject(
			String validatePrimaryProject,
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;

	// Saving matched provides info
	public void saveMatchedProvidersInfo(
			List<LeadMatchingProVO> matchedProviders,
			LeadStatisticsVO statisticsVO, LeadRequest leadRequest)
			throws BusinessServiceException;

	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			LeadRequest leadRequest) throws BusinessServiceException;

	// For fetching the insurance and policy details for each firms
	public Map getCompanyProfileDetails(List<Integer> firmIdList)
			throws BusinessServiceException;

	// For getting the credential details of firm
	public Map getFirmCredentials(List<String> firmIdList)
			throws BusinessServiceException;

	// For fetching firm details for getProviderFirmrequest
	public Map getProviderFirmDetails(List<Integer> firmIdList)
			throws BusinessServiceException;

	// for fetching services offered by firm
	public Map getFirmServices(List<String> firmIdList)
			throws BusinessServiceException;

	// for fetching lead details for firm
	public List<LeadInfoVO> getAllLeadDetails(String firmId, String status,
			String count, String staleAfter) throws BusinessServiceException;

	// for fetching the details of given lead
	public LeadInfoVO getIndividualLeadDetails(Map<String, String> reqMap)
			throws BusinessServiceException;

	// for validating firmId
	public Boolean validateFirmId(String firmId)
			throws BusinessServiceException;

	// for validating slLeadId
	public Boolean validateSlLeadId(String slLeadId)
			throws BusinessServiceException;

	// Fetching available spn for Firms
	public Map getAvailableSpnForFirms(List<Integer> firmIdList)
			throws BusinessServiceException;

	// for validating firmid for the given lead
	public Boolean validateFirmForLead(Map<String, String> reqMap)
			throws BusinessServiceException;

	// Getting weigthtages for ranking criteria
	public Map getRankWeigthtages() throws BusinessServiceException;

	// For Getting spn id for leadCategory
	public Integer getSpnId(String electricalLeadCategory)
			throws BusinessServiceException;

	// For getting completesPerWeek
	public Map getCompletesPerWeek(List<Integer> firmIdList)
			throws BusinessServiceException;

	// fetching leadstatus and status id
	public UpdateLeadStatusRequestVO leadStatusValidate(String leadStatus)
			throws BusinessServiceException;

	// fetching firmstatus and status id
	public UpdateLeadStatusRequestVO validateFirmStatus(String firmStatus)
			throws BusinessServiceException;

	// updating firmstatus
	public void updateMatchedFirmStatus(UpdateLeadStatusRequestVO requestVO)
			throws BusinessServiceException;

	// updating lead status
	public void updateLeadStatus(UpdateLeadStatusRequestVO requestVO)
			throws BusinessServiceException;

	// method for getting lead and firm details to send reminder mail to lead
	// owner
	public List<LeadReminderVO> getDetailsForRemindMail(String serviceDate)
			throws BusinessServiceException;

	// Validate resource id in assignOrReassignProvider API
	public String validateResourceId(Map<String, String> validateMap)
			throws BusinessServiceException;

	// Validate firm id in assignOrReassignProvider API
	public String toValidateFirmId(String firmId)
			throws BusinessServiceException;

	// updating provider
	public void updateProvider(
			AssignOrReassignProviderRequest assignOrReassignProviderRequest)
			throws BusinessServiceException;

	// To get the resourceId
	public String checkProvider(Map<String, String> checkMap)
			throws BusinessServiceException;

	// To validate FirmFOrLead
	public String toValidateFirmIdForLead(Map<String, String> firmLeadMap)
			throws BusinessServiceException;

	// To make entry in alert task to send mail
	public void sendToDestination(AlertTask alertTask)
			throws BusinessServiceException;

	// To convert map data to string
	public String createKeyValueStringFromMap(Map<String, Object> alertMap);

	// validate note category
	public Boolean validateNoteCategory(String noteCategory)
			throws BusinessServiceException;

	// save lead notes
	public Integer saveLeadNotes(SLLeadNotesVO leadNotesVO)
			throws BusinessServiceException;

	// getting eligible providers for the lead belongs to the firm
	public List<ProviderDetailsVO> getEligibleProviders(String firmId,
			String leadId) throws BusinessServiceException;;

	public void sendConfirmationMailforAddNotes(
			LeadAddNoteRequest leadAddNoteRequest, String emailToAddresses)
			throws BusinessServiceException;

	public List<String> getProviderEmailIdsForLead(String leadId)
			throws BusinessServiceException;

	public boolean validateBuyerForLead(Map<String, String> reqMap)
			throws BusinessServiceException;

	// to update resourceId
	public void updateResourceId(CompleteLeadsRequestVO completeLeadsVO)
			throws BusinessServiceException;

	// to update complete lead details
	public void updateLeadDetails(CompleteLeadsRequestVO completeLeadsVO)
			throws BusinessServiceException;

	// to update complete firm details
	public void updateMatchedFirmDetails(CompleteLeadsRequestVO completeLeadsVO)
			throws BusinessServiceException;

	// To validate leadId and its status
	public String validateSlLeadIdAndStatus(String slLeadId)
			throws BusinessServiceException;

	// To fetch Current lead firm Status
	public String validateLeadFirmStatus(Map<String, String> reqMap)
			throws BusinessServiceException;

	// To fetch all leadFirm Status from lu table
	public List<String> getAllleadFirmStatus() throws BusinessServiceException;

	/**
	 * Description: This method checks whether the given firm id is associated
	 * with given lead id
	 * 
	 * @param ScheduleAppointmentRequest
	 * @throws BusinessServiceException
	 */
	public ScheduleRequestVO validateLeadAssosiationWithFirm(
			ScheduleAppointmentRequest scheduleAppointmentRequest)
			throws BusinessServiceException;

	public List<SLLeadNotesVO> getNoteList(String LeadId)
			throws BusinessServiceException;

	// to get lead history details
	public List<LeadHistoryVO> getHistoryList(String LeadId)
			throws BusinessServiceException;

	// to get lead Attachment details
	public List<LeadAttachmentVO> getAttachmentsList(String LeadId)
			throws BusinessServiceException;

	// To get mail details
	public ScheduleAppointmentMailVO getScheduleMailDeatils(
			ScheduleRequestVO scheduleVO) throws BusinessServiceException;

	// To send rescheduleMail
	public void sendRescheduleMailToCustomer(
			ScheduleAppointmentRequest scheduleAppointmentRequest,
			ScheduleAppointmentMailVO scheduleVO)
			throws BusinessServiceException;

	/**
	 * Description: This method gets all the details related to lead
	 * 
	 * @param SLLeadVO
	 * @throws BusinessServiceException
	 */
	public SLLeadVO getLead(String slLeadId) throws BusinessServiceException;

	/**
	 * Description: This method will insert details into lead history
	 * 
	 * @param LeadLoggingVO
	 * @throws BusinessServiceException
	 */
	public void insertLeadLogging(LeadLoggingVO leadLoggingVO)
			throws BusinessServiceException;

	/**
	 * Description: This method will get user name of firm
	 * 
	 * @param String
	 * @throws BusinessServiceException
	 */
	public String getUserName(String firmId) throws BusinessServiceException;

	/**
	 * Description: This method will get resource name
	 * 
	 * @param String
	 * @throws BusinessServiceException
	 */
	public String getResourceName(String resourceId)
			throws BusinessServiceException;

	/**
	 * @param leadNotesVO
	 * 
	 * update lead notes
	 */
	public void updateLeadNotes(SLLeadNotesVO leadNotesVO)
			throws BusinessServiceException;

	/**
	 * @param noteId
	 * @param leadId
	 * @param roles
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Boolean validateLeadNoteIdForLeadId(Integer noteId, String leadId,
			Integer roles, Integer firmId) throws BusinessServiceException;

	// to fetch assigned resource ratings
	public Double getProviderRating(Integer resourceAssigned);

	/**
	 * @param leadVO
	 * @throws BusinessServiceException
	 */
	public void updateLmsLeadIdAndWfStatus(SLLeadVO leadVO)
			throws BusinessServiceException;

	/**
	 * @param FetchProviderFirmRequest
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String saveLeadInfoforNonLaunchMarket(
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;

	/**
	 * @param resourceId
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO getProviderImageDocument(String resourceId)
			throws BusinessServiceException;

	public String getFormattedTime(String time);

	public String getFormattedDateAsString(String date);

	/**
	 * @param mailMap
	 * @return CompleteLeadsRequestVO
	 * @throws BusinessServiceException
	 */
	public CompleteLeadsRequestVO getCompleteMailDetails(
			Map<String, String> mailMap) throws BusinessServiceException;

	/**
	 * @param contactId
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String getBuyerName(Integer buyerId) throws BusinessServiceException;

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
	public List<FirmDetails> filterFirmsWithLeadProfileAndPrice(
			List<FirmDetails> firmDetailsList,
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;
	
	/* Added for SL-19727 */
	/**
	 * Description: This method gets all the details related to lead customer
	 * 
	 * @param LeadCustomerDetailsVO
	 * @throws BusinessServiceException
	 */
	public LeadCustomerDetailsVO getLeadCustomerDetails(String slLeadId) throws BusinessServiceException;
	public List<FirmDetailsVO> getLeadFirmDetails(String slLeadId) throws BusinessServiceException;
	
	/************************************************B2C*****************************************/
	public SLLeadVO savePostLeadFirmInfo(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
			throws BusinessServiceException;

	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) throws BusinessServiceException;

	public Map getFirmDetailsPost(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
			throws BusinessServiceException;
	
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest validatePrimaryProject(
			String primaryProject,
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
					throws BusinessServiceException;
	public String saveLeadInfoforNonLaunchMarket(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest, String requestXML)
					throws BusinessServiceException;
	// This method will fetch Firm Details
	public Map getFirmDetails(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;
	
	public List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> filterFirmsWithLeadProfileAndPrice(
			List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmDetailsList,
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException;
	public boolean isLeadNonLaunchSwitch() throws BusinessServiceException;
	public String validateAllZip(String custZip) throws BusinessServiceException;
	public List<Integer> getFirmIds(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest) throws BusinessServiceException;
	public Map<String, String> validateLeadPostFirmIds(String slLeadId, FirmIds firmIds) throws BusinessServiceException;
	public String getPropertyValue(String key) throws BusinessServiceException;
	
	public void saveMatchedProvidersInfo(
			List<LeadMatchingProVO> matchedProviders, com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
			throws BusinessServiceException;
	
	public void updateMembershipInfo(
			UpdateMembershipInfoRequest updateMembershipInfoRequest) throws BusinessServiceException;
	
	public SLLeadVO savePostLeadFirmInfo(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest, String requestXml)
			throws BusinessServiceException;
	
	public void sendConfirmationMailToCustomer(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmdetailsList, String leadStatus);
	
	public void sendConfirmationMailToProvider(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmdetailsList, String leadStatus);
	public void saveResponseInfoV2(List<LeadPostedProVO> matchProviders,LeadStatisticsVO statisticsVO, SLLeadVO leadVO) throws BusinessServiceException;
	public String validateSlleadForPost(String leadId)throws BusinessServiceException;
	//SL-20893 Send customer mail for unmatched and out of area leads
	public void sendConfirmationMailToCustomerForUnmatchedAndOutOfArea(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest);
	
	public void saveEmployeesList(List<Employee> empList) throws BusinessServiceException;
	
	public void saveCampaignList(List<Campaign> campList) throws BusinessServiceException;
	
	public void saveCdrList(List<Cdr> cdrList) throws BusinessServiceException;
	
	public void saveImpressionsList(List<Impressions> impsList) throws BusinessServiceException;
	
	public Integer getLatestId(Integer type) throws BusinessServiceException;
	
	public void truncateData(Integer type) throws BusinessServiceException;

}
