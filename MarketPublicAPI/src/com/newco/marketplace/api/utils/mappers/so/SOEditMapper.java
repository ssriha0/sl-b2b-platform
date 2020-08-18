/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 22-Oct-2009	KMSTRSUP   Infosys				1.0
 *
 *
 */
package com.newco.marketplace.api.utils.mappers.so;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Phone;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.Task;
import com.newco.marketplace.api.beans.so.Tasks;
import com.newco.marketplace.api.beans.so.create.ServiceOrderBean;
import com.newco.marketplace.api.beans.so.edit.SOEditResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.provider.SkillAssignBOImpl;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.jobcode.IJobCodeMappingBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateContactDTO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskMappingVO;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.SODocument;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.provider.ServiceTypesVO;

public class SOEditMapper {
	private Logger logger = Logger.getLogger(SOEditMapper.class);
	private SkillAssignBOImpl skillAssignBOImpl;
	private ILookupBO lookupBO;
	private ArrayList<LookupVO> lookUpVOList = null;
	private ArrayList<ServiceTypesVO> serviceTypes = null;
	private ArrayList<LookupVO> shippingCarrierList = null;
	private SimpleDateFormat sdfToTime = new SimpleDateFormat("HH:mm:ss");
	private IBuyerSOTemplateBO buyerSOTemplateBO;
	private IJobCodeMappingBO jobCodeMappingBO;
	private IBuyerBO buyerBO;
	private IDocumentBO documentBO;
	private IServiceOrderBO serviceOrderBO;

	/**
	 * This method is for mapping request object to ServiceOrder object .
	 *
	 * @param request
	 *            ServiceOrderBean
	 * @param securityContext
	 *            SecurityContext
	 * @param serviceOrder
	 *            ServiceOrder
	 * @throws DataException
	 * @return serviceOrder
	 */
	public HashMap<String, Object> mapServiceOrder(ServiceOrder serviceOrder,
			ServiceOrderBean request, SecurityContext securityContext)
			throws DataException {
		logger.info("Entering SOEditMapper.mapServiceOrder()");
		HashMap<String, Object> soTitlesMap = new HashMap<String, Object>();
		Contact pickUpContact = null;
		// buyer setup
		if (securityContext.getCompanyId() == null) {
			logger.error("The company ID is null in the Security Context");
			throw new NullPointerException(CommonUtility
					.getMessage(PublicAPIConstant.COMPANY_ERROR_CODE));
		}
		// For mapping General Section
		if (request.getGeneralSection() != null) {
			mapGeneralSection(serviceOrder, request.getGeneralSection());
		}
		// For mapping Scope Of Work
		if (request.getScopeOfWork() != null) {
			mapScopeOfWork(serviceOrder, request.getScopeOfWork(),
					securityContext.getCompanyId(), soTitlesMap);
		}
		if (!soTitlesMap.isEmpty()) {
			return soTitlesMap;
		}
		// For mapping ServiceLocation
		if (request.getServiceLocation() != null) {
			String soZip = serviceOrder.getServiceLocation() != null ? serviceOrder
					.getServiceLocation().getZip()
					: null;
			String zip = request.getServiceLocation().getZip() != null ? request
					.getServiceLocation().getZip()
					: soZip;
			String soState = serviceOrder.getServiceLocation() != null ? serviceOrder
					.getServiceLocation().getState()
					: null;
			String state = request.getServiceLocation().getState() != null ? request
					.getServiceLocation().getState()
					: soState;
			validateZipAndState(PublicAPIConstant.SERVICE, zip, state,
					soTitlesMap);
			if (!soTitlesMap.isEmpty()) {
				return soTitlesMap;
			}
			mapServiceLocation(serviceOrder, request.getServiceLocation());
		}
		// For mapping Schedule Details
		if (request.getSchedule() != null) {
			mapServiceOrderSchedule(serviceOrder, request.getSchedule(),
					soTitlesMap);
		}
		if (!soTitlesMap.isEmpty()) {
			return soTitlesMap;
		}
		// For mapping Pricing Details

		if (request.getPricing() != null) {
			mapServiceOrderPricing(serviceOrder, request.getPricing());
		}
		// For mapping Contact Details

		if (request.getContacts() != null) {
			pickUpContact = mapServiceOrderContacts(serviceOrder, request
					.getContacts(), soTitlesMap);
		}else{
			mapServiceContactPhone(serviceOrder);
		}
		if (!soTitlesMap.isEmpty()) {
			return soTitlesMap;
		}

		// For mapping Attachment Details
		if (request.getAttachments() != null) {
			mapServiceOrderDocuments(serviceOrder, request.getAttachments());
		}
		// For mapping Parts Details
		if (request.getParts() != null) {
			mapServiceOrderParts(serviceOrder, request.getParts(),
					pickUpContact, soTitlesMap);
		}
		if (!soTitlesMap.isEmpty()) {
			return soTitlesMap;
		}

		// For mapping Custom References
		if (request.getCustomReferences() != null) {
			mapServiceOrderCustomRef(serviceOrder, request
					.getCustomReferences(), securityContext.getCompanyId());
		}

		// setting the role id
		serviceOrder.getBuyer().setRoleId(securityContext.getRoleId());
		
		// For mapping the template values from the database
		// the below mapping should be the last mapping method as it retuns
		// serviceorder object to hashmap
		if (null != request.getTemplate()) {
			soTitlesMap = mapTemplateName(securityContext.getCompanyId(),
					request.getTemplate(), serviceOrder);
		} else {
			soTitlesMap.put(PublicAPIConstant.SERVICE_ORDER, serviceOrder);
		}
		logger.info("Entering SOEditMapper.mapServiceOrder()");
		return soTitlesMap;
	}

	public HashMap<String, Object> mapServiceOrderForParts(
			ServiceOrder serviceOrder, ServiceOrderBean request,
			SecurityContext securityContext) throws DataException {
		HashMap<String, Object> soTitlesMap = new HashMap<String, Object>();
		if (request.getParts() != null) {
			// For mapping Parts Details

			mapServiceOrderPartsForEdit(serviceOrder, request.getParts(),
					soTitlesMap);
			if (!soTitlesMap.isEmpty()) {
				return soTitlesMap;
			}
			soTitlesMap.put(PublicAPIConstant.SERVICE_ORDER, serviceOrder);
		}
		return soTitlesMap;

	}

