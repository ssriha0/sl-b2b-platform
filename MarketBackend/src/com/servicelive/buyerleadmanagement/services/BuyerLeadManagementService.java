package com.servicelive.buyerleadmanagement.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.constants.DocumentConstants;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadLookupVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadManagementCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadDetailsCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.buyerleadmanagement.BuyerLeadManagementDao;
import com.newco.marketplace.persistence.dao.document.DocumentDao;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.utils.DocumentUtils;

/**
 * @author Madhup_Chand
 * 
 */
public class BuyerLeadManagementService {
	private BuyerLeadManagementDao buyerLeadManagementDao;
	private IApplicationPropertiesDao applicationPropertiesDao;
	private DocumentDao documentDao;
	private static final Logger logger = Logger
			.getLogger(BuyerLeadManagementService.class.getName());

	public List<LeadInfoVO> getBuyerLeadManagementDetails(
			BuyerLeadManagementCriteriaVO buyerLeadManagementPagination) {
		List<LeadInfoVO> leadDetails = new ArrayList<LeadInfoVO>();
		leadDetails = buyerLeadManagementDao
				.getBuyerLeadManagementDetails(buyerLeadManagementPagination);
		return leadDetails;
	}

	public List<BuyerLeadLookupVO> loadStates() {
		List<BuyerLeadLookupVO> buyerLeadStates = new ArrayList<BuyerLeadLookupVO>();
		buyerLeadStates = buyerLeadManagementDao.loadStates();
		return buyerLeadStates;
	}

	public BuyerLeadManagementDao getBuyerLeadManagementDao() {
		return buyerLeadManagementDao;
	}

	public void setBuyerLeadManagementDao(
			BuyerLeadManagementDao buyerLeadManagementDao) {
		this.buyerLeadManagementDao = buyerLeadManagementDao;
	}

	public Integer getBuyerLeadManagementCount(
			BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO) {
		Integer leadManagementCount = buyerLeadManagementDao
				.getBuyerLeadManagementCount(buyerLeadManagementCriteriaVO);
		return leadManagementCount;
	}
	 public Integer selectShopYourWayPointsForLeadMember(String leadId,String memberShipId){
		 Integer shopYourWayPointsForLeadMemberPoint = buyerLeadManagementDao
					. selectShopYourWayPointsForLeadMember(leadId, memberShipId);
			return shopYourWayPointsForLeadMemberPoint;
	 }
	//

	public LeadInfoVO getBuyerLeadManagementSummary(String leadId) {
		LeadInfoVO leadDetails = new LeadInfoVO();
		leadDetails = buyerLeadManagementDao
				.getBuyerLeadManagementSummary(leadId);
		return leadDetails;
	}

	public List<ProviderInfoVO> getBuyerLeadManagementProviderInfo(String leadId) {
		List<ProviderInfoVO> ProviderDetails = new ArrayList<ProviderInfoVO>();
		ProviderDetails = buyerLeadManagementDao
				.getBuyerLeadManagementProviderInfo(leadId);
		return ProviderDetails;
	}
	public List<ProviderInfoVO> getBuyerLeadManagementCancelledProviderInfo(String leadId) {
		List<ProviderInfoVO> ProviderDetails = new ArrayList<ProviderInfoVO>();
		ProviderDetails = buyerLeadManagementDao
				.getBuyerLeadManagementCancelledProviderInfo(leadId);
		return ProviderDetails;
	}

	public List<SLLeadNotesVO> getBuyerLeadViewNotes(BuyerLeadManagementCriteriaVO buyerLeadViewNotesCriteriaVO) {
		List<SLLeadNotesVO> leadNotes = new ArrayList<SLLeadNotesVO>();
		leadNotes = buyerLeadManagementDao.getBuyerLeadViewNotes(buyerLeadViewNotesCriteriaVO);
		return leadNotes;
	}

