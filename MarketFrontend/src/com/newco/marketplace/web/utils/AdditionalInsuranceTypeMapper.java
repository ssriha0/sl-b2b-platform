package com.newco.marketplace.web.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.web.dto.provider.AdditionalInsurancePolicyDTO;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;

/*Changes related to SL-20301: Details based on LastUploadedDocument*/
public class AdditionalInsuranceTypeMapper {

	
	private static final Logger logger = Logger.getLogger(InsuranceTypeMapper.class.getName());

	/**
	 * @param objectDto
	 * @param objectVO
	 * @return
	 */
	public CredentialProfile convertDTOtoVO(Object objectDto, Object objectVO) {
		AdditionalInsurancePolicyDTO addtnlinsuranceRequest = (AdditionalInsurancePolicyDTO) objectDto;
		CredentialProfile credentialProfile = (CredentialProfile) objectVO;
		//logger.info("-----Vendor credential Id --:InsuranceTypeMapper"+addtnlinsuranceRequest.getVendorCredentialId());
		credentialProfile.setCredentialId(addtnlinsuranceRequest.getVendorCredentialId());		
		logger.info("-----Vendor Id --:InsuranceTypeMapper"+addtnlinsuranceRequest.getVendorId());
		credentialProfile.setVendorId(addtnlinsuranceRequest.getVendorId());
		credentialProfile.setCredentialTypeId(addtnlinsuranceRequest.getCredentialTypeId());
				
		return credentialProfile;
	}
	
