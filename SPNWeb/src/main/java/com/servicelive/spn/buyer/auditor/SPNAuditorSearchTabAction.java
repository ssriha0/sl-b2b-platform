package com.servicelive.spn.buyer.auditor;

import com.servicelive.spn.constants.SPNActionConstants;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.utils.DateUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.common.detached.BackgroundCheckHistoryVO;
import com.servicelive.spn.common.detached.BackgroundFilterVO;
import com.servicelive.spn.common.detached.BackgroundInformationVO;
import com.servicelive.spn.common.detached.SearchBackgroundInformationVO;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.service.auditor.SPNAuditorService;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.auditor.AuditorSearchService;
import com.servicelive.spn.services.buyer.SPNBuyerServices;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
/**
 * 
 * 
 *
 */
/**
 * @author Jayanth_Justin
 *
 */
public class SPNAuditorSearchTabAction extends SPNBaseAction implements
		ModelDriven<SPNAuditorSearchModel> {
	private static final long serialVersionUID = -3639670601619316675L;
	private SPNAuditorSearchModel model = new SPNAuditorSearchModel();
	protected LookupService lookupService;
	protected SPNBuyerServices spnBuyerServices;
	protected AuditorSearchService auditorSearchService;
	protected SPNAuditorService spnAuditorService;

	private Integer sEcho = 1;
	private String iTotalRecords = "2";
	private String iTotalDisplayRecords = "2";
	private String aaData[][];

	private static final String MINUS_ONE = "-1";

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String display() throws Exception {
		User loggedInUser = getLoggedInUser();
	
		if (loggedInUser == null) {
			return NOT_LOGGED_IN;
		} else if (loggedInUser.getRole() == null
				|| loggedInUser.getRole().getId() == null
				|| loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER
						.intValue()) {
			return NOT_LOGGED_IN_AS_BUYER;
		}

		Integer buyerId = loggedInUser.getUserId();
		
		//R11_0 SL-20367 CR-#5
		if(buyerId != null){
			model.setRecertificationBuyerFeature(auditorSearchService.getBuyerFeatureSet(buyerId,"BACKGROUND_CHECK_RECERTIFICATION"));
		}
		model.setMarketList(lookupService.getAllMarkets());
		model.setStateList(lookupService.findStatesNotBlackedOut());
		model.setSpnList(spnBuyerServices.getSPNListForBuyer(buyerId));
		model.setDistrictList(lookupService.getALLDistricts());
		model.setProviderFirmMembershipStatusList(lookupService
				.getPFMembershipStatusList());
		initApplicantCounts(buyerId);

		return SUCCESS;
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String searchSPNAjax() throws Exception {
		User loggedInUser = getLoggedInUser();

		if (loggedInUser == null) {
			return NOT_LOGGED_IN;
		} else if (loggedInUser.getRole() == null
				|| loggedInUser.getRole().getId() == null
				|| loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER
						.intValue()) {
			return NOT_LOGGED_IN_AS_BUYER;
		}

		Integer buyerId = loggedInUser.getUserId();

		SPNAuditorSearchCriteriaVO auditorCriteriaVO = model
				.getSearchCriteriaVO();
		auditorCriteriaVO.setBuyerId(buyerId);
		setNullsSearchCriteria(auditorCriteriaVO);
		String status = auditorCriteriaVO.getMemberStatus();
		List<SPNAuditorSearchResultVO> providerList = null;
		try {
			if (null == status || MINUS_ONE.equals(status)) {
				List<SPNAuditorSearchResultVO> searchResultList = auditorSearchService
						.getAuditorSearchForProviderFirms(auditorCriteriaVO);
				providerList = spnAuditorService.getAuditorMemberStatus(
						searchResultList, auditorCriteriaVO.getBuyerId());
			} else {
				// SL-17473:setting the member compliance status and sorting
				// based on that
				providerList = spnAuditorService
						.getProviderFirms(auditorCriteriaVO);
			}
		} catch (BusinessServiceException e) {
			logger.error("Exception in SPNAuditorSearchTabAction searchSPNAjax() due to "
					+ e.getMessage());
		}

		model.setSearchResultsList(providerList);
		if (null != providerList
				&& 2 == loggedInUser.getRole().getId().intValue()) {
			Integer adminUserId = spnAuditorService
					.getAdminResourceId(loggedInUser.getUsername());
			this.getRequest().getSession()
					.setAttribute("adminUserId", adminUserId);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String expandProviderFirmForSPNAjax() throws Exception {
		/* SPNAuditorSearchExpandCriteriaVO expandCriteriaVO = */model
				.getExpandCriteriaVO();

		return SUCCESS;
	}

	private void setNullsSearchCriteria(
			SPNAuditorSearchCriteriaVO auditorCriteriaVO) {
		if (auditorCriteriaVO.getMarketId() != null
				&& auditorCriteriaVO.getMarketId().intValue() == -1) {
			auditorCriteriaVO.setMarketId(null);
		}
		if ("-1".equals(auditorCriteriaVO.getProviderFirmName())
				|| "".equals(auditorCriteriaVO.getProviderFirmName())) {
			auditorCriteriaVO.setProviderFirmName(null);
		}
		if ("-1".equals(auditorCriteriaVO.getProviderFirmNumber())
				|| "".equals(auditorCriteriaVO.getProviderFirmNumber())) {
			auditorCriteriaVO.setProviderFirmNumber(null);
		}
		if (auditorCriteriaVO.getSpnId() != null
				&& auditorCriteriaVO.getSpnId().intValue() == -1) {
			auditorCriteriaVO.setSpnId(null);
		}
		if ("-1".equals(auditorCriteriaVO.getStateCd())
				|| "".equals(auditorCriteriaVO.getStateCd())) {
			auditorCriteriaVO.setStateCd(null);
		}
		if ("-1".equals(auditorCriteriaVO.getZipCode())
				|| "".equals(auditorCriteriaVO.getZipCode())) {
			auditorCriteriaVO.setZipCode(null);
		}
		if (auditorCriteriaVO.getDistrictId() != null
				&& auditorCriteriaVO.getDistrictId().intValue() == -1) {
			auditorCriteriaVO.setDistrictId(null);
		}
		if (auditorCriteriaVO.getProviderFirmStatus() == null
				|| "".equals(auditorCriteriaVO.getProviderFirmStatus())
				|| "-1".equals(auditorCriteriaVO.getProviderFirmStatus())) {
			auditorCriteriaVO.setProviderFirmStatus(null);
		}
	}

	private void initApplicantCounts(Integer buyerId) {
		SPNApplicantCounts spnApplicantCounts = getApplicantCounts(buyerId);
		model.setSpnApplicantCounts(spnApplicantCounts);
	}

	
	
	/**
	 * @return String
	 * @throws Exception
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db to displayed in SPN Audtior Tab.
	public String searchBackgroundInformationCountAjax() {

		try {
			

			Integer spnId = -1;
			String stateCd = SPNActionConstants.BG_DEFAULT_SELECTION;
			String status= SPNActionConstants.BG_DEFAULT_SELECTION;
			String providerFirmName=null;
			String providerFirmId=null;
			String zipCode=null;
			Integer districtId = null;
			Integer marketId = null ;
			
			Integer count = 0;
			String searching = "";
			
			SearchBackgroundInformationVO searchBackgroundInformationVO = new SearchBackgroundInformationVO();
			
			List<String> sLBackgroundStatusList = new ArrayList<String>();
			List<String> reCertificationList = new ArrayList<String>();
			List<String> systemActionList = new ArrayList<String>();
			
			String selectedReCertification = "";
			String selectedSLBackgroundStatus = "";
			String selectedSystemAction = "";
			String selectedProviderFirmId = "";
			List<String> selectedProviderFirmIds = new ArrayList<String>();
			
			BackgroundFilterVO backgroundFilterVO=model.getBackgroundFilterVO();
			
			//Fetching the buyerId of logged in user
			
			User loggedInUser = getLoggedInUser();

			Integer buyerId = loggedInUser.getUserId();
			
			//Fetching spnId from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_SPN_ID_PARAM))) {
				spnId = Integer.parseInt((String) getRequest().getParameter(
						SPNActionConstants.BG_SPN_ID_PARAM));
			}

			//Fetching state code from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_STATE_CD_PARAM))) {
				stateCd = ((String) getRequest().getParameter(SPNActionConstants.BG_STATE_CD_PARAM));
			}

			//Fetching status code from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_STATUS_PARAM))) {
				status=((String) getRequest().getParameter(SPNActionConstants.BG_STATUS_PARAM));
//				if(status.equals(SPNActionConstants.BG_SPN_MEMBER_NOSPACE))
//				{
//					status =SPNActionConstants.BG_SPN_MEMBER_SPACE;
//				}
//				else if(status.equals(SPNActionConstants.BG_SPN_OUTOFCOMPLIANCE_NOSPACE))
//				{
//					status =SPNActionConstants.BG_SPN_OUTOFCOMPLIANCE_SPACE;
//				}
			}
			
			//Fetching provider firm name from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NAME_PARAM))) {
				providerFirmName = ((String) getRequest().getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NAME_PARAM));
			}
			
			//Fetching provider firm id from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NO_PARAM))) {
				providerFirmId = ((String) getRequest().getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NO_PARAM));
			}
			
			//Fetching zip code from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_ZIP_CODE_PARAM))) {
				zipCode = ((String) getRequest().getParameter(SPNActionConstants.BG_ZIP_CODE_PARAM));
			}
			
			//Fetching market id from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_MARKET_ID_PARAM))) {
				marketId = Integer.parseInt(((String) getRequest().getParameter(SPNActionConstants.BG_MARKET_ID_PARAM)));
			}
			
			//Fetching district id from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DISTRICT_ID_PARAM))) {
				districtId = Integer.parseInt(((String) getRequest().getParameter(SPNActionConstants.BG_DISTRICT_ID_PARAM)));
			}
			
			
			
			getRequest().setAttribute(SPNActionConstants.BG_SPN_ID_PARAM, spnId);
			getRequest().setAttribute(SPNActionConstants.BG_STATE_CD_PARAM, stateCd);
			getRequest().setAttribute(SPNActionConstants.BG_STATUS_PARAM, status);
			getRequest().setAttribute(SPNActionConstants.BG_PROVIDER_FIRM_NAME_PARAM, providerFirmName);
			getRequest().setAttribute(SPNActionConstants.BG_PROVIDER_FIRM_NO_PARAM, providerFirmId);
			getRequest().setAttribute(SPNActionConstants.BG_ZIP_CODE_PARAM, zipCode);
			getRequest().setAttribute(SPNActionConstants.BG_MARKET_ID_PARAM, marketId);
			getRequest().setAttribute(SPNActionConstants.BG_DISTRICT_ID_PARAM, districtId);
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_SEARCHING_PARAM))) {
				searching = (String) getRequest().getParameter(SPNActionConstants.BG_DATATABLE_SEARCHING_PARAM);
			}
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_SEARCHING_PARAM, searching);

			
			
			searchBackgroundInformationVO.setBuyerId(buyerId);
			searchBackgroundInformationVO.setSpnId(spnId);
			searchBackgroundInformationVO.setStateCd(stateCd);
			searchBackgroundInformationVO.setStatus(status);
			searchBackgroundInformationVO.setProviderFirmName(providerFirmName);
			searchBackgroundInformationVO.setProviderFirmNumber(providerFirmId);
			searchBackgroundInformationVO.setZipCode(zipCode);
			searchBackgroundInformationVO.setDistrictId(districtId);
			searchBackgroundInformationVO.setMarketId(marketId);

			//Setting ServiceLive Background Check Status list to be displayed

			sLBackgroundStatusList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM1);
			sLBackgroundStatusList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM2);
			sLBackgroundStatusList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM3);
			sLBackgroundStatusList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM4);
			sLBackgroundStatusList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM5);
			

			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM,
					sLBackgroundStatusList);

			
			//Setting Re-Certification Due list to be displayed
			
			reCertificationList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1);
			reCertificationList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7);
			reCertificationList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2);
			reCertificationList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5);
			reCertificationList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6);
			
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM,
					reCertificationList);

			//Setting System Action list to be displayed
			
			systemActionList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1);
			systemActionList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2);
			systemActionList.add(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3);
			

			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM, systemActionList);

			
			//Fetching selected options from Advance Filter


			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedReCertification()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedReCertification())) {
					selectedReCertification = backgroundFilterVO
							.getSelectedReCertification();
					if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5)) {
						searchBackgroundInformationVO
								.setSelectedReCertification(SPNActionConstants.BG_SEVEN);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6)) {
						searchBackgroundInformationVO
								.setSelectedReCertification(SPNActionConstants.BG_THIRTY);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2)) {
						searchBackgroundInformationVO
								.setSelectedReCertification(SPNActionConstants.BG_ZERO);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1)) {
						searchBackgroundInformationVO.setPastDue(SPNActionConstants.BG_PAST);
					}else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7)) {
						searchBackgroundInformationVO.setSelectedReCertification(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM8);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInformationVO.setSelectedReCertificationAll(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM_ALL);
					} else{
						searchBackgroundInformationVO.setSelectedReCertification(selectedReCertification);
					}

				}

			}
			if (null != backgroundFilterVO
					&& null !=backgroundFilterVO
							.getSelectedSLBackgroundStatus()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedSLBackgroundStatus())) {
					selectedSLBackgroundStatus = backgroundFilterVO
							.getSelectedSLBackgroundStatus();
					
					 if(selectedSLBackgroundStatus.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
							searchBackgroundInformationVO.setSelectedSLBackgroundStatusAll(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM_ALL);
						}
					 else
					 { 
					searchBackgroundInformationVO
							.setSelectedSLBackgroundStatus(selectedSLBackgroundStatus);
					 }
				}

			}

			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedSystemAction()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedSystemAction())) {
					selectedSystemAction = backgroundFilterVO
							.getSelectedSystemAction();

					if (selectedSystemAction.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2)) {
						searchBackgroundInformationVO
								.setSelectedSystemAction(SPNActionConstants.BG_SEVEN);
					} else if (selectedSystemAction
							.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1)) {
						searchBackgroundInformationVO
								.setSelectedSystemAction(SPNActionConstants.BG_THIRTY);
					} else if (selectedSystemAction.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3)) {
						searchBackgroundInformationVO
								.setSelectedSystemAction(SPNActionConstants.BG_ZERO);
					}
					else if (selectedSystemAction.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInformationVO.setSelectedSystemActionAll(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM_ALL);
					} else {
						searchBackgroundInformationVO.setSelectedSystemAction(selectedSystemAction);
					}

				}

			}

			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedProviderFirmIds()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedProviderFirmIds())) {
					selectedProviderFirmId = backgroundFilterVO
							.getSelectedProviderFirmIds();
					String[] splitsFirms = selectedProviderFirmId.split("[ ]");
					for (int i = 0; i < splitsFirms.length; i++) {
						selectedProviderFirmIds.add(splitsFirms[i]);
					}
					searchBackgroundInformationVO
							.setSelectedProviderFirmIds(selectedProviderFirmIds);
				}

			}
			
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_SELECTED_RECERTIFICATION_PARAM,
					selectedReCertification);
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_SELECTED_BG_STATUS_PARAM,
					selectedSLBackgroundStatus);
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_SELECTED_SYSTEM_ACTION_PARAM,
					selectedSystemAction);
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_SELECTED_FIRMID_PARAM,
					selectedProviderFirmId);

			//Fetching the count from database
			
			count = auditorSearchService
					.getBackgroundInformationCount(searchBackgroundInformationVO);
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_COUNT_RESULTS_PARAM, count);
			
			//Setting the total record count in session to use in export
			getRequest().getSession().setAttribute(SPNActionConstants.BG_DATATABLE_COUNT_RESULTS_EXPORT_PARAM, count);
		} catch (Exception e) {
			
			logger.error("Exception in searchBackgroundInformationCountAjax method of SPNAuditorSearchTabAction java class due to "
					+ e.getMessage());
			
			return "error";
		}

		return "back";
	}
	

	/**
	 * @return String
	 */
	//SL-19387
	//Fetching Background Check details of resources from db to displayed in SPN Audtior Tab.
	public String searchBackgroundInformationAjax() {

		try {

			Integer spnId = -1;
			String stateCd = SPNActionConstants.BG_DEFAULT_SELECTION;
			String status = SPNActionConstants.BG_DEFAULT_SELECTION;
			String providerFirmName=null;
			String providerFirmId=null;
			String zipCode=null;
			Integer districtId = null;
			Integer marketId = null ;
			
			List<String> selectedProviderFirmIds = new ArrayList<String>();
			
			SearchBackgroundInformationVO searchBackgroundInformationVO = new SearchBackgroundInformationVO();
			
			List<BackgroundInformationVO> backgroundInfoList = new ArrayList<BackgroundInformationVO>();
			
			String searching = "";
			
			int startIndex = 0;
			int numberOfRecords = 0;
			String sortColumnName = "";
			String sortOrder = "";
			String sSearch = "";
			
			User loggedInUser = getLoggedInUser();

			//Fetching the buyerId of logged in user
			
			Integer buyerId = loggedInUser.getUserId();

			//Fetching spnId from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_SPN_ID_PARAM))) {
				spnId = Integer.parseInt((String) getRequest().getParameter(
						SPNActionConstants.BG_SPN_ID_PARAM));
			}

			//Fetching state code from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_STATE_CD_PARAM))) {
				stateCd = ((String) getRequest().getParameter(SPNActionConstants.BG_STATE_CD_PARAM));
			}
			
			//Fetching status code from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_STATUS_PARAM))) {
				status = ((String) getRequest().getParameter(SPNActionConstants.BG_STATUS_PARAM));
//				if(status.equals(SPNActionConstants.BG_SPN_MEMBER_NOSPACE))
//				{
//					status =SPNActionConstants.BG_SPN_MEMBER_SPACE;
//				}
//				else if(status.equals(SPNActionConstants.BG_SPN_OUTOFCOMPLIANCE_NOSPACE))
//				{
//					status =SPNActionConstants.BG_SPN_OUTOFCOMPLIANCE_SPACE;
//				}
			}
			
			
			//Fetching provider firm name from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NAME_PARAM))) {
				providerFirmName = ((String) getRequest().getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NAME_PARAM));
			}
			
			//Fetching provider firm id from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NO_PARAM))) {
				providerFirmId = ((String) getRequest().getParameter(SPNActionConstants.BG_PROVIDER_FIRM_NO_PARAM));
			}
			
			//Fetching zip code from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_ZIP_CODE_PARAM))) {
				zipCode = ((String) getRequest().getParameter(SPNActionConstants.BG_ZIP_CODE_PARAM));
			}
			
			//Fetching market id from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_MARKET_ID_PARAM))) {
				marketId = Integer.parseInt(((String) getRequest().getParameter(SPNActionConstants.BG_MARKET_ID_PARAM)));
			}
			
			//Fetching district id from frontend
			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DISTRICT_ID_PARAM))) {
				districtId = Integer.parseInt(((String) getRequest().getParameter(SPNActionConstants.BG_DISTRICT_ID_PARAM)));
			}
			
			searchBackgroundInformationVO.setProviderFirmName(providerFirmName);
			searchBackgroundInformationVO.setProviderFirmNumber(providerFirmId);
			searchBackgroundInformationVO.setZipCode(zipCode);
			searchBackgroundInformationVO.setDistrictId(districtId);
			searchBackgroundInformationVO.setMarketId(marketId);
			
			model = getModel();
			
			BackgroundFilterVO backgroundFilterVO=model.getBackgroundFilterVO();
			
			getRequest().setAttribute(SPNActionConstants.BG_SPN_ID_PARAM, spnId);
			getRequest().setAttribute(SPNActionConstants.BG_STATE_CD_PARAM, stateCd);
			getRequest().setAttribute(SPNActionConstants.BG_STATUS_PARAM, status);
			getRequest().setAttribute(SPNActionConstants.BG_PROVIDER_FIRM_NAME_PARAM, providerFirmName);
			getRequest().setAttribute(SPNActionConstants.BG_PROVIDER_FIRM_NO_PARAM, providerFirmId);
			getRequest().setAttribute(SPNActionConstants.BG_ZIP_CODE_PARAM, zipCode);
			getRequest().setAttribute(SPNActionConstants.BG_MARKET_ID_PARAM, marketId);
			getRequest().setAttribute(SPNActionConstants.BG_DISTRICT_ID_PARAM, districtId);
			
			
			searchBackgroundInformationVO.setBuyerId(buyerId);

			searchBackgroundInformationVO.setSpnId(spnId);
			searchBackgroundInformationVO.setStateCd(stateCd);
			searchBackgroundInformationVO.setStatus(status);

			//Fetching selected options from Advance Filter
			
			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedReCertification()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedReCertification())) {

					String selectedReCertification = backgroundFilterVO
							.getSelectedReCertification();
					if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5)) {
						searchBackgroundInformationVO
								.setSelectedReCertification(SPNActionConstants.BG_SEVEN);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6)) {
						searchBackgroundInformationVO
								.setSelectedReCertification(SPNActionConstants.BG_THIRTY);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2)) {
						searchBackgroundInformationVO
								.setSelectedReCertification(SPNActionConstants.BG_ZERO);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1)) {
						searchBackgroundInformationVO.setPastDue(SPNActionConstants.BG_PAST);
					} else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7)) {
						searchBackgroundInformationVO.setSelectedReCertification(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM8);
					}else if (selectedReCertification.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInformationVO.setSelectedReCertificationAll(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM_ALL);
					}else
					{
						searchBackgroundInformationVO.setSelectedReCertification(selectedReCertification);
					}

				}

			}
			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedSLBackgroundStatus()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedSLBackgroundStatus())) {
					String selectedSLBackgroundStatus = backgroundFilterVO
							.getSelectedSLBackgroundStatus();
					
					 if(selectedSLBackgroundStatus.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
							searchBackgroundInformationVO.setSelectedSLBackgroundStatusAll(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM_ALL);
						}
					 else
					 { 
					searchBackgroundInformationVO
							.setSelectedSLBackgroundStatus(selectedSLBackgroundStatus);
					 }
				}

			}

			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedSystemAction()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedSystemAction())) {
					String selectedSystemAction = backgroundFilterVO
							.getSelectedSystemAction();

					if (selectedSystemAction.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2)) {
						searchBackgroundInformationVO
								.setSelectedSystemAction(SPNActionConstants.BG_SEVEN);
					} else if (selectedSystemAction
							.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1)) {
						searchBackgroundInformationVO
								.setSelectedSystemAction(SPNActionConstants.BG_THIRTY);
					} else if (selectedSystemAction.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3)) {
						searchBackgroundInformationVO
								.setSelectedSystemAction(SPNActionConstants.BG_ZERO);
					}else if (selectedSystemAction.equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInformationVO.setSelectedSystemActionAll(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM_ALL);
					}else {
						searchBackgroundInformationVO.setSelectedSystemAction(selectedSystemAction);
					}

				}

			}

			if (null != backgroundFilterVO
					&& null != backgroundFilterVO
							.getSelectedProviderFirmIds()) {

				if (StringUtils.isNotBlank(backgroundFilterVO
						.getSelectedProviderFirmIds())) {
					String selectedProviderFirmId = backgroundFilterVO
							.getSelectedProviderFirmIds();
					String[] splitsFirms = selectedProviderFirmId.split("[ ]");
					for (int i = 0; i < splitsFirms.length; i++) {
						selectedProviderFirmIds.add(splitsFirms[i]);
					}
					searchBackgroundInformationVO
							.setSelectedProviderFirmIds(selectedProviderFirmIds);
				}

			}

		
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_SEARCHING_PARAM))) {
				searching = (String) getRequest().getParameter(SPNActionConstants.BG_DATATABLE_SEARCHING_PARAM);
			}
			getRequest().setAttribute(SPNActionConstants.BG_DATATABLE_SEARCHING_PARAM, searching);
			long startTime = System.currentTimeMillis();

			// to handle server side pagination.

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_IDISPLAYSTART_PARAM))) {
				startIndex = Integer.parseInt((String) getRequest()
						.getParameter(SPNActionConstants.BG_DATATABLE_IDISPLAYSTART_PARAM));
			}

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_IDISPLAYLENGTH_PARAM))) {
				numberOfRecords = Integer.parseInt((String) getRequest()
						.getParameter(SPNActionConstants.BG_DATATABLE_IDISPLAYLENGTH_PARAM));
			}
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_ISORTCOL_0_PARAM))) {
				String sortColumnId = (String) getRequest().getParameter(
						SPNActionConstants.BG_DATATABLE_ISORTCOL_0_PARAM);

				// To do sorting based on column

				if (sortColumnId.equals("0")) {
					sortColumnName = SPNActionConstants.BG_DATATABLE_COLUMN_SORT_FIRM;
				} else if (sortColumnId.equals("1")) {
					sortColumnName = SPNActionConstants.BG_DATATABLE_COLUMN_SORT_PROVIDER;
				} else if (sortColumnId.equals("2")) {
					sortColumnName = SPNActionConstants.BG_DATATABLE_COLUMN_SORT_SLSTATUS;
				} else if (sortColumnId.equals("3")) {
					sortColumnName = SPNActionConstants.BG_DATATABLE_COLUMN_SORT_CERTDATE;
				} else if (sortColumnId.equals("4")) {
					sortColumnName = SPNActionConstants.BG_DATATABLE_COLUMN_SORT_RECERTDATE;
				} else if (sortColumnId.equals("5")) {
					sortColumnName = SPNActionConstants.BG_DATATABLE_COLUMN_SORT_RECERTSTATUS;
				} 

			}

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_SSORTDIR_0_PARAM))) {
				sortOrder = (String) getRequest().getParameter(SPNActionConstants.BG_DATATABLE_SSORTDIR_0_PARAM);
			}

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_SSEARCH_PARAM))) {
				sSearch = (String) getRequest().getParameter(SPNActionConstants.BG_DATATABLE_SSEARCH_PARAM);
			}
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(SPNActionConstants.BG_DATATABLE_SEECHO_PARAM))) {
				sEcho = Integer.parseInt(getRequest().getParameter(SPNActionConstants.BG_DATATABLE_SEECHO_PARAM));
			}

			searchBackgroundInformationVO.setSortColumnName(sortColumnName);
			searchBackgroundInformationVO.setSortOrder(sortOrder);
			searchBackgroundInformationVO.setsSearch(sSearch);
			searchBackgroundInformationVO.setStartIndex(startIndex);
			searchBackgroundInformationVO.setNumberOfRecords(numberOfRecords);
			
			//Setting the VO in session to use during export
			getRequest().getSession().setAttribute(SPNActionConstants.SEARCH_VO, searchBackgroundInformationVO);
			
			
			iTotalRecords = SPNActionConstants.BG_DATATABLE_HUNDRED_PARAM;
			iTotalDisplayRecords = SPNActionConstants.BG_DATATABLE_TEN_PARAM;

			//Fetching Background Check details count of resources from db
			iTotalRecords = auditorSearchService.getBackgroundInformationCount(
					searchBackgroundInformationVO).toString();
	
			
			//Fetching Background Check details of resources from db
			backgroundInfoList = auditorSearchService
					.getBackgroundInformation(searchBackgroundInformationVO);

			
			//Displaying data in each column of data table
			iTotalDisplayRecords = iTotalRecords;
			if (null != backgroundInfoList) {
				aaData = new String[backgroundInfoList.size()][7];
				int count = 0;
				for (BackgroundInformationVO backgroundInformationVO : backgroundInfoList) {
					try{
						
						if (null != backgroundInformationVO) {
					
						String data[] = new String[7];
						data[0] = "";
						data[1] = "";
						data[2] = "";
						data[3] = "";
						data[4] = "";
						data[5] = "";
						data[6] = "";

						//'Provider Firm' data to be displayed with link to open their profile
						if (null != backgroundInformationVO
								.getVendorBusinessName()
								&& null != backgroundInformationVO
										.getVendorId()) {
							data[0] = "<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderFirmProfile("
									+ backgroundInformationVO.getVendorId()
									+ ")' ><b>"
									+ backgroundInformationVO
											.getVendorBusinessName()
									+ "</b></a><br/>"
									+ " (ID #"
									+ backgroundInformationVO.getVendorId()
									+ ")";
						}

						//'Provider' data to be displayed with link to open their profile
						if (null != backgroundInformationVO.getResourceId()
								&& null != backgroundInformationVO
										.getVendorId()
								&& null != backgroundInformationVO
										.getProviderFirstName()
								&& null != backgroundInformationVO
										.getProviderLastName()) {
							data[1] = "<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderProfile("
									+ backgroundInformationVO.getResourceId()
									+ ","
									+ backgroundInformationVO.getVendorId()
									+ ")' ><b>"
									+ backgroundInformationVO
											.getProviderFirstName()
									+ " "
									+ backgroundInformationVO
											.getProviderLastName()
									+ "</b></a><br/>"
									+ " (ID #"
									+ backgroundInformationVO.getResourceId()
									+ ")<br/>";
						}

						//'Background Check Status' data to be displayed-'Clear'/'Adverse Finding'/'Not Started'/'In Process'/'Pending Submission'
						if (null != backgroundInformationVO
								.getBackgroundState()) {
							if(backgroundInformationVO.getBackgroundState().equalsIgnoreCase(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM1) || backgroundInformationVO.getBackgroundState().equalsIgnoreCase(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM2)){
								data[2] = 
									"<a  style='color: #00A0D2;' href='javascript:void(0);'	onclick='javascript:openBackgroundHistory("
									+backgroundInformationVO.getVendorId()+","+backgroundInformationVO.getResourceId()+","+"\""+backgroundInformationVO.getBackgroundState()+"\""
									+");'>"
									+ backgroundInformationVO.getBackgroundState()	
									+ "</a>";
							}
							else{
							data[2] = backgroundInformationVO
									.getBackgroundState();
							}
						}

						//'Last Certification Date' data to be displayed
						if (null != backgroundInformationVO
								.getVerificationDate()) {

							data[3] = ""
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getVerificationDate(),
													SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
						}

						//'ReCertification Due Date' data to be displayed
						if (null != backgroundInformationVO
								.getReverificationDate() && null!=backgroundInformationVO.getCriteriaBg() && backgroundInformationVO.getCriteriaBg() > 0) {
							data[4] = ""
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getReverificationDate(),
													SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
						}

						//'Last ReCertification Status' data to be displayed based on the number of days
						if (null != backgroundInformationVO.getRecertificationStatus() && null!=backgroundInformationVO.getCriteriaBg() && backgroundInformationVO.getCriteriaBg() > 0) {
							
							 if(backgroundInformationVO.getRecertificationStatus().equals(SPNActionConstants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM3))
								{
									data[5] =backgroundInformationVO.getRecertificationStatus();
								}
							   else if (Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) == 0) {
								data[5] = "Due Today";
							} else if (Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) < 0) {
								data[5] = "<font color='red'>Past Due</font>";
							} else if(Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) > 0 && Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) <= 30) {
								data[5] = "Due in "
										+ backgroundInformationVO.getRecertificationStatus() + " days";
							}

						}

						//'System Action/Notice Sent On' data to be displayed based on the notification type
						if (null != backgroundInformationVO
								.getNotificationType()
								&& (null != backgroundInformationVO
										.getNotificationDateThirty() || null != backgroundInformationVO
												.getNotificationDateSeven() || null != backgroundInformationVO
														.getNotificationDateZero()) && null!=backgroundInformationVO.getCriteriaBg() && backgroundInformationVO.getCriteriaBg() > 0 && null != backgroundInformationVO.getRecertificationStatus()) {
							
							
							if(null != backgroundInformationVO
									.getNotificationDateThirty())
							{
							data[6] ="30 days notice sent on"+" "
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getNotificationDateThirty(),
													SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
							}
							
							if(null != backgroundInformationVO
									.getNotificationDateSeven())
							{
							data[6] =data[6]+"<br/>"+"7 days notice sent on"+" "
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getNotificationDateSeven(),
													SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
							}
							
							if(null != backgroundInformationVO
									.getNotificationDateZero())
							{
							data[6] =data[6]+"<br/>"+"0 days notice sent on"+" "
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getNotificationDateZero(),
													SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
							}

						}
						aaData[count] = data;

						count = count + 1;
					}
					}
					catch(Exception e){
						
						if(null != backgroundInformationVO.getResourceId())
						{
						logger.error("Exception in searchBackgroundInformationAjax method of SPNAuditorSearchTabAction java class while processing record:"
								+" Resource Id: "+backgroundInformationVO.getResourceId()+e.getMessage());
						}
						else
						{
							logger.error("Exception in searchBackgroundInformationAjax method of SPNAuditorSearchTabAction java class while processing records:"
									+ e.getMessage());
						}
					}
				}
			}

			model.setAaData(aaData);
			model.setsEcho(sEcho);
			model.setiTotalDisplayRecords(iTotalDisplayRecords);
			model.setiTotalRecords(iTotalRecords);
			getRequest().setAttribute(SPNActionConstants.BG_LIST, backgroundInfoList);
			long endTime1 = System.currentTimeMillis() - startTime;
			logger.info("timefor overall fetch" + endTime1);

		} catch (Exception e) {
			logger.error("Exception in searchBackgroundInformationAjax method of SPNAuditorSearchTabAction java class due to "
					+ e.getMessage());
			return "json";
		}

		return "json";

	}
	//R11.0 Generating Report in XLS,CSV format
	public String exportBackgroundInformationAjax() throws IOException, SQLException {
		Integer format = 0;
		List<BackgroundInformationVO> backgroundVOList = new ArrayList<BackgroundInformationVO>();
		SearchBackgroundInformationVO searchVO = new  SearchBackgroundInformationVO();

		//Getting the selected format from the front end
		if (getRequest().getParameter(SPNActionConstants.EXPORT_SELECTED_FORMAT) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(SPNActionConstants.EXPORT_SELECTED_FORMAT))) {
			format = Integer.parseInt((String) getRequest().getParameter(SPNActionConstants.EXPORT_SELECTED_FORMAT));
		}
		//Getting  the Details from Session 
		searchVO = (SearchBackgroundInformationVO) getRequest().getSession().getAttribute(SPNActionConstants.SEARCH_VO);
		searchVO.setStartIndex(0);
		searchVO.setNumberOfRecords(1000);
		
		//Fetching Background Check details of resources from db
		
		try{
		backgroundVOList = auditorSearchService.getBackgroundInformation(searchVO);
		}catch (Exception e) {
			logger.info("error in fetching background details in exportBackgroundInformationAjax excel method of SPNAuditorSearchTabAction java class"+e.getMessage()); 
		}
		
		
		//XLS Format
		if(format.intValue() == 1 && null!=backgroundVOList){
			OutputStream out=null;
			ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
			String currentDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),SPNActionConstants.EXPORT_DATE_FORMAT);
			String fileName = (SPNActionConstants.EXPORT_FILE_NAME+ currentDate +SPNActionConstants.XLS_FILE_FORMAT);
			 try 
			 {
				logger.info("start--> exportBackgroundInformationAjax excel method"); 
				
				
				outFinal = auditorSearchService.getExportToExcel(outFinal,backgroundVOList);
			    		 
				int size = 0;
				if (outFinal != null) {
					size = outFinal.size();
				}
				out = getResponse().getOutputStream();
				getResponse().setContentType("application/vnd.ms-excel");
				getResponse().setContentLength(size);
				getResponse().setHeader("Content-Disposition", "attachment; filename="+fileName);
				getResponse().setHeader("Expires", "0");
				getResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				getResponse().setHeader("Pragma", "public");
				outFinal.writeTo(out);			 
			 }
			 catch(Exception e){
				 logger.error("Exception in Excel export method in exportBackgroundInformationAjax excel method of SPNAuditorSearchTabAction java class(excel)", e);
				}
			 finally
			  {
			   if (out != null){
				   out.flush();
				   out.close();
				   outFinal.close();
			   }
			    
			  }
			 logger.info("End--> exportBackgroundInformationAjax excel method");
			 
		}
		//CSV Comma format
		else if (format.intValue() == 2 && null!=backgroundVOList){
			OutputStream out=null;
			ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
			String currentDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),SPNActionConstants.EXPORT_DATE_FORMAT);
			String fileName = (SPNActionConstants.EXPORT_FILE_NAME+ currentDate + SPNActionConstants.CSV_FILE_FORMAT);
			 try 
			 {
				logger.info("start--> exportBackgroundInformationAjax CSV Comma method"); 
				
				StringBuffer buffer = null;
				out = getResponse().getOutputStream();
				
				buffer = auditorSearchService.getExportToCSVComma(backgroundVOList);
			   
				InputStream in = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
				Integer size = buffer.toString().getBytes().length;
				byte[] outputByte = new byte[size];
				while(in.read(outputByte, 0, size) != -1)
				{
					outFinal.write(outputByte, 0, size);
				}
				in.close();
				
				int fileSize = 0;
				if (outFinal != null) {
					fileSize = outFinal.size();
				}
				getResponse().setContentType("application/csv");
				getResponse().setContentLength(fileSize);
				getResponse().setHeader("Content-Disposition", "attachment; filename="+fileName);
				getResponse().setHeader("Expires", "0");
				getResponse().setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
				getResponse().setHeader("Pragma", "public");
				outFinal.writeTo(out);
			
							 
			 }
			 catch(Exception e){
				 logger.error("Exception in exportBackgroundInformationAjax  method of SPNAuditorSearchTabAction java class(CSV comma)", e);
				}
			 finally
			  {
			   if (out != null){
				   outFinal.close();
				   out.flush();
				   out.close();
			   }
			    
			  }
			 logger.info("End--> exportBackgroundInformationAjax CSV Comma method");
			
		}
		//CSV Pipe format
		else if (format.intValue() == 3 && null!=backgroundVOList){
			OutputStream out=null;
			ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
			String currentDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),SPNActionConstants.EXPORT_DATE_FORMAT);
			String fileName = (SPNActionConstants.EXPORT_FILE_NAME+ currentDate + SPNActionConstants.CSV_FILE_FORMAT);
			 try 
			 {
				logger.info("start--> exportBackgroundInformationAjax CSV Pipe method"); 
				StringBuffer buffer = null;
				out = getResponse().getOutputStream();
				
				buffer = auditorSearchService.getExportToCSVPipe(backgroundVOList);
			   
				InputStream in = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
				Integer size = buffer.toString().getBytes().length;
				byte[] outputByte = new byte[size];
				while(in.read(outputByte, 0, size) != -1)
				{
					outFinal.write(outputByte, 0, size);
				}
				in.close();
				
				int fileSize = 0;
				if (outFinal != null) {
					fileSize = outFinal.size();
				}
				getResponse().setContentType("application/csv");
				getResponse().setContentLength(fileSize);
				getResponse().setHeader("Content-Disposition", "attachment; filename="+fileName);
				getResponse().setHeader("Expires", "0");
				getResponse().setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
				getResponse().setHeader("Pragma", "public");
				outFinal.writeTo(out);									 
			 }
			 catch(Exception e){
				 logger.error("Exception in exportBackgroundInformationAjax  method of SPNAuditorSearchTabAction java class(CSV Pipe)", e);
				}
			 finally
			  {
			   if (out != null){
				   outFinal.close();
				   out.flush();
				   out.close();
			   }
			    
			  }
			 logger.info("End--> exportBackgroundInformationAjax CSV Pipe method");
		}
		
		return "success";
		
	}
	//To Display the BackgroundCheck History
	public String displayBackgroundHistoryAjax(){
		Integer resourceId = 0;
		Integer vendorId = 0;
		String providerName = null;
		String backgroundState = null;
		List<BackgroundCheckHistoryVO> backgroundHistVOList = new ArrayList<BackgroundCheckHistoryVO>();
		BackgroundCheckHistoryVO bgHistVO = new BackgroundCheckHistoryVO();
		//Getting the resourceId and vendorId from request
		if (getRequest().getParameter(SPNActionConstants.VENDOR_ID) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(SPNActionConstants.VENDOR_ID))) {
			vendorId = Integer.parseInt((String) getRequest().getParameter(SPNActionConstants.VENDOR_ID));
		}
		if (getRequest().getParameter(SPNActionConstants.RESOURCE_ID) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(SPNActionConstants.RESOURCE_ID))) {
			resourceId = Integer.parseInt((String) getRequest().getParameter(SPNActionConstants.RESOURCE_ID));
		}
		if (getRequest().getParameter(SPNActionConstants.BACKGROUND_STATE) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(SPNActionConstants.BACKGROUND_STATE))) {
			backgroundState = ((String) getRequest().getParameter(SPNActionConstants.BACKGROUND_STATE));
			if(backgroundState.equalsIgnoreCase(SPNActionConstants.NOTCLEARED)){
				backgroundState = SPNActionConstants.NOT_CLEARED;
			}
		}
		
		getRequest().setAttribute(SPNActionConstants.BACKGROUND_STATE, backgroundState);
		getRequest().setAttribute(SPNActionConstants.RESOURCE_ID, resourceId);
		
		
		bgHistVO.setResourceId(resourceId);
		try{
			providerName = auditorSearchService.getProviderName(resourceId);
			backgroundHistVOList = auditorSearchService.getBackgroundCheckHistoryDetails(bgHistVO);
			List<BackgroundCheckHistoryVO> formattedHistList = new ArrayList<BackgroundCheckHistoryVO>();
			if(backgroundHistVOList!=null && !backgroundHistVOList.isEmpty()){
				for(BackgroundCheckHistoryVO histVO:backgroundHistVOList){
					BackgroundCheckHistoryVO VO = new BackgroundCheckHistoryVO();
					if(null!= histVO.getDisplayDate()){
						VO.setFmtDisplayDate(DateUtils.getFormatedDate(histVO.getDisplayDate(),SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT));
					}
					if(null!= histVO.getVerificationDate()){
						VO.setFmtVerificationDate(DateUtils.getFormatedDate(histVO.getVerificationDate(),SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT));
					}
					if(null!= histVO.getReverificationDate()&& histVO.getCriteriaIdCount()>0){
						VO.setFmtReverificationDate(DateUtils.getFormatedDate(histVO.getReverificationDate(),SPNActionConstants.BACKGROUND_CHECK_STATUS_DATE_FORMAT));
					}
					VO.setBackgroundStatus(histVO.getBackgroundStatus());
					if(histVO.getCriteriaIdCount()>0){
					VO.setRecertificationStatus(histVO.getRecertificationStatus());
					}
					VO.setChangingComments(histVO.getChangingComments());
					formattedHistList.add(VO);
				}
			}
			getRequest().setAttribute(SPNActionConstants.BG_PROVIDERNAME, providerName);
			getRequest().setAttribute(SPNActionConstants.BG_FORMATTEDHISTLIST, formattedHistList);

		}
		catch (Exception e) {
			logger.error("Exception in displayBackgroundHistory method of SPNAuditorSearchTabAction java class", e);
		}
		return "success";
		
	}
	/**
	 * 
	 * @return LookupService
	 */
	public LookupService getLookupService() {
		return lookupService;
	}

	/**
	 * 
	 * @param lookupService
	 */
	public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}

	/**
	 * 
	 * @return SPNBuyerServices
	 */
	public SPNBuyerServices getSpnBuyerServices() {
		return spnBuyerServices;
	}

	/**
	 * 
	 * @param spnBuyerServices
	 */
	public void setSpnBuyerServices(SPNBuyerServices spnBuyerServices) {
		this.spnBuyerServices = spnBuyerServices;
	}

	public SPNAuditorSearchModel getModel() {
		return model;
	}

	/**
	 * 
	 * @return AuditorSearchService
	 */
	public AuditorSearchService getAuditorSearchService() {
		return auditorSearchService;
	}

	/**
	 * 
	 * @param auditorSearchService
	 */
	public void setAuditorSearchService(
			AuditorSearchService auditorSearchService) {
		this.auditorSearchService = auditorSearchService;
	}

	public SPNAuditorService getSpnAuditorService() {
		return spnAuditorService;
	}

	public void setSpnAuditorService(SPNAuditorService spnAuditorService) {
		this.spnAuditorService = spnAuditorService;
	}

	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}

	public String getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(String iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public String getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String[][] getAaData() {
		return aaData;
	}

	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}

}
