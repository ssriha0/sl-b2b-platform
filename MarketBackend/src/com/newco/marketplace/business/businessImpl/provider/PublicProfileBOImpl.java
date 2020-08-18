package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IPublicProfileBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.provider.IBusinessinfoDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao;
import com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao;
import com.newco.marketplace.persistence.iDao.provider.IResourceStatusDao;
import com.newco.marketplace.persistence.iDao.provider.ISkillAssignDao;
import com.newco.marketplace.persistence.iDao.provider.ITeamCredentialsDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.vo.provider.BusinessinfoVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.sears.os.business.ABaseBO;

/**
 * Business Bean for Team Profile service request
 * @author Bob Larson, Sogeti USA, LLC
 *
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 00:40:25 $
 */

/*
 * Maintenance History
 * $Log: PublicProfileBOImpl.java,v $
 * Revision 1.11  2008/04/26 00:40:25  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.12.1  2008/04/23 11:42:14  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:01:45  hravi
 * Reverting to build 247.
 *
 * Revision 1.9  2008/02/05 22:22:02  mhaye05
 * removed commented out code
 *
 */
public class PublicProfileBOImpl extends ABaseBO implements IPublicProfileBO {

	private IBusinessinfoDao businessinfoDao;
	private IVendorResourceDao resourceDao;
	private ILocationDao locationDao;
	private IContactDao iContactDao;
	private ITeamCredentialsDao credentialsDao;
	private ISkillAssignDao skillAssignDao;
	private IResourceScheduleDao iResourceScheduleDao;
	private IMarketPlaceDao iMarketPlaceDao;
	private ILookupDAO iLookupDAO;
	private IResourceStatusDao iResourceStatusDao;
	
	private static final Logger logger = Logger
	.getLogger(PublicProfileBOImpl.class.getName());