	/**
	 *
	 *
	 * @param companyId
	 *            Integer
	 * @param template
	 *            String
	 * @return soTitlesMap HashMap<String, Object>
	 */
	private HashMap<String, Object> mapTemplateName(Integer companyId,
			String templateName, ServiceOrder serviceOrder) {

		HashMap<String, Object> soTitlesMap = new HashMap<String, Object>();
		List<String> fileTitles = null;
		BuyerSOTemplateDTO templateDto = buyerSOTemplateBO.loadBuyerSOTemplate(
				companyId, templateName);
		if (null != templateDto) {

			if (null != templateDto.getTerms()) {
				serviceOrder.setBuyerTermsCond(templateDto.getTerms());
			}

			if (null != templateDto.getOverview()) {
				serviceOrder.setSowDs(templateDto.getOverview() + "\n"
						+ serviceOrder.getSowDs());
			}

			if (null != templateDto.getTitle()) {
				serviceOrder.setSowTitle(templateDto.getTitle()
						+ "-"
						+ (serviceOrder.getSowTitle() != null ? serviceOrder
								.getSowTitle() : ""));
			}

			if (null != templateDto.getPartsSuppliedBy()) {
				serviceOrder.setPartsSupplier(templateDto.getPartsSuppliedBy());
			}

			if (null != templateDto.getSpecialInstructions()) {
				serviceOrder.setProviderInstructions(serviceOrder
						.getProviderInstructions()
						+ templateDto.getSpecialInstructions());
			}

			if (null != templateDto.getConfirmServiceTime()) {
				serviceOrder.setProviderServiceConfirmInd(templateDto
						.getConfirmServiceTime());
			}

			if (null != templateDto.getDocumentLogo()) {
				mapDocumentLogotoSO(serviceOrder, templateDto);
			}

			BuyerSOTemplateContactDTO contactDto = templateDto
					.getAltBuyerContact();
			Contact contact = new Contact();
			contact.setBusinessName(contactDto.getCompanyName());
			contact.setContactTypeId(Integer.valueOf(1));
			contact.setEmail(contactDto.getEmail());
			contact.setFirstName(contactDto.getIndividualName());
			serviceOrder.setServiceContactAlt(contact);
			fileTitles = templateDto.getDocumentTitles();
			soTitlesMap.put(PublicAPIConstant.FILE_TITLE, fileTitles);
		}
		soTitlesMap.put(PublicAPIConstant.SERVICE_ORDER, serviceOrder);
		return soTitlesMap;
	}

	/**
	 * @param serviceorder
	 * @param templateDto
	 * @return It gets the corresponding document id
	 */
	private ServiceOrder mapDocumentLogotoSO(ServiceOrder serviceorder,
			BuyerSOTemplateDTO templateDto) {

		DocumentVO documentVO = null;
		try {
			documentVO = documentBO.retrieveDocumentByTitleAndEntityID(
					Constants.DocumentTypes.BUYER, templateDto
							.getDocumentLogo(), serviceorder.getBuyer()
							.getBuyerId());
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error(
					"MarketPublicAPI logomapping() while retrieving documents",
					e);
		}
		if (documentVO != null && documentVO.getDocumentId() != null) {
			serviceorder.setLogoDocumentId(documentVO.getDocumentId());
		}
		return serviceorder;
	}

	/**
	 * This method is for mapping the General Section of Edit Request to Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param generalSection
	 *            GeneralSection
	 * @return void
	 */
	private void mapGeneralSection(ServiceOrder serviceOrder,
			GeneralSection generalSection) {

		logger.info("Mapping: General Section --->Starts");
		if (generalSection.getTitle() != null) {
			serviceOrder.setSowTitle(generalSection.getTitle());
		}
		if (generalSection.getOverview() != null) {
			serviceOrder.setSowDs(generalSection.getOverview());
		}
		if (generalSection.getBuyerTerms() != null) {
			serviceOrder.setBuyerTermsCond(generalSection.getBuyerTerms());
		}
		if (generalSection.getSpecialInstructions() != null) {
			serviceOrder.setProviderInstructions(generalSection
					.getSpecialInstructions());
		}

	}

