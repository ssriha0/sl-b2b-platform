package com.servicelive.spn.services.auditor;

import java.util.List;

import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.spn.dao.network.SPNUploadedDocumentStateDao;
import com.servicelive.spn.services.interfaces.IUploadedDocumentStateService;

/**
 * 
 * @author svanloon
 *
 */
public class UploadedDocumentStateService implements IUploadedDocumentStateService {

	private SPNUploadedDocumentStateDao spnUploadedDocumentStateDao;

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.auditor.IUploadedDocumentStateService#findBy(java.lang.Integer, java.lang.Integer)
	 */
	public List<SPNUploadedDocumentState> findBy(Integer spnId, Integer providerFirmId) {
		return spnUploadedDocumentStateDao.find(spnId, providerFirmId);
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.auditor.IUploadedDocumentStateService#update(com.servicelive.domain.spn.network.SPNUploadedDocumentState)
	 */
	public void update(SPNUploadedDocumentState docState)
	{
		try
		{
			spnUploadedDocumentStateDao.update(docState);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param spnUploadedDocumentStateDao
	 */
	public void setSpnUploadedDocumentStateDao(SPNUploadedDocumentStateDao spnUploadedDocumentStateDao) {
		this.spnUploadedDocumentStateDao = spnUploadedDocumentStateDao;
	}
}
