package com.newco.marketplace.web.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.vo.provider.BusinessinfoVO;
import com.newco.marketplace.web.dto.provider.BusinessinfoDto;
import com.newco.marketplace.dto.vo.provider.TeamMemberVO;


public class BusinessinfoMapper extends ObjectMapper{

	public BusinessinfoVO convertDTOtoVO(Object objectDto, Object objectVO) {
		BusinessinfoDto businessinfoDto = (BusinessinfoDto) objectDto;
		BusinessinfoVO businessinfoVO = (BusinessinfoVO) objectVO;
		businessinfoVO.setTaxpayerid(businessinfoDto.getTaxpayerid());
		businessinfoVO.setAnnualSalesRevenue(businessinfoDto.getAnnualSalesRevenue());
		if(StringUtils.isEmpty(businessinfoDto.getBusStructure())){
			businessinfoVO.setBusStructure(null);	
		}else{
			businessinfoVO.setBusStructure(businessinfoDto.getBusStructure());
		}
		businessinfoVO.setCompanySize(businessinfoDto.getCompanySize());
		businessinfoVO.setDescription(businessinfoDto.getDescription());
		businessinfoVO.setDunsNo(businessinfoDto.getDunsNo());
		businessinfoVO.setForeignOwnedPct(businessinfoDto.getForeignOwnedPct());
		businessinfoVO.setIsForeignOwned(businessinfoDto.getIsForeignOwned());
		businessinfoVO.setNoServicePros(businessinfoDto.getNoServicePros());
		businessinfoVO.setVendorId(businessinfoDto.getVendorId());
		businessinfoVO.setMapCompanySize(businessinfoDto.getMapCompanySize());
		businessinfoVO.setMapBusStructure(businessinfoDto.getMapBusStructure());
		businessinfoVO.setMapForeignOwnedPct(businessinfoDto.getMapForeignOwnedPct());
		businessinfoVO.setMapAnnualSalesRevenue(businessinfoDto.getMapAnnualSalesRevenue());
	
		businessinfoVO.setBusinessName(businessinfoDto.getBusinessName());
		businessinfoVO.setDbaName(businessinfoDto.getDbaName());
		businessinfoVO.setBusinessFax(businessinfoDto.getBusinessFax1()+businessinfoDto.getBusinessFax2()+businessinfoDto.getBusinessFax3());
		businessinfoVO.setBusinessPhone(businessinfoDto.getMainBusiPhoneNo1()+businessinfoDto.getMainBusiPhoneNo2()+businessinfoDto.getMainBusiPhoneNo3());
		businessinfoVO.setBusPhoneExtn(businessinfoDto.getBusPhoneExtn());
		businessinfoVO.setWebAddress(businessinfoDto.getWebAddress());
		businessinfoVO.setPrimaryIndList(businessinfoDto.getPrimaryIndList());
		if(StringUtils.isBlank(businessinfoDto.getPrimaryIndustry())){
			businessinfoVO.setPrimaryIndustry(null);
		}else{
			businessinfoVO.setPrimaryIndustry(businessinfoDto.getPrimaryIndustry());
		}	
		businessinfoVO.setEinNo(businessinfoDto.getEinNo());
		businessinfoVO.setBusinessState(businessinfoDto.getBusinessState());
		businessinfoVO.setBusinessStreet1(businessinfoDto.getBusinessStreet1());
		businessinfoVO.setBusinessStreet2(businessinfoDto.getBusinessStreet2());
		businessinfoVO.setBusinessAprt(businessinfoDto.getBusinessAprt());
		businessinfoVO.setBusinessCity(businessinfoDto.getBusinessCity());
		businessinfoVO.setBusinessZip(businessinfoDto.getBusinessZip());
		businessinfoVO.setMailAddressChk(businessinfoDto.isMailAddressChk());
		businessinfoVO.setMailingStreet1(businessinfoDto.getMailingStreet1());
		businessinfoVO.setMailingStreet2(businessinfoDto.getMailingStreet2());
		businessinfoVO.setMailingCity(businessinfoDto.getMailingCity());
		businessinfoVO.setMailingState(businessinfoDto.getMailingState());
		businessinfoVO.setMailingZip(businessinfoDto.getMailingZip());
		businessinfoVO.setMailingAprt(businessinfoDto.getMailingAprt());
		businessinfoVO.setRoleWithinCom(businessinfoDto.getRoleWithinCom());
		businessinfoVO.setJobTitle(businessinfoDto.getJobTitle());
		businessinfoVO.setFirstName(businessinfoDto.getFirstName());
		businessinfoVO.setMiddleName(businessinfoDto.getMiddleName());
		businessinfoVO.setLastName(businessinfoDto.getLastName());
		businessinfoVO.setNameSuffix(businessinfoDto.getNameSuffix());
		businessinfoVO.setEmail(businessinfoDto.getEmail());
		businessinfoVO.setConfirmEmail(businessinfoDto.getConfirmEmail());
		businessinfoVO.setAltEmail(businessinfoDto.getAltEmail());
		businessinfoVO.setConfAltEmail(businessinfoDto.getConfAltEmail());
		businessinfoVO.setAdminInd(businessinfoDto.getAdminInd());
		businessinfoVO.setOtherInd(businessinfoDto.getOtherInd());
		businessinfoVO.setManagerInd(businessinfoDto.getManagerInd());
		businessinfoVO.setOwnerInd(businessinfoDto.getOwnerInd());
		businessinfoVO.setSproInd(businessinfoDto.getSproInd());
		businessinfoVO.setDispatchInd(businessinfoDto.getDispatchInd());
		businessinfoVO.setLocnId(businessinfoDto.getLocnId());
		businessinfoVO.setLocnIdB(businessinfoDto.getLocnIdB());
		businessinfoVO.setStateType(businessinfoDto.getStateType());
		businessinfoVO.setStateTypeList(businessinfoDto.getStateTypeList());
		businessinfoVO.setResID(businessinfoDto.getResID());
		// Changes Starts for Admin name change
		businessinfoVO.setProviderList(businessinfoDto.getProviderList());
		businessinfoVO.setResourceIdList(businessinfoDto.getResourceIdList());
		businessinfoVO.setNewAdminResourceId(businessinfoDto.getNewAdminResourceId());
		businessinfoVO.setOldAdminResourceId(businessinfoDto.getOldAdminResourceId());
		businessinfoVO.setModifiedBy(businessinfoDto.getModifiedBy());
		businessinfoVO.setNewAdminName(businessinfoDto.getNewAdminName());
		// Changes Ends for Admin name change	
		
		String format="yyyy-MM-dd";
		
		try {
        	if(businessinfoDto.getBusStartDt()!=null&&businessinfoDto.getBusStartDt().trim().length()>0){
        		businessinfoVO.setBusStartDt((convertDate(businessinfoDto.getBusStartDt(), format)));
        	}
            
        }catch(Exception ex){
        	//ex.printStackTrace();
        }
		
		return businessinfoVO;
	}