	/**
	 * This method is for mapping the Scope Of Work Section of Edit Request to
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param scopeOfWork
	 *            ScopeOfWork
	 * @param buyerId
	 *            Integer
	 * @param soTitlesMap
	 *            HashMap<String, Object>
	 * @return void
	 * @throws BusinessServiceException
	 */
	private void mapScopeOfWork(ServiceOrder serviceOrder,
			ScopeOfWork scopeOfWork, Integer buyerId,
			HashMap<String, Object> soTitlesMap) {

		logger.info("Mapping: Scope Of Work --->Starts");

		if (scopeOfWork.getMainCategoryID() != null) {
			serviceOrder.setPrimarySkillCatId(Integer.parseInt(scopeOfWork
					.getMainCategoryID()));
		}
		List<ServiceOrderTask> tasks = new ArrayList<ServiceOrderTask>();
		if (null != scopeOfWork.getSkus()
				&& null != scopeOfWork.getSkus().getSkuList()) {
			logger.info("Mapping SKU details ---->starts");
			Iterator<String> skuList = scopeOfWork.getSkus().getSkuList()
					.iterator();
			List<BuyerSkuTaskMappingVO> taskList = null;
			Double bidPrice = new Double(0);
			List<String> invalidSkus = new ArrayList<String>();
			while (skuList.hasNext()) {
				String sku = skuList.next();
				try {
					taskList = jobCodeMappingBO.getTaskDetailsBySkuAndBuyerId(
							sku, buyerId);
				} catch (BusinessServiceException e) {
					logger
							.error("Exception Occcurred while getting Task details using sku Number and Buyer Id");
				}
				if (null != taskList && !taskList.isEmpty()) {
					Iterator<BuyerSkuTaskMappingVO> skuTaskMappinglist = taskList
							.iterator();
					while (skuTaskMappinglist.hasNext()) {
						BuyerSkuTaskMappingVO skuTaskMap = skuTaskMappinglist
								.next();
						ServiceOrderTask serviceOrderTask = new ServiceOrderTask();
						serviceOrderTask.setTaskName(skuTaskMap.getTaskName());
						serviceOrderTask.setSkillNodeId(skuTaskMap
								.getCategoryId());
						serviceOrderTask.setServiceTypeId(skuTaskMap
								.getSkillId());
						serviceOrderTask.setTaskComments(skuTaskMap
								.getTaskComments());
						bidPrice = bidPrice + skuTaskMap.getBidPrice();
						tasks.add(serviceOrderTask);
					}

				} else {
					if (!invalidSkus.contains(sku)) {
						invalidSkus.add(sku);
					}

				}

			}
			if (!invalidSkus.isEmpty()) {
				soTitlesMap
						.put(
								PublicAPIConstant.ERROR_KEY,
								CommonUtility
										.getMessage(PublicAPIConstant.SKU_INVALID_OR_NO_TASK_MAPPING)
										+ invalidSkus.toString());
			}
			// Setting the total bidprice of sku's as Labor SpendLimit.
			// If the labor Spend limit is present in request, that value will
			// be
			// overriding bidprice whilemapping Prcing details.
			serviceOrder.setSpendLimitLabor(bidPrice);
			serviceOrder.setTasks(tasks);
		} else if (scopeOfWork.getTasks() != null) {
			Iterator<ServiceOrderTask> soTasksList=null;
			List<ServiceOrderTask> orderTasks = serviceOrder.getTasks();

			Map<Integer, ServiceOrderTask> taskMap = new HashMap<Integer, ServiceOrderTask>();
			if(orderTasks!=null){
			soTasksList = orderTasks.iterator();
			}
			while (soTasksList.hasNext()) {
				ServiceOrderTask task = soTasksList.next();
				taskMap.put(task.getTaskId(), task);

			}

			logger.info("Mapping Task details  starts----");
			Tasks reqTasks = scopeOfWork.getTasks();
			if (null != reqTasks && null != reqTasks.getTaskList()) {
				Iterator<Task> taskList = reqTasks.getTaskList().iterator();
				while (taskList.hasNext()) {
					Task task = (Task) taskList.next();
					boolean edit = false;
					ServiceOrderTask serviceOrderTask = new ServiceOrderTask();
					if (task.getTaskId() != 0) {
						serviceOrderTask = taskMap.get(task.getTaskId());
						if (serviceOrderTask == null) {
							soTitlesMap
									.put(
											PublicAPIConstant.ERROR_KEY,
											CommonUtility
													.getMessage(PublicAPIConstant.TASKID_WRONG_ERROR));
							return;
						}
						edit = true;

					}
					if (edit == false
							&& (task.getTaskName() == null
									|| task.getServiceType() == null || task
									.getCategoryID() == null)) {
						soTitlesMap
								.put(
										PublicAPIConstant.ERROR_KEY,
										CommonUtility
												.getMessage(PublicAPIConstant.TASK_DETAILS_INCOMPLETE));
						return;
					}
					if (edit == true) {
						serviceOrderTask.setTaskId(task.getTaskId());
					}
					if (task.getTaskName() != null) {
						serviceOrderTask.setTaskName(task.getTaskName());
					}
					if (null != task.getCategoryID()) {
						serviceOrderTask.setSkillNodeId(Integer.parseInt(task
								.getCategoryID()));
						if (null != task.getServiceType()) {
							serviceOrderTask.setServiceTypeId(getServiceTypeId(
									serviceOrder.getPrimarySkillCatId(), task
											.getServiceType()));
						}
					}
					if (task.getTaskComment() != null) {
						serviceOrderTask.setTaskComments(task.getTaskComment());
					}
					serviceOrderTask.setSoId(serviceOrder.getSoId());
					tasks.add(serviceOrderTask);
				}
				serviceOrder.setTasks(tasks);
			}
		}

	}

	/**
	 * This method is for mapping the ServiceLocation Section of Edit Request to
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param location
	 *            Location
	 * @return void
	 */
	private void mapServiceLocation(ServiceOrder serviceOrder, Location location) {
		logger.info("Mapping: Service Location--->Starts");

		SoLocation serviceLocation = serviceOrder.getServiceLocation();
		if(serviceLocation==null){
			serviceLocation=new SoLocation();
		}
		if (location.getLocationType() != null) {
			String locationType = location.getLocationType();

			if (PublicAPIConstant.LOCATION_TYPE_COMMERCIAL
					.equalsIgnoreCase(locationType)) {
				serviceLocation.setLocnClassId(1);
			} else if (PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL
					.equalsIgnoreCase(locationType)) {
				serviceLocation.setLocnClassId(2);
			}
		}
		if (location.getLocationName() != null) {
			serviceLocation.setLocnName(location.getLocationName());
		}
		if (location.getAddress1() != null) {
			serviceLocation.setStreet1(location.getAddress1());
		}
		if (location.getAddress2() != null) {
			serviceLocation.setStreet2(location.getAddress2());
		}
		if (location.getCity() != null) {
			serviceLocation.setCity(location.getCity());
		}
		if (location.getState() != null) {
			serviceLocation.setState(location.getState());
		}
		if (location.getZip() != null) {
			serviceLocation.setZip(location.getZip());
		}
		if (location.getNote() != null) {
			serviceLocation.setLocnNotes(location.getNote());
		}
		/*
		 * serviceLocation.setCreatedDate(new
		 * Timestamp(DateUtils.getCurrentDate() .getTime()));
		 */
		serviceOrder.setServiceLocation(serviceLocation);

	}

