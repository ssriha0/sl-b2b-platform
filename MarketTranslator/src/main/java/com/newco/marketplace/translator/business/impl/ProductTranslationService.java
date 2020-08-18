/*
 * @(#)ProductTranslationService.java
 *
 * Copyright 2010 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.translator.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.business.IBuyerService;
import com.newco.marketplace.translator.business.IZipService;
import com.newco.marketplace.translator.dao.BuyerSku;
import com.newco.marketplace.translator.dao.BuyerSkuAttributes;
import com.newco.marketplace.translator.dao.BuyerSkuCategory;
import com.newco.marketplace.translator.dao.BuyerSkuTask;
import com.newco.marketplace.translator.dao.BuyerSkuTaskDAO;
import com.newco.marketplace.translator.dao.IBuyerSkuCategoryDAO;
import com.newco.marketplace.translator.dao.IBuyerSkuDAO;
import com.newco.marketplace.translator.dao.IBuyerSkuTaskDAO;
import com.newco.marketplace.translator.dao.ISkillTreeDAO;
import com.newco.marketplace.translator.dao.SkillTree;
import com.newco.marketplace.translator.exception.InvalidZipCodeException;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.TranslatorUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.SpecialtyAddonRequest;

/**
 * This is the parent class to translate any injected order. Subclass will implement the client
 * specific translation rules.
 *
 */
public abstract class ProductTranslationService {
	CreateDraftRequest request;
	IApplicationPropertiesService applicationPropertiesService;
	IBuyerSkuDAO buyerSkuDao;
	IBuyerSkuTaskDAO buyerSkuTaskDao;
	IBuyerSkuCategoryDAO  buyerSkuCategoryDao;
	IBuyerService buyerService;
	ISkillTreeDAO skillDao;
	IZipService zipService;
	BuyerSku mainSku;
	BuyerSkuTask mainSkuTask;
	String client;
	List<NoteRequest> notes = new ArrayList<NoteRequest>();
	
	private static final Logger logger = Logger.getLogger(ProductTranslationService.class);
	
	public final void translateOrder(CreateDraftRequest cdr, String client) throws Exception{
		this.request = cdr;
		this.client = client;
		if (request.getNotes() != null) {
			for (int noteIndex = 0; noteIndex < request.getNotes().size(); noteIndex++) {
				notes.add(request.getNotes().get(noteIndex));
			}
		}		
		determineBuyer();
		determineMainSkuAndTask();
		determineTitleAndOverview();
		determinePrimaryCategory();
		if(mainSku!= null){
			determineTemplate();
			mapSkuToTask();
			determinePricing();
			determineAddons();
		}
		determineFinalSchedule();
		determineMisc();
		if (!notes.isEmpty()) {
			request.setNotes(notes);
		}
	}
	
	
	public final void translateUpdateOrder(CreateDraftRequest cdr) throws Exception{
		this.request = cdr;
		if(request.getNotes()!= null){
			for (int noteIndex = 0; noteIndex < request.getNotes().size(); noteIndex++) {
				notes.add(request.getNotes().get(noteIndex));
			}
		}
	    determineBuyer();
	}	
		
	public abstract void determineMisc();
	
	public abstract void determineTitleAndOverview();

	public void determinePrimaryCategory() {
		if (mainSkuTask != null) {
			Integer rootNodeId = mainSkuTask.getSkillTree().getRootNodeId();
			request.setPrimarySkillCatId(rootNodeId);
		}
		else{
			logger.error("Error setting primary skill node id");
			NoteRequest note = new NoteRequest();
			note.setSubject("Error setting primary skill node id");
			note.setNote("Error setting primary skill node id. A default has been set. No template name was set.");
			notes.add(note);
			request.setPrimarySkillCatId(new Integer(Constants.DEFAULT_PRIMARY_SKILL_ID));
			request.setSowTitle("Need primary skill category to set title");
			request.setAutoRoute(new Boolean(false));
		}
	}

	public abstract void determineMainSkuAndTask()throws Exception;

	/**
	 * Subclasses must implement this method to determine the template
	 */
	public abstract void determineBuyer()throws Exception;

