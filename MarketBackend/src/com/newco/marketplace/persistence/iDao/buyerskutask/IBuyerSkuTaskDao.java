package com.newco.marketplace.persistence.iDao.buyerskutask;

import java.util.List;

import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskMappingVO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IBuyerSkuTaskDao {
	
	public void insertSkuTaskAssoc(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException;

	public int selectCategoryId(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException;
	
	public int insertBuyerSkuCategory(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException;
	
	public int selectSkuId(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException;
	
	public int insertBuyerSku(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException;
	
	public void insertBuyerSkuTask(BuyerSkuTaskVO buyerSkuTaskVo) throws DataServiceException;
	
	public List<BuyerSkuTaskVO> getSkuTaskListBySku(String sku) throws DataServiceException;
	
	public List<BuyerSkuTaskVO> getSkuTaskListBySpecCodeSkuAndBuyerId(String sku, String specCode, Integer buyerId) throws DataServiceException;
	
	public List<BuyerSkuTaskVO> getSkuTaskListBySkuAndBuyerId(String sku, Integer buyerId) throws DataServiceException;
	
	public void deleteSkuTaskAssoc(BuyerSkuTaskVO buyerSkuTaskVO) throws DataServiceException;
	
	public void deleteSkuTask(BuyerSkuTaskVO buyerSkuTaskVO) throws DataServiceException;
	
	public void saveJobCodeInAssoc(String jobCode, String specCode, Integer buyerId, Integer templateId) throws DataServiceException;
	
	public void saveJobCode(String jobCode, String specCode, Integer buyerId, Integer templateId) throws DataServiceException;
	
	public List<BuyerSkuTaskMappingVO> getTaskDetailsBySkuAndBuyerId(String sku,Integer buyerId) throws DataServiceException;
	
}