	/**
	 * This method is for mapping the Schedule Section of Edit Request to
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Schedule
	 *            schedule
	 * @return void
	 */
	private void mapServiceOrderSchedule(ServiceOrder serviceOrder,
			Schedule schedule, HashMap<String, Object> soTitlesMap) {
		logger.info("Mapping: ServiceOrder Schedule --->Starts");

		if (null != schedule.getServiceDateTime1()
				&& !schedule.getServiceDateTime1().equals("")) {
			serviceOrder.setServiceDate1(new Timestamp(DateUtils
					.defaultFormatStringToDate(schedule.getServiceDateTime1())
					.getTime()));
			// setting the servicestartTime
			Date serviceStartTime = null;
			try {
				serviceStartTime = CommonUtility.sdfToDate.parse(schedule
						.getServiceDateTime1());
			} catch (ParseException e) {
				logger.error("Exception Occurred while setting Service "
						+ "Start Time");
			}
			String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
			serviceOrder.setServiceTimeStart(serviceStartTimeStr);
		}
		if (schedule.getScheduleType() != null) {
			if (PublicAPIConstant.DATETYPE_FIXED.equalsIgnoreCase((schedule
					.getScheduleType()))) {
				serviceOrder.setServiceDate2(null);
				serviceOrder.setServiceTimeEnd(null);
				serviceOrder.setServiceDateTypeId(1);
			} else {
				serviceOrder.setServiceDateTypeId(2);
				if (null != schedule.getServiceDateTime2()
						&& !schedule.getServiceDateTime2().equals("")) {
					serviceOrder.setServiceDate2(new Timestamp(DateUtils
							.defaultFormatStringToDate(
									schedule.getServiceDateTime2()).getTime()));
					// setting the serviceEndTime
					Date serviceEndDate = null;
					try {
						serviceEndDate = CommonUtility.sdfToDate.parse(schedule
								.getServiceDateTime2());
					} catch (ParseException e) {
						logger.error("Parse Exception Occurred while "
								+ "setting ServiceEndDate");
					}
					String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
					serviceOrder.setServiceTimeEnd(serviceEndTimeStr);
				}
			}
		}
		validateSODates(serviceOrder, soTitlesMap);
		if (!soTitlesMap.isEmpty()) {
			return;
		}
		if (schedule.getConfirmWithCustomer() != null) {
			if (PublicAPIConstant.CONFIRM_CUSTOMER_TRUE
					.equalsIgnoreCase(schedule.getConfirmWithCustomer())) {
				serviceOrder.setProviderServiceConfirmInd(1);
			}else {
				serviceOrder.setProviderServiceConfirmInd(0);
			}
		}
	}

	/**
	 * This method is for validating Service Order dates
	 *
	 * @param serviceOrder
	 *            ServiceOrder HashMap<String, Object> soTitlesMap
	 * @return void
	 */
	private void validateSODates(ServiceOrder serviceOrder,
			HashMap<String, Object> soTitlesMap) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		Date now = cal.getTime();

