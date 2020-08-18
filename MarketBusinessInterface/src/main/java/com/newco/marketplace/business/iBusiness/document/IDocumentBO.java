package com.newco.marketplace.business.iBusiness.document;

import java.util.List;


import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */
public interface IDocumentBO {
	

	/**
	 * insertBuyerDocument is responsible for inserting a document that is associated with a Buyer
	 * @param documentVO
	 * @return ProcessResponse
	 * @throws DataServiceException 
	 */
	public ProcessResponse insertBuyerDocument(DocumentVO documentVO) throws DataServiceException;

	public ProcessResponse insertVendorDocument(DocumentVO documentVO) throws BusinessServiceException;
	
	/**
	 * Newly added function to retrieve the MetaData of a document.
	 * @param myDocumentVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentMetadataByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException;
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public DocumentVO retrieveDocumentsInfoByDocumentId (DocumentVO myDocumentVO) throws BusinessServiceException;
	
	//SL-21233: Document Retrieval Code Ends
	
	
	public DocumentVO retrieveResourceDocMetadataByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException;
	public List<DocumentVO> getRequiredDocuments(DocumentVO documentVO) throws BusinessServiceException;
	/**
	 * Newly added function to Update the Document details.
	 * @param myDocumentVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO updateDocumentByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException;
	public DocumentVO updateResourceDocByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException;
	
	/**
	 * insertResourceDocument is responsible for inserting a document that is associated with a Resource
	 * @param documentVO
	 * @return ProcessResponse
	 */
	public ProcessResponse insertResourceDocument(DocumentVO documentVO) throws DataServiceException ;
	
	/**
	 * insertServiceOrderDocument is responsible for inserting a document that is associated with a Service Order
	 * @param documentVO
	 * @return ProcessResponse
	 */
	public ProcessResponse insertServiceOrderDocument(DocumentVO documentVO) throws DataServiceException ;
	
	/**
	 * deleteBuyerDocument is responsible for deleting a document associated to a Buyer for the given documentId
	 * @param documentId
	 * @return ProcessResponse
	 */
	public ProcessResponse deleteBuyerDocument(Integer documentId,Integer buyerId,String docTitle)throws DataServiceException,BusinessServiceException;
	
	/**
	 * deleteVendorDocument is responsible for deleting a document associated to a Vendor for the given documentId
	 * @param documentId
	 * @return ProcessResponse
	 */
	public ProcessResponse deleteVendorDocument(Integer documentId)throws DataServiceException;
	
	/**
	 * deleteResourceDocument is responsible for deleting a document associated to a Resource for the given documentId
	 * @param documentId
	 * @return ProcessResponse
	 */
	public ProcessResponse deleteResourceDocument(Integer documentId)throws DataServiceException;
	
	/**
	 * deleteResourceDocument is responsible for deleting a document associated to a Service Order for the given documentId
	 * This method assumes that it will not be called by any user who does not have rights to view or edit the service order
	 * @param documentId
	 * @param roleId role id of user requesting deletion
	 * @param userId id of user requesting deletion
	 * @param platformIndicator - default values  - PublicAPI, MobileAPI, ProPortal
	 * @return ProcessResponse
	 */
	public ProcessResponse deleteServiceOrderDocument(Integer documentId, Integer roleId, String platformInd, Integer userId) throws DataServiceException;
	
	/**
	 * deleteResourceDocument is responsible for deleting a document associated to a Service Order for the given documentId
	 * This method assumes that it will not be called by any user who does not have rights to view or edit the service order
	 * @param documentId
	 * @param roleId role id of user requesting deletion
	 * @param userId id of user requesting deletion
	 * @return ProcessResponse
	 */
	public ProcessResponse deleteServiceOrderDocument(Integer documentId, Integer roleId, Integer userId,String soId) throws DataServiceException;
	
	
	public ProcessResponse deleteServiceOrderDocumentforTask(String soId) throws DataServiceException;

	
	/**
	 * deleteResourceDocument is responsible for deleting a document associated to a Service Order for the given documentId
	 * @param documentIds List of document Id's to delete
	 * @param roleId role id of user requesting deletion
	 * @param userId id of user requesting deletion
	 * @return ProcessResponse
	 */
	public ProcessResponse deleteServiceOrderDocuments(List<Integer> documentIds, Integer roleId, Integer userId) throws DataServiceException;
	
