package com.newco.marketplace.web.action.skuMaintenance;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;


import org.apache.log4j.Logger;

import flexjson.JSONSerializer;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SkuMaintenanceVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.sku.maintenance.BuyerSKUEntity;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;
import com.servicelive.domain.sku.maintenance.BuyerSkuTask;
import com.servicelive.domain.sku.maintenance.LuServiceTypeTemplate;
import com.servicelive.domain.sku.maintenance.SkillTree;
import com.servicelive.domain.sku.maintenance.BuyerSoTemplate;
import com.servicelive.domain.sku.maintenance.SkuCategoryHistoy;
import com.servicelive.domain.sku.maintenance.SkuHistory;
import com.servicelive.sku.maintenance.services.MaintainSkuService;

/**
 * Action  Class for SKU Maintenance
 * @(#)skuMaintainceAction.java
 *
 * Copyright 2009 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 *  
 */
public class SkuMaintenanceAction extends SLBaseAction implements ModelDriven<SkuMaintenanceVO> {
	/**
	 * 
	 */
	Logger logger = Logger.getLogger(SkuMaintenanceAction.class);	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private MaintainSkuService maintainSkuService;
	/**
	 * @return
	 */

	private SkuMaintenanceVO skuMaintenanceVO = new SkuMaintenanceVO ();
	public String displayPage() throws Exception {	

		String updateSkuCategory = (String)getSession().getAttribute("updateSkuCategory");
		String categoryName = (String)getSession().getAttribute("newCategoryName");
		String skuName = (String)getSession().getAttribute("newSkuName");
		String addSkuCategory = (String)getSession().getAttribute("addSkuCategory");	
		
		//setting values in request and removing from session
		setAttribute("updateSkuCategory",updateSkuCategory);
		setAttribute("newCategoryName",categoryName);
		setAttribute("newSkuName",skuName);
		setAttribute("addSkuCategory",addSkuCategory);	
		
		getSession().removeAttribute("updateSkuCategory");
		getSession().removeAttribute("newCategoryName");
		getSession().removeAttribute("newSkuName");
		getSession().removeAttribute("addSkuCategory");
		
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		if(soContxt == null)
		{
			return "homepage";
		}
		int buyerId = soContxt.getCompanyId();
		
		try {
			List<BuyerSkuCategory> buyerSkuCategoryList = maintainSkuService.fetchBuyerSkuCategories(buyerId);
			getSession().setAttribute("buyerSkuCategoryList",buyerSkuCategoryList);
		
		} catch (Exception e) {			
			logger.error("Caught exception in  SkuMaintenanceAction - displayPage() - "+e);
		}
		return SUCCESS;		
	}
	
	public String displaySkuNameByCategory() throws Exception {		
		   Integer categoryId =Integer.parseInt(getParameter("categoryId"));

		try {
			List<BuyerSKUEntity> buyerSkuNameList = maintainSkuService.fetchBuyerSkuNameByCategory(categoryId,null,null);
			if(buyerSkuNameList != null && buyerSkuNameList.size() != 0)
			{	
				getSession().setAttribute("buyerSkuNameList",buyerSkuNameList);
				Integer noOfSkus = buyerSkuNameList.size();
				getSession().setAttribute("noOfSkus",noOfSkus.toString());
				if(noOfSkus > 0)
				{	
					BuyerSKUEntity bsku = buyerSkuNameList.get(0);
				
					for(BuyerSkuTask bSkuTask:bsku.getBuyerSkuTasks()){
						BuyerSkuTask buyerSkuTask = bSkuTask;
						getSession().setAttribute("mainCategoryId",buyerSkuTask.getSkillTree().getRootNodeId());
					}
				}
			}
		} catch (Exception e) {		
			logger.error("Caught exception in  SkuMaintenanceAction - displaySkuNameByCategory() - "+e);
		}
		return SUCCESS;		
	}

	@SuppressWarnings("null")
	public String fetchSkillTreeBySkuId(Integer skuId)
	{
		BuyerSkuTask buyerSkuTasks = null;
	    SkillTree skill=null;
		try {	
		buyerSkuTasks = maintainSkuService.fetchSkillTreeBySkuId(skuId);
		setAttribute("buyerSkuTasks",buyerSkuTasks);
		skill = buyerSkuTasks.getSkillTree();
		int skillLevelCount = skill.getLevel().intValue();
		
		//Setting main service category
		if(skillLevelCount == OrderConstants.MAIN_SERVICE_CAT_SKILL_LEVEL){
			//newTask.setMainServiceCatName(skill.getNodeName());
			setAttribute("mainServiceCatName", skill.getNodeName());
			setAttribute("taskCatName","No Category");
			setAttribute("taskSubCatName","No Sub Category");
			return SUCCESS;
		}	
		
			Integer parentNode = skill.getParentNode();
			if(null != parentNode){
				SkillTree parentSkill = maintainSkuService.fetchSkillTreeBySkuIdByNodeId(parentNode);			
				if(OrderConstants.SUB_CAT_SKILL_LEVEL == skillLevelCount){
					//Set subcategory and category names if level=3					
					setAttribute("taskSubCatName", skill.getNodeName());
					setAttribute("taskCatName",parentSkill.getNodeName());
					SkillTree rootSkill = maintainSkuService.fetchSkillTreeBySkuIdByNodeId(skill.getRootNodeId());
					setAttribute("mainServiceCatName", rootSkill.getNodeName());
				}else if(OrderConstants.CATEGORY_SKILL_LEVEL == skillLevelCount){
					//Set category and main service category names if level=2
					setAttribute("taskSubCatName", "No Sub Category");
					setAttribute("taskCatName",skill.getNodeName());
					setAttribute("mainServiceCatName", parentSkill.getNodeName());					
				}
			}
		}
		catch (Exception e) 
		{
			return null;
		}
		return SUCCESS;
             
	}
	public String skuDetailsBySkuIdAndCategoryId() throws Exception {		
		Integer skuId =Integer.parseInt(getParameter("categoryNameId"));
		Integer categoryId =Integer.parseInt(getParameter("categoryId"));
		try {
			BuyerSKUEntity skuDetailsBySkuIdAndCategoryId = maintainSkuService.skuDetailsBySkuIdAndCategoryId(skuId,categoryId);
			setAttribute("skuDetailsBySkuIdAndCategoryId",skuDetailsBySkuIdAndCategoryId);
			 fetchSkillTreeBySkuId(skuId);
		} catch (Exception e) {
			
			logger.error("Caught exception in  SkuMaintenanceAction - skuDetailsBySkuIdAndCategoryId() - "+e);
		}
		return SUCCESS;		
	}
	//SL-17504 : To validate update SKU category
	public String validateSKUCategory(){
		boolean flag = false;
		String updatedSkuName = getRequest().getParameter("skuCategoryName");
		String skuCategoryId = getRequest().getParameter("skuCategoryId");		
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		if(soContxt == null)
		{
			return "homepage";
		}	
		int buyerId = soContxt.getCompanyId();
		if(buyerId != 0 && skuCategoryId != null && skuCategoryId != ""){				
		  boolean validatebuyerSKUCategory = maintainSkuService.validateBuyerSKUCategory(buyerId, Integer.parseInt(skuCategoryId), updatedSkuName);
		  if(validatebuyerSKUCategory){
			  getSession().setAttribute("isValidate", "true"); 
		  }else{
			  getSession().setAttribute("isValidate", "fail"); 
		  }		
		}	
		
		return SUCCESS;
	}
	
