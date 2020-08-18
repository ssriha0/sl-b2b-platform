package com.newco.marketplace.webservices.dispatcher.so.order;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.dto.serviceorder.ContactRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.PhoneRequest;
import com.newco.marketplace.webservices.dto.serviceorder.SpecialtyAddonRequest;
import com.newco.marketplace.webservices.util.WSConstants;

public class OrderDispatcherHelper {

	private static final Logger logger = Logger.getLogger(OrderDispatcherHelper.class);

	public static final String TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
	/**
	 * This method maps the LocationRequest object that acts as the DTO or payload holder
	 * for a web service call to SoLocation the VO object.
	 * @param request
	 * @return
	 */
	public static SoLocation mapLocationRequest(LocationRequest request) {
		SoLocation soLocation = new SoLocation();
		soLocation.setAptNo(request.getAptNo());
		soLocation.setCity(request.getCity());
		soLocation.setCountry(request.getCountry());
		soLocation.setCreatedDate(request.getCreatedDate());
		soLocation.setLatitude(request.getLatitude());
		soLocation.setLocName(request.getLocName());
		soLocation.setLocnClassDesc(request.getLocnClassDesc());
		soLocation.setLocnClassId(request.getLocnClassId());
		soLocation.setLocnId(request.getLocnId());
		soLocation.setLocnName(request.getLocName());
		soLocation.setLocnNotes(request.getLocnNotes());
		soLocation.setLocnTypeId(request.getLocnTypeId());
		soLocation.setLongitude(request.getLongitude());
		soLocation.setModifiedBy(request.getModifiedBy());
		soLocation.setModifiedDate(request.getModifiedDate());
		soLocation.setSoId(request.getSoId());
		soLocation.setState(request.getState());
		soLocation.setStreet1(request.getStreet1());
		soLocation.setStreet2(request.getStreet2());
		soLocation.setZip(request.getZip());
		soLocation.setZip4(request.getZip4());
		return soLocation;
	}
	
	/**
	 * This method maps the ContactRequest object that acts as the DTO or payload holder
	 * for a web service call to Contact the VO object.
	 * @param request
	 * @return
	 */
	public static Contact mapContactRequest(ContactRequest request) {
		Contact contact = new Contact();
		contact.setBusinessName(request.getBusinessName());
		contact.setCellNo(contact.getCellNo());
		contact.setContactGroup(request.getContactGroup());
		contact.setContactId(request.getContactId());
		contact.setContactMethodId(request.getContactMethodId());
		contact.setContactTypeId(request.getContactTypeId());
		contact.setCreatedDate(request.getCreatedDate());
		contact.setEmail(request.getEmail());
		contact.setEmailPreference(request.getEmailPreference());
		contact.setEntityId(request.getEntityId());
		contact.setEntityTypeId(request.getEntityTypeId());
		contact.setFirstName(request.getFirstName());
		contact.setHonorific(request.getHonorific());
		contact.setLastName(request.getLastName());
		contact.setMi(request.getMi());
		contact.setModifiedBy(request.getModifiedBy());
		contact.setModifiedDate(request.getModifiedDate());
		contact.setPhoneCreatedDate(request.getPhoneCreatedDate());
		contact.setPhoneTypeId(request.getPhoneTypeId());
		contact.setSmsPreference(request.getSmsPreference());
		contact.setSoId(request.getSoId());
		contact.setSuffix(request.getSuffix());
		contact.setTitle(request.getTitle());
		if (null != request.getPhones()	&& request.getPhones().size() > 0) {
			List<PhoneVO> phonesVo = new ArrayList<PhoneVO>();
			for (PhoneRequest phone : request.getPhones()) {
				PhoneVO phoneVo = new PhoneVO();
				if (phone.getCreatedDate() != null) {
					phoneVo.setCreatedDate(new Timestamp(phone.getCreatedDate().getTime()));
				} else {
					phoneVo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					phone.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				}
				phoneVo.setPhoneNo(phone.getPhoneNo());
				phoneVo.setPhoneExt(phone.getPhoneExt());
				phoneVo.setPhoneType(phone.getPhoneType());
				phoneVo.setClassId(phone.getClassId());
				phonesVo.add(phoneVo);
			}
			contact.setPhones(phonesVo);
		}
		return contact;
	}