	/**
	 * retrieveBuyerDocumentByDocumentId retrieves the Buyer document with the given documentId
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)throws BusinessServiceException;
	
	/**
	 * retrieveVendorDocumentByDocumentId retrieves the Vendor document with the given documentId
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveVendorDocumentByDocumentId(Integer documentId)throws BusinessServiceException;
	
	
	
	/**
	 * retrieveResourceDocumentByDocumentId retrieves the Resource document with the given documentId
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveResourceDocumentByDocumentId(Integer documentId)throws BusinessServiceException;
	
	/**
	 * retrieveServiceOrderDocumentByDocumentId retrieves the Service Order document with the given documentId 
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveServiceOrderDocumentByDocumentId(Integer documentId)throws BusinessServiceException;

	/**
	 * retrieveServiceOrderDocumentByDocumentId retrieves the Service Order document with the given documentId
	 * while validating that the provided user has rights to view the asked for document
	 * @param documentId
	 * @param soId
	 * @param roleId
	 * @param requestingUserId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveServiceOrderDocumentByDocumentId(Integer documentId, String soId, Integer roleId, Integer requestingUserId,Integer requestingUserRoleId)throws BusinessServiceException;
	
	/**
	 * retrieveDocumentsByServiceOrderId returns all documents attached to the Service Order.  If no documents are found and empty List is returned
	 * @param soId service order id to retrieve documents for
	 * @param roleId role associated to user requesting documents
	 * @param userId Id of user requesting documents
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByServiceOrderId(String soId, Integer roleId, Integer userId) throws BusinessServiceException;
	
	/**
	 * retrieveDocumentsMetaDataByServiceOrderId returns all documents meta data attached to the Service Order.  If no documents are found and empty List is returned
	 * @param soId service order id to retrieve documents for
	 * @param roleId role associated to user requesting documents
	 * @param userId Id of user requesting documents
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsMetaDataByServiceOrderId(String soId, Integer roleId, Integer userId,String docSource) throws BusinessServiceException;
	
	/**
	 * retrieveDocumentsByVendorId returns all documents associated to the given vendor.  If no documents are found and empty List is returned
	 * @param vendorId
	 * @param roleId role associated to user requesting documents
	 * @param userId Id of user requesting documents
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByVendorId(Integer vendorId, Integer roleId, Integer userId)throws BusinessServiceException;

	/**
	 * retrieveDocumentsByBuyerId returns all documents associated to the given buyer.  If no documents are found and empty List is returned
	 * @param buyerID
	 * @param roleId role associated to user requesting documents
	 * @param userId Id of user requesting documents
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByBuyerId(Integer buyerID, Integer roleId, Integer userId)throws BusinessServiceException;
	
	
	/**
	 * @param buyerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsMetaDataByBuyerId(Integer buyerID)throws BusinessServiceException;
	/**
	 * @param entity_id
	 * @param document_id
	 * @param roleId role associated to user requesting documents
	 * @param userId Id of user requesting documents
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByEntityIdAndDocumentId(Integer entity_id,Integer document_id)throws BusinessServiceException;

	/**
	 * retrieveBuyerDocumentsByBuyerIdAndCategory returns all documents associated with the given Buyer and Category
	 * @param buyerId
	 * @param categoryId
	 * @param roleId role associated to user requesting documents
	 * @param userId Id of user requesting documents
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveBuyerDocumentsByBuyerIdAndCategory(Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws BusinessServiceException;
	
	/**
	 * retrieveServiceOrderDocumentsBySOIdAndCategory returns all documents associated with the given Service Order and Category
	 * @param soId
	 * @param categoryId
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveServiceOrderDocumentsBySOIdAndCategory(String soId, Integer categoryId, Integer roleId, Integer userId) throws BusinessServiceException;

	
	/**
	 * @param documentType
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByTitleAndEntityID(int documentType, String title, Integer ownerID) throws BusinessServiceException;
	
	/**
	 * Retrieve all documents with the same title for the buyer
	 * @param documentType
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByTitleAndEntityID(int documentType, String title, Integer ownerID) throws BusinessServiceException;	
	
	
	/**
	 * @param documentVO
	 * @return
	 * @throws DataServiceException
	 */
	public ProcessResponse insertSPNDocument(DocumentVO documentVO) throws DataServiceException;
	

