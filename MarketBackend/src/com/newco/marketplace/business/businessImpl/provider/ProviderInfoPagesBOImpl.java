/**
 *
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.provider.ICompanyProfileBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.business.iBusiness.provider.IPublicProfileBO;
import com.newco.marketplace.business.iBusiness.provider.ISkillAssignBO;
import com.newco.marketplace.business.iBusiness.provider.ITeamProfileBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingByQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.dao.d2cproviderportal.ID2CProviderPortalDao;
import com.newco.marketplace.persistence.daoImpl.document.IDocumentDao;
import com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.persistence.iDao.survey.SurveyDAO;
import com.newco.marketplace.util.DocumentUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * @author hoza
 *
 */
public class ProviderInfoPagesBOImpl extends ABaseBO implements IProviderInfoPagesBO{
	private ProviderSearchDao providerSearchDao;
	private IPublicProfileBO publicProfileBO;
	private ICompanyProfileBO companyProfileBO;
	private ISkillAssignBO skillAssingBO;
	private ITeamProfileBO teamProfileBO;
	private SurveyDAO surveyRatingsDAO;
	private IProviderProfileDocumentDao profileDocDAO;
	private IDocumentDao resourceDocumentDao;
	private IDocumentBO documentBO;
	private ISurveyBO survey;
	
	private static final Logger logger = Logger.getLogger(ProviderInfoPagesBOImpl.class.getName());
	public PublicProfileVO getProviderDetails(Integer resourceId)
			throws BusinessServiceException {
		PublicProfileVO  requestVo = new PublicProfileVO();
		PublicProfileVO	 responseVO = new PublicProfileVO();
		requestVo.setResourceId(resourceId);
		try {
			responseVO = publicProfileBO.getPublicProfile(requestVo);
			//dataDto.setProviderPublicInfo(responseVO);
			if(responseVO != null && responseVO.getVendorId() != null) {

				CompanyProfileVO companyvo = getCompanyProfileInfo(responseVO);
				updateTotalSoCompleted(companyvo);
				LocationVO zipData = providerSearchDao.getZipLatAndLong(responseVO.getDispAddZip());
				responseVO.setProviderLatitueLongi(zipData);
				updateCreditalsInfo(responseVO);
				updateResourceSkillsInsdustryList(responseVO);

			// SLT-1772
			//	List <SurveyRatingByQuestionVO>resourceratings = getResourceRating(resourceId);
				//addOverallRating(resourceratings);
			//	List <SurveyRatingByQuestionVO>vendorRatings = getVendorRating(responseVO.getVendorId());
				List <CustomerFeedbackVO> feedbacks =  getResourceFeedback(resourceId,SurveyConstants.ENTITY_BUYER_ID, 0);
				updateStarImageForFeedback(feedbacks);
				responseVO.setFeedbacks(feedbacks);

				//addOverallRating(vendorRatings);

			//	updateStarImageNumber(resourceratings);
			//	updateStarImageNumber(vendorRatings);
			//	responseVO.setSurveyratings(resourceratings);
			//	companyvo.setSurveyRatings(vendorRatings);

				//SL Rating
				String entityId=companyvo.getVendorId().toString();
				SurveyRatingsVO surveyRatings = getRatingsInfo(entityId, "0",OrderConstants.PROVIDER_ROLEID);
				if(surveyRatings!=null && null != surveyRatings.getHistoricalRating())
				{
					responseVO.setHistoricRating(surveyRatings.getHistoricalRating());
					Integer	ratingNumber = calculateScoreNumber(surveyRatings.getHistoricalRating());
					responseVO.setRatingNumber(ratingNumber);
				}

				else
				{
					responseVO.setHistoricRating(OrderConstants.SL_NOTRATED);	
				}
			}
		} catch (BusinessServiceException e) {
			logger.error( "Delegate could not retrive info for resource id ="+resourceId);
			throw new BusinessServiceException("Delegate could not retrive info for resource id ="+resourceId,e);

		}
		/*catch (DataServiceException e)
		{
			logger.error("Delegate could not retrieve zip info for resource id ="+resourceId);
			throw new BusinessServiceException("Delegate could not retrieve zip info for resource id ="+resourceId,e);
		}*/
		return responseVO;
	}

