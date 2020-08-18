package com.newco.marketplace.persistence.daoImpl.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;


import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNNetworkDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentDeleteException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;
import com.newco.marketplace.exception.core.document.DocumentUpdateException;
import com.newco.marketplace.exception.core.document.ServiceOrderDocumentSizeExceededException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.dao.impl.ABaseImplDao;
import com.sears.os.service.ServiceConstants;

public class SPNDocumentDaoImpl  extends ABaseImplDao implements IDocumentDao{
	
	private static final Logger logger = Logger.getLogger(BuyerDocumentDaoImpl.class);
	
	private DocumentService documentService;
	
	public List<DocumentVO> getDocumentsByEntityAndCategory(Object entityId, Integer categoryId)
	throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeDocAll(String soId) throws DataServiceException{
		
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
	
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId) throws DataServiceException {
		DocumentVO documentVO = null;
		try {
			documentVO = (DocumentVO) queryForObject("document.query_buyerdocument_by_document_id", documentId);
			documentVO = documentService.retrieveDocumentFromDBOrFS(documentVO);
        }
        catch (Exception ex) {
        	logger.info("[SPNDocumentDaoImpl.retrieveBuyerDocumentByDocumentId - Exception] "
                    + ex);
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
		DocumentVO currentDocumentVO = isAlreadyLoadedReturnLoaded(documentVO);
		if (null == currentDocumentVO) {
			try {
				insertDocument(documentVO);
			} catch (ServiceOrderDocumentSizeExceededException e) {
				pr.setCode(OrderConstants.SO_DOC_SIZE_EXCEEDED_RC);
				pr.setMessage(e.getMessage());
			}
		} else {
			pr.setCode(OrderConstants.SO_DOC_UPLOAD_EXSITS);
			pr.setMessage("A file with that name has already been uploaded.");
			//updateDocument(currentDocumentVO, documentVO);
		}
		return pr;
	}
	
	protected void insertDocument(DocumentVO newDocumentVO) throws DataServiceException {
		newDocumentVO.setStoreInDatabase(false);
		try{
			/*determinePersistanceLocation(newDocumentVO, Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB );
			buildSavePath(newDocumentVO);*/
			
			//Insert the document in Document table
			newDocumentVO = documentService.createDocument(newDocumentVO);
			
			//load so to doc cross reference table
			SPNNetworkDocumentVO spnDocVO = new SPNNetworkDocumentVO();
			spnDocVO.setDocumentId(newDocumentVO.getDocumentId());
			spnDocVO.setSpnNetworkId(newDocumentVO.getSpnNetworkId());
			
			
			//insert a record in the buyer document table
			insert("spn.network.document.insert", spnDocVO);
		} catch (DataAccessException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		} catch (ServiceOrderDocumentSizeExceededException e) {
			throw e;
		} catch (DataServiceException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		}
	}
	
	/*protected void buildSavePath(DocumentVO documentVO) throws DataServiceException{
		
		if (!documentVO.isStoreInDatabase()) {
			// get file path and append so_id 
			try {
				ApplicationPropertiesVO prop = applicationPropertiesDao.query(Constants.AppPropConstants.SO_SAVE_DOC_PATH);
				documentVO.setDocPath(prop.getAppValue() + documentVO.getSoId() + "/");
			} catch (DataAccessException e) {
				throw new DocumentRetrievalException("Unable to retrieve " + Constants.AppPropConstants.SO_SAVE_DOC_PATH + " from application_properties",e);
			} catch (DataNotFoundException e) {
				throw new DocumentRetrievalException("Unable to retrieve " + Constants.AppPropConstants.SO_SAVE_DOC_PATH + " from application_properties",e);
			}
		}
	}*/
	
	public DocumentVO isAlreadyLoadedReturnLoaded (DocumentVO documentVO) throws DataServiceException {

		DocumentVO existingDocVO = null;
		DocumentVO returnDocVO = null;
		
		existingDocVO = (DocumentVO)queryForObject("snp.network.document.query_already_exists", documentVO);
		
		if (null != existingDocVO ) {
			returnDocVO = (DocumentVO)queryForObject("snp.network.document.query_metadata_by_document_id", existingDocVO.getDocumentId());
			//returnDocVO.setSoId(existingDocVO.getSoId());
		}
		
		return returnDocVO;
	}
	
	/**
	 * removeDocument removes the document reference by the provided documentId
	 * @param documentId
	 * @throws DataServiceException
	 */
	public void removeDocument(Integer documentId) throws DataServiceException {
		List<Integer> list = new ArrayList<Integer>();
		list.add(documentId);
		removeDocuments(list);
	}
	
	
	public void removeDocuments(List<Integer> doucmentIds) throws DataServiceException{
		DocumentVO documentVO = null;
		try {
			for (Integer docId : doucmentIds) {
				// get the doc size
				documentVO = (DocumentVO) queryForObject("buyer.document.query_metadata_by_document_id", docId);
				//determinePersistanceLocation(documentVO, Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB);
				
				// flag the document as deleted
				documentService.deleteDocument(documentVO);
			}
		} 
		catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document",e);
		} 
		catch (DataServiceException e) {
			throw new DocumentUpdateException("Unable to remove documents",e);
		}
	}

