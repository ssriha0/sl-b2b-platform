package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.permission.UserRoleVO;
import com.newco.marketplace.dto.vo.permission.UserVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.AdminAddEditUserDTO;
import com.newco.marketplace.web.dto.AdminManageUsersUserDTO;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersAddEditDTO;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersUserDTO;


/**
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 01:13:46 $
 */
public class ManageUsersMapper {

	private static final Logger logger = Logger.getLogger(ManageUsersMapper.class.getName());
	
	public static List<AdminManageUsersUserDTO> convertUserListToAdminList(List<UserVO> userList)
	{
		if(userList == null)
			return null;
		
		List<AdminManageUsersUserDTO> dtoList = new ArrayList<AdminManageUsersUserDTO>();
		for(UserVO userVO : userList){
			dtoList.add(ManageUsersMapper.convertUserVOToAdminRow(userVO));
		}
		
		return dtoList;
	}

	public static List<BuyerAdminManageUsersUserDTO> convertUserListToBuyerList(List<UserVO> userList)
	{
		if(userList == null)
			return null;

		List<BuyerAdminManageUsersUserDTO> dtoList = new ArrayList<BuyerAdminManageUsersUserDTO>();
		for(UserVO userVO : userList) {
			dtoList.add(ManageUsersMapper.convertUserVOToBuyerRow(userVO));
		}
		
		return dtoList;
	}
	
	public static AdminManageUsersUserDTO convertUserVOToAdminRow(UserVO userVO)
	{
		if(userVO == null){
			return null;
		}
		
		AdminManageUsersUserDTO dto = new AdminManageUsersUserDTO();

		List<UserRoleVO> roles = userVO.getUserRoles();
		String roleName = null;
		if (!roles.isEmpty()) {
			roleName = roles.get(0).getRoleDescription();
		}
		dto.setName(userVO.getFirstName() + " " + userVO.getLastName());
		dto.setRole(roleName);
		dto.setTitle(userVO.getJobTitle());
		dto.setUsername(userVO.getUserName());
		dto.setResourceId(userVO.getResourceId().intValue());
		return dto;
	}

	public static BuyerAdminManageUsersUserDTO convertUserVOToBuyerRow(UserVO userVO)	
	{
		if(userVO == null)
			return null;
		
		BuyerAdminManageUsersUserDTO dto = new BuyerAdminManageUsersUserDTO();
		
		StringBuilder builder = new StringBuilder();
		builder.append(userVO.getFirstName())
		       .append(" ")
		       .append(userVO.getLastName());
		       //.append(" (")
		       //.append(userVO.getResourceId())
		       //.append(")");
		
		dto.setUsername(userVO.getUserName());
		dto.setName(builder.toString());
		dto.setMaxSpendLimit(userVO.getMaxSpendLimitPerSO());
		dto.setResourceId(userVO.getResourceId());
		dto.setCompanyRoles(userVO.getUserRoles());
		dto.setUserActivities(userVO.getUserActivities());
		dto.setTitle(userVO.getJobTitle());
		
		return dto;
	}
	
	public static AdminAddEditUserDTO convertUserVOToUserDTO(UserVO vo)
	{
		if(vo == null)
			return null;
		
		AdminAddEditUserDTO dto = new AdminAddEditUserDTO();
		vo.setRoleTypeId(OrderConstants.NEWCO_ADMIN_ROLEID);
		dto.setUsername(vo.getUserName());
		
		dto.setEmail(vo.getEmail());
		dto.setEmailConfirm(vo.getEmail());
		
		dto.setFirstName(vo.getFirstName());
		dto.setLastName(vo.getLastName());
		dto.setMiddleName(vo.getMiddleName());
		dto.setSuffix(vo.getSuffix());
		
		dto.setJobTitle(vo.getJobTitle());			
		
		dto.setActivitiesList(vo.getUserActivities());
		dto.setNeverLoggedIn(vo.isNeverLoggedIn());
		
		List<UserRoleVO> roles = vo.getUserRoles();
		if (null != roles && !roles.isEmpty()) {
			dto.setJobRole(((UserRoleVO)roles.get(0)).getCompanyRoleId() + "");
		}

		dto.setEditable(vo.isEditable());
		
		return dto;		
	}
	
