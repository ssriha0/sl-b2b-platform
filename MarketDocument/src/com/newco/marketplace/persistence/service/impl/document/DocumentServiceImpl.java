package com.newco.marketplace.persistence.service.impl.document;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.ReplicateScaleFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.constants.DocumentConstants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.dao.document.DocumentDao;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.util.FileServiceUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 *  This is main service class for document handling.
 */

public class DocumentServiceImpl implements DocumentService{

	private static final Logger logger = Logger.getLogger(DocumentServiceImpl.class.getName());
	
	private DocumentDao documentDao;
	protected IApplicationPropertiesDao applicationPropertiesDao = null; 

	
	/**
	 * createDocument handles the inserting into the Document table and persisting to the file system if necessary.  
	 * If the calling class wants to persist the document to the file system,
	 * the docuemntVO.docPath attribute must be set with the file path not including the context root.
	 * @param documentVO
	 * @throws DataServiceException
	 */
//	@Transactional ( propagation = Propagation.REQUIRED , rollbackFor=Exception.class) 
	public DocumentVO createDocument(DocumentVO documentVO) throws DataServiceException {
		Integer docId = null;
		String docWriteReadInd = null;	
		//code to store video id
		if(null != documentVO.getDocCategoryId() && Constants.DocumentTypes.CATEGORY.VIDEO == documentVO.getDocCategoryId().intValue()){
			docId = createDocumentMetadataInDb(documentVO);
			documentVO.setDocumentId(docId);
			return documentVO;
		}		
		//Retrieving the document Write Read indicator from application properties
		docWriteReadInd = getDocWriteReadInd();
		documentVO.setDocWriteReadInd(docWriteReadInd);
		
		//Creating the document meta data in database
		documentVO.setDocPath(null); 
		docId = createDocumentMetadataInDb(documentVO);
		documentVO.setDocumentId(docId);
		
		if(docWriteReadInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_DB)){
			updateDocumentBlobInDb(documentVO);
		}else if(docWriteReadInd.equals(DocumentConstants.DOC_WRITE_TO_DB_AND_FS)){
			documentVO.setDocPath(buildDocumentPath(documentVO));
			updateDocumentBlobInDb(documentVO);
			createDocumentInFs(documentVO);
		}else if(docWriteReadInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_FS)){
			documentVO.setDocPath(buildDocumentPath(documentVO));
			createDocumentInFs(documentVO);
		}
		return documentVO;
	}
	/**
	 * Creates the new document in file system
	 * @param documentVO
	 * @throws DataServiceException
	 */
	private void createDocumentInFs(DocumentVO documentVO) throws DataServiceException {		     
		try {
			File file = new File(documentVO.getDocPath());
			if(null != documentVO.getDocument()) {
				FileUtils.copyFile(documentVO.getDocument(), file);
			} else if(documentVO.getBlobBytes() != null){
                FileUtils.writeByteArrayToFile(file, documentVO.getBlobBytes());
            } else {
            	//SL-10106 - think this is related to having video documents that have neither blob or document 
            	logger.info("trying to write file to filesystem, but document doesn't have a blob or file documentId[" + documentVO.getDocumentId() + "]");
            }
            //Now update the new docPath in database
            updateDocPathInDb(documentVO);
            //create thumbnail image for provider profile image in FS
            //we don't to insert thumbnail details into document table
            if(null != documentVO.getDocCategoryId() 
            		&& documentVO.getDocCategoryId().intValue() == Constants.DocumentTypes.CATEGORY.PROVIDER_PHOTO &&
            		StringUtils.contains(documentVO.getFormat(), 
        					Constants.ThumbNail.IMAGE_TYPE_STRING)){
            	DocumentVO thumbDoc = createThumbImage(documentVO);
            	File thumbFile = new File(thumbDoc.getDocPath());
    			if(thumbDoc.getBlobBytes() != null){
                    FileUtils.writeByteArrayToFile(thumbFile, thumbDoc.getBlobBytes());
                }
            }
		} catch (IOException e) {
            throw new DataServiceException("Unable to save document",e);
		}
	}
	
	

	
	/**
	 * Updates the document details
	 * @param documentVO
	 * @throws DataServiceException
	 */
	@Transactional ( propagation = Propagation.REQUIRED , rollbackFor=Exception.class) 
	public DocumentVO updateDocument (DocumentVO documentVO) throws DataServiceException {
		String docWriteReadInd = null;
		
		// update document meta data in document table
		try {
			documentDao.update("document.update", documentVO);
		} catch (Exception e) {
			throw new DataServiceException("Unable to update document meta data : " + documentVO.getFileName(), e);
		}
		
		//Retrieving the document Write Read indicator from application properties
		docWriteReadInd = getDocWriteReadInd();
		documentVO.setDocWriteReadInd(docWriteReadInd);

		if(docWriteReadInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_DB)){
			updateDocumentBlobInDb(documentVO);
		}else if(docWriteReadInd.equals(DocumentConstants.DOC_WRITE_TO_DB_AND_FS)){
			documentVO.setDocPath(buildDocumentPath(documentVO));
			updateDocumentBlobInDb(documentVO);	
			createDocumentInFs(documentVO);
		}else if(docWriteReadInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_FS)){
			documentVO.setDocPath(buildDocumentPath(documentVO));
			createDocumentInFs(documentVO);
		}
		return documentVO;
	}	
	/**
     * deleteDocument set the delete_ind on the document table to 1 and appends '_DELETED_' || '<docId>' 
     * to the file name 
     * @param documentVO
     * @throws DataServiceException
     */
	//@Transactional ( propagation = Propagation.REQUIRED , rollbackFor=Exception.class)    
    public void deleteDocument(DocumentVO documentVO)throws DataServiceException{
          
          String newFileName = null;          
          documentVO.setDocWriteReadInd(getDocWriteReadInd());
          
          String fileExtn = StringUtils.substring(documentVO.getFileName(), documentVO.getFileName().lastIndexOf("."));
          newFileName = StringUtils.substring(documentVO.getFileName(), 0,documentVO.getFileName().lastIndexOf("."));
          if (documentVO.getFileName().length() > 200) {
                newFileName = StringUtils.substring(newFileName, 0, 200) + "_DELETED_" + documentVO.getDocumentId();
          } else {
                newFileName = newFileName + "_DELETED_" + documentVO.getDocumentId();
          }
          newFileName = newFileName + fileExtn;
          
          if (documentVO.getDocWriteReadInd().equals(DocumentConstants.DOC_READ_WRITE_FROM_DB) || StringUtils.isEmpty(documentVO.getDocPath())) {
                documentVO.setDocPath(StringUtils.EMPTY);             
          }else if(documentVO.getDocWriteReadInd().equals(DocumentConstants.DOC_WRITE_TO_DB_AND_FS) ||documentVO.getDocWriteReadInd().equals(DocumentConstants.DOC_READ_WRITE_FROM_FS) ){
        	    String oldFilePath = documentVO.getDocPath();        	    
                String newFilePath = oldFilePath.substring(0, oldFilePath.lastIndexOf("/")+1)+ newFileName;
                File file = new File(documentVO.getDocPath());
                file.renameTo(new File(newFilePath));
                documentVO.setDocPath(newFilePath);                   
          }     
          documentVO.setFileName(newFileName);
          try {
        	  documentDao.update ("document.delete",documentVO);
          }catch (DataAccessException e) {
                throw new DataServiceException("Unable to delete file (" + newFileName + ")from file system",e);
          } 
    }
    /**
     * deleteDocument set the delete_ind on the document table to 1 and appends '_DELETED_' || '<docId>' 
     * to the file name 
     * @param documentVO
     * @throws DataServiceException
     */
	//@Transactional ( propagation = Propagation.REQUIRED , rollbackFor=Exception.class)    
    public void  deleteDoc(DocumentVO documentVO) throws DataServiceException{
    	try{
    		 documentDao.delete ("document.so_delete",documentVO);
    	} 
    	catch (DataAccessException e) {
    		throw new DataServiceException("Unable to delete file from file system",e);
    	} 
    }
    	
    public void  deleteDocs(DocumentVO documentVO) throws DataServiceException{
    	try{
    		 documentDao.delete ("document.so_delete_docs",documentVO);
    	} 
    	catch (DataAccessException e) {
    		throw new DataServiceException("Unable to delete file from file system",e);
    	} 
    }
    public void  deleteDocAll(String soId) throws DataServiceException{
    	try{
    		 documentDao.delete ("document.so_deleteAll",soId);
    	} 
    	catch (DataAccessException e) {
    		throw new DataServiceException("Unable to delete file from file system",e);
    	} 
    }
    
    
    @SuppressWarnings("unchecked")
	public DocumentVO selectCount(DocumentVO documentVO) throws DataServiceException{
    	try{
    		DocumentVO info = (DocumentVO) documentDao.queryForObject("document.selectCount",documentVO);
    		return info;
    	} 
    	catch (DataAccessException e) {
    		throw new DataServiceException("Unable to retrieve count",e);
    	} 
    }
    
    
	/**
     * Returns the contents of the file in a byte array
     * @param file File this method should read
     * @return byte[] Returns a byte[] array of the contents of the file
     */
    private static byte[] getBytesFromFile(File file) throws DataServiceException, IOException {

        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        /*
         * You cannot create an array using a long type. It needs to be an int
         * type. Before converting to an int type, check to ensure that file is
         * not larger than Integer.MAX_VALUE;
         */
        if (length > Integer.MAX_VALUE) {
        	throw new DataServiceException("File is too large to process");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while ( (offset < bytes.length) && ( (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) ) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;

    }	
	/**
	 * Deletes the folder/files from file system
	 * @param documentVo
	 * @return
	 * @throws DataServiceException
	 */
	public int deleteDocumentFromFileSystem(DocumentVO documentVo) throws DataServiceException {
		String docPath = documentVo.getDocPath();
		if (StringUtils.isNotEmpty(docPath)) {
			File path = new File(docPath);
			if (path.isDirectory()) {
				return FileServiceUtil.deleteFileResource(documentVo.getDocPath());
			}
		}
		return 0;
		
	}	
	/**
	 * Deletes the files from file system
	 * @param queryId
	 * @param documentId
	 * @return
	 * @throws DataServiceException
	 */
	public int deleteDocumentFromFileSystem(String queryId,Integer documentId) throws DataServiceException {
		try {
			DocumentVO documentVo = null;
			String docReadWriteInd = getDocWriteReadInd();
			if(!docReadWriteInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_DB)){
				documentVo = (DocumentVO) documentDao.queryForObject(queryId, documentId);
				deleteDocumentFromFileSystem(documentVo);
			}
		}catch(DataAccessException dae){
		    throw new DataServiceException("Unable to delete file from file system",dae);
		}
		return 0;
	}
	/**
	 * isAllowedExtension checks to see if the File Extension provided is an allowed uploadable Extension.
	 * @param fileExtenstion
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAllowedExtension(String fileExtenstion) throws DataServiceException {
		boolean isAllowed = false;
		Integer count = (Integer)documentDao.queryForObject("document.query_isvalid_extension",fileExtenstion);
		if (count.intValue() == 1) {
			isAllowed = true;
		}
		return isAllowed;
	}	
	/**
	 * getDocSavePath retrieves the location for saving the document in file system
	 * @param saveDocPathKey
	 * @return savePath 
	 * @throws DocumentRetrievalException
	 */
	public void getDocSavePath(DocumentVO documentVO)throws DocumentRetrievalException {
		String saveDocPathKey = "";
	    if(null!=documentVO && documentVO.isCompanyLogo()){  
		 saveDocPathKey=Constants.AppPropConstants.FIRM_LOGO_SAVE_LOC;	
		}else{
		saveDocPathKey=Constants.AppPropConstants.DOC_SAVE_LOC;
		}
		try {
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(saveDocPathKey);
			documentVO.setDocPath(prop.getAppValue());
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve " + saveDocPathKey + " from application_properties",e);
		} catch (DataNotFoundException e) {
			throw new DocumentRetrievalException("Unable to retrieve " + saveDocPathKey + " from application_properties",e);
		}
	}
	
	/**
	 * Creates the document meta data in database
	 * @param documentVO
	 * @throws DataServiceException
	 */
	private Integer createDocumentMetadataInDb(DocumentVO documentVO)	throws DataServiceException {
		Integer docId = null;
		try {
			docId = (Integer) documentDao.insert("document.insert", documentVO);
		} catch (Exception e) {
			throw new DataServiceException("Unable to create document metadata in DB: " + documentVO.getFileName(), e);
		}
		return docId;		
	}
	/**
	 * updates the blob field in document table in database
	 * @param documentVO
	 * @throws DataServiceException
	 */
	private void updateDocumentBlobInDb(DocumentVO documentVO) throws DataServiceException{
		try {
			if(documentVO.getBlobBytes() == null 
					&& documentVO.getDocument() == null){
				throw new DataServiceException("Unable to convert file " + documentVO.getFileName()+ " to byte[]");
			}else if(documentVO.getDocument() != null){
				documentVO.setBlobBytes(getBytesFromFile(documentVO.getDocument()));
			}
		} catch (IOException e) {
			throw new DataServiceException("Unable to convert file " + documentVO.getFileName()+ " to byte[]",e);
		}
		try {
			documentDao.update("document.updateBlob", documentVO);
		} catch (Exception e) {
			throw new DataServiceException("Unable to update blob for document: " + documentVO.getFileName(), e);
		}		
	}
	/**
	 * retrieves the doc_write_read_ind from application_properties table in database
	 * @param key
	 * @throws DocumentRetrievalException
	 */
	private String getDocWriteReadInd()throws DocumentRetrievalException{
		String docReadWriteInd = null;	
		String key = Constants.AppPropConstants.DOC_WRITE_READ_IND;
		try {
			docReadWriteInd = applicationPropertiesDao.queryByKey(key);
			if (null == docReadWriteInd)
				logger.warn ("DocumentServiceImpl.getDocWriteWreadInd(): docReadWriteInd == null.  Check your database settings.");
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve Document write read indicator from application_properties",e);
		} catch (DataNotFoundException e) {
			throw new DocumentRetrievalException("Unable to retrieve Document write read indicator from application_properties",e);
		}
		return docReadWriteInd;
	}
	/**
	 * updates the doc_path in document table in database
	 * @param documentVO
	 * @throws DataServiceException
	 */
	private void updateDocPathInDb(DocumentVO documentVO) throws DataServiceException{
		try{
			documentDao.update("document.updateDocPath",documentVO);
		}catch(Exception e){
			throw new DataServiceException("Unable to update doc path for documentId: " + documentVO.getDocumentId()+" due to :", e);
		}
	}		
	/**
     * retrieveDocumentByDocumentId method retrieves the document details from database. 
     * @param document_id
     * @param queryId
     * @return DocumentVO 
     * @throws DataServiceException
     */	
    public DocumentVO retrieveDocumentByDocumentId(Integer documentId, String queryId) throws DataServiceException {
          DocumentVO documentVO = null;  
          try {
        	  //Gets document meta data  from database
              documentVO = (DocumentVO)documentDao.queryForObject(queryId, documentId);              
              //Get document blob data from database or file system
           	  documentVO = retrieveDocumentFromDBOrFS(documentVO);   
          }catch (DataAccessException e) {
                throw new DocumentRetrievalException("Unable to retrieve document: " + documentId, e);
          }
          return documentVO; 
    }  
    public DocumentVO retrieveDocumentByDocumentId_spn(Integer documentId, String queryId) throws DataServiceException {
        DocumentVO documentVO = null;  
        try {
      	  //Gets document meta data  from database
            documentVO = (DocumentVO)documentDao.queryForObject(queryId, documentId);              
            //Get document blob data from database or file system
//         	  documentVO = retrieveDocumentFromDBOrFS(documentVO);   
        }catch (DataAccessException e) {
              throw new DocumentRetrievalException("Unable to retrieve document: " + documentId, e);
        }
        return documentVO; 
  }
    /**
     * checkBuyerDocByDocumentId method retrieves the document details from database. 
     * @param documentId
     * @param queryId
     * @return count 
     */	
    public Integer checkBuyerDocByDocumentId(Integer documentId, String queryId){
          Integer count = null;  
          try {
        	  //Gets count of document from buyer_document
              count = (Integer)documentDao.queryForObject(queryId, documentId);              
          }catch (DataAccessException e) {
                logger.debug("DocumentServiceImpl.checkBuyerDocByDocumentId"+e);
          }        
          return count; 
    }    
   	/**
	 * retrieveDocumentFromDBOrFS method calls the methods to get the document from file system or DB,
	 * based on the value of doc_write_read_ind,
	 * @param pDocumentVO
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO retrieveDocumentFromDBOrFS(DocumentVO pDocumentVO)throws DataServiceException {
		DocumentVO documentVO = pDocumentVO;
		if(documentVO == null) {
			return null;
		}

		try {
			//Retrieves the document storage retrieval indicator from application properties
			 String docWriteReadInd = getDocWriteReadInd();   
          
            if(DocumentConstants.DOC_READ_WRITE_FROM_DB.equals(docWriteReadInd)) {
            	//get document blob from DB
            	documentVO = getDocumentBlobFromDb(documentVO);
    		} else {
    			// get document from file system
    			documentVO = getDocumentFromFileSystem(documentVO);
    		}
		} catch(DataAccessException e) {
			throw new DataServiceException("Unable to retrieve blob data for documentId: " + documentVO.getDocumentId()+" due to :", e);
		}	
		return documentVO;
	}	
	/**
	 * retrieveDocumentFromDBOrFS method calls the methods to get the list of document from file system or DB,
	 * based on the value of doc_write_read_ind,
	 * @param docVoList
	 * @return docVoList
	 * @throws DataServiceException
	 */
	public List<DocumentVO> retrieveDocumentsFromDBOrFS(List<DocumentVO> docVoList)throws DataServiceException { 
		String docWriteReadInd = null;
		try {
			//Retrieves the document storage retrieval indicator from application properties
            docWriteReadInd = getDocWriteReadInd();   
          
            if(docWriteReadInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_DB)){
            	//get document blob from DB
            	return getDocumentsBlobFromDb(docVoList);
    		}
			// get documents from file system
			return getDocumentsFromFileSystem(docVoList);
		} catch(DataAccessException e) {
			throw new DataServiceException("Unable to retrieve blob data due to :", e);
		}	
	}	
	/**
	 * getDocumentsFromFileSystem calls the method to retrive the document list from file system
	 * @param docVoList
	 * @return docVoList with the documents from the file system
	 */
	private List<DocumentVO> getDocumentsFromFileSystem(List<DocumentVO> docVoList) {

		for (Iterator<DocumentVO> iter = docVoList.iterator(); iter.hasNext(); ) {
			DocumentVO docVo = iter.next();
			boolean getFromFS = false;
			try {
				if(null != docVo.getDocCategoryId()&& docVo.getDocCategoryId().intValue()==Constants.DocumentTypes.CATEGORY.VIDEO) {
					getFromFS = false;
				}
				else{
					getFromFS = true;
				}
				if(getFromFS){
					getDocumentFromFileSystem(docVo);
				}
			} catch (DataServiceException ex) {
				logger.error("Error occured during retrieving a document : " + docVo.getDocPath() 
						+ " Actual Cause: ",  ex);
				logger.info("removing the document from the list!!! ");
				iter.remove();
			}
		}
		return docVoList;
	}	
	/**
	 * getDocumentsBlobFromDb method calls the method to retrieve the blob data of the documents from database
	 * @param docVoList
	 * @return docVoList with the documents from the file system
	 * @throws DataServiceException
	 */
	private List<DocumentVO> getDocumentsBlobFromDb(List<DocumentVO> docVoList)throws DataServiceException {
		for (DocumentVO docVo : docVoList) {
			getDocumentBlobFromDb(docVo);
		}
		return docVoList;
	}	
	 /**
     * getDocumentFromFileSystem retrieves the file from file system, converts it to a byte[] and saves it in the blobBytes
     * attribute of the documentVO.  The File object is also stored in the document attribute
     * @param documentVO
     * @throws DataServiceException
     */
    private DocumentVO getDocumentFromFileSystem(DocumentVO documentVO) throws DataServiceException {        
          if (StringUtils.isEmpty(documentVO.getDocPath())) {
        	  throw new DataServiceException ("Document path is NULL!!!!");
          }
          String fullFileName = documentVO.getDocPath();
          try {        
              File file = new File(fullFileName);                  
              //Converts the file to byte[] and save in DocumentVO
              documentVO.setBlobBytes(FileUtils.readFileToByteArray(file));
              documentVO.setDocument(file);
              return documentVO;
          } catch (DataAccessException dae) {
                throw new DataServiceException("Unable to retrieve context root for file system persistence",dae);
          } catch (IOException ioe) {
                throw new DataServiceException("Unable to retrieve file (" + fullFileName + ")from file system",ioe);
          }
          
    }	
    /**
	 * Retrieves the document blob data from database
	 * @param documentVO
	 * @throws DataServiceException
	 */
	private DocumentVO getDocumentBlobFromDb(DocumentVO documentVO) throws DataServiceException { 
		DocumentVO docVO = null;
		try{
			Integer docId = documentVO.getDocumentId();
			docVO = (DocumentVO)documentDao.queryForObject("document.query_document_blob", docId);
			documentVO.setBlobBytes(docVO.getBlobBytes());
		}catch(DataAccessException e){
			throw new DataServiceException("Unable to retrieve blob data for documentId: " + documentVO.getDocumentId()+" due to :", e);
		}
		return documentVO;
	}
	
	/**
	 * Retrieves document blobBytes 
	 * @param docId
	 * @return
	 * @throws DataServiceException
	 */
	public byte[] getDocumentBlobByDocumentId(Integer docId) throws DataServiceException { 
		DocumentVO docVO = new DocumentVO();
		byte[] blobBytes = null;
		try{
			docVO.setDocumentId(docId);
			docVO = getDocumentMetadata(docVO);
			docVO = retrieveDocumentFromDBOrFS(docVO);
			blobBytes = docVO.getBlobBytes();
		}catch(DataAccessException e){
			throw new DataServiceException("Unable to retrieve blob data for documentId: " + docVO.getDocumentId()+" due to :", e);
		}
		return blobBytes;
	}
	
    /**
	 * Retrieves document metadata 
	 * @param DocumentVO
	 * @return DocumentVO
	 * @throws DataServiceException
	 */
	public DocumentVO getDocumentMetadata(DocumentVO documentVO) throws DataServiceException{
		Integer docId = null;
		try{
			docId = documentVO.getDocumentId();
			return (DocumentVO)documentDao.queryForObject("document.query_by_document_id", docId);
		}catch(Exception ex){
			throw new DataServiceException("Unable to retrieve document meta data for documentId: " + documentVO.getDocumentId()+" due to :", ex);
		}
	}
	/**
	 * Retrieves retrieves the full location for saving the document in file system
	 * @param docVO
	 * @return docPath
	 * @throws DocumentRetrievalException
	 */
	public String buildDocumentPath(DocumentVO docVO)throws DocumentRetrievalException{
    	String docPath = "";
    	int yyyy = 0;
    	int mm = 0;
    	int dd = 0;
    	String feedbackIndicator=docVO.getDocSource();
		logger.info("Feedback Indicator"+feedbackIndicator);
		if(null!=feedbackIndicator && feedbackIndicator.equalsIgnoreCase(MPConstants.PROVIDER_FEEDBACK_INDICATOR))
		{
			String feedBackScreenShotSaveLocation="";
			try {
				feedBackScreenShotSaveLocation = documentDao.getAppPropertiesValue(MPConstants.FEEDBACK_SAVE_LOCATION);
				logger.info("Feedback screen shot location"+feedBackScreenShotSaveLocation);
				docVO.setDocPath(feedBackScreenShotSaveLocation);
			} catch (DataServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
    	getDocSavePath(docVO);
		}
    	//set the temp file path
    	docVO.setFilePath(docVO.getDocPath());
    	//buid the file path
		Integer ss = Integer.valueOf(docVO.getDocumentId().intValue()%100);
		
		if(null != docVO.getCreatedDate()){
			
			Timestamp createdDate = docVO.getCreatedDate();
			Calendar gCal = new GregorianCalendar();
			gCal.setTime(createdDate);
			yyyy = gCal.get(Calendar.YEAR);
			mm = gCal.get(Calendar.MONTH)+1;
			dd = gCal.get(Calendar.DATE);
		}else{
			Calendar cal = Calendar.getInstance();
	        yyyy = cal.get(Calendar.YEAR);
	        mm = cal.get(Calendar.MONTH)+1;
	        dd = cal.get(Calendar.DATE);
		}
		String path = "";
		if(null != docVO.getDocCategoryId() && 
				docVO.getDocCategoryId().intValue() == Constants.DocumentTypes.CATEGORY.PROVIDER_PHOTO){
			path = Constants.PROVIDER_PHOTO_FOLDER + "/" + yyyy + "/" + mm + "/" + dd + "/" + ss + "/";
		}else{
			path = yyyy + "/" + mm + "/" + dd + "/" + ss + "/";
		}
        
		
		//Save the file in the file system
        docPath = docVO.getDocPath() + path + docVO.getDocumentId() + "_" + docVO.getFileName() ;
        logger.info("The final doc path is "+docPath);
    	return docPath;
    }
	/**
	 * method to create the thumbnail document object
	 * @param documentVO
	 * @return documentVO
	 * 
	 */
	private DocumentVO createThumbImage(DocumentVO documentVO)
		throws IOException{
		DocumentVO thumbDoc = new DocumentVO();
		thumbDoc = mapDocument(documentVO);
		String fileName = thumbDoc.getDocPath();
		int extStart = fileName.lastIndexOf(Constants.DOT);
		String fileExtn = fileName.substring(extStart+1);
		String fileNameWithOutExtn = fileName.substring(0, extStart);
		StringBuilder newFileName = new StringBuilder(fileNameWithOutExtn);
		newFileName.append(Constants.ThumbNail.THUMBNAIL_SUFFIX);			
		newFileName.append(Constants.DOT);
		newFileName.append(fileExtn);
		thumbDoc.setDocPath(newFileName.toString());
		try
		{
			if(null != thumbDoc.getBlobBytes()){
				thumbDoc.setBlobBytes(resizeoImage(
						thumbDoc.getBlobBytes(),Constants.ThumbNail.THUMB_IMAGE_WIDTH,
						Constants.ThumbNail.THUMB_IMAGE_HEIGHT));
			}
			else if(null != thumbDoc.getDocument()){
				byte[] imageBytes = FileUtils.readFileToByteArray(thumbDoc.getDocument());
				thumbDoc.setBlobBytes(resizeoImage(
						imageBytes,Constants.ThumbNail.THUMB_IMAGE_WIDTH,
						Constants.ThumbNail.THUMB_IMAGE_HEIGHT));
			}
			thumbDoc.setDocSize(new Long(thumbDoc.getBlobBytes().length));
			thumbDoc.setDocument(null);
		}
		catch(Exception e)
		{
			logger.error("Exception in createThumbImage" , e);
			throw new IOException();
		}
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
		documentVo.setDocPath(documentVO.getDocPath());		
		documentVo.setFileName(documentVO.getFileName());		
		documentVo.setDocument(documentVO.getDocument());		
		return documentVo;
	}

	public static byte[] resizeoImage(byte[] inImagedata, 
			int targetWidth, int targetHeight) {

		int scaledWidth;
		int scaledHeight;

		Image sourceImage = new ImageIcon(inImagedata).getImage();
		float targetscale = 0;
		float scale1 = (int) ((float)targetWidth/(float)(sourceImage.getWidth(null))*100);
		float scale2 = (int) ((float)targetHeight/(float)(sourceImage.getHeight(null))*100) ;
		if (scale1 < scale2)
			targetscale = scale1;
		else
			targetscale = scale2;
		targetscale = targetscale / 100;
		
		scaledWidth = (int) (sourceImage.getWidth(null) * targetscale);
		scaledHeight = (int) (sourceImage.getHeight(null) * targetscale);

		logger.debug("image width = " + sourceImage.getWidth(null));
		logger.debug("image height = " + sourceImage.getHeight(null));
		
		logger.debug("target width = " + targetWidth);
		logger.debug("target height = " + targetHeight);
		
		logger.debug("scale1 = " + scale1);
		logger.debug("scale2 = " + scale2);
		logger.debug("targetscale = " + targetscale);
		
		
		logger.debug("new width = " + scaledWidth);
		logger.debug("new height = " + scaledWidth);
		
		
		
		BufferedImage resizedImage = scaleImage(sourceImage, scaledWidth,
				scaledHeight);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		try {
			encoder.encode(resizedImage);			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image " );
			logger.error(e);
		}
		
		
		byte[] b = out.toByteArray();

		return b;
	}
	private static BufferedImage scaleImage(Image sourceImage, int width,
			int height) {
		ImageFilter filter = new ReplicateScaleFilter(width, height);
		ImageProducer producer = new FilteredImageSource(sourceImage
				.getSource(), filter);
		Image resizedImage = Toolkit.getDefaultToolkit().createImage(producer);

		return toBufferedImage(resizedImage);
	}
	private static BufferedImage toBufferedImage(Image image) {
		image = new ImageIcon(image).getImage();
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bufferedImage;
	}
	
	public DocumentDao getDocumentDao() {
		return documentDao;
	}	
	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}
	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}
}