	public ProviderInfoVO getBuyerLeadManagementResourceInfo(ProviderInfoVO leadProviderDetails) {
		ProviderInfoVO ResourceDetails = new ProviderInfoVO();
		ResourceDetails = buyerLeadManagementDao
				.getBuyerLeadManagementResourceInfo(leadProviderDetails);
		return ResourceDetails;
	}
	public ProviderInfoVO getBuyerLeadManagementResourceScore(ProviderInfoVO resourceDetails) {
		ProviderInfoVO resourceDetailsScore = new ProviderInfoVO();
		resourceDetailsScore = buyerLeadManagementDao
				.getBuyerLeadManagementResourceScore(resourceDetails);
		return resourceDetailsScore;
	}
	public void insertShowYourWayRewardsHistoryForLeadMember(
			LeadHistoryVO leadHistoryVO) {
		buyerLeadManagementDao
				.insertShowYourWayRewardsHistoryForLeadMember(leadHistoryVO);
	}

	public void addOrRevokeShopYourWayPoints(int points, String leadId,
			String menberShipId, String modifiedBy) {
		buyerLeadManagementDao.addOrRevokeShopYourWayPoints(points, leadId,
				menberShipId, modifiedBy);
	}

	public void updateBuyerLeadCustomerInfo(
			LeadDetailsCriteriaVO leadDeatilsCriteriaVO) {
		buyerLeadManagementDao
				.updateBuyerLeadCustomerInfo(leadDeatilsCriteriaVO);
	}

	// For Lead History
	public List<SLLeadHistoryVO> getBuyerLeadHistory(String leadId) {
		List<SLLeadHistoryVO> leadHistory = new ArrayList<SLLeadHistoryVO>();
		leadHistory = buyerLeadManagementDao.getBuyerLeadHistory(leadId);
		return leadHistory;
	}

	// For Lead History
	public List<SLLeadHistoryVO> getBuyerLeadHistory(String leadId,
			String actionId) {
		List<SLLeadHistoryVO> leadHistory = new ArrayList<SLLeadHistoryVO>();
		leadHistory = buyerLeadManagementDao.getBuyerLeadHistory(leadId,
				actionId);
		return leadHistory;
	}

	// For Lead Add Note Category
	public List<String> getBuyerLeadNoteCategory() {
		List<String> leadReasons = buyerLeadManagementDao
				.getBuyerLeadNoteCategory();
		return leadReasons;
	}

	// For Lead Reason Codes
	public Map<String, Integer> getBuyerLeadReasons(String reason,String roleType) {
		Map<String, Integer> leadReasons = buyerLeadManagementDao
				.getBuyerLeadReasons(reason,roleType);
		return leadReasons;

	}

	// For Lead Reason Codes
	public List<String> getLeadReasons(String reason) {
		List<String> leadReasons = buyerLeadManagementDao
				.getLeadReasons(reason);
		return leadReasons;
	}

	// Get rewardPoints
	public Map<String, String> getRewardPoints() {
		Map<String, String> rewardPointsLst = buyerLeadManagementDao
				.getRewardPoints();
		return rewardPointsLst;
	}

	//

	// to get the documents for a lead
	public List<BuyerLeadAttachmentVO> getAttachmentDetails(String leadId) {
		List<BuyerLeadAttachmentVO> attachments = null;
		try {
			attachments = buyerLeadManagementDao.getAttachmentDetails(leadId);
			if (null != attachments) {
				for (BuyerLeadAttachmentVO attachment : attachments) {
					Double docSize = DocumentUtils.convertBytesToKiloBytes(
							attachment.getDocSize().intValue(), true);
					attachment.setDocSize(docSize);
				}
			}
		} catch (Exception e) {
			logger.info("Unable to retrieve documents for the lead: " + leadId,
					e);
		}
		return attachments;
	}

	// get document by document ID
	public BuyerLeadAttachmentVO retrieveDocumentByDocumentId(Integer documentId) {
		BuyerLeadAttachmentVO document = buyerLeadManagementDao
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
