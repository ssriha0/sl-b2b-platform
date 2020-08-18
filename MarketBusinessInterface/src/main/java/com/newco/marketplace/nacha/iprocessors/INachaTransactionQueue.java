package com.newco.marketplace.nacha.iprocessors;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.ach.NachaProcessQueueVO;
import com.newco.marketplace.exception.BusinessServiceException;

public interface INachaTransactionQueue {

	 public List<NachaProcessQueueVO> getNachaRecords(HashMap<String,String> userTransMap) throws BusinessServiceException;
}
