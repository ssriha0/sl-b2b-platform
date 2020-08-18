package com.newco.marketplace.business.businessImpl.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentDeleteException;
import com.newco.marketplace.exception.core.document.DocumentUpdateException;
import com.newco.marketplace.exception.core.document.ServiceOrderDocumentSizeExceededException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.daoImpl.document.DocumentDaoFactory;
import com.newco.marketplace.persistence.daoImpl.document.IDocumentDao;
import com.newco.marketplace.persistence.daoImpl.document.ServiceOrderDocumentDao;
import com.newco.marketplace.persistence.iDao.document.ISimpleDocumentDao;
import com.newco.marketplace.persistence.iDao.so.IBuyerSOTemplateDAO;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class DocumentBO implements IDocumentBO {

	private static final String STRING_EMPTY = "";
	private static final String DOCUMENT_INSERT = "insert";
	private static final String DOCUMENT_DELETED = "delete";
	private static final Logger logger = Logger.getLogger(DocumentBO.class);
	private static final String PHOTO="photo";
	private static final String DOCUMENT="document";
	private static final String OTHERS="others";
	private static final String IMAGE="image";
	private static final String REFERENCE="reference";

	private ServiceOrderDao serviceOrderDao;
	private DocumentDaoFactory documentDaoFactory;
	private ISimpleDocumentDao simpleDocumentDao;
	private IBuyerSOTemplateBO buyerSOTemplateBO;
	private IBuyerSOTemplateDAO templateDAO;
	private IRelayServiceNotification relayNotificationService;

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#insertBuyerDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertBuyerDocument(DocumentVO documentVO) throws DataServiceException  {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);

		try {
			processResp = insertDocument(documentVO,Constants.DocumentTypes.BUYER);
		} catch (DataServiceException e) {
			logger.error("[DocumentBO.insertBuyerDocument] " + e);                
            throw new DataServiceException("Exception: While Inserting Buyer Document Details", e);
		} 

		return processResp;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#insertVendorDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertVendorDocument(DocumentVO documentVO) throws BusinessServiceException{
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		
		try {
			processResp = insertDocument(documentVO,Constants.DocumentTypes.VENDOR);
		} catch (DataServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			
            processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
            processResp.setMessage("Exception thrown inserting vendor document");
            throw new BusinessServiceException("Exception : While Inserting Document ", e);
		} 
		
		return processResp;
	}
	
	public DocumentVO retrieveDocumentMetadataByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException{
		
		DocumentVO documentVO = new DocumentVO();
		try{
			documentVO =  documentDaoFactory.getDocumentDao(Constants.DocumentTypes.VENDOR)
											.retrieveDocumentMetaDataByDocumentId(myDocumentVO);
			logger.info(" I got a document metadata in the bus bean " );
				
		}catch (DataServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveDocumentMetadataByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveDocumentMetadataByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return documentVO;
	}
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public DocumentVO retrieveDocumentsInfoByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException{
		
		DocumentVO documentVO = new DocumentVO();
		try{
			documentVO =  documentDaoFactory.getDocumentDao(Constants.DocumentTypes.VENDOR)
											.retrieveDocumentsInfoByDocumentId(myDocumentVO);
			logger.info(" I got a document metadata in the bus bean " );
				
		}catch (DataServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveDocumentMetadataByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveDocumentMetadataByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return documentVO;
	}
	
	//SL-21233: Document Retrieval Code Ends
	
	
	public DocumentVO updateDocumentByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException {
		try{
			documentDaoFactory.getDocumentDao(Constants.DocumentTypes.VENDOR)
			.updateDocumentByDocumentId(myDocumentVO);
				
		}catch (DBException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.updateDocumentByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.updateDocumentByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return myDocumentVO;
	}
	
	public DocumentVO retrieveResourceDocMetadataByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException{
		
		DocumentVO documentVO = new DocumentVO();
		try{
			documentVO =  documentDaoFactory.getDocumentDao(Constants.DocumentTypes.RESOURCE)
											.retrieveDocumentMetaDataByDocumentId(myDocumentVO);
			logger.info(" I got a document metadata in the bus bean " );
				
		}catch (DataServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveResourceDocMetadataByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveResourceDocMetadataByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return documentVO;
	}
	public List<DocumentVO> getRequiredDocuments(DocumentVO documentVO)throws BusinessServiceException{
		List <DocumentVO> documentvoList=new ArrayList<DocumentVO>();
		try
		{
			documentvoList=documentDaoFactory.getDocumentDao(Constants.DocumentTypes.SERVICEORDER).getRequiredDocuments(documentVO);
		}
		catch (DataServiceException e) {
			logger.error("Exception thrown inside getRequiredDocuments", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.getRequiredDocuments Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inside getRequiredDocuments", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.getRequiredDocuments Exception Message "
					+ a_Ex.getMessage());
		}
		 return  documentvoList;
	}
	
	public DocumentVO updateResourceDocByDocumentId(DocumentVO myDocumentVO) throws BusinessServiceException {
		try{
			documentDaoFactory.getDocumentDao(Constants.DocumentTypes.RESOURCE)
			.updateDocumentByDocumentId(myDocumentVO);
				
		}catch (DBException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.updateResourceDocByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.updateResourceDocByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return myDocumentVO;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#insertResourceDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertResourceDocument(DocumentVO documentVO) {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		
		try {
			processResp = insertDocument(documentVO,Constants.DocumentTypes.RESOURCE);
		} catch (DataServiceException e) {
			logger.error("Exception thrown inserting resource document", e);
			
            processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
            processResp.setMessage("Exception thrown inserting resource document");
		} 
		
		return processResp;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#insertServiceOrderDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertServiceOrderDocument(DocumentVO documentVO) {
		
		// set category id for document
		 /*if(StringUtils.contains(documentVO.getDocCategory().toLowerCase(), PHOTO))
		 {
			 documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.IMAGE));
		 }
		 else if(StringUtils.contains(documentVO.getDocCategory().toLowerCase(), DOCUMENT))
		 {
			 documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.REFERENCE));
		 }
		 else if(StringUtils.contains(documentVO.getDocCategory().toLowerCase(), OTHERS))
		 {
			 documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.OTHER));
		 }*/
		  if(null!=documentVO.getDocCategory() && StringUtils.contains(documentVO.getDocCategory().toLowerCase(), IMAGE))
		 {
			 documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.IMAGE));
		 }
		 else if(null!= documentVO.getDocCategory() && StringUtils.contains(documentVO.getDocCategory().toLowerCase(), REFERENCE))
		 {
			 documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.REFERENCE));
		 }
		 else if (StringUtils.contains(documentVO.getFormat(), "image/")&& (documentVO.getDocCategoryId()==null)) {	
				documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.IMAGE));
			} 
		 else if(null == documentVO.getDocCategoryId()) {
			documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.REFERENCE));
		}
		
		
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		try {
			ServiceOrder serviceOrderVO = serviceOrderDao.getServiceOrder(documentVO.getSoId());
            if(null == serviceOrderVO) throw new BusinessServiceException("Could not locate service order");
			IServiceOrderBO serviceOrderBO = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx()
			.getBean(Constants.ApplicationContextBeans.SERVICE_ORDER_BO_BEAN);
			
			// validate that the user id in the document is authorized to upload a document for the service order
			if (documentVO.getRoleId().intValue() == OrderConstants.BUYER_ROLEID ||
				documentVO.getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				boolean isBuyerAssociated = false;
				isBuyerAssociated = serviceOrderBO.isBuyerAssociatedToServiceOrder(serviceOrderVO, documentVO.getCompanyId());
				
				if (!isBuyerAssociated) {
					processResp.setCode(OrderConstants.DOC_USER_AUTH_ERROR_RC);
		            processResp.setMessage("User is not associated with Buyer origization and cannot insert document into service order");
				} else {
					processResp = insertDocument(documentVO,Constants.DocumentTypes.SERVICEORDER);
				}
			} else {
				// check to see if the user is associated with the provider who accepted the service order
				if (serviceOrderBO.isVendorAssociatedToServiceOrder(serviceOrderVO, documentVO.getEntityId())) {
					// user is associated with accepted provider now time to check to see if the SO
					// is in the state where the provider can insert
					switch (serviceOrderVO.getWfStateId().intValue()) {
						case OrderConstants.ACCEPTED_STATUS:
						case OrderConstants.ACTIVE_STATUS:
						case OrderConstants.COMPLETED_STATUS:
						case OrderConstants.PROBLEM_STATUS:
						case OrderConstants.CLOSED_STATUS:
						case OrderConstants.CANCELLED_STATUS:	
							processResp = insertDocument(documentVO,Constants.DocumentTypes.SERVICEORDER);
							break;
						default:
							processResp.setCode(OrderConstants.SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC);
			            	processResp.setMessage("Service Order is not in a state for the provider to upload documents");
							break;
					}
				} else {
					// user is not authorized to upload to this service order returning error
					processResp.setCode(OrderConstants.DOC_USER_AUTH_ERROR_RC);
		            processResp.setMessage("User is not associated with Buyer or Provider origization cannot insert document into service order");
				}
			}
		} catch (ServiceOrderDocumentSizeExceededException e) {
			processResp.setCode(OrderConstants.SO_DOC_SIZE_EXCEEDED_RC);
            processResp.setMessage(e.getMessage());  
		} catch (DocumentUpdateException e) {
			processResp.setCode(OrderConstants.DOC_UPLOAD_ERROR_RC);
            processResp.setMessage(e.getMessage());  
		} catch (BusinessServiceException e) {
			processResp.setCode(OrderConstants.DOC_USER_AUTH_ERROR_RC);
            processResp.setMessage(e.getMessage());
		} catch (DataServiceException e) {
			processResp.setCode(OrderConstants.DOC_PROCESSING_ERROR_RC);
            processResp.setMessage(e.getMessage());  
		} 
		
		return processResp;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteBuyerDocument(java.lang.Integer)
	 */
	public ProcessResponse deleteBuyerDocument(Integer documentId,Integer buyerId,String docTitle) throws DataServiceException,BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		
		try {
			deleteDocument(documentId, Constants.DocumentTypes.BUYER);
			if(null != docTitle){
				this.updateDocListInBuyerSoTemplate(buyerId,docTitle);
			}
		} catch (DataServiceException e) {
			logger.error("Exception thrown deleting buyer document", e);
            throw new DataServiceException("Exception: While Inserting Buyer Document Details", e);
		} 
		
		return processResp;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteVendorDocument(java.lang.Integer)
	 */
	public ProcessResponse deleteVendorDocument(Integer documentId) {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		
		try {
			deleteDocument(documentId, Constants.DocumentTypes.VENDOR);
		} catch (DataServiceException e) {
			logger.error("Exception thrown deleting vendor document", e);
			
            processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
            processResp.setMessage("Exception thrown deleting vendor document");
		} 
		
		return processResp;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteResourceDocument(java.lang.Integer)
	 */
	public ProcessResponse deleteResourceDocument(Integer documentId) {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		
		try {
			deleteDocument(documentId, Constants.DocumentTypes.RESOURCE);
		} catch (DataServiceException e) {
			logger.error("Exception thrown deleting resource document", e);
			
            processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
            processResp.setMessage("Exception thrown deleting resource document");
            
		} 
		
		return processResp;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteServiceOrderDocument(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public ProcessResponse deleteServiceOrderDocument(Integer documentId, Integer roleId, String platformInd, Integer userId) {
		
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		int soStatus = 0;
		try {
			DocumentVO documentVO = retrieveServiceOrderDocumentByDocumentId(documentId);
			
			ServiceOrder serviceOrderVO = serviceOrderDao.getServiceOrder(documentVO.getSoId());
			IServiceOrderBO serviceOrderBO = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx()
			.getBean(Constants.ApplicationContextBeans.SERVICE_ORDER_BO_BEAN);
			
			if (roleId.intValue() == OrderConstants.BUYER_ROLEID ||
				roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				boolean isBuyerAssociated = false;
				
				isBuyerAssociated = serviceOrderBO.isBuyerAssociatedToServiceOrder(serviceOrderVO, documentVO.getCompanyId());
				
				if (!isBuyerAssociated) {
					processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
	            	processResp.setMessage("User is not associated to SO.  Cannot delete it's documents");
				}else {		
					if(null != serviceOrderVO.getWfStateId()){
						soStatus = serviceOrderVO.getWfStateId().intValue();
					}					
					switch (soStatus) {
						case OrderConstants.VOIDED_STATUS:	
						case OrderConstants.DELETED_STATUS:	
							processResp.setCode(OrderConstants.SO_DOC_WFSTATE_CLOSED_DELETE);
							processResp.setMessage("Service Order is not in a state for the buyer to delete documents");
							break;
						default:
							deleteDocument(documentId, Constants.DocumentTypes.SERVICEORDER);	
							
							// relay notification
						if (!"PublicAPI".equals(platformInd) && null != documentVO.getDocCategoryId()
								&& (documentVO.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.IMAGE 
									|| documentVO.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.REFERENCE)) {
								sendRelayNotification(documentId, documentVO.getFileName(), documentVO.getRoleId(), documentVO.getSoId(), DOCUMENT_DELETED);
							}
							
							break;
					}					
				}
			} else if (documentVO.getRoleId().intValue() == OrderConstants.BUYER_ROLEID ||
					documentVO.getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				processResp.setCode(OrderConstants.DOC_USER_AUTH_ERROR_RC);
            	processResp.setMessage("User is not associated to SO.  Cannot delete it's documents");
			} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
				if (serviceOrderBO.isVendorAssociatedToServiceOrder(serviceOrderVO, documentVO.getEntityId())) {
					switch (serviceOrderVO.getWfStateId().intValue()) {
						case OrderConstants.ROUTED_STATUS:	
							processResp.setCode(OrderConstants.SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC);
			            	processResp.setMessage("Service Order is not in a state for the provider to delete documents");
			            	break;
						default:
							deleteDocument(documentId, Constants.DocumentTypes.SERVICEORDER);	
							
							// relay notification
							if (null != documentVO.getDocCategoryId()
									&& (documentVO.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.IMAGE || documentVO.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.REFERENCE)) {
								sendRelayNotification(documentId, documentVO.getFileName(), documentVO.getRoleId(), documentVO.getSoId(), DOCUMENT_DELETED);
							}
							
							break;
					}
				} else {
					processResp.setCode(OrderConstants.DOC_USER_AUTH_ERROR_RC);
	            	processResp.setMessage("User is not associated to SO.  Cannot delete it's documents");
				}
			} else {
				processResp.setCode(OrderConstants.DOC_USER_AUTH_ERROR_RC);
            	processResp.setMessage("User does not have the right role to delete documents");
			}
		} catch (DocumentDeleteException e){
			processResp.setCode(OrderConstants.DOC_DELETE_ERROR_RC);
            processResp.setMessage("Exception thrown deleting service order document");
		} catch (DataServiceException e) {
			processResp.setCode(OrderConstants.DOC_PROCESSING_ERROR_RC);
            processResp.setMessage("Exception thrown deleting service order document");           
		}  catch (BusinessServiceException e) {
			processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
            processResp.setMessage("Exception thrown deleting service order document");
		}
		
		return processResp;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteServiceOrderDocuments(java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	public ProcessResponse deleteServiceOrderDocuments(List<Integer> documentIds,
			Integer roleId, Integer userId) {
		
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		ProcessResponse tempResp;
		List<String> errorMessages = new ArrayList<String>();
		boolean errorOccured = false;
		
		for (Integer docId : documentIds){
			tempResp = deleteServiceOrderDocument (docId, roleId, null, userId);
			
			if (!StringUtils.equals(tempResp.getCode(), ServiceConstants.VALID_RC)) {
				errorOccured = true;
				for (String message : tempResp.getMessages()) {
					errorMessages.add(message);
				}
			}
		}
		
		if (errorOccured) {
			processResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			processResp.setMessages(errorMessages);
		}
		
		return processResp;
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveDocumentsByServiceOrderId(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveDocumentsByServiceOrderId(String soId,
			Integer roleId, Integer userId) throws BusinessServiceException{
		
		List<DocumentVO> toReturn;
		try {
			toReturn = applyServiceOrderVisibilityRules(soId, this.getDocumentsByEntity(soId, Constants.DocumentTypes.SERVICEORDER), roleId, userId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Error retrieving documents by service order id", e);
		}
		
		return toReturn;
	}

	public List<DocumentVO> retrieveDocumentsMetaDataByServiceOrderId(String soId,
			  Integer roleId, Integer userId,String docSource) throws BusinessServiceException{
		
		List<DocumentVO> toReturn;
		try {
			 toReturn = applyServiceOrderVisibilityRules(soId, this.getDocumentsMetaDataByEntity(soId, Constants.DocumentTypes.SERVICEORDER,docSource), roleId, userId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Error retrieving documents by service order id", e);
		}
		
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveDocumentsByBuyerId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveDocumentsByBuyerId(Integer buyerID,
			Integer roleId, Integer userId) throws BusinessServiceException {
		
		return null;
	}
	
	public List<DocumentVO> retrieveDocumentsMetaDataByBuyerId(Integer buyerID)throws BusinessServiceException{
		List<DocumentVO> documentList = null;
		try {
			documentList = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).retrieveDocumentsMetaDataByBuyerId(buyerID);
		} catch (DataServiceException e) {
			logger.error("Exception in retrieving buyer documents ");
			throw new BusinessServiceException("Error retrieving documents by buyer id", e);
		}
		return documentList;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveDocumentsByVendorId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveDocumentsByVendorId(Integer vendorId,
			Integer roleId, Integer userId) throws BusinessServiceException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveResourceDocumentByDocumentId(java.lang.Integer)
	 */
	public DocumentVO retrieveResourceDocumentByDocumentId(Integer documentId)
			throws BusinessServiceException {
		
		DocumentVO myDocumentVO = new DocumentVO();
		try{
			myDocumentVO =  documentDaoFactory.getDocumentDao(Constants.DocumentTypes.RESOURCE)
													.retrieveDocumentByDocumentId(documentId);
		}catch (DataServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveResourceDocumentByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveResourceDocumentByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return myDocumentVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveServiceOrderDocumentByDocumentId(java.lang.Integer)
	 */
	public DocumentVO retrieveServiceOrderDocumentByDocumentId(
			Integer documentId) throws BusinessServiceException {
	
		DocumentVO documentVO = null;
		try {
			documentVO = getDocumentByDocumentId (documentId, Constants.DocumentTypes.SERVICEORDER);
		} catch (DataServiceException e) {
			logger.error("[DataServiceException] " + e);                
            throw new BusinessServiceException("Error retrieving service order document by document id", e);
		}
		
		return documentVO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveServiceOrderDocumentByDocumentId(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public DocumentVO retrieveServiceOrderDocumentByDocumentId(
			Integer documentId, String soId, Integer roleId,
			Integer requestingUserId,Integer requestingUserRoleId) throws BusinessServiceException {
		
		DocumentVO documentVO = null;
		boolean userIsAssociated = false;
		
		try {
			IServiceOrderBO serviceOrderBO = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx()
			.getBean(Constants.ApplicationContextBeans.SERVICE_ORDER_BO_BEAN);
			
			// make sure user requesting document is associated with service order
			ServiceOrder serviceOrderVO = serviceOrderDao.getServiceOrder(soId);
			if (null != serviceOrderVO) {
				if (roleId.intValue() == OrderConstants.BUYER_ROLEID ||
					roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
					userIsAssociated = serviceOrderBO.isBuyerAssociatedToServiceOrder(serviceOrderVO, requestingUserId);
				} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
					userIsAssociated = serviceOrderBO.isVendorAssociatedToServiceOrder(serviceOrderVO, requestingUserId);
				} else if(OrderConstants.NEWCO_ADMIN_ROLEID == roleId.intValue()){
					if (OrderConstants.BUYER_ROLEID == requestingUserRoleId.intValue() ||
							OrderConstants.SIMPLE_BUYER_ROLEID == requestingUserRoleId.intValue()) {
							userIsAssociated = serviceOrderBO.isBuyerAssociatedToServiceOrder(serviceOrderVO, requestingUserId);
						} else if (OrderConstants.PROVIDER_ROLEID == requestingUserRoleId.intValue()) {
							userIsAssociated = serviceOrderBO.isVendorAssociatedToServiceOrder(serviceOrderVO, requestingUserId);
						}
				}
			}
			
			// SL-11290 , If the order is a bulletin board order, there is no specific user( provider ) associated, with it.
			if (userIsAssociated || ( serviceOrderVO.getPriceModel()!=null && serviceOrderVO.getPriceModel().equals("BULLETIN")) ) {
				documentVO = getDocumentByDocumentIdAndSOId(documentId, soId, Constants.DocumentTypes.SERVICEORDER);
				
				if (null != documentVO) {
					List<DocumentVO> tempList = new ArrayList<DocumentVO>();
					tempList.add(documentVO);
					List<DocumentVO> resultList = this.applyServiceOrderVisibilityRules(soId, tempList, roleId, requestingUserId,requestingUserRoleId);
					
					if (!resultList.isEmpty()) {
						documentVO = (DocumentVO)resultList.get(0);
					} else {
						documentVO = null;
					}
				}
			}
		} catch (DataServiceException e) {
			logger.error("[DataServiceException] " + e);                
            throw new BusinessServiceException("Error retrieving service order document by document id", e);
		}
		
		return documentVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveVendorDocumentByDocumentId(java.lang.Integer)
	 */
	public DocumentVO retrieveVendorDocumentByDocumentId(Integer documentId)
			throws BusinessServiceException {
		DocumentVO myDocumentVO = new DocumentVO();
		try{
			myDocumentVO =  documentDaoFactory.getDocumentDao(Constants.DocumentTypes.VENDOR)
													.retrieveDocumentByDocumentId(documentId);
		}catch (DataServiceException e) {
			logger.error("Exception thrown inserting vendor document", e);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveDocumentByDocumentId Exception Message "
					+ e.getMessage());
		}catch(Exception a_Ex)
		{
			logger.error("Exception thrown inserting vendor document", a_Ex);
			throw new BusinessServiceException(" Error Caused @DocumentBO.retrieveDocumentByDocumentId Exception Message "
					+ a_Ex.getMessage());
		}
		return myDocumentVO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveBuyerDocumentByDocumentId(java.lang.Integer)
	 */
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)throws BusinessServiceException {
		//return getDocumentsByEntity();
		DocumentVO documentVO = retrieveBuyerDocumentByDocumentId(documentId, Constants.DocumentTypes.BUYER);
		return documentVO;
	}
	
	/**
	 * @param documentId
	 * @param documentType
	 * @return
	 * @throws DataServiceException
	 */
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId, int documentType) throws BusinessServiceException {
		
		DocumentVO documentVO = null;
		
		try {
			documentVO = documentDaoFactory.getDocumentDao(documentType).retrieveBuyerDocumentByDocumentId(documentId);
		} catch (DataServiceException e) {
			logger.error("[DataServiceException] " + e);                
            throw new BusinessServiceException("Error retrieving buyer document by document id", e);
		}
		
		return documentVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveDocumentsByEntityIdAndDocumentId(java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveDocumentsByEntityIdAndDocumentId(Integer entityId, Integer documentId) throws BusinessServiceException{
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveBuyerDocumentsByBuyerIdAndCategory(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveBuyerDocumentsByBuyerIdAndCategory(
			Integer buyerId, Integer categoryId, Integer roleId, Integer userId) throws BusinessServiceException {
		
		List<DocumentVO> toReturn = new ArrayList<DocumentVO>();
		
		try {
			toReturn = getDocumentsByEntityAndCategory(buyerId, categoryId,  Constants.DocumentTypes.BUYER);
		} catch (DataServiceException e) {
			logger.error("[DataServiceException] " + e);                
            throw new BusinessServiceException("Error retrieving buyer documents by buyer id and category id", e);
		}
		
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveServiceOrderDocumentsBySOIdAndCategory(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveServiceOrderDocumentsBySOIdAndCategory(
			String soId, Integer categoryId, Integer roleId, Integer userId) throws BusinessServiceException {
		
		List<DocumentVO> toReturn = new ArrayList<DocumentVO>();
		
		try {
			toReturn = applyServiceOrderVisibilityRules(soId, getDocumentsByEntityAndCategory(soId, categoryId, Constants.DocumentTypes.SERVICEORDER), roleId, userId);
		} catch (DataServiceException e) {
			logger.error("[DataServiceException] " + e);                
            throw new BusinessServiceException("Error retrieving service order documents by SO id and category id", e);
		}
		
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteTemporarySimpleBuyerDocument(java.lang.String, java.lang.Integer)
	 */
	public void deleteTemporarySimpleBuyerDocument(String simpleBuyerId,
			Integer docId) throws BusinessServiceException {
		getSimpleDocumentDao().deleteTemporarySimpleBuyerDocument(simpleBuyerId,docId);

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#deleteTemporarySimpleBuyerDocuments(java.lang.String)
	 */
	public void deleteTemporarySimpleBuyerDocuments(String simpleBuyerId)
			throws BusinessServiceException {
		getSimpleDocumentDao().deleteTemporarySimpleBuyerDocuments(simpleBuyerId);

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#insertTemporarySimpleBuyerServiceOrderDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertTemporarySimpleBuyerServiceOrderDocument(
			DocumentVO documentVO) throws DataServiceException {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		// TODO check for allowed format
		boolean allowedFormat = false;
		if(documentVO.getDocCategoryId()!=null &&
				 (Constants.DocumentTypes.CATEGORY.REFERENCE)== documentVO.getDocCategoryId().intValue()){
			allowedFormat = getSimpleDocumentDao().isAllowedFormat(documentVO.getFormat());
		}else if (documentVO.getDocCategoryId()!=null &&
				 (Constants.DocumentTypes.CATEGORY.IMAGE)== documentVO.getDocCategoryId().intValue()){
			allowedFormat = getSimpleDocumentDao().isAllowedImageFormat(documentVO.getFormat());
		}
		
		if (allowedFormat) {
			processResp = getSimpleDocumentDao().insertTemporarySimpleBuyerServiceOrderDocument(documentVO);
		} else {
			processResp.setCode(OrderConstants.SO_DOC_INVALID_FORMAT);
		}
		
		return processResp;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#persistSimpleBuyerDocuments(java.lang.String, java.lang.String)
	 */
	public void persistSimpleBuyerDocuments(String simpleBuyerId, String soId, Integer entityId)
			throws BusinessServiceException {
		getSimpleDocumentDao().persistSimpleBuyerDocuments(simpleBuyerId,soId, entityId);

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(java.lang.String, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(
			String simpleBuyerId, Integer categoryId)
			throws BusinessServiceException {
		List<DocumentVO> docList = getSimpleDocumentDao().retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(
				simpleBuyerId, categoryId);
		return docList;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveTemporaryDocumentsMetaDataBySimpleBuyerIdAndCategory(java.lang.String, java.lang.Integer)
	 */
	public List<DocumentVO> retrieveTemporaryDocumentsMetaDataBySimpleBuyerIdAndCategory(
			String simpleBuyerId, Integer categoryId)
			throws BusinessServiceException {
		getSimpleDocumentDao().retrieveTemporaryDocumentsMetaDataBySimpleBuyerIdAndCategory(
				simpleBuyerId,categoryId);

		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#retrieveTemporarySimpleBuyerSODocumentByDocumentId(java.lang.Integer, java.lang.String)
	 */
	public DocumentVO retrieveTemporarySimpleBuyerSODocumentByDocumentId(
			Integer documentId, String simpleBuyerId)
			throws BusinessServiceException {
		DocumentVO doc = getSimpleDocumentDao().retrieveTemporarySimpleBuyerSODocumentByDocumentId(
				documentId, simpleBuyerId);
		return doc;
	}

	/**
	 * This is an utility method that is used by all document types for insertion
	 * @param documentVO
	 * @param documentType
	 * @return
	 * @throws DataServiceException
	 */
	protected ProcessResponse insertDocument(DocumentVO documentVO, int documentType) throws DataServiceException {
		ProcessResponse pr = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		boolean  isAllowedDocumentFormat =false;
		String responseErrorCode = null;
        logger.info("Inside insertDocument of Document BO");
		try
		{	
			IDocumentDao dao = documentDaoFactory.getDocumentDao(documentType);
			//code to store video id
			if(null != documentVO.getDocCategoryId() && Constants.DocumentTypes.CATEGORY.VIDEO == documentVO.getDocCategoryId().intValue()){
				pr = dao.uploadDocument(documentVO);
			}else{
			
				Integer docCategoryId = documentVO.getDocCategoryId();
				String fileName=documentVO.getFileName();
				String fileExtenstion=STRING_EMPTY;
				if(null!=fileName && !(OrderConstants.EMPTY_STR.equals(fileName.trim()))){
					fileExtenstion = fileName.substring(fileName.lastIndexOf("."),fileName.length());
					if(null == documentVO.getFormat()){
						documentVO.setFormat(dao.getFormatByExtension(fileExtenstion));
					}
				}
				//Validating document types
				if (documentType == Constants.DocumentTypes.BUYER && null != docCategoryId
						&& docCategoryId == Constants.BuyerAdmin.LOGO_DOC_CATEGORY_ID) {
					isAllowedDocumentFormat = this.isAllowedLogoDocumentFormat(documentVO.getFormat());
					responseErrorCode = OrderConstants.LOGO_DOC_INVALID_FORMAT;
				} else {
					if(documentVO.isAutocloseOn()&& documentVO.getRoleId().equals(OrderConstants.PROVIDER_ROLEID))
					{
					isAllowedDocumentFormat=dao.isAllowedExtensionForSearsBuyer(fileExtenstion);
					responseErrorCode = OrderConstants.SO_DOC_INVALID_FORMAT_SEARS_BUYER;
					}
					else
					{
					isAllowedDocumentFormat = dao.isAllowedExtension(fileExtenstion);
					responseErrorCode = OrderConstants.SO_DOC_INVALID_FORMAT;
					}
				}
				
				//Insert if valid document type
				if (isAllowedDocumentFormat) {
					pr = dao.uploadDocument(documentVO);
					
					logger.info("document upload role id : " + documentVO.getRoleId() + " platform indicator : " + documentVO.getPlatformIndicatior());
					
					// relay notification
					if (!((null == documentVO.getRoleId() || documentVO.getRoleId().intValue() == OrderConstants.BUYER_ROLEID || documentVO.getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) 
							&& "PublicAPI".equals(documentVO.getPlatformIndicatior())) && dao instanceof ServiceOrderDocumentDao && null != docCategoryId && 
							(docCategoryId == Constants.DocumentTypes.CATEGORY.IMAGE || docCategoryId == Constants.DocumentTypes.CATEGORY.REFERENCE)) {
						sendRelayNotification((Integer )pr.getObj(), documentVO.getFileName(), documentVO.getRoleId(), documentVO.getSoId(), DOCUMENT_INSERT);
					}
				} else {
					pr.setCode(responseErrorCode);
				}
			}	
		}catch (DataServiceException e) {
			logger.error("[DataServiceException] While Inserting Document Details: ", e);                
            throw new DataServiceException("Exception: While Inserting Document Details", e);
		}
		
		return pr;	
	}	
	
	/**
	 * helper method to send relay webhook for SO related document upload
	 * @param soId 
	 * @param documentId 
	 * @param roleId 
	 * 
	 * @param documentVO
	 */
	private void sendRelayNotification(Integer documentId, String filename, Integer roleId, String soId, String crudOperation) {
		try {
			// SL-21587 - webhook event for relay buyer
			Integer buyerId = relayNotificationService.getBuyerId(soId);
			boolean relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, soId);
			
			logger.info("sending relay notification: "+ relayServicesNotifyFlag + " for soid: " +  soId);
			
			if (relayServicesNotifyFlag) {
				Map<String, String> params = new HashMap<String, String>();

				params.put("crudevent", null != crudOperation ? crudOperation : STRING_EMPTY);
				params.put("document", null != filename ? filename : STRING_EMPTY);
				params.put("roleid", null != roleId ? roleId.toString() : STRING_EMPTY);
				if (null != documentId) {
					params.put("documentid", documentId.toString());
					logger.info("document id : " + documentId);
				}

				relayNotificationService.sentNotificationRelayServices("document_changed_for_so", soId, params);
			}
		} catch (Exception e) {
			logger.error(" exception in invoking webhook event for document upload: " + e);
		}
	}

	/**
	 * This is an utility method that is used by all document types for deletion
	 * @param documentId
	 * @param documentType
	 * @throws DataServiceException
	 */
	protected void deleteDocument(Integer documentId, int documentType)throws DataServiceException{
		documentDaoFactory.getDocumentDao(documentType).removeDocument(documentId);
	}
	/**
	 * This is an utility method used to delete doc from so_document
	 * @param documentId
	 * @param documentType
	 * @throws DataServiceException
	 */
	protected void deleteDoc(Integer documentId, int documentType, String soId) throws DataServiceException {
		documentDaoFactory.getDocumentDao(documentType).removeDoc(documentId, soId);
		// relay notification
		DocumentVO document = getDocumentByDocumentIdAndSOId(documentId, soId, Constants.DocumentTypes.SERVICEORDER);
		if (null != document.getDocCategoryId()
				&& (document.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.IMAGE 
					|| document.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.REFERENCE)) {
			sendRelayNotification(documentId, document.getFileName(), document.getRoleId(), soId, DOCUMENT_DELETED);
		}
	}
	
	protected void deleteDocAll(String soId)throws DataServiceException{
		documentDaoFactory.getDocumentDao(Constants.DocumentTypes.SERVICEORDER).removeDocAll(soId);
	}
	/**
	 * @param documentId
	 * @param documentType
	 * @return
	 * @throws DataServiceException
	 */
	protected DocumentVO getDocumentByDocumentId (Integer documentId, int documentType) throws DataServiceException {
		return documentDaoFactory.getDocumentDao(documentType).retrieveDocumentByDocumentId(documentId);
	}
	
	/**
	 * Retrieves the document based on given documentId and soId
	 * @param documentId
	 * @param soId
	 * @param documentType
	 * @return
	 * @throws DataServiceException
	 */
	protected DocumentVO getDocumentByDocumentIdAndSOId(Integer documentId, String soId, int documentType) throws DataServiceException {
		return documentDaoFactory.getDocumentDao(documentType).retrieveDocumentByDocumentIdAndSOId(documentId, soId);
	}
	
	/**
	 * @param ownerId
	 * @param documentType
	 * @return
	 * @throws DataServiceException
	 */
	protected List<DocumentVO> getDocumentsByEntity(Object ownerId, int documentType) throws DataServiceException {
		return documentDaoFactory.getDocumentDao(documentType).getDocumentsByEntity(ownerId);
	}
	
	/**
	 * @param ownerId
	 * @param documentType
	 * @param docSource
	 * @return
	 * @throws DataServiceException
	 */
	protected List<DocumentVO> getDocumentsMetaDataByEntity(Object ownerId, int documentType,String docSource) throws DataServiceException {
		 return documentDaoFactory.getDocumentDao(documentType).getDocumentsMetaDataByEntity(ownerId,docSource);
	}

	/**
	 * @param ownerId
	 * @param categoryId
	 * @param documentType
	 * @return
	 * @throws DataServiceException
	 */
	protected List<DocumentVO> getDocumentsByEntityAndCategory(Object ownerId, Integer categoryId, int documentType) throws DataServiceException {
		return documentDaoFactory.getDocumentDao(documentType).getDocumentsByEntityAndCategory(ownerId, categoryId);
	}
	
	/**
	 * @param documentId
	 * @param documentType
	 * @return
	 */
	protected boolean checkBuyerDocByDocumentId (Integer documentId, int documentType){
		return documentDaoFactory.getDocumentDao(documentType).checkBuyerDocByDocumentId(documentId);
	}
	
	/**
	 * applyServiceOrderVisibilityRules applies the document visibility rules and removes the documents from the list that fail
	 * Added : applies the document visibility rules for admin
	 * @param soId
	 * @param list
	 * @param roleId
	 * @param requestingUserId
	 * @param requestingUserRoleId
	 */
	protected List<DocumentVO> applyServiceOrderVisibilityRules (String soId, List<DocumentVO> list, Integer roleId, Integer requestingUserId,Integer requestingUserRoleId) 
		throws DataServiceException{
		
		List<DocumentVO> listToReturn = new ArrayList<DocumentVO>();
		
		// Buyers get to see all documents all the time
		// Added: Admin to see all documents when viewing buyer/provider details
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID ||
			roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID||(roleId.intValue() == OrderConstants.NEWCO_ADMIN_ROLEID &&(OrderConstants.BUYER_ROLEID == requestingUserRoleId.intValue() ||
					OrderConstants.SIMPLE_BUYER_ROLEID == requestingUserRoleId.intValue()))) {
			listToReturn =  list;
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID||(roleId.intValue() == OrderConstants.NEWCO_ADMIN_ROLEID && OrderConstants.PROVIDER_ROLEID == requestingUserRoleId.intValue() )) {
			// get service order state
			ServiceOrder serviceOrderVO = serviceOrderDao.getServiceOrder(soId);
			
			switch (serviceOrderVO.getWfStateId()) {
				case OrderConstants.ACCEPTED_STATUS:
				case OrderConstants.ACTIVE_STATUS:
				case OrderConstants.COMPLETED_STATUS:
				case OrderConstants.PROBLEM_STATUS:
				case OrderConstants.CLOSED_STATUS:
				case OrderConstants.ROUTED_STATUS:
					listToReturn = list;
					break;
				default:
					// Provider is only allowed to view images
					List<DocumentVO> newList = list;
					for(DocumentVO docVO : newList){
						if (docVO.getDocCategoryId().intValue() == Constants.DocumentTypes.CATEGORY.IMAGE){
							listToReturn.add(docVO);
						}
					}
					break;
			}
		} else {
			list.clear();
			listToReturn =  list;
		}
		
		return listToReturn;
	}
	/**
	 * applyServiceOrderVisibilityRules applies the document visibility rules and removes the documents from the list that fail
	 * @param soId
	 * @param list
	 * @param roleId
	 * @param requestingUserId
	 */
	protected List<DocumentVO> applyServiceOrderVisibilityRules (String soId, List<DocumentVO> list, Integer roleId, Integer requestingUserId) 
		throws DataServiceException{
		
		List<DocumentVO> listToReturn = new ArrayList<DocumentVO>();
		
		// Buyers get to see all documents all the time		
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID ||
			roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			listToReturn =  list;
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			// get service order state
			ServiceOrder serviceOrderVO = serviceOrderDao.getServiceOrder(soId);
			if (serviceOrderVO != null) {
			switch (serviceOrderVO.getWfStateId()) {
				case OrderConstants.ACCEPTED_STATUS:
				case OrderConstants.ACTIVE_STATUS:
				case OrderConstants.COMPLETED_STATUS:
				case OrderConstants.PROBLEM_STATUS:
				case OrderConstants.CLOSED_STATUS:
				case OrderConstants.ROUTED_STATUS:
					listToReturn = list;
					break;
				default:
					// Provider is only allowed to view images
					List<DocumentVO> newList = list;
					for(DocumentVO docVO : newList){
						if (docVO.getDocCategoryId().intValue() == Constants.DocumentTypes.CATEGORY.IMAGE){
							listToReturn.add(docVO);
						}
					}
					break;
			}
			}
		} else {
			list.clear();
			listToReturn =  list;
		}
		
		return listToReturn;
	}
	public DocumentVO retrieveDocumentByTitleAndEntityID(int documentType, String title, Integer ownerID) throws BusinessServiceException {
		
		DocumentVO toReturn = new DocumentVO();
		
		try {
			toReturn = documentDaoFactory.getDocumentDao(documentType).retrieveDocumentByTitleAndEntityID(title, ownerID);
		} 
		catch (DataServiceException e) {
			logger.error("[DataServiceException].retrieveDocumentByTitleAndOwnerID " + e);                
            throw new BusinessServiceException("Error retrieving buyer documents by owner id and title. DocumentType:" + Integer.toString(documentType), e);
		}
		
		return toReturn;
	}
	
	/**
	 * Retrieve all documents with the same title for the buyer
	 * @param documentType
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByTitleAndEntityID
		(int documentType, String title, Integer ownerID) throws BusinessServiceException {		
		List<DocumentVO> toReturn = new ArrayList<DocumentVO>();
		try {
			toReturn = documentDaoFactory.getDocumentDao(documentType).
				retrieveDocumentsByTitleAndEntityID(title, ownerID);
		} 
		catch (DataServiceException e) {
			logger.error("[DataServiceException].retrieveDocumentByTitleAndOwnerID " + e);                
            throw new BusinessServiceException("Error retrieving buyer documents " +
            		"by owner id and title. DocumentType:" + Integer.toString(documentType), e);
		}
		
		return toReturn;
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#insertBuyerDocument(com.newco.marketplace.dto.vo.DocumentVO)
	 */
	public ProcessResponse insertSPNDocument(DocumentVO documentVO) throws DataServiceException  {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);

		try {
			processResp = insertDocument(documentVO,Constants.DocumentTypes.SPN);
		} catch (DataServiceException e) {
			logger.error("[DocumentBO.insertBuyerDocument] " + e);                
            throw new DataServiceException("Exception: While Inserting Buyer Document Details", e);
		} 

		return processResp;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#isAllowedImageFormat(java.lang.String)
	 */
	public boolean isAllowedImageFormat(String format)
			throws BusinessServiceException {
		try {
			 return getSimpleDocumentDao().isAllowedImageFormat(format);
		}catch(DataServiceException e) {
			logger.error("[DocumentBO.isAllowedImageFormat] " + e);                
            throw new BusinessServiceException("Exception: While Inserting Buyer Document Details", e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.document.IDocumentBO#isTotalImageSizeAllowed(java.lang.Long)
	 */
	public boolean isTotalImageSizeAllowed(Long totalSize)
			throws BusinessServiceException {

		try {
			return getSimpleDocumentDao().isTotalImageSizeAllowed(totalSize);
		} catch (DataServiceException e) {
			logger.error("[DocumentBO.isTotalImageSizeAllowed] " + e);                
            throw new BusinessServiceException("Exception: checking max file size ", e);
		}
	}
	
	/**
	 * retrieveLogoForBuyer returns one Logo document for the given Buyer
	 * @param buyerId
	 * @param categoryId
	 * @return logoDocumentId
	 * @throws BusinessServiceException
	 */
	public Integer retrieveLogoForBuyer(Integer buyerId, Integer categoryId) throws BusinessServiceException{
		Integer logoDocumentId = 0;
		try{
			 logoDocumentId = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).getLogoForBuyer(buyerId, categoryId);
		}catch(DataServiceException e) {
			logger.error("Exception in retrieveBuyerLogoDocumentByBuyerIdAndCategory" + e);                
            throw new BusinessServiceException("Exception: While retrieving Logo document id ", e);
		}
		return logoDocumentId;
		
	}

	
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	
	public void setDocumentDaoFactory(DocumentDaoFactory documentDaoFactory) {
		this.documentDaoFactory = documentDaoFactory;
	}

	public ISimpleDocumentDao getSimpleDocumentDao() {
		return simpleDocumentDao;
	}

	public void setSimpleDocumentDao(ISimpleDocumentDao simpleDocumentDao) {
		this.simpleDocumentDao = simpleDocumentDao;
	}
	/**
	 * This method provides the valid extensions from the DB
	 * @return String
	 * @throws DataServiceException
	 */
	public String getValidDisplayExtensions() {
		List<String>  validDisplayExtensions= new ArrayList<String>();
		String displayExtension =" ";
		try{
			validDisplayExtensions =simpleDocumentDao.getValidDisplayExtension();
			for( String extension : validDisplayExtensions){
				displayExtension =displayExtension+extension+"|";
			}
			displayExtension=displayExtension.substring(0,displayExtension.length()-1);
		} catch (DataServiceException e) {
			logger.error("[DocumentBO.getValidDisplayExtensions] " + e);                
        }
		return displayExtension;
	}
	
	/**
	 * @param documentType Integer
	 * @param fileName String
	 * @param ownerID Integer
	 * @return DocumentVO
	 * @throws BusinessServiceException
	 */
	public DocumentVO retrieveDocumentByFileNameAndEntityID(int documentType, String fileName, Integer ownerID) throws BusinessServiceException {
		logger.info("Inside retrieveDocumentByFileNameAndEntityID-->Start");
		DocumentVO documentVO = new DocumentVO();
		documentVO.setCompanyId(ownerID);
		documentVO.setFileName(fileName);
		
		try {			
			documentVO = documentDaoFactory.getDocumentDao(documentType).isAlreadyLoadedReturnLoaded(documentVO);
		} 
		catch (DataServiceException e) {
			logger.error("[DataServiceException].retrieveDocumentByTitleAndOwnerID " + e);                
            throw new BusinessServiceException("Error retrieving buyer documents by owner id and FileName. DocumentType:" + Integer.toString(documentType), e);
		}
		
		return documentVO;
	}
	/**
	 * retrieves document by given documentId and buyerId
	 * @param docId Integer
	 * @param buyerId Integer
	 * @return userDocId Integer
	 * @throws BusinessServiceException
	 */
	public Integer isDocExistsForUser(Integer docId, Integer buyerId) throws BusinessServiceException{
		Integer userDocId = null;
		try{
			int documentType = Constants.DocumentTypes.BUYER;
			userDocId = documentDaoFactory.getDocumentDao(documentType).isDocExistsForUser(docId,buyerId);
		}catch(DataServiceException e){
			 throw new BusinessServiceException("Error retrieving user documents in docExistForUser: ", e);
		}
		return userDocId;
	}

	/**
		 * @param documentType Integer
		 * @param DocumentVO documentVO
		 * @return DocumentVO
		 * @throws BusinessServiceException
		 */
		public DocumentVO retrieveDocumentByFileNameAndSoID(int documentType,DocumentVO documentVO) throws BusinessServiceException {
			logger.info("Inside retrieveDocumentByFileNameAndEntityID-->Start");
			
			try {			
				documentVO = documentDaoFactory.getDocumentDao(documentType).retrieveDocumentByFileNameAndSoID(documentVO);
			} 
			catch (DataServiceException e) {
				logger.error("[DataServiceException].retrieveDocumentByTitleAndOwnerID " + e);                
	            throw new BusinessServiceException("Error retrieving buyer documents by owner id and FileName. DocumentType:" + Integer.toString(documentType), e);
			}
			
			return documentVO;
		}
		
		
	/**
	 * Retrieves documents and logos by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<DocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveBuyerDocumentsAndLogosByBuyerId(Integer buyerId) throws BusinessServiceException {
		List<DocumentVO> buyerDocList = null;			
		try {		
			buyerDocList = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).getDocumentsAndLogosByEntity(buyerId);
		} catch (DataServiceException dse) {            
			throw new BusinessServiceException("Error retrieving buyer documents and logo in DocumentBO.retrieveBuyerDocumentsAndLogosByBuyerId() ", dse);
		}			
		return buyerDocList;
	}	
	
	/**
	 * Retrieves document types by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<BuyerDocumentTypeVO>
	 * @throws BusinessServiceException
	 */
	public List<BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer buyerId,Integer source) throws BusinessServiceException {
		List<BuyerDocumentTypeVO> buyerDocList = null;			
		try {		
			buyerDocList = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).retrieveDocTypesByBuyerId(buyerId,source);
		} catch (DataServiceException dse) {            
			throw new BusinessServiceException("Error retrieving buyer documents and logo in DocumentBO.retrieveDocTypeByBuyerId() ", dse);
		}			
		return buyerDocList;
	}
	/**
	 * Retrieves document types count by buyer id, to be listed in document manager
	 * @param buyerId
	 * @return List<BuyerDocumentTypeVO>
	 * @throws BusinessServiceException
	 */
	public Integer retrieveDocTypesCountByBuyerId(Integer buyerId,Integer source) throws BusinessServiceException {
		Integer docCount = null;			
		try {		
			docCount = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).retrieveDocTypesCountByBuyerId(buyerId,source);
		} catch (DataServiceException dse) {            
			throw new BusinessServiceException("Error retrieving buyer documents and logo in DocumentBO.retrieveDocTypeByBuyerId() ", dse);
		}			
		return docCount;
	}
	 /**
	 * Adds document types entered by the buyer and displays it in the document manager, 
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws BusinessServiceException
	 */
	public void addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws BusinessServiceException
	{
		
		try {		
		 documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).addDocumentTypeDetailByBuyerId(buyerDocumentTypeVO);
		} catch (DataServiceException dse) {            
		throw new BusinessServiceException("Error retrieving buyer documents and logo in DocumentBO.retrieveDocTypeByBuyerId() ", dse);
		}			
	}
	 /**
	 * Removes document types selected by the buyer on the basis of buyerDocTypeId and 
	 * displays the remaining in the document manager, 
	 * @param buyerDocTypeId
	 * @return void
	 * @throws BusinessServiceException
     */
	public void deleteDocumentTypeDetailByBuyerId(Integer buyerDocTypeId) throws BusinessServiceException
	{
				
		try {		
		documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).deleteDocumentTypeDetailByBuyerId(buyerDocTypeId);
		} catch (DataServiceException dse) {            
			throw new BusinessServiceException("Error retrieving buyer documents and logo in DocumentBO.retrieveDocTypeByBuyerId() ", dse);
		}			

	}
	/**
	 * Checks if the format provided is an allowed logo document format.
	 * @param format
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	private boolean isAllowedLogoDocumentFormat(String format)throws DataServiceException {
			return documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).isAllowedLogoDocumentFormat(format);
	}

	/**
	 * This method updates docList in so template, when document is deleted from Document Manager
	 * @param buyerId
	 * @param docTitle
	 * @return 
	 * @throws BusinessServiceException
	 */
	private void updateDocListInBuyerSoTemplate(Integer buyerId,String docTitle) throws BusinessServiceException{	
		getBuyerSOTemplateBO().updateDocListInBuyerSoTemplate(buyerId,docTitle);
	}

	public IBuyerSOTemplateBO getBuyerSOTemplateBO() {
		return buyerSOTemplateBO;
	}

	public void setBuyerSOTemplateBO(IBuyerSOTemplateBO buyerSOTemplateBO) {
		this.buyerSOTemplateBO = buyerSOTemplateBO;
	}
	
	/**
	 * Retrieves reference documents by buyer id, to be listed in Create SO
	 * @param buyerId
	 * @param soId
	 * @return List<DocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveRefBuyerDocumentsByBuyerId(Integer buyerId, String soId) throws BusinessServiceException {
		List<DocumentVO> buyerDocList = null;			
		try {		
			buyerDocList = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.BUYER).getRefDocumentsByEntity(buyerId, soId);
		} catch (DataServiceException dse) {            
			throw new BusinessServiceException("Error retrieving buyer ref documents in DocumentBO.retrieveRefBuyerDocumentsByBuyerId() ", dse);
		}			
		return buyerDocList;
	}	
	
	public void insertSODocuments(SoDocumentVO soDocVO) {
		IDocumentDao dao = documentDaoFactory.getDocumentDao(Constants.DocumentTypes.SERVICEORDER);
		try {
			dao.uploadDocs(soDocVO);

			// relay notification
			DocumentVO document = getDocumentByDocumentIdAndSOId(soDocVO.getDocumentId(), soDocVO.getSoId(),
					Constants.DocumentTypes.SERVICEORDER);
			if (null != document.getDocCategoryId()
					&& (document.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.IMAGE
						|| document.getDocCategoryId() == Constants.DocumentTypes.CATEGORY.REFERENCE)) {
				sendRelayNotification(soDocVO.getDocumentId(), document.getFileName(), document.getRoleId(), soDocVO.getSoId(), DOCUMENT_INSERT);
			}
		} catch (DataServiceException e) {
			logger.error("Error in DocumentBO.insertSODocuments");
		}

	}
	
	public ProcessResponse deleteServiceOrderDocument(Integer documentId, Integer roleId, Integer userId, String soId) {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		try {
			//SL-17601:checking whether the document is in buyer_document table
			boolean exists = checkBuyerDocByDocumentId(documentId);
			if(exists){
				deleteDoc(documentId, Constants.DocumentTypes.SERVICEORDER, soId);				
			}		
			else{
				processResp = deleteServiceOrderDocument(documentId, roleId, "ProPortal", userId);
			}				
		}catch(Exception e){
			logger.debug("DocumentBO.deleteServiceOrderDocument"+e);
		}
		return processResp;
	}
	
	public ProcessResponse deleteServiceOrderDocumentforTask(String soId) {
		ProcessResponse processResp = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		try {
			//SL-17601:checking whether the document is in buyer_document table
			
			deleteDocAll(soId);
							
		}catch(Exception e){
			logger.debug("DocumentBO.deleteServiceOrderDocument"+e);
		}
		return processResp;
	}
	
	
	public boolean checkBuyerDocByDocumentId(Integer documentId){
	
		boolean exists = false;
		try {
			exists = checkBuyerDocByDocumentId (documentId, Constants.DocumentTypes.SERVICEORDER);
		} catch (Exception e) {
			logger.error("[Exception] " + e);                
		}
		return exists;
	}

	public boolean checkIfMobileSignatureDocumentExists(String soId)
			throws BusinessServiceException {
		try {
			Integer signatureCount = serviceOrderDao.getCountOfMobileSignatureDocuments(soId);
			if(null != signatureCount && signatureCount.intValue() > 0){
				return true;
			}
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		return false;
	}

	public Integer checkIfSignaturePDFDocumentExisits(String soId)
			throws BusinessServiceException {
		try {
			DocumentVO documentVO = new DocumentVO();
			documentVO.setSoId(soId);
			documentVO.setDescription(MPConstants.PDF_TITLE);
			documentVO.setTitle(MPConstants.PDF_TITLE);
			documentVO.setDocCategoryId(new Integer(Constants.DocumentTypes.CATEGORY.REFERENCE));
			
			return serviceOrderDao.getSignaturePDFDocument(documentVO);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}

	public void deleteSODocumentMapping(String soId, Integer documentId)
			throws DataServiceException {
		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId(soId);
		documentVO.setDocumentId(documentId);
		
		serviceOrderDao.deleteSODocumentMapping(documentVO);
	}

	public void updatePDFBatchParamaters(String soId)
			throws DataServiceException {
		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId(soId);
		documentVO.setPdfStatus(MPConstants.PDF_STATUS_WAITING);
		
		serviceOrderDao.updatePDFBatchParamaters(documentVO);
	}
	
	 
	/**
	 * validates if document logo is present in template
	 * @param buyerId
	 * @param title
	 * @return boolean
	 * @throws BusinessServiceException**/
	
	public boolean validateLogoPresent(Integer buyerId,String title)throws BusinessServiceException{
		List<BuyerSOTemplateVO> buyerSOTemplates = null;
		BuyerSOTemplateDTO buyerSOTemplateDTO = null;
		String documentLogo = null;
		boolean flag = false;
		try {
			if(null!=buyerId){
				//fetch templates from the table buyer so templates
				buyerSOTemplates = getTemplateDAO().loadBuyerSoTemplates(buyerId);
				if (null != buyerSOTemplates && !buyerSOTemplates.isEmpty()) {
					for (BuyerSOTemplateVO buyerSOTemplateVO : buyerSOTemplates) {
						if(null!=buyerSOTemplateVO){
							buyerSOTemplateDTO = getBuyerSOTemplateBO().getBuyerSOTemplateXMLAsDTO(buyerSOTemplateVO
											.getTemplateData());
							if (null != buyerSOTemplateDTO && null != buyerSOTemplateDTO.getDocumentLogo()) {
								documentLogo = buyerSOTemplateDTO.getDocumentLogo();
							}
							if (null != documentLogo && documentLogo.equals(title)) {
								flag = true;
								break;
							}
						}
					}
				}
			}
			
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(
					"Error in DocumentBO.validateLogoPresent() method",
					dse);
		}
		return flag;
	}
	
	public String getConstantValueFromDB(String appkey)
			throws BusinessServiceException {
		try{		
		return serviceOrderDao.getConstantValueFromDB(appkey);
		}catch (DataServiceException dse) {
			throw new BusinessServiceException(
					"Error in DocumentBO.getConstantValueFromDB() method",
					dse);
		}
		
	}

	public IBuyerSOTemplateDAO getTemplateDAO() {
		return templateDAO;
	}

	public void setTemplateDAO(IBuyerSOTemplateDAO templateDAO) {
		this.templateDAO = templateDAO;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
}
