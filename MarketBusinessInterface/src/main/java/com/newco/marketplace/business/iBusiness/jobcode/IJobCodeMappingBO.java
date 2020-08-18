package com.newco.marketplace.business.iBusiness.jobcode;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskMappingVO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.dto.vo.jobcode.JobCodeMappingVO;
import com.newco.marketplace.exception.BusinessServiceException;

public interface IJobCodeMappingBO {
	
	public void addSkuTask(BuyerSkuTaskVO skuTaskVO) throws BusinessServiceException;
	
	public void deleteSkuTask(BuyerSkuTaskVO skuTaskVO) throws BusinessServiceException;
	
	public List<BuyerSkuTaskVO> getSkuTaskList(String sku, String specCode, Integer buyerId) throws BusinessServiceException;
	
	public JobCodeMappingVO getJobCodeDetails(String sku, String specCode, Integer buyerId) throws BusinessServiceException;
	
	public Map<Integer, String> getSoTemplates(Integer buyerId) throws BusinessServiceException;
	
	public void saveJobCode(JobCodeMappingVO jobCodeMappingVO) throws BusinessServiceException;
	
	public List<BuyerSkuTaskMappingVO> getTaskDetailsBySkuAndBuyerId(String sku,Integer buyerId) throws BusinessServiceException;
	

}