	/**
	 * Returns the rating count 
	 * @param score
	 * @return
	 */
	private Integer calculateScoreNumber(double score)
	{
		//Formula for scoreNumber => {intValue / 0.25} + {number associated 
		//with fractionalValue based on which range it belongs to}
		int scoreNumber = (int)(score / 0.25);
		double fractionValue = score % 0.25;

		if(fractionValue > 0.12)
			scoreNumber++;

		return scoreNumber;
	}

	/**
	 * Returns the overall rating
	 * @param entityId
	 * @param resourceId
	 * @param entityTypeId
	 * @return
	 * @throws BusinessServiceException
	 */
	private SurveyRatingsVO getRatingsInfo(String entityId, String resourceId, Integer entityTypeId) throws BusinessServiceException{
		// Rating info:		
		SurveyRatingsVO surveyRatings = null;
		int entTypeId = 0;		
		if (entityTypeId != null){
			entTypeId = entityTypeId.intValue();
		}			
		try{
			//SLT-1751
			surveyRatings = survey.getRatings(entTypeId,Integer.parseInt(entityId),((resourceId != null)? Integer.parseInt(resourceId) : 0));
		}catch(DataServiceException dse){
			logger.error("Could not retrieve ratings info in getRatingInfo - database error. ",  dse);
			throw new BusinessServiceException("Could not retrieve ratings info in getRatingInfo - database error. " , dse);
		}catch(NumberFormatException nfe){
			logger.error("Could not parse the string into a numeric in getRatingsInfo. " +
					"Check entityId and resourceId to make sure there only numeric characters.",  nfe);
			throw new BusinessServiceException("Could not parse the string into a numeric in getRatingsInfo. " +
					"Check entityId and resourceId to make sure there only numeric characters.",  nfe);
		}
		return surveyRatings;
	}

	private CompanyProfileVO getCompanyProfileInfo(PublicProfileVO responseVO)
			throws BusinessServiceException {
		CompanyProfileVO companyvo = companyProfileBO.getCompleteProfile(responseVO.getVendorId().intValue());
		responseVO.setParentVendor(companyvo);
		updateCompanyCreditalsInfo(companyvo);
		UserProfile userprofile = new UserProfile();
		userprofile.setResourceId(responseVO.getVendorId());
		userprofile.setVendorId(responseVO.getVendorId());
		companyvo.setTeamMembers(getTeamMembers(userprofile));
		updateTotalSoCompleted(companyvo);
		return companyvo;
	}

	public PublicProfileVO getProviderFirmDetails(Integer vendorId)throws BusinessServiceException{
		PublicProfileVO providerFirmInfo = new PublicProfileVO();
		providerFirmInfo.setVendorId(vendorId);
		getCompanyProfileInfo(providerFirmInfo);
		return providerFirmInfo;
	}



	/**
	 * Required for provider search service.
	 * @param resourceId
	 * @param count
	 * @return
	 */
	public List <CustomerFeedbackVO> getCustomerFeedbacks(int resourceId, int count) throws BusinessServiceException{		
		return getResourceFeedback(resourceId,SurveyConstants.ENTITY_BUYER_ID, count);		
	}

