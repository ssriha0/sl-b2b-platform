package com.newco.marketplace.persistence.daoImpl.document;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.constants.Constants;

import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerDocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentDeleteException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;
import com.newco.marketplace.exception.core.document.DocumentUpdateException;
import com.newco.marketplace.exception.core.document.ServiceOrderDocumentSizeExceededException;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.dao.impl.ABaseImplDao;
import com.sears.os.service.ServiceConstants;

public class  BuyerDocumentDaoImpl extends ABaseImplDao implements IDocumentDao {
	
	private static final Logger logger = Logger.getLogger(BuyerDocumentDaoImpl.class);
	
	private DocumentService documentService;
	
	public List<DocumentVO> getDocumentsByEntityAndCategory(Object entityId, Integer categoryId)
	throws DataServiceException {
		Integer buyerId = null;
		List<DocumentVO> result = null;
		try {
			buyerId = (Integer) entityId;		
			Integer document_id = categoryId;
	        HashMap<String, Integer> map = new HashMap<String, Integer>();
	        map.put("buyer_id", buyerId);
	        map.put("doc_category_id", document_id);
            result = queryForList("buyer.fetchDocumentList.query", map);
            result = documentService.retrieveDocumentsFromDBOrFS(result);
		}
        catch (Exception ex) {
        	logger.error("[BuyerDocumentDaoImpl.getDocumentsByEntityAndCategory - Exception] ", ex);
            throw new DataServiceException(ex.getMessage(), ex);
        }    
		return result;
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
	
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)
			throws DataServiceException {
		DocumentVO documentVO = null;
		try{
			//documentVO = documentService.retrieveDocumentByDocumentId(documentId, "so.document.query_by_document_id");
			documentVO = (DocumentVO) queryForObject("document.query_buyerdocument_by_document_id", documentId);
			documentVO = documentService.retrieveDocumentFromDBOrFS(documentVO);
		}catch (DataAccessException e) {
				throw new DocumentRetrievalException("Unable to retrieve document: " + documentId,e);
		}
		return documentVO;
	}
	
	
	public List<DocumentVO> retrieveDocumentsByType(int type, Integer entity_id)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ProcessResponse uploadDocument(DocumentVO documentVO)
			throws DataServiceException {
		ProcessResponse pr = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		boolean docNameExists = false, docTitleExists = false;
		
		//Check if the document with the same title already exists
		Integer entityId = documentVO.getEntityId();
		if(documentVO.isValidateOnBuyerId()){
			entityId = documentVO.getCompanyId();
		}
		List<DocumentVO> currentDocumentVOList = retrieveDocumentsByTitleAndEntityID(documentVO.getTitle(), 
				entityId);
		if(currentDocumentVOList != null && currentDocumentVOList.size()>0) {
			docTitleExists = true;
			pr.setCode(Constants.BuyerAdmin.DOC_WITH_TITLE_EXISTS);
			pr.setMessage("A document with the same name has already been uploaded.");
		}

		//Check if the document with the same name already exists
		if(!docTitleExists) {
			DocumentVO currentDocumentVO = isAlreadyLoadedReturnLoaded(documentVO);
			if(currentDocumentVO != null) {
				docNameExists = true;
				pr.setCode(Constants.BuyerAdmin.DOC_WITH_FILENAME_EXISTS);
				pr.setMessage("A document with the same name has already been uploaded.");
			}
		}
		if (!docTitleExists && !docNameExists) {
			try {
				insertDocument(documentVO);
				pr.setObj(documentVO.getDocumentId());
			} catch (DataServiceException dse) {
				logger.error("[BuyerDocumentDaoImpl.uploadDocument - Exception] ", dse);
	            throw new DataServiceException("Error executing: insertDocument", dse);
			}
		}
		
		return pr;
	}
	
