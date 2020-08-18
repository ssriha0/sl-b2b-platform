package com.newco.marketplace.validator.buyerFileUploadTool;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.buyerUploadScheduler.UploadToolConstants;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.constants.Constants.LocationConstants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.BuyerRefTypeVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditSuccessVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.persistence.iDao.buyerFileUploadTool.IBuyerFileUploadToolDAO;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class BuyerFileUploadToolValidatorImpl implements
		IBuyerFileUploadToolValidator, UploadToolConstants {

	private static final String DATE_FORMAT_YYYY = "MM/dd/yyyy";
	private static final String DATE_FORMAT_YY = "MM/dd/yy";

	protected final Log logger = LogFactory.getLog(getClass());

	IBuyerFileUploadToolDAO BFUTDaoImpl = null;
	ServiceOrderBO serviceOrderBOTarget = null;
	private IBuyerSOTemplateBO buyerSOTemplateBO;
	private IDocumentBO documentBO;

	/* (non-Javadoc)
	 * @see com.newco.marketplace.validator.buyerFileUploadTool.IBuyerFileUploadToolValidator#validateAndSubmit(java.util.Map, java.util.Map, int, int)
	 */
	public void validateAndSubmit(Map<String, String> soMap, Map lookUpMap, int fileId, int soCount,SecurityContext securityContext) {

		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setWfStateId(100); // Draft
		serviceOrder.setIsEditMode(false);
		List<Integer> attachments = new ArrayList<Integer>();
		List<String> errorList = new ArrayList<String>();

		// soMap - Excel raw data, lookUpMap - lookup Values for validations,
		// serviceOrder - transformed object
		long startTime = System.currentTimeMillis();	
		
		String templateId= soMap.get(Template_Id);		
		if(StringUtils.isNotBlank(templateId)){
			this.adoptTemplateDetails(soMap,serviceOrder,lookUpMap,attachments,errorList);
		}else{
			this.validtateAndMapDetailsFromSOMap(soMap,serviceOrder,lookUpMap,errorList);
		}	
		errorList = validateTransformSOReturnErrors(soMap,
				lookUpMap, serviceOrder, attachments,errorList);
		
		logger.info("errorList - size : " + errorList.size());		
		long endTime = System.currentTimeMillis();
		logger.error("Timer::Time taken to validate record(" + soCount + ")"
				+ (endTime - startTime) / 1000 + " seconds");
	
		String soId = null;
		if (errorList.isEmpty()) { // No errors, all validations passed through
			startTime = System.currentTimeMillis();
			try {
				ProcessResponse processResponse = serviceOrderBOTarget
						.doProcessCreatePreDraftSO(serviceOrder,
								securityContext);			
				soId = (String) processResponse.getObj();				
			} catch (Exception e) {
				soId = null;
				logger.error("Error in creating so", e);
				errorList
						.add("Unexpected error occured while creating this draft service order");
			}

			if (soId != null) { // Draft SO successfully created, attach
								// documents to it and store in success table
								// for buyer to view
				for (int docId : attachments) {
					BFUTDaoImpl.insertDocumentDetails(docId, soId);
				}
				UploadAuditSuccessVO uASVO = new UploadAuditSuccessVO(fileId,
						soId);
				BFUTDaoImpl.successInsert(uASVO);
			}
			endTime = System.currentTimeMillis();
			logger.error("Timer::Time taken to create SO:"
					+ (endTime - startTime) / 1000 + " seconds");			
		}

		if (soId == null) { // Draft SO creation failed due to some errors, dump
							// the SO details in an error object in error table
							// for buyer to view later
			try {
				UploadAuditErrorVO uAEVO = getUploadErrorObject(fileId, soCount,
						errorList, soMap);
				BFUTDaoImpl.errorInsert(uAEVO);
			} catch (Exception ex) {
				// In case of error in error-logging; proceed with processing rest of the SOs to be imported
				logger.error("SO Import - Error logging failed due to: ", ex);
			}
		}
	}
	
	
	/**
	 * Get SO details from template if matching template exists for the given buyer and template id
	 * @param buyerTemplateId
	 * @param serviceOrder
	 * @param lookUpMap
	 * @param attachments
	 */
	private void adoptTemplateDetails(Map<String, String> soMap,
			ServiceOrder serviceOrder, Map<String, HashMap> lookUpMap,
			List<Integer> attachments,List<String> errorList) {
		try{
			String buyerTemplateId= soMap.get(Template_Id);		
			Integer templateId = Integer.parseInt(buyerTemplateId);
			Map<?, ?> buyerInfoMap = (HashMap<?, ?>) lookUpMap.get("buyerInfoMap");
			int buyerId = Integer.parseInt(buyerInfoMap.get("buyerId").toString());
			BuyerSOTemplateDTO template = buyerSOTemplateBO.loadBuyerSOTemplate(templateId,buyerId);
				
			//Mapping details from template
			if(null != template){
				//Map Buyer Terms 
				serviceOrder.setBuyerTermsCond(template.getTerms());
				
				//Mapping SO Overview
				String overview = template.getOverview();
				if(StringUtils.isNotBlank(soMap.get(Overview))){
					overview = overview + "\n" +soMap.get(Overview);
				}
				serviceOrder.setSowDs(overview);

				//Mapping SO Title
				String title = template.getTitle();
				if(StringUtils.isNotBlank(soMap.get(Title))){
					title = title + "-" +soMap.get(Title);
				}
				serviceOrder.setSowTitle(title);
				
				//Mapping Provider Instructions
				String providerInstructions = template.getSpecialInstructions();
				if(StringUtils.isNotBlank(soMap.get(Special_Instructions))){
					providerInstructions = soMap.get(Special_Instructions) + "\r\n" +providerInstructions;
				}
				serviceOrder.setProviderInstructions(providerInstructions);
			
				//Map Parts supplier
				serviceOrder.setPartsSupplier(template.getPartsSuppliedBy());					
				
				//Mapping alt buyer contact details from template		
				if(null != template.getBuyerContact()){	
					Contact contact = new Contact();
					contact.setBusinessName(template.getBuyerContact().getCompanyName());
					contact.setContactTypeId(1);
					contact.setEmail(template.getBuyerContact().getEmail());
					contact.setFirstName(template.getBuyerContact().getIndividualName());
					serviceOrder.setServiceContactAlt(contact);
				}			
				
				//Mapping buyer contact details from template	
				serviceOrder.setBuyerContactId(template.getAltBuyerContactId());
				if (null != template.getAltBuyerContact()  && template.getAltBuyerContact().getResourceId() != null) {
					serviceOrder.setBuyerResourceId(template.getAltBuyerContact().getResourceId());
					if (serviceOrder.getBuyerResourceId() == null) {
						errorList.add("Buyer Resource ID is being set to null from the template details");
					}
				}
				Buyer buyer = new Buyer();				
				buyer.setBuyerId(buyerId);
				buyer.setContactId(template.getAltBuyerContactId());
				serviceOrder.setBuyer(buyer);	
									
				//Mapping logo document from template	
				Map<?, ?> logoMap = (HashMap<?, ?>) lookUpMap.get("logoInfoMap");			
				if (StringUtils.isNotBlank(template.getDocumentLogo())) {		
					String logoDocTitle = template.getDocumentLogo().trim().toUpperCase();
					if (logoMap.containsKey(logoDocTitle)) {					
						DocumentVO documentVO =  documentBO.retrieveDocumentByTitleAndEntityID(Constants.DocumentTypes.BUYER, template.getDocumentLogo(), buyerId);
						if (null != documentVO && null != documentVO.getDocumentId() && documentVO.getDocumentId() > 0) {
							serviceOrder.setLogoDocumentId(documentVO.getDocumentId());
						}
					}else{
						errorList.add("No Logo Id associated with the logo document \'"+template.getDocumentLogo() +"\'specified in template.<br/>");
					}
				}
					
				//Mapping other documents from template
				if (null != template.getDocumentTitles()&& template.getDocumentTitles().size() > 0) {
					for(String docTitle : template.getDocumentTitles()){
						DocumentVO documentVO =  documentBO.retrieveDocumentByTitleAndEntityID(Constants.DocumentTypes.BUYER, docTitle, buyerId);
						if (null != documentVO && null != documentVO.getDocumentId()) {
							attachments.add(documentVO.getDocumentId());
						}else{
							errorList.add("No Document Id associated with the document title \'"+docTitle +"\' specified in template.<br/>");
						}
					}
				}
					
				if (template.getConfirmServiceTime() != null) {
					serviceOrder.setProviderServiceConfirmInd(template.getConfirmServiceTime());
				}	
				
				//Mapping SPN Details
				if(null != template.getIsNewSpn() && template.getIsNewSpn().booleanValue()){
					serviceOrder.setSpnId(template.getSpnId());
				}		
							
			}else{
				soMap.put(Template_Id, null); 
				this.validtateAndMapDetailsFromSOMap(soMap,serviceOrder,lookUpMap,errorList);
			}
		}catch(Exception bse){
			errorList.add("Error fetching template details ");
		}		
	}
	
	/**
	 * Get So details from soMap,if template does not exists
	 * @param soMap
	 * @param serviceOrder
	 * @param lookUpMap
	 */
	private void validtateAndMapDetailsFromSOMap(Map<String, String> soMap,
			ServiceOrder serviceOrder, Map<String, HashMap> lookUpMap,
			List<String> errorList) {

		String unexpectedErrorInField = null;
		try {			
			String buyerTermsCond = soMap.get(Buyer_TC);
			serviceOrder.setBuyerTermsCond(buyerTermsCond);			
									
			String overview = soMap.get(Overview);
			serviceOrder.setSowDs(overview);
			logger.debug("overview: " + overview);			

			String title = soMap.get(Title);
			serviceOrder.setSowTitle(title);
			logger.debug("title: " + title);
			
			String specialInstructions = soMap.get(Special_Instructions);
			serviceOrder.setProviderInstructions(specialInstructions);
			logger.debug("specialInstructions: " + specialInstructions);
			
			// Part And Material
			unexpectedErrorInField = "Part and Material";
			Map<?, ?> partSupplierMap = (HashMap<?, ?>) lookUpMap.get("partSupplierMap");
			String partMaterial = soMap.get(Part_And_Material);
			if (StringUtils.isNotBlank(partMaterial)) {
				partMaterial = partMaterial.trim().toUpperCase();
				if (partSupplierMap.containsKey(partMaterial)) {
					int partsSupplier = Integer.parseInt(partSupplierMap.get(
							partMaterial).toString());
					serviceOrder.setPartsSupplier(partsSupplier);
				} else {
					errorList.add("Lookup for Part And Material failed.<br/>");
				}
			}
			
			//Setting buyer details
			Buyer buyer = new Buyer();
			Map<?, ?> buyerInfoMap = (HashMap<?, ?>) lookUpMap.get("buyerInfoMap");
			int buyerId = Integer.parseInt(buyerInfoMap.get("buyerId")
					.toString());
			int buyerResourceId = Integer.parseInt(buyerInfoMap.get(
					"buyerResourceId").toString());
			int contactId = (Integer) buyerInfoMap.get("contactId");
			serviceOrder.setBuyerContactId(contactId);
			serviceOrder.setBuyerResourceId(buyerResourceId);
			buyer.setBuyerId(buyerId);
			buyer.setContactId(contactId);
			serviceOrder.setBuyer(buyer);	
			
		} catch (Exception ex) {
			errorList.add("Unexpected error in validating the field '"
					+ unexpectedErrorInField
					+ "', please contact ServiceLive Administrator.<br/>");
		}
	}
	

	/**
	 * @param soMap
	 * @param lookUpMap
	 * @param serviceOrder
	 * @param attachments
	 * @param errorList
	 * @return
	 */
	private List<String> validateTransformSOReturnErrors(
			Map<String, String> soMap, Map lookUpMap,
			ServiceOrder serviceOrder, List<Integer> attachments,List<String> errorList) {
		// Counting error list containing errors during processing
		String unexpectedErrorInField = null;
		try {
			Contact contact = new Contact();
			SoLocation serviceLocation = new SoLocation();
		
			// Validation of Values from Lookup HashMaps as follows
						
			Map buyerInfoMap = (HashMap) lookUpMap.get("buyerInfoMap");
			int buyerId = Integer.parseInt(buyerInfoMap.get("buyerId")
					.toString());
			
			//Seeting creator user name
			String creatorUserName = (String) buyerInfoMap.get("creatorUserName");
			serviceOrder.setCreatorUserName(creatorUserName);
		
			// Main Service Category
			unexpectedErrorInField = "Main Service Category";
			String mainCategory = soMap.get(Main_Service_Catagory);
			logger.debug("mainCategory: " + mainCategory);
			Map mainCategoryMap = (HashMap) lookUpMap.get("mainCatMap");
			logger.debug("mainCategoryMap: " + mainCategoryMap);
			int primarySkillCatId = -1;
			if (StringUtils.isNotBlank(mainCategory)) {
				mainCategory = mainCategory.trim().toUpperCase();
				if (mainCategoryMap.containsKey(mainCategory)) {
					primarySkillCatId = Integer.parseInt(mainCategoryMap
							.get(mainCategory).toString());
					serviceOrder.setPrimarySkillCatId(primarySkillCatId);
				} else {
					errorList.add("Lookup for Main Service Category failed.<br/>");
				}
			} else {
					errorList.add("Main Service Category is mandatory.<br/>");
			}

			// Tasks
			List<ServiceOrderTask> taskList = new ArrayList<ServiceOrderTask>();
			// categoryMap for task category and subcategory
			Map categoryMap = (HashMap) lookUpMap.get("subCatMap");
			// skillMap for task skill
			Map skillMap = (HashMap) lookUpMap.get("skillMap");
			logger.debug("categoryMap: " + categoryMap);
			logger.debug("skillMap: " + skillMap);

			// Task One
			boolean taskF1 = false;
			boolean taskF2 = false;
			boolean taskF3 = false;
			unexpectedErrorInField = "Task-1";
			ServiceOrderTask task1 = new ServiceOrderTask();
			String taskName = soMap.get(TaskOne_TaskName);
			logger.debug("taskName: " + taskName);
			task1.setTaskName(taskName);
			String category = soMap.get(TaskOne_Category);
			logger.debug("category: " + category);
			if (StringUtils.isNotBlank(taskName)) {
				taskF1 = true;
			}
			if (StringUtils.isNotBlank(category)) {
				category = category.trim().toUpperCase();
				if (categoryMap.containsKey(category)) {
					int skillNodeId = Integer.parseInt(categoryMap.get(
							category).toString());
					task1.setSkillNodeId(skillNodeId);
				} else {
					taskF1 = false;
					errorList.add("Lookup for Task One_Category failed.<br/>");
				}
			}
			String subCategory = soMap.get(TaskOne_SubCategory);
			logger.debug("subCategory: " + subCategory);
			if (StringUtils.isNotBlank(subCategory)) {
				subCategory = subCategory.trim().toUpperCase();
				if (categoryMap.containsKey(subCategory)) {
					int skillNodeId = Integer.parseInt(categoryMap.get(
							subCategory).toString());
					task1.setSkillNodeId(skillNodeId);
				} else {
					taskF1 = false;
					errorList.add("Lookup for Task One_Sub Category failed.<br/>");
				}
			}
			String skill = soMap.get(TaskOne_Skill);
			if (StringUtils.isNotBlank(skill)) {
				skill = skill.trim().toUpperCase();
				if (skillMap.containsKey(primarySkillCatId+"|"+skill)) {
					int serviceTypeId = Integer.parseInt(skillMap.get(
							primarySkillCatId+"|"+skill).toString());
					task1.setServiceTypeId(serviceTypeId);
				} else {
					taskF1 = false;
					errorList.add("Lookup for Task One_Skill failed.<br/>");
				}
			} else {
				// default set to -1 when no skill mentioned
				task1.setServiceTypeId(-1);
			}
			String taskComments = soMap.get(TaskOne_TaskComments);
			task1.setTaskComments(taskComments);

			// Task Two
			unexpectedErrorInField = "Task-2";
			ServiceOrderTask task2 = new ServiceOrderTask();
			taskName = soMap.get(TaskTwo_TaskName);
			task2.setTaskName(taskName);
			category = soMap.get(TaskTwo_Category);
			if (StringUtils.isNotBlank(taskName)) {
				taskF2 = true;
			}
			
			if (StringUtils.isNotBlank(category)) {
				category = category.trim().toUpperCase();
				if (categoryMap.containsKey(category)) {
					int skillNodeId = Integer.parseInt(categoryMap.get(
							category).toString());
					task2.setSkillNodeId(skillNodeId);
				} else {
					taskF2 = false;
					errorList.add("Lookup for Task Two_Category failed.<br/>");
				}
			}
			subCategory = soMap.get(TaskTwo_SubCategory);
			if (StringUtils.isNotBlank(subCategory)) {
				subCategory = subCategory.trim().toUpperCase();
				if (categoryMap.containsKey(subCategory)) {
					int skillNodeId = Integer.parseInt(categoryMap.get(
							subCategory).toString());
					task2.setSkillNodeId(skillNodeId);
				} else {
					taskF2 = false;
					errorList.add("Lookup for Task Two_Sub Category failed.<br/>");
				}
			}
			skill = soMap.get(TaskTwo_Skill);
			if (StringUtils.isNotBlank(skill)) {
				skill = skill.trim().toUpperCase();
				if (skillMap.containsKey(primarySkillCatId+"|"+skill)) {
					int serviceTypeId = Integer.parseInt(skillMap.get(
							primarySkillCatId+"|"+skill).toString());
					task2.setServiceTypeId(serviceTypeId);
				} else {
					taskF2 = false;
					errorList.add("Lookup for Task Two_Skill failed.<br/>");
				}
			} else {
				// default set to -1 when no skill mentioned
				task2.setServiceTypeId(-1);
			}
			taskComments = soMap.get(TaskTwo_TaskComments);
			task2.setTaskComments(taskComments);

			// Task Three
			unexpectedErrorInField = "Task-3";
			ServiceOrderTask task3 = new ServiceOrderTask();
			taskName = soMap.get(TaskThree_TaskName);
			task3.setTaskName(taskName);
			category = soMap.get(TaskThree_Category);
			if (StringUtils.isNotBlank(taskName)) {
				taskF3 = true;
			}
			if (StringUtils.isNotBlank(category)) {
				category = category.trim().toUpperCase();
				if (categoryMap.containsKey(category)) {
					int skillNodeId = Integer.parseInt(categoryMap.get(
							category).toString());
					task3.setSkillNodeId(skillNodeId);
				} else {
					taskF3 = false;
					errorList.add("Lookup for Task Three_Category failed.<br/>");
				}
			}
			subCategory = soMap.get(TaskThree_SubCategory);
			if (StringUtils.isNotBlank(subCategory)) {
				subCategory = subCategory.trim().toUpperCase();
				if (categoryMap.containsKey(subCategory)) {
					int skillNodeId = Integer.parseInt(categoryMap.get(
							subCategory).toString());
					task3.setSkillNodeId(skillNodeId);
				} else {
					taskF3 = false;
					errorList.add("Lookup for Task Three_Sub Category failed.<br/>");
				}
			}
			skill = soMap.get(TaskThree_Skill);
			if (StringUtils.isNotBlank(skill)) {
				skill = skill.trim().toUpperCase();
				if (skillMap.containsKey(primarySkillCatId+"|"+skill)) {
					int serviceTypeId = Integer.parseInt(skillMap.get(
							primarySkillCatId+"|"+skill).toString());
					task3.setServiceTypeId(serviceTypeId);
				} else {
					taskF3 = false;
					errorList.add("Lookup for Task Three_Skill failed.<br/>");
				}
			} else {
				// default set to -1 when no skill mentioned
				task3.setServiceTypeId(-1);
			}
			taskComments = soMap.get(TaskThree_TaskComments);
			task3.setTaskComments(taskComments);

			// adding part to list and setting parts
			if (taskF1) {
				taskList.add(task1);
			}
			if (taskF2) {
				taskList.add(task2);
			}
			if (taskF3) {
				taskList.add(task3);
			}
			serviceOrder.setTasks(taskList);

			// for parts
			Carrier shippingCarrier1 = new Carrier();
			Carrier returnCarrier1 = new Carrier();
			Carrier shippingCarrier2 = new Carrier();
			Carrier returnCarrier2 = new Carrier();
			Carrier shippingCarrier3 = new Carrier();
			Carrier returnCarrier3 = new Carrier();
			List<Part> partList = new ArrayList<Part>();
			Map shipInfoMap = (HashMap) lookUpMap.get("shipInfoMap");

			// Part One
			boolean partF1 = false;
			boolean partF2 = false;
			boolean partF3 = false;

			unexpectedErrorInField = "Part-1";
			Part part1 = new Part();
			String manufacturer = soMap.get(PartOne_Manufacturer);
			part1.setManufacturer(manufacturer);
			String modelNumber = soMap.get(PartOne_ModelNumber);
			if (StringUtils.isNotBlank(modelNumber)) {
				part1.setModelNumber(modelNumber);
			}
			String description = soMap.get(PartOne_Description);
			part1.setPartDs(description);
			String quantity = soMap.get(PartOne_Quantity);
			if (StringUtils.isNotBlank(quantity)) {
				part1.setQuantity(quantity);
			}
			if (StringUtils.isNotBlank(manufacturer)
					|| StringUtils.isNotBlank(modelNumber)
					|| StringUtils.isNotBlank(quantity)
					|| StringUtils.isNotBlank(description)) {
				partF1 = true;
			}

			String inboundCarrier = soMap.get(PartOne_InboundCarrier);
			String inboundTracking = soMap.get(PartOne_InboundTracking);
			if (StringUtils.isNotBlank(inboundCarrier)) {
				inboundCarrier = inboundCarrier.trim().toUpperCase();
				if (shipInfoMap.containsKey(inboundCarrier)) {
					int shippingCarrier = Integer.parseInt(shipInfoMap.get(
							inboundCarrier).toString());
					shippingCarrier1.setCarrierId(shippingCarrier);
					if (StringUtils.isNotBlank(inboundTracking)) {
						shippingCarrier1.setTrackingNumber(inboundTracking);
					}
				} else {
					partF1 = false;
					errorList.add("Lookup for Part One_Inbound Carrier failed.<br/>");
				}
			}

			// setting the shipping carrier
			part1.setShippingCarrier(shippingCarrier1);

			String outboundCarrier = soMap.get(PartOne_OutboundCarrier);
			String outboundTracking = soMap.get(PartOne_OutboundTracking);
			if (StringUtils.isNotBlank(outboundCarrier)) {
				outboundCarrier = outboundCarrier.trim().toUpperCase();
				if (shipInfoMap.containsKey(outboundCarrier)) {
					int returnCarrier = Integer.parseInt(shipInfoMap.get(
							outboundCarrier).toString());
					returnCarrier1.setCarrierId(returnCarrier);
					if (StringUtils.isNotBlank(outboundTracking)) {
						returnCarrier1.setTrackingNumber(outboundTracking);
					}
				} else {
					partF1 = false;
					errorList
							.add("Lookup for Part One_Outbound Carrier failed.<br/>");
				}
			}

			// setting the return carrier
			part1.setReturnCarrier(returnCarrier1);

			// Part Two
			unexpectedErrorInField = "Part-2";
			Part part2 = new Part();
			manufacturer = soMap.get(PartTwo_Manufacturer);
			part2.setManufacturer(manufacturer);

			modelNumber = soMap.get(PartTwo_ModelNumber);
			if (StringUtils.isNotBlank(modelNumber)) {
				part2.setModelNumber(modelNumber);
			}
			description = soMap.get(PartTwo_Description);
			part2.setPartDs(description);
			if (StringUtils.isNotBlank(quantity)) {
				quantity = soMap.get(PartTwo_Quantity);
			}
			part2.setQuantity(quantity);
			if (StringUtils.isNotBlank(manufacturer)
					|| StringUtils.isNotBlank(modelNumber)
					|| StringUtils.isNotBlank(quantity)
					|| StringUtils.isNotBlank(description)) {
				partF2 = true;
			}

			inboundCarrier = soMap.get(PartTwo_InboundCarrier);
			inboundTracking = soMap.get(PartTwo_InboundTracking);
			if (StringUtils.isNotBlank(inboundCarrier)) {
				inboundCarrier = inboundCarrier.trim().toUpperCase();
				if (shipInfoMap.containsKey(inboundCarrier)) {
					int shippingCarrier = Integer.parseInt(shipInfoMap.get(
							inboundCarrier).toString());
					shippingCarrier2.setCarrierId(shippingCarrier);
					if (StringUtils.isNotBlank(inboundTracking)) {
						shippingCarrier2.setTrackingNumber(inboundTracking);
					}
				} else {
					partF2 = false;
					errorList.add("Lookup for Part Two_Inbound Carrier failed.<br/>");
				}
			}

			// setting the shipping carrier
			part2.setShippingCarrier(shippingCarrier2);

			outboundCarrier = soMap.get(PartTwo_OutboundCarrier);
			outboundTracking = soMap.get(PartTwo_OutboundTracking);
			if (StringUtils.isNotBlank(outboundCarrier)) {
				outboundCarrier = outboundCarrier.trim().toUpperCase();
				if (shipInfoMap.containsKey(outboundCarrier)) {
					int returnCarrier = Integer.parseInt(shipInfoMap.get(
							outboundCarrier).toString());
					returnCarrier2.setCarrierId(returnCarrier);
					if (StringUtils.isNotBlank(outboundTracking)) {
						returnCarrier2.setTrackingNumber(outboundTracking);
					}
				} else {
					partF2 = false;
					errorList
							.add("Lookup for Part Two_Outbound Carrier failed.<br/>");
				}
			}

			// setting the return carrier
			part2.setReturnCarrier(returnCarrier2);

			// Part Three
			unexpectedErrorInField = "Part-3";
			Part part3 = new Part();
			manufacturer = soMap.get(PartThree_Manufacturer);
			part3.setManufacturer(manufacturer);

			modelNumber = soMap.get(PartThree_ModelNumber);
			if (StringUtils.isNotBlank(modelNumber)) {
				part3.setModelNumber(modelNumber);
			}
			description = soMap.get(PartThree_Description);
			part3.setPartDs(description);
			quantity = soMap.get(PartThree_Quantity);
			if (StringUtils.isNotBlank(quantity)) {
				part3.setQuantity(quantity);
			}
			if (StringUtils.isNotBlank(manufacturer)
					|| StringUtils.isNotBlank(modelNumber)
					|| StringUtils.isNotBlank(quantity)
					|| StringUtils.isNotBlank(description)) {
				partF3 = true;
			}
			inboundCarrier = soMap.get(PartThree_InboundCarrier);
			inboundTracking = soMap.get(PartThree_InboundTracking);
			if (StringUtils.isNotBlank(inboundCarrier)) {
				inboundCarrier = inboundCarrier.trim().toUpperCase();
				if (shipInfoMap.containsKey(inboundCarrier)) {
					int shippingCarrier = Integer.parseInt(shipInfoMap.get(
							inboundCarrier).toString());
					shippingCarrier3.setCarrierId(shippingCarrier);
					if (StringUtils.isNotBlank(inboundTracking)) {
						shippingCarrier3.setTrackingNumber(inboundTracking);
					}
				} else {
					partF3 = false;
					errorList
							.add("Lookup for Part Three_Inbound Carrier failed.<br/>");
				}
			}

			// setting the shipping carrier
			part3.setShippingCarrier(shippingCarrier3);

			outboundCarrier = soMap.get(PartThree_OutboundCarrier);
			outboundTracking = soMap.get(PartThree_OutboundTracking);
			if (StringUtils.isNotBlank(outboundCarrier)) {
				outboundCarrier = outboundCarrier.trim().toUpperCase();
				if (shipInfoMap.containsKey(outboundCarrier)) {
					int returnCarrier = Integer.parseInt(shipInfoMap.get(
							outboundCarrier).toString());
					returnCarrier2.setCarrierId(returnCarrier);
					if (StringUtils.isNotBlank(outboundTracking)) {
						returnCarrier3.setTrackingNumber(outboundTracking);
					}
				} else {
					partF3 = false;
					errorList
							.add("Lookup for Part Three_Outbound Carrier failed.<br/>");
				}
			}

			// setting the return carrier
			part3.setReturnCarrier(returnCarrier3);

			// adding part to list and setting parts
			if (partF1) {
				partList.add(part1);
			}
			if (partF2) {
				partList.add(part2);
			}
			if (partF3) {
				partList.add(part3);
			}
			serviceOrder.setParts(partList);

			// buyer details
			unexpectedErrorInField = "Customer Details";
			Map locationMap = (HashMap) lookUpMap.get("locationMap");
			logger.debug("locationMap: " + locationMap);
			String locationType = soMap.get(Location_Type);
			logger.debug("locationType:" + locationType + ":");
			if (StringUtils.isNotBlank(locationType)) {
				locationType = locationType.trim().toUpperCase();
				if (locationMap.containsKey(locationType)) {
					int locnTypeId = Integer.parseInt(locationMap.get(
							locationType).toString());
					serviceLocation.setLocnClassId(locnTypeId);
					contact.setLocnTypeId(locnTypeId);
				} else {
					errorList.add("Lookup for Location Type failed.<br/>");
				}
			}
			String businessName = soMap.get(Business_Name);
			contact.setBusinessName(businessName);
			logger.debug("businessName: " + businessName);
			String firstName = soMap.get(First_Name);
			contact.setFirstName(firstName);
			logger.debug("firstName: " + firstName);
			String lastName = soMap.get(Last_Name);
			contact.setLastName(lastName);
			logger.debug("lastName: " + lastName);

			String street_1 = soMap.get(Street_Name1);
			serviceLocation.setStreet1(street_1);
			logger.debug("street_1: " + street_1);
			String street_2 = soMap.get(Street_Name2);
			serviceLocation.setStreet2(street_2);
			logger.debug("street_2: " + street_2);
			String aptNo = soMap.get(Apt);
			serviceLocation.setAptNo(aptNo);
			String city = soMap.get(City);
			serviceLocation.setCity(city);
			logger.debug("city: " + city);
			String state = soMap.get(State);
			serviceLocation.setState(state);
			logger.debug("state: " + state);
			String zip = soMap.get(Zip);
			serviceLocation.setZip(zip);
			logger.debug("zip: " + zip);

			// Zip-state validations
			if (StringUtils.isBlank(zip)) {
				errorList.add("Zipcode is mandatory for a draft service order.<br/>");
			} else if (StringUtils.isBlank(state)) {
				errorList.add("State is mandatory for a draft service order.<br/>");
			} else {
				int zipStateValidationResult = LocationUtils
						.checkIfZipAndStateValid(zip, state);
				if (zipStateValidationResult == LocationConstants.ZIP_STATE_NO_MATCH) {
					errorList
							.add("Zipcode doesn't match with specified state.<br/>");
				}
			}

			String email = soMap.get(Email);
			contact.setEmail(email);
			logger.info("email: " + email);

			// For Phone , Alt. phone and Fax
			boolean phoneF1 = false;
			boolean phoneF2 = false;
			boolean phoneF3 = false;

			Map phoneTypeMap = (HashMap) lookUpMap.get("phoneTypeMap");
			Map phoneClassMap = (HashMap) lookUpMap.get("phoneClassMap");

			List<PhoneVO> phoneList = new ArrayList<PhoneVO>();

			PhoneVO phoneVO1 = new PhoneVO();

			String phoneNo = soMap.get(Phone_Number);
			if (StringUtils.isNotBlank(phoneNo)) {
				phoneNo = phoneNo.replace("-", "");
				String phoneErrMsg = "Invalid Phone Number, please use this format: 123-456-7890<br/>";
				try {
					phoneVO1.setPhoneNo(phoneNo);
					if (phoneNo.length() != 10) {
						errorList.add(phoneErrMsg);
					}
				} catch (NumberFormatException nfEx) {
					errorList.add(phoneErrMsg);
				}
			}
			
			String phoneType = soMap.get(Phone_Type);
			if (StringUtils.isNotBlank(phoneNo)
					|| StringUtils.isNotBlank(phoneType)) {
				phoneF1 = true;
				if (StringUtils.isNotBlank(phoneType)) {
					phoneType = phoneType.trim().toUpperCase();
					if (phoneClassMap.containsKey(phoneType)) {
						int phoneClass = Integer.parseInt(phoneClassMap.get(
								phoneType).toString());
						phoneVO1.setClassId(phoneClass);
					} else {
						phoneF1 = false;
						errorList.add("Lookup for Phone Class Failed.<br/>");
					}
				} else {
					phoneF1 = false;
					errorList.add("Phone Type Empty.<br/>");
				}
				logger.debug("phoneClassMap: " + phoneClassMap);
				if (phoneTypeMap.get("PRIMARY") != null) {
					int phoneTypeId = Integer.parseInt(phoneTypeMap.get(
							"PRIMARY").toString());
					phoneVO1.setPhoneType(phoneTypeId);
				} else {
					phoneF1 = false;
					errorList.add("Lookup for Phone Type failed.<br/>");
				}
			}

			logger.info("debug: " + phoneNo);

			PhoneVO phoneVO2 = new PhoneVO();
			String altPhoneNumber = soMap.get(Alternate_Phone_Number);
			if (StringUtils.isNotBlank(altPhoneNumber)) {
				altPhoneNumber = altPhoneNumber.replace("-", "");
				String phoneErrMsg = "Invalid Alternate Phone Number, please use this format: 123-456-7890<br/>";
				try {
					phoneVO2.setPhoneNo(altPhoneNumber);
					if (altPhoneNumber.length() != 10) {
						errorList.add(phoneErrMsg);
					}
				} catch (NumberFormatException nfEx) {
					errorList.add(phoneErrMsg);
				}
			}
			String altPhoneType = soMap.get(Alternate_Phone_Type);

			if (StringUtils.isNotBlank(altPhoneNumber)
					|| StringUtils.isNotBlank(altPhoneType)) {
				phoneF2 = true;
				if (StringUtils.isNotBlank(altPhoneType)) {
					altPhoneType = altPhoneType.trim().toUpperCase();
					if (phoneClassMap.containsKey(altPhoneType)) {
						int phoneClass = Integer.parseInt(phoneClassMap.get(
								altPhoneType).toString());
						phoneVO2.setClassId(phoneClass);
					} else {
						phoneF2 = false;
						errorList.add("Lookup for Phone Type failed.<br/>");
					}
				} else {
					phoneF2 = false;
					errorList.add("Alt. Phone Type Empty.<br/>");
				}
				if (phoneTypeMap.get("ALTERNATE") != null) {
					int phoneTypeId = Integer.parseInt(phoneTypeMap.get(
							"ALTERNATE").toString());
					phoneVO2.setPhoneType(phoneTypeId);
				} else {
					phoneF2 = false;
					errorList.add("Lookup for Alternate Phone Type failed.<br/>");
				}

			}

			// for fax
			PhoneVO phoneVO3 = new PhoneVO();
			String faxNo = soMap.get(Fax);
			if (StringUtils.isNotBlank(faxNo)) {
				faxNo = faxNo.replace("-", "");
				String phoneErrMsg = "Invalid Fax Number, please use this format: 123-456-7890<br/>";
				try {
					phoneVO3.setPhoneNo(faxNo);
					if (altPhoneNumber.length() != 10) {
						errorList.add(phoneErrMsg);
					}
				} catch (NumberFormatException nfEx) {
					errorList.add(phoneErrMsg);
				}
			}
			
			String faxPhoneType = "FAX";
			if (StringUtils.isNotBlank(faxNo)) {
				phoneF3 = true;

				int phoneClass = Integer.parseInt(phoneClassMap.get(
						faxPhoneType.trim()).toString());
				phoneVO3.setClassId(phoneClass);

				if (phoneTypeMap.get("FAX") != null) {
					int phoneTypeId = Integer.parseInt(phoneTypeMap.get("FAX")
							.toString());
					phoneVO3.setPhoneType(phoneTypeId);
				} else {
					phoneF3 = false;
					errorList.add("Look up for Fax Type failed.<br/>");
				}
			}
			if (phoneF1) {
				phoneList.add(phoneVO1);
			}
			if (phoneF2) {
				phoneList.add(phoneVO2);
			}
			if (phoneF3) {
				phoneList.add(phoneVO3);
			}

			// setting phoneList into ServiceContact

			contact.setPhones(phoneList);
			// setting the contact info into service order
			serviceOrder.setServiceContact(contact);

			unexpectedErrorInField = "Service Order Details";
			String serviceLocationNotes = soMap.get(Service_Location_Notes);
			serviceLocation.setLocnNotes(serviceLocationNotes);

			// setting location to serviceorder
			serviceOrder.setServiceLocation(serviceLocation);

			// setting time and date
			String serviceDateType = soMap.get(Service_Date_Type);
			int serviceDateTypeId = 0;
			if (StringUtils.isNotBlank(serviceDateType)) {
				if (serviceDateType.trim().equalsIgnoreCase("Fixed")) {
					serviceDateTypeId = 1;
				} else if (serviceDateType.trim().equalsIgnoreCase("Range")) {
					serviceDateTypeId = 2;
				} else {
					errorList.add("Service Date Type must be 'Fixed' or 'Range'.<br/>");
				}
			}
			if (serviceDateTypeId > 0) {
				serviceOrder.setServiceDateTypeId(serviceDateTypeId);
			}
			// lookup
			// serviceOrder.setServiceDateTypeId(serviceDateType);
			String serviceDate = soMap.get(Service_Date);
			logger.debug("serviceDate: " + serviceDate);
			if (StringUtils.isNotBlank(serviceDate)) {
				Date serviceDt = getDateFromString(serviceDate); 
				if (serviceDt != null) {
					Timestamp date1 = new Timestamp(serviceDt.getTime());
					logger.debug("date1: " + date1);
					if (date1 != null) {
						serviceOrder.setServiceDate1(date1);
					}
				} else {
					errorList.add("Incorrect format for Service Date '"+serviceDate+"'.  Expected Format: MM/DD/YYYY  Example: 29/9/2008<br/>");
				}
			}

			String serviceTime = soMap.get(Service_Time);
			logger.debug("serviceTime: " + serviceTime);
			
			if (StringUtils.isNotBlank(serviceTime)) {
				serviceTime = getTimeFromFixedValues(serviceTime);
				if (serviceTime != null) {
					serviceOrder.setServiceTimeStart(serviceTime);
				} else {
					errorList.add("Service Time must be 15 minute interval value in valid format like 12:00 AM, 12:15 AM, 12:30 AM, 12:45 AM.<br/>");
				}
			}
			
			if (StringUtils.isNotBlank(serviceDateType) && serviceDateType.equalsIgnoreCase("Range")) {
				String serviceEndDate = soMap.get(Service_End_Date);
				String serviceEndTime = soMap.get(Service_End_Time);
				logger.debug("serviceEndDate: " + serviceEndDate);
				logger.debug("serviceEndTime: " + serviceEndTime);

				if (StringUtils.isBlank(serviceEndDate)
						|| StringUtils.isBlank(serviceEndTime)) {
					errorList
							.add("Service Date Type is Range, but End Date & End Time not provided.<br/>");
				} else {
					Date serviceEndDt = getDateFromString(serviceEndDate);
					if (serviceEndDt != null) {
						Timestamp date2 = new Timestamp(serviceEndDt.getTime());
						logger.debug("date2: " + date2);
						if (date2 != null) {
							serviceOrder.setServiceDate2(date2);
						}
					} else {
						errorList.add("Incorrect format for Service End Date '"+serviceEndDate+"'.  Expected Format: MM/DD/YYYY  Example: 9/29/2008<br/>");
					}

					serviceEndTime = getTimeFromFixedValues(serviceEndTime);
					if (serviceEndTime != null) {
						serviceOrder.setServiceTimeEnd(serviceEndTime);
					} else {
						errorList.add("Service End Time must be 15 minute interval value in valid format like 12:00 AM, 12:15 AM, 12:30 AM, 12:45 AM.<br/>");
					}
				}
			}

			String providerToConfirmTime = soMap.get(Provider_To_Confirm_Time);
			if (StringUtils.isNotBlank(providerToConfirmTime)) {
				int providerServiceConfirmInd = 1;
				if (providerToConfirmTime.equalsIgnoreCase("No") || providerToConfirmTime.equalsIgnoreCase("N")) {
					providerServiceConfirmInd = 0;
				} else if (providerToConfirmTime.equalsIgnoreCase("Yes") || providerToConfirmTime.equalsIgnoreCase("Y")) {
					providerServiceConfirmInd = 1;
				}
				serviceOrder.setProviderServiceConfirmInd(providerServiceConfirmInd);
			}

			// Custom Reference
			unexpectedErrorInField = "Custom Reference Fields";
			Map cusRefMap = (HashMap) lookUpMap.get("cusRefMap"+buyerId);
			List<BuyerRefTypeVO> custRefTypes = (List<BuyerRefTypeVO>)cusRefMap.get("cusRefMap"+buyerId);
			List<ServiceOrderCustomRefVO> customList = new ArrayList<ServiceOrderCustomRefVO>();
			int totalCustRefTypesCountForThisBuyer = custRefTypes.size();
			if (totalCustRefTypesCountForThisBuyer > MAX_SUPPORTED_CUSTOM_REF_COUNT) {
				totalCustRefTypesCountForThisBuyer = MAX_SUPPORTED_CUSTOM_REF_COUNT;
			}
			for (int custRefCount = 0; custRefCount < totalCustRefTypesCountForThisBuyer; ++custRefCount) {
				ServiceOrderCustomRefVO customRef = new ServiceOrderCustomRefVO();
				String refValue = soMap.get(Custom_Reference_Value + custRefCount);
				if (StringUtils.isNotBlank(refValue)) {
					customRef.setRefTypeId(custRefTypes.get(custRefCount).getBuyer_ref_type_Id());
					customRef.setRefType(custRefTypes.get(custRefCount).getRef_type());
					customRef.setRefValue(refValue);
					customList.add(customRef);
				}
			}
			logger.debug("customList: " + customList);
			if (customList.isEmpty()) {
				serviceOrder.setCustomRefs(null);
			} else {
				serviceOrder.setCustomRefs(customList);
			}

			// setting labor and part spend limit
			unexpectedErrorInField = "Maximum Price";
			String laborSpendLimit = soMap.get(Labor_Spend_Limit);
			logger.debug("laborSpendLimit: " + laborSpendLimit);
			if (StringUtils.isNotBlank(laborSpendLimit)) {
				if (laborSpendLimit.startsWith("$")) {
					laborSpendLimit = laborSpendLimit.replace("$", "");
				}
				serviceOrder.setSpendLimitLabor(Double
						.parseDouble(laborSpendLimit));
			}
			String partSpendLimit = soMap.get(Part_Spend_Limit);
			if (StringUtils.isNotBlank(partSpendLimit)) {
				if (partSpendLimit.startsWith("$")) {
					partSpendLimit = partSpendLimit.replace("$", "");
				}
				serviceOrder.setSpendLimitParts(Double
						.parseDouble(partSpendLimit));
			}
			logger.debug("partSpendLimit: " + partSpendLimit);

			Map docMap = (HashMap) lookUpMap.get("docInfoMap");
			Map logoMap = (HashMap) lookUpMap.get("logoInfoMap");

			
			String templateId= soMap.get(Template_Id);
			if(StringUtils.isBlank(templateId)){
				// Attachment1 Description1
				unexpectedErrorInField = "Attachments";
				String attach1 = soMap.get(Attachment1);
				if (StringUtils.isNotBlank(attach1)) {
					attach1 = attach1.trim().toUpperCase();
					if (docMap.containsKey(attach1)) {
						int docId = Integer.parseInt(docMap.get(attach1)
								.toString());
						if (docId > 0) {
							attachments.add(docId);
						}
					} else {
						errorList.add("No document Id associated with Attachment1.<br/>");
					}
				}
	
				String attach2 = soMap.get(Attachment2);
				if (StringUtils.isNotBlank(attach2)) {
					attach2 = attach2.trim().toUpperCase();
					if (docMap.containsKey(attach2)) {
						int docId = Integer.parseInt(docMap.get(attach2)
								.toString());
						if (docId > 0) {
							attachments.add(docId);
						}
					} else {
						errorList.add("No document Id associated with Attachment2.<br/>");
					}
				}
	
				String attach3 = soMap.get(Attachment3);
				if (StringUtils.isNotBlank(attach3)) {
					attach3 = attach3.trim().toUpperCase();
					if (docMap.containsKey(attach3)) {
						int docId = Integer.parseInt(docMap.get(attach3)
								.toString());
						if (docId > 0) {
							attachments.add(docId);
						}
					} else {
						errorList.add("No document Id associated with Attachment3.<br/>");
					}
				}
				
				String brandingInfo = soMap.get(Branding_Information);
				if (StringUtils.isNotBlank(brandingInfo)) {
					brandingInfo = brandingInfo.trim().toUpperCase();
					if (logoMap.containsKey(brandingInfo)) {
						int logoDocId = Integer.parseInt(logoMap.get(
								brandingInfo).toString());
						BFUTDaoImpl.selectLogoId(brandingInfo, buyerId);
						if (logoDocId > 0) {
							serviceOrder.setLogoDocumentId(logoDocId);
						}
					} else {
						errorList
								.add("No Logo Id associated with the Branding Information.<br/>");
					}
				}
			}
			
			
		} catch (Exception ex) {
			errorList.add("Unexpected error in validating the field '"
					+ unexpectedErrorInField
					+ "', please contact ServiceLive Administrator.<br/>");
		}
		return errorList;
	}

	public UploadAuditErrorVO getUploadErrorObject(int fileId, int soCount,
			List<String> errorList, Map<String, String> soMap) {

		UploadAuditErrorVO uAEVO = new UploadAuditErrorVO();

		uAEVO.setFileId(fileId);
		uAEVO.setRowId(soCount); // Excel Row #

		StringBuilder errorNotes = new StringBuilder();
		for (int errCnt = 0; errCnt < errorList.size(); errCnt++) {
			errorNotes.append(errorList.get(errCnt)).append("\n");
		}
		uAEVO.setErrorNotes(errorNotes.toString());
		if(soMap!=null)
		{
			uAEVO.setMainServiceCategory(soMap.get(Main_Service_Catagory));
			uAEVO.setTaskOneName(soMap.get(TaskOne_TaskName));
			uAEVO.setTaskOneCategory(soMap.get(TaskOne_Category));
			uAEVO.setTaskOneSubCategory(soMap.get(TaskOne_SubCategory));
			uAEVO.setTaskOneSkill(soMap.get(TaskOne_Skill));
			uAEVO.setTaskOneComments(soMap.get(TaskOne_TaskComments));
			uAEVO.setTaskTwoName(soMap.get(TaskTwo_TaskName));
			uAEVO.setTaskTwoCategory(soMap.get(TaskTwo_Category));
			uAEVO.setTaskTwoSubCategory(soMap.get(TaskTwo_SubCategory));
			uAEVO.setTaskTwoSkill(soMap.get(TaskTwo_Skill));
			uAEVO.setTaskTwoComments(soMap.get(TaskTwo_TaskComments));
			uAEVO.setTaskThreeName(soMap.get(TaskThree_TaskName));
			uAEVO.setTaskThreeCategory(soMap.get(TaskThree_Category));
			uAEVO.setTaskThreeSubCategory(soMap.get(TaskThree_SubCategory));
			uAEVO.setTaskThreeSkill(soMap.get(TaskThree_Skill));
			uAEVO.setTaskThreeComments(soMap.get(TaskThree_TaskComments));
			uAEVO.setPartOneManufacturer(soMap.get(PartOne_Manufacturer));
			uAEVO.setPartOneName(soMap.get(PartOne_PartName));
			uAEVO.setPartOneModel(soMap.get(PartOne_ModelNumber));
			uAEVO.setPartOneDesc(soMap.get(PartOne_Description));
			uAEVO.setPartOneQuantity(soMap.get(PartOne_Quantity));
			uAEVO.setPartOneInboundCarrier(soMap.get(PartOne_InboundCarrier));
			uAEVO.setPartOneInboundTrackingId(soMap.get(PartOne_InboundTracking));
			uAEVO.setPartOneOutboundCarrier(soMap.get(PartOne_OutboundCarrier));
			uAEVO.setPartOneOutboundTrackingId(soMap.get(PartOne_OutboundTracking));
			uAEVO.setPartTwoManufacturer(soMap.get(PartTwo_Manufacturer));
			uAEVO.setPartTwoName(soMap.get(PartTwo_PartName));
			uAEVO.setPartTwoModel(soMap.get(PartTwo_ModelNumber));
			uAEVO.setPartTwoDesc(soMap.get(PartTwo_Description));
			uAEVO.setPartTwoQuantity(soMap.get(PartTwo_Quantity));
			uAEVO.setPartTwoInboundCarrier(soMap.get(PartTwo_InboundCarrier));
			uAEVO.setPartTwoInboundTrackingId(soMap.get(PartTwo_InboundTracking));
			uAEVO.setPartTwoOutboundCarrier(soMap.get(PartTwo_OutboundCarrier));
			uAEVO.setPartTwoOutboundTrackingId(soMap.get(PartTwo_OutboundTracking));
			uAEVO.setPartThreeManufacturer(soMap.get(PartThree_Manufacturer));
			uAEVO.setPartThreeName(soMap.get(PartThree_PartName));
			uAEVO.setPartThreeModel(soMap.get(PartThree_ModelNumber));
			uAEVO.setPartThreeDesc(soMap.get(PartThree_Description));
			uAEVO.setPartThreeQuantity(soMap.get(PartThree_Quantity));
			uAEVO.setPartThreeInboundCarrier(soMap.get(PartThree_InboundCarrier));
			uAEVO.setPartThreeInboundTrackingId(soMap
					.get(PartThree_InboundTracking));
			uAEVO.setPartThreeOutboundCarrier(soMap.get(PartThree_OutboundCarrier));
			uAEVO.setPartThreeOutboundTrackingId(soMap
					.get(PartThree_OutboundTracking));
			uAEVO.setPartMaterial(soMap.get(Part_And_Material));
			uAEVO.setLocationType(soMap.get(Location_Type));
			uAEVO.setBusinessName(soMap.get(Business_Name));
			uAEVO.setFirstName(soMap.get(First_Name));
			uAEVO.setLastName(soMap.get(Last_Name));
			uAEVO.setStreet1(soMap.get(Street_Name1));
			uAEVO.setStreet2(soMap.get(Street_Name2));
			uAEVO.setAptNo(soMap.get(Apt));
			uAEVO.setCity(soMap.get(City));
			uAEVO.setState(soMap.get(State));
			uAEVO.setZip(soMap.get(Zip));
			uAEVO.setEmail(soMap.get(Email));
			uAEVO.setPhone(soMap.get(Phone_Number));
			uAEVO.setPhoneType(soMap.get(Phone_Type));
			uAEVO.setAltPhone(soMap.get(Alternate_Phone_Number));
			uAEVO.setAltPhoneType(soMap.get(Alternate_Phone_Type));
			uAEVO.setFax(soMap.get(Fax));
			uAEVO.setSoLocNotes(soMap.get(Service_Location_Notes));
			uAEVO.setTitle(soMap.get(Title));
			uAEVO.setOverview(soMap.get(Overview));
			uAEVO.setBuyerTc(soMap.get(Buyer_TC));
			uAEVO.setSpecialInst(soMap.get(Special_Instructions));
			uAEVO.setServiceDateType(soMap.get(Service_Date_Type));
			uAEVO.setServiceDate(soMap.get(Service_Date));
			uAEVO.setServiceTime(soMap.get(Service_Time));
			uAEVO.setServiceEndDate(soMap.get(Service_End_Date));
			uAEVO.setServiceEndTime(soMap.get(Service_End_Time));
			uAEVO.setProvConfTimeInd(soMap.get(Provider_To_Confirm_Time));
			uAEVO.setAttachment1(soMap.get(Attachment1));
			uAEVO.setDesc1(soMap.get(Desription1));
			uAEVO.setAttachment2(soMap.get(Attachment2));
			uAEVO.setDesc2(soMap.get(Desription2));
			uAEVO.setAttachment3(soMap.get(Attachment3));
			uAEVO.setDesc3(soMap.get(Desription3));
			uAEVO.setBrandingInfo(soMap.get(Branding_Information));
			uAEVO.setCustRef1Value(soMap.get(Custom_Reference_Value + "0"));
			uAEVO.setCustRef2Value(soMap.get(Custom_Reference_Value + "1"));
			uAEVO.setCustRef3Value(soMap.get(Custom_Reference_Value + "2"));
			uAEVO.setCustRef4Value(soMap.get(Custom_Reference_Value + "3"));
			uAEVO.setCustRef5Value(soMap.get(Custom_Reference_Value + "4"));
			uAEVO.setCustRef6Value(soMap.get(Custom_Reference_Value + "5"));
			uAEVO.setCustRef7Value(soMap.get(Custom_Reference_Value + "6"));
			uAEVO.setCustRef8Value(soMap.get(Custom_Reference_Value + "7"));
			uAEVO.setCustRef9Value(soMap.get(Custom_Reference_Value + "8"));
			uAEVO.setCustRef10Value(soMap.get(Custom_Reference_Value + "9"));
			uAEVO.setTemplateId(soMap.get(Template_Id));
			uAEVO.setLaborSpendLimit(soMap.get(Labor_Spend_Limit));
			uAEVO.setPartSpendLimit(soMap.get(Part_Spend_Limit));
		}

		return uAEVO;
	}

	public static Date getDateFromString(String dateStr) {
		Date date = null;
		String formatStr = null;
		if (dateStr.length() == 8) {
			formatStr = DATE_FORMAT_YY;
		} else if (dateStr.length() == 10) {
			formatStr = DATE_FORMAT_YYYY;
		} else {
			// Unexpected scenario; do not handle
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// logger.info("Caught Exception and ignoring",e);
		}
		return date;
	}

	private String getTimeFromFixedValues(String serviceTime) {
		String[] validTimeArray = new String[] {
				"12:00 AM", "12:15 AM", "12:30 AM", "12:45 AM", "01:00 AM", "01:15 AM", "01:30 AM", "01:45 AM", "02:00 AM", "02:15 AM", "02:30 AM", "02:45 AM",
				"03:00 AM", "03:15 AM", "03:30 AM", "03:45 AM", "04:00 AM", "04:15 AM", "04:30 AM", "04:45 AM", "05:00 AM", "05:15 AM", "05:30 AM", "05:45 AM",
				"06:00 AM", "06:15 AM", "06:30 AM", "06:45 AM", "07:00 AM", "07:15 AM", "07:30 AM", "07:45 AM", "08:00 AM", "08:15 AM", "08:30 AM", "08:45 AM",
				"09:00 AM", "09:15 AM", "09:30 AM", "09:45 AM", "10:00 AM", "10:15 AM", "10:30 AM", "10:45 AM", "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM",
				"12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM", "01:00 PM", "01:15 PM", "01:30 PM", "01:45 PM", "02:00 PM", "02:15 PM", "02:30 PM", "02:45 PM",
				"03:00 PM", "03:15 PM", "03:30 PM", "03:45 PM", "04:00 PM", "04:15 PM", "04:30 PM", "04:45 PM", "05:00 PM", "05:15 PM", "05:30 PM", "05:45 PM",
				"06:00 PM", "06:15 PM", "06:30 PM", "06:45 PM", "07:00 PM", "07:15 PM", "07:30 PM", "07:45 PM", "08:00 PM", "08:15 PM", "08:30 PM", "08:45 PM",
				"09:00 PM", "09:15 PM", "09:30 PM", "09:45 PM", "10:00 PM", "10:15 PM", "10:30 PM", "10:45 PM", "11:00 PM", "11:15 PM", "11:30 PM", "11:45 PM"};
		
		serviceTime = serviceTime.trim().toUpperCase();
		if (serviceTime.indexOf(":") == 1) {
			serviceTime = "0" + serviceTime;
		}
		
		List<String> validTimeList = Arrays.asList(validTimeArray);
		int foundIndex = validTimeList.indexOf(serviceTime);
		if (foundIndex >= 0) {
			return validTimeArray[foundIndex];
		} else {
			return null;
		}
	}
	
	public IBuyerFileUploadToolDAO getBFUTDaoImpl() {
		return BFUTDaoImpl;
	}

	public void setBFUTDaoImpl(IBuyerFileUploadToolDAO daoImpl) {
		this.BFUTDaoImpl = daoImpl;
	}

	public ServiceOrderBO getServiceOrderBOTarget() {
		return serviceOrderBOTarget;
	}

	public void setServiceOrderBOTarget(ServiceOrderBO serviceOrderBOTarget) {
		this.serviceOrderBOTarget = serviceOrderBOTarget;
	}
	
	public IBuyerSOTemplateBO getBuyerSOTemplateBO() {
		return buyerSOTemplateBO;
	}


	public void setBuyerSOTemplateBO(IBuyerSOTemplateBO buyerSOTemplateBO) {
		this.buyerSOTemplateBO = buyerSOTemplateBO;
	}


	public IDocumentBO getDocumentBO() {
		return documentBO;
	}


	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}


}