	/**
	 * This method will persist a document to the file system when the user is
	 * in the Simple Buyer flow and is in the process of creating a new Service
	 * Order.
	 * <b>This method must not be used when the user is editing an existing Service Order,
	 * use <code>insertServiceOrderDocument(DocumentVO documentVO)</code></b> 
	 * @param documentVO
	 * @return
	 * @throws DataServiceException
	 */
	public ProcessResponse insertTemporarySimpleBuyerServiceOrderDocument(DocumentVO documentVO) throws DataServiceException ;
	
	/**
	 * This method will return the document meta data for a simple buyer that is creating a new service order
	 * <b>This method must not be used when the user is editing an existing Service Order,
	 * use <code>retrieveDocumentsMetaDataByServiceOrderId(String soId, Integer roleId, Integer userId)</code></b>
	 * @param simpleBuyerId
	 * @param categoryId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveTemporaryDocumentsMetaDataBySimpleBuyerIdAndCategory(String simpleBuyerId, Integer categoryId) throws BusinessServiceException;
	
	/**
	 * This method will return the documents for a simple buyer that is creating a new service order
	 * <b>This method must not be used when the user is editing an existing Service Order,
	 * use <code>retrieveServiceOrderDocumentsBySOIdAndCategory(String soId, Integer roleId, Integer userId)</code></b>
	 * @param simpleBuyerId
	 * @param categoryId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(String simpleBuyerId, Integer categoryId) throws BusinessServiceException;
	
	/**
	 * This method will return a specific document for the Simple Buyer flow.
	 * <b>This method must not be used when the user is editing an existing Service Order, 
	 * use <code>retrieveServiceOrderDocumentByDocumentId(Integer documentId, String soId, Integer roleId, Integer requestingUserId)</code>
	 * instead</b>
	 * @param documentId
	 * @param simpleBuyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveTemporarySimpleBuyerSODocumentByDocumentId(Integer documentId, String simpleBuyerId) throws BusinessServiceException;
	
	/**
	 * This method will move the Simple Buyer documents from the temporary location to the system default document location,
	 * and associate the document(s) with the soId passed in.
	 * Step 1 - get all documents from temporary location by simpleBuyerId
	 * Step 2 - Call uploadDocument(DocumentVO documentVO) in <code>ServiceOrderDocumentDao<code>
	 * Step 3 - delete all documents for buyer from temporary location  
	 * @param simpleBuyerId
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public void persistSimpleBuyerDocuments(String simpleBuyerId, String soId, Integer entityId) throws BusinessServiceException;
	
	/**
	 * This method will delete all the Simple Buyer documents from the temporary location   
	 * @param simpleBuyerId
	 * @throws BusinessServiceException
	 */
	public void deleteTemporarySimpleBuyerDocuments(String simpleBuyerId) throws BusinessServiceException;

	 

