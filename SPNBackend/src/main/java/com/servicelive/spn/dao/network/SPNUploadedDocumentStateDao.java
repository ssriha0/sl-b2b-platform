package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface SPNUploadedDocumentStateDao extends BaseDao {

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return List
	 */
	public List<SPNUploadedDocumentState> find(Integer spnId, Integer providerFirmId);
	/**
	 * 
	 * @param docState
	 * @return SPNUploadedDocumentState
	 * @throws Exception
	 */
	public SPNUploadedDocumentState update(SPNUploadedDocumentState docState) throws Exception;
}
