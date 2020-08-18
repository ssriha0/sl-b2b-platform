package com.newco.marketplace.persistence.iDao.ach;

import java.util.Date;
import java.util.ArrayList;

import com.newco.marketplace.dto.vo.ach.FieldDetailVO;
import com.newco.marketplace.dto.vo.ach.TransactionBatchVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * This class in the database interface to retrieve several template blocks for
 * the Nacha File Construction
 * 
 * @author Siva
 * 
 */

public interface NachaMetaDataDao {

	/**
	 * This method retrieves the unique batch Ids for the set of user ledger
	 * transactions. A batch represents the type of SEC code.
	 * 
	 * @return ArrayList<TransactionBatchVO>
	 */
	public ArrayList<TransactionBatchVO> retrieveUniqueBatchIds(Date runDate)
			throws DataServiceException;

	/**
	 * This method retrieves the batch header template from the database
	 * 
	 * @return ArrayList<FieldDetailVO>
	 */
	

	public ArrayList<FieldDetailVO> retrieveFieldDetais(FieldDetailVO fieldVO)throws DataServiceException ;


}