	//temporary method
	public String validateExistingSkuCategory(){
		boolean flag = false;
		String updatedSkuName = getRequest().getParameter("skuCategoryName");
    	logger.debug("The sku Category Name is"+updatedSkuName+ "And the sku Category Description is ");
    	SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		if(soContxt == null)
		{
			return "homepage";
		}	
		int buyerId = soContxt.getCompanyId();
		List<BuyerSkuCategory> buyerSkuCategoryList = maintainSkuService.fetchBuyerSkuCategories(buyerId);
		if(buyerSkuCategoryList != null && buyerSkuCategoryList.size() > 0)
		{	
			for (BuyerSkuCategory buyerSkuCategory : buyerSkuCategoryList) {
				if (updatedSkuName.equalsIgnoreCase(buyerSkuCategory.getCategoryName())){
					flag = true;
					getRequest().setAttribute("returnvalue", "fail");
					break;
				}
			}
		}	
		if(flag == false){
			getRequest().setAttribute("returnvalue", "true");
		}
		
		return SUCCESS;
	}
	
//validation for existing sku name
	public String validateSku(){ 		
		boolean flag = false;
		Integer parentCategId = null;
		Integer currentSkuId = null;
		String updatedSkuName = getRequest().getParameter("skuNameUpdated");
		String categId = getRequest().getParameter("parentCategId");
		String skuId = getRequest().getParameter("currentSkuId");
		if(categId!=null){
			parentCategId = Integer.parseInt(categId);
		}
		if(skuId!=null && !skuId.equals("-1")){
			currentSkuId = Integer.parseInt(skuId);
		}
    	logger.debug("The sku Category Name is"+updatedSkuName+ "And the sku Category Description is ");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		if(soContxt == null)
		{
			return "homepage";
		}	
		Integer buyerId = soContxt.getCompanyId();
		List<BuyerSKUEntity> buyerSku = maintainSkuService.fetchBuyerSkuNameByCategory(parentCategId,currentSkuId,buyerId);
		if(buyerSku != null && buyerSku.size() > 0)
		{	
			for (BuyerSKUEntity bSku : buyerSku) {
				if (updatedSkuName.equalsIgnoreCase(bSku.getSku())){
					flag = true;
					getRequest().setAttribute("returnvalue", "fail");
					break;
				}
			}
		}	
		if(flag==false){
			getRequest().setAttribute("returnvalue", "true");
		}
			
			return SUCCESS;
	}

	public String updateSkuCategory(){
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		if(soContxt == null)
		{
			return "homepage";
		}	
		String updateSkuCategory = getParameter("updateSkuCategory");
		getSession().setAttribute("updateSkuCategory", updateSkuCategory);
    	String updatedSkuName = getRequest().getParameter("skuCategoryName");
    	String descr = getRequest().getParameter("skuCategoryDescription");
    	Integer skuCatId = Integer.parseInt(getRequest().getParameter("skuCategoryId"));

    	if(updatedSkuName != null && descr != null){
	    try {
	    	maintainSkuService.updateSkuCategory(updatedSkuName,descr,skuCatId,soContxt);
	    	getSession().setAttribute("updatedSKUCategoryId", skuCatId);
	    }
		catch (Exception e){					
			logger.debug("SkuMaintenanceAction.updateSkuCategory: Error in updating sku category");
		}
    	}else{
			return ERROR;
		}
	    return SUCCESS;
	}
	
	//to display addSkuCategory//add sku//update sku modal
	
	public String addSkuCategory() throws Exception {		
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		String source = getParameter("source");
		if(source.equals("addSKUType")){
			getRequest().setAttribute("modalTitle", OrderConstants.ADD_SKU_CATEGORY);
		}
		else if(source.equals("addSkuByCategory")){
			getRequest().setAttribute("modalTitle", OrderConstants.ADD_SKU);
		}
		else{
			int skuId = Integer.parseInt(getParameter("skuIdforUpdate"));
			int categoryId = Integer.parseInt(getParameter("categIdforUpdate"));
			boolean activeSKUUpdate = checkForActiveSKUDuringUpdateSKU(String.valueOf(skuId), String.valueOf(categoryId));	
			logger.info("addSkuCategory -- activeSKUUpdate -- "+activeSKUUpdate);
			getRequest().setAttribute("activeSKU", activeSKUUpdate);			
			getRequest().setAttribute("modalTitle", OrderConstants.UPDATE_SKU);
			BuyerSKUEntity buyerSku = maintainSkuService.skuDetailsBySkuIdAndCategoryId(skuId,categoryId);

			setAttribute("buyerSkuDetails",buyerSku);
			for(BuyerSkuTask bskutask:buyerSku.getBuyerSkuTasks()){
				if(OrderConstants.SUB_CAT_SKILL_LEVEL == bskutask.getSkillTree().getLevel()){
					setAttribute("oldCatgegId",bskutask.getSkillTree().getParentNode());
					setAttribute("oldsubCategId",bskutask.getSkillTree().getNodeId());
				}
				else if(OrderConstants.CATEGORY_SKILL_LEVEL == bskutask.getSkillTree().getLevel()){
					setAttribute("oldsubCategId","noSubCateg");
					setAttribute("oldCatgegId",bskutask.getSkillTree().getNodeId());					
				}
				setAttribute("oldSkill",bskutask.getLuServiceTypeTemplate().getServiceTypeTemplateId());				
			}
			setAttribute("oldTemplate",buyerSku.getBuyerSoTemplate().getTemplateId());
		}
		try {
			List<Map<Integer, String>> mainCategoryList = maintainSkuService.fetchMainCategory();
			setAttribute("mainCategoryList",mainCategoryList);
			/**This will validate non funded feature set for buyer and prevent
			 * creation of sku for price greater than zero*/
			Integer buyerId = null;
			Integer roleId = soContxt.getRoleId();
			boolean isNonFunded = false;
			if (roleId.intValue() == OrderConstants.BUYER_ROLEID) {
				buyerId = soContxt.getCompanyId();
				isNonFunded = maintainSkuService.isNonFundedBuyer(buyerId);
				} 
			   setAttribute("isNonFundedBuyer",isNonFunded);
						
		} catch (Exception e) {
			
			logger.error("Caught exception in  SkuMaintenanceAction - addSkuCategory() - "+e);
		}
		return SUCCESS;		
	}
	
