package com.newco.marketplace.business.businessImpl.jobcode;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.jobcode.IJobCodeMappingBO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskMappingVO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.dto.vo.jobcode.JobCodeMappingVO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.persistence.iDao.buyerskutask.IBuyerSkuTaskDao;
import com.newco.marketplace.persistence.iDao.so.IBuyerSOTemplateDAO;
import com.sears.hs.order.domain.StockNoClassify;
import com.sears.hs.order.domain.StockNoClassifyLookup;
import com.sears.hs.order.domain.request.StockNoClassifyRequest;
import com.sears.hs.order.domain.response.StockNoClassifyResponse;
import com.sears.hs.order.service.OrderService;
import com.servicelive.routingrulesengine.services.RoutingRulesService;

/**
 * 
 * 
 */
public class JobCodeMappingBOImpl implements IJobCodeMappingBO {

	private static final Logger logger = Logger.getLogger(JobCodeMappingBOImpl.class);

	private IBuyerSkuTaskDao buyerSkuTaskDao;
	private IBuyerSOTemplateDAO buyerSOTemplateDao;
	private OrderService orderService;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private RoutingRulesService routingRulesService;

	public void addSkuTask(BuyerSkuTaskVO skuTaskVO) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> addSkuTask()");
		int categoryId=0;
		int skuId=0;
		try {
			//select existing categoryId for corresponding specCode and buyerId
			if(skuTaskVO.getSpecCode()!=null && skuTaskVO.getBuyerId()!=null){
			categoryId=getBuyerSkuTaskDao().selectCategoryId(skuTaskVO);
			//insert into buyerSkuCategory table,if the categoryId is not exist for specCode and buyerId
				if(categoryId==0){
					categoryId=getBuyerSkuTaskDao().insertBuyerSkuCategory(skuTaskVO);
				}
				//select existing skuId for corresponding sku,skuCategoryId and buyerId
					if(categoryId!=0 && skuTaskVO.getSku()!=null){
						skuTaskVO.setSkuCategoryId(categoryId);
						skuId=getBuyerSkuTaskDao().selectSkuId(skuTaskVO);
						//insert into buyersku table,if the skuId is not exist for sku,skuCategoryId and buyerId
						if(skuId==0){
							skuId=getBuyerSkuTaskDao().insertBuyerSku(skuTaskVO);
							}
							if(skuId!=0 && skuTaskVO.getNodeId()!=null && skuTaskVO.getSkillId()!=null){
								skuTaskVO.setSkuId(skuId);
								//insert into skuTaskAssoc table
								getBuyerSkuTaskDao().insertSkuTaskAssoc(skuTaskVO);
								//insert into skuTask table
								getBuyerSkuTaskDao().insertBuyerSkuTask(skuTaskVO);
							}
					}
			}
			/*
			 * Commented s part of SL-15175
			 */
			//routingRulesService.addSkuTask(skuTaskVO.getBuyerId(), skuTaskVO.getSku(), skuTaskVO.getSpecCode());
		} catch (DataServiceException dse) {
			logger.error("Exception in JobCodeMappingBOImpl --> addSkuTask() due to " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in JobCodeMappingBOImpl --> addSkuTask() " + " due to " + dse.getMessage(), dse);
		}
		logger.debug("Leaving JobCodeMappingBOImpl --> addSkuTask()");
	}
	
	public void deleteSkuTask(BuyerSkuTaskVO skuTaskVO) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> deleteSkuTask()");
		try {
			//delete from SkuTaskAssoc table
			getBuyerSkuTaskDao().deleteSkuTaskAssoc(skuTaskVO);
			//delete from SkuTask table
			getBuyerSkuTaskDao().deleteSkuTask(skuTaskVO);
			/*
			 * Commented s part of SL-15175
			 */
			//routingRulesService.removeSkuTask(skuTaskVO.getBuyerId(), skuTaskVO.getSku(), skuTaskVO.getSpecCode());
		} catch (DataServiceException dse) {
			logger.error("Exception in JobCodeMappingBOImpl --> deleteSkuTask() due to " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in JobCodeMappingBOImpl --> deleteSkuTask() " + " due to " + dse.getMessage(), dse);
		}
		logger.debug("Leaving JobCodeMappingBOImpl --> deleteSkuTask()");
	}

