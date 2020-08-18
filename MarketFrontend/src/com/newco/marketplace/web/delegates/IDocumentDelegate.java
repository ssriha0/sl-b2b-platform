package com.newco.marketplace.web.delegates;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;

import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IDocumentDelegate extends Serializable {
	
	/**
	 * @param documentVO
	 * @return
	 * @throws BusinessServiceException 
	 */
	public ProcessResponse insertDocument(DocumentVO documentVO) throws BusinessServiceException;
	
	/**
	 * @param documentVO
	 * @return
	 */
	public ProcessResponse deleteDocument(DocumentVO documentVO);
	
	/**
	 * @param buyerID
	 * @param roleId
	 * @param userId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByBuyerId(Integer buyerID,
			Integer roleId, Integer userId) throws BusinessServiceException;
	
	/**
	 * Retrieves documents and logos by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<DocumentVO> 
	 * @throws DelegateException
	 */
	public List<DocumentVO> retrieveBuyerDocumentsAndLogosByBuyerId(Integer buyerId) throws DelegateException;
	
	
	/**
	 * retrieves the Buyer document types 
	 * @param buyerId
	 * @return List<BuyerDocumentTypeVO>
	 * @throws BusinessServiceException
	 */
	public List<BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer buyerId,Integer source) throws DelegateException;
	/**
	 * Adds document types entered by the buyer and displays it in the document manager, 
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws DelegateException
    */
	public void  addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws DelegateException;
	
 /**
	 * Removes document types selected by the buyer on the basis of buyerDocTypeId and 
	 * displays the remaining in the document manager, 
	 * @param buyerDocTypeId
	 * @return void
	 * @throws DelegateException
     */
	 public void  deleteDocumentTypeDetailByBuyerId(Integer buyerDocTypeId) throws DelegateException;
	/**
	 * retrieveBuyerDocumentByDocumentId retrieves the Buyer document with the given documentId
	 * @param docum entId
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)throws BusinessServiceException;

	/**
	 * Retrieve a document for a Buyer with the given title
	 * @param documentType The buyer document type
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByTitleAndEntityID(int documentType, String title, Integer ownerID) throws BusinessServiceException;

	
	/**
	 * Validates if logo is present in the template
	 * @param documentVO 
	 * @return boolean
	 * @throws DelegateException
	 */
	
	public boolean isLogoPresentInTemplate(DocumentVO documentVO) throws DelegateException;

}
