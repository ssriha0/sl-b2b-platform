package com.newco.marketplace.persistence.daoImpl.so.buyerPII;


import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyer.BuyerPIIVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.daoImpl.so.buyer.BuyerDaoImpl;
import com.newco.marketplace.persistence.iDao.so.buyerPII.IBuyerPIIDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerPIIDaoImpl extends ABaseImplDao implements IBuyerPIIDao{
	
	private static Logger logger = Logger.getLogger(BuyerPIIDaoImpl.class.getName());
	
	public void saveBuyerPII(BuyerPIIVO buyerPII) throws DataServiceException{
		try {
			getSqlMapClient().insert("buyerPII.insert", buyerPII);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @BuyerPIIDaoImpl.buyerPII.insert due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @BuyerPIIDaoImpl.buyerPII.insert due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @BuyerPIIDaoImpl.buyerPII.insert due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @BuyerPIIDaoImpl.buyerPII.insert due to "
							+ ex.getMessage());
		}
	}
}
