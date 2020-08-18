package com.servicelive.sku.maintenance.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.sku.maintenance.BuyerSKUEntity;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;
import com.servicelive.domain.sku.maintenance.BuyerSkuTask;
import com.servicelive.domain.sku.maintenance.BuyerSoTemplate;
import com.servicelive.domain.sku.maintenance.LuServiceTypeTemplate;
import com.servicelive.domain.sku.maintenance.SkillTree;
import com.servicelive.domain.sku.maintenance.SkuCategoryHistoy;
import com.servicelive.domain.sku.maintenance.SkuHistory;
import com.servicelive.sku.maintenance.dao.MaintainSkuDao;
import com.servicelive.sku.maintenance.services.MaintainSkuService;


public class MaintainSkuServiceImpl implements MaintainSkuService {
	private MaintainSkuDao maintainSkuDao;

	
	
	public MaintainSkuDao getMaintainSkuDao() {
		return maintainSkuDao;
	}

	public void setMaintainSkuDao(MaintainSkuDao maintainSkuDao) {
		this.maintainSkuDao = maintainSkuDao;
	}

	public List<BuyerSkuCategory> fetchBuyerSkuCategories(Integer buyerId, Integer skuCategoryId)
	{
		List<BuyerSkuCategory> resultSer = null;
		resultSer = maintainSkuDao.fetchBuyerSkuCategories(buyerId, skuCategoryId);
		return resultSer;
             
	}
	
	public boolean validateBuyerSKUCategory(Integer buyerId, Integer skuCategoryId, String updatedSkuName){
		boolean validatebuyerSKUCategory = true;
		BuyerSkuCategory buyerSkuCat = maintainSkuDao.validateBuyerSKUCategory(buyerId, skuCategoryId, updatedSkuName);
		if(null!=buyerSkuCat){
			validatebuyerSKUCategory=false;
		}
		return validatebuyerSKUCategory;
	}
	public List<BuyerSkuCategory> fetchBuyerSkuCategories(Integer buyerId)
	{
		List<BuyerSkuCategory> resultSer = null;
		resultSer = maintainSkuDao.fetchBuyerSkuCategories(buyerId);
		return resultSer;
             
	}	
	public List<BuyerSKUEntity> fetchBuyerSkuNameByCategory(Integer categoryId, Integer currentSkuId, Integer buyerId)
	{
		List<BuyerSKUEntity> resultSkuName = null;
		resultSkuName = maintainSkuDao.fetchBuyerSkuNameByCategory(categoryId, currentSkuId, buyerId);
		return resultSkuName;
             
	}
	
	//to fetch maincategory list
	public List<Map<Integer, String>> fetchMainCategory(){
		List<Map<Integer, String>> resultList = null;
		resultList = maintainSkuDao.fetchMainCategory();
		return resultList;
	}
	
	//to fetch template list
	public List<Map<Integer, String>> fetchTemplates(int nodeId,int buyerId){
		List<Map<Integer, String>> resultList = null;
		resultList = maintainSkuDao.fetchTemplates(nodeId,buyerId);
		return resultList;
	}
	
	//to fetch category and subcategory list
	public List<SkillTree> fetchCategory(int nodeId){
		List<SkillTree> resultList = null;
		resultList = maintainSkuDao.fetchCategory(nodeId);
		return resultList;
	}
	
	public List<LuServiceTypeTemplate> fetchSkills(int nodeId){
		List<LuServiceTypeTemplate> resultList = null;
		resultList = maintainSkuDao.fetchSkills(nodeId);
		return resultList;
	}
	