	protected void insertDocument(DocumentVO newDocumentVO) 
		throws DataServiceException {
		newDocumentVO.setStoreInDatabase(false);
		try{
			/*determinePersistanceLocation(newDocumentVO, 
			 Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB );
			buildSavePath(newDocumentVO);*/
			
			//Code for resizing the image files to 800x600
			File file = null;
			if(StringUtils.contains(newDocumentVO.getFormat(), 
					Constants.ThumbNail.IMAGE_TYPE_STRING)){
				if(null != newDocumentVO.getBlobBytes() && 
						null == newDocumentVO.getDocument()){					
					file = new File(Constants.DOCUMENT_TEMPORARY_FOLDER +
							newDocumentVO.getFileName());
					FileUtils.writeByteArrayToFile(
							file,
							newDocumentVO.getBlobBytes());
					newDocumentVO.setDocument(file);					
				}
				byte[] imageBlobBytes = resizeIfRequired(
						newDocumentVO.getDocument());
				FileUtils.writeByteArrayToFile(
						newDocumentVO.getDocument(),
						 imageBlobBytes);
				newDocumentVO.setBlobBytes(imageBlobBytes);
				newDocumentVO.setDocSize(new Long(imageBlobBytes.length));				
			}
			//Code for resizing the image files to 800x600:end
			
			//Insert the document in Document table
			newDocumentVO = documentService.createDocument(newDocumentVO);
			
			//load so to doc cross reference table
			BuyerDocumentVO bDocVO = new BuyerDocumentVO();
			bDocVO.setDocumentId(newDocumentVO.getDocumentId());
			bDocVO.setBuyerId(newDocumentVO.getCompanyId());
			bDocVO.setCategoryId(newDocumentVO.getDocCategoryId());
			
			//insert a record in the buyer document table
			insert("buyer.document.insert", bDocVO);
			if(null != file){
				file.delete();
			}			
		} catch (DataAccessException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		} catch (ServiceOrderDocumentSizeExceededException e) {
			throw e;
		} catch (DataServiceException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		} catch (IOException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		}
	}
		