	/**
	 * Subclasses must implement this method to determine the template
	 */
	public abstract void determineTemplate()throws Exception;
	
	/**
	 * No overwriting is allowed. There should be only way to map sku to task
	 */
	final void mapSkuToTask() {
		List<CreateTaskRequest> taskRequests = new ArrayList<CreateTaskRequest>();
		Boolean primaryTaskNotFound = Boolean.TRUE;
		for (CreateTaskRequest  orgTaskReq : request.getTasks()) {
			BuyerSku buyerSku = buyerSkuDao.findBySkuAndBuyerID(orgTaskReq.getJobCode(), request.getBuyerId());
			List<BuyerSkuTask> skuTasks = buyerSkuTaskDao.findByProperty(BuyerSkuTaskDAO.BUYER_SKU, buyerSku);
			for (BuyerSkuTask bst : skuTasks) {
				CreateTaskRequest ctr = new CreateTaskRequest();
				String taskName = bst.getTaskName();
				//Restricting to 35 character since frontend does not allow more than 35 characters
				if (StringUtils.isNotBlank(taskName) && taskName.length() > 35) {
					taskName = taskName.substring(0, 35);
				}
				ctr.setTaskName(taskName);
				ctr.setTaskComments(bst.getTaskComments());
				ctr.setServiceTypeTempleteId(bst.getLuServiceTypeTemplate().getServiceTypeTemplateId());
				if (bst.getSkillTree().getLevel().intValue() == Constants.SUB_CAT_SKILL_LEVEL) {
					ctr.setSubCategoryNodeId(bst.getSkillTree().getNodeId());
					ctr.setSkillNodeId(bst.getSkillTree().getParentNode());
				}
				else
					ctr.setSkillNodeId(bst.getSkillTree().getNodeId());
				ctr.setJobCode(buyerSku.getSku());
				if (primaryTaskNotFound &&  buyerSku.getSku().equals(mainSku.getSku())) {
					ctr.setServiceTypeId(Constants.PRIMARY_JOB_CODE);
					primaryTaskNotFound = Boolean.FALSE;
				}
				taskRequests.add(ctr);
			}
		}
		request.setTasks(taskRequests);		
	}
	
	public void determinePricing(){
		Double orderPrice = 0.0;
		if (mainSku.getBidPriceSchema() != null && 
				mainSku.getBidPriceSchema().equalsIgnoreCase(Constants.MARGIN_ADJUST_SCHEMA)) {
			if (mainSku.getRetailPrice() != null && mainSku.getBidMargin() != null) {
				orderPrice = mainSku.getRetailPrice()*(1- mainSku.getBidMargin());
			}
		}
		else if (mainSku.getBidPrice() != null){
			orderPrice = mainSku.getBidPrice();
		}
		request.setSpendLimitLabor(MoneyUtil.getRoundedMoney(orderPrice));
		
		if (orderPrice < 1.0) {
			NoteRequest note = new NoteRequest();
			note.setSubject("Error pricing Service Order");
			note.setNote("Service Order could not be priced. Please check the Buyer Sku configuration");
			notes.add(note);
			request.setSpendLimitLabor(Double.valueOf(0.0));
			request.setAutoRoute(new Boolean(false));
		}		
	}
	
