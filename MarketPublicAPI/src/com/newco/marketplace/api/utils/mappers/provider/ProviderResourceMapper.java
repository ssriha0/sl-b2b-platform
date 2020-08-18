
package com.newco.marketplace.api.utils.mappers.provider;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.create.provider.Address;
import com.newco.marketplace.api.beans.hi.account.create.provider.BackgroundCheck;
import com.newco.marketplace.api.beans.hi.account.create.provider.Credential;
import com.newco.marketplace.api.beans.hi.account.create.provider.LicenseAndCertifications;
import com.newco.marketplace.api.beans.hi.account.create.provider.NameDetails;
import com.newco.marketplace.api.beans.hi.account.create.provider.Provider;
import com.newco.marketplace.api.beans.hi.account.create.provider.Week;
import com.newco.marketplace.api.beans.hi.account.firm.addproviderskill.AddProviderSkillRequest;
import com.newco.marketplace.api.beans.hi.account.firm.removeproviderskill.RemoveProviderSkillRequest;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.SkillDetail;
import com.newco.marketplace.api.beans.hi.account.provider.create.ProviderRegistrationRequest;
import com.newco.marketplace.api.beans.hi.account.provider.create.ProviderRegistrationResponse;
import com.newco.marketplace.api.beans.hi.account.provider.update.UpdateProviderRegistrationResponse;
import com.newco.marketplace.api.beans.hi.account.update.provider.ProviderRegistration;
import com.newco.marketplace.api.beans.hi.account.update.provider.ResourceCredential;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;
import com.newco.marketplace.vo.hi.provider.ProviderSkillVO;
import com.newco.marketplace.vo.hi.provider.SkillDetailVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;

/**
 * This class is a mapper class for mapping  provider information.
 * 
 * 
 * @version 1.0
 */
public class ProviderResourceMapper {
	 
	
	private Logger LOGGER = Logger.getLogger(ProviderResourceMapper.class);
	public static Integer ONE=1;
	public static Integer ZERO = 0;
	public static String ONE_S="1";
	public static String ZERO_S="0";
	public static String TWO_S="2";
	public static String format="yyyy-MM-dd";
	ArrayList<String> list= null; 
	
