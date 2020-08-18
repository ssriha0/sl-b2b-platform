package com.newco.marketplace.business.externalCalendarSync;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.newco.calendarPortal.Services.impl.VendorSlCalendarService;
import com.newco.marketplace.business.iBusiness.externalcalendar.IExternalCalendarIntegrationBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.ExternalCalendarSyncConstant;
import com.newco.marketplace.utils.ExternalCalendarSyncConstant.CRONOFY_PARAMS;
import com.newco.marketplace.utils.ExternalCalendarSyncConstant.CRONOFY_PROPERTIES;
import com.newco.marketplace.vo.calendarPortal.CalendarEventVO;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;
import com.servicelive.CronofyRestClient;

/**
 * 
 * @author mbhupta
 * 
 * @param <T>
 * @param <V>
 */
public class CronofyCalendarSyncService implements
		ExternalCalendarSyncService {

	private static final Logger logger = Logger
			.getLogger(CronofyCalendarSyncService.class);
	private static String cronofy_base_url;
	private static String cronofy_base_url_v1;
	private static String cronofy_redirect_url;
	private static String cronofy_get_event_path;
	private static String cronofy_post_event_path;
	private static String cronofy_post_oatuh_path;
	private static String cronofy_date_format;
	private static String cronofy_time_zone;
	private static String cronofy_token_type;
	private static String cronofy_include_managed;
	private static int cronofy_sync_period;
	private VendorSlCalendarService vendorSlCalendarService;
	private ExternalCalendarEventMapper externalCalendarEventMapper;
	private IExternalCalendarIntegrationBO externalCalendarIntegrationBO;
	private static boolean initialize = false;
	
	
	
	public CronofyCalendarSyncService() {
		if(!initialize){
			initializeCronofyService();
		}
	}

	private boolean initializeCronofyService() {
		try {
			cronofy_base_url = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_BASE_URL);
			cronofy_base_url_v1 = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_BASE_URL_V1);
			cronofy_redirect_url = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_REDIRECT_URI);
			cronofy_get_event_path = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_GET_EVENTS_API);
			cronofy_post_event_path = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_POST_EVNET_API);
			cronofy_post_oatuh_path = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_POST_OAUTH_API);
			cronofy_date_format = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_DATE_FORMAT);
			cronofy_time_zone = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_TIME_ZONE);
			cronofy_token_type = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_TOKEN_TYPE);
			cronofy_sync_period = Integer.valueOf(PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_SYNC_PERIOD));
			cronofy_include_managed = PropertiesUtils.getPropertyValue(CRONOFY_PROPERTIES.CRONOFY_INCLUDE_MANAGED);
		} catch (Exception exp) {
			this.logger.error("Error while setting values bean : ", exp);
		}
		return true;
	}
	
	public boolean syncExternalEvents(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException {
		List<CalendarEventVO> externalEvents = fetchExternalEvents(externalCalendarDTO);
		for (CalendarEventVO externalEvent : externalEvents) {
			externalEvent.setPersonId(externalCalendarDTO.getPersonId());
			externalEvent.setFirmId(externalCalendarDTO.getFirmId());
			vendorSlCalendarService.providerCalendarInsertOrUpdate(externalEvent);
		}
		return true;
	}

	private List<CalendarEventVO> fetchExternalEvents(ExternalCalendarDTO externalCalendarDTO)
			throws BusinessServiceException {
		CronofyRestClient cronofyRestClient = new CronofyRestClient(
				cronofy_base_url_v1);
		String from = DateUtils.getFormatedDate(DateUtils.subMonth(new Date(), cronofy_sync_period), cronofy_date_format);
		String to = DateUtils.getFormatedDate(DateUtils.addMonth(new Date(), cronofy_sync_period), cronofy_date_format);
		Map<String, String> urlParams = new LinkedHashMap<String, String>();
		urlParams.put(CRONOFY_PARAMS.TZ_ID, cronofy_time_zone);
		urlParams.put(CRONOFY_PARAMS.FROM, from);
		urlParams.put(CRONOFY_PARAMS.TO, to);
		urlParams.put(CRONOFY_PARAMS.INCLUDE_MANAGED, cronofy_include_managed);
		try {
			String response = cronofyRestClient.get(cronofy_get_event_path,
					null, urlParams, cronofy_token_type + ExternalCalendarSyncConstant.SPACE + externalCalendarDTO.getAccess_token());
			return externalCalendarEventMapper.mapToCalendarEventVO(response);
		} catch (MalformedURLException e) {
			throw new BusinessServiceException(e);
		} catch (IOException e) {
			throw new BusinessServiceException(e);
		}
	}

	public ExternalCalendarDTO connectToNewExternalCalendar(int firmID, int personID, String oAuthCode, String contextPath)
			throws BusinessServiceException {
		CronofyRestClient cronofyRestClient = new CronofyRestClient(
				cronofy_base_url);
		JsonObject accessTokenReq = new JsonObject();
		logger.info("Getting auth and refresh tokens...");
		accessTokenReq.addProperty(CRONOFY_PARAMS.CLIENT_ID, "04EgkMlpYDMuX32hourcBI8GAyGqjyli");
		accessTokenReq.addProperty(CRONOFY_PARAMS.CLIENT_SECRET, "HHsG9UeS9dwFkYe-lyH_u_kLkYSE7G4YxhR6CyxXvJj6svcnM3ViP9tkfrMoz3R9i4AhEipbJCmj2tVN16S_RQ");
		accessTokenReq.addProperty(CRONOFY_PARAMS.GRANT_TYPE, CRONOFY_PARAMS.GRANT_TYPE_AUTH_CODE);
		accessTokenReq.addProperty(CRONOFY_PARAMS.CODE, oAuthCode);
		logger.info("Final redirect URL: "+contextPath + cronofy_redirect_url);
		accessTokenReq.addProperty(CRONOFY_PARAMS.REDIRECT_URI, contextPath + cronofy_redirect_url);
		String response;
		ExternalCalendarDTO externalCalendarDTO = null;
		try {
			response = cronofyRestClient.post(cronofy_post_oatuh_path ,null , null, null,accessTokenReq.toString());
			externalCalendarDTO = externalCalendarEventMapper.mapToExternalCalenarDTO(response);
			externalCalendarDTO.setPersonId(personID);
			externalCalendarDTO.setFirmId(firmID);
		} catch (MalformedURLException e) {
			logger.error(e);
			throw new BusinessServiceException(e);
		} catch (IOException e) {
			logger.error(e);
			throw new BusinessServiceException(e);
		}
		return externalCalendarDTO;
	}
	
	public ExternalCalendarDTO updateAccessToken(ExternalCalendarDTO externalCalendarDTO) throws BusinessServiceException{
		CronofyRestClient cronofyRestClient = new CronofyRestClient(
				cronofy_base_url);
		JsonObject accessTokenReq = new JsonObject();
		logger.info("Refreshing auth token...");
		accessTokenReq.addProperty(CRONOFY_PARAMS.CLIENT_ID, "04EgkMlpYDMuX32hourcBI8GAyGqjyli");
		accessTokenReq.addProperty(CRONOFY_PARAMS.CLIENT_SECRET, "HHsG9UeS9dwFkYe-lyH_u_kLkYSE7G4YxhR6CyxXvJj6svcnM3ViP9tkfrMoz3R9i4AhEipbJCmj2tVN16S_RQ");
		accessTokenReq.addProperty(CRONOFY_PARAMS.GRANT_TYPE, CRONOFY_PARAMS.GRANT_TYPE_REFRESH_TOKEN);
		accessTokenReq.addProperty(CRONOFY_PARAMS.REFRESH_TOKEN, externalCalendarDTO.getRefresh_token());
		String response;
		String new_access_token;
		try {
			response = cronofyRestClient.post(cronofy_post_oatuh_path ,null , null, null,accessTokenReq.toString());
			new_access_token = externalCalendarEventMapper.retriveAccessToken(response);
			externalCalendarDTO.setAccess_token(new_access_token);
			externalCalendarIntegrationBO.saveUpdatedAccessToken(externalCalendarDTO);
		} catch (MalformedURLException e) {
			logger.error(e);
			throw new BusinessServiceException(e);
		} catch (IOException e) {
			logger.error(e);
			throw new BusinessServiceException(e);
		} 
		return externalCalendarDTO;
	}

	public boolean syncInternalEvents() throws BusinessServiceException {/*
		mock();
		CronofyRestClient cronofyRestClient = new CronofyRestClient(
				cronofy_base_url_v1);
		List<CalendarEventVO> internalEvents = vendorSlCalendarService
				.getSlProviderCalenderDetail(personId, startDate, endDate,
						startTime, endTime);
		logger.info("list of internal events: " + internalEvents.size());
		
			try {
				JsonObject eventObject = externalCalendarEventMapper.mapToExternalEventVO(internalEvents);
				cronofyRestClient.post(cronofy_post_event_path, 
						null, null, access_token, eventObject.toString(),calendar_id);
			} catch (MalformedURLException e) {
				throw new BusinessServiceException(e);
			} catch (IOException e) {
				throw new BusinessServiceException(e);
			}
		
	*/	return true;
	}

	public boolean twoWaySync() {
		// TODO Auto-generated method stub
		return false;
	}

	
	// region Properties getters and setters
	public VendorSlCalendarService getVendorSlCalendarService() {
		return vendorSlCalendarService;
	}

	public void setVendorSlCalendarService(
			VendorSlCalendarService vendorSlCalendarService) {
		this.vendorSlCalendarService = vendorSlCalendarService;
	}

	public ExternalCalendarEventMapper getExternalCalendarEventMapper() {
		return externalCalendarEventMapper;
	}

	public void setExternalCalendarEventMapper(
			ExternalCalendarEventMapper externalCalendarEventMapper) {
		this.externalCalendarEventMapper = externalCalendarEventMapper;
	}

	public IExternalCalendarIntegrationBO getExternalCalendarIntegrationBO() {
		return externalCalendarIntegrationBO;
	}

	public void setExternalCalendarIntegrationBO(
			IExternalCalendarIntegrationBO externalCalendarIntegrationBO) {
		this.externalCalendarIntegrationBO = externalCalendarIntegrationBO;
	}
	// endregion

}