	public List<BuyerSkuTaskVO> getSkuTaskList(String sku, String specCode, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> getSkuTaskList()");
		try {
			if (StringUtils.isNotBlank(sku)) {
				return getBuyerSkuTaskDao().getSkuTaskListBySpecCodeSkuAndBuyerId(sku, specCode, buyerId);
			}
		} catch (DataServiceException dse) {
			logger.error("Exception in JobCodeMappingBOImpl --> getSkuTaskList() due to " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in JobCodeMappingBOImpl --> getSkuTaskList()" + " due to " + dse.getMessage(), dse);
		}
		logger.debug("Leaving JobCodeMappingBOImpl --> getSkuTaskList()");
		return null;
	}

	public JobCodeMappingVO getJobCodeDetails(String sku, String specCode, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> getJobCodeDetails()");
		final JobCodeMappingVO jobCodeMappingVO;
		// If buyer wants to call SST webservice to get the details about the
		// Sku
		if (buyerId != null && buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.SKU_DETAILS_FROM_SST).booleanValue()) {
			jobCodeMappingVO = getSkuDetailsFromSST(sku, specCode);
		} else {
			jobCodeMappingVO = new JobCodeMappingVO();
		}

		List<BuyerSkuTaskVO> skuTaskList = getSkuTaskList(sku, specCode, buyerId);
		if (skuTaskList != null && skuTaskList.size() > 0) {
			jobCodeMappingVO.setTemplateId(skuTaskList.get(0).getTemplateId());
		}
		jobCodeMappingVO.setTemplateList(getSoTemplates(buyerId));
		jobCodeMappingVO.setSkuTaskList(skuTaskList);
		jobCodeMappingVO.setJobCode(sku);
		jobCodeMappingVO.setSpecCode(specCode);

		logger.debug("Leaving JobCodeMappingBOImpl --> getJobCodeDetails()");
		return jobCodeMappingVO;
	}

	private JobCodeMappingVO getSkuDetailsFromSST(String sku, String specCode) throws BusinessServiceException {
		final StockNoClassifyResponse stockNoClassifyResponse;
		try {
			StockNoClassifyRequest stockNoClassifyRequest = new StockNoClassifyRequest();
			stockNoClassifyRequest.setClient("client");
			stockNoClassifyRequest.setPassword("password");
			stockNoClassifyRequest.setStockNos(new StockNoClassifyLookup[] { new StockNoClassifyLookup(sku, specCode) });
			stockNoClassifyRequest.setUser("user");
			stockNoClassifyRequest.setVersion("1.0");
			stockNoClassifyResponse = orderService.lookup(stockNoClassifyRequest);
		} catch (RemoteException e) {
			logger.error("Error connecting to the SST Webservice due to " + e.getMessage(), e);
			throw new BusinessServiceException("Error connecting to the SST Webservice due to " + e.getMessage(), e);
		}

		JobCodeMappingVO jobCodeMappingVO = new JobCodeMappingVO();
		if (stockNoClassifyResponse == null) {
			// is this even possible?
			return jobCodeMappingVO;
		}

		StockNoClassify[] stockNoClassify = stockNoClassifyResponse.getStockNos();
		if (stockNoClassify == null || stockNoClassify.length != 1) {
			logger.error("No response received from SST webservice for the given JobCode value. Please check the JobCode values.");
			throw new BusinessServiceException("No response received from SST webservice for the given JobCode value. Please check the JobCode values.");
		}

		StockNoClassify singleStockNoClassify = stockNoClassify[0];
		jobCodeMappingVO.setInclusionDescr(singleStockNoClassify.getInclDs());
		jobCodeMappingVO.setMajorHeadingDescr(singleStockNoClassify.getMajorHdDs());
		jobCodeMappingVO.setSubHeadingDescr(singleStockNoClassify.getSubHdDs());
		jobCodeMappingVO.setTaskComment(singleStockNoClassify.getInclDs());
		jobCodeMappingVO.setTaskName(sku + "-" + singleStockNoClassify.getRegisterDs());
		logger.info("****** JobCodeMarginService - SUCCESS ! *******");
		return jobCodeMappingVO;
	}

