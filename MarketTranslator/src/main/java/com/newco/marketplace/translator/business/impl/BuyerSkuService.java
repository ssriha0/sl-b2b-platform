package com.newco.marketplace.translator.business.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IBuyerSkuService;
import com.newco.marketplace.translator.dao.BuyerMarketAdjustmentDAO;
import com.newco.marketplace.translator.dao.BuyerRetailPrice;
import com.newco.marketplace.translator.dao.BuyerRetailPriceDAO;
import com.newco.marketplace.translator.dao.BuyerSkuDAO;
import com.newco.marketplace.translator.dao.BuyerSkuTaskAssocDAO;
import com.newco.marketplace.translator.dao.BuyerSkuTaskAssocMT;
import com.newco.marketplace.translator.dao.ISkillTreeDAO;
import com.newco.marketplace.translator.dao.SkillTree;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.dto.Task;
import com.newco.marketplace.translator.dto.TaskMapper;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.DateUtil;
import com.newco.marketplace.translator.util.SimpleCache;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.util.MoneyUtil;

public class BuyerSkuService implements IBuyerSkuService {
	
	private static final Logger logger = Logger.getLogger(BuyerSkuService.class);
	
	private BuyerSkuDAO buyerSkuDAO;
	private BuyerRetailPriceDAO buyerRetailPriceDAO;
	private BuyerMarketAdjustmentDAO buyerMarketAdjustmentDAO;
	private BuyerSkuTaskAssocDAO buyerSkuTaskAssocDAO;
	private ISkillTreeDAO skillTreeDAO;
	
	public SkuPrice priceBuyerSku(	SkuPrice skuPrice, 
									String zip, 
									String storeCode, 
									Integer buyerID) 
		throws Exception 
	{
		if(Arrays.asList(Constants.WORK_ORDER_PERMIT_SKUS).contains(skuPrice.getSku())){
			skuPrice.setSellingPrice(skuPrice.getActualSellingPrice());
		}else{		
			Double retailPrice = calculateRetailPrice(skuPrice.getSku(), storeCode, buyerID);
				
			skuPrice.setRetailPrice(retailPrice);		
			Double buyerMarketAdjustment = Double.valueOf(1.0);
				try {
					buyerMarketAdjustment = buyerMarketAdjustmentDAO.findAdjustmentByZip(zip).getAdjustment();
				}
				catch (Exception e) {
					logger.error("Adjustment not found for SKU "+skuPrice.getSku()+
							" and zip "+zip+" - defaulting to "+buyerMarketAdjustment.toString());
				}
				if (buyerMarketAdjustment != null) {
					//Get the big decimal for the margin
					BigDecimal marginRate = BigDecimal.valueOf(skuPrice.getMargin().doubleValue());
					//Scale it to 4th precision
					marginRate = marginRate.setScale(4,RoundingMode.DOWN);
					//If the converted value is greater or less than the actual value do some math
					if (marginRate.floatValue() > skuPrice.getMargin().doubleValue()) {
						marginRate = marginRate.subtract(BigDecimal.valueOf(0.0001));
					} 
					else if (marginRate.floatValue() < skuPrice.getMargin().doubleValue()) {
						marginRate = marginRate.add(BigDecimal.valueOf(0.0001));
					}
					//Again scale it to 4th precision
					marginRate = marginRate.setScale(4,RoundingMode.DOWN);
					//Calculate the price
					Double price = Double.valueOf(skuPrice.getRetailPrice().doubleValue() * (1 - marginRate.doubleValue()) * buyerMarketAdjustment.doubleValue());
					
					skuPrice.setSellingPrice(price);
				}
		}
		return skuPrice;
	}

		public void priceServiceOrder(	List<SkuPrice> skus, 
										String storeCode, 
										CreateDraftRequest request) 
		throws Exception 
	{
			
		List<CreateTaskRequest> tasks = request.getTasks(); 
		String zip = request.getServiceLocation().getZip();
		Integer buyerID = request.getBuyerId();
		double price = 0.0;
		double primaryJobCodePrice = 0.0;
		for (SkuPrice skuPrice : skus) {
			if(!Constants.CALL_COLLECT_COVERAGE_TYPE.equals(skuPrice.getCoverage())){
				priceBuyerSku(skuPrice, zip, storeCode, buyerID);
				
				//Add sku selling price to the task request for future reference
				updateTaskForRequest(tasks, skuPrice);
			
				BigDecimal scaledSellingPrice = BigDecimal.valueOf(skuPrice.getSellingPrice().doubleValue());
				scaledSellingPrice = scaledSellingPrice.setScale(2, RoundingMode.HALF_DOWN);
				
				price = MoneyUtil.add(price,skuPrice.getSellingPrice().doubleValue());
				if(skuPrice.getJobCodeType()!=null && skuPrice.getJobCodeType().equals(Constants.PRIMARY_SKU_TYPE)){
					primaryJobCodePrice = MoneyUtil.add(primaryJobCodePrice,skuPrice.getSellingPrice().doubleValue());
				}
			}
		}
			
		 //If the Primary Job Code 'total order price' is less than 1 dollar, order should stay as draft
		if (primaryJobCodePrice < 1.00) {
			request.setSpendLimitLabor(Double.valueOf(0.0));
			throw new Exception("Order spend limit cannot be 0.00");
		}
		//Scale the price to 2nd precision with proper rounding because the database does not round, just truncates
		BigDecimal scaledPrice = BigDecimal.valueOf(price);
		scaledPrice = scaledPrice.setScale(2, RoundingMode.HALF_DOWN);

		request.setSpendLimitLabor(Double.valueOf(scaledPrice.doubleValue()));
		request.setTasks(tasks);
		
	}
	
