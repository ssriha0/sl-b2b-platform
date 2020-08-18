package com.newco.marketplace.persistence.daoImpl.document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.ServiceOrderDocumentSizeExceededException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.iDao.document.ISimpleDocumentDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class SimpleDocumentDaoImpl  extends BaseDocumentDao implements
		ISimpleDocumentDao {

	private static final Logger logger = Logger
			.getLogger(SimpleDocumentDaoImpl.class.getName());
	protected IApplicationPropertiesDao applicationPropertiesDao ;
	protected ServiceOrderDocumentDao serviceOrderDocumentDAO ;
	ApplicationPropertiesVO prop = new ApplicationPropertiesVO();
	ApplicationPropertiesVO propSimple = new ApplicationPropertiesVO();

	public String getFilePath(String buyerGuid, String fileName) {
		String fullName = "";
		try {
			prop = applicationPropertiesDao
					.query(Constants.AppPropConstants.DOC_SAVE_LOC);
			propSimple = applicationPropertiesDao
					.query(Constants.AppPropConstants.SIMPLE_DOC_PATH);
			fullName = prop.getAppValue() + propSimple.getAppValue() + buyerGuid + "/" + fileName;
		} catch (DataNotFoundException de) {
			logger.info("Find Error Message : " + de.getMessage());
		}
		return fullName;

	}

	public ProcessResponse insertTemporarySimpleBuyerServiceOrderDocument(
			DocumentVO documentVO) throws DataServiceException {
		// Insert the document in Document table
		ProcessResponse processResp = new ProcessResponse(
				ServiceConstants.VALID_RC, ServiceConstants.VALID_MSG);
		
		
		try {
			logger.info("inside insert" );
			
			
			DocumentVO currentDocumentVO = isAlreadyLoaded(documentVO);
			
			if(currentDocumentVO != null){
				processResp.setCode(OrderConstants.SO_DOC_UPLOAD_EXSITS);
				processResp.setMessage("A file with that name has already been uploaded.");
				return processResp;
			}
		
			
			//  check for size limit
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(Constants.AppPropConstants.SO_DOC_UPLOAD_LIMIT);
			
			Long currentTotal = getTempDocSizeTotal(documentVO.getSoId());
			if (null == currentTotal) {
				currentTotal = new Long(0);
			}
			long new_total = currentTotal.longValue() + documentVO.getDocSize().longValue();
			if (new_total <= Long.parseLong(prop.getAppValue())) {
			
				insert("simple.document.insert", documentVO);
				String fullFileName = getFilePath(documentVO.getSoId(), documentVO.getFileName());
				logger.info("FullFile Details : " + fullFileName);
				File theFile = new File(fullFileName);
				try {
					FileUtils.copyFile(documentVO.getDocument(), theFile);
				} catch (IOException e) {
					processResp = new ProcessResponse(
							ServiceConstants.SYSTEM_ERROR_RC,
							ServiceConstants.SYSTEM_ERROR_MSG);
					logger.info("ERROR ALERT - File not saved : " + e);
					throw new DataServiceException("Unable to save document", e);
				}
	
			} else {
				throw new ServiceOrderDocumentSizeExceededException("Error max size exceeded");
			}


		}catch (ServiceOrderDocumentSizeExceededException e) {
			processResp.setCode(OrderConstants.SO_DOC_SIZE_EXCEEDED_RC);
			processResp.setMessage(e.getMessage());
		}catch (Exception e) {
			processResp = new ProcessResponse(ServiceConstants.SYSTEM_ERROR_RC,
					ServiceConstants.SYSTEM_ERROR_MSG);
			logger.info("Error::");
			e.printStackTrace();
			throw new DataServiceException("ERROR OCCURED, find details : ", e);
		}

		return processResp;
	}

	private Long getTempDocSizeTotal(String simpleBuyerId) {
		
		Long docSize = (Long)queryForObject("simple.document.total_doc_size", simpleBuyerId);
		return docSize;
	}

	public void deleteTemporarySimpleBuyerDocument(String simpleBuyerId,
			Integer docId) throws BusinessServiceException {
		// Delete Document from table and location
		DocumentVO documentVO = new DocumentVO();
		try {
			documentVO.setSoId(simpleBuyerId);
			documentVO.setDocumentId(docId);
			String fileName = "";
			fileName = (String) queryForObject("simple.document.select_single",
					documentVO);
			if (fileName != null) {
				if (!fileName.equalsIgnoreCase("")) {
					String fullFileName = getFilePath(simpleBuyerId, fileName);
					File theFile = new File(fullFileName);
					try {
						FileUtils.forceDelete(theFile);
					} catch (IOException e) {
						logger.info("ERROR ALERT - File not saved : " + e);
						throw new BusinessServiceException(
								"Unable to save document", e);
					}
					delete("simple.document.delete_single", documentVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(
					"ERROR OCCURED, find details : ", e);

		}
	}

	public void deleteTemporarySimpleBuyerDocuments(String simpleBuyerId)
			throws BusinessServiceException {
		// Delete Document's' from table and location
		DocumentVO documentVO = new DocumentVO();
		try {
			documentVO.setSoId(simpleBuyerId);
			List<String> fileNameList = queryForList(
					"simple.document.select_multi", documentVO);
			String fileName = "";
			for (int i = 0; i < fileNameList.size(); i++) {
				fileName = fileNameList.get(i);
				String fullFileName = getFilePath(simpleBuyerId, fileName);
				File theFile = new File(fullFileName);
				try {
					FileUtils.forceDelete(theFile);
				} catch (IOException e) {
					logger.info("ERROR ALERT - File not saved : " + e);
					throw new BusinessServiceException(
							"Unable to save document", e);
				}
			}

			delete("simple.document.delete_multi", documentVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(
					"ERROR OCCURED, find details : ", e);

		}
	}

	public void persistSimpleBuyerDocuments(String simpleBuyerId, String soId, Integer entityId)
			throws BusinessServiceException {
		// To Persist the Document from Temporary Location to Database and to
		// relate to so_id
		// Step 1 - get all documents from temporary location by simpleBuyerId
		// Step 2 - Call uploadDocument(DocumentVO documentVO) in
		// <code>ServiceOrderDocumentDao<code>
		// Step 3 - delete all documents for buyer from temporary location

		List<DocumentVO> docList = new ArrayList<DocumentVO>();
		try {
			DocumentVO documentVO = new DocumentVO();
			documentVO.setSoId(simpleBuyerId);
			//long docSize=0;
			logger.info("fetch all documents from temp location begins");
			docList = queryForList("simple.buyer.select_to_persist", documentVO);
			for (int i = 0; i < docList.size(); i++) {
				DocumentVO docVO = new DocumentVO();
				docVO = docList.get(i);
				docVO.setSoId(soId);
				if (docVO.getEntityId() == null )
				{
					docVO.setEntityId(entityId);
				}
				//docVO.setDocSize(docSize);
				String fileName = docVO.getFileName();
				String fullFileName = getFilePath(simpleBuyerId, fileName);
				File theFile = new File(fullFileName);
				try {
					docVO.setBlobBytes(FileUtils.readFileToByteArray(theFile));
					docVO.setDocument(theFile);
					logger.info("inside reading");
				} catch (IOException e) {
					logger.info("ERROR ALERT - File not saved : " + e);
					throw new BusinessServiceException(
							"Unable to save document", e);
				}
				docVO.setStoreInDatabase(true);
				logger.info("Persisting file into Database, begins");
				serviceOrderDocumentDAO.uploadDocument(docVO);
				logger
						.info("Deletion of files from Temp Location post persistance");
				

			}
			deleteTemporarySimpleBuyerDocuments(simpleBuyerId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(
					"ERROR OCCURED, find details : ", e);
		}

	}

	public List<DocumentVO> retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(
			String simpleBuyerId, Integer categoryId)
			throws BusinessServiceException {
		List<DocumentVO> docList = new ArrayList<DocumentVO>();
		List<DocumentVO> returnList = new ArrayList<DocumentVO>();

		try {
			DocumentVO documentVO = new DocumentVO();
			documentVO.setSoId(simpleBuyerId);
			documentVO.setDocCategoryId(categoryId);
			docList = queryForList("simple.buyer.select_with_buyer_category",
					documentVO);
			for (int i = 0; i < docList.size(); i++) {
				DocumentVO workDoc = new DocumentVO();
				String fileName = "";

				workDoc = docList.get(i);
				fileName = workDoc.getFileName();
				String fullFileName = getFilePath(simpleBuyerId, fileName);
				File theFile = new File(fullFileName);
				try {
					workDoc
							.setBlobBytes(FileUtils
									.readFileToByteArray(theFile));
				} catch (IOException e) {
					logger.info("ERROR ALERT - File not saved : " + e);
					throw new BusinessServiceException(
							"Unable to save document", e);
				}

				returnList.add(workDoc);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(
					"ERROR OCCURED, find details : ", e);
		}

		return returnList;
	}

	public List<DocumentVO> retrieveTemporaryDocumentsMetaDataBySimpleBuyerIdAndCategory(
			String simpleBuyerId, Integer categoryId)
			throws BusinessServiceException {
		List<DocumentVO> docList = new ArrayList<DocumentVO>();
		try {
			DocumentVO documentVO = new DocumentVO();
			documentVO.setSoId(simpleBuyerId);
			documentVO.setDocCategoryId(categoryId);
			docList = queryForList("simple.buyer.select_with_buyer_category",
					documentVO);
		} catch (Exception e) {
			throw new BusinessServiceException(
					"ERROR OCCURED, find details : ", e);
		}

		return docList;
	}

	public DocumentVO retrieveTemporarySimpleBuyerSODocumentByDocumentId(
			Integer documentId, String simpleBuyerId)
			throws BusinessServiceException {
		DocumentVO docVO = new DocumentVO();
		try {

			DocumentVO documentVO = new DocumentVO();
			documentVO.setSoId(simpleBuyerId);
			documentVO.setDocumentId(documentId);
			docVO = (DocumentVO) queryForObject(
					"simple.buyer.select_with_buyer_documentid", documentVO);
			String fileName = "";
			if (docVO!=null){
			fileName = docVO.getFileName();
			
			if (fileName != null) {
				if (!fileName.equalsIgnoreCase("")) {
					String fullFileName = getFilePath(simpleBuyerId, fileName);
					File theFile = new File(fullFileName);
					try {
						docVO.setBlobBytes(FileUtils
								.readFileToByteArray(theFile));
					} catch (IOException e) {
						logger.info("ERROR ALERT - File not saved : " + e);
						throw new BusinessServiceException(
								"Unable to save document", e);
					}

				}
			}}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(
					"ERROR OCCURED, find details : ", e);

		}
		return docVO;

	}
	
	/**
	 * isAllowedFormat checks to see if the format provided is an allowed uploadable format.
	 * @param format
	 * @return
	 * @throws DataServiceException
	 */
	@Override
	public boolean isAllowedFormat(String format) {
		boolean isAllowed = false;
		
		Integer count = (Integer)queryForObject("document.query_validate_format", format);
		if (count == 1) {
			isAllowed = true;
		}
		
		return isAllowed;
	}
	
	/**
	 * isAllowedFormat checks to see if the format provided is an allowed uploadable format.
	 * @param format
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isAllowedImageFormat(String format) throws DataServiceException {
		boolean isAllowed = false;
		Integer count = (Integer)queryForObject("image.query_validate_format", format);
		if (count == 1) {
			isAllowed = true;
		}
		
		return isAllowed;
	}

	public DocumentVO isAlreadyLoaded(DocumentVO documentVO)
			throws DataServiceException {

		DocumentVO existingDocVO = null;
		DocumentVO returnDocVO = null;
		
		existingDocVO = (DocumentVO)queryForObject("temp.document.query_already_exists", documentVO);
		
		if (null != existingDocVO ) {
			returnDocVO = (DocumentVO)queryForObject("temp.document.query_metadata_by_document_id", existingDocVO.getDocumentId());
			returnDocVO.setSoId(existingDocVO.getSoId());
		}
		
		return returnDocVO;
	}
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.document.ISimpleDocumentDao#isTotalImageSizeAllowed(java.lang.Long)
	 */
	public boolean isTotalImageSizeAllowed(Long totalSize)
			throws DataServiceException {
		
		try {
			ApplicationPropertiesVO maxSizeprop = applicationPropertiesDao.query(Constants.AppPropConstants.MAX_PHOTO_SIZE);
			if(maxSizeprop != null) {
				Long value = Long.decode(maxSizeprop.getAppValue());
				int result = value.compareTo(totalSize);
				if( result >= 0) {
					return true;
				}else {
					return false;
				}
			}
			
		} catch (DataNotFoundException e) {
			logger.info("Find Error Message : " + e.getMessage());
		} catch (DataAccessException e) {
			logger.info("Find Error Message : " + e.getMessage());
		}
		return false;
	}
	/**
	 * This method provides the valid extensions from the DB
	 * @return List
	 * @throws DataServiceException
	 */
	public List<String> getValidDisplayExtension() throws DataServiceException {
		List<String> validDisplayExtensions = null;
		try{
			validDisplayExtensions = (List<String> ) queryForList("document.query_valid_extensions",null);
		} catch (Exception e) {
			logger.info("Exception in document.query_valid_extensions : " + e.getMessage());
		}
		return validDisplayExtensions;
	}
	
	//code change for SLT-2231
	 public ArrayList<DocumentVO> getAttachements(ArrayList<Integer> docIds)throws DataServiceException{
		 DocumentVO documentVO = null;
		 ArrayList<DocumentVO> attachmentVOs = new ArrayList<DocumentVO>();
		 try {
			 for (Integer documentId : docIds) {
				 documentVO = (DocumentVO) getSqlMapClient().queryForObject("document.getAttachementsForEmails", documentId);
				 attachmentVOs.add(documentVO);
				}
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("General Exception @SimpleDocumentDaoImpl.getAttachements() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @SimpleDocumentDaoImpl.getAttachements() due to "+ex.getMessage());
		}
		 return attachmentVOs;
	 }
	
	@Override
	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}

	@Override
	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}

	public ServiceOrderDocumentDao getServiceOrderDocumentDAO() {
		return serviceOrderDocumentDAO;
	}

	public void setServiceOrderDocumentDAO(
			ServiceOrderDocumentDao serviceOrderDocumentDAO) {
		this.serviceOrderDocumentDAO = serviceOrderDocumentDAO;	
	}
}