	public List<DocumentVO> retrieveDocumentsByDocumentIds(List<Integer> doc_ids)throws DataServiceException {
		return null;
	}

	public DocumentVO retrieveDocumentByDocumentId(Integer documentId)throws DataServiceException{
		DocumentVO documentVO = null;
		try {
			documentVO = documentService.retrieveDocumentByDocumentId(documentId, "buyer.document.query_by_document_id");	
		}catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve document: " + documentId,e);
		}
		return documentVO;
	}
	
	
	public List<DocumentVO> getDocumentsByEntity(Object ownerId) throws DataServiceException {
		return null;
	}
	
	
	public int deleteDocumentEntity(String documentId) throws DataServiceException {
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("documentId", documentId);
			delete("buyer.delete_entity", parameter);
		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Delete Document Entity Failure",e);
		}
		return 0; 
	}
	
	
	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO) throws DBException{
		return inDocumentVO;
	}
	
	
	public DocumentVO retrieveDocumentMetaDataByDocumentId(DocumentVO inDocumentVO) throws DataServiceException{
		return inDocumentVO;
	}
	public List<DocumentVO> getRequiredDocuments(DocumentVO documentVO)throws DataServiceException {
		List <DocumentVO> documentvoList=new ArrayList<DocumentVO>();
		return documentvoList;
	}
	public List<DocumentVO> getDocumentsMetaDataByEntity(Object ownerId,String docSource) throws DataServiceException {
		return null;
	}
	
	public DocumentVO retrieveDocumentByTitleAndEntityID(String title, Integer buyerID) throws DataServiceException {
		DocumentVO documentVO = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("buyer_id", Integer.toString(buyerID.intValue()));
		try {
			documentVO = (DocumentVO) queryForObject("document.query_buyerdocument_by_title", params);
			documentVO = documentService.retrieveDocumentFromDBOrFS(documentVO);
        }
        catch (Exception ex) {
        	logger.error("[SPNDocumentDaoImpl.retrieveDocumentByTitleAndOwnerID - Exception] ", ex);
            throw new DataServiceException("Error executing: document.query_buyerdocument_by_title", ex);
        }                
        return documentVO;
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
	
	public Integer getLogoForBuyer(Integer buyerId, Integer categoryId) throws DataServiceException{
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
	 * @param documentType Integer
	 * @param DocumentVO documentVO
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByFileNameAndSoID(DocumentVO documentVO) throws DataServiceException{
		return null;
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
	public DocumentService getDocumentService() {
		return documentService;
	}
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
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
