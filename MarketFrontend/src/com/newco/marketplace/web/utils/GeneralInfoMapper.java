package com.newco.marketplace.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.web.dto.provider.GeneralInfoDto;


/**
 * @author KSudhanshu
 *
 * $Revision: 1.25 $ $Author: glacy $ $Date: 2008/04/26 01:13:46 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class GeneralInfoMapper extends ObjectMapper {

	private static final Logger logger = Logger.getLogger(GeneralInfoMapper.class.getName());
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object, java.lang.Object)
	 */
	public GeneralInfoVO convertDTOtoVO(Object objectDto, Object objectVO) {
		GeneralInfoDto generalInfoDto = (GeneralInfoDto) objectDto;
		GeneralInfoVO generalInfoVO = (GeneralInfoVO) objectVO;
		if (generalInfoVO != null && generalInfoDto!=null) {
			generalInfoVO.setVendorId(generalInfoDto.getVendorId());
			generalInfoVO.setResourceId(generalInfoDto.getResourceId());
			generalInfoVO.setContactId(generalInfoDto.getContactId());
			generalInfoVO.setLocationId(generalInfoDto.getLocationId());	
			
			 //Added as per JIRA#SL-20083: To check if username already exists for this user or not in DB
			generalInfoVO.setUserNameExists(generalInfoDto.isUserNameExists());
		
			//Added to find User is Service Pro or not
			generalInfoVO.setPrimaryInd(generalInfoDto.getPrimaryIndicator());
			
			generalInfoVO.setMarketPlaceInd(generalInfoDto.getMarketPlaceInd());
			generalInfoVO.setFirstName(generalInfoDto.getFirstName());
			generalInfoVO.setMiddleName(generalInfoDto.getMiddleName());
			generalInfoVO.setLastName(generalInfoDto.getLastName());
			generalInfoVO.setSuffix(generalInfoDto.getSuffix());
			
			generalInfoVO.setSsnLast4(generalInfoDto.getSsnRight());
			if(null != generalInfoDto.getSsnLeft() || null != generalInfoDto.getSsnMiddle())
			{
				if(generalInfoDto.getSsnLeft().equals("###") || generalInfoDto.getSsnMiddle().equals("##"))
				{
					if ((generalInfoDto.getSsn() != null)&&(generalInfoDto.getSsn().length() == 9)){
						generalInfoVO.setSsn(generalInfoDto.getSsn());
					}
					else{
						generalInfoVO.setSsn(null);
					}
				}
				else
					generalInfoVO.setSsn(generalInfoDto.getSsn());
			}
			else
			{
				generalInfoVO.setSsn(generalInfoDto.getSsn());
			}
			generalInfoVO.setDispAddStreet1(generalInfoDto.getDispAddStreet1());
			generalInfoVO.setDispAddStreet2(generalInfoDto.getDispAddStreet2());
			generalInfoVO.setDispAddApt(generalInfoDto.getDispAddApt());
			generalInfoVO.setDispAddCity(generalInfoDto.getDispAddCity());
			generalInfoVO.setDispAddState(generalInfoDto.getDispAddState());
			generalInfoVO.setDispAddZip(generalInfoDto.getDispAddZip());
			generalInfoVO.setDispAddGeographicRange(generalInfoDto.getDispAddGeographicRange());
			
			if (StringUtils.isNotEmpty(generalInfoDto.getHourlyRate()))
				try {
					generalInfoVO.setHourlyRate(Double.parseDouble(generalInfoDto.getHourlyRate()));
				}catch (Exception e) {
					e.printStackTrace();
				} else {
					generalInfoVO.setHourlyRate(0);
			}
			
			generalInfoVO.setMonStart(covertTimeStringToDate(generalInfoDto.getMonStart()));
			generalInfoVO.setMonEnd(covertTimeStringToDate(generalInfoDto.getMonEnd()));
			generalInfoVO.setTueStart(covertTimeStringToDate(generalInfoDto.getTueStart()));
			generalInfoVO.setTueEnd(covertTimeStringToDate(generalInfoDto.getTueEnd()));
			generalInfoVO.setWedStart(covertTimeStringToDate(generalInfoDto.getWedStart()));
			generalInfoVO.setWedEnd(covertTimeStringToDate(generalInfoDto.getWedEnd()));
			generalInfoVO.setThuStart(covertTimeStringToDate(generalInfoDto.getThuStart()));
			generalInfoVO.setThuEnd(covertTimeStringToDate(generalInfoDto.getThuEnd()));
			generalInfoVO.setFriStart(covertTimeStringToDate(generalInfoDto.getFriStart()));
			generalInfoVO.setFriEnd(covertTimeStringToDate(generalInfoDto.getFriEnd()));
			generalInfoVO.setSatStart(covertTimeStringToDate(generalInfoDto.getSatStart()));
			generalInfoVO.setSatEnd(covertTimeStringToDate(generalInfoDto.getSatEnd()));
			generalInfoVO.setSunStart(covertTimeStringToDate(generalInfoDto.getSunStart()));
			generalInfoVO.setSunEnd(covertTimeStringToDate(generalInfoDto.getSunEnd()));
			generalInfoVO.setUserName(generalInfoDto.getUserName());
			generalInfoVO.setOtherJobTitle(generalInfoDto.getOtherJobTitle());
			generalInfoVO.setOwnerInd(generalInfoDto.getOwnerInd());
			generalInfoVO.setDispatchInd(generalInfoDto.getDispatchInd());
			generalInfoVO.setManagerInd(generalInfoDto.getManagerInd());
			generalInfoVO.setResourceInd(generalInfoDto.getResourceInd());
			generalInfoVO.setAdminInd(generalInfoDto.getAdminInd());
			generalInfoVO.setOtherInd(generalInfoDto.getOtherInd());
			generalInfoVO.setSproInd(generalInfoDto.getSproInd());
			generalInfoVO.setUserExistInd(generalInfoDto.getUserExistInd());
			generalInfoVO.setActiveInactiveInd(generalInfoDto.getActiveInactiveInd());
			
			// Added as per SLT 2432 to save/update zipcodes coverage
			generalInfoVO.setCoverageZip(generalInfoDto.getCoverageZip());
			generalInfoVO.setZipcodesUpated(generalInfoDto.isZipcodesUpated());
			generalInfoVO.setStateZipcodeVO(generalInfoDto.getStateZipCodeList());
			// Added as per SLT 2695 to save out-of-state zipcodes	

			generalInfoVO.setGeographicalRange(generalInfoDto.getGeographicalRange());
			generalInfoVO.setScheduleTimeList(generalInfoDto.getScheduleTimeList());
		
			generalInfoVO.setStateType(generalInfoDto.getStateType());//MTedder@covansys.com
			generalInfoVO.setStateTypeList(generalInfoDto.getStateTypeList());//MTedder@covansys.com
			
			/**
			 * Added for Week Indicator - Covansys (Offshore)
			 */
			generalInfoVO.setMon24Ind(generalInfoDto.getMon24Ind());
			generalInfoVO.setMonNaInd(generalInfoDto.getMonNaInd());
			
			generalInfoVO.setTue24Ind(generalInfoDto.getTue24Ind());
			generalInfoVO.setTueNaInd(generalInfoDto.getTueNaInd());
			
			generalInfoVO.setWed24Ind(generalInfoDto.getWed24Ind());
			generalInfoVO.setWedNaInd(generalInfoDto.getWedNaInd());
			
			generalInfoVO.setThu24Ind(generalInfoDto.getThu24Ind());
			generalInfoVO.setThuNaInd(generalInfoDto.getThuNaInd());
			
			generalInfoVO.setFri24Ind(generalInfoDto.getFri24Ind());
			generalInfoVO.setFriNaInd(generalInfoDto.getFriNaInd());
			
			generalInfoVO.setSat24Ind(generalInfoDto.getSat24Ind());
			generalInfoVO.setSatNaInd(generalInfoDto.getSatNaInd());
			
			generalInfoVO.setSun24Ind(generalInfoDto.getSun24Ind());
			generalInfoVO.setSunNaInd(generalInfoDto.getSunNaInd());
			generalInfoVO.setUserNameAdmin(generalInfoDto.getUserNameAdmin());
		}
		return generalInfoVO;
	}
	
	// SL-19667  made code change to convert dto to vo
	  public GeneralInfoVO convertDTOObjecttoVO(GeneralInfoDto generalInfoDto) {
		  GeneralInfoVO generalInfoVO =new GeneralInfoVO();
		  
		 // setting ssn
			if(null != generalInfoDto.getSsnLeft() || null != generalInfoDto.getSsnMiddle())
			{
				if(generalInfoDto.getSsnLeft().equals("###") || generalInfoDto.getSsnMiddle().equals("##"))
				{
					if ((generalInfoDto.getSsn() != null)&&(generalInfoDto.getSsn().length() == 9)){
						generalInfoVO.setSsn(generalInfoDto.getSsn());
					}
					else{
						generalInfoVO.setSsn(null);
					}
				}
				else
					generalInfoVO.setSsn(generalInfoDto.getSsn());
			}
			else
			{
				generalInfoVO.setSsn(generalInfoDto.getSsn());
			}
			// Encripting ssn number
			String resourceSsn = generalInfoVO.getSsn();
			if (resourceSsn != null && resourceSsn.trim().length()>0){
				
				generalInfoVO.setSsn(CryptoUtil.encryptKeyForSSNAndPlusOne(resourceSsn));			
			} else {
				generalInfoVO.setSsn(null);
			}
			
			// setting vendor Id
			generalInfoVO.setVendorId(generalInfoDto.getVendorId());
			// setting resourceId
			generalInfoVO.setResourceId(generalInfoDto.getResourceId());
		  return generalInfoVO;
	
	  }
	
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public GeneralInfoDto convertVOtoDTO(Object objectVO, Object objectDto) {
		GeneralInfoVO generalInfoVO = (GeneralInfoVO) objectVO;
		GeneralInfoDto generalInfoDto = (GeneralInfoDto) objectDto;
		if (generalInfoVO != null && generalInfoDto!= null) {
			
			generalInfoDto.setVendorId(generalInfoVO.getVendorId());
			generalInfoDto.setResourceId(generalInfoVO.getResourceId());
			generalInfoDto.setContactId(generalInfoVO.getContactId());
			generalInfoDto.setLocationId(generalInfoVO.getLocationId());
			
			//Added as per JIRA#SL-20083: To check if username already exists for this user or not in DB
			generalInfoDto.setUserNameExists(generalInfoVO.isUserNameExists()); // Added 
			
			//Added to find User is Service Pro or not
			generalInfoDto.setPrimaryIndicator(generalInfoVO.getPrimaryInd());
			
			generalInfoDto.setMarketPlaceInd(generalInfoVO.getMarketPlaceInd());
			generalInfoDto.setFirstName(generalInfoVO.getFirstName());
			generalInfoDto.setMiddleName(generalInfoVO.getMiddleName());
			generalInfoDto.setLastName(generalInfoVO.getLastName());
			generalInfoDto.setSuffix(generalInfoVO.getSuffix());

			generalInfoDto.setSsnRight(generalInfoVO.getSsnLast4());
			
			generalInfoDto.setDispAddStreet1(generalInfoVO.getDispAddStreet1());
			generalInfoDto.setDispAddStreet2(generalInfoVO.getDispAddStreet2());
			generalInfoDto.setDispAddApt(generalInfoVO.getDispAddApt());
			generalInfoDto.setDispAddCity(generalInfoVO.getDispAddCity());
			generalInfoDto.setDispAddState(generalInfoVO.getDispAddState());
			generalInfoDto.setDispAddZip(generalInfoVO.getDispAddZip());
			generalInfoDto.setDispAddGeographicRange(generalInfoVO.getDispAddGeographicRange());
			
			try{
				if (generalInfoVO.getHourlyRate() > 0) 
				{
					String houtlyRate  = String.valueOf(generalInfoVO.getHourlyRate());
					
					if(null != houtlyRate && 
							houtlyRate.substring(houtlyRate.indexOf(".")+1).length() != 2 && 
							houtlyRate.substring(houtlyRate.indexOf(".")+1).equals("0"))
						houtlyRate = houtlyRate.substring(0,houtlyRate.indexOf(".")+1)+"00";
					generalInfoDto.setHourlyRate(houtlyRate);
				}
				else 
					generalInfoDto.setHourlyRate("");
			}catch (Exception e) {
				generalInfoDto.setHourlyRate("");
			}
			
			generalInfoDto.setMonStart(covertDateToTimeString(generalInfoVO.getMonStart()));
			generalInfoDto.setMonEnd(covertDateToTimeString(generalInfoVO.getMonEnd()));
			generalInfoDto.setTueStart(covertDateToTimeString(generalInfoVO.getTueStart()));
			generalInfoDto.setTueEnd(covertDateToTimeString(generalInfoVO.getTueEnd()));
			generalInfoDto.setWedStart(covertDateToTimeString(generalInfoVO.getWedStart()));
			generalInfoDto.setWedEnd(covertDateToTimeString(generalInfoVO.getWedEnd()));
			generalInfoDto.setThuStart(covertDateToTimeString(generalInfoVO.getThuStart()));
			generalInfoDto.setThuEnd(covertDateToTimeString(generalInfoVO.getThuEnd()));
			generalInfoDto.setFriStart(covertDateToTimeString(generalInfoVO.getFriStart()));
			generalInfoDto.setFriEnd(covertDateToTimeString(generalInfoVO.getFriEnd()));
			generalInfoDto.setSatStart(covertDateToTimeString(generalInfoVO.getSatStart()));
			generalInfoDto.setSatEnd(covertDateToTimeString(generalInfoVO.getSatEnd()));
			generalInfoDto.setSunStart(covertDateToTimeString(generalInfoVO.getSunStart()));
			generalInfoDto.setSunEnd(covertDateToTimeString(generalInfoVO.getSunEnd()));
			generalInfoDto.setUserName(generalInfoVO.getUserName());
			generalInfoDto.setOtherJobTitle(generalInfoVO.getOtherJobTitle());
			generalInfoDto.setOwnerInd(generalInfoVO.getOwnerInd());
			generalInfoDto.setDispatchInd(generalInfoVO.getDispatchInd());
			generalInfoDto.setManagerInd(generalInfoVO.getManagerInd());
			generalInfoDto.setResourceInd(generalInfoVO.getResourceInd());
			generalInfoDto.setAdminInd(generalInfoVO.getAdminInd());
			generalInfoDto.setOtherInd(generalInfoVO.getOtherInd());
			generalInfoDto.setSproInd(generalInfoVO.getSproInd());
			generalInfoDto.setGeographicalRange(generalInfoVO.getGeographicalRange());
			generalInfoDto.setScheduleTimeList(generalInfoVO.getScheduleTimeList());
			generalInfoDto.setUserExistInd(generalInfoVO.getUserExistInd());
			generalInfoDto.setActiveInactiveInd(generalInfoVO.getActiveInactiveInd());
			
			// Added as per SLT 2432 to save zipcodes coverage
			generalInfoDto.setCoverageZip(generalInfoVO.getCoverageZip());
			generalInfoDto.setZipcodesUpated(generalInfoVO.isZipcodesUpated());
			
			// Added as per SLT 2695 to save out-of-state zipcodes
			generalInfoDto.setStateZipCodeList(generalInfoVO.getStateZipcodeVO());
			
			if(generalInfoVO.getBckStateId()==9 || generalInfoVO.getBckStateId()==10 || generalInfoVO.getBckStateId()==8)
			{
				generalInfoDto.setLockedSsn(true);
			}
			else if(generalInfoVO.getBckStateId()==7 || generalInfoVO.getBckStateId()==28)
			{
				generalInfoDto.setLockedSsn(false);
			}
			
			generalInfoVO.setGeographicalRange(generalInfoDto.getGeographicalRange());//MTedder@covansys.com
			generalInfoVO.setScheduleTimeList(generalInfoDto.getScheduleTimeList());//MTedder@covansys.com
			
			generalInfoDto.setStateType(generalInfoVO.getStateType());//MTedder@covansys.com
			generalInfoDto.setStateTypeList(generalInfoVO.getStateTypeList());//MTedder@covansys.com
			
			/**
			 * Added for Week Indicator - Covansys (Offshore)
			 */
			generalInfoDto.setMon24Ind(generalInfoVO.getMon24Ind());
			generalInfoDto.setMonNaInd(generalInfoVO.getMonNaInd());
			
			generalInfoDto.setTue24Ind(generalInfoVO.getTue24Ind());
			generalInfoDto.setTueNaInd(generalInfoVO.getTueNaInd());
			
			generalInfoDto.setWed24Ind(generalInfoVO.getWed24Ind());
			generalInfoDto.setWedNaInd(generalInfoVO.getWedNaInd());
			
			generalInfoDto.setThu24Ind(generalInfoVO.getThu24Ind());
			generalInfoDto.setThuNaInd(generalInfoVO.getThuNaInd());
			
			generalInfoDto.setFri24Ind(generalInfoVO.getFri24Ind());
			generalInfoDto.setFriNaInd(generalInfoVO.getFriNaInd());
			
			generalInfoDto.setSat24Ind(generalInfoVO.getSat24Ind());
			generalInfoDto.setSatNaInd(generalInfoVO.getSatNaInd());
			
			generalInfoDto.setSun24Ind(generalInfoVO.getSun24Ind());
			generalInfoDto.setSunNaInd(generalInfoVO.getSunNaInd());
			generalInfoDto.setUserNameAdmin(generalInfoVO.getUserNameAdmin());
			//SL-20225
			generalInfoDto.setBgComplete(generalInfoVO.isBgComplete());
		}
		return generalInfoDto;
	}
	
	
	private Date covertTimeStringToDate(String str){
		if (str!=null && str.trim().length()>0) {
			str="20070101:" + str;
			SimpleDateFormat timeStringformater = new SimpleDateFormat("yyyyMMdd:hh:mm a");
			try {
				return timeStringformater.parse(str);
			}catch (Exception e) {
				logger.info("covertTimeStringToDate failed to process for input");
			}
		}
		return null;
	}
	
	private String covertDateToTimeString(Date date){
		if (date!=null) {
			SimpleDateFormat timeStringformater = new SimpleDateFormat("hh:mm a");
			return timeStringformater.format(date);
		}
		return null;
	}
}
/*
 * Maintenance History
 * $Log: GeneralInfoMapper.java,v $
 * Revision 1.25  2008/04/26 01:13:46  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.21.10.1  2008/04/23 11:41:33  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.24  2008/04/23 05:19:54  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.22  2008/04/15 17:57:45  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.21.22.1  2008/04/11 03:47:42  araveen
 * Made changes for RemoveUserButton Sears00049387
 *
 * Revision 1.21  2008/02/21 04:38:35  glacy
 * Sears00048067 Defect Fix, was not persisting SSN when hashed, which is ok unless the user made an error on the page leading to the relaoad to keep the ssn in session but show it hashed. The fix checks to see if the hashed SSN also has a valid hidden SSN and if so persists that.
 *
 * Revision 1.20  2008/02/18 13:10:17  hrajago
 * Sears00047504 - General Info hourly billing rate
 *
 * Revision 1.19  2008/02/11 17:35:51  mhaye05
 * removed statesList attributes
 *
 */