    /**
     * @param providerAccountVO
     * @param week
     * @throws Exception
     */
    private void mapScheduleTimes(ProviderAccountVO providerAccountVO, Week week)throws Exception {
		if(week.getWeekDayName().equals(ProviderConstants.SUNDAY)){
			if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setSun24Ind(ONE_S);
				providerAccountVO.setSunNaInd(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setSunNaInd(ONE_S);
				providerAccountVO.setSun24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setSun24Ind(ZERO_S);
				providerAccountVO.setSunNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime &&  null!=endTime){
					providerAccountVO.setSunStart(startTime);
					providerAccountVO.setSunEnd(endTime);
					
				}
			}
			
		}
		if(week.getWeekDayName().equals(ProviderConstants.MONDAY)){
			if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setMon24Ind(ONE_S);
				providerAccountVO.setMonNaInd(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setMonNaInd(ONE_S);
				providerAccountVO.setMon24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setMon24Ind(ZERO_S);
				providerAccountVO.setMonNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime && null!=endTime){
					providerAccountVO.setMonStart(startTime);
					providerAccountVO.setMonEnd(endTime);
				}
			}
			
		}
		if(week.getWeekDayName().equals(ProviderConstants.TUESDAY)){
			if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setTue24Ind(ONE_S);
				providerAccountVO.setTueNaInd(ZERO_S);
			}
			else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setTueNaInd(ONE_S);
				providerAccountVO.setTue24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setTue24Ind(ZERO_S);
				providerAccountVO.setTueNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime && null!=endTime){
					providerAccountVO.setTueStart(startTime);
					providerAccountVO.setTueEnd(endTime);
				}
			}
			
		}
		if(week.getWeekDayName().equals(ProviderConstants.WEDNESDAY)){
			if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setWed24Ind(ONE_S);
				providerAccountVO.setWedNaInd(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setWedNaInd(ONE_S);
				providerAccountVO.setWed24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setWed24Ind(ZERO_S);
				providerAccountVO.setWedNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime && null!=endTime){
					providerAccountVO.setWedStart(startTime);
					providerAccountVO.setWedEnd(endTime);
				}
			}
			
		}
		if(week.getWeekDayName().equals(ProviderConstants.THURSDAY)){
			if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setThu24Ind(ONE_S);
				providerAccountVO.setThuNaInd(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setThuNaInd(ONE_S);
				providerAccountVO.setThu24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setThu24Ind(ZERO_S);
				providerAccountVO.setThuNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime && null!=endTime){
					providerAccountVO.setThuStart(startTime);
					providerAccountVO.setThuEnd(endTime);
				}
			}
					
		}
		
        if(week.getWeekDayName().equals(ProviderConstants.FRIDAY)){
        	if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setFri24Ind(ONE_S);
				providerAccountVO.setFriNaInd(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setFriNaInd(ONE_S);
				providerAccountVO.setFri24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setFri24Ind(ZERO_S);
				providerAccountVO.setFriNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime && null!=endTime){
					providerAccountVO.setFriStart(startTime);
					providerAccountVO.setFriEnd(endTime);
				}
			}
		}
		
	   if(week.getWeekDayName().equals(ProviderConstants.SATURDAY)){
		   if(week.getWholeDayAvailableInd().equals(ONE_S)){
				providerAccountVO.setSat24Ind(ONE_S);
				providerAccountVO.setSatNaInd(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(ZERO_S)){
				providerAccountVO.setSatNaInd(ONE_S);
				providerAccountVO.setSat24Ind(ZERO_S);
			}else if(week.getWholeDayAvailableInd().equals(TWO_S)){
				providerAccountVO.setSat24Ind(ZERO_S);
				providerAccountVO.setSatNaInd(ZERO_S);
				Date startTime=covertTimeStringToDate(week.getStartTime());
				Date endTime=covertTimeStringToDate(week.getEndTime());
				if(null!=startTime && null!=endTime){
					providerAccountVO.setSatStart(startTime);
				    providerAccountVO.setSatEnd(endTime);
				}
			}
	   }
	}
	
	
	/**
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private Date covertTimeStringToDate(String str) throws Exception{
		if (str!=null && str.trim().length()>0) {
			str=ProviderConstants.DEFAULT_DATE_RESOURCE_SCHEDULE + str;
			SimpleDateFormat timeStringformater = new SimpleDateFormat(ProviderConstants.TIME_STRING_FORMAT);
			try {
				return timeStringformater.parse(str);
			}catch (Exception e) {
				LOGGER.error("Exception in covertTimeStringToDate failed to process for input :"+e.getMessage());
				throw e ;
			}
		}
		return null;
	}
	
	/**
	 * @param date1
	 * @param format
	 * @return
	 * @throws Exception
	 */
	private final Date convertDate(final String date1,final String format) throws Exception{
	       Date dateObj1=null;
	      try {
	      if(date1!=null){
	        DateFormat dateFormat = new SimpleDateFormat(format);
	        dateObj1 = dateFormat.parse(date1);
	      
	        }
	      }//end of try
	      catch (Exception errorexcep) {
	        //LOGGER.log(ExceptionConstants.ERROR,"The Parse Error in compare date is:" + errorexcep);
	    	  System.out.println("-------------------Exception ex in parsing the date in mapper-----");
	    	  throw errorexcep;
	      }//end of catch
	      return dateObj1;
	  }
	
	
	/**
	 * @param number
	 * @return
	 */
	public  String removeHyphenFromNumber(String number){
		String formattedNumber = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(number)){
			formattedNumber=StringUtils.remove(number, SOPDFConstants.HYPHEN);
		}
		return formattedNumber;
	}


	/**@Description Mapping request of create provider API
	 * @param request
	 * @return
	 */
	public ProviderAccountVO mapCreateProviderRequest(ProviderRegistrationRequest request) {
		ProviderAccountVO providerVO = new ProviderAccountVO(ProviderConstants.CREATE);
		try{
			if(null!= request.getFirmId()){
			  providerVO.setVendorId(request.getFirmId());
			}
			if(null!= request.getProvider()){
				providerVO = mapResourceNameDetails(providerVO,request.getProvider());
				providerVO = mapRoleLevelIndicators(providerVO,request.getProvider());
				providerVO = mapResourceAddress(providerVO,request.getProvider());
				providerVO = mapUserNameAndRangeRate(providerVO,request.getProvider());
				providerVO = mapBackGroundDetails(providerVO,request.getProvider());
				providerVO = mapContactDetails(providerVO,request.getProvider());
				providerVO = mapAvailability(providerVO,request.getProvider());
				providerVO = mapCredentils(providerVO,request.getProvider());
				providerVO = mapLanguagesAndProviderCrewId(providerVO,request.getProvider());
			}
		}catch (Exception e) {
			LOGGER.error("Exception in mapping request,due to error in conversion of date"+ e.getMessage());
			 e.printStackTrace();
			}
		return providerVO;
	}
    /**@Description Mapping resource name details of create provider API
	 * @param providerVO
	 * @param provider
	 * @return
	 */
	private ProviderAccountVO mapResourceNameDetails(ProviderAccountVO providerVO, Provider provider) {
		NameDetails name = null;
		if(null!= provider && null!= provider.getNameDetails()){
			name = provider.getNameDetails();
			providerVO.setFirstName(name.getFirstName());
			providerVO.setLastName(name.getLastName());
			providerVO.setMiddleName(name.getMiddleName());
			providerVO.setSuffix(name.getSuffix());
		}
		if(null!= provider && StringUtils.isNotBlank(provider.getJobTitle())){
			providerVO.setOtherJobTitle(provider.getJobTitle());
		}
		return providerVO;
	   }
    /**@Description Mapping role level indicators of create provider API
     * @param providerVO
     * @param provider
     * @return
     */
    private ProviderAccountVO mapRoleLevelIndicators(ProviderAccountVO providerVO, Provider provider) {
    	if(null!=provider && null!= provider.getMarketPlaceInd()){
    		if( provider.getMarketPlaceInd().equals(PublicAPIConstant.TRUE)){
    			providerVO.setMarketPlaceInd(ONE_S);
    			providerVO.setResourceInd(ONE);
    		}else{
    			providerVO.setMarketPlaceInd(ZERO_S);
    			providerVO.setResourceInd(ZERO);
    		}
    	}
    	List<String> role= null;
    	if(null!= provider.getRoles() && null!= provider.getRoles().getRole()&& !provider.getRoles().getRole().isEmpty()){
    		role=provider.getRoles().getRole();
			if(role.contains(ProviderConstants.OWNER_ROLE)){
				providerVO.setOwnerInd(ONE);
			}
			if(role.contains(ProviderConstants.DISPATCHER_ROLE)){
				providerVO.setDispatchInd(ONE);
			}
			if(role.contains(ProviderConstants.MANAGER)){
				providerVO.setManagerInd(ONE);
			}
			if(role.contains(ProviderConstants.ADMIN_ROLE)){
				providerVO.setAdminInd(ONE);
			}
		    if(role.contains(ProviderConstants.SP_ROLE)){
		    	providerVO.setSproInd(ONE);
			}
			if(role.contains(ProviderConstants.OTHER_ROLE)){
				providerVO.setOtherInd(ONE);
			}
    	}
	    return providerVO;
	}
    /**@Description Mapping dispatch address details of create provider API
     * @param providerVO
     * @param provider
     * @return
     */
    private ProviderAccountVO mapResourceAddress(ProviderAccountVO providerVO,Provider provider) {
    	Address address = null;
		if(null!= provider && null!= provider.getAddress()){
			address = provider.getAddress();
			providerVO.setDispAddStreet1(address.getStreetName1());
			providerVO.setDispAddStreet2(address.getStreetName2());
			providerVO.setDispAddState(address.getState());
			providerVO.setDispAddCity(address.getCity());
			providerVO.setDispAddZip(address.getZip());
			providerVO.setDispAddApt(address.getAptNo());
		}
		return providerVO;
	}
    /**@Description Mapping username ,geographic range & SSN of create provider API
     * @param providerVO
     * @param provider
     * @return
     */
    private ProviderAccountVO mapUserNameAndRangeRate(ProviderAccountVO providerVO, Provider provider) {
    	providerVO.setUserName(provider.getUserName());
    	providerVO.setDispAddGeographicRange(provider.getGeographicalRange());
    	if(StringUtils.isNotBlank(provider.getPreferredBillingRate())){
    	  providerVO.setHourlyRate(Double.parseDouble(provider.getPreferredBillingRate()));
    	}
    	providerVO.setSsn(provider.getSsn());
    	providerVO.setSsnUnEncrypted(provider.getSsn());
		return providerVO;
	}
    
    /**@Description Mapping background details of create provider API
     * @param providerVO
     * @param provider
     * @return
     */
    private ProviderAccountVO mapBackGroundDetails(ProviderAccountVO providerVO, Provider provider) {
    	BackgroundCheck backgroundCheck =null;
    	if(null!= provider.getBackgroundCheck()){    
    		backgroundCheck = provider.getBackgroundCheck();
			if(StringUtils.isNotBlank(backgroundCheck.getBackgroundCheckStatus())){
				providerVO.setBackgroundCheckStatus(backgroundCheck.getBackgroundCheckStatus());
			}
			if(StringUtils.isNotBlank(backgroundCheck.getVerificationDate())){
				providerVO.setBackgroundVerificationDate(backgroundCheck.getVerificationDate());
				providerVO.setBackGroundRequestedDate(backgroundCheck.getVerificationDate());
			}
			if(StringUtils.isNotBlank(backgroundCheck.getReverificationDate())){
				providerVO.setBackgroundRecertificationDate(backgroundCheck.getReverificationDate());
			}
			if(StringUtils.isNotBlank(backgroundCheck.getRequestDate())){
				providerVO.setBackGroundRequestedDate(backgroundCheck.getRequestDate());
			}else{
				providerVO.setBackGroundRequestedDate(backgroundCheck.getVerificationDate());
			}
			
		}
    	
    	return providerVO;
	}
    
    /**@Description Mapping contact details of create provider API
     * @param providerVO
     * @param provider
     * @return
     */
    private ProviderAccountVO mapContactDetails(ProviderAccountVO providerVO,Provider provider) {
    	if(StringUtils.isNotBlank(provider.getBusinessPhone())){
    		providerVO.setBusinessPhone(removeHyphenFromNumber(provider.getBusinessPhone()));
    	}
    	if(StringUtils.isNotBlank(provider.getMobileNo())){
    		providerVO.setMobilePhone(removeHyphenFromNumber(provider.getMobileNo()));
    	}
    	if(StringUtils.isNotBlank(provider.getSmsAddress())){
    		providerVO.setSmsAddress(removeHyphenFromNumber(provider.getSmsAddress()));
    	}
    	providerVO.setBusinessExtn(provider.getBusinessExtn());
    	providerVO.setEmail(provider.getPrimaryEmail());
    	providerVO.setAltEmail(provider.getAltEmail());
    	if(StringUtils.isNotBlank(provider.getSecondaryContact())){
    	  providerVO.setSecondaryContact(provider.getSecondaryContact());
    	}
		return providerVO;
	}
    /**@Description Mapping availability details of create provider API
     * @param providerVO
     * @param provider
     * @return
     * @throws Exception
     */
    private ProviderAccountVO mapAvailability(ProviderAccountVO providerVO,Provider provider) throws Exception {
    	List<Week> weekList=null;
    	if(null!=provider.getAvailability() && null!=provider.getAvailability().getWeek() 
    			 && !provider.getAvailability().getWeek().isEmpty()){
			weekList=provider.getAvailability().getWeek();
			list= new ArrayList<String>();
			for(Week week :weekList){
				String weekName =week.getWeekDayName();
				if(StringUtils.isNotBlank(weekName)){
					list.add(weekName);	
					mapScheduleTimes(providerVO, week);
				}
			}
			
			HashSet hs = new HashSet();
			hs.addAll(list);
			list.clear();
			list.addAll(hs);
			
			if(null!=list && list.size() < 7)
			{
				providerVO.setDuplicateWeekPresent(true);
			}
    	}
		return providerVO;
	}
	/**@Description Mapping credential details of create provider API
	 * @param providerVO
	 * @param provider
	 * @return
	 * @throws Exception 
	 */
	private ProviderAccountVO mapCredentils(ProviderAccountVO providerVO,Provider provider) throws Exception {
	   List<TeamCredentialsVO> licensesList=null;
	   
	   if(null!=provider.getLicenseAndCertifications())
	   {
		   LicenseAndCertifications certifications = provider.getLicenseAndCertifications();
		   if(null!=certifications)
		   {	
			   if(null!=certifications.getLicenseCertificationInd() && certifications.getLicenseCertificationInd().equals(PublicAPIConstant.TRUE))
			   {
				 providerVO.setLicensecredentialInd(true);
			   }
		   }
	   }
	   
	   
       if(null!=provider.getLicenseAndCertifications() && null!= provider.getLicenseAndCertifications().getCredentials() 
				&& null!=provider.getLicenseAndCertifications().getCredentials().getCredential()){
    	        LicenseAndCertifications certifications = provider.getLicenseAndCertifications();
    	        if(StringUtils.isNotBlank(certifications.getLicenseCertificationInd())
    	        		&& certifications.getLicenseCertificationInd().equals(PublicAPIConstant.TRUE)){
    	        	
		            List<Credential> credentials=(List<Credential>) provider.getLicenseAndCertifications().getCredentials().getCredential();
					if(null!=credentials){
						licensesList=new ArrayList<TeamCredentialsVO>();
					    for(Credential credential:credentials){
						TeamCredentialsVO teamCredentialsVO=new TeamCredentialsVO();
						if(StringUtils.isNotBlank(credential.getCredentialType())){
							teamCredentialsVO.setCredType(credential.getCredentialType());
						}if(StringUtils.isNotBlank(credential.getCredentialCategory())){
							teamCredentialsVO.setCredCategory(credential.getCredentialCategory());
						}if(StringUtils.isNotBlank(credential.getLicenseCertName())){
							teamCredentialsVO.setLicenseName(credential.getLicenseCertName());
						}if(StringUtils.isNotBlank(credential.getCredentialIssuer())){
							teamCredentialsVO.setIssuerName(credential.getCredentialIssuer());
						}if(StringUtils.isNotBlank(credential.getCredentialNumber())){
							teamCredentialsVO.setCredentialNumber(credential.getCredentialNumber());
						}if(StringUtils.isNotBlank(credential.getCredentialCity())){
							teamCredentialsVO.setCity(credential.getCredentialCity());
						}if(StringUtils.isNotBlank(credential.getCredentialState())){
							teamCredentialsVO.setState(credential.getCredentialState());
						}if(StringUtils.isNotBlank(credential.getCredentialCounty())){
							teamCredentialsVO.setCounty(credential.getCredentialCounty());
						}if(null!=credential.getCredentialIssueDate()){
							teamCredentialsVO.setIssueDate(convertDate(credential.getCredentialIssueDate(),format));
						}
						if(null!=credential.getCredentialExpirationDate()){
							teamCredentialsVO.setExpirationDate(convertDate(credential.getCredentialExpirationDate(),format));
						}
						licensesList.add(teamCredentialsVO);
					}
				}
    	     }
    	    providerVO.setLicensesList(licensesList);
		  }	
		return providerVO;
	}
		

	/**@Description Mapping language details & SubContractorCrewId of create provider API
	 * @param providerVO
	 * @param provider
	 * @return
	 */
	private ProviderAccountVO mapLanguagesAndProviderCrewId(ProviderAccountVO providerVO, Provider provider) {
		if(null!=provider.getLanguages() && null!=provider.getLanguages().getLanguage()
				&& !provider.getLanguages().getLanguage().isEmpty()){
			List<String> languages= new ArrayList<String>();
			languages =provider.getLanguages().getLanguage();
			
			HashSet hs = new HashSet();
			hs.addAll(languages);
			languages.clear();
			languages.addAll(hs);
			
			providerVO.setLanguageList(languages);
		}
		if(StringUtils.isNotBlank(provider.getSubContractorCrewId())){
			providerVO.setSubContractorCrewId(provider.getSubContractorCrewId());
		}
		return providerVO;
	}


	/**
	 * @param result
	 * @return
	 */
	public ProviderRegistrationResponse createErrorResponse(Results result) {
		Results resultLatest =null;
		ProviderRegistrationResponse response = new ProviderRegistrationResponse();
		if(null!=result ){
			resultLatest = result;
		}else{
			resultLatest = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		response.setResults(resultLatest);
		return response;
	}


	/**
	 * @param providerVO
	 * @return
	 */
	public ProviderRegistrationResponse createSuccessResponse(ProviderAccountVO providerVO) {
		ProviderRegistrationResponse response = new ProviderRegistrationResponse();
		if(StringUtils.isNotBlank(providerVO.getVendorId())){
			response.setFirmId(Integer.parseInt(providerVO.getVendorId()));
		}
        if(StringUtils.isNotBlank(providerVO.getResourceId())){
    	   response.setProviderId(Integer.parseInt(providerVO.getResourceId()));
		}
		response.setResults(Results.getSuccess(ResultsCode.PROVIDER_SUCCESS.getCode(), ResultsCode.PROVIDER_SUCCESS.getMessage()));
		return response;
	}
	
	/**@Description Mapping request of update provider API
	 * @param providerRegistration
	 * @return
	 * @throws Exception
	 */
	public ProviderAccountVO mapUpdateProviderRequest(ProviderRegistration providerRegistration) throws Exception {
		
		ProviderAccountVO providerAccountVO =new ProviderAccountVO();
		
		//Mapping resource ind
		if(StringUtils.isNotBlank(providerRegistration.getMarketPlaceInd()) && providerRegistration.getMarketPlaceInd().equals(PublicAPIConstant.TRUE))
		{
			providerAccountVO.setResourceInd(ONE);
		}else if(StringUtils.isNotBlank(providerRegistration.getMarketPlaceInd()) && providerRegistration.getMarketPlaceInd().equals(PublicAPIConstant.FALSE))
		{
			providerAccountVO.setResourceInd(ZERO);
		}
		
		//Mapping market place ind
		if(StringUtils.isNotBlank(providerRegistration.getMarketPlaceStatus()) && providerRegistration.getMarketPlaceStatus().equals(PublicAPIConstant.TRUE))
		{
			providerAccountVO.setMarketPlaceInd(ONE_S);
		}else if(StringUtils.isNotBlank(providerRegistration.getMarketPlaceStatus()) && providerRegistration.getMarketPlaceStatus().equals(PublicAPIConstant.FALSE))
		{
			providerAccountVO.setMarketPlaceInd(ZERO_S);
		}
		
		//Setting roles for resource
		mapRolesUpdateProvider(providerRegistration, providerAccountVO);		
		
		//Mapping job title
		if(StringUtils.isNotBlank(providerRegistration.getJobTitle()))
		{
			providerAccountVO.setOtherJobTitle(providerRegistration.getJobTitle());
		}
		
		//Mapping dispatch address
		mapDispatchAddressUpdate(providerRegistration, providerAccountVO);
		
		//Mapping geographical range
		if(StringUtils.isNotBlank(providerRegistration.getGeographicalRange()))
		{
			providerAccountVO.setDispAddGeographicRange(providerRegistration.getGeographicalRange());
		}		
		
		//Mapping hourly rate
		if(null!=providerRegistration.getPreferredBillingRate())
		{
			providerAccountVO.setHourlyRateString(providerRegistration.getPreferredBillingRate());
		}
		
		//Mapping availability
		mapAvailabilityUpdate(providerRegistration, providerAccountVO);
		
		//Mapping background details
		mapBackgroundDetailsUpdate(providerRegistration, providerAccountVO);
		
		//Mapping business phone number
		if(StringUtils.isNotBlank(providerRegistration.getBusinessPhone()))
		{
			providerAccountVO.setBusinessPhone(removeHyphenFromNumber(providerRegistration.getBusinessPhone()));
		}
		
		//Mapping mobile number
		if(StringUtils.isNotBlank(providerRegistration.getMobileNo()))
		{
			providerAccountVO.setMobilePhone(removeHyphenFromNumber(providerRegistration.getMobileNo()));
		}
		
		//Mapping business extension
		if(StringUtils.isNotBlank(providerRegistration.getBusinessExtn()))
		{
			providerAccountVO.setBusinessExtn(providerRegistration.getBusinessExtn());
		}
		
		//Mapping primary email and alternate email
		if(StringUtils.isNotBlank(providerRegistration.getPrimaryEmail()))
		{
			providerAccountVO.setEmail(providerRegistration.getPrimaryEmail());
		}
		
		if(StringUtils.isNotBlank(providerRegistration.getAltEmail()))
		{
			providerAccountVO.setAltEmail(providerRegistration.getAltEmail());
		}
		
		//Mapping sms address and secondary contact
		
		if(StringUtils.isNotBlank(providerRegistration.getSmsAddress()))
		{
			providerAccountVO.setSmsAddress(removeHyphenFromNumber(providerRegistration.getSmsAddress()));
		}
		
		
		if(StringUtils.isNotBlank(providerRegistration.getSecondaryContact()))
		{
			providerAccountVO.setSecondaryContact(providerRegistration.getSecondaryContact());
		}
		
		
		//Mapping languages
		mapLanguagesUpdateProvider(providerRegistration, providerAccountVO);
			
		
		//Mapping License
		mapLicenseUpdateProvider(providerRegistration, providerAccountVO);			
		
		
		return providerAccountVO;
	}

	
	/**@Description Mapping role level indicators of update provider API
	 * @param providerRegistration
	 * @param providerAccountVO
	 */
	private void mapRolesUpdateProvider(
			ProviderRegistration providerRegistration,
			ProviderAccountVO providerAccountVO) {
		if(null!=providerRegistration.getRoles() && null!=providerRegistration.getRoles().getRole() && !providerRegistration.getRoles().getRole().isEmpty())
		{
			List<String> role= new ArrayList<String>();
			role=providerRegistration.getRoles().getRole();
					
			if(role.contains(ProviderConstants.OWNER_ROLE))
			{
				providerAccountVO.setOwnerInd(ONE);
			}
				
					
			if(role.contains(ProviderConstants.DISPATCHER_ROLE))
			{
				providerAccountVO.setDispatchInd(ONE);
			}
					
			if(role.contains(ProviderConstants.MANAGER))
			{
				providerAccountVO.setManagerInd(ONE);
			}
			
			if(role.contains(ProviderConstants.ADMIN_ROLE))
			{
				providerAccountVO.setAdminInd(ONE);
			}
					
			if(role.contains(ProviderConstants.SP_ROLE))
			{
				providerAccountVO.setSproInd(ONE);
			}
					
					
			if(role.contains(ProviderConstants.OTHER_ROLE))
			{
				providerAccountVO.setOtherInd(ONE);
			}
					
		}
	}
	
	/**@Description Mapping dispatcher address details of update provider API
	 * @param providerRegistration
	 * @param providerAccountVO
	 */
	private void mapDispatchAddressUpdate(
			ProviderRegistration providerRegistration,
			ProviderAccountVO providerAccountVO) {
		if(null!=providerRegistration.getAddress())
		{
			if(StringUtils.isNotBlank(providerRegistration.getAddress().getStreetName1())){
				providerAccountVO.setDispAddStreet1(providerRegistration.getAddress().getStreetName1());
			}	
			
			if(StringUtils.isNotBlank(providerRegistration.getAddress().getStreetName2())){
				providerAccountVO.setDispAddStreet2(providerRegistration.getAddress().getStreetName2());
			}	
		
			if(StringUtils.isNotBlank(providerRegistration.getAddress().getCity())){
				providerAccountVO.setDispAddCity(providerRegistration.getAddress().getCity());
			}
			
			if(StringUtils.isNotBlank(providerRegistration.getAddress().getState())){
				providerAccountVO.setDispAddState(providerRegistration.getAddress().getState());
			}
			
			if(StringUtils.isNotBlank(providerRegistration.getAddress().getZip())){
				providerAccountVO.setDispAddZip(providerRegistration.getAddress().getZip());
			}

			if(StringUtils.isNotBlank(providerRegistration.getAddress().getAptNo())){
				providerAccountVO.setDispAddApt(providerRegistration.getAddress().getAptNo());
			}
			
		}
	}
	
	/**@Description Mapping availability details of update provider API
	 * @param providerRegistration
	 * @param providerAccountVO
	 * @throws Exception
	 */
	private void mapAvailabilityUpdate(
			ProviderRegistration providerRegistration,
			ProviderAccountVO providerAccountVO) throws Exception {
		if(null!=providerRegistration.getAvailability() && null!=providerRegistration.getAvailability().getWeek() && !providerRegistration.getAvailability().getWeek().isEmpty())
		{
			List<Week> weekList= new ArrayList<Week>();
			weekList=providerRegistration.getAvailability().getWeek();
			list= new ArrayList<String>();
			for(Week week :weekList){
				
				String weekName =week.getWeekDayName();
				if(StringUtils.isNotBlank(weekName))
				{
				
					list.add(weekName);	
					mapScheduleTimes(providerAccountVO, week);
				}
			}
			
			HashSet hs = new HashSet();
			hs.addAll(list);
			list.clear();
			list.addAll(hs);
			
			if(null!=list && list.size() < 7)
			{
				providerAccountVO.setDuplicateWeekPresent(true);
			}
			
		}
	}
	
	/**@Description Mapping background info of update provider API
	 * @param providerRegistration
	 * @param providerAccountVO
	 * @throws Exception
	 */
	private void mapBackgroundDetailsUpdate(
			ProviderRegistration providerRegistration,
			ProviderAccountVO providerAccountVO) throws Exception {
		if(null!=providerRegistration.getBackgroundCheck())
		{
			if(StringUtils.isNotBlank(providerRegistration.getBackgroundCheck().getBackgroundCheckStatus()))
			{
				providerAccountVO.setBackgroundCheckStatus(providerRegistration.getBackgroundCheck().getBackgroundCheckStatus());
			}
			
			if(StringUtils.isNotBlank(providerRegistration.getBackgroundCheck().getVerificationDate()))
			{
				providerAccountVO.setBackgroundVerificationDate(providerRegistration.getBackgroundCheck().getVerificationDate());
			}
			
			if(StringUtils.isNotBlank(providerRegistration.getBackgroundCheck().getReverificationDate()))
			{
				providerAccountVO.setBackgroundRecertificationDate(providerRegistration.getBackgroundCheck().getReverificationDate());
			}
			
			if(StringUtils.isNotBlank(providerRegistration.getBackgroundCheck().getRequestDate()))
			{
				providerAccountVO.setRequestDate(providerRegistration.getBackgroundCheck().getRequestDate());
			}
			
		}
	}


	/**@Description Mapping language details of update provider API
	 * @param providerRegistration
	 * @param providerAccountVO
	 */
	private void mapLanguagesUpdateProvider(
			ProviderRegistration providerRegistration,
			ProviderAccountVO providerAccountVO) {
		if(null!=providerRegistration.getLanguages() && null!=providerRegistration.getLanguages().getLanguage() && !providerRegistration.getLanguages().getLanguage().isEmpty()) 
		{
			List<String> languages= new ArrayList<String>();
			languages =providerRegistration.getLanguages().getLanguage();
			
			HashSet hs = new HashSet();
			hs.addAll(languages);
			languages.clear();
			languages.addAll(hs);
			
			providerAccountVO.setLanguageList(languages);
		}
	}

	/**@Description Mapping credential details of update provider API
	 * @param providerRegistration
	 * @param providerAccountVO
	 * @throws Exception
	 */
	private void mapLicenseUpdateProvider(
			ProviderRegistration providerRegistration,
			ProviderAccountVO providerAccountVO) throws Exception {
		List<TeamCredentialsVO> licensesList=null;
		List<TeamCredentialsVO> editCredential = null;

		if(null!=providerRegistration.getLicenseAndCertifications() && null!= providerRegistration.getLicenseAndCertifications().getCredentials() 
				&& null!=providerRegistration.getLicenseAndCertifications().getCredentials().getCredential())
		{

		List<ResourceCredential> credentials=(List<ResourceCredential>) providerRegistration.getLicenseAndCertifications().getCredentials().getCredential();
		if(null!=credentials)
		{
		
			providerAccountVO.setLicensePresent(true);
			
			licensesList=new ArrayList<TeamCredentialsVO>();
		    editCredential = new ArrayList<TeamCredentialsVO>();
	
		    
		    for(ResourceCredential credential:credentials){
			TeamCredentialsVO teamCredentialsVO=new TeamCredentialsVO();
			
			if(StringUtils.isNotBlank(credential.getCredentialType()))
			{
				teamCredentialsVO.setCredType(credential.getCredentialType());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialCategory()))
			{
				teamCredentialsVO.setCredCategory(credential.getCredentialCategory());
			}
		
			if(StringUtils.isNotBlank(credential.getLicenseCertName()))
			{
				teamCredentialsVO.setLicenseName(credential.getLicenseCertName());
			}
		
			if(StringUtils.isNotBlank(credential.getCredentialIssuer()))
			{
				teamCredentialsVO.setIssuerName(credential.getCredentialIssuer());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialNumber()))
			{
				teamCredentialsVO.setCredentialNumber(credential.getCredentialNumber());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialCity()))
			{
				teamCredentialsVO.setCity(credential.getCredentialCity());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialState()))
			{
				teamCredentialsVO.setState(credential.getCredentialState());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialCounty()))
			{
				teamCredentialsVO.setCounty(credential.getCredentialCounty());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialIssueDate()))
			{
				teamCredentialsVO.setIssueDate(convertDate(credential.getCredentialIssueDate(),format));
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialExpirationDate()))
			{
				teamCredentialsVO.setExpirationDate(convertDate(credential.getCredentialExpirationDate(),format));
			}
			
			if(StringUtils.isNotBlank(credential.getResourceCredentialId()))
			{
				teamCredentialsVO.setResourceCredId(Integer.parseInt(credential.getResourceCredentialId()));
				editCredential.add(teamCredentialsVO);	
			}
			else
			{
				licensesList.add(teamCredentialsVO);
			}
			
		}
		providerAccountVO.setEditLicensesList(editCredential);
		providerAccountVO.setLicensesList(licensesList);
	  }	
	}
	}
	
	 /**
     * @Description Method to map the addProviderSkillRequest to the VO
     * @param addProviderSkillRequest
     * @return ProviderSkillVO
     * @throws Exception
     */
	public ProviderSkillVO mapAddProviderSkillRequest(AddProviderSkillRequest addProviderSkillRequest) throws Exception {
		
		ProviderSkillVO addProviderSkillVO=null;
		SkillDetailVO skillDetailVO=null;
		List<SkillDetailVO> skillDetailVOList=null;
		//setting the values in the VO class
		if(null !=addProviderSkillRequest && null !=addProviderSkillRequest.getProvider().getProviderId()
				&& null !=addProviderSkillRequest.getProvider().getSkills() ){
			skillDetailVOList=new ArrayList<SkillDetailVO>();
			addProviderSkillVO=new ProviderSkillVO();
			//Setting providerId
			addProviderSkillVO.setProviderId(addProviderSkillRequest.getProvider().getProviderId());
			//Setting skill details
			List<SkillDetail> skillDetailList=addProviderSkillRequest.getProvider().getSkills().getSkill();
			if(null !=skillDetailList && ! skillDetailList.isEmpty()){
				for(SkillDetail skillDetail:skillDetailList){
					skillDetailVO=new SkillDetailVO();
					skillDetailVO.setRootSkillName(skillDetail.getRootSkillName());
					skillDetailVO.setSkillName(skillDetail.getSkillName());
					skillDetailVO.setSkillCategoryName(skillDetail.getSkillCategoryName());
					if(null !=skillDetail.getSkillServiceTypes()){
						skillDetailVO.setServiceType(skillDetail.getSkillServiceTypes().getServiceType());
					}
					skillDetailVOList.add(skillDetailVO);
				}
			}
			if(null !=skillDetailVOList && !skillDetailVOList.isEmpty()){
				addProviderSkillVO.setSkill(skillDetailVOList);	
			}
		}
		return addProviderSkillVO;
	}
    
	/**
	 * @Description Method to map the removeProviderSkillRequest to the VO
	 * @param removeProviderSkillRequest
	 * @return ProviderSkillVO
	 * @throws Exception
	 */
	public ProviderSkillVO mapRemoveProviderSkillRequest(RemoveProviderSkillRequest removeProviderSkillRequest) throws Exception{
		ProviderSkillVO removeProviderSkillVO=null;
		SkillDetailVO skillDetailVO=null;
		List<SkillDetailVO> skillDetailVOList=null;
		//setting the values in the VO class
		if(null !=removeProviderSkillRequest && null !=removeProviderSkillRequest.getProvider().getProviderId() 
				&& null !=removeProviderSkillRequest.getProvider().getSkills() ){
			skillDetailVOList=new ArrayList<SkillDetailVO>();
			removeProviderSkillVO=new ProviderSkillVO();
			//Setting providerId
			removeProviderSkillVO.setProviderId(removeProviderSkillRequest.getProvider().getProviderId());
			//Setting skill details
			List<SkillDetail> skillDetailList=removeProviderSkillRequest.getProvider().getSkills().getSkill();
			if(null !=skillDetailList && ! skillDetailList.isEmpty()){
				for(SkillDetail skillDetail:skillDetailList){
					skillDetailVO=new SkillDetailVO();
					skillDetailVO.setRootSkillName(skillDetail.getRootSkillName());
					skillDetailVO.setSkillName(skillDetail.getSkillName());
					skillDetailVO.setSkillCategoryName(skillDetail.getSkillCategoryName());
					if(null !=skillDetail.getSkillServiceTypes()){
						skillDetailVO.setServiceType(skillDetail.getSkillServiceTypes().getServiceType());
					}
					skillDetailVOList.add(skillDetailVO);
				}
			}
			if(null !=skillDetailVOList && !skillDetailVOList.isEmpty()){
				removeProviderSkillVO.setSkill(skillDetailVOList);	
			}
		}
		return removeProviderSkillVO;
	}
	
	
	/**
	 * @param providerVO
	 * @return
	 */
	public UpdateProviderRegistrationResponse createSuccessResponseUpdate(ProviderAccountVO providerVO) {
		UpdateProviderRegistrationResponse response = new UpdateProviderRegistrationResponse();
		if(StringUtils.isNotBlank(providerVO.getVendorId())){
			response.setFirmId(providerVO.getVendorId());
		}
        if(StringUtils.isNotBlank(providerVO.getResourceId())){
    	   response.setProviderId(providerVO.getResourceId());
		}
		response.setResults(Results.getSuccess(ResultsCode.SUCCESS_UPDATE_PROVIDER.getCode(), ResultsCode.SUCCESS_UPDATE_PROVIDER.getMessage()));
		return response;
	}
}
	
	
