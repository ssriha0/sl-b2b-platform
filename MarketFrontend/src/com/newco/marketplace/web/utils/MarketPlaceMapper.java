package com.newco.marketplace.web.utils;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.web.dto.provider.MarketPlaceDTO;

/**
 * 
 * @author Covansys - Offshore
 *
 * $Revision: 1.10 $ $Author: glacy $ $Date: 2008/04/26 01:13:45 $
 */

/*
 * Maintenance History
 * $Log: MarketPlaceMapper.java,v $
 * Revision 1.10  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.8.10.1  2008/04/23 11:41:32  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.9  2008/04/23 05:19:55  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.8  2008/02/19 21:06:05  langara
 * LMA: Alert issues:  The provider admin email addr is not being saved when the team mbr is added as not being able to logon. Commented out the email addr setter so that it does not default to null.  Also, the SMS is being saved in the table as null,null,null.  Modified the code so that if part 1, part 2 and part 3 are not null, then save the sms number or default the setter to null.
 *
 * Revision 1.7  2007/12/13 14:18:49  hrajago
 * Added for Provider Admin functionality
 *
 * Revision 1.6  2007/12/09 02:33:16  hrajago
 * Iteration-14 - Check for unique primary email address
 *
 * Revision 1.5  2007/12/07 20:20:49  mhaye05
 * removed call to set general activities
 *
 */
public class MarketPlaceMapper extends ObjectMapper {
	
	public MarketPlaceVO convertDTOtoVO(Object objectDto, Object objectVO) {
		MarketPlaceDTO marketPlaceDTO = (MarketPlaceDTO) objectDto;
		MarketPlaceVO marketPlaceVO = (MarketPlaceVO) objectVO;
		if (objectDto != null && objectVO!=null) {
			
			marketPlaceVO.setManageUser(marketPlaceDTO.getManageUser());
			marketPlaceVO.setManageSO(marketPlaceDTO.getManageSO());
			
			//All the Phone number values are appended to form a single value for 
			//DB insertion.
			marketPlaceVO.setBusinessPhone(marketPlaceDTO.getBusinessPhone1() 
											+ marketPlaceDTO.getBusinessPhone2()
											+ marketPlaceDTO.getBusinessPhone3());
			marketPlaceVO.setBusinessExtn(marketPlaceDTO.getBusinessExtn());
			
			//All the Mobile number values are appended to form a single value for 
			//DB insertion.
			marketPlaceVO.setMobilePhone(marketPlaceDTO.getMobilePhone1()
											+ marketPlaceDTO.getMobilePhone2()
											+ marketPlaceDTO.getMobilePhone3());
			
			marketPlaceVO.setEmail(marketPlaceDTO.getEmail());
			marketPlaceVO.setConfirmEmail(marketPlaceDTO.getConfirmEmail());
			
			marketPlaceVO.setAltEmail(marketPlaceDTO.getAltEmail());
			marketPlaceVO.setConfirmAltEmail(marketPlaceDTO.getConfirmAltEmail());
			
			//LMA...Do not save the smsAddress with nullnullnull just (null)
			String smsAddress=null;
			
			if (marketPlaceDTO.getSmsAddress1() != null && marketPlaceDTO.getSmsAddress2() != null &&
					marketPlaceDTO.getSmsAddress3() !=null){
				smsAddress = marketPlaceDTO.getSmsAddress1() + marketPlaceDTO.getSmsAddress2() + 
				marketPlaceDTO.getSmsAddress3();
			}
			
			marketPlaceVO.setSmsAddress(smsAddress);
			//END LMA
			
			marketPlaceVO.setSecondaryContact1(marketPlaceDTO.getSecondaryContact1());
			marketPlaceVO.setSecondaryContact2(marketPlaceDTO.getSecondaryContact2());
			
			marketPlaceVO.setPrimaryContact(marketPlaceDTO.getPrimaryContact());
			
			marketPlaceVO.setPrimaryContList(marketPlaceDTO.getPrimaryContList());
			marketPlaceVO.setSecondaryContList(marketPlaceDTO.getSecondaryContList());
			marketPlaceVO.setActivityList(marketPlaceDTO.getActivityList());
			marketPlaceVO.setResultList(marketPlaceDTO.getResultList());
			
			marketPlaceVO.setServiceCall(marketPlaceDTO.getServiceCall());
			marketPlaceVO.setUserName(marketPlaceDTO.getUserName());
			marketPlaceVO.setEditEmailInd(marketPlaceDTO.getEditEmailInd());
			marketPlaceVO.setResourceID(marketPlaceDTO.getResourceID());
			marketPlaceVO.setEntityId(marketPlaceDTO.getEntityId());
			marketPlaceVO.setRoleID(marketPlaceDTO.getRoleId());
			marketPlaceVO.setLoggedInResourceId(marketPlaceDTO.getLoggedInResourceId());
			
			/*
			 * Added for 'Provider Admin' 
			 */
			marketPlaceVO.setPrimaryIndicator(marketPlaceDTO.getPrimaryIndicator());
			
			//R16_1: SL-18979: Variable to store logged in userName
			marketPlaceVO.setLoggedInUserName(marketPlaceDTO.getLoginUserName());
			
		}
		return marketPlaceVO;
	}
	