	private void updateTaskForRequest(List<CreateTaskRequest> tasks,
				SkuPrice skuPrice) {
		
		String sku = skuPrice.getSku();
		Double sellingPrice = skuPrice.getSellingPrice();
		
		for(CreateTaskRequest task : tasks) {
			if (task.getJobCode().equals(sku)) {
				// set the sku to the task
				task.setLaborPrice(sellingPrice);
			}
		}
	}

	public Integer calculateLeadTime(	List<SkuPrice> skus) 
		throws Exception
	{
		Integer leadTime = Integer.valueOf(-1);
		//loop lead times and find lowest value
//		if (skus != null) {
			for (SkuPrice sku : skus) {
				if (leadTime.intValue() < 0) {
					leadTime = sku.getLeadTime();
				}
				else if (sku.getLeadTime().intValue() < leadTime.intValue()) {
					leadTime = sku.getLeadTime();
				}
			}
//		}
		return leadTime;
	}
	
	public Date getServiceEndDate(	List<SkuPrice> skus, 
									Date serviceStartDate) 
		throws Exception
	{
		Date serviceEndDate = null;
//		try 
		{
//			if (serviceStartDate != null)
			{
				Integer leadTime = calculateLeadTime(skus);
				//have to set the var again since addDaysToDate is using a cal object
				serviceEndDate = DateUtil.addDaysToDate(serviceStartDate, leadTime);
			}
		}
//		catch (Exception e) 
//		{
//			logger.error("Error calculating service date", e);
//		}
		return serviceEndDate;
	}
	
	public List<SkuPrice> getSkuPrices(	List<SkuPrice> skus, 
										String zip, 
										String storeCode, 
										Integer buyerID) 
		throws Exception 
	{
		List<SkuPrice> skuPrices = new ArrayList<SkuPrice>();
		for(SkuPrice skuPrice : skus) 
		{
			skuPrices.add(priceBuyerSku(skuPrice, zip, storeCode, buyerID));
		}
		return skuPrices;
	}
	
	public BuyerSkuDAO getBuyerSkuDAO() {
		return buyerSkuDAO;
	}

	public BuyerRetailPriceDAO getBuyerRetailPriceDAO() {
		return buyerRetailPriceDAO;
	}

	public void setBuyerRetailPriceDAO(BuyerRetailPriceDAO buyerRetailPriceDAO) {
		this.buyerRetailPriceDAO = buyerRetailPriceDAO;
	}

	public void setBuyerSkuDAO(BuyerSkuDAO buyerSkuDAO) {
		this.buyerSkuDAO = buyerSkuDAO;
	}

	public BuyerMarketAdjustmentDAO getBuyerMarketAdjustmentDAO() {
		return buyerMarketAdjustmentDAO;
	}

	public void setBuyerMarketAdjustmentDAO(
			BuyerMarketAdjustmentDAO buyerMarketAdjustmentDAO) {
		this.buyerMarketAdjustmentDAO = buyerMarketAdjustmentDAO;
	}

	public List<Task> translateSKUs(List<Task> skus) {
		List<Task> out = new ArrayList<Task>();
		for (Task sku : skus) {
			out.addAll(translateSKU(sku));
		}
		return out;
	}
	