	public static BuyerAdminManageUsersAddEditDTO convertUserVOToBuyerUserDTO(UserVO vo)
	{
		if(vo == null)
			return null;
		
		BuyerAdminManageUsersAddEditDTO dto = new BuyerAdminManageUsersAddEditDTO();
		if(dto == null)
			return null;

		vo.setRoleTypeId(OrderConstants.NEWCO_ADMIN_ROLEID);		
		dto.setUserName(vo.getUserName());
		
		dto.setPriEmail(vo.getEmail());
		dto.setConfirmPriEmail(vo.getEmail());

		dto.setAltEmail(vo.getAltEmail());
		dto.setConfirmAltEmail(vo.getAltEmail());
		
		dto.setFirstName(vo.getFirstName());
		dto.setLastName(vo.getLastName());
		dto.setMiddleName(vo.getMiddleName());
		dto.setSuffix(vo.getSuffix());
		dto.setEditable(vo.isEditable());
		dto.setJobTitle(vo.getJobTitle());
		dto.setNeverLoggedIn(vo.isNeverLoggedIn());

		String number;
		number = vo.getBusinessPhone();
		if(SLStringUtils.isNullOrEmpty(number) == false)
		{
			dto.setPhoneBusinessAreaCode(SLStringUtils.getAreaCodeFromPhoneNumber(number));
			dto.setPhoneBusinessPart1(SLStringUtils.getPart1FromPhoneNumber(number));
			dto.setPhoneBusinessPart2(SLStringUtils.getPart2FromPhoneNumber(number));
		}
		if(SLStringUtils.isNullOrEmpty(vo.getPhoneExt()) == false)
		dto.setPhoneBusinessExt(vo.getPhoneExt());

		number = vo.getBusinessFax();
		if(SLStringUtils.isNullOrEmpty(number) == false)
		{
			dto.setFaxBusinessAreaCode(SLStringUtils.getAreaCodeFromPhoneNumber(number));
			dto.setFaxBusinessPart1(SLStringUtils.getPart1FromPhoneNumber(number));
			dto.setFaxBusinessPart2(SLStringUtils.getPart2FromPhoneNumber(number));
		}
		
		number = vo.getMobilePhone();
		if(SLStringUtils.isNullOrEmpty(number) == false)
		{
			dto.setPhoneMobileAreaCode(SLStringUtils.getAreaCodeFromPhoneNumber(number));
			dto.setPhoneMobilePart1(SLStringUtils.getPart1FromPhoneNumber(number));
			dto.setPhoneMobilePart2(SLStringUtils.getPart2FromPhoneNumber(number));
		}
		
		if(vo.getMaxSpendLimitPerSO() != null && vo.getMaxSpendLimitPerSO() > 0.0)
			dto.setMaxSpendLimit(UIUtils.formatDollarAmount(vo.getMaxSpendLimitPerSO().doubleValue()));
		
		// Selected roles
		dto.setJobRoleList(vo.getUserRoles());
		
		// Selected Activities
		dto.setActivitiesList(vo.getUserActivities());
		
		return dto;		
	}
	
