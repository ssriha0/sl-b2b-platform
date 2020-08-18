package com.newco.marketplace.persistence.daoImpl.buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerSubstatusAssocDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerSubstatusAssocDAOImpl extends ABaseImplDao implements IBuyerSubstatusAssocDAO{
	
	private static final Logger logger = Logger.getLogger(BuyerSubstatusAssocDAOImpl.class);

	public List<BuyerSubstatusAssocVO> getBuyerStatus(Integer buyerID, Integer statusId, Integer substatus, String buyerSubstatus) throws DataServiceException {
		String buyerStatus = null;
		List<BuyerSubstatusAssocVO> buyerSubstatusAssocVOList = new ArrayList<BuyerSubstatusAssocVO>();
		try{
			Map<String, String> params = new HashMap<String, String>();
			params.put("buyerId", String.valueOf(buyerID.intValue()));
			params.put("statusId", Integer.toString(statusId.intValue()));
			if (substatus != null) {
				params.put("substatusId", Integer.toString(substatus.intValue()));
			}
			if (buyerSubstatus != null) {
				params.put("buyerSubstatus", buyerSubstatus);
			}
			
			buyerSubstatusAssocVOList = (List<BuyerSubstatusAssocVO>) getSqlMapClient(). queryForList("buyerSubstatusAssoc.getBuyerStatus", params);
    	}catch (SQLException ex) {
		     logger.info("SQL Exception @BuyerSubstatusAssocDAOImpl.getBuyerStatus() due to" + ex.getMessage());
		     throw new DataServiceException("SQL Exception @BuyerSubstatusAssocDAOImpl.getBuyerStatus() due to " + ex.getMessage());
       }catch (Exception ex) {
		     logger.info("General Exception @BuyerSubstatusAssocDAOImpl.getBuyerStatus() due to" + ex.getMessage());
		     throw new DataServiceException("General Exception @BuyerSubstatusAssocDAOImpl.getBuyerStatus() due to " + ex.getMessage());
       }
       return buyerSubstatusAssocVOList;
	}

	public BuyerSubstatusAssocVO getBuyerSubstatusAssoc(
			Integer buyerSubStatusAssocId) throws DataServiceException {
		BuyerSubstatusAssocVO buyerSubstatusAssocVO = new BuyerSubstatusAssocVO();
		try{
			buyerSubstatusAssocVO = (BuyerSubstatusAssocVO)queryForObject("buyerSubstatusAssoc.getObject", buyerSubStatusAssocId);
		}catch(Exception ex){
			logger.info("SQL Exception @BuyerSubstatusAssocDAOImpl.getBuyerSubstatusAssoc() due to" + ex.getMessage());
		     throw new DataServiceException("SQL Exception @BuyerSubstatusAssocDAOImpl.getBuyerSubstatusAssoc() due to " + ex.getMessage());
		}
		
		return buyerSubstatusAssocVO;
	}

}
