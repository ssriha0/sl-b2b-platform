/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 *
 *
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.AttachmentType;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.Dimensions;
import com.newco.marketplace.api.beans.so.FileNames;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.History;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.LogEntry;
import com.newco.marketplace.api.beans.so.Note;
import com.newco.marketplace.api.beans.so.Notes;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.Reschedule;
import com.newco.marketplace.api.beans.so.RoutedProviders;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.Task;
import com.newco.marketplace.api.beans.so.Tasks;
import com.newco.marketplace.api.beans.so.retrieve.SOGetResponse;
import com.newco.marketplace.api.beans.so.retrieve.SOSpendLimitHistory;
import com.newco.marketplace.api.beans.so.retrieve.SpendLimitHistory;
import com.newco.marketplace.api.beans.so.retrieve.SpendLimitHistoryResponse;
import com.newco.marketplace.api.beans.so.retrieve.TimeOnSites;
import com.newco.marketplace.api.beans.so.timeonsite.SOTimeOnSiteRequest;
import com.newco.marketplace.api.beans.substatusrespose.GetSubStatusResponse;
import com.newco.marketplace.api.beans.substatusrespose.SoSubStatus;
import com.newco.marketplace.api.beans.substatusrespose.SoSubStatuses;
import com.newco.marketplace.api.beans.substatusrespose.SubStatus;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SODocument;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryListVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.UIUtils;

/**
 * This class would act as a Mapper class for Mapping Service Object to
 * SORetrieveResponse Object.
 *
 * @author Infosys
 * @version 1.0
 */
public class SORetrieveMapper {
	private Logger logger = Logger.getLogger(SORetrieveMapper.class);
	private IServiceOrderBO serviceOrderBO;
	private IOnSiteVisitBO timeOnSiteVisitBO;


	/**
	 * This method is for mapping ServiceOrder Object to RetrieveSOResponse
	 * Object.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @throws DataException
	 * @return RetrieveSOResponse
	 */
	public SOGetResponse adaptRequest(ServiceOrder serviceOrder,
			List<String> responseFilter,Locale locale) throws DataServiceException {

		SOGetResponse retrieveSOResponse = new SOGetResponse();

		// For mapping order status Section
		OrderStatus orderStatus = new OrderStatus();
		mapOrderStatus(serviceOrder, orderStatus);
		if (null != responseFilter) {
			// For mapping General Section
			if (responseFilter.contains(PublicAPIConstant.GENERAL)) {
				GeneralSection sectionGeneral = new GeneralSection();
				mapGeneralSection(serviceOrder, sectionGeneral,locale);
				retrieveSOResponse.setSectionGeneral(sectionGeneral);
			}

			// For mapping Scope Of Work
			if (responseFilter.contains(PublicAPIConstant.SCOPEOFWORK)) {
				ScopeOfWork scopeOfWork = new ScopeOfWork();
				mapScopeOfWork(serviceOrder, scopeOfWork);
				retrieveSOResponse.setScopeOfWork(scopeOfWork);
			}

			// For mapping ServiceLocation
			if (responseFilter.contains(PublicAPIConstant.SERVICELOCATION)) {
				Location serviceLocation = new Location();
				mapServiceLocation(serviceOrder, serviceLocation);
				retrieveSOResponse.setServiceLocation(serviceLocation);
			}
			// For mapping Schedule Details
			if (responseFilter.contains(PublicAPIConstant.SCHEDULE)) {
				Schedule schedule = new Schedule();
				mapServiceOrderSchedule(serviceOrder, schedule);
				retrieveSOResponse.setSchedule(schedule);
			}

			// For mapping Pricing Details
			if (responseFilter.contains(PublicAPIConstant.PRICING)) {
				Pricing pricing = new Pricing();
				mapServiceOrderPricing(serviceOrder, pricing);
				retrieveSOResponse.setPricing(pricing);
			}

			// For mapping Contact Details
			if (responseFilter.contains(PublicAPIConstant.CONTACTS)) {
				Contacts contacts = new Contacts();
				mapServiceOrderContacts(serviceOrder, contacts);
				retrieveSOResponse.setContacts(contacts);
			}

			// For mapping Attachment Details
			if (responseFilter.contains(PublicAPIConstant.ATTACHMENTS)) {
				AttachmentType attachments = new AttachmentType();
				mapServiceOrderDocuments(serviceOrder, attachments,
						retrieveSOResponse);

			}

			// For mapping Parts Details
			if (responseFilter.contains(PublicAPIConstant.PARTS)) {
				Parts parts = new Parts();
				mapServiceOrderParts(serviceOrder, parts, retrieveSOResponse);

			}

			// For mapping Custom References
			if (responseFilter.contains(PublicAPIConstant.CUSTOMREFERENCES)) {
				CustomReferences customReferences = new CustomReferences();
				mapServiceOrderCustomRef(serviceOrder, customReferences,
						retrieveSOResponse);

			}

			// For mapping Notes
			if (responseFilter.contains(PublicAPIConstant.NOTES)) {
				Notes notes = new Notes();
				mapServiceOrderNotes(serviceOrder, notes, retrieveSOResponse);

			}

			// For mapping History
			if (responseFilter.contains(PublicAPIConstant.HISTORY)) {
				History history = new History();
				mapServiceOrderHistory(serviceOrder, history,
						retrieveSOResponse);

			}

			// For mapping Routed Resources
			if (responseFilter.contains(PublicAPIConstant.ROUTEDPROVIDERS)) {
				RoutedProviders routedProviders = new RoutedProviders();
				mapServiceOrderRoutedResources(serviceOrder, routedProviders,
						retrieveSOResponse);

			}
		}

		Results results = Results.getSuccess(ResultsCode.RETRIEVE_RESULT_CODE
				.getMessage());
		retrieveSOResponse.setResults(results);
		retrieveSOResponse.setOrderstatus(orderStatus);
		return retrieveSOResponse;
	}

