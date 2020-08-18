package com.newco.marketplace.web.delegatesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMQueueVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.IWorkflowQueueDelegate;
import com.newco.marketplace.web.dto.WFMCategoryDTO;
import com.newco.marketplace.web.utils.WFMBuyerQueueMapper;

//SLT-1613 START
public class WorkflowQueueDelegateImpl implements IWorkflowQueueDelegate {

	private IPowerBuyerBO powerBuyerBO;
	private WFMBuyerQueueMapper wfmBuyerQueueMapper = new WFMBuyerQueueMapper();
	Logger logger = Logger.getLogger(WorkflowQueueDelegateImpl.class);

	//SLT-1894 START
	@Override
	public Map<String, List<WFMCategoryDTO>> getWfmQueueDetails(Integer buyerId) throws BusinessServiceException {
		logger.debug("Inside getWfmQueueDetails method");
		Map<String, List<WFMQueueVO>> queueMap = powerBuyerBO.getWfmQueueDetails(buyerId);

		List<WFMCategoryDTO> standardQueues = wfmBuyerQueueMapper
				.convertVOListtoDTOList(queueMap.get(Constants.STANDARD_QUEUES));
		List<WFMCategoryDTO> buyerQueues = wfmBuyerQueueMapper
				.convertVOListtoDTOList(queueMap.get(Constants.BUYER_QUEUES));

		Map<String, List<WFMCategoryDTO>> queueDtoMap = new HashMap<String, List<WFMCategoryDTO>>();
		queueDtoMap.put(Constants.STANDARD_QUEUES, standardQueues);
		queueDtoMap.put(Constants.BUYER_QUEUES, buyerQueues);
		return queueDtoMap;
	}
	//SLT-1894 END
	
	@Override
	public void saveWfmBuyerQueues(List<BuyerQueueVO> wfmBuyerQueues, List<Integer> queueIdList,Integer buyerId)
			throws BusinessServiceException {
		logger.debug("Inside saveWfmBuyerQueues method");
		powerBuyerBO.saveWfmBuyerQueues(wfmBuyerQueues, queueIdList,buyerId);
	}
	
	public IPowerBuyerBO getPowerBuyerBO() {
		return powerBuyerBO;
	}

	public void setPowerBuyerBO(IPowerBuyerBO powerBuyerBO) {
		this.powerBuyerBO = powerBuyerBO;
	}

	public WFMBuyerQueueMapper getWfmBuyerQueueMapper() {
		return wfmBuyerQueueMapper;
	}

	public void setWfmBuyerQueueMapper(WFMBuyerQueueMapper wfmBuyerQueueMapper) {
		this.wfmBuyerQueueMapper = wfmBuyerQueueMapper;
	}

}
// SLT-1613 END
