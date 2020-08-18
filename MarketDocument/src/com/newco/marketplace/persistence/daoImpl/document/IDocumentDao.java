package com.newco.marketplace.persistence.daoImpl.document;

import java.util.List;


import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.32 $ $Author: glacy $ $Date: 2008/05/02 21:23:47 $
 */


public interface IDocumentDao{
	
	/**
	 * Uploads a document to storage
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public ProcessResponse uploadDocument(DocumentVO documentVO) throws DataServiceException;
	public DocumentVO retrieveDocumentMetaDataByDocumentId(DocumentVO inDocumentVO) throws DataServiceException;
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public DocumentVO retrieveDocumentsInfoByDocumentId(DocumentVO inDocumentVO) throws DataServiceException;
	
	//SL-21233: Document Retrieval Code Ends
	
	
	public List<DocumentVO> getRequiredDocuments(DocumentVO documentVO)throws DataServiceException;
	
	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO) throws DBException;
	
	
	/**
	 * retrieveDocumentByDocumentId will retrieve a DocumentVO based on the id passed in
	 * @param document_id
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO retrieveDocumentByDocumentId(Integer document_id) throws DataServiceException;
	
	public DocumentVO retrieveDocumentByTitleAndEntityID(String title, Integer ownerID) throws DataServiceException;
	
	/**
	 * Retrieve all documents with the same title.
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws DataServiceException
	 */
	
