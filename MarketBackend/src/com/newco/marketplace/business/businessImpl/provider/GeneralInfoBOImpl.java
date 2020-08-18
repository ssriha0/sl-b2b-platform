package com.newco.marketplace.business.businessImpl.provider;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.net.BCodec;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO;
import com.newco.marketplace.business.iBusiness.searchportal.ISearchPortalBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.util.TimeChangeUtil;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.TimeSlotDTO;
import com.newco.marketplace.vo.provider.UserProfile;

/**
 * @author KSudhanshu
 *
 * $Revision: 1.32 $ $Author: akashya $ $Date: 2008/05/21 22:54:26 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class GeneralInfoBOImpl extends ABaseBO implements IGeneralInfoBO {

	private static final Logger logger = Logger.getLogger(GeneralInfoBOImpl.class.getName());
	private ILookupDAO iLookupDAO;
	private IContactDao iContactDao;
	private IVendorResourceDao iVendorResourceDao;
	private IResourceScheduleDao iResourceScheduleDao;
	private ILocationDao iLocationDao;
	private IActivityRegistryDao activityRegistryDao;
	private IAuditBO auditBO;
	private IUserProfileDao iUserProfileDao;
	private IActivityRegistryBO activityRegistryBO;
	private IZipDao zipDao;
	private IEmailTemplateBO emailTemplateBO;
	
	//SLT-976
	private ISearchPortalBO searchPortalBO;

	private static final int ADDRESS_TYPE_WORK = 4;
	private static final int BG_NOT_STARTED = 7;


	/**
	 * @param lookupDAO
	 * @param contactDao
	 * @param vendorResourceDao
	 * @param resourceScheduleDao
	 * @param locationDao
	 * @param activityRegistryDao
	 * @param userProfileDao
	 * @param activityRegistryBO
	 * @param zipDao
	 */
	public GeneralInfoBOImpl(ILookupDAO lookupDAO, IContactDao contactDao,
			IVendorResourceDao vendorResourceDao,
			IResourceScheduleDao resourceScheduleDao, 
			ILocationDao locationDao,
			IActivityRegistryDao activityRegistryDao,
			IUserProfileDao userProfileDao,
			IActivityRegistryBO activityRegistryBO,
			IZipDao zipDao,
			IEmailTemplateBO emailTemplateBO) {
		iLookupDAO = lookupDAO;
		iContactDao = contactDao;
		iVendorResourceDao = vendorResourceDao;
		iResourceScheduleDao = resourceScheduleDao;
		iLocationDao = locationDao;
		this.activityRegistryDao = activityRegistryDao;
		iUserProfileDao = userProfileDao;
		this.activityRegistryBO = activityRegistryBO;
		this.zipDao = zipDao;
		this.emailTemplateBO = emailTemplateBO;

	}

	/**
	 * @param generalInfoVO
	 * @return
	 * @throws Exception
	 */
	public GeneralInfoVO saveUserInfo(GeneralInfoVO generalInfoVO)
			throws Exception {

		String resourceSsn = null;
		String resourceId = null;
		logger.info("Enterign GeneralInfoBOImpl.addUserInfo()");

		if (generalInfoVO == null) {
			logger.info("GeneralInfoVO request object was null.  Unable to add");
			throw new BusinessServiceException(
					"Request Can not be modified,Please Make Sure your request object has required values");
		}

		// Checking userId is already exist or not
		try {
			if (generalInfoVO.getUserName() != null && generalInfoVO.getUserName().trim().length()>0) {
				if (isUserAlreadyExist(generalInfoVO.getUserName(),generalInfoVO.getResourceId())) {
					throw new DuplicateUserException("The user ID("
							+ generalInfoVO.getUserName()
							+ ") has already been used.");
				}
			} else {
				generalInfoVO.setUserName(null);
			}
		} catch (DBException dae) {
			logger.error("DataAccessException thrown while looking up userId("
					+ generalInfoVO.getUserName() + ").", dae);
			throw new BusinessServiceException(
					"DataAccessException thrown while looking up userId("
							+ generalInfoVO.getUserName() + ").", dae);
		}

		// Encripting ssn number

		resourceSsn = generalInfoVO.getSsn();
		if (resourceSsn != null && resourceSsn.trim().length()>0){

			generalInfoVO.setSsn(CryptoUtil.encryptKeyForSSNAndPlusOne(resourceSsn));			
		} else {
			generalInfoVO.setSsn(null);
		}

		if (generalInfoVO.getResourceId() == null || "".equals(generalInfoVO.getResourceId().trim())) {

			try {
				// SL-19667 first time provider registration
				Integer bcCheckId=iContactDao.getBackgroundCheckIdWithSameContact(generalInfoVO);
				generalInfoVO = insertGeneralInfo(generalInfoVO);
				resourceId = generalInfoVO.getResourceId();
				// inserting default value in activity registry
				activityRegistryDao.insertResourceActivityStatus(resourceId);
				// updating activity registry
				activityRegistryDao.updateResourceActivityStatus(resourceId, ActivityRegistryConstants.RESOURCE_GENERALINFO);
				Integer sharedBackgroundCheckStateId=null;
				if(null!=bcCheckId){
					GeneralInfoVO generalInfo=new GeneralInfoVO();
					generalInfo.setBcCheckId(bcCheckId);
					generalInfo.setResourceId(resourceId);
					// SL-19667 get background check information from background check Id
					BackgroundCheckVO backgroundCheck=iContactDao.getBackgroundCheckInfo(bcCheckId);
					if(null!=backgroundCheck){
						generalInfo.setBcStateId(backgroundCheck.getBackgroundCheckStateId());
						sharedBackgroundCheckStateId=backgroundCheck.getBackgroundCheckStateId();
					}
					//  SL-19667 update bg_check_id and background_state_id of the resource to share the background check info.
					iContactDao.updateBcCheck(generalInfo);
					// SL-20356 provide background check history for sharing the background check

					GeneralInfoVO generalInformation=new GeneralInfoVO();
					generalInformation.setChangedComment(MPConstants.SHARE_BKND_CHK);
					generalInformation.setResourceId(resourceId);   
					iVendorResourceDao.insertBcHistory(generalInformation);


				}else{
					//  SL-19667 Insert background check information with the plusOneKey
					Integer backgroundCheckId=insertPlusOneKeyForBackgroundCheck(resourceSsn,resourceId);
					if(null!=backgroundCheckId){
						GeneralInfoVO generalInfo=new GeneralInfoVO();
						generalInfo.setBcCheckId(backgroundCheckId);
						generalInfo.setResourceId(resourceId);
						//  SL-19667 update bc_check_id and background_state_id of the resource to share the background check info.
						iContactDao.updateBcCheck(generalInfo);
					}
				}

				String backgroundCheckStatus=IAuditStates.RESOURCE_BACKGROUND_CHECK_INCOMPLETE;
				if(null!=sharedBackgroundCheckStateId)
				{
					if (sharedBackgroundCheckStateId.intValue() == 28)
						backgroundCheckStatus = IAuditStates.RESOURCE_BACKGROUND_CHECK_PENDING_SUBMISSION;
					else if (sharedBackgroundCheckStateId.intValue() == 8)
						backgroundCheckStatus = IAuditStates.RESOURCE_BACKGROUND_CHECK_INPROCESS;
					else if (sharedBackgroundCheckStateId.intValue() == 9)
						backgroundCheckStatus = IAuditStates.RESOURCE_BACKGROUND_CHECK_CLEAR;
					else if (sharedBackgroundCheckStateId.intValue() == 10)
						backgroundCheckStatus = IAuditStates.RESOURCE_BACKGROUND_CHECK_ADVERSE_FINDINGS;
				}


				// Start audit General Info tab - Adding a Team member, Background check  
				try {
					int resId = Integer.parseInt(generalInfoVO.getResourceId());

					getAuditBO().auditVendorResource(resId, IAuditStates.RESOURCE_INCOMPLETE);

					getAuditBO().auditResourceBackgroundCheck(resId,backgroundCheckStatus);

					//SL-20225 : if the resource is shared, update the background status a Complete
					BackgroundCheckVO backgroundCheckVo = new BackgroundCheckVO();
					//check if re-certification is required
					backgroundCheckVo = iVendorResourceDao.isBackgroundCheckRecertification(Integer.valueOf(resourceId));
					if(null == backgroundCheckVo){
						//check if resource is shared and back ground check status is not 'Not Started'
						backgroundCheckVo = iVendorResourceDao.getBackgroundInfo(Integer.valueOf(resourceId));
						if(null != backgroundCheckVo){
							// updating activity registry
							activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId(), ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
							generalInfoVO.setBgComplete(true);
						}
					}

				} catch (AuditException ae) {
					logger.error("[GeneralInfoBOImpl] - saveUserInfo() - Audit Exception Occured for audit record: saveUserInfo()",ae);
					throw ae;
				}
				// End audit General Info tab - Adding a Team member, Background check  

			} catch (DBException dae) {
				logger.error("DB Exception @GeneralInfoBOImpl.addUserInfo(),Unable to add User", dae);
				throw new BusinessServiceException(
						"DB Layer Exception Occurred while inserting the values",
						dae);
			} catch (Exception e) {
				logger.error("[Genaral Exception]@ GeneralInfoBOImpl.addUserInfo()",e);
				throw new BusinessServiceException(
						"General Exception Occured while adding User", e);
			}
		} else {
			try {	

				resourceId = generalInfoVO.getResourceId();
				// generalInfoVO = updateGeneralInfo(generalInfoVO);
				Integer bcCheckId=iContactDao.getBackgroundCheckIdWithSameContact(generalInfoVO);
				// SL-19667 get background Check Id with same personal information
				generalInfoVO = updateGeneralInfo(generalInfoVO);
				if (null != bcCheckId) {
					// generalInfo.setBcCheckId(bcCheckId);
					// generalInfo.setResourceId(resourceId);
					Integer existingBGCheckId = searchPortalBO.getBGCheckId(generalInfoVO.getResourceId());
					Integer existingBgStateInfo = getBGStateInfo(existingBGCheckId);
					GeneralInfoVO bgCheckVO = validateForExistingResources(generalInfoVO.getResourceId(),
							generalInfoVO.getFirstName(), generalInfoVO.getLastName(), generalInfoVO.getSsn());

					GeneralInfoVO generalInfo = new GeneralInfoVO();

					BackgroundCheckVO backgroundCheck = iContactDao.getBackgroundCheckInfo(bcCheckId);
					if (null != backgroundCheck) {
						// generalInfo.setBcStateId(backgroundCheck.getBackgroundCheckStateId());
						setSharedBgDetails(bgCheckVO.getBackgroundCheckStatusId(), existingBgStateInfo, generalInfo,
								generalInfoVO.getResourceId(), bgCheckVO.getResourceId(), existingBGCheckId, bcCheckId);
					}
					// SL-19667 update bg_check_id and background_state_id of
					// the resource to share the background check info.
					iContactDao.updateBcCheck(generalInfo);
				}
				else{
					// get the bg_check_id of vendor_resource by giving resource id as parameter.
					// if null
					boolean primaryAdmin=false;

					String bgCheckId = iContactDao.getBackgroundCheckResourceInfo(resourceId);

					if(null==bgCheckId)
					{
						primaryAdmin =true;
					}


					if(primaryAdmin){
						//  SL-19667 Insert background check information with the plusOneKey in sl_pro_bknd_chk table.
						Integer backgroundCheckId=insertPlusOneKeyForBackgroundCheck(resourceSsn,resourceId);
						if(null!=backgroundCheckId){
							GeneralInfoVO generalInfo=new GeneralInfoVO();
							generalInfo.setBcCheckId(backgroundCheckId);
							generalInfo.setResourceId(resourceId);
							//  SL-19667 update bc_check_id and background_state_id of the vendor_resource to share the background check info.
							iContactDao.updateBcCheck(generalInfo);
						}
					}else{

						//R12_2
						//SL-20553
						//Editing SSN Scenario
						Integer bcCheckIdCount=iContactDao.getCountWithSameBackgroundCheckId(bgCheckId);
						if(1 == bcCheckIdCount.intValue())
						{
							updatePlusOneKey(resourceSsn,resourceId);	
						}
						else
						{
							Integer backgroundCheckId=insertPlusOneKeyForBackgroundCheck(resourceSsn,resourceId);
							if(null!=backgroundCheckId){
								GeneralInfoVO generalInfo=new GeneralInfoVO();
								generalInfo.setBcCheckId(backgroundCheckId);
								generalInfo.setResourceId(resourceId);
								generalInfo.setBcStateId(BG_NOT_STARTED);
								iContactDao.updateBcCheck(generalInfo);
							}
						}
					}
				}
				// updating activity registry
				activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId(), ActivityRegistryConstants.RESOURCE_GENERALINFO);

				//SL-20225 : if the resource is shared, update the background status a Complete
				BackgroundCheckVO backgroundCheckVo = new BackgroundCheckVO();
				//check if re-certification is required
				backgroundCheckVo = iVendorResourceDao.isBackgroundCheckRecertification(Integer.valueOf(resourceId));
				if(null == backgroundCheckVo){
					//check if resource is shared and back ground check is clear
					backgroundCheckVo = iVendorResourceDao.getBackgroundCheckInfo(Integer.valueOf(resourceId));
					if(null != backgroundCheckVo){
						// updating activity registry
						activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId(), ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
						generalInfoVO.setBgComplete(true);
					}
				}	

			} catch (DBException dae) {
				logger.error("DB Exception @GeneralInfoBOImpl.addUserInfo(),Unable to update User", dae);
				throw new BusinessServiceException(
						"DB Layer Exception Occurred while updating the values",
						dae);
			} catch (Exception e) {
				logger.error("[Genaral Exception]@ GeneralInfoBOImpl.addUserInfo()",e);
				throw new BusinessServiceException(
						"General Exception Occured while updating User", e);
			}
		}

		return generalInfoVO;
	}

	
	// sl-19667 insert plusOne key for new registration
	private Integer insertPlusOneKeyForBackgroundCheck(String ssn, String resourceID) throws Exception {
		if (ssn != null && ssn.trim().length() > 0 && resourceID != null && resourceID.trim().length() > 0) {
			String plusOne = resourceID.trim() + ssn.trim();
			GeneralInfoVO backgroundInfo=new GeneralInfoVO();
			//R12_2
			//SL-20553
			backgroundInfo.setResourceId(resourceID);
			backgroundInfo.setPlusOneKey(CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne));
			Integer bcCheckId = iContactDao.insertBcCheck(backgroundInfo);
			return bcCheckId;
		}
		return null;
	}


	//sl-19667 updates plusOne key for provider registration
	private boolean updatePlusOneKey(String ssn, String resourceID) throws Exception{
		if(ssn != null && ssn.trim().length() > 0 && resourceID != null && resourceID.trim().length()>0){
			String plusOne= resourceID.trim()+ssn.trim();
			// SL-19667 need not encrypt
			int result = 0;
			result = iVendorResourceDao.insertResourcePlusOneKey(resourceID,CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne));
			if (result > 0)
				return true;
		}
		return false;
	}

	/**
	 * @param userID
	 * @return
	 * @throws DBException
	 */
	public boolean isUserAlreadyExist(String userID, String resourceId) throws DBException {

		GeneralInfoVO tempGInfoVO = null;
		tempGInfoVO = iVendorResourceDao.getVendorResourceByUserId(userID);

		if (tempGInfoVO == null )
			return false;

		if (resourceId != null && resourceId.trim().length()>0 && resourceId.equals(tempGInfoVO.getResourceId())) 
			return false;

		return true;

	}

	//sl-19667 is resource with same personal info exist for the same vendor
	public boolean isSameResourceExist(GeneralInfoVO generalInfoVO)throws DBException{

		boolean isSameResourceExist;
		isSameResourceExist = iVendorResourceDao.isSameResourceExist(generalInfoVO);
		return isSameResourceExist;


	}


	/**
	 * @param generalInfoVO
	 * @return
	 * @throws Exception
	 */
	private GeneralInfoVO insertGeneralInfo(
			GeneralInfoVO generalInfoVO) throws Exception {

		GeneralInfoVO tempGeneralInfoVO = null;

		// insert the object
		logger.info("inserting contact");
		tempGeneralInfoVO = iContactDao.insert(generalInfoVO);

		generalInfoVO.setContactId(tempGeneralInfoVO.getContactId());

		logger.info("inserting location");
		generalInfoVO.setLocnTypeId(ADDRESS_TYPE_WORK);
		tempGeneralInfoVO = iLocationDao.insert(generalInfoVO);
		generalInfoVO.setLocationId(tempGeneralInfoVO.getLocationId());

		logger.info("inserting vendor resource");
		tempGeneralInfoVO = iVendorResourceDao.insert(generalInfoVO);
		
		logger.info("inserting zipcodes coverage");
		tempGeneralInfoVO = iVendorResourceDao.insertZipcodes(generalInfoVO);
		
		if(generalInfoVO.getStateZipcodeVO()!=null&& generalInfoVO.getStateZipcodeVO().size()>0){
			logger.info("inserting vendor_resource_coverage_outof_states");
			for (StateZipcodeVO statezipVo : generalInfoVO.getStateZipcodeVO()) {
				iVendorResourceDao.insertOutOfStateZips(statezipVo, generalInfoVO.getResourceId());
			}

		}

		if (generalInfoVO.getUserName()!=null && generalInfoVO.getUserName().trim().length()>0) {
			logger.info("insert team member in user profile table");
			insertTeamMemberUserProfile(generalInfoVO);
		}

		generalInfoVO.setResourceId(tempGeneralInfoVO.getResourceId());
		logger.info("inserting resource schedule");
		iResourceScheduleDao.insert(generalInfoVO);

		return generalInfoVO;
	}

	/**
	 * @param generalInfoVO
	 * @return
	 * @throws Exception
	 */
	private GeneralInfoVO updateGeneralInfo(GeneralInfoVO generalInfoVO) throws Exception {


		GeneralInfoVO tempGeneralInfoVO = null;
		// insert the object
		logger.info("updating contact");
		generalInfoVO.setLocnTypeId(ADDRESS_TYPE_WORK);
		if (generalInfoVO.getContactId() > 0) {
			iContactDao.update(generalInfoVO);
		} else {
			tempGeneralInfoVO = iContactDao.insert(generalInfoVO);
			generalInfoVO.setContactId(tempGeneralInfoVO.getContactId());
		}

		logger.info("updating location resource");

		if (generalInfoVO.getLocationId() > 0) {
			iLocationDao.update(generalInfoVO);
		} else {
			tempGeneralInfoVO = iLocationDao.insert(generalInfoVO);
			generalInfoVO.setLocationId(tempGeneralInfoVO.getLocationId());
		}
		
		if (generalInfoVO.isZipcodesUpated()){
			logger.info("updating vendor resource coverage");
			iVendorResourceDao.updateZipcodes(generalInfoVO);
		}
		
		if (null == generalInfoVO.getStateZipcodeVO()) {
			logger.info("delete existing vendor_resource_coverage_outof_states");
			iVendorResourceDao.deleteOutOfStateZips(generalInfoVO.getResourceId());
		} else if (generalInfoVO.getStateZipcodeVO() != null && generalInfoVO.getStateZipcodeVO().size() > 0) {
			logger.info("delete existing vendor_resource_coverage_outof_states");
			iVendorResourceDao.deleteOutOfStateZips(generalInfoVO.getResourceId());
			logger.info("inserting vendor_resource_coverage_outof_states");
			for (StateZipcodeVO statezipVo : generalInfoVO.getStateZipcodeVO()) {
				iVendorResourceDao.insertOutOfStateZips(statezipVo, generalInfoVO.getResourceId());
			}
			
		}

		// inserting user profile only when requesting userId is not null and database userId is null
		if (generalInfoVO.getUserName()!=null && generalInfoVO.getUserName().trim().length()>0) {
			GeneralInfoVO tGInfoVO = new GeneralInfoVO();
			tGInfoVO.setResourceId(generalInfoVO.getResourceId());

			tGInfoVO = iVendorResourceDao.get(tGInfoVO);
			if (tGInfoVO!= null && (tGInfoVO.getUserName() == null || tGInfoVO.getUserName().length()==0)) {
				logger.info("insert team member in user profile table");
				insertTeamMemberUserProfile(generalInfoVO);
			}
		}

		logger.info("updating vendor resource");
		iVendorResourceDao.update(generalInfoVO);

		tempGeneralInfoVO = iResourceScheduleDao.get(generalInfoVO);
		if (tempGeneralInfoVO == null){
			logger.info("inserting resource schedule");
			iResourceScheduleDao.insert(generalInfoVO);
		} else {
			logger.info("updating resource schedule");
			iResourceScheduleDao.update(generalInfoVO);
		}

		return generalInfoVO;
	}
	
	/**
	 * @param getAvailableTimeSlots
	 * @return
	 * @throws Exception
	 */
	public Map<String, TimeSlotDTO> getAvailableTimeSlots(List<String> providerList, Date date) throws Exception {
		
		logger.info("inside getAvailableTimeSlots - ");
		Calendar startTimeCal = Calendar.getInstance();
		startTimeCal.setTime(date);
		startTimeCal.set(Calendar.HOUR_OF_DAY, 0);
		startTimeCal.set(Calendar.MINUTE, 0);
		startTimeCal.set(Calendar.SECOND, 0);

		Calendar endTimeCal = Calendar.getInstance();
		endTimeCal.setTime(date);
		endTimeCal.set(Calendar.HOUR_OF_DAY, 23);
		endTimeCal.set(Calendar.MINUTE, 59);
		endTimeCal.set(Calendar.SECOND, 59);

		return getCapacityAvailableTimeSlots(providerList, startTimeCal, endTimeCal);
	}

	public Map<String, TimeSlotDTO> getCapacityAvailableTimeSlots(List<String> providerList, Calendar startTimeCal, Calendar endTimeCal) throws Exception {
		
		int dayOfWeek = startTimeCal.get(Calendar.DAY_OF_WEEK);

		Map<String, TimeSlotDTO> providerTimeSlots = new HashMap<String, TimeSlotDTO>();
		
		logger.info("providerList: " + providerList);
		List<GeneralInfoVO> providerCapacityList = iResourceScheduleDao.fetchProviderScheduleByProviderList(providerList);
		
		for (GeneralInfoVO providerGeneralInfo : providerCapacityList) {
			
			TimeZone destTimeZone = TimeZone.getTimeZone("GMT");
			TimeZone sourceTimeZone = TimeZone.getTimeZone("GMT");
			
			try {
				logger.info("providerId: " + providerGeneralInfo.getResourceId());
				
				if (providerGeneralInfo.getTimeZoneType() != null) {
					sourceTimeZone = TimeZone.getTimeZone(providerGeneralInfo.getTimeZoneType());
				} else {
					GeneralInfoVO tempGeneralInfoVO = new GeneralInfoVO();
					tempGeneralInfoVO.setResourceId(providerGeneralInfo.getResourceId());
					GeneralInfoVO generalInfoVO = iVendorResourceDao.get(tempGeneralInfoVO);
					tempGeneralInfoVO.setLocationId(generalInfoVO.getLocationId());
					generalInfoVO = iLocationDao.get(tempGeneralInfoVO);

					String addressZip = generalInfoVO.getDispAddZip();
					String addressTimeZone = LocationUtils.getTimeZone(addressZip);
					sourceTimeZone = TimeZone.getTimeZone(addressTimeZone);
				}
			} catch (Exception exp) {
				logger.error("Error while getting time zone: ", exp);
			}
			
			try {
				TimeSlotDTO providerTimeSlot;

				// 1-7 for Sunday to Saturday.
				switch (dayOfWeek) {
				case 1:
					if ("1".equals(providerGeneralInfo.getSunNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getSun24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSunStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSunEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getSunStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getMonNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getMon24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getMonStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getSunStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getSatNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSat24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSatStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getSunEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getMonNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getMon24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getMonEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getSunEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getSatNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSat24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSatEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				case 2:
					if ("1".equals(providerGeneralInfo.getMonNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getMon24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getMonStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getMonEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getMonStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getTueNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getTue24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getTueStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getMonStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getSunNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSun24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSunStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getMonEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getTueNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getTue24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getTueEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getMonEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getSunNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSun24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSunEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				case 3:
					if ("1".equals(providerGeneralInfo.getTueNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getTue24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getTueStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getTueEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getTueStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getWedNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getWed24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getWedStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getTueStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getMonNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getMon24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getMonStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getTueEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getWedNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getWed24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getWedEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getTueEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getMonNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getMon24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getMonEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				case 4:
					if ("1".equals(providerGeneralInfo.getWedNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getWed24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getWedStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getWedEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getWedStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getThuNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getThu24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getThuStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getWedStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getTueNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getTue24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getTueStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getWedEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getThuNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getThu24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getThuEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getWedEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getTueNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getTue24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getTueEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				case 5:
					if ("1".equals(providerGeneralInfo.getThuNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getThu24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getThuStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getThuEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getThuStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getFriNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getFri24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getFriStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getThuStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getWedNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getWed24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getWedStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getThuEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getFriNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getFri24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getFriEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getThuEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getWedNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getWed24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getWedEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				case 6:
					if ("1".equals(providerGeneralInfo.getFriNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getFri24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getFriStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getFriEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getFriStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getSatNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSat24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSatStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getFriStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getThuNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getThu24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getThuStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getFriEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getSatNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSat24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSatEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getFriEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getThuNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getThu24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getThuEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				case 7:
					if ("1".equals(providerGeneralInfo.getSatNaInd())) {
						break;
					} else if ("1".equals(providerGeneralInfo.getSat24Ind())) {
						providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
						providerTimeSlot.setHourInd24(true);
					} else {
						Date startTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSatStart(), sourceTimeZone,
								destTimeZone);
						Date endTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSatEnd(), sourceTimeZone,
								destTimeZone);

						Calendar startCalTemp = Calendar.getInstance();
						startCalTemp.setTimeInMillis(startTimeCal.getTimeInMillis());
						if (startTime.getDate() < providerGeneralInfo.getSatStart().getDate()) {
							Date newStartTime;
							if ("1".equals(providerGeneralInfo.getSunNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSun24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSunStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, -1);
						} else if (startTime.getDate() > providerGeneralInfo.getSatStart().getDate()) {
							Date newStartTime = startTime;
							if ("1".equals(providerGeneralInfo.getFriNaInd())) {
								newStartTime = endTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getFri24Ind())) {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newStartTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getFriStart(), sourceTimeZone,
										destTimeZone);
							}

							startTime = newStartTime;
							startCalTemp.add(Calendar.DATE, 1);
						}

						Calendar endCalTemp = Calendar.getInstance();
						endCalTemp.setTimeInMillis(endTimeCal.getTimeInMillis());
						if (endTime.getDate() < providerGeneralInfo.getSatEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getSunNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getSun24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(endTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getSunEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, -1);
						} else if (endTime.getDate() > providerGeneralInfo.getSatEnd().getDate()) {
							Date newEndTime;
							if ("1".equals(providerGeneralInfo.getFriNaInd())) {
								newEndTime = startTimeCal.getTime();
							} else if ("1".equals(providerGeneralInfo.getFri24Ind())) {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(startTimeCal.getTime(), sourceTimeZone,
										destTimeZone);
							} else {
								newEndTime = TimeChangeUtil.convertDateBetweenTimeZones(providerGeneralInfo.getFriEnd(), sourceTimeZone,
										destTimeZone);
							}

							endTime = newEndTime;
							endCalTemp.add(Calendar.DATE, 1);
						}

						providerTimeSlot = new TimeSlotDTO(startTime, endTime, startCalTemp, endCalTemp);
					}
					providerTimeSlots.put(providerGeneralInfo.getResourceId(), providerTimeSlot);
					break;
				} // end: case
			} catch (Exception e) {
				logger.error("Please check the data for the provider id: providerGeneralInfo.getResourceId() ", e);
			}
			
		} // end: for (GeneralInfoVO providerGeneralInfo : providerCapacityList) {
		
		// ------- OLD_CODE --------------------------------
		/*for (String providerId : providerList) {
			
			GeneralInfoVO providerGeneralInfo = new GeneralInfoVO();
			providerGeneralInfo.setResourceId(providerId);
			providerGeneralInfo = iResourceScheduleDao.get(providerGeneralInfo);
			
			TimeSlotDTO providerTimeSlot;
			
			logger.info("providerId: " + providerId);
			if (null == providerGeneralInfo) {
				logger.info("No capacity for providerId: " + providerId);
				continue;
			}
			
			// 1-7 for Sunday to Saturday.
			switch (dayOfWeek) {
			case 1:
				if ("1".equals(providerGeneralInfo.getSunNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getSun24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getSunStart(), providerGeneralInfo.getSunEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			case 2:
				if ("1".equals(providerGeneralInfo.getMonNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getMon24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getMonStart(), providerGeneralInfo.getMonEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			case 3:
				if ("1".equals(providerGeneralInfo.getTueNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getTue24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getTueStart(), providerGeneralInfo.getTueEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			case 4:
				if ("1".equals(providerGeneralInfo.getWedNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getWed24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getWedStart(), providerGeneralInfo.getWedEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			case 5:
				if ("1".equals(providerGeneralInfo.getThuNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getThu24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getThuStart(), providerGeneralInfo.getThuEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			case 6:
				if ("1".equals(providerGeneralInfo.getFriNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getFri24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getFriStart(), providerGeneralInfo.getFriEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			case 7:
				if ("1".equals(providerGeneralInfo.getSatNaInd())) {
					break;
				} else if ("1".equals(providerGeneralInfo.getSat24Ind())) {
					providerTimeSlot = new TimeSlotDTO(startTimeCal.getTime(), endTimeCal.getTime());
					providerTimeSlot.setHourInd24(true);
				} else {
					providerTimeSlot = new TimeSlotDTO(providerGeneralInfo.getSatStart(), providerGeneralInfo.getSatEnd(),startTimeCal,endTimeCal);
				}
				providerTimeSlots.put(providerId, providerTimeSlot);
				break;
			}
		}*/
		
		logger.info("getAvailableTimeSlots - " + providerTimeSlots);
		
		return providerTimeSlots;
	}

	/**
	 * Description: This method persists the user profile data.
	 * @param generalInfoVO
	 * @throws DBException
	 */
	private void insertTeamMemberUserProfile(GeneralInfoVO generalInfoVO) throws DBException {
		UserProfile userProfile = new UserProfile();

		userProfile.setUserName(generalInfoVO.getUserName());
		userProfile.setContactId(generalInfoVO.getContactId());
		userProfile.setPasswordFlag(1);
		userProfile.setAnswerTxt("");
		userProfile.setQuestionId(0);
		userProfile.setRoleId(1);

		iUserProfileDao.insertTeamMemberUserProfile(userProfile);			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO#getGeneralInfo(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO getUserInfo(GeneralInfoVO generalInfoVO) throws Exception {		
		if (!(generalInfoVO.getResourceId()==null || "".equals(generalInfoVO.getResourceId().trim()))) {
			GeneralInfoVO tempGeneralInfoVO = null;

			tempGeneralInfoVO = iVendorResourceDao.get(generalInfoVO);
			generalInfoVO.setContactId(tempGeneralInfoVO.getContactId());
			generalInfoVO.setLocationId(tempGeneralInfoVO.getLocationId());
			generalInfoVO.setHourlyRate(tempGeneralInfoVO.getHourlyRate());
			generalInfoVO.setUserName(tempGeneralInfoVO.getUserName());
			generalInfoVO.setMarketPlaceInd(tempGeneralInfoVO.getMarketPlaceInd());
			generalInfoVO.setSsnLast4(tempGeneralInfoVO.getSsnLast4());
			generalInfoVO.setDispAddGeographicRange(tempGeneralInfoVO.getDispAddGeographicRange());
			generalInfoVO.setOwnerInd(tempGeneralInfoVO.getOwnerInd());
			generalInfoVO.setDispatchInd(tempGeneralInfoVO.getDispatchInd());
			generalInfoVO.setResourceInd(tempGeneralInfoVO.getResourceInd());
			generalInfoVO.setManagerInd(tempGeneralInfoVO.getManagerInd());
			generalInfoVO.setAdminInd(tempGeneralInfoVO.getAdminInd());
			generalInfoVO.setOtherInd(tempGeneralInfoVO.getOtherInd());
			generalInfoVO.setSproInd(tempGeneralInfoVO.getSproInd());
			generalInfoVO.setBckStateId(tempGeneralInfoVO.getBckStateId());
			//Added to find the User is Service Pro or Not
			generalInfoVO.setPrimaryInd(tempGeneralInfoVO.getPrimaryInd());
			generalInfoVO.setUserNameAdmin(iVendorResourceDao.getUserNameAdmin(generalInfoVO.getVendorId()));

			//Added as per JIRA#SL-20083 -- To check user name exists in DB or not.
			if((tempGeneralInfoVO.getUserName() != null) && (tempGeneralInfoVO.getUserName() != ""))
			{
				generalInfoVO.setUserNameExists(Boolean.TRUE);
			}


			tempGeneralInfoVO = iContactDao.get(generalInfoVO);

			if (tempGeneralInfoVO!=null) {
				generalInfoVO.setFirstName(tempGeneralInfoVO.getFirstName());
				generalInfoVO.setLastName(tempGeneralInfoVO.getLastName());
				generalInfoVO.setMiddleName(tempGeneralInfoVO.getMiddleName());
				generalInfoVO.setSuffix(tempGeneralInfoVO.getSuffix());
				generalInfoVO.setOtherJobTitle(tempGeneralInfoVO.getOtherJobTitle());
			}

			tempGeneralInfoVO = iLocationDao.get(generalInfoVO);
			if (tempGeneralInfoVO!=null) {
				generalInfoVO.setDispAddStreet1(tempGeneralInfoVO.getDispAddStreet1());
				generalInfoVO.setDispAddStreet2(tempGeneralInfoVO.getDispAddStreet2());
				generalInfoVO.setDispAddApt(tempGeneralInfoVO.getDispAddApt());
				generalInfoVO.setDispAddCity(tempGeneralInfoVO.getDispAddCity());
				generalInfoVO.setDispAddState(tempGeneralInfoVO.getDispAddState());
				generalInfoVO.setDispAddZip(tempGeneralInfoVO.getDispAddZip());
			}

			tempGeneralInfoVO = iResourceScheduleDao.get(generalInfoVO);
			if (tempGeneralInfoVO != null) {
				generalInfoVO.setMonStart(tempGeneralInfoVO.getMonStart());
				generalInfoVO.setMonEnd(tempGeneralInfoVO.getMonEnd());
				generalInfoVO.setTueStart(tempGeneralInfoVO.getTueStart());
				generalInfoVO.setTueEnd(tempGeneralInfoVO.getTueEnd());
				generalInfoVO.setWedStart(tempGeneralInfoVO.getWedStart());
				generalInfoVO.setWedEnd(tempGeneralInfoVO.getWedEnd());
				generalInfoVO.setThuStart(tempGeneralInfoVO.getThuStart());
				generalInfoVO.setThuEnd(tempGeneralInfoVO.getThuEnd());
				generalInfoVO.setFriStart(tempGeneralInfoVO.getFriStart());
				generalInfoVO.setFriEnd(tempGeneralInfoVO.getFriEnd());
				generalInfoVO.setSatStart(tempGeneralInfoVO.getSatStart());
				generalInfoVO.setSatEnd(tempGeneralInfoVO.getSatEnd());
				generalInfoVO.setSunStart(tempGeneralInfoVO.getSunStart());
				generalInfoVO.setSunEnd(tempGeneralInfoVO.getSunEnd());
				generalInfoVO.setMon24Ind(tempGeneralInfoVO.getMon24Ind());
				generalInfoVO.setMonNaInd(tempGeneralInfoVO.getMonNaInd());
				generalInfoVO.setTue24Ind(tempGeneralInfoVO.getTue24Ind());
				generalInfoVO.setTueNaInd(tempGeneralInfoVO.getTueNaInd());
				generalInfoVO.setWed24Ind(tempGeneralInfoVO.getWed24Ind());
				generalInfoVO.setWedNaInd(tempGeneralInfoVO.getWedNaInd());
				generalInfoVO.setThu24Ind(tempGeneralInfoVO.getThu24Ind());
				generalInfoVO.setThuNaInd(tempGeneralInfoVO.getThuNaInd());
				generalInfoVO.setFri24Ind(tempGeneralInfoVO.getFri24Ind());
				generalInfoVO.setFriNaInd(tempGeneralInfoVO.getFriNaInd());
				generalInfoVO.setSat24Ind(tempGeneralInfoVO.getSat24Ind());
				generalInfoVO.setSatNaInd(tempGeneralInfoVO.getSatNaInd());
				generalInfoVO.setSun24Ind(tempGeneralInfoVO.getSun24Ind());
				generalInfoVO.setSunNaInd(tempGeneralInfoVO.getSunNaInd());				
			}
		}
		// getting status of user already exist or not
		generalInfoVO.setUserExistInd(getUserExistWithServiceInMarket(generalInfoVO.getVendorId(),generalInfoVO.getResourceId()));
		generalInfoVO.setGeographicalRange(iLookupDAO.loadServiceAreaRadius());
		generalInfoVO.setScheduleTimeList(iLookupDAO.loadTimeInterval());

		return generalInfoVO;
	}

	/**
	 * @param vendorId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	private int getUserExistWithServiceInMarket(String vendorId,String resourceId) throws BusinessServiceException{
		int count =0;
		try {
			count = iVendorResourceDao.getResourceCountWithServiceInMarket(vendorId, resourceId);
		} catch (DBException dae) {
			logger
			.info("DB Exception @GeneralInfoBOImpl.getUserExistWithServiceInMarket()");
			throw new BusinessServiceException(
					"DB Layer Exception Occurred while getting resource count in market place",
					dae);
		} catch (Exception e) {
			logger.info("[Genaral Exception]@GeneralInfoBOImpl.getUserExistWithServiceInMarket()");
			e.printStackTrace();
			throw new BusinessServiceException(
					"General Exception Occured while getting resource count in market place", e);
		}
		return count;
	}

	public IAuditBO getAuditBO() {
		return auditBO;
	}

	public void setAuditBO(IAuditBO auditBO) {
		this.auditBO = auditBO;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO#loadZipSet(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO loadZipSet(GeneralInfoVO generalInfoVO) throws Exception {
		try {				
			List stateTypeList = null;
			if (generalInfoVO.getStateType() != null
					&& 	generalInfoVO.getStateType().length() > 0
					&&  generalInfoVO.getStateType().equalsIgnoreCase("business"))
			{
				stateTypeList = zipDao.queryList(generalInfoVO.getDispAddState());
			}

			if (generalInfoVO.getStateType() != null
					&& 	generalInfoVO.getStateType().length() > 0
					&&  generalInfoVO.getStateType().equalsIgnoreCase("mail"))
			{
				stateTypeList = zipDao.queryList(generalInfoVO.getDispAddState());
			}
			generalInfoVO.setStateTypeList(stateTypeList);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger
			.info("General Exception @GeneralInfoBOImpl.loadZipSet() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @GeneralInfoBOImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		return generalInfoVO;
	}
	/**
	 * Description: This method Soft deletes the provider
	 * @param resourceId
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param adminUserName
	 * @param vendorId
	 * @return boolean
	 */
	public Boolean removeUser(String resourceId, String userName, String firstName, String lastName, String adminUserName, String vendorId) {
		List affectedServiceOrders = null;
		boolean flagTemp=false;
		try{

			affectedServiceOrders=iVendorResourceDao.openServiceOrders(resourceId);
			if ((affectedServiceOrders == null) || (affectedServiceOrders !=null && affectedServiceOrders.size() <= 0))
			{
				iVendorResourceDao.removeUser(resourceId,userName);
				this.sendRemoveUserMailConfirmation(resourceId, userName, firstName, lastName, adminUserName, vendorId);
				flagTemp=true;
			}else{
				flagTemp=false;
			}


		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return flagTemp;
	}

	/**
	 * Description: This method sends the provider soft delete confirmation mail
	 * @param resourceId
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param adminUserName
	 * @param vendorId
	 */
	private void sendRemoveUserMailConfirmation(String resourceId, String userName, String firstName, String lastName, String adminUserName, String vendorId){
		try{
			String toAddress = iVendorResourceDao.getProviderAdminEmail(vendorId);
			emailTemplateBO.sendRemoveUserMailConfirmation(resourceId, userName, firstName, lastName, adminUserName, toAddress);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
	}
	/**
	 * Takes a zip code and returns whether or not that zip exists in the DB
	 * @param zip
	 * @return <code>boolean</code>
	 */
	public boolean zipExists(String zip) {
		boolean zipFound = false;
		try {
			if (zipDao.queryZipCount(zip) > 0){
				zipFound = true;
			} else {
				zipFound = false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return zipFound;
	}
	
	public ISearchPortalBO getSearchPortalBO() {
		return searchPortalBO;
	}

	public void setSearchPortalBO(ISearchPortalBO searchPortalBO) {
		this.searchPortalBO = searchPortalBO;
	}
	
	private Integer getBGStateInfo(Integer bgCheckId) throws Exception{
		return searchPortalBO.getBackgroundCheckInfo(bgCheckId);
	}

	
	private GeneralInfoVO validateForExistingResources(String resourceId, String providerFirstName,String providerLastName, String ssn) throws Exception {
		GeneralInfoVO pro = new GeneralInfoVO();
		pro.setFirstName(providerFirstName);
		pro.setLastName(providerLastName);
		pro.setSsn(ssn);
		pro.setResourceId(resourceId);
		try {
			pro = iContactDao.getProviderDetailsWithSameContact(pro);
		} catch (Exception e) {
			logger.error("Exception occurred while fetching bgCheckId");
		}
		return pro;
			
	}
	
	private void setSharedBgDetails(Integer sharedBgStateInfo, Integer existingBgStateInfo,GeneralInfoVO pro,String existingResourceId,String sharedResourceId,Integer existingCheckId, Integer sharedBgCheckId){
		
		if(Constants.BG_STATE_NOT_STARTED.intValue()==sharedBgStateInfo.intValue() && Constants.BG_STATE_PENDING_SUBMISSION.intValue()== existingBgStateInfo.intValue()){
			pro.setBcStateId(existingBgStateInfo);
			pro.setBcCheckId(existingCheckId);
			pro.setResourceId(sharedResourceId);
		}
		else{
			pro.setBcStateId(sharedBgStateInfo);
			pro.setResourceId(existingResourceId);
			pro.setBcCheckId(sharedBgCheckId);
		}
	}
	
	public List<StateZipcodeVO> getStateCdAndZipAgainstCoverageZip(List<String> zipList) throws Exception {
		List<StateZipcodeVO> stateNameList = null;
		try {
			stateNameList = zipDao.queryStateCdAndZip(zipList);
		} catch (Exception e) {
			logger.info("[Genaral Exception]@GeneralInfoBOImpl.loadStateForZip");
			e.printStackTrace();
			throw new BusinessServiceException(
					"General Exception Occured while getting stateName for zipcode", e);
		}
		return stateNameList;
		
	}

}


/*
 * Maintenance History
 * $Log: GeneralInfoBOImpl.java,v $
 * Revision 1.32  2008/05/21 22:54:26  akashya
 * I21 Merged
 *
 * Revision 1.31.6.1  2008/05/08 16:33:19  dmill03
 * NS - Zip verification and HomepageAction and Model changes
 *
 * Revision 1.31  2008/04/26 00:40:26  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.27.12.1  2008/04/23 11:42:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.30  2008/04/23 05:01:44  hravi
 * Reverting to build 247.
 *
 * Revision 1.28  2008/04/15 17:51:01  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.27.24.1  2008/04/11 03:16:56  araveen
 * Made changes for RemoveUserButton Sears00049387
 *
 * Revision 1.27  2008/02/11 17:35:50  mhaye05
 * removed statesList attributes
 *
 * Revision 1.26  2008/02/06 19:38:53  mhaye05
 * merged with Feb4_release branch
 *
 * Revision 1.22.6.3  2008/02/06 17:38:22  mhaye05
 * all transactions are not handled by Spring AOP.  no more beginWork!!
 *
 */