	/**
	 * This method is for mapping the orderstatus Response
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param orderStatus
	 *            OrderStatus
	 * @return void
	 */
	private void mapOrderStatus(ServiceOrder serviceOrder,
			OrderStatus orderStatus) {

		if (null != serviceOrder.getCreatedDate()) {
			orderStatus.setCreatedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getCreatedDate()));
		}
		if (null != serviceOrder.getRoutedDate()) {
			orderStatus.setPostedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getRoutedDate()));
		}
		orderStatus.setSoId(StringUtils.isEmpty(serviceOrder.getSoId()) ? ""
				: serviceOrder.getSoId());
		orderStatus
				.setStatus(StringUtils.isEmpty(serviceOrder.getStatus()) ? ""
						: serviceOrder.getStatus());
		orderStatus.setSubstatus(StringUtils.isEmpty(serviceOrder
				.getSubStatus()) ? "" : serviceOrder.getSubStatus());

		ProblemResolutionSoVO pbResVo = null;

		try {
			pbResVo = serviceOrderBO.getProblemDesc(serviceOrder.getSoId());
		}  catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}


		if(null!=pbResVo)
		orderStatus.setProblemDescription(pbResVo.getPbComment());
	}

	/**
	 * This method is for mapping the General Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param generalSection
	 *            GeneralSection
	 * @return void
	 */
	private void mapGeneralSection(ServiceOrder serviceOrder,
			GeneralSection generalSection,Locale locale) {

		logger.info("Mapping: General Section --->Starts");
		
		TimeZone localtimezone = TimeUtils.getLocalTimeZone(locale); 

		generalSection.setBuyerTerms(StringUtils.isEmpty(serviceOrder
				.getBuyerTermsCond()) ? "" : ServiceLiveStringUtils.removeHTML(serviceOrder.getBuyerTermsCond()));
		generalSection
				.setOverview(StringUtils.isEmpty(serviceOrder.getSowDs()) ? ""
						: ServiceLiveStringUtils.removeHTML(serviceOrder.getSowDs()));
		generalSection.setSpecialInstructions(ServiceLiveStringUtils.removeHTML(serviceOrder
				.getProviderInstructions()));
		generalSection
				.setTitle(StringUtils.isEmpty(serviceOrder.getSowTitle()) ? ""
						: serviceOrder.getSowTitle());

		generalSection.setPreviousState(serviceOrder.getLastStatus());

		List<SOOnsiteVisitVO> SOTimeOnSiteList=null;
		try{
			SOTimeOnSiteList = timeOnSiteVisitBO.getTimeOnSiteResults(serviceOrder.getSoId());
		}catch (Exception e) {
				logger.error("Exception Occurred" + e.getMessage());
			}

		if (null != SOTimeOnSiteList
				&& (!SOTimeOnSiteList.isEmpty())) {
			List<SOTimeOnSiteRequest> timeOnSiteList = new ArrayList<SOTimeOnSiteRequest>();
			TimeOnSites timeOnSites=new TimeOnSites();
			Iterator<SOOnsiteVisitVO> soOnsiteVisitVO = SOTimeOnSiteList.iterator();
			while (soOnsiteVisitVO.hasNext()) {
				SOTimeOnSiteRequest soTimeOnSite = new SOTimeOnSiteRequest();
				SOOnsiteVisitVO SOOnsiteVisit = soOnsiteVisitVO.next();

				java.util.Date arrivalDate, departureDate;

				if (null != SOOnsiteVisit.getArrivalDate()) {
					arrivalDate = (java.util.Date)SOOnsiteVisit.getArrivalDate();
					HashMap<String,Object> hmArrival = TimeUtils.convertGMTToGivenTimeZone
					(new Timestamp(arrivalDate.getTime()),localtimezone.getID());
					soTimeOnSite.setArrivalDateTime(hmArrival.get(OrderConstants.GMT_TIME).toString());
					}

				if (null != SOOnsiteVisit.getDepartureDate()) {
					departureDate = (java.util.Date)SOOnsiteVisit.getDepartureDate();
					HashMap<String,Object> hmDeparture = TimeUtils.convertGMTToGivenTimeZone
					(new Timestamp(departureDate.getTime()), localtimezone.getID());
					soTimeOnSite.setDepartureDateTime(hmDeparture.get(OrderConstants.GMT_TIME).toString());
					}

				timeOnSiteList.add(soTimeOnSite);
			}
			timeOnSites.setTimeOnSites(timeOnSiteList);
			generalSection.setTimeOnSites(timeOnSites);
		}

	}



	/**
	 * This method is for mapping the Scope Of Work Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param scopeOfWork
	 *            ScopeOfWork
	 * @return void
	 */
	private void mapScopeOfWork(ServiceOrder serviceOrder,
			ScopeOfWork scopeOfWork) {

		logger.info("Mapping: Scope Of Work --->Starts");

		scopeOfWork.setMainCategoryID((null != serviceOrder
				.getPrimarySkillCatId()) ? serviceOrder.getPrimarySkillCatId()
				.toString() : "");
		List<Task> tasks = new ArrayList<Task>();
		Tasks allTasks = new Tasks();
		if (serviceOrder.getTasks() != null
				&& serviceOrder.getTasks().size() > 0) {
			Iterator<ServiceOrderTask> soTaskList = serviceOrder.getTasks()
					.iterator();
			while (soTaskList.hasNext()) {
				ServiceOrderTask soTask = (ServiceOrderTask) soTaskList.next();
				Task task = new Task();
				task.setTaskId(soTask.getTaskId());
				task.setTaskName(StringUtils.isEmpty(soTask.getTaskName()) ? ""
						: soTask.getTaskName());
				task.setCategoryID((null != soTask.getSkillNodeId()) ? soTask
						.getSkillNodeId().toString() : "");
				task.setServiceType(StringUtils
						.isEmpty(soTask.getServiceType()) ? "" : soTask
						.getServiceType());
				task.setTaskComment(ServiceLiveStringUtils.removeHTML(soTask.getTaskComments()));
				tasks.add(task);
			}
			allTasks.setTaskList(tasks);
			scopeOfWork.setTasks(allTasks);
		}

	}

	/**
	 * This method is for mapping the ServiceLocation Section of Response xml
	 * from Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param location
	 *            Location
	 * @return void
	 */

	private void mapServiceLocation(ServiceOrder serviceOrder,
			Location serviceLocation) {
		logger.info("Mapping: Service Location--->Starts");
		int locationTypeId = 0;
		SoLocation soLocation = serviceOrder.getServiceLocation();
		if (null != soLocation.getLocnClassId()) {
			locationTypeId = soLocation.getLocnClassId();
		}

		if (locationTypeId == 1) {

			serviceLocation
					.setLocationType(PublicAPIConstant.LOCATION_TYPE_COMMERCIAL);

		} else if (locationTypeId == 2) {

			serviceLocation
					.setLocationType(PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL);

		}
		serviceLocation.setLocationName(StringUtils.isEmpty(soLocation
				.getLocnName()) ? "" : soLocation.getLocnName());
		serviceLocation.setAddress1(StringUtils
				.isEmpty(soLocation.getStreet1()) ? "" : soLocation
				.getStreet1());
		serviceLocation.setAddress2(soLocation.getStreet2());
		serviceLocation.setCity(StringUtils.isEmpty(soLocation.getCity()) ? ""
				: soLocation.getCity());
		serviceLocation
				.setState(StringUtils.isEmpty(soLocation.getState()) ? ""
						: soLocation.getState());
		serviceLocation.setZip(StringUtils.isEmpty(soLocation.getZip()) ? ""
				: soLocation.getZip());
		serviceLocation.setNote(soLocation.getLocnNotes());
	}

	/**
	 * This method is for mapping the Schedule Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Schedule
	 *            schedule
	 * @return void
	 */
	private void mapServiceOrderSchedule(ServiceOrder serviceOrder,
			Schedule schedule) {

		try {
			Reschedule reschedule=new Reschedule();
		schedule
				.setConfirmWithCustomer(("1".equals(serviceOrder
						.getProviderServiceConfirmInd())) ? PublicAPIConstant.CONFIRM_CUSTOMER_TRUE
						: PublicAPIConstant.CONFIRM_CUSTOMER_FALSE);
		schedule
				.setScheduleType((1 == serviceOrder.getServiceDateTypeId()) ? PublicAPIConstant.DATETYPE_FIXED
						: PublicAPIConstant.DATETYPE_RANGE);

		java.util.Date serviceDate1, serviceDate2;

			if (null != serviceOrder.getServiceDate1()) {
				serviceDate1 = (java.util.Date) TimeUtils.combineDateTime(
						serviceOrder.getServiceDate1(), serviceOrder
								.getServiceTimeStart());
				schedule.setServiceDateTime1(CommonUtility.sdfToDate
						.format(new Timestamp(serviceDate1.getTime())));
			}
			if (null != serviceOrder.getServiceDate2()) {
				serviceDate2 = (java.util.Date) TimeUtils.combineDateTime(
						serviceOrder.getServiceDate2(), serviceOrder
								.getServiceTimeEnd());

				schedule.setServiceDateTime2(CommonUtility.sdfToDate
						.format(new Timestamp(serviceDate2.getTime())));
			}

		if(null!=serviceOrder.getRescheduleServiceDateTypeId())
		{
			reschedule
		.setRescheduleType((1 == serviceOrder.getRescheduleServiceDateTypeId()) ? PublicAPIConstant.DATETYPE_FIXED
				: PublicAPIConstant.DATETYPE_RANGE); }

java.util.Date rescheduleServiceDate1, rescheduleServiceDate2;

	if (null != serviceOrder.getRescheduleServiceDate1()) {
		rescheduleServiceDate1 = (java.util.Date) TimeUtils.combineDateTime(
				serviceOrder.getRescheduleServiceDate1(), serviceOrder
						.getRescheduleServiceTimeStart());
		reschedule.setRescheduleServiceDateTime1(CommonUtility.sdfToDate
				.format(new Timestamp(rescheduleServiceDate1.getTime())));
	}
	if (null != serviceOrder.getRescheduleServiceDate2()) {
		rescheduleServiceDate2 = (java.util.Date) TimeUtils.combineDateTime(
				serviceOrder.getRescheduleServiceDate2(), serviceOrder
						.getRescheduleServiceTimeEnd());

		reschedule.setRescheduleServiceDateTime2(CommonUtility.sdfToDate
				.format(new Timestamp(rescheduleServiceDate2.getTime())));
	}



ServiceOrderRescheduleVO rescheduleDetails = null;
String role=null;
rescheduleDetails = serviceOrderBO.getRescheduleRequestInfo(serviceOrder.getSoId());
if (rescheduleDetails != null && rescheduleDetails.getUserRole() != null) {
	if(rescheduleDetails.getUserRole()==3||rescheduleDetails.getUserRole()==5)
	{
	role = PublicAPIConstant.retrieveSO.BUYER;
	}
	else if(rescheduleDetails.getUserRole()==1)
	{
		role = PublicAPIConstant.retrieveSO.PROVIDER;}
	}
reschedule.setRescheduledBy(role);
schedule.setReschedule(reschedule);
		} catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}
	}

	/**
	 * This method is for mapping the Pricing Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Pricing
	 *            pricing
	 * @return void
	 */
	private void mapServiceOrderPricing(ServiceOrder serviceOrder,
			Pricing pricing) {
		logger.info("Mapping: Pricing --->Starts");

		pricing
				.setLaborSpendLimit((null != serviceOrder.getSpendLimitLabor()) ? serviceOrder
						.getSpendLimitLabor().toString()
						: "0");
		pricing
				.setPartsSpendLimit((null != serviceOrder.getSpendLimitParts()) ? serviceOrder
						.getSpendLimitParts().toString()
						: "0");
		pricing
		.setFinalPriceForLabor((null != serviceOrder.getLaborFinalPrice()) ? serviceOrder
				.getLaborFinalPrice().toString()
				: "0");
pricing
		.setFinalPriceForParts((null != serviceOrder.getPartsFinalPrice()) ? serviceOrder
				.getPartsFinalPrice().toString()
				: "0");
pricing.setPriceModel(serviceOrder.getPriceModel());
	}

	/**
	 * This method is for mapping Contacts Section of Response xml from Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Contacts
	 *            contacts
	 * @return void
	 */
	private void mapServiceOrderContacts(ServiceOrder serviceOrder,
			Contacts contacts) {

		logger.info("Mapping: Contact Details--->Starts");
		List<com.newco.marketplace.api.beans.so.Contact> contactList = new ArrayList<com.newco.marketplace.api.beans.so.Contact>();
		com.newco.marketplace.api.beans.so.Contact respContact = new com.newco.marketplace.api.beans.so.Contact();
		if (null != serviceOrder.getServiceContact()) {
			respContact = mapContactDetails(serviceOrder.getServiceContact(),
					serviceOrder.getServiceContactAlt(),
					PublicAPIConstant.SERVICE);
			if (null != respContact) {
				contactList.add(respContact);
			}
		}
		if (null != serviceOrder.getEndUserContact()) {
			respContact = mapContactDetails(serviceOrder.getEndUserContact(),
					serviceOrder.getAltEndUserContact(),
					PublicAPIConstant.END_USER);
			if (null != respContact) {
				contactList.add(respContact);
			}
		}
		if (null != serviceOrder.getAltBuyerResource()
				&& null != serviceOrder.getAltBuyerResource()
						.getBuyerResContact()) {
			Contact buyerSupportContact = serviceOrder.getAltBuyerResource()
					.getBuyerResContact();
			if (buyerSupportContact != null) {
				com.newco.marketplace.api.beans.so.Contact contact = new com.newco.marketplace.api.beans.so.Contact();
				contact.setContactLocnType(PublicAPIConstant.BUYER_SUPPORT);
				contact
						.setFirstName(buyerSupportContact.getFirstName() != null ? buyerSupportContact
								.getFirstName()
								: "");
				if (!"".equals(contact.getFirstName())) {
					contact
							.setLastName(buyerSupportContact.getLastName() != null ? buyerSupportContact
									.getLastName()
									: "");
					contact.setEmail(StringUtils.isEmpty(buyerSupportContact
							.getEmail()) ? null : buyerSupportContact
							.getEmail());

					Map<String, String> phoneList = new LinkedHashMap<String, String>(
							5);

					if (null != buyerSupportContact.getPhoneNo()
							&& (!"".equals(buyerSupportContact.getPhoneNo()))) {
						phoneList.put(PublicAPIConstant.WORK,
								buyerSupportContact.getPhoneNo());
					}
					if (null != buyerSupportContact.getCellNo()
							&& (!"".equals(buyerSupportContact.getCellNo()))) {
						phoneList.put(PublicAPIConstant.MOBILE,
								buyerSupportContact.getCellNo());
					}
					if (null != buyerSupportContact.getHomeNo()
							&& (!"".equals(buyerSupportContact.getHomeNo()))) {
						phoneList.put(PublicAPIConstant.HOME,
								buyerSupportContact.getHomeNo());
					}
					if (null != buyerSupportContact.getPagerText()
							&& (!"".equals(buyerSupportContact.getPagerText()))) {
						phoneList.put(PublicAPIConstant.PAGER,
								buyerSupportContact.getPagerText());
					}
					if (null != buyerSupportContact.getFaxNo()
							&& (!"".equals(buyerSupportContact.getFaxNo()))) {
						phoneList.put(PublicAPIConstant.OTHER,
								buyerSupportContact.getFaxNo());
					}
					if (phoneList.size() > 0) {
						Iterator<String> phoneTypes = phoneList.keySet()
								.iterator();

						while (phoneTypes.hasNext()) {
							if (phoneList.size() >= 2) {
								String phoneType = phoneTypes.next();
								String phoneNumber = phoneList.get(phoneType);
								if (!PublicAPIConstant.WORK.equals(phoneType)) {
									contact
											.setPrimaryPhone(mapBuyerSupportPhoneDetails(
													phoneNumber, null,
													phoneType));
								} else {
									contact
											.setPrimaryPhone(mapBuyerSupportPhoneDetails(
													phoneNumber,
													buyerSupportContact
															.getPhoneNoExt(),
													phoneType));
								}
								phoneType = phoneTypes.next();
								phoneNumber = phoneList.get(phoneType);
								contact
										.setAltPhone(mapBuyerSupportPhoneDetails(
												phoneNumber, null, phoneType));

							} else {
								String phoneType = phoneTypes.next();
								String phoneNumber = phoneList.get(phoneType);
								if (!PublicAPIConstant.WORK.equals(phoneType)) {
									contact
											.setPrimaryPhone(mapBuyerSupportPhoneDetails(
													phoneNumber, null,
													phoneType));
								} else {
									contact
											.setPrimaryPhone(mapBuyerSupportPhoneDetails(
													phoneNumber,
													buyerSupportContact
															.getPhoneNoExt(),
													phoneType));
								}
							}

							break;
						}
					}
					contactList.add(contact);
				}

			}

		}
		if (null != serviceOrder.getVendorResourceContact()) {
			respContact = mapContactDetails(serviceOrder
					.getVendorResourceContact(), null,
					PublicAPIConstant.PROVIDER);
			if (null != respContact) {
				if (null != serviceOrder
						.getServiceproviderContactOnQuickLinks()
						&& null != serviceOrder
								.getServiceproviderContactOnQuickLinks()
								.getAlternatePhoneNo()) {
					com.newco.marketplace.api.beans.so.Phone phone = new com.newco.marketplace.api.beans.so.Phone();
					phone.setNumber((StringUtils.isEmpty(serviceOrder
							.getServiceproviderContactOnQuickLinks()
							.getAlternatePhoneNo()) ? "" : UIUtils
							.formatPhoneNumber(serviceOrder
									.getServiceproviderContactOnQuickLinks()
									.getAlternatePhoneNo())));
					phone.setPhoneType(PublicAPIConstant.WORK);
					respContact.setAltPhone(phone);
				}
				contactList.add(respContact);
			}
		}
		if (null != serviceOrder.getParts()) {
			Iterator<Part> soPartsList = serviceOrder.getParts().iterator();
			while (soPartsList.hasNext()) {
				Part part = soPartsList.next();
				if (null != part.getPickupContact()) {
					respContact = mapContactDetails(part.getPickupContact(),
							part.getAltPickupContact(),
							PublicAPIConstant.PICKUP);
					if (null != respContact) {
						contactList.add(respContact);
					}
				}
			}

		}
		contacts.setContactList(contactList);

	}

	/**
	 * This method is for mapping Contact Details.
	 *
	 * @param soContact
	 *            Contact
	 * @param soAltContact
	 *            Contact
	 * @param contactType
	 *            String
	 * @return contact
	 */
	private com.newco.marketplace.api.beans.so.Contact mapContactDetails(
			Contact soContact, Contact soAltContact, String contactType) {
		logger.info("Inside mapContactDetails--->Start");
		com.newco.marketplace.api.beans.so.Contact contact = new com.newco.marketplace.api.beans.so.Contact();
		com.newco.marketplace.api.beans.so.Phone phone = null;
		contact.setContactLocnType(contactType);
		contact.setFirstName(StringUtils.isEmpty(soContact.getFirstName()) ? ""
				: soContact.getFirstName());
		if ("".equals(contact.getFirstName())) {
			return null;
		}
		contact.setLastName(StringUtils.isEmpty(soContact.getLastName()) ? ""
				: soContact.getLastName());
		contact.setEmail(StringUtils.isEmpty(soContact.getEmail()) ? null
				: soContact.getEmail());

		phone = mapPhoneDetails(soContact);
		if ("".equals(phone.getNumber())) {
			phone = null;
		}
		contact.setPrimaryPhone(phone);
		if (null != soAltContact) {
			phone = mapPhoneDetails(soAltContact);
			if ("".equals(phone.getNumber())) {
				phone = null;
			}
			contact.setAltPhone(phone);
		}
		if ("".equals(contact.getFirstName())) {
			return null;
		}
		return contact;

	}

	/**
	 * This method is for mapping Phone Details.
	 *
	 * @param contact
	 *            Contact
	 *
	 * @return Phone
	 */
	private com.newco.marketplace.api.beans.so.Phone mapPhoneDetails(
			Contact contact) {
		logger.info("Inside mapPhoneDetails--->Start");
		com.newco.marketplace.api.beans.so.Phone phone = new com.newco.marketplace.api.beans.so.Phone();
		phone.setNumber((StringUtils.isEmpty(contact.getPhoneNo()) ? ""
				: UIUtils.formatPhoneNumber(contact.getPhoneNo())));
		if (!"".equals(phone.getNumber())) {
			phone.setPhoneType(getPhoneType(contact.getPhoneClassId()));
		} else {
			phone.setPhoneType("");
		}
		phone.setExtension(StringUtils.isEmpty(contact.getPhoneNoExt()) ? ""
				: contact.getPhoneNoExt());
		return phone;
	}

	/**
	 * This method is for mapping BuyerSupport Phone Details.
	 *
	 * @param contact
	 *            Contact
	 *
	 * @return Phone
	 */
	private com.newco.marketplace.api.beans.so.Phone mapBuyerSupportPhoneDetails(
			String number, String ext, String type) {
		logger.info("Inside BuyerSupport mapPhoneDetails--->Start");
		com.newco.marketplace.api.beans.so.Phone phone = new com.newco.marketplace.api.beans.so.Phone();
		phone.setNumber((StringUtils.isEmpty(number) ? "" : UIUtils
				.formatPhoneNumber(number)));
		phone.setPhoneType(type);

		phone.setExtension(StringUtils.isEmpty(ext) ? "" : ext);
		return phone;
	}

	/**
	 * This method is for mapping the Attachments Section of Create Request for
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param attachments
	 *            Attachments
	 * @return void
	 */
	private void mapServiceOrderDocuments(ServiceOrder serviceOrder,
			AttachmentType attachments, SOGetResponse retrieveSOResponse) {

		logger.info("Mapping: Document --->Starts");
		List<FileNames> fileNameList = new ArrayList<FileNames>();

		if (null != serviceOrder.getSoDocuments()
				&& serviceOrder.getSoDocuments().size() > 0) {
			for (SODocument soDocument : serviceOrder.getSoDocuments()) {
				FileNames fileNames=new FileNames();
				fileNames.setFile(soDocument.getFileName());
				if(PublicAPIConstant.VIDEO_TYPE==soDocument.getDocCategoryId())
				{
					fileNames.setVideo(PublicAPIConstant.TRUE);
					String videoUrl = PropertiesUtils.getFMPropertyValue(Constants.AppPropConstants.VIDEO_BASE_URL);
					videoUrl=videoUrl.replace(PublicAPIConstant.DELIMITER, soDocument.getFileName());
					fileNames.setVideoUrl(videoUrl);
				}
				fileNameList.add(fileNames);
					}

			attachments.setFileNames(fileNameList);

			retrieveSOResponse.setAttachments(attachments);
		}

	}

	/**
	 * This method is for mapping the Parts Section of Create Request for
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Parts
	 *            parts
	 * @return void
	 */
	private void mapServiceOrderParts(ServiceOrder serviceOrder, Parts parts,
			SOGetResponse retrieveSOResponse) {

		logger.info("Mapping: Parts --->Starts");
		List<com.newco.marketplace.api.beans.so.Part> partList = new ArrayList<com.newco.marketplace.api.beans.so.Part>();
		if (null != serviceOrder.getParts()
				&& (!serviceOrder.getParts().isEmpty())) {
			Iterator<Part> soPartsList = serviceOrder.getParts().iterator();
			while (soPartsList.hasNext()) {
				com.newco.marketplace.api.beans.so.Part part = new com.newco.marketplace.api.beans.so.Part();
				Part soPart = (Part) soPartsList.next();
				part.setManufacturer(StringUtils.isEmpty(soPart
						.getManufacturer()) ? "" : soPart.getManufacturer());
				part.setPartId(soPart.getPartId());
				part.setModel(StringUtils.isEmpty(soPart.getModelNumber()) ? ""
						: soPart.getModelNumber());
				part
						.setDescription(StringUtils.isEmpty(soPart.getPartDs()) ? ""
								: soPart.getPartDs());
				part
						.setQuantity(StringUtils.isEmpty(soPart.getQuantity()) ? "1"
								: soPart.getQuantity());

				// Dimension
				Dimensions dimensions = new Dimensions();
				dimensions
						.setHeight(StringUtils.isEmpty(soPart.getHeight()) ? "0"
								: soPart.getHeight());
				dimensions
						.setLength(StringUtils.isEmpty(soPart.getLength()) ? "0"
								: soPart.getLength());
				dimensions
						.setWidth(StringUtils.isEmpty(soPart.getWidth()) ? "0"
								: soPart.getWidth());
				dimensions
						.setWeight(StringUtils.isEmpty(soPart.getWeight()) ? "0"
								: soPart.getWeight());

				if (null != soPart.getMeasurementStandard()
						&& OrderConstants.ENGLISH.equalsIgnoreCase((soPart
								.getMeasurementStandard().toString()))) {
					dimensions
							.setMeasurementType(PublicAPIConstant.STANDARD_ENGLISH);
				} else if (null != soPart.getMeasurementStandard()
						&& OrderConstants.METRIC.equalsIgnoreCase(soPart
								.getMeasurementStandard().toString())) {
					dimensions
							.setMeasurementType(PublicAPIConstant.STANDARD_METRIC);
				}
				part.setDimensions(dimensions);

				Carrier shippingCarrier = soPart.getShippingCarrier();
				Carrier returnCarrier = soPart.getReturnCarrier();
				if (null != shippingCarrier) {
					part.setShipCarrier(StringUtils.isEmpty(shippingCarrier
							.getCarrierName()) ? "" : shippingCarrier
							.getCarrierName());
					part.setShipTracking(StringUtils.isEmpty(shippingCarrier
							.getTrackingNumber()) ? "" : shippingCarrier
							.getTrackingNumber());
				}
				if (null != returnCarrier) {
					part.setReturnCarrier(StringUtils.isEmpty(returnCarrier
							.getCarrierName()) ? "" : returnCarrier
							.getCarrierName());
					part.setReturnTracking(StringUtils.isEmpty(returnCarrier
							.getTrackingNumber()) ? "" : returnCarrier
							.getTrackingNumber());
				}
				if (null != soPart.isProviderBringPartInd()) {
					part
							.setRequiresPickup(soPart.isProviderBringPartInd() ? "true"
									: "false");
				} else {
					part.setRequiresPickup("false");
				}

				if (null != soPart.getPickupLocation()) {

					SoLocation soPickupLocation = soPart.getPickupLocation();
					Location pickupLocation = new Location();
					int locationTypeId = 0;
					if (null != soPickupLocation.getLocnClassId()) {
						locationTypeId = soPickupLocation.getLocnClassId();
					}

					if (locationTypeId == 1) {

						pickupLocation
								.setLocationType(PublicAPIConstant.LOCATION_TYPE_COMMERCIAL);

					} else if (locationTypeId == 2) {

						pickupLocation
								.setLocationType(PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL);

					}
					pickupLocation.setLocationName(StringUtils
							.isEmpty(soPickupLocation.getLocnName()) ? ""
							: soPickupLocation.getLocnName());
					pickupLocation.setAddress1(StringUtils
							.isEmpty(soPickupLocation.getStreet1()) ? ""
							: soPickupLocation.getStreet1());
					pickupLocation.setAddress2(soPickupLocation.getStreet2());
					pickupLocation.setCity(StringUtils.isEmpty(soPickupLocation
							.getCity()) ? "" : soPickupLocation.getCity());
					pickupLocation.setState(StringUtils
							.isEmpty(soPickupLocation.getState()) ? ""
							: soPickupLocation.getState());
					pickupLocation.setZip(StringUtils.isEmpty(soPickupLocation
							.getZip()) ? "" : soPickupLocation.getZip());
					pickupLocation.setNote(soPickupLocation.getLocnNotes());
					part.setPickupLocation(pickupLocation);

				}
				partList.add(part);
			}
			parts.setPartList(partList);
			retrieveSOResponse.setParts(parts);
		}

	}

	/**
	 * This method is for mapping the Parts Section of Create Request to Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param customReferences
	 *            CustomReferences
	 * @return void
	 */
	private void mapServiceOrderCustomRef(ServiceOrder serviceOrder,
			CustomReferences customReferences,
			SOGetResponse retrieveSOResponse) {
		logger.info("Mapping: Customer Reference --->Starts");
		List<CustomReference> customReferenceList = new ArrayList<CustomReference>();

		if (null != serviceOrder.getCustomRefs()
				&& (!serviceOrder.getCustomRefs().isEmpty())) {
			Iterator<ServiceOrderCustomRefVO> soCustomRefsVoList = serviceOrder
					.getCustomRefs().iterator();
			while (soCustomRefsVoList.hasNext()) {
				CustomReference customReference = new CustomReference();
				ServiceOrderCustomRefVO serviceOrderCustomRefVO = soCustomRefsVoList
						.next();
				customReference.setName(StringUtils
						.isEmpty(serviceOrderCustomRefVO.getRefType()) ? ""
						: serviceOrderCustomRefVO.getRefType());
				customReference.setValue(StringUtils
						.isEmpty(serviceOrderCustomRefVO.getRefValue()) ? ""
						: serviceOrderCustomRefVO.getRefValue());
				customReferenceList.add(customReference);
			}
			customReferences.setCustomRefList(customReferenceList);
			retrieveSOResponse.setCustomReferences(customReferences);
		}

	}

	/**
	 * This method is for mapping the Notes Section for service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param notes
	 *            Notes
	 * @return void
	 */
	private void mapServiceOrderNotes(ServiceOrder serviceOrder, Notes notes,
			SOGetResponse retrieveSOResponse) {
		logger.info("Mapping: ServiceOrderNotes --->Starts");
		List<Note> noteList = new ArrayList<Note>();

		if (null != serviceOrder.getSoNotes()
				&& (!serviceOrder.getSoNotes().isEmpty())) {
			Iterator<ServiceOrderNote> soNoteList = serviceOrder.getSoNotes()
					.iterator();
			while (soNoteList.hasNext()) {
				Note note = new Note();
				ServiceOrderNote serviceOrderNote = soNoteList.next();
				// Get only the public notes
				if(serviceOrderNote.getPrivateId() == 0){
					note.setRoleId((null != serviceOrderNote.getRoleId()) 
						? serviceOrderNote.getRoleId(): new Integer(0));
					note.setEntityID((null != serviceOrderNote.getEntityId()) 
						? serviceOrderNote.getEntityId(): new Integer(0));
					note.setCreatedDate((null != serviceOrderNote.getCreatedDate()) 
						? CommonUtility.sdfToDate.format(serviceOrderNote.getCreatedDate()) : "");
					note.setCreatedByName(StringUtils.isEmpty(serviceOrderNote.getCreatedByName())
						? "" : serviceOrderNote.getCreatedByName());
					note.setSubject(StringUtils.isEmpty(serviceOrderNote.getSubject())
						? "" : serviceOrderNote.getSubject());
					note.setNoteBody(StringUtils.isEmpty(serviceOrderNote.getNote()) 
						? "": serviceOrderNote.getNote());
				if(null!=serviceOrderNote.getNoteTypeId()&&1==serviceOrderNote.getNoteTypeId())
				    note.setNoteType(PublicAPIConstant.retrieveSO.SUPPORT_NOTE);
				else if(null!=serviceOrderNote.getNoteTypeId()&&2==serviceOrderNote.getNoteTypeId())
					note.setNoteType(PublicAPIConstant.retrieveSO.GENERAL_NOTE);
				else if(null!=serviceOrderNote.getNoteTypeId()&&3==serviceOrderNote.getNoteTypeId())
					note.setNoteType(PublicAPIConstant.retrieveSO.CLAIM_NOTE);
				noteList.add(note);
				}
			}
			if(null!= noteList && noteList.size()>0){
				notes.setNotes(noteList);
				retrieveSOResponse.setNotes(notes);
			}
		}

	}

	/**
	 * This method is for mapping the History Section for service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param history
	 *            History
	 * @return void
	 * @throws DataServiceException
	 */
	private void mapServiceOrderHistory(ServiceOrder serviceOrder,
			History history, SOGetResponse retrieveSOResponse)
			throws DataServiceException {
		logger.info("Mapping: ServiceOrder History --->Starts");
		List<SoLoggingVo> soLogList = serviceOrderBO
				.getSoLogDetails(serviceOrder.getSoId());
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		if (null != soLogList && (!soLogList.isEmpty())) {
			Iterator<SoLoggingVo> soLogs = soLogList.iterator();
			while (soLogs.hasNext()) {
				SoLoggingVo soLoggingVo = soLogs.next();
				LogEntry logEntry = new LogEntry();
				logEntry
						.setRoleId((null != soLoggingVo.getRoleId()) ? soLoggingVo
								.getRoleId()
								: new Integer(0));
				logEntry
						.setEntityID((null != soLoggingVo.getEntityId()) ? soLoggingVo
								.getEntityId()
								: new Integer(0));
				logEntry
						.setCreatedDate((null != soLoggingVo.getCreatedDate()) ? CommonUtility.sdfToDate
								.format(soLoggingVo.getCreatedDate())
								: "");
				logEntry.setCreatedByName(StringUtils.isEmpty(soLoggingVo
						.getCreatedByName()) ? "" : soLoggingVo
						.getCreatedByName());
				logEntry.setAction(StringUtils.isEmpty(soLoggingVo
						.getActionName()) ? "" : soLoggingVo.getActionName());
				logEntry.setComment(StringUtils.isEmpty(soLoggingVo
						.getComment()) ? "" : soLoggingVo.getComment());
				logEntry.setValue(StringUtils
						.isEmpty(soLoggingVo.getNewValue()) ? "" : soLoggingVo
						.getNewValue());
				logEntries.add(logEntry);
			}
			history.setLogEntries(logEntries);
			retrieveSOResponse.setHistory(history);
		}

	}

	/**
	 * This method is for mapping the RoutedProvider Section for service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param routedProviders
	 *            RoutedProviders
	 * @return void
	 */
	private void mapServiceOrderRoutedResources(ServiceOrder serviceOrder,
			RoutedProviders routedProviders,
			SOGetResponse retrieveSOResponse) {
		Timestamp expireDateTimeCombined = null;

		logger.info("Mapping: Customer Reference --->Starts");
		List<com.newco.marketplace.api.beans.so.RoutedProvider> routedProList = new ArrayList<com.newco.marketplace.api.beans.so.RoutedProvider>();

		if (null != serviceOrder.getRoutedResources()
				&& (!serviceOrder.getRoutedResources().isEmpty())) {
			Iterator<RoutedProvider> soRouteList = serviceOrder
					.getRoutedResources().iterator();
			while (soRouteList.hasNext()) {
				Pricing pricing = new Pricing();
				Schedule schedule = new Schedule();
				com.newco.marketplace.api.beans.so.RoutedProvider routedProvider = new com.newco.marketplace.api.beans.so.RoutedProvider();
				RoutedProvider soRoutedProvider = soRouteList.next();
				routedProvider.setResourceId((null != soRoutedProvider
						.getResourceId()) ? soRoutedProvider.getResourceId()
						: new Integer(0));
				routedProvider.setCompanyID((null != soRoutedProvider
						.getVendorId()) ? soRoutedProvider.getVendorId()
						: new Integer(0));

				if (null != soRoutedProvider.getCreatedDate()) {
					routedProvider.setCreatedDate(CommonUtility.sdfToDate
							.format(soRoutedProvider.getCreatedDate()));
				}
				routedProvider
						.setResponse((StringUtils.isEmpty(soRoutedProvider
								.getProviderRespDescription())) ? null
								: soRoutedProvider.getProviderRespDescription());
				if(null!=routedProvider.getResponse()&&routedProvider.getResponse().equalsIgnoreCase(PublicAPIConstant.ACCEPTED)){

					if(null!=serviceOrder.getAcceptedResource()&&null!=serviceOrder.getAcceptedResource().getResourceContact()){
					com.newco.marketplace.api.beans.so.Contact providerContact = new com.newco.marketplace.api.beans.so.Contact();
					providerContact=mapContactDetails(serviceOrder.getAcceptedResource().getResourceContact(), null,
							PublicAPIConstant.PROVIDER);
					routedProvider.setProviderContact(providerContact);
					}

				}
				routedProvider.setComment(StringUtils.isEmpty(soRoutedProvider
						.getProviderRespComment()) ? null : soRoutedProvider
						.getProviderRespComment());
				if (null != soRoutedProvider.getConditionalExpirationDate()) {
					if (null != soRoutedProvider.getConditionalExpirationTime()) {
						expireDateTimeCombined = new Timestamp(
								TimeUtils
										.combineDateAndTime(
												soRoutedProvider
														.getConditionalExpirationDate(),
												soRoutedProvider
														.getConditionalExpirationTime(),
												serviceOrder
														.getServiceLocationTimeZone())
										.getTime());
					} else {
						expireDateTimeCombined = soRoutedProvider
								.getConditionalExpirationDate();
					}
					routedProvider.setOfferExpiration(CommonUtility.sdfToDate
							.format(expireDateTimeCombined));
				}
				getSpendDetails(pricing, soRoutedProvider, routedProvider);
				getScheduleDetails(schedule, soRoutedProvider, serviceOrder
						.getServiceLocationTimeZone(), routedProvider);

				routedProList.add(routedProvider);
			}
			routedProviders.setRoutedProviders(routedProList);
			retrieveSOResponse.setRoutedProviders(routedProviders);
		}

	}

	/**
	 * This method is for getting the spend details for Routed Resource.
	 *
	 * @param pricing
	 *            Pricing
	 * @param routedProvider
	 *            RoutedProvider
	 * @return void
	 */
	private void getSpendDetails(Pricing pricing,
			RoutedProvider soRoutedProvider,
			com.newco.marketplace.api.beans.so.RoutedProvider routedProvider) {

		if (null != soRoutedProvider.getConditionalSpendLimit()) {
			pricing.setLaborSpendLimit(soRoutedProvider
					.getConditionalSpendLimit().toString());
			pricing.setPartsSpendLimit("0.0");
			routedProvider.setIncreaseSpend(pricing);
		}

	}

	/**
	 * This method is for getting the Schedule details for Routed Resource.
	 *
	 * @param schedule
	 *            Schedule
	 * @param routedProvider
	 *            RoutedProvider
	 * @param timezone
	 *            String
	 * @return void
	 */
	private void getScheduleDetails(Schedule schedule,
			RoutedProvider soRoutedProvider, String timezone,
			com.newco.marketplace.api.beans.so.RoutedProvider routedProvider) {
		Timestamp startDateTimeCombined = null;
		Timestamp endDateTimeCombined = null;
		if (null != soRoutedProvider.getConditionalChangeDate1()) {
			startDateTimeCombined = new Timestamp(TimeUtils.combineDateAndTime(
					soRoutedProvider.getConditionalChangeDate1(),
					soRoutedProvider.getConditionalStartTime(), timezone)
					.getTime());
			schedule.setServiceDateTime1(CommonUtility.sdfToDate
					.format(startDateTimeCombined));
			schedule.setScheduleType(PublicAPIConstant.DATETYPE_FIXED);
			routedProvider.setRescheduleDates(schedule);
		}
		if (null != soRoutedProvider.getConditionalChangeDate2()) {
			endDateTimeCombined = new Timestamp(TimeUtils.combineDateAndTime(
					soRoutedProvider.getConditionalChangeDate1(),
					soRoutedProvider.getConditionalStartTime(), timezone)
					.getTime());
			schedule.setServiceDateTime2(CommonUtility.sdfToDate
					.format(endDateTimeCombined));
			schedule.setScheduleType(PublicAPIConstant.DATETYPE_RANGE);
			routedProvider.setRescheduleDates(schedule);
		}
	}

	/**
	 * This method is for getting the phoneType from ClassId.
	 *
	 * @param classId
	 *            String
	 * @return String
	 */
	private String getPhoneType(String classId) {
		if (OrderConstants.PHONE_CLASS_HOME.equalsIgnoreCase(classId)) {// Home
			return PublicAPIConstant.HOME;
		} else if (OrderConstants.PHONE_CLASS_MOBILE.equalsIgnoreCase(classId)) {//Mobile
			return PublicAPIConstant.MOBILE;
		} else if (OrderConstants.PHONE_CLASS_WORK.equalsIgnoreCase(classId)) {//Work
			return PublicAPIConstant.WORK;
		} else if (OrderConstants.PHONE_CLASS_PAGER.equalsIgnoreCase(classId)) {//Pager
			return PublicAPIConstant.PAGER;
		} else if (OrderConstants.PHONE_CLASS_OTHER.equalsIgnoreCase(classId)) {//Fax
			return PublicAPIConstant.OTHER;
		}
		return PublicAPIConstant.OTHER;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public IOnSiteVisitBO getTimeOnSiteVisitBO() {
		return timeOnSiteVisitBO;
	}

	public void setTimeOnSiteVisitBO(IOnSiteVisitBO timeOnSiteVisitBO) {
		this.timeOnSiteVisitBO = timeOnSiteVisitBO;
	}

	

	/**
	 * This method returns an object of SpendLimitHistoryResponse
	 *  
	 *  @param soSpendLimitHistoryList
	 *            List<SOSpendLimitHistory> 
	 *  @param  found
	 *               boolean      
	 * @param soCount 
	 * @return SpendLimitHistoryResponse
	 */
	public SpendLimitHistoryResponse mapResponseForSpendLimit(
			List<SOSpendLimitHistory> soSpendLimitHistoryList,  int soCount) {
		SpendLimitHistoryResponse response = new SpendLimitHistoryResponse();
		Results results = new Results();
		String appendServiceOrder=(soCount==1)? "serviceOrder":"serviceOrders";
		if (null != soSpendLimitHistoryList
				&& !soSpendLimitHistoryList.isEmpty() && soCount>0 ) {
			results = Results.getSuccess(ResultsCode.SO_SPENDLIMIT_FETCHED
					.getMessage()+" "+ soCount+" "+appendServiceOrder);
		} else {
			results = Results.getError(ResultsCode.SO_SPENDLIMIT_NOT_RECORDED
					.getMessage(), ResultsCode.FAILURE.getCode());
		}
		response.setSoSpendLimitHistoryList(soSpendLimitHistoryList);
		response.setResults(results);
		return response;
	}
	/**
	 * This method set values for  object soSpendLimitHistory
	 * from its VO class  ServiceOrderSpendLimitHistoryVO
	 *  
	 *  @param  spendLimitIncrease
	 *           HashMap<String, ServiceOrderSpendLimitHistoryVO>
	 *  @param  soSpendLimitHistory
	 *              SOSpendLimitHistory    
	 * @param responseForSpendLimit 
	 *              String
	 * @return void
	 */
	public void getResponseForSOSpendLimitHistory(
			HashMap<String, ServiceOrderSpendLimitHistoryVO> spendLimitIncrease,
			SOSpendLimitHistory soSpendLimitHistory,
			String responseForSpendLimit) {
		ServiceOrderSpendLimitHistoryVO historyVO = new ServiceOrderSpendLimitHistoryVO();
		String soId = soSpendLimitHistory.getSoId();
		List<SpendLimitHistory> spendLimitList = new ArrayList<SpendLimitHistory>();
		DecimalFormat priceFormatter = new DecimalFormat("#0.00");
		historyVO = spendLimitIncrease.get(soSpendLimitHistory.getSoId());
		List<ServiceOrderSpendLimitHistoryListVO> historyVOList = historyVO
				.getHistoryVOlist();
		Results results = new Results();
		if (responseForSpendLimit
				.equalsIgnoreCase(PublicAPIConstant.retrieveSOForSpendLimit.THIN_RESPONSE)) {

			ServiceOrderSpendLimitHistoryListVO thinHistoryVO = (ServiceOrderSpendLimitHistoryListVO) historyVOList
					.get(0);
			SpendLimitHistory soHistory = new SpendLimitHistory();
			soHistory.setDate(thinHistoryVO.getModifiedDate());
			String updatedPriceAfterFormatting = priceFormatter.format(thinHistoryVO
					.getUpdatedPrice());
			String oldPriceAfterFormatting=priceFormatter.format(thinHistoryVO
					.getOldPrice());
			soHistory.setNewPrice(updatedPriceAfterFormatting);
			soHistory.setOldPrice(oldPriceAfterFormatting);
			soHistory.setReason(thinHistoryVO.getReason());
			StringBuilder user = new StringBuilder();
			user.append(
					null != thinHistoryVO.getUserName() ? thinHistoryVO.getUserName()
							: "").append("(UserId#").append(
					null != thinHistoryVO.getUserId() ? thinHistoryVO.getUserId() : "")
					                                .append(")");
			soHistory.setUser(user.toString());
			spendLimitList.add(soHistory);
			results = Results.getSuccess(ResultsCode.SO_SPENDLIMIT_FETCHED
					.getMessage()
					+ " " + soId);
			String priceAfterFormatting ="";
			soSpendLimitHistory.setResults(results);
			if (null != historyVO.getSpendLimitLabor()
					&& null != historyVO.getSpendLimitParts()) {
				Double originalPrice = historyVO.getSpendLimitLabor()
						+ historyVO.getSpendLimitParts();
				priceAfterFormatting = priceFormatter.format(originalPrice);
			}
			soSpendLimitHistory.setCurrentPrice(priceAfterFormatting);
			soSpendLimitHistory
					.setSpendLimitIncreaseHistoryList(spendLimitList);

		} else {

			results = new Results();
			for (ServiceOrderSpendLimitHistoryListVO solist : historyVOList) {
				
				SpendLimitHistory soHistory = new SpendLimitHistory();
				soHistory.setDate(solist.getModifiedDate());
				soHistory.setReason(solist.getReason());
				String priceAfterFormatting = priceFormatter.format(solist
						.getUpdatedPrice());
				String oldPriceAfterFormatting=priceFormatter.format(solist
						.getOldPrice());
				soHistory.setNewPrice(priceAfterFormatting);
				soHistory.setOldPrice(oldPriceAfterFormatting);
				StringBuilder user = new StringBuilder();
				user.append(
						null != solist.getUserName() ? solist.getUserName()
								: "").append("(UserId#").append(
						null != solist.getUserId() ? solist.getUserId() : "")
						.append(")");
				soHistory.setUser(user.toString());
				spendLimitList.add(soHistory);
				results = Results.getSuccess(ResultsCode.SO_SPENDLIMIT_FETCHED
						.getMessage()
						+ " " + soId);
			}
			soSpendLimitHistory.setResults(results);
			String priceAfterFormatting ="";
			if (null != historyVO.getSpendLimitLabor()
					&& null != historyVO.getSpendLimitParts()) {
				Double originalPrice = historyVO.getSpendLimitLabor()
						+ historyVO.getSpendLimitParts();
				priceAfterFormatting = priceFormatter.format(originalPrice);
			}
			
			soSpendLimitHistory.setCurrentPrice(priceAfterFormatting);
			soSpendLimitHistory
					.setSpendLimitIncreaseHistoryList(spendLimitList);
		}

	}

	
}