	public List<Task> translateSKU(Task in) {
		List<Task> tasks = new ArrayList<Task>();
		//sears oms codes will come in as 05542
		try 
		{
			String sku = in.getSku();
			String title = in.getTitle();
			List<BuyerSkuTaskAssocMT> skuTasks = getBuyerSkuTaskAssocDAO().findBySkuAndSpecialtyCode(sku, in.getSpecialtyCode());
			
			if (skuTasks != null && skuTasks.size() > 0) 
			{
				Boolean primaryTaskNotFound = Boolean.TRUE;
				for (BuyerSkuTaskAssocMT taskAssoc : skuTasks) 
				{
					Task newTask = TaskMapper.mapDomainToDTO(taskAssoc, in.getComments(), title, in.isDefaultTask());
					//TODO remove this call and make it with the above one in a join.
					SkillTree skill = skillTreeDAO.findById(taskAssoc.getNodeId());
					if (	skill != null 
							&& skill.getLevel().intValue() == 3) 
					{
						newTask.setTaskSubCatNodeID(skill.getNodeId());					
						newTask.setTaskNodeID(skill.getParentNode());	
					}
					if (	in.getServiceTypeId() != null 
							&& in.getServiceTypeId().intValue() == Constants.PRIMARY_JOB_CODE 
							&& primaryTaskNotFound.booleanValue()) 
					{
						newTask.setServiceTypeId(Integer.valueOf(Constants.PRIMARY_JOB_CODE));
						//Sets main service category,category and subcategory names in Task object
						if(null != skill){
							this.mapTaskName(skill,newTask);	
						}
						primaryTaskNotFound = Boolean.FALSE; // i.e. primary task found
					} 
					else 
					{
						newTask.setServiceTypeId(Integer.valueOf(Constants.ALTERNAME_JOB_CODE));
					}
					tasks.add(newTask);
				}
			}
			else 
			{
				in.setTaskNodeID(Integer.valueOf(0));
				in.setSkillID(Integer.valueOf(8));
				tasks.add(in);
			}
		}
		catch (Exception e) 
		{
			//set the taskID to 0 to trigger attention status and message
			logger.error(e.getMessage(), e);
			logger.info( "Exception caught: " + e.getMessage() + " Setting default taskNodeID to 0 and default skillTreeID to 8" );
			in.setTaskNodeID(Integer.valueOf(0));
			in.setSkillID(Integer.valueOf(8));
			tasks.add(in);
		}
		return tasks;
	}

	/**
	 * Method to map the main service category, category and subcategory names 
	 * from the skills
	 * @param skill
	 * @param newTask
	 */
	private void mapTaskName(SkillTree skill,Task newTask){
		int skillLevelCount = skill.getLevel().intValue();		
		//Setting main service category
		if(skillLevelCount == Constants.MAIN_SERVICE_CAT_SKILL_LEVEL){
			newTask.setMainServiceCatName(skill.getNodeName());
		}	
		
		while(skillLevelCount > 1){
			Integer parentNode = skill.getParentNode();
			if(null != parentNode){
				SkillTree parentSkill = skillTreeDAO.findById(parentNode);			
				if(Constants.SUB_CAT_SKILL_LEVEL == skillLevelCount){
					//Set subcategory and category names if level=3
					newTask.setTaskSubCatName(skill.getNodeName());
					newTask.setTaskCatName(parentSkill.getNodeName());
				}else if(Constants.CATEGORY_SKILL_LEVEL == skillLevelCount){
					//Set category and main service category names if level=2
					newTask.setTaskCatName(skill.getNodeName());
					newTask.setMainServiceCatName(parentSkill.getNodeName());
				}
				
				skill.setNodeName(parentSkill.getNodeName());
				skill.setParentNode(parentSkill.getParentNode()) ;
				skillLevelCount--;
			}
		}	
	}
	
	/**
	 * Method to get the retailPrice of the given sku	
	 * @param String sku
	 * @param String storeCode
	 * @param int buyerID
	 * @return Double retailPrice
	 */
	public Double calculateRetailPrice(	String sku, 
			String storeCode,
			Integer buyerID)
	{

		BuyerRetailPrice buyerPrice = null;
		
		//Cache data from Buyer_retail_price. caching is done with String-Object mapping and string is constructed using sku:stroecode:buyerId.
		String cacheKey = sku + ":" + storeCode + ":" + String.valueOf(buyerID.intValue());
		Double retailPrice = (Double)SimpleCache.getInstance().get(cacheKey);
		if(retailPrice== null)
		{
			logger.info("get retail price from cache");
			try {
				buyerPrice = buyerRetailPriceDAO.findByStoreNoAndSKU(storeCode,	sku, buyerID,Constants.DEFAULT_STORE_NO);
				if(buyerPrice != null)
					retailPrice = buyerPrice.getRetailPrice();
			} catch (Exception ex) {
			    logger.info("RetailPrice not be found either with the default store");
			}
			//Caching interval is set to 60 mins for now. 
			SimpleCache.getInstance().put(cacheKey, retailPrice, SimpleCache.ONE_HOUR);
		}
		return retailPrice;
	}	
	
	public BuyerSkuTaskAssocDAO getBuyerSkuTaskAssocDAO() {
		return buyerSkuTaskAssocDAO;
	}

	public void setBuyerSkuTaskAssocDAO(BuyerSkuTaskAssocDAO buyerSkuTaskAssocDAO) {
		this.buyerSkuTaskAssocDAO = buyerSkuTaskAssocDAO;
	}

	public ISkillTreeDAO getSkillTreeDAO() {
		return skillTreeDAO;
	}

	public void setSkillTreeDAO(ISkillTreeDAO skillTreeDAO) {
		this.skillTreeDAO = skillTreeDAO;
	}

}