	/**
	 * No overwriting is allowed. There should be only way to get the list of add ons
	 */
	final void determineAddons() {
		BuyerSkuCategory skuCategory = mainSku.getBuyerSkuCategory();
		List<SpecialtyAddonRequest> addOnsList = new ArrayList<SpecialtyAddonRequest>();
		List<BuyerSku> addOnSkus  = buyerSkuDao.findAddOnsBySkuCategory(skuCategory);
		
		if(addOnSkus!= null && addOnSkus.size()>0){
			for(BuyerSku eachAddOn : addOnSkus){
				SpecialtyAddonRequest addOn = new SpecialtyAddonRequest();
				populateAddOn(eachAddOn, addOn);
				addOnsList.add(addOn);
			}
		}
		
		
		/* If mainSku is required Add on , then default quantity to 1*/
		Set<BuyerSkuAttributes> skuAttributes = mainSku.getBuyerSkuAttributeses();
		Iterator<BuyerSkuAttributes> iterator = skuAttributes.iterator();
		while(iterator.hasNext()){
			BuyerSkuAttributes tempAttribute = (BuyerSkuAttributes)iterator.next();
			if(tempAttribute.getId()!= null &&  (Constants.ADD_ON_ATTRIBUTE_TYPE_REQ.equals(tempAttribute.getId().getAttributeType()) ) ){
				SpecialtyAddonRequest reqAddOn = new SpecialtyAddonRequest();
				populateAddOn(mainSku, reqAddOn);
				reqAddOn.setQuantity(Constants.DEFAULT_REQ_ADDON_QTY);
				reqAddOn.setCoverage(Constants.CALL_COLLECT_COVERAGE_TYPE);
				addOnsList.add(reqAddOn);
				break;
			}
		}

		
		request.setAddons(addOnsList);
		
	}

	private void populateAddOn(BuyerSku eachAddOn, SpecialtyAddonRequest addOn) {
		//addOn.setCoverage(eachAddOn.get);
		addOn.setDescription(eachAddOn.getSkuDescription());
		addOn.setMargin(eachAddOn.getBidMargin());
		if(eachAddOn.getPriceType().equals(Constants.SKU_PRICE_TYPE_VARIABLE)){
			addOn.setMiscInd(true);
		}else{
			addOn.setMiscInd(false);
		}
		addOn.setRetailPrice(eachAddOn.getRetailPrice());
		addOn.setSku(eachAddOn.getSku());
		
		/* get the task comments from of the sku task and set to scope of work */
		String taskComments = StringUtils.EMPTY;
		List<BuyerSkuTask> skuTasks = buyerSkuTaskDao.findByProperty(BuyerSkuTaskDAO.BUYER_SKU, eachAddOn);
		if(skuTasks!= null & skuTasks.size()>0){
			 BuyerSkuTask skuTask = skuTasks.get(0);
			 taskComments = skuTask.getTaskComments();
		}
		addOn.setScopeOfWork(taskComments);
	}
	
	/**
	 * Maps main service category, category and sub category as custom reference fields
	 * @param request
	 * @param tasks
	 * @param serviceTypeDescr
	 * return request
	 */
	public void mapCustomRefs() {
		List<CustomRef> refsToAdd = new ArrayList<CustomRef>();
		List<CustomRef> customRefs = request.getCustomRef();
		//Assigning this to a new list as it is immutable
		refsToAdd.addAll(customRefs);
		
		if (mainSkuTask != null) {
			if (request.getPrimarySkillCatId() != null) {
				SkillTree primarySkill = skillDao.findById(request.getPrimarySkillCatId());
				CustomRef mainServiceCategory = new CustomRef();
				mainServiceCategory.setKey(Constants.CUSTOM_REF_MAIN_SERVICE_CAT);
				mainServiceCategory.setValue(primarySkill.getNodeName());
				refsToAdd.add(mainServiceCategory);
			}
			if (mainSkuTask.getLuServiceTypeTemplate() != null) {
				CustomRef skill = new CustomRef();
				skill.setKey(Constants.CUSTOM_REF_SKILL);
				skill.setValue(mainSkuTask.getLuServiceTypeTemplate().getDescr());
				refsToAdd.add(skill);
			}
			if (mainSkuTask.getSkillTree().getLevel().intValue() == Constants.SUB_CAT_SKILL_LEVEL) {
				SkillTree categorySkill = skillDao.findById(mainSkuTask.getSkillTree().getParentNode());
				CustomRef category = new CustomRef();
				category.setKey(Constants.CUSTOM_REF_CATEGORY);
				category.setValue(categorySkill.getNodeName());
				refsToAdd.add(category);
				SkillTree subCatSkill = skillDao.findById(mainSkuTask.getSkillTree().getNodeId());
				CustomRef subCategory = new CustomRef();
				subCategory.setKey(Constants.CUSTOM_REF_SUB_CATEGORY);
				subCategory.setValue(subCatSkill.getNodeName());
				refsToAdd.add(subCategory);
				
			}
			else{
				SkillTree categorySkill = skillDao.findById(mainSkuTask.getSkillTree().getNodeId());
				CustomRef category = new CustomRef();
				category.setKey(Constants.CUSTOM_REF_CATEGORY);
				category.setValue(categorySkill.getNodeName());
				refsToAdd.add(category);
			}
				
			
		}
		if (StringUtils.isNotEmpty(request.getTemplateName())) {
			CustomRef template = new CustomRef();
			template.setKey(Constants.CUSTOM_REF_TEMPLATE_NAME);
			template.setValue(request.getTemplateName());
			refsToAdd.add(template);
		}
		
		request.setCustomRef(refsToAdd);
	}
	
