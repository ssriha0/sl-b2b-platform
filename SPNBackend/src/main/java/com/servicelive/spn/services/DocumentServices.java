/**
 * 
 */
package com.servicelive.spn.services;

import static com.servicelive.spn.common.SPNBackendConstants.DOC_CATEGORY_ID_FOR_SPN;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.common.Document;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.common.DocumentDao;
import com.servicelive.spn.dao.network.SPNDocumentDao;

/**
 * @author hoza
 *
 */
public class DocumentServices extends BaseServices {
	
	private SPNDocumentDao spnDocumentDao;
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		Document doc = (Document) entity;
		if(doc.getDocumentId() == null) {
			doc.setCreatedDate(CalendarUtil.getNow());
		}
		doc.setModifiedDate(CalendarUtil.getNow());
		
	}

	private DocumentDao spnetDocumentDao;
	
	
	/**
	 * This method save only Document in document table. The reason being like that is whenever we go to alfreco.. we can implement this one in different way
	 * @param entity
	 * @throws Exception
	 */
	@Transactional ( propagation = Propagation.REQUIRES_NEW)
	public void saveDocument(Document entity) throws Exception {
		//for spn always save doc with SPN type
		if(entity.getDocCategoryId() == null || (entity.getDocCategoryId() != null  && !entity.getDocCategoryId().equals(DOC_CATEGORY_ID_FOR_SPN))){
			entity.setDocCategoryId(DOC_CATEGORY_ID_FOR_SPN);
		}
		handleDates(entity);
		spnetDocumentDao.save(entity);
	}
	
	/**
	 * 
	 * @param documentId
	 * @return boolean
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public boolean deleteDocument(Integer documentId)  {
		boolean result = true;
		try {
			Document doc = spnetDocumentDao.findById(documentId);
			if(doc != null) {
				spnetDocumentDao.delete(doc);
			}
		} catch (Exception e) {
			logger.debug("exception during the document delete via the Ajax call",e);
		}
		return result;
	}
	
	/**
	 * 
	 * @param spnDocumentId
	 * @return boolean
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public boolean deleteSPNDocument(Integer spnDocumentId) {
		boolean result = true;
		try {
			SPNDocument spndoc = spnDocumentDao.findById(spnDocumentId);
			if(spndoc != null) {
				/*Integer docId = */spndoc.getDocument().getDocumentId();
				spnDocumentDao.delete(spndoc);
				//this.deleteDocument(docId);
				//TODO: need to delete from the documents table
			}
		} catch (Exception e) {
			logger.debug("Exception whiledelteing document.. may be supplied spnID is not available",e);
		}
		return result;
	}
	
	public Document findDocumentById(Integer documentId)
	{
		if(documentId == null)
			return null;
		
		Document document = null;
		try
		{
			document = spnetDocumentDao.findById(documentId);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return document;
	}
	public Document findDocumentById_spn(Integer documentId)
	{
		if(documentId == null)
			return null;
		
		Document document = null;
		try
		{
			document = spnetDocumentDao.findById_spn(documentId);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return document;
	}
	

	/**
	 * @param spnDocumentDao the spnDocumentDao to set
	 */
	public void setSpnDocumentDao(SPNDocumentDao spnDocumentDao) {
		this.spnDocumentDao = spnDocumentDao;
	}
	
	public DocumentDao getSpnetDocumentDao() {
		return spnetDocumentDao;
	}

	public void setSpnetDocumentDao(DocumentDao spnetDocumentDao) {
		this.spnetDocumentDao = spnetDocumentDao;
	}

}
