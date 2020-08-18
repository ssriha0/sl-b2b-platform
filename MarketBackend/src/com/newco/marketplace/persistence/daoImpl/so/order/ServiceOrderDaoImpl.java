package com.newco.marketplace.persistence.daoImpl.so.order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.api.mobile.beans.vo.WarrantyHomeReasonInfoVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.RoleVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.price.PendingCancelPriceVO;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.provider.FirmContact;
import com.newco.marketplace.dto.vo.provider.ProviderDetailWithSOAccepted;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLanguageVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerDetail;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.ClosedOrdersRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedServiceOrderVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CounterOfferReasonsVO;
import com.newco.marketplace.dto.vo.serviceorder.JobCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.MarketMakerProviderResponse;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PartDatasVO;
import com.newco.marketplace.dto.vo.serviceorder.PartDetail;
import com.newco.marketplace.dto.vo.serviceorder.PaymentDetailsVO;
import com.newco.marketplace.dto.vo.serviceorder.PendingCancelHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderContactVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.ReasonLookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ResponseSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProviderResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.SODocument;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderContact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderIvrDetailsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNoteDetail;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderRole;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryListVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTabResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTaskDetail;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWorkflowStateTransitionVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.serviceorder.SoPriceChangeHistory;
import com.newco.marketplace.dto.vo.serviceorder.WfStatesVO;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.dto.vo.so.order.SoCancelVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.vo.provider.LastClosedOrderVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.dto.vo.ValidationRulesVO;

/**
 * 
 * $Revision: 1.128 $ $Author: awadhwa $ $Date: 2008/06/10 23:26:29 $
 */

public class ServiceOrderDaoImpl extends ABaseImplDao implements ServiceOrderDao {

	private static final Logger logger = Logger
			.getLogger(ServiceOrderDaoImpl.class.getName());
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	private Object lock3 = new Object();

//	RandomGUID randomNo = RandomGUIDThreadSafe.getInstance();

	public ServiceOrder getServiceOrder(String serviceOrderID) throws DataServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(serviceOrderID);
		try {
			serviceOrder = (ServiceOrder) queryForObject("so.query", serviceOrder);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return serviceOrder;
	}
	
