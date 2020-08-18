package com.newco.marketplace.persistence.daoImpl.buyerskutask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskMappingVO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyerskutask.IBuyerSkuTaskDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerSkuTaskDaoImpl extends ABaseImplDao implements IBuyerSkuTaskDao {
	public void insertSkuTaskAssoc(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> insertSkuTaskAssoc() ");
		try {
			Integer rowsInserted = (Integer) insert("buyerSkuTaskAssoc.insert", buyerSkuTaskVo);
			logger.info(rowsInserted + " row(s) inserted into buyer_sku_task_assoc");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> insertSkuTaskAssoc() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> insertSkuTaskAssoc() ");
	}
	public int selectCategoryId(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException {
		Integer buyerCategoryId=0;
		int categoryId=0;
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> selectCategoryId() ");
		try {
			buyerCategoryId = (Integer) getSqlMapClientTemplate().queryForObject("buyerSkuCategory.select", buyerSkuTaskVo);
			if(buyerCategoryId==null){
				categoryId=0;
			}else{
				categoryId=buyerCategoryId;
			}
			logger.info("CategoryId " +categoryId + " is selected from buyer_sku_category");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> selectCategoryId() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> selectCategoryId() ");
		return categoryId;
	}

	public int insertBuyerSkuCategory(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException {
		Integer categoryId=0;
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> insertSkuCategory() ");
		try {
			categoryId = (Integer) insert("buyerSkuCategory.insert", buyerSkuTaskVo);
			logger.info("CategoryId "  +categoryId +  " is inserted into buyer_sku_category");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> insertSkuCategory() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> insertSkuCategory() ");
		return categoryId;
	}
	public int selectSkuId(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException {
		Integer buyerSkuId=0;
		int skuId=0;
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> selectSkuId() ");
		try {
			buyerSkuId = (Integer) getSqlMapClientTemplate().queryForObject("buyerSku.select", buyerSkuTaskVo);
			if(buyerSkuId==null){
				skuId=0;
			}else{
				skuId=buyerSkuId;
			}
			logger.info("SkuId "  +skuId + " is selected from buyer_sku");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> selectSkuId() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> selectSkuId() ");
		return skuId;
	}

	public int insertBuyerSku(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException {
		Integer skuId=0;
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> insertBuyerSku() ");
		try {
			skuId = (Integer) insert("buyerSku.insert", buyerSkuTaskVo);
			logger.info("SkuId " +skuId + "is inserted into buyer_sku");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> insertBuyerSku() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> insertBuyerSku() ");
		return skuId;
	}
	public void insertBuyerSkuTask(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> insertBuyerSkuTask() ");
		try {
			insert("buyerSkuTask.insert", buyerSkuTaskVo);
			logger.info("Insertion in buyer_sku_task");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> insertBuyerSkuTask() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> insertBuyerSkuTask() ");
	}
	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskVO> getSkuTaskListBySku(String sku) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> getSkuTaskListBySku() ");
		try {
			return queryForList("buyerSkuTask.queryby.sku", sku);
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> getSkuTaskListBySku() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		} finally {
			logger.debug("Leaving BuyerSkuTaskDaoImpl --> getSkuTaskListBySku() ");
		}
	}
	
	public void deleteSkuTaskAssoc(BuyerSkuTaskVO buyerSkuTaskVO) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> deleteSkuTaskAssoc() ");
		try {
			int rowsDeleted = delete("buyerSkuTaskAssoc.deleteTask", buyerSkuTaskVO);
			logger.info("Deleted " + rowsDeleted + " row(s) from the buyer_sku_task_assoc table");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> deleteSkuTaskAssoc() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> deleteSkuTaskAssoc() ");

	}

	public void deleteSkuTask(BuyerSkuTaskVO buyerSkuTaskVO) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> deleteSkuTask() ");
		try {
			int rowsDeleted = delete("buyerSkuTask.deleteTask", buyerSkuTaskVO);
			logger.info("Deleted " + rowsDeleted + " row(s) from the buyer_sku_task table");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> deleteSkuTask() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> deleteSkuTask() ");

	}
	
	public void saveJobCodeInAssoc(String jobCode, String specCode, Integer buyerId, Integer templateId) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> saveJobCodeInAssoc() ");
		try {
			Map<String, Object> jobCodeMap = new HashMap<String, Object>();
			jobCodeMap.put("jobCode", jobCode);
			jobCodeMap.put("specCode", specCode);
			jobCodeMap.put("buyerId", buyerId);
			jobCodeMap.put("templateId", templateId);
			int rowsUpdated = update("buyerSkuTaskAssoc.templateId.update", jobCodeMap);
			logger.info("Updated template id for " + rowsUpdated + " row(s) for the buyer_sku_task_assoc table");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> saveJobCodeInAssoc() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> saveJobCodeInAssoc() ");
	}

	public void saveJobCode(String jobCode, String specCode, Integer buyerId, Integer templateId) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> saveJobCode() ");
		try {
			Map<String, Object> jobCodeMap = new HashMap<String, Object>();
			jobCodeMap.put("jobCode", jobCode);
			jobCodeMap.put("specCode", specCode);
			jobCodeMap.put("buyerId", buyerId);
			jobCodeMap.put("templateId", templateId);
			int rowsUpdated = update("buyerSkuTask.templateId.update", jobCodeMap);
			logger.info("Updated template id for " + rowsUpdated + " row(s) for the buyer_sku table");
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> saveJobCode() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		}
		logger.debug("Leaving BuyerSkuTaskDaoImpl --> saveJobCode() ");
	}

	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskVO> getSkuTaskListBySpecCodeSkuAndBuyerId(String sku, String specCode, Integer buyerId) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> getSkuTaskListBySpecCodeSkuAndBuyerId() ");
		try {
			Map<String, Object> request = new HashMap<String, Object>();
			request.put("sku", sku);
			request.put("specCode", specCode);
			request.put("buyerId", buyerId);
			List<BuyerSkuTaskVO> resultList= queryForList("buyerSkuTask.queryby.sku.specCode.buyerId", request);
			return resultList;
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> getSkuTaskListBySpecCodeSkuAndBuyerId() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		} finally {
			logger.debug("Leaving BuyerSkuTaskDaoImpl --> getSkuTaskListBySpecCodeSkuAndBuyerId() ");
		}
	}

	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskVO> getSkuTaskListBySkuAndBuyerId(String sku, Integer buyerId) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> getSkuTaskListBySkuAndBuyerId() ");
		try {
			Map<String, Object> request = new HashMap<String, Object>();
			request.put("sku", sku);
			request.put("buyerId", buyerId);
			return queryForList("buyerSkuTask.queryby.sku.buyerId", request);
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> getSkuTaskListBySkuAndBuyerId() -->" + e.getMessage(), e);
			throw new DataServiceException(e.getMessage(), e);
		} finally {
			logger.debug("Leaving BuyerSkuTaskDaoImpl --> getSkuTaskListBySkuAndBuyerId() ");
		}
	}

	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskMappingVO> getTaskDetailsBySkuAndBuyerId(String sku, Integer buyerId) throws DataServiceException {
		logger.debug("Enterting BuyerSkuTaskDaoImpl --> getTaskDetailsBySkuAndBuyerId() ");
		try {
			Map<String, Object> request = new HashMap<String, Object>();
			request.put("sku", sku);
			request.put("buyerId", buyerId);
			return queryForList("buyerSkuTaskMap.queryby.sku.buyerId", request);
		} catch (Exception e) {
			logger.error("Exception in BuyerSkuTaskDaoImpl --> getTaskDetailsBySkuAndBuyerId() -->" + e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		} finally {
			logger.debug("Leaving BuyerSkuTaskDaoImpl --> getTaskDetailsBySkuAndBuyerId() ");
		}
	}
}