	public BusinessinfoDto convertVOtoDTO(Object objectVO, Object objectDto) {
		BusinessinfoDto businessinfoDto = (BusinessinfoDto) objectDto;
		BusinessinfoVO businessinfoVO = (BusinessinfoVO) objectVO;
		if(businessinfoVO != null){
			if (!isInvalid(businessinfoVO.getTaxpayerid()))
				businessinfoDto.setTaxpayerid(businessinfoVO.getTaxpayerid());
			
			businessinfoDto.setAnnualSalesRevenue(businessinfoVO.getAnnualSalesRevenue());
			 if(businessinfoVO.getBusStartDt()!=null){
				 businessinfoDto.setBusStartDt(formatDate(businessinfoVO.getBusStartDt()));
	            }
			businessinfoDto.setBusStructure(businessinfoVO.getBusStructure());
			businessinfoDto.setCompanySize(businessinfoVO.getCompanySize());
			if (!isInvalid(businessinfoVO.getDescription()))
				businessinfoDto.setDescription(businessinfoVO.getDescription());
			if (!isInvalid(businessinfoVO.getDunsNo()))
				businessinfoDto.setDunsNo(businessinfoVO.getDunsNo());
			businessinfoDto.setForeignOwnedPct(businessinfoVO.getForeignOwnedPct());
			businessinfoDto.setIsForeignOwned(businessinfoVO.getIsForeignOwned());
			businessinfoDto.setNoServicePros(businessinfoVO.getNoServicePros());
			businessinfoDto.setVendorId(businessinfoVO.getVendorId());
			businessinfoDto.setMapCompanySize(businessinfoVO.getMapCompanySize());
			businessinfoDto.setMapBusStructure(businessinfoVO.getMapBusStructure());
			businessinfoDto.setMapForeignOwnedPct(businessinfoVO.getMapForeignOwnedPct());
			businessinfoDto.setMapAnnualSalesRevenue(businessinfoVO.getMapAnnualSalesRevenue());
			
			businessinfoDto.setBusinessName(businessinfoVO.getBusinessName());
			businessinfoDto.setDbaName(businessinfoVO.getDbaName());
			businessinfoDto.setBusinessFax(businessinfoVO.getBusinessFax());
			businessinfoDto.setBusinessPhone(businessinfoVO.getBusinessPhone());
			String mobilePhone = businessinfoVO.getBusinessPhone();
			if (mobilePhone != null 
			&& 	!mobilePhone.equals("")
			&&	mobilePhone.length() == 10)
			{
				businessinfoDto.setMainBusiPhoneNo1(mobilePhone.substring(0,3));
				businessinfoDto.setMainBusiPhoneNo2(mobilePhone.substring(3,6));
				businessinfoDto.setMainBusiPhoneNo3(mobilePhone.substring(6,10));
			}
			String faxNo = businessinfoVO.getBusinessFax();
			if (faxNo != null 
			&& 	!faxNo.equals("")
			&&	faxNo.length() == 10)
			{
				businessinfoDto.setBusinessFax1(faxNo.substring(0,3));
				businessinfoDto.setBusinessFax2(faxNo.substring(3,6));
				businessinfoDto.setBusinessFax3(faxNo.substring(6,10));
			}
			businessinfoDto.setBusPhoneExtn(businessinfoVO.getBusPhoneExtn());
			businessinfoDto.setWebAddress(businessinfoVO.getWebAddress());
			businessinfoDto.setPrimaryIndList(businessinfoVO.getPrimaryIndList());
			businessinfoDto.setPrimaryIndustry(businessinfoVO.getPrimaryIndustry());
			businessinfoDto.setEinNo(businessinfoVO.getEinNo());
			businessinfoDto.setBusinessAprt(businessinfoVO.getBusinessAprt());
			businessinfoDto.setBusinessCity(businessinfoVO.getBusinessCity());
			businessinfoDto.setBusinessState(businessinfoVO.getBusinessState());
			businessinfoDto.setBusinessStreet1(businessinfoVO.getBusinessStreet1());
			businessinfoDto.setBusinessStreet2(businessinfoVO.getBusinessStreet2());
			businessinfoDto.setBusinessZip(businessinfoVO.getBusinessZip());
			businessinfoDto.setMailAddressChk(businessinfoVO.isMailAddressChk());
			businessinfoDto.setMailingStreet1(businessinfoVO.getMailingStreet1());
			businessinfoDto.setMailingStreet2(businessinfoVO.getMailingStreet2());
			businessinfoDto.setMailingCity(businessinfoVO.getMailingCity());
			businessinfoDto.setMailingState(businessinfoVO.getMailingState());
			businessinfoDto.setMailingZip(businessinfoVO.getMailingZip());
			businessinfoDto.setMailingAprt(businessinfoVO.getMailingAprt());
			businessinfoDto.setRoleWithinCom(businessinfoVO.getRoleWithinCom());
			businessinfoDto.setJobTitle(businessinfoVO.getJobTitle());
			businessinfoDto.setFirstName(businessinfoVO.getFirstName());
			businessinfoDto.setMiddleName(businessinfoVO.getMiddleName());
			businessinfoDto.setLastName(businessinfoVO.getLastName());
			businessinfoDto.setNameSuffix(businessinfoVO.getNameSuffix());
			businessinfoDto.setEmail(businessinfoVO.getEmail().trim());
			businessinfoDto.setAltEmail(businessinfoVO.getAltEmail().trim());
			businessinfoDto.setConfirmEmail(businessinfoVO.getConfirmEmail().trim());
			businessinfoDto.setConfAltEmail(businessinfoVO.getConfAltEmail().trim());
			businessinfoDto.setAdminInd(businessinfoVO.getAdminInd());
			businessinfoDto.setOtherInd(businessinfoVO.getOtherInd());
			businessinfoDto.setManagerInd(businessinfoVO.getManagerInd());
			businessinfoDto.setOwnerInd(businessinfoVO.getOwnerInd());
			businessinfoDto.setDispatchInd(businessinfoVO.getDispatchInd());
			businessinfoDto.setSproInd(businessinfoVO.getSproInd());
			businessinfoDto.setLocnId(businessinfoVO.getLocnId());
			businessinfoDto.setLocnIdB(businessinfoVO.getLocnIdB());
			businessinfoDto.setStateType(businessinfoVO.getStateType());
			businessinfoDto.setStateTypeList(businessinfoVO.getStateTypeList());
			businessinfoDto.setResID(businessinfoVO.getResID());
			// Changes Starts for Admin name change
			if(null!=businessinfoVO.getProviderList()){
				List<TeamMemberVO> vendorresourseList=businessinfoVO.getProviderList();
				List<String> resourceIdList= new ArrayList<String>();
				List<String> providerList=new ArrayList<String>();
				
				for (TeamMemberVO resource : vendorresourseList) {
					providerList.add(resource.getLastName()+","+resource.getFirstName()+"("+resource.getResourceId()+")#");
					resourceIdList.add(resource.getResourceId().toString());
				}
				
				businessinfoDto.setProviderList(providerList);
				businessinfoDto.setResourceIdList(resourceIdList);
				businessinfoDto.setModifiedBy(businessinfoVO.getModifiedBy());
				businessinfoDto.setNewAdminResourceId(businessinfoVO.getNewAdminResourceId());
				businessinfoDto.setOldAdminResourceId(businessinfoVO.getOldAdminResourceId());
				}
			// Changes Ends for Admin name change
		
			
		}
		return businessinfoDto;
	}
	
	private boolean isInvalid(String value) {
		return (value == null || value.length() == 0);
	}
	
	private final Date convertDate(final String date1,final String format){
	       Date dateObj1=null;
	      try {
	      if(date1!=null){
	        DateFormat dateFormat = new SimpleDateFormat(format);
	        dateObj1 = dateFormat.parse(date1);
	        }
	      }
	      catch (Exception errorexcep) {
	     	  
	      }
	      return dateObj1;
	  }
		
		private String formatDate(Date dateString){
			
			 Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	         String stringDate1=null;
	          if(dateString!=null) {
	              stringDate1= formatter.format(dateString);
	          }
			return stringDate1;
		}

}