		Timestamp newStartTimeCombined = new Timestamp(TimeUtils
				.combineDateAndTime(serviceOrder.getServiceDate1(),
						serviceOrder.getServiceTimeStart(),
						serviceOrder.getServiceLocationTimeZone()).getTime());
		if (null != serviceOrder.getServiceDate2()) {
			Timestamp newEndTimeCombined = new Timestamp(TimeUtils
					.combineDateAndTime(serviceOrder.getServiceDate2(),
							serviceOrder.getServiceTimeEnd(),
							serviceOrder.getServiceLocationTimeZone())
					.getTime());

			if (serviceOrder.getServiceDateTypeId() == 2) {
				if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {

					soTitlesMap.put(PublicAPIConstant.ERROR_KEY, CommonUtility
							.getMessage(PublicAPIConstant.STARTDATE_ENDDATE));
					return;

				}
			}
		}
		if (newStartTimeCombined.compareTo(now) < 0) {
			soTitlesMap.put(PublicAPIConstant.ERROR_KEY, CommonUtility
					.getMessage(PublicAPIConstant.STARTDATE_IMPROPER));
			return;
		}

	}

	/**
	 * This method is for mapping the Pricing Section of Edit Request to Service
	 * Order.
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
		if (serviceOrder.getWfStateId() == OrderConstants.DRAFT_STATUS){
			if (null != pricing.getPriceModel()) {
				serviceOrder.setPriceModel(pricing.getPriceModel());
			}
		}
		if (null != pricing.getLaborSpendLimit()
				&& (!"".equals(pricing.getLaborSpendLimit()))
				&& (!"0".equals(pricing.getLaborSpendLimit()))
				&& (!"0.0".equals(pricing.getLaborSpendLimit()))) {
			Double tempLabourSpendLimit = Double.parseDouble(pricing
					.getLaborSpendLimit());
			if (tempLabourSpendLimit != null) {
				serviceOrder.setSpendLimitLabor(tempLabourSpendLimit);
			}
		}
		if (null != pricing.getPartsSpendLimit()
				&& (!"".equals(pricing.getPartsSpendLimit()))
				&& (!"0".equals(pricing.getPartsSpendLimit()))
				&& (!"0.0".equals(pricing.getPartsSpendLimit()))) {
			Double tempPartsSpendLimit = Double.parseDouble(pricing
					.getPartsSpendLimit());
			if (tempPartsSpendLimit != null) {
				serviceOrder.setSpendLimitParts(tempPartsSpendLimit);
			}
		}
	}

	/**
	 * This method is for mapping Contacts Section of Edit Request to Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Contacts
	 *            contacts
	 * @return void
	 */
	private Contact mapServiceOrderContacts(ServiceOrder serviceOrder,
			Contacts contacts, HashMap<String, Object> soTitlesMap) {

		logger.info("Mapping: Contact Details--->Starts");
		Contact pickUpContact = null;
		if (null != contacts.getBuyerSupportResID()
				&& (!"0".equals(contacts.getBuyerSupportResID()))) {
			try {
				Integer buyerResId = new Integer(contacts
						.getBuyerSupportResID());
				int buyerId = 0;
				if (serviceOrder.getBuyer() != null) {
					buyerId = serviceOrder.getBuyer().getBuyerId();
				}

				ContactLocationVO contactLocationVO = new ContactLocationVO();
				contactLocationVO = buyerBO.getBuyerResContactLocationVO(
						buyerId, buyerResId);
				Integer buyerContactId = 0;
				if (contactLocationVO != null
						&& contactLocationVO.getBuyerPrimaryContact() != null) {
					buyerContactId = contactLocationVO.getBuyerPrimaryContact()
							.getContactId();
					logger.info("buyerContactId------>" + buyerContactId);
					serviceOrder.setBuyerContactId(buyerContactId);
					if (buyerContactId == 0 || null == buyerContactId) {
						soTitlesMap
								.put(
										PublicAPIConstant.ERROR_KEY,
										CommonUtility
												.getMessage(PublicAPIConstant.BUYER_RESOURCEID_NOT_VALID));
					}
				} else {
					soTitlesMap
							.put(
									PublicAPIConstant.ERROR_KEY,
									CommonUtility
											.getMessage(PublicAPIConstant.BUYER_RESOURCEID_NOT_VALID));
				}
			} catch (Exception e) {
				logger.error("Exception Occurred while setting buyerContactid");
			}
		}
		if ((null != contacts && null != contacts.getContactList())
				&& soTitlesMap.isEmpty()) {
			Iterator<com.newco.marketplace.api.beans.so.Contact> contactList = contacts
					.getContactList().iterator();
			boolean isService=false;
			while (contactList.hasNext()) {
				com.newco.marketplace.api.beans.so.Contact contactRequst = (com.newco.marketplace.api.beans.so.Contact) contactList
						.next();

				if (contactRequst.getContactLocnType().equalsIgnoreCase(
						PublicAPIConstant.SERVICE)) {
					serviceOrder
							.setServiceContact(mapContactDetails(contactRequst));
					isService=true;
				} else if (contactRequst.getContactLocnType().equalsIgnoreCase(
						PublicAPIConstant.END_USER)) {
					serviceOrder
							.setEndUserContact(mapContactDetails(contactRequst));
				} else if (contactRequst.getContactLocnType().equalsIgnoreCase(
						PublicAPIConstant.PICKUP)) {
					pickUpContact = mapContactDetails(contactRequst);
				}
			}
			if(!isService){
				mapServiceContactPhone(serviceOrder);
			}
		}
		return pickUpContact;

	}

	/**
	 * This method is for mapping the Attachments Section of Edit Request to
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Contacts
	 *            contacts
	 * @return void
	 */
	private void mapServiceOrderDocuments(ServiceOrder serviceOrder,
			Attachments attachments) {

		logger.info("Mapping: Document --->Starts");
		List<SODocument> documentsVo = new ArrayList<SODocument>();
		if (null != attachments && null != attachments.getFilenameList()) {
			for (String fileName : attachments.getFilenameList()) {
				SODocument documentVo = new SODocument();
				documentVo.setFileName(fileName);
				documentsVo.add(documentVo);
			}
			serviceOrder.setSoDocuments(documentsVo);
		}
	}

	/**
	 * This method is for mapping the Parts Section of Edit Request to Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Parts
	 *            parts
	 * @param Contact
	 *            pickUpContact
	 * @param HashMap
	 *            <String, Object> soTitlesMap
	 * @return void
	 */
	private HashMap<String, Object> mapServiceOrderParts(
			ServiceOrder serviceOrder, Parts parts, Contact pickUpContact,
			HashMap<String, Object> soTitlesMap) {

		logger.info("Mapping: Parts --->Starts");
		List<Part> partVoList = new ArrayList<Part>();
		List<Part> orderParts = serviceOrder.getParts();
		Map<Integer, Part> partMap = new HashMap<Integer, Part>();
		Iterator<Part> soPartsList = orderParts.iterator();
		int statusId = serviceOrder.getWfStateId();

		while (soPartsList.hasNext()) {
			Part part = soPartsList.next();
			partMap.put(part.getPartId(), part);

		}

		Part partVo = new Part();
		if (null != parts && null != parts.getPartList()) {
			Iterator<com.newco.marketplace.api.beans.so.Part> partsList = parts
					.getPartList().iterator();
			while (partsList.hasNext()) {
				com.newco.marketplace.api.beans.so.Part reqPart = (com.newco.marketplace.api.beans.so.Part) partsList
						.next();
				boolean edit = false;
				partVo = new Part();
				if (reqPart.getPartId() != 0) {
					partVo = partMap.get(reqPart.getPartId());
					if (partVo == null) {
						soTitlesMap
								.put(
										PublicAPIConstant.ERROR_KEY,
										CommonUtility
												.getMessage(PublicAPIConstant.PARTID_WRONG_ERROR));
						return soTitlesMap;
					}
					edit = true;

				}
				if (edit == false
						&& (reqPart.getManufacturer() == null
								|| reqPart.getModel() == null
								|| reqPart.getDescription() == null || reqPart
								.getQuantity() == null)) {
					soTitlesMap
							.put(
									PublicAPIConstant.ERROR_KEY,
									CommonUtility
											.getMessage(PublicAPIConstant.PART_DETAILS_INCOMPLETE));
					return soTitlesMap;
				}
				if (edit == true) {
					partVo.setPartId(reqPart.getPartId());
				}
				if (reqPart.getManufacturer() != null) {
					partVo.setManufacturer(reqPart.getManufacturer());
				}
				if (reqPart.getModel() != null) {
					partVo.setModelNumber(reqPart.getModel());
				}
				if (reqPart.getDescription() != null) {
					partVo.setPartDs(reqPart.getDescription());
				}
				if (reqPart.getQuantity() != null) {
					partVo.setQuantity(reqPart.getQuantity());
				}
				// Dimension
				if (null != reqPart.getDimensions()) {
					if (edit == false
							|| (edit == true && reqPart.getDimensions()
									.getHeight() != PublicAPIConstant.ZERO)) {
						partVo.setHeight(reqPart.getDimensions().getHeight());
					}
					if (edit == false
							|| (edit == true && reqPart.getDimensions()
									.getLength() != PublicAPIConstant.ZERO)) {
						partVo.setLength(reqPart.getDimensions().getLength());
					}
					if (edit == false
							|| (edit == true && reqPart.getDimensions()
									.getWidth() != PublicAPIConstant.ZERO)) {
						partVo.setWidth(reqPart.getDimensions().getWidth());
					}
					if (edit == false
							|| (edit == true && reqPart.getDimensions()
									.getWeight() != PublicAPIConstant.ZERO)) {
						partVo.setWeight(reqPart.getDimensions().getWeight());
					}
					if (edit == false
							&& reqPart.getDimensions().getMeasurementType() == null) {
						soTitlesMap
								.put(
										PublicAPIConstant.ERROR_KEY,
										CommonUtility
												.getMessage(PublicAPIConstant.PART_DETAILS_INCOMPLETE));
						return soTitlesMap;
					}
					if (reqPart.getDimensions().getMeasurementType() != null) {
						if (PublicAPIConstant.STANDARD_ENGLISH
								.equalsIgnoreCase((reqPart.getDimensions()
										.getMeasurementType()))) {
							partVo.setMeasurementStandard(Integer
									.parseInt(OrderConstants.ENGLISH));
						} else if (PublicAPIConstant.STANDARD_METRIC
								.equalsIgnoreCase(reqPart.getDimensions()
										.getMeasurementType())) {
							partVo.setMeasurementStandard(Integer
									.parseInt(OrderConstants.METRIC));
						}
					}
				}

				Carrier shippingCarrier = new Carrier();
				Carrier returnCarrier = new Carrier();
				if (partVo.getShippingCarrier() != null) {
					shippingCarrier = partVo.getShippingCarrier();
				}
				if (partVo.getReturnCarrier() != null) {
					returnCarrier = partVo.getReturnCarrier();
				}
				if (reqPart.getShipCarrier() != null) {
					shippingCarrier.setCarrierName(reqPart.getShipCarrier());
				}
				if (reqPart.getShipTracking() != null) {
					shippingCarrier
							.setTrackingNumber(reqPart.getShipTracking());
				}
				if (reqPart.getShipCarrier() != null) {
					shippingCarrier.setCarrierId(getShippingCarrierId(reqPart
							.getShipCarrier()));
				}
				if (reqPart.getReturnCarrier() != null) {
					returnCarrier.setCarrierName(reqPart.getReturnCarrier());
				}
				if (reqPart.getReturnTracking() != null) {
					returnCarrier
							.setTrackingNumber(reqPart.getReturnTracking());
				}
				if (reqPart.getReturnCarrier() != null) {
					returnCarrier.setCarrierId(getShippingCarrierId(reqPart
							.getReturnCarrier()));
				}
				if (reqPart.getShippingDate() != null) {
					Date shipDate = null;
					try {
						DateFormat df=new SimpleDateFormat("yyyy-mm-dd");

						shipDate=df.parse(reqPart.getShippingDate());
					} catch (ParseException e) {
						logger
								.error("Exception Occurred while setting Service "
										+ "Shipping date");
					}
					Timestamp shipDateTime = new Timestamp(shipDate.getTime());
					partVo.setShipDate(shipDateTime);
				}
				partVo.setReturnCarrier(returnCarrier);
				partVo.setShippingCarrier(shippingCarrier);
				if (reqPart.getRequiresPickup() != null) {
					partVo.setProviderBringPartInd(Boolean.parseBoolean(reqPart
							.getRequiresPickup()));
				}
				if (partVo.isProviderBringPartInd()) {
					if (reqPart.getPickupLocation() != null) {
						Location pickupLocation = reqPart.getPickupLocation();
						validateZipAndState(PublicAPIConstant.PICKUP,
								pickupLocation.getZip(), pickupLocation
										.getState(), soTitlesMap);
						if (!soTitlesMap.isEmpty()) {
							return soTitlesMap;
						}

						SoLocation soPickupLocation = partVo
								.getPickupLocation();
						if (soPickupLocation == null) {
							soPickupLocation = new SoLocation();
						}
						if (pickupLocation.getLocationType() != null) {
							if (PublicAPIConstant.LOCATION_TYPE_COMMERCIAL
									.equalsIgnoreCase(pickupLocation
											.getLocationType())) {
								soPickupLocation.setLocnClassId(1);
							} else if (PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL
									.equalsIgnoreCase(pickupLocation
											.getLocationType())) {
								soPickupLocation.setLocnClassId(2);
							}
						}
						/*
						 * soPickupLocation.setCreatedDate(new
						 * Timestamp(DateUtils .getCurrentDate().getTime()));
						 */
						if (pickupLocation.getLocationName() != null) {
							soPickupLocation.setLocName(pickupLocation
									.getLocationName());
						}
						if (pickupLocation.getAddress1() != null) {
							soPickupLocation.setStreet1(pickupLocation
									.getAddress1());
						}
						if (pickupLocation.getAddress2() != null) {
							soPickupLocation.setStreet2(pickupLocation
									.getAddress2());
						}
						if (pickupLocation.getCity() != null) {
							soPickupLocation.setCity(pickupLocation.getCity());
						}
						if (pickupLocation.getState() != null) {
							soPickupLocation
									.setState(pickupLocation.getState());
						}
						if (pickupLocation.getZip() != null) {
							soPickupLocation.setZip(pickupLocation.getZip());
						}
						if (pickupLocation.getNote() != null) {
							soPickupLocation.setLocnNotes(pickupLocation
									.getNote());
						}
						partVo.setPickupLocation(soPickupLocation);

					}
					if (pickUpContact != null) {
						partVo.setPickupContact(pickUpContact);

					}
				}
				partVoList.add(partVo);
			}
			serviceOrder.setParts(partVoList);
		}
		return soTitlesMap;
	}

	/**
	 * This method is for mapping the Parts Section of Edit Request to Service
	 * Order which are Accepted,Active,Problem,Completed.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Parts
	 *            parts
	 * @param Contact
	 *            pickUpContact
	 * @param HashMap
	 *            <String, Object> soTitlesMap
	 * @return void
	 */
	private HashMap<String, Object> mapServiceOrderPartsForEdit(
			ServiceOrder serviceOrder, Parts parts,
			HashMap<String, Object> soTitlesMap) {
		List<Part> partVoList = new ArrayList<Part>();
		List<Part> orderParts = serviceOrder.getParts();
		Map<Integer, Part> partMap = new HashMap<Integer, Part>();
		Iterator<Part> soPartsList = orderParts.iterator();

		while (soPartsList.hasNext()) {
			Part part = soPartsList.next();
			partMap.put(part.getPartId(), part);

		}

		Part partVo = new Part();
		if (null != parts && null != parts.getPartList()) {
			Iterator<com.newco.marketplace.api.beans.so.Part> partsList = parts
					.getPartList().iterator();
			while (partsList.hasNext()) {
				com.newco.marketplace.api.beans.so.Part reqPart = (com.newco.marketplace.api.beans.so.Part) partsList
						.next();
				partVo = new Part();
				if (reqPart.getPartId() != 0) {
					partVo = partMap.get(reqPart.getPartId());
					if (partVo == null) {
						soTitlesMap
								.put(
										PublicAPIConstant.ERROR_KEY,
										CommonUtility
												.getMessage(PublicAPIConstant.PARTID_WRONG_ERROR));
						return soTitlesMap;
					}

				} else {
					soTitlesMap
							.put(
									PublicAPIConstant.ERROR_KEY,
									CommonUtility
											.getMessage(PublicAPIConstant.PARTID_REQUIRED_ERROR));
					return soTitlesMap;
				}
				Carrier shippingCarrier = new Carrier();
				Carrier returnCarrier = new Carrier();
				if (partVo.getShippingCarrier() != null) {
					shippingCarrier = partVo.getShippingCarrier();
				}
				if (partVo.getReturnCarrier() != null) {
					returnCarrier = partVo.getReturnCarrier();
				}

				if (reqPart.getShipCarrier() != null) {
					shippingCarrier.setCarrierName(reqPart.getShipCarrier());
				}
				if (reqPart.getShipTracking() != null) {
					shippingCarrier
							.setTrackingNumber(reqPart.getShipTracking());
				}
				if (reqPart.getShipCarrier() != null) {
					shippingCarrier.setCarrierId(getShippingCarrierId(reqPart
							.getShipCarrier()));
				}
				if (reqPart.getReturnCarrier() != null) {
					returnCarrier.setCarrierName(reqPart.getReturnCarrier());
				}
				if (reqPart.getReturnTracking() != null) {
					returnCarrier
							.setTrackingNumber(reqPart.getReturnTracking());
				}
				if (reqPart.getReturnCarrier() != null) {
					returnCarrier.setCarrierId(getShippingCarrierId(reqPart
							.getReturnCarrier()));
				}
				if (reqPart.getShippingDate() != null) {
					Date shipDate = null;
					try {
						DateFormat df=new SimpleDateFormat("yyyy-mm-dd");

						shipDate=df.parse(reqPart.getShippingDate());
					} catch (ParseException e) {
						logger
								.error("Exception Occurred while setting Service "
										+ "Shipping date");
					}
					Timestamp shipDateTime = new Timestamp(shipDate.getTime());
					partVo.setShipDate(shipDateTime);
				}
				partVo.setReturnCarrier(returnCarrier);
				partVo.setShippingCarrier(shippingCarrier);
				partVo.setSoId(serviceOrder.getSoId());
				partVoList.add(partVo);
			}
			serviceOrder.setParts(partVoList);
		}

		return soTitlesMap;
	}

	/**
	 * This method is for mapping the Custom Reference Section of Edit Request
	 * to Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Parts
	 *            parts
	 * @return void
	 */
	private void mapServiceOrderCustomRef(ServiceOrder serviceOrder,
			CustomReferences customReferences, Integer ownerId) {
		logger.info("Mapping: Customer Reference --->Starts");

		List<ServiceOrderCustomRefVO> soCustomRefsVoList = new ArrayList<ServiceOrderCustomRefVO>();
		if (null != customReferences
				&& null != customReferences.getCustomRefList()) {
			Iterator<CustomReference> customRefsVoList = customReferences
					.getCustomRefList().iterator();
			while (customRefsVoList.hasNext()) {
				CustomReference customRef = (CustomReference) customRefsVoList
						.next();
				ServiceOrderCustomRefVO customRefVO = new ServiceOrderCustomRefVO();
				customRefVO.setRefType(customRef.getName());
				customRefVO.setRefValue(customRef.getValue());
				customRefVO.setRefTypeId(getCustomRefTypeId(ownerId + "",
						customRef.getName()));
				if (null != customRefVO.getRefTypeId()) {
					soCustomRefsVoList.add(customRefVO);
				}
			}
			if (!soCustomRefsVoList.isEmpty()) {
				serviceOrder.setCustomRefs(soCustomRefsVoList);
			}
		}
	}

	/**
	 * This method is for getting ServiceType Id
	 *
	 * @param mainCategoryId
	 *            Integer
	 * @param serviceType
	 *            String
	 * @return Integer
	 */
	private Integer getServiceTypeId(Integer mainCategoryId, String serviceType) {
		logger.info("Inside getServiceTypeId--->Start");
		try {
			if (serviceTypes == null) {
				serviceTypes = skillAssignBOImpl
						.getServiceTypes(mainCategoryId);
			}
			for (com.newco.marketplace.vo.provider.ServiceTypesVO serviceTypesVO : serviceTypes) {
				if (serviceTypesVO.getDescription().equalsIgnoreCase(
						serviceType)) {
					return serviceTypesVO.getServiceTypeId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting "
					+ " ServiceTypeId " + e);
		}
		return null;
	}

	/**
	 * This method is for getting Phone TypeId.
	 *
	 * @param phoneType
	 *            String
	 * @return Integer
	 */
	private Integer getPhoneTypeId(String phoneType) {
		logger.info("Inside getPhoneTypeId--->Start");
		try {
			if (lookUpVOList == null) {
				lookUpVOList = lookupBO.getPhoneTypes();
			}
			for (LookupVO lookupVO : lookUpVOList) {
				if (phoneType.equalsIgnoreCase(lookupVO.getDescr())) {
					return lookupVO.getId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting  PhoneTypeId "
					+ e);
		}
		return null;
	}

	/**
	 * This method is for getting the CustomerrefTypeId.
	 *
	 * @param buyerId
	 *            String
	 * @param customRefType
	 *            String
	 * @return Integer
	 */
	private Integer getCustomRefTypeId(String buyerId, String customRefType) {
		logger.info("Inside getCustomRefTypeId--->Start");
		try {
			ArrayList<LuBuyerRefVO>	customRefList = lookupBO.getBuyerRef(buyerId);
			for (LuBuyerRefVO customRef : customRefList) {
				if (customRef.getRefType().equalsIgnoreCase(customRefType)) {
					return customRef.getRefTypeId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting "
					+ " CustomerRefId " + "Carrier Details" + e);
		}
		return null;
	}

	/**
	 * This method is for getting the Shipping Carrier Id.
	 *
	 * @param carrierType
	 *            String
	 * @return int
	 */
	private Integer getShippingCarrierId(String carrierType) {
		logger.info("Inside getShippingCarrierId--->Start");
		try {
			shippingCarrierList = lookupBO.getShippingCarrier();
			for (LookupVO shippingCarrier : shippingCarrierList) {
				if (shippingCarrier.getDescr().equalsIgnoreCase(carrierType)) {
					return shippingCarrier.getId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while Setting the Shipping "
					+ "Carrier Details" + e);
		}
		return null;
	}

	/**
	 * This method is for mapping Contact Details.
	 *
	 * @param requestContact
	 *            Contact
	 * @return contact
	 */
	private Contact mapContactDetails(
			com.newco.marketplace.api.beans.so.Contact requestContact) {
		logger.info("Inside mapContactDetails--->Start");
		Contact contact = new Contact();
		contact.setFirstName(requestContact.getFirstName());
		contact.setLastName(requestContact.getLastName());
		contact.setEmail(requestContact.getEmail());
		List<PhoneVO> phoneVOList = new ArrayList<PhoneVO>();
		if (requestContact.getPrimaryPhone() != null) {
			phoneVOList
					.add(mapPhoneDetails(requestContact.getPrimaryPhone(), 1));
		}
		if (requestContact.getAltPhone() != null) {
			phoneVOList.add(mapPhoneDetails(requestContact.getAltPhone(), 2));
		}
		contact.setPhones(phoneVOList);
		contact.setCreatedDate(new Timestamp(DateUtils.getCurrentDate()
				.getTime()));
		return contact;
	}

	/**
	 * This method is for mapping Phone Details.
	 *
	 * @param phone
	 *            Phone
	 * @param phoneTypeId
	 *            int
	 * @return phoneVO
	 */
	private PhoneVO mapPhoneDetails(Phone phone, int phoneTypeId) {
		logger.info("Inside mapPhoneDetails--->Start");
		PhoneVO phoneVO = new PhoneVO();
		phoneVO.setPhoneNo(StringUtils.isEmpty(phone.getNumber()) ? null
				: phone.getNumber().replace("-", ""));
		phoneVO.setPhoneExt(phone.getExtension());
		phoneVO.setClassId(getPhoneTypeId(phone.getPhoneType()));
		phoneVO.setPhoneType(phoneTypeId);
		return phoneVO;
	}

	/**
	 * This method is for SOEditResponse xml from ProcessResponse object.
	 *
	 * @param soId
	 *            String
	 * @return SOEditResponse
	 */
	public SOEditResponse editSOResponseMapping(String soId,
			List<String> invalidDocumentList) {
		logger.info("Inside editSOResponseMapping--->Start");
		SOEditResponse soEditResponse = new SOEditResponse();
		Results results = Results.getSuccess(ResultsCode.EDIT_SUCCESSFUL
				.getMessage());
		soEditResponse.setResults(results);
		if (!invalidDocumentList.isEmpty()) {
			logger.info("Mapping the Invalid Documents with error List");

			String errorMessage = CommonUtility
					.getMessage(PublicAPIConstant.DOCUMENT_ERROR_CODE);
			for (String fileName : invalidDocumentList) {
				errorMessage = errorMessage + fileName + "  ::  ";
			}

			Results errorResults = Results.getError(errorMessage,
					ResultsCode.EDIT_FAILURE.getCode());
			errorResults.setResult(results.getResult());
			soEditResponse.setResults(errorResults);
		}

		return soEditResponse;
	}

	/**
	 * This method is for validating the zipCode and State
	 *
	 * @param locationType
	 *            String
	 * @param zip
	 *            String
	 * @param state
	 *            String
	 * @param map
	 *            HashMap<String, Object>
	 */
	private void validateZipAndState(String locationType, String zip,
			String state, HashMap<String, Object> map) {

		int zipValidation = LocationUtils.checkIfZipAndStateValid(zip, state);
		switch (zipValidation) {
		case Constants.LocationConstants.ZIP_NOT_VALID:
			if (PublicAPIConstant.SERVICE.equals(locationType)) {
				map.put(PublicAPIConstant.ERROR_KEY, CommonUtility
						.getMessage(PublicAPIConstant.ZIPCODEINVALID_SERVICE));
			} else {
				map.put(PublicAPIConstant.ERROR_KEY, CommonUtility
						.getMessage(PublicAPIConstant.ZIPCODEINVALID_PICKUP));
			}
			break;

		case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
			if (PublicAPIConstant.SERVICE.equals(locationType)) {
				map.put(PublicAPIConstant.ERROR_KEY, CommonUtility
						.getMessage(PublicAPIConstant.STATEINVALID_SERVICE));
			} else {
				map.put(PublicAPIConstant.ERROR_KEY, CommonUtility
						.getMessage(PublicAPIConstant.STATEINVALID_PICKUP));
			}
			break;

		}
	}
	/**
	 * This method is for setting Phone number
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 *
	 */
	public void mapServiceContactPhone(
			ServiceOrder serviceOrder){
		List<PhoneVO> phoneVOList = new ArrayList<PhoneVO>();
		Phone phone=new Phone();
		PhoneVO phoneVO=new PhoneVO();
		if(serviceOrder.getServiceContact()!=null){
			phone.setExtension(serviceOrder.getServiceContact().getPhoneNoExt());
			phone.setNumber(serviceOrder.getServiceContact().getPhoneNo());
			if(null!=serviceOrder.getServiceContact().getPhoneTypeId()){
				phone.setPhoneType(serviceOrder.getServiceContact().getPhoneTypeId().toString());
			}
			phoneVO=mapPhoneDetails(phone, serviceOrder.getServiceContact().getPhoneTypeId());
			if(null!=serviceOrder.getServiceContact().getPhoneClassId()){
				phoneVO.setClassId(Integer.parseInt(serviceOrder.getServiceContact().getPhoneClassId()));
			}
			phoneVOList.add(phoneVO);

			if(null!=serviceOrder.getServiceContactAlt()){

				Phone altPhone=new Phone();
				PhoneVO altPhoneVO=new PhoneVO();
				altPhone.setExtension(serviceOrder.getServiceContactAlt().getPhoneNoExt());
				altPhone.setNumber(serviceOrder.getServiceContactAlt().getPhoneNo());
				if(null!=serviceOrder.getServiceContactAlt().getPhoneTypeId()){
					altPhone.setPhoneType(serviceOrder.getServiceContactAlt().getPhoneTypeId().toString());
				}
				altPhoneVO=mapPhoneDetails(altPhone, serviceOrder.getServiceContactAlt().getPhoneTypeId());
				if(null!=serviceOrder.getServiceContactAlt().getPhoneClassId()){
					altPhoneVO.setClassId(Integer.parseInt(serviceOrder.getServiceContactAlt().getPhoneClassId()));
				}
				phoneVOList.add(altPhoneVO);

			}
			Contact contact=new Contact();

			contact=serviceOrder.getServiceContact();
			contact.setPhones(phoneVOList);
			serviceOrder.setServiceContact(contact);
		}

	}
	public void setSkillAssignBOImpl(SkillAssignBOImpl skillAssignBOImpl) {
		this.skillAssignBOImpl = skillAssignBOImpl;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public void setBuyerSOTemplateBO(IBuyerSOTemplateBO buyerSOTemplateBO) {
		this.buyerSOTemplateBO = buyerSOTemplateBO;
	}

	public void setJobCodeMappingBO(IJobCodeMappingBO jobCodeMappingBO) {
		this.jobCodeMappingBO = jobCodeMappingBO;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SkillAssignBOImpl getSkillAssignBOImpl() {
		return skillAssignBOImpl;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public IBuyerSOTemplateBO getBuyerSOTemplateBO() {
		return buyerSOTemplateBO;
	}

	public IJobCodeMappingBO getJobCodeMappingBO() {
		return jobCodeMappingBO;
	}

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

}
