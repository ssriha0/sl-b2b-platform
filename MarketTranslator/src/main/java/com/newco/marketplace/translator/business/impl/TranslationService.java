package com.newco.marketplace.translator.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.oms.OMSStagingBridge;
import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.business.ITranslationService;
import com.newco.marketplace.translator.business.PricingService;
import com.newco.marketplace.translator.business.ScheduleService;
import com.newco.marketplace.translator.dao.BuyerSoTemplate;
import com.newco.marketplace.translator.dao.IBuyerSoTemplateDAO;
import com.newco.marketplace.translator.dao.ILuServiceTypeTemplateDAO;
import com.newco.marketplace.translator.dao.ISkillTreeDAO;
import com.newco.marketplace.translator.dao.LuServiceTypeTemplate;
import com.newco.marketplace.translator.dao.SkillTree;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.dto.Task;
import com.newco.marketplace.translator.dto.TaskMapper;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.translator.util.TranslatorUtils;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.SpecialtyAddonRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;

public class TranslationService implements ITranslationService {

	private static final Logger logger = Logger.getLogger(TranslationService.class);

	public static final String STORE_CODE_KEY = "STORE NUMBER";
	public static final char KEY_PROMISE_DATE = 'P';
	private static final int DEFAULT_PRIMARY_SKILL_ID = 800;

	private IBuyerSoTemplateDAO buyerSOTemplateDAO;
	private ISkillTreeDAO skillTreeDAO;
	private OMSStagingBridge omsStagingBridge;

	//private StageOrderSEILocator stagingServiceLocator;

	// Constructors

	public TranslationService() {
		// intentionally blank
	}

	public TranslationService(OMSStagingBridge omsStagingBridge) {
		this.omsStagingBridge = omsStagingBridge;
	}


	public OMSStagingBridge getOmsStagingBridge() {
		return omsStagingBridge;
	}

	public void setOmsStagingBridge(OMSStagingBridge omsStagingBridge) {
		this.omsStagingBridge = omsStagingBridge;
	}

	/*private void setStagingWebServiceURL(StageOrderSEILocator stagingServiceLocator) {
		String stagingServiceEndPointUrl = Constants.STAGING_WS_URL;
		logger.info("[[[[[[===== Staging URL in StagingService = {" + stagingServiceEndPointUrl + "} =====]]]]]]");
		stagingServiceLocator.setStageOrderSEIHttpPortEndpointAddress(stagingServiceEndPointUrl);
	}*/

