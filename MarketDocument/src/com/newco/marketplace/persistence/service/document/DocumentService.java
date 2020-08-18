package com.newco.marketplace.persistence.service.document;

import java.util.List;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;

public interface DocumentService {
	/**
	 * createDocument handles the inserting into the Document table and persisting to the file system if necessary.  
	 * If the calling class wants to persist the document to the file system,
	 * the docuemntVO.docPath attribute must be set with the file path not including the context root.
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public DocumentVO createDocument(DocumentVO documentVO) throws DataServiceException;
	/**
	 * Updates the document details
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public DocumentVO updateDocument (DocumentVO documentVO) throws DataServiceException; 
	/**
     * deleteDocument set the delete_ind on the document table to 1 and appends '_DELETED_' || '<docId>' 
     * to the file name 
     * @param documentVO
     * @throws DataServiceException
     */
	public void deleteDocument(DocumentVO documentVO)throws DataServiceException;
	/**
     * deleteDocument set the delete_ind on the document table to 1 and appends '_DELETED_' || '<docId>' 
     * to the file name 
     * @param documentVO
     * @throws DataServiceException
     */
	public void deleteDoc(DocumentVO documentVO)throws DataServiceException;
	
    public void  deleteDocAll(String soId) throws DataServiceException;

	
	public void deleteDocs(DocumentVO documentVO)throws DataServiceException;
	
	public DocumentVO selectCount(DocumentVO documentVO)throws DataServiceException;
	/**
	 * Deletes the folder/files from file system
	 * @param documentVo
	 * @return
	 * @throws DataServiceException
	 */
	public int deleteDocumentFromFileSystem(DocumentVO documentVo) throws DataServiceException;
	/**
	 * isAllowedExtension checks to see if the File Extension provided is an allowed uploadable Extension.
	 * @param fileExtenstion
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAllowedExtension(String fileExtenstion) throws DataServiceException;
	/**
     * retrieveDocumentByDocumentId method retrieves the document details from database. 
     * @param document_id
     * @param queryId
     * @return DocumentVO 
     * @throws DataServiceException
     */
	public DocumentVO retrieveDocumentByDocumentId(Integer documentId, String queryId) throws DataServiceException;
	public DocumentVO retrieveDocumentByDocumentId_spn(Integer documentId, String queryId) throws DataServiceException;
	/**
	 * retrieveDocumentFromDBOrFS method calls the methods to get the document from file system or DB,
	 * based on the value of doc_write_read_ind,
	 * @param documentVO
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO retrieveDocumentFromDBOrFS(DocumentVO documentVO)throws DataServiceException;
	/**
	 * retrieveDocumentFromDBOrFS method calls the methods to get the list of document from file system or DB,
	 * based on the value of doc_write_read_ind,
	 * @param docVoList
	 * @return docVoList
	 * @throws DataServiceException
	 */
	public List<DocumentVO> retrieveDocumentsFromDBOrFS(List<DocumentVO> docVoList)throws DataServiceException;
	/**
	 * getDocSavePath retrieves the location for saving the document in file system
	 * @param saveDocPathKey
	 * @return savePath 
	 * @throws DocumentRetrievalException
	 */
	public void getDocSavePath(DocumentVO documentVO)throws DocumentRetrievalException;
	/**
	 * Deletes the files from file system
	 * @param queryId
	 * @param documentId
	 * @return
	 * @throws DataServiceException
	 */
	public int deleteDocumentFromFileSystem(String queryId,Integer documentId) throws DataServiceException;
	
	/**
	 * Retrieves document blobBytes 
	 * @param docId
	 * @return
	 * @throws DataServiceException
	 */
	public byte[] getDocumentBlobByDocumentId(Integer docId) throws DataServiceException;
	
	/**
	 * Retrieves document metadata 
	 * @param documentVO
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO getDocumentMetadata(DocumentVO documentVO) throws DataServiceException;
	/**
	 * Retrieves retrieves the full location for saving the document in file system
	 * @param docVO
	 * @return docPath
	 * @throws DocumentRetrievalException
	 */
	public String buildDocumentPath(DocumentVO docVO)throws DocumentRetrievalException;
	/**
     * retrieveDocumentByDocumentId method retrieves the document details from database. 
     * @param documentId
     * @param queryId
     * @return Integer
     */
	public Integer checkBuyerDocByDocumentId(Integer documentId, String queryId);
	public int deleteFileFromFileSystem(DocumentVO documentVO) throws DataServiceException ;
	
}