	public PublicProfileBOImpl(IBusinessinfoDao businessinfoDao,IVendorResourceDao resourceDao,ILocationDao locationDao,IContactDao iContactDao,ITeamCredentialsDao credentialsDao,ISkillAssignDao skillAssignDao,
			IResourceScheduleDao iResourceScheduleDao,IMarketPlaceDao iMarketPlaceDao,ILookupDAO iLookupDAO,IResourceStatusDao iResourceStatusDao) {
		this.businessinfoDao = businessinfoDao;
		this.resourceDao = resourceDao;
		this.locationDao = locationDao;
		this.iContactDao = iContactDao;
		this.credentialsDao = credentialsDao;
		this.skillAssignDao = skillAssignDao;
		this.iResourceScheduleDao = iResourceScheduleDao;
		this.iMarketPlaceDao = iMarketPlaceDao;
		this.iLookupDAO = iLookupDAO;
		this.iResourceStatusDao = iResourceStatusDao;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPublicProfileBO#getPublicProfile(com.newco.marketplace.vo.provider.PublicProfileVO)
	 */
	public PublicProfileVO getPublicProfile(PublicProfileVO publicProfileVO) throws BusinessServiceException
	{
		BusinessinfoVO businessinfoVO = new BusinessinfoVO();
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		VendorResource vendorResource = new VendorResource();
		List credentialList = null;
		List languageList = null;
		List resourceSkillList = null;
		GeneralInfoVO tmpGeneralInfoVO = null;
		GeneralInfoVO tmpGeneralScheduleInfoVO = null;
		MarketPlaceVO tmpMarketPlaceVO = null;
		MarketPlaceVO tmpMarketPlaceActivityVO = null;
		int vendorFromGInfo = 0;

		generalInfoVO.setResourceId(publicProfileVO.getResourceId().toString());
		PublicProfileVO profileVO = null;

		try
		{

			generalInfoVO = resourceDao.get(generalInfoVO);


			tmpGeneralScheduleInfoVO =iResourceScheduleDao.get(generalInfoVO);
			if(generalInfoVO != null)
			{
				if(null!= generalInfoVO.getVendorId()){
					vendorFromGInfo = Integer.parseInt(generalInfoVO.getVendorId());
				}
				
				if(vendorFromGInfo != publicProfileVO.getVendorId())
				{
					publicProfileVO.setCompleteFlag(true);

				}

				publicProfileVO.setUserId(generalInfoVO.getUserName());
				publicProfileVO.setDispAddGeographicRange(resourceDao.getRadiusMiles(generalInfoVO.getDispAddGeographicRange()));
				if(null!=generalInfoVO.getResourceId()){
					publicProfileVO.setResourceId(Integer.parseInt(generalInfoVO.getResourceId()));
				}				
				publicProfileVO.setUserName(generalInfoVO.getUserName());
				publicProfileVO.setSsnLast4(generalInfoVO.getSsnLast4());
				publicProfileVO.setAdminInd(generalInfoVO.getAdminInd());
				publicProfileVO.setOwnerInd(generalInfoVO.getOwnerInd());
				publicProfileVO.setManagerInd(generalInfoVO.getManagerInd());
				publicProfileVO.setDispatchInd(generalInfoVO.getDispatchInd());
				publicProfileVO.setResourceInd(generalInfoVO.getResourceInd());
				publicProfileVO.setOtherInd(generalInfoVO.getOtherInd());
				publicProfileVO.setHourlyRate(generalInfoVO.getHourlyRate());
				publicProfileVO.setMarketPlaceInd(generalInfoVO.getMarketPlaceInd());
				publicProfileVO.setSproInd(generalInfoVO.getSproInd());
				publicProfileVO.setSlStatus(generalInfoVO.getSlStatus());

				businessinfoVO.setVendorId(generalInfoVO.getVendorId());
				businessinfoVO = businessinfoDao.getData(businessinfoVO);

				if(businessinfoVO != null)
				{
					logger.info("----------------inside vo not null-----------------------------");
					publicProfileVO.setBusinessinfoVO(businessinfoVO);
					publicProfileVO.setBusStartDt(businessinfoVO.getBusStartDt());
					publicProfileVO.setJoinDate(businessinfoVO.getJoinDate());
					if(null!= businessinfoVO.getCompanySize()){
						publicProfileVO.setCompanySize(businessinfoDao.getCompanySizeDesc(Integer.parseInt(businessinfoVO.getCompanySize())));
					}
					publicProfileVO.setVendorId(Integer.parseInt(businessinfoVO.getVendorId()));
				}}




			publicProfileVO.setScheduleTimeList(iLookupDAO.loadTimeInterval());
			tmpGeneralInfoVO = locationDao.get(generalInfoVO);
			if(tmpGeneralInfoVO!=null)
			{
				publicProfileVO.setDispAddStreet1(tmpGeneralInfoVO.getDispAddStreet1());
				publicProfileVO.setDispAddStreet2(tmpGeneralInfoVO.getDispAddStreet2());
				publicProfileVO.setDispAddApt(tmpGeneralInfoVO.getDispAddApt());
				publicProfileVO.setDispAddCity(tmpGeneralInfoVO.getDispAddCity());
				publicProfileVO.setDispAddState(tmpGeneralInfoVO.getDispAddState());
				publicProfileVO.setDispAddZip(tmpGeneralInfoVO.getDispAddZip());
			}
			tmpGeneralInfoVO = iContactDao.get(generalInfoVO);

			if(tmpGeneralInfoVO!=null)
			{
				publicProfileVO.setFirstName(tmpGeneralInfoVO.getFirstName());
				publicProfileVO.setLastName(tmpGeneralInfoVO.getLastName());
				publicProfileVO.setOtherJobTitle(tmpGeneralInfoVO.getOtherJobTitle());
				publicProfileVO.setEmail(tmpGeneralInfoVO.getEmail());
				publicProfileVO.setPhoneNumber(tmpGeneralInfoVO.getPhoneNumber());
				publicProfileVO.setAlternatePhone(tmpGeneralInfoVO.getAlternatePhone());
				publicProfileVO.setPhoneNumberExt(tmpGeneralInfoVO.getPhoneNumberExt());
			}
			if(tmpGeneralScheduleInfoVO != null)
			{
				publicProfileVO.setGeneralScheduleInfoVO(tmpGeneralScheduleInfoVO);
			}
			//if(publicProfileVO.getUserName() != null)
			tmpMarketPlaceVO = iMarketPlaceDao.getContactDetailsForResource(publicProfileVO.getResourceId().toString());
			if(tmpMarketPlaceVO != null)
			{
				publicProfileVO.setMarketPlaceVO(tmpMarketPlaceVO);
			}
			if(publicProfileVO.getUserName() != null)
			tmpMarketPlaceVO.setUserName(publicProfileVO.getUserName());
			tmpMarketPlaceVO.setEntityId(generalInfoVO.getVendorId());
			tmpMarketPlaceVO.setRoleID("1");
			tmpMarketPlaceVO.setLoggedInResourceId(publicProfileVO.getResourceId());
			
			tmpMarketPlaceVO.setResourceId(publicProfileVO.getResourceId().toString());
			tmpMarketPlaceActivityVO = iMarketPlaceDao.getData(tmpMarketPlaceVO);

			if(tmpMarketPlaceActivityVO != null)
			{
				publicProfileVO.setActivityList(tmpMarketPlaceActivityVO.getActivityList());

			}
			//get the credentials list

			credentialList = credentialsDao.queryCredByResourceIdIncludedStates(publicProfileVO.getResourceId(),getInlcudeStatusList());
			if(credentialList!=null)
			{
				logger.info("teamCredentilaList Size------------ "+credentialList.size());
				publicProfileVO.setCredentialsList(credentialList);
			}

			//retrieve the resource languages
			languageList = skillAssignDao.getResourceLanguages(publicProfileVO.getResourceId());
			if(languageList!=null)
			{
				publicProfileVO.setLanguageList(languageList);
			}
			//get bgChek

			profileVO = resourceDao.getPublicProfile(publicProfileVO.getResourceId());
			if(profileVO!=null)
			{
				publicProfileVO.setBgChkAppDate(profileVO.getBgChkAppDate());
				publicProfileVO.setBgChkReqDate(profileVO.getBgChkReqDate());
				publicProfileVO.setBgVerificationDate(profileVO.getBgVerificationDate()); 
			}
			//get the resource skill categories
			resourceSkillList = skillAssignDao.getResourceSkillCat(publicProfileVO.getResourceId());
			if(resourceSkillList != null)
			{
				logger.info("resourceSkillList Size------------ "+resourceSkillList.size());
				publicProfileVO.setResourceSkillList(resourceSkillList);
			}
			publicProfileVO.setGeneralInfoVO(generalInfoVO);
			vendorResource.setResourceId(publicProfileVO.getResourceId());
			String status = iResourceStatusDao.getResourceStatus(vendorResource);
			if(status != null)
				publicProfileVO.setServiceLiveStatus(status);
		}

		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @PublicProfileBOImpl.getPublicProfile() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @PublicProfileBOImpl.getPublicProfile() due to "
							+ ex.getMessage());
		}

