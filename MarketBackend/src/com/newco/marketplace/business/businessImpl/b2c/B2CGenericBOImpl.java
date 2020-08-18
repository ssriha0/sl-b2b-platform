package com.newco.marketplace.business.businessImpl.b2c;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.b2c.IB2CGenericBO;
import com.newco.marketplace.persistence.iDao.b2c.IB2CGenericDao;

public class B2CGenericBOImpl implements IB2CGenericBO {

	private static final Logger logger = Logger.getLogger(B2CGenericBOImpl.class);
	
	private IB2CGenericDao b2CGenericDao;

	public IB2CGenericDao getB2CGenericDao() {
		return b2CGenericDao;
	}

	public void setB2CGenericDao(IB2CGenericDao b2cGenericDao) {
		b2CGenericDao = b2cGenericDao;
	}


}