	public static ServiceOrder adaptRequest(CreateDraftRequest request) throws DataException {
		
		// create service order from request
		ServiceOrder so = new ServiceOrder();
		Buyer buyer = new Buyer();
		
		//buyer setup
		buyer.setBuyerId(request.getBuyerId());
		Contact buyerContact = new Contact();
		buyerContact.setContactId(request.getBuyerContactId());
		buyer.setBuyerContact(buyerContact);
		so.setBuyer(buyer);
		
		//service order setup
		so.setRetailPrice(request.getRetailPrice());
		so.setRetailCancellationFee(request.getRetailCancellationFee());
		so.setCreatorUserName(request.getUserId());
		so.setPricingTypeId(request.getPricingTypeId());
		so.setSpendLimitLabor(request.getSpendLimitLabor());
		so.setSpendLimitParts(request.getSpendLimitParts());
		so.setSowTitle(request.getSowTitle());
		so.setSowDs(request.getSowDs());
		so.setServiceDate1((Timestamp) request.getScheduledDate());
		so.setProviderInstructions(request.getProviderInstructions());
		so.setBuyerTermsCond(request.getBuyerTermsCond());
		so.setProviderServiceConfirmInd(request.getConfirmServiceTime());
		if (so.getSowDs() != null && so.getSowDs().length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH.intValue()){
			String sowDs=so.getSowDs().trim().replaceAll("\\n", "").replaceAll("\\t","");
			if(sowDs.length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH.intValue()){
				sowDs=sowDs.substring(0,OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH.intValue());
			}
			so.setSowDs(sowDs); 
		}
		if (null != request.getServiceLocation()) {
			so.setServiceLocation(mapLocationRequest(request.getServiceLocation()));
		}
		if (null != request.getBuyerSupportLocation()) {
			so.setBuyerSupportLocation(mapLocationRequest(request.getBuyerSupportLocation()));
		}
		if(null != request.getServiceContact()){
			so.setServiceContact(mapContactRequest(request.getServiceContact()));
		}
		if(null != request.getServiceContactAlt()){
			so.setServiceContactAlt(mapContactRequest(request.getServiceContactAlt()));
		}
		if(null != request.getBuyerSupportContact()){
			so.setBuyerSupportContact(mapContactRequest(request.getBuyerSupportContact()));
		}
		if(null != request.getEndUserContact()){
			so.setEndUserContact(mapContactRequest(request.getEndUserContact()));
		}
		
		mapTaskRequests(request, so);
		
		// add the parts if there are any...
		List<Part> parts = new ArrayList<Part>();
		if (request.getParts() != null && request.getParts().size() > 0) {
			for (CreatePartRequest reqPart : request.getParts()) {
				Part partVo = new Part();
				Carrier shippingCarrier = new Carrier();
				Carrier returnCarrier = new Carrier();
//				try {
//					RandomGUID randomNo = new RandomGUID();
//					//partVo.setPartId(randomNo.generateGUID().intValue());
//				} catch (Exception e) {
//					logger.log(Level.ERROR, e.getMessage());
//					throw new DataException(e.getMessage());
//				}
				if (reqPart.getCreatedDate() != null) {
					partVo.setCreatedDate(reqPart.getCreatedDate());
				} else {
					partVo.setCreatedDate(new Date());
					reqPart.setCreatedDate(new Date());
				}
				partVo.setHeight(reqPart.getHeight());
				partVo.setLength(reqPart.getLength());
				partVo.setWidth(reqPart.getWidth());
				partVo.setWeight(reqPart.getWeight());
				partVo.setManufacturer(reqPart.getManufacturer());
				partVo.setReferencePartId(reqPart.getReferencePartId());
				
				if (reqPart.getStandard() != null) {
					partVo.setMeasurementStandard(Integer.valueOf(reqPart.getStandard()));
				}
				else {
					partVo.setMeasurementStandard(null);
				}
				partVo.setModelNumber(reqPart.getModelNumber());
				partVo.setSerialNumber(reqPart.getSerialNumber());
				partVo.setAltPartRef1(reqPart.getAltPartRef1());
				partVo.setAltPartRef2(reqPart.getAltPartRef2());
				partVo.setManufacturerPartNumber(reqPart.getManufacturerPartNumber());
				partVo.setVendorPartNumber(reqPart.getVendorPartNumber());
				partVo.setAdditionalPartInfo(reqPart.getAdditionalPartInfo());
				partVo.setPartDs(reqPart.getPartDesc());
				partVo.setProductLine(reqPart.getProductLine());
				partVo.setPartStatusId(reqPart.getPartStatusId());
				partVo.setProviderBringPartInd(reqPart.isProviderBringparts());
				partVo.setOrderNumber(reqPart.getOrderNumber());
				partVo.setPurchaseOrderNumber(reqPart.getPurchaseOrderNumber());
				partVo.setPartDs(reqPart.getPartDesc());
				partVo.setProviderBringPartInd(reqPart.isProviderBringparts());
				if (reqPart.getQuantity() != null && !reqPart.getQuantity().equals("")) {
					partVo.setQuantity(reqPart.getQuantity());
				}
				if (reqPart.getShippingCarrierId() != null) {
					shippingCarrier.setCarrierId(reqPart.getShippingCarrierId());
					if (reqPart.getOtherShippingCarrier() != null) {
						partVo.setShipCarrierOther(reqPart.getOtherShippingCarrier());
					}
					if (reqPart.getShippingTrackingNo() != null) {
						shippingCarrier.setTrackingNumber(reqPart.getShippingTrackingNo());
					}
					partVo.setShippingCarrier(shippingCarrier);
					partVo.setShipCarrierOther(reqPart.getOtherShippingCarrier());
				}
				if (reqPart.getReturnCarrierId() != null && reqPart.getReturnTrackingNo() != null) {
					returnCarrier.setCarrierId(reqPart.getReturnCarrierId());
					if (reqPart.getOtherReturnCarrier() != null) {
						partVo.setReturnCarrierOther(reqPart.getOtherReturnCarrier());
					}
					returnCarrier.setTrackingNumber(reqPart.getReturnTrackingNo());
					partVo.setReturnCarrier(returnCarrier);
				}
					if (reqPart.getPickupLocation() != null) {
						Contact pickupContactVo = mapContactRequest(reqPart.getPickupContact());
						partVo.setPickupContact(pickupContactVo);
						partVo.setProviderBringPartInd(new Boolean(true));
						SoLocation pickupLocationVo = mapLocationRequest(reqPart.getPickupLocation());
						partVo.setPickupLocation(pickupLocationVo);
					}
				
				Timestamp shipDate = null;
				Timestamp returnTrackTimestamp = null;
				//Get shipDate from CreatePartRequest
				try {
					if(reqPart.getShipDate() != null) {
						Date shippingDate = parseXMLGregorian(reqPart.getShipDate());
						shipDate = new Timestamp(shippingDate.getTime());
						//Set the shipDate into service object
						partVo.setShipDate(shipDate);
					}
					if(reqPart.getReturnTrackDate() != null) {
						Date returnTrackDate = parseXMLGregorian(reqPart.getReturnTrackDate());
						returnTrackTimestamp = new Timestamp(returnTrackDate.getTime());
						//Set the shipDate into service object
						partVo.setReturnTrackDate(returnTrackTimestamp);
					}
				} catch(Exception exc) {
					logger.error(exc);
					//Don't throw error for invalid shipDate
					//throw new DataException(exc);
				}

				parts.add(partVo);
			}
			so.setParts(parts);
		}
		
		so.setPrimarySkillCatId(request.getPrimarySkillCatId());
		if (request.getAppointmentStartDate() != null && request.getAppointmentEndDate() != null) {
			try {
				Date serviceDate1 = parseXMLGregorian(request.getAppointmentStartDate());
				Timestamp tserviceDate1 = new Timestamp(serviceDate1.getTime());
				so.setServiceDate1(tserviceDate1);
				Date serviceDate2 = parseXMLGregorian(request.getAppointmentEndDate());
				Timestamp tserviceDate2 = new Timestamp(serviceDate2.getTime());
				so.setServiceDate2(tserviceDate2);
				if (request.getServiceDateTypeId() == null) {
					if (request.getAppointmentEndDate() != null && request.getAppointmentEndTime() != null)
						so.setServiceDateTypeId(Integer.valueOf(OrderConstants.RANGE_DATE));
					else
						so.setServiceDateTypeId(Integer.valueOf(OrderConstants.FIXED_DATE));
				}
				else
					so.setServiceDateTypeId(request.getServiceDateTypeId());
			}
			catch (Exception exc) {
				logger.error(exc);
				throw new DataException(exc);
			}
		}
		so.setServiceTimeStart(request.getAppointmentStartTime());
		so.setServiceTimeEnd(request.getAppointmentEndTime());
		so.setPartsSupplier(request.getPartsSuppliedBy());
		
		/*  Add buyer Specific Fields*/
		so.setBuyerSpecificFields(request.getBuyerSpecificFields());
		
		//Template
		
		return so;
	}

	
	/**
	 * Map the create task requests to Service Order tasks
	 * @param request
	 * @param so
	 */
	private static void mapTaskRequests(CreateDraftRequest request,
            ServiceOrder so) {
        // create tasks from request
        List<ServiceOrderTask> tasks = new ArrayList<ServiceOrderTask>();
        if(request.getTasks() != null && request.getTasks().size() > 0) {
            String jobCode = null;
            Integer serviceTypeTemplateId = null;
            Integer currentSubcatNodeId = 0;
            Integer newSubcatNodeId = 0;
            for (CreateTaskRequest reqTask : request.getTasks()) {
                ServiceOrderTask task = new ServiceOrderTask();
                task.setServiceTypeId(reqTask.getServiceTypeId());
                task.setSkillNodeId(reqTask.getSkillNodeId());
                if (reqTask.getSubCategoryNodeId() != null) {
                    task.setSkillNodeId(reqTask.getSubCategoryNodeId());
                    task.setParentId(reqTask.getSkillNodeId());
                }
                else {
                    task.setSkillNodeId(reqTask.getSkillNodeId());
                }
                task.setTaskName(reqTask.getTaskName());
                task.setTaskComments(reqTask.getTaskComments());
                task.setPartsSuppliedId(reqTask.getPartsSuppliedId());
                task.setServiceTypeId(reqTask.getServiceTypeTempleteId());
                if(reqTask.getSubCategoryNodeId()!=null)
                    newSubcatNodeId = reqTask.getSubCategoryNodeId();
                else
                    newSubcatNodeId = 0;
                //set price to only one of the tasks created for each jobcode in the file
                if(null == jobCode || !jobCode.equals(reqTask.getJobCode()) ||
                        (jobCode.equals(reqTask.getJobCode()) &&
                                serviceTypeTemplateId != null &&
                                serviceTypeTemplateId.equals(reqTask.getServiceTypeTempleteId()) &&  currentSubcatNodeId.equals(newSubcatNodeId))){
                    jobCode = reqTask.getJobCode();
                    serviceTypeTemplateId = reqTask.getServiceTypeTempleteId();
                    currentSubcatNodeId = reqTask.getSubCategoryNodeId();
                    if(currentSubcatNodeId==null)
                        currentSubcatNodeId = 0;
                    task.setPrice(reqTask.getLaborPrice());                   
                }                   
                task.setAutoInjectedInd(Integer.valueOf(1));               
                tasks.add(task);
            }
            so.setTasks(tasks);           
        }
    }

