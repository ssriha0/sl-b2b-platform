package com.newco.marketplace.persistence.daoImpl.document;

import java.util.Map;

import com.newco.marketplace.persistence.daoImpl.document.IDocumentDao;

/**
 * DocumentDaoFactory is responsible for obtaining a concrete implementation of IDocumentDao classes
 *
 */

public class DocumentDaoFactory {

	private Map<Integer, ? extends IDocumentDao> documentDaos;
	
	public IDocumentDao getDocumentDao(int documentType) {
		IDocumentDao result = null;		
		if(documentDaos != null) {
			result = documentDaos.get(new Integer(documentType));
		}
		return result;
	}
	

	public void setDocumentDaos(Map<Integer, ? extends IDocumentDao> documentDaos) {
		this.documentDaos = documentDaos;
	}


	public Map<Integer, ? extends IDocumentDao> getDocumentDaos() {
		return documentDaos;
	}
	
}