	public static UserVO convertBuyerUserDTOToUserVO(BuyerAdminManageUsersAddEditDTO dto)
	{
		if(dto == null)
			return null;
		
		UserVO vo = new UserVO();

		vo.setRoleTypeId(OrderConstants.BUYER_ROLEID);
		vo.setUserName(dto.getUserName());
		vo.setEmail(dto.getPriEmail());
		vo.setAltEmail(dto.getAltEmail());
		vo.setFirstName(dto.getFirstName());
		vo.setMiddleName(dto.getMiddleName());
		vo.setLastName(dto.getLastName());
		vo.setSuffix(dto.getSuffix());
		//SL-19704
		if(StringUtils.isNotBlank(vo.getUserName())){
			vo.setUserName(vo.getUserName().trim());
		}
		if(StringUtils.isNotBlank(vo.getFirstName())){
			vo.setFirstName(vo.getFirstName().trim());
		}
		if(StringUtils.isNotBlank(vo.getMiddleName())){
			vo.setMiddleName(vo.getMiddleName().trim());
		}
		if(StringUtils.isNotBlank(vo.getLastName())){
			vo.setLastName(vo.getLastName().trim());
		}
		
		try{
		if(dto.getAcceptTermsAndConditions() == null || dto.getAcceptTermsAndConditions().equalsIgnoreCase("0")){
			throw new Exception("Terms & Conditions must be accepted or not null");
		}
		} catch (Exception e){
			return null;
		}
		vo.setTerms_cond_id(dto.getTermsCondId());
		vo.setTerm_cond_ind(Integer.parseInt(dto.getAcceptTermsAndConditions()));
		vo.setJobTitle(dto.getJobTitle());
		vo.setBuyerId(dto.getBuyerId());
		boolean isParsableDouble = SLStringUtils.IsParsableNumber(dto.getMaxSpendLimit());
		if(isParsableDouble)
			vo.setMaxSpendLimitPerSO(Double.parseDouble(dto.getMaxSpendLimit()));
		else
			vo.setMaxSpendLimitPerSO(0.0);
		
		String businessPhone = "";
		if(dto.getPhoneBusinessAreaCode() != null)
		{
			businessPhone += dto.getPhoneBusinessAreaCode();
		}
		if(dto.getPhoneBusinessPart1() != null)
		{
			businessPhone += dto.getPhoneBusinessPart1();
		}
		if(dto.getPhoneBusinessAreaCode() != null)
		{
			businessPhone += dto.getPhoneBusinessPart2();
		}
		vo.setBusinessPhone(businessPhone);
		
		String businessFax= "";
		if(dto.getFaxBusinessAreaCode() != null)
		{
			businessFax += dto.getFaxBusinessAreaCode();
		}
		if(dto.getFaxBusinessPart1() != null)
		{
			businessFax += dto.getFaxBusinessPart1();
		}
		if(dto.getFaxBusinessPart2() != null)
		{
			businessFax += dto.getFaxBusinessPart2();
		}
		vo.setBusinessFax(businessFax);
		
		String mobilePhone = "";
		if(dto.getPhoneMobileAreaCode() != null)
		{
			mobilePhone += dto.getPhoneMobileAreaCode();
		}
		if(dto.getPhoneBusinessPart1() != null)
		{
			mobilePhone += dto.getPhoneMobilePart1();
		}
		if(dto.getPhoneBusinessAreaCode() != null)
		{
			mobilePhone += dto.getPhoneMobilePart2();
		}
		vo.setMobilePhone(mobilePhone);
		
		// Extract selected roles
		vo.setUserRoles(dto.getJobRoleList());
		
		// Extract Activities
		vo.setUserActivities(dto.getActivitiesList());
		
		return vo;		
	}
	

	public static UserVO convertAdminUserDTOToUserVO(AdminAddEditUserDTO dto)
	{
		if(dto == null)
			return null;
		
		UserVO vo = new UserVO();
		
		vo.setUserName(dto.getUsername());
		vo.setEmail(dto.getEmail());
		vo.setFirstName(dto.getFirstName());
		vo.setMiddleName(dto.getMiddleName());
		vo.setLastName(dto.getLastName());
		vo.setSuffix(dto.getSuffix());
		vo.setJobTitle(dto.getJobTitle());
		//SL-19704
		if(StringUtils.isNotBlank(vo.getUserName())){
			vo.setUserName(vo.getUserName().trim());
		}
		if(StringUtils.isNotBlank(vo.getFirstName())){
			vo.setFirstName(vo.getFirstName().trim());
		}
		if(StringUtils.isNotBlank(vo.getMiddleName())){
			vo.setMiddleName(vo.getMiddleName().trim());
		}
		if(StringUtils.isNotBlank(vo.getLastName())){
			vo.setLastName(vo.getLastName().trim());
		}
		
		List<UserRoleVO> userRoles = new ArrayList<UserRoleVO>();
		UserRoleVO userRole = new UserRoleVO();
		try {
			userRole.setCompanyRoleId(Integer.parseInt(dto.getJobRole()));
		} catch (NumberFormatException e) {
			logger.info("Company role id is not numeric.  No value is being saved");
		}
		userRoles.add(userRole);
		vo.setUserRoles(userRoles);

		vo.setUserActivities(dto.getActivitiesList());
		vo.setRoleTypeId(OrderConstants.NEWCO_ADMIN_ROLEID);
		
		return vo;		
	}
	
}