	public static void adaptRequestNotes(ServiceOrder so, List<NoteRequest> noteRequests) {
		if (noteRequests != null && !noteRequests.isEmpty()) {
			List<ServiceOrderNote> notes = new ArrayList<ServiceOrderNote>();
			for (NoteRequest note : noteRequests) {
				ServiceOrderNote soNote = new ServiceOrderNote();
				soNote.setCreatedByName(OrderConstants.NEWCO_DISPLAY_SYSTEM);
				soNote.setCreatedDate(new Date());
				soNote.setNote(note.getNote());
				soNote.setSubject(note.getSubject());
				soNote.setRoleId(Integer.valueOf(OrderConstants.BUYER_ROLEID));
				soNote.setEntityId(so.getBuyerResourceId());
				//1 = support
				soNote.setNoteTypeId(OrderConstants.SO_NOTE_GENERAL_TYPE);//General
//				RandomGUID randomNo = new RandomGUID();
//				try {
//					//soNote.setNoteId(randomNo.generateGUID().longValue());
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
				notes.add(soNote);
			}
			so.setSoNotes(notes);
		}
	}
	
	/**
	 * This method enforces good dates passed by setting lenient to false in the formatter.
	 * JAXB by default allows dates like 2/30/08 and parses them to 3/2/08.
	 * @param in
	 * @return
	 * @throws ParseException
	 */
	public static Date parseXMLGregorian(String in) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat(TIME_FORMAT);
		fmt.setLenient(false);
		return fmt.parse(in);
	}
	
	public static Part adaptRequest(CreatePartRequest reqPart) throws DataException {
		Part part = new Part();
		part.setSoId(reqPart.getSoId());
		Carrier shippingCarrier = new Carrier();
		Carrier returnCarrier = new Carrier();
		part.setReferencePartId(reqPart.getReferencePartId());
		part.setManufacturer(reqPart.getManufacturer());
		part.setModelNumber(reqPart.getModelNumber());
		part.setQuantity(reqPart.getQuantity());
		part.setHeight(reqPart.getHeight());
		part.setWeight(reqPart.getWeight());
		part.setLength(reqPart.getLength());
		part.setSerialNumber(reqPart.getSerialNumber());
		part.setOrderNumber(reqPart.getOrderNumber());
		part.setPurchaseOrderNumber(reqPart.getPurchaseOrderNumber());
		part.setVendorPartNumber(reqPart.getVendorPartNumber());
		part.setAdditionalPartInfo(reqPart.getAdditionalPartInfo());
		part.setAltPartRef1(reqPart.getAltPartRef1());
		part.setAltPartRef2(reqPart.getAltPartRef2());
		part.setManufacturerPartNumber(reqPart.getManufacturerPartNumber());
		part.setProductLine(reqPart.getProductLine());
		part.setPartStatusId(reqPart.getPartStatusId());
		
		shippingCarrier.setCarrierId(reqPart.getShippingCarrierId());
		shippingCarrier.setTrackingNumber(reqPart.getShippingTrackingNo());
		part.setShippingCarrier(shippingCarrier);
		returnCarrier.setCarrierId(reqPart.getShippingCarrierId());
		returnCarrier.setTrackingNumber(reqPart.getReturnTrackingNo());
		part.setReturnCarrier(returnCarrier);		
		part.setPartDs(reqPart.getPartDesc());
		part.setProviderBringPartInd(part.isProviderBringPartInd());
		//LOCATION
		SoLocation pickupLocation = new SoLocation();
		if(reqPart.getPickupLocation() !=null) {
			pickupLocation = mapLocationRequest(reqPart.getPickupLocation());
		}
		pickupLocation.setLocnTypeId(Integer.valueOf(40));
		part.setPickupLocation(pickupLocation);
		//CONTACT
		Contact pickupContact = new Contact();			
		if(reqPart.getPickupContact() != null) {
			pickupContact = mapContactRequest(reqPart.getPickupContact());
		}
		pickupContact.setContactTypeId(Integer.valueOf(10));
		part.setPickupContact(pickupContact);
		Timestamp shipDate = null;
		Timestamp returnTrackTimestamp = null;
		//Get shipDate from CreatePartRequest
		try {
			if(reqPart.getShipDate() != null) {
				Date shippingDate = parseXMLGregorian(reqPart.getShipDate());
				shipDate = new Timestamp(shippingDate.getTime());
				//Set the shipDate into service object
				part.setShipDate(shipDate);
			}
			if(reqPart.getShipDate() != null) {
				Date returnTrackDate = parseXMLGregorian(reqPart.getReturnTrackDate());
				returnTrackTimestamp = new Timestamp(returnTrackDate.getTime());
				//Set the shipDate into service object
				part.setShipDate(returnTrackTimestamp);
			}
		} catch(Exception exc) {
			logger.error(exc);
			//Don't throw error response for invalid dates
			//throw new DataException(exc);
		}

		return part;
	}
	
	
	public static String retrieveMainJobCode(CreateDraftRequest request) {
		final int PRIMARY_JOB_CODE = 1;
		String mainJobCode= null;
		List<CreateTaskRequest> tasks = request.getTasks();
		if(tasks != null && tasks.size() > 0) {
			for (CreateTaskRequest reqTask : tasks) {
				if(reqTask.getServiceTypeId().intValue() == PRIMARY_JOB_CODE) {
					mainJobCode = reqTask.getJobCode();
					break;
				}
			}
		}
		return mainJobCode;
	}
	
	/**
	 * This method maps the ContactRequest object that acts as the DTO or payload holder
	 * for a web service call to Contact the VO object.
	 * @param request
	 * @return
	 */
	/**
	 * This method maps the CreateDraftRequest object to ServiceOrderAddon that acts as the VO
	 * @param CreateDraft request
	 * @return
	 */
	public static List<ServiceOrderAddonVO> adaptAddonRequest(CreateDraftRequest request){
		List<ServiceOrderAddonVO> addons = new ArrayList<ServiceOrderAddonVO>();
		if (request.getAddons() != null && request.getAddons().size() > 0) {
			for (SpecialtyAddonRequest reqAddon : request.getAddons()) {
				ServiceOrderAddonVO soAddon = new ServiceOrderAddonVO();
				//soAddon.setSoId(reqAddon.getSoId());
				soAddon.setSku(reqAddon.getSku());
				soAddon.setDescription(reqAddon.getDescription());
				soAddon.setRetailPrice(reqAddon.getRetailPrice());
				soAddon.setScopeOfWork(reqAddon.getScopeOfWork());
				soAddon.setMargin(reqAddon.getMargin());
				soAddon.setMiscInd(reqAddon.getMiscInd());
				soAddon.setCoverage(reqAddon.getCoverage());
				soAddon.setQuantity(reqAddon.getQuantity());
				addons.add(soAddon);
			}
		}	
		
		return addons;
	}


	public static List<String> getSkusAsList(List<CreateTaskRequest> tasks) {
		Set<String> skus = new HashSet<String>(tasks.size());
		if(tasks != null && tasks.size() > 0) {
			for (CreateTaskRequest task : tasks) {
				if (task.getJobCode() != null) {
					skus.add(task.getJobCode());
				}
			}
		}
		List<String> result = new ArrayList<String>();
		result.addAll(skus);
		return result;
	}


	public static Object getSpecialtyFromRequest(CreateDraftRequest request) {
		String specialty = null;
		List<CustomRef> refs = request.getCustomRef();
		for (CustomRef ref : refs) {
			if (ref.getKey().equals(WSConstants.CUSTOM_REF_SPECIALTY)) {
				specialty = ref.getValue();
				break;
			}
		}
		return specialty;
	}
	
}
