package com.newco.marketplace.web.delegates;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.web.dto.JobCodeMappingDTO;
import com.newco.marketplace.web.dto.SkuTaskDTO;

public interface IJobCodeDelegate {
	
	public void addSkuTask(SkuTaskDTO skuTaskDTO, String jobCode, String specCode, Integer buyerId) throws BusinessServiceException;

	public List<SkuTaskDTO> getSkuTasks(String jobCode, String specCode, Integer buyerId) throws BusinessServiceException;
	
	public void deleteSkuTask(SkuTaskDTO skuTaskDTO, String jobCode, String specCode, Integer buyerId) throws BusinessServiceException;
	
	public JobCodeMappingDTO getJobCodeDetails(String jobCode, String specCode, Integer buyerId) throws BusinessServiceException;
	
	public Map<Integer, String> getSoTemplates(Integer buyerId) throws BusinessServiceException;
	
	public void saveJobCode(JobCodeMappingDTO jobCodeMappingDTO, Integer buyerId) throws BusinessServiceException;
}
