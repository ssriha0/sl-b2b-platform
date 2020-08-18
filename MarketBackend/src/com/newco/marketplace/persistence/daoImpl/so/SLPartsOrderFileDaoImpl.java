package com.newco.marketplace.persistence.daoImpl.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.so.order.SLPartsOrderFileVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.ISLPartsOrderFileDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class SLPartsOrderFileDaoImpl extends ABaseImplDao implements ISLPartsOrderFileDao{
	private static final Logger logger = Logger
	.getLogger(SLPartsOrderFileDaoImpl.class.getName());
	
	public List<SLPartsOrderFileVO> getPartsOrderRecords(Integer buyerId) throws DataServiceException
	{
		List<SLPartsOrderFileVO> slPartsOrderList = new ArrayList<SLPartsOrderFileVO>();
		try {
			slPartsOrderList = (ArrayList<SLPartsOrderFileVO>) queryForList("soPartsOrderFile.query", buyerId);
			update("soPartsOrderFile.update",null);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return slPartsOrderList;
	}
}
