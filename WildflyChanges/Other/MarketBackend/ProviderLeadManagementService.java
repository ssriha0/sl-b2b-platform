package com.servicelive.providerleadmanagement.services;

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
import java.util.List;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.constants.DocumentConstants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CallCustomerReasonCodeVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.document.DocumentRetrievalException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.dao.document.DocumentDao;
import com.newco.marketplace.persistence.dao.providerleadmanagement.ProviderLeadManagementDao;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
//I kept comment for jdk1.8
/*import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;*/

public class ProviderLeadManagementService {
	private ProviderLeadManagementDao providerLeadManagementDao;
	private IApplicationPropertiesDao applicationPropertiesDao;
	private DocumentDao documentDao;
	private static final Logger logger = Logger
			.getLogger(ProviderLeadManagementService.class.getName());

	// get document by document ID
	public BuyerLeadAttachmentVO retrieveDocumentByDocumentId(Integer documentId) {
		BuyerLeadAttachmentVO document = providerLeadManagementDao
				.retrieveDocumentById(documentId);
		if (null != document) {
			document = retrieveDocumentsFromDBOrFS(document);
		}
		return document;
	}

	private BuyerLeadAttachmentVO retrieveDocumentsFromDBOrFS(
			BuyerLeadAttachmentVO attachment) {
		String docWriteReadInd = null;
		try {
			// Retrieves the document storage retrieval indicator from
			// application properties
			docWriteReadInd = getDocWriteReadInd();

			if (docWriteReadInd
					.equals(DocumentConstants.DOC_READ_WRITE_FROM_DB)) {
				// get document blob from DB
				return getDocumentBlobFromDb(attachment);
			}
			// get documents from file system
			return getDocumentFromFileSystem(attachment);
		} catch (DataAccessException e) {
			logger.info("Unable to retrieve blob data due to :", e);
		} catch (DataServiceException e) {
			logger.info("Unable to retrieve blob data due to :", e);
		}
		return null;
	}

	/**
	 * retrieves the doc_write_read_ind from application_properties table in
	 * database
	 * 
	 * @param key
	 */
	private String getDocWriteReadInd() {
		String docReadWriteInd = null;
		String key = Constants.AppPropConstants.DOC_WRITE_READ_IND;
		try {
			docReadWriteInd = applicationPropertiesDao.queryByKey(key);
			if (null == docReadWriteInd)
				logger.warn("DocumentServiceImpl.getDocWriteWreadInd(): docReadWriteInd == null.  Check your database settings.");
		} catch (DataAccessException e) {
			logger.info(
					"Unable to retrieve Document write read indicator from application_properties",
					e);
		} catch (DataNotFoundException e) {
			logger.info(
					"Unable to retrieve Document write read indicator from application_properties",
					e);
		}
		return docReadWriteInd;
	}

	/**
	 * Retrieves the document blob data from database
	 * 
	 * @param attachment
	 * @throws DataServiceException
	 */
	private BuyerLeadAttachmentVO getDocumentBlobFromDb(
			BuyerLeadAttachmentVO attachment) throws DataServiceException {
		BuyerLeadAttachmentVO docVO = null;
		try {
			Integer docId = attachment.getDocumentId();
			docVO = (BuyerLeadAttachmentVO) documentDao.queryForObject(
					"document.query_document_blob", docId);
			attachment.setBlobBytes(docVO.getBlobBytes());
		} catch (DataAccessException e) {
			throw new DataServiceException(
					"Unable to retrieve blob data for documentId: "
							+ attachment.getDocumentId() + " due to :", e);
		}
		return attachment;
	}

