package com.newco.marketplace.business.businessImpl.provider;

import java.net.URLEncoder;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.ITeamProfileBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ITeamProfileDAO;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.sears.os.business.ABaseBO;

public class TeamProfileBOImpl extends ABaseBO implements ITeamProfileBO {
	
	private static final Logger logger = Logger.getLogger(LicensesAndCertBOImpl.class);
	
	private ITeamProfileDAO teamProfileDAO;
	private IUserProfileDao userProfileDao;
	private ProviderEmailBOImpl iProviderEmailBO;
	private IActivityRegistryDao activityRegistryDao;
	private IVendorResourceDao iVendorResourceDao;
	private IAuditBO auditBusinessBean;
	
	private IAuditBO getAuditBusinessBean() {
		if (auditBusinessBean == null) {
			auditBusinessBean = (IAuditBO) MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.BUSINESSBEAN_AUDIT);
		}
		return (auditBusinessBean);
	}
	
	public TeamProfileBOImpl(ITeamProfileDAO teamProfileDAO, IUserProfileDao userProfileDao, ProviderEmailBOImpl iProviderEmailBO, 
			IActivityRegistryDao activityRegistryDao, IVendorResourceDao vendorResourceDao) {
		this.teamProfileDAO = teamProfileDAO;
		this.userProfileDao = userProfileDao;
		this.iProviderEmailBO = iProviderEmailBO;
		this.activityRegistryDao = activityRegistryDao;
		iVendorResourceDao = vendorResourceDao;
	}

	/**
	 * Returns the Team Profile List for the Vendor associated with the
	 * user_name that is passed in
	 * 
	 * @param request
	 * @return
	 */
	public List<TeamMemberVO> getTeamMemberList(UserProfile userProfile) throws BusinessServiceException {
		List<TeamMemberVO> teamList = null;
		try {
			teamList=new ArrayList<TeamMemberVO>();
			teamList.addAll(teamProfileDAO.queryListForResource(userProfile.getVendorId()));
		} catch (DataServiceException dse) {
			logger.debug("[DataServiceException thrown while building the team member list.]", dse);
			throw new BusinessServiceException(dse);
		}
		return teamList;
	}

	public BackgroundCheckVO queryEmailForTeamMember(BackgroundCheckVO backgroundCheckVO) throws BusinessServiceException {
		try {
			backgroundCheckVO = teamProfileDAO.queryEmailForTeamMember(backgroundCheckVO);
			int bgCheckStatus = teamProfileDAO.getBackgroundStatus(backgroundCheckVO);
			backgroundCheckVO.setBgCheckStatus(bgCheckStatus);
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return  backgroundCheckVO;		
	}

	public boolean saveBackgroundCheckData(BackgroundCheckVO backgroundCheckVO) throws BusinessServiceException {
		TMBackgroundCheckVO tMBackgroundCheckVO = new TMBackgroundCheckVO();
		Integer confID = backgroundCheckVO.getBackgroundConfirmInd();
		String email = backgroundCheckVO.getEmail();
		String altEmail = backgroundCheckVO.getEmailAlt();
		String provUserEmail = null;
		UserProfile adminProfile = new UserProfile();
		String ccArr[] = null;
		try {
			//Gets the Provider Admin's details based on the Provider User Name
			adminProfile  = userProfileDao.getProvAdminDetails(backgroundCheckVO.getProvUserName());
			tMBackgroundCheckVO.setResourceId(String.valueOf(backgroundCheckVO.getResourceId()));
			tMBackgroundCheckVO.setResourceEmail(backgroundCheckVO.getEmail());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		//R11_0 To set fourth parameter in Team Background Check Email
		tMBackgroundCheckVO.setRecertificationIndParam(backgroundCheckVO.getRecertificationInd());
		
		
		//R11_1
		//Jira SL-20434
		tMBackgroundCheckVO.setEncryptedResourceIdSsn(backgroundCheckVO.getEncryptedResourceIdSsn());
		
		backgroundCheckVO = queryEmailForTeamMember(backgroundCheckVO);
		backgroundCheckVO.setBackgroundConfirmInd(confID);
		backgroundCheckVO.setEmail(email);
		backgroundCheckVO.setEmailAlt(altEmail);
		provUserEmail = adminProfile.getEmail();
		
		//If the Email and Alternate Email are same CC will not be attached.  
		if (provUserEmail.equalsIgnoreCase(email)) {	
			if(altEmail.equalsIgnoreCase(email)) {
				ccArr = null;
			} else {
				ccArr = new String[1];
				ccArr[0]=new String(altEmail);
			}
		} else {
			if(altEmail.equalsIgnoreCase(email)) {
				ccArr = new String[1];
				ccArr[0]=new String(email);
			} else {
				ccArr = new String[2];
				ccArr[0]=new String(provUserEmail);
				ccArr[1]=new String(altEmail);
			}
		}
		tMBackgroundCheckVO.setCcArr(ccArr);
		tMBackgroundCheckVO.setFirstName(backgroundCheckVO.getFirstName());
		tMBackgroundCheckVO.setLastName(backgroundCheckVO.getLastName());
	
		int backgroundStatus =0;
		try {
			backgroundStatus = teamProfileDAO.getBackgroundStatus(backgroundCheckVO);	
			teamProfileDAO.update(backgroundCheckVO);
			//Mails will be sent for background check when the status is either not started or 
			//Pending submission.
			//Added by bnatara
			if(OrderConstants.TEAM_BACKGROUND_NOT_STARTED == backgroundStatus
					|| OrderConstants.TEAM_BACKGROUND_PENDING_SUBMISSION == backgroundStatus || ("Y").equals(tMBackgroundCheckVO.getRecertificationIndParam())   ) {
				
				
				if(("Y").equals(tMBackgroundCheckVO.getRecertificationIndParam()) && null!=tMBackgroundCheckVO.getEncryptedResourceIdSsn())
				{
					//R11_1
					//Jira SL-20434
					tMBackgroundCheckVO.setEncryptedPlusOneKey(tMBackgroundCheckVO.getEncryptedResourceIdSsn());
				}
				else
				{
					//R12_2
					//SL-20553
					tMBackgroundCheckVO.setResourceId(iVendorResourceDao.getBgOriginalResourceId(String.valueOf(backgroundCheckVO.getResourceId())));
					
					//R11_0 To encrypt Plus One key
					tMBackgroundCheckVO.setEncryptedPlusOneKey(URLEncoder.encode(iVendorResourceDao.getResourcePlusOneKey(tMBackgroundCheckVO.getResourceId())));
				}
				
				logger.info("b4 sendingmail ++++++altemail---"+tMBackgroundCheckVO.getResourceAltEmail()+"===email===="+tMBackgroundCheckVO.getResourceEmail()+"==fN=="+tMBackgroundCheckVO.getFirstName()+"==LN=="+tMBackgroundCheckVO.getLastName());
				iProviderEmailBO.sendBackgroundCheckEmail(tMBackgroundCheckVO);
				
				//Moved - To update only when the user agrees for Back Ground Check.
				getAuditBusinessBean().auditResourceBackgroundReCheck(backgroundCheckVO.getResourceId(),AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_PENDING_SUBMISSION,tMBackgroundCheckVO.getRecertificationIndParam());
			}	
			
			if(!("Y").equals(tMBackgroundCheckVO.getRecertificationIndParam())){
			activityRegistryDao.updateResourceActivityStatus(backgroundCheckVO.getResourceId().toString(), ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
			}
		} catch (Exception dse) {
			throw new BusinessServiceException(dse);
		}
		return false;
	}
	
	public List getResourceActivityStatus(int vendorId) throws BusinessServiceException {
		List resourceActKeyList = null;
		try {
			resourceActKeyList = activityRegistryDao.queryResourceActivityStatus(vendorId);
		} catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return resourceActKeyList;
	}
	
	/**
	 * Returns the Team Grid List for the Vendor associated with the
	 * Vendor_id that is passed in
	 * @param TeamMemberVO teamMemberVO
	 * @return List
	 */
	public List<TeamMemberVO> getTeamGridDetails(TeamMemberVO teamMemberVO)	throws BusinessServiceException {
		List<TeamMemberVO> teamList = null;
		try {
			logger.info("-----------------Start getTeamGridDetails------------------");
			// query for the team grid details
			teamList = new ArrayList<TeamMemberVO>();
			teamList.addAll(teamProfileDAO.getTeamGridDetails(teamMemberVO));
			logger.info("-----------------End getTeamGridDetails------------------"+teamList.size());
		} catch (DataServiceException dse) {
			logger.error("[BusinessServiceException thrown while building the team member grid list.]", dse);
			throw new BusinessServiceException(dse);
		}
		return teamList;
	}
	
	/**
	 * Returns the encrypted plusOneKey for the given resource_id
	 * @param String resourceId
	 * @return String
	 */
	public String getEncryptedPlusOneKey(String resourceId) throws BusinessServiceException, DataServiceException {
		String encryptedPlusOneKey = null;
		try {
			encryptedPlusOneKey = iVendorResourceDao.getResourcePlusOneKey(resourceId);
		} catch (DBException e) {
			logger.error(e.getMessage(), e);
		}
		return encryptedPlusOneKey;
	}
	
	/**
	 * Returns the background check status for the given resource_id
	 * @param String resourceId
	 * @return String
	 */
	public String getBackgroundCheckStatus(String resourceId) throws BusinessServiceException, DataServiceException {
		String backgroundCheckStatus = null;
		try {
			backgroundCheckStatus = iVendorResourceDao.getBackgroundChckStatus(resourceId);
			logger.info("backgroundCheckStatus:"+backgroundCheckStatus);
		} catch (DBException e) {
			logger.error(e.getMessage(), e);
		}
		return backgroundCheckStatus;
	}
	
	/**
	 * Returns the marketplace indicator for the given resource_id
	 * @param String resourceId
	 * @return int
	 */
	public int getMarketPlaceIndicator(String resourceId) throws BusinessServiceException {
		int marketPlaceIndicator = 0;
		try {
			marketPlaceIndicator = iVendorResourceDao.getMarketPlaceIndicator(resourceId);
			logger.info("backgroundCheckStatus:"+marketPlaceIndicator);
		} catch (DBException e) {
			logger.error(e.getMessage(), e);
		}
		return marketPlaceIndicator;
	}

	//SL-19667 Update background_state_id in vendor_resource and sl_pro_bknd_chk tables
	public void updateBackgroundCheckStatus(String resourceId) throws BusinessServiceException {			
		try {
			// update background_state_id as '28'-Pending submission
			int rowsUpdated=iVendorResourceDao.updateBackgroundCheckStatus(resourceId);
			if(rowsUpdated>0){
				//insert background check history
			GeneralInfoVO generalInfoVO=new GeneralInfoVO();
			generalInfoVO.setChangedComment(MPConstants.NEW_SCREENING);
			generalInfoVO.setResourceId(resourceId);   
			iVendorResourceDao.insertBcHistory(generalInfoVO);
			}
			activityRegistryDao.updateResourceActivityStatus(resourceId, ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
	}
	
	//SL-19667 update recertification status.
	public void recertify(String resourceId)throws BusinessServiceException{	
	try {
		
		int rowsUpdated=iVendorResourceDao.recertify(resourceId); 
		if(rowsUpdated>0){ 
			//insert background check history
		GeneralInfoVO generalInfoVO=new GeneralInfoVO();
		generalInfoVO.setChangedComment(MPConstants.RECERT_SUB);
		generalInfoVO.setResourceId(resourceId);   
		iVendorResourceDao.insertBcHistory(generalInfoVO);
		}
		
	} catch (Exception e) {
		throw new BusinessServiceException(e);
	}
 
	}
	
	//SL-19667 sharing background info
	public void doShare(String resourceId)throws BusinessServiceException{
		//insert background check history
		GeneralInfoVO generalInfoVO=new GeneralInfoVO();
		generalInfoVO.setChangedComment(MPConstants.SHARE_BKND_CHK);
		generalInfoVO.setResourceId(resourceId);   
		iVendorResourceDao.insertBcHistory(generalInfoVO);
		
	}


	public BackgroundCheckVO getBackgroundCheckInfo(Integer resourceId)throws BusinessServiceException{
		
		BackgroundCheckVO backgroundCheckVO=null;
		
		try {
			backgroundCheckVO=iVendorResourceDao.getBackgroundCheckInfo(resourceId);
			return backgroundCheckVO;
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		
		
	}
	
	
	
	public BackgroundCheckVO isBackgroundCheckRecertification(Integer resourceId)throws BusinessServiceException{
		BackgroundCheckVO backgroundCheckVO=null;
		
		try {
			backgroundCheckVO=iVendorResourceDao.isBackgroundCheckRecertification(resourceId);
			return backgroundCheckVO;
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
	}

	public boolean isRecertificationDateDisplay (String resourceId)throws  BusinessServiceException{
		boolean isRecertificationDateDisplay;
		try {
			isRecertificationDateDisplay=iVendorResourceDao.isRecertificationDateDisplay(resourceId);
			return isRecertificationDateDisplay;
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		
	}

	//SL-20434
	public String getResourceSSNLastFour(String resourceId) throws BusinessServiceException {
		String ssnLastFour = null;
		try {
			ssnLastFour = iVendorResourceDao.getResourceSSNLastFour(resourceId);
		} catch (DBException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessServiceException(e);
		}
		return ssnLastFour;
	}
	
	//R12_2
	//SL-20553
	public String getBgOriginalResourceId(String resourceId) throws BusinessServiceException {
			String originalResourceId = null;
			try {
				originalResourceId = iVendorResourceDao.getBgOriginalResourceId(resourceId);
			} catch (DBException e) {
				logger.error(e.getMessage(), e);
				throw new BusinessServiceException(e);
			}
			return originalResourceId;
		}

	

}
