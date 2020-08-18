package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.List;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ITeamProfileBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.web.delegates.provider.ITeamMemberDelegate;
import com.newco.marketplace.web.dto.provider.BackgroundCheckDTO;
import com.newco.marketplace.web.dto.provider.TeamProfileDTO;
import com.newco.marketplace.web.utils.TeamMemberMapper;

public class TeamMemberDelegateImpl implements ITeamMemberDelegate {

	private ITeamProfileBO iTeamProfileBO;
	private TeamMemberMapper teamMemberMapper;
	private static final Logger localLogger = Logger
			.getLogger(TeamMemberDelegateImpl.class.getName());

	/**
	 * @param teamProfileBO
	 * @param teamMemberVO
	 * @param teamMemberMapper
	 */
	public TeamMemberDelegateImpl(ITeamProfileBO teamProfileBO,
			 TeamMemberMapper teamMemberMapper) {		
		iTeamProfileBO = teamProfileBO;
		this.teamMemberMapper = teamMemberMapper;
	}

	public TeamProfileDTO getTeamMemberList(TeamProfileDTO teamProfileDTO)
			throws DelegateException {

		try {
			UserProfile profile = new UserProfile();
			profile = (UserProfile)teamMemberMapper.convertDTOtoVO(teamProfileDTO, profile);
			teamProfileDTO.setTeamMemberList(iTeamProfileBO.getTeamMemberList(profile));
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @TeamMemberDelegateImpl.getTeamMemberList() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @TeamMemberDelegateImpl.getTeamMemberList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @TeamMemberDelegateImpl.getTeamMemberList() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @TeamMemberDelegateImpl.getTeamMemberList() due to "
							+ ex.getMessage());
		}

