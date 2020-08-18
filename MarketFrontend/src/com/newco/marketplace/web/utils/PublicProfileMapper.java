package com.newco.marketplace.web.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import com.newco.marketplace.web.dto.provider.PublicProfileDto;



public class PublicProfileMapper extends ObjectMapper{
	
	
	public PublicProfileVO convertDTOtoVO(Object objectDto, Object objectVO) {
		PublicProfileDto publicProfileDto = (PublicProfileDto) objectDto;
		PublicProfileVO publicProfileVO = (PublicProfileVO) objectVO;
		if (publicProfileVO != null && publicProfileDto!=null) {
			publicProfileVO.setVendorId(publicProfileDto.getVendorId());
			publicProfileVO.setResourceId(publicProfileDto.getResourceId());
						
			publicProfileVO.setDispAddCity(publicProfileDto.getDispAddCity());
			publicProfileVO.setDispAddState(publicProfileDto.getDispAddState());
			publicProfileVO.setDispAddZip(publicProfileDto.getDispAddZip());
			publicProfileVO.setDispAddGeographicRange(publicProfileDto.getDispAddGeographicRange());
			
			publicProfileVO.setUserId(publicProfileDto.getUserId());
			//publicProfileVO.setBusStartDt(publicProfileDto.getBusStartDt());
			publicProfileVO.setCompanySize(publicProfileDto.getCompanySize());
			
		}
		return publicProfileVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public PublicProfileDto convertVOtoDTO(Object objectVO, Object objectDto) {
		PublicProfileVO publicProfileVO = (PublicProfileVO) objectVO;
		PublicProfileDto publicProfileDto = (PublicProfileDto) objectDto;
		GeneralInfoVO tmpGeneralInfoVO = null;
		MarketPlaceVO tmpMarketPlaceVO =null;
		if (publicProfileVO != null && publicProfileDto!= null) {
			
			
			publicProfileDto.setCompleteFlag(publicProfileVO.isCompleteFlag());
					
			
			publicProfileDto.setVendorId(publicProfileVO.getVendorId());
			publicProfileDto.setResourceId(publicProfileVO.getResourceId());
			publicProfileDto.setCompanySize(publicProfileVO.getCompanySize());
			
			
			publicProfileDto.setDispAddCity(publicProfileVO.getDispAddCity());
			publicProfileDto.setDispAddState(publicProfileVO.getDispAddState());
			publicProfileDto.setDispAddZip(publicProfileVO.getDispAddZip());
			publicProfileDto.setDispAddGeographicRange(publicProfileVO.getDispAddGeographicRange());
			 if(publicProfileVO.getBusStartDt()!=null){
				 publicProfileDto.setBusStartDt(formatDate(publicProfileVO.getBusStartDt()));
	            }
			 if(publicProfileVO.getJoinDate()!=null)
			 {
				 publicProfileDto.setJoinDate(formatDate(publicProfileVO.getJoinDate()));
			 }
		
			publicProfileDto.setUserId(publicProfileVO.getUserId());
			publicProfileDto.setFirstName(publicProfileVO.getFirstName());
			publicProfileDto.setLastName(publicProfileVO.getLastName());
			if(publicProfileVO.getCredentialsList()!= null)
				publicProfileDto.setCredentialsList(publicProfileVO.getCredentialsList());
			
			if(publicProfileVO.getLanguageList()!=null)
				publicProfileDto.setLanguageList(publicProfileVO.getLanguageList());
			publicProfileDto.setCredType(publicProfileVO.getCredType());
			if(publicProfileVO.getBgChkAppDate()!=null)
			{
				publicProfileDto.setBgChkAppDate(formatDate(publicProfileVO.getBgChkAppDate()));
			}
			if(publicProfileVO.getBgChkReqDate()!=null)
			{
				publicProfileDto.setBgChkReqDate(formatDate(publicProfileVO.getBgChkReqDate()));
			}
			if(publicProfileVO.getResourceSkillList()!=null)
			{
				publicProfileDto.setResourceSkillList(publicProfileVO.getResourceSkillList());
			}
			/*######  Added  for Complete Profile - Starts###*/
			if(publicProfileVO.getUserName() != null)
			{
				publicProfileDto.setUserName(publicProfileVO.getUserName());
				
			}
			if(publicProfileVO.getSsnLast4() != null)
			{
				publicProfileDto.setSsnLast4(publicProfileVO.getSsnLast4());
				
			}
			
			publicProfileDto.setAdminInd(publicProfileVO.getAdminInd());
			publicProfileDto.setManagerInd(publicProfileVO.getManagerInd());
			publicProfileDto.setOwnerInd(publicProfileVO.getOwnerInd());
			publicProfileDto.setDispatchInd(publicProfileVO.getDispatchInd());
			publicProfileDto.setResourceInd(publicProfileVO.getResourceInd());
			publicProfileDto.setOtherInd(publicProfileVO.getOtherInd());
			publicProfileDto.setSproInd(publicProfileVO.getSproInd());
			
			publicProfileDto.setOtherJobTitle(publicProfileVO.getOtherJobTitle());
			publicProfileDto.setHourlyRate(publicProfileVO.getHourlyRate());
			publicProfileDto.setMarketPlaceInd(publicProfileVO.getMarketPlaceInd());
			publicProfileDto.setScheduleTimeList(publicProfileVO.getScheduleTimeList());
			publicProfileDto.setActivityList(publicProfileVO.getActivityList());
		
			
			if(publicProfileVO.getGeneralScheduleInfoVO() != null)
			{
				
			tmpGeneralInfoVO = publicProfileVO.getGeneralScheduleInfoVO();
			
			if(tmpGeneralInfoVO.getSunStart() != null)
			publicProfileDto.setSunStart(formatTime(tmpGeneralInfoVO.getSunStart()));
			if(tmpGeneralInfoVO.getSunEnd() != null)
			publicProfileDto.setSunEnd(formatTime(tmpGeneralInfoVO.getSunEnd()));
			if(tmpGeneralInfoVO.getMonStart() != null)
			publicProfileDto.setMonStart(formatTime(tmpGeneralInfoVO.getMonStart()));
			if(tmpGeneralInfoVO.getMonEnd() != null)
			publicProfileDto.setMonEnd(formatTime(tmpGeneralInfoVO.getMonEnd()));
			if(tmpGeneralInfoVO.getTueStart() != null)
			publicProfileDto.setTueStart(formatTime(tmpGeneralInfoVO.getTueStart()));
			if(tmpGeneralInfoVO.getTueEnd() != null)
			publicProfileDto.setTueEnd(formatTime(tmpGeneralInfoVO.getTueEnd()));
			if(tmpGeneralInfoVO.getWedStart() != null)
			publicProfileDto.setWedStart(formatTime(tmpGeneralInfoVO.getWedStart()));
			
			if(tmpGeneralInfoVO.getWedEnd() != null)
			publicProfileDto.setWedEnd(formatTime(tmpGeneralInfoVO.getWedEnd()));
			if(tmpGeneralInfoVO.getThuStart() != null)
			publicProfileDto.setThuStart(formatTime(tmpGeneralInfoVO.getThuStart()));
			if(tmpGeneralInfoVO.getThuEnd() != null)
			publicProfileDto.setThuEnd(formatTime(tmpGeneralInfoVO.getThuEnd()));
			if(tmpGeneralInfoVO.getFriStart() != null)
			publicProfileDto.setFriStart(formatTime(tmpGeneralInfoVO.getFriStart()));
			if(tmpGeneralInfoVO.getTueEnd() != null)
			publicProfileDto.setFriEnd(formatTime(tmpGeneralInfoVO.getFriEnd()));
			if(tmpGeneralInfoVO.getSatStart() != null)
			publicProfileDto.setSatStart(formatTime(tmpGeneralInfoVO.getSatStart()));
			if(tmpGeneralInfoVO.getSatEnd() != null)
			publicProfileDto.setSatEnd(formatTime(tmpGeneralInfoVO.getSatEnd()));
			if(tmpGeneralInfoVO.getMon24Ind()!= null)
			publicProfileDto.setMon24Ind(tmpGeneralInfoVO.getMon24Ind());
			if(tmpGeneralInfoVO.getMonNaInd() != null)
			publicProfileDto.setMonNaInd(tmpGeneralInfoVO.getMonNaInd());
			
			publicProfileDto.setTue24Ind(tmpGeneralInfoVO.getTue24Ind());
			publicProfileDto.setTueNaInd(tmpGeneralInfoVO.getTueNaInd());
			
			publicProfileDto.setWed24Ind(tmpGeneralInfoVO.getWed24Ind());
			publicProfileDto.setWedNaInd(tmpGeneralInfoVO.getWedNaInd());
			
			publicProfileDto.setThu24Ind(tmpGeneralInfoVO.getThu24Ind());
			publicProfileDto.setThuNaInd(tmpGeneralInfoVO.getThuNaInd());
			
			publicProfileDto.setFri24Ind(tmpGeneralInfoVO.getFri24Ind());
			publicProfileDto.setFriNaInd(tmpGeneralInfoVO.getFriNaInd());
			
			publicProfileDto.setSat24Ind(tmpGeneralInfoVO.getSat24Ind());
			publicProfileDto.setSatNaInd(tmpGeneralInfoVO.getSatNaInd());
			
			publicProfileDto.setSun24Ind(tmpGeneralInfoVO.getSun24Ind());
			publicProfileDto.setSunNaInd(tmpGeneralInfoVO.getSunNaInd());
			
			
//			publicProfileDto.setMonStart(covertTimeStringToDate(tmpGeneralInfoVO.getMonStart()));
//			publicProfileDto.setMonEnd(covertTimeStringToDate(tmpGeneralInfoVO.getMonEnd()));
//			publicProfileDto.setTueStart(covertTimeStringToDate(tmpGeneralInfoVO.getTueStart()));
//			publicProfileDto.setTueEnd(covertTimeStringToDate(tmpGeneralInfoVO.getTueEnd()));
//			publicProfileDto.setWedStart(covertTimeStringToDate(tmpGeneralInfoVO.getWedStart()));
//			publicProfileDto.setWedEnd(covertTimeStringToDate(tmpGeneralInfoVO.getWedEnd()));
//			publicProfileDto.setThuStart(covertTimeStringToDate(tmpGeneralInfoVO.getThuStart()));
//			publicProfileDto.setThuEnd(covertTimeStringToDate(tmpGeneralInfoVO.getThuEnd()));
//			publicProfileDto.setFriStart(covertTimeStringToDate(tmpGeneralInfoVO.getFriStart()));
//			publicProfileDto.setFriEnd(covertTimeStringToDate(tmpGeneralInfoVO.getFriEnd()));
//			publicProfileDto.setSatStart(covertTimeStringToDate(tmpGeneralInfoVO.getSatStart()));
//			publicProfileDto.setSatEnd(covertTimeStringToDate(tmpGeneralInfoVO.getSatEnd()));
//			publicProfileDto.setSunStart(covertTimeStringToDate(tmpGeneralInfoVO.getSunStart()));
//			publicProfileDto.setSunEnd(covertTimeStringToDate(tmpGeneralInfoVO.getSunEnd()));
			}
			if(publicProfileVO.getMarketPlaceVO() != null)
			{
			tmpMarketPlaceVO = publicProfileVO.getMarketPlaceVO();
			if(tmpMarketPlaceVO.getBusinessPhone() != null)
			{
				String businessPhone =   tmpMarketPlaceVO.getBusinessPhone();
				if (businessPhone != null 
						&& 	!businessPhone.equals("")
						&&	businessPhone.length() == 10)
					{
					publicProfileDto.setBusinessPhone1(businessPhone.substring(0,3));
					publicProfileDto.setBusinessPhone2(businessPhone.substring(3,6));
					publicProfileDto.setBusinessPhone3(businessPhone.substring(6,10));
					}
				
			}
			
			publicProfileDto.setBusinessExtn(tmpMarketPlaceVO.getBusinessExtn());
			
			if(tmpMarketPlaceVO.getMobilePhone() != null)
			{
				String mobilePhone =   tmpMarketPlaceVO.getMobilePhone();
				if (mobilePhone != null 
						&& 	!mobilePhone.equals("")
						&&	mobilePhone.length() == 10)
					{
					publicProfileDto.setMobilePhone1(mobilePhone.substring(0,3));
					publicProfileDto.setMobilePhone2(mobilePhone.substring(3,6));
					publicProfileDto.setMobilePhone3(mobilePhone.substring(6,10));
					}
			}
			if(tmpMarketPlaceVO.getSmsAddress() != null 
					&& 	!tmpMarketPlaceVO.getSmsAddress().equals("")
					&&	tmpMarketPlaceVO.getSmsAddress().length() == 10){
				String sms = tmpMarketPlaceVO.getSmsAddress();
			publicProfileDto.setSmsAddress1(sms.substring(0,3));
			publicProfileDto.setSmsAddress2(sms.substring(3,6));
			publicProfileDto.setSmsAddress3(sms.substring(6,10));
			}
			publicProfileDto.setEmail(tmpMarketPlaceVO.getEmail());
			publicProfileDto.setAltEmail(tmpMarketPlaceVO.getAltEmail());
			
			
			if(publicProfileVO.getServiceLiveStatus() != null)
			publicProfileDto.setServiceLiveStatus(publicProfileVO.getServiceLiveStatus());
			/*######  Added  for Complete Profile - Ends###*/
			
			}
			
		}
		return publicProfileDto;
	}
	
	
	private String formatTime(Date dateString){
		
		 Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		 
        String stringDate1=null;
         if(dateString!=null) {
             stringDate1= DateFormat.getTimeInstance(DateFormat.SHORT).format(dateString);
         }
		return stringDate1;
	}
	
	private String formatDate(Date dateString){
		
		 Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		 
       String stringDate1=null;
        if(dateString!=null) {
            stringDate1= formatter.format(dateString);
        }
		return stringDate1;
	}
//	private Date covertTimeStringToDate(String str){
//		if (str!=null && str.trim().length()>0) {
//			str="20070101:" + str;
//			SimpleDateFormat timeStringformater = new SimpleDateFormat("yyyyMMdd:hh:mm a");
//			try {
//				return timeStringformater.parse(str);
//			}catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		return null;
//	}
	
}