	public BuyerSkuTask  fetchSkillTreeBySkuId(Integer skuId)
	{
	BuyerSkuTask resultSkuName = null;
		resultSkuName = maintainSkuDao.fetchSkillTreeBySkuId(skuId);
		return resultSkuName;
             
	}
	public SkillTree fetchSkillTreeBySkuIdByNodeId(Integer nodeId)
	{
		SkillTree skillTree = null;
		skillTree = maintainSkuDao.fetchSkillTreeBySkuIdByNodeId(nodeId);
		return skillTree;
	}
	public BuyerSKUEntity skuDetailsBySkuIdAndCategoryId(Integer skuId,Integer categoryId)
	{
		BuyerSKUEntity  skuDetail = null;
		skuDetail = maintainSkuDao.skuDetailsBySkuIdAndCategoryId(skuId,categoryId);
		return skuDetail;
	}
	@Transactional
	public void updateSkuCategory(String updatedSkuName, String descr, int skuCatId,SecurityContext soContxt) {
		//Updating buyerSkuCategory
		String action = "";
		    BuyerSkuCategory buyerSkuCategory = maintainSkuDao.findBuyerSkuCategoryById(skuCatId);

		    if(buyerSkuCategory != null){	
		    String actCatName = buyerSkuCategory.getCategoryName();
		    String actDescr   = buyerSkuCategory.getCategoryDescr();
		    
		    	if(!actCatName.equals(updatedSkuName)){
		    		action=OrderConstants.NAME_UPDATED;	
			    	buyerSkuCategory.setCategoryName(updatedSkuName);
			    	buyerSkuCategory.setCategoryDescr(descr);	
					SkuCategoryHistoy skuCategoryHistoy = new SkuCategoryHistoy();
					skuCategoryHistoy.setSkuCategoryName(updatedSkuName);					
					skuCategoryHistoy.setAction(action);
					skuCategoryHistoy.setModifiedBy(soContxt.getRoles().getFirstName()+" "+soContxt.getRoles().getLastName());
					skuCategoryHistoy.setModifiedOn(new Date());
					skuCategoryHistoy.setRoleId(String.valueOf(soContxt.getRoleId()));
					skuCategoryHistoy.setBuyerSkuCategory(buyerSkuCategory);
					buyerSkuCategory.getSkuCategoryHistory().add(skuCategoryHistoy);					
					maintainSkuDao.saveBuyerSkuCategory(buyerSkuCategory);								    		
		    	}
		    	if(!actDescr.equals(descr)){
		    		action=OrderConstants.DESC_UPDATED;			
			    	buyerSkuCategory.setCategoryName(updatedSkuName);
			    	buyerSkuCategory.setCategoryDescr(descr);	
					SkuCategoryHistoy skuCategoryHistoy = new SkuCategoryHistoy();
					skuCategoryHistoy.setSkuCategoryName(updatedSkuName);					
					skuCategoryHistoy.setAction(action);
					skuCategoryHistoy.setModifiedBy(soContxt.getRoles().getFirstName()+" "+soContxt.getRoles().getLastName());
					skuCategoryHistoy.setModifiedOn(new Date());
					skuCategoryHistoy.setRoleId(String.valueOf(soContxt.getRoleId()));
					skuCategoryHistoy.setBuyerSkuCategory(buyerSkuCategory);
					buyerSkuCategory.getSkuCategoryHistory().add(skuCategoryHistoy);					
					maintainSkuDao.saveBuyerSkuCategory(buyerSkuCategory);								    		
		    	}
		    }
	}
	
	public BuyerSoTemplate findTemplate(int templateId){
		BuyerSoTemplate buyerSoTemplate = maintainSkuDao.findTemplate(templateId);
		return  buyerSoTemplate;
	}
	
	public LuServiceTypeTemplate findServiceType(int id){
		LuServiceTypeTemplate luServiceTypeTemplate = maintainSkuDao.findServiceType(id);
		return luServiceTypeTemplate;
	}
	public SkillTree findSkillTree(int nodeId){
		SkillTree skillTree = maintainSkuDao.findSkillTree(nodeId);
		return skillTree;
	}
	
	public Buyer findBuyer(int buyerId){
			Buyer buyer = maintainSkuDao.findBuyer(buyerId);
			return buyer;
	}
	
	public BuyerSkuCategory findBuyerSkuCategory(int catgoryId){
		BuyerSkuCategory  buyerSkuCategory= maintainSkuDao.findBuyerSkuCategory(catgoryId);
		return buyerSkuCategory;
	}
	
	public BuyerSKUEntity findBuyerSku(){
		BuyerSKUEntity buyerSku = maintainSkuDao.findBuyerSku();
		return buyerSku;
	}
	
	@Transactional
	public void deleteSku(List<Integer> skuIds, String categoryName,String categoryId){
		//deleting sku id
		if(!skuIds.isEmpty()){
			maintainSkuDao.deleteSku(skuIds,categoryName,categoryId);
		}
	}
	
	public List<Integer> checkForDeleteSku(List<String> skuIds, String skuCategoryId){
		return maintainSkuDao.checkForDeleteSku(skuIds, skuCategoryId);		
	}
	
	public int checkForDeleteSkuCategory(String skuCategoryId){	
		return maintainSkuDao.checkForDeleteSkuCategory(skuCategoryId);	
	}
	
	public List<SkuCategoryHistoy> fetchSkuCategoryHistory(Integer categoryId){
		List<SkuCategoryHistoy> skuCategoryHistoyList= maintainSkuDao.fetchSkuCategoryHistory(categoryId);
		return skuCategoryHistoyList;
	}
	
	public List<SkuHistory> fetchSkuHistory(Integer skuId){
		List<SkuHistory> skuHistoryList= maintainSkuDao.fetchSkuHistory(skuId);
		return skuHistoryList;	
	}
	public void addSkuCategory(Object object){
		maintainSkuDao.addSkuCategory(object);
	}
	/***Description:Checking non funded feature set is enabled for the buyer
	 * @param buyerId
	 * @return
	 */
	public boolean isNonFundedBuyer(Integer buyerId) {
		return maintainSkuDao.isNonFundedBuyer(buyerId);
	}
}