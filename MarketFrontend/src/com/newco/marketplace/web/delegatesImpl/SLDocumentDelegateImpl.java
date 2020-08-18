package com.newco.marketplace.web.delegatesImpl;

import java.util.List;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.document.DocumentBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.document.BuyerDocumentDaoImpl;
import com.newco.marketplace.web.delegates.IDocumentDelegate;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class SLDocumentDelegateImpl implements IDocumentDelegate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1872876202098154603L;
	//private ServiceOrderBO serviceOrderBo;
	private IDocumentBO documentBO = new DocumentBO();
	private static final Logger logger = Logger.getLogger(BuyerDocumentDaoImpl.class.getName());
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IDocumentDelegate#insertDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertDocument(DocumentVO documentVO){

		ProcessResponse pr = new ProcessResponse();

		try {
			pr = documentBO.insertBuyerDocument(documentVO);
		} catch(DataServiceException ex){
			logger.error("[SLDocumentDelegateImpl.insertDocument - Exception] ", ex);
	        pr.setCode(OrderConstants.DOC_UPLOAD_ERROR_RC);
	        pr.setMessage("Exception thrown inserting Buyer document.");
		}
		
		return pr;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IDocumentDelegate#deleteDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse deleteDocument(DocumentVO documentVO){
		ProcessResponse pr = new ProcessResponse();
		try{
			Integer documentId  = documentVO.getDocumentId();
			Integer buyerId = documentVO.getCompanyId();
			String docTitle = documentVO.getTitle();
			pr = documentBO.deleteBuyerDocument(documentId,buyerId,docTitle);
		}catch(DataServiceException dse){
			logger.error("[SLDocumentDelegateImpl.deleteDocument - Exception] ", dse);
	        pr.setCode(OrderConstants.DOC_DELETE_ERROR_RC);
	        pr.setMessage("Exception thrown deleting Buyer document.");
		}
		catch(BusinessServiceException bse){
	        pr.setCode(OrderConstants.DOC_DELETE_ERROR_RC);
	        pr.setMessage("Exception thrown deleting Buyer document.");
		}

		return pr;
	}
	
	/**
	 * @return
	 */
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	/**
	 * @param documentBO
	 */
	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IDocumentDelegate#retrieveDocumentsByBuyerId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveDocumentsByBuyerId(Integer buyerID,
			Integer roleId, Integer userId) throws BusinessServiceException {
		return documentBO.retrieveDocumentsByBuyerId(buyerID, roleId, userId);
	}

	/**
	 * Retrieves documents and logos by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<DocumentVO> 
	 * @throws DelegateException
	 */
	public List<DocumentVO> retrieveBuyerDocumentsAndLogosByBuyerId(Integer buyerId) throws DelegateException {
		List<DocumentVO> buyerDocList = null;			
		try {		
			buyerDocList = documentBO.retrieveBuyerDocumentsAndLogosByBuyerId(buyerId);
		} catch (BusinessServiceException bse) {            
			throw new DelegateException("Error retrieving buyer documents and logo in SLDocumentDelegateImpl.retrieveBuyerDocumentsAndLogosByBuyerId()", bse);
		}			
		return buyerDocList;		
	}
	
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId,source
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DelegateException
	 */
	public List< BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer buyerId, Integer source) throws DelegateException {
		List<BuyerDocumentTypeVO> buyerDocTypeList = null;			
		try {		
			buyerDocTypeList = documentBO.retrieveDocTypesByBuyerId(buyerId,source);
		} 
		catch (BusinessServiceException bse) {            
			throw new DelegateException("Error retrieving buyer documents and logo in SLDocumentDelegateImpl.retrieveDocTypeByBuyerId()", bse);
		}			
		return buyerDocTypeList;		
	}
	/**
	 * Adds document types entered by the buyer and displays it in the document manager, 
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws DelegateException
     */
	public void addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws DelegateException
	{
		try {		
			 documentBO.addDocumentTypeDetailByBuyerId(buyerDocumentTypeVO);
		} 
		catch (BusinessServiceException bse) {            
			throw new DelegateException("Error retrieving buyer documents and logo in SLDocumentDelegateImpl.retrieveDocTypeByBuyerId()", bse);
		}			
	}
	/**
	 * Removes document types selected by the buyer on the basis of buyerDocTypeId and 
	 * displays the remaining in the document manager, 
	 * @param buyerDocTypeId
	 * @return void
	 * @throws DelegateException
    */
	public void deleteDocumentTypeDetailByBuyerId(Integer  buyerDocTypeId) throws DelegateException
	{
		try {		
		documentBO.deleteDocumentTypeDetailByBuyerId(buyerDocTypeId);
		} 
		catch (BusinessServiceException bse) {            
			throw new DelegateException("Error retrieving buyer documents and logo in SLDocumentDelegateImpl.retrieveDocTypeByBuyerId()", bse);
		}			
	}
	/**
	 * retrieveBuyerDocumentByDocumentId retrieves the Buyer document with the given documentId
	 * @param documentId
	 * @return
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)throws BusinessServiceException {
		return documentBO.retrieveBuyerDocumentByDocumentId(documentId);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IDocumentDelegate#retrieveDocumentByTitleAndEntityID(int, java.lang.String, java.lang.Integer)
	 */
	public DocumentVO retrieveDocumentByTitleAndEntityID(int documentType, String title, Integer ownerID) throws BusinessServiceException{
		return documentBO.retrieveDocumentByTitleAndEntityID(documentType, title, ownerID);
	}
	
	
	/**
	 * Validates if document logo is present in template  
	 * @param documentVO
	 * @return boolean
	 * @throws DelegateException
    */
	public boolean isLogoPresentInTemplate(DocumentVO documentVO)throws DelegateException{
		boolean flag = false;
		try{
			Integer buyerId = documentVO.getCompanyId();
			String title = documentVO.getTitle();
			flag = documentBO.validateLogoPresent(buyerId,title);
		}catch (BusinessServiceException dse) {
			throw new DelegateException(
					"Error in SLDocumentDelegateImpl.validateDocument() method",
					dse);
		}
		return flag;
	}
	
}