	/**
	 * Subclasses must implement this method to determine the template
	 */
	public abstract void determineFinalSchedule();

	/**
	 * Get the offset for the timezone
	 * 
	 * @return
	 */
	public int getTimeZoneOffset() {
		TimeZone localTimezone = null;
		LocationRequest serviceLocation = request.getServiceLocation();
		String zip = serviceLocation.getZip();
		try {
			localTimezone = zipService.getTimeZoneByZip(zip);
		} catch (InvalidZipCodeException e1) {
			//Generate a note for the ServiceOrder
			//Set AutoRoute action to false
			// Generate warning mail to ProdSupport for adding the missing zipcode data in ServiceLive DB
			localTimezone = TranslatorUtils.zipCodeNotFoundActions(request, notes,client);
		}
		int offset = Math.round(localTimezone.getOffset((new Date()).getTime()) / (1000*60*60));
		return offset;
	}
	
	/**
	 * @return the applicationPropertiesService
	 */
	public IApplicationPropertiesService getApplicationPropertiesService() {
		return applicationPropertiesService;
	}

	/**
	 * @param applicationPropertiesService the applicationPropertiesService to set
	 */
	public void setApplicationPropertiesService(
			IApplicationPropertiesService applicationPropertiesService) {
		this.applicationPropertiesService = applicationPropertiesService;
	}

	/**
	 * @return the buyerSkuDao
	 */
	public IBuyerSkuDAO getBuyerSkuDao() {
		return buyerSkuDao;
	}

	/**
	 * @param buyerSkuDao the buyerSkuDao to set
	 */
	public void setBuyerSkuDao(IBuyerSkuDAO buyerSkuDao) {
		this.buyerSkuDao = buyerSkuDao;
	}

	/**
	 * @return the buyerSkuTaskDao
	 */
	public IBuyerSkuTaskDAO getBuyerSkuTaskDao() {
		return buyerSkuTaskDao;
	}

	/**
	 * @param buyerSkuTaskDao the buyerSkuTaskDao to set
	 */
	public void setBuyerSkuTaskDao(IBuyerSkuTaskDAO buyerSkuTaskDao) {
		this.buyerSkuTaskDao = buyerSkuTaskDao;
	}

	public IBuyerSkuCategoryDAO getBuyerSkuCategoryDao() {
		return buyerSkuCategoryDao;
	}

	public void setBuyerSkuCategoryDao(IBuyerSkuCategoryDAO buyerSkuCategoryDao) {
		this.buyerSkuCategoryDao = buyerSkuCategoryDao;
	}

	/**
	 * @return the buyerService
	 */
	public IBuyerService getBuyerService() {
		return buyerService;
	}

	/**
	 * @param buyerService the buyerService to set
	 */
	public void setBuyerService(IBuyerService buyerService) {
		this.buyerService = buyerService;
	}

	/**
	 * @return the skillDao
	 */
	public ISkillTreeDAO getSkillDao() {
		return skillDao;
	}

	/**
	 * @param skillDao the skillDao to set
	 */
	public void setSkillDao(ISkillTreeDAO skillDao) {
		this.skillDao = skillDao;
	}

	/**
	 * @return the zipService
	 */
	public IZipService getZipService() {
		return zipService;
	}

	/**
	 * @param zipService the zipService to set
	 */
	public void setZipService(IZipService zipService) {
		this.zipService = zipService;
	}

	
	
	
}