package com.newco.marketplace.business.iBusiness.serviceorder;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOEventVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;


public interface ISOEventBO {

	ProcessResponse insertEvent(String so_id, String resourceID, long reasonCode, long eventType) throws BusinessServiceException;

	void updateSOEvent(InOutVO inOutVO) throws BusinessServiceException;

	List<SOEventVO> selectSOEventVO(InOutVO inOutVO) throws BusinessServiceException;
	
	//void processOnSiteVisits() throws BusinessServiceException;
}