	public DocumentVO isAlreadyLoadedReturnLoaded (DocumentVO documentVO) throws DataServiceException{
		DocumentVO existingDocVO = null;
		DocumentVO returnDocVO = null;
		
		existingDocVO = (DocumentVO) queryForObject("buyer.document.query_already_exists", documentVO);
		
		if (null != existingDocVO ) {
			returnDocVO = (DocumentVO)queryForObject("buyer.document.query_metadata_by_document_id", existingDocVO.getDocumentId());
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
	

	public List<DocumentVO> retrieveDocumentsByDocumentIds(List<Integer> doc_ids)throws DataServiceException{
		return null;
	}
	
	public DocumentVO retrieveDocumentByDocumentId(Integer documentId)throws DataServiceException{
		DocumentVO documentVO = null;
		
		/*try {
			documentVO = (DocumentVO)queryForObject("buyer.document.query_by_document_id", document_id);
			determinePersistanceLocation(documentVO, Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB);
			buildSavePath(documentVO);
			
			if (!documentVO.isStoreInDatabase()) {
				// get from file system
				getDocumentFromFileSystem(documentVO);
			}
			
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrive document: " + document_id,e);
		}*/
		try {
			documentVO = documentService.retrieveDocumentByDocumentId(documentId, "so.document.query_by_document_id");	
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
	
	
	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO) throws DBException {
		return inDocumentVO;
	}
	
	
	public DocumentVO retrieveDocumentMetaDataByDocumentId(DocumentVO inDocumentVO) throws DataServiceException {
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
        	logger.error("[BuyerDocumentDaoImpl.retrieveDocumentByTitleAndOwnerID - Exception] for title: " + title + ", buyer id: " + buyerID, ex);
            throw new DataServiceException("Error executing: document.query_buyerdocument_by_title", ex);
        }                
            
        return documentVO;
	}
	/**
	 * Retrieve all documents with the same title for the buyer.
	 * @param documentType
	 * @param title
	 * @param ownerID
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<DocumentVO> retrieveDocumentsByTitleAndEntityID(String title, Integer buyerID) throws DataServiceException {
		List<DocumentVO> docsWithSameTitle = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("buyer_id", Integer.toString(buyerID.intValue()));
		try {
			docsWithSameTitle = (List<DocumentVO>) queryForList("document.query_buyerdocument_by_title", params);
			docsWithSameTitle = documentService.retrieveDocumentsFromDBOrFS(docsWithSameTitle);
        }
        catch (Exception ex) {
        	logger.error("[BuyerDocumentDaoImpl.retrieveDocumentByTitleAndOwnerID - Exception] for title: " + title + ", buyer id: " + buyerID, ex);
            throw new DataServiceException("Error executing: document.query_buyerdocument_by_title", ex);
        }                
            
        return docsWithSameTitle;
	}
	
	public List<DocumentVO> retrieveDocumentsMetaDataByBuyerId(Integer buyerID) throws DataServiceException {
		List<DocumentVO> documentVO = null;
		try {
			documentVO = (List<DocumentVO>) queryForList("document.query_buyerdocument_by_buyerId", buyerID);
			documentVO = documentService.retrieveDocumentsFromDBOrFS(documentVO);
        }
        catch (Exception ex) {
        	logger.error("[BuyerDocumentDaoImpl.retrieveDocumentByTitleAndOwnerID - Exception] ", ex);
            throw new DataServiceException("Error executing: document.query_buyerdocument_by_title", ex);
        }                
            
        return documentVO;
	}
	
	/**
	 * getLogoForBuyer returns one Logo document for the given buyer id and category.
	 * @param Buyer id
	 * @param categoryId category id documents belong to. In this case it will Logo category id
	 * @return logoDocumentId
	 * @throws DataServiceException
	 */
	
	public Integer getLogoForBuyer(Integer buyerId, Integer categoryId) throws DataServiceException{
		Integer logoDocumentId = 0;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
	    map.put("buyer_id", buyerId);
	    map.put("doc_category_id", categoryId);
	    logoDocumentId = (Integer)queryForObject("buyer.fetchLogoDocument.query", map);
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
	 * retrieves document by given documentId and buyerId
	 * @param docId Integer
	 * @param buyerId Integer
	 * @return userDocId Integer
	 * @throws DataServiceException
	 */
	public Integer isDocExistsForUser(Integer docId, Integer buyerId) throws DataServiceException{
		Integer userDocId = null;
		try{
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("docId", docId);
			map.put("buyerId", buyerId);
			userDocId = (Integer)queryForObject("document.query_document_exist_for_buyer", map);
		}catch(Exception e){
			 throw new DataServiceException("Error retrieving user documents in docExistForUser: ", e);
		}
		return userDocId;
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
	 * This method reduces the dimension of the image if it is
	 *  greater than the system specified standard
	 * @param File uploadedImage
	 * @return byte[]
	 * @throws Exception
	 */
	private byte[] resizeIfRequired(File uploadedImage) throws IOException {
		byte[] originalImageBytes = FileUtils.readFileToByteArray(uploadedImage);
		Image sourceImage = new ImageIcon(originalImageBytes).getImage();
		logger.debug("Checks if  Resize of image is Required");
		if (sourceImage.getWidth(null) > Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_WIDTH
				&& sourceImage.getHeight(null) > Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_HEIGHT) {
			logger.debug("Resize is Required");
			byte[] resizedImageBytes = DocumentUtils.resizeoImage(originalImageBytes,
					Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_WIDTH,
					Constants.IMAGE_COMPRESSION_CONSTANTS.IMAGE_HEIGHT);
			return resizedImageBytes;
		} else {
			logger.debug("Resizing is not required");
			return originalImageBytes;
		}
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
	 * Retrieves documents and logos by buyer id, to be listed in document manager
	 * @param ownerId
	 * @return List<DocumentVO>
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsAndLogosByEntity(Object entityId)throws DataServiceException {
		Integer buyerId = null;
		List<DocumentVO> buyerDocList = null;
		try {
			buyerId = (Integer) entityId;	
			buyerDocList = queryForList("fetchBuyerDocumentAndLogoList.query", buyerId);
			buyerDocList = documentService.retrieveDocumentsFromDBOrFS(buyerDocList);
		}
        catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
        }    
		return buyerDocList;
	}
	
	/**
	 * Checks if the format provided is an allowed logo document format.
	 * @param format
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean isAllowedLogoDocumentFormat(String format) throws DataServiceException {
		boolean isAllowed = false;
		try {
			Integer count = (Integer)queryForObject("logo.query_validate_format", format);
			if (count >= 1) {
				isAllowed = true;
			}
		}catch (Exception ex) {
			logger.error("Error while validating logo format in BuyerDocumentDaoImpl.isAllowedLogoFormat() ", ex);
			throw new DataServiceException("Error while validating logo format in BuyerDocumentDaoImpl.isAllowedLogoFormat() ", ex);
	    }    
		return isAllowed;
	}
	/**
	 * Retrieves document types  by buyer id, to be listed in document manager
	 * @param entityId,source
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DataServiceException
	*/
	public List<BuyerDocumentTypeVO> retrieveDocTypesByBuyerId(Integer entityId,Integer source)throws DataServiceException {
		Integer buyerId = null;
		List<BuyerDocumentTypeVO> buyerDocTypeList = null;
		try {
			buyerId =  entityId;	
			HashMap<String, Integer> map = new HashMap<String, Integer>();
		    map.put("buyerId", buyerId);
		    map.put("source", source);
			buyerDocTypeList = queryForList("document.fetchBuyerDocTypeList", map);
		}
        catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.retrieveDocTypesByBuyerId()", ex);
            throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.retrieveDocTypesByBuyerId() ", ex);
        }    
		return buyerDocTypeList;
	}
	/**
	 * Retrieves document types Count  by buyer id, to be listed in document manager
	 * @param entityId,source
	 * @return List<BuyerDocumentTypeVO>
	 * @throws DataServiceException
	*/
	public Integer retrieveDocTypesCountByBuyerId(Integer entityId,Integer source)throws DataServiceException {
		Integer buyerId = null;
		List<BuyerDocumentTypeVO> buyerDocTypeList = null;
		Integer docCount=null;
		try {
			buyerId =  entityId;	
			HashMap<String, Integer> map = new HashMap<String, Integer>();
		    map.put("buyerId", buyerId);
		    map.put("source", source);
		    docCount = (Integer)queryForObject("document.fetchBuyerDocTypeListCount", map);
		}
        catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.retrieveDocTypesCountByBuyerId()", ex);
            throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.retrieveDocTypesCountByBuyerId() ", ex);
        }    
		return docCount;
	}
	/**
	 * Inserts document types on the basis of buyerDocumentTypeVO
	 * @param buyerDocumentTypeVO
	 * @return void
	 * @throws DataServiceException
  */
		public void addDocumentTypeDetailByBuyerId(BuyerDocumentTypeVO buyerDocumentTypeVO) throws DataServiceException
	{
		try {
			insert("buyer.document.addDocType", buyerDocumentTypeVO);
		} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
        }
		
	}
	/**
	 * Deletes the  document types on the basis of buyerDocTypeId
	 * @param buyerDocTypeId
	 * @return void
	 * @throws DataServiceException
    */
	public void  deleteDocumentTypeDetailByBuyerId(Integer buyerDocTypeId) throws DataServiceException
	{
		try {
			update("buyer.document.deleteDocType", buyerDocTypeId);
		} catch (Exception ex) {
        	logger.error("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity()", ex);
            throw new DataServiceException("Error while retrieving buyer documents and logos in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
        }
	}

	/**
	 * Retrieves reference documents by buyer id, to be listed in Create SO
	 * @param ownerId
	 * @return List<DocumentVO>
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getRefDocumentsByEntity(Object entityId, String soId)throws DataServiceException {
		String buyerId = null;
		List<DocumentVO> buyerDocList = null;
		try {
			buyerId = entityId.toString();	
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("buyerId", buyerId);
			map.put("soId", soId);
			buyerDocList = queryForList("fetchBuyerRefDocumentList.query", map);
		}
        catch (Exception ex) {
        	logger.error("Error retrieving buyer ref documents in BuyerDocumentDaoImpl.getRefDocumentsByEntity()", ex);
            throw new DataServiceException("Error retrieving buyer ref documents in BuyerDocumentDaoImpl.getDocumentsAndLogosByEntity() ", ex);
        }    
		return buyerDocList;
	}
	
	public void uploadDocs(SoDocumentVO soDocVO) throws DataServiceException{
	
	}
	public boolean checkBuyerDocByDocumentId(Integer document_id){
		return false;
	}
	public void removeDoc(Integer documentId, String soId) throws DataServiceException{
		
	}
}