	public String isServiceOrderUnique(Integer buerId, String soUniqueId)
			throws DataServiceException {
		HashMap map = new HashMap();
		map.put("buyerId", buerId);
		map.put("soUniqueId", soUniqueId);
		List<String> soIds = null;
		try {
			soIds = (List<String>) queryForList("count.uniqueSo", map);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		if(null == soIds || soIds.size() <= 0){
			return "";
		}
		return soIds.get(0);
	}
	
	/**
	 * 
	 * method to retrieve document list for invoice parts
	 */
	public List<InvoiceDocumentVO> getInvoiceDocumentList(List <Integer> invoiceIds) throws DataServiceException{
		 List<InvoiceDocumentVO> invoiceDocuments = new ArrayList<InvoiceDocumentVO>();
		
		try {
			invoiceDocuments = (ArrayList<InvoiceDocumentVO>)queryForList("fetchInvoiceDocumentsList.query", invoiceIds);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.query - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return invoiceDocuments;	
	}
	
	
	
	public SoCancelVO getServiceOrderForCancel(String serviceOrderID) throws DataServiceException {
		SoCancelVO soCancelVO = new SoCancelVO();
		soCancelVO.setId(serviceOrderID);
		try {
			soCancelVO = (SoCancelVO) queryForObject("socancellation.query", soCancelVO);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return soCancelVO;
	}
	
	
	public Map getTaskPrice(Integer taskId) throws DataServiceException {
   		Map sotaskMap = null;
		try {
			sotaskMap = (HashMap)queryForObject("task.price",taskId);
		} 
		catch (Exception e) {
				throw new DataServiceException(
						"Exception occured in ServiceOrderDaoImpl.getTaskPrice",e);
		}
		return sotaskMap;
   	}
	
	public ServiceOrder getGroupedServiceOrders(String parentGroupId) throws DataServiceException{
			ServiceOrder serviceOrders = new ServiceOrder();
		
		try {
			serviceOrders = (ServiceOrder)queryForObject("so.group.query", parentGroupId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.query - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return serviceOrders;
	}
	
	public ServiceOrder getServiceOrdersForGroup(String parentGroupId,List<String> responseFilters) throws DataServiceException {
		
		ServiceOrder serviceOrders = new ServiceOrder();
		
		try {
			serviceOrders = (ServiceOrder)queryForObject("so.group.query", parentGroupId);
			
			if (responseFilters.contains(OrderConstants.SERVICELOCATION)) {
				SoLocation serviceLocation= new SoLocation();
				serviceLocation = (SoLocation) queryForObject("getServicelocation.query", serviceOrders.getSoId());
				serviceOrders.setServiceLocation(serviceLocation);
			}
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.query - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return serviceOrders;
	}
	
	public List<String> getServiceOrderIDsForGroup(String parentGroupId) throws DataServiceException {
		
		List<String> soIdList = new ArrayList<String>();
		
		try {
			soIdList = (ArrayList<String>)queryForList("so.group.childIds.query", parentGroupId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.query - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return soIdList;		
		
	}

	public ServiceOrder insertServiceOrderAsscoiates(ServiceOrder so)
			throws DataServiceException {
//		RoutedProvider routedProvider;

		if (null != so.getRoutedResources()
				&& so.getRoutedResources().size() > 0) {
			insertRoutedResources(so.getRoutedResources());
		}

		Contact altBuyerConatctInfo = null;
		if (so.getSelectedAltBuyerContact() != null
				&& so.getSelectedAltBuyerContact().getContactId() != null) {
			altBuyerConatctInfo = (Contact) queryForObject(
					"altBuyerContactInsert.query", so
							.getSelectedAltBuyerContact().getContactId());
			altBuyerConatctInfo.setSoId(so.getSoId());
			altBuyerConatctInfo.setContactTypeId(20);
			//Reset CreatedDate populated in above query, so the trigger would set a correct CreatedDate
			altBuyerConatctInfo.setCreatedDate(null);
			Integer contactId = (Integer) insert("socontact.insert", altBuyerConatctInfo);
			altBuyerConatctInfo.setContactId(contactId);
			altBuyerConatctInfo.setContactTypeId(30);
			insert("socontactlocn.insert", altBuyerConatctInfo);
		}

//		List<ServiceOrderCustomRefVO> customRef = new ArrayList<ServiceOrderCustomRefVO>();
		delete("sowcustomRef.delete", so);
		if (so.getCustomRefs() != null && so.getCustomRefs().size() > 0) {
			batchInsert("socustomRef.insert", so.getCustomRefs());
//			customRef = so.getCustomRefs();
//			ArrayList<ServiceOrderCustomRefVO> customRefBatchInsert = new ArrayList<ServiceOrderCustomRefVO>();
//			for (int i = 0; i < customRef.size(); i++) {
//				int count = update("sowcustomRef.update", customRef.get(i));
//				if (count == 0) {
//					if(logger.isDebugEnabled()) {
//						ServiceOrderCustomRefVO vo = new ServiceOrderCustomRefVO();
//						//vo.getRefTypeId()todo
//						logger.debug("sowcustomRef.insert:: so_id: " + so.getSoId() + ", customRef_id: " );
//					}
//					insert("sowcustomRef.insert", customRef.get(i));
//				}
//			}

		}
		Contact endUserContact = null;
		if (null != so.getEndUserContact()) {
//				&& null != so.getEndUserContact().getContactId()) {
			endUserContact = so.getEndUserContact();
			endUserContact.setSoId(so.getSoId());
			Integer contactId = (Integer) insert("socontact.insert", endUserContact);
			endUserContact.setContactId(contactId);
			if (null != endUserContact.getPhones()
					&& endUserContact.getPhones().size() > 0) {
				List<PhoneVO> phonesVo = endUserContact.getPhones();

				for (int pi = 0; pi < phonesVo.size(); pi++) {
					PhoneVO phoneVo = phonesVo.get(pi);
					phoneVo.setSoId(endUserContact.getSoId());
					phoneVo.setSoContactId(contactId);
//					try {
//						phoneVo.setSoContactPhoneId(randomNo.generateGUID()
//								.intValue());
//					} catch (Exception e) {
//						logger.info("Caught Exception and ignoring",e);
//					}
					if (phoneVo != null && phoneVo.getPhoneNo() != null
							&& !phoneVo.getPhoneNo().trim().equals("")) {
						insert("contactPhone.insert", phoneVo);
					}

				}
			}
		}
		Contact serviceContact = null;
		if (null != so.getServiceContact()) {
//				&& null != so.getServiceContact().getContactId()) {
			serviceContact = so.getServiceContact();
			serviceContact.setSoId(so.getSoId());
			Integer contactId = (Integer) insert("socontact.insert", serviceContact);
			serviceContact.setContactId(contactId);
			if (null != serviceContact.getPhones()
					&& serviceContact.getPhones().size() > 0) {
				List<PhoneVO> phonesVo = serviceContact.getPhones();
				for (int pi = 0; pi < phonesVo.size(); pi++) {
					PhoneVO phoneVo = phonesVo.get(pi);
					phoneVo.setSoContactId(contactId);
					if (phoneVo != null && phoneVo.getPhoneNo() != null
							&& !phoneVo.getPhoneNo().trim().equals(""))
						insert("contactPhone.insert", phoneVo);

				}
			}

		}
		Contact alternateServiceContact = null;
		if (null != so.getAltServiceContact()) {
			alternateServiceContact = so.getAltServiceContact();
			alternateServiceContact.setSoId(so.getSoId());
//			//int contactId = -1;
//			try {
//				//contactId = randomNo.generateGUID().intValue();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//alternateServiceContact.setContactId(contactId);
			alternateServiceContact.setBusinessName(so.getServiceContact()
					.getBusinessName());
			alternateServiceContact.setContactTypeId(20);
			Integer contactId = (Integer) insert("socontact.insert", alternateServiceContact);
			alternateServiceContact.setContactId(contactId);
			alternateServiceContact.setContactTypeId(10);
			insert("socontactlocn.insert", alternateServiceContact);

			// List<PhoneVO> phonesVo = alternateServiceContact.getPhones();
			if (null != alternateServiceContact.getPhones()
					&& alternateServiceContact.getPhones().size() > 0) {
				List<PhoneVO> phonesVo = alternateServiceContact.getPhones();
				for (int pi = 0; pi < phonesVo.size(); pi++) {
					PhoneVO phoneVo = phonesVo.get(pi);
					phoneVo.setSoId(alternateServiceContact.getSoId());
					phoneVo.setSoContactId(contactId);
					if (phoneVo.getPhoneType() == null)
						phoneVo.setPhoneType(2);
					if (phoneVo.getClassId() == null)
						phoneVo.setClassId(1);
//					try {
//						phoneVo.setSoContactPhoneId(randomNo.generateGUID()
//								.intValue());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
					insert("contactPhone.insert", phoneVo);
				}
			}
		}

		Contact buyerSupportContact = null;
		if (null != so.getBuyerSupportContact()) {
//				&& null != so.getBuyerSupportContact().getContactId()) {
			buyerSupportContact = so.getBuyerSupportContact();
			buyerSupportContact.setSoId(so.getSoId());
			Integer contactId = (Integer) insert("socontact.insert", buyerSupportContact);
			buyerSupportContact.setContactId(contactId);
		}
		SoLocation buyerSupportLocation = null;
		if (null != so.getBuyerSupportLocation()) {
//				&& null != so.getBuyerSupportLocation().getLocationId()) {
			buyerSupportLocation = so.getBuyerSupportLocation();
			buyerSupportLocation.setSoId(so.getSoId());
			Integer locationId = (Integer) insert("solocation.insert", buyerSupportLocation);
			buyerSupportLocation.setLocationId(locationId);
		}
		SoLocation serviceLocation = null;
		if (null != so.getServiceLocation()) {
//				&& null != so.getServiceLocation().getLocationId()) {
			serviceLocation = so.getServiceLocation();
			serviceLocation.setSoId(so.getSoId());
			Integer locationId = (Integer) insert("solocation.insert", serviceLocation);
			serviceLocation.setLocationId(locationId);

			if (Constants.PriceModel.BULLETIN.equals(so.getPriceModel())) {
				insert("solocationgis.insert", locationId);
			}
		}
		ServiceOrderNote soNote = null;
		if (null != so.getSoNotes() && so.getSoNotes().size() > 0) {
			List<ServiceOrderNote> soNotes = (List<ServiceOrderNote>) so
					.getSoNotes();
			Iterator<ServiceOrderNote> noteItr = soNotes.iterator();
			while (noteItr.hasNext()) {
				soNote = noteItr.next();
				soNote.setSoId(so.getSoId());
				insert("note.insert", soNote);
			}
		}
		ServiceOrderTask task;
		if (null != so.getTasks() && so.getTasks().size() > 0) {
			List<ServiceOrderTask> tasks = (List<ServiceOrderTask>) so
					.getTasks();
			Iterator<ServiceOrderTask> tasksItr = tasks.iterator();
			delete("sowtask.delete", so);
			int sortOrder = 0;
			while (tasksItr.hasNext()) {
				task = tasksItr.next();
				task.setSoId(so.getSoId());
				task.setSortOrder(new Integer(sortOrder++));
//				if (task.getTaskId() != null) {
					if (task.getSkillNodeId() != null
							&& task.getSkillNodeId() <= 0) {
						task.setSkillNodeId(null);
					}
					if (task.getServiceTypeId() != null
							&& task.getServiceTypeId() == -1) {
						task.setServiceTypeId(null);
					}

					int count = update("sowtask.update", task);
					if (count == 0) {
						insert("task.insert", task);
					}
//				}

			}
		} else {
			delete("sowtask.delete", so);
		}

		Contact pickupContact = null;
		SoLocation pickupLocation = null;

		if (null != so.getParts() && so.getParts().size() > 0) {
			List<Part> parts = so.getParts();
			Iterator<Part> partsItr = parts.iterator();

			Part part;
			delete("sowpart.delete", so);
			while (partsItr.hasNext()) {
				part = partsItr.next();
				part.setSoId(so.getSoId());
				pickupContact = part.getPickupContact();
				pickupLocation = part.getPickupLocation();
				if (pickupContact != null) {
					pickupContact.setSoId(so.getSoId());
//					if (pickupContact.getContactId() != null) {
						Integer contactId = (Integer) insert("socontact.insert", pickupContact);
						if (null != pickupContact.getPhones() && pickupContact.getPhones().size() > 0) {
							List<PhoneVO> phonesVo = pickupContact.getPhones();
							for (int pi = 0; pi < phonesVo.size(); pi++) {
								PhoneVO phoneVo = phonesVo.get(pi);
								phoneVo.setSoContactId(contactId);
								if (phoneVo != null && StringUtils.isNotBlank(phoneVo.getPhoneNo())) {
									insert("contactPhone.insert", phoneVo);
								}
							}
						}
						pickupContact.setContactId(contactId);
//					}
				}
				if (pickupLocation != null) {
					pickupLocation.setSoId(so.getSoId());
					//if (pickupLocation.getLocationId() != null) {
						Integer locationId = (Integer)insert("solocation.insert", pickupLocation);
						pickupLocation.setLocationId(locationId);
					//}
				}
				if (pickupContact != null && pickupLocation != null	&& pickupContact.getContactId() != null	&& pickupLocation.getLocationId() != null) {
					HashMap pickupLocationContact = new HashMap();
					pickupLocationContact.put("soId", so.getSoId());
					pickupLocationContact.put("ContactLocTypeId", 40);
					pickupLocationContact.put("contactId", pickupContact.getContactId());
					pickupLocationContact.put("locationId", pickupLocation.getLocationId());
					pickupLocationContact.put("createdDate", pickupLocation.getCreatedDate());
					if (pickupLocationContact.get("locationId") != null) {
						insert("socontactlocation.insert", pickupLocationContact);
					}
				}

				int count = update("sowpart.update", part);
				if (count == 0) {
					insert("part.insert", part);
				}
			}//while -end
		} else {
			delete("sowpart.delete", so);
		}

		if (serviceContact != null && serviceLocation != null) {
			HashMap serviceLocationContact = new HashMap();
			serviceLocationContact.put("soId", so.getSoId());
			serviceLocationContact.put("ContactLocTypeId", 10);

			serviceLocationContact.put("contactId", serviceContact
					.getContactId());
			serviceLocationContact.put("locationId", serviceLocation
					.getLocationId());
			serviceLocationContact.put("createdDate", serviceLocation
					.getCreatedDate());
			if (serviceLocationContact.get("locationId") != null) {
				insert("socontactlocation.insert", serviceLocationContact);
			}
		}

		if (buyerSupportContact != null && buyerSupportLocation != null
				&& buyerSupportContact.getContactId() != null
				&& buyerSupportLocation.getLocationId() != null) {
			HashMap buyerSupportLocationContact = new HashMap();
			buyerSupportLocationContact.put("soId", so.getSoId());
			buyerSupportLocationContact.put("ContactLocTypeId", 30);
			buyerSupportLocationContact.put("contactId", buyerSupportContact
					.getContactId());
			buyerSupportLocationContact.put("locationId", buyerSupportLocation
					.getLocationId());
			buyerSupportLocationContact.put("createdDate", buyerSupportLocation
					.getCreatedDate());
			insert("socontactlocation.insert", buyerSupportLocationContact);
		}

		if (endUserContact != null && endUserContact.getContactId() != null) {
			HashMap endUserLocationContact = new HashMap();
			endUserLocationContact.put("soId", so.getSoId());
			endUserLocationContact.put("ContactLocTypeId", 20);
			endUserLocationContact.put("contactId", endUserContact
					.getContactId());
			endUserLocationContact.put("locationId", null);
			endUserLocationContact.put("createdDate", endUserContact
					.getCreatedDate());
			insert("socontactlocation.insert", endUserLocationContact);
		}
		return null;

	}
	
	public ServiceOrder insertServiceOrderAsscoiatesForWS(ServiceOrder so)
			throws DataServiceException {
//		RoutedProvider routedProvider;

		if (null != so.getRoutedResources()
				&& so.getRoutedResources().size() > 0) {
			insertRoutedResources(so.getRoutedResources());
		}

		Contact altBuyerConatctInfo = null;
		if (so.getSelectedAltBuyerContact() != null) {
//				&& so.getSelectedAltBuyerContact().getContactId() != null) {
			altBuyerConatctInfo = (Contact) queryForObject(
					"altBuyerContactInsert.query", so
							.getSelectedAltBuyerContact().getContactId());
			altBuyerConatctInfo.setSoId(so.getSoId());
			altBuyerConatctInfo.setContactTypeId(20);
			Integer contactId = (Integer) insert("socontact.insert", altBuyerConatctInfo);
			altBuyerConatctInfo.setContactTypeId(30);
			altBuyerConatctInfo.setContactId(contactId);
			insert("socontactlocn.insert", altBuyerConatctInfo);
		}

		List<ServiceOrderCustomRefVO> customRef = new ArrayList<ServiceOrderCustomRefVO>();
		delete("sowcustomRef.delete", so);
		if (so.getCustomRefs() != null && so.getCustomRefs().size() > 0) {
			batchInsert("socustomRef.insert", so.getCustomRefs());
//			for (ServiceOrderCustomRefVO vo : so.getCustomRefs()) {
//				//insert("socustomRef.insert", vo);
//				if (logger.isDebugEnabled())
//				  logger.debug("socustomRef.insert:" + vo.getsoId() + ":" + vo.getRefValue());
//			}
		}
		Contact endUserContact = null;
		if (null != so.getEndUserContact()){
//				&& null != so.getEndUserContact().getContactId()) {
			endUserContact = so.getEndUserContact();
			endUserContact.setSoId(so.getSoId());
			Integer contactId = (Integer) insert("socontact.insert", endUserContact);
			endUserContact.setContactId(contactId);
			if (null != endUserContact.getPhones()
					&& endUserContact.getPhones().size() > 0) {
				List<PhoneVO> phonesVo = endUserContact.getPhones();

				for (int pi = 0; pi < phonesVo.size(); pi++) {
					PhoneVO phoneVo = phonesVo.get(pi);
					phoneVo.setSoId(endUserContact.getSoId());
					phoneVo.setSoContactId(contactId);
//					try {
//						phoneVo.setSoContactPhoneId(randomNo.generateGUID()
//								.intValue());
//					} catch (Exception e) {
//						logger.info("Caught Exception and ignoring",e);
//					}
					if (phoneVo != null && StringUtils.isNotBlank(phoneVo.getPhoneNo())) {
						insert("contactPhone.insert", phoneVo);
					}

				}
			}
		}
		Contact serviceContact = null;
		if (null != so.getServiceContact()){
//				&& null != so.getServiceContact().getContactId()) {
			serviceContact = so.getServiceContact();
			serviceContact.setSoId(so.getSoId());
			Integer contactId = (Integer) insert("socontact.insert", serviceContact);
			serviceContact.setContactId(contactId);
			if (null != serviceContact.getPhones()
					&& serviceContact.getPhones().size() > 0) {
				List<PhoneVO> phonesVo = serviceContact.getPhones();
				for (int pi = 0; pi < phonesVo.size(); pi++) {
					PhoneVO phoneVo = phonesVo.get(pi);
					phoneVo.setSoContactId(contactId);
					if (phoneVo != null && StringUtils.isNotBlank(phoneVo.getPhoneNo())) {
						insert("contactPhone.insert", phoneVo);
					}
				}
				
			}

		}
		Contact alternateServiceContact = null;
		if (null != so.getAltServiceContact()) {
			alternateServiceContact = so.getAltServiceContact();
			alternateServiceContact.setSoId(so.getSoId());
//			int contactId = -1;
//			try {
//				contactId = randomNo.generateGUID().intValue();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			alternateServiceContact.setContactId(contactId);
			alternateServiceContact.setBusinessName(so.getServiceContact()
					.getBusinessName());
			alternateServiceContact.setContactTypeId(20);
			Integer contactId = (Integer) insert("socontact.insert", alternateServiceContact);
			alternateServiceContact.setContactTypeId(10);
			alternateServiceContact.setContactId(contactId);
			insert("socontactlocn.insert", alternateServiceContact);

			// List<PhoneVO> phonesVo = alternateServiceContact.getPhones();
			if (null != alternateServiceContact.getPhones()
					&& alternateServiceContact.getPhones().size() > 0) {
				List<PhoneVO> phonesVo = alternateServiceContact.getPhones();
				for (int pi = 0; pi < phonesVo.size(); pi++) {
					PhoneVO phoneVo = phonesVo.get(pi);
					phoneVo.setSoId(alternateServiceContact.getSoId());
					phoneVo.setSoContactId(contactId);
					if (phoneVo.getPhoneType() == null)
						phoneVo.setPhoneType(2);
					if (phoneVo.getClassId() == null)
						phoneVo.setClassId(1);
//					try {
//						phoneVo.setSoContactPhoneId(randomNo.generateGUID()
//								.intValue());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
					insert("contactPhone.insert", phoneVo);
				}
			}
		}

		Contact buyerSupportContact = null;
		if (null != so.getBuyerSupportContact()) {
			buyerSupportContact = so.getBuyerSupportContact();
			buyerSupportContact.setSoId(so.getSoId());
			Integer contactId = (Integer) insert("socontact.insert", buyerSupportContact);
			buyerSupportContact.setContactId(contactId);
		}
		SoLocation buyerSupportLocation = null;
		if (null != so.getBuyerSupportLocation()) {
			buyerSupportLocation = so.getBuyerSupportLocation();
			buyerSupportLocation.setSoId(so.getSoId());
			Integer locationId = (Integer) insert("solocation.insert", buyerSupportLocation);
			buyerSupportLocation.setLocationId(locationId);
		}
		SoLocation serviceLocation = null;
		if (null != so.getServiceLocation()) {
			serviceLocation = so.getServiceLocation();
			serviceLocation.setSoId(so.getSoId());
			Integer locationId = (Integer) insert("solocation.insert", serviceLocation);
			serviceLocation.setLocationId(locationId);

			insert("solocationgis.insert", locationId);
		}
		ServiceOrderNote soNote = null;
		if (null != so.getSoNotes() && so.getSoNotes().size() > 0) {
			List<ServiceOrderNote> soNotes = (List<ServiceOrderNote>) so
					.getSoNotes();
			Iterator<ServiceOrderNote> noteItr = soNotes.iterator();
			while (noteItr.hasNext()) {
				soNote = noteItr.next();
				soNote.setSoId(so.getSoId());
				insert("note.insert", soNote);
			}
		}
		ServiceOrderTask task;
		if (null != so.getTasks() && so.getTasks().size() > 0) {
			List<ServiceOrderTask> tasks = (List<ServiceOrderTask>) so
					.getTasks();
			Iterator<ServiceOrderTask> tasksItr = tasks.iterator();
			int sortOrder = 0;
			while (tasksItr.hasNext()) {
				task = tasksItr.next();
				task.setSoId(so.getSoId());
				task.setSortOrder(new Integer(sortOrder++));
				//if (task.getTaskId() != null) {
					if (task.getSkillNodeId() != null
							&& task.getSkillNodeId() <= 0) {
						task.setSkillNodeId(null);
					}
					if (task.getServiceTypeId() != null
							&& task.getServiceTypeId() == -1) {
						task.setServiceTypeId(null);
					}
					insert("task.insert", task);
				//}

			}
		} 
		Contact pickupContact = null;
		SoLocation pickupLocation = null;

		if (null != so.getParts() && so.getParts().size() > 0) {
			List<Part> parts = (List<Part>) so.getParts();
			Iterator<Part> partsItr = parts.iterator();

			Part part;
			//delete("sowpart.delete", so);
			while (partsItr.hasNext()) {
				part = partsItr.next();
				part.setSoId(so.getSoId());

				pickupContact = part.getPickupContact();
				pickupLocation = part.getPickupLocation();
				if (pickupContact != null) {
					pickupContact.setSoId(so.getSoId());
					//if (pickupContact.getContactId() != null) {
						Integer contactId = (Integer) insert("socontact.insert", pickupContact);
						pickupContact.setContactId(contactId);
						if (null != pickupContact.getPhones()
								&& pickupContact.getPhones().size() > 0) {
							List<PhoneVO> phonesVo = pickupContact.getPhones();
							for (int pi = 0; pi < phonesVo.size(); pi++) {
								PhoneVO phoneVo = phonesVo.get(pi);
								phoneVo.setSoContactId(contactId);
								if (phoneVo != null && StringUtils.isNotBlank(phoneVo.getPhoneNo())) {
									insert("contactPhone.insert", phoneVo);
								}

							}
						}
					//}
				}
				
				if (pickupLocation != null) {
					pickupLocation.setSoId(so.getSoId());
					//if (pickupLocation.getLocationId() != null) {
						Integer locationId = (Integer)insert("solocation.insert", pickupLocation);
						pickupLocation.setLocationId(locationId);
					//}
				}
				if (pickupContact != null && pickupLocation != null
						&& pickupContact.getContactId() != null
						&& pickupLocation.getLocationId() != null) {
					HashMap pickupLocationContact = new HashMap();
					pickupLocationContact.put("soId", so.getSoId());
					pickupLocationContact.put("ContactLocTypeId", 40);

					pickupLocationContact.put("contactId", pickupContact.getContactId());
					pickupLocationContact.put("locationId", pickupLocation.getLocationId());
					pickupLocationContact.put("createdDate", pickupLocation.getCreatedDate());
					if (pickupLocationContact.get("locationId") != null) {
						insert("socontactlocation.insert",
								pickupLocationContact);
					}
				}

				insert("part.insert", part);
			}
		} 

		if (serviceContact != null && serviceLocation != null) {
			HashMap serviceLocationContact = new HashMap();
			serviceLocationContact.put("soId", so.getSoId());
			serviceLocationContact.put("ContactLocTypeId", 10);

			serviceLocationContact.put("contactId", serviceContact
					.getContactId());
			serviceLocationContact.put("locationId", serviceLocation
					.getLocationId());
			serviceLocationContact.put("createdDate", serviceLocation
					.getCreatedDate());
			if (serviceLocationContact.get("locationId") != null) {
				insert("socontactlocation.insert", serviceLocationContact);
			}
		}

		if (buyerSupportContact != null && buyerSupportLocation != null
				&& buyerSupportContact.getContactId() != null
				&& buyerSupportLocation.getLocationId() != null) {
			HashMap buyerSupportLocationContact = new HashMap();
			buyerSupportLocationContact.put("soId", so.getSoId());
			buyerSupportLocationContact.put("ContactLocTypeId", 30);
			buyerSupportLocationContact.put("contactId", buyerSupportContact
					.getContactId());
			buyerSupportLocationContact.put("locationId", buyerSupportLocation
					.getLocationId());
			buyerSupportLocationContact.put("createdDate", buyerSupportLocation
					.getCreatedDate());
			insert("socontactlocation.insert", buyerSupportLocationContact);
		}

		if (endUserContact != null && endUserContact.getContactId() != null) {
			HashMap endUserLocationContact = new HashMap();
			endUserLocationContact.put("soId", so.getSoId());
			endUserLocationContact.put("ContactLocTypeId", 20);
			endUserLocationContact.put("contactId", endUserContact
					.getContactId());
			endUserLocationContact.put("locationId", null);
			endUserLocationContact.put("createdDate", endUserContact
					.getCreatedDate());
			insert("socontactlocation.insert", endUserLocationContact);
		}
		return null;

	}

	public ServiceOrder insert(ServiceOrder so) throws DataServiceException {

		if (logger.isDebugEnabled())
			logger.debug("inserting service order: " + so);
		
		setServiceLocnTimeZone(so);
		insert("sohdr.insert", so);
		insertServiceOrderAsscoiates(so);
		insertSOPrice(so);

		return null;
	}
	
	private ServiceOrder setServiceLocnTimeZone(ServiceOrder so) {
		String timezone = "";
		try{
			String serviceLocnZip = so.getServiceLocation().getZip();
			timezone = (String) queryForObject("zipCode.query", serviceLocnZip);
			if(timezone == null || StringUtils.isEmpty(timezone)){
				String serviceLocnState = so.getServiceLocation().getState();
				List<String> timeZonesList = queryForList("stateTimeZone.query", serviceLocnState);
				if(timeZonesList!= null && timeZonesList.size() > 0){
					timezone = timeZonesList.get(0);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception occurred while getting timezone for Zip");
		}
		if(null != timezone){
			TimeZone timezone1 = TimeZone.getTimeZone(timezone);
			int offset = Math.round(timezone1.getOffset((new Date()).getTime()) / (1000*60*60));
			so.setServiceDateTimezoneOffset(new Integer(offset));
		}		
		so.setServiceLocationTimeZone(timezone);
		return so;
	}

	public ServiceOrder insertSOForWS(ServiceOrder so) throws DataServiceException {

		if (logger.isDebugEnabled())
			logger.debug("inserting service order: " + so);
		setServiceLocnTimeZone(so);
		synchronized (lock1) {				
		insert("sohdr.insert", so);
		}

		synchronized (lock2) {
		insertServiceOrderAsscoiatesForWS(so);
		}
		
		synchronized (lock3) {
		insertSOPrice(so);
		}
		
		return so;
	}

	private void insertSOPrice(ServiceOrder so) {
		if (logger.isDebugEnabled())
			logger.debug("inserting service order price: " + so);
		
		ServiceOrderPriceVO soPriceVO = so.getSoPrice();
		if(soPriceVO!= null){
			soPriceVO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			insert("soprice.insert", soPriceVO);			
		}

		
	}

	public ServiceOrder insertSoHeader(ServiceOrder so)
			throws DataServiceException {
		setServiceLocnTimeZone(so);
		insert("sohdr.insert", so);
		return null;
	}
	
	/**
	 * This query is funky in the map. The where contains part_id != part_id
	 */
	public void deleteParts(ServiceOrder so) throws DataServiceException {
		delete("sowpart.delete", so);
	}
	
	/**
	 * This query deletes all the parts for a service order regardless of part_id
	 */
	public void deleteParts(String soID) {
		delete("soparts.delete", soID);
		//remove pickup locations and contacts
		
	}
	
	public ServiceOrder query(ServiceOrder serviceOrder)
			throws DataServiceException {
		try {
			serviceOrder = (ServiceOrder) queryForObject("so.query",
					serviceOrder);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return serviceOrder;
	}

	public ArrayList<ServiceOrder> queryList(ServiceOrder serviceOrder)
			throws DataServiceException {
		ArrayList<ServiceOrder> serviceOrderList;
		try {
			serviceOrderList = (ArrayList<ServiceOrder>) queryForList(
					"so.query", serviceOrder);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return serviceOrderList;
	}

	public void addOrUpdatePart(Part part) throws DataServiceException {
		try {
			Integer partId=(Integer) queryForObject("wspart.select", part);
			if(partId==null){
				//RandomGUID randomNo = new RandomGUID();	
				//part.setPartId(randomNo.generateGUID().intValue());
				insert("part.insert", part);
			}else{
				//part.setPartId(partId);
				update("sowpart.update", part);
			}

		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.updatePartInfo - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	
	public int updateParts(List<Part> partList) throws DataServiceException {
		int intUpdPartCnt = 0;
		try {
			for (int pi = 0; pi < partList.size(); pi++) {
				Part part = partList.get(pi);
				if (part != null) {
					intUpdPartCnt = update("part.update", part);
				}
			}
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.updatePartInfo - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return intUpdPartCnt;
	}

	public int update(ServiceOrder so) throws DataServiceException {
		try {
			return update("so.update", so);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	/**
	 * This method updates the final price data
	 * @param so
	 * @return
	 * @throws DataServiceException
	 */
	public int updateFinalPrice(ServiceOrder so) throws DataServiceException {
		try {
			return update("soFinalPrice.update", so);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.soFinalPrice.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public int updateSOReschedule(ServiceOrder so) throws DataServiceException {
		try { 
			// hack here to set the service date type field correctly in the 
			// database
			if (so.getServiceDate2() != null && so.getServiceTimeEnd() != null)
				so.setServiceDateTypeId(Integer.valueOf(OrderConstants.RANGE_DATE));
			else
				so.setServiceDateTypeId(Integer.valueOf(OrderConstants.FIXED_DATE));
			HashMap rescheduleDetails = new HashMap();
			rescheduleDetails.put("soId", so.getSoId());
			rescheduleDetails.put("reasonCodeId", so.getWfSubStatusId());
			rescheduleDetails.put("comments","Provider requests a reschedule Reason:" +so.getReasonText()+ "Comments:"+so.getComments());
			if (rescheduleDetails.get("soId") != null) {
				insert("soReschedule.insert", rescheduleDetails);
			}
			return update("soReschedule.update", so);
			} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public int updateLimit(ServiceOrder so) throws DataServiceException {
		try {
			logger.debug("Start of ServiceOrderDaoImpl.updateLimit");
			logger.info("changed limit : " + so.getSpendLimitLabor());
			return update("so.updateLimit", so);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.updateLimit - Exception]", ex);
			throw new DataServiceException("[ServiceOrderDaoImpl.updateLimit - Exception]", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#updateSOPrice(com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO)
	 */
	public int updateSOPrice(ServiceOrderPriceVO soPriceVO)
			throws DataServiceException {
		try {
			logger.debug("Start of ServiceOrderDaoImpl.updateSOPrice");
			return update("so.updateSOPrice", soPriceVO);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.updateSOPrice - Exception]", ex);
			throw new DataServiceException("[ServiceOrderDaoImpl.updateSOPrice - Exception]", ex);
		}
	}
	
	public String queryProviderEmail(Integer id) throws DataServiceException {
		return (String) queryForObject("so.queryProviderEmail", id);
	}

	public ServiceOrderDetail queryDetail(ServiceOrder serviceOrder)
			throws DataServiceException {
		try {
			return (ServiceOrderDetail) queryForObject("soDtl.querySo",
					serviceOrder);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryDetail - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public BuyerDetail queryBuyerDetail(String soId)
			throws DataServiceException {
		try {
			return (BuyerDetail) queryForObject("soDtl.queryBuyer", soId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryBuyerDetail - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ProviderDetail queryAcceptedProviderDetail(String soId)
			throws DataServiceException {
		try {
			return (ProviderDetail) queryForObject(
					"soDtl.queryAcceptedProvider", soId);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.queryProviderDetail - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ArrayList<ProviderDetail> queryRoutedProviderDetail(String soId)
			throws DataServiceException {
		try {
			return (ArrayList<ProviderDetail>) queryForList(
					"soDtl.queryRoutedProviders", soId);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.queryProviderDetail - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ArrayList<ServiceOrderTaskDetail> queryTasksDetail(String soId)
			throws DataServiceException {
		try {
			return (ArrayList<ServiceOrderTaskDetail>) queryForList(
					"soDtl.queryTasks", soId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryTasksDetail - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ArrayList<PartDetail> queryPartsDetail(Part part)
			throws DataServiceException {
		try {
			return (ArrayList<PartDetail>) queryForList("soDtl.queryParts",
					part);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryPartsDetail - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ArrayList<ServiceOrderNoteDetail> queryNotesDetail(String soId)
			throws DataServiceException {
		try {
			return (ArrayList<ServiceOrderNoteDetail>) queryForList(
					"soDtl.queryNotes", soId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryNotesDetail - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.services.order.dao.ServiceOrderDao#updateServiceOrderWorkflowState(com.newco.domain.ServiceOrder,
	 *      java.lang.Integer, java.util.ArrayList)
	 */
	public int updateServiceOrderWorkflowState(ServiceOrder serviceOrder,
			Integer futureWorkflowState,
			ArrayList<Integer> validWorkflowStateTransitions)
			throws DataServiceException {
		ServiceOrderWorkflowStateTransitionVO vo = new ServiceOrderWorkflowStateTransitionVO();

		vo.setFutureWorkflowState(futureWorkflowState);
		vo.setServiceOrder(serviceOrder);
		vo.setWorkflowStateTransitionList(validWorkflowStateTransitions);
		vo
				.setUpdateTime(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));

		try {
			return update("so.transitionSOWorkflowState", vo);

		}
		catch (Throwable t) {
			logger.error("[updateServiceOrderWorkflowState() - SO{"
					+ serviceOrder + "}; FutureState{" + futureWorkflowState
					+ "}; TransitionStates{" + validWorkflowStateTransitions
					+ "}.", t);
			throw new DataServiceException("Unable to update workflow state");

		}

	}

	public ArrayList<ServiceOrderRole> queryListBuyer(RoleVO rvo)
			throws DataServiceException {

		try {

			return (ArrayList) queryForList("sorole.queryBuyer", rvo
					.getRoleIdInt());

		} catch (Exception e) {

			logger.error("[ServiceOrderDaoImpl.queryListBuyer]\n" + e);
			throw new DataServiceException(
					"Cannot retrieve Buyer service orders");

		}

	}

	public ArrayList<ServiceOrderRole> queryListProvider(RoleVO rvo)
			throws DataServiceException {

		try {

			return (ArrayList) queryForList("sorole.queryProvider", rvo
					.getRoleIdInt());

		} catch (Exception e) {

			logger.error("[ServiceOrderDaoImpl.queryListProvider]\n" + e);
			throw new DataServiceException(
					"Cannot retrieve provider service orders");

		}
	}

	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesVendorCounts(
			ServiceOrderWfStatesCountsVO soWfStatesVO)
			throws DataServiceException {

		try {

			return (ArrayList) queryForList(
					"soVendorCounts.querySOWfStatesVendorCounts", soWfStatesVO);

		} catch (Exception e) {

			logger.error("[ServiceOrderDaoImpl.querySOWfStatesVendorCounts]\n"
					+ e);
			throw new DataServiceException(
					"Cannot retrieve service order vendor wf states counts");

		}
	}

	// called from ServiceOrderMonitorAction
	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesBuyerCounts(
			AjaxCacheVO ajaxCacheVo) throws DataServiceException {
		try {

			return (ArrayList) queryForList("SOWfStatesBuyerCounts.query",
					ajaxCacheVo);

		} catch (Exception e) {

			logger.error("[ServiceOrderDaoImpl.SOWfStatesBuyerCounts.query]\n"
					+ e);
			throw new DataServiceException(
					"Cannot retrieve service order buyer wf states counts");

		}
	}
	
	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesSimpleBuyerCounts(
			AjaxCacheVO ajaxCacheVo) throws DataServiceException {
		try {
			return (ArrayList) queryForList("SOWfStatesSimpleBuyerCounts.query", ajaxCacheVo);
		} catch (Exception e) {
			logger.error("[ServiceOrderDaoImpl.SOWfStatesSimpleBuyerCounts.query]",e);
			throw new DataServiceException("Cannot retrieve service order simple buyer wf states counts");
		}
	}

	// Used by Ajax- Cache
	public HashMap getSummaryCountsBuyer(AjaxCacheVO ajaxCacheVO)
			throws DataServiceException {
		return getSummaryCountsBuyer ("SOWfStatesBuyerCounts.query", ajaxCacheVO);
	}
	
	public HashMap getSummaryCountsSimpleBuyer(AjaxCacheVO ajaxCacheVO)
		throws DataServiceException {
		return getSummaryCountsBuyer ("SOWfStatesSimpleBuyerCounts.query", ajaxCacheVO);
	}
	
	private HashMap getSummaryCountsBuyer(String queryName, AjaxCacheVO ajaxCacheVO)
			throws DataServiceException {
		try {
			int todayCount = 0;
			int draftCount = 0;
			int routedCount = 0;
			int acceptedCount = 0;
			int problemCount = 0;
			int inactiveCount = 0;
			int pendingCancelCount = 0;
			
			//R12_1
			//SL-20362
			int pendingReschedule =0;

			HashMap summaryMap = new HashMap();
			ArrayList tabCountList;

			tabCountList = (ArrayList) queryForList(
					"SOWfStatesBuyerCounts.query", ajaxCacheVO);

			for (java.util.Iterator it = tabCountList.iterator(); it.hasNext();) {

				ServiceOrderWfStatesCountsVO tabCountsIterator = (ServiceOrderWfStatesCountsVO) it
						.next();
				String tabType = tabCountsIterator.getTabType();

				// TODAY: 'Active','Completed', 'Expired'
				// DRAFT: 'Draft'
				// POSTED: 'Routed' (Buyer)
				// RECEIVED: 'Routed' (Provider)
				// Accepted: 'Accepted'
				// Problem: 'Problem'
				// Inactive: 'Closed', 'Cancelled', 'Voided'

				if (tabType.equals(OrderConstants.TAB_ACTIVE)
						|| tabType.equals(OrderConstants.TAB_COMPLETED)
						|| tabType.equals(OrderConstants.TAB_EXPIRED)) {
					todayCount += tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_PENDING_CANCEL)){
					todayCount += tabCountsIterator.getSoCount();
					pendingCancelCount += tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_DRAFT)) {
					draftCount = tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_ROUTED)) {
					routedCount = tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_ACCEPTED)) {
					acceptedCount = tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_PROBLEM)) {
					problemCount = tabCountsIterator.getSoCount();
				} else if (tabType.equalsIgnoreCase(OrderConstants.CLOSED)
						|| tabType.equalsIgnoreCase(OrderConstants.CANCELLED)
						|| tabType.equalsIgnoreCase(OrderConstants.VOIDED)
						|| tabType.equalsIgnoreCase(OrderConstants.DELETED)) {
					inactiveCount += tabCountsIterator.getSoCount();
				}
			}

			
			//R12_1
			//SL-20362
			pendingReschedule =(Integer) queryForObject("DashboardPendingRescheduleCount.query", ajaxCacheVO);
			
			summaryMap.put("TODAY", todayCount);
			summaryMap.put("DRAFT", draftCount);
			summaryMap.put("SENT", routedCount);
			summaryMap.put("ACCEPTED", acceptedCount);
			summaryMap.put("PROBLEM", problemCount);
			summaryMap.put("INACTIVE", inactiveCount);
			summaryMap.put("PENDINGCANCEL", pendingCancelCount);

			
			//R12_1
			//SL-20362
			summaryMap.put("PENDINGRESCHEDULE", pendingReschedule);
			
			return summaryMap;

		} catch (Exception e) {

			logger
					.error("[ServiceOrderDaoImpl.soTabCounts.querySOWfStatesBuyerCounts2]\n"
							+ e);
			throw new DataServiceException(
					"Cannot retrieve service order buyer wf states counts");

		}
	}

	// Used by Ajax- Cache
	public HashMap getSummaryCountsProvider(AjaxCacheVO ajaxCacheVO)
		throws DataServiceException {
		try {
			int todayCount = 0;
			int draftCount = 0;
			int routedCount = 0;
			int acceptedCount = 0;
			int problemCount = 0;
			int inactiveCount = 0;
			int bidCount = 0;
			int bBoardCount = 0;
			//Sl-21645
			int estimationRequestCount = 0;
			int pendingCancelCount = 0;
			
			//R12_1
			//SL-20362
			int pendingReschedule =0;
		
			HashMap summaryMap = new HashMap();
			ArrayList tabCountList;
		
			tabCountList = (ArrayList) queryForList(
					"SOWfStatesProviderCounts.query", ajaxCacheVO);
		
			for (java.util.Iterator it = tabCountList.iterator(); it.hasNext();) {
		
				ServiceOrderWfStatesCountsVO tabCountsIterator = (ServiceOrderWfStatesCountsVO) it
						.next();
				String tabType = tabCountsIterator.getTabType();
		
				// TODAY: 'Active','Completed'
				// DRAFT: 'Draft'
				// POSTED: 'Routed' (Buyer)
				// RECEIVED: 'Routed' (Provider)
				// Accepted: 'Accepted'
				// Problem: 'Problem'
				// Inactive: 'Closed', 'Cancelled'
				// BID: 'Routed' (Provider Zero Bid)
				// BBOARD: bulletin board
				// PENDINGRESCHEDULE : pending reschedule
		
				if (tabType.equals(OrderConstants.TAB_ACTIVE)
						|| tabType.equals(OrderConstants.TAB_COMPLETED)) {
					todayCount += tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_PENDING_CANCEL)){
					todayCount += tabCountsIterator.getSoCount();
					pendingCancelCount += tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_ACCEPTED)) {
					acceptedCount = tabCountsIterator.getSoCount();
				} else if (tabType.equals(OrderConstants.TAB_PROBLEM)) {
					problemCount = tabCountsIterator.getSoCount();
				} else if (tabType.equalsIgnoreCase(OrderConstants.CLOSED)
						|| tabType.equalsIgnoreCase(OrderConstants.CANCELLED)) {
					inactiveCount += tabCountsIterator.getSoCount();
				}
			}
		
			routedCount = (Integer) queryForObject(
					"providerReceivedCount.query", ajaxCacheVO);
			
			bidCount = (Integer) queryForObject("providerBidCount.query", ajaxCacheVO);
		
			bBoardCount = (Integer) queryForObject("providerBulletinBoardCount.query", ajaxCacheVO);
			
			//Sl-21465
			estimationRequestCount = (Integer) queryForObject("providerEstimationRequestCount.query", ajaxCacheVO);
			
			//R12_1
			//SL-20362
			pendingReschedule =(Integer) queryForObject("DashboardPendingRescheduleCount.query", ajaxCacheVO);
			
			summaryMap.put("TODAY", todayCount);
			summaryMap.put("RECEIVED", routedCount);
			summaryMap.put("ACCEPTED", acceptedCount);
			summaryMap.put("PROBLEM", problemCount);
			summaryMap.put("INACTIVE", inactiveCount);
			summaryMap.put("BID", bidCount);
			summaryMap.put("BBOARD", bBoardCount);
			//Sl-21465
			summaryMap.put("ESTIMATIONREQUEST", estimationRequestCount);
			summaryMap.put("PENDINGCANCEL", pendingCancelCount);
			
			//R12_1
			//SL-20362
			summaryMap.put("PENDINGRESCHEDULE", pendingReschedule);
		
			return summaryMap;
		
		} catch (Exception e) {
		
			logger
					.error("[ServiceOrderDaoImpl.soTabCounts.querySOWfStatesBuyerCounts2]\n"
							+ e);
			throw new DataServiceException(
					"Cannot retrieve service order buyer wf states counts");
		
		}
	}

	// Called from ServiceOrderMonitorAction
	public ArrayList<ServiceOrderWfStatesCountsVO> queryListSOWfStatesProviderCounts(
			AjaxCacheVO ajaxCacheVo) throws DataServiceException {
		ArrayList<ServiceOrderWfStatesCountsVO> alTabCounts = null;
		try {

			alTabCounts = (ArrayList<ServiceOrderWfStatesCountsVO>) queryForList(
					"SOWfStatesProviderCounts.query", ajaxCacheVo);

			// Fetch Received Tab Count
			Integer receivedTabCount = (Integer) queryForObject(
					"providerReceivedCount.query", ajaxCacheVo);
			ServiceOrderWfStatesCountsVO receivedTabCountVO = new ServiceOrderWfStatesCountsVO();
			receivedTabCountVO.setTabType("RECEIVED");
			receivedTabCountVO.setSoCount(receivedTabCount);
			alTabCounts.add(receivedTabCountVO);
			
			Integer bidTabCount = (Integer) queryForObject(
					"providerBidCount.query", ajaxCacheVo);
			ServiceOrderWfStatesCountsVO bidRequestsTabCountVO = new ServiceOrderWfStatesCountsVO();
			bidRequestsTabCountVO.setTabType(OrderConstants.TAB_BID_REQUESTS);
			bidRequestsTabCountVO.setSoCount(bidTabCount);
			alTabCounts.add(bidRequestsTabCountVO);
			
			Integer bullBoardTabCount = (Integer) queryForObject(
					"providerBulletinBoardCount.query", ajaxCacheVo);
			
			ServiceOrderWfStatesCountsVO billBoardTabCountVO = new ServiceOrderWfStatesCountsVO();
			billBoardTabCountVO.setTabType(OrderConstants.TAB_BULLETIN_BOARD);
			billBoardTabCountVO.setSoCount(bullBoardTabCount);
			alTabCounts.add(billBoardTabCountVO);
			
			
			

		} catch (Exception e) {

			logger
					.error("[ServiceOrderDaoImpl.soTabCounts.querySOWfStatesProviderCounts2]\n"
							+ e);
			throw new DataServiceException(
					"Cannot retrieve service order provider wf states counts");

		}
		return alTabCounts;
	}

	// TODO: SC-REMOVE IT
	public ArrayList<ServiceOrderTabResultsVO> getSOProviderReceived(
			Integer vendorId) throws DataServiceException {
		ArrayList<ServiceOrderTabResultsVO> receivedTabResults = new ArrayList<ServiceOrderTabResultsVO>();

		try {
			receivedTabResults = (ArrayList<ServiceOrderTabResultsVO>) queryForList(
					"soReceived.queryReceivedTabData", vendorId);

		} catch (Exception e) {
			logger
					.info("[ServiceOrderSearchDaoImpl.getSOProviderReceived exception]"
							+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);

		}

		return receivedTabResults;
	}

	public boolean acceptServiceOrder(String serviceOrderID, String userName,
			Integer resourceID) {

		return false;
	}

	public ArrayList<HashMap> queryRoutedSO(String serviceOrderID) {
		ArrayList<HashMap> al = (ArrayList<HashMap>) queryForList(
				"so.getRoutedResources", serviceOrderID);
		for (int i = 0; i < al.size(); i++) {
			HashMap hm = (HashMap) al.get(i);
		}
		return al;
	}

	public ArrayList<ServiceOrderSearchResultsVO> getDetailedCountsBuyer(
			AjaxCacheVO ajaxCacheVO) throws DataServiceException {

		ArrayList<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		try {
			soSearchList = (ArrayList<ServiceOrderSearchResultsVO>) queryForList(
					"soSearch.queryBuyerPostedCache", ajaxCacheVO);
		} catch (Exception ex) {
			logger.info("[ServiceOrderSearchDAOImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return soSearchList;
	}

	// TODO: - NOT USED
	public ArrayList<ServiceOrderSearchResultsVO> getProviderCachedDetails(
			AjaxCacheVO ajaxCacheVO) throws DataServiceException {

		ArrayList<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		try {
			soSearchList = (ArrayList<ServiceOrderSearchResultsVO>) queryForList(
					"soSearch.queryProviderReceivedCache", ajaxCacheVO
							.getCompanyId());

		} catch (Exception e) {
			logger
					.info("[ServiceOrderSearchDaoImpl.getProviderCachedDetails] Exception)"
							+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
		return soSearchList;
	}

	// Called by CacheManager
	public ArrayList<ServiceOrderSearchResultsVO> getDetailedCountsProvider(
			AjaxCacheVO ajaxCacheVO) throws DataServiceException {
		ArrayList<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		try {
			soSearchList = (ArrayList<ServiceOrderSearchResultsVO>) queryForList(
					"providerReceivedCache.query", ajaxCacheVO);
		} catch (Exception ex) {
			logger.info("[ServiceOrderSearchDAOImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return soSearchList;
	}

	public int getRoutedProviderCount(String soId) throws DataServiceException {
		try {
			return (Integer) queryForObject("so.routedProviderCount", soId);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.getRoutedProviderCount - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public int getRejectResponseCount(String soId) throws DataServiceException {
		try {
			return (Integer) queryForObject("so.rejectResponseCount", soId);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.getRejectResponseCount - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public int updateProviderResponse(ResponseSoVO responseSoVo)
			throws DataServiceException {
		try {
			return update("so.updateResponse", responseSoVo);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#updateRoutedProvidersEmailSentFlag(com.newco.marketplace.dto.vo.serviceorder.ServiceOrder)
	 */
	public int updateRoutedProvidersEmailSentFlag(ServiceOrder serviceOrder)
			throws DataServiceException {
		try {
			return update("soRoutedProviders.email.update", serviceOrder);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

	}

	public ArrayList<ReasonLookupVO> queryListRejectReason()
			throws DataServiceException {
		ArrayList<ReasonLookupVO> reasonLookupList;
		try {
			reasonLookupList = (ArrayList<ReasonLookupVO>) queryForList(
					"so_reject_reason_lookup.query", null);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return reasonLookupList;

	}

	private void populateOtherSOContactTables(ServiceOrder so)
			throws DataServiceException {
		Contact acptResCt = null;
		Timestamp ts = null;
		Calendar calendar = Calendar.getInstance();
//		int intSoContactId = 0;
//		int intSoLocnId = 0;

		try {
			// Retrieve information from so_hdr, vendor_resource, contact,
			// location, vendor_hdr tables
			acptResCt = (Contact) queryForObject(
					"acceptedResourceContactInfo.query", so.getSoId());
			System.out.println("acptResCt : " + acptResCt.toString());

			ts = new Timestamp(calendar.getTimeInMillis());
//			intSoContactId = randomNo.generateGUID().intValue();
//			acptResCt.setContactId(intSoContactId);
			acptResCt.setCreatedDate(ts);
			// Primary Contact
			acptResCt.setContactTypeId(new Integer(10));
			// Entity Type id 20 for provider
			acptResCt.setEntityTypeId(new Integer(20));

			// populate so_contact table
			Integer contactId = (Integer) insert("socontact.insert", acptResCt);
			acptResCt.setContactId(contactId);
			
//			intSoLocnId = randomNo.generateGUID().intValue();

			// CQ Sears62103: prevents the duplication of so_contact and so_contact_locn information.
			if (so.getVendorResourceLocation() == null) {
				SoLocation sl = new SoLocation();
				sl.setSoId(acptResCt.getSoId());
//				sl.setLocationId(intSoLocnId);
				sl.setStreet1(acptResCt.getStreet_1());
				sl.setStreet2(acptResCt.getStreet_2());
				sl.setCity(acptResCt.getCity());
				sl.setZip(acptResCt.getZip());
				sl.setZip4(acptResCt.getZip4());
				sl.setState(acptResCt.getStateCd());
				sl.setCountry(acptResCt.getCountry());
				// Provider Location Type
				sl.setLocnTypeId(50);
				// Commercial Address Class
				sl.setLocnClassId(1);
				sl.setLocName(acptResCt.getLocName());
				sl.setCreatedDate(ts);

				// --Inserting location details--
				// populate so_location table
				Integer locationId = (Integer)insert("solocation.insert", sl);
				// Provider Location Type Id
				acptResCt.setContactTypeId(new Integer(50));
				acptResCt.setLocationId(locationId);
				// populate so_contact_locn table
				insert("socontactlocn.insert", acptResCt);
			} //CQ Sears62103

			// --Inserting contact phone details
			List phoneVoList = new ArrayList();
			Integer phnExt = null;
			if (StringUtils.isNotBlank(acptResCt.getPhoneNo())) {
				PhoneVO phnVo = new PhoneVO();
				phnVo.setSoId(acptResCt.getSoId());
				phnVo.setSoContactId(acptResCt.getContactId());
				//phnVo.setSoContactPhoneId(randomNo.generateGUID().intValue());
				phnVo.setPhoneType(1);
				// Work Phone
				phnVo.setClassId(new Integer(1));
				phnVo.setPhoneNo(acptResCt.getPhoneNo());
				phnVo.setPhoneExt(acptResCt.getPhoneNoExt());
				phnVo.setCreatedDate(ts);
				phoneVoList.add(phnVo);
			}

			if (StringUtils.isNotBlank(acptResCt.getCellNo())) {
				PhoneVO cellVo = new PhoneVO();
				cellVo.setSoId(acptResCt.getSoId());
				cellVo.setSoContactId(acptResCt.getContactId());
				//cellVo.setSoContactPhoneId(randomNo.generateGUID().intValue());
				cellVo.setPhoneType(2);
				// Work Phone
				cellVo.setClassId(new Integer(2));
				cellVo.setPhoneNo(acptResCt.getCellNo());
				cellVo.setCreatedDate(ts);
				phoneVoList.add(cellVo);
			}

			if (StringUtils.isNotBlank(acptResCt.getFaxNo())) {
				PhoneVO faxVo = new PhoneVO();
				faxVo.setSoId(acptResCt.getSoId());
				faxVo.setSoContactId(acptResCt.getContactId());
				//faxVo.setSoContactPhoneId(randomNo.generateGUID().intValue());
				faxVo.setPhoneType(3);
				// Work Phone
				faxVo.setClassId(new Integer(6));
				faxVo.setPhoneNo(acptResCt.getFaxNo());
				faxVo.setCreatedDate(ts);
				phoneVoList.add(faxVo);
			}

			if (phoneVoList != null && phoneVoList.size() > 0) {
				for (int i = 0; i < phoneVoList.size(); i++) {
					PhoneVO phoneVo = (PhoneVO) phoneVoList.get(i);
					insert("contactPhone.insert", phoneVo);
				}
			}
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.populateOtherSOContactTables - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public int updateAccepted(ServiceOrder so) throws DataServiceException {
		int updAccepted = 0;
		try {
			updAccepted = update("so.updateAccepted", so);
			// populate so_contact, so_contact_locn and so_contact_phone for
			// accepted provider information
			if (updAccepted > 0) {
				populateOtherSOContactTables(so);
			}
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return updAccepted;
	}

	public ServiceOrderDetail retrieveSoDetail(String soId)
			throws DataServiceException {
		try {
			return (ServiceOrderDetail) queryForObject("soId.query", soId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryBuyerDetail - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public ServiceOrderNote insertNote(ServiceOrderNote note)
			throws DataServiceException {
		int noteId = (Integer) insert("note.insert", note);
		note.setNoteId(new Long(noteId));
		return note; 
	}

	public ServiceOrderNote queryMax(ServiceOrderNote note)
			throws DataServiceException {
		return (ServiceOrderNote) queryForObject("note.queryMax", note);
	}	
	
	public List getSONotes(ServiceOrderNote soNote) throws DataServiceException {
		if (soNote.getNoteTypeId() != null && soNote.getNoteTypeId().intValue() != -1){
			List noteTypeIds = new ArrayList<Integer>();
			noteTypeIds.add(soNote.getNoteTypeId());
			soNote.setNoteTypeIds(noteTypeIds);
		}
		return (ArrayList<ServiceOrderNote>) queryForList("note.select", soNote);
	}
	
	/**
	 * Function to get all the Notes for SL Administrator
	 */
	public List getAllSONotes(ServiceOrderNote soNote) throws DataServiceException {
		return (ArrayList<ServiceOrderNote>) queryForList("note.select.all", soNote);
	}
	
	/**
	 * Function to get all the Deleted notes for SL Administrator
	 * @param soNote
	 * @return
	 * @throws DataServiceException
	 */
	public List getSODeletedNotes(ServiceOrderNote soNote) throws DataServiceException {
		return (ArrayList<ServiceOrderNote>) queryForList("note.select.deleted.all", soNote);
	}
	
	public Part insertPart(Part part) throws DataServiceException {
		if (part.getPickupLocation() != null) {
			part.getPickupLocation().setSoId(part.getSoId());
			if (part.getPickupLocation().getLocationId() == null) {
				try {
					Integer locationId = (Integer)insert("solocation.insert", part.getPickupLocation());
					part.getPickupLocation().setLocationId(locationId);
				}
				catch (Exception e) {
					throw new DataServiceException("Error generating ID or saving pickup location");
				}
			}
			else {
				updateServiceLocation(part.getSoId(), part.getPickupLocation());
			}
			if (part.getPickupContact() != null && part.getPickupContact().getContactId() == null) {
				try {
					part.getPickupContact().setSoId(part.getSoId());
                    Integer contactId = (Integer) insert("socontact.insert", part.getPickupContact());
					part.getPickupContact().setContactId(contactId);
					HashMap pickupLocationContact = new HashMap();
					pickupLocationContact.put("soId", part.getSoId());
					pickupLocationContact.put("ContactLocTypeId", 40);
					pickupLocationContact.put("contactId", part.getPickupContact().getContactId());
					pickupLocationContact.put("locationId", part.getPickupLocation().getLocationId());
					pickupLocationContact.put("createdDate", part.getPickupContact().getCreatedDate());
					if (pickupLocationContact.get("locationId") != null) {
						insert("socontactlocation.insert", pickupLocationContact);
					}
				}
				catch (Exception e) {
					throw new DataServiceException("Error generating ID or saving pickup contact");
				}
			}
		}
		Integer partId = (Integer)insert("part.insert", part);
		part.setPartId(partId);
		return part;
	}

	public ServiceOrderTask insertTask(ServiceOrderTask task)
		throws DataServiceException {
		Integer taskId = (Integer)insert("task.insert", task);
		task.setTaskId(taskId);
		return task;
	}

	/*
	 * public WfStatesVO query(WfStatesVO wfStatesVO) throws DataAccessException {
	 * try { wfStatesVO = (WfStatesVO) queryForObject("wf_states_lookup.query",
	 * wfStatesVO); } catch (Exception ex) {
	 * logger.info("[ServiceOrderDaoImpl.query - Exception] " +
	 * StackTraceHelper.getStackTrace(ex)); }
	 * 
	 * return wfStatesVO; }
	 * 
	 * public ArrayList<WfStatesVO> queryList(WfStatesVO wfStatesVO) throws
	 * DataAccessException { ArrayList<WfStatesVO> wfStatesVOList = null; try {
	 * wfStatesVOList = (ArrayList<WfStatesVO>)queryForList("wf_states_lookup.query",
	 * wfStatesVO); } catch (Exception ex) {
	 * logger.info("[ServiceOrderDaoImpl.queryList - Exception] " +
	 * StackTraceHelper.getStackTrace(ex)); }
	 * 
	 * return wfStatesVOList; }
	 */

	public ArrayList<ServiceOrder> queryFilterServiceOrderByStatus(String status)
			throws DataServiceException {
		return null;
	}

	public WfStatesVO getWfStatesVO(Integer wfStateID)
			throws DataServiceException {

		WfStatesVO wfStatesVO = new WfStatesVO();

		try {
			// code change for SLT-2112
			Map<String, Integer> parameter = new HashMap<String, Integer>();
					parameter.put("wfStateID", wfStateID);
			wfStatesVO = (WfStatesVO) queryForObject("so.getWfStates",
					parameter);
		} catch (Exception ex) {
			logger.info("[CancelSODaoImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return wfStatesVO;
	}

	public int updateSOStatus(ServiceOrder serviceOrder)
			throws DataServiceException {
		return update("so.updateSOState", serviceOrder);
	}

	public Integer checkWfState(String soId) throws DataServiceException {
		Integer wf_state;
		try {
			
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			
			wf_state = (Integer) queryForObject("so.checkWfState", parameter);
		} catch (Exception ex) {
			logger.info("[RouteSODaoImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return wf_state;
	}

	public Integer checkConditionalOfferResp(RoutedProvider routedProvider)
			throws DataServiceException {
		Integer respId;
		try {
			respId = (Integer) queryForObject("so.checkconditionaloffer",
					routedProvider);
		} catch (Exception e) {
			throw new DataServiceException("Error checking Offer Response", e);
		}
		return respId;
	}

	// resets the response history on a particular soId
	public int resetResponseHistory(String soId) throws DataServiceException {

		return update("routedProvider.update", soId);
	}

	public int removeConditionalFromRoutedProviders(String soId) throws DataServiceException{
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("soId", soId);
			return update("removeConditional.update", parameter);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ServiceOrderDaoImpl.removeConditionalFromRoutedProviders - Exception] ", e);
			throw new DataServiceException("Error while updating", e);
		}
		
	}
	
	public String getSubStatusDesc(Integer soSubStatusID) throws DataServiceException {
		try {
			return (String) queryForObject("so_status_desc.query", soSubStatusID);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.getSubStatusDesc - Exception] ", ex);
			throw new DataServiceException("Error getting substatus", ex);
		}
	}
	public String getSubStatusDesc(String soID) throws DataServiceException {
		try {
			return (String) queryForObject("so_status_desc.query", soID);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.getSubStatusDesc - Exception] ", ex);
			throw new DataServiceException("Error getting substatus", ex);
		}
	}	
	
	/*-----------------------------------------------------------------
	 * Used to List problem types
	 * @param 		wfStateId - State of Service Order
	 * @returns 	ProblemLookupVO ArrayList
	 *-----------------------------------------------------------------*/
	public ArrayList<ProblemLookupVO> queryListSoProblemType(int wfStateId)
			throws DataServiceException {
		logger
				.debug("----Start of ServiceOrderDaoImpl.queryListSoProblemType----");
		ArrayList<ProblemLookupVO> problemLookupList;
		try {
			problemLookupList = (ArrayList<ProblemLookupVO>) queryForList(
					"so_problem_lookup.query", wfStateId);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.queryListSoProblemType - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger
				.debug("----End of ServiceOrderDaoImpl.queryListSoProblemType----");
		return problemLookupList;
	}

	/*-----------------------------------------------------------------
	 * This is common method which will be used to add problem 
	 * or resolution
	 * @param 		So - ServiceOrder Object with necessary information
	 * @returns 	ProblemResolutionSoVO
	 *-----------------------------------------------------------------*/
	public int reportProblemResolution(ServiceOrder so)
			throws DataServiceException {
		logger
				.debug("----Start of ServiceOrderDaoImpl.reportProblemResolution----");
		int result = 0;
		try {
			result = update("so.reportProblemResolution", so);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.reportProblemResolution - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger
				.debug("----End of ServiceOrderDaoImpl.reportProblemResolution----");
		return result;
	}

	/*-----------------------------------------------------------------
	 * Get the problem details to display on resolution screen
	 * @param 		soId - ServiceOrder Id
	 * @returns 	ProblemResolutionSoVO
	 *-----------------------------------------------------------------*/
	public ProblemResolutionSoVO getProblemDesc(String soId)
			throws DataServiceException {
		logger.info("----Start of ServiceOrderDaoImpl.getProblemDesc----");
		ProblemResolutionSoVO pbResolutionVo = null;
		try {
			pbResolutionVo = (ProblemResolutionSoVO) queryForObject(
					"so.pbdesc", soId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryBuyerDetail - Exception]"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.info("----Start of ServiceOrderDaoImpl.getProblemDesc----");
		return pbResolutionVo;
	}

	public int createConditionalOffer(RoutedProvider routedProvider)
			throws DataServiceException {
		int result = 0;
		result = update("so.createConditionalOffer", routedProvider);
		try {
			this.updateReasonCdForCounterOffer(routedProvider);
		} catch (DataServiceException dse) {
			result = 0;	
			logger.error("Error occured in ServiceOrderDaoImpl.createConditionalOffer()",dse);
		}	
		return result;
	}

	/**
	 * Updates counter offer reason code for the counter offer made
	 * @param conditionalOffer
	 * @throws BusinessServiceException 
	 * @throws BusinessServiceException
	 */
	private void updateReasonCdForCounterOffer(RoutedProvider conditionalOffer) throws DataServiceException  {
		List<Integer> counterOfferReasonCds = conditionalOffer.getSelectedCounterOfferReasonsList();
		if (null != counterOfferReasonCds) {
			//Delete if counter offer reasons entry exists
			this.deleteReasonCdForCounterOffer(conditionalOffer);
			for (Integer reasonId : counterOfferReasonCds) {
				conditionalOffer.setSelectedCounterOfferReasonId(reasonId);
				//Inserts provider counter offer reasons for the SO
				this.insertReasonCdForCounterOffer(conditionalOffer);
			}	
		}
	}
	
	public int updateSODateTime(ServiceOrderRescheduleVO reschedule)
			throws DataServiceException {
		return update("so.updateSODateTime", reschedule);
	}

	public ServiceOrderRescheduleVO getRescheduleRequestInfo(String soId)
			throws DataServiceException {
		return (ServiceOrderRescheduleVO) queryForObject(
				"so.querySODateTimeRequest", soId);
	}
	
	public String getAssignmentType(String soId)throws DataServiceException{
		return (String) queryForObject(
				"so.assignmentType", soId);
	}

	public int updateCancelReschedule(String soId) throws DataServiceException {
		return update("so.updateCancelReschedule", soId);
	}

	public int updateSODateTimeFinal(ServiceOrderRescheduleVO reschedule)
			throws DataServiceException {
		return update("so.updateSODateTimeFinal", reschedule);
	}

	public int deleteSO(String soId) throws DataServiceException {
		return delete("so.deleteSOById", soId);
	}

	public int deleteLogEntry(String soId) throws DataServiceException {
		return delete("so.deleteLogEntry", soId);
	}

	public int updateProviderResponseConditionalOffer(
			RoutedProvider routedProvider) {
		int result = 0;
		result =  update("withdrawConditionalAcceptance.update", routedProvider);
		try {
			this.deleteReasonCdForCounterOffer(routedProvider);
		} catch (DataServiceException dse) {
			result = 0;	
			logger.error("Error occured in ServiceOrderDaoImpl.updateProviderResponseConditionalOffer()",dse);
		}
		return result;
	}
	
	public int updateProvRespGroupedConditionalOffer(
			RoutedProvider routedProvider) {
		return update("withdrawGroupedConditionalAcceptance.update", routedProvider);
	}

	public synchronized Integer insertRoutedResources(List<RoutedProvider> routedResources)
			throws DataServiceException {
		logger.debug("----Start of ServiceOrderDaoImpl.insertRoutedResources()----");
		int insertCounter = 0;
		try {
			Iterator<RoutedProvider> routedProviderItr = routedResources.iterator();
			RoutedProvider routedProvider = null;

			while (routedProviderItr.hasNext()) {
				routedProvider = (RoutedProvider) routedProviderItr.next();
				int count = update("sowroutedproviders.update", routedProvider);
				if (count == 0) {
					insert("soroutedproviders.insert", routedProvider);
				}
			}

		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.insertRoutedResources - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger.debug("----End of ServiceOrderDaoImpl.insertRoutedResources()----");
		return insertCounter;
	}

	public Integer updateTotalOrdersComplete(Integer buyerID,
			Integer vendorResourceId) throws DataServiceException {
		logger
				.debug("----Start of ServiceOrderDaoImpl.updateTotalOrderComplete()----");
		int updateCount = 0;
		HashMap map = new HashMap();
		try {
			/* updating this information through a Quartz job*/
			//updateCount += update("updateBuyerTotalOrderComplete.update",
				//	buyerID);
			updateCount += update(
					"updateVendorResourceTotalOrderComplete.update",
					vendorResourceId);

			map.put("buyerID", buyerID);
			map.put("vendorResourceId", vendorResourceId);

			int cnt = (Integer) queryForObject("checkIfAlreadyRated.query", map);

			if (cnt == 0)
				insert("insertBuyerVendorResourceTotalOrderComplete.insert",
						map);
			else
				updateCount += update(
						"updateBuyerVendorResourceTotalOrderComplete.update",
						map);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.updateTotalOrderComplete - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		logger
				.debug("----End of ServiceOrderDaoImpl.updateTotalOrderComplete()----");
		return updateCount;
	}

	public int updateSOSubStatus(String serviceOrderId, Integer subStatusId)
			throws DataServiceException {
		HashMap<String, Object> hmSOSubStatus = new HashMap<String, Object>();
		hmSOSubStatus.put("soId", serviceOrderId);
		hmSOSubStatus.put("subStatusId", subStatusId);

		return update("so.updateSubStatus", hmSOSubStatus);

	}
	
	public int updateSOCustomReference(String serviceOrderId, String referenceType, String referenceValue)
			throws DataServiceException {
		HashMap<String, Object> hmSOCustomReference = new HashMap<String, Object>();
		hmSOCustomReference.put("soId", serviceOrderId);
		hmSOCustomReference.put("referenceType", referenceType);
		hmSOCustomReference.put("referenceValue", referenceValue);

		return update("so.updateCustomReference", hmSOCustomReference);

}

	public int updateDocSizeTotal(String soId, long size)
			throws DataServiceException {
		ServiceOrder so = new ServiceOrder();
		so.setSoId(soId);
		so.setDocSizeTotal(size);
		return update("so.updateDocSizeTotal", so);
	}


	
	public void updateServiceOrder(ServiceOrder serviceOrder)
			throws DataServiceException {
		try {
			String soId = serviceOrder.getSoId();

			delete("sowpart.delete", serviceOrder);
			if (serviceOrder.getParts() != null) {
				List<Part> part = serviceOrder.getParts();
				for (int i = 0; i < part.size(); i++) {
					if (part.get(i).getPickupLocation()!=null && part.get(i).getPickupLocation().getLocationId() != null
							&& part.get(i).getPickupContact()!=null && part.get(i).getPickupContact().getContactId() != null) {
						int locId = part.get(i).getPickupLocation()
								.getLocationId();
						int contId = part.get(i).getPickupContact()
								.getContactId();
						part.get(i).setSoId(serviceOrder.getSoId());
						part.get(i).getPickupLocation().setLocationId(null);
						part.get(i).getPickupContact().setContactId(null);
						 update("sowpart.update", part.get(i));

						part.get(i).getPickupLocation().setLocationId(locId);
						part.get(i).getPickupContact().setContactId(contId);
					} else {
						part.get(i).setSoId(serviceOrder.getSoId());
					update("sowpart.update", part.get(i));

					}
				}

			}
			
	        if(null== serviceOrder.getBuyer()||null== serviceOrder.getBuyer().getRoleId()||OrderConstants.SIMPLE_BUYER_ROLEID!= serviceOrder.getBuyer().getRoleId()){
	        	delete("soroutedproviders.delete", serviceOrder);
	        }
			delete("so.deleteContactLocnBySoId", soId);
			delete("so.deleteLocationBySoId", soId);
			delete("so.deleteContactPhoneBySoId", soId);
			delete("so.deleteContactBySoId", soId);
			//delete("so.deleteNotesBySoId", soId);
			delete("so.deleteSurveyResponseBySoId", soId);
			delete("so.deleteEventsBySoId", soId);

			//Remove newline character from the text entered by user in textarea boxes (Special Instructions, Task Comments,
			//Buyer Terms and Conditions, Overview) at the time of creating or editing a Service Order 
			removeNewlineCharacters(serviceOrder);
			
			insertServiceOrderAsscoiates(serviceOrder);
			Double tempSpendLimitLabor = serviceOrder.getSpendLimitLabor(); 
			Double tempSpendLimitParts = serviceOrder.getSpendLimitParts();
			//Used for updating initial posted labor and parts spend limits if already populated
			ServiceOrder newServiceOrder = new ServiceOrder();
			newServiceOrder = getInitialPostedSOPrices(serviceOrder);
			// Data mining for individual orders, not for the child order while editing
			if (StringUtils.isBlank(serviceOrder.getGroupId()) && serviceOrder.isUpdateSoPriceFlag()) {
				delete("so.deleteSoPrice", soId);
				insertSOPrice(serviceOrder);  // data mining
				if(null!=newServiceOrder.getInitialPostedLaborSpendLimit() && null!= newServiceOrder.getInitialPostedPartsSpendLimit()){
					serviceOrder.setInitialPostedLaborSpendLimit(newServiceOrder.getInitialPostedLaborSpendLimit());
					serviceOrder.setInitialPostedPartsSpendLimit(newServiceOrder.getInitialPostedPartsSpendLimit());
					updateInitialPostedSOPrice(serviceOrder);
				}
			}
			else if (!serviceOrder.isUpdateSoPriceFlag())
			{
				serviceOrder.setSpendLimitLabor(null);
				serviceOrder.setSpendLimitParts(null);
			}
			serviceOrder.setSpendLimitLabor(tempSpendLimitLabor);
			serviceOrder.setSpendLimitParts(tempSpendLimitParts);
			updateSoHeader(serviceOrder);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.deleteServiceOrder - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public void updateLockEditMode(String soId, Integer EditMode)
			throws DataServiceException {
		ServiceOrder so = new ServiceOrder();
		so.setSoId(soId);
		so.setLoctEditInd(EditMode);
		update("so.updateLockEditMode", so);
	}

	public void updateSoHeader(ServiceOrder serviceOrder) {
		if (serviceOrder.getPrimarySkillCatId() != null 
				&& serviceOrder.getPrimarySkillCatId() < 0) {
			serviceOrder.setPrimarySkillCatId(null);
		}
		if (serviceOrder.getLastChngStateId() != null
				&& serviceOrder.getLastChngStateId() == 0) {
			serviceOrder.setLastChngStateId(null);
		}
		// hack here to set the service date type field correctly in the 
		// database
		if (serviceOrder.getServiceDate2() != null && serviceOrder.getServiceTimeEnd() != null)
			serviceOrder.setServiceDateTypeId(Integer.valueOf(OrderConstants.RANGE_DATE));
		else
			serviceOrder.setServiceDateTypeId(Integer.valueOf(OrderConstants.FIXED_DATE));
		
		update("so.update", serviceOrder);
	}

	public void updateSoHdrLogoDocument(String soId, Integer logoDocumentId)
			throws DataServiceException {
		ServiceOrder so = new ServiceOrder();
		so.setSoId(soId);
		so.setLogoDocumentId(logoDocumentId);
		update("so.updateLogoDocument", so);
	}

	  public int updateSoHdrForReleaseSO(ServiceOrder so) throws DataServiceException{
		  return update ("so.releaseSO", so);  
	  }
	  
	  public int updateRoutedProviderRecords(RoutedProvider routedProvider) throws DataServiceException{
		  String soId = routedProvider.getSoId();
		  ServiceOrderContact soc = (ServiceOrderContact)queryForObject("so.selectContactInfo", soId);
		  // deletes snapshot info for provider for this service order
		  if (soc != null) {
				delete("so.deleteSOContactLocation", soc);
				delete("so.deleteSOContactPhone", soc);
				delete("so.deleteSOContact", soc);
				delete("so.deleteSOLocation", soc);
		  }
		  // updates the routed providers table 
		  return update("so.releaseSOroutedProvider", routedProvider);
	  }
	  
	  /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#getRoutedProviders(java.lang.String)
	 */
	public List<RoutedProvider> getRoutedProviders(String soId)  throws DataServiceException{
		  return (ArrayList<RoutedProvider>)queryForList("routedresource.query", soId);
	  }
	  
	  /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#getRoutedProvidersWithBasicInfo(java.lang.String)
	 */
	public List<RoutedProvider> getRoutedProvidersWithBasicInfo(String soId)
		throws DataServiceException {
		  return (ArrayList<RoutedProvider>)queryForList("routedProvidersWithBasicInfo.query", soId);
	}
	
	public RescheduleVO getRescheduleInfo(String soId)
			throws DataServiceException {
			  return ((RescheduleVO)queryForObject("rescheduleInfo.query", soId));
		}
	
	public RescheduleVO getBuyerRescheduleInfo(String soId) throws DataServiceException {
			  return ((RescheduleVO)queryForObject("buyerRescheduleInfo.query", soId));
		}
	  

	  /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#getMarketMakerProvidersResponse(java.lang.String, java.util.List)
	 */
	public List<MarketMakerProviderResponse> getMarketMakerProvidersResponse(String soId, List<Integer> resourceIds)
		throws DataServiceException {
		  Map<String,Object> paramMap = new HashMap<String, Object>();
			try { 
				paramMap.put("soId", soId);
				paramMap.put("resourceIds",resourceIds);
				return (ArrayList<MarketMakerProviderResponse>)queryForList("marketMakerProvidersResponse.query", paramMap);
				
			} catch (Exception ex) {
				throw new DataServiceException("Error getting Market Maker Provider Response.", ex);
			}		
	  }
	  
	  /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#getPostedSoCountForVendors(java.util.List)
	 */
	public Map<Integer, Integer> getPostedSoCountForVendors(List<Integer> vendorIds) throws DataServiceException {
			if(vendorIds == null || vendorIds.isEmpty()) {
				return new HashMap<Integer, Integer>();
			}

			try {
				return getSqlMapClient().queryForMap("routedSoCount.query",
						vendorIds, "vendor_id", "routed_so_count");
			} catch (Exception ex) {
				throw new DataServiceException("Error getting SO count for the vendors.", ex);
			}
	  }
	  
	  public List<LookupVO> getCallStatusList()
	  {
		  //List<LookupVO> al = null;
		  return  queryForList("callstatuslist.query", null);
		  //return (HashMap)getSqlMapClientTemplate().queryForMap("callstatuslist.query",null,"callStatusId","callStatusDesc");
	  }
	  
	  public String deleteServiceOrder(String soId ,String groupId) throws DataServiceException {
			Map<String,Object> paramMap = new HashMap<String, Object>();
			try { 
				paramMap.put("soId", soId);
				paramMap.put("deletedDate", new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				paramMap.put("groupId", groupId);
				paramMap.put("status",OrderConstants.DELETED_STATUS);
				paramMap.put("subStatus",null);
				int i = update("so.deleteServiceOrder", paramMap);
				if(i > 0)
					return "true";
				else
					return "false";
			} catch (Exception ex) {
				logger.error("Exception caught inside ServiceOrderDaoImpl.deleteServiceOrder():", ex);
				throw new DataServiceException("ServiceOrderDaoImpl --> updateServiceOrderCancellationFee", ex);
			}		
		}
	
	public Buyer getBuyerAttributes(Integer buyerId) throws DataServiceException{
		Buyer buyer = new Buyer();
		try {
			buyer = (Buyer) queryForObject("sobuyer.query", buyerId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.getBuyerFundingType - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return buyer;
	}
	
	public void deleteOldServiceOrders(Integer numberDaysOld) {
		int i = update("so.deleteServiceOrder", numberDaysOld);
	}
	
	/**
	 * Method to remove newline character '\n' from the user typed text 
	 * @param serviceOrder
	 */
	public void removeNewlineCharacters(ServiceOrder serviceOrder) {
		//Remove Special characters (newline character) from contents of 
		//Special Instructions, Buyer Terms and Conditions, Overview and Task Comments
		//entered on 1st page in SOW.
		//Task Comments
		List<ServiceOrderTask> tasks = serviceOrder.getTasks();
		for(ServiceOrderTask task : tasks) {
			String comments = task.getTaskComments();
			task.setTaskComments(comments != null ? comments.trim().replaceAll("\\n", "") : comments );
		}
		//Special Instructions
		String specialInstructions = serviceOrder.getProviderInstructions();
		serviceOrder.setProviderInstructions(specialInstructions != null ? specialInstructions.trim().replaceAll("\\n", "") : specialInstructions);
		
		//Overview
		String overview = serviceOrder.getSowDs();
		serviceOrder.setSowDs(overview != null ? overview.trim().replaceAll("\\n", "") : overview);
		
		//Buyer Terms and Conditions
		String buyerTermCond = serviceOrder.getBuyerTermsCond();
		serviceOrder.setBuyerTermsCond(buyerTermCond != null ? buyerTermCond.trim().replaceAll("\\n", "") : buyerTermCond);
	}
	
	public Integer getMainServiceCategoryCount(Integer mainCategoryId) throws DataServiceException {
		try {
			return (Integer) queryForObject("count.primarycategory", mainCategoryId);
		} catch (Exception ex) {
			logger
					.info("[LookupDaoImpl.getMainServiceCategoryCount - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public int updatePartsSuppier(ServiceOrder so) throws DataServiceException {
		try { 
			return update("soPartsSupplier.update", so);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.update - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public Integer checkForValidServiceOrderID(String so_id) {
		try {
			return (Integer) queryForObject("count.serviceorder", so_id);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return new Integer(0);
	}
	
	public boolean checkIfResourceAcceptedServiceOrder(Integer resource_id, String so_id) {
		try {
			Integer resourceId = (Integer) queryForObject("query.acceptedResourceId", so_id);
			if(resourceId == null)
				return false;
			if(resourceId.intValue() == resource_id.intValue())
				return true;
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return false;
	}

	public Date getServiceOrderModifiedDate(String serviceOrderID) throws DataServiceException {
		try {
			return (java.util.Date) queryForObject("so.modfied_date.query", serviceOrderID);
		} catch (Exception ex) {
			logger
					.info("[LookupDaoImpl.getServiceOrderModifiedDate - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}


	/**
	 * Verify a resource is related to a service order by taking the last 6 digits(123456)
	 * of a service order and performing a like in the where clause with accepted_resource_id.
	 * @param resource_id
	 * @param last6SoId
	 * 
	 */
	public List<String> findValidServiceOrders(Integer resource_id, String last6SoId) {
		//check for length 6
		if (StringUtils.isEmpty(last6SoId) || last6SoId.length() != 6) {
			return null;
		}
		//convert the string into a like for service order
		char[] nums = last6SoId.toCharArray();
		last6SoId = "%-" + nums[0] + nums[1] + nums[2] + nums[3] + "-" + nums[4] + nums[5];
		Map paramMap = new HashMap<String, String>();
		paramMap.put("so_id", last6SoId);
		paramMap.put("accepted_resource_id", resource_id.toString());
		return (List<String>) queryForList("query.findValidServiceOrders", paramMap);
	}
	public String getServiceLocationTimeZone(String serviceLocationZipCode){
		return (String) queryForObject("zipCode.query", serviceLocationZipCode);
	}

	public int updateServiceOrderCancellationFee(String soId, Double cancellationFee) throws DataServiceException {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		try { 
			paramMap.put("soId", soId);
			paramMap.put("cancellationFee", cancellationFee);
			return update("soCancellationFee.update", paramMap);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl --> updateServiceOrderCancellationFee - Exception] "
					+ ex.getMessage());
			throw new DataServiceException("ServiceOrderDaoImpl --> updateServiceOrderCancellationFee", ex);
		}
	}
	
	public ArrayList<Contact> getRoutedResources(String soId,String companyId) throws DataServiceException
	{
		ArrayList<Contact> routedResourcesList = null;
		Map<String, String> map = new HashMap<String, String>();
			try {
				map.put("soId", soId);
				map.put("companyId", companyId);
				routedResourcesList = (ArrayList<Contact>) queryForList(
						"soRoutedResources.queryList", map);
			} catch (Exception ex) {
				logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
			return routedResourcesList;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#getRoutedResourceIds(java.lang.String)
	 */
	public List<RoutedProviderResponseVO> getRoutedResourcesResponseInfo(String soId) throws DataServiceException
	{
		List<RoutedProviderResponseVO> routedResources = null;
		try {
			routedResources = (ArrayList<RoutedProviderResponseVO>) queryForList(
						"soRoutedResourceIds.queryList", soId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return routedResources;
	}
	
	public int soContactTableUpdate(SoLoggingVo soLogging) throws DataServiceException
	{
		int updateSOContactStatus;
		try {
			updateSOContactStatus =  update("saveReassignSOContact.update",soLogging);
		}
		catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.saveReassignSO - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return updateSOContactStatus;
	}
	public int soRoutedProviderUpdate(SoLoggingVo soLogging) throws DataServiceException {
		int updateSoRoutedProviderRespId;
		int updateSoRoutedProviderRespIdNull;
		try{
			//This will update provider_resp_id as null for previously accepted resources
			updateSoRoutedProviderRespIdNull=update("saveReassignSoRoutedProvidersNull.update",soLogging);
			//This will update newly assigned resource details
			updateSoRoutedProviderRespId=update("saveReassignSoRoutedProviders.update",soLogging);
		}
		catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.saveReassignSO - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
			
		}
		return updateSoRoutedProviderRespId;
	}
	public int soHdrTableUpdate(SoLoggingVo soLogging) throws DataServiceException
	{
			Map map = new HashMap();
			int updateSOHdrStatus;
			try {
			map.put("soId", soLogging.getServiceOrderNo());
			map.put("resourceId", soLogging.getNewValue());
			map.put("modifiedDate",soLogging.getModifiedDate());
			updateSOHdrStatus = update("saveReassignSOHdr.update",map);
			//update so_routed_providers
			update("orderManagement.updateRoutedRes", map);
			update("orderManagement.updateRoutedResource", map);
			}
			catch (Exception ex) {
				logger.info("[ServiceOrderDaoImpl.saveReassignSO - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
		return updateSOHdrStatus;
	}
	
	public void soNotesInsert(ServiceOrderNote soNote) throws DataServiceException
	{
		try
		{
			//Long noteMax = (Long) queryForObject("maxNotesId.query", null);
			//soNote.setNoteId(noteMax);
			insert("note.insert", soNote);
		}
		catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.saveReassignSO - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	
	public void soLoggingInsert(SoLoggingVo soLogging) throws DataServiceException
	{
		try
		{	
			insert("saveReassignSO.insert", soLogging);
		}
		catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.saveReassignSO - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	/**
	 * The custom reference value must be flagged in the database thru the buyer_reference table
	 * as the service identifier (so_identifier = 1)
	 */
	public ServiceOrder getServiceOrder(String externalID, Integer buyerID) throws DataServiceException {
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("buyer_ref_value", externalID);
		paramMap.put("buyer_id", Integer.toString(buyerID.intValue()));
		
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = (ServiceOrder) queryForObject("query.findByReference", paramMap);
		} catch (Exception ex) {
			String message = "Data error while querying latest service order for given external service order id:";
			logger.error(message, ex);
			throw new DataServiceException(message, ex);
		}
		
		return serviceOrder;
	}
	
	public ServiceOrder getServiceOrder(String referenceType, String referenceValue, Integer buyerID) throws DataServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyer_ref_value", referenceValue);
		paramMap.put("ref_type", referenceType);
		paramMap.put("buyer_id", buyerID);
		String soID = (String) queryForObject("query.findByReferenceValue", paramMap);
		
		if (null != soID) {
			serviceOrder.setSoId(soID);
			try {
				serviceOrder = (ServiceOrder) queryForObject("so.query", serviceOrder);
			} catch (Exception ex) {
				logger.info("[ServiceOrderDaoImpl.query - Exception] ", ex);
				throw new DataServiceException("Error", ex);
			}
		}
		else {
			serviceOrder = null;
		}
		return serviceOrder;
	}

	public int updateServiceLocation(String soID, SoLocation location) {
		return update("solocation.update", location);
	}
	
	public int updateProviderInstructions(String soID, String newinst) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("newinst", newinst);
		param.put("soID", soID);
		return update("soProviderInstr.update", param);
	}

	public int deleteTask(ServiceOrderTask task) {
		return delete("sotask.delete", task.getTaskId());
	}
	
	public void insertCustomRef(ServiceOrderCustomRefVO ref) {
		insert("socustomRef.insert", ref);
	}

	public int updateServiceOrderContact(String soId, Contact serviceContact) {
		int response = update("socontact.update", serviceContact);
		if(serviceContact.getPhones() != null && serviceContact.getPhones().size() > 0)
		{
			delete("so.deleteContactPhoneByContactId", serviceContact.getContactId());			
			for (PhoneVO phone : serviceContact.getPhones())
			{
				insert("contactPhone.insert", phone);
			}
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#deleteCustomRefByBuyerRefID(com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO)
	 */
	public void deleteCustomRefByBuyerRefID(ServiceOrderCustomRefVO custRefVO) throws DataServiceException {
		try {
			int recordsDeleted = delete("soRefDeleteByTypeID.delete", custRefVO);
			logger.info("Total ["+recordsDeleted+"] reference fields deleted for refTypeId=["+custRefVO.getRefTypeId()+"] and soId=["+custRefVO.getsoId()+"]");
		} catch (Exception ex) {
			String strMessage = "Unexpected exception occured while deleting reference fields for refTypeId=["+custRefVO.getRefTypeId()+"] and soId=["+custRefVO.getsoId()+"]";
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
	}

	public void deleteContactLocation(String soID, Integer contactId, Integer locationId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("locationId", Integer.toString(locationId.intValue()));
		param.put("contactId", Integer.toString(contactId.intValue()));
		param.put("soID", soID);
		delete("soContactLocation.delete", param);
	}

	public void deletePartPickupLocation(String soID, Integer locationId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("locationId", Integer.toString(locationId.intValue()));
		param.put("soID", soID);
		delete("soPartPickupLocation.delete", param);
	}
	/**
	 * This method is used to delete the so_contact_phones data 
	 * for a particular soID and so_contact_id.
	 * @param soID
	 * @param contactId
	 */
	public void deletePartPickupPhones(String soID, Integer contactId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("contactId", Integer.toString(contactId.intValue()));
		param.put("soID", soID);
		delete("soPartPickupPhone.delete", param);
	}

	public void deletePartPickupContact(String soID, Integer contactId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("contactId", Integer.toString(contactId.intValue()));
		param.put("soID", soID);
		delete("soPartPickupContact.delete", param);
	}

	public void deleteRoutedProviders(String soId) {
		delete("soRoutedProvidersNoResourceCheck.delete", soId);
	}	
	
	/**
	 * Updates the final labor price value in so_hdr with the value of cancellation fee
	 * @param soID
	 * @param cancellationFee
	 * 
	 */
	public void updateFinalLaborPrice(String soID,Double cancellationFee) throws DataServiceException {
		try
		{			
			Map<String, String> param = new HashMap<String, String>();
			param.put("cancellationFee", cancellationFee.toString());
			param.put("soID", soID);
			update("so.updateLaborFinalPrice", param);			
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.updateFinalLaborPrice - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}	
	}

	public void insertSoCustomReference(ServiceOrderCustomRefVO soRefVO) {
		try {
			insert("soCustRef.insert", soRefVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @soCustRef.insert() due to"
					+ ex.getMessage());
		}
	}

	public int updateSoCustomReference(ServiceOrderCustomRefVO socrVo)
			throws DataServiceException {
		int updates = 0;
		try {
			updates = update("soCustRef.update", socrVo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @soCustRef.insert() due to"
					+ ex.getMessage());
			throw new DataServiceException("Error", ex);
		}
		return updates;
	}
	
	public int updateMktMakerComments(MarketMakerProviderResponse mktMakerProvResp) throws DataServiceException
	{
		int updates = 0;
		try {
			updates = update("mktmakercomments.insert", mktMakerProvResp);
		} catch (Exception ex) {
			throw new DataServiceException("Error", ex);
		}
		return updates;
		
	}
	
	public ServiceOrderPriceVO getSoPrice(String soId)throws DataServiceException {
		ServiceOrderPriceVO soPrice = new ServiceOrderPriceVO();
		try{
			soPrice = (ServiceOrderPriceVO)queryForObject("soPrice.query", soId); 	
		}catch(Exception ex){
			logger.error("Unexpected data exception while retrieving So Price for SoID in ServiceOrderDaoImpl.getSoPrice", ex);
			throw new DataServiceException("Unexpected data exception while retrieving So Price for SoID in ServiceOrderDaoImpl.getSoPrice", ex);
		}
		return soPrice;
	}

	public List<ServiceOrderCustomRefVO> getCustomReferenceFields(String soId)
			throws DataServiceException {
		List<ServiceOrderCustomRefVO> customRefs = null;
		try {
			customRefs = queryForList("customReferences.query", soId);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving custom refs for so:"+soId;
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
		return customRefs;
	}
	
	public InitialPriceDetailsVO getInitialPrice(String soId) throws DataServiceException{
		InitialPriceDetailsVO initialPrice=new InitialPriceDetailsVO();
		try {
			initialPrice = (InitialPriceDetailsVO)queryForObject("getInitialPrice.query", soId);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving Initial price for so:"+soId;
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
		return initialPrice;
	}

	public ServiceOrderCustomRefVO getCustomReferenceObject(String customRefId,
			String soId) throws DataServiceException {
		Map paramMap = new HashMap<String, String>();
		paramMap.put("soId", soId);
		paramMap.put("customRefKey", customRefId);
		ServiceOrderCustomRefVO customRef = new ServiceOrderCustomRefVO();
		try {
			customRef = (ServiceOrderCustomRefVO)queryForObject("customReferenceObject.query", paramMap);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving custom refs for so:"+soId+", customRefKey:" + customRefId;
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
		return customRef;
	}
	
	public ServiceOrderCustomRefVO getCustomReferenceValue(String customRefId,
			String soId) throws DataServiceException {
		Map paramMap = new HashMap<String, String>();
		paramMap.put("soId", soId);
		paramMap.put("customRefKey", customRefId);
		ServiceOrderCustomRefVO customRef = new ServiceOrderCustomRefVO();
		try {
			customRef = (ServiceOrderCustomRefVO)queryForObject("getCustomReference.query", paramMap);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving custom refs for so:"+soId+", customRefKey:" + customRefId;
			logger.error(strMessage, ex);
			throw new DataServiceException(strMessage, ex);
		}
		return customRef;
	}
	
	public int updateSOPartsShippingInfo(List<Part> parts) throws DataServiceException {
		int intUpdPartCnt = 0;
		try {
			for (int pi = 0; pi < parts.size(); pi++) {
				Part part = parts.get(pi);
				if (part != null) {
					intUpdPartCnt = update("partshipinfo.update", part);
				}
			}
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.partshipinfo - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	return intUpdPartCnt;
		
	}
	
	public List<ServiceOrderCustomRefVO>  getCustomReferenceList(String customRefType, String customRefValue, String soId, List statusIds) throws DataServiceException{
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("customRefType", customRefType);
		paramMap.put("customRefValue", customRefValue);
		paramMap.put("soId", soId);
		paramMap.put("statusIds", statusIds);
		List<ServiceOrderCustomRefVO> customRefs = null; 
		try{
			customRefs = queryForList("customReferenceList.query", paramMap);
		} catch(Exception e){
			String message = new String("Error on  getCustomReferenceList for referneceType = " + customRefType + ", customRefValue = " + customRefValue + ", soId = " + soId );
			logger.error(message, e); 
			throw new DataServiceException(message, e);
		}
		return customRefs;
	}
	
	public ServiceOrder getServiceOrderStatusAndCompletedDate(String serviceOrderID) throws DataServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(serviceOrderID);
		try {
			serviceOrder = (ServiceOrder) queryForObject("so.statusquery", serviceOrder);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return serviceOrder;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#updateSowDs(java.lang.String, java.lang.String)
	 */
	public int updateSowDs(String soId, String newDescription)
			throws DataServiceException {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("newDs", newDescription);
			param.put("soId", soId);
			return update("soSowDs.update", param);
		} catch (Exception e) {
			throw new DataServiceException("Problem updating the sowDs with new description: " +
					newDescription, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#updateSowTitle(java.lang.String, java.lang.String)
	 */
	public int updateSowTitle(String soId, String newTitle)
			throws DataServiceException {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("newTitle", newTitle);
			param.put("soId", soId);
			return update("soSowTitle.update", param);
		} catch (Exception e) {
			throw new DataServiceException("Problem updating the sow Title with new title: " +
					newTitle, e);
		}
	}
	
	/**
	 * Returns the associated incidents
	 * @param soId
	 * @return List<AssociatedIncidentVO> associatedIncidents
	 * @throws BusinessServiceException
	 */
	public List<AssociatedIncidentVO> getAssociatedIncidents(String soId) throws DataServiceException {		
		List<AssociatedIncidentVO> associatedIncidents = new ArrayList<AssociatedIncidentVO>();		
		try {
			associatedIncidents = (ArrayList<AssociatedIncidentVO>)queryForList("so.queryAssociatedIncidents.storeproc", soId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.getAssociatedIncidents - Exception] " + ex.getMessage());
			throw new DataServiceException("Error", ex);
		}
		return associatedIncidents;
	}

	public void insertSoDocument(SoDocumentVO soDocVO) {
		insert("so.document.insert", soDocVO);
	}

	/**
	 * Calls a stored procedure which creates staging data if no staging data is available.
	 * @param soId
	 * @throws DataServiceException
	 */
	public void stageShcOrder(String soId)throws DataServiceException{
		try {
			insert("spStageShcOrder.storeproc", soId);			
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.stageShcOrder - Exception] " + ex.getMessage());
			throw new DataServiceException("Error", ex);
		}
	}
	/*
	 * This method retrieves all the service providers names under a firm.
	 * @param resourceId	
	 * @return ArrayList
	 * @throws BusinessServiceException
	 * 
	 */
	public ArrayList<ProviderDetail> queryServiceProviders(Integer resourceId)
			throws BusinessServiceException {
		ArrayList<ProviderDetail> providersList;
		try {
			providersList = (ArrayList<ProviderDetail>) queryForList(
					"service_providers_name.query", resourceId);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.queryServiceProviders - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new BusinessServiceException("Error", ex);
		}

		return providersList;

	}
	/*
	 * This method retrieves all the Market names under a firm.
	 * @param resourceId	
	 * @return ArrayList
	 * @throws BusinessServiceException
	 * 
	 */
	public ArrayList<ProviderDetail> queryMarketNames(Integer resourceId)
			throws BusinessServiceException {
		ArrayList<ProviderDetail> providersList;
		try {
			providersList = (ArrayList<ProviderDetail>) queryForList(
					"service_markets_name.query", resourceId);
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.queryServiceProviders - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new BusinessServiceException("Error", ex);
		}

		return providersList;

	}	

	/**
	 * Returns the list of service orders which satisfies the Search criteria specified in SearchRequestVO searchRequest
	 * @param searchRequest SearchRequestVO
	 * @return serviceOrderSearchResults List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSet(
			SearchRequestVO searchRequest) throws DataServiceException {
		List<ServiceOrder> serviceOrderSearchResults = new ArrayList<ServiceOrder>();
		List<String> customRefSoIds = new ArrayList<String>();
		List<String> soIdList = null;
		try {
			List<ServiceOrderCustomRefVO> customRefsList = searchRequest.getCustomRefs();
			if(customRefsList != null && customRefsList.size() > 0 ) {
				for (ServiceOrderCustomRefVO customRef:customRefsList) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("customRefType", customRef.getRefType());
					param.put("customRefValue", customRef.getRefValue());
					soIdList = (List<String>)queryForList("so.customRefSoIdList",param);
					if(soIdList != null && soIdList.size() > 0) {
						if(customRefSoIds.size()>0) {
							customRefSoIds = ListUtils.intersection(customRefSoIds, soIdList);
						} else {
							customRefSoIds = soIdList;
						}						
					}						
				}
				if(customRefSoIds.size() == 0) {
					customRefSoIds.add("");
				}
				searchRequest.setCustomRefSoIds(customRefSoIds);
			}
			serviceOrderSearchResults = (List<ServiceOrder>)queryForList("so.searchSO",searchRequest);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.getSearchResultSet - Exception] " + ex.getMessage());
			throw new DataServiceException("Error", ex);
		}
		return serviceOrderSearchResults;
	}
	/**
	 * Returns the list of service orders which satisfies the Search criteria specified in SearchRequestVO searchRequest
	 * @param searchRequest SearchRequestVO
	 * @return serviceOrderSearchResults List<ServiceOrder>
	 * 
	 */
	public List<ServiceOrder> getSearchResultSetPaged(
			SearchRequestVO searchRequest) throws DataServiceException {
		List<ServiceOrder> serviceOrderSearchResults = new ArrayList<ServiceOrder>();
		List<String> customRefSoIds = new ArrayList<String>();
		List<String> soIdList = null;
		try {
			List<ServiceOrderCustomRefVO> customRefsList = searchRequest.getCustomRefs();
			if(customRefsList != null && customRefsList.size() > 0 ) {
				for (ServiceOrderCustomRefVO customRef:customRefsList) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("customRefType", customRef.getRefType());
					param.put("customRefValue", customRef.getRefValue());
					soIdList = (List<String>)queryForList("so.customRefSoIdList",param);
					if(soIdList != null && soIdList.size() > 0) {
						if(customRefSoIds.size()>0) {
							customRefSoIds = ListUtils.intersection(customRefSoIds, soIdList);
						} else {
							customRefSoIds = soIdList;
						}						
					}						
				}
				if(customRefSoIds.size() == 0) {
					customRefSoIds.add("");
				}
				searchRequest.setCustomRefSoIds(customRefSoIds);
			}
			serviceOrderSearchResults = (List<ServiceOrder>)queryForList("so.searchSOPaged",searchRequest);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.getSearchResultSet - Exception] " + ex.getMessage());
			throw new DataServiceException("Error", ex);
		}
		return serviceOrderSearchResults;
	}
	
	/**
	 * Returns the list of language Ids corresponding to language names
	 * @param languageNames List<String>
	 * @return languageId List<Integer>
	 * 
	 */
	public List<Integer> getLanguageIds(List<String> languageNames) {
		List<Integer> languageId = null;
		ProviderLanguageVO providerLanguageVO=new ProviderLanguageVO();
		providerLanguageVO.setLanguageNames(languageNames);
		try {
			languageId = (List<Integer>)queryForList("so.selectlanguage",providerLanguageVO);
			
		
		} catch (Exception e) {
			logger.error("error occurred while getting language Ids.");

		}
		logger.info("INSIDE ServiceOrderDaoImpl ----> getLanguageIds() Ends");
		return languageId;

	}
	/**
	 * This method inserts the initial_posted_labor_spend_limit and initial_posted_parts_spend_limit
	 * of service order while its Posted 
	 * @return integer 
	 * throws DataServiceException
	 */	
	public int updateInitialPostedSOPrice(ServiceOrder serviceOrder)throws DataServiceException{
		return update("so.updateInitalPostedPriceSO", serviceOrder);
	}
	/**
	 * This method gets the initial_posted_labor_spend_limit and initial_posted_parts_spend_limit
	 * of service order while its Posted 
	 * @return integer 
	 * throws DataServiceException
	 */
	public ServiceOrder getInitialPostedSOPrices(ServiceOrder serviceOrder){
		ServiceOrder newServiceOrder = new ServiceOrder();
		String soId = serviceOrder.getSoId();
		try{
			newServiceOrder = (ServiceOrder)queryForObject("so.getInitalPostedPricesSO", soId);
		}catch(Exception e){
			logger.error("Error occured in getInitialPostedSOPrices : " ,e);
		}
		return newServiceOrder;
	}
 
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#updateRoutedDateForProviders(java.lang.String)
	 */
	public void updateRoutedDateForProviders(String soId) throws DataServiceException {
		try{
			Map paramMap = new HashMap<String, Object>();
			Calendar calendar = Calendar.getInstance();
			Timestamp routedDateTimeStamp = new Timestamp(calendar.getTimeInMillis());
			paramMap.put("soId", soId);
			paramMap.put("routedDate", routedDateTimeStamp);
			update("soRoutedProviders.routedDate.update", paramMap);
		}catch(Exception e){
			logger.error("Error occured in updateRoutedDateForProviders : " ,e);
			throw new DataServiceException("error ocurred while updated routed date for routed providers", e);
		}
		
	}

	public Date getRoutedDateForResourceId(String soId, Integer resourceId) throws DataServiceException {
 		Date routedDate = null;
		try{
			Map paramMap = new HashMap<String, Object>();
			paramMap.put("soId", soId);
			paramMap.put("resourceId", resourceId);
			routedDate = (Date)queryForObject("select.soRoutedProvider.routedDate", paramMap);
		}catch(Exception e){
			logger.error("Error occured in getRoutedDateForResourceId : " ,e);
			throw new DataServiceException("error ocurred while get routed date for routed providers", e);
		}
		return routedDate;
	}
	
	public Date getRoutedDateForFirm(String soId, Integer vendorId) throws DataServiceException {
 		Date routedDate = null;
		try{
			Map paramMap = new HashMap<String, Object>();
			paramMap.put("soId", soId);
			paramMap.put("vendorId", vendorId);
			routedDate = (Date)queryForObject("select.soRoutedProvider.routedDateForFirmAccept", paramMap);
		}catch(Exception e){
			logger.error("Error occured in getRoutedDateForFirm : " ,e);
			throw new DataServiceException("error ocurred while get routed date for firm ", e);
		}
		return routedDate;
	}
	
	
	/**
	 * This method gets the Substatus id for the given substatus description
	 * @param subStatus,status
	 * @return Integer 
	 * throws DataServiceException
	 */
	public Integer getSubStatusId(String subStatus,int status) throws DataServiceException {
		try {
			Integer result=0;
			Map<String, Object>param = new HashMap<String, Object>();
			param.put("subStatus", subStatus);
			param.put("status", status);
			result= (Integer) queryForObject("so_substatus_id.query", param);
			return result;
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.getSubStatusId - Exception] ", ex);
			throw new DataServiceException("Error getting substatusId", ex);
		}
	}
	
	/**
	 * Method gets the reasons list for the selected counter offer condition
	 * @param providerRespId
	 * @return List<CounterOfferReasonsVO>
	 * @throws DataServiceException
	 */
	public List<CounterOfferReasonsVO> getReasonsForSelectedCounterOffer(int providerRespId) throws DataServiceException{
		List<CounterOfferReasonsVO> counterOfferReasonsList = null;
		try{
			counterOfferReasonsList =  queryForList("getReasonsForCounterOffer.query", providerRespId);
		}catch(Exception ex){
			logger.error("Error occured while getting selected counter offer reasons list in ServiceOrderDaoImpl.getReasonsForSelectedCounterOffer()", ex);
			throw new DataServiceException("Error occured while getting selected counter offer reasons list in ServiceOrderDaoImpl.getReasonsForSelectedCounterOffer()",ex);
		}
		return counterOfferReasonsList;
	}
	
	/**
	 * Method to insert the counter offer reason code
	 * @param conditionalOffer
	 * @throws DataServiceException
	 */
	public void insertReasonCdForCounterOffer(RoutedProvider conditionalOffer) throws DataServiceException{	
		try{
			insert("so.insertReasonCdForCounterOffer", conditionalOffer);
		}catch(Exception ex){
			logger.error("Error occured while inserting selected counter offer reasons list in ServiceOrderDaoImpl.insertReasonCdForCounterOffer()",ex);
			throw new DataServiceException("Error occured while inserting selected counter offer reasons list in ServiceOrderDaoImpl.insertReasonCdForCounterOffer()",ex);
		}
	}

	/**
	 * Method to delete the existing counter offer reason code
	 * @param conditionalOffer
	 * @return int 
	 * @throws DataServiceException
	 */
	public int deleteReasonCdForCounterOffer(RoutedProvider conditionalOffer)throws DataServiceException{
		int deleteCount = 0;
		try{
			deleteCount = delete("so.deleteReasonCdForCounterOffer", conditionalOffer);
		}catch(Exception ex){
			logger.error("Error occured while deleting selected counter offer reasons list in ServiceOrderDaoImpl.insertReasonCdForCounterOffer()",ex);
			throw new DataServiceException("Error occured while inserting selected counter offer reasons list in ServiceOrderDaoImpl.insertReasonCdForCounterOffer()",ex);
		}
		return deleteCount;			
	}
	/**
	 * Method fetches unit no, order no, buyer id and so contact phone no for a given SO ID
	 * @param String soId
	 * @return String
	 * @throws BusinessServiceException
	 */
	public ServiceOrderIvrDetailsVO getSoCustomReferencesWS(String soId)throws DataServiceException{
		ServiceOrderIvrDetailsVO ivrDetails = new ServiceOrderIvrDetailsVO();
		try{
			ivrDetails = (ServiceOrderIvrDetailsVO)queryForObject("so.getSoCustomReferencesWS",soId);
		}catch(Exception ex){
			throw new DataServiceException("Error occured while retrieving IVR details: ",ex);
		}		
		return ivrDetails;
	}
	  /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao#getRoutedProviders(java.lang.String)
	 */
	public ArrayList<LookupVO> getSubstatusDesc(List<Integer> id)  throws DataServiceException{
		ArrayList<LookupVO> codeList=null;
		try{
			codeList=(ArrayList<LookupVO>)queryForList("subStatusDesc.query", id);
		}catch(Exception ex){
			throw new DataServiceException("Error occured while retrieving Substatus description: ",ex);
} 
		
		  return codeList;
	  }
	
	public ArrayList<LookupVO> getRescheduleReasonCodes(Integer roleId)  throws DataServiceException{
		ArrayList<LookupVO> codeList=null;
		try{
			codeList=(ArrayList<LookupVO>)queryForList("rescheduleReasonCodes.query", roleId);
		}catch(Exception ex){
			throw new DataServiceException("Error occured while retrieving reschedule reasons: ",ex);
		}
		return codeList;
	}
	
	public String getServiceLocTimeZone_soHdr(String serviceOrderID) throws DataServiceException 
	{
		try {
			return (String) queryForObject("so.service_locn_time_zone.query", serviceOrderID);
			} 
		catch (Exception ex) 
		{
		logger.info("[serviceOrderDaoImpl.getServiceLocTimeZone - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
		throw new DataServiceException("Error", ex);
		}
	}

	
	
	public ArrayList<LookupVO> getPermitTypes()  throws DataServiceException{
		ArrayList<LookupVO> permitTypesList=null;
		try{
			permitTypesList=(ArrayList<LookupVO>)queryForList("permitTypes.query");
		}catch(Exception ex){
			throw new DataServiceException("Error occured while retrieving permit types : ",ex);
		} 
		
		  return permitTypesList;
	  }
	
	/**
	 * This method gets the routed providers for a specific SO and provider firm
	 * @param String soId, String vendorId, String resourceId, Boolean manageSoFlag
	 * @return List <ProviderResultVO> 
	 * throws DataServiceException
	 */
	public List <ProviderResultVO> getRoutedResourcesForFirm(String soId, String vendorId, String resourceId, Boolean manageSOFlag) 
		throws DataServiceException {
		
		List <ProviderResultVO> providerList = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("vendorId", vendorId);
			if (resourceId != null ) param.put("resourceId", resourceId);
			param.put("manageSOFlag", manageSOFlag);
			providerList = (List <ProviderResultVO>) queryForList("som.availableProvidersFirm", param);
			
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.getRoutedResourcesForFirm - Exception] ", ex);
			throw new DataServiceException("Error getting routed resources for firm", ex);
		}
		
		return providerList;
	}
	/**
	 * This method gets the routed providers for a Group SO and provider firm
	 * @param String soId, String vendorId, String resourceId, Boolean manageSoFlag
	 * @return List <ProviderResultVO> 
	 * throws DataServiceException
	 */
	public List <ProviderResultVO> getRoutedResourcesListForFirmForGroup(String soId, String vendorId, String resourceId, Boolean manageSOFlag, ServiceOrder serviceOrder) 
		throws DataServiceException {
		
		List <ProviderResultVO> providerList = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("vendorId", vendorId);
			if (resourceId != null ) param.put("resourceId", resourceId);
			param.put("manageSOFlag", manageSOFlag);
			param.put("groupId", serviceOrder.getGroupId());
			providerList = (List <ProviderResultVO>) queryForList("som.availableProvidersFirmForGroup", param);
			
		} catch (Exception ex) {
			logger.error("[ServiceOrderDaoImpl.getRoutedResourcesListForFirmForGroup - Exception] ", ex);
			throw new DataServiceException("Error getting routed resources for firm", ex);
		}
		
		return providerList;
	}
	
	
	
	
	public List<PendingCancelHistoryVO> getPendingCancelHistory(String soId)
			throws DataServiceException {
		
		List<PendingCancelHistoryVO> pendingCancelHistoryVO=null;
		try {
			pendingCancelHistoryVO= (List<PendingCancelHistoryVO>) queryForList("soPendingCancelHistory.query", soId);
			} 
		catch (Exception ex) 
		{
		logger.info("[serviceOrderDaoImpl.getPendingCancelHistory - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
		throw new DataServiceException("Error", ex);
	
		}
		return pendingCancelHistoryVO;
		
	}
	
	public PendingCancelPriceVO getPendingCancelBuyerDetails(String soId) throws DataServiceException
	{
		PendingCancelPriceVO priceVO=null;
		try {
			priceVO= (PendingCancelPriceVO) queryForObject("soBuyerPendingCancelPrice.query", soId);
			} 
		catch (Exception ex) 
		{
		logger.info("[serviceOrderDaoImpl.getPendingCancelBuyerDetails - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
		throw new DataServiceException("Error", ex);
	
		}
		return priceVO;
		
	}
	
	public PendingCancelPriceVO getPendingCancelBuyerPriceDetails(String soId) throws DataServiceException
	{
		PendingCancelPriceVO priceVO=null;
		try {
			priceVO= (PendingCancelPriceVO) queryForObject("soBuyerPrevPendingCancelPrice.query", soId);
			} 
		catch (Exception ex) 
		{
		logger.info("[serviceOrderDaoImpl.getPendingCancelBuyerPrevPriceDetails - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
		throw new DataServiceException("Error", ex);
	
		}
		return priceVO;
		
	}
	
	
	public PendingCancelPriceVO getPendingCancelProviderDetails(String soId) throws DataServiceException
	{
		PendingCancelPriceVO priceVO=null;
		try {
			priceVO= (PendingCancelPriceVO) queryForObject("soProviderPendingCancelPrice.query", soId);
			} 
		catch (Exception ex) 
		{
		logger.info("[serviceOrderDaoImpl.getPendingCancelProviderDetails - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
		throw new DataServiceException("Error", ex);
	
		}
		return priceVO;
		
	}
	

public Double getBuyerMaxTransactionLimit(Integer resourceId,Integer buyerId) throws DataServiceException {
		
		Double maxTransactionLimit  = 0.0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resourceId", resourceId);
		param.put("buyerId", buyerId);
		try {
			maxTransactionLimit = (Double) queryForObject("buyerTransactionLimit.query", param);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return maxTransactionLimit;
	}

	/**
	 * Get the response based on the response filter
	 * @param serviceOrderID
	 *  @param responseFilters
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrder(String serviceOrderID,List<String> responseFilters) throws DataServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(serviceOrderID);
		try {
			// Order Status 
			// General
			serviceOrder = (ServiceOrder) queryForObject("so.getReducedQuery", serviceOrder);
			
			// Scope of work
			if(responseFilters.contains(OrderConstants.SCOPEOFWORK)){
				List<ServiceOrderTask> tasks=new ArrayList<ServiceOrderTask>();				
				tasks= (List<ServiceOrderTask>) queryForList("getTasks.query", serviceOrderID);				
				serviceOrder.setTasks(tasks);				
			}
			
			//getdateTimeslots
			
			List<ServiceDatetimeSlot> serviceDatetimeSlots=new ArrayList<ServiceDatetimeSlot>();
			serviceDatetimeSlots= (List<ServiceDatetimeSlot>) queryForList("getServiceDatetimeSlots.query", serviceOrderID);
			serviceOrder.setServiceDatetimeSlots(serviceDatetimeSlots);
			
			
			// Contact
			if (responseFilters.contains(OrderConstants.CONTACTS)) {
				
				// Service contact
				Contact serviceContact = new Contact();
				serviceContact = (Contact) queryForObject("getServicecontact.query", serviceOrderID);
				serviceOrder.setServiceContact(serviceContact);
				
				// Alternate Service contact
				Contact altServiceContact = new Contact();
				altServiceContact = (Contact) queryForObject("getServicecontactalt.query", serviceOrderID);
				serviceOrder.setAltServiceContact(altServiceContact);
				
				// End user contact
				Contact endUserContact = new Contact();
				endUserContact = (Contact) queryForObject("getEndusercontact.query", serviceOrderID);
				serviceOrder.setEndUserContact(endUserContact);
				
				// Alternate end user contact
				Contact altEndUserContact = new Contact();
				altEndUserContact = (Contact) queryForObject("getAltEndusercontact.query", serviceOrderID);
				serviceOrder.setEndUserContact(altEndUserContact);
				
				// Alternate buyer resource contact
				Integer buyerContactId = serviceOrder.getBuyerContactId();
				if(null!=buyerContactId){
					BuyerResource altBuyerresource = new BuyerResource();
					altBuyerresource = (BuyerResource) queryForObject("getSOaltbuyerresource.query", buyerContactId);
					serviceOrder.setAltBuyerResource(altBuyerresource);
				}
				
				// Vendor resource contact
				Contact vendorResourceContact = new Contact();
				vendorResourceContact = (Contact) queryForObject("getVendorResourcecontact.query", serviceOrderID);
				serviceOrder.setVendorResourceContact(vendorResourceContact);
				
				// Service provider contact  
				ProviderContactVO providerContactVO = new ProviderContactVO();
				providerContactVO = (ProviderContactVO) queryForObject("getServiceprovider.telephonenumbers.query", serviceOrderID);
				serviceOrder.setServiceproviderContactOnQuickLinks(providerContactVO);
				
				// Parts pick up contact -- This will be handled along with the parts query
				
			}
			
			//ServiceLocation
			if (responseFilters.contains(OrderConstants.SERVICELOCATION)) {
				SoLocation serviceLocation= new SoLocation();
				serviceLocation = (SoLocation) queryForObject("getServicelocation.query", serviceOrderID);
				serviceOrder.setServiceLocation(serviceLocation);
			}
			
			// Documents
			if (responseFilters.contains(OrderConstants.ATTACHMENTS)) {
				List<SODocument> documents =new ArrayList<SODocument>();
				documents= (List<SODocument>) queryForList("getDocuments.query", serviceOrderID);
				serviceOrder.setSoDocuments(documents);
			}
			
			// Parts
			if (responseFilters.contains(OrderConstants.PARTS) || 
					responseFilters.contains(OrderConstants.CONTACTS)) {
				List<Part> soParts =new ArrayList<Part>();
				soParts= (List<Part>) queryForList("getParts.query", serviceOrderID);
				serviceOrder.setParts(soParts);
			}
			
			// Custom references
			if (responseFilters.contains(OrderConstants.CUSTOMREFERENCES)) {
				List<ServiceOrderCustomRefVO> soCustomReferences =new ArrayList<ServiceOrderCustomRefVO>();
				soCustomReferences = (List<ServiceOrderCustomRefVO>) queryForList("getCustomReferences.query", serviceOrderID);
				serviceOrder.setCustomRefs(soCustomReferences);
			}
			
			// Notes
			if (responseFilters.contains(OrderConstants.NOTES)) {
				List<ServiceOrderNote> soNotes =new ArrayList<ServiceOrderNote>();
				soNotes = (List<ServiceOrderNote>) queryForList("getNotes.query", serviceOrderID);
				serviceOrder.setSoNotes(soNotes);
			}
			// History -- It is done by another query in the mapper class
			
			// Routed providers
			if (responseFilters.contains(OrderConstants.ROUTEDPROVIDERS)) {
				List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
				routedResources = (List<RoutedProvider>) queryForList("getRoutedresource.query", serviceOrderID);
				serviceOrder.setRoutedResources(routedResources);
				   //Get the accepted vendor details:It will be present in the response if we use API v1.3 
					FirmContact acceptedVendor = new FirmContact();
					acceptedVendor = (FirmContact)queryForObject("getAcceptedVendor.query",serviceOrderID);
					 if(null!=acceptedVendor && null != acceptedVendor.getVendorId() ){
						 serviceOrder.setFirmContact(acceptedVendor);
					     serviceOrder.setAcceptedVendorId(acceptedVendor.getVendorId());
					 }
				
				//Get the accepted resource details
				Integer acceptedResourceId = serviceOrder.getAcceptedResourceId();
				if(null!=acceptedResourceId){
					VendorResource acceptedResource = new VendorResource();
					acceptedResource = (VendorResource) queryForObject("getAcceptedresource.query", acceptedResourceId);
					serviceOrder.setAcceptedResource(acceptedResource);
				}
			}
			
			//Reason codes
			if(responseFilters.contains(OrderConstants.FETCHREASONCODES)){
				List<LookupVO> fetchReasonCodes=new ArrayList<LookupVO>();
				fetchReasonCodes= (List<LookupVO>) queryForList("getReasoncodes.query");
				serviceOrder.setReasonCodes(fetchReasonCodes);
			}
			
			if(responseFilters.contains(OrderConstants.FETCH_PRECALL_REASONCODES)){
				List<LookupVO> customerResponseCodes=new ArrayList<LookupVO>();
				customerResponseCodes= (List<LookupVO>) queryForList("getCustResponseCodes.query");
				serviceOrder.setCustomerResponseCodes(customerResponseCodes);
				List<LookupVO> preCallReasonCodes=new ArrayList<LookupVO>();
				preCallReasonCodes= (List<LookupVO>) queryForList("getPreCallReasonCodes.query");
				serviceOrder.setPreCallReasonCodes(preCallReasonCodes);
			}
			
			//B2C Changes: To fetch estimation details
			if(responseFilters.contains(OrderConstants.ESTIMATE)){
				List<EstimateVO> fetchEstimates =new ArrayList<EstimateVO>();
				fetchEstimates= (List<EstimateVO>) queryForList("getEstimates.query",serviceOrderID);
				serviceOrder.setEstimateList(fetchEstimates);
			}

			//B2C Changes: To fetch review details
			if(responseFilters.contains(OrderConstants.REVIEW)){
				List<ReviewVO> reviewList = null;
				reviewList = (List<ReviewVO>)queryForList("getReview.query", serviceOrderID);
				serviceOrder.setReview(reviewList);
			}
			
			if(responseFilters.contains(OrderConstants.ADDONS)){
				List<ServiceOrderAddonVO> addonList = null;
				addonList = (List<ServiceOrderAddonVO>)queryForList("upsellAddons.query", serviceOrderID);
				serviceOrder.setUpsellInfo(addonList);
			}
			
			//jobcodes
			if(responseFilters.contains(OrderConstants.JOBCODES)){
				List<JobCodeVO> jobCode =null;
				jobCode= (List<JobCodeVO>)queryForList("getJobCodeId.query",serviceOrderID);
				serviceOrder.setJobCodes(jobCode);		
			}
			//paymentDetails
			if(responseFilters.contains(OrderConstants.PAYMENTDETAILS)){
				PaymentDetailsVO paymentDetails=new PaymentDetailsVO();
				paymentDetails=(PaymentDetailsVO)queryForObject("getPaymentDetails.query",serviceOrderID);
				serviceOrder.setPaymentDetails(paymentDetails);
			}
			//invoiceParts
			if(responseFilters.contains(OrderConstants.INVOICEPARTS)){
				List<PartDatasVO> invoiceParts=null;
				invoiceParts= (List<PartDatasVO>)queryForList("getinvoicedetails.query",serviceOrderID);
				serviceOrder.setInvoicePartsdata(invoiceParts);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return serviceOrder;
	}
	/**
	 * Get the response for spendLimitIncrease 
	 * based on the response 
	 * @param serviceOrderID
	 * @param responseForSpendLimit
	 * @return
	 * @throws DataServiceException
	 */
    public HashMap<String,ServiceOrderSpendLimitHistoryVO>  getSpendLimitIncreaseForAPI(List<String>soIdList) throws DataServiceException{
    	 List<ServiceOrderSpendLimitHistoryVO> spendLimitHistory=new ArrayList<ServiceOrderSpendLimitHistoryVO>();
    	 HashMap historyVOMap=new HashMap<String,ServiceOrderSpendLimitHistoryVO >();
    	 HashMap soIdListMap = new HashMap();
    	 soIdListMap.put("soIdList", soIdList);
    	 spendLimitHistory= queryForList("so.getSpendLimitHistory.query",soIdListMap);
    	      for(ServiceOrderSpendLimitHistoryVO history:spendLimitHistory){
    	    	  if(null!=history.getSoId()){
    	    		  historyVOMap.put(history.getSoId(),history);
    	    	  }
    	      }
    	      return historyVOMap;
	}
	/**
	 * Get the service order with a reduced set of result map
	 * @param serviceOrderID
	 * @return
	 * @throws DataServiceException
	 */	
	public ServiceOrder getServiceOrderForAPI(String serviceOrderID) throws DataServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(serviceOrderID);
		try {
			serviceOrder = (ServiceOrder) queryForObject("so.getQuery", serviceOrder);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return serviceOrder;
	}

	public void  updateSkuIndicator(String soId) 
	{
		try {
			 update("skuIndicator.update", soId);
			logger.info("The sku indicator is updated in the table for so id#" + soId);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			
		}
		
	}
	//This  method to fetch the sku indicator column of  so_workflow_controls table to identify the type of  serive order
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soId)
	{
		Boolean fetchSkuIndicator=false;
		try {
			fetchSkuIndicator=(Boolean) queryForObject("skuIndicator.fetch",soId);
			if(fetchSkuIndicator==null)
			{
				fetchSkuIndicator=false;
			}
		} catch (Exception e) {
			logger.error("Exception :" + e);
			
		}
		return fetchSkuIndicator;
	}
	public List<Integer> getRoutedProvidersForFirm(String soId,Integer vendorId)
			throws DataServiceException {
		List<Integer> providers = new ArrayList<Integer>();
		try{
		HashMap input = new HashMap();
		input.put("soId", soId);
		input.put("vendorId", vendorId);
		providers = (ArrayList<Integer>)queryForList("routedProvidersForFirm.query", input);
		}catch(Exception e){
			throw new DataServiceException("exception in getRoutedProvidersForFirm() of ServiceOrderDaoImpl",e);
		}
		return providers;
	}
	
	//SL-15642: check whether so is car routed
	public boolean isCARroutedSO(String soId) throws DataServiceException{
		boolean  isCARroutedSO = false;
		try{		
			Integer count = (Integer)queryForObject("assocCarSo.query", soId);
			if(count > 0){
				isCARroutedSO = true;
			}			
		}catch(Exception e){
			throw new DataServiceException("exception in isCARroutedSO() of ServiceOrderDaoImpl",e);
		}
		return isCARroutedSO;
	}
	public boolean isAuthorizedToViewSODetls(String soId,String providerId) throws DataServiceException{
		// TODO Auto-generated method stub
		boolean isAuthorized =  Boolean.FALSE;
		int count = 0;
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("vendorId", providerId);
			count = (Integer)queryForObject("so.isProviderAuthToViewSO.query", param);
			if(count > 0){
				isAuthorized = true;
			}
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.isAuthorizedToViewSODetls() due to "+e.getMessage());
		}
		return isAuthorized;
	}
	
	public boolean isAuthorizedToViewGroupSODetls(String groupId,String providerId) throws DataServiceException{
		// TODO Auto-generated method stub
		boolean isAuthorized =  Boolean.FALSE;
		int count = 0;
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", groupId);
			param.put("vendorId", providerId);
			count = (Integer)queryForObject("so.isProviderAuthToViewGroupSO.query", param);
			if(count > 0){
				isAuthorized = true;
			}
		}catch(Exception e){
			logger.error("Exception occured in OrderManagementDaoImpl.isAuthorizedToViewGroupSODetls() due to "+e.getMessage());
		}
		return isAuthorized;
	}

	
	/**
	 * SL-18007: Get the so price details response based on the infoLevel requested
	 * @param serviceOrderID
	 * @param infoLevel (0=Current price details, 1=so level price history, 2=task level price history)
	 * @return ServiceOrder
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrderPriceDetails(ServiceOrder serviceOrder,Integer infolevel) throws DataServiceException {
		try {
			
			//SO Level Price History
			if (OrderConstants.SO_LEVEL_PRICE_HISTORY.equals(infolevel)){
				List<SoPriceChangeHistory> soPriceChangeHistoryList= new ArrayList<SoPriceChangeHistory>();
				soPriceChangeHistoryList = (List<SoPriceChangeHistory>) queryForList("getSOLevelPriceHistory.query", serviceOrder.getSoId());
				serviceOrder.setSoPriceChangeHistoryList(soPriceChangeHistoryList);
			}else if (OrderConstants.TASK_LEVEL_PRICE_HISTORY.equals(infolevel)) {  //Task Level Price History
				
				// Get both service order level and task level
				List<SoPriceChangeHistory> soPriceChangeHistoryList= new ArrayList<SoPriceChangeHistory>();
				soPriceChangeHistoryList = (List<SoPriceChangeHistory>) queryForList("getSOLevelPriceHistory.query", serviceOrder.getSoId());
				serviceOrder.setSoPriceChangeHistoryList(soPriceChangeHistoryList);
				
				List<ServiceOrderTask> serviceOrderTaskList= new ArrayList<ServiceOrderTask>();
				serviceOrderTaskList = (List<ServiceOrderTask>) queryForList("getTaskLevelPriceHistory.query", serviceOrder.getSoId());
				serviceOrder.setTasks(serviceOrderTaskList);
			}
			
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return serviceOrder;
	}
	
	/**
	 * SL-15642: Get vendor business name
	 * @param vendorId
	 * @return businessName
	 * @throws DataServiceException
	 */
	public String getVendorBusinessName(Integer vendorId)throws DataServiceException{
		String businessName = null;
		try {
			businessName = (String)queryForObject("getVendorBusinessName.query", vendorId);
		} catch (Exception e) {
			logger.error("Exception :" + e);
		}
		return businessName;
	}
	/**
	 * SL-15642: Get reschedule reason descr
	 * @param reasonCode
	 * @return businessName
	 * @throws DataServiceException
	 */
	public String getRescheduleReason(Integer reasonCode)throws DataServiceException{
		String businessName = null;
		try {
			businessName = (String)queryForObject("getRescheduleReason.query", reasonCode);
		} catch (Exception e) {
			logger.error("Exception :" + e);
		}
		return businessName;
	}
//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
	public String getMethodOfRouting(String soId)throws DataServiceException
	{
		String methodOfRouting=null;
		try {
			methodOfRouting =(String)queryForObject("getMethodOfRouting.query", soId);
			logger.info("Method of routing for so"+soId+"is"+methodOfRouting);

			}catch (Exception e) {
				logger.info("Method of routing for so"+soId+"is"+methodOfRouting);
				logger.error("Exception :" + e);
			}
		return methodOfRouting;
		
	}
	
	//Method to fetch schedule history
	public List<PreCallHistory> getScheduleHistory(String soId,
			Integer acceptedVendorId)
	{
		List<PreCallHistory> callHistory =null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("vendorId", acceptedVendorId);
			callHistory =(List<PreCallHistory>)queryForList("scheduleHistory.query", param);

			}catch (Exception e) {
				logger.error("Exception :" + e);
			}
		return callHistory;
		
	}
			
			
			//to check whether SO is tier routed
			public boolean checkTierRoute(String soId){
				Integer tierRoute = 0;
				boolean tierRouteInd = false;
				try{
					tierRoute = (Integer)queryForObject("checkTierRoute.query", soId);
					if(1 == tierRoute.intValue()){
						tierRouteInd = true;
					}
					
				}catch (Exception e) {
					logger.error("Exception in ServiceOrderDaoImpl.checkTierRoute due to:" + e.getMessage());
				}
				return tierRouteInd;
			}
	/**
	 * Get accepted firms details
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */		
	public ProviderFirmVO getAcceptedFirmDetails(Integer vendorId) throws DataServiceException {
		ProviderFirmVO providerFirmVO = new ProviderFirmVO();
		try {
			providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetails.query", vendorId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return providerFirmVO;
	}
	public ProviderFirmVO getAcceptedFirmDetailsSoId(String soId) throws DataServiceException {
		ProviderFirmVO providerFirmVO = new ProviderFirmVO();
		try {
			providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetailsSoId.query", soId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return providerFirmVO;
	}
	public List<ServiceOrderTask> getActiveTasks(String soId) {
		List<ServiceOrderTask> tasks=new ArrayList<ServiceOrderTask>();
		tasks= (List<ServiceOrderTask>) queryForList("getTasks.query", soId);
		return tasks;
	}

	public Integer getCountOfMobileSignatureDocuments(String soId)
			throws DataServiceException {
		try {
			return (Integer) queryForObject("mobile.so.signature.documentCount", soId);
		} catch (Exception ex) {
			logger
					.info("[ServiceOrderDaoImpl.getCountOfMobileSignatureDocuments - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public void deleteSODocumentMapping(DocumentVO documentVO)
			throws DataServiceException {
		delete("mobile.so.documentMapping.delete", documentVO);		
	}

	public void updatePDFBatchParamaters(DocumentVO documentVO)
			throws DataServiceException {
		update("mobile.so.pdf.batch.parameterUpdate", documentVO);		
	}

	public Integer getSignaturePDFDocument(DocumentVO documentVO)
			throws DataServiceException {
		try {
			Integer documentId=(Integer) queryForObject("mobile.so.signature.documentQuery", documentVO);
			return documentId;
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.getSignaturePDFDocument - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	public Integer getBuyerIdForSo(String soid) throws DataServiceException {
		Integer buyerId=null;
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soid);
			buyerId=(Integer) queryForObject("getBuyerIdForSo.query", parameter);
		}catch(Exception e){
			logger.error("Exception in getting buyer id for the so"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return buyerId;
	}
	
	//For checking Non Funded feature for an SO

	public boolean checkNonFunded(String soId){
		Integer nonFunded = 0;
		boolean nonFundedInd = false;
		try{
			nonFunded = (Integer)queryForObject("checkNonFunded.query", soId);
			if(1 == nonFunded.intValue()){
				nonFundedInd = true;
			}
			
		}catch (Exception e) {
			logger.error("Exception in ServiceOrderDaoImpl.checkNonFunded due to:" + e.getMessage());
		}
		return nonFundedInd;
	}
	
	
	
	 
	
	public boolean isNonFundedBuyer(Integer buyerId) throws DataServiceException {
		boolean isNonFunded = false;
		try{
			HashMap params = new HashMap();
			params.put("buyerID",buyerId);
			params.put("feature","NON_FUNDED");
			String feature = (String)queryForObject("buyerFeatuerSet.getFeature",params);
			if(null != feature){
				isNonFunded = true;
			}
		}catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.isNonFundedBuyer due to:" + e);
			throw new DataServiceException(e.getMessage());
		}
		return isNonFunded;
	}
	
	
	
	//SL-19050
	//marking note as Read
	public void markSOAsRead(String noteId)
			throws DataServiceException {
		try{
			update("so.updatenoteAsRead", noteId);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.markSOAsRead due to:" + e);
			throw new DataServiceException(e.getMessage());
		}
	}
	
		//SL-19050
		//marking note as UnRead
		public void markSOAsUnRead(String noteId)
				throws DataServiceException {
			try{
				update("so.updatenoteAsUnRead", noteId);
			}
			catch(Exception e){
				logger.error("Exception in ServiceOrderDaoImpl.markSOAsUnRead due to:" + e);
				throw new DataServiceException(e.getMessage());
			}
		}
		
		/**
		* get closure method
		* @param soId
		* @return
		* @throws DataServiceException
		*/
		public String getMethodOfClosure(String soId) throws DataServiceException {
			try{
				return (String)queryForObject("so.getMethodOfClosure", soId);
			}
			catch(Exception e){
				logger.error("Exception in ServiceOrderDaoImpl.getMethodOfClosure due to:" + e);
				throw new DataServiceException(e.getMessage());
			}
		}

		public boolean loadSOMswitch() throws DataServiceException{
			boolean loadSOMSwitch = true;
		 try{
			
			String value = (String)getSqlMapClient().queryForObject("loadSOMSwitch.query",null);		
			if("off".equalsIgnoreCase(value)){
			return false;
			}
			
		}catch(SQLException e){
			logger.info("Exception in getting loadSOMSwitch"+ e);
		}
		return loadSOMSwitch;
		
		}
		
		/**SL-20400 ->To find out the duplicate service orders with the given unit number and 
		order number combination in ServiceLive database for InHome Buyer (3000). */	
		public ServiceOrder getDuplicateSOInHome(String customUnitNumber,String customOrderNumber)
				throws DataServiceException {
			ServiceOrder serviceOrder = null;
			try {
				HashMap params = new HashMap();
				params.put("customUnitNumber",customUnitNumber);
				params.put("customOrderNumber",customOrderNumber);
				serviceOrder = (ServiceOrder)queryForObject("duplicateSOInHome.query",params);
			} catch (Exception ex) {
				String strMessage = "Unexpected error while retrieving Duplicate SO for InHome";
				logger.error(strMessage, ex);
				throw new DataServiceException(strMessage, ex);
			}
			return serviceOrder;
		}

		/**
		 * Used to log request and response Duplicate SO's for InHome Buyer (3000).
		 * @param String request,String response, String buyerId
		 * @return Integer
		 */	
		public Integer logDuplicateSORequestResponse(String request,String response, Integer buyerId,String apiName)throws DataServiceException {
			Integer loggingId =null;
			try{
				HashMap params = new HashMap();
				params.put("apiName",apiName);
				params.put("request",request);
				params.put("response",response);
				params.put("buyerId",buyerId);
				loggingId = (Integer) insert("logDuplicateSORequestResponse.insert", params);
			}catch (Exception e) {
				String strMessage = "Unexpected error in logDuplicateSORequestResponse";
				logger.error(strMessage, e);
				throw new DataServiceException(strMessage, e);
			}
			
			return loggingId;
		}
		public SOWorkflowControlsVO getSoWorkflowControls(String soId)throws DataServiceException {
			SOWorkflowControlsVO controlsVO =null;
			try{
				controlsVO = (SOWorkflowControlsVO) queryForObject("soWrkFlowControlsOriginalOrderWarrantyProvider.query", soId);
			}catch (Exception e) {
				logger.error("Exception in fetching original order id and warranty provider for the order" + e);
				throw new DataServiceException("Exception in fetching original order id and warranty provider for the order",e);
			}
			return controlsVO;
			
		}
		

	/*	public boolean isLessThanSpendLimitLabour(String soId) throws DataServiceException{
			boolean  isLessThanSpendLimitLabour = false;
			try{		
				Integer count = (Integer)queryForObject("spendLimitLabour.query", soId);
				if(count > 0){
					isLessThanSpendLimitLabour = true;
				}			
			}catch(Exception e){
				throw new DataServiceException("exception in isCARroutedSO() of ServiceOrderDaoImpl",e);
			}
			return isLessThanSpendLimitLabour;
		}*/
		/**Priority 5B changes
		 * update invalid_model_serial_ind column in so_workflow_controls
		 * @param soId
		 * @param ind
		 * @throws BusinessServiceException
		 */
		public void updateModelSerialInd(String soId, String ind) throws DataServiceException{
			
			HashMap params = new HashMap();
			params.put("soId",soId);
			params.put("ind",ind);
			
			try{
				update("modelSerialInd.update", params);
				
			}catch (Exception e) {
				throw new DataServiceException("Exception in updateModelSerialInd() of ServiceOrderDaoImpl", e);
			}
		}
		
		/**
		 * priority 5B changes
		 * get buyer first name & last name
		 * @param buyerResId
		 * @return 
		 * @throws BusinessServiceException
		 */
		public String getBuyerName(Integer buyerResId) throws DataServiceException{
			
			try{
				
				return (String)queryForObject("getBuyerName.query", buyerResId);
				
			}catch (Exception e) {
				throw new DataServiceException("Exception in getBuyerName() of ServiceOrderDaoImpl", e);
			}
		}
		
		/**
		 * priority 5B changes
		 * get the validation rules for the fields
		 * @param fields
		 * @return List<ValidationRulesVO>
		 * @throws BusinessServiceException
		 */
		public List<ValidationRulesVO> getValidationRules(List<String> fields) throws DataServiceException{
			
			try{
					
				return (List<ValidationRulesVO>)queryForList("getValidationRules.query", fields);
				
			}catch (Exception e) {
				throw new DataServiceException("Exception in getValidationRules() of ServiceOrderDaoImpl", e);
			}
		}
		/**
		 * SL-21070
		 * Method fetches the lock_edit_ind of the so
		 * @param soId
		 * @return int lockEditInd
		 */
		public int getLockEditInd(String soId){
			Integer lockEditInd = null;
			try{
				lockEditInd =  (Integer)queryForObject("so.lockEditInd", soId);
				if(null != lockEditInd){
					return lockEditInd.intValue();
				}
			}
			catch(Exception e){
				logger.error("Exception in ServiceOrderDaoImpl.getLockEditInd due to:" + e);
			}
			return 0;
		}
		
	/** Get Estimate Details
	 * @param soId
	 * @param estimationId
	 * @return EstimateVO
	 */
	public EstimateVO getEstimate(String soId, Integer estimationId)
			throws DataServiceException {
		EstimateVO estimateVO = null;
		try{
			HashMap params = new HashMap();
			params.put("soId",soId);
			params.put("estimationId",estimationId);
			estimateVO =  (EstimateVO)queryForObject("getEstimate.query", params);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.getEstimate due to:" + e);
		}
		return estimateVO;
	}
	
	
	public EstimateVO getEstimateMainDetails(String soId, Integer estimationId) throws DataServiceException{
		
		EstimateVO estimateVO = null;
		try{
			HashMap params = new HashMap();
			params.put("soId",soId);
			params.put("estimationId",estimationId);
			estimateVO =  (EstimateVO)queryForObject("getEstimateMain.query", params);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.getEstimateMainDetails due to:" + e);
		}
		return estimateVO;
		
	}
	public void insertEstimateHistory(EstimateVO estimateVO) throws DataServiceException{
		
		try{
		insert("saveEstimationHistoryMain.insert", estimateVO);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.insertEstimateHistory due to:" + e);
		}
	
		
	}



	/**
	 * @param soId
	 * @param estimateId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateEstimate(String soId, Integer estimateId) {
		boolean isValid =  Boolean.FALSE;
		try {
			int count = 0;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("soId", soId);
			param.put("estimateId", estimateId);
			count = (Integer)queryForObject("validateEstimate.query", param);
			if(count > 0){
				isValid = true;
			}
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return isValid;
	}

	/**
	 * @param soId
	 * @param estId
	 * @param status
	 * @param comments
	 * @param source
	 * @param customerName
	 * @return
	 * @throws DataServiceException
	 */
	public void updateEstimateStatus(String soId, Integer estId, String status, String comments,String source, String modifiedBy,String customerName)throws DataServiceException{

		HashMap params = new HashMap();
		params.put("soId",soId);
		params.put("estId",estId);
		params.put("status",status);
		params.put("comments",comments);
		params.put("source",source);
		params.put("modifiedBy",modifiedBy);
		params.put("customerName",customerName);
		try{
			update("updateEstimation.update", params);
		}catch (Exception e) {
			throw new DataServiceException("Exception in updateEstimateStatus() of ServiceOrderDaoImpl", e);
		}
	}

	/**
	 * Fetch the estimation details of an SO
	 * @param soId
	 * @param vendorId
	 * @return List<EstimateVO>
	 * @throws DataServiceException
	 */
	public List<EstimateVO> getEstimationDetails(String soId,Integer vendorId) throws DataServiceException{

		List<EstimateVO> estimateDetails = null;
		HashMap params = new HashMap();
		params.put("soId",soId);
		params.put("vendorId",vendorId);
		try {
			estimateDetails = (List<EstimateVO>)queryForList("getEstimationDetails.query", params);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getEstimationDetails() of ServiceOrderDaoImpl", e);
		}
		return estimateDetails;
	}

	/** method for retrieving closed SO Details for provider.
	 * @param closedOrdersRequestVO
	 * @return List<ClosedServiceOrderVO> 
	 * @throws DataServiceException
	 */
	public List<ClosedServiceOrderVO> getClosedOrders(
			ClosedOrdersRequestVO closedOrdersRequestVO)
					throws DataServiceException {
		List<ClosedServiceOrderVO> serviceOrderResults = null;
		try {
			serviceOrderResults = (List<ClosedServiceOrderVO>)queryForList("getClosedSOProvider.query",closedOrdersRequestVO);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getClosedOrders() of ServiceOrderDaoImpl", e);
		}
		return serviceOrderResults;
	}


	/**
	 * To fetch the insurance details
	 */
	public List<LicensesAndCertVO> getVendorInsuranceDetails(List<String> firmIdList) throws DataServiceException {

		List insuranceList=null;
		try {		
			insuranceList =  getSqlMapClient().queryForList("getVendorInsuranceDetails.query", firmIdList);   

		} catch (Exception ex) {
			throw new DataServiceException(
					"General Exception @ServiceOrderDaoImpl.getVendorInsuranceDetails() due to "
							+ ex.getMessage());
		}
		return insuranceList;
	}

	/**
	 * To fetch the license details
	 */
	public List<LicensesAndCertVO> getVendorLicenseDetails(List<String> firmIdList) throws DataServiceException {

		List licenseList=null;
		try {		
			licenseList =  getSqlMapClient().queryForList("getVendorLicenseDetails.query", firmIdList);   

		} catch (Exception ex) {
			throw new DataServiceException(
					"General Exception @ServiceOrderDaoImpl.getVendorLicenseDetails() due to "
							+ ex.getMessage());
		}
		return licenseList;
	}


	/**
	 * To fetch the last closed service order for a firm
	 */
	public List<LastClosedOrderVO> getLastClosedOrder(List<String> firmIdList) throws DataServiceException {

		List<LastClosedOrderVO> lastClosedOrderList=null;
		try {	

			lastClosedOrderList = getSqlMapClient().queryForList("getLastClosedOrder.query", firmIdList);

		} catch (Exception ex) {
			throw new DataServiceException(
					"General Exception @ServiceOrderDaoImpl.getLastClosedOrder() due to "
							+ ex.getMessage());
		}
		return lastClosedOrderList;
	}
	/**
	 * method to fetch the valid firms to validate the user entered firmIds
	 */
	public List<String> getValidProviderFirms(List<String> firmIds) throws DataServiceException {
		List<String> validFirms = null;
		try {
			validFirms = (List<String>)queryForList("getValidFirmId.query", firmIds);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getValidProviderFirms() of ServiceOrderDaoImpl", e);
		}
		return validFirms;
	}
	/**
	 * fetching the basic firm details
	 */
	public List<BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds)throws DataServiceException {
		List<BasicFirmDetailsVO> basicDetailsList = null;
		try{
			basicDetailsList = getSqlMapClient().queryForList("getBasicDetails.query", firmIds);

		}
		catch(Exception e){
			throw new DataServiceException("Exception in getBasicFirmDetails() of ServiceOrderDaoImpl", e);
		}
		return basicDetailsList;

		/*numberOfEmployees = (Integer) getSqlMapClient().queryForObject("getApprovedMarketReadyproviders.query", Integer.parseInt(vendorId));
		aggregateRating = (Double) getSqlMapClient().queryForObject("getProviderFirmRating.query", vendorId);
		reviewCount = (Integer) getSqlMapClient().queryForObject("getFirmReviewCount.query", vendorId);
		if(null != basicFirmDetailsVO){
			basicFirmDetailsVO.setNumberOfEmployees(numberOfEmployees);
			basicFirmDetailsVO.setFirmAggregateRating(aggregateRating);
			basicFirmDetailsVO.setReviewCount(reviewCount);
		}
	}
	catch(Exception e){
		throw new DataServiceException("Exception in getBasicFirmDetails() of ServiceOrderDaoImpl", e);
	}
	return basicDetailsList;*/
	}

	/**
	 * fetch the service details of the firm
	 * @param firmIds
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<FirmServiceVO> getVendorServiceDetails(List<String> firmIdList) throws DataServiceException{
		List serviceList = null;
		try {		
			serviceList =  getSqlMapClient().queryForList("getVendorServiceDetails.query", firmIdList);   

		} catch (Exception e) {
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getVendorServiceDetails() due to "	+ e.getMessage(), e);
		}
		return serviceList;
	}

	/**
	 * fetch the review details of the firm
	 * @param firmIdList
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ReviewVO> getVendorReviewDetails(List<String> firmIdList) throws DataServiceException{
		List reviewList = null;
		try {		
			reviewList =  getSqlMapClient().queryForList("getVendorReviewDetails.query", firmIdList);   

		} catch (Exception e) {
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getVendorReviewDetails.query() due to "	+ e.getMessage(), e);
		}
		return reviewList;
	}
	/**
	 * method to fetch the warranty details
	 * @param firmIdList
	 * @return
	 * @throws DataServiceException
	 */
	public List<WarrantyVO> getWarrantyDetails(List<String> firmIdList) throws DataServiceException{
		List warrantyList = null;
		try{
			warrantyList = getSqlMapClient().queryForList("getWarrantyDetails.query", firmIdList);   
		}
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getWarrantyDetails.query() due to "	+ e.getMessage(), e);
		}
		return warrantyList;

	}

	public Map<Long, Long> getNoOfEmployees(List<String> firmIds)throws DataServiceException{
		Map<Long, Long> employeeMap = null;
		try{
			employeeMap = (Map<Long, Long>)getSqlMapClient().queryForMap(
					"getNOOfEmployees.query", firmIds,
					"firmId", "noOfEmployees");

		} 
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getNoOfEmployees.query() due to "	+ e.getMessage(), e);
		}
		return employeeMap;
	}

	public Map<Integer, BigDecimal> getAggregateRating(List<String> firmIds)throws DataServiceException{
		Map<Integer, BigDecimal> aggregateRating = null;
		try{
			aggregateRating = (Map<Integer, BigDecimal>)getSqlMapClient().queryForMap(
					"getProviderFirmRating.query", firmIds,
					"vendor_id", "rating");

		} 
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getNoOfEmployees.query() due to "	+ e.getMessage(), e);
		}
		return aggregateRating;
	}
	public Map<Integer, Long> getOverallReviewCount(List<String> firmIds)throws DataServiceException{
		Map<Integer, Integer> reviewCount = null;
		Map<Integer, Long> reviewCountRes = new HashMap<Integer, Long>();
		try{
			reviewCount = (Map<Integer, Integer>)getSqlMapClient().queryForMap(
					"getFirmReviewCount.query", firmIds,
					"vendor_id", "reviewCount");
			if(!reviewCount.isEmpty()){
				 for (Map.Entry<Integer, Integer> entry : reviewCount.entrySet())  
					 reviewCountRes.put(entry.getKey(), new Long(entry.getValue()));
				}

		} 
		
				
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getNoOfEmployees.query() due to "	+ e.getMessage(), e);
		}
		return reviewCountRes;
	}

	
	public Boolean fetchSealedBidIndicator(String soId) throws DataServiceException
	{
		Boolean sealedBidInd=false;
		try {
			sealedBidInd=(Boolean) queryForObject("sealedBidInd.query",soId);
			if(null == sealedBidInd)
			{
				sealedBidInd=false;
			}
		} catch (Exception e) {
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.fetchSealedBidIndicator() due to "	+ e.getMessage(), e);
		}
		return sealedBidInd;
	}

	/** R16_1_1: SL-21270:Fetching finalLaborPrice and finalPartsPrice from so_hdr
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrderPriceVO getFinalPrice(String soId)
			throws DataServiceException {
		ServiceOrderPriceVO serviceOrderPriceVO = null;
		try{
			serviceOrderPriceVO =  (ServiceOrderPriceVO)queryForObject("getFinalSOPrice.query", soId);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.getFinalPrice due to:" + e);
		}
		return serviceOrderPriceVO;
	}
	
	/** R16_1_1: SL-21270:Fetching basic addon details
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public AdditionalPaymentVO getAddonDetails(String soId) throws DataServiceException {
		AdditionalPaymentVO additionalPaymentVO = null;
		try{
			additionalPaymentVO =  (AdditionalPaymentVO)queryForObject("getAddonExistsDetails.query", soId);
		}
		catch(Exception e){
			logger.error("Exception in ServiceOrderDaoImpl.getAddonDetails due to:" + e);
		}
		return additionalPaymentVO;
	}
	
	/**@Description: Fetching the logo document id for the service order
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	
	public Integer getLogoDocumentId(String soId) throws DataServiceException {
		Integer logoDocumentId =null;
		try{
			logoDocumentId = (Integer) queryForObject("getSoLogoDocumentId.query",soId);
		}catch (Exception e) {
			logger.error("Error while retrieving document Id  in SOWizardFetchDelegateImpl.getLogoDocumentId()", e);
			throw new DataServiceException("Error while retrieving document Id  in SOWizardFetchDelegateImpl.getLogoDocumentId()");
		}
		return logoDocumentId;
	}
	

	/** SL-21446-Relay Services: Fetching the company logo if available else the default image 
	 * @param firmIds
	 * @return
	 */
	public Map<Long, String> getCompanyLogo(List<String> firmIds)
			throws DataServiceException {
		Map<Long, String> companyLogo = null;
		try{
			companyLogo = (Map<Long, String>)getSqlMapClient().queryForMap(
					"getCompanyLogo.query", firmIds,
					"vendor_id", "companyLogo"); 

		} 
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderDaoImpl.getCompanyLogo.query() due to "	+ e.getMessage(), e);
		}
		return companyLogo;
	}

	/* SL-21446-Relay Services: Fetching the default logo image
	 */
	public String getDefaultLogo(String defaultFirmLogo)
			throws DataServiceException {
		String logo=null;
		try{
			logo = (String) queryForObject("getAppKeyValue.query",defaultFirmLogo);
		}catch (Exception e) {
			logger.error("Error while retrieving default logo  in ServiceOrderDaoImpl.getDefaultLogo()", e);
			throw new DataServiceException("Error while retrieving logo in ServiceOrderDaoImpl.getDefaultLogo() due to "+ e.getMessage(), e);
		}
		return logo; 
	}
	
	/* SL-21446-Relay Services: Fetching the logo image path from application properties
	 */
	public String getStaticUrl(String firmLogoPath) throws DataServiceException {
		String staticUrl=null;
		try{
			staticUrl = (String) queryForObject("getAppKeyValue.query",firmLogoPath);
		}catch (Exception e) {
			logger.error("Error while retrieving default logo  in ServiceOrderDaoImpl.getStaticUrl()", e);
			throw new DataServiceException("Error while retrieving logo in ServiceOrderDaoImpl.getStaticUrl() due to "+ e.getMessage(), e);
		}
		return staticUrl; 
	}
	
	
	public String getFirmLogoSaveLocation(String firmLogoSavePath) throws DataServiceException{
		String firmLogoSaveLocation=null;
		try{
			firmLogoSaveLocation = (String) queryForObject("getAppKeyValue.query",firmLogoSavePath);  
		}catch (Exception e) { 
			logger.error("Error while retrieving default logo  in ServiceOrderDaoImpl.getFirmLogoSaveLocation()", e);
			throw new DataServiceException("Error while retrieving logo in ServiceOrderDaoImpl.getFirmLogoSaveLocation() due to "+ e.getMessage(), e);
		}
		return firmLogoSaveLocation; 
	}
	
	public HashMap<String, Object> getDocumentDetails(String soId,String documentName)throws DataServiceException{
		HashMap<String, Object> docDetails = new HashMap<String, Object>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("soId", soId);
		params.put("documentName", documentName);
		
		try{
			docDetails = (HashMap<String, Object>) queryForObject("getDocumentByName.query", params);
			
		}catch(Exception e){
			logger.info("Exception in ProviderUploadDocumentService.getFirmId() "+e.getMessage());
		}
		return docDetails;
	}
	
	public String getConstantValueFromDB(String appkey)
			throws DataServiceException {
		String value = null;
		try{
			value=(String) queryForObject("getConstantValue.query",appkey);
		}catch(Exception e){
			logger.error("Exception occured in getPropertyFromDB() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return value;
	}
	public  List<ServiceDatetimeSlot> getSODateTimeSlots(String serviceOrderID) throws DataServiceException {
		List<ServiceDatetimeSlot> serviceDatetimeSlots=null;
		try{
			serviceDatetimeSlots= (List<ServiceDatetimeSlot>) queryForList("getServiceDatetimeSlots.query", serviceOrderID);
		}catch(Exception ex){
			logger.error("Exception occured in getSODateTimeSlots() due to "+ex.getMessage());
			throw new DataServiceException(ex.getMessage());

		}
		
		return serviceDatetimeSlots;
	}
	
	 public  void updateAcceptedServiceDatetimeSlot(ServiceDatetimeSlot serviceDatetimeSlot) throws BusinessServiceException{
		 logger.info("Entering updateAcceptedServiceDatetimeSlot() of ServiceOrderDaoImpl");
		 int count=0;
		 try{
			 count = update("so.updateSeviceDateTimeSlot", serviceDatetimeSlot);
			 logger.info("count after updated so_service_datetime_slot"+count);
			 count = update("so.updateSelctedSeviceDateTimeSlot", serviceDatetimeSlot); 
			 logger.info("count after updated so_hdr "+count);
			 logger.info("existing updateAcceptedServiceDatetimeSlot() of ServiceOrderDaoImpl");
		 }catch(Exception ex){
			 logger.error("Exception occured in updateAcceptedServiceDatetimeSlot() of ServiceOrderDaoImpl due to: "+ex.getMessage());
		 }
		  
		 
		  
	 }
	
	 public  ServiceDatetimeSlot getSODateTimeSlot(String serviceOrderID, Integer preferenceInd) throws DataServiceException {
			ServiceDatetimeSlot serviceDatetimeSlot=null;
			
			Map<String, Object> timeSlotParameter = new HashMap<String, Object>();
			timeSlotParameter.put("serviceOrderID", serviceOrderID);
			timeSlotParameter.put("preferenceInd", preferenceInd);
			try{
				serviceDatetimeSlot= (ServiceDatetimeSlot) queryForObject("getServiceDatetimeSlot.query", timeSlotParameter );
			}catch(Exception ex){
				logger.error("Exception occured in getSODateTimeSlot() due to "+ex.getMessage());
				throw new DataServiceException(ex.getMessage());

			}
			
			return serviceDatetimeSlot;
		}
	 
		public void updateCorelationIdWithSoId(HashMap<String, Object> corelationIdAndSoidMap) throws DataServiceException {
			
			try {
				// `so_corelation_id_generator`
				getSqlMapClientTemplate().update("update.CorelationIdWithSoIdInSoCorelationIdGenerator", corelationIdAndSoidMap);
			} catch (Exception e) {
				logger.error("Exception occured in update.CorelationIdWithSoIdInSoCorelationIdGenerator due to " + e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			
			try {
				// `vendor_ranking_weighted_score`
				getSqlMapClientTemplate().update("update.CorelationIdWithSoIdInVendorRankingWeightedScore", corelationIdAndSoidMap);
			} catch (Exception e) {
				logger.error("Exception occured in update.CorelationIdWithSoIdInVendorRankingWeightedScore due to " + e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			
			try {
				// `vendor_so_ranking`
				getSqlMapClientTemplate().update("update.CorelationIdWithSoIdInVendorSoRanking", corelationIdAndSoidMap);
			} catch (Exception e) {
				logger.error("Exception occured in update.CorelationIdWithSoIdInVendorSoRanking due to " + e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
		}

	 public void saveWarrantyHomeReasons(WarrantyHomeReasonInfoVO warrantyHomeReasonInfoVO) throws BusinessServiceException{
		 int count=0;
		 try{
			 logger.info("Entering into saveWarrantyHomeReasons of ServiceOrderDaoImpl");
			 String soId = warrantyHomeReasonInfoVO.getSoId();
			 logger.info("SO ID:"+soId);
			 logger.info("Home warranty Reason code id :"+warrantyHomeReasonInfoVO.getReasonCode());
			 count = (Integer)queryForObject("getHomeWarrantyReasonCodesCountForSO.query", soId );
			 if(count>0){
				 throw new BusinessServiceException("Reason code has already been submitted");
			 }else{
				 insert("soWarrantyHomeReason.insert", warrantyHomeReasonInfoVO);	
			 }

			 logger.info("exiting from saveWarrantyHomeReasons of ServiceOrderDaoImpl ");

		 } catch (Exception ex) {
			 logger.error("[ServiceOrderDaoImpl.saveWarrantyHomeReasons - Exception] "+ ex);
			 throw new BusinessServiceException(ex.getMessage(), ex);
		 }
	 }
	  
	 public List<ProviderDetailWithSOAccepted> getAvailableProviderAcceptedSO(Integer buyerId, Integer days)throws DataServiceException {
			List<ProviderDetailWithSOAccepted> dayBeforeServiceProviderdetails = null;
			HashMap<String, Object> params = new HashMap<String, Object>();
			
			params.put("buyerId", buyerId);
			params.put("days", days);
			try{
				dayBeforeServiceProviderdetails = getSqlMapClient().queryForList("getProviderByAcceptedSO.query", params);
						
			}catch (Exception e) {
				logger.error("Exception in fetching provider details available for day before service");
				throw new DataServiceException(e.getMessage());
			}
			return dayBeforeServiceProviderdetails;
		}
	 
	//SLT-2138: Get acceptedVendorId for push Notification
	 public Integer getAcceptedVendorIdForPushNotfcn(String soId) throws BusinessServiceException{
		 Integer acceptedVendorId=null;
		 try{
			 acceptedVendorId=(Integer) queryForObject("getAcceptedVendorId.query", soId);
		 }catch(Exception ex){
			 logger.error("[ServiceOrderDaoImpl.getAcceptedVendorIdForPushNotfcn - Exception] "+ ex);
			 throw new BusinessServiceException(ex.getMessage(), ex);
		 }
		 return acceptedVendorId;
	 }
	 
	 //SLT-2138: Send push notification to Primary resource of the firm
	 public Integer getPrimaryResourceId(Integer acceptedVendorId) throws BusinessServiceException{
		 Integer primaryResourceId=null;
		 try{
			 primaryResourceId=(Integer) queryForObject("getPrimaryResourceId.query", acceptedVendorId);
			 logger.info("ServiceOrderDaoImpl.getPrimaryResourceId --> " +primaryResourceId);
		 }catch(Exception ex){
			 logger.error("[ServiceOrderDaoImpl.getPrimaryResourceId - Exception] "+ ex);
			 throw new BusinessServiceException(ex.getMessage(), ex);
		 }
		 return primaryResourceId;
	 }
	 
	 	//SLT-4491  validate provider Id
		public Integer getValidProvider(Integer providerId) throws DataServiceException{
			 Integer vendorId=null;
			 try{
				 vendorId= (Integer) queryForObject("getValidVendor.query",providerId);
				 logger.info("ServiceOrderDaoImpl.getValidProvider --> " +providerId);
			 }catch(Exception ex){
				 logger.error("[ServiceOrderDaoImpl.getValidProvider - Exception] "+ ex);
				 throw new DataServiceException(ex.getMessage(), ex);
			 }
			 return vendorId;
		 }
	 
} 