package com.servicelive.wallet.batch.ofac.dao;

import java.util.ArrayList;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.ofac.vo.OFACProcessQueueVO;

// TODO: Auto-generated Javadoc
/**
 * The Class OFACDao.
 */
public class OFACDao extends ABaseDao implements IOFACDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ofac.dao.IOFACDao#getOfacData()
	 */
	public ArrayList<OFACProcessQueueVO> getOfacData() throws DataServiceException {

		ArrayList<OFACProcessQueueVO> ofacprocessqueuevo = null;

		try {
			ofacprocessqueuevo = (ArrayList<OFACProcessQueueVO>) getSqlMapClient().queryForList("OFAC.select", ofacprocessqueuevo);
		} catch (Exception e) {
			throw new DataServiceException("Exception occured in OFACDaoImpl.getOfacData", e);
		}

		return ofacprocessqueuevo;

	}

}
