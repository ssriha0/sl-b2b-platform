package com.newco.marketplace.persistence.daoImpl.document;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;


import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentDeleteException;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.dao.impl.ABaseImplDao;
import com.sears.os.service.ServiceConstants;

public class ResourceDocumentDaoImpl extends ABaseImplDao implements IDocumentDao{
	
	private static final Logger logger = Logger.getLogger(ResourceDocumentDaoImpl.class.getName());
	
	private DocumentService documentService;
		
	public List<DocumentVO> getDocumentsByEntityAndCategory(Object ownerId, Integer categoryId) throws DataServiceException {
		return null;
	}
	
	public List<DocumentVO> retrieveDocumentsByEntityIdAndDocumentId(Integer entity_id, Integer document_id)throws DataServiceException{
		return null;
	}
	
	//SL-21233: Document Retrieval Code Starts
	
	public DocumentVO retrieveDocumentsInfoByDocumentId(DocumentVO inDocumentVO) throws DataServiceException {
		return null;
	}
	
	//SL-21233: Document Retrieval Code Ends
	
	public DocumentVO retrieveDocumentByDocumentIdAndSOId(Integer documentId, String soId) throws DataServiceException {
		return null;
	}
	public void removeDocAll(String soId) throws DataServiceException{
		
	}
	
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId) throws DataServiceException {
		DocumentVO documentVO = null;
		try {
			documentVO = (DocumentVO) queryForObject("document.query_buyerdocument_by_document_id", documentId);
			documentVO = documentService.retrieveDocumentFromDBOrFS(documentVO);
        }
        catch (Exception ex) {
        	logger.info("document_id " + documentId, ex);
            final String error = "dataaccess.select";
            throw new DataServiceException(error, ex);
        }                
            
        return documentVO;
	}

	
	public List<DocumentVO> retrieveDocumentsByType(int type, Integer entity_id) throws DataServiceException {
		return null;
	}
	
	public ProcessResponse uploadDocument(DocumentVO documentVO) throws DataServiceException {
		ProcessResponse pr = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		try {
			//pr.setObj(getSqlMapClient().insert("document_full.insert", documentVO));
			documentVO = documentService.createDocument(documentVO);
			if(null != documentVO.getDocumentId() && documentVO.getDocumentId() > 0){
				pr.setObj(documentVO.getDocumentId());
			}
		} catch(Exception exception) {
			logger.error("Exception : " , exception);
			throw new DataServiceException("General Exception @ResourceDocumentDaoImpl.uploadDocument()", exception);
		}
		return pr;
	}
	
	public DocumentVO isAlreadyLoadedReturnLoaded (DocumentVO documentVO) throws DataServiceException {
		return null;
	}
	
	
	public void removeDocument(Integer documentId) throws DataServiceException {
		DocumentVO documentVO = null;
		try {	
			documentVO = (DocumentVO) queryForObject("document.query_by_document_id", documentId);
			documentService.deleteDocument(documentVO);
		} catch(Exception exception) {
			logger.error("Document ID: " + documentId, exception);
			throw new DataServiceException("General Exception @ResourceDocumentDaoImpl.removeDocument()", exception);
		}
	}
	
	
	public void removeDocuments(List<Integer> doucmentIds) throws DataServiceException {
		DocumentVO documentVO = null;
		try {
			for (Integer docId : doucmentIds) {
				// get the doc size
				documentVO = (DocumentVO) queryForObject("document.query_by_document_id", docId);
				//determinePersistanceLocation(documentVO, AppPropConstants.PERSIST_SO_DOC_IN_DB);
				
				// flag the document as deleted
				documentService.deleteDocument(documentVO);
			}
		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document",e);
		}
	}
	
	public List<DocumentVO> retrieveDocumentsByDocumentIds(List<Integer> doc_ids) throws DataServiceException {
		return null;
	}
	
	public DocumentVO retrieveDocumentByDocumentId(Integer documentId) throws DataServiceException {
		DocumentVO inDocumentVO = new DocumentVO();
		try {
			inDocumentVO  = (DocumentVO) getSqlMapClient().queryForObject("document.query_by_document_id",
					documentId);
			inDocumentVO = documentService.retrieveDocumentFromDBOrFS(inDocumentVO);
		} catch(SQLException exception){
			logger.error("Document ID: " + documentId, exception);
			throw new DataServiceException("General Exception @ResourceDocumentDaoImpl.retrieveDocumentByDocumentId()" + exception);
		} catch(Exception a_Ex) {
			logger.error("Document ID: " + documentId, a_Ex);
			throw new DataServiceException("General Exception @ResourceDocumentDaoImpl.retrieveDocumentByDocumentId()" + a_Ex);
		}
		return inDocumentVO;
	}
	
	
	public List<DocumentVO> getDocumentsByEntity(Object ownerId) throws DataServiceException {
		return null;
	}
	
	
	public int deleteDocumentEntity(String entityId) throws DataServiceException {
		return 0;
	}
	
	
	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO) throws DBException {
		Integer docId= (inDocumentVO != null) ? inDocumentVO.getDocumentId() : null;
		
		try {
			//getSqlMapClient().update("document.updateAll", inDocumentVO);
			inDocumentVO = documentService.updateDocument(inDocumentVO);
		} catch(Exception a_Ex)	{
			logger.error("Document ID: " + docId, a_Ex);
			throw new DBException("General Exception @ResourceDocumentDaoImpl.updateDocumentByDocumentId()", a_Ex);
		}
		return inDocumentVO;
	}
	
	
	public DocumentVO retrieveDocumentMetaDataByDocumentId(DocumentVO inDocumentVO) throws DataServiceException {
		Integer docId= null;
		DocumentVO documentVO = new DocumentVO();
		try {
			documentVO = (DocumentVO) getSqlMapClient().queryForObject("document.query_metadata_by_document_id", inDocumentVO);
			docId= (inDocumentVO != null) ? inDocumentVO.getDocumentId() : null;
			documentVO = documentService.retrieveDocumentFromDBOrFS(documentVO);
		} catch(SQLException exception){
			logger.error("Document ID: " + docId, exception);
			throw new DataServiceException("General Exception @ResourceDocumentDaoImpl.selectMetadatByDocId()", exception);
		} catch(Exception a_Ex) {
			logger.error("Document ID: " + docId, a_Ex);
			throw new DataServiceException("General Exception @ResourceDocumentDaoImpl.selectMetadatByDocId() due to " + a_Ex.getMessage());
		}
		return documentVO;
	}
	public List<DocumentVO> getRequiredDocuments(DocumentVO documentVO)throws DataServiceException {
		List <DocumentVO> documentvoList=new ArrayList<DocumentVO>();
		return documentvoList;
	}
	
	public List<DocumentVO> getDocumentsMetaDataByEntity(Object ownerId,String docSource) throws DataServiceException {
		return null;
	}
	
	public DocumentVO retrieveDocumentByTitleAndEntityID(String title, Integer ownerID) {
		return null;
	}
	
	/**
	 * Retrieve all documents with the same title for the buyer
	 * @param documentType
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByTitleAndEntityID(String title, Integer buyerID) throws DataServiceException {
		return null;
	}

	
	public List<DocumentVO> retrieveDocumentsMetaDataByBuyerId(Integer buyerID) throws DataServiceException {
		return null;
	}
	
	public Integer getLogoForBuyer(Integer buyerId, Integer categoryId) throws DataServiceException {
		int logoDocumentId = 0;
		return logoDocumentId;
	}


	/**
	 * isAllowedExtension checks to see if the File Extension provided is an allowed uploadable Extension.
	 * @param fileExtenstion
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAllowedExtension(String fileExtenstion) throws DataServiceException {
		boolean isAllowed = false;
		Integer count = (Integer)queryForObject("document.query_isvalid_extension",fileExtenstion);
		if (count == 1) {
			isAllowed = true;
		}
		return isAllowed;
	}
	
	public boolean isAllowedExtensionForSearsBuyer(String fileExtenstion) throws DataServiceException {
		boolean isAllowed = false;
		Integer count = (Integer)queryForObject("document.query_isvalid_extension.searsBuyer",fileExtenstion);
		if (count == 1) {
			isAllowed = true;
		}
		return isAllowed;
	}
	/**
	 * @param docId Integer
	 * @param buyerId Integer
	 * @return null
	 * @throws DataServiceException
	 */
	public Integer isDocExistsForUser(Integer docId, Integer buyerId)throws DataServiceException{
		return null;
	}
	/**
	 * fetches the allowed format for a given File Extension.
	 * @param fileExtenstion
	 * @return String
	 * @throws DataServiceException
	 */
	public String getFormatByExtension(String fileExtenstion) throws DataServiceException{
		String format = "";
		format = (String)queryForObject("document.query_format",fileExtenstion);
		return format;
	}
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	/**
	 * @param documentType Integer
	 * @param DocumentVO documentVO
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByFileNameAndSoID(DocumentVO documentVO) throws DataServiceException{
		return null;
	}
	/**
	 * @param ownerId
	 * @return List<DocumentVO>
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsAndLogosByEntity(Object ownerId) throws DataServiceException {
		return null;
	}
	/**
	 * Checks if the format provided is an allowed logo document format.
	 * @param format
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean isAllowedLogoDocumentFormat(String format) throws DataServiceException {
		return false;
	}
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param entityId
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DataServiceException
	 */
	public List<BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer entityId,Integer source)throws DataServiceException {
		return null;
	}
	/**
	 * Retrieves document types Count  by buyer id, to be listed in document manager
	 * @param entityId,source
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DataServiceException
	*/
	public Integer retrieveDocTypesCountByBuyerId(Integer entityId,Integer source)throws DataServiceException {
		return null;
	}
	/**
	 * Adds the  document types on the basis of buyerDocumentTypeVO
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws DataServiceException
    */
	public void addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws DataServiceException 
	{
	}
	/**
	 * Deletes the  document types on the basis of buyerDocTypeId
	 * @param buyerDocTypeId
	 * @return void
	 * @throws DataServiceException
   */
	public void  deleteDocumentTypeDetailByBuyerId(Integer buyerDocTypeId) throws DataServiceException 
	{
	}
	
	public List<DocumentVO> getRefDocumentsByEntity(Object entityId, String soId)throws DataServiceException {		
		return null;
	}
	
	public void uploadDocs(SoDocumentVO soDocVO) throws DataServiceException{
	}
	public boolean checkBuyerDocByDocumentId(Integer document_id){
		return false;
	}
	public void removeDoc(Integer documentId, String soId) throws DataServiceException{
		
	}
}
