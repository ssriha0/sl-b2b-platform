package com.newco.marketplace.web.delegates;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.BuyerQueueVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.dto.WFMCategoryDTO;

//SLT-1613 START
public interface IWorkflowQueueDelegate {

	// SLT-1894 START
	public Map<String, List<WFMCategoryDTO>> getWfmQueueDetails(Integer buyerId) throws BusinessServiceException;
	// SLT-1894 END
	public void saveWfmBuyerQueues(List<BuyerQueueVO> wfmBuyerQueues, List<Integer> queueIdList,Integer buyerId)
			throws BusinessServiceException;

}
// SLT-1613 END