		return publicProfileVO;
	}

	
	public List<TeamCredentialsVO> getCredentials(int resourceId) {
		try {
			List<TeamCredentialsVO> resourceCList = 
				 credentialsDao.queryCredByResourceIdIncludedStates(resourceId,getInlcudeStatusList());	
			return resourceCList;
		} catch (DataServiceException e) {			
			e.printStackTrace();
			return new ArrayList<TeamCredentialsVO>();
		} 
	}
	
	
	public  Map<String,List> getCheckedSkillsTree(int resourceId) {
		try {
			 List cat = skillAssignDao.getResourceSkillCat(resourceId);
			 List skilllist  = skillAssignDao.getALLCheckedSkillsForResource(new Integer(resourceId));
			 Map<String, List> map = new HashMap<String, List>();
			 map.put("cat", cat);
			 map.put("skills", skilllist);
			 return map;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HashMap<String, List>();
	}
	
	
	private List<Integer> getInlcudeStatusList() {
		List<Integer> list = new ArrayList();
			list.add(ProviderConstants.TEAM_MEMBER_CREDENTIAL_REVIEWED);
			list.add(ProviderConstants.TEAM_MEMBER_CREDENTIAL_APPROVED);
			list.add(ProviderConstants.TEAM_MEMBER_CREDENTIAL_PENDING_APPROVAL);
		return list;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPublicProfileBO#checkVendorResource(java.lang.String, java.lang.String)
	 */
	public boolean checkVendorResource(String resourceId, String vendorId) throws BusinessServiceException
	{
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		boolean chkStatus = false;
		int vendorFromDb = 0;

		try
		{
			generalInfoVO.setResourceId(resourceId);
			generalInfoVO = resourceDao.get(generalInfoVO);
			logger.info("vendorId from there backend "+generalInfoVO.getVendorId()+"--for the resource"+resourceId);


			if(generalInfoVO != null)
			{
				vendorFromDb = Integer.parseInt(generalInfoVO.getVendorId());
				if(vendorFromDb != Integer.parseInt(vendorId))
				{
					chkStatus= true;
				}


			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @PublicProfileBOImpl.checkVendorResource() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @PublicProfileBOImpl.checkVendorResource() due to "
							+ ex.getMessage());
		}
		return chkStatus;
	}


}
