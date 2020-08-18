package com.newco.marketplace.persistence.iDao.so;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOEventVO;
import com.newco.marketplace.exception.core.DataServiceException;


public interface ISOEventDao {

	void insert(SOEventVO vo);
	
	SOEventVO selectByID(SOEventVO vo);
	
	List<SOEventVO> selectSOEventVO(InOutVO inOutVO) throws DataServiceException;

	int updateSOEvent(InOutVO inOutVO) throws DataServiceException;

}