	public ClientServiceOrderNoteRequest translateClientNote(
			ClientServiceOrderNoteRequest request) throws Exception {
		// BuyerSkuService skuService = (BuyerSkuService)
		// SpringUtil.factory.getBean("BuyerSkuService");

		BuyerCredentials buyerCreds = authorizeBuyer(request.getUserId());
		try {
			request.setBuyerId(buyerCreds.getBuyerID());
			// The password can not be decrypted - the passwordFlag=internal
			// tells service live
			// not to re-encrypt the password.
			request.setPassword(buyerCreds.getPassword());
			request.setPasswordFlag("internal");
			request.setRoleId(Constants.BUYER_ROLE_ID);
			request.setCreatedBy(Constants.WEBSERVICE);
			request.setNoteType(Constants.SO_NOTE_PUBLIC_ACCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return request;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.ITranslationService#translateIncidentAck(com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest)
	 */
	public UpdateIncidentTrackingRequest translateIncidentAck(
			UpdateIncidentTrackingRequest request) throws Exception {
		
		BuyerCredentials buyerCreds = authorizeBuyer(request.getUserId());
		try {
			request.setBuyerId(buyerCreds.getBuyerID());
			// The password can not be decrypted - the passwordFlag=internal
			// tells service live
			// not to re-encrypt the password.
			request.setPassword(buyerCreds.getPassword());
			request.setPasswordFlag("internal");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return request;
	}

	/**
	 * Take a raw request and perform translations such as sku to tasks and
	 * pricing.
	 */
	public CreateDraftRequest translateDraft(CreateDraftRequest request,
			List<SkuPrice> skus, String client) throws Exception {
		List<NoteRequest> notes = new ArrayList<NoteRequest>();
		if (request.getNotes() != null) {
			for (int noteIndex = 0; noteIndex < request.getNotes().size(); noteIndex++) {
				notes.add(request.getNotes().get(noteIndex));
			}
		}

		BuyerSkuService skuService = (BuyerSkuService) SpringUtil.factory
				.getBean("BuyerSkuService");

		BuyerCredentials buyerCreds = authorizeBuyer(request.getUserId());
		Boolean partPickupLoctionAvailable = Boolean.FALSE;
		if (buyerCreds == null) {
			return request;
		}
		request.setBuyerId(buyerCreds.getBuyerID());
		// The password can not be decrypted - the passwordFlag=internal
		// tells service live
		// not to re-encrypt the password.
		request.setPassword(buyerCreds.getPassword());
		request.setPasswordFlag("internal");
		if (request.getParts() != null) {
			for (CreatePartRequest part : request.getParts()) {
				part.setPassword(buyerCreds.getPassword());
				part.setBuyerId(buyerCreds.getBuyerID());
				part.setUserId(buyerCreds.getUsername());
				if (part.getPickupLocation() != null
						&& part.getPickupLocation().getZip() != null) {
					partPickupLoctionAvailable = Boolean.TRUE;
				}
			}
		}

		// CreateTaskRequest will contain the descriptive data for the sku
		// (description, etc)
		// SkuPrice will contain the margin and lead time for the sku

		List<Task> tasks = new ArrayList<Task>();
		CreateTaskRequest[] draftTasks = request.getTasks().toArray(new CreateTaskRequest[0]);
		for (int i = 0; i < draftTasks.length; i++) {
			tasks.add(TaskMapper.mapRequestToDTO(draftTasks[i], skus));
			request.setSowDs((request.getSowDs() != null ? request.getSowDs()
					: "")
					+ draftTasks[i].getTaskComments() + "\n");
		}

		// translate job codes more than one task maybe created from each
		// jobcode(sku)
		tasks = skuService.translateSKUs(tasks);
		
		//Maps main service category, category and sub category as custom reference fields
		String serviceTypeDescr = this.getServiceTypeDescr(tasks);
		request = TaskMapper.mapTaskCustomRefs(request,tasks,serviceTypeDescr);
		
		Boolean allTasksTranslated = mapTasksToRequest(request, tasks, notes);

		PricingService pricingService = (PricingService) SpringUtil.factory
				.getBean("PricingService" + client);
		pricingService.priceOrder(request, allTasksTranslated.booleanValue(), notes, skus);

		// For error logging
		IStagingService stagingService = (IStagingService) SpringUtil.factory
				.getBean(Constants.SL_STAGING_SERVICE);
		String orderNumber = TranslatorUtils
				.getOMSOrderNumberFromReferenceFields(request);
		String unitNumber = TranslatorUtils
				.getOMSUnitNumberFromReferenceFields(request);

		ScheduleService scheduleService = (ScheduleService) SpringUtil.factory
				.getBean("ScheduleService" + client);
		Boolean serviceOrderScheduled = Boolean.FALSE;
		if (request.getServiceLocation() != null
				&& request.getServiceLocation().getZip() != null) {
			try {
				serviceOrderScheduled = Boolean.valueOf(scheduleService.scheduleOrder(request, skus, notes,client));
			} catch (Exception e) {
				logger.error("Error Scheduling Order", e);
				NoteRequest note = new NoteRequest();
				note.setSubject("Error scheduling Service Order");
				note
						.setNote("Service Order could not be scheduled. Exception: "
								+ e.getMessage());
				notes.add(note);
				request.setAutoRoute(new Boolean(false));

				stagingService.persistErrors(Constants.EX_SCHEDULING_ERROR,
						Constants.EX_SCHEDULING_ERROR_MESSAGE, orderNumber,
						unitNumber);
				throw e;
			}
		} else {
			logger.error("Error Scheduling Order");
			NoteRequest note = new NoteRequest();
			note.setSubject("Error scheduling Service Order");
			note
					.setNote("Service Order could not be scheduled. Service location can not be found");
			notes.add(note);
			request.setAutoRoute(new Boolean(false));
			stagingService.persistErrors(Constants.EX_SCHEDULING_ERROR,
					Constants.EX_SCHEDULING_ERROR_MESSAGE, orderNumber,
					unitNumber);
		}
		// the request will still contain the source key as the first char of
		// the date
		// if it could not be scheduled - strip it out
		if (!serviceOrderScheduled.booleanValue()) {
			request.setAppointmentStartDate(request.getAppointmentStartDate()
					.substring(1));
		}

		Boolean primarySkillSet = setPrimarySkillCatIdInRequest(request, tasks,
				buyerCreds);
		if (!primarySkillSet.booleanValue()) {
			logger.error("Error setting primary skill node id");
			NoteRequest note = new NoteRequest();
			note.setSubject("Error setting primary skill node id");
			note
					.setNote("Error setting primary skill node id. A default has been set. No template name was set.");
			notes.add(note);
			request.setPrimarySkillCatId(new Integer(DEFAULT_PRIMARY_SKILL_ID));
			request.setSowTitle("Need primary skill category to set title");
			request.setAutoRoute(new Boolean(false));
			stagingService.persistErrors(Constants.EX_PRIMARY_SKILL_ERROR, Constants.EX_PRIMARY_SKILL_ERROR_MESSAGE,orderNumber,unitNumber);
			// set a default skill for injection
			for (int index = 0; index < request.getTasks().size(); index++) {
				CreateTaskRequest task = request.getTasks().get(index);
				if (task.getSkillNodeId().intValue() == 0) {
					task.setSkillNodeId(new Integer(19));
					task.setTaskName(task.getTaskName()+Constants.UNMAPPED_TASK);
				}
			}
		}

		// Enhancement by Sarah: Delivery task should be created if part pickup
		// location is available; AND the delivery does not already exists
		createDeliveryTaskIfRequired(request, partPickupLoctionAvailable);

		
		if (!notes.isEmpty()) {
			/*int i = 0;
			NoteRequest[] notesRequests = new NoteRequest[notes.size()];
			for (NoteRequest note : notes) {
				notesRequests[i] = note;
				notesRequests[i].setPassword(buyerCreds.getPassword());
				notesRequests[i].setBuyerId(buyerCreds.getBuyerID());
				notesRequests[i].setUserId(buyerCreds.getUsername());
				i++;
			}
			*/
			request.setNotes(notes);
		}

		return request;
	}

	// TODO fix this function
	private void createDeliveryTaskIfRequired(CreateDraftRequest request,
			Boolean partPickupLoctionAvailable) throws Exception {

		ILuServiceTypeTemplateDAO luServiceTypeTemplateDao = (ILuServiceTypeTemplateDAO) SpringUtil.factory
				.getBean("LuServiceTypeTemplateDAO");

		Boolean deliveryTaskExists = Boolean.FALSE;
		CreateTaskRequest[] tasks = request.getTasks().toArray(new CreateTaskRequest[0]);
		if (tasks != null) {
			for (CreateTaskRequest task : tasks) {
				LuServiceTypeTemplate serviceTypeTemplate = luServiceTypeTemplateDao
						.findById(task.getServiceTypeTempleteId());
				if (serviceTypeTemplate != null
						&& serviceTypeTemplate.getDescr() != null
						&& serviceTypeTemplate.getDescr().equalsIgnoreCase(
								Constants.DELIVERY)) {
					deliveryTaskExists = Boolean.TRUE;
				}
			}
		}

		if (!deliveryTaskExists.booleanValue() && partPickupLoctionAvailable.booleanValue()) {
			CreateTaskRequest primaryTask = null;
			for (CreateTaskRequest task : request.getTasks()) {
				if (task.getServiceTypeId() != null
						&& task.getServiceTypeId().intValue() == Constants.PRIMARY_JOB_CODE) {
					primaryTask = task;
					break;
				}
			}

			if (primaryTask != null) {

				// Construct new CreateTaskRequest
				CreateTaskRequest deliveryTask = new CreateTaskRequest();
				deliveryTask.setTaskName(OrderConstants.DELIVERY_TASK_NAME);
				deliveryTask.setTaskComments(Constants.DELIVERY_TASK_COMMENTS);
				deliveryTask.setServiceTypeId(Integer.valueOf(Constants.ALTERNAME_JOB_CODE));
				deliveryTask.setPartsSuppliedId(primaryTask
						.getPartsSuppliedId());
				deliveryTask.setSkillNodeId(primaryTask.getSkillNodeId());
				deliveryTask.setSubCategoryNodeId(primaryTask
						.getSubCategoryNodeId());

				LuServiceTypeTemplate primaryServiceTypeTemplate = luServiceTypeTemplateDao
						.findByTemplateIdAndNodeId(request
								.getPrimarySkillCatId(), Constants.DELIVERY);
				deliveryTask
						.setServiceTypeTempleteId(primaryServiceTypeTemplate
								.getServiceTypeTemplateId());

				// Add this new delivery task to the request
				CreateTaskRequest[] newTasks = new CreateTaskRequest[tasks.length + 1];
				System.arraycopy(tasks, 0, newTasks, 0, tasks.length);
				newTasks[tasks.length] = deliveryTask;
				// TODO : check If we really need list and array conversion ?
				request.setTasks(Arrays.asList(newTasks));

			} else {
				// Unexpected scenario!
				logger
						.error("Unexpected scenario: No task available in the order with primary job code.");
			}
		}
	}

	public int getauthorizeBuyerId(String userName) throws Exception {
		int buyerId = 0;
		BuyerCredentials buyerCreds = authorizeBuyer(userName);
		if (buyerCreds != null)
			buyerId = buyerCreds.getBuyerID().intValue();
		return buyerId;

	}

	public BuyerCredentials authorizeBuyer(String userName) throws Exception {
		BuyerService buyerService = (BuyerService) SpringUtil.factory
				.getBean("BuyerService");
		BuyerCredentials buyerCreds = buyerService
				.getBuyerCredentials(userName);
		return buyerCreds;
	}

	public Boolean mapTasksToRequest(CreateDraftRequest request, List<Task> tasks, List<NoteRequest> notes) throws Exception {

		IStagingService stagingService = (IStagingService) SpringUtil.factory.getBean(Constants.SL_STAGING_SERVICE);
		String orderNumber = TranslatorUtils.getOMSOrderNumberFromReferenceFields(request);
		String unitNumber = TranslatorUtils.getOMSUnitNumberFromReferenceFields(request);

		CreateTaskRequest[] translatedTasks = new CreateTaskRequest[tasks
				.size()];
		Boolean allTranslated = Boolean.TRUE;
		int taskIndex = 0;
		for (Task task : tasks) {
			CreateTaskRequest taskRequest = new CreateTaskRequest();
			if (task.getTaskNodeID().intValue() == 0) {
				NoteRequest note = new NoteRequest();
				note.setSubject("Error translating SKU: " + task.getSku());
				note.setNote("SKU " + task.getSku()
						+ " could not be translated from request.");
				notes.add(note);
				task.setTaskNodeID(new Integer(0));
				request.setAutoRoute(new Boolean(false));
				allTranslated = Boolean.FALSE;
				stagingService.persistErrors(Constants.EX_SKU_ERROR, Constants.EX_SKU_ERROR_MESSAGE, orderNumber, unitNumber);
			}
			if (task.getTaskSubCatNodeID() != null) {
				taskRequest.setSubCategoryNodeId(task.getTaskSubCatNodeID());
			}
			taskRequest.setSkillNodeId(task.getTaskNodeID());
			taskRequest.setTaskName(task.getTitle());
			taskRequest.setTaskComments(task.getComments());
			taskRequest.setServiceTypeTempleteId(task.getSkillID());
			taskRequest.setServiceTypeId(task.getServiceTypeId());
			taskRequest.setJobCode(task.getSku());			
			translatedTasks[taskIndex] = taskRequest;
			taskIndex++;
		}
		
		// TODO : check If we really need list and array conversion ? 
		request.setTasks(Arrays.asList(translatedTasks));
		return allTranslated;
	}

	/**
	 * Per business this might change and be determined by the SST web service.
	 * If that is the case the ESB layer would control this setting.
	 * 
	 * @param request
	 * @param tasks
	 * @param buyerCreds
	 * @return
	 */
	public Boolean setPrimarySkillCatIdInRequest(CreateDraftRequest request,
			List<Task> tasks, BuyerCredentials buyerCreds) throws Exception {
		Boolean primaryFound = Boolean.FALSE;
		for (Task task : tasks) {
			if (task.isDefaultTask()) {
				SkillTree skillTree = skillTreeDAO.findById(task
						.getTaskNodeID());
				if (skillTree != null) {
					request.setPrimarySkillCatId(skillTree.getParentNode());
					request
							.setSowTitle(task.getTitle()
									+ (StringUtils.isNotBlank(request
											.getSowTitle()) ? " "
											+ request.getSowTitle() : ""));
					request.setTemplateName(getTemplateName(task.getSku(),
							buyerCreds.getBuyerID(), task.getSpecialtyCode()));
					primaryFound = Boolean.TRUE;
				} else {
					// If left null instead of "", then later database insert
					// query for custom ref would fail
					request.setTemplateName(StringUtils.EMPTY);
				}
				break;
			}
		}
		return primaryFound;
	}

	public String getTemplateName(String sku, Integer buyerID,
			String specialtyCode) throws Exception {
		BuyerSoTemplate template = getBuyerSOTemplateDAO().findBySkuAndBuyerID(sku, buyerID, specialtyCode);
		if (template != null) {
			return template.getTemplateName();
		}
		return StringUtils.EMPTY;
	}

	public void createSOUpsells(CreateDraftRequest request) {
		// get the order number
		String orderNumber = "";
		String unitNo = "";
		// StageOrderSEI stagingService = (StageOrderSEI)
		// SpringUtil.factory.getBean(Constants.OMS_STAGING_SERVICE);
		for (CustomRef custRef : request.getCustomRef()) {
			if ("ORDER NUMBER".equals(custRef.getKey())) {
				orderNumber = custRef.getValue();
			} else if ("UNIT NUMBER".equals(custRef.getKey())) {
				unitNo = custRef.getValue();
			}
		}
		
		try {

			// Convert ShcOrderAddOn list  to ShcOrderAddOn Array
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			ShcOrderAddOn [] addons = omsStagingBridge.getShcOrderDetails(orderNumber, unitNo).toArray(new ShcOrderAddOn[0]);
			if(addons != null) {
				mapShcOrderAddonsToRequest(request, addons);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void mapShcOrderAddonsToRequest(CreateDraftRequest request, ShcOrderAddOn[] shcOrderAddons) {
		SpecialtyAddonRequest[] addons = new SpecialtyAddonRequest[shcOrderAddons.length];
		for (int i = 0; i < shcOrderAddons.length; i++) {
			ShcOrderAddOn shcOrderAddon = shcOrderAddons[i];
			double retailPrice = shcOrderAddon.getRetailPrice() != null ? shcOrderAddon
					.getRetailPrice().doubleValue()
					: 0.00;
			double margin = shcOrderAddon.getMargin() != null ? shcOrderAddon
					.getMargin().doubleValue() : 0.00;
			SpecialtyAddonRequest addon = new SpecialtyAddonRequest();
			addon.setDescription(shcOrderAddon.getSpecialtyAddOn()
					.getLongDescription());
			addon.setScopeOfWork(shcOrderAddon.getSpecialtyAddOn()
					.getInclusionDescription());
			addon.setSku(shcOrderAddon.getSpecialtyAddOn().getStockNumber());
			addon.setRetailPrice(retailPrice);
			addon.setMargin(margin);
			addon.setCoverage(shcOrderAddon.getCoverage());
			addon.setQuantity(shcOrderAddon.getQty().intValue());
			boolean miscInd = false;
			if (shcOrderAddon.getMiscInd().intValue() == 1)
				miscInd = true;
			addon.setMiscInd(miscInd);
			addons[i] = addon;

		}

		// TODO : check If we really need list and array conversion ?
		request.setAddons(Arrays.asList(addons));

	}
	
	/**
	 * Gets the serviceType description for a selected skillID(for the main job code)
	 * @param tasks
	 * @return serviceTypeDescr
	 * @throws Exception
	 */
	private String  getServiceTypeDescr(List<Task> tasks) throws Exception {
		ILuServiceTypeTemplateDAO luServiceTypeTemplateDao = (ILuServiceTypeTemplateDAO) SpringUtil.factory
				.getBean("LuServiceTypeTemplateDAO");
		String serviceTypeDescr = null;
		for (Task task : tasks) {
			if (task != null && task.getServiceTypeId().equals(Integer.valueOf(Constants.PRIMARY_JOB_CODE))
					&& task.getSkillID() != null) {
				LuServiceTypeTemplate serviceTypeTemplate = luServiceTypeTemplateDao.findById(task.getSkillID());
				serviceTypeDescr = serviceTypeTemplate.getDescr();
				break;
			}
		}
		return serviceTypeDescr;
	}	
		
	public IBuyerSoTemplateDAO getBuyerSOTemplateDAO() {
		return buyerSOTemplateDAO;
	}

	public void setBuyerSOTemplateDAO(IBuyerSoTemplateDAO buyerSOTemplateDAO) {
		this.buyerSOTemplateDAO = buyerSOTemplateDAO;
	}

	public ISkillTreeDAO getSkillTreeDAO() {
		return skillTreeDAO;
	}

	public void setSkillTreeDAO(ISkillTreeDAO skillTreeDAO) {
		this.skillTreeDAO = skillTreeDAO;
	}

}