	/**
	 * This method will delete a Simple Buyer document from the temporary location
	 * @param simpleBuyerId
	 * @param docId
	 * @throws BusinessServiceException
	 */
	public void deleteTemporarySimpleBuyerDocument(String simpleBuyerId , Integer docId) throws BusinessServiceException;
	/**
	 * This mehtod check for the valid image format
	 * @param format
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isAllowedImageFormat(String format) throws BusinessServiceException;
	
	/**
	 * This methis checks the allowed max file sie from the application_properties table
	 * @param totalSize
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isTotalImageSizeAllowed(Long totalSize) throws BusinessServiceException;

	public String getValidDisplayExtensions(); 
	/**
	 * retrieveLogoForBuyer returns one Logo document associated with the given Buyer
	 * @param buyerId
	 * @param categoryId
	 * @return logoDocumentId
	 * @throws BusinessServiceException
	 */
	public Integer retrieveLogoForBuyer(Integer buyerId, Integer categoryId) throws BusinessServiceException;
	/**
	 * @param documentType Integer
	 * @param fileName String
	 * @param ownerID Integer
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByFileNameAndEntityID(int documentType, String fileName, Integer ownerID) throws BusinessServiceException;
	
	/**
	 * retrieves document by given documentId and buyerId
	 * @param docId Integer
	 * @param buyerId Integer
	 * @return userDocId Integer
	 * @throws BusinessServiceException
	 */
	public Integer isDocExistsForUser(Integer docId, Integer buyerId)throws BusinessServiceException;
	
	/**
	 * @param documentType Integer
	 * @param DocumentVO documentVO
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByFileNameAndSoID(int documentType,DocumentVO documentVO) throws BusinessServiceException;
	/**
	 * Retrieves documents and logos by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<DocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveBuyerDocumentsAndLogosByBuyerId(Integer buyerId) throws BusinessServiceException;

	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<DocumentVO>
	 * @throws BusinessServiceException
	 */
	
	
	public List< BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer buyerId,Integer source)throws BusinessServiceException;
	/**
	 * Retrieves document types count by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<BuyerDocumentTypeVO>
	 * @throws BusinessServiceException
	 */
	public Integer retrieveDocTypesCountByBuyerId(Integer buyerId,Integer source) throws BusinessServiceException;
	
	/**
	 * Adds document types entered by the buyer and displays it in the document manager, 
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws BusinessServiceException
     */
	public void addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws BusinessServiceException ;
	
	 /**
	 * Removes document types selected by the buyer on the basis of buyerDocTypeId and 
	 * displays the remaining in the document manager, 
	 * @param buyerDocTypeId
	 * @return void
	 * @throws BusinessServiceException
     */
	 public void  deleteDocumentTypeDetailByBuyerId(Integer buyerDocTypeId) throws BusinessServiceException;
	 
	/**
	 * Retrieves reference documents by buyer id, to be listed in Create SO
	 * @param buyerId
	 * @param soId
	 * @return List<DocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveRefBuyerDocumentsByBuyerId(Integer buyerId, String soId) throws BusinessServiceException;
	
	/**
	 * insertSODocuments is responsible for inserting a document that is associated with a Service Order
	 * @param soDocVO
	 * @return 
	 */
	public void insertSODocuments(SoDocumentVO soDocVO) throws DataServiceException ;
	
	/*
	 * PDF batch regeneration
	 */
	
	public boolean checkIfMobileSignatureDocumentExists(String soId) throws BusinessServiceException;
	
	public Integer checkIfSignaturePDFDocumentExisits(String soId) throws BusinessServiceException;
	
	public void deleteSODocumentMapping(String soId, Integer documentId) throws DataServiceException;
	
	public void updatePDFBatchParamaters(String soId) throws DataServiceException;
	
	/**
	 * validates if document logo is present in template
	 * @param buyerId
	 * @param title
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean validateLogoPresent(Integer buyerId,String title) throws BusinessServiceException;
	
	public String getConstantValueFromDB(String appkey)
			throws BusinessServiceException ;
	
	
	
}
