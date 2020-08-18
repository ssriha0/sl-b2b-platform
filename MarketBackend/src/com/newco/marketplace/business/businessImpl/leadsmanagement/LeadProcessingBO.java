package com.newco.marketplace.business.businessImpl.leadsmanagement;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.provider.CompanyProfileBOImpl;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Contact;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmIds;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteLeadsRequestVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmCredentialVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadCustVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadVO;
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
import com.newco.marketplace.dto.vo.survey.SurveyAnswerVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.dao.buyerleadmanagement.BuyerLeadManagementDao;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.iDao.leadsmanagement.FetchProviderDao;
import com.newco.marketplace.persistence.iDao.survey.SurveyDAO;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.Campaign;
import com.newco.marketplace.vo.provider.Cdr;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.Employee;
import com.newco.marketplace.vo.provider.Impressions;
import com.newco.marketplace.webservices.base.response.ProcessResponse;


public class LeadProcessingBO implements ILeadProcessingBO {

	private static final Logger logger = Logger
			.getLogger(LeadProcessingBO.class);
	private FetchProviderDao fetchProviderDao;
	private BuyerLeadManagementDao buyerLeadManagementDao;
	private CompanyProfileBOImpl companyProfileBOImpl;
	private SurveyDAO surveyDAO;
	private IApplicationPropertiesDao applicationPropertiesDao;
	private ILookupBO lookupBO;
	private AlertDao alertDao;

	/**
	 * Save Lead Object Information
	 * 
	 * @param fetchProviderFirmRequest
	 * @return
	 * @throws BusinessServiceException
	 */
	public String saveLeadInfo(FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException {
		String slLeadId = getNextId("6");
		Date today = new Date();

		SLLeadVO leadInfoVO = new SLLeadVO(slLeadId, fetchProviderFirmRequest
				.getCustomerZipCode(), fetchProviderFirmRequest.getSkill(),
				fetchProviderFirmRequest.getUrgencyOfService(),
				NewServiceConstants.COMPETITIVE_LEAD_TYPE,
				fetchProviderFirmRequest.getLeadSource(), "1",
				fetchProviderFirmRequest.getPrimaryProject(), today, today,
				"SL", "SL", fetchProviderFirmRequest.getClientId(),
				NewServiceConstants.HOME_SERVICES_BUYER_ID);

		LeadStatisticsVO statisticsVO = new LeadStatisticsVO(slLeadId, today,
				today,
				PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL,
				PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
				today, today, "SL", "SL");
		try {
			fetchProviderDao.saveLeadInfo(leadInfoVO);
			saveLeadStats(statisticsVO);
		} catch (DataServiceException e) {
			logger.error("Exception in saving Lead Information" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}

		return slLeadId;
	}

	/**
	 * @param FetchProviderFirmRequest
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String saveLeadInfoforNonLaunchMarket(
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException {
		String slLeadId = getNextId("6");
		Date today = new Date();

		SLLeadVO leadInfoVO = new SLLeadVO(slLeadId, fetchProviderFirmRequest
				.getCustomerZipCode(), fetchProviderFirmRequest.getSkill(),
				fetchProviderFirmRequest.getUrgencyOfService(),
				NewServiceConstants.COMPETITIVE_LEAD_TYPE,
				fetchProviderFirmRequest.getLeadSource(), "5",
				fetchProviderFirmRequest.getPrimaryProject(), today, today,
				"SL", "SL", fetchProviderFirmRequest.getClientId(),
				NewServiceConstants.HOME_SERVICES_BUYER_ID);

		LeadStatisticsVO statisticsVO = new LeadStatisticsVO(slLeadId, today,
				today,
				PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL,
				PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
				today, today, "SL", "SL");
		try {
			fetchProviderDao.saveLeadInfo(leadInfoVO);
			saveLeadStats(statisticsVO);
		} catch (DataServiceException e) {
			logger.error("Exception in saving Lead Information" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}

		return slLeadId;
	}

	public void saveLeadStats(LeadStatisticsVO statisticsVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.saveLeadStats(statisticsVO);
		} catch (DataServiceException e) {
			logger.error("Exception in saving Lead Stats" + e.getCause());
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public SLLeadVO savePostLeadFirmInfo(LeadRequest leadRequest)
			throws BusinessServiceException {
		SLLeadVO leadInfoVO = new SLLeadVO();
		LeadContactInfoVO contactInfoVO = new LeadContactInfoVO();
		LeadStatisticsVO statisticsVO = new LeadStatisticsVO();
		try {
			mapPostLeadInfo(leadInfoVO, contactInfoVO, leadRequest,
					statisticsVO);
			leadInfoVO = fetchProviderDao.savePostLeadInfo(leadInfoVO,
					contactInfoVO, statisticsVO);
		} catch (DataServiceException e) {
			logger.info("Exception in saving PostLead Information"
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return leadInfoVO;

	}

	private void mapPostLeadInfo(SLLeadVO leadInfoVO,
			LeadContactInfoVO contactInfoVO, LeadRequest leadRequest,
			LeadStatisticsVO statisticsVO) throws BusinessServiceException {
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		Date serviceDate = null;
		// setting lmsLeadId in leadInfo to validate sl_lead_hdr
		// leadInfoVO.setLmsLeadId(leadRequest.getLeadId());
		leadInfoVO.setSlLeadId(leadRequest.getLeadId());
		try {
			if (null != leadRequest.getServiceDate()) {
				serviceDate = (Date) formatter.parse(leadRequest
						.getServiceDate());
			}
		} catch (ParseException pe) {
			logger.error("Error parsing service date" + pe.getMessage());
		}
		if (!leadRequest.isNonLaunchZip()) {
			leadInfoVO.setServiceDate(serviceDate);
			// setting timeframe
			leadInfoVO.setServiceTimeZone(leadRequest.getServiceTimeZone());
			leadInfoVO.setServiceStartTime(leadRequest.getServiceStartTime());
			leadInfoVO.setServiceEndTime(leadRequest.getServiceEndTime());
			leadInfoVO.setModifiedDate(createdDate);
			leadInfoVO.setModifiedBy("LMS");
			leadInfoVO.setProjectDescription(leadRequest
					.getProjectDescription());
			leadInfoVO.setSecondaryProjects(leadRequest.getSecondaryProjects());
			// setting leadType based on NO of firms in request
			if (null != leadRequest.getFirmIds()
					&& null != leadRequest.getFirmIds().getFirmId()
					&& !leadRequest.getFirmIds().getFirmId().isEmpty()) {
				int i = leadRequest.getFirmIds().getFirmId().size();
				switch (i) {
				case 1:
					leadInfoVO.setLeadType(PublicAPIConstant.EXCLUSIVE_LEAD);
					break;
				default:
					leadInfoVO.setLeadType(PublicAPIConstant.COMPETITIVE);
					break;
				}

			}
			/*
			 * lead_wf_status column has to be updated with following status
			 * which will be mapped to wf_states table Matched :1,UnMatched:2
			 */
			/*
			 * Setting it as 2 as we dont know wheather it is matched or not
			 * yet.
			 */
			leadInfoVO.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED);
		} else {
			leadInfoVO.setNonLaunchZip(true);
		}

