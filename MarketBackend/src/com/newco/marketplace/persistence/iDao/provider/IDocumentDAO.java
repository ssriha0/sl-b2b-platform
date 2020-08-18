package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.DocumentVO;

public interface IDocumentDAO{
	
	
	public DocumentVO insert(DocumentVO myDocumentVO)throws DBException;
	
	/**
	 * @param Used to retrieve a document by id.
	 * @return
	 */
	public DocumentVO selectByDocId(DocumentVO inDocumentVO) throws DBException;
	
	public DocumentVO selectMetadatByDocId(DocumentVO inDocumentVO)throws DBException;
	
	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO)throws DBException;
}