	public Map<Integer, String> getSoTemplates(Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> getSoTemplates()");

		try {
			List<BuyerSOTemplateVO> templateList = getBuyerSOTemplateDao().loadBuyerSoTemplates(buyerId);
			if (templateList == null) {
				// should there be a difference between an null templateList and
				// empty templateList?
				return null;
			}
			Map<Integer, String> templateMap = new LinkedHashMap<Integer, String>();
			for (BuyerSOTemplateVO template : templateList) {
				templateMap.put(template.getTemplateID(), template.getTemplateName());
			}
			logger.debug("Leaving JobCodeMappingBOImpl --> getSoTemplates()");
			return templateMap;
		} catch (DataServiceException dse) {
			logger.error("Exception in getting the buyer so templates due to " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting the buyer so templates due to " + dse.getMessage(), dse);
		}
	}

	public void saveJobCode(JobCodeMappingVO jobCodeMappingVO) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> saveJobCode()");
		try {
			if (jobCodeMappingVO == null) {
				logger.info("Nothing to save in JobCodeMappingBOImpl.saveJobCode");
				return;
			}

			List<BuyerSkuTaskVO> list = jobCodeMappingVO.getSkuTaskList();

			// One recommendation would be to pass in the buyerId
			Integer buyerId = null;
			if (list != null && !list.isEmpty()) {
				buyerId = list.get(0).getBuyerId();
			}
			//saveJobCode in buyerSku table
			getBuyerSkuTaskDao().saveJobCode(jobCodeMappingVO.getJobCode(), jobCodeMappingVO.getSpecCode(), buyerId, jobCodeMappingVO.getTemplateId());
			//saveJobCode in buyerskuTaskAssoc table
			getBuyerSkuTaskDao().saveJobCodeInAssoc(jobCodeMappingVO.getJobCode(), jobCodeMappingVO.getSpecCode(), buyerId, jobCodeMappingVO.getTemplateId());
		} catch (DataServiceException dse) {
			logger.error("Exception in saving the buyer so templates due to " + dse.getMessage());
			throw new BusinessServiceException("Exception in saving the buyer so templates due to " + dse.getMessage(), dse);
		}
		logger.debug("Leaving JobCodeMappingBOImpl --> saveJobCode()");
	}

	public List<BuyerSkuTaskMappingVO> getTaskDetailsBySkuAndBuyerId(String sku, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeMappingBOImpl --> getTaskDetailsBySkuAndBuyerId()");
		try {
			if (StringUtils.isBlank(sku)) {
				return null;
			}
			return getBuyerSkuTaskDao().getTaskDetailsBySkuAndBuyerId(sku, buyerId);
		} catch (DataServiceException dse) {
			logger.error("Exception in JobCodeMappingBOImpl --> getTaskDetailsBySkuAndBuyerId()" + " due to " + dse.getMessage());
			throw new BusinessServiceException("Exception in JobCodeMappingBOImpl --> getTaskDetailsBySkuAndBuyerId()" + " due to " + dse.getMessage(), dse);
		} finally {
			logger.debug("Leaving JobCodeMappingBOImpl --> getSkuTaskList()");
		}
	}

	/**
	 * @return the buyerSkuTaskDao
	 */
	public IBuyerSkuTaskDao getBuyerSkuTaskDao() {
		return buyerSkuTaskDao;
	}

	/**
	 * @param buyerSkuTaskDao
	 *            the buyerSkuTaskDao to set
	 */
	public void setBuyerSkuTaskDao(IBuyerSkuTaskDao buyerSkuTaskDao) {
		this.buyerSkuTaskDao = buyerSkuTaskDao;
	}

	/**
	 * @return the orderService
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * @param orderService
	 *            the orderService to set
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * @return the buyerSOTemplateDao
	 */
	public IBuyerSOTemplateDAO getBuyerSOTemplateDao() {
		return buyerSOTemplateDao;
	}

	/**
	 * @param buyerSOTemplateDao
	 *            the buyerSOTemplateDao to set
	 */
	public void setBuyerSOTemplateDao(IBuyerSOTemplateDAO buyerSOTemplateDao) {
		this.buyerSOTemplateDao = buyerSOTemplateDao;
	}

	/**
	 * 
	 * @param buyerFeatureSetBO
	 */
	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	/**
	 * @param routingRuleService the routingRuleService to set
	 */
	public void setRoutingRulesService(RoutingRulesService routingRulesService) {
		this.routingRulesService = routingRulesService;
	}

}
