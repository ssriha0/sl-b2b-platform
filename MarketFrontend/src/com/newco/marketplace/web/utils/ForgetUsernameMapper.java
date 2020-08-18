package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.web.dto.provider.ForgotUsernameDto;

public class ForgetUsernameMapper extends ObjectMapper{

	public Object convertDTOtoVO(Object objectDto, Object objectVO) {
		ForgotUsernameDto forgotUsernameDto = (ForgotUsernameDto) objectDto;
		
		LostUsernameVO lostUsernameVO=(LostUsernameVO)objectVO;
		if(objectVO instanceof LostUsernameVO){
			lostUsernameVO.setUserId(forgotUsernameDto.getRoleId());
			lostUsernameVO.setEmailAddress(forgotUsernameDto.getEmail());
			lostUsernameVO.setUserName(forgotUsernameDto.getUserName());
			lostUsernameVO.setQuestionTxtAnswer(forgotUsernameDto.getQuestionTxtAnswer());
			lostUsernameVO.setQuestionTxt(forgotUsernameDto.getQuestionTxt());
			lostUsernameVO.setQuestionId(forgotUsernameDto.getQuestionId());
			//author - bnatara
			lostUsernameVO.setResourceId(forgotUsernameDto.getResourceId());
			lostUsernameVO.setZip(forgotUsernameDto.getZip());
			lostUsernameVO.setBusinessName(forgotUsernameDto.getBusinessName());
			lostUsernameVO.setPhoneNo(forgotUsernameDto.getPhoneNo());
			lostUsernameVO.setLockedInd(forgotUsernameDto.getLockedInd());
			lostUsernameVO.setDetailedProfile(forgotUsernameDto.isDetailedProfile());
			return lostUsernameVO;
		}
		return null;
	}
	/*
	 * MTedder@covansys.com
	 */
	public ForgotUsernameDto convertVOtoDTO(Object objectVO, Object objectDto) {
	
		ForgotUsernameDto forgotUsernameDto = (ForgotUsernameDto) objectDto;
		LostUsernameVO lostUsernameVO =((LostUsernameVO)objectVO);	
		
		forgotUsernameDto.setEmail(lostUsernameVO.getEmailAddress());
		forgotUsernameDto.setRoleId(lostUsernameVO.getUserId());
		forgotUsernameDto.setQuestionId(lostUsernameVO.getQuestionId());
		forgotUsernameDto.setQuestionTxt(lostUsernameVO.getQuestionTxt());
		forgotUsernameDto.setQuestionTxtAnswer(lostUsernameVO.getQuestionTxtAnswer());
		forgotUsernameDto.setSuccess(lostUsernameVO.getSuccessAns());
		forgotUsernameDto.setUserName(lostUsernameVO.getUserName());
		forgotUsernameDto.setPwdInd(lostUsernameVO.getPwdInd());
		forgotUsernameDto.setZip(lostUsernameVO.getZip());
		forgotUsernameDto.setBusinessName(lostUsernameVO.getBusinessName());
		forgotUsernameDto.setPhoneNo(lostUsernameVO.getPhoneNo());
		forgotUsernameDto.setDetailedProfile(lostUsernameVO.isDetailedProfile());
		forgotUsernameDto.setLockedInd(lostUsernameVO.getLockedInd());		
		forgotUsernameDto.setModifiedDate(lostUsernameVO.getModifiedDate());
		return forgotUsernameDto;
	}
}
