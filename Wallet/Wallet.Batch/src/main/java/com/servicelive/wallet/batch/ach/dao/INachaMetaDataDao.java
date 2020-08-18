package com.servicelive.wallet.batch.ach.dao;

import java.util.ArrayList;
import java.util.Date;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;

// TODO: Auto-generated Javadoc
/**
 * This class in the database interface to retrieve several template blocks for
 * the Nacha File Construction.
 * 
 * @author Siva
 */

public interface INachaMetaDataDao {

	/**
	 * This retrieves field values for the given criteria.
	 * 
	 * @param fieldVO 
	 * 
	 * @return ArrayList<FieldDetailVO>
	 * 
	 * @throws DataServiceException 
	 */
	public ArrayList<FieldDetailVO> retrieveFieldDetais(FieldDetailVO fieldVO) throws DataServiceException;

	/**
	 * This method retrieves the unique batch Ids for the set of user ledger
	 * transactions. A batch represents the type of SEC code.
	 * 
	 * @return ArrayList<TransactionBatchVO>
	 * 
	 * @throws DataServiceException 
	 */
	public ArrayList<TransactionBatchVO> retrieveUniqueBatchIds(Date runDate) throws DataServiceException;

}
