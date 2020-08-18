
package com.newco.marketplace.persistence.daoImpl.leadsmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteLeadsRequestVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FetchLeadVO;
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
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleAppointmentMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleRequestVO;
import com.newco.marketplace.dto.vo.leadsmanagement.UpdateLeadStatusRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.leadsmanagement.FetchProviderDao;
import com.newco.marketplace.vo.provider.Campaign;
import com.newco.marketplace.vo.provider.Cdr;
import com.newco.marketplace.vo.provider.Employee;
import com.newco.marketplace.vo.provider.Impressions;
import com.sears.os.dao.impl.ABaseImplDao;

public class FetchProviderDaoImpl extends ABaseImplDao implements FetchProviderDao {

	public void saveLeadInfo(SLLeadVO leadInfoVO) throws DataServiceException {
		try {
			insert("leadsmanagement.insertLeadInfo", leadInfoVO);
		} catch (Exception e) {
			logger.error("Exception in saving Lead Information"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public void saveLeadStats(LeadStatisticsVO statisticsVO)
	throws DataServiceException {
		try {
			insert("leadsmanagement.insertLeadStatics", statisticsVO);
		} catch (Exception e) {
			logger.error("Exception in saving Lead statistics "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	// validating ResourceId
	public String validateResourceId(Map<String, String> validateMap)
	throws DataServiceException {
		String resourceId = "";
		try {
			resourceId = (String) queryForObject(
					"leadsmanagement.validateResourceId", validateMap);
		} catch (Exception e) {
			logger.info("Exception in validatingResourceId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resourceId;
	}

	// validating FirmId in vendor_hdr
	public String toValidateFirmId(String firmId) throws DataServiceException {
		String firmID = "";
		try {
			firmID = (String) queryForObject(
					"leadsmanagement.validatingFirmID", firmId);
		} catch (Exception e) {
			logger.info("Exception in validatingFirmId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return firmID;

	}

	// To validate FirmForLead
	public String toValidateFirmId(Map<String, String> firmLeadMap)
	throws DataServiceException {
		String firmId = "";
		try {
			firmId = (String) queryForObject(
					"leadsmanagement.validateFirmLead", firmLeadMap);

		} catch (Exception e) {
			logger.info("Exception in validate firmId " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return firmId;
	}

	// validating slLeadId
	public String validateSlLeadId(String slLeadId) throws DataServiceException {
		String slLeadId1 = "";
		SLLeadVO leadVO = null;
		try {
			leadVO = (SLLeadVO) queryForObject(
					"leadsmanagement.validateSlLeadId", slLeadId);
			if (null != leadVO) {
				slLeadId1 = leadVO.getSlLeadId();
			}

		} catch (Exception e) {
			logger.info("Exception in validateSlLeadId " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return slLeadId1;
	}

	// validating firmId
	public String validateFirmId(String firmId) throws DataServiceException {
		String firmId1 = "";
		try {
			firmId1 = (String) queryForObject("leadsmanagement.validateFirmId",
					firmId);
		} catch (Exception e) {
			logger.info("Exception in validateFirmId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return firmId1;
	}

	// for validating firm for given lead
	public LeadInfoVO validateFirmForLead(Map<String, String> reqMap)
	throws DataServiceException {
		LeadInfoVO leadInfo = null;
		try {
			leadInfo = (LeadInfoVO) queryForObject(
					"leadsmanagement.validateFirmForLead", reqMap);
		} catch (Exception e) {
			logger.info("Exception in validate Firm For Lead ethod"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadInfo;
	}

	public SLLeadVO savePostLeadInfo(SLLeadVO leadInfoVO,
			LeadContactInfoVO contactInfoVO, LeadStatisticsVO statisticsVO)
	throws DataServiceException {
		SLLeadVO leadInfoVOnew = null;
		try {

			if (null != leadInfoVO
					&& StringUtils.isNotBlank(leadInfoVO.getSlLeadId())) {
				// Validating leadId exists in lead_hdr
				leadInfoVOnew = new SLLeadVO();
				leadInfoVOnew = (SLLeadVO) queryForObject(
						"leadsmanagement.validateSlLeadId", leadInfoVO
						.getSlLeadId());
			}
			// inserting customer Information
			if (StringUtils.isNotBlank(leadInfoVO.getSlLeadId())) {
				contactInfoVO.setSlLeadId(leadInfoVO.getSlLeadId());
				leadInfoVO.setSlLeadId(leadInfoVO.getSlLeadId());
				statisticsVO.setSlLeadId(leadInfoVO.getSlLeadId());
				// inserting contact information
				insert("leadsmanagement.insertContactInfo", contactInfoVO);
				// updating leadInformation in lead_hdr if it is in a launch zip
				if (!leadInfoVO.isNonLaunchZip()) {
					update("leadsmanagement.updatePostLeadInfo", leadInfoVO);
				}
				// insert into lead_statistics table
				insert("leadsmanagement.insertLeadStatics", statisticsVO);
			}

		} catch (Exception e) {
			logger.info("Exception in saving PostLead Information"
					+ e.getMessage());
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
		return leadInfoVOnew;

	}

	// getting credential details from
	public List<FirmCredentialVO> getFirmCredentials(List<String> firmIds)
	throws DataServiceException {
		List<FirmCredentialVO> credentialList = null;
		try {
			credentialList = queryForList("leadsmanagement.getCredentials",
					firmIds);
		} catch (Exception e) {
			logger.info("Exception in getting firm credentials"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return credentialList;
	}

	/**
	 * Get the lead details for the firm
	 */
	public List<LeadInfoVO> getAllLeadDetails(String firmId, String status,
			String count, String staleAfter) throws DataServiceException {
		List<LeadInfoVO> leadInfoList = null;

		FetchLeadVO fetchLeadVO = new FetchLeadVO();
		fetchLeadVO.setFirmId(firmId);
		fetchLeadVO.setStatus(status);
		if (StringUtils.isNotBlank(count)) {
			fetchLeadVO.setCount(new Integer(count));
		} else {
			fetchLeadVO.setCount(null);
		}
		fetchLeadVO.setStaleAfter(staleAfter);
		try {
			leadInfoList = queryForList("leadsmanagement.getAllLeadDetails",
					fetchLeadVO);
			Integer totalLeadCount = (Integer) queryForObject(
					"leadsmanagement.getTotalLeadCount", fetchLeadVO);
			if (leadInfoList != null) {
				if (leadInfoList.size() > 0) {
					if (leadInfoList.get(0) != null) {
						leadInfoList.get(0).setTotalLeadCount(totalLeadCount);
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception in getting AllleadDetails" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadInfoList;
	}

	// getting services of firm
	public List<FirmServiceVO> getFirmServices(List<String> firmIds)
	throws DataServiceException {
		List<FirmServiceVO> serviceList = null;
		try {
			serviceList = queryForList("leadsmanagement.getFirmServices",
					firmIds);
		} catch (Exception e) {
			logger.info("Exception in getting firm services " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return serviceList;
	}

	// for fetching the details of given lead
	public LeadInfoVO getIndividualLeadDetails(Map<String, String> reqMap)
	throws DataServiceException {
		LeadInfoVO leadInfo = null;
		try {
			leadInfo = (LeadInfoVO) queryForObject(
					"leadsmanagement.getIndividualLeadDetails", reqMap);
		} catch (Exception e) {
			logger.info("Exception in getting lead details " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadInfo;
	}

	public List<FirmDetailsVO> getFirmDetailsPost(LeadRequest leadRequest)
	throws DataServiceException {
		List<FirmDetailsVO> postResponseList = new ArrayList<FirmDetailsVO>();
		try {
			postResponseList = queryForList(
					"leadsmanagement.getPostResponseAndContactInfo",
					leadRequest);

		} catch (Exception e) {
			logger.error("Exception in getFirmDetailsPost " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

		return postResponseList;
	}

	public List<FirmDetailsVO> getFirmDetails(
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException {
		List<FirmDetailsVO> finalResultList = new ArrayList<FirmDetailsVO>();
		List<FirmDetailsVO> reviewList = new ArrayList<FirmDetailsVO>();
		List<FirmDetailsVO> ratingList = new ArrayList<FirmDetailsVO>();
		List<FirmDetailsVO> contactList = new ArrayList<FirmDetailsVO>();
		List<ProviderDetailsVO> providerList = new ArrayList<ProviderDetailsVO>();
		try {
			List<Integer> firmIds = fetchProviderFirmRequest.getFirmIds();
			reviewList = queryForList("leadsmanagement.getLatestReviews",
					firmIds);
			ratingList = queryForList("leadsmanagement.getLatestFirmRating",
					firmIds);
			contactList = queryForList("leadsmanagement.getContactInfo",
					firmIds);
			// fetch provider distance within the firm and set it as firm
			// distance
			providerList = queryForList(
					"leadsmanagement.getProvidersMatchingForLead",
					fetchProviderFirmRequest);
			for (FirmDetailsVO contactObj : contactList) {
				for (FirmDetailsVO reviewObj : reviewList) {
					if (null != reviewObj
							&& null != contactObj
							&& reviewObj.getFirmId().equals(
									contactObj.getFirmId())) {
						// setting reviews
						contactObj.setReviewComment(reviewObj
								.getReviewComment());
						contactObj.setReviewdDate(reviewObj.getReviewdDate());
						contactObj.setReviewerName(reviewObj.getReviewerName());
						contactObj.setReviewRating(reviewObj.getReviewRating());
						// for setting yearsofService
						contactObj.setBusinessStartDate(contactObj
								.getBusinessStartDate());

					}
				}
				finalResultList.add(contactObj);
			}
			// For Setting FirmRatings
			if (null != finalResultList) {
				for (FirmDetailsVO result : finalResultList) {
					for (FirmDetailsVO rating : ratingList) {
						if (null != result
								&& null != rating
								&& result.getFirmId()
								.equals(rating.getFirmId())) {
							// setting firmRatings
							if (null != rating.getRating()) {
								result.setRating(rating.getRating());
							} else {
								result.setRating(0.0d);
							}
						}
					}
				}
			}
			if (null != finalResultList) {
				for (FirmDetailsVO firmVO : finalResultList) {
					Double minFirmDist = 0.0;
					int counter = 1;
					for (ProviderDetailsVO provDetailsVO : providerList) {
						if (null != firmVO.getFirmId()
								&& firmVO.getFirmId().equals(
										provDetailsVO.getFirmId())) {
							if (1 == counter) {
								minFirmDist = provDetailsVO
								.getDistanceFromZipInMiles();
								counter = counter + 1;
							} else if (minFirmDist > provDetailsVO
									.getDistanceFromZipInMiles()) {
								minFirmDist = provDetailsVO
								.getDistanceFromZipInMiles();
							}
						}
					}
					firmVO.setDistance(minFirmDist);
				}
			}
		} catch (Exception e) {
			logger.info("Exception in getting firm ratings and reviewe"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return finalResultList;
	}

	public void saveFirmResponse(List<LeadPostedProVO> postedProviders,
			LeadStatisticsVO statisticsVO, SLLeadVO leadVO)
	throws DataServiceException {
		try {
			if (null != postedProviders && !postedProviders.isEmpty()) {
				// Using batch insert multiple rows
				insert("leadsmanagement.insertprovidersInfo", postedProviders);
			}
			// updating lms_id in sl_lead_hdr and lead_wf_status
			update("leadsmanagement.updateLmsLeadId", leadVO);

		} catch (Exception e) {
			logger.info("Exception in saving response in SL" + e.getMessage());
			throw new DataServiceException(e.getMessage());

		}
	}

	public String validateLaunchZip(String custZip) throws DataServiceException {
		String id = null;
		try {
			id = (String) queryForObject("leadsmanagement.validateLaunchZip",
					custZip);
		} catch (Exception e) {
			logger
			.error("Exception in validating customer zip in FetchProvidersService: "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return id;
	}

	public String validateLeadId(String leadId) throws DataServiceException {
		String id = null;
		try {
			id = (String) queryForObject("leadsmanagement.validateLeadId",
					leadId);
		} catch (Exception e) {
			logger
			.error("Exception in validating customer zip in FetchProvidersService: "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return id;
	}

	public FetchProviderFirmRequest validatePrimaryProject(
			String primaryProject,
			FetchProviderFirmRequest fetchProviderFirmRequest)
	throws DataServiceException {
		try {
			fetchProviderFirmRequest = (FetchProviderFirmRequest) queryForObject(
					"leadsmanagement.validatePrimaryProject", primaryProject);
		} catch (Exception e) {
			logger
			.error("Exception in validating customer zip in FetchProvidersService: "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return fetchProviderFirmRequest;
	}

	public Integer getProjectId(SLLeadVO leadInfoVO)
	throws DataServiceException {
		Integer id = null;
		try {
			id = (Integer) queryForObject("leadsmanagement.getProjectId",
					leadInfoVO);
		} catch (Exception e) {
			logger.info("Exception in getting project Id in SL"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return id;
	}

	public void saveMatchedProviders(List<LeadMatchingProVO> matchedProviders,
			LeadStatisticsVO statisticsVO) throws DataServiceException {
		try {
			insert("leadsmanagement.saveMatchedProvidersInfo", matchedProviders);
			insert("leadsmanagement.insertLeadStatics", statisticsVO);
			update("leadsmanagement.updateLeadHdrleadWfStatus", statisticsVO
					.getSlLeadId());
		} catch (Exception e) {
			logger.info("Exception in saving matched Providers SL"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

	}

	public List<String> getAllProvidersPosted(String leadId)
	throws DataServiceException {
		List<String> postedProviders = null;
		try {
			postedProviders = queryForList("leadsmanagement.validateVendorId",
					leadId);
		} catch (Exception e) {
			logger.info("Exception in getting posted providers"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return postedProviders;
	}

	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			LeadRequest leadRequest) throws DataServiceException {
		List<FirmDetailsVO> firmDetailsVO = null;
		try {
			firmDetailsVO = queryForList(
					"leadsmanagement.getLeadPriceAndLmsPartnerIdForFirms",
					leadRequest);
		} catch (Exception ex) {
			logger
			.info("Exception in getLeadPriceForFirms method of FetchProviderDaoImpl: "
					+ ex.getMessage());
			throw new DataServiceException(ex.getMessage());
		}
		return firmDetailsVO;
	}

	// For fetching ProviderfirmDetails
	public List<FirmDetailsVO> getProviderFirmDetails(List<Integer> firmIdList)
	throws DataServiceException {

		List<FirmDetailsVO> ratingList = null;
		List<ReviewVO> reviewVOList = null;
		List<FirmDetailsVO> resultList = new ArrayList<FirmDetailsVO>();

		try {

			ratingList = queryForList("leadsmanagement.getLatestFirmRating",
					firmIdList);
			if (null != ratingList && !ratingList.isEmpty()) {
				for (FirmDetailsVO firmDetVO : ratingList) {
					if (null != firmDetVO && null != firmDetVO.getFirmId()) {

						reviewVOList = queryForList(
								"leadsmanagement.getLatestReviewList",
								firmDetVO.getFirmId());
						if (null != reviewVOList && !reviewVOList.isEmpty()) {
							firmDetVO.setReviewVO(reviewVOList);
						}
						resultList.add(firmDetVO);
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception in getting firm ratings and reviewe"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resultList;
	}

	// TO fetch providerId
	public String checkProvider(Map<String, String> checkMap)
	throws DataServiceException {
		String resourceId = "";
		try {

			resourceId = (String) queryForObject(
					"leadsmanagement.getResourceId", checkMap);
		} catch (Exception e) {
			logger.info("Exception in getting resourceId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resourceId;
	}

	// updating provider
	public void updateProvider(
			AssignOrReassignProviderRequest assignOrReassignProviderRequest)
	throws DataServiceException {
		try {

			update("leadsmanagement.updateProvider",
					assignOrReassignProviderRequest);
		} catch (Exception e) {
			logger.info("Exception in updating resourceId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	// updating resourceId
	public void updateResourceId(CompleteLeadsRequestVO completeLeadsVO)
	throws DataServiceException {
		try {

			update("leadsmanagement.updateResourceId", completeLeadsVO);
		} catch (Exception e) {
			logger.info("Exception in updating resourceId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	// updating lead details
	public void updateLeadDetails(CompleteLeadsRequestVO completeLeadsVO)
	throws DataServiceException {
		try {

			update("leadsmanagement.updateLeadDetails", completeLeadsVO);
		} catch (Exception e) {
			logger.info("Exception in updating leadDetails" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	// updating Matched Firm Details
	public void updateMatchedFirmDetails(CompleteLeadsRequestVO completeLeadsVO)
	throws DataServiceException {
		try {

			update("leadsmanagement.updateMatchedFirmDetails", completeLeadsVO);
		} catch (Exception e) {
			logger.info("Exception in updating matched Firm Details"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public void updateMembershipInfo(
			UpdateMembershipInfoRequest updateMembershipInfoRequest) {
		// updating membership info
		update("leadsmanagement.updateMembershipInfo",
				updateMembershipInfoRequest);
	}

	public void updateScheduleInfo(ScheduleRequestVO scheduleRequestVO) {
		// updating membership info
		update("leadsmanagement.updateScheduleInfo", scheduleRequestVO);
	}

	public void insertScheduleInfoHistory(ScheduleRequestVO scheduleRequestVO) {
		try {
			insert("leadsmanagement.insertScheduleHistory", scheduleRequestVO);
		} catch (Exception e) {
			logger.info(" error in inserting lead schedule history" + e);
		}
	}

	public List<FirmDetailsVO> getSpnMapForFirms(List<Integer> firmIdList)
	throws DataServiceException {
		List<FirmDetailsVO> detailsVO = null;
		try {
			detailsVO = queryForList("leadsmanagement.getSpnIdForFirm",
					firmIdList);
		} catch (Exception e) {
			logger.info("Exception in getting spn available for Firms");
		}
		return detailsVO;
	}

	public Map getRankCriteriaWeightage() throws DataServiceException {
		Map weigthtageMap = new HashMap();
		try {
			weigthtageMap = (Map) queryForMap("leadsmanagement.getCriteriaMap",
					null, "key", "value");
		} catch (Exception e) {
			logger.info("Exception in getting criteria weightage");
			throw new DataServiceException(e.getMessage());
		}
		return weigthtageMap;
	}

	public List<CompleteVO> getCompletesPerWeek(List<Integer> firmIdList)
	throws DataServiceException {
		List<CompleteVO> completeVO = null;
		try {
			completeVO = queryForList("leadsmanagement.getcompletesForFirm",
					firmIdList);

		} catch (Exception e) {
			logger.info("Exception in getting completesPerWeek for Firms");
		}
		return completeVO;

	}

	public void updateLeadStatus(UpdateLeadStatusRequestVO requestVO)
	throws DataServiceException {
		// udating lead status
		try {
			update("leadsmanagement.updateLeadStatus", requestVO);
		} catch (Exception e) {
			logger.error("Exception in updating Lead status " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public void updateMatchedFirmStatus(UpdateLeadStatusRequestVO requestVO)
	throws DataServiceException {
		// updating firms status
		try {
			Map<String, Integer> luMap = (Map<String, Integer>) queryForMap(
					"leadsmanagement.getLeadFirmStatusMap", null, "key",
			"value");
			if (null != requestVO
					&& StringUtils.isNotBlank(requestVO.firmStatus)) {
				Integer leadFirmStatus = luMap.get(requestVO.firmStatus);
				requestVO.setFirmStatusId(leadFirmStatus);
			}
			update("leadsmanagement.updateMatchedFirmStatus", requestVO);
		} catch (Exception e) {
			logger.error("Exception in updating firm status " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	// fetching firmstatus and status id
	public UpdateLeadStatusRequestVO validateFirmStatus(String firmStatus)
	throws DataServiceException {
		UpdateLeadStatusRequestVO requestVO = null;
		try {
			requestVO = (UpdateLeadStatusRequestVO) queryForObject(
					"leadsmanagement.validateFirmStatus", firmStatus);
		} catch (Exception e) {
			logger.info("Exception in validateFirmStatus");
		}
		return requestVO;

	}

	// fetching leadstatus and status id
	public UpdateLeadStatusRequestVO validateLeadStatus(String leadStatus)
	throws DataServiceException {
		UpdateLeadStatusRequestVO requestVO = null;
		try {
			requestVO = (UpdateLeadStatusRequestVO) queryForObject(
					"leadsmanagement.validateLeadStatus", leadStatus);
		} catch (Exception e) {
			logger.info("Exception in validateLeadStatus");
		}
		return requestVO;
	}

	// for add note for lead
	public String validateLeadNoteCategoryId(String leadNoteCategoryId)
	throws DataServiceException {
		String id = "";
		try {
			id = (String) queryForObject(
					"leadsmanagement.validateLeadNoteCategoryId",
					leadNoteCategoryId);
		} catch (Exception e) {
			logger.info("Exception in validateLeadNoteCategoryId"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return id;
	}

	public Integer saveLeadNotes(SLLeadNotesVO leadNotesVO)
	throws DataServiceException {
		try {
			Integer leadNoteId = (Integer) insert(
					"leadsmanagement.insertLeadNotes", leadNotesVO);
			return leadNoteId;

		} catch (Exception e) {
			logger.info("Exception in saveLeadNotes" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

	}

	public List<String> getProviderEmailIdsForLead(String leadId)
	throws DataServiceException {
		List<String> providerEmailIds = null;
		try {
			providerEmailIds = queryForList(
					"leadsmanagement.getAllProviderEmailsForLead", leadId);
		} catch (Exception e) {
			logger.info("Exception in getting getProviderEmailIdsForLead"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return providerEmailIds;
	}

	public String getSupportMailId() throws DataServiceException {

		String supportEmailId = null;
		try {
			supportEmailId = (String) queryForObject(
					"leadsmanagement.getSupportMailIdForNote", null);
		} catch (Exception e) {
			logger.info("Exception in getSupportMailId" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return supportEmailId;
	}

	public Integer validateBuyerForLead(Map<String, String> reqMap)
	throws DataServiceException {
		Integer valid = null;
		try {
			valid = (Integer) queryForObject(
					"leadsmanagement.validateBuyerForLead", reqMap);
		} catch (Exception e) {
			logger.info("Exception in validateBuyerForLead" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return valid;
	}

	// method for getting lead and firm details to send reminder mail to lead
	// owner
	public List<LeadReminderVO> getDetailsForRemindMail(String serviceDate)
	throws DataServiceException {
		List<LeadReminderVO> leadReminderVOs = null;
		try {
			logger
			.info("Entering getDetailsForRemindMail() of FetchProviderDao");
			leadReminderVOs = queryForList(
					"leadsmanagement.getDetailsForRemindMail", serviceDate);
			logger
			.info("Leaving getDetailsForRemindMail() of FetchProviderDao");
		} catch (Exception e) {
			logger.info("Exception in getting Details For Reminder Mail"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadReminderVOs;

	}

	public ScheduleAppointmentMailVO getScheduleMailDeatils(
			ScheduleRequestVO scheduleVO) throws DataServiceException {
		ScheduleAppointmentMailVO scheduleMailVO = null;
		try {

			scheduleMailVO = (ScheduleAppointmentMailVO) queryForObject(
					"leadsmanagement.getDetailsForScheduleMail", scheduleVO);

		} catch (Exception e) {
			logger.info("Exception in getting Details For Schedule Mail"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return scheduleMailVO;
	}

	// Method to fetch spnId,slNodeId,and Zip associated with a lead
	public SLLeadVO getLeadZipAndSpnForLead(String leadId)
	throws DataServiceException {
		SLLeadVO leadVO = null;
		try {
			leadVO = (SLLeadVO) queryForObject(
					"leadsmanagement.getLeadDetails", leadId);
		} catch (Exception e) {
			logger.info("Exception in getting Details For lead");
			throw new DataServiceException(e.getMessage());
		}

		return leadVO;
	}

	public List<ProviderDetailsVO> getEligibleProvidersForLead(
			LocationVO locationVO) throws DataServiceException {
		List<ProviderDetailsVO> providerList = null;
		try {
			providerList = queryForList(
					"leadsmanagement.getEligibleProvidersForlead", locationVO);
		} catch (Exception e) {
			logger.info("Exception in getting eligible providers"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return providerList;
	}

	public String getLeadStatusToUpdate(String slLeadId)
	throws DataServiceException {
		String leadStatus = null;
		try {
			leadStatus = (String) queryForObject(
					"leadsmanagement.validateLeadAndStatus", slLeadId);
		} catch (Exception e) {
			logger.info("Exception in getting leadSatus" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadStatus;
	}

	public String getLeadFirmStatus(Map<String, String> reqMap)
	throws DataServiceException {
		String leadFirmStatus = null;
		try {
			leadFirmStatus = (String) queryForObject(
					"leadsmanagement.validateFirmAndStatus", reqMap);
		} catch (Exception e) {
			logger
			.info("Exception in getting lead Firm Satus"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadFirmStatus;
	}

	public List<String> getAllLeadFirmStatus() throws DataServiceException {
		List<String> list = null;
		try {
			list = queryForList("leadsmanagement.getAllLeadFirmStatus");
		} catch (Exception e) {
			logger.info("Exception in getting All lead Firm Status"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return list;
	}

	public List<SLLeadNotesVO> getNoteList(String LeadId)
	throws DataServiceException {
		List<SLLeadNotesVO> leadNotesList = null;
		try {
			leadNotesList = queryForList("leadsmanagement.getNotes", LeadId);
		} catch (Exception e) {
			logger.info("Exception in getting getNoteList " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadNotesList;
	}

	public List<LeadHistoryVO> getHistoryList(String LeadId)
	throws DataServiceException {
		List<LeadHistoryVO> historyList = null;
		try {
			historyList = queryForList("leadsmanagement.getHistory", LeadId);
		} catch (Exception e) {
			logger.info("Exception in getting historyList " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return historyList;
	}

	// To get lead documents details
	public List<LeadAttachmentVO> getAttachmentsList(String LeadId)
	throws DataServiceException {
		List<LeadAttachmentVO> attachmentList = null;
		try {
			attachmentList = queryForList(
					"leadsmanagement.getProviderAttachments", LeadId);
		} catch (Exception e) {
			logger
			.info("Exception in getting attachmentList "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return attachmentList;
	}

	/**
	 * Description: This method checks whether the given firm id is associated
	 * with given lead id
	 * 
	 * @param ScheduleAppointmentRequest
	 * @throws DataServiceException
	 */
	public ScheduleRequestVO validateLeadAssosiationWithFirm(
			ScheduleAppointmentRequest scheduleAppointmentRequest)
	throws DataServiceException {
		ScheduleRequestVO sRequestVO = null;
		try {
			sRequestVO = (ScheduleRequestVO) queryForObject(
					"leadsmanagement.validateLeadAssosiationWithFirm",
					scheduleAppointmentRequest);
		} catch (Exception e) {
			logger.info("Exception in validateBuyerForLead" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return sRequestVO;
	}

	/**
	 * Description: This method gets all the details related to lead
	 * 
	 * @param SLLeadVO
	 * @throws DataServiceException
	 */
	public SLLeadVO getLead(String slLeadId) throws DataServiceException {
		SLLeadVO slLeadVO = new SLLeadVO();
		try {
			slLeadVO = (SLLeadVO) queryForObject("leadsmanagement.getLead",
					slLeadId);
		} catch (Exception e) {
			logger.info("Exception in getLead" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return slLeadVO;
	}

	/**
	 * Description: This method will insert into lead history
	 * 
	 * @param LeadLoggingVO
	 * @throws DataServiceException
	 */
	public void insertLeadLogging(LeadLoggingVO leadLoggingVO)
	throws DataServiceException {
		try {
			insert("leadsmanagement.insertLeadLogging", leadLoggingVO);
		} catch (Exception e) {
			logger
			.error("Exception in inserting lead history"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public boolean getResourceIdForFirm(Map<String, String> firmLeadMap)
	throws DataServiceException {
		boolean isValid = false;
		Integer count = null;
		try {
			count = (Integer) queryForObject(
					"leadsmanagement.getResourceIdForFirm", firmLeadMap);
			if (null != count && count > 0) {
				isValid = true;
			}
		} catch (Exception e) {
			logger.info("Exception in getting resource Id for firm "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isValid;
	}

	/**
	 * Description: This method will get user name of firm
	 * 
	 * @param String
	 * @throws DataServiceException
	 */
	public String getUserName(String firmId) throws DataServiceException {
		String userName = "";
		try {
			userName = (String) queryForObject("leadsmanagement.getUserName",
					firmId);
		} catch (Exception e) {
			logger.info("Exception in getting user Name" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return userName;
	}

	/**
	 * Description: This method will get resource name
	 * 
	 * @param String
	 * @throws DataServiceException
	 */
	public String getResourceName(String resourceId)
	throws DataServiceException {
		String resourceName = "";
		try {
			resourceName = (String) queryForObject(
					"leadsmanagement.getResourseName", resourceId);
		} catch (Exception e) {
			logger.info("Exception in getting resource Name" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resourceName;
	}

	public void updateLeadNotes(SLLeadNotesVO leadNotesVO)
	throws DataServiceException {
		try {
			update("leadsmanagement.updateLeadNotes", leadNotesVO);

		} catch (Exception e) {
			logger.info("Exception in saveLeadNotes" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.leadsmanagement.FetchProviderDao#validateLeadNoteIdForLeadId(java.lang.Integer,
	 *      java.lang.String, java.lang.Integer)
	 * 
	 * to validate note id
	 */
	public Integer validateLeadNoteIdForLeadId(Integer noteId, String leadId,
			Integer roles, Integer firmId) throws DataServiceException {
		Integer id = null;
		try {
			Map<String, String> validateMap = new HashMap<String, String>();
			validateMap.put("noteId", noteId.toString());
			validateMap.put("leadId", leadId);
			validateMap.put("role", roles.toString());
			validateMap.put("firmId", firmId.toString());

			id = (Integer) queryForObject(
					"leadsmanagement.validateLeadNoteIdForLeadId", validateMap);
		} catch (Exception e) {
			logger.info("Exception in validateLeadNoteCategoryId"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.leadsmanagement.FetchProviderDao#getProviderRating(java.lang.Integer)
	 * 
	 * to getProviderRating
	 */
	public Double getProviderRating(Integer resourceAssigned) {

		Double rating = new Double(0.00);
		rating = (Double) queryForObject("leadsmanagement.getProviderRating",
				resourceAssigned);
		return rating;
	}

	public void updateLmsLeadIdAndWfStatus(SLLeadVO leadVO)
	throws DataServiceException {
		try {
			// updating lms_id in sl_lead_hdr and lead_wf_status
			update("leadsmanagement.updateLmsLeadId", leadVO);
		} catch (Exception e) {
			logger.info("Exception in updating lms Lead Id and Wfstatus"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public String getSlLeadForPostLead(String leadId)
	throws DataServiceException {
		String slLeadId = null;
		try {
			slLeadId = (String) queryForObject(
					"leadsmanagement.validateLeadIdForPost", leadId);
		} catch (Exception e) {
			logger.info("Exception in getting slLead Id" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return slLeadId;
	}

	public SLLeadVO getleadDetailsForRewardPonits(String leadId)
	throws DataServiceException {
		SLLeadVO leadVO = null;
		try {
			leadVO = (SLLeadVO) queryForObject(
					"leadsmanagement.getLeadDetailsForRewardPoints", leadId);
		} catch (Exception e) {
			logger.info("Exception in getting lead reward details"
					+ e.getMessage());
		}
		return leadVO;
	}

	public DocumentVO getProviderImageDocument(String resourceId)
	throws DataServiceException {
		DocumentVO documentVO = null;
		try {
			documentVO = (DocumentVO) queryForObject(
					"leadsmanagement.getProviderImageDocument", resourceId);
		} catch (Exception e) {
			logger.info("Exception in getProviderImageDocument"
					+ e.getMessage());

		}
		return documentVO;
	}

	public CompleteLeadsRequestVO getCompleteMailDetails(
			Map<String, String> mailMap) throws DataServiceException {
		CompleteLeadsRequestVO completeLeadsRequestVO = null;
		try {
			completeLeadsRequestVO = (CompleteLeadsRequestVO) queryForObject(
					"leadsmanagement.getCompleteMailDetails", mailMap);
		} catch (Exception e) {
			logger.info("Exception in getCompleteMailDetails" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return completeLeadsRequestVO;
	}

	public LeadHistoryVO getBuyerName(Integer buyerId)
	throws DataServiceException {
		LeadHistoryVO leadHistoryVO = null;
		try {
			leadHistoryVO = (LeadHistoryVO) queryForObject(
					"leadsmanagement.getBuyerName", buyerId);
		} catch (Exception e) {
			logger.info("Exception in getBuyerName" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadHistoryVO;
	}

	public String getBusinessName(Integer vendorId) throws DataServiceException {
		String businessName = "";
		try {
			businessName = (String) queryForObject(
					"leadsmanagement.getBusinessNameOfVendor", vendorId);
		} catch (Exception e) {
			logger.info("Exception in getBusinessName" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return businessName;
	}

	public void updateVendorStatus() throws DataServiceException {

		try {

			getSqlMapClientTemplate().update("leadsmanagement.updateVendor",
					null);
		} catch (Exception e) {

		}
	}

	public Map<Integer, Integer> getInactiveVendors()
	throws DataServiceException {

		try {
			return getSqlMapClient().queryForMap(
					"leadsmanagement.getInactiveVendors", null, "vendor_id",
			"lms_partner_id");
		} catch (Exception ex) {
			throw new DataServiceException("error in getting inactive vendors",
					ex);
		}
	}

	public List<Integer> getInactiveVendorList() throws DataServiceException {

		List<Integer> inactiveVendorList = new ArrayList<Integer>();
		try {
			inactiveVendorList = (List<Integer>) queryForList(
					"leadsmanagement.getInactiveVendorsList", null);
			return inactiveVendorList;
		} catch (Exception ex) {
			throw new DataServiceException("error in getting inactive vendors",
					ex);
		}

	}
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
	public List<FirmDetailsVO> filterFirmsWithLeadProfileAndPrice(FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException{
		List<FirmDetailsVO> firmsWithProfile = new ArrayList<FirmDetailsVO>();
		try{
			firmsWithProfile=(List<FirmDetailsVO>)queryForList(
					"leadsmanagement.filterFirmsWithLeadProfileAndPrice", fetchProviderFirmRequest);
		}catch(Exception e){
			logger.error("Exception in occurred in filterFirmsWithLeadProfileAndPrice method of FetchProviderDaoImpl: " + e.getMessage());
			throw new DataServiceException("Error occurred in getting filterFirmsWithLeadProfileAndPrice",e);
		}
		return firmsWithProfile;
		
	}
	
	/* Added for SL-19727 */
	
	public Integer validateLeadBuyerAssociation(String leadId, Integer buyerId){
		Integer id = null;
		HashMap parameterMap = new HashMap();
		parameterMap.put("slLeadId", leadId);
		parameterMap.put("buyerId", buyerId);
		try{
			id = (Integer)queryForObject("leadsmanagement.validateLeadBuyerAssociation", parameterMap);
		}catch(Exception e){
			logger.error("Exception in validating Lead Id and buyerId Association: " + e);

		}
		return id;
	}

	public Integer validateLeadBuyerId(Integer buyerId){
		Integer id = null;
		try{
			id = (Integer)queryForObject("leadsmanagement.validateBuyerId",buyerId);
		}catch(Exception e){
			logger.error("Exception in validating  buyerId: " + e);
	
		}
		return id;
	}
	/**
	 * Description: This method gets all the details related to lead
	 * 
	 * @param LeadCustomerDetailsVO
	 * @throws DataServiceException
	 */
	public LeadCustomerDetailsVO getLeadCustomerDetails(String slLeadId) throws DataServiceException {
		LeadCustomerDetailsVO leadCustomerDetailsVO = new LeadCustomerDetailsVO();
		try {
			leadCustomerDetailsVO = (LeadCustomerDetailsVO) queryForObject("leadsmanagement.leadCustomerDetails",
					slLeadId);
		} catch (Exception e) {
			logger.info("Exception in getLeadCustomerDetails" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return leadCustomerDetailsVO;
	}
	
	public List<FirmDetailsVO> getLeadFirmDetails(String slLeadId)
	throws DataServiceException {
		List<FirmDetailsVO> firmDetailsVOList = new ArrayList<FirmDetailsVO>();
		try {
			firmDetailsVOList = queryForList("leadsmanagement.getLeadProviderDetails",slLeadId);

		} catch (Exception e) {
			logger.error("Exception in getLeadFirmDetails " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

		return firmDetailsVOList;
	}

	/************************************************B2C*****************************************/
	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) throws DataServiceException {
		List<FirmDetailsVO> firmDetailsVO = null;
		try {
			firmDetailsVO = queryForList(
					"leadsmanagement.getLeadPriceAndLmsPartnerIdForFirms",
					leadRequest);
		} catch (Exception ex) {
			logger
			.info("Exception in getLeadPriceForFirms method of FetchProviderDaoImpl: "
					+ ex.getMessage());
			throw new DataServiceException(ex.getMessage());
		}
		return firmDetailsVO;
	}
	
	public List<FirmDetailsVO> getFirmDetailsPost(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
	throws DataServiceException {
		List<FirmDetailsVO> postResponseList = new ArrayList<FirmDetailsVO>();
		try {
			postResponseList = queryForList(
					"leadsmanagement.getPostResponseAndContactInfo.v2",
					leadRequest);

		} catch (Exception e) {
			logger.error("Exception in getFirmDetailsPost " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

		return postResponseList;
	}
	
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest validatePrimaryProject(
			String primaryProject,
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
	throws DataServiceException {
		try {
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest requestWithProjectDetails = 
					new com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest(); 
			requestWithProjectDetails = (com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest) queryForObject(
					"leadsmanagement.validatePrimaryProject.v2", primaryProject);
			
			if(null != requestWithProjectDetails){
				fetchProviderFirmRequest.setSlNodeId(requestWithProjectDetails.getSlNodeId());
				fetchProviderFirmRequest.setLmsProjectDescription(requestWithProjectDetails.getLmsProjectDescription());
				fetchProviderFirmRequest.setLeadCategory(requestWithProjectDetails.getLeadCategory());
			}
		} catch (Exception e) {
			logger
			.error("Exception in validating customer zip in FetchProvidersService: "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return fetchProviderFirmRequest;
	}
	
	public List<FirmDetailsVO> getFirmDetails(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException {
		List<FirmDetailsVO> finalResultList = new ArrayList<FirmDetailsVO>();
		List<FirmDetailsVO> reviewList = new ArrayList<FirmDetailsVO>();
		List<FirmDetailsVO> ratingList = new ArrayList<FirmDetailsVO>();
		List<FirmDetailsVO> contactList = new ArrayList<FirmDetailsVO>();
		List<ProviderDetailsVO> providerList = new ArrayList<ProviderDetailsVO>();
		try {
			List<Integer> firmIds = fetchProviderFirmRequest.getFirmIds();
			reviewList = queryForList("leadsmanagement.getLatestReviews",
					firmIds);
			ratingList = queryForList("leadsmanagement.getLatestFirmRating",
					firmIds);
			contactList = queryForList("leadsmanagement.getContactInfo",
					firmIds);
			// fetch provider distance within the firm and set it as firm
			// distance
			providerList = queryForList(
					"leadsmanagement.getProvidersMatchingForLead.v2",
					fetchProviderFirmRequest);
			for (FirmDetailsVO contactObj : contactList) {
				for (FirmDetailsVO reviewObj : reviewList) {
					if (null != reviewObj
							&& null != contactObj
							&& reviewObj.getFirmId().equals(
									contactObj.getFirmId())) {
						// setting reviews
						contactObj.setReviewComment(reviewObj
								.getReviewComment());
						contactObj.setReviewdDate(reviewObj.getReviewdDate());
						contactObj.setReviewerName(reviewObj.getReviewerName());
						contactObj.setReviewRating(reviewObj.getReviewRating());
						// for setting yearsofService
						contactObj.setBusinessStartDate(contactObj
								.getBusinessStartDate());

					}
				}
				finalResultList.add(contactObj);
			}
			// For Setting FirmRatings
			if (null != finalResultList) {
				for (FirmDetailsVO result : finalResultList) {
					for (FirmDetailsVO rating : ratingList) {
						if (null != result
								&& null != rating
								&& result.getFirmId()
								.equals(rating.getFirmId())) {
							// setting firmRatings
							if (null != rating.getRating()) {
								result.setRating(rating.getRating());
							} else {
								result.setRating(0.0d);
							}
						}
					}
				}
			}
			if (null != finalResultList) {
				for (FirmDetailsVO firmVO : finalResultList) {
					Double minFirmDist = 0.0;
					String minDistCity = null;
					String minDistState = null;

					int counter = 1;
					for (ProviderDetailsVO provDetailsVO : providerList) {
						if (null != firmVO.getFirmId()
								&& firmVO.getFirmId().equals(
										provDetailsVO.getFirmId())) {
							if (1 == counter) {
								minFirmDist = provDetailsVO
								.getDistanceFromZipInMiles();
								minDistCity = provDetailsVO.getCity();
								minDistState = provDetailsVO.getState();
								counter = counter + 1;
							} else if (minFirmDist > provDetailsVO
									.getDistanceFromZipInMiles()) {
								minFirmDist = provDetailsVO
								.getDistanceFromZipInMiles();
								minDistCity = provDetailsVO.getCity();
								minDistState = provDetailsVO.getState();
							}
						}
					}
					firmVO.setDistance(minFirmDist);
					firmVO.setCity(minDistCity);
					firmVO.setState(minDistState);
				}
			}
		} catch (Exception e) {
			logger.info("Exception in getting firm ratings and reviewe"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return finalResultList;
	}
	
	public List<FirmDetailsVO> filterFirmsWithLeadProfileAndPrice(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException{
		List<FirmDetailsVO> firmsWithProfile = new ArrayList<FirmDetailsVO>();
		try{
			firmsWithProfile=(List<FirmDetailsVO>)queryForList(
					"leadsmanagement.filterFirmsWithLeadProfileAndPrice", fetchProviderFirmRequest);
		}catch(Exception e){
			logger.error("Exception in occurred in filterFirmsWithLeadProfileAndPrice method of FetchProviderDaoImpl: " + e.getMessage());
			throw new DataServiceException("Error occurred in getting filterFirmsWithLeadProfileAndPrice",e);
		}
		return firmsWithProfile;
		
	}
	
	public boolean isLeadNonLaunchSwitch() throws DataServiceException{
		boolean isLeadNonLaunchSwitch = false;
		String isLeadNonLaunch = "0";
		try {
			isLeadNonLaunch = (String) queryForObject("leadsmanagement.isLeadNonLaunchSwitch", null);

			if(StringUtils.isNotBlank(isLeadNonLaunch) && ("1").equals(isLeadNonLaunch)){
				isLeadNonLaunchSwitch = true;
			}
		} catch (Exception e) {
			logger.info("Exception in isLeadNonLaunchSwitch " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return isLeadNonLaunchSwitch;
	}
	
	public String validateAllZip(String custZip) throws DataServiceException {
		String id = null;
		try {
			id = (String) queryForObject("leadsmanagement.validateAllZip",
					custZip);
		} catch (Exception e) {
			logger
			.error("Exception in validating customer zip in FetchProvidersService.validateAllZip(): "
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getFirmIds(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws DataServiceException{
		List<Integer> firmIds = null;
		try{
			firmIds=(List<Integer>)queryForList("leadsmanagement.getFirms", fetchProviderFirmRequest);
		}catch(Exception e){
			logger.error("Exception in occurred in getFirmIds method of FetchProviderDaoImpl: " + e.getMessage());
			throw new DataServiceException("Error occurred in getting getFirmIds",e);
		}
		return firmIds;
		
	}
	
	public void saveMatchedProviders(List<LeadMatchingProVO> matchedProviders, String leadId) throws DataServiceException {
		try {
			insert("leadsmanagement.saveMatchedProvidersInfo", matchedProviders);
			update("leadsmanagement.updateLeadHdrleadWfStatus", leadId);
		} catch (Exception e) {
			logger.info("Exception in saving matched Providers SL"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public List<String> validateLeadMatchedFirmIds(String leadId) throws DataServiceException {
		List<String> invalidFirms = null;
		try {
			invalidFirms = queryForList("leadsmanagement.getMatchedFirmIds",
					leadId);
		} catch (Exception e) {
			logger.info("Exception in validateLeadPostFirmIds"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		/*if(null == invalidFirms){
			return new ArrayList<String>();
		}*/
		return invalidFirms;
	}

	public List<String> validateFirmsIds(List<String> firmIds) throws DataServiceException {
		List<String> invalidFirms = null;
		try {
			invalidFirms = queryForList("leadsmanagement.getFirmIds",
					firmIds);
		} catch (Exception e) {
			logger.info("Exception in validateLeadPostFirmIds"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		/*if(null == invalidFirms){
			return new ArrayList<String>();
		}*/
		return invalidFirms;
	}

	public List<String> validateFirmsIdsPosted(String leadId) throws DataServiceException {
		List<String> invalidFirms = null;
		try {
			invalidFirms = queryForList("leadsmanagement.getPostFirmIds",
					leadId);
		} catch (Exception e) {
			logger.info("Exception in validateLeadPostFirmIds"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		/*if(null == invalidFirms){
			return new ArrayList<String>();
		}*/
		return invalidFirms;
	}
	
	/**
	 * 
	 */
	public void saveEmployeesList(List<Employee> empList)
			throws DataServiceException {
		try {
			batchInsert("insertEmployees.query", empList);
		} catch (Exception e) {
			throw new DataServiceException(
					"General Exception @saveEmployeesList due to "
							+ e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void saveCampaignList(List<Campaign> campList)
			throws DataServiceException {
		try {
			batchInsert("insertCampaigns.query", campList);
		} catch (Exception e) {
			throw new DataServiceException(
					"General Exception @saveCampaignList due to "
							+ e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void saveCdrList(List<Cdr> cdrList)
			throws DataServiceException {
		try {
			batchInsert("insertCDRs.query", cdrList);
		} catch (Exception e) {
			throw new DataServiceException(
					"General Exception @saveCdrList due to "
							+ e.getMessage());
		}
	}
	
	
	/**
	 * 
	 */
	public void saveImpressionsList(List<Impressions> impsList) 
			throws DataServiceException{
		try {
			batchInsert("insertImpressions.query", impsList);
		} catch (Exception e) {
			throw new DataServiceException(
					"General Exception @saveImpressionsList due to "
							+ e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public Integer getLatestId(Integer type) throws DataServiceException {
		
		Integer latestId = 0;
		try {
			// Type 1  - Truncate is_employees  
			// Type 2  - Truncate is_campaigns
			// Type 3  - Truncate is_cdr
			// Type 4  - Truncate is_impressions
			
			if(1 == type){
				latestId = (Integer) queryForObject("getEmpId.query", null);
			}else if(2 == type){
				latestId = (Integer) queryForObject("getCmpId.query", null);
			}else if(3 == type){
				latestId = (Integer) queryForObject("getCdrId.query", null);
			}else if(4 == type){
				latestId = (Integer) queryForObject("getImressionId.query", null);
			}
		} catch (Exception e) {
			throw new DataServiceException(
					"General Exception @saveCampaignList due to "
							+ e.getMessage());
		}
		return latestId;
	}
	
	public void truncateData(Integer type)
			throws DataServiceException {
		try {
		
			// Type 1  - Truncate is_employees  
			// Type 2  - Truncate is_campaigns
			// Type 3  - Truncate is_cdr
			// Type 4  - Truncate is_impressions
			
			if(1 == type){
				delete("truncateEmployees.delete",null);
			}else if(2 == type){
				delete("truncateCampaigns.delete",null);
			}else if(3 == type){
				delete("truncateCDR.delete",null);
			}else if(4 == type){
				delete("truncateImpressions.delete",null);
			}
		} catch (Exception e) {
			throw new DataServiceException(
					"General Exception @SMSDataMigrationDaoImpl.insertSubscriptionDetails() due to "
							+ e.getMessage());
		}
	}
}
