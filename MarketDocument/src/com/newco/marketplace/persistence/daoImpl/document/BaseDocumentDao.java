package com.newco.marketplace.persistence.daoImpl.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.util.FileServiceUtil;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * 
 */
public class BaseDocumentDao extends ABaseImplDao{
	
	protected IApplicationPropertiesDao applicationPropertiesDao = null;

	/**
	 * isAllowedFormat checks to see if the format provided is an allowed uploadable format.
	 * @param format
	 * @return
	 */
	public boolean isAllowedFormat(String format) {
		boolean isAllowed = false;
		
		Integer count = (Integer)queryForObject("document.query_validate_format", format);
		if (count.intValue() == 1) {
			isAllowed = true;
		}
		
		return isAllowed;
	}
	
	/**
	 * createDocument handles the inserting into the Document table and persisting to the file system if necessary.  
	 * If the calling class wants to persist the document to the file system,
	 * the docuemntVO.docPath attribute must be set with the file path not including the context root.
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public void createDocument(DocumentVO documentVO, String saveDocKey, String saveDocPathKey) throws DataServiceException {
		Integer id = null;
		
		determinePersistanceLocation(documentVO, saveDocKey);
		buildSavePath(documentVO, saveDocPathKey);
		
		if(documentVO.isStoreInDatabase()) {
			createDocumentInDb(documentVO);	
		}else{
			//Save document meta data in the document table
			id =  (Integer)  insert("document.insert", documentVO);
			documentVO.setDocumentId(id);
			createDocumentInFs(documentVO);
		}
	}

	/**
	 * @param documentVO
	 * @param id
	 * @throws DataServiceException
	 */
	private void createDocumentInDb(DocumentVO documentVO)
			throws DataServiceException {
		Integer id = null;
		try {
			documentVO.setDocPath(StringUtils.EMPTY);
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
			id = (Integer) insert("document.insert", documentVO);
		} catch (Exception e) {
			throw new DataServiceException("Unable to insert document: " + documentVO.getFileName(), e);
		}
		documentVO.setDocumentId(id);

		try {
			update("document.updateBlob", documentVO);
		} catch (Exception e) {
			throw new DataServiceException("Unable to update blob for document: " + documentVO.getFileName(), e);
		}
	}

	/**
	 * @param documentVO
	 * @throws DataServiceException
	 */
	private void createDocumentInFs(DocumentVO documentVO)
			throws DataServiceException {
		try {
			//Save the file in the file system
			String fullFileName = documentVO.getDocPath() + documentVO.getDocumentId() + "_" + documentVO.getFileName() ;
			File theFile = new File(fullFileName);
			FileUtils.copyFile(documentVO.getDocument(), theFile);
		} catch (IOException e) {
			throw new DataServiceException("Unable to save document",e);
		}
	}
	
	/**
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public void updateDocument (DocumentVO documentVO, String saveDocKey, String saveDocPathKey) throws DataServiceException {
		
		// update document table
		try {
			update("document.update", documentVO);
		} catch (Exception e) {
			throw new DataServiceException("Unable to update document: " + documentVO.getFileName(), e);
		}
		
		determinePersistanceLocation(documentVO, saveDocKey);
		buildSavePath(documentVO, saveDocPathKey);
	
		if(documentVO.isStoreInDatabase()) {
			try {
				documentVO.setBlobBytes(getBytesFromFile(documentVO.getDocument()));
				update("document.updateBlob", documentVO);
			} catch (IOException e) {
				throw new DataServiceException("Unable to convert file " + documentVO.getFileName()+ " to byte[]",e);
			}
		} else {
			createDocumentInFs(documentVO);
		}
	}
	
	/**
	 * determinePersistanceLocation determines whether the document should be stored in the database
	 * or on the file system.  It sets the storeInDatabase attribute.
	 * @param documentVO
	 * @param persistDocType - See AppPropConstants
	 * @throws DataServiceException
	 */
	public void determinePersistanceLocation(DocumentVO documentVO, String persistDocType) 
	throws DataServiceException {
		documentVO.setStoreInDatabase(isStoreInDatabase(persistDocType));
	}

	/**
	 * determinePersistanceLocation determines whether the document should be stored in the database
	 * or on the file system.  It sets the storeInDatabase attribute.
	 * @param documentVO
	 * @param persistDocType - See AppPropConstants
	 * @throws DataServiceException
	 */
	public boolean isStoreInDatabase(String persistDocType) 
	throws DataServiceException {
		
		boolean toReturn = false;
		try {
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(persistDocType);
			if (prop.getAppValue().equals("1")) {
				toReturn = true;
			}else {
				toReturn = false;
			}
		} catch (DataNotFoundException e) {
			throw new DataServiceException("Unable to retrieve " + persistDocType + " from application_properties",e);
		} catch (DataAccessException e) {
			throw new DataServiceException("Unable to retrieve " + persistDocType + " from application_properties",e);
		}
		return toReturn;
	}
	
	/**
	 * @param docVoList
	 * @return docVoList with the documents from the file system
	 * @throws DocumentRetrievalException
	 * @throws DataServiceException
	 */
	public List<DocumentVO> getDocumentsFromFileSystem(List<DocumentVO> docVoList)
			throws DocumentRetrievalException, DataServiceException {
		for (DocumentVO docVo : docVoList) {
			getDocumentFromFileSystem(docVo);
		}
		return docVoList;
	}
	
	/**
	 * getDocumentFromFileSystem retrieves the file from file system, converts it to a byte[] and saves it in the blobBytes
	 * attribute of the documentVO.  The File object is also stored in the document attribute
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public void getDocumentFromFileSystem(DocumentVO documentVO) throws DataServiceException {
		
		String fullFileName = null;
		
		try {
			/*ApplicationPropertiesVO prop = applicationPropertiesDao.query(Constants.AppPropConstants.DOC_SAVE_LOC);
			
			fullFileName = prop.getAppValue() + documentVO.getDocPath() + documentVO.getFileName();*/
			if (StringUtils.isNotEmpty(documentVO.getDocPath())) {
				fullFileName = documentVO.getDocPath() + documentVO.getFileName();
				File file = new File(fullFileName);
				documentVO.setBlobBytes(FileUtils.readFileToByteArray(file));
				documentVO.setDocument(file);
			}
		} catch (DataAccessException e) {
			throw new DataServiceException("Unable to retrieve context root for file system persistence",e);
		} catch (IOException e) {
			throw new DataServiceException("Unable to retrive file (" + fullFileName + ")from file system",e);
		}
	}
	
	/**
	 * deleteDocument set the delete_ind on the document table to 1 and appends '_DELETED_' || '<docId>' 
	 * to the file name 
	 * @param documentVO
	 * @throws DataServiceException
	 */
	public void deleteDocument(DocumentVO documentVO, String saveDocKey, String saveDocPathKey)throws DataServiceException{
		
		String newFileName = null;
		determinePersistanceLocation(documentVO, saveDocKey);
		if (StringUtils.isNotEmpty(saveDocPathKey)) {
			buildSavePath(documentVO, saveDocPathKey);
		}
		if (documentVO.getFileName().length() > 200) {
			newFileName = StringUtils.substring(documentVO.getFileName(), 0, 200) + "_DELETED_" + documentVO.getDocumentId();
		} else {
			newFileName = documentVO.getFileName() + "_DELETED_" + documentVO.getDocumentId();
		}
		
		if (documentVO.isStoreInDatabase() || StringUtils.isEmpty(documentVO.getDocPath())) {
			documentVO.setFileName(newFileName);
			documentVO.setDocPath(StringUtils.EMPTY);
			update ("document.delete",documentVO);
		} else {
			try {
				String newFilePath = documentVO.getDocPath() + newFileName;
				File file = new File(documentVO.getDocPath() + documentVO.getFileName());

				file.renameTo(new File(newFilePath));
				documentVO.setFileName(newFileName);
				update ("document.delete",documentVO);
			} catch (DataAccessException e) {
				throw new DataServiceException("Unable to rename file (" + newFileName + ")from file system",e);
			} 

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

	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}

	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}

	/**
	 * Get the DOC_SAVE_LOC value
	 * @return
	 * @throws DataServiceException
	 */
	public String getRootPath() throws DataServiceException{
		ApplicationPropertiesVO prop = new ApplicationPropertiesVO();

		try {
			prop = applicationPropertiesDao.query(Constants.AppPropConstants.DOC_SAVE_LOC);
		} catch (DataNotFoundException e) {
			throw new DataServiceException("Unable to retrieve " + Constants.AppPropConstants.DOC_SAVE_LOC + " from database", e);
		} catch (DataAccessException e) {
			throw new DataServiceException("Unable to retrieve " + Constants.AppPropConstants.DOC_SAVE_LOC + " from database", e);
		}
		return prop.getAppValue();
	}
	
	/**
	 * Deletes the folder/files from file system
	 * @param path
	 * @return
	 * @throws DataServiceException 
	 */
	public int deleteDocumentFromFileSystem(String saveDocPathKey, String fileName) throws DataServiceException {
		return FileServiceUtil.deleteFileResource(getSavePath(saveDocPathKey)+ fileName);
	}
	
	/**
	 * Deletes the folder/files from file system
	 * @param documentVo
	 * @return
	 * @throws DataServiceException
	 */
	public int deleteDocumentFromFileSystem(DocumentVO documentVo) throws DataServiceException {
		if (StringUtils.isNotEmpty(documentVo.getDocPath())) {
			return FileServiceUtil.deleteFileResource(documentVo.getDocPath());
		}
		return 0;
		
	}
	
	/**
	 * isAllowedExtension checks to see if the File Extension provided is an allowed uploadable Extension.
	 * @param fileExtenstion
	 * @return boolean
	 */
	public boolean isAllowedExtension(String fileExtenstion) {
		boolean isAllowed = false;
		Integer count = (Integer)queryForObject("document.query_isvalid_extension",fileExtenstion);
		if (count.intValue() == 1) {
			isAllowed = true;
		}
		return isAllowed;
	}
	
	public DocumentVO retrieveDocumentByDocumentId(Integer document_id, String queryId, String saveDocKey, String saveDocPathKey) 
	throws DataServiceException {
		
		DocumentVO documentVO = null;
		
		try {
			documentVO = (DocumentVO)queryForObject(queryId, document_id);
			determinePersistanceLocation(documentVO, saveDocKey);
			buildSavePath(documentVO, saveDocPathKey);
						
			if (!documentVO.isStoreInDatabase()) {
				// get from file system
				getDocumentFromFileSystem(documentVO);
			}
			
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrive document: " + document_id,e);
		}
		
		return documentVO;
	}
	
	public void buildSavePath(DocumentVO documentVO, String saveDocPathKey) throws DataServiceException{
		
		if (!documentVO.isStoreInDatabase()){
			// get file path and append so_id 
			documentVO.setDocPath(getSavePath(saveDocPathKey));
		}
	}

	/**
	 * @param saveDocPathKey
	 * @return savePath 
	 * @throws DocumentRetrievalException
	 */
	public String getSavePath(String saveDocPathKey)
			throws DocumentRetrievalException {
		try {
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(saveDocPathKey);
			return prop.getAppValue();
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve " + saveDocPathKey + " from application_properties",e);
		} catch (DataNotFoundException e) {
			throw new DocumentRetrievalException("Unable to retrieve " + saveDocPathKey + " from application_properties",e);
		}
	}
}
