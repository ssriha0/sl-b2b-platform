package com.newco.marketplace.persistence.iDao.document;

import java.util.List;
import java.util.ArrayList;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ISimpleDocumentDao {
	
	public ProcessResponse insertTemporarySimpleBuyerServiceOrderDocument(
			DocumentVO documentVO) throws DataServiceException ;
	
	public void deleteTemporarySimpleBuyerDocument(String simpleBuyerId,
			Integer docId)throws BusinessServiceException;
	
	public void deleteTemporarySimpleBuyerDocuments(String simpleBuyerId)throws BusinessServiceException;

	public void persistSimpleBuyerDocuments(String simpleBuyerId, String soId, Integer entityId)throws BusinessServiceException;
	
	public List<DocumentVO> retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(
			String simpleBuyerId, Integer categoryId)throws BusinessServiceException;
	
	public List<DocumentVO> retrieveTemporaryDocumentsMetaDataBySimpleBuyerIdAndCategory(
			String simpleBuyerId, Integer categoryId)throws BusinessServiceException;
	
	public DocumentVO retrieveTemporarySimpleBuyerSODocumentByDocumentId(
			Integer documentId, String simpleBuyerId)throws BusinessServiceException;
	// check for doc format
	public boolean isAllowedFormat(String format) throws DataServiceException;
	// check for image doc format
	public boolean isAllowedImageFormat(String format) throws DataServiceException;
	// check for already existing documents
	public DocumentVO isAlreadyLoaded(DocumentVO documentVO)	throws DataServiceException; 
	public boolean isTotalImageSizeAllowed(Long totalSize)throws DataServiceException;
	public List<String> getValidDisplayExtension() throws DataServiceException ;	
	
	
	public ArrayList<DocumentVO> getAttachements(ArrayList<Integer> docIds)throws DataServiceException;	
}
