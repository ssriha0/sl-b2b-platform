package com.newco.marketplace.persistence.daoImpl.document;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.apache.commons.io.FileUtils;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentDeleteException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;
import com.newco.marketplace.exception.core.document.DocumentUpdateException;
import com.newco.marketplace.exception.core.document.ServiceOrderDocumentSizeExceededException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.dao.impl.ABaseImplDao;
import com.sears.os.service.ServiceConstants;


public class ServiceOrderDocumentDao extends ABaseImplDao implements IDocumentDao{

	private Logger logger = Logger.getLogger(ServiceOrderDocumentDao.class);

	private ServiceOrderDao serviceOrderDao;
	private DocumentService documentService;
	private IApplicationPropertiesDao applicationPropertiesDao;

	public ProcessResponse uploadDocument(DocumentVO documentVO)
			throws DataServiceException {

		ProcessResponse pr = new ProcessResponse(ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		if (!isAlreadyLoaded(documentVO)) {
			try {
				logger.info("Calling actual insert document that insert it in the database");
                insertDocument(documentVO);
			    pr.setObj(documentVO.getDocumentId());
			} catch (ServiceOrderDocumentSizeExceededException e) {
                logger.info("Document has increased the size limitation not saving");
				pr.setCode(OrderConstants.SO_DOC_SIZE_EXCEEDED_RC);
				pr.setMessage(e.getMessage());
			}
		} else {
			pr.setCode(OrderConstants.SO_DOC_UPLOAD_EXSITS);
			logger.info("A file with that name has already been associated with this SO: " + documentVO.getSoId());
		}
		return pr;
	}


	public DocumentVO retrieveDocumentByDocumentId(Integer documentId) throws DataServiceException {
		DocumentVO documentVO = null;
		try {
			documentVO = documentService.retrieveDocumentByDocumentId(documentId, "so.document.query_by_document_id");
		}catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve document: " + documentId,e);
		}
		return documentVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.IDocumentDao#retrieveDocumentByDocumentIdAndSOId(java.lang.Integer, java.lang.String)
	 */
	public DocumentVO retrieveDocumentByDocumentIdAndSOId(Integer documentId, String soId) throws DataServiceException {
		DocumentVO documentVO = null;
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("soId", soId);
		queryParamMap.put("documentId", documentId);
		try {
			documentVO = (DocumentVO)queryForObject("so.document.query_by_document_id_so_id", queryParamMap);
			documentVO = documentService.retrieveDocumentFromDBOrFS(documentVO);
		} catch (Exception ex) {
			String msg = "Unexpected error while fetching document details for goven soId + " + soId + " documentId = " + documentId;
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return documentVO;
	}

	public List<DocumentVO> retrieveDocumentsByDocumentIds(List<Integer> doc_ids)
			throws DataServiceException {

		List<DocumentVO> returnList = new ArrayList<DocumentVO> ();

		for (Integer docId : doc_ids) {
			returnList.add(retrieveDocumentByDocumentId(docId));
		}

		return returnList;
	}


	public List<DocumentVO> retrieveDocumentsByType(int type, Integer entity_id)
			throws DataServiceException {

		return null;
	}
	
	//SL-21233: Document Retrieval Code Starts
	
	public DocumentVO retrieveDocumentsInfoByDocumentId(DocumentVO inDocumentVO) throws DataServiceException {
		return null;
	}
	
	//SL-21233: Document Retrieval Code Ends


	public List<DocumentVO> getDocumentsByEntity(Object ownerId) throws DataServiceException {
		List<DocumentVO> toReturn;
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			String soId = (String)ownerId;
	        map.put("so_id", soId);
	        map.put("file_name", Constants.ThumbNail.THUMBNAIL_SUFFIX);
			toReturn = queryForList("so.document.query_by_so_id", map);
			toReturn = documentService.retrieveDocumentsFromDBOrFS(toReturn);
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve documents for: " + ownerId,e);
		}
		if (null == toReturn) {
			toReturn = new ArrayList<DocumentVO>();
		}
		return toReturn;
	}


	 public List<DocumentVO> getDocumentsMetaDataByEntity(Object ownerId,String docSource) throws DataServiceException {
		List<DocumentVO> toReturn;
		DocumentVO documentVO = new DocumentVO();
		try {
			documentVO.setSoId((String)ownerId);
			documentVO.setDocSource(docSource);
			documentVO.setFileName(Constants.ThumbNail.THUMBNAIL_SUFFIX);
			toReturn = queryForList("so.document.query_metadata_by_so_id", documentVO);
			toReturn = documentService.retrieveDocumentsFromDBOrFS(toReturn);
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve documents for: " + ownerId,e);
		}
		if (null == toReturn) {
			toReturn = new ArrayList<DocumentVO>();
		}
		return toReturn;
	}


	public List<DocumentVO> getDocumentsByEntityAndCategory(Object ownerId, Integer categoryId)
			throws DataServiceException {
		DocumentVO documentVO = new DocumentVO();
		documentVO.setSoId((String)ownerId);
		documentVO.setDocCategoryId(categoryId);
		documentVO.setFileName(Constants.ThumbNail.THUMBNAIL_SUFFIX);
		List<DocumentVO> toReturn;
		try {
			toReturn = queryForList("so.document.query_by_so_id_and_category",documentVO);
			toReturn = documentService.retrieveDocumentsFromDBOrFS(toReturn);
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrive documents for: " + ownerId + " category: " + categoryId,e);
		}

		if (null == toReturn) {
			toReturn = new ArrayList<DocumentVO>();
		}
		return toReturn;
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
	/**
	 * removeDoc removes the document reference by the provided documentId
	 * @param documentId
	 * @throws DataServiceException
	 */
	public void removeDoc(Integer documentId, String soId) throws DataServiceException {
		DocumentVO soDocumentInfo = new DocumentVO();
		//fetch the count and max value of so_document_id 
		soDocumentInfo = selectCount(documentId,soId);
		Integer maxDocId = null;
		Integer count = null;
		if(soDocumentInfo!=null){
			maxDocId=soDocumentInfo.getMaxDocId();
			count=soDocumentInfo.getCount();
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(documentId);
		//if there exists duplicate documents for the given so_id and document_id, delete all except the latest
		if(count>1){
			removeDocs(list,soId,maxDocId);
		}
		else{
			removeDoc(list,soId);
		}
	}

	
	public void removeDocAll(String soId) throws DataServiceException {

		try {
			
			documentService.deleteDocAll(soId);

		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document", e);
		} 
	}
	
	
	public void removeDocuments(List<Integer> doucmentIds)
			throws DataServiceException {

		DocumentVO documentVO = null;
		ServiceOrder serviceOrderVO = null;

		try {
			for (Integer docId : doucmentIds) {
				// get the doc size
				documentVO = (DocumentVO) queryForObject("so.document.query_metadata_by_document_id", docId);
				//determinePersistanceLocation(documentVO, Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB);

				// flag the document as deleted
				documentService.deleteDocument(documentVO);

				// decrement the service order document size
				serviceOrderVO = serviceOrderDao.getServiceOrder(documentVO.getSoId());

				serviceOrderDao.updateDocSizeTotal(serviceOrderVO.getSoId(),
						new Long(serviceOrderVO.getDocSizeTotal().longValue() - documentVO.getDocSize().longValue()) );
			}

		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document",e);
		} catch (DataServiceException e) {
			throw new DocumentUpdateException("Unable to remove documents",e);
		}
	}
	
	public void removeDoc(List<Integer> doucmentIds, String soId) throws DataServiceException {
		DocumentVO documentVO = new DocumentVO();
		try {
			for (Integer docId : doucmentIds) {
				documentVO.setDocumentId(docId);
				documentVO.setSoId(soId);	
				
			}
			documentService.deleteDoc(documentVO);

		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document", e);
		} 
	}

	public void removeDocs(List<Integer> doucmentIds, String soId, Integer maxDocId) throws DataServiceException {
		DocumentVO documentVO = new DocumentVO();
		try {
			for (Integer docId : doucmentIds) {
				documentVO.setDocumentId(docId);
				documentVO.setSoId(soId);	
				documentVO.setMaxDocId(maxDocId);
			}
			documentService.deleteDocs(documentVO);

		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document", e);
		} 
	}
	
	public DocumentVO selectCount(Integer documentId, String soId) throws DataServiceException {
		DocumentVO documentVO = new DocumentVO();
		try {
				documentVO.setDocumentId(documentId);
				documentVO.setSoId(soId);				
				DocumentVO info = documentService.selectCount(documentVO);;
				return info;
		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Unable to delete document", e);
		} 

	}	

	private Boolean isAlreadyLoaded(DocumentVO documentVO){
		Integer docId = (Integer) queryForObject("so.document.isAlreadyUploaded", documentVO);
		logger.info("SO document id " + docId);
		return (docId != null && docId.intValue() > 0);
	}
	
	public DocumentVO isAlreadyLoadedReturnLoaded(DocumentVO documentVO)
			throws DataServiceException {

		DocumentVO existingDocVO = null;
		DocumentVO returnDocVO = null;

		existingDocVO = (DocumentVO)queryForObject("so.document.query_already_exists", documentVO);

		if (null != existingDocVO ) {
			returnDocVO = (DocumentVO)queryForObject("so.document.query_metadata_by_document_id", existingDocVO.getDocumentId());
			returnDocVO.setSoId(existingDocVO.getSoId());
		}

		return returnDocVO;
	}

	/**
	 * @param existingDocumentVO
	 * @param newDocumentVO
	 * @throws DataServiceException
	 */
	protected void updateDocument(DocumentVO existingDocumentVO, DocumentVO newDocumentVO) throws DataServiceException {
		ServiceOrder serviceOrderVO = null;
		long currentTotal;

		try{
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(Constants.AppPropConstants.SO_DOC_UPLOAD_LIMIT);

			serviceOrderVO = serviceOrderDao.getServiceOrder(newDocumentVO.getSoId());

			if (null != serviceOrderVO && null!=serviceOrderVO.getDocSizeTotal()) {
				currentTotal = serviceOrderVO.getDocSizeTotal().longValue() - existingDocumentVO.getDocSize().longValue();
			} else {
				currentTotal = existingDocumentVO.getDocSize().longValue();
			}

			long new_total = currentTotal + newDocumentVO.getDocSize().longValue();

			if (new_total <= Long.parseLong(prop.getAppValue())) {
				/*determinePersistanceLocation(newDocumentVO, Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB);
				buildSavePath(newDocumentVO);*/

				existingDocumentVO.setDescription(newDocumentVO.getDescription());
				existingDocumentVO.setDocSize(newDocumentVO.getDocSize());
				existingDocumentVO.setDocument(newDocumentVO.getDocument());

				existingDocumentVO = documentService.updateDocument(existingDocumentVO);

				serviceOrderDao.updateDocSizeTotal(existingDocumentVO.getSoId(), new_total);
			} else {
				throw new ServiceOrderDocumentSizeExceededException("Error max size exceeded");
			}
		} catch (DataAccessException e) {
			throw new DocumentUpdateException("Unable to update document",e);
		} catch (DataNotFoundException e) {
			throw new DocumentUpdateException("Unable to update document",e);
		} catch (ServiceOrderDocumentSizeExceededException e) {
			throw e;
		} catch (DataServiceException e) {
			throw new DocumentUpdateException("Unable to update document",e);
		}
	}

	/**
	 * @param newDocumentVO
	 * @throws DataServiceException
	 */
	protected void insertDocument(DocumentVO newDocumentVO) 
		throws DataServiceException {
		ServiceOrder serviceOrderVO = null;

		newDocumentVO.setStoreInDatabase(false);
		try{
			// Code to store the video id in document and so_document table
			if(Constants.DocumentTypes.CATEGORY.VIDEO == newDocumentVO.getDocCategoryId().intValue()){
				newDocumentVO = documentService.createDocument(newDocumentVO);

				//load so to doc cross reference table
				SoDocumentVO soDocVO = new SoDocumentVO();
				soDocVO.setDocumentId(newDocumentVO.getDocumentId());
				soDocVO.setSoId(newDocumentVO.getSoId());				
				serviceOrderDao.insertSoDocument(soDocVO);
			}else{
			
			ApplicationPropertiesVO prop = 
				applicationPropertiesDao.query(
						Constants.AppPropConstants.SO_DOC_UPLOAD_LIMIT);
			//Code for resizing the image files to 800x600: begin
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
			Long currentTotal = 0L;
			serviceOrderVO = serviceOrderDao.getServiceOrder(newDocumentVO.getSoId());
			if(null != serviceOrderVO)
				currentTotal = serviceOrderVO.getDocSizeTotal();
			/*if (null == currentTotal) {
				currentTotal = new Long(0);
			}*/
			long new_total = currentTotal.longValue() + newDocumentVO.getDocSize().longValue();
			if (new_total <= Long.parseLong(prop.getAppValue())) {
				/*determinePersistanceLocation(newDocumentVO, Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB );
				buildSavePath(newDocumentVO);*/
                logger.info("Creating a copy of document for service order");
				newDocumentVO = documentService.createDocument(newDocumentVO);

				//load so to doc cross reference table
				SoDocumentVO soDocVO = new SoDocumentVO();
				soDocVO.setDocumentId(newDocumentVO.getDocumentId());
				soDocVO.setSoId(newDocumentVO.getSoId());
				soDocVO.setDocSource(newDocumentVO.getDocSource());
                logger.info("Creating association with service order");
				serviceOrderDao.insertSoDocument(soDocVO);
                logger.info("Updating the total size uploaded on so header");
				serviceOrderDao.updateDocSizeTotal(newDocumentVO.getSoId(), new_total);
				// Creating thumbnails for image document type
					if (StringUtils.contains(newDocumentVO.getFormat(),
							Constants.ThumbNail.IMAGE_TYPE_STRING)
							&& !StringUtils.equals(newDocumentVO.getFormat(),
									Constants.ThumbNail.TIFF_IMAGE_TYPE_STRING)) {
						DocumentVO thumbDoc = new DocumentVO();

						thumbDoc = createThumbImage(newDocumentVO);
						thumbDoc = documentService.createDocument(thumbDoc);
						SoDocumentVO thumbSoDocVO = new SoDocumentVO();
						thumbSoDocVO.setDocumentId(thumbDoc.getDocumentId());
						thumbSoDocVO.setSoId(thumbDoc.getSoId());
						thumbSoDocVO.setDocSource(thumbDoc.getDocSource());
						serviceOrderDao.insertSoDocument(thumbSoDocVO);
					}
			} else {
				throw new ServiceOrderDocumentSizeExceededException("Error max size exceeded");
			}
			if(null != file){
				file.delete();
			}
		}
		} catch (DataAccessException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		} catch (DataNotFoundException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		} catch (ServiceOrderDocumentSizeExceededException e) {
			throw e;
		} catch (DataServiceException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		}catch (IOException e) {
			throw new DocumentUpdateException("Unable to upload document",e);
		}

	}


	public List<DocumentVO> retrieveDocumentsByEntityIdAndDocumentId(Integer entity_id, Integer document_id)throws DataServiceException{
		return null;

	}
	public DocumentVO retrieveBuyerDocumentByDocumentId(Integer documentId)throws DataServiceException{

		return null;
	}

	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	/**
	 * Does a physical delete of the documents FROM THE FILE SYSTEM ONLY
	 * The MetaData is not deleted.
	 * @param soId
	 */
	public int deleteDocumentEntity(String soId) throws DataServiceException {
		/*try {
			if(!isStoreInDatabase(Constants.AppPropConstants.PERSIST_SO_DOC_IN_DB))
				return deleteDocumentFromFileSystem(Constants.AppPropConstants.SO_SAVE_DOC_PATH);
		} catch (DataAccessException e) {
			throw new DocumentDeleteException("Delete Document Failure",e);
		}
		return 0; */
		return 1;
	}

	public DocumentVO updateDocumentByDocumentId(DocumentVO inDocumentVO) throws DBException{
		return inDocumentVO;
	}


	public DocumentVO retrieveDocumentMetaDataByDocumentId(DocumentVO inDocumentVO)
			throws DataServiceException{
		return inDocumentVO;
	}
	


	public DocumentVO retrieveDocumentByTitleAndEntityID(String title,
			Integer ownerID) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	
	public List<DocumentVO> getRequiredDocuments(DocumentVO documentVO) throws DataServiceException{
		
		List <DocumentVO> documentvoList=new ArrayList<DocumentVO>();
		try{
			documentvoList = queryForList("so.document.query_get_required_documents", documentVO);
		}catch (DataAccessException e) {
			throw new DataServiceException("Unable to retrive documents for: " + documentVO.getSoId(),e);
		}
		
		return documentvoList;
	}
	

	public DocumentVO retrieveDocumentByFileNameAndSoID(DocumentVO documentVO) throws DataServiceException {
		logger.info("Inside retrieveDocumentByFileNameAndEntityID-->Start");
		DocumentVO returnDocVO = null;
		try {			
			returnDocVO = (DocumentVO)queryForObject("so.document.query_by_so_id_and_filename", documentVO);
		}catch (DataAccessException e) {
			throw new DataServiceException("Unable to retrive documents for: " + documentVO.getSoId(),e);
		}
		
		return returnDocVO;
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
	 * method to create the thumbnail document object
	 * @param documentVO
	 * @return documentVO
	 * @throws DataServiceException 
	 */
	private DocumentVO createThumbImage(DocumentVO documentVO)
		throws IOException{
		DocumentVO thumbDoc = new DocumentVO();
		thumbDoc = mapDocument(documentVO);
		String fileName = thumbDoc.getFileName();
		int extStart = fileName.lastIndexOf(Constants.DOT);
		String fileExtn = fileName.substring(extStart+1);
		String fileNameWithOutExtn = fileName.substring(0, extStart);
		StringBuilder newFileName = new StringBuilder(fileNameWithOutExtn);
		newFileName.append(Constants.ThumbNail.THUMBNAIL_SUFFIX);			
		newFileName.append(Constants.DOT);
		newFileName.append(fileExtn);
		thumbDoc.setFileName(newFileName.toString());
		if(null != thumbDoc.getBlobBytes()){
			thumbDoc.setBlobBytes(DocumentUtils.resizeoImage(
					thumbDoc.getBlobBytes(),Constants.ThumbNail.THUMB_IMAGE_WIDTH,
					Constants.ThumbNail.THUMB_IMAGE_HEIGHT));
		}
		else if(null != thumbDoc.getDocument()){
			byte[] imageBytes = FileUtils.readFileToByteArray(thumbDoc.getDocument());
			thumbDoc.setBlobBytes(DocumentUtils.resizeoImage(
					imageBytes,Constants.ThumbNail.THUMB_IMAGE_WIDTH,
					Constants.ThumbNail.THUMB_IMAGE_HEIGHT));
		}		
		thumbDoc.setDocSize(new Long(thumbDoc.getBlobBytes().length));
		thumbDoc.setDocument(null);
		return thumbDoc;
	}	
	/**
	 * method to map the thumbnail document object from document object
	 * @param documentVO
	 * @return documentVO
	 */
	private DocumentVO mapDocument(DocumentVO documentVO){
		DocumentVO documentVo = new DocumentVO();
		documentVo.setBlobBytes(documentVO.getBlobBytes());
		documentVo.setBuyerId(documentVO.getBuyerId());
		documentVo.setCompanyId(documentVO.getCompanyId());
		documentVo.setDescription(documentVO.getDescription());
		documentVo.setDocCategoryId(documentVO.getDocCategoryId());
		documentVo.setDocSize(documentVO.getDocSize());
		documentVo.setEntityId(documentVO.getEntityId());
		documentVo.setFileName(documentVO.getFileName());
		documentVo.setFormat(documentVO.getFormat());
		documentVo.setRoleId(documentVO.getRoleId());
		documentVo.setSoId(documentVO.getSoId());
		documentVo.setTitle(documentVO.getTitle());
		documentVo.setDocument(documentVO.getDocument());
		
		return documentVo;
	}

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
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}


	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
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
		serviceOrderDao.insertSoDocument(soDocVO);
	}
	
	public boolean checkBuyerDocByDocumentId(Integer documentId){
		boolean exists = false;
		try {
			Integer count = (Integer)documentService.checkBuyerDocByDocumentId(documentId, "so.document.get_document_count");
			if(count.intValue() > 0){
				exists = true;
			}
		}catch (Exception e) {
			logger.debug("ServiceOrderDocumentDao.checkBuyerDocByDocumentId"+e);
		}
		return exists;
	}
}