		return teamProfileDTO;
	}

	/*
	 * MTedder
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.ITeamMemberDelegate#queryEmailForTeamMember(com.newco.marketplace.dto.vo.provider.TeamMemberVO)
	 */
	public BackgroundCheckDTO queryEmailForTeamMember(BackgroundCheckDTO teamProfileDTO) throws DelegateException	
	{	
		System.out.println("queryEmailForTeamMember: " + teamProfileDTO.getResourceId());
		BackgroundCheckVO backgroundCheckVO = new BackgroundCheckVO();
		backgroundCheckVO = teamMemberMapper.convertDTOtoVOBackgroundCheck(teamProfileDTO, backgroundCheckVO);
		
		try {
			backgroundCheckVO = iTeamProfileBO.queryEmailForTeamMember(backgroundCheckVO);
		} catch (BusinessServiceException ex) {
			
			throw new DelegateException(
					"General Exception @TeamMemberDelegateImpl.queryEmailForTeamMember() due to "
							+ ex.getMessage());			
		}
		teamProfileDTO = teamMemberMapper.convertVOtoDTOBackgroundCheck(backgroundCheckVO, teamProfileDTO);
//				
//		try{
//			teamProfileDTO = iTeamProfileBO.queryEmailForTeamMember(teamProfileDTO);
//		} catch(BusinessServiceException ex){
//			throw new DelegateException("Business Service @TeamMemberDelegateImpl.getTeamMemberList() due to " + ex.getMessage());			
//		}
//		
//		teamProfileDTO = teamMemberMapper.convertVOtoDTO(teamMemberVO, teamProfileDTO);
		return teamProfileDTO;
	}
	
	public boolean saveBackgroundCheckData(BackgroundCheckDTO teamProfileDTO) throws DelegateException
	{
		System.out.println("saveBackgroundCheckData: delegate");
		BackgroundCheckVO backgroundCheckVO = new BackgroundCheckVO();
		backgroundCheckVO = teamMemberMapper.convertDTOtoVOBackgroundCheck(teamProfileDTO, backgroundCheckVO);
		try {
			iTeamProfileBO.saveBackgroundCheckData(backgroundCheckVO);			
		} catch (BusinessServiceException ex) {
			throw new DelegateException(
					"General Exception @TeamMemberDelegateImpl.saveBackgroundCheckData() due to "
							+ ex.getMessage());			
		}
		return true;	
	}
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws DelegateException
	 */
	public List getResourceActivityStatus(TeamProfileDTO teamProfileDTO) throws DelegateException
	{
		List resourceActKeyList = null;
		try
		{
			localLogger.info("inside delegate");	
			resourceActKeyList = iTeamProfileBO.getResourceActivityStatus(teamProfileDTO.getVendorId().intValue());
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceActivityStatus() due to "
							+ ex.getMessage());			
		}
		return resourceActKeyList;
	}
	
	/**
	 * 
	 * @param TeamProfileDTO teamProfileDTO 
	 * @return
	 * @throws DelegateException
	 */
	public TeamProfileDTO getTeamGridDetails(TeamProfileDTO teamProfileDTO) throws DelegateException
	{
		List <TeamMemberVO>vendorTeamGridList= null;
		try
		{
			localLogger.info("inside delegate getTeamGridDetails()----------:Start");
			TeamMemberVO teamMemberVO = new TeamMemberVO();
			teamMemberVO=(TeamMemberVO)teamMemberMapper.convertDTOtoVO(teamProfileDTO, teamMemberVO);
			localLogger.info("inside delegate vendor Id----------:"+teamMemberVO.getVendorId());	
			vendorTeamGridList = iTeamProfileBO.getTeamGridDetails(teamMemberVO);
			teamProfileDTO.setTeamMemberList(vendorTeamGridList);
			localLogger.info("inside delegate getTeamGridDetails()----------:End");
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getTeamGridDetails() due to "
							+ ex.getMessage());			
		}
		return teamProfileDTO;
	}
	
	/**
	 * Returns the encrypted plusOneKey for the given resource_id
	 * @param String resourceId
	 * @return String
	 */
	public String getEncryptedPlusOneKey(String resourceId) throws DelegateException
	{
		String encryptedPlusOneKey = null;
		try
		{			
			encryptedPlusOneKey = iTeamProfileBO.getEncryptedPlusOneKey(resourceId);
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceActivityStatus() due to "
							+ ex.getMessage());			
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedPlusOneKey;
	}
	
	/**
	 * Returns the background check status for the given resource_id
	 * @param String resourceId
	 * @return String
	 */
	public String getBackgroundCheckStatus(String resourceId) throws DelegateException
	{
		String backgroundCheckStatus = null;
		try
		{			
			backgroundCheckStatus = iTeamProfileBO.getBackgroundCheckStatus(resourceId);
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceActivityStatus() due to "
							+ ex.getMessage());			
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backgroundCheckStatus;
	}
	
	/**
	 * Returns the marketplace indicator for the given resource_id
	 * @param String resourceId
	 * @return int
	 */
	public int getMarketPlaceIndicator(String resourceId) throws DelegateException
	{
		int marketPlaceIndicator = 0;
		try
		{				
			marketPlaceIndicator = iTeamProfileBO.getMarketPlaceIndicator(resourceId);  
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceActivityStatus() due to "
							+ ex.getMessage());			
		}
		return marketPlaceIndicator;
	}
	
	//SL-19667 update background check status.
	public void updateBackgroundCheckStatus(String resourceId) throws DelegateException{
		try
		{			
			iTeamProfileBO.updateBackgroundCheckStatus(resourceId); 
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.updateBackgroundCheckStatus() due to "
							+ ex.getMessage());			
		}
	}
	//SL-19667 update recertification status.
	public void recertify(String resourceId)throws DelegateException{
		try
		{			
			iTeamProfileBO.recertify(resourceId); 
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.recertify() due to "
							+ ex.getMessage());			
		}
	
		
	}
	//SL-19667 sharing the background Info
	public void doShare(String resourceId)throws DelegateException{
		try
		{			
			iTeamProfileBO.doShare(resourceId); 
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.doShare() due to "
							+ ex.getMessage());			
		}
	
	}

	
	
	public BackgroundCheckVO getBackgroundCheckInfo(Integer resourceId)throws DelegateException{
		try
		{			 
			BackgroundCheckVO backgroundCheckVO =iTeamProfileBO.getBackgroundCheckInfo(resourceId); 
			return backgroundCheckVO;
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceActivityStatus() due to "
							+ ex.getMessage());			
		}
	}
	
	
	
	public BackgroundCheckVO isBackgroundCheckRecertification(Integer resourceId)throws DelegateException{
		try
		{			 
			BackgroundCheckVO backgroundCheckVO =iTeamProfileBO.isBackgroundCheckRecertification(resourceId); 
			return backgroundCheckVO;
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceActivityStatus() due to "
							+ ex.getMessage());			
		}
	}
	
	public boolean isRecertificationDateDisplay (String resourceId)throws DelegateException{
		try
		{			 
			boolean isRecertificationDateDispaly =iTeamProfileBO.isRecertificationDateDisplay(resourceId); 
			return isRecertificationDateDispaly;
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.isRecertificationDateDispaly() due to "
							+ ex.getMessage());			
		}
	}

	//R11_1
	//Jira SL-20434
	public String getResourceSSNLastFour(String resourceId) throws DelegateException
	{
		String ssnLastFour = null;
		try
		{			
			ssnLastFour = iTeamProfileBO.getResourceSSNLastFour(resourceId);
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getResourceSSNLastFour() due to "
							+ ex.getMessage());			
		} 
		return ssnLastFour;
	}

	//R12_2
	//Jira SL-20553
	public String getBgOriginalResourceId(String resourceId) throws DelegateException
	{
		String originalResourceId = null;
		try
		{			
				originalResourceId = iTeamProfileBO.getBgOriginalResourceId(resourceId);
		}
		catch (BusinessServiceException ex) {
				throw new DelegateException(	"General Exception @TeamMemberDelegateImpl.getBgOriginalResourceId() due to "
									+ ex.getMessage());			
		} 
			return originalResourceId;
	}		
	
}
