package com.servicelive.wallet.batch.ofac.dao;

import java.util.ArrayList;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.ofac.vo.OFACProcessQueueVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface IOFACDao.
 */
public interface IOFACDao {

	/**
	 * Gets the ofac data.
	 * 
	 * @return the ofac data
	 * 
	 * @throws DataServiceException 
	 */
	public ArrayList<OFACProcessQueueVO> getOfacData() throws DataServiceException;

}