	/**
	 * This would update the display list order..
	 * @param responseVO
	 * @throws BusinessServiceException
	 */
	private void updateResourceSkillsInsdustryList(PublicProfileVO	 responseVO)throws BusinessServiceException{
		Integer primaryind= responseVO.getParentVendor().getPrimaryIndustryId();
		if(primaryind > 0 ) {

			List<SkillNodeVO> newList = new ArrayList<SkillNodeVO>();
			List<SkillNodeVO> skills = responseVO.getResourceSkillList();
			for(SkillNodeVO vo : skills){
				if(vo.getNodeId() == primaryind){
					newList.add(vo);
				}
			}

			for(SkillNodeVO vo : skills){
				if(vo.getNodeId() != primaryind){
					newList.add(vo);
				}
			}
			responseVO.setResourceSkillList(newList);
		}

	}
	private void updateTotalSoCompleted(CompanyProfileVO companyvo){
		Integer totalSoCompleted = new Integer(0);
		for(TeamMemberVO vo : companyvo.getTeamMembers()){
			if(vo != null && vo.getTotalSoCompleted() != null) {

				totalSoCompleted += vo.getTotalSoCompleted();
			}
		}
		companyvo.setTotalSoCompleted(totalSoCompleted);
	}
	private List<TeamMemberVO> getTeamMembers(UserProfile userProfile) throws BusinessServiceException {
		List<TeamMemberVO> teamList = teamProfileBO.getTeamMemberList(userProfile);

		return teamList;
	}
	private void updateCreditalsInfo(PublicProfileVO	 responseVO) {
		List<TeamCredentialsVO> creds = responseVO.getCredentialsList();
		for(TeamCredentialsVO vo : creds   ) {

			if(OrderConstants.TEAM_CREDENTIAL_APPROVED == vo.getWfStateId()) {
				vo.setVerified(true);
			}
			//if()
		}
	}

	private void updateCompanyCreditalsInfo(CompanyProfileVO companyvo) {
		if(companyvo.getLicensesList() == null) return;
		List<LicensesAndCertVO> creds = companyvo.getLicensesList();
		for(LicensesAndCertVO vo : creds   ) {

			if(ProviderConstants.COMPANY_CREDENTIAL_APPROVAL.intValue() == vo.getWfStateId()) {
				vo.setVerified(true);
			}

		}
	}
	private void updateStarImageNumber(List<SurveyRatingByQuestionVO> ratingVos){
		for(SurveyRatingByQuestionVO vo : ratingVos){
			if(vo.getRating() != null){
				if(vo.getRating() >= 0 ) {
					vo.setStarImageId(UIUtils.calculateScoreNumber(vo.getRating()));
				}else if(vo.getRating() == -1){
					vo.setStarImageId(-1);
				}
			}

		}

	}

	private void updateStarImageForFeedback(List<CustomerFeedbackVO> feedbacks){
		for(CustomerFeedbackVO vo : feedbacks){
			if(vo.getOverallRatingScore() != null){
				if(vo.getOverallRatingScore() >= 0 ) {
					vo.setOverallRatingScoreImageId(UIUtils.calculateScoreNumber(vo.getOverallRatingScore()));
				}else if(vo.getOverallRatingScore() == -1){
					vo.setOverallRatingScoreImageId(-1);
				}
			}

		}

	}
	private void addOverallRating(List<SurveyRatingByQuestionVO> ratingVos){
		ratingVos.add(getOverallRating(ratingVos));
	}
//	private List<SurveyRatingByQuestionVO> getResourceRating(Integer resourceId)  throws BusinessServiceException{
//		List<SurveyRatingByQuestionVO> list = new ArrayList();
//		try {
//			list = surveyRatingsDAO.getVendorResourceRatings(resourceId);
//		} catch (DataServiceException e) {
//			logger.error( "BO could not retrive info for resource id ="+resourceId);
//			throw new BusinessServiceException("Delegate could not retrive info for resource id ="+resourceId,e);
//		}
//		return list;
//	}