	public List<DocumentVO> retrieveDocumentsByTitleAndEntityID(String title, Integer ownerID) throws DataServiceException;
	
	
	/**
	 * retrieveDocumentsByDocumentIds will return a List of DocumentVOs for the List of id's passed in
	 * @param doc_ids
	 * @return
	 * @throws DataServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByDocumentIds(List<Integer> doc_ids)throws DataServiceException;

	
	/**
	 * @param type
	 * @param entity_id
	 * @return
	 * @throws DataServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByType(int type, Integer entity_id)throws DataServiceException;
	
	/**
	 * @param entity_id
	 * @return
	 * @throws DataServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByEntityIdAndDocumentId(Integer entity_id,Integer document_id)throws DataServiceException;	
	
	/**
	 * retrieveBuyerDocumentByDocumentId will return a DocumentVO of id passed in
	 * @param documentId
	 * @return
	 * @throws DataServiceException
	 */
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)throws DataServiceException;
	
	public List<DocumentVO> retrieveDocumentsMetaDataByBuyerId(Integer buyerID) throws DataServiceException;
	/**
	 * isAlreadyLoaded checks to see if the document has already been uploaded and if it has it will return 
	 * the document.  If the document has not been previously uploaded a null will be returned
	 * @param documentVO
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO isAlreadyLoadedReturnLoaded (DocumentVO documentVO) throws DataServiceException; 
	
	/**
	 * removeDocuments takes a list of documentIds and removes them
	 * @param doucmentIds
	 * @throws DataServiceException
	 */
	public void removeDocuments(List<Integer> doucmentIds) throws DataServiceException;
	
	/**
	 * removeDocument removes the document reference by the provided documentId
	 * @param documentId
	 * @throws DataServiceException
	 */
	public void removeDocument(Integer documentId) throws DataServiceException;
	/**
	 * removeDoc removes the document by the provided documentId
	 * @param documentId
	 * @throws DataServiceException
	 */
	public void removeDoc(Integer documentId, String soId) throws DataServiceException;
	
	public void removeDocAll(String soId) throws DataServiceException;

	
	/**
	 * getDocumentsByEntity returns all documents associated for the given entity.  If no documents are found an empty List is returned
	 * @param ownerId examples (Service Order id, Buyer id, Vendor id)
	 * @return
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsByEntity(Object ownerId) throws DataServiceException;
	
	/**
	 * getDocumentsMetaDataByEntity returns all documents meta data associated for the given entity.  If no documents are found an empty List is returned
	 * @param ownerId examples (Service Order id, Buyer id, Vendor id)
	 * @return
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsMetaDataByEntity(Object ownerId,String docSource) throws DataServiceException;
	/**
	 * getDocumentsByEntityAndCategory returns all documents for the given entity and category.  If no documents are found an empty List is returned
	 * @param ownerId examples (Service Order id, Buyer id, Vendor id)
	 * @param categoryId category id documents belong to
	 * @return
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsByEntityAndCategory(Object ownerId, Integer categoryId) throws DataServiceException;
	
	
	/**
	 * Does a physical delete of the documents FROM THE FILE SYSTEM ONLY
	 * The MetaData is not deleted.
	 * @param entityId
	 */
	public int deleteDocumentEntity(String entityId) throws DataServiceException;


	/**
	 * getLogoForBuyer returns one Logo document for the given buyer id and category.
	 * @param Buyer id
	 * @param categoryId category id documents belong to. In this case it will Logo category id
	 * @return logoDocumentId
	 * @throws DataServiceException
	 */
	public Integer getLogoForBuyer(Integer buyerId, Integer categoryId) throws DataServiceException;
	
	/**
	 * isAllowedExtension checks to see if the File Extension provided is an allowed uploadable Extension.
	 * @param fileExtenstion
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAllowedExtension(String fileExtenstion) throws DataServiceException;
	public boolean isAllowedExtensionForSearsBuyer(String fileExtenstion) throws DataServiceException;
	
	
	public DocumentVO retrieveDocumentByDocumentIdAndSOId(Integer documentId, String soId) throws DataServiceException;
	/**
	 * retrieves document by given documentId and buyerId
	 * @param docId Integer
	 * @param buyerId Integer
	 * @return userDocId Integer
	 * @throws DataServiceException
	 */
	public Integer isDocExistsForUser(Integer docId, Integer buyerId)throws DataServiceException;
	/**
	 * fetches the allowed format for a given File Extension.
	 * @param fileExtenstion
	 * @return String
	 * @throws DataServiceException
	 */
	public String getFormatByExtension(String fileExtenstion) throws DataServiceException;
	
	/**
	 * @param DocumentVO documentVO
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO retrieveDocumentByFileNameAndSoID(DocumentVO documentVO) throws DataServiceException;
	
	/**
	 * Retrieves documents and logos by buyer id, to be listed in document manager
	 * @param ownerId
	 * @return List<DocumentVO>
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsAndLogosByEntity(Object ownerId) throws DataServiceException;
	/**
	 * Checks to see if the format provided is an allowed logo uploadable format.
	 * @param format
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean isAllowedLogoDocumentFormat(String format) throws DataServiceException;
	
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param ownerId,source
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DataServiceException
	  */
	public List<BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer ownerId,Integer source) throws DataServiceException;
	/**
	 * Retrieves document types count by buyer id, to be listed in document manager
	 * @param ownerId,source
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DataServiceException
	  */
	public Integer retrieveDocTypesCountByBuyerId(Integer entityId,Integer source)throws DataServiceException;
	/**
	 * Adds document types entered by the buyer and displays it in the document manager, 
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws BusinessServiceException
     */
	public void addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws DataServiceException;
	 /**
	 * Removes document types selected by the buyer on the basis of buyerDocTypeId and 
	 * displays the remaining in the document manager, 
	 * @param buyerDocTypeId
	 * @return void
	 * @throws DataServiceException
     */
	public void deleteDocumentTypeDetailByBuyerId(Integer buyerDocTypeId) throws DataServiceException;

	/**
	 * Retrieves reference documents by buyer id, to be listed in Create SO
	 * @param ownerId
	 * @param soId
	 * @return List<DocumentVO>
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getRefDocumentsByEntity(Object ownerId, String soId) throws DataServiceException;
	

	/**
	 * Uploads a document to storage
	 * @param soDocVO
	 * @throws DataServiceException
	 */
	public void uploadDocs(SoDocumentVO soDocVO) throws DataServiceException;
	/**
	 * checkBuyerDocByDocumentId will retrieve a DocumentVO based on the id passed in
	 * @param document_id
	 * @return boolean
	 */
	public boolean checkBuyerDocByDocumentId(Integer document_id);
}