		// creating leadStatistics info
		statisticsVO
				.setDataFlowDirection(PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL);
		statisticsVO.setCreatedDate(createdDate);
		statisticsVO.setModifiedDate(createdDate);
		statisticsVO.setCreatedBy("SL");
		statisticsVO.setModifiedBy("SL");
		statisticsVO.setRequestDate(createdDate);
		statisticsVO.setResponseDate(null);
		statisticsVO
				.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION);

		// Creating contact info
		if (null != leadRequest.getCustContact()
				&& null != leadRequest.getCustContact().getContact()) {
			contactInfoVO.setStreet1(leadRequest.getCustContact().getContact()
					.getAddress());
			contactInfoVO.setCity(leadRequest.getCustContact().getContact()
					.getCity());
			contactInfoVO.setEmail(leadRequest.getCustContact().getContact()
					.getEmail());
			contactInfoVO.setFirstName(leadRequest.getCustContact()
					.getFirstName());
			contactInfoVO.setLastName(leadRequest.getCustContact()
					.getLastName());
			if (null != leadRequest.getCustContact().getContact().getPhone()) {
				contactInfoVO.setPhoneNo(Long.valueOf(leadRequest
						.getCustContact().getContact().getPhone()));
			}
			contactInfoVO.setCreatedDate(createdDate);
			contactInfoVO.setCreatedBy("LMS");
			contactInfoVO.setModifiedDate(createdDate);
			contactInfoVO.setModifiedBy("LMS");
			contactInfoVO.setState(leadRequest.getCustContact().getContact()
					.getState());
			contactInfoVO.setZipCode(leadRequest.getCustContact().getContact()
					.getCustomerZipCode());
			if (StringUtils.isNotBlank(leadRequest.getSYWRMemberId())
					&& !(leadRequest.isNonLaunchZip() || leadRequest.isNoProvLead())) {
				contactInfoVO.setsWYRMemberId(leadRequest.getSYWRMemberId());
				contactInfoVO
						.setsWYRReward(NewServiceConstants.SWYR_REWARD_POINTS);
			}

		}
	}

	private void mapLeadInfo(SLLeadVO leadInfoVO,
			LeadStatisticsVO statisticsVO, FetchProviderFirmRequest leadInfo) {
		// Setting request values into VO
		if (StringUtils.isNotEmpty(leadInfo.getCustomerZipCode())) {
			leadInfoVO.setCustomerZipCode(leadInfo.getCustomerZipCode());
			if (StringUtils.isNotEmpty(leadInfo.getSkill())) {
				leadInfoVO.setSkill(leadInfo.getSkill());
			}
			if (StringUtils.isNotEmpty(leadInfo.getUrgencyOfService())) {
				leadInfoVO.setUrgencyOfService(leadInfo.getUrgencyOfService());
			} 
			//SL-20893 changed to null as per urgency flag need
			//else { /* if the urgency is null setting it as SAME_DAY */
				//leadInfoVO.setUrgencyOfService("SAME_DAY");
			//}
			leadInfoVO.setPrimaryProject(leadInfo.getPrimaryProject());
			if (StringUtils.isNotEmpty(leadInfo.getLeadSource())) {
				leadInfoVO.setLeadSource(leadInfo.getLeadSource());
			} else {/* if leadSource is null setting it as SHS */
				leadInfoVO.setLeadSource("SHS");
			}
			if (StringUtils.isNotEmpty(leadInfo.getClientId())) {
				leadInfoVO.setClientId(leadInfo.getClientId());
			} else {/* if clientid is null setting it as SHS */
				leadInfoVO.setClientId("SHS");
			}
			/*
			 * lead_wf_status column has to be updated with following status
			 * which will be mapped to wf_states table Unmatched:2 Matched :1
			 */
			leadInfoVO.setLeadWfStatus("2");
			leadInfoVO.setCreatedBy("SL");
			Date today = new Date(System.currentTimeMillis());
			Timestamp createdDate = new Timestamp(today.getTime());
			leadInfoVO.setCreatedDate(createdDate);
			// creating leadStatistics info
			statisticsVO.setSlLeadId(leadInfoVO.getSlLeadId());
			statisticsVO
					.setDataFlowDirection(PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL);
			statisticsVO.setCreatedDate(createdDate);
			statisticsVO.setModifiedDate(createdDate);
			statisticsVO.setCreatedBy("SL");
			statisticsVO.setModifiedBy("SL");
			statisticsVO.setRequestDate(createdDate);
			statisticsVO.setResponseDate(null);
			statisticsVO
					.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION);

		}
	}

	public Map getFirmDetailsPost(LeadRequest leadRequest)
			throws BusinessServiceException {
		List<FirmDetailsVO> detailsVOs = null;
		Map firmDeatilsMap = null;
		try {
			detailsVOs = fetchProviderDao.getFirmDetailsPost(leadRequest);
			firmDeatilsMap = new HashMap();
			for (String firmId : leadRequest.getFirmIds().getFirmId()) {
				for (FirmDetailsVO vo : detailsVOs) {
					if (firmId.equals(vo.getFirmId())) {
						firmDeatilsMap.put(firmId, vo);
					}
				}
			}
		} catch (DataServiceException e) {
			logger.info("Exception in getting firm Reviews and Ratings"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return firmDeatilsMap;
	}

	public Map getFirmDetails(FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException {
		List<FirmDetailsVO> detailsVOs = null;
		Map firmDeatilsMap = null;
		try {
			detailsVOs = fetchProviderDao
					.getFirmDetails(fetchProviderFirmRequest);
			firmDeatilsMap = new HashMap();
			for (Integer firmId : fetchProviderFirmRequest.getFirmIds()) {
				for (FirmDetailsVO vo : detailsVOs) {
					if (null != firmId
							&& null != vo.getFirmId()
							&& firmId.intValue() == Integer.parseInt(vo
									.getFirmId())) {
						firmDeatilsMap.put(firmId.intValue(), vo);
					}
				}
			}
		} catch (DataServiceException e) {
			logger.info("Exception in getting firm Reviews and Ratings"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return firmDeatilsMap;
	}

	private synchronized String getNextId(String sourceId) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbOrder = new StringBuilder();
		Random rand = new Random();
		String ts = "";
		String random = "";

		// current time stamp is the 2nd part of the service order
		// it is at least 13 characters as of today...
		ts = String.valueOf(Calendar.getInstance().getTimeInMillis());
		// remove first 3 chars
		ts = ts.substring(3, 13);

		// a random number is the 3rd part of the key
		random = String.valueOf(Math.abs(rand.nextInt()));
		random = random.substring(0, 2);

		// put the 3 pieces together
		sb.append(sourceId);
		sb.append(ts);
		sb.append(random);

		// construct the service order format XXX-XXXX-XXXX-XXXX
		sbOrder.append(sb.substring(0, 3));
		sbOrder.append("-");
		sbOrder.append(sb.substring(3, 7));
		sbOrder.append("-");
		sbOrder.append(sb.substring(7, 11));
		sbOrder.append("-");
		sbOrder.append(sb.substring(11));

		return sbOrder.toString();
	}

	public void saveResponseInfo(List<LeadPostedProVO> matchProviders,
			LeadStatisticsVO statisticsVO, SLLeadVO leadVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.saveFirmResponse(matchProviders, statisticsVO,
					leadVO);
			saveLeadStats(statisticsVO);
		} catch (Exception e) {
			logger.info("Exception in saving response in SL" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	public String validateLaunchZip(String custZip)
			throws BusinessServiceException {
		String id;
		try {
			id = fetchProviderDao.validateLaunchZip(custZip);
		} catch (Exception e) {
			logger.error("Exception in validating customer zip code "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return id;
	}

	public FetchProviderFirmRequest validatePrimaryProject(
			String primaryProject,
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException {
		try {

			fetchProviderFirmRequest = fetchProviderDao.validatePrimaryProject(
					primaryProject, fetchProviderFirmRequest);
		} catch (Exception e) {
			logger.error("Exception in validating primary project "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return fetchProviderFirmRequest;
	}

	public void saveMatchedProvidersInfo(
			List<LeadMatchingProVO> matchedProviders,
			LeadStatisticsVO statisticsVO, LeadRequest leadRequest)
			throws BusinessServiceException {
		try {
			fetchProviderDao.saveMatchedProviders(matchedProviders,
					statisticsVO);
			// validating the vendors before saving posted vendors info
			/*
			 * List<String> postedVendors = fetchProviderDao
			 * .getAllProvidersPosted(statisticsVO.getSlLeadId()); List<String>
			 * allProviders = leadRequest.getFirmIds().getFirmId(); if (null !=
			 * postedVendors && null != allProviders) { if
			 * (postedVendors.containsAll(allProviders)) {
			 * fetchProviderDao.saveMatchedProviders(matchedProviders,
			 * statisticsVO); } }
			 */
		} catch (Exception e) {
			logger.info("Exception in saving matched Providers SL"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			LeadRequest leadRequest) throws BusinessServiceException {
		List<FirmDetailsVO> firmDetailsVO = null;
		try {
			firmDetailsVO = fetchProviderDao
					.getLeadPriceAndLmsPartnerIdForFirms(leadRequest);
		} catch (Exception ex) {
			logger
					.info("Exception in getLeadPriceForFirms method of LeadProcessingBO: "
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return firmDetailsVO;
	}

	// for fetching the vendorId and the status for the firms in
	// vendor_lead_profile

	public List<Integer> getInactiveFirmStatusForNewTC()
			throws BusinessServiceException {

		List<Integer> inactiveFirms = new ArrayList<Integer>();

		try {
			inactiveFirms = fetchProviderDao.getInactiveVendorList();
		} catch (Exception e) {
			return null;
		}
		return null;

	}

	// new method for credentials
	public Map getFirmCredentials(List<String> firmIdList)
			throws BusinessServiceException {

		List<FirmCredentialVO> firmCredentialVOs = null;
		Map credentialVOs = new HashMap();
		try {

			if (firmIdList != null) {
				firmCredentialVOs = fetchProviderDao
						.getFirmCredentials(firmIdList);
				if (firmCredentialVOs != null) {
					for (String firmid : firmIdList) {
						List<FirmCredentialVO> dummyList = new ArrayList<FirmCredentialVO>();
						for (FirmCredentialVO vo : firmCredentialVOs) {
							if (firmid.equals(vo.getFirmId())) {
								dummyList.add(vo);
							}

						}
						credentialVOs.put(firmid, dummyList);
					}
				}
			}
		} catch (Exception ex) {
			logger
					.info("Exception in getCompanyProfileDetails method of LeadProcessingBO: "
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return credentialVOs;

	}

	/*
	 * public List<CompanyProfileVO> getCompanyProfileDetails(List<Integer>
	 * firmIdList) throws BusinessServiceException{ List<CompanyProfileVO>
	 * companyProfileVO=null;
	 * 
	 * for(Integer firmId:firmIdList){ CompanyProfileVO comProfileVO = new
	 * CompanyProfileVO(); try{
	 * comProfileVO=companyProfileBOImpl.getCompleteProfile(firmId); }
	 * catch(Exception ex){ logger.info("Exception in getCompanyProfileDetails
	 * method of LeadProcessingBO: "+ex.getMessage()); throw new
	 * BusinessServiceException(ex.getMessage()); }
	 * companyProfileVO.add(comProfileVO); }
	 * 
	 * return companyProfileVO; }
	 */
	// for fetching the company porfile details
	public Map getCompanyProfileDetails(List<Integer> firmIdList)
			throws BusinessServiceException {
		List<CompanyProfileVO> companyProfileVOList = new ArrayList<CompanyProfileVO>();
		Map CompanyProfileVOs = new HashMap();
		try {
			for (Integer firmId : firmIdList) {
				CompanyProfileVO companyProfileVO = new CompanyProfileVO();
				companyProfileVO = companyProfileBOImpl
						.getCompleteProfile(firmId);
				CompanyProfileVOs.put(firmId, companyProfileVO);
			}
		} catch (Exception ex) {
			logger
					.info("Exception in getCompanyProfileDetails method of LeadProcessingBO: "
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return CompanyProfileVOs;
	}

	public Map getProviderFirmDetails(List<Integer> firmIdList)
			throws BusinessServiceException {
		List<FirmDetailsVO> detailsVO = null;
		Map firmDeatilsMap = new HashMap();
		try {

			detailsVO = fetchProviderDao.getProviderFirmDetails(firmIdList);
			if (null != detailsVO && !detailsVO.isEmpty()) {
				for (Integer firmId : firmIdList) {
					for (FirmDetailsVO vo : detailsVO) {
						if (firmId.intValue() == Integer.parseInt(vo
								.getFirmId())) {
							firmDeatilsMap.put(firmId.intValue(), vo);

						}
					}
				}

			}

		} catch (DataServiceException e) {
			logger.info("Exception in getting firm Reviews and Ratings"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return firmDeatilsMap;
	}

	public FetchProviderDao getFetchProviderDao() {
		return fetchProviderDao;
	}

	public void setFetchProviderDao(FetchProviderDao fetchProviderDao) {
		this.fetchProviderDao = fetchProviderDao;
	}

	public CompanyProfileBOImpl getCompanyProfileBOImpl() {
		return companyProfileBOImpl;
	}

	public void setCompanyProfileBOImpl(
			CompanyProfileBOImpl companyProfileBOImpl) {
		this.companyProfileBOImpl = companyProfileBOImpl;
	}

	public Map getFirmServices(List<String> firmIdList)
			throws BusinessServiceException {

		List<FirmServiceVO> firmServiceVOs = null;
		List<FirmServiceVO> firmServiceList = null;
		Map serviceVOMap = new HashMap();
		try {

			if (firmIdList != null) {
				firmServiceVOs = fetchProviderDao.getFirmServices(firmIdList);
				if (firmServiceVOs != null) {
					for (String firmid : firmIdList) {
						if (StringUtils.isNotBlank(firmid)) {
							firmServiceList = new ArrayList<FirmServiceVO>();
							for (FirmServiceVO firmService : firmServiceVOs) {
								if (firmid.equals(firmService.getFirmId())) {
									firmService.setServiceScope("true");
									firmServiceList.add(firmService);
								}
							}
						}
						serviceVOMap.put(firmid, firmServiceList);
					}
				}
			}
		} catch (Exception ex) {
			logger
					.info("Exception in getFirmServices method of LeadProcessingBO: "
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return serviceVOMap;

	}

	public List<LeadInfoVO> getAllLeadDetails(String firmId, String status,
			String count, String staleAfter) throws BusinessServiceException {
		List<LeadInfoVO> leadInfoVOs = null;
		try {
			leadInfoVOs = fetchProviderDao.getAllLeadDetails(firmId, status,
					count, staleAfter);

		} catch (Exception ex) {
			logger
					.info("Exception in getAllLeadDetails method of LeadProcessingBO: "
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return leadInfoVOs;
	}

	public String validateLeadId(String leadId) throws BusinessServiceException {
		String id;
		try {
			id = fetchProviderDao.validateLeadId(leadId);
		} catch (Exception e) {
			logger.error("Exception in validating customer zip code "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return id;

	}

	public String validateResourceId(Map<String, String> validateMap)
			throws BusinessServiceException {
		String resourceId;
		try {
			resourceId = fetchProviderDao.validateResourceId(validateMap);
		} catch (Exception e) {
			logger.error("Exception in validating Resource Id "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return resourceId;
	}

	public String toValidateFirmId(String firmId)
			throws BusinessServiceException {
		String firmID;
		try {
			firmID = fetchProviderDao.toValidateFirmId(firmId);
		} catch (Exception e) {
			logger.error("Exception in validating firmId " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return firmID;
	}

	public Integer validateLeadStatus(String leadId)
			throws BusinessServiceException {
		Integer resourceId = null;
		try {
			resourceId = surveyDAO.getCompletedResourceIdForLead(leadId);
		} catch (Exception e) {
			logger.error("Exception in validating lead Id status "
					+ e.getMessage());
			return null;
		}
		return resourceId;

	}

	public String validateLeadCompletedStatus(String leadId)
			throws BusinessServiceException {
		String resultLead = null;
		try {
			resultLead = surveyDAO.getCompletedLead(leadId);
		} catch (Exception e) {
			logger.error("Exception in validating lead Id status "
					+ e.getMessage());
			return null;
		}
		return resultLead;

	}

	public void updateMembershipInfo(
			UpdateMembershipInfoRequest updateMembershipInfoRequest)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateMembershipInfo(updateMembershipInfoRequest);
		} catch (Exception e) {
			logger.error("Exception in updating membership info "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

	}

	public void updateProvider(
			AssignOrReassignProviderRequest assignOrReassignProviderRequest)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateProvider(assignOrReassignProviderRequest);
		} catch (Exception e) {
			logger.error("Exception in updating provider " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

	}

	public void updateResourceId(CompleteLeadsRequestVO completeLeadsVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateResourceId(completeLeadsVO);
		} catch (Exception e) {
			logger.error("Exception in updating resourceId" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

	}

	public void updateLeadDetails(CompleteLeadsRequestVO completeLeadsVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateLeadDetails(completeLeadsVO);
		} catch (Exception e) {
			logger.error("Exception in updating Lead Details" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}

	public void updateMatchedFirmDetails(CompleteLeadsRequestVO completeLeadsVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateMatchedFirmDetails(completeLeadsVO);
		} catch (Exception e) {
			logger.error("Exception in updating matched Firm Details"
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}

	public LeadInfoVO getIndividualLeadDetails(Map<String, String> reqMap)
			throws BusinessServiceException {
		LeadInfoVO leadInfo = null;
		try {
			leadInfo = fetchProviderDao.getIndividualLeadDetails(reqMap);
			if (null != leadInfo) {
				String address = "";
				address = leadInfo.getStreet1();
				String street2 = leadInfo.getStreet2();
				if (StringUtils.isNotBlank(street2)) {
					address = address + "," + street2;
				}
				leadInfo.setConcatStreet(address);
			}

		} catch (Exception e) {
			logger.error("Exception in getLeadDetails " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return leadInfo;
	}

	// for validating firmId
	public Boolean validateFirmId(String firmId)
			throws BusinessServiceException {
		Boolean validate = false;
		String id = "";
		try {
			id = fetchProviderDao.validateFirmId(firmId);
			if (!StringUtils.isBlank(id) && id.equals(firmId)) {
				validate = true;
			}

		} catch (Exception e) {
			logger.error("Exception in validateFirmId " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return validate;
	}

	// for validating slLeadId
	public Boolean validateSlLeadId(String slLeadId)
			throws BusinessServiceException {
		Boolean validate = false;
		String id = "";
		try {
			id = fetchProviderDao.validateSlLeadId(slLeadId);
			if (!StringUtils.isBlank(id) && id.equals(slLeadId)) {
				validate = true;
			}
		} catch (Exception e) {
			logger.error("Exception in validateSlLeadId " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return validate;
	}

	// for validate firm for given lead
	public Boolean validateFirmForLead(Map<String, String> reqMap)
			throws BusinessServiceException {
		Boolean validate = false;
		LeadInfoVO leadInfo = null;
		try {
			leadInfo = fetchProviderDao.validateFirmForLead(reqMap);
			if (leadInfo != null) {
				String frimId = reqMap.get(PublicAPIConstant.FIRM_ID);
				String slLeadId = reqMap.get(PublicAPIConstant.SL_LEAD_ID);

				if (leadInfo.getFirmId().equals(Integer.parseInt(frimId))
						&& leadInfo.getSlLeadId().equals(slLeadId)) {
					validate = true;
				}
			}

		} catch (Exception e) {
			logger.error("Exception in validateFirmForLead " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return validate;
	}

	public Map getAvailableSpnForFirms(List<Integer> firmIdList)
			throws BusinessServiceException {
		Map spnMap = new HashMap();
		List<FirmDetailsVO> detailsVO = null;

		try {
			detailsVO = fetchProviderDao.getSpnMapForFirms(firmIdList);
			if (null != detailsVO && !detailsVO.isEmpty()) {
				for (Integer vendorId : firmIdList) {
					List<Integer> spnIdList = new ArrayList<Integer>();
					for (FirmDetailsVO resultList : detailsVO) {
						if (null != resultList
								&& StringUtils.isNotBlank(resultList
										.getFirmId())) {
							if (vendorId.intValue() == Integer
									.parseInt(resultList.getFirmId())) {
								spnIdList.add(resultList.getSpnId());
							}
						}
					}
					spnMap.put(vendorId, spnIdList);
				}
			}
		} catch (DataServiceException e) {
			logger.info("Exception in getting spn available for Firms");
			throw new BusinessServiceException(e.getMessage());
		}
		return spnMap;
	}

	public Map getRankWeigthtages() throws BusinessServiceException {
		Map weigthtageMap = new HashMap();
		try {
			weigthtageMap = fetchProviderDao.getRankCriteriaWeightage();
		} catch (DataServiceException e) {
			logger.info("Exception in getting criteria weightage");
			throw new BusinessServiceException(e.getMessage());
		}
		return weigthtageMap;
	}

	// For GettingCompletesPerWeek for firms
	public Map getCompletesPerWeek(List<Integer> firmIdList)
			throws BusinessServiceException {
		Map completesPerWeekMap = new HashMap();
		Map completesPerWeek = new HashMap();
		List<CompleteVO> completeVOList = null;
		try {
			completeVOList = fetchProviderDao.getCompletesPerWeek(firmIdList);
			for (Integer firmId : firmIdList) {
				List<Date> completeList = new ArrayList<Date>();
				if (null != completeVOList && !completeVOList.isEmpty()) {
					for (CompleteVO resultVO : completeVOList) {
						if (firmId.equals(resultVO.getFirmId())) {
							completeList.add(resultVO.getCompletedDate());
						}
					}
					completesPerWeek.put(firmId, completeList);
				}
			}
			if (null != completesPerWeek && !completesPerWeek.isEmpty()) {
				for (Integer firmId : firmIdList) {
					List<Date> resultList = (List<Date>) completesPerWeek
							.get(firmId);
					int count = 0;
					if (null != resultList && !resultList.isEmpty()) {
						for (Date completedDate : resultList) {
							int i = checkDateInAWeek(completedDate);
							if (i == 1) {
								count++;
							}
						}
					}
					CompleteVO vo = new CompleteVO();
					vo.setFirmId(firmId);
					vo.setNoOfCompletedDate(count);
					completesPerWeekMap.put(firmId, vo);
				}
			}

		} catch (DataServiceException e) {
			logger.info("Exception in getting completes perweek");
			throw new BusinessServiceException(e.getMessage());
		}
		return completesPerWeekMap;
	}

	private int checkDateInAWeek(Date compDate) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentDate = dateFormat.format(date);
		String databaseDate = dateFormat.format(compDate);
		int count = 0;

		String currentYear = currentDate.substring(0, 4);
		int currentYearint = Integer.parseInt(currentYear);

		String currentMonth = currentDate.substring(5, 7);
		int currentMonthint = Integer.parseInt(currentMonth);

		String currentDay = currentDate.substring(8, 10);
		int currentDayint = Integer.parseInt(currentDay);

		String dbYear = databaseDate.substring(0, 4);
		int dbYearint = Integer.parseInt(dbYear);

		String dbMonth = databaseDate.substring(5, 7);
		int dbMonthint = Integer.parseInt(dbMonth);

		String dbDay = databaseDate.substring(8, 10);
		int dbDayint = Integer.parseInt(dbDay);

		Calendar currDate = Calendar.getInstance();
		;
		currDate.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - currDate
				.get(Calendar.DAY_OF_WEEK)));

		if ((currentYearint - dbYearint) == 0
				|| (currentYearint - dbYearint) == -1
				|| (currentYearint - dbYearint) == 1) {
			if ((currentMonthint - dbMonthint) == 0
					|| (currentMonthint - dbMonthint == -1)
					|| (currentMonthint - dbMonthint) == 1) {
				if ((currDate.get(Calendar.DAY_OF_MONTH) - dbDayint < 7)
						&& (currDate.get(Calendar.DAY_OF_MONTH) - dbDayint > 0)
						&& (currDate.get(Calendar.DAY_OF_MONTH) - dbDayint > -7)) {
					count++;

				}

			}
		}
		return count;
	}

	public Integer getSpnId(String electricalLeadCategory)
			throws BusinessServiceException {
		String spnIdString = null;
		Integer spnId = 0;
		try {
			spnIdString = applicationPropertiesDao
					.queryByKey(electricalLeadCategory);
		} catch (Exception e) {
			logger
					.info("Exception in getting spn Id from Application properties");
			throw new BusinessServiceException(e.getMessage());
		}
		if (StringUtils.isNotBlank(spnIdString)) {
			spnId = new Integer(spnIdString);
		}
		return spnId;
	}

	// Method to insert an entry in alert task to send mail to customer
	public void sendConfirmationMailToCustomer(LeadRequest leadRequest,
			List<FirmDetails> firmdetailsList, String lmsResponseStatus) {
		if (StringUtils.isNotBlank(lmsResponseStatus)
				&& PublicAPIConstant.LMSLeadStatus.MATCHED
						.equals(lmsResponseStatus)) {

			// setting template input values to a map
			if (null != firmdetailsList && !firmdetailsList.isEmpty()) {
				Map<String, Object> alertMap = new HashMap<String, Object>();
				// Sorting ProviderFirm List based on Rank from Highest to
				// Lowest(1,2,3);
				Collections.sort(firmdetailsList,
						new Comparator<FirmDetails>() {
							public int compare(FirmDetails o1, FirmDetails o2) {
								return o1.getProviderFirmRank().compareTo(
										o2.getProviderFirmRank());
							}
						});
				for (int i = 0; i < firmdetailsList.size(); i++) {
					String phoneNo = "";
					FirmDetails firmDetail = firmdetailsList.get(i);
					if (null != firmDetail
							&& null != firmDetail.getContact()
							&& StringUtils.isNotBlank(firmDetail.getContact()
									.getPhone())) {
						phoneNo = firmDetail.getContact().getPhone();
						if (StringUtils.isNotBlank(phoneNo))
							phoneNo = formatPhoneNumber(phoneNo);
					}
					// setting template input values to a map
					alertMap.put(NewServiceConstants.FIRM_NAME + i, firmDetail
							.getFirmName());
					alertMap
							.put(NewServiceConstants.FIRM_PHONE_NO + i, phoneNo);
					alertMap.put(NewServiceConstants.FIRM_EMAIL + i, firmDetail
							.getContact().getEmail());
					alertMap.put(NewServiceConstants.FIRM_OWNER + i, firmDetail
							.getFirmOwner());
					alertMap.put(NewServiceConstants.FIRM_CITY + i, firmDetail
							.getContact().getCity());
					alertMap.put(NewServiceConstants.FIRM_STATE + i, firmDetail
							.getContact().getState());
					if (null != firmDetail.getFirmRating()) {
						int score = UIUtils.calculateScoreNumber(firmDetail
								.getFirmRating());
						alertMap.put(NewServiceConstants.RATINGIND + i, score);
					} else {
						alertMap.put(NewServiceConstants.RATINGIND + i, 0);
					}

				}
				if (firmdetailsList.size() == 1) {
					alertMap.put(NewServiceConstants.FIRM_ENABLE1, "N");
					alertMap.put(NewServiceConstants.FIRM_ENABLE2, "N");
				} else if (firmdetailsList.size() == 2) {
					alertMap.put(NewServiceConstants.FIRM_ENABLE1, "Y");
					alertMap.put(NewServiceConstants.FIRM_ENABLE2, "N");
				} else if (firmdetailsList.size() == 3) {
					alertMap.put(NewServiceConstants.FIRM_ENABLE1, "Y");
					alertMap.put(NewServiceConstants.FIRM_ENABLE2, "Y");
				}
				Contact custAddress = leadRequest.getCustContact().getContact();
				String custName = leadRequest.getCustContact().getFirstName()
						+ " " + leadRequest.getCustContact().getLastName();
				alertMap.put(NewServiceConstants.LEAD_FIRST_NAME, leadRequest
						.getCustContact().getFirstName());
				alertMap.put(NewServiceConstants.CUSTOMER_NAME, custName);
				alertMap.put(NewServiceConstants.ADDRESS, custAddress
						.getAddress());
				alertMap.put(NewServiceConstants.CITY, custAddress.getCity());
				alertMap.put(NewServiceConstants.STATE, custAddress.getState());
				alertMap.put(NewServiceConstants.ZIP, custAddress
						.getCustomerZipCode());

				/* Correcting the code to handle empty date and time */

				String serviceDate = "";
				if (null != leadRequest.getServiceDate()) {
					serviceDate = getFormattedDateAsString(leadRequest
							.getServiceDate());
				} else {
					serviceDate = "Not Specified";
				}
				alertMap.put(NewServiceConstants.SERVICE_PREFERRED_DATE,
						serviceDate);
				String startTime = "";
				if (null != leadRequest.getServiceStartTime()) {
					startTime = getFormattedTime(leadRequest
							.getServiceStartTime());
					startTime = startTime + " - ";
				}

				String endTime = "";
				if (null != leadRequest.getServiceEndTime()) {
					endTime = getFormattedTime(leadRequest.getServiceEndTime());
				}
				if (StringUtils.isNotBlank(startTime)
						&& StringUtils.isNotBlank(endTime)) {
					alertMap.put(
							NewServiceConstants.SERVICE_PREFERRED_START_TIME,
							startTime);
					alertMap.put(
							NewServiceConstants.SERVICE_PREFERRED_END_TIME,
							endTime);
				} else {
					alertMap.put(
							NewServiceConstants.SERVICE_PREFERRED_START_TIME,
							"&nbsp;");
					alertMap.put(
							NewServiceConstants.SERVICE_PREFERRED_END_TIME,
							"&nbsp;");
				}
				if (StringUtils.isNotBlank(leadRequest.getSYWRMemberId())) {
					alertMap.put(NewServiceConstants.SYW_ID, leadRequest
							.getSYWRMemberId());
				} else {
					alertMap.put(NewServiceConstants.SYW_ID, "Not Available");
				}

				if (null != leadRequest.getSkill()
						&& leadRequest.getSkill().equalsIgnoreCase("REPAIR")) {
					alertMap.put(NewServiceConstants.SKILL, "Repair");
				} else if (null != leadRequest.getSkill()
						&& leadRequest.getSkill().equalsIgnoreCase("DELIVERY")) {
					alertMap.put(NewServiceConstants.SKILL, "Delivery");
				} else if (null != leadRequest.getSkill()
						&& leadRequest.getSkill().equalsIgnoreCase("INSTALL")) {
					alertMap.put(NewServiceConstants.SKILL, "Install");
				} else {
					alertMap.put(NewServiceConstants.SKILL, leadRequest
							.getSkill());
				}
				alertMap.put(NewServiceConstants.LEAD_REFERENCE, leadRequest
						.getLeadId());
				alertMap.put(NewServiceConstants.SERVICE, leadRequest
						.getClientProjectType());
				// convert the map values to pipe separated key-value pairs
				String templateInputValue = createKeyValueStringFromMap(alertMap);
				// create AlertTask object
				AlertTask alertTask = new AlertTask();
				alertTask.setTemplateInputValue(templateInputValue);
				alertTask.setAlertTo(leadRequest.getCustContact().getContact()
						.getEmail());
				// setting template_id
				alertTask
						.setTemplateId(NewServiceConstants.TEMPLATE_SEND_MAIL_CUSTOMER);

				try {
					// inserting an entry into alert_task
					sendToDestination(alertTask);
				} catch (BusinessServiceException e) {
					logger
							.info("Exception in sendConfirmationMailToCustomer due to "
									+ e.getMessage());
				}

			}

		}

	}

	// Method to insert an entry in alert task to send mail to provider
	public void sendConfirmationMailToProvider(LeadRequest leadRequest,
			List<FirmDetails> firmdetailsList, String lmsResponseStatus) {
		if (StringUtils.isNotBlank(lmsResponseStatus)
				&& PublicAPIConstant.LMSLeadStatus.MATCHED
						.equals(lmsResponseStatus)) {

			// setting template input values to a map
			if (null != firmdetailsList && !firmdetailsList.isEmpty()) {

				for (int i = 0; i < firmdetailsList.size(); i++) {
					Map<String, Object> alertMap = new HashMap<String, Object>();

					FirmDetails firmDetail = firmdetailsList.get(i);
					// setting template input values to a map
					Contact custAddress = leadRequest.getCustContact()
							.getContact();
					String custName = leadRequest.getCustContact()
							.getFirstName()
							+ " " + leadRequest.getCustContact().getLastName();
					String dispatchAddress = firmDetail.getContact()
							.getAddress()
							+ "+"
							+ firmDetail.getContact().getCity()
							+ "+"
							+ firmDetail.getContact().getState()
							+ "+"
							+ firmDetail.getContact().getCustomerZipCode();
					String locationForDirections = custAddress.getState() + "+"
							+ custAddress.getCity() + "+"
							+ custAddress.getCustomerZipCode();
					// /
					// alertMap.put(NewServiceConstants.CUSTOMER_NAME,custName);
					alertMap.put(NewServiceConstants.LEAD_FIRST_NAME,
							leadRequest.getCustContact().getFirstName());
					alertMap.put(NewServiceConstants.LEAD_LAST_NAME,
							leadRequest.getCustContact().getLastName());
					alertMap.put(NewServiceConstants.FIRM_NAME, firmDetail
							.getFirmName());
					alertMap.put(NewServiceConstants.FIRM_DISPATCHADDRESS,
							dispatchAddress);
					alertMap.put(NewServiceConstants.LOCATION_DIRECTIONS,
							locationForDirections);
					alertMap.put(NewServiceConstants.CITY, custAddress
							.getCity());
					alertMap.put(NewServiceConstants.ZIP, custAddress
							.getCustomerZipCode());
					alertMap.put(NewServiceConstants.STATE, custAddress
							.getState());
					alertMap.put(NewServiceConstants.PROJECT_TYPE, leadRequest
							.getProjectType());
					alertMap.put(NewServiceConstants.SKILL, leadRequest
							.getSkill());
					alertMap.put(NewServiceConstants.LEAD_CATEGORY, leadRequest
							.getLeadCategory());
					alertMap.put(NewServiceConstants.URGENCY_OF_SERVICE,
							leadRequest.getUrgencyOfService());
					alertMap.put(NewServiceConstants.PROJECT_DESCRIPTION,
							leadRequest.getProjectDescription());
					alertMap.put(
							NewServiceConstants.ADDITIONAL_PROJECT_DESCRIPTION,
							leadRequest.getSecondaryProjects());
					alertMap.put(NewServiceConstants.CUSTOMER_PHONE_NO,
							custAddress.getPhone());
					alertMap.put(NewServiceConstants.LEAD_REFERENCE,
							leadRequest.getLeadId());
					alertMap.put(NewServiceConstants.CUSTOMER_EMAIL,
							custAddress.getEmail());
					alertMap.put(NewServiceConstants.LEAD_SOURCE, leadRequest
							.getLeadSource());
					alertMap.put(NewServiceConstants.SERVICE_PREFERRED_DATE,
							leadRequest.getServiceDate());
					alertMap.put(
							NewServiceConstants.SERVICE_PREFERRED_START_TIME,
							leadRequest.getServiceStartTime());
					alertMap.put(
							NewServiceConstants.SERVICE_PREFERRED_END_TIME,
							leadRequest.getServiceEndTime());
					// convert the map values to pipe separated key-value pairs
					String templateInputValue = createKeyValueStringFromMap(alertMap);
					// create AlertTask object
					AlertTask alertTask = new AlertTask();
					alertTask.setTemplateInputValue(templateInputValue);
					alertTask.setAlertTo(firmDetail.getContact().getEmail());
					// setting template_id
					alertTask
							.setTemplateId(NewServiceConstants.TEMPLATE_SEND_MAIL_FIRM);
					try {
						// inserting an entry into alert_task
						sendToDestination(alertTask);

					} catch (BusinessServiceException e) {
						logger
								.info("Exception in sendConfirmationMailToProvider due to "
										+ e.getMessage());
					}

				}
			}

		}

	}

	public void sendToDestination(AlertTask alertTask)
			throws BusinessServiceException {
		Date currentdate = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask
				.setCompletionIndicator(NewServiceConstants.INCOMPLETE_INDICATOR);
		alertTask.setAlertTypeId(NewServiceConstants.ALERT_TYPE_ID);
		alertTask.setPriority(NewServiceConstants.PRIORITY);
		alertTask.setAlertFrom(NewServiceConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertBcc(NewServiceConstants.EMPTY_STRING);
		alertTask.setAlertCc(NewServiceConstants.EMPTY_STRING);
		alertTask.setCreatedDate(currentdate);
		alertTask.setModifiedDate(currentdate);
		try {// inserting an entry into alert_task
			alertDao.addAlertToQueue(alertTask);
		} catch (DataServiceException e) {
			logger.info("Exception in inserting to alert Task "
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	// To send rescheduleMailToCustomer
	public void sendRescheduleMailToCustomer(
			ScheduleAppointmentRequest scheduleAppointmentRequest,
			ScheduleAppointmentMailVO scheduleVO)
			throws BusinessServiceException {

		if (null != scheduleAppointmentRequest && null != scheduleVO) {
			Map<String, Object> alertMap = new HashMap<String, Object>();
			alertMap.put(NewServiceConstants.FIRM_NAME, scheduleVO
					.getFirmName());
			alertMap.put(NewServiceConstants.FIRM_EMAIL, scheduleVO
					.getFirmEmail());
			alertMap.put(NewServiceConstants.FIRM_PHONE_NO, scheduleVO
					.getFirmPhoneNo());
			alertMap.put(NewServiceConstants.CUSTOMER_NAME, scheduleVO
					.getCustFirstName()
					+ scheduleVO.getCustLastName());

			String serviceDate = "";
			serviceDate = getFormattedDateAsString(scheduleAppointmentRequest
					.getServiceDate());
			alertMap.put(NewServiceConstants.SERVICE_DATE, serviceDate);
			String serviceStartTime = "";
			serviceStartTime = getFormattedTime(scheduleAppointmentRequest
					.getServiceStartTime());
			alertMap.put(NewServiceConstants.SERVICE_START_TIME,
					serviceStartTime);
			String serviceEndTime = "";
			serviceEndTime = getFormattedTime(scheduleAppointmentRequest
					.getServiceEndTime());
			alertMap.put(NewServiceConstants.SERVICE_END_TIME, serviceEndTime);
			alertMap.put(NewServiceConstants.LEAD_REFERENCE,
					scheduleAppointmentRequest.getLeadId());
			alertMap.put(NewServiceConstants.PROJECT_TYPE, scheduleVO
					.getProjectType());
			alertMap.put(NewServiceConstants.PROJECT_DESCRIPTION, scheduleVO
					.getProjectDescription());
			alertMap.put(NewServiceConstants.ADDITIONAL_PROJECT_DESCRIPTION,
					scheduleVO.getAdditionalProjects());
			alertMap.put(NewServiceConstants.LEAD_FIRST_NAME, scheduleVO
					.getCustFirstName());
			alertMap.put(NewServiceConstants.SERVICE, scheduleVO.getService());
			if (null != scheduleVO.getSkill()
					&& scheduleVO.getSkill().equalsIgnoreCase("REPAIR")) {
				alertMap.put(NewServiceConstants.SKILL, "Repair");
			} else if (null != scheduleVO.getSkill()
					&& scheduleVO.getSkill().equalsIgnoreCase("DELIVERY")) {
				alertMap.put(NewServiceConstants.SKILL, "Delivery");
			} else if (null != scheduleVO.getSkill()
					&& scheduleVO.getSkill().equalsIgnoreCase("INSTALL")) {
				alertMap.put(NewServiceConstants.SKILL, "Install");
			} else {
				alertMap.put(NewServiceConstants.SKILL, scheduleVO.getSkill());
			}
			alertMap.put(NewServiceConstants.FIRM_OWNER, scheduleVO
					.getFirmOwner());
			String leadCategory = scheduleVO.getLeadCategory();
			if (StringUtils.equals(leadCategory, "Electrical")) {
				leadCategory = NewServiceConstants.ELECTRICIAN;
			} else if (StringUtils.equals(leadCategory, "Plumbing")) {
				leadCategory = NewServiceConstants.PLUMBER;
			}
			alertMap.put(NewServiceConstants.LEAD_CATEGORY, leadCategory);
			alertMap.put(NewServiceConstants.CITY, scheduleVO.getServiceCity());
			String address = scheduleVO.getStreet1();
			String street2 = scheduleVO.getStreet2();
			if (StringUtils.isNotBlank(street2)) {
				address = address + "," + street2;
			}
			alertMap.put(NewServiceConstants.ADDRESS, address);
			alertMap.put(NewServiceConstants.STATE, scheduleVO
					.getServiceState());
			alertMap.put(NewServiceConstants.ZIP, scheduleVO.getServiceZip());
			alertMap.put(NewServiceConstants.FIRM_CITY, scheduleVO
					.getFirmCity());
			alertMap.put(NewServiceConstants.FIRM_STATE, scheduleVO
					.getFirmState());
			if (StringUtils.isNotBlank(scheduleVO.getSWYRID())) {
				alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID, scheduleVO
						.getSWYRID());
			} else {
				alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID,
						"Not Available");
			}
			String templateInputValue = createKeyValueStringFromMap(alertMap);

			AlertTask alertTask = new AlertTask();
			alertTask
					.setTemplateId(NewServiceConstants.TEMPLATE_SEND_RESCHEDULE_MAIL);
			alertTask.setAlertTo(scheduleVO.getCustEmail());
			alertTask.setTemplateInputValue(templateInputValue);
			try {
				// inserting an entry into alert_task
				sendToDestination(alertTask);

			} catch (Exception e) {
				logger.info("Exception in sendReschduleMailToCustomer due to "
						+ e.getMessage());
			}
		}
	}

	public void addRatingsAndReview(
			AddRatingsAndReviewRequest addRatingsAndReviewRequest)
			throws BusinessServiceException {
		try {

			SurveyVO surveyVO = new SurveyVO();
			// role Id is Buyer
			surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
			surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
			// setting the userId as 7000.
			surveyVO.setUserId(SurveyConstants.HOMES_SERVICES_BUYER_ID);
			surveyVO.setLeadId(addRatingsAndReviewRequest.getLeadId());
			// Fetch the questions
			surveyVO = retrieveQuestions(surveyVO);
			// for setting answer id
			Map<String, Integer> answerIdMap = new HashMap<String, Integer>();
			answerIdMap = surveyVO.getAnswerIdMap();
			ArrayList questions = surveyVO.getQuestions();
			ArrayList responses = new ArrayList();
			int iQuesSize = questions.size();
			SurveyQuestionVO surveyQuestionVO = null;
			int iAnswerId = 1;
			SurveyResponseVO surveyResponseVO = null;
			ArrayList<SurveyAnswerVO> answers = null;
			SurveyAnswerVO surveyAnswerVO = null;

			for (int i = 0; i < iQuesSize; i++) {

				surveyQuestionVO = (SurveyQuestionVO) questions.get(i);
				responses = new ArrayList();
				surveyResponseVO = new SurveyResponseVO();
				// need to set answer id
				if (PublicAPIConstant.QUESTION_COMMUNICATION == surveyQuestionVO
						.getQuestionId()) {
					iAnswerId = answerIdMap.get(surveyQuestionVO
							.getQuestionId()
							+ "|"
							+ addRatingsAndReviewRequest.getRatings()
									.getCommunication());
				} else if (PublicAPIConstant.QUESTION_QUALITY == surveyQuestionVO
						.getQuestionId()) {
					iAnswerId = answerIdMap.get(surveyQuestionVO
							.getQuestionId()
							+ "|"
							+ addRatingsAndReviewRequest.getRatings()
									.getQuality());

				} else if (PublicAPIConstant.QUESTION_TIMELINESS == surveyQuestionVO
						.getQuestionId()) {
					iAnswerId = answerIdMap.get(surveyQuestionVO
							.getQuestionId()
							+ "|"
							+ addRatingsAndReviewRequest.getRatings()
									.getTimeliness());

				} else if (PublicAPIConstant.QUESTION_PROFESSIONALISM == surveyQuestionVO
						.getQuestionId()) {
					iAnswerId = answerIdMap.get(surveyQuestionVO
							.getQuestionId()
							+ "|"
							+ addRatingsAndReviewRequest.getRatings()
									.getProfessionalism());

				} else if (PublicAPIConstant.QUESTION_VALUE == surveyQuestionVO
						.getQuestionId()) {
					iAnswerId = answerIdMap.get(surveyQuestionVO
							.getQuestionId()
							+ "|"
							+ addRatingsAndReviewRequest.getRatings()
									.getValue());

				} else if (PublicAPIConstant.QUESTION_CLEANLINESS == surveyQuestionVO
						.getQuestionId()) {
					iAnswerId = answerIdMap.get(surveyQuestionVO
							.getQuestionId()
							+ "|"
							+ addRatingsAndReviewRequest.getRatings()
									.getCleanliness());

				}

				surveyResponseVO.setAnswerId(iAnswerId);
				surveyResponseVO.setSurveyId(surveyVO.getSurveyId());
				surveyResponseVO.setEntityTypeId(surveyVO.getEntityTypeId());
				surveyResponseVO.setEntityId(surveyVO.getUserId());
				surveyResponseVO
						.setQuestionId(surveyQuestionVO.getQuestionId());
				responses.add(surveyResponseVO);
				surveyQuestionVO.setResponses(responses);
			}
			if (null != addRatingsAndReviewRequest
					&& null != addRatingsAndReviewRequest.getRatings()) {
				surveyVO.setSurveyComments(UIUtils
						.encodeSpecialChars(addRatingsAndReviewRequest
								.getRatings().getComments()));
			}

			surveyVO.setQuestions(questions);
			surveyVO.setLeadId(addRatingsAndReviewRequest.getLeadId());
			saveResponse(surveyVO);

		} catch (Exception e) {
			logger.error("Exception in adding ratings and review info "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

	}

	public void scheduleAppointment(
			ScheduleAppointmentRequest scheduleAppointmentRequest)
			throws BusinessServiceException {
		try {

			ScheduleRequestVO scheduleRequestVO = new ScheduleRequestVO();
			if (null != scheduleAppointmentRequest.getRecheduleIndicator()) {
				scheduleRequestVO
						.setRescheduleReason(scheduleAppointmentRequest
								.getResheduleReason());
			} else {
				scheduleRequestVO
						.setRescheduleReason(scheduleAppointmentRequest
								.getResheduleReason());
			}
			scheduleRequestVO.setLeadId(scheduleAppointmentRequest.getLeadId());
			scheduleRequestVO.setVendorId(scheduleAppointmentRequest
					.getVendorId());
			scheduleRequestVO.setServiceEndTime(scheduleAppointmentRequest
					.getServiceEndTime());
			scheduleRequestVO.setServiceStartTime(scheduleAppointmentRequest
					.getServiceStartTime());
			// for setting firm status as "scheduled"
			scheduleRequestVO.setStatusId(NewServiceConstants.SCHEDULE);
			Date today = new Date(System.currentTimeMillis());
			DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
			Date serviceDate;
			// setting lmsLeadId in leadInfo to validate sl_lead_hdr
			// leadInfoVO.setLmsLeadId(leadRequest.getLeadId());

			try {
				serviceDate = (Date) formatter.parse(scheduleAppointmentRequest
						.getServiceDate());
				scheduleRequestVO.setServiceDate(serviceDate);
			} catch (ParseException pe) {
				scheduleRequestVO.setServiceDate(today);
			}

			fetchProviderDao.updateScheduleInfo(scheduleRequestVO);
			fetchProviderDao.insertScheduleInfoHistory(scheduleRequestVO);

		} catch (Exception e) {
			logger.error("Exception in schedule Appointment " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

	}

	public String saveResponse(SurveyVO surveyVO) throws DataServiceException {
		logger.info("LeadProcessingBO-->SurveyId=" + surveyVO.getSurveyId());
		SurveyResponseVO surveyResponseVOHdr = new SurveyResponseVO();
		SurveyResponseVO surveyResponseVO = null;
		ProcessResponse processResp = new ProcessResponse();
		Long responseHdrId = new Long(0);
		java.util.Date today = new java.util.Date();
		java.sql.Date now = new java.sql.Date(today.getTime());
		try {
			// Validate that at least one Reponse for each question
			/*
			 * if (!validateQuestions(surveyVO)) { logger
			 * .info("LeadProcessingBO-->validateQuestions--REQUIRED QUESTION'S
			 * ANSWER MISSING"); return null; }
			 */
			// Response Header & SO
			RandomGUID randomGUID = new RandomGUID();
			responseHdrId = randomGUID.generateGUID();
			surveyResponseVOHdr.setLeadId(surveyVO.getLeadId());
			surveyResponseVOHdr.setResponseHdrId(responseHdrId);
			surveyResponseVOHdr.setSurveyId(surveyVO.getSurveyId());
			surveyResponseVOHdr.setEntityTypeId(surveyVO.getEntityTypeId());
			surveyResponseVOHdr.setEntityId(surveyVO.getUserId());
			surveyResponseVOHdr.setOverallScore(0.0);
			surveyResponseVOHdr.setCreatedDate(now);
			surveyResponseVOHdr.setModifiedDate(now);
			surveyResponseVOHdr.setModifiedBy(String.valueOf(surveyVO
					.getUserId()));

			// Calculate Overall Score
			surveyResponseVOHdr.setOverallScore(overallScore(surveyVO));
			surveyResponseVOHdr.setComment(surveyVO.getSurveyComments());
			surveyVO.setOverallScore(surveyResponseVOHdr.getOverallScore());
			surveyDAO.saveResponseHeaderForLead(surveyResponseVOHdr);
			Map<Integer, Integer> answers = new HashMap<Integer, Integer>();

			// Question's answers
			if (surveyVO.getQuestions() != null
					&& surveyVO.getQuestions().size() > 0) {
				ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
				SurveyQuestionVO surveyQuestionVO = null;
				int iQuesSize = questions.size();
				for (int i = 0; i < iQuesSize; i++) {
					surveyQuestionVO = questions.get(i);
					if (surveyQuestionVO.getResponses() != null
							&& surveyQuestionVO.getResponses().size() > 0) {
						ArrayList<SurveyResponseVO> responses = surveyQuestionVO
								.getResponses();
						int iResSize = responses.size();
						for (int j = 0; j < iResSize; j++) {
							surveyResponseVO = responses.get(j);
							surveyResponseVO.setResponseHdrId(responseHdrId);
							surveyResponseVO.setResponseId(randomGUID
									.generateGUID());
							surveyResponseVO.setCreatedDate(now);
							surveyResponseVO.setModifiedDate(now);
							surveyResponseVO.setModifiedBy(String
									.valueOf(surveyVO.getUserId()));
							surveyDAO.saveResponseDetails(surveyResponseVO);
							answers.put(Integer.valueOf(surveyQuestionVO
									.getQuestionId()), Integer
									.valueOf(surveyResponseVO.getAnswerId()));
						}
					}
				}
			}

			// get details of resource id who completed the lead , then update
			// the resource table accordingly.
			Integer resourceId = getSurveyDAO().getCompletedResourceIdForLead(
					surveyVO.getLeadId());
			if (null != resourceId) {
				if (surveyVO.getEntityTypeId() == SurveyConstants.ENTITY_BUYER_ID) {
					getSurveyDAO().updateVendorResourceRatedCountAndRating(
							SurveyConstants.HOMES_SERVICES_BUYER_ID,
							resourceId, surveyResponseVOHdr.getOverallScore());

					if (!answers.isEmpty()) {
						Set<Integer> questions = answers.keySet();
						for (Integer question : questions) {
							getSurveyDAO()
									.updateVendorResourceSurveyRollup(
											resourceId, question,
											answers.get(question));
						}
					}
				}
			}

		} catch (Throwable t) {
			logger.error("SurveyBOImpl-->saveResponse-->Exception-->"
					+ t.getMessage(), t);
			t.printStackTrace();

		} finally {

		}

		return "SUCCESS";
	}

	private boolean validateQuestions(SurveyVO surveyVO) {
		boolean bValid = true;
		if (surveyVO.getQuestions() != null
				&& surveyVO.getQuestions().size() > 0) {
			ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
			SurveyQuestionVO surveyQuestionVO = null;
			SurveyResponseVO surveyResponseVO = null;
			int iQuesSize = questions.size();
			int iRespSize = 0;
			ArrayList<SurveyResponseVO> response = null;
			for (int i = 0; i < iQuesSize; i++) {
				if (surveyVO.isBatch() && i == 4)
					continue;
				surveyQuestionVO = questions.get(i);
				if (surveyQuestionVO.getResponses() == null
						|| surveyQuestionVO.getResponses().size() == 0) {
					// If required Question is not answered
					if (surveyQuestionVO.isRequired()) {
						bValid = false;
						break;
					}
				} else {
					response = surveyQuestionVO.getResponses();
					iRespSize = response.size();
					for (int j = 0; j < iRespSize; j++) {
						surveyResponseVO = response.get(j);
						if (surveyResponseVO.getAnswerId() == 0) {
							bValid = false;
							break;
						}
					}
					// break outer loop
					if (!bValid) {
						break;
					}
				}
			}
		}

		return bValid;
	}

	public SurveyVO retrieveQuestions(SurveyVO pSurveyVO)
			throws DataServiceException {
		SurveyVO surveyVO = pSurveyVO;
		logger.debug("SurveyBOImpl-->SurveyTypeId="
				+ surveyVO.getSurveyTypeId() + "-->EntityTypeId="
				+ surveyVO.getEntityTypeId());
		ProcessResponse processResp = new ProcessResponse();
		try {

			surveyVO.setEntityTypeId(SurveyConstants.HOME_SERVICE_ENTITY_TYPE);
			surveyVO.setSurveyTypeId(SurveyConstants.HOME_SERVICE_SURVEY_TYPE);
			// 1. lead id exist
			// 2. User is authorized to rate/survey the service order
			// 3. Order is closed
			// 4. rating not exist for the lead id
			// 5. retrive questions

			surveyVO = surveyDAO.retrieveQuestions(surveyVO);
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error(
					"LeadProcessinBoImpl-->retrieveQuestions-->Exception-->"
							+ t.getMessage(), t);

		}
		return surveyVO;
	}

	private double overallScore(SurveyVO surveyVO) {
		double totalScore = 0.0;
		logger.info("SurveyBOImpl-->overallScore()");
		if (surveyVO.getQuestions() != null
				&& surveyVO.getQuestions().size() > 0) {
			ArrayList<SurveyQuestionVO> questions = surveyVO.getQuestions();
			ArrayList<SurveyResponseVO> response = null;
			ArrayList<SurveyAnswerVO> answers = null;
			SurveyQuestionVO surveyQuestionVO = null;
			SurveyResponseVO surveyResponseVO = null;
			SurveyAnswerVO surveyAnswerVO = null;
			int iQuesSize = questions.size();
			int iRespSize = 0;
			int iAnsSize = 0;
			for (int i = 0; i < iQuesSize; i++) {
				if (surveyVO.isBatch() && i == 4)
					continue;
				surveyQuestionVO = questions.get(i);
				if (surveyQuestionVO.getResponses() != null
						&& surveyQuestionVO.getResponses().size() > 0) {
					answers = surveyQuestionVO.getAnswers();
					iAnsSize = answers.size();
					response = surveyQuestionVO.getResponses();
					iRespSize = response.size();
					for (int j = 0; j < iRespSize; j++) {
						surveyResponseVO = response.get(j);
						if (surveyResponseVO.getAnswerId() > 0) {
							for (int k = 0; k < iAnsSize; k++) {
								surveyAnswerVO = answers.get(k);
								if (surveyResponseVO.getAnswerId() == surveyAnswerVO
										.getAnswerId()) {
									totalScore = totalScore
											+ surveyAnswerVO.getScore();
									logger
											.debug("SurveyBOImpl-->overallScore()-->Score="
													+ surveyAnswerVO.getScore());
									break;
								}
							}

						}
					}
				}
			}
			if (surveyVO.isBatch())
				totalScore = totalScore / (iQuesSize - 1);
			else
				totalScore = totalScore / iQuesSize;

		}
		logger.info("SurveyBOImpl-->overallScore()-->OverallScore="
				+ totalScore);
		return totalScore;
	}

	/**
	 * Method to format the phone number(0000000000) to 000-000-0000
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static String formatPhoneNumber(String phoneNumber) {
		String formattedPhone = StringUtils.EMPTY;
		if (null != phoneNumber) {
			if (phoneNumber.length() > 2
					&& !phoneNumber.contains(SOPDFConstants.HYPHEN)) {
				formattedPhone = phoneNumber.substring(0, 3);
				if (phoneNumber.length() > 5) {
					formattedPhone = formattedPhone + SOPDFConstants.HYPHEN
							+ phoneNumber.substring(3, 6);
				} else if (phoneNumber.length() > 3) {
					formattedPhone = formattedPhone + SOPDFConstants.HYPHEN
							+ phoneNumber.substring(3);
				}
				if (phoneNumber.length() > 6) {
					formattedPhone = formattedPhone + SOPDFConstants.HYPHEN
							+ phoneNumber.substring(6);
				}
			} else {
				formattedPhone = phoneNumber;
			}
		}
		return formattedPhone;
	}

	/**
	 * convert map to '|' separated key-value pairs
	 * 
	 * @param alertMap
	 * @return String
	 */
	public String createKeyValueStringFromMap(Map<String, Object> alertMap) {
		StringBuilder stringBuilder = new StringBuilder("");
		boolean isFirstKey = true;
		if (null != alertMap) {
			Set<String> keySet = alertMap.keySet();
			for (String key : keySet) {
				if (isFirstKey) {
					isFirstKey = !isFirstKey;
				} else {
					stringBuilder.append("|");
				}

				stringBuilder.append(key).append("=").append(alertMap.get(key));
			}
		}
		return stringBuilder.toString();
	}

	// fetching leadstatus and status id
	public UpdateLeadStatusRequestVO leadStatusValidate(String leadStatus)
			throws BusinessServiceException {
		UpdateLeadStatusRequestVO requestVO = null;
		try {
			requestVO = fetchProviderDao.validateLeadStatus(leadStatus);
		} catch (Exception e) {
			logger.error("Exception in validating lead status "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return requestVO;
	}

	// fetching firmstatus and status id
	public UpdateLeadStatusRequestVO validateFirmStatus(String firmStatus)
			throws BusinessServiceException {
		UpdateLeadStatusRequestVO requestVO = null;
		try {
			requestVO = fetchProviderDao.validateFirmStatus(firmStatus);
		} catch (Exception e) {
			logger.error("Exception in validating lead status "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return requestVO;
	}

	// updating firmstatus
	public void updateMatchedFirmStatus(UpdateLeadStatusRequestVO requestVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateMatchedFirmStatus(requestVO);
		} catch (DataServiceException e) {
			logger.info("Exception in updating firm status" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}

	// updating lead status
	public void updateLeadStatus(UpdateLeadStatusRequestVO requestVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateLeadStatus(requestVO);
		} catch (DataServiceException e) {
			logger.info("Exception in updating lead status" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}

	// for validating lead note category Id
	public Boolean validateNoteCategory(String leadNoteCategoryId)
			throws BusinessServiceException {
		Boolean validate = false;
		String id = "";
		try {
			id = fetchProviderDao
					.validateLeadNoteCategoryId(leadNoteCategoryId);
			if (!StringUtils.isBlank(id) && id.equals(leadNoteCategoryId)) {
				validate = true;
			}

		} catch (Exception e) {
			logger.error("Exception in validateNoteCategory " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return validate;
	}

	// for saving lead notes
	public Integer saveLeadNotes(SLLeadNotesVO leadNotesVO)
			throws BusinessServiceException {
		try {
			Integer leadNoteId = fetchProviderDao.saveLeadNotes(leadNotesVO);
			return leadNoteId;

		} catch (Exception e) {
			logger.error("Exception in saveLeadNotes " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}

	// To get mail details for scheduleAppointment

	public ScheduleAppointmentMailVO getScheduleMailDeatils(
			ScheduleRequestVO scheduleVO) throws BusinessServiceException {

		ScheduleAppointmentMailVO scheduleMailVO = null;
		try {
			scheduleMailVO = fetchProviderDao
					.getScheduleMailDeatils(scheduleVO);

		} catch (DataServiceException e) {
			logger.info("Exception in getScheduleMailDeatils" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

		return scheduleMailVO;

	}

	public String checkProvider(Map<String, String> checkMap)
			throws BusinessServiceException {
		String resourceId = "";
		try {
			resourceId = fetchProviderDao.checkProvider(checkMap);
		} catch (DataServiceException e) {
			logger.info("Exception in updating lead status" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return resourceId;
	}

	public String toValidateFirmIdForLead(Map<String, String> firmLeadMap)
			throws BusinessServiceException {
		String firmId = "";
		try {
			firmId = fetchProviderDao.toValidateFirmId(firmLeadMap);
		} catch (DataServiceException e) {
			logger.info("Exception in updating lead status" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return firmId;
	}

	public void sendConfirmationMailforAddNotes(
			LeadAddNoteRequest leadAddNoteRequest, String emailToAddresses)
			throws BusinessServiceException {

		if (leadAddNoteRequest != null) {
			Map<String, Object> alertMap = new HashMap<String, Object>();
			alertMap.put(NewServiceConstants.LMS_LEAD_REFERENCE,
					leadAddNoteRequest.getLmsLeadId());
			alertMap.put(NewServiceConstants.LEAD_ID, leadAddNoteRequest
					.getLeadId());
			alertMap.put(NewServiceConstants.LEAD_NOTE, leadAddNoteRequest
					.getLeadNote().getNoteBody());
			alertMap.put(NewServiceConstants.LEAD_EMAIL, emailToAddresses);
			alertMap.put(NewServiceConstants.LEAD_TITLE, leadAddNoteRequest
					.getLeadEmailTitle());
			alertMap.put(NewServiceConstants.LEAD_AUTHOR, leadAddNoteRequest
					.getLeadEmailAuthor());
			alertMap.put(NewServiceConstants.LEAD_CUSTOMER_NAME,
					leadAddNoteRequest.getLeadName());
			alertMap.put(NewServiceConstants.LEAD_CUSTOMER_PHONE,
					leadAddNoteRequest.getLeadPhone());
			String templateInputValue = createKeyValueStringFromMap(alertMap);
			// create AlertTask object
			AlertTask alertTask = new AlertTask();

			// setting template_id
			alertTask
					.setTemplateId(NewServiceConstants.TEMPLATE_SEND_MAIL_NOTES);
			alertTask.setAlertTo(emailToAddresses);
			alertTask.setTemplateInputValue(templateInputValue);
			//
			try {
				// inserting an entry into alert_task
				sendToDestination(alertTask);

			} catch (Exception e) {
				logger
						.info("Exception in sendConfirmationMailforAddNotes due to "
								+ e.getMessage());
			}
		}
	}

	public List<String> getProviderEmailIdsForLead(String leadId)
			throws BusinessServiceException {
		List<String> providerEmailIds = null;
		try {
			providerEmailIds = fetchProviderDao
					.getProviderEmailIdsForLead(leadId);
		} catch (Exception e) {
			logger.info("Exception in getting getProviderEmailIdsForLead"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerEmailIds;
	}

	public boolean validateBuyerForLead(Map<String, String> reqMap)
			throws BusinessServiceException {
		Boolean validate = false;
		Integer valid = null;
		try {
			valid = fetchProviderDao.validateBuyerForLead(reqMap);
			if (valid != null) {
				validate = true;
			}

		} catch (Exception e) {
			logger.error("Exception in validateFirmForLead " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return validate;
	}

	// Method to validate leadId and it its status for firm Status updation.
	public String validateSlLeadIdAndStatus(String slLeadId)
			throws BusinessServiceException {
		String leadStatus = null;
		try {
			leadStatus = fetchProviderDao.getLeadStatusToUpdate(slLeadId);
		} catch (DataServiceException e) {
			logger.info("Exception in getting leadSatus" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return leadStatus;
	}

	public String validateLeadFirmStatus(Map<String, String> reqMap)
			throws BusinessServiceException {
		String leadFirmStatus = null;
		try {
			leadFirmStatus = fetchProviderDao.getLeadFirmStatus(reqMap);
		} catch (DataServiceException e) {
			logger
					.info("Exception in getting lead Firm Satus"
							+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return leadFirmStatus;
	}

	// Method to fetch all lead Firm Status from lu table
	public List<String> getAllleadFirmStatus() throws BusinessServiceException {
		List<String> list = null;
		try {
			list = fetchProviderDao.getAllLeadFirmStatus();
		} catch (DataServiceException e) {
			logger.info("Exception in getting All lead Firm Status"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return list;
	}

	public List<ProviderDetailsVO> getEligibleProviders(String firmId,
			String leadId) throws BusinessServiceException {
		List<ProviderDetailsVO> providerList = new ArrayList<ProviderDetailsVO>();
		SLLeadVO leadDetails = null;
		LocationVO locationVO = null;
		int spnId = 0;
		int slNodeId = 0;
		String zip = "";
		try {
			leadDetails = fetchProviderDao.getLeadZipAndSpnForLead(leadId);
			if (null != leadDetails) {
				spnId = leadDetails.getSpnId();
				slNodeId = leadDetails.getSlNodeId();
				zip = leadDetails.getCustomerZipCode();
			}

			// Getting location Vo values to fetch distance
			locationVO = lookupBO.checkIFZipExists(zip);
			if (null != locationVO) {
				// To handle If spnId updated in property file.
				if (spnId != 0) {

					// setting spnId and slNodeId in Vo.
					locationVO.setSpnId(spnId);
					locationVO.setSlNodeId(slNodeId);
					locationVO.setVendorId(firmId);
					locationVO.setWfStatus(NewServiceConstants.SP_SPN_APPROVED);
					locationVO
							.setSpnJoin(NewServiceConstants.JOIN_REQUIRED_TRUE);
					locationVO
							.setSkillJoin(NewServiceConstants.JOIN_REQUIRED_TRUE);
					providerList = fetchProviderDao
							.getEligibleProvidersForLead(locationVO);
				}
				// To handle If spnId is not updated in property file.
				if (spnId == 0
						|| (null != providerList && providerList.isEmpty())) {
					locationVO.setSlNodeId(slNodeId);
					locationVO.setVendorId(firmId);
					// We are filtering spnId and Wf status.
					locationVO.setSpnId(0);
					locationVO.setWfStatus(null);
					// clearing list for sake.
					providerList.clear();
					locationVO
							.setSpnJoin(NewServiceConstants.JOIN_REQUIRED_FALSE);
					locationVO
							.setSkillJoin(NewServiceConstants.JOIN_REQUIRED_TRUE);
					providerList = fetchProviderDao
							.getEligibleProvidersForLead(locationVO);
				}
				if (null != providerList && providerList.isEmpty()) {
					// We are filtering skills
					locationVO.setSlNodeId(0);
					locationVO.setVendorId(firmId);
					locationVO
							.setSpnJoin(NewServiceConstants.JOIN_REQUIRED_FALSE);
					locationVO
							.setSkillJoin(NewServiceConstants.JOIN_REQUIRED_FALSE);
					// clearing list for sake.
					providerList.clear();
					providerList = fetchProviderDao
							.getEligibleProvidersForLead(locationVO);
				}
				if (null != providerList && providerList.isEmpty()) {
					// Since we are not got any eligible providers,making
					// returning empty list;
					providerList = new ArrayList<ProviderDetailsVO>();
				}

			}

		} catch (DataServiceException e) {
			logger.info("Exception in getting eligible providers"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return providerList;
	}

	/**
	 * Description: This method checks whether the given firm id is associated
	 * with given lead id
	 * 
	 * @param ScheduleAppointmentRequest
	 * @throws BusinessServiceException
	 */
	public ScheduleRequestVO validateLeadAssosiationWithFirm(
			ScheduleAppointmentRequest scheduleAppointmentRequest)
			throws BusinessServiceException {
		ScheduleRequestVO sRequestVO = null;
		try {
			sRequestVO = fetchProviderDao
					.validateLeadAssosiationWithFirm(scheduleAppointmentRequest);
		} catch (Exception e) {
			logger.error("Exception in validateFirmForLead " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return sRequestVO;
	}

	public List<SLLeadNotesVO> getNoteList(String LeadId)
			throws BusinessServiceException {
		List<SLLeadNotesVO> leadNotesList = null;
		try {
			leadNotesList = fetchProviderDao.getNoteList(LeadId);
		} catch (Exception e) {
			logger.info("Exception in getting getNoteList " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return leadNotesList;
	}

	public List<LeadHistoryVO> getHistoryList(String LeadId)
			throws BusinessServiceException {
		List<LeadHistoryVO> historyList = null;
		try {
			historyList = fetchProviderDao.getHistoryList(LeadId);
		} catch (Exception e) {
			logger
					.info("Exception in getting getHistoryList "
							+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return historyList;
	}

	// to get lead Attachment details
	public List<LeadAttachmentVO> getAttachmentsList(String LeadId)
			throws BusinessServiceException {
		List<LeadAttachmentVO> attachmentList = null;
		try {
			attachmentList = fetchProviderDao.getAttachmentsList(LeadId);
		} catch (Exception e) {
			logger
					.info("Exception in getting attachmentList "
							+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return attachmentList;
	}

	/**
	 * Description: This method gets all the details related to lead
	 * 
	 * @param SLLeadVO
	 * @throws BusinessServiceException
	 */
	public SLLeadVO getLead(String slLeadId) throws BusinessServiceException {
		SLLeadVO slLeadVO = new SLLeadVO();
		try {
			slLeadVO = fetchProviderDao.getLead(slLeadId);
		} catch (Exception e) {
			logger.info("Exception in getting getLead " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return slLeadVO;
	}

	/**
	 * Description: This method will insert into lead history
	 * 
	 * @param LeadLoggingVO
	 * @throws BusinessServiceException
	 */
	public void insertLeadLogging(LeadLoggingVO leadLoggingVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.insertLeadLogging(leadLoggingVO);
		} catch (DataServiceException e) {
			logger.error("Exception in logging lead history" + e.getCause());
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public boolean getResourceIdForFirm(Map<String, String> firmLeadMap)
			throws BusinessServiceException {
		boolean isValid = false;
		try {
			isValid = fetchProviderDao.getResourceIdForFirm(firmLeadMap);
		} catch (Exception e) {
			logger.info("Exception in getting resource Id for firm "
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return isValid;
	}

	/**
	 * Description: This method will get user name of firm
	 * 
	 * @param String
	 * @throws BusinessServiceException
	 */
	public String getUserName(String firmId) throws BusinessServiceException {
		String userName = "";
		try {
			userName = fetchProviderDao.getUserName(firmId);
			if (null == userName && StringUtils.isBlank(userName)) {
				userName = NewServiceConstants.FIRM_USERNAME;
			}
		} catch (Exception e) {
			logger.info("Exception in getUserName" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return userName;
	}

	/**
	 * Description: This method will get resource name
	 * 
	 * @param String
	 * @throws BusinessServiceException
	 */
	public String getResourceName(String resourceId)
			throws BusinessServiceException {
		String resourceName = "";
		try {
			resourceName = fetchProviderDao.getResourceName(resourceId);
			if (null == resourceName && StringUtils.isBlank(resourceName)) {
				resourceName = NewServiceConstants.RESOURCE_USERNAME;
			}
		} catch (Exception e) {
			logger.info("Exception in resourceName" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resourceName;
	}

	/**
	 * @author naveenkomanthakal_v Description: This method is used to cancel a
	 *         lead
	 * 
	 * @param cancelLeadRequest
	 * @throws BusinessServiceException
	 */
	public int cancelLead(CancelLeadRequest cancelLeadRequest)
			throws BusinessServiceException {
		int response = 0;
		try {

			List<Integer> providerInfoList = new ArrayList<Integer>();
			// converting string to Arraylist
			List<String> providerList1 = new ArrayList<String>(Arrays
					.asList(cancelLeadRequest.getProviders().split(",")));
			for (String s : providerList1) {
				providerInfoList.add(Integer.parseInt(s));
			}
			CancelLeadVO cancelLeadVo = new CancelLeadVO();
			cancelLeadVo
					.setStatus(NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_STATUS_CANCELLED);
			cancelLeadVo.setLeadId(cancelLeadRequest.getLeadId());
			cancelLeadVo.setComment(cancelLeadRequest.getComments());
			cancelLeadVo.setReasonCode(cancelLeadRequest.getReasonCode());
			cancelLeadVo.setRevokePointsInd(cancelLeadRequest
					.isRevokePointsIndicator());
			cancelLeadVo.setProviderList(providerInfoList);
			cancelLeadVo.setChkAllProviderInd(cancelLeadRequest
					.isChkAllProviderInd());
			cancelLeadVo.setBuyer_name(cancelLeadRequest.getVendorBuyerName());
			cancelLeadVo.setRoleId(cancelLeadRequest.getRoleId());
			cancelLeadVo
					.setCancelInitiatedByRoleType(OrderConstants.LEAD_ROLE_TYPE_BUYER);
			response = buyerLeadManagementDao.cancelLead(cancelLeadVo);

		} catch (Exception e) {
			logger.info("Exception in getting getLead " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return response;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDao) {
		this.alertDao = alertDao;
	}

	public SurveyDAO getSurveyDAO() {
		return surveyDAO;
	}

	public void setSurveyDAO(SurveyDAO surveyDAO) {
		this.surveyDAO = surveyDAO;
	}

	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}

	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}

	public BuyerLeadManagementDao getBuyerLeadManagementDao() {
		return buyerLeadManagementDao;
	}

	public void setBuyerLeadManagementDao(
			BuyerLeadManagementDao buyerLeadManagementDao) {
		this.buyerLeadManagementDao = buyerLeadManagementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO#updateLeadNotes(com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO)
	 *      update notes
	 */

	public void updateLeadNotes(SLLeadNotesVO leadNotesVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.updateLeadNotes(leadNotesVO);

		} catch (Exception e) {
			logger.error("Exception in updateLeadNotes " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO#validateLeadNoteIdForLeadId(java.lang.Integer,
	 *      java.lang.String, java.lang.Integer) to validate note id for the
	 *      particular lead
	 */
	public Boolean validateLeadNoteIdForLeadId(Integer noteId, String leadId,
			Integer roles, Integer firmId) throws BusinessServiceException {
		Boolean validate = false;
		Integer id = null;
		try {
			id = fetchProviderDao.validateLeadNoteIdForLeadId(noteId, leadId,
					roles, firmId);
			if (null != id && id.intValue() == noteId.intValue()) {
				validate = true;
			}

		} catch (Exception e) {
			logger.error("Exception in validateNoteCategory " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return validate;
	}

	// to fetch assigned resource rating
	public Double getProviderRating(Integer resourceAssigned) {
		// TODO Auto-generated method stub
		Double rating = new Double(0.00);
		rating = fetchProviderDao.getProviderRating(resourceAssigned);
		return rating;

	}

	public void updateLmsLeadIdAndWfStatus(SLLeadVO leadVO)
			throws BusinessServiceException {
		try {
			// updating lms_id in sl_lead_hdr and lead_wf_status
			fetchProviderDao.updateLmsLeadIdAndWfStatus(leadVO);
		} catch (Exception e) {
			logger.info("Exception in updating lms Lead Id and Wfstatus"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	public String validateSlleadForPost(String leadId)
			throws BusinessServiceException {
		String slLeadId = null;
		try {
			// updating lms_id in sl_lead_hdr and lead_wf_status
			slLeadId = fetchProviderDao.getSlLeadForPostLead(leadId);
		} catch (Exception e) {
			logger.info("Exception in getting slLead Id" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return slLeadId;
	}

	public void saveResponseInfo(List<LeadPostedProVO> matchProviders,
			SLLeadVO leadVO) throws BusinessServiceException {
		try {
			// fetchProviderDao.saveFirmResponse(matchProviders, leadVO);
		} catch (Exception e) {
			logger.info("Exception in saving response in SL" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	// To send mail to customer on cancellation of a Lead

	public void sendConfirmationMailforCancelLeadtoCustomer(
			List<CancelLeadVO> cancelLeadVO, CancelLeadMailVO cancelLeadMailVO)
			throws BusinessServiceException {

		if (cancelLeadVO != null) {

			Map<String, Object> alertMap = new HashMap<String, Object>();
			alertMap.put(NewServiceConstants.LEAD_REFERENCE, cancelLeadVO
					.get(0).getLeadId());
			alertMap.put(NewServiceConstants.CUSTOMER_NAME, cancelLeadVO.get(0)
					.getCustomerFullName());
			alertMap.put(NewServiceConstants.PROJECT_TYPE, cancelLeadVO.get(0)
					.getProjectType());// Constant for skill
			alertMap.put(NewServiceConstants.SERVICE_CATEGORY_DESCRIPTION,
					cancelLeadVO.get(0).getServiceCategoryDescription());// for
																			// service
																			// category
			alertMap.put(NewServiceConstants.SERVICE_DATE, cancelLeadVO.get(0)
					.getPreferredAppointment());
			alertMap.put(NewServiceConstants.SERVICE_PREFERRED_START_TIME,
					cancelLeadVO.get(0).getPreferredStartTime());
			alertMap.put(NewServiceConstants.SERVICE_PREFERRED_END_TIME,
					cancelLeadVO.get(0).getPreferredEndTime());
			alertMap.put(NewServiceConstants.CANCEL_LEAD_FIRM_FIRST_NAME,
					cancelLeadVO.get(0).getCustomerFirstName());
			alertMap.put(NewServiceConstants.CLIENT_PROJECT_DESCRIPTION,
					cancelLeadVO.get(0).getService());// for
														// client_project_description
			alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID, cancelLeadVO
					.get(0).getSwyrId());
			alertMap.put(NewServiceConstants.LEAD_CITY_INFO, cancelLeadVO
					.get(0).getCityInfo());
			alertMap.put(NewServiceConstants.LEAD_STREET_INFO, cancelLeadVO
					.get(0).getStreetInfo());
			alertMap.put(NewServiceConstants.LEAD_ZIP, cancelLeadVO.get(0)
					.getZip());
			// alertMap.put(NewServiceConstants.CANCEL_LEAD_FIRM_FIRST_NAME,cancelLeadVO.get(0).getFirmFirstName());
			// for(int i=0;i<cancelLeadVO.size();i++)
			// {
			// alertMap.put(NewServiceConstants.FIRM_NAME+i,
			// cancelLeadVO.get(i).getFirmFirstName()+"
			// "+cancelLeadVO.get(0).getFirmLastName());
			// }

			String templateInputValue = createKeyValueStringFromMap(alertMap);
			// create AlertTask object
			AlertTask alertTask = new AlertTask();

			// setting template_id
			alertTask
					.setTemplateId(NewServiceConstants.TEMPLATE_SEND_CANCEL_LEAD_CUSTOMER);
			alertTask.setAlertTo(cancelLeadVO.get(0).getCustomerEmail());
			alertTask.setTemplateInputValue(templateInputValue);
			//
			try {
				// inserting an entry into alert_task
				sendToDestination(alertTask);

			} catch (Exception e) {
				logger
						.info("Exception in sendConfirmationMailforCancelLeadtoCustomer due to "
								+ e.getMessage());
			}
		}
	}

	public List<CancelLeadVO> getCancelLeadEmailDetails(
			CancelLeadMailVO cancelLeadMailVO) {
		// TODO Auto-generated method stub

		return buyerLeadManagementDao
				.getCancelLeadEmailDetails(cancelLeadMailVO);

	}

	// To send mail to providers on cancellation of a Lead

	public void sendConfirmationMailforCancelLeadtoProviders(
			List<CancelLeadVO> cancelLeadVO, CancelLeadMailVO cancelLeadMailVO)
			throws BusinessServiceException {

		if (cancelLeadVO != null) {

			Map<String, Object> alertMap = new HashMap<String, Object>();
			alertMap.put(NewServiceConstants.LEAD_REFERENCE, cancelLeadVO
					.get(0).getLeadId());
			alertMap.put(NewServiceConstants.LMS_LEAD_REFERENCE, cancelLeadVO
					.get(0).getLmsLeadId());
			// alertMap.put(NewServiceConstants.CUSTOMER_NAME,cancelLeadVO.get(0).getCustomerFirstName()+cancelLeadVO.get(0).getCustomerLastName());
			// alertMap.put(NewServiceConstants.PROJECT_TYPE,
			// cancelLeadVO.get(0).getProjectType());
			// alertMap.put(NewServiceConstants.ADDITIONAL_PROJECT_DESCRIPTION,
			// cancelLeadVO.get(0).getAdditionalProjects());
			alertMap.put(NewServiceConstants.SERVICE_DATE, cancelLeadVO.get(0)
					.getPreferredAppointment());
			alertMap.put(NewServiceConstants.SERVICE_PREFERRED_START_TIME,
					cancelLeadVO.get(0).getPreferredStartTime());
			alertMap.put(NewServiceConstants.SERVICE_PREFERRED_END_TIME,
					cancelLeadVO.get(0).getPreferredEndTime());
			alertMap.put(NewServiceConstants.CANCEL_COMMENT, cancelLeadMailVO
					.getCancelComments());
			alertMap.put(NewServiceConstants.LEAD_CANCEL_DATE, cancelLeadMailVO
					.getCancelDate());

			for (int i = 0; i < cancelLeadVO.size(); i++) {
				// alertMap.put(NewServiceConstants.EMAIL,
				// cancelLeadVO.get(i).getFirmEmail());
				alertMap.put(NewServiceConstants.CANCEL_LEAD_FIRM_FIRST_NAME,
						cancelLeadVO.get(i).getFirmFirstName());
				String templateInputValue = createKeyValueStringFromMap(alertMap);
				// create AlertTask object
				AlertTask alertTask = new AlertTask();

				// setting template_id
				alertTask
						.setTemplateId(NewServiceConstants.TEMPLATE_SEND_CANCEL_LEAD_PROVIDER);
				alertTask.setAlertTo(cancelLeadVO.get(i).getFirmEmail());
				alertTask.setTemplateInputValue(templateInputValue);
				//
				try {
					// inserting an entry into alert_task
					sendToDestination(alertTask);

				} catch (Exception e) {
					logger
							.info("Exception in sendConfirmationMailforCancelLeadtoCustomer due to "
									+ e.getMessage());
				}

			}

		}
	}

	// method for getting lead and firm details to send reminder mail to lead
	// owner
	public List<LeadReminderVO> getDetailsForRemindMail(String serviceDate)
			throws BusinessServiceException {
		logger.info("Entering getDetailsForRemindMail() of leadprocessing BO");
		List<LeadReminderVO> leadReminderVOs = null;
		try {
			leadReminderVOs = fetchProviderDao
					.getDetailsForRemindMail(serviceDate);
			logger.info("leaving getDetailsForRemindMail() ");
		} catch (DataServiceException e) {
			logger
					.info("Exception in getDetailsForRemindMail"
							+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}

		return leadReminderVOs;

	}

	public SLLeadVO validateRewardPoints(String leadId)
			throws BusinessServiceException {
		SLLeadVO leadVO = null;
		try {
			leadVO = fetchProviderDao.getleadDetailsForRewardPonits(leadId);
		} catch (DataServiceException e) {
			logger.info("Exception in getting lead reward details"
					+ e.getMessage());
		}
		return leadVO;
	}

	public DocumentVO getProviderImageDocument(String resourceId)
			throws BusinessServiceException {
		DocumentVO documentVO = null;
		try {
			documentVO = fetchProviderDao.getProviderImageDocument(resourceId);
		} catch (DataServiceException e) {
			logger.info("Exception in getProviderImageDocument"
					+ e.getMessage());
		}
		return documentVO;
	}

	public String getFormattedTime(String time) {
		DateFormat f1 = new SimpleDateFormat("hh:mm:ss");
		Date formattedDateTime;
		try {
			formattedDateTime = f1.parse(time);
			DateFormat f2 = new SimpleDateFormat("hh:mm aa");
			time = f2.format(formattedDateTime);
		} catch (ParseException e) {
			return time;
		}
		return time;
	}

	public String getFormattedDateAsString(String date) {
		String formattedDateString = "";
		Date formattedDate;
		DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat f2 = new SimpleDateFormat("MM/dd/yyyy");
		try {
			formattedDate = f1.parse(date);
			formattedDateString = f2.format(formattedDate);
		} catch (ParseException e) {
			formattedDateString = date;
		}
		return formattedDateString;

	}

	public CompleteLeadsRequestVO getCompleteMailDetails(
			Map<String, String> mailMap) throws BusinessServiceException {
		CompleteLeadsRequestVO completeLeadsRequestVO = null;
		try {
			completeLeadsRequestVO = fetchProviderDao
					.getCompleteMailDetails(mailMap);
		} catch (DataServiceException e) {
			logger.info("Exception in getCompleteMailDetails" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return completeLeadsRequestVO;
	}

	public String getBuyerName(Integer buyerId) throws BusinessServiceException {
		LeadHistoryVO leadHistoryVO = null;
		String custName = "";
		try {
			leadHistoryVO = fetchProviderDao.getBuyerName(buyerId);
			if (null != leadHistoryVO) {
				custName = leadHistoryVO.getBuyerFirstName();

				if (null != leadHistoryVO.getBuyerLastName()
						&& StringUtils.isNotBlank(leadHistoryVO
								.getBuyerLastName())) {
					custName = custName + " "
							+ leadHistoryVO.getBuyerLastName();
				}
			}
		} catch (DataServiceException e) {

			logger.info("Exception in getBuyerName" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return custName;
	}

	public String getBusinessName(Integer vendorId)
			throws BusinessServiceException {
		String businessName = "";
		try {
			businessName = fetchProviderDao.getBusinessName(vendorId);
		} catch (Exception e) {
			logger.error("Exception in getting businessName" + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return businessName;
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
	public List<FirmDetails> filterFirmsWithLeadProfileAndPrice(
			List<FirmDetails> firmDetailsList,
			FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException {
		List<FirmDetails> resultFirmList = new ArrayList<FirmDetails>();		
		List<Integer> firmIdList = new ArrayList<Integer>();
		List<FirmDetailsVO> firmsWithProfile= new ArrayList<FirmDetailsVO>();
		Map<Integer, FirmDetails> firmsMap = new HashMap<Integer, FirmDetails>();
		// create a list of firm id's
		for (FirmDetails firm : firmDetailsList) {
			firmIdList.add(firm.getFirmId());
			firmsMap.put(firm.getFirmId(),firm);
		}
		fetchProviderFirmRequest.setFirmIdList(firmIdList);
		try {
			firmsWithProfile=fetchProviderDao.filterFirmsWithLeadProfileAndPrice(fetchProviderFirmRequest);
			if(null!=firmsWithProfile && !(firmsWithProfile.isEmpty())){
				for(FirmDetailsVO firm : firmsWithProfile){
					Integer firmId= Integer.parseInt(firm.getFirmId());
					resultFirmList.add(firmsMap.get(firmId));
				}
			}else{
				logger.info("None of the LMS returned firms have lead profile or lead prices in SL for lead id: "
						+fetchProviderFirmRequest.getSlLeadId());
			}
		} catch (Exception e) {
			logger.error("Exception in occurred in filterFirmsWithLeadProfileAndPrice method of LeadProcessingBO: " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return resultFirmList;
	}

	public Integer validateLeadBuyerAssociation(String leadId, Integer buyerId) throws BusinessServiceException {
		Integer id = null;
		try{
			id = fetchProviderDao.validateLeadBuyerAssociation(leadId,buyerId);
		}catch(Exception e){
			logger.error("Exception Error validating Lead Id and buyerId Association: " + e);
			throw new BusinessServiceException(e.getCause());
		}
		return id;
	}

	public Integer validateLeadBuyerId(Integer buyerId)throws BusinessServiceException {
		Integer id = null;
		try{
			id = fetchProviderDao.validateLeadBuyerId(buyerId);
		}catch(Exception e){
			logger.error("Exception Error validating Lead Id and buyerId Association: " + e);
			throw new BusinessServiceException(e.getCause());
		}
		return id;
	}
	/* Added for SL-19727 */
	/**
	 * Description: This method gets all the details related to lead customer
	 * 
	 * @param LeadCustomerDetailsVO
	 * @throws BusinessServiceException
	 */
	public LeadCustomerDetailsVO getLeadCustomerDetails(String slLeadId) throws BusinessServiceException {
		LeadCustomerDetailsVO leadCustomerDetailsVO = new LeadCustomerDetailsVO();
		try {
			leadCustomerDetailsVO = fetchProviderDao.getLeadCustomerDetails(slLeadId);
		} catch (Exception e) {
			logger.info("Exception in getting getLeadCustomerDetails " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return leadCustomerDetailsVO;
	}
	public List<FirmDetailsVO> getLeadFirmDetails(String slLeadId) throws BusinessServiceException {
		List<FirmDetailsVO> firmDetailsVOList = new ArrayList<FirmDetailsVO>();
		try {
			firmDetailsVOList = fetchProviderDao.getLeadFirmDetails(slLeadId);
		} catch (Exception e) {
			logger.info("Exception in getting getLeadFirmDetails " + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return firmDetailsVOList;
	}
	
	/************************************************B2C*****************************************/
	public SLLeadVO savePostLeadFirmInfo(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
			throws BusinessServiceException {
		SLLeadVO leadInfoVO = new SLLeadVO();
		LeadContactInfoVO contactInfoVO = new LeadContactInfoVO();
		LeadStatisticsVO statisticsVO = new LeadStatisticsVO();
		try {
			mapPostLeadInfo(leadInfoVO, contactInfoVO, leadRequest,
					statisticsVO);
			leadInfoVO = fetchProviderDao.savePostLeadInfo(leadInfoVO,
					contactInfoVO, statisticsVO);
		} catch (DataServiceException e) {
			logger.info("Exception in saving PostLead Information"
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return leadInfoVO;
	}

	private void mapPostLeadInfo(SLLeadVO leadInfoVO,
			LeadContactInfoVO contactInfoVO, com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			LeadStatisticsVO statisticsVO) throws BusinessServiceException {
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		Date serviceDate = null;
		// setting lmsLeadId in leadInfo to validate sl_lead_hdr
		// leadInfoVO.setLmsLeadId(leadRequest.getLeadId());
		leadInfoVO.setSlLeadId(leadRequest.getLeadId());
		try {
			if (null != leadRequest.getServiceDate()) {
				serviceDate = (Date) formatter.parse(leadRequest
						.getServiceDate());
			}
		} catch (ParseException pe) {
			logger.error("Error parsing service date" + pe.getMessage());
		}
		if (!leadRequest.isNonLaunchZip()) {
			leadInfoVO.setServiceDate(serviceDate);
			// setting timeframe
			leadInfoVO.setServiceTimeZone(leadRequest.getServiceTimeZone());
			leadInfoVO.setServiceStartTime(leadRequest.getServiceStartTime());
			leadInfoVO.setServiceEndTime(leadRequest.getServiceEndTime());
			leadInfoVO.setModifiedDate(createdDate);
			leadInfoVO.setModifiedBy("SL");
			leadInfoVO.setProjectDescription(leadRequest
					.getProjectDescription());
			leadInfoVO.setSecondaryProjects(leadRequest.getSecondaryProjects());
			// setting leadType based on NO of firms in request
			if (null != leadRequest.getFirmIds()
					&& null != leadRequest.getFirmIds().getFirmId()
					&& !leadRequest.getFirmIds().getFirmId().isEmpty()) {
				int i = leadRequest.getFirmIds().getFirmId().size();
				switch (i) {
				case 1:
					leadInfoVO.setLeadType(PublicAPIConstant.EXCLUSIVE_LEAD);
					break;
				default:
					leadInfoVO.setLeadType(PublicAPIConstant.COMPETITIVE);
					break;
				}

			}
			/*
			 * lead_wf_status column has to be updated with following status
			 * which will be mapped to wf_states table Matched :1,UnMatched:2
			 */
			/*
			 * Setting it as 2 as we dont know wheather it is matched or not
			 * yet.
			 */
			leadInfoVO.setLeadWfStatus(NewServiceConstants.LEAD_UNMATCHED);
		} else {
			leadInfoVO.setNonLaunchZip(true);
		}

		// creating leadStatistics info
		statisticsVO
		.setDataFlowDirection(PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL);
		statisticsVO.setCreatedDate(createdDate);
		statisticsVO.setModifiedDate(createdDate);
		statisticsVO.setCreatedBy("SL");
		statisticsVO.setModifiedBy("SL");
		statisticsVO.setRequestDate(createdDate);
		statisticsVO.setResponseDate(null);
		statisticsVO
		.setTypeOfInteraction(PublicAPIConstant.TypeOfInteraction.POST_LEAD_INTERACTION);

		// Creating contact info
		if (null != leadRequest.getCustContact()
				&& null != leadRequest.getCustContact().getContact()) {
			contactInfoVO.setStreet1(leadRequest.getCustContact().getContact()
					.getAddress());
			contactInfoVO.setCity(leadRequest.getCustContact().getContact()
					.getCity());
			contactInfoVO.setEmail(leadRequest.getCustContact().getContact()
					.getEmail());
			contactInfoVO.setFirstName(leadRequest.getCustContact()
					.getFirstName());
			contactInfoVO.setLastName(leadRequest.getCustContact()
					.getLastName());
			if (null != leadRequest.getCustContact().getContact().getPhone()) {
				contactInfoVO.setPhoneNo(Long.valueOf(leadRequest
						.getCustContact().getContact().getPhone()));
			}
			contactInfoVO.setCreatedDate(createdDate);
			contactInfoVO.setCreatedBy("SL");
			contactInfoVO.setModifiedDate(createdDate);
			contactInfoVO.setModifiedBy("SL");
			contactInfoVO.setState(leadRequest.getCustContact().getContact()
					.getState());
			contactInfoVO.setZipCode(leadRequest.getCustContact().getContact()
					.getCustomerZipCode());
			if (StringUtils.isNotBlank(leadRequest.getSYWRMemberId())
					&& !(leadRequest.isNonLaunchZip() || leadRequest.isNoProvLead())) {
				contactInfoVO.setsWYRMemberId(leadRequest.getSYWRMemberId());
				contactInfoVO
				.setsWYRReward(NewServiceConstants.SWYR_REWARD_POINTS);
			}

		}
	}

	// For fetching the lead pricing for each firms
	public List<FirmDetailsVO> getLeadPriceAndLmsPartnerIdForFirms(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) throws BusinessServiceException {
		List<FirmDetailsVO> firmDetailsVO = null;
		try {
			firmDetailsVO = fetchProviderDao
					.getLeadPriceAndLmsPartnerIdForFirms(leadRequest);
		} catch (Exception ex) {
			logger
			.info("Exception in getLeadPriceForFirms method of LeadProcessingBO: "
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		}
		return firmDetailsVO;
	}

	public Map getFirmDetailsPost(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
			throws BusinessServiceException {
		List<FirmDetailsVO> detailsVOs = null;
		Map firmDeatilsMap = null;
		try {
			detailsVOs = fetchProviderDao.getFirmDetailsPost(leadRequest);
			firmDeatilsMap = new HashMap();
			for (String firmId : leadRequest.getFirmIds().getFirmId()) {
				for (FirmDetailsVO vo : detailsVOs) {
					if (firmId.equals(vo.getFirmId())) {
						firmDeatilsMap.put(firmId, vo);
					}
				}
			}
		} catch (DataServiceException e) {
			logger.info("Exception in getting firm Reviews and Ratings"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return firmDeatilsMap;
	}	

	
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest validatePrimaryProject(
			String primaryProject,
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
					throws BusinessServiceException {
		try {

			fetchProviderFirmRequest = fetchProviderDao.validatePrimaryProject(
					primaryProject, fetchProviderFirmRequest);
		} catch (Exception e) {
			logger.error("Exception in validating primary project "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return fetchProviderFirmRequest;
	}
	
	/**
	 * @param FetchProviderFirmRequest
	 * @return String
	 * @throws BusinessServiceException
	 */
	public String saveLeadInfoforNonLaunchMarket(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest,
			String requestXML)
					throws BusinessServiceException {
		String slLeadId = getNextId("6");
		Date today = new Date();

		SLLeadVO leadInfoVO = new SLLeadVO(slLeadId, fetchProviderFirmRequest
				.getCustomerZipCode(), fetchProviderFirmRequest.getSkill(),
				fetchProviderFirmRequest.getUrgencyOfService(),
				NewServiceConstants.COMPETITIVE_LEAD_TYPE,
				fetchProviderFirmRequest.getLeadSource(), "5",
				fetchProviderFirmRequest.getPrimaryProject(), today, today,
				"SL", "SL", fetchProviderFirmRequest.getClientId(),
				NewServiceConstants.HOME_SERVICES_BUYER_ID);

		LeadStatisticsVO statisticsVO = new LeadStatisticsVO(slLeadId, today,
				today,
				PublicAPIConstant.DataFlowDirection.DATA_FLOW_CLIENT_TO_SL,
				PublicAPIConstant.TypeOfInteraction.GET_FIRM_INTERACTION,
				today, today, "SL", "SL",requestXML);
		try {
			fetchProviderDao.saveLeadInfo(leadInfoVO);
			saveLeadStats(statisticsVO);
		} catch (DataServiceException e) {
			logger.error("Exception in saving Lead Information" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}

		return slLeadId;
	}
	
	public Map getFirmDetails(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
			throws BusinessServiceException {
		List<FirmDetailsVO> detailsVOs = null;
		Map firmDeatilsMap = null;
		try {
			detailsVOs = fetchProviderDao
					.getFirmDetails(fetchProviderFirmRequest);
			firmDeatilsMap = new HashMap();
			for (Integer firmId : fetchProviderFirmRequest.getFirmIds()) {
				for (FirmDetailsVO vo : detailsVOs) {
					if (null != firmId
							&& null != vo.getFirmId()
							&& firmId.intValue() == Integer.parseInt(vo
									.getFirmId())) {
						firmDeatilsMap.put(firmId.intValue(), vo);
					}
				}
			}
		} catch (DataServiceException e) {
			logger.info("Exception in getting firm Reviews and Ratings"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

		return firmDeatilsMap;
	}
	
	public List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> filterFirmsWithLeadProfileAndPrice(
			List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmDetailsList,
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest)
					throws BusinessServiceException {
		List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> resultFirmList = new ArrayList<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails>();		
		List<Integer> firmIdList = new ArrayList<Integer>();
		List<FirmDetailsVO> firmsWithProfile= new ArrayList<FirmDetailsVO>();
		Map<Integer, com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmsMap = new HashMap<Integer, com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails>();
		// create a list of firm id's
		for (com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails firm : firmDetailsList) {
			firmIdList.add(firm.getFirmId());
			firmsMap.put(firm.getFirmId(),firm);
		}
		fetchProviderFirmRequest.setFirmIdList(firmIdList);
		try {
			firmsWithProfile=fetchProviderDao.filterFirmsWithLeadProfileAndPrice(fetchProviderFirmRequest);
			if(null!=firmsWithProfile && !(firmsWithProfile.isEmpty())){
				for(FirmDetailsVO firm : firmsWithProfile){
					Integer firmId= Integer.parseInt(firm.getFirmId());
					resultFirmList.add(firmsMap.get(firmId));
				}
			}else{
				logger.info("None of the LMS returned firms have lead profile or lead prices in SL for lead id: "
						+fetchProviderFirmRequest.getSlLeadId());
			}
		} catch (Exception e) {
			logger.error("Exception in occurred in filterFirmsWithLeadProfileAndPrice method of LeadProcessingBO: " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return resultFirmList;
	}

	public boolean isLeadNonLaunchSwitch() throws BusinessServiceException {
		boolean isLeadNonLaunchSwitch =  false;
		try {
			isLeadNonLaunchSwitch = fetchProviderDao.isLeadNonLaunchSwitch();
		} catch (DataServiceException e) {
			logger.error("Exception in occurred in isLeadNonLaunchSwitch method of LeadProcessingBO: " + e.getMessage());
			e.printStackTrace();
		}
		return isLeadNonLaunchSwitch;
	}

	public String validateAllZip(String custZip)
			throws BusinessServiceException {
		String id;
		try {
			id = fetchProviderDao.validateAllZip(custZip);
		} catch (Exception e) {
			logger.error("Exception in validating customer zip code "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return id;
	}
	
	public List<Integer> getFirmIds(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest) 
			throws BusinessServiceException{
		List<Integer> firmIds = null;
		try{
			firmIds = fetchProviderDao.getFirmIds(fetchProviderFirmRequest);
		}catch (Exception e) {
			logger.error("Exception in getFirmIds() in getting the firm ids "
					+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return firmIds;
	}
	
	public Map<String, String> validateLeadPostFirmIds(String slLeadId, FirmIds firmIds) throws BusinessServiceException{
		List<String> invalidFirms = new ArrayList<String>();
		List<String> invalidPostFirms = new ArrayList<String>();
		StringBuffer invalidFirmIds = new StringBuffer();
		Map<String, String> validateResponse = new HashMap<String, String>();
		try {
			invalidFirms.addAll(firmIds.getFirmId());
			invalidFirms.removeAll(fetchProviderDao.validateFirmsIdsPosted(slLeadId));
			if(!invalidFirms.isEmpty()){
				invalidPostFirms.addAll(invalidFirms);
				invalidFirms.removeAll(fetchProviderDao.validateFirmsIds(invalidFirms));
				if(!invalidFirms.isEmpty()){
					for(String firmId : invalidFirms){
						invalidFirmIds.append(firmId);
						invalidFirmIds.append(", ");
					}
					if(invalidFirmIds.length() > 1){
						invalidFirmIds.delete(invalidFirmIds.length()-2, invalidFirmIds.length()-1);
					}
					validateResponse.put("validationType", NewServiceConstants.INVALID_FIRM_ID);
					validateResponse.put("invalidFirms", invalidFirmIds.toString());
					return validateResponse;
				}
				for(String firmId : invalidPostFirms){
					invalidFirmIds.append(firmId);
					invalidFirmIds.append(", ");
				}
				if(invalidFirmIds.length() > 1){
					invalidFirmIds.delete(invalidFirmIds.length()-2, invalidFirmIds.length()-1);
				}
				validateResponse.put("validationType", NewServiceConstants.LEAD_NOT_POSTED_TO_FIRM);
				validateResponse.put("invalidFirms", invalidFirmIds.toString());
				return validateResponse;
			}
			invalidFirms = fetchProviderDao.validateLeadMatchedFirmIds(slLeadId);
			if(!invalidFirms.isEmpty()){
				for(String firmId : firmIds.getFirmId()){
					if(invalidFirms.contains(firmId)){
						invalidFirmIds.append(firmId);
						invalidFirmIds.append(", ");
					}
				}
				if(invalidFirmIds.length() > 1){
					invalidFirmIds.delete(invalidFirmIds.length()-2, invalidFirmIds.length()-1);
					validateResponse.put("validationType", NewServiceConstants.LEAD_ALREADY_MATCHED_TO_FIRM);
					validateResponse.put("invalidFirms", invalidFirmIds.toString());
					return validateResponse;
				}
			}
			validateResponse = null;
		} catch (Exception ex) {
			logger
			.info("Exception in validateLeadPostFirmIds method of LeadProcessingBO: "
					+ ex.getMessage());
			throw new BusinessServiceException(ex);
		}
		return validateResponse;
	}

	public void saveMatchedProvidersInfo(
			List<LeadMatchingProVO> matchedProviders, com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest)
					throws BusinessServiceException {
		try {
			fetchProviderDao.saveMatchedProviders(matchedProviders, leadRequest.getLeadId());
			// validating the vendors before saving posted vendors info
			/*
			 * List<String> postedVendors = fetchProviderDao
			 * .getAllProvidersPosted(statisticsVO.getSlLeadId()); List<String>
			 * allProviders = leadRequest.getFirmIds().getFirmId(); if (null !=
			 * postedVendors && null != allProviders) { if
			 * (postedVendors.containsAll(allProviders)) {
			 * fetchProviderDao.saveMatchedProviders(matchedProviders,
			 * statisticsVO); } }
			 */
		} catch (Exception e) {
			logger.info("Exception in saving matched Providers SL"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}
	
	public SLLeadVO savePostLeadFirmInfo(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest, String requestXml)
			throws BusinessServiceException {
		SLLeadVO leadInfoVO = new SLLeadVO();
		LeadContactInfoVO contactInfoVO = new LeadContactInfoVO();
		LeadStatisticsVO statisticsVO = new LeadStatisticsVO();
		try {
			mapPostLeadInfo(leadInfoVO, contactInfoVO, leadRequest,
					statisticsVO);
			statisticsVO.setReqResXML(requestXml);
			leadInfoVO = fetchProviderDao.savePostLeadInfo(leadInfoVO,
					contactInfoVO, statisticsVO);
		} catch (DataServiceException e) {
			logger.info("Exception in saving PostLead Information"
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return leadInfoVO;

	}
	
	// Method to insert an entry in alert task to send mail to customer
	public void sendConfirmationMailToCustomer(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmdetailsList, String leadStatus) {
		// setting template input values to a map
		if (null != firmdetailsList && !firmdetailsList.isEmpty()) {
			Map<String, Object> alertMap = new HashMap<String, Object>();
			// Sorting ProviderFirm List based on Rank from Highest to
			// Lowest(1,2,3);
			Collections.sort(firmdetailsList,
					new Comparator<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails>() {
				public int compare(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails o1, 
						com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails o2) {
					return o1.getProviderFirmRank().compareTo(
							o2.getProviderFirmRank());
				}
			});
			for (int i = 0; i < firmdetailsList.size(); i++) {
				String phoneNo = "";
				com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails firmDetail = firmdetailsList.get(i);
				if (null != firmDetail
						&& null != firmDetail.getContact()
						&& StringUtils.isNotBlank(firmDetail.getContact()
								.getPhone())) {
					phoneNo = firmDetail.getContact().getPhone();
					if (StringUtils.isNotBlank(phoneNo))
						phoneNo = formatPhoneNumber(phoneNo);
				}
				// setting template input values to a map
				alertMap.put(NewServiceConstants.FIRM_NAME + i, (null == firmDetail
						.getFirmName() ? "" : firmDetail.getFirmName()));
				alertMap
				.put(NewServiceConstants.FIRM_PHONE_NO + i, phoneNo);
				alertMap.put(NewServiceConstants.FIRM_EMAIL + i, firmDetail
						.getContact().getEmail());
				alertMap.put(NewServiceConstants.FIRM_OWNER + i, firmDetail
						.getFirmOwner());
				alertMap.put(NewServiceConstants.FIRM_CITY + i, firmDetail
						.getContact().getCity());
				alertMap.put(NewServiceConstants.FIRM_STATE + i, firmDetail
						.getContact().getState());
				if (null != firmDetail.getFirmRating()) {
					int score = UIUtils.calculateScoreNumber(firmDetail
							.getFirmRating());
					alertMap.put(NewServiceConstants.RATINGIND + i, score);
				} else {
					alertMap.put(NewServiceConstants.RATINGIND + i, 0);
				}

			}
			if (firmdetailsList.size() == 1) {
				alertMap.put(NewServiceConstants.FIRM_ENABLE1, "N");
				alertMap.put(NewServiceConstants.FIRM_ENABLE2, "N");
			} else if (firmdetailsList.size() == 2) {
				alertMap.put(NewServiceConstants.FIRM_ENABLE1, "Y");
				alertMap.put(NewServiceConstants.FIRM_ENABLE2, "N");
			} else if (firmdetailsList.size() == 3) {
				alertMap.put(NewServiceConstants.FIRM_ENABLE1, "Y");
				alertMap.put(NewServiceConstants.FIRM_ENABLE2, "Y");
			}
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.Contact custAddress = leadRequest.getCustContact().getContact();
			String custName = leadRequest.getCustContact().getFirstName()
					+ " " + leadRequest.getCustContact().getLastName();
			alertMap.put(NewServiceConstants.LEAD_FIRST_NAME, leadRequest
					.getCustContact().getFirstName());
			alertMap.put(NewServiceConstants.CUSTOMER_NAME, custName);
			alertMap.put(NewServiceConstants.ADDRESS, custAddress
					.getAddress());
			alertMap.put(NewServiceConstants.CITY, custAddress.getCity());
			alertMap.put(NewServiceConstants.STATE, custAddress.getState());
			alertMap.put(NewServiceConstants.ZIP, custAddress
					.getCustomerZipCode());

			/* Correcting the code to handle empty date and time */

			String serviceDate = "";
			if (null != leadRequest.getServiceDate()) {
				serviceDate = getFormattedDateAsString(leadRequest
						.getServiceDate());
			} else {
				serviceDate = "Not Specified";
			}
			alertMap.put(NewServiceConstants.SERVICE_PREFERRED_DATE,
					serviceDate);
			String startTime = "";
			if (null != leadRequest.getServiceStartTime()) {
				startTime = getFormattedTime(leadRequest
						.getServiceStartTime());
				startTime = startTime + " - ";
			}

			String endTime = "";
			if (null != leadRequest.getServiceEndTime()) {
				endTime = getFormattedTime(leadRequest.getServiceEndTime());
			}
			if (StringUtils.isNotBlank(startTime)
					&& StringUtils.isNotBlank(endTime)) {
				alertMap.put(
						NewServiceConstants.SERVICE_PREFERRED_START_TIME,
						startTime);
				alertMap.put(
						NewServiceConstants.SERVICE_PREFERRED_END_TIME,
						endTime);
			} else {
				alertMap.put(
						NewServiceConstants.SERVICE_PREFERRED_START_TIME,
						"&nbsp;");
				alertMap.put(
						NewServiceConstants.SERVICE_PREFERRED_END_TIME,
						"&nbsp;");
			}
			if (StringUtils.isNotBlank(leadRequest.getSYWRMemberId())) {
				alertMap.put(NewServiceConstants.SYW_ID, leadRequest
						.getSYWRMemberId());
			} else {
				alertMap.put(NewServiceConstants.SYW_ID, "Not Available");
			}

			if (null != leadRequest.getSkill()
					&& leadRequest.getSkill().equalsIgnoreCase("REPAIR")) {
				alertMap.put(NewServiceConstants.SKILL, "Repair");
			} else if (null != leadRequest.getSkill()
					&& leadRequest.getSkill().equalsIgnoreCase("DELIVERY")) {
				alertMap.put(NewServiceConstants.SKILL, "Delivery");
			} else if (null != leadRequest.getSkill()
					&& leadRequest.getSkill().equalsIgnoreCase("INSTALL")) {
				alertMap.put(NewServiceConstants.SKILL, "Install");
			} else {
				alertMap.put(NewServiceConstants.SKILL, leadRequest
						.getSkill());
			}
			alertMap.put(NewServiceConstants.LEAD_REFERENCE, leadRequest
					.getLeadId());
			alertMap.put(NewServiceConstants.SERVICE, leadRequest
					.getClientProjectType());
			// convert the map values to pipe separated key-value pairs
			String templateInputValue = createKeyValueStringFromMap(alertMap);
			// create AlertTask object
			AlertTask alertTask = new AlertTask();
			alertTask.setTemplateInputValue(templateInputValue);
			alertTask.setAlertTo(leadRequest.getCustContact().getContact()
					.getEmail());
			// setting template_id
			alertTask
			.setTemplateId(NewServiceConstants.TEMPLATE_SEND_MAIL_CUSTOMER);

			try {
				// inserting an entry into alert_task
				sendToDestination(alertTask);
			} catch (BusinessServiceException e) {
				logger
				.info("Exception in sendConfirmationMailToCustomer due to "
						+ e.getMessage());
			}
		}
	}

	// Method to insert an entry in alert task to send mail to provider
	public void sendConfirmationMailToProvider(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmdetailsList, String leadStatus) {
		// setting template input values to a map
		if (null != firmdetailsList && !firmdetailsList.isEmpty()) {

			for (int i = 0; i < firmdetailsList.size(); i++) {
				Map<String, Object> alertMap = new HashMap<String, Object>();

				com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails firmDetail = firmdetailsList.get(i);
				// setting template input values to a map
				com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.Contact custAddress = leadRequest.getCustContact()
						.getContact();
				String custName = leadRequest.getCustContact()
						.getFirstName()
						+ " " + leadRequest.getCustContact().getLastName();
				String dispatchAddress = firmDetail.getContact()
						.getAddress()
						+ "+"
						+ firmDetail.getContact().getCity()
						+ "+"
						+ firmDetail.getContact().getState()
						+ "+"
						+ firmDetail.getContact().getCustomerZipCode();
				String locationForDirections = custAddress.getState() + "+"
						+ custAddress.getCity() + "+"
						+ custAddress.getCustomerZipCode();
				// /
				// alertMap.put(NewServiceConstants.CUSTOMER_NAME,custName);
				alertMap.put(NewServiceConstants.LEAD_FIRST_NAME,
						(null == leadRequest.getCustContact().getFirstName() ? "" : leadRequest.getCustContact().getFirstName()));
				alertMap.put(NewServiceConstants.LEAD_LAST_NAME,
						(null == leadRequest.getCustContact().getLastName() ? "" : leadRequest.getCustContact().getLastName()));
				alertMap.put(NewServiceConstants.FIRM_NAME, firmDetail
						.getFirmName());
				alertMap.put(NewServiceConstants.FIRM_DISPATCHADDRESS,
						dispatchAddress);
				alertMap.put(NewServiceConstants.LOCATION_DIRECTIONS,
						locationForDirections);
				alertMap.put(NewServiceConstants.CITY, (null == custAddress
						.getCity() ? "" : custAddress.getCity()));
				alertMap.put(NewServiceConstants.ZIP, custAddress
						.getCustomerZipCode());
				alertMap.put(NewServiceConstants.STATE, custAddress
						.getState());
				alertMap.put(NewServiceConstants.PROJECT_TYPE, leadRequest
						.getProjectType());
				alertMap.put(NewServiceConstants.SKILL, leadRequest
						.getSkill());
				alertMap.put(NewServiceConstants.LEAD_CATEGORY, leadRequest
						.getLeadCategory());
				alertMap.put(NewServiceConstants.URGENCY_OF_SERVICE,
						leadRequest.getUrgencyOfService());
				alertMap.put(NewServiceConstants.PROJECT_DESCRIPTION,
						(null == leadRequest.getProjectDescription() ? "" : leadRequest.getProjectDescription()));
				alertMap.put(
						NewServiceConstants.ADDITIONAL_PROJECT_DESCRIPTION,
						(null == leadRequest.getSecondaryProjects() ? "" : leadRequest.getSecondaryProjects()));
				alertMap.put(NewServiceConstants.CUSTOMER_PHONE_NO,
						(null == custAddress.getPhone() ? "" : custAddress.getPhone()));
				alertMap.put(NewServiceConstants.LEAD_REFERENCE,
						leadRequest.getLeadId());
				alertMap.put(NewServiceConstants.CUSTOMER_EMAIL,
						custAddress.getEmail());
				alertMap.put(NewServiceConstants.LEAD_SOURCE, (null == leadRequest
						.getLeadSource() ? "" : leadRequest.getLeadSource()));
				alertMap.put(NewServiceConstants.SERVICE_PREFERRED_DATE,
						leadRequest.getServiceDate());
				alertMap.put(
						NewServiceConstants.SERVICE_PREFERRED_START_TIME,
						leadRequest.getServiceStartTime());
				alertMap.put(
						NewServiceConstants.SERVICE_PREFERRED_END_TIME,
						leadRequest.getServiceEndTime());
				// convert the map values to pipe separated key-value pairs
				String templateInputValue = createKeyValueStringFromMap(alertMap);
				// create AlertTask object
				AlertTask alertTask = new AlertTask();
				alertTask.setTemplateInputValue(templateInputValue);
				alertTask.setAlertTo(firmDetail.getContact().getEmail());
				// setting template_id
				alertTask
				.setTemplateId(NewServiceConstants.TEMPLATE_SEND_MAIL_FIRM);
				try {
					// inserting an entry into alert_task
					sendToDestination(alertTask);

				} catch (BusinessServiceException e) {
					logger
					.info("Exception in sendConfirmationMailToProvider due to "
							+ e.getMessage());
				}
			}
		}
	}
	
	public String getPropertyValue(String key) throws BusinessServiceException {
		String value =  "0";
		try {
			value = applicationPropertiesDao.queryByKey(key);
		} catch (DataNotFoundException e) {
			logger
			.info("Exception in getPropertyValue method of LeadProcessingBO: "
					+ e.getMessage());
			
		} catch (DataAccessException e) {
			logger
			.info("Exception in getPropertyValue method of LeadProcessingBO: "
					+ e.getMessage());
		}
		return value;
	}
	
	public void saveResponseInfoV2(List<LeadPostedProVO> matchProviders,
			LeadStatisticsVO statisticsVO, SLLeadVO leadVO)
			throws BusinessServiceException {
		try {
			fetchProviderDao.saveFirmResponse(matchProviders, statisticsVO,
					leadVO);
		} catch (Exception e) {
			logger.info("Exception in saving response in SL" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}

	}

	//SL-20893 Send customer mail for unmatched and out of area leads
	public void sendConfirmationMailToCustomerForUnmatchedAndOutOfArea(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) {
		// setting template input values to a map
		Map<String, Object> alertMap = new HashMap<String, Object>();
		com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.Contact custAddress = leadRequest.getCustContact().getContact();
		String custName = leadRequest.getCustContact().getFirstName()
				+ " " + leadRequest.getCustContact().getLastName();
		alertMap.put(NewServiceConstants.LEAD_FIRST_NAME, leadRequest
				.getCustContact().getFirstName());
		alertMap.put(NewServiceConstants.CUSTOMER_NAME, custName);
		alertMap.put(NewServiceConstants.ADDRESS, custAddress
				.getAddress());
		alertMap.put(NewServiceConstants.CITY, custAddress.getCity());
		alertMap.put(NewServiceConstants.STATE, custAddress.getState());
		alertMap.put(NewServiceConstants.ZIP, custAddress
				.getCustomerZipCode());

		/* Correcting the code to handle empty date and time */

		String serviceDate = "";
		if (null != leadRequest.getServiceDate()) {
			serviceDate = getFormattedDateAsString(leadRequest
					.getServiceDate());
		} else {
			serviceDate = "Not Specified";
		}
		alertMap.put(NewServiceConstants.SERVICE_PREFERRED_DATE,
				serviceDate);
		String startTime = "";
		if (null != leadRequest.getServiceStartTime()) {
			startTime = getFormattedTime(leadRequest
					.getServiceStartTime());
			startTime = startTime + " - ";
		}

		String endTime = "";
		if (null != leadRequest.getServiceEndTime()) {
			endTime = getFormattedTime(leadRequest.getServiceEndTime());
		}
		if (StringUtils.isNotBlank(startTime)
				&& StringUtils.isNotBlank(endTime)) {
			alertMap.put(
					NewServiceConstants.SERVICE_PREFERRED_START_TIME,
					startTime);
			alertMap.put(
					NewServiceConstants.SERVICE_PREFERRED_END_TIME,
					endTime);
		} else {
			alertMap.put(
					NewServiceConstants.SERVICE_PREFERRED_START_TIME,
					"&nbsp;");
			alertMap.put(
					NewServiceConstants.SERVICE_PREFERRED_END_TIME,
					"&nbsp;");
		}
		if (StringUtils.isNotBlank(leadRequest.getSYWRMemberId())) {
			alertMap.put(NewServiceConstants.SYW_ID, leadRequest
					.getSYWRMemberId());
		} else {
			alertMap.put(NewServiceConstants.SYW_ID, "Not Available");
		}

		if (null != leadRequest.getSkill()
				&& leadRequest.getSkill().equalsIgnoreCase("REPAIR")) {
			alertMap.put(NewServiceConstants.SKILL, "Repair");
		} else if (null != leadRequest.getSkill()
				&& leadRequest.getSkill().equalsIgnoreCase("DELIVERY")) {
			alertMap.put(NewServiceConstants.SKILL, "Delivery");
		} else if (null != leadRequest.getSkill()
				&& leadRequest.getSkill().equalsIgnoreCase("INSTALL")) {
			alertMap.put(NewServiceConstants.SKILL, "Install");
		} else {
			alertMap.put(NewServiceConstants.SKILL, leadRequest
					.getSkill());
		}
		alertMap.put(NewServiceConstants.LEAD_REFERENCE, leadRequest
				.getLeadId());
		alertMap.put(NewServiceConstants.SERVICE, leadRequest
				.getClientProjectType());
		// convert the map values to pipe separated key-value pairs
		String templateInputValue = createKeyValueStringFromMap(alertMap);
		// create AlertTask object
		AlertTask alertTask = new AlertTask();
		alertTask.setTemplateInputValue(templateInputValue);
		alertTask.setAlertTo(leadRequest.getCustContact().getContact()
				.getEmail());
		// setting template_id
		alertTask
		.setTemplateId(NewServiceConstants.TEMPLATE_SEND_MAIL_CUSTOMER_UNMATCHED_OUT_OF_AREA);

		try {
			// inserting an entry into alert_task
			sendToDestination(alertTask);
		} catch (BusinessServiceException e) {
			logger
			.info("Exception in sendConfirmationMailToCustomerForUnmatchedAndOutOfArea due to "
					+ e.getMessage());
		}
	}
	
	/**
	 *  
	 */
	public void saveEmployeesList(List<Employee> empList) throws BusinessServiceException {
		try {
			fetchProviderDao.saveEmployeesList(empList);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating saveEmployeesList and ignoring", e);
		}
	}
	
	/**
	 *  
	 */
	public void saveCampaignList(List<Campaign> campList) throws BusinessServiceException {
		try {
			fetchProviderDao.saveCampaignList(campList);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating saveCampaignList and ignoring", e);
		}
	}
	
	
	/**
	 *  
	 */
	public void saveCdrList(List<Cdr> cdrList) throws BusinessServiceException {
		try {
			fetchProviderDao.saveCdrList(cdrList);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating saveCdrList and ignoring", e);
		}
	}
	
	/**
	 *  
	 */
	public void saveImpressionsList(List<Impressions> impsList) throws BusinessServiceException {
		try {
			fetchProviderDao.saveImpressionsList(impsList);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating saveCdrList and ignoring", e);
		}
	}
	
	
	/**
	 *  
	 */
	public Integer getLatestId(Integer type) throws BusinessServiceException {
		Integer latestId = 0;
		try {
			latestId =  fetchProviderDao.getLatestId(type);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating saveCdrList and ignoring", e);
		}
		return latestId;
	}
	
	/**
	 * 
	 */

	public void truncateData(Integer type) throws BusinessServiceException{
		try {
			fetchProviderDao.truncateData(type);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating truncateData and ignoring", e);
		}
	}
	
	
	
	public Integer saveInsidesSalesLeadInfo(InsideSalesLeadVO insideSalesLeadVO)
			throws BusinessServiceException {
		
		try {
			Integer id=buyerLeadManagementDao.saveInsidesSalesLeadInfo(insideSalesLeadVO);
			return id;
		} catch (Exception e) {
			logger.error("Exception in saveInsidesSalesLeadInfo" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}
		
	}
	
	public void updateIsLeadStatus(InsideSalesLeadVO insideSalesLeadVO)
			throws BusinessServiceException {		
		try {
			buyerLeadManagementDao.updateIsLeadStatus(insideSalesLeadVO);
		} catch (Exception e) {
			logger.error("Exception in updateIsLeadStatus" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	}
	
	
	public void updateIsLeadStatusHistory(InsideSalesLeadVO insideSalesLeadVO)
			throws BusinessServiceException {		
		try {
			buyerLeadManagementDao.updateIsLeadStatusHistory(insideSalesLeadVO);
		} catch (Exception e) {
			logger.error("Exception in updateIsLeadStatus" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	}
	
	public void saveInsidesSalesLeadCustInfo(List<InsideSalesLeadCustVO> custInfoList)
			throws BusinessServiceException {		
		try {
			buyerLeadManagementDao.saveInsidesSalesLeadCustInfo( custInfoList);
		} catch (Exception e) {
			logger.error("Exception in saveInsidesSalesLeadCustInfo" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	}
	
	
	public InsideSalesLeadVO getInsideSalesLead(String leadId)
			throws BusinessServiceException {		
		try {
			InsideSalesLeadVO lead=buyerLeadManagementDao.getLead(leadId);
			return lead;
		} catch (Exception e) {
			logger.error("Exception in saveInsidesSalesLeadInfo" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	}
	
	public void deleteLead(Integer leadId)
			throws BusinessServiceException {		
		try {
			buyerLeadManagementDao.deleteLead(leadId);
			
		} catch (Exception e) {
			logger.error("Exception in deleteLead" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	}
	public void deleteLeadCustomFields(Integer leadId)
			throws BusinessServiceException {		
		try {
			buyerLeadManagementDao.deleteLeadCustomFields(leadId);
			
		} catch (Exception e) {
			logger.error("Exception in deleteLeadCustomFields" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	}
	
	public void updateLeadStatusHistory(InsideSalesLeadVO insideSalesLeadVO)
			throws BusinessServiceException {		
		try {
			buyerLeadManagementDao.updateLeadStatusHistory(insideSalesLeadVO);
			
		} catch (Exception e) {
			logger.error("Exception in updateLeadStatusHistory" + e.getCause());
			throw new BusinessServiceException(e.getCause());
		}		
	} 
	
	
	
	
	
	
	
}