	//method to fetch category ad skill for selected main category
	public String fetchCategory() throws Exception {
		
		int nodeId = Integer.parseInt(getParameter("mainCatId"));
		
		SecurityContext securityContext=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = securityContext.getCompanyId();
		@SuppressWarnings("unchecked")
		List<SkillTree> categoryList = maintainSkuService.fetchCategory(nodeId);
		List<LookupVO> categList = new ArrayList<LookupVO>();
		for(SkillTree catList :categoryList){
			LookupVO category = new LookupVO();
			category.setId(catList.getNodeId());
			category.setDescr(catList.getNodeName());
			categList.add(category);
		}
		
		// Sort category
		ListComparator categoryComparator =  new ListComparator();
		Collections.sort(categList ,categoryComparator) ;
		
		@SuppressWarnings("unchecked")
		List<LuServiceTypeTemplate> skillList = maintainSkuService.fetchSkills(nodeId);
		List<LookupVO> skills = new ArrayList<LookupVO>();
		for(LuServiceTypeTemplate serviceType :skillList){
			LookupVO skill = new LookupVO();
			skill.setId(serviceType.getServiceTypeTemplateId());
			skill.setDescr(serviceType.getDescr());
			skills.add(skill);
		}
		// Sort skill
		ListComparator skillComparator =  new ListComparator();
		Collections.sort(skills ,skillComparator) ;
		
		//fetch templates corresponding to the selected maincategoryid
		List<Map<Integer, String>> templateList = maintainSkuService.fetchTemplates(nodeId,buyerId);		
		//setAttribute("templateList",templateList);
		
		skuMaintenanceVO.setSkillList(skills);
		skuMaintenanceVO.setCategoryList(categList);
		skuMaintenanceVO.setTemplateNames(templateList);
		getRequest().setAttribute("skuMaintenanceVO", skuMaintenanceVO);
		String returnvalue = getJSON(skuMaintenanceVO);
		getRequest().setAttribute("returnvalue", returnvalue);
		return SUCCESS;
	}
	
	//method to fecth subcategories for selected category
	public String fetchSubCategory() throws Exception {
		
		int nodeId = Integer.parseInt(getParameter("categoryId"));
		List<SkillTree> categoryList = maintainSkuService.fetchCategory(nodeId);
		List<LookupVO> subCategList = new ArrayList<LookupVO>();
		for(SkillTree catList :categoryList){
			LookupVO category = new LookupVO();
			category.setId(catList.getNodeId());
			category.setDescr(catList.getNodeName());
			subCategList.add(category);
		}
		
		// Sort skill
		ListComparator subCatComparator =  new ListComparator();
		Collections.sort(subCategList ,subCatComparator) ;
		
		skuMaintenanceVO.setSubCategoryList(subCategList);
		String returnvalue = getJSON(skuMaintenanceVO);
		getRequest().setAttribute("returnvalue", returnvalue);
		return SUCCESS;
	}
	
	//method for persisting newly added data-for update sku category, add sku, update sku
	public String saveData() throws Exception {
		SecurityContext securityContext=(SecurityContext) getSession().getAttribute("SecurityContext");
		int buyerId = securityContext.getCompanyId();
		skuMaintenanceVO = getModel();
		String categoryId = getParameter("skuCatId");
		String skuNameId = getParameter("skuNameId");
		int flag = 0;

		//for persisting add sku category data
		Buyer buyer = maintainSkuService.findBuyer(buyerId);
		if((categoryId==null||categoryId.equals("null")) && (skuNameId == null || skuNameId.equals("null"))){
			BuyerSkuCategory entity = createSkuCategoryObject(buyer, skuMaintenanceVO, securityContext);
			maintainSkuService.addSkuCategory(entity);
			flag= 1;  
			//setting values to highlight the newly added attributes on reload
			getSession().setAttribute("newCategoryName",skuMaintenanceVO.getSkuCategoryName());
			getSession().setAttribute("newSkuName",skuMaintenanceVO.getSku());
			getSession().setAttribute("addSkuCategory","true");	
			displayDetailsonReload();
		}
		
		//for persisting add sku to sku category data
		if(flag==0 && categoryId!=null && (skuNameId == null||skuNameId.equals("null"))){
			BuyerSKUEntity entity = createAddBuyerSkuObject(buyer, skuMaintenanceVO, securityContext, categoryId);
			maintainSkuService.addSkuCategory(entity);
			displayDetailsonReload();
			flag = 1;			
		}
		
		// for persisting update sku data
		if( flag==0 && categoryId!=null && skuNameId != null){
			BuyerSKUEntity entity = createUpdateBuyerSkuObject(buyer, skuMaintenanceVO, securityContext, categoryId, skuNameId);
			maintainSkuService.addSkuCategory(entity);
			displayDetailsonReload();	
		}
		return SUCCESS;	
	}
	
	//method for creating BuyerSku Object for persisting update sku data
	public BuyerSKUEntity createUpdateBuyerSkuObject(Buyer buyer, SkuMaintenanceVO skuMaintenanceVO, SecurityContext securityContext, String categoryId, String skuNameId){
		NumberFormat formatter = new DecimalFormat("###0.00");
		String actionDetails ="";
		BuyerSkuCategory buyerSkuCategory = maintainSkuService.findBuyerSkuCategory(Integer.parseInt(categoryId));
		BuyerSKUEntity buyerSku = null;
		for(BuyerSKUEntity bsku:buyerSkuCategory.getBuyerSkus()){
			if(bsku.getSkuId().equals(Integer.parseInt(skuNameId))){
				buyerSku = bsku;
			}
		}
		BuyerSkuTask buyerSkuTask = null;
		for(BuyerSkuTask bSkuTask:buyerSku.getBuyerSkuTasks()){
			buyerSkuTask = bSkuTask;
		}
		if(!buyerSkuTask.getTaskName().equals(skuMaintenanceVO.getTaskName())){
			actionDetails= actionDetails+"-Task name updated from ("+buyerSkuTask.getTaskName()+") to ("+skuMaintenanceVO.getTaskName()+")\n";	
		buyerSkuTask.setTaskName(skuMaintenanceVO.getTaskName());
		}
		if(!buyerSkuTask.getTaskComments().equals(skuMaintenanceVO.getTaskComments())){
			actionDetails= actionDetails+"-Task comments updated from ("+buyerSkuTask.getTaskComments()+") to ("+skuMaintenanceVO.getTaskComments()+")\n";
		buyerSkuTask.setTaskComments(skuMaintenanceVO.getTaskComments());
		}
		
		//*******DOUBT******* check for change sin main category,category,subcategory *******DOUBT*******
		if(!buyerSkuTask.getSkillTree().getNodeId().equals(Integer.parseInt(skuMaintenanceVO.getNodeId()))){
			String oldNodeName = buyerSkuTask.getSkillTree().getNodeName();
			Integer oldParentNodeId = buyerSkuTask.getSkillTree().getParentNode();
			Integer oldRootNodeId = buyerSkuTask.getSkillTree().getRootNodeId();
			Integer oldLevel = buyerSkuTask.getSkillTree().getLevel();
			Integer oldNodeId = buyerSkuTask.getSkillTree().getNodeId();			
			
		SkillTree skilltree = maintainSkuService.findSkillTree(Integer.parseInt(skuMaintenanceVO.getNodeId()));
		Integer newLevel = skilltree.getLevel();
		
		// newLevel == 3 -- means there is sub category
		// newLevel == 2 -- means there is no sub category
		boolean oldHasSubcategory = false;
		boolean newHasSubcategory = false;
		if(3 == oldLevel)
			oldHasSubcategory = true;		
		if(3 == newLevel)
			newHasSubcategory = true;
		
		if(!skilltree.getRootNodeId().equals(oldRootNodeId)){
			// Level 1- which is main category
			SkillTree skilltreeRootOld = maintainSkuService.findSkillTree((oldRootNodeId));
			SkillTree skilltreeRoot = maintainSkuService.findSkillTree((skilltree.getRootNodeId()));
			actionDetails= actionDetails+"-Main category updated from ("+skilltreeRootOld.getNodeName()+") to ("+skilltreeRoot.getNodeName()+")\n";
		}
		if(!skilltree.getParentNode().equals(oldParentNodeId)){
			// Level 2- which is category
			SkillTree skilltreeParentOld = new SkillTree();
			if(2==oldLevel)
				skilltreeParentOld = maintainSkuService.findSkillTree((oldNodeId));
			else if(3==oldLevel)
				skilltreeParentOld = maintainSkuService.findSkillTree((oldParentNodeId));
				
			
			SkillTree skilltreeParent = new SkillTree();
			if(2==newLevel)				
				skilltreeParent = maintainSkuService.findSkillTree((skilltree.getNodeId()));
			else if(3==newLevel)
				skilltreeParent = maintainSkuService.findSkillTree((skilltree.getParentNode()));
			
			actionDetails= actionDetails+"-Category updated from ("+skilltreeParentOld.getNodeName()+") to ("+skilltreeParent.getNodeName()+")\n";
		}	
		
		
		if(!skilltree.getNodeId().equals(oldNodeId)){
			String oldSubCatName = "";
			String newSubCatName = "";
			if(null!=oldNodeId && oldHasSubcategory){
				SkillTree skilltreeOld = maintainSkuService.findSkillTree((oldNodeId));
				oldSubCatName = skilltreeOld.getNodeName();
			}
			
			if(null!=skilltree.getNodeId() && newHasSubcategory){
				SkillTree skilltreeNew = maintainSkuService.findSkillTree((skilltree.getNodeId()));			
				newSubCatName = skilltreeNew.getNodeName();
			}
			actionDetails= actionDetails+"-Subcategory updated from (";
			if(!oldHasSubcategory){
				actionDetails= actionDetails+"No sub category";
			}else{
				actionDetails= actionDetails+oldSubCatName;
			}
			
			actionDetails = actionDetails+") to (";
			
			if(!newHasSubcategory){
				actionDetails = actionDetails+"No sub category";
			}else{
				actionDetails = actionDetails+newSubCatName;
			}
				
			actionDetails = actionDetails+")\n";
		}
		buyerSkuTask.setSkillTree(skilltree);
		}
		
		if(!buyerSkuTask.getLuServiceTypeTemplate().getServiceTypeTemplateId().equals(Integer.parseInt(skuMaintenanceVO.getSelectedSkill()))){
			String oldSkill = buyerSkuTask.getLuServiceTypeTemplate().getDescr();
		LuServiceTypeTemplate luServiceTypeTemplate = maintainSkuService.findServiceType(Integer.parseInt(skuMaintenanceVO.getSelectedSkill()));
		actionDetails= actionDetails+"-Skill updated from ("+oldSkill+") to ("+luServiceTypeTemplate.getDescr()+")\n";
		buyerSkuTask.setLuServiceTypeTemplate(luServiceTypeTemplate);
		}
		if(!buyerSku.getSku().equals(skuMaintenanceVO.getSku())){
			actionDetails= actionDetails+"-SKU Name changed from ("+buyerSku.getSku()+") to ("+skuMaintenanceVO.getSku()+")\n";
		buyerSku.setSku(skuMaintenanceVO.getSku().trim());
		}
		if(!buyerSku.getSkuDescription().equals(skuMaintenanceVO.getSkuNameDesc())){
			actionDetails= actionDetails+"-SKU Description changed from ("+buyerSku.getSkuDescription()+") to ("+skuMaintenanceVO.getSkuNameDesc()+")\n";
		buyerSku.setSkuDescription(skuMaintenanceVO.getSkuNameDesc());
		}
		/*if(!buyerSku.getPriceType().equals(skuMaintenanceVO.getPriceType())){
			actionDetails= actionDetails+"-Price type changed from ("+buyerSku.getPriceType()+") to ("+skuMaintenanceVO.getPriceType()+")\n";
		buyerSku.setPriceType(skuMaintenanceVO.getPriceType());
		}*/
		if(!buyerSku.getOrderitemType().equals(skuMaintenanceVO.getOrderItemType())){
			actionDetails= actionDetails+"-Order item type updated from ("+buyerSku.getOrderitemType()+") to ("+skuMaintenanceVO.getOrderItemType()+")\n";
		buyerSku.setOrderitemType(skuMaintenanceVO.getOrderItemType());
		} 
		if(skuMaintenanceVO.getRetailPrice() != null){
		if(!buyerSku.getRetailPrice().equals(skuMaintenanceVO.getRetailPrice())){
			actionDetails=actionDetails+"-Retail Price updated from ( $"+formatter.format(buyerSku.getRetailPrice())+") to ( $"+formatter.format(skuMaintenanceVO.getRetailPrice())+")\n";
		buyerSku.setRetailPrice(skuMaintenanceVO.getRetailPrice());
		}
		}else{
			actionDetails=actionDetails+"-Retail Price updated from ( $"+formatter.format(buyerSku.getRetailPrice())+") to ( $"+formatter.format(OrderConstants.DEFAULT_BILLING_PRICE)+")\n";
			buyerSku.setRetailPrice(OrderConstants.DEFAULT_BILLING_PRICE);
		}
		if(skuMaintenanceVO.getBidMargin()!=null){
		if(!buyerSku.getBidMargin().equals((skuMaintenanceVO.getBidMargin())/100)){
			actionDetails=actionDetails+"-Margin updated from ( "+formatter.format((buyerSku.getBidMargin()*100))+" %) to ( "+formatter.format(skuMaintenanceVO.getBidMargin())+" %)\n";
		buyerSku.setBidMargin((skuMaintenanceVO.getBidMargin())/100);
		}
		}else{
			actionDetails=actionDetails+"-Margin updated from ( "+formatter.format((buyerSku.getBidMargin()*100))+" %) to ( "+formatter.format(OrderConstants.DEFAULT_BILLING_MARGIN)+" %)\n";
			buyerSku.setBidMargin(OrderConstants.DEFAULT_BILLING_MARGIN);
		}
		if(!buyerSku.getBidPrice().equals(skuMaintenanceVO.getBidPrice())){
			actionDetails=actionDetails+"-Maximum Price updated from ( $"+formatter.format(buyerSku.getBidPrice())+") to ( $"+formatter.format(skuMaintenanceVO.getBidPrice())+")\n";
		buyerSku.setBidPrice(skuMaintenanceVO.getBidPrice());
		}
		if(!buyerSku.getManageScopeInd().equals(skuMaintenanceVO.getManageScopeInd())){
			if(true == skuMaintenanceVO.getManageScopeInd()){
				actionDetails = actionDetails+"-SKU is updated so that it will be visible in manage scope widget\n";
			}
			if(false == skuMaintenanceVO.getManageScopeInd()){
				actionDetails = actionDetails+"-SKU is updated so that it will not be visible in manage scope widget\n";
			}
		}
		if(skuMaintenanceVO.getBillingMargin()!=null && !buyerSku.getBillingMargin().equals((skuMaintenanceVO.getBillingMargin())/100)){
			actionDetails=actionDetails+"-Billing Margin updated from ( "+formatter.format((buyerSku.getBillingMargin()*100))+" %) to ( "+formatter.format(skuMaintenanceVO.getBillingMargin())+" %)\n";
			buyerSku.setBillingMargin((skuMaintenanceVO.getBillingMargin())/100);
		}
		else{
			buyerSku.setBillingMargin(OrderConstants.DEFAULT_BILLING_MARGIN);
		}
		if(skuMaintenanceVO.getBillingPrice()!=null && !buyerSku.getBillingPrice().equals(skuMaintenanceVO.getBillingPrice())){
			actionDetails = actionDetails+"-Billing Price updated from ( $"+formatter.format(buyerSku.getBillingPrice())+") to ( $"+formatter.format(skuMaintenanceVO.getBillingPrice())+")\n";
			buyerSku.setBillingPrice(skuMaintenanceVO.getBillingPrice());
		}
		else{
			buyerSku.setBillingPrice(OrderConstants.DEFAULT_BILLING_PRICE);
		}

		buyerSku.setBidPriceSchema(OrderConstants.DEFAULT_BID_PRICE_SCHEMA);
		buyerSku.setBuyer(buyer);
		buyerSku.setCreatedDate(new Date());
		buyerSku.setModifiedDate(new Date());
	    buyerSku.setManageScopeInd(skuMaintenanceVO.getManageScopeInd());
	   String templateName = "";
	   BuyerSoTemplate buyersoTemplate = maintainSkuService.findTemplate(Integer.parseInt(skuMaintenanceVO.getselectedtemplateId()));
	    if(buyerSku.getBuyerSoTemplate()!=null){
	    	if(!buyerSku.getBuyerSoTemplate().getTemplateId().equals(Integer.parseInt(skuMaintenanceVO.getselectedtemplateId()))){
				templateName= buyerSku.getBuyerSoTemplate().getTemplateName();
				actionDetails=actionDetails+"-Template name updated from ( "+templateName+") to ( "+buyersoTemplate.getTemplateName()+")";
	    }
	    }
	    else{
	    	actionDetails=actionDetails+"-Template name updated to ( "+buyersoTemplate.getTemplateName()+")";
	    }
		buyerSku.setBuyerSoTemplate(buyersoTemplate);
		
		
		buyerSkuTask.setBuyerSku(buyerSku);
		buyerSku.getBuyerSkuTasks().add(buyerSkuTask);
		
		SkuHistory skuHistory = new SkuHistory();
		skuHistory.setSkuName(skuMaintenanceVO.getSku());
		skuHistory.setRoleId(String.valueOf(securityContext.getRoleId()));
		skuHistory.setAction(OrderConstants.SKU_MODIFIED);
		skuHistory.setActionDetails(actionDetails);
		skuHistory.setModifiedBy(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		skuHistory.setModifiedOn(new Date());
		skuHistory.setBuyerSku(buyerSku);
		buyerSku.getSkuHistory().add(skuHistory);
		
		buyerSku.setBuyerSkuCategory(buyerSkuCategory);

		return buyerSku;
	}
	
	//method for creating BuyerSkuCategory Object for persisting add sku category data
	public BuyerSkuCategory createSkuCategoryObject(Buyer buyer,SkuMaintenanceVO skuMaintenanceVO, SecurityContext securityContext){

		BuyerSkuCategory buyerSkuCategory = new BuyerSkuCategory();
		buyerSkuCategory.setCategoryName(skuMaintenanceVO.getSkuCategoryName());
		buyerSkuCategory.setCategoryDescr(skuMaintenanceVO.getSkuCategoryDesc());
		buyerSkuCategory.setCreatedDate(new Date());
		buyerSkuCategory.setModifiedDate(new Date());
		buyerSkuCategory.setBuyer(buyer);
		
		BuyerSkuTask buyerSkuTask = new BuyerSkuTask();
		buyerSkuTask.setTaskName(skuMaintenanceVO.getTaskName());
		buyerSkuTask.setTaskComments(skuMaintenanceVO.getTaskComments());
		SkillTree skilltree = maintainSkuService.findSkillTree(Integer.parseInt(skuMaintenanceVO.getNodeId()));
		buyerSkuTask.setSkillTree(skilltree);
		LuServiceTypeTemplate luServiceTypeTemplate = maintainSkuService.findServiceType(Integer.parseInt(skuMaintenanceVO.getSelectedSkill()));
		buyerSkuTask.setLuServiceTypeTemplate(luServiceTypeTemplate);
		
		BuyerSKUEntity buyerSku = new BuyerSKUEntity();
		buyerSku.setSku(skuMaintenanceVO.getSku().trim());
		buyerSku.setSkuDescription(skuMaintenanceVO.getSkuNameDesc());
		buyerSku.setPriceType(OrderConstants.DEFAULT_PRICE_TYPE);
		buyerSku.setOrderitemType(skuMaintenanceVO.getOrderItemType());
		if(skuMaintenanceVO.getRetailPrice()!=null){
		buyerSku.setRetailPrice(skuMaintenanceVO.getRetailPrice());
		}else{
			buyerSku.setRetailPrice(OrderConstants.DEFAULT_BILLING_PRICE);
		}
		if(skuMaintenanceVO.getBidMargin()!=null){
		buyerSku.setBidMargin((skuMaintenanceVO.getBidMargin())/100);
		}else{
			buyerSku.setBidMargin(OrderConstants.DEFAULT_BILLING_MARGIN);
		}
		
		buyerSku.setBidPrice(skuMaintenanceVO.getBidPrice());
		if(skuMaintenanceVO.getBillingMargin()!=null){
			buyerSku.setBillingMargin((skuMaintenanceVO.getBillingMargin())/100);
		}
		else{
			buyerSku.setBillingMargin(OrderConstants.DEFAULT_BILLING_MARGIN);
		}
		if(skuMaintenanceVO.getBillingPrice()!=null){
			buyerSku.setBillingPrice(skuMaintenanceVO.getBillingPrice());
		}
		else{
			buyerSku.setBillingPrice(OrderConstants.DEFAULT_BILLING_PRICE);
		}
		buyerSku.setBidPriceSchema(OrderConstants.DEFAULT_BID_PRICE_SCHEMA);
		buyerSku.setBuyer(buyer);
		buyerSku.setCreatedDate(new Date());
		buyerSku.setModifiedDate(new Date());
		buyerSku.setManageScopeInd(skuMaintenanceVO.getManageScopeInd());
		BuyerSoTemplate buyersoTemplate = maintainSkuService.findTemplate(Integer.parseInt(skuMaintenanceVO.getselectedtemplateId()));
		buyerSku.setBuyerSoTemplate(buyersoTemplate);
		buyerSkuTask.setBuyerSku(buyerSku);
		buyerSku.getBuyerSkuTasks().add(buyerSkuTask);
		buyerSku.setBuyerSkuCategory(buyerSkuCategory);
		
		SkuHistory skuHistory = new SkuHistory();
		skuHistory.setSkuName(skuMaintenanceVO.getSku());
		skuHistory.setRoleId(String.valueOf(securityContext.getRoleId()));
		skuHistory.setAction(OrderConstants.SKU_CREATED);
		skuHistory.setModifiedBy(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		skuHistory.setModifiedOn(new Date());
		skuHistory.setBuyerSku(buyerSku);
		buyerSku.getSkuHistory().add(skuHistory);
		
		
		SkuCategoryHistoy skuCategoryHistoy = new SkuCategoryHistoy();
		skuCategoryHistoy.setSkuCategoryName(skuMaintenanceVO.getSkuCategoryName());
		skuCategoryHistoy.setAction(OrderConstants.SKU_CATEGORY_CREATED);
		skuCategoryHistoy.setModifiedBy(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		skuCategoryHistoy.setModifiedOn(new Date());
		skuCategoryHistoy.setRoleId(String.valueOf(securityContext.getRoleId()));
		skuCategoryHistoy.setBuyerSkuCategory(buyerSkuCategory);
		buyerSkuCategory.getSkuCategoryHistory().add(skuCategoryHistoy);
		
		buyerSkuCategory.getBuyerSkus().add(buyerSku);
		return buyerSkuCategory;
		
	}
	
	//method for creating BuyerSku Object for persisting add sku to sku category data
	public BuyerSKUEntity createAddBuyerSkuObject(Buyer buyer,SkuMaintenanceVO skuMaintenanceVO, SecurityContext securityContext, String categoryId){
		
		
		BuyerSkuCategory buyerSkuCategory = maintainSkuService.findBuyerSkuCategory(Integer.parseInt(categoryId));
		
		BuyerSkuTask buyerSkuTask = new BuyerSkuTask();
		buyerSkuTask.setTaskName(skuMaintenanceVO.getTaskName());
		buyerSkuTask.setTaskComments(skuMaintenanceVO.getTaskComments());
		SkillTree skilltree = maintainSkuService.findSkillTree(Integer.parseInt(skuMaintenanceVO.getNodeId()));
		buyerSkuTask.setSkillTree(skilltree);
		LuServiceTypeTemplate luServiceTypeTemplate = maintainSkuService.findServiceType(Integer.parseInt(skuMaintenanceVO.getSelectedSkill()));
		buyerSkuTask.setLuServiceTypeTemplate(luServiceTypeTemplate);
		
		BuyerSKUEntity buyerSku = new BuyerSKUEntity();
		buyerSku.setSku(skuMaintenanceVO.getSku().trim());
		buyerSku.setSkuDescription(skuMaintenanceVO.getSkuNameDesc());
		buyerSku.setPriceType(OrderConstants.DEFAULT_PRICE_TYPE);
		buyerSku.setOrderitemType(skuMaintenanceVO.getOrderItemType());
		if(skuMaintenanceVO.getRetailPrice()!=null){
			buyerSku.setRetailPrice(skuMaintenanceVO.getRetailPrice());
			}else{
				buyerSku.setRetailPrice(OrderConstants.DEFAULT_BILLING_PRICE);
			}
		if(skuMaintenanceVO.getBidMargin()!=null){
			buyerSku.setBidMargin((skuMaintenanceVO.getBidMargin())/100);
		}else{
			buyerSku.setBidMargin(OrderConstants.DEFAULT_BILLING_MARGIN);
		}
		buyerSku.setBidPrice(skuMaintenanceVO.getBidPrice());
		if(skuMaintenanceVO.getBillingMargin()!=null){
			buyerSku.setBillingMargin((skuMaintenanceVO.getBillingMargin())/100);
		}
		else{
			buyerSku.setBillingMargin(OrderConstants.DEFAULT_BILLING_MARGIN);
		}
		if(skuMaintenanceVO.getBillingPrice()!=null){
			buyerSku.setBillingPrice(skuMaintenanceVO.getBillingPrice());
		}
		else{
			buyerSku.setBillingPrice(OrderConstants.DEFAULT_BILLING_PRICE);
		}

		buyerSku.setBidPriceSchema(OrderConstants.DEFAULT_BID_PRICE_SCHEMA);
		buyerSku.setBuyer(buyer);
		buyerSku.setCreatedDate(new Date());
		buyerSku.setModifiedDate(new Date());
	    buyerSku.setManageScopeInd(skuMaintenanceVO.getManageScopeInd());
		BuyerSoTemplate buyersoTemplate = maintainSkuService.findTemplate(Integer.parseInt(skuMaintenanceVO.getselectedtemplateId()));
		buyerSku.setBuyerSoTemplate(buyersoTemplate);
		buyerSkuTask.setBuyerSku(buyerSku);
		buyerSku.getBuyerSkuTasks().add(buyerSkuTask);
		buyerSku.setBuyerSkuCategory(buyerSkuCategory);
		
		SkuHistory skuHistory = new SkuHistory();
		skuHistory.setSkuName(skuMaintenanceVO.getSku());
		skuHistory.setRoleId(String.valueOf(securityContext.getRoleId()));
		skuHistory.setAction(OrderConstants.SKU_CREATED);
		skuHistory.setModifiedBy(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		skuHistory.setModifiedOn(new Date());
		skuHistory.setBuyerSku(buyerSku);
		buyerSku.getSkuHistory().add(skuHistory);
		buyerSku.setBuyerSkuCategory(buyerSkuCategory);
		return buyerSku;
	}
	
	//method to display details of newly added/updated sku in sku maintenance page
	public void displayDetailsonReload() throws Exception{		
		BuyerSKUEntity buyersku = maintainSkuService.findBuyerSku();
		String returnvalue = getJSON(buyersku);
		getRequest().setAttribute("returnvalue", returnvalue);
	}

	public String deleteSKU(){
		String selSkusDelete = getRequest().getParameter("deleteSkuList");
		String skuCategoryId = getRequest().getParameter("skuCategoryId");		
		logger.debug("deleteSKU--selSkusDelete--"+selSkusDelete);
		List<Integer> skuIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(selSkusDelete)){
			// Parse the string into individual IDs
			StringTokenizer tokenizer = new StringTokenizer(selSkusDelete, ",");
			while(tokenizer.hasMoreTokens()) {
				String id = tokenizer.nextToken().trim();
				if(StringUtils.isNumeric(id)) { 
					skuIds.add(Integer.valueOf(id));
				}
			}
		}		
    	String skuCategoryName = getRequest().getParameter("skuCategoryName");
		maintainSkuService.deleteSku(skuIds, skuCategoryName, skuCategoryId);
		return SUCCESS;
	}

	
	public boolean checkForActiveSKUDuringUpdateSKU(String skuId, String skuCategoryId){
		logger.debug("checkForActiveSKUDuringUpdateSKU");
		logger.debug("skuId -- "+skuCategoryId);
		logger.debug("skuCategoryId -- "+skuCategoryId);
		
		List<String> skuIds = new ArrayList<String>();		
		skuIds.add(skuId);	
		int totalNoOfSkus = skuIds.size();
		int noOfInactiveSkus=0;
		int noOfActiveSkus = 0;
		boolean activeSku = false;
		List<Integer> noOfInactiveSkusList = null;
		if(!(skuIds.isEmpty()) && StringUtils.isNotEmpty(skuCategoryId)){
			noOfInactiveSkusList = maintainSkuService.checkForDeleteSku(skuIds,skuCategoryId);
			noOfInactiveSkus = noOfInactiveSkusList.size();
			logger.debug("noOfInactiveSkus -- "+noOfInactiveSkus);			
			noOfActiveSkus = totalNoOfSkus - noOfInactiveSkus;
			logger.debug("noOfActiveSkus -- "+noOfActiveSkus);				
			 if(noOfInactiveSkus == 0 && noOfActiveSkus == totalNoOfSkus){
				if(totalNoOfSkus == 1){
					logger.debug("SELECTED SKU FOR UPDATE IS ACTIVE HENCE MAIN CATEGORY WILL BE DISABLED");
					activeSku = true; 					
				}
			 }
		}
		return activeSku;
	}	
	
	
	public String checkForDeleteSKU(){
		String selSkus = getRequest().getParameter("selSkus");
		String selSkuIds = getRequest().getParameter("selSkuIds");
		String skuCategoryId = getRequest().getParameter("skuCategoryId");
		String skuCategoryName = getRequest().getParameter("skuCategoryName");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		if(soContxt == null)
		{
			return "homepage";
		}
		String [] selSkusArray = selSkuIds.split(",");
		List<String> skuIds = new ArrayList<String>();		
		skuIds = Arrays.asList(selSkusArray);
	
		String resultMessage="";
		int totalNoOfSkus = skuIds.size();
		int noOfInactiveSkus=0;
		int noOfActiveSkus = 0;
		int countOfSku = 0;
		List<Integer> noOfInactiveSkusList = null;
		if(!(skuIds.isEmpty()) && StringUtils.isNotEmpty(skuCategoryId)){
			noOfInactiveSkusList = maintainSkuService.checkForDeleteSku(skuIds,skuCategoryId);
			noOfInactiveSkus = noOfInactiveSkusList.size();
			//countOfSku = maintainSkuService.checkForDeleteSkuCategory(skuCategoryId);
			countOfSku = Integer.parseInt(getRequest().getParameter("countOfSku"));
			noOfActiveSkus = totalNoOfSkus - noOfInactiveSkus;
			if(noOfActiveSkus == 0 && noOfInactiveSkus == totalNoOfSkus){
				if(totalNoOfSkus == 1 && countOfSku > 1 && noOfInactiveSkus == 1){
					//getSession().setAttribute("yesNoFlag", "true");
					getSession().setAttribute("yesNoFlag", true);
					getSession().setAttribute("continueFlag", false);	
					// resultMessage = "Are you sure you want to delete the selected SKU "+noOfInactiveSkusList.get(0)+"?";
					resultMessage = "Are you sure you want to delete the selected SKU(s)?";
				}
				else{
					getSession().setAttribute("yesNoFlag", true);
					getSession().setAttribute("continueFlag", false);				
					// resultMessage = "Are you sure you want to delete all " +totalNoOfSkus+ " selected SKU(s)?";
					resultMessage = "Are you sure you want to delete the selected SKU(s)?";
					
					 if(totalNoOfSkus == countOfSku){
						//getSession().setAttribute("yesNoFlag", "true");					
						// resultMessage = "Are you sure you want to delete all " +totalNoOfSkus+ " SKU(s), as it will also delete associated SKU Category " +skuCategoryName+"?";
						 resultMessage = "Deleting the selected SKU(s) will also delete the associated SKU Category <i>"+skuCategoryName+"</i>. Do you wish to proceed?";
						if(totalNoOfSkus == 1){
							//getSession().setAttribute("yesNoFlag", "true");						
							// resultMessage = "Are you sure you want to delete the SKU "+noOfInactiveSkusList.get(0)+", as it will also delete associated SKU Category "+skuCategoryName+"?";						
							resultMessage = "Deleting the SKU <i>"+selSkus+"</i> will also delete the associated SKU Category <i>"+skuCategoryName+"</i>. Do you wish to proceed?";
					    }
				     }	
					}
			}		
			else if(noOfInactiveSkus == 0 && noOfActiveSkus == totalNoOfSkus){
				getSession().setAttribute("continueFlag", true);
				getSession().setAttribute("yesNoFlag", false);				
				// resultMessage = "The selected (" +totalNoOfSkus+ ") SKU(s) are active and cannot be deleted.";
				resultMessage = "All the selected SKUs are Active and cannot be deleted.";
				if(totalNoOfSkus == 1){
					resultMessage = "The selected SKU <i>"+selSkus+"</i> is active and cannot be deleted.";
				}
			}
			else if(noOfActiveSkus != totalNoOfSkus){
					getSession().setAttribute("yesNoFlag", true);
					getSession().setAttribute("continueFlag", false);
					
					// resultMessage = noOfActiveSkus+ " of the selected SKU(s) are active and cannot be deleted. Do you want to delete remaining " +noOfInactiveSkus+ " SKU(s)?";
					resultMessage ="Some of the selected SKU(s) are Active and cannot be deleted. Do you want to delete the remaining SKU(s)?";
			}
			else{
				resultMessage = OrderConstants.NO_SKU_SELECTED;
			}
		}
		StringBuffer skusForDeletion = new StringBuffer();
		if(!noOfInactiveSkusList.isEmpty()){
			for(Integer sku:noOfInactiveSkusList) {
				skusForDeletion.append(sku.toString()+",");
			}
			skusForDeletion.replace(skusForDeletion.length()-1,skusForDeletion.length(),"");
		}
		getSession().setAttribute("resultMessage", resultMessage);
		getRequest().setAttribute("deleteSkuList", skusForDeletion);

		return SUCCESS;
	}
		//method to fetch sku category history
	public String fetchSkuCategoryHistory(){
		Integer categoryId = null;
		String catId = getParameter("categId");
		if(catId!=null){
			categoryId = Integer.parseInt(catId);
		}
		List<SkuCategoryHistoy> skuCategoryHistoyList = maintainSkuService.fetchSkuCategoryHistory(categoryId);
		getSession().setAttribute("skuCategoryHistoyList", skuCategoryHistoyList);
		return SUCCESS;
	}
	
	
	//method to fetch sku history
	public String fetchSkuHistory(){
		Integer skuId = null;
		String skuHistId = getParameter("skuHistId");
		if(skuHistId!=null){
			skuId = Integer.parseInt(skuHistId);
		}
		List<SkuHistory> skuHistoyList = maintainSkuService.fetchSkuHistory(skuId);
		for (Iterator iterator = skuHistoyList.iterator(); iterator.hasNext();) {
	    SkuHistory skuHistory = (SkuHistory) iterator.next();
	    String actDtlsTemp =  skuHistory.getActionDetails();
	    String[] strArray=null;
	    if(actDtlsTemp!=null){
	    	strArray = actDtlsTemp.split("\n");
				if (strArray.length > 0) {
					List<String> list = Arrays.asList(strArray);
					skuHistory.setActionDetailsList(list);
	         }
	    }
		getSession().setAttribute("skuHistoryList", skuHistoyList);
		}
		return SUCCESS;
	}
	
	//creating JSON object
	public String getJSON(Object obj) {
		String rval= "null";
		JSONSerializer serializer = new JSONSerializer();

        if (obj != null) try {
			rval= serializer.exclude("*.class").include("categoryList","skillList","subCategoryList","templateNames").serialize(obj); 
		} catch (Throwable e) {
			logger.error("Caught exception in  SkuMaintenanceAction - getJSON() - "+e);
		}				
		return rval;
	}
	
	public SkuMaintenanceVO getModel() {
		return skuMaintenanceVO;
	}	
		
	public MaintainSkuService getMaintainSkuService() {
		return maintainSkuService;
	}
	
	public void setMaintainSkuService(MaintainSkuService maintainSkuService) {
		this.maintainSkuService = maintainSkuService;
	}
		
}