	private List<CustomerFeedbackVO> getResourceFeedback(Integer resourceId, Integer entitypeId, int count)  throws BusinessServiceException{
		List<CustomerFeedbackVO> list = new ArrayList();
		try {
			list = surveyRatingsDAO.getVendorResourceFeedback(resourceId, entitypeId, count);
		} catch (DataServiceException e) {
			logger.error( "BO could not retrive info for resource id ="+resourceId);
			throw new BusinessServiceException("Delegate could not retrive info for resource id ="+resourceId,e);
		}
		return list;
	}
	/**
	 * @deprecated
	 * @param ratingVos
	 * @return
	 */
	private SurveyRatingByQuestionVO getOverallRating(List<SurveyRatingByQuestionVO> ratingVos) {
		SurveyRatingByQuestionVO overallvo = new SurveyRatingByQuestionVO();
		Double overallRating = new Double(-1);
		overallvo.setQuestion("Overall");
		overallvo.setQuestionId(new Integer(1000));
		overallvo.setRating(overallRating);

		int count  = 0;

		for(SurveyRatingByQuestionVO vo :ratingVos ){
			if(vo.getSurveyCount() > 0 ){
				overallRating = overallRating + vo.getRating();
				count++;
			}
		}
		if(count > 0 ){
			overallRating = (overallRating/count);
		}

		return overallvo;


	}
//	private List<SurveyRatingByQuestionVO> getVendorRating(Integer vendorId)  throws BusinessServiceException{
//		List<SurveyRatingByQuestionVO> list = new ArrayList();
//		try {
//			list = surveyRatingsDAO.getVendorRatingsGroupedByQuestion(vendorId);
//		} catch (DataServiceException e) {
//			logger.error( "BO could not retrive info for ratings for vendor id ");
//			throw new BusinessServiceException("BO could not retrive info for ratings for vendor id"+ e);
//		}
//		return list;
//	}


	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}


	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}


	public IPublicProfileBO getPublicProfileBO() {
		return publicProfileBO;
	}


	public void setPublicProfileBO(IPublicProfileBO publicProfileBO) {
		this.publicProfileBO = publicProfileBO;
	}


	public ISkillAssignBO getSkillAssingBO() {
		return skillAssingBO;
	}


	public void setSkillAssingBO(ISkillAssignBO skillAssingBO) {
		this.skillAssingBO = skillAssingBO;
	}


	public ICompanyProfileBO getCompanyProfileBO() {
		return companyProfileBO;
	}


	public void setCompanyProfileBO(ICompanyProfileBO companyProfileBO) {
		this.companyProfileBO = companyProfileBO;
	}


	public ITeamProfileBO getTeamProfileBO() {
		return teamProfileBO;
	}


	public void setTeamProfileBO(ITeamProfileBO teamProfileBO) {
		this.teamProfileBO = teamProfileBO;
	}

	public SurveyDAO getSurveyRatingsDAO() {
		return surveyRatingsDAO;
	}

	public void setSurveyRatingsDAO(SurveyDAO surveyRatingsDAO) {
		this.surveyRatingsDAO = surveyRatingsDAO;
	}

	/**
	 * @return the profileDocDAO
	 */
	public IProviderProfileDocumentDao getProfileDocDAO() {
		return profileDocDAO;
	}

	/**
	 * @param profileDocDAO the profileDocDAO to set
	 */
	public void setProfileDocDAO(IProviderProfileDocumentDao profileDocDAO) {
		this.profileDocDAO = profileDocDAO;
	}

	/**
	 * @return the resourceDocumentDao
	 */
	public IDocumentDao getResourceDocumentDao() {
		return resourceDocumentDao;
	}

	/**
	 * @param resourceDocumentDao the resourceDocumentDao to set
	 */
	public void setResourceDocumentDao(IDocumentDao resourceDocumentDao) {
		this.resourceDocumentDao = resourceDocumentDao;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO#getProviderPrimaryPhoto(java.lang.Integer)
	 */
	public ProviderDocumentVO getProviderPrimaryPhoto(Integer resourceId)
			throws BusinessServiceException {
		ProviderDocumentVO doc = new ProviderDocumentVO();
		try {
			doc = profileDocDAO.getPrimaryProviderPicture(resourceId);
			if( doc != null && doc.getDocumentId() != null)
				doc.setDocDetails(getDocumentDetails(doc.getDocumentId()));

		} catch (DataServiceException e) {
			logger.error( "BO could not retrive info for resource id ="+resourceId);
			throw new BusinessServiceException("Delegate could not retrive info for resource id ="+resourceId,e);
		}
		return doc;

	}

	public VendorDocumentVO getFirmLogo(Integer vendorId)
			throws BusinessServiceException {
		VendorDocumentVO doc = new VendorDocumentVO();
		try {
			doc = profileDocDAO.getLogoForFirm(vendorId);
			if( doc != null && doc.getDocumentId() != null)
				doc.setDocDetails(getDocumentDetails(doc.getDocumentId()));

		} catch (DataServiceException e) {
			logger.error( "BO could not retrive info for vendor id ="+vendorId);
			throw new BusinessServiceException("Delegate could not retrive info for vendor id ="+vendorId,e);
		}
		return doc;

	}
	public DocumentVO getDocumentDetails(Integer documentId)throws BusinessServiceException {
		DocumentVO detail = new DocumentVO();
		try {
			detail = (DocumentVO) resourceDocumentDao.retrieveDocumentByDocumentId(documentId);

		} catch (DataServiceException e) {
			logger.error( "BO could not retrive getDocumentDetails="+documentId);
			throw new BusinessServiceException("Delegate could not retrive info for document id ="+documentId,e);
		}
		return detail;
	}

	/**
	 * @return the documentBO
	 */
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	/**
	 * @param documentBO the documentBO to set
	 */
	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO#uploadResourcePicture(com.newco.marketplace.dto.vo.provider.ProviderDocumentVO)
	 */
	public ProcessResponse uploadResourcePicture(
			ProviderDocumentVO providerdocument)
					throws BusinessServiceException {
		//providerdocument expect aleast resource id, fileblob
		ProcessResponse pr = new ProcessResponse();
		DocumentVO detail = providerdocument.getDocDetails();
		try {
			//Validate here before uploading to DB
			try {
				if(!documentBO.isAllowedImageFormat(detail.getFormat()) ) {
					pr.setCode(OrderConstants.SO_DOC_INVALID_FORMAT);
					return pr;
				}
				if(!documentBO.isTotalImageSizeAllowed(detail.getDocSize())){
					pr.setCode(OrderConstants.SO_DOC_SIZE_EXCEEDED_RC);
					return pr;
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
				logger.error( "BO could not retrive update =");

			}

			//get List of all the resource document
			List<ProviderDocumentVO> allPhotos = profileDocDAO.getAllPicturesForProvider(providerdocument.getResourceId());
			ProviderDocumentVO updateSecondoryVO = null;
			List<ProviderDocumentVO> deleteVos = new ArrayList<ProviderDocumentVO>();
			for (ProviderDocumentVO vo : allPhotos) {
				if(vo.getPrimaryInd() && updateSecondoryVO  == null ){
					updateSecondoryVO = vo;
				}else {
					deleteVos.add(vo);
				}
			}
			
			//now insert the new guy as primary
			pr = documentBO.insertResourceDocument(detail);
			if ( pr.getObj() != null){
				Integer id = (Integer) pr.getObj();
				detail.setDocumentId(id);
				providerdocument.setDocumentId(id);
				ProviderDocumentVO vo = profileDocDAO.uploadProviderPicture(providerdocument);
				pr.setObj(vo);
			}

			//do the update now
			if(updateSecondoryVO != null) {
				updateSecondoryVO.setPrimaryInd(Boolean.FALSE);
				profileDocDAO.update(updateSecondoryVO);
			}

			//now delete the rest of the VOs
			for(ProviderDocumentVO deleteVo : deleteVos ){
				//TODO delete the Document table
				profileDocDAO.delete(deleteVo);
				documentBO.deleteResourceDocument(deleteVo.getDocumentId());
			}
		} catch (DataServiceException e) {
			logger.error( "BO could not retrive update =");
			throw new BusinessServiceException("Delegate could not retrive info for update id ",e);
		}
		return pr;
	}

	public ProcessResponse uploadFirmLogo(VendorDocumentVO vendorDocument) throws BusinessServiceException{

		ProcessResponse pr = new ProcessResponse();
		DocumentVO detail = vendorDocument.getDocDetails();
		// to save the firm logo in a specified location
		detail.setCompanyLogo(true);
		try {

			try {
				// validate the document
				if(!documentBO.isAllowedImageFormat(detail.getFormat()) ) {
					pr.setCode(OrderConstants.SO_DOC_INVALID_FORMAT);
					return pr;
				}
				if(!documentBO.isTotalImageSizeAllowed(detail.getDocSize())){
					pr.setCode(OrderConstants.SO_DOC_SIZE_EXCEEDED_RC);
					return pr;
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
				logger.error( "BO could not retrive update =");

			}
			// check whether if the firm logo already exist.
			VendorDocumentVO vendorLogo = profileDocDAO.getLogoForFirm(vendorDocument.getVendorId());
			// insert into the document table
			pr = documentBO.insertVendorDocument(detail);
			if ( pr.getObj() != null){
				VendorDocumentVO vo =null;
				Integer id = (Integer) pr.getObj();
				detail.setDocumentId(id);
				vendorDocument.setDocumentId(id);
				if(null!=vendorLogo && null!=vendorLogo.getDocumentId()){
					// update the vendor_document table
					vo =profileDocDAO.updateFirmLogo(vendorDocument);
					// delete the old document from document table
					documentBO.deleteVendorDocument(vendorLogo.getDocumentId());
				}else{
					// insert into the vedor_document table
					vo = profileDocDAO.uploadFirmLogo(vendorDocument);	
				}				
				pr.setObj(vo);
			}

		} catch (DataServiceException e) {
			logger.error( "BO could not retrive update =");
			throw new BusinessServiceException("Delegate could not retrive info for update id ",e);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			e.printStackTrace();
		}
		return pr;

	}	 


	public List <CustomerFeedbackVO> getCustomerFeedbacks(int resourceId, int startIndex, int endIndex, String sortColumn, String order,
			java.util.Date startDate, java.util.Date endDate,Double maxRating,Double minRating) throws BusinessServiceException {
		List<CustomerFeedbackVO> list = new ArrayList();
		try {
			list = surveyRatingsDAO.getVendorResourceFeedback(resourceId, SurveyConstants.ENTITY_BUYER_ID,
					startIndex, endIndex, sortColumn, order, startDate, endDate,maxRating,minRating);
		} catch (DataServiceException e) {
			logger.error( "BO could not retrive info for resource id ="+resourceId);
			throw new BusinessServiceException("Delegate could not retrive info for resource id ="+resourceId,e);
		}
		return list;		
	}

	public Integer getCustomerFeedbacksCount(int resourceId) throws BusinessServiceException{
		try {
			Integer count = surveyRatingsDAO.getVendorResourceFeedbackCount(resourceId, SurveyConstants.ENTITY_BUYER_ID);
			return count;
		} catch (DataServiceException e) {
			logger.error( "BO could not retrive info for resource id ="+resourceId);
			throw new BusinessServiceException("Delegate could not retrive count for resource id ="+resourceId,e);
		}
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

	public ISurveyBO getSurvey() {
		return survey;
	}

	public void setSurvey(ISurveyBO survey) {
		this.survey = survey;
	}

	public List<String> getAllProvidersForExternalCalenderSync() throws BusinessServiceException {
		List<String> allProvidersForExternalCalenderSync = new ArrayList<String>();
		try {
			allProvidersForExternalCalenderSync = providerSearchDao.getAllProvidersForExternalCalenderSync();
		} catch (DataServiceException e) {
			logger.error("unable to getAllProvidersForExternalCalenderSync from DB");
			throw new BusinessServiceException("getAllProvidersForExternalCalenderSync failed ",e);
		}
		return allProvidersForExternalCalenderSync;
	}
	

}
