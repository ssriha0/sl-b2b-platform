package com.newco.marketplace.web.delegatesImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.externalCalendarSync.ExternalCalendarSyncService;
import com.newco.marketplace.business.iBusiness.calendar.ICalendarPortalBO;
import com.newco.marketplace.business.iBusiness.externalcalendar.IExternalCalendarIntegrationBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICalendarPortalDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.RejectServiceOrderDTO;
import com.newco.marketplace.web.dto.d2cproviderportal.CalendarPortalResponseDto;
import com.newco.marketplace.web.dto.d2cproviderportal.ErrorDto;

public class CalendarPortalDelegateImpl implements ICalendarPortalDelegate {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CalendarPortalDelegateImpl.class);

	private final ICalendarPortalBO calendarPortalBO;
	private final IProviderInfoPagesBO providerInfoPagesBOImpl;
	private final IExternalCalendarIntegrationBO externalCalendarIntegrationBO;
	private final ExternalCalendarSyncService externalCalendarSyncService;
	protected ISOMonitorDelegate soMonitorDelegate;
	protected ISODetailsDelegate detailsDelegate;

	CalendarPortalDelegateImpl(ICalendarPortalBO calendarPortalBO, IProviderInfoPagesBO providerInfoPagesBO,
			IExternalCalendarIntegrationBO externalCalendarIntegrationBO, ExternalCalendarSyncService externalCalendarSyncService,
			ISODetailsDelegate delegate, ISOMonitorDelegate soMonitorDelegate) {
		this.calendarPortalBO = calendarPortalBO;
		this.providerInfoPagesBOImpl = providerInfoPagesBO;
		this.externalCalendarIntegrationBO = externalCalendarIntegrationBO;
		this.externalCalendarSyncService = externalCalendarSyncService;
		this.detailsDelegate = delegate;
		this.soMonitorDelegate = soMonitorDelegate;
	}

	public ExternalCalendarDTO saveOrUpdateExternalCalendarDetail(int firmID, int personId, String oAuthCode,String contextPath){
		ExternalCalendarDTO externalCalendarDetail = null;
		try {
			externalCalendarDetail = externalCalendarSyncService.connectToNewExternalCalendar(firmID, personId, oAuthCode, contextPath);
			externalCalendarIntegrationBO.saveOrUpdateExternalCalendarDetail(externalCalendarDetail);
		} catch (BusinessServiceException e) {
			logger.error("Error occuring in integrating with new external calendar:= " + e);
		}
		return externalCalendarDetail;
	}
	
	public ExternalCalendarDTO getExternalCalendarDetial(int personId){
		ExternalCalendarDTO externalCalendarDetail = null;
		try {
			externalCalendarDetail = externalCalendarIntegrationBO.getExternalCalendarDetail(personId);
		} catch (BusinessServiceException e) {
			logger.error("Error fetching external calendar detail:= "+e);
		}
		return externalCalendarDetail;
	
	}

	public CalendarPortalResponseDto getVendorEvents(String vendorIdStr, Date startDate, Date endDate) {
		logger.info("start getVendorEvents method of CalendarPortalDelegateImpl");
		CalendarPortalResponseDto response = new CalendarPortalResponseDto();
		try {
			response.setResult(calendarPortalBO.getVendorEvents(vendorIdStr, startDate, endDate));
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in getVendorEvents CalendarPortalDelegateImpl : ", exception);
			response.setError(getErrorObject("0001", "Error occured"));
		}
		logger.info("end getVendorEvents method of CalendarPortalDelegateImpl");
		return response;
	}

	public CalendarPortalResponseDto acceptSO(String soId, Integer routedResourceId, Integer companyID, ServiceDatetimeSlot selectedSlot) {
		logger.info("start getVendorEvents method of CalendarPortalDelegateImpl");
		CalendarPortalResponseDto response = new CalendarPortalResponseDto();
		try {

			ServiceOrder so = soMonitorDelegate.getServiceOrder(soId);
			if (null != so) {

				String groupId = so.getGroupId();
				int selectedResourceId = 0;
				int remainingTimeToAcceptSO = 0;

				List<RoutedProvider> routedResourceIds = so.getRoutedResources();
				if (null != routedResourceIds && null != routedResourceId) {
					for (RoutedProvider routedPro : routedResourceIds) {
						if (routedPro.getResourceId().equals(routedResourceId)) {
							selectedResourceId = routedResourceId;
							break;
						}
					}
					
					if (0 == selectedResourceId) {
						logger.error("routed resource id is not present in routed pro list: " + routedResourceId);
						throw new BusinessServiceException("routed resource id is not present in routed pro list");
					}
				} else {
					logger.error("selected provider is not present in routed list: " + routedResourceId);
					throw new BusinessServiceException("Selected provider is not present in routed list");
				}

				String assignee = "typeProvider";

				ServiceDatetimeSlot slotToUpdate = null;

				List<ServiceDatetimeSlot> slots = detailsDelegate.getSODateTimeSlots(soId);
				if (null != slots && slots.size() > 0) {
					String newStartTime = Integer.parseInt(selectedSlot.getServiceStartTime().split(":")[0]) > 12 ? (((Integer
							.parseInt(selectedSlot.getServiceStartTime().split(":")[0]) - 12) < 10 ? "0" : "")
							+ (Integer.parseInt(selectedSlot.getServiceStartTime().split(":")[0]) - 12)
							+ ""
							+ ":"
							+ selectedSlot.getServiceStartTime().split(":")[1] + " PM") : (selectedSlot.getServiceStartTime().split(":")[0]
							+ ":" + selectedSlot.getServiceStartTime().split(":")[1] + " AM");

					String newEndTime = Integer.parseInt(selectedSlot.getServiceEndTime().split(":")[0]) > 12 ? (((Integer
							.parseInt(selectedSlot.getServiceEndTime().split(":")[0]) - 12) < 10 ? "0" : "")
							+ (Integer.parseInt(selectedSlot.getServiceEndTime().split(":")[0]) - 12)
							+ ""
							+ ":"
							+ selectedSlot.getServiceEndTime().split(":")[1] + " PM") : (selectedSlot.getServiceEndTime().split(":")[0]
							+ ":" + selectedSlot.getServiceEndTime().split(":")[1] + " AM");

					selectedSlot.setServiceStartTime(newStartTime);
					selectedSlot.setServiceEndTime(newEndTime);

					for (ServiceDatetimeSlot tempSlots : slots) {

						if (tempSlots.getServiceStartDate().equals(selectedSlot.getServiceStartDate())
								&& tempSlots.getServiceEndDate().equals(selectedSlot.getServiceEndDate())
								&& (tempSlots.getServiceStartTime().equals(selectedSlot.getServiceStartTime()) || tempSlots
										.getServiceEndTime().equals(selectedSlot.getServiceEndTime()))) {
							slotToUpdate = new ServiceDatetimeSlot();
							slotToUpdate.setSlotId(tempSlots.getSlotId());
							slotToUpdate.setPreferenceInd(tempSlots.getPreferenceInd());
							break;
						}
					}
				}

				TermsAndConditionsVO termAndCond = detailsDelegate
						.getAcceptServiceOrderTermsAndCond(SOConstants.ACCEPT_TERMS_AND_COND_FOR_AGREEMENT);

				String acceptResponse = "";

				if (StringUtils.isNotEmpty(groupId)) {
					String orderId = groupId;
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptGrpOrder(orderId, selectedResourceId);

					if (remainingTimeToAcceptSO > 0) {
						logger.error("Acceptance time was expired so it was not successfully accepted. Please try again: "
								+ remainingTimeToAcceptSO);
						throw new BusinessServiceException(
								"Acceptance time was expired so it was not successfully accepted. Please try again.");
					} else {
						acceptResponse = detailsDelegate.groupedServiceOrderAccept(groupId, "", selectedResourceId, companyID,
								termAndCond.getTermsCondId(), true, assignee);
					}

				} else {
					String orderId = so.getSoId();
					remainingTimeToAcceptSO = detailsDelegate.getTheRemainingTimeToAcceptSO(orderId, selectedResourceId);

					if (remainingTimeToAcceptSO > 0) {
						logger.error("Acceptance time was expired so it was not successfully accepted. Please try again: "
								+ remainingTimeToAcceptSO);
						throw new BusinessServiceException(
								"Acceptance time was expired so it was not successfully accepted. Please try again.");
					} else {
						acceptResponse = detailsDelegate.serviceOrderAccept(orderId, "", selectedResourceId, companyID,
								termAndCond.getTermsCondId(), true, assignee);
					}
				}

				if (acceptResponse.contains("SUCCESS") || acceptResponse.contains("Success") 
						|| acceptResponse.contains("success") || acceptResponse.trim().equalsIgnoreCase("SUCCESS")) {
					if (null != slotToUpdate) {
						logger.info("Sevice order id: " + slotToUpdate.getSoId() + " Selected slot id: " + slotToUpdate.getSlotId());
						detailsDelegate.updateAcceptedServiceDatetimeSlot(slotToUpdate);
					} else {
						logger.error("Sevice order id: " + soId + ", no matching slots with details. Start: "
								+ selectedSlot.getServiceStartDate() + " " + selectedSlot.getServiceStartTime() + " End: "
								+ selectedSlot.getServiceEndDate() + " " + selectedSlot.getServiceEndTime());
					}

					response.setResult(acceptResponse);
				} else if (acceptResponse.equalsIgnoreCase(OrderConstants.ORDER_BEING_EDITED)) {
					logger.error("SO is in editing state : " + soId);
					throw new BusinessServiceException(OrderConstants.ORDER_BEING_EDITED);
				} else if (acceptResponse.equalsIgnoreCase(OrderConstants.ORDER_IN_CANCELLED_STATUS)) {
					logger.error("SO is in cancelled state : " + soId);
					throw new BusinessServiceException(OrderConstants.ORDER_IN_CANCELLED_STATUS);
				} else if (acceptResponse.equalsIgnoreCase(OrderConstants.ORDER_ACCEPTED_BY_ANOTHER_PROVIDER)
						|| acceptResponse.indexOf(OrderConstants.ORDER_ACCEPTED_BY_ANOTHER_PROVIDER_OF) != -1) {
					logger.error("SO is already accepted : " + soId);
					throw new BusinessServiceException(OrderConstants.ORDER_ACCEPTED_BY_ANOTHER_PROVIDER);
				} else {
					logger.error("Not a valid SO ID : " + acceptResponse);
					throw new BusinessServiceException("SO id is not vaid.");
				}
			} else {
				logger.error("Not a valid SO ID : " + soId);
				throw new BusinessServiceException("SO id is not vaid.");
			}
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in getVendorEvents CalendarPortalDelegateImpl : ", exception);
			response.setError(getErrorObject("0001", "Error occured"));
		} catch (DataServiceException exception) {
			logger.error("Error occured in getVendorEvents CalendarPortalDelegateImpl : ", exception);
			response.setError(getErrorObject("0002", "Error occured"));
		}
		logger.info("end getVendorEvents method of CalendarPortalDelegateImpl");
		return response;
	}

	public CalendarPortalResponseDto rejectSO(String soId, Integer routedResourceId, Integer rejectReasonId, String rejectReasonComment,
			String userName) {
		logger.info("start rejectSO method of CalendarPortalDelegateImpl");
		CalendarPortalResponseDto response = new CalendarPortalResponseDto();
		try {

			ServiceOrder so = soMonitorDelegate.getServiceOrder(soId);
			if (null != so) {
				if (null != routedResourceId && StringUtils.isNotEmpty(rejectReasonComment)) {
					RejectServiceOrderDTO rsoDTO = new RejectServiceOrderDTO();
					rsoDTO.setModifiedBy(userName);
					rsoDTO.setReasonDesc(rejectReasonComment);
					rsoDTO.setReasonId(rejectReasonId != null ? rejectReasonId.intValue() : 0);
					rsoDTO.setResponseId(OrderConstants.PROVIDER_RESP_REJECTED);
					rsoDTO.setSoId(soId);
					rsoDTO.setGroupId(so.getGroupId());

					String strMessage = soMonitorDelegate.rejectServiceOrder(rsoDTO, Arrays.asList(routedResourceId.intValue()));
					if (strMessage.equalsIgnoreCase("ERROR_OCCURED")) {
						logger.error("Error occured while rejecting order: " + soId);
						throw new BusinessServiceException("Error occured while rejecting order.");
					} else {
						response.setResult(strMessage);
					}
				} else {
					logger.error("resource id or rejectReason comment is null. SO ID : " + soId);
					throw new BusinessServiceException("resource id or rejectReason comment is null.");
				}

			} else {
				logger.error("Not a valid SO ID : " + soId);
				throw new BusinessServiceException("SO id is not vaid.");
			}
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in rejectSO CalendarPortalDelegateImpl : ", exception);
			response.setError(getErrorObject("0001", "Error occured"));
		}
		logger.info("end rejectSO method of CalendarPortalDelegateImpl");
		return response;
	}

	/**
	 * Used to create error dto object
	 * 
	 * @param errorCode
	 * @param message
	 * @return
	 */
	private ErrorDto getErrorObject(String errorCode, String message) {
		ErrorDto errorDto = null;
		if (null != errorCode || null != message) {
			errorDto = new ErrorDto();
			errorDto.setErrorCode(errorCode);
			errorDto.setErrorMessage(message);
		}
		return errorDto;
	}
}