	public MarketPlaceDTO convertVOtoDTO(Object objectVO, Object objectDto) {
		MarketPlaceVO marketPlaceVO = (MarketPlaceVO) objectVO;
		MarketPlaceDTO marketPlaceDTO = (MarketPlaceDTO) objectDto;
		if (objectVO != null && objectDto!= null) {
			
			marketPlaceDTO.setManageUser(marketPlaceVO.getManageUser());
			marketPlaceDTO.setManageSO(marketPlaceVO.getManageSO());
			
			String businessPhone = marketPlaceVO.getBusinessPhone();
			if (businessPhone != null 
			&& 	!businessPhone.equals("")
			&&	businessPhone.length() == 10)
			{
				marketPlaceDTO.setBusinessPhone1(businessPhone.substring(0,3));
				marketPlaceDTO.setBusinessPhone2(businessPhone.substring(3,6));
				marketPlaceDTO.setBusinessPhone3(businessPhone.substring(6,10));
			}
			marketPlaceDTO.setBusinessExtn(marketPlaceVO.getBusinessExtn());
			
			String mobilePhone = marketPlaceVO.getMobilePhone();
			if (mobilePhone != null 
			&& 	!mobilePhone.equals("")
			&&	mobilePhone.length() == 10)
			{
				marketPlaceDTO.setMobilePhone1(mobilePhone.substring(0,3));
				marketPlaceDTO.setMobilePhone2(mobilePhone.substring(3,6));
				marketPlaceDTO.setMobilePhone3(mobilePhone.substring(6,10));
			}
			
			marketPlaceDTO.setEmail(marketPlaceVO.getEmail());
			/**
			 * From VO to DTO the EMAIL and Confirm EMAIL will be same.
			 */
			marketPlaceDTO.setConfirmEmail(marketPlaceVO.getEmail());
			
			marketPlaceDTO.setAltEmail(marketPlaceVO.getAltEmail());
			marketPlaceDTO.setConfirmAltEmail(marketPlaceVO.getAltEmail());
			
			String smsNumber = marketPlaceVO.getSmsAddress();
			if (smsNumber != null 
			&& 	!smsNumber.equals("")
			&&	smsNumber.length() == 10)
			{
				marketPlaceDTO.setSmsAddress1(smsNumber.substring(0,3));
				marketPlaceDTO.setSmsAddress2(smsNumber.substring(3,6));
				marketPlaceDTO.setSmsAddress3(smsNumber.substring(6,10));
				
				marketPlaceDTO.setConfirmSmsAddress1(smsNumber.substring(0,3));
				marketPlaceDTO.setConfirmSmsAddress2(smsNumber.substring(3,6));
				marketPlaceDTO.setConfirmSmsAddress3(smsNumber.substring(6,10));
			}
			
			marketPlaceDTO.setSecondaryContact1(marketPlaceVO.getSecondaryContact1());
			marketPlaceDTO.setSecondaryContact2(marketPlaceVO.getSecondaryContact2());
			
			marketPlaceDTO.setPrimaryContact(marketPlaceVO.getPrimaryContact());
			
			marketPlaceDTO.setPrimaryContList(marketPlaceVO.getPrimaryContList());
			marketPlaceDTO.setSecondaryContList(marketPlaceVO.getSecondaryContList());
			marketPlaceDTO.setActivityList(marketPlaceVO.getActivityList());
			marketPlaceDTO.setResultList(marketPlaceVO.getResultList());
			
			marketPlaceDTO.setServiceCall(marketPlaceVO.getServiceCall());
			marketPlaceDTO.setUserName(marketPlaceVO.getUserName());
			marketPlaceDTO.setEditEmailInd(marketPlaceVO.getEditEmailInd());
			marketPlaceDTO.setResourceID(marketPlaceVO.getResourceID());
			marketPlaceDTO.setEntityId(marketPlaceVO.getEntityId());
			marketPlaceDTO.setRoleId(marketPlaceVO.getRoleID());
			marketPlaceDTO.setLoggedInResourceId(marketPlaceVO.getLoggedInResourceId());
			
			/*
			 * Added for 'Provider Admin' 
			 */
			marketPlaceDTO.setPrimaryIndicator(marketPlaceVO.getPrimaryIndicator());
			
			//R16_1: SL-18979: Variable to store vibes error
			if (StringUtils.isNotBlank(marketPlaceVO.getVibesError())){
				marketPlaceDTO.setVibesError(marketPlaceVO.getVibesError());
			}
		}
		return marketPlaceDTO;
	}
	 

}