	/**
	 * retrieves the file from file system, converts it to a byte[] and saves it
	 * in the blobBytes attribute of the documentVO. The File object is also
	 * stored in the document attribute
	 * 
	 * @param attachment
	 * @throws DataServiceException
	 */
	private BuyerLeadAttachmentVO getDocumentFromFileSystem(
			BuyerLeadAttachmentVO attachment) throws DataServiceException {
		if (StringUtils.isEmpty(attachment.getDocPath())) {
			throw new DataServiceException("Document path is NULL!!!!");
		}
		String fullFileName = attachment.getDocPath();
		try {
			File file = new File(fullFileName);
			// Converts the file to byte[] and save in DocumentVO
			attachment.setBlobBytes(FileUtils.readFileToByteArray(file));
			attachment.setDocument(file);
			return attachment;
		} catch (DataAccessException dae) {
			throw new DataServiceException(
					"Unable to retrieve context root for file system persistence",
					dae);
		} catch (IOException ioe) {
			throw new DataServiceException("Unable to retrieve file ("
					+ fullFileName + ")from file system", ioe);
		}

	}

	// To delete Document
	public DocumentVO removeDocumentByDocumentId(Integer documentId) {
		DocumentVO document = providerLeadManagementDao
				.removeDocumentById(documentId);
		if (null != document) {
			try {
				document = deleteDocument(document);
			} catch (DataServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return document;
	}

	public DocumentVO deleteDocument(DocumentVO document)
			throws DataServiceException {
		String newFileName = null;
		document.setDocWriteReadInd(getDocWriteReadInd());

		String fileExtn = StringUtils.substring(document.getFileName(),
				document.getFileName().lastIndexOf("."));
		newFileName = StringUtils.substring(document.getFileName(), 0, document
				.getFileName().lastIndexOf("."));
		if (document.getFileName().length() > 200) {
			newFileName = StringUtils.substring(newFileName, 0, 200)
					+ "_DELETED_" + document.getDocumentId();
		} else {
			newFileName = newFileName + "_DELETED_" + document.getDocumentId();
		}
		newFileName = newFileName + fileExtn;

		if (document.getDocWriteReadInd().equals(
				DocumentConstants.DOC_READ_WRITE_FROM_DB)
				|| StringUtils.isEmpty(document.getDocPath())) {
			document.setDocPath(StringUtils.EMPTY);
		} else if (document.getDocWriteReadInd().equals(
				DocumentConstants.DOC_WRITE_TO_DB_AND_FS)
				|| document.getDocWriteReadInd().equals(
						DocumentConstants.DOC_READ_WRITE_FROM_FS)) {
			String oldFilePath = document.getDocPath();
			String newFilePath = oldFilePath.substring(0,
					oldFilePath.lastIndexOf("/") + 1)
					+ newFileName;
			File file = new File(document.getDocPath());
			file.renameTo(new File(newFilePath));
			document.setDocPath(newFilePath);
		}
		document.setFileName(newFileName);
		try {
			documentDao.update("document.delete", document);
		} catch (DataAccessException e) {
			throw new DataServiceException("Unable to delete file ("
					+ newFileName + ")from file system", e);
		}
		return document;
	}

	// To uploadDocument
	public DocumentVO uploadDocument(DocumentVO documentVO)
			throws DataServiceException {
		String leadId = documentVO.getLeadId();
		String createdBy=documentVO.getCreatedBy();
		DocumentVO document = createDoc(documentVO);
		if (null != document) {
			document.setDocumentId(document.getDocumentId());
			document.setLeadId(leadId);
			document.setCreatedBy(createdBy);
			Integer leadDocId = providerLeadManagementDao
					.updateDocumentId(document);
			document.setLeadDocId(leadDocId);
		}
		return document;
	}

	public DocumentVO createDoc(DocumentVO documentVO)
			throws DataServiceException {
		Integer docId = null;
		String docWriteReadInd = null;
		// code to store video id
		if (null != documentVO.getDocCategoryId()
				&& Constants.DocumentTypes.CATEGORY.VIDEO == documentVO
						.getDocCategoryId().intValue()) {
			docId = createDocumentMetadataInDb(documentVO);
			documentVO.setDocumentId(docId);
			return documentVO;
		}
		// Retrieving the document Write Read indicator from application
		// properties
		docWriteReadInd = getDocWriteReadInd();
		documentVO.setDocWriteReadInd(docWriteReadInd);

		// Creating the document meta data in database
		documentVO.setDocPath(null);
		docId = createDocumentMetadataInDb(documentVO);
		documentVO.setDocumentId(docId);

		if (docWriteReadInd.equals(DocumentConstants.DOC_READ_WRITE_FROM_DB)) {
			updateDocumentBlobInDb(documentVO);
		} else if (docWriteReadInd
				.equals(DocumentConstants.DOC_WRITE_TO_DB_AND_FS)) {
			documentVO.setDocPath(buildDocumentPath(documentVO));
			updateDocumentBlobInDb(documentVO);
			createDocumentInFs(documentVO);
		} else if (docWriteReadInd
				.equals(DocumentConstants.DOC_READ_WRITE_FROM_FS)) {
			documentVO.setDocPath(buildDocumentPath(documentVO));
			createDocumentInFs(documentVO);
		}
		return documentVO;

	}

	private Integer createDocumentMetadataInDb(DocumentVO documentVO)
			throws DataServiceException {
		Integer docId = null;
		try {
			docId = (Integer) documentDao.insert("document.insert", documentVO);
		} catch (Exception e) {
			throw new DataServiceException(
					"Unable to create document metadata in DB: "
							+ documentVO.getFileName(), e);
		}
		return docId;
	}

	private void updateDocumentBlobInDb(DocumentVO documentVO)
			throws DataServiceException {
		try {
			if (documentVO.getBlobBytes() == null
					&& documentVO.getDocument() == null) {
				throw new DataServiceException("Unable to convert file "
						+ documentVO.getFileName() + " to byte[]");
			} else if (documentVO.getDocument() != null) {
				documentVO.setBlobBytes(getBytesFromFile(documentVO
						.getDocument()));
			}
		} catch (IOException e) {
			throw new DataServiceException("Unable to convert file "
					+ documentVO.getFileName() + " to byte[]", e);
		}
		try {
			documentDao.update("document.updateBlob", documentVO);
		} catch (Exception e) {
			throw new DataServiceException(
					"Unable to update blob for document: "
							+ documentVO.getFileName(), e);
		}
	}

	private void createDocumentInFs(DocumentVO documentVO)
			throws DataServiceException {
		try {
			File file = new File(documentVO.getDocPath());
			if (null != documentVO.getDocument()) {
				FileUtils.copyFile(documentVO.getDocument(), file);
			} else if (documentVO.getBlobBytes() != null) {
				FileUtils.writeByteArrayToFile(file, documentVO.getBlobBytes());
			} else {
				// SL-10106 - think this is related to having video documents
				// that have neither blob or document
				logger.info("trying to write file to filesystem, but document doesn't have a blob or file documentId["
						+ documentVO.getDocumentId() + "]");
			}
			// Now update the new docPath in database
			updateDocPathInDb(documentVO);
			// create thumbnail image for provider profile image in FS
			// we don't to insert thumbnail details into document table
			if (null != documentVO.getDocCategoryId()
					&& documentVO.getDocCategoryId().intValue() == Constants.DocumentTypes.CATEGORY.PROVIDER_PHOTO
					&& StringUtils.contains(documentVO.getFormat(),
							Constants.ThumbNail.IMAGE_TYPE_STRING)) {
				DocumentVO thumbDoc = createThumbImage(documentVO);
				File thumbFile = new File(thumbDoc.getDocPath());
				if (thumbDoc.getBlobBytes() != null) {
					FileUtils.writeByteArrayToFile(thumbFile,
							thumbDoc.getBlobBytes());
				}
			}
		} catch (IOException e) {
			throw new DataServiceException("Unable to save document", e);
		}
	}

	private DocumentVO createThumbImage(DocumentVO documentVO)
			throws IOException {
		DocumentVO thumbDoc = new DocumentVO();
		thumbDoc = mapDocument(documentVO);
		String fileName = thumbDoc.getDocPath();
		int extStart = fileName.lastIndexOf(Constants.DOT);
		String fileExtn = fileName.substring(extStart + 1);
		String fileNameWithOutExtn = fileName.substring(0, extStart);
		StringBuilder newFileName = new StringBuilder(fileNameWithOutExtn);
		newFileName.append(Constants.ThumbNail.THUMBNAIL_SUFFIX);
		newFileName.append(Constants.DOT);
		newFileName.append(fileExtn);
		thumbDoc.setDocPath(newFileName.toString());
		try {
			if (null != thumbDoc.getBlobBytes()) {
				thumbDoc.setBlobBytes(resizeoImage(thumbDoc.getBlobBytes(),
						Constants.ThumbNail.THUMB_IMAGE_WIDTH,
						Constants.ThumbNail.THUMB_IMAGE_HEIGHT));
			} else if (null != thumbDoc.getDocument()) {
				byte[] imageBytes = FileUtils.readFileToByteArray(thumbDoc
						.getDocument());
				thumbDoc.setBlobBytes(resizeoImage(imageBytes,
						Constants.ThumbNail.THUMB_IMAGE_WIDTH,
						Constants.ThumbNail.THUMB_IMAGE_HEIGHT));
			}
			thumbDoc.setDocSize(new Long(thumbDoc.getBlobBytes().length));
			thumbDoc.setDocument(null);
		} catch (Exception e) {
			logger.error("Exception in createThumbImage", e);
			throw new IOException();
		}
		return thumbDoc;
	}

	private static byte[] getBytesFromFile(File file)
			throws DataServiceException, IOException {

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
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while ((offset < bytes.length)
				&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		is.close();
		return bytes;

	}

	public String buildDocumentPath(DocumentVO docVO)
			throws DocumentRetrievalException {
		String docPath = "";
		int yyyy = 0;
		int mm = 0;
		int dd = 0;
		String feedbackIndicator = docVO.getDocSource();
		logger.info("Feedback Indicator" + feedbackIndicator);
		if (null != feedbackIndicator
				&& feedbackIndicator
						.equalsIgnoreCase(MPConstants.PROVIDER_FEEDBACK_INDICATOR)) {
			String feedBackScreenShotSaveLocation = "";
			try {
				feedBackScreenShotSaveLocation = documentDao
						.getAppPropertiesValue(MPConstants.FEEDBACK_SAVE_LOCATION);
				logger.info("Feedback screen shot location"
						+ feedBackScreenShotSaveLocation);
				docVO.setDocPath(feedBackScreenShotSaveLocation);
			} catch (DataServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			getDocSavePath(docVO);
		}
		// set the temp file path
		docVO.setFilePath(docVO.getDocPath());
		// buid the file path
		Integer ss = Integer.valueOf(docVO.getDocumentId().intValue() % 100);

		if (null != docVO.getCreatedDate()) {

			Timestamp createdDate = docVO.getCreatedDate();
			Calendar gCal = new GregorianCalendar();
			gCal.setTime(createdDate);
			yyyy = gCal.get(Calendar.YEAR);
			mm = gCal.get(Calendar.MONTH) + 1;
			dd = gCal.get(Calendar.DATE);
		} else {
			Calendar cal = Calendar.getInstance();
			yyyy = cal.get(Calendar.YEAR);
			mm = cal.get(Calendar.MONTH) + 1;
			dd = cal.get(Calendar.DATE);
		}
		String path = "";
		if (null != docVO.getDocCategoryId()
				&& docVO.getDocCategoryId().intValue() == Constants.DocumentTypes.CATEGORY.PROVIDER_PHOTO) {
			path = Constants.PROVIDER_PHOTO_FOLDER + "/" + yyyy + "/" + mm
					+ "/" + dd + "/" + ss + "/";
		} else {
			path = yyyy + "/" + mm + "/" + dd + "/" + ss + "/";
		}

		// Save the file in the file system
		docPath = docVO.getDocPath() + path + docVO.getDocumentId() + "_"
				+ docVO.getFileName();
		logger.info("The final doc path is " + docPath);
		return docPath;
	}

	public void getDocSavePath(DocumentVO documentVO)
			throws DocumentRetrievalException {
		String saveDocPathKey = Constants.AppPropConstants.DOC_SAVE_LOC;
		try {
			ApplicationPropertiesVO prop = applicationPropertiesDao
					.query(saveDocPathKey);
			documentVO.setDocPath(prop.getAppValue());
		} catch (DataAccessException e) {
			throw new DocumentRetrievalException("Unable to retrieve "
					+ saveDocPathKey + " from application_properties", e);
		} catch (DataNotFoundException e) {
			throw new DocumentRetrievalException("Unable to retrieve "
					+ saveDocPathKey + " from application_properties", e);
		}
	}

	private void updateDocPathInDb(DocumentVO documentVO)
			throws DataServiceException {
		try {
			documentDao.update("document.updateDocPath", documentVO);
		} catch (Exception e) {
			throw new DataServiceException(
					"Unable to update doc path for documentId: "
							+ documentVO.getDocumentId() + " due to :", e);
		}
	}

	public static byte[] resizeoImage(byte[] inImagedata, int targetWidth,
			int targetHeight) {

		int scaledWidth;
		int scaledHeight;

		Image sourceImage = new ImageIcon(inImagedata).getImage();
		float targetscale = 0;
		float scale1 = (int) ((float) targetWidth
				/ (float) (sourceImage.getWidth(null)) * 100);
		float scale2 = (int) ((float) targetHeight
				/ (float) (sourceImage.getHeight(null)) * 100);
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
		//I am added for jdk1.8
		try {
			ImageIO.write(resizedImage, "JPEG", out);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image ");
			logger.error(e);
		}
		//I kept comment for jdk1.8
		/*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		try {
			encoder.encode(resizedImage);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" error  resizing image ");
			logger.error(e);
		}
*/
		byte[] b = out.toByteArray();

		return b;
	}

	public void insertHistoryForLead(LeadHistoryVO leadHistoryVO) {
		try {
			providerLeadManagementDao.insertHistoryForLead(leadHistoryVO);
		} catch (Exception e) {
			logger.error(
					"ProviderLeadManagementService:Exception while inserting history into lead history:",
					e);
		}
	}
	
	public void insertHistoryForLeadProvider(LeadHistoryVO leadHistoryVO) {
		try {
			providerLeadManagementDao.insertHistoryForLeadProvider(leadHistoryVO);
		} catch (Exception e) {
			logger.error(
					"ProviderLeadManagementService:Exception while inserting history into lead history:",
					e);
		}
	}

	private static BufferedImage scaleImage(Image sourceImage, int width,
			int height) {
		ImageFilter filter = new ReplicateScaleFilter(width, height);
		ImageProducer producer = new FilteredImageSource(
				sourceImage.getSource(), filter);
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

	private DocumentVO mapDocument(DocumentVO documentVO) {
		DocumentVO documentVo = new DocumentVO();
		documentVo.setBlobBytes(documentVO.getBlobBytes());
		documentVo.setDocPath(documentVO.getDocPath());
		documentVo.setFileName(documentVO.getFileName());
		documentVo.setDocument(documentVO.getDocument());
		return documentVo;
	}

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<CallCustomerReasonCodeVO> getCustInterestedReasonCodes()throws BusinessServiceException {
		List<CallCustomerReasonCodeVO> custInterested = null;
		try{
			custInterested=providerLeadManagementDao.getCustInterestedReasonCodes();
		}catch(DataServiceException e){
			logger.info("Exception in getting the available reason codes for Call customer"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return custInterested;
	}

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<CallCustomerReasonCodeVO> getCustNotInterestedReasonCodes()throws BusinessServiceException {
		List<CallCustomerReasonCodeVO> custNotInterested = null;
		try{
			custNotInterested=providerLeadManagementDao.getCustNotInterestedReasonCodes();
		}catch(DataServiceException e){
			logger.info("Exception in getting the available reason codes for Call customer"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return custNotInterested;
	}

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<CallCustomerReasonCodeVO> getcustNotRespondedReasonCodes()throws BusinessServiceException {
		List<CallCustomerReasonCodeVO> custNotResponded = null;
		try{
			custNotResponded=providerLeadManagementDao.getcustNotRespondedReasonCodes();
		}catch(DataServiceException e){
			logger.info("Exception in getting the available reason codes for Call customer"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return custNotResponded;
	}
		
		/**
		 * @param leadId
		 * @return
		 *  to get the details of all matched firms for lead
		 * 
		 */
		public List<ProviderInfoVO> getProviderInfoForLead(String leadId) {
			// TODO Auto-generated method stub
			List<ProviderInfoVO> providerInfoVOs= providerLeadManagementDao.getProviderInfoForLead(leadId);
			return providerInfoVOs;
		}
		public List<LeadHistoryVO> getHistoryListForLead(String leadId) {
			// TODO Auto-generated method stub
			List<LeadHistoryVO> historyVOListForLead= providerLeadManagementDao.getHistoryListForLead(leadId);
			return historyVOListForLead;
		}

		/**
		 * @param leadId
		 * @return
		 * 
		 * to get the details of all cancelled firms for lead
		 * 
		 * 
		 */
		public List<ProviderInfoVO> getCancelledProviderInfo(String leadId) {
			// TODO Auto-generated method stub
			List<ProviderInfoVO> providerInfoVOs= providerLeadManagementDao.getCancelledProviderInfo(leadId);
			return providerInfoVOs;
		}
		// For Provider cancel Lead Reason Codes
		public Map<String, Integer> getProviderLeadReasons(String reason,String roleType) {
			Map<String, Integer> leadReasons = providerLeadManagementDao.getProviderLeadReasons(reason,roleType);
			return leadReasons;

		}
		// For Customer cancel Lead Reason Codes
		public Map<String, Integer> getCustomerLeadReasons(String reason,String roleType) {
			Map<String, Integer> leadReasons = providerLeadManagementDao.getCustomerLeadReasons(reason,roleType);
			return leadReasons;

		}
		//To get BusinessName for the Vendor
		public String getBusinessNameOfVendor(Integer vendorId) throws BusinessServiceException {
			String businessName="";
			try{
			businessName=providerLeadManagementDao.getBusinessNameOfVendor(vendorId);
			}
			catch(DataServiceException e){
				logger.info("Exception in getting business name"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return businessName;
		}
		
		/**@Description:To get actual lead firm status for stale lead 
		 * @param leadId
		 * @param vendorid
		 * @return String
		 */
		public String getLeadfirmStatus(String leadId, String vendorid) {
			String leadFirmStatus="";
			try{
			 leadFirmStatus = providerLeadManagementDao.getLeadFirmStatus(leadId,vendorid);
			}catch(DataServiceException e){
			 logger.info("Exception in getting Actual lead firm status for stale lead"+e.getMessage());
			}
			return leadFirmStatus;
		}
		
	public ProviderLeadManagementDao getProviderLeadManagementDao() {
		return providerLeadManagementDao;
	}

	public void setProviderLeadManagementDao(
			ProviderLeadManagementDao providerLeadManagementDao) {
		this.providerLeadManagementDao = providerLeadManagementDao;
	}

	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}

	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}

	public DocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	

	

}