	/**
	 * @param objectVO
	 * @param objectDto
	 * @return
	 */
	public AdditionalInsurancePolicyDTO convertVOtoDTO(Object objectVO, Object objectDto) {
		String dateFormat = null;
		AdditionalInsurancePolicyDTO additionalInsurancePolicyDTORes = (AdditionalInsurancePolicyDTO) objectDto;
		//InsurancePolicyDto insurancePolicyDtoRes = (InsurancePolicyDto) objectDto;
		CredentialProfile cProfile = (CredentialProfile) objectVO;
		logger.info("-----------cProfile.getCredentialId()-----"+cProfile.getCredentialId());		
		if(cProfile!=null&& cProfile.getCredentialId()>0){
			additionalInsurancePolicyDTORes.setCategoryId(cProfile.getCredentialCategoryIdName());
		logger.info("-----cProfile.getCredentialCategoryIdName()------"+cProfile.getCredentialCategoryIdName());
		 //Set " " to avoid display of null value in jsp tab_licences_add_edit.jsp
		if(StringUtils.isNotBlank(cProfile.getCredentialSource())){
			additionalInsurancePolicyDTORes.setCarrierName(cProfile.getCredentialSource());
		}else{
			additionalInsurancePolicyDTORes.setCarrierName("");
			}
		logger.info("-----cProfile.getCredentialSource()------"+cProfile.getCredentialSource());
		if(StringUtils.isNotBlank(cProfile.getCredentialName())){
			additionalInsurancePolicyDTORes.setAgencyName(cProfile.getCredentialName());
		}else{
			additionalInsurancePolicyDTORes.setAgencyName("");	
		}
		
        Timestamp aSTimestamp = cProfile.getCurrentDocumentTS();
        SimpleDateFormat asdf = new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a zzz");       

        if (aSTimestamp != null) additionalInsurancePolicyDTORes.setCredentialDocumentUploadTime(asdf.format(aSTimestamp));
        
		Timestamp issueDate = (cProfile.getCredentialIssueDate());
        Timestamp expirationDate = (cProfile.getCredentialExpirationDate());
        
        if(additionalInsurancePolicyDTORes.isProviderAdmin()){
        	dateFormat = "MM/dd/yyyy"; 
        }else{
        	dateFormat = "yyyy-MM-dd";
        }
        
        if (issueDate != null)
        {
        	Date date1 = new Date(issueDate.getTime());
        	if (date1 != null)
        	{
        		additionalInsurancePolicyDTORes.setPolicyIssDate(date1);
        		additionalInsurancePolicyDTORes.setPolicyIssueDate(formatDate(date1,dateFormat));
        	}
        		
        }else{
        	additionalInsurancePolicyDTORes.setPolicyIssueDate("");
        }
        
        if (expirationDate != null)
        {
        	Date date2 = new Date(expirationDate.getTime());
        	if (date2 != null)
        	{
        		additionalInsurancePolicyDTORes.setPolicyExpDate(date2);
        		additionalInsurancePolicyDTORes.setPolicyExpirationDate(formatDate(date2,dateFormat));
        	}
        		
        }else{
        	additionalInsurancePolicyDTORes.setPolicyExpirationDate("");
        }
        //Set " " to avoid display of null value in jsp tab_licences_add_edit.jsp
        if(StringUtils.isNotBlank(cProfile.getCredentialNumber())){
        	additionalInsurancePolicyDTORes.setPolicyNumber(cProfile.getCredentialNumber());
        }else{
        	additionalInsurancePolicyDTORes.setPolicyNumber("");
        }
        if(StringUtils.isNotBlank(cProfile.getCredentialCounty())){
        	additionalInsurancePolicyDTORes.setAgencyCountry(cProfile.getCredentialCounty());
        }else{
        	additionalInsurancePolicyDTORes.setAgencyCountry("");	
        }
        additionalInsurancePolicyDTORes.setAgencyState(cProfile.getCredentialState());
        //Set " " to avoid display of null value in jsp tab_licences_add_edit.jsp
        if(StringUtils.isNotBlank(cProfile.getCredAmount())){
        	additionalInsurancePolicyDTORes.setAmount(cProfile.getCredAmount());
          }else{
        	  additionalInsurancePolicyDTORes.setAmount("");
          }
        
        additionalInsurancePolicyDTORes.setTypeId(cProfile.getCredentialTypeId());
        additionalInsurancePolicyDTORes.setCatId(cProfile.getCredentialCategoryId());
        additionalInsurancePolicyDTORes.setCredentialCategoryId(cProfile.getCredentialCategoryId());
        additionalInsurancePolicyDTORes.setCategory(cProfile.getCredCategory());
        int typeId = (cProfile.getCredentialTypeId());
        int cateId = (cProfile.getCredentialCategoryId());
        String key = typeId + "-" + cateId;
        additionalInsurancePolicyDTORes.setCombinedKey(key);
        additionalInsurancePolicyDTORes.setKey(key);
        
        /**
         * Document related details
         */
        additionalInsurancePolicyDTORes.setCredentialDocumentId(cProfile.getCurrentDocumentID());
        additionalInsurancePolicyDTORes.setCredentialDocumentFileName(cProfile.getCredentialDocumentFileName());
        additionalInsurancePolicyDTORes.setCredentialDocumentFileSize(cProfile.getCredentialDocumentFileSize());
        additionalInsurancePolicyDTORes.setLastUploadedDocumentId(cProfile.getCurrentDocumentID());
		}else if(cProfile!=null){
			/**
	         * Last Document uploaded by vendor is set to DTO
	         */
			additionalInsurancePolicyDTORes.setCredentialDocumentId(cProfile.getCurrentDocumentID());
			additionalInsurancePolicyDTORes.setCredentialDocumentFileName(cProfile.getCredentialDocumentFileName());
			additionalInsurancePolicyDTORes.setCredentialDocumentFileSize(cProfile.getCredentialDocumentFileSize());
			additionalInsurancePolicyDTORes.setLastUploadedDocumentId(cProfile.getCurrentDocumentID());
		}
		//CredentialType Map
		if(cProfile.getMapCredentialType()!=null)
		{
			additionalInsurancePolicyDTORes.setMapCredentialType(cProfile.getMapCredentialType());
		}
		
		//SL-10809: Additional Insurance
		if(cProfile.getAdditionalInsuranceList()!=null && !(cProfile.getAdditionalInsuranceList().isEmpty()))
		{
			additionalInsurancePolicyDTORes.setAdditionalInsuranceList(cProfile.getAdditionalInsuranceList());
			additionalInsurancePolicyDTORes.setInsuranceListSize(cProfile.getAdditionalInsuranceList().size());
			
		}
		if(cProfile.getCredentialCategoryDesc()!=null)
		{
			additionalInsurancePolicyDTORes.setCredentialCategoryDesc(cProfile.getCredentialCategoryDesc());
		}
		return additionalInsurancePolicyDTORes;
	}
	
	/**
	 * @param dateString
	 * @return
	 */
	private String formatDate(Date dateString,String format){
		
		// Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		 Format formatter = new SimpleDateFormat(format);
		 System.out.println("------------------insie format date-----"+dateString);
        String stringDate1=null;
         if(dateString!=null) {
             stringDate1= formatter.format(dateString);
         }
		return stringDate1;
	}
	
}
