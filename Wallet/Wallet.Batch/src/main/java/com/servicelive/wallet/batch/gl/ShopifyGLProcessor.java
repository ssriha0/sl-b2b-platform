
package com.servicelive.wallet.batch.gl;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.ScriptEngineFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.IProcessor;
import com.servicelive.wallet.batch.gl.dao.IGLDao;
import com.servicelive.wallet.batch.gl.dao.ISHCOMSDao;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLFeedVO;
import com.servicelive.wallet.batch.gl.vo.SKUDetails;
import com.servicelive.wallet.batch.gl.vo.ShopifyDetailsVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLProcessLogVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLRuleVO;

/**
 * The Class GLProcessor.
 * This class is used to generate the GL file for buyer 9000
 */
public class ShopifyGLProcessor extends BaseFileProcessor implements IProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ShopifyGLProcessor.class);

	/** The gl dao. */
	private IGLDao glDao = null;

	/** The gl transformer. */
	private GLTransformer glTransformer;

	/** The gl writer. */
	private IGLWriter glWriter;

	/** The shc supplier dao. */
	private ISHCOMSDao shcSupplierDao = null;
	
    private Timestamp glPostedDate = null;
    private Timestamp startDate = null;
    private Timestamp endDate = null;
    private String fileName = null;
    private List<ShopifyDetailsVO> shopifyUpdateList = new ArrayList<ShopifyDetailsVO>();
    
	public static List<String> CREATED_LIST(){
		List<String> CREATED_LIST = new ArrayList<String>();
		CREATED_LIST.add("1121");
		return CREATED_LIST;
	}
	
	public static List<String> INCREASE_LIST(){
		List<String> INCREASE_LIST = new ArrayList<String>();
		INCREASE_LIST.add("1201");
		INCREASE_LIST.add("1135");
		return INCREASE_LIST;
	}
	
	public static List<String> DECREASE_LIST(){
		List<String> DECREASE_LIST = new ArrayList<String>();
		DECREASE_LIST.add("1270");
		DECREASE_LIST.add("1470");
		return DECREASE_LIST;
	}	
	
	public static List<String> CANCELLED_LIST(){
		List<String> CANCELLED_LIST = new ArrayList<String>();
		CANCELLED_LIST.add("1385");
		CANCELLED_LIST.add("1396");
		return CANCELLED_LIST;
	}
	
	public static List<String> CLOSED_LIST(){
		List<String> CLOSED_LIST = new ArrayList<String>();
		CLOSED_LIST.add("1405");
		// CLOSED_LIST.add("1305");  Commented this to avoid duplicate entry for CC rule
		return CLOSED_LIST;
	}
    
	public static HashMap<String, List<String>> ledgerRuleMap(){
		HashMap<String, List<String>> ledgerRuleMap = new HashMap<String, List<String>>();
		ledgerRuleMap.put(CommonConstants.CREATED, CREATED_LIST());
		ledgerRuleMap.put("Increase", INCREASE_LIST());
		ledgerRuleMap.put("Decrease", DECREASE_LIST());
		ledgerRuleMap.put(CommonConstants.CANCELLED, CANCELLED_LIST());
		ledgerRuleMap.put(CommonConstants.CLOSED, CLOSED_LIST());
		return ledgerRuleMap;
	}    
	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		this.endDate = new java.sql.Timestamp(cal.getTime().getTime());
		
		// Go one day back to get the GL information
		cal.add(Calendar.DATE, -1);
		this.startDate = new java.sql.Timestamp(cal.getTime().getTime());
		
		this.glPostedDate = new java.sql.Timestamp(new Date().getTime());
		
		long start = System.currentTimeMillis();
		processDetails();
		long end = System.currentTimeMillis();
		logger.info("SL-20730: ShopifyGLProcessor.processDetails() total execution time:"+(end-start));
	}
	
	/**
	 * @throws SLBusinessServiceException
	 */
	private void processDetails() throws SLBusinessServiceException {

		// File name generation for GL Report
		fileName = createFileName();
		
		// Iterating the list of buyers needing custom GL
		for (String buyerId : CommonConstants.CUSTOM_GL_BUYERS) {
			// Get the GL rules
			Map<String, List<ShopifyGLRuleVO>> ruleMap = getCustomRulesForGL(buyerId);

			if (null != ruleMap) {

				Integer glProcessId = null;
				List<GLDetailVO> glDetails = null;

				for (String status : CommonConstants.STATUS_LIST) {

					if (StringUtils.isNotBlank(status)) {

						//Get the list of created/cancelled/closed orders
						if(null != ruleMap.get(status)){
							glDetails = getOrderDetailsForCustomGL(buyerId, status, ruleMap.get(status));							
						}
						
						if(null != shopifyUpdateList && !shopifyUpdateList.isEmpty()){
							if(null != glDetails && !glDetails.isEmpty()){
								//Insert GL details
								glProcessId = insertGLDetails(glDetails, glProcessId);
							}

							//Update shopify_gl_state for the service orders
							updateSOGLStatus();
						}
					}

				}
				
				//generating GL file
				if(null != glProcessId){
					generateGLFile(glProcessId);
				}

			}
		}
		
		/*//Get the GL rules
		Map<String, List<ShopifyGLRuleVO>> ruleMap = getRulesForGL();
		
		if(null != ruleMap){
			
			//File name generation for GL Report
			fileName = createFileName();
			
			Integer glProcessId = null;
			List<GLDetailVO> glDetails = null;
			
			for(String status : CommonConstants.STATUS_LIST){
				
				if(StringUtils.isNotBlank(status)){
					
					//Get the list of created/cancelled/closed orders
					glDetails = getShopifyOrderDetails(status, ruleMap.get(status));
					
					if(null != shopifyUpdateList && !shopifyUpdateList.isEmpty()){
						if(null != glDetails && !glDetails.isEmpty()){
							//Insert GL details
							glProcessId = insertGLDetails(glDetails, glProcessId);
						}

						//Update shopify_gl_state for the service orders
						updateSOGLStatus();
					}
				}
			}
			
			//generating GL file
			if(null != glProcessId){
				generateGLFile(glProcessId);
			}
		}*/
	}
 
	/**
	 * This method is used to generate GL file
	 * 
	 * @param glProcessId
	 * @throws SLBusinessServiceException
	 */
	private void generateGLFile(Integer glProcessId) throws SLBusinessServiceException{
		
		try{
			//get the GL details for buyer 9000
			List<GLDetailVO> glDetailVOList = glDao.getShopifyGLDetails(glProcessId);
				
			if(null != glDetailVOList && !glDetailVOList.isEmpty()){
				//Mapping GLDetailVO list to GLFeedVO list
				ArrayList<GLFeedVO> glFeedItemList = glTransformer.convertGLDetailVOListToGLFeedVOList(glDetailVOList,startDate);
					
				if(null != glFeedItemList && !glFeedItemList.isEmpty()){
						
					//Writing the GL file
					glWriter.writeGLFeed(glFeedItemList, CommonConstants.SHOPIFY_BUYER+fileName);
				}
			}
		}catch (DataServiceException e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.generateGLFile()", e);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.generateGLFile()", e);
		}
	}

	/**
	 * This method is used to insert GL details
	 * @param glDetails
	 * @return Integer
	 * @throws SLBusinessServiceException
	 */
	private Integer insertGLDetails(List<GLDetailVO> glDetails, Integer glProcessId) throws SLBusinessServiceException{
		
		try{
			// Insert GL details into 'shopify_gl_log' table
			if(null == glProcessId){
				Date batchExecutionDate = DateUtils.truncate(startDate, Calendar.DATE);
				ShopifyGLProcessLogVO shopifyGLProcessLogVO = new ShopifyGLProcessLogVO(batchExecutionDate,batchExecutionDate,fileName);
				glProcessId = glDao.insertGLDetailsLog(shopifyGLProcessLogVO);
			}
			
			// setting the value of glProcessId to the VO list
	        for(GLDetailVO glDetail : glDetails){
	            glDetail.setGlProcessId(glProcessId);
	        }
	        
			//Insert GL details into 'shopify_gl_detail' table
			glDao.insertGLDetails(glDetails);
			return glProcessId;
			
		}catch (DataServiceException e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.insertGLDetails()", e);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.insertGLDetails()", e);
		}
		
	}
	
	/**
	 * This method is used to get the list of orders created/cancelled/closed orders
	 * @param rules
	 * @param status
	 * @param ruleMap 
	 * @return  List<GLDetailVO>
	 * @throws SLBusinessServiceException
	 */
	private List<GLDetailVO> getOrderDetailsForCustomGL(String buyerId, String status,
			List<ShopifyGLRuleVO> rules) throws SLBusinessServiceException{
		
		List<GLDetailVO> glDetails = null;		
		
		try{
			
			if(CommonConstants.SHOPIFY_BUYER_ID.equals(buyerId)){
				/* Get the 'Shopify Order Number', 'Shopify Order SKU Price1', 'Shopify Order SKU Price2', 
				'Shopify Order SKU Price3', 'Shopify Order SKU Price4' custom references and the category
				for buyer 9000 orders */ 
				List<ShopifyDetailsVO> shopifyDetails = shcSupplierDao.getShopifyOrderDetails(startDate, endDate, status);
				if(CommonConstants.CANCELLED.equalsIgnoreCase(status)){
					for(String canceledStatus : CommonConstants.CANCELLED_STATUS_LIST){
						shopifyDetails.addAll(shcSupplierDao.getShopifyOrderDetails(startDate, endDate, canceledStatus));
					}
				}
				
				//Get the list of GLDetailVO for the shopify orders
				if(null != shopifyDetails && !shopifyDetails.isEmpty()){
					glDetails = getGLDetails(shopifyDetails, rules, status);
				}
			}else if(CommonConstants.RELAY_BUYER_ID.equals(buyerId) || CommonConstants.TECH_TALK_BUYER_ID.equals(buyerId)){
				List<String> ruleList = ledgerRuleMap().get(status);
				
				List<ShopifyDetailsVO> orderDetails = shcSupplierDao.getOrderDetailsForGL(startDate, endDate, status, ruleList, buyerId);
								
				//Get the list of GLDetailVO for the relay orders
				if(null != orderDetails && !orderDetails.isEmpty()){
					glDetails = getRelayOrTechTalkGLDetails(orderDetails, rules, status, buyerId);
					//glDetails = getRelayGLDetails(relayDetails, rules, status);
				}
			}
			
			return glDetails;
			
		}catch (DataServiceException e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getShopifyDetails()", e);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getShopifyDetails()", e);
		}
	}	

	/**
	 * This method is used to get the list of orders created/cancelled/closed orders
	 * @param rules
	 * @param status
	 * @return  List<GLDetailVO>
	 * @throws SLBusinessServiceException
	 */
	private List<GLDetailVO> getShopifyOrderDetails(String status,
			List<ShopifyGLRuleVO> rules) throws SLBusinessServiceException{
		
		List<GLDetailVO> glDetails = null;		
		
		try{
			/* Get the 'Shopify Order Number', 'Shopify Order SKU Price1', 'Shopify Order SKU Price2', 
			'Shopify Order SKU Price3', 'Shopify Order SKU Price4' custom references and the category
			for buyer 9000 orders */ 
			List<ShopifyDetailsVO> shopifyDetails = shcSupplierDao.getShopifyOrderDetails(startDate, endDate, status);
			if(CommonConstants.CANCELLED.equalsIgnoreCase(status)){
				for(String canceledStatus : CommonConstants.CANCELLED_STATUS_LIST){
					shopifyDetails.addAll(shcSupplierDao.getShopifyOrderDetails(startDate, endDate, canceledStatus));
				}
			}
			
			//Get the list of GLDetailVO for the shopify orders
			if(null != shopifyDetails && !shopifyDetails.isEmpty()){
				glDetails = getGLDetails(shopifyDetails, rules, status);
			}
			return glDetails;
			
		}catch (DataServiceException e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getShopifyDetails()", e);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getShopifyDetails()", e);
		}
	}
	
	/**
	 * This method is used to get the custom GL rules for buyer 9000 and 3333
	 * @return  Map<String, List<ShopifyGLRuleVO>>
	 * @throws SLBusinessServiceException
	 */
	private Map<String, List<ShopifyGLRuleVO>> getCustomRulesForGL(String buyerId) throws SLBusinessServiceException{
		
		try{
			//Get the rule details from 'shopify_gl_reporting' table
			List<ShopifyGLRuleVO> glRuleDetails = glDao.getCustomRulesForGL(buyerId);
			
			//Getting rule map based on transaction type
			Map<String, List<ShopifyGLRuleVO>> ruleMap = new HashMap<String, List<ShopifyGLRuleVO>>();
			if(null != glRuleDetails && !glRuleDetails.isEmpty()){
				for(ShopifyGLRuleVO ruleVO : glRuleDetails){
					String transactionType = ruleVO.getTransactionType();
					if(StringUtils.isNotBlank(transactionType)){
						if(ruleMap.containsKey(transactionType)){
							List<ShopifyGLRuleVO> ruleList = ruleMap.get(transactionType);
							ruleList.add(ruleVO);
							ruleMap.put(transactionType, ruleList);
						}else{
							List<ShopifyGLRuleVO> ruleList = new ArrayList<ShopifyGLRuleVO>();
							ruleList.add(ruleVO);
							ruleMap.put(transactionType, ruleList);
						}
					}
				}
			}
			return ruleMap;
			
		}catch (DataServiceException e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getRulesForGL()", e);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getRulesForGL()", e);
		}
	}	
	
	/**
	 * This method is used to get the GL rules for buyer 9000
	 * @return  Map<String, List<ShopifyGLRuleVO>>
	 * @throws SLBusinessServiceException
	 */
	private Map<String, List<ShopifyGLRuleVO>> getRulesForGL() throws SLBusinessServiceException{
		
		try{
			//Get the rule details from 'shopify_gl_reporting' table
			List<ShopifyGLRuleVO> glRuleDetails = glDao.getRulesForGL();
			
			//Getting rule map based on transaction type
			Map<String, List<ShopifyGLRuleVO>> ruleMap = new HashMap<String, List<ShopifyGLRuleVO>>();
			if(null != glRuleDetails && !glRuleDetails.isEmpty()){
				for(ShopifyGLRuleVO ruleVO : glRuleDetails){
					String transactionType = ruleVO.getTransactionType();
					if(StringUtils.isNotBlank(transactionType)){
						if(ruleMap.containsKey(transactionType)){
							List<ShopifyGLRuleVO> ruleList = ruleMap.get(transactionType);
							ruleList.add(ruleVO);
							ruleMap.put(transactionType, ruleList);
						}else{
							List<ShopifyGLRuleVO> ruleList = new ArrayList<ShopifyGLRuleVO>();
							ruleList.add(ruleVO);
							ruleMap.put(transactionType, ruleList);
						}
					}
				}
			}
			return ruleMap;
			
		}catch (DataServiceException e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getRulesForGL()", e);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Error in ShopifyGLProcessor.getRulesForGL()", e);
		}
	}
	
	/**
	 * This method is used to get the GL details to be inserted into 'shopify_gl_detail' table
	 * @param shopifyDetails
	 * @param glRuleDetails
	 * @param ruleMap 
	 * @return List<GLDetailVO>
	 */
	private List<GLDetailVO> getRelayOrTechTalkGLDetails(List<ShopifyDetailsVO> relayDetails, 
			List<ShopifyGLRuleVO> glRuleDetails, String status, String buyerId) {

		// Group the orders based on Shopify Order Number and category
		Map<String, HashMap<String, List<ShopifyDetailsVO>>> shopifyOrderMap = new HashMap<String, HashMap<String, List<ShopifyDetailsVO>>>();
		List<GLDetailVO> glDetailsVO = new ArrayList<GLDetailVO>();
		List<SKUDetails> skuDetails = new ArrayList<SKUDetails>();
		Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
		Date createdDate = startDate;
		Double transactionAmount = 0.0d;
		Double taxAmountReal = 0.0d;
		Double taxOnParts = 0.0d;
		Double taxOnLabor = 0.0d;
		Double taxAmountPersonal = 0.0d;

		// Iterating each order in a particular status
		for (ShopifyDetailsVO relayDetail : relayDetails) {
			transactionAmount = 0.0d;
			
			try{
				transactionAmount = Double.parseDouble(relayDetail.getRelayTotalPrice());
				logger.info("relayDetail.getTaxPercentLabor(): "+relayDetail.getTaxPercentLabor());
				if(null != relayDetail.getTaxPercentLabor() && !relayDetail.getTaxPercentLabor().equals(BigDecimal.ZERO)){
					taxOnLabor = (transactionAmount * relayDetail.getTaxPercentLabor().doubleValue())/(1+relayDetail.getTaxPercentLabor().doubleValue());
				}
				logger.info("relayDetail.getTaxPercentParts(): "+relayDetail.getTaxPercentParts());
				if(null != relayDetail.getTaxPercentParts() && !relayDetail.getTaxPercentParts().equals(BigDecimal.ZERO)){
					//7777 does not have parts
					//taxOnParts = transactionAmount * relayDetail.getTaxPercentParts().doubleValue();
				}
				taxAmountReal = taxOnLabor + taxOnParts;
				logger.info("taxAmountReal: "+taxAmountReal);
			}catch (Exception e) {
				logger.error("Error while parsing the Relay Total or Tax Price for SO #"+relayDetail.getSoId());
				relayDetail.setStatus(CommonConstants.ERROR
						+ status.toUpperCase());
				shopifyUpdateList.add(relayDetail);
				continue;
			}
			
			// Iterating each rule for that status in consideration
			for (ShopifyGLRuleVO rules : glRuleDetails) {
				try {
					GLDetailVO detailVO = null;
					if(rules.getLedgerRule().equalsIgnoreCase(CommonConstants.RULE_TAX_REAL) || rules.getLedgerRule().equalsIgnoreCase(CommonConstants.RULE_RTAX_REAL)){
						detailVO = new GLDetailVO(rules.getGlUnit(),
								rules.getGlDivision(), rules.getGlAccountNo(),
								rules.getGlCategory(), rules.getLedgerRule(),
								rules.getMultiplier() * taxAmountReal, rules.getDescr());

					}else if(rules.getLedgerRule().equalsIgnoreCase(CommonConstants.RULE_TAX_PERS) || rules.getLedgerRule().equalsIgnoreCase(CommonConstants.RULE_RTAX_PERS)){
						detailVO = new GLDetailVO(rules.getGlUnit(),
								rules.getGlDivision(), rules.getGlAccountNo(),
								rules.getGlCategory(), rules.getLedgerRule(),
								rules.getMultiplier() * taxAmountPersonal, rules.getDescr());
					}
					else if(rules.getPricingExpression() !=null){
						// SLT-4200 :Added this if block to calculate transaction amount based on pricing expression
						detailVO = new GLDetailVO(rules.getGlUnit(),
								rules.getGlDivision(), rules.getGlAccountNo(),
								rules.getGlCategory(), rules.getLedgerRule(),
								rules.getMultiplier() * calculatePriceByExpression(rules.getPricingExpression(), relayDetail), rules.getDescr());
					
					}
					else{
						detailVO = new GLDetailVO(rules.getGlUnit(),
								rules.getGlDivision(), rules.getGlAccountNo(),
								rules.getGlCategory(), rules.getLedgerRule(),
								rules.getMultiplier() * transactionAmount, rules.getDescr());
					}
					if (null != detailVO) {
						detailVO.setNpsOrder(relayDetail.getRelayOrderNumber());
						detailVO.setTransactionId(relayDetail.getLedgerTransId());
						detailVO.setOrderNumber(relayDetail.getSoId());
						detailVO.setGlDatePosted(this.glPostedDate.toString());
						// created date will be the cancelled date in case of
						// cancelled orders
						detailVO.setTransactionDate(new java.sql.Timestamp(
								createdDate.getTime()).toString());
						detailVO.setPeopleSoftFile(fileName);
						detailVO.setBuyerId(Integer
								.parseInt(buyerId));
						detailVO.setSoFundingType(CommonConstants.FUNDING_TYPE);
						detailVO.setSellValue(0.00);

						glDetailsVO.add(detailVO);

						// If mapping successful, update the shopify GL status
						shopifyUpdateList.add(relayDetail);
					}
				} catch (Exception e) {
					logger.error("Exception while getting the details of Relay order with so id "
							+ relayDetail.getSoId() + " and relay order no " + relayDetail.getRelayOrderNumber());

					// if there is a problem with any one order, then update all
					// the
					// orders with the same shopify order number and category as
					// error.
					relayDetail.setStatus(CommonConstants.ERROR
							+ status.toUpperCase());
					shopifyUpdateList.add(relayDetail);
				}

			}
			

		}

		return glDetailsVO;
	}	

	/**
	 * This method is used to get the GL details to be inserted into 'shopify_gl_detail' table
	 * @param shopifyDetails
	 * @param glRuleDetails
	 * @return List<GLDetailVO>
	 */
	private List<GLDetailVO> getGLDetails(List<ShopifyDetailsVO> shopifyDetails, 
			 List<ShopifyGLRuleVO> glRuleDetails, String status) {
		
		//Group the orders based on Shopify Order Number and category
		Map<String, HashMap<String, List<ShopifyDetailsVO>>> shopifyOrderMap = 
				new HashMap<String, HashMap<String, List<ShopifyDetailsVO>>>();
		List<GLDetailVO> glDetailsVO = new ArrayList<GLDetailVO>();
		List<SKUDetails> skuDetails = new ArrayList<SKUDetails>();
		Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
		
		createDetailsMap(shopifyDetails, shopifyOrderMap);
		
		//Calculate the total SKU price and tax for each group
		Date createdDate = startDate;
		for(String shopifyOrderNo : shopifyOrderMap.keySet()){
			
			Map<String, List<ShopifyDetailsVO>> categoryMap = shopifyOrderMap.get(shopifyOrderNo);
			for(String category : categoryMap.keySet()){
				
				//Get the created date for a shopify order number and category
				createdDate = getCreatedDate(categoryMap.get(category), createdDate);
				
				try{
					//Get the sku details for a shopify order number and category
					skuDetails.clear();
					getSKUDetails(categoryMap.get(category), skuDetails);
					
					//calculate the total price and tax	
					priceMap.clear();
					calculateAmountAndTax(skuDetails, priceMap);
					
					//Map the details to GLDetailVO
					mapGLDetails(shopifyOrderNo, category, createdDate, glRuleDetails, priceMap, glDetailsVO);
					
					//If mapping successful, update the shopify GL status
					shopifyUpdateList.addAll(categoryMap.get(category));
					
				}catch(Exception e){
					logger.error("Exception while getting the price details of order with Shopify order number " + shopifyOrderNo + " and category " + category);
					
					//if there is a problem with any one order, then update all the orders with the same shopify order number and category as error.
					for(ShopifyDetailsVO order : categoryMap.get(category)){
						order.setStatus(CommonConstants.ERROR + status.toUpperCase());
					}
					shopifyUpdateList.addAll(categoryMap.get(category));
				}
			}
		}	
		return glDetailsVO;
	}

	/**
	 * The method is to set the details in GLDetailVO object
	 * @param categoryList
	 * @param createdDate
	 * @return Date
	 */
	private Date getCreatedDate(List<ShopifyDetailsVO> categoryList, Date createdDate) {
		List<Date> dates = new ArrayList<Date>();
        for(int i=0; i< categoryList.size(); i++){
            if(null != categoryList.get(i) && null != categoryList.get(i).getCreatedDate()){
            	dates.add(categoryList.get(i).getCreatedDate());
            }
        }
        Collections.sort(dates);
        if(null != dates && !dates.isEmpty()){
            createdDate = dates.get(0);
        }
		return createdDate;
	}

	/**
	 * The method is to set the details in GLDetailVO object
	 * @param shopifyOrderNo
	 * @param category
	 * @param createdDate
	 * @param glRuleDetails
	 * @param priceMap
	 * @param glDetailsVO
	 */
	private void mapGLDetails(String shopifyOrderNo, String category, Date createdDate, 
			List<ShopifyGLRuleVO> glRuleDetails, Map<String, BigDecimal> priceMap,  List<GLDetailVO> glDetailsVO) {

		for(String price : priceMap.keySet()){
			if(StringUtils.isNotBlank(price) && null != glRuleDetails && !glRuleDetails.isEmpty()){
				for(ShopifyGLRuleVO rules : glRuleDetails){
					if(null != rules){
						GLDetailVO detailVO = mapPriceDetails(price, rules, priceMap);
						if(null != detailVO){
							detailVO.setNpsOrder(category);
							detailVO.setOrderNumber(shopifyOrderNo);
							detailVO.setGlDatePosted(this.glPostedDate.toString());
							//created date will be the cancelled date in case of cancelled orders
							detailVO.setTransactionDate(new java.sql.Timestamp(createdDate.getTime()).toString());
							detailVO.setPeopleSoftFile(fileName);
							detailVO.setBuyerId(Integer.parseInt(CommonConstants.SHOPIFY_BUYER_ID));
							detailVO.setSoFundingType(CommonConstants.FUNDING_TYPE);
							detailVO.setSellValue(0.00);
							glDetailsVO.add(detailVO);
						}
					}
				}
			}
		}
	}

	/**
	 * The method is to calculate the SKU price and
	 * tax based on tax type
	 * Customer deposit = Deferred revenue - Tax
	 * @param skuDetails
	 * @param priceMap
	 */
	private void calculateAmountAndInclusiveTax(List<SKUDetails> skuDetails, Map<String, BigDecimal> priceMap) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal realTax = BigDecimal.ZERO;
		BigDecimal personalTax = BigDecimal.ZERO;
		BigDecimal customerDeposit = BigDecimal.ZERO;
		
		for(SKUDetails details : skuDetails){
			if(null != details && null != details.getSkuPrice()){
				totalPrice = totalPrice.add(details.getSkuPrice());
					
				if(null != details.getTaxPercentage() && null != details.getTaxType()){
					if(CommonConstants.TAX_REAL.equalsIgnoreCase(details.getTaxType())){
						realTax = realTax.add(details.getSkuPrice().multiply(details.getTaxPercentage()));
					}
					else if(CommonConstants.TAX_PERSONAL.equalsIgnoreCase(details.getTaxType())){
						personalTax = personalTax.add(details.getSkuPrice().multiply(details.getTaxPercentage()));
					}
				}
			}
		}
		customerDeposit = totalPrice.subtract(realTax.add(personalTax));
		priceMap.put(CommonConstants.TOTAL_PRICE, totalPrice);
		priceMap.put(CommonConstants.REAL_TAX, realTax);
		priceMap.put(CommonConstants.PERSONAL_TAX, personalTax);
		priceMap.put(CommonConstants.CUSTOMER_DEPOSIT, customerDeposit);
	}
	
	/**
	 * The method is to calculate the SKU price and
	 * tax based on tax type
	 * Customer deposit = Deferred revenue + Tax
	 * @param skuDetails
	 * @param priceMap
	 */
	private void calculateAmountAndTax(List<SKUDetails> skuDetails, Map<String, BigDecimal> priceMap) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal realTax = BigDecimal.ZERO;
		BigDecimal personalTax = BigDecimal.ZERO;
		BigDecimal customerDeposit = BigDecimal.ZERO;
		
		for(SKUDetails details : skuDetails){
			if(null != details && null != details.getSkuPrice()){
				totalPrice = totalPrice.add(details.getSkuPrice());
					
				if(null != details.getTaxPercentage() && null != details.getTaxType()){
					if(CommonConstants.TAX_REAL.equalsIgnoreCase(details.getTaxType())){
						realTax = realTax.add(details.getSkuPrice().multiply(details.getTaxPercentage()));
					}
					else if(CommonConstants.TAX_PERSONAL.equalsIgnoreCase(details.getTaxType())){
						personalTax = personalTax.add(details.getSkuPrice().multiply(details.getTaxPercentage()));
					}
				}
			}
		}
		customerDeposit = totalPrice.add(realTax.add(personalTax));
		priceMap.put(CommonConstants.TOTAL_PRICE, totalPrice);
		priceMap.put(CommonConstants.REAL_TAX, realTax);
		priceMap.put(CommonConstants.PERSONAL_TAX, personalTax);
		priceMap.put(CommonConstants.CUSTOMER_DEPOSIT, customerDeposit);
	}

	/**
	 * Creates the file name. 
	 * @return the string
	 */
	private String createFileName() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy_HHmmss");
		String dateStr = sdf1.format(cal.getTime());
		return dateStr + "_gl.dat";
	}
	
	/**
	 * The method creates a Map where the key is shopify order number and value is another Map where key 
	 * is category and value is shopifyDetails list
	 * @param shopifyDetails
	 * @param shopifyOrderMap
	 */
	
	private void createDetailsMap(List<ShopifyDetailsVO> shopifyDetails, Map<String, HashMap<String, List<ShopifyDetailsVO>>> shopifyOrderMap){
		
		for(ShopifyDetailsVO detailVO : shopifyDetails){
			if(null != detailVO){
				String shopifyOrderNo = detailVO.getShopifyOrderNumber();
				String category = detailVO.getCategory();
				
				if(StringUtils.isNotBlank(shopifyOrderNo)){
					if(shopifyOrderMap.containsKey(shopifyOrderNo)){
						HashMap<String, List<ShopifyDetailsVO>> categoryMap = shopifyOrderMap.get(shopifyOrderNo);
						if(categoryMap.containsKey(category)){
							List<ShopifyDetailsVO> detailsList = categoryMap.get(category);
							detailsList.add(detailVO);
							categoryMap.put(category, detailsList);
							shopifyOrderMap.put(shopifyOrderNo, categoryMap);
						}
						else{
							List<ShopifyDetailsVO> details = new ArrayList<ShopifyDetailsVO>();
							details.add(detailVO);
							categoryMap.put(category, details);
							shopifyOrderMap.put(shopifyOrderNo, categoryMap);
						}
					}
					else{
						HashMap<String, List<ShopifyDetailsVO>> categoryMap = new HashMap<String, List<ShopifyDetailsVO>>();
						List<ShopifyDetailsVO> details = new ArrayList<ShopifyDetailsVO>();
						details.add(detailVO);
						categoryMap.put(category, details);
						shopifyOrderMap.put(shopifyOrderNo, categoryMap);
					}
				}
			}
		}
	}
	
	/**
	 * The method is to get the sku details for a particular shopify order no and category
	 * @param details
	 * @param skuDetails
	 */
	private void getSKUDetails(List<ShopifyDetailsVO> details, List<SKUDetails> skuDetails){
		StringBuilder skuPrice = new StringBuilder();
		List<String> skuInfos = new ArrayList<String>();
		for(ShopifyDetailsVO vo : details){
			skuPrice.setLength(0);
			if(null != vo){
				if(StringUtils.isNotBlank(vo.getPrice1())){
					skuPrice.append(vo.getPrice1());
				}
				if(StringUtils.isNotBlank(vo.getPrice2())){
					skuPrice.append(vo.getPrice2());
				}
				if(StringUtils.isNotBlank(vo.getPrice3())){
					skuPrice.append(vo.getPrice3());
				}
				if(StringUtils.isNotBlank(vo.getPrice4())){
					skuPrice.append(vo.getPrice4());
				}
				if(skuPrice.length() != 0){
					StringTokenizer priceString = new StringTokenizer(skuPrice.toString(), CommonConstants.TOKEN);
					while (priceString.hasMoreTokens()) {
						String priceStr = priceString.nextToken().trim();
						StringTokenizer skuString = new StringTokenizer(priceStr, CommonConstants.SEPERATOR);
						skuInfos.clear();
						while (skuString.hasMoreTokens()) {
							skuInfos.add(skuString.nextToken().trim());
						}
						if(!skuInfos.isEmpty()){
							SKUDetails sku = new SKUDetails(skuInfos.get(0), new BigDecimal(skuInfos.get(1)), 
									new BigDecimal(skuInfos.get(2)), skuInfos.get(3));
							skuDetails.add(sku);
						}
					}
				}
			}
			
		}
	}
	
	/**
	 * Method to set price related details in detailVO
	 * @param price
	 * @param rules
	 * @param priceMap
	 */
	private GLDetailVO mapPriceDetails(String price, 
			ShopifyGLRuleVO rules, Map<String, BigDecimal> priceMap) {
		
		GLDetailVO detailVO = null;
		if(CommonConstants.TOTAL_PRICE.equalsIgnoreCase(price) 
				&& rules.getLedgerRule().contains(CommonConstants.RULE_DEF_REV)){
			detailVO = new GLDetailVO(rules.getGlUnit(), rules.getGlDivision(), 
								rules.getGlAccountNo(), rules.getGlCategory(), 
								rules.getLedgerRule(), rules.getMultiplier()*(priceMap.get(CommonConstants.TOTAL_PRICE).doubleValue()),
								rules.getDescr());
		}
		else if(CommonConstants.CUSTOMER_DEPOSIT.equalsIgnoreCase(price) 
				&& (rules.getLedgerRule().contains(CommonConstants.RULE_CUS_DEP) || 
						rules.getLedgerRule().contains(CommonConstants.RULE_MISC_REV) || rules.getLedgerRule().contains(CommonConstants.RULE_INST_REV))){
			detailVO = new GLDetailVO(rules.getGlUnit(), rules.getGlDivision(), 
								rules.getGlAccountNo(), rules.getGlCategory(), 
								rules.getLedgerRule(), rules.getMultiplier()*(priceMap.get(CommonConstants.CUSTOMER_DEPOSIT).doubleValue()),
								rules.getDescr());
		}
		else if(CommonConstants.REAL_TAX.equalsIgnoreCase(price) 
				&& rules.getLedgerRule().contains(CommonConstants.RULE_TAX_REAL)){
			detailVO = new GLDetailVO(rules.getGlUnit(), rules.getGlDivision(), 
								rules.getGlAccountNo(), rules.getGlCategory(), 
								rules.getLedgerRule(), rules.getMultiplier()*(priceMap.get(CommonConstants.REAL_TAX).doubleValue()),
								rules.getDescr());
		}
		else if(CommonConstants.PERSONAL_TAX.equalsIgnoreCase(price) 
				&& rules.getLedgerRule().contains(CommonConstants.RULE_TAX_PERS)){
			detailVO = new GLDetailVO(rules.getGlUnit(), rules.getGlDivision(), 
								rules.getGlAccountNo(), rules.getGlCategory(), 
								rules.getLedgerRule(), rules.getMultiplier()*(priceMap.get(CommonConstants.PERSONAL_TAX).doubleValue()),
								rules.getDescr());
		}
		return detailVO;
	}
	
	/**Method to update the shopify_gl_state in so_price table in order to avoid the multiple picking of data by batch
	 * @throws SLBusinessServiceException
	 */
	private void updateSOGLStatus() throws SLBusinessServiceException{
		
			try {
				shcSupplierDao.updateSOGLStatus(shopifyUpdateList);
				shopifyUpdateList.clear();
				
			}catch (DataServiceException e) {
				throw new SLBusinessServiceException("Error in ShopifyGLProcessor.updateSOGLStatus()", e);
			}catch (Exception e) {
				throw new SLBusinessServiceException("Error in ShopifyGLProcessor.updateSOGLStatus()", e);
			}
	}
	private double calculatePriceByExpression(String pricingExpression, ShopifyDetailsVO relayDetail) throws ScriptException{
		String modifiedExpression=pricingExpression;
		String arrayOfSubParts[]=modifiedExpression.split("[+|(|)|-|*|/]");
		for(String subPart: arrayOfSubParts){
			switch(subPart){
			case CommonConstants.FINAL_LABOUR:{
				modifiedExpression=modifiedExpression.replace(subPart, relayDetail.getFinalLaborPrice());
				break;
			}
			case CommonConstants.FINAL_PARTS:{
				modifiedExpression=modifiedExpression.replace(subPart, relayDetail.getFinalPartsPrice());
				break;
			}
			case CommonConstants.UPSELL_PROVIDER_TOTAL:{
				modifiedExpression=modifiedExpression.replace(subPart, relayDetail.getFinalAddonPrice());
				break;
			}
			}
		}
		if(Double.valueOf(relayDetail.getFinalLaborPrice()) <= 0 && Double.valueOf(relayDetail.getFinalPartsPrice()) <= 0){
			// this will get execute in case of cancel without Penaulty scenario
			return 0.00;
		}
		else{			
		// Calculate Price using updated values in expression
		ScriptEngineManager mgr = new ScriptEngineManager(null);
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    double finalPrice=(double) engine.eval(modifiedExpression);
	    logger.info("Price expression : "+modifiedExpression+"---> Transaction Amount :"+finalPrice);
		return finalPrice;
		}
	}
	/**
	 * Sets the gl dao. 
	 * @param glDao the new gl dao
	 */
	public void setGlDao(IGLDao glDao) {

		this.glDao = glDao;
	}

	/**
	 * Sets the gl transformer.
	 * @param glTransformer the new gl transformer
	 */
	public void setGlTransformer(GLTransformer glTransformer) {

		this.glTransformer = glTransformer;
	}

	/**
	 * Sets the gl writer.
	 * @param glWriter the new gl writer
	 */
	public void setGlWriter(IGLWriter glWriter) {

		this.glWriter = glWriter;
	}

	/**
	 * Sets the shc supplier dao.
	 * @param shcSupplierDao the new shc supplier dao
	 */
	public void setShcSupplierDao(ISHCOMSDao shcSupplierDao) {

		this.shcSupplierDao = shcSupplierDao;
	}
	
	public IGLDao getGlDao() {
		return glDao;
	}

	public GLTransformer getGlTransformer() {
		return glTransformer;
	}

	public IGLWriter getGlWriter() {
		return glWriter;
	}

	public ISHCOMSDao getShcSupplierDao() {
		return shcSupplierDao;
	}
	public Timestamp getGlPostedDate() {
		return glPostedDate;
	}

	public void setGlPostedDate(Timestamp glPostedDate) {
		this.glPostedDate = glPostedDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public List<ShopifyDetailsVO> getShopifyUpdateList() {
		return shopifyUpdateList;
	}

	public void setShopifyUpdateList(List<ShopifyDetailsVO> shopifyUpdateList) {
		this.shopifyUpdateList = shopifyUpdateList;
	}
}
