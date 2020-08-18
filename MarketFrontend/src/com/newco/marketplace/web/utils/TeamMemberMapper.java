package com.newco.marketplace.web.utils;


import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.web.dto.provider.BackgroundCheckDTO;
import com.newco.marketplace.web.dto.provider.TeamProfileDTO;

public class TeamMemberMapper extends ObjectMapper{

	public Object convertDTOtoVO(Object objectDto, Object objectVO) {
		TeamProfileDTO teamProfileDTO = (TeamProfileDTO) objectDto;
		UserProfile userProfile=null;
		TeamMemberVO teamMemberVO=null;
		if(objectVO instanceof UserProfile){
			userProfile = (UserProfile) objectVO;
			userProfile.setUserName(teamProfileDTO.getUserName());
			userProfile.setVendorId(teamProfileDTO.getVendorId());
			return userProfile;
		}
		if(objectVO instanceof TeamMemberVO){
			teamMemberVO = (TeamMemberVO) objectVO;
			teamMemberVO.setVendorId(teamProfileDTO.getVendorId());
			return teamMemberVO;
		}
		return null;
	}

	public TeamProfileDTO convertVOtoDTO(Object objectVO, Object objectDto) {
		TeamProfileDTO teamProfileDTO = (TeamProfileDTO) objectDto;
		TeamMemberVO teamMemberVO = (TeamMemberVO) objectVO;
		return teamProfileDTO;
	}
	/*public TeamProfileDTO convertVOtoDTO(Object objectVO, Object objectDto) {
		TeamProfileDTO teamProfileDTO = (TeamProfileDTO) objectDto;
		TeamMemberVO teamMemberVO = (TeamMemberVO) objectVO;
		
		return teamProfileDTO;
	}*/



	/*
	 * MTedder
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public BackgroundCheckVO convertDTOtoVOBackgroundCheck(Object objectDto, Object objectVO) {
		
		BackgroundCheckVO backgroundCheckVO = (BackgroundCheckVO) objectVO;		
		BackgroundCheckDTO teamProfileDTO = (BackgroundCheckDTO) objectDto;
		System.out.println("convertDTOtoVOBackgroundCheck(): " + teamProfileDTO.getResourceId());
		//mapping
		backgroundCheckVO.setResourceId(teamProfileDTO.getResourceId());
		backgroundCheckVO.setEmail(teamProfileDTO.getEmail());
		backgroundCheckVO.setEmailAlt(teamProfileDTO.getEmailAlt());
		backgroundCheckVO.setLastName(teamProfileDTO.getLastName());
		backgroundCheckVO.setFirstName(teamProfileDTO.getFirstName());
		if(teamProfileDTO.getBackgroundConfirmInd() != null && !"".equalsIgnoreCase(teamProfileDTO.getBackgroundConfirmInd())){
			backgroundCheckVO.setBackgroundConfirmInd(Integer.valueOf(teamProfileDTO.getBackgroundConfirmInd()));//convert from String to Integer for VO
		}
		
		/**
		 * Added fields to map Provider Name and User Name
		 * author - bnatara
		 */
		backgroundCheckVO.setProvUserName(teamProfileDTO.getProvUserName());
		backgroundCheckVO.setUserName(teamProfileDTO.getUserName());
		
		/**
		 * Added to pass the background check status.
		 * author - bnatara
		 */
		backgroundCheckVO.setBgCheckStatus(teamProfileDTO.getBgCheckStatus());
		
		//R11_0 To set fourth parameter in Team Background Check Email
		if(teamProfileDTO.isBackgroundCheckRecertify())
		{
			backgroundCheckVO.setRecertificationInd("Y");
		}
		else
		{
			backgroundCheckVO.setRecertificationInd("N");
		}
		
		//R11_1
		//Jira SL-20434
		backgroundCheckVO.setEncryptedResourceIdSsn(teamProfileDTO.getEncryptedResourceIdSsn());
		
		return backgroundCheckVO;
	}
	/*
	 * MTedder
	 */
	public BackgroundCheckDTO convertVOtoDTOBackgroundCheck(Object objectVO, Object objectDto) {
		BackgroundCheckVO backgroundCheckVO = (BackgroundCheckVO) objectVO;		
		BackgroundCheckDTO teamProfileDTO = (BackgroundCheckDTO) objectDto;		
		System.out.println("convertVOtoDTOBackgroundCheck(): " + backgroundCheckVO.getResourceId());
		//mapping
		teamProfileDTO.setResourceId(backgroundCheckVO.getResourceId());
		teamProfileDTO.setEmail(backgroundCheckVO.getEmail());
		teamProfileDTO.setEmailAlt(backgroundCheckVO.getEmailAlt());;
		teamProfileDTO.setLastName(backgroundCheckVO.getLastName());
		teamProfileDTO.setFirstName(backgroundCheckVO.getFirstName());
		if(backgroundCheckVO.getBackgroundConfirmInd() != null){
			teamProfileDTO.setBackgroundConfirmInd(String.valueOf(backgroundCheckVO.getBackgroundConfirmInd()));//convert from Integer to string for DTO
		}
		
		/**
		 * Added fields to map Provider Name and User Name
		 * author - bnatara
		 */
		teamProfileDTO.setUserName(backgroundCheckVO.getUserName());
		teamProfileDTO.setProvUserName(backgroundCheckVO.getProvUserName());
		
		/**
		 * Added to pass the background check status.
		 * author - bnatara
		 */
		teamProfileDTO.setBgCheckStatus(backgroundCheckVO.getBgCheckStatus());
		
		return teamProfileDTO;
	}
	
	/*
	 * MTedder
	 */
	public TMBackgroundCheckVO convertBackgroundCheckVOtoTMBackgroundCheckVO(Object objectVO, Object objectTMVO){
		BackgroundCheckVO backgroundCheckVO = (BackgroundCheckVO) objectVO;		
		TMBackgroundCheckVO tMBackgroundCheckVO = (TMBackgroundCheckVO) objectTMVO;
		//mapping
		tMBackgroundCheckVO.setResourceId(String.valueOf(backgroundCheckVO.getResourceId()));
		tMBackgroundCheckVO.setVendorEmail(backgroundCheckVO.getEmail());	
		
		return tMBackgroundCheckVO;
	}
}
