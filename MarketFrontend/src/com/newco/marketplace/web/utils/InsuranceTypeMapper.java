/**
 * 
 */
package com.newco.marketplace.web.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;

public class InsuranceTypeMapper extends ObjectMapper {
	
	private static final Logger logger = Logger.getLogger(InsuranceTypeMapper.class.getName());

	/**
	 * @param objectDto
	 * @param objectVO
	 * @return
	 */
	public CredentialProfile convertDTOtoVO(Object objectDto, Object objectVO) {
		InsurancePolicyDto insuranceRequest = (InsurancePolicyDto) objectDto;
		CredentialProfile credentialProfile = (CredentialProfile) objectVO;
		logger.info("-----Vendor credential Id --:InsuranceTypeMapper"+insuranceRequest.getVendorCredentialId());
		credentialProfile.setCredentialId(insuranceRequest.getVendorCredentialId());		
		logger.info("-----Vendor Id --:InsuranceTypeMapper"+insuranceRequest.getVendorId());
		credentialProfile.setVendorId(insuranceRequest.getVendorId());
		credentialProfile.setCredentialTypeId(insuranceRequest.getCredentialTypeId());
				
		return credentialProfile;
	}

	
	/**
	 * @param objectVO
	 * @param objectDto
	 * @return
	 */
	public InsurancePolicyDto convertVOtoDTO(Object objectVO, Object objectDto) {
		String dateFormat = null;
		InsurancePolicyDto insurancePolicyDtoRes = (InsurancePolicyDto) objectDto;
		CredentialProfile cProfile = (CredentialProfile) objectVO;
		logger.info("-----------cProfile.getCredentialId()-----"+cProfile.getCredentialId());		
		if(cProfile!=null&& cProfile.getCredentialId()>0){
		insurancePolicyDtoRes.setCategoryId(cProfile.getCredentialCategoryIdName());
		logger.info("-----cProfile.getCredentialCategoryIdName()------"+cProfile.getCredentialCategoryIdName());
		 //Set " " to avoid display of null value in jsp tab_licences_add_edit.jsp
		if(StringUtils.isNotBlank(cProfile.getCredentialSource())){
		    insurancePolicyDtoRes.setCarrierName(cProfile.getCredentialSource());
		}else{
			insurancePolicyDtoRes.setCarrierName("");
			}
		logger.info("-----cProfile.getCredentialSource()------"+cProfile.getCredentialSource());
		if(StringUtils.isNotBlank(cProfile.getCredentialName())){
		   insurancePolicyDtoRes.setAgencyName(cProfile.getCredentialName());
		}else{
		   insurancePolicyDtoRes.setAgencyName("");	
		}
		
        Timestamp aSTimestamp = cProfile.getCurrentDocumentTS();
        SimpleDateFormat asdf = new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a zzz");       

        if (aSTimestamp != null) insurancePolicyDtoRes.setCredentialDocumentUploadTime(asdf.format(aSTimestamp));
        Timestamp issueDate = (cProfile.getCredentialIssueDate());
        Timestamp expirationDate = (cProfile.getCredentialExpirationDate());
		
		//SL-21233: Document Retrieval
        Timestamp createdDate = (cProfile.getCreatedDate());
		//SL-21233: Document Retrieval
        
        if(insurancePolicyDtoRes.isProviderAdmin()){
        	dateFormat = "MM/dd/yyyy"; 
        }else{
        	dateFormat = "yyyy-MM-dd";
        }
        
        if (issueDate != null)
        {
        	Date date1 = new Date(issueDate.getTime());
        	if (date1 != null)
        	{
        		insurancePolicyDtoRes.setPolicyIssDate(date1);
        		insurancePolicyDtoRes.setPolicyIssueDate(formatDate(date1,dateFormat));
        	}
        		
        }else{
        	insurancePolicyDtoRes.setPolicyIssueDate("");
        }
        
        if (expirationDate != null)
        {
        	Date date2 = new Date(expirationDate.getTime());
        	if (date2 != null)
        	{
        		insurancePolicyDtoRes.setPolicyExpDate(date2);
        		insurancePolicyDtoRes.setPolicyExpirationDate(formatDate(date2,dateFormat));
        	}
        		
        }else{
        	insurancePolicyDtoRes.setPolicyExpirationDate("");
        }
        
		
		//SL-21233: Document Retrieval Code Starts
		
        if (createdDate != null)
        {
        	Date date3 = new Date(createdDate.getTime());
        	if (date3 != null)
        	{
        		insurancePolicyDtoRes.setPolicyCreDate(date3);
        		insurancePolicyDtoRes.setPolicyCreatedDate(formatDate(date3,dateFormat));
        	}
        		
        }else{
        	insurancePolicyDtoRes.setPolicyCreatedDate("");
        }
		
		//SL-21233: Document Retrieval Code Ends
		
		
        //Set " " to avoid display of null value in jsp tab_licences_add_edit.jsp
        if(StringUtils.isNotBlank(cProfile.getCredentialNumber())){
           insurancePolicyDtoRes.setPolicyNumber(cProfile.getCredentialNumber());
        }else{
        	insurancePolicyDtoRes.setPolicyNumber("");
        }
        if(StringUtils.isNotBlank(cProfile.getCredentialCounty())){
        insurancePolicyDtoRes.setAgencyCountry(cProfile.getCredentialCounty());
        }else{
        	 insurancePolicyDtoRes.setAgencyCountry("");	
        }
        
		
		//SL-21233: Document Retrieval Code Starts
		
        if(StringUtils.isNotBlank(cProfile.getStatus())){
            insurancePolicyDtoRes.setStatus(cProfile.getStatus());
            }else{
            	 insurancePolicyDtoRes.setStatus("");	
            }
			
        //SL-21233: Document Retrieval Code Ends
		
        
        insurancePolicyDtoRes.setAgencyState(cProfile.getCredentialState());
        //Set " " to avoid display of null value in jsp tab_licences_add_edit.jsp
        if(StringUtils.isNotBlank(cProfile.getCredAmount())){
          insurancePolicyDtoRes.setAmount(cProfile.getCredAmount());
          }else{
        	  insurancePolicyDtoRes.setAmount("");
          }
        
        insurancePolicyDtoRes.setTypeId(cProfile.getCredentialTypeId());
        insurancePolicyDtoRes.setCatId(cProfile.getCredentialCategoryId());
        insurancePolicyDtoRes.setCredentialCategoryId(cProfile.getCredentialCategoryId());
        insurancePolicyDtoRes.setCategory(cProfile.getCredCategory());
        int typeId = (cProfile.getCredentialTypeId());
        int cateId = (cProfile.getCredentialCategoryId());
        String key = typeId + "-" + cateId;
        insurancePolicyDtoRes.setCombinedKey(key);
        insurancePolicyDtoRes.setKey(key);
        
        /**
         * Document related details
         */
        insurancePolicyDtoRes.setCredentialDocumentId(cProfile.getCurrentDocumentID());
        insurancePolicyDtoRes.setCredentialDocumentFileName(cProfile.getCredentialDocumentFileName());
        insurancePolicyDtoRes.setCredentialDocumentFileSize(cProfile.getCredentialDocumentFileSize());
        insurancePolicyDtoRes.setLastUploadedDocumentId(cProfile.getCurrentDocumentID());
		}else if(cProfile!=null){
			/**
	         * Last Document uploaded by vendor is set to DTO
	         */
	        insurancePolicyDtoRes.setCredentialDocumentId(cProfile.getCurrentDocumentID());
	        insurancePolicyDtoRes.setCredentialDocumentFileName(cProfile.getCredentialDocumentFileName());
	        insurancePolicyDtoRes.setCredentialDocumentFileSize(cProfile.getCredentialDocumentFileSize());
	        insurancePolicyDtoRes.setLastUploadedDocumentId(cProfile.getCurrentDocumentID());
		}
		//CredentialType Map
		if(cProfile.getMapCredentialType()!=null)
		{
			insurancePolicyDtoRes.setMapCredentialType(cProfile.getMapCredentialType());
		}
		
		//SL-10809: Additional Insurance
		if(cProfile.getAdditionalInsuranceList()!=null && !(cProfile.getAdditionalInsuranceList().isEmpty()))
		{
			insurancePolicyDtoRes.setAdditionalInsuranceList(cProfile.getAdditionalInsuranceList());
			insurancePolicyDtoRes.setInsuranceListSize(cProfile.getAdditionalInsuranceList().size());
			
		}
		if(cProfile.getCredentialCategoryDesc()!=null)
		{
			insurancePolicyDtoRes.setCredentialCategoryDesc(cProfile.getCredentialCategoryDesc());
		}
		return insurancePolicyDtoRes;
	}
	
	/**
	 * @param objectVO
	 * @param objectDto
	 * @return
	 */
	public InsurancePolicyDto convertVOtoDTO4Display(Object objectVO, Object objectDto) {
		InsurancePolicyDto insurancePolicyDtoRes = (objectDto != null) ? (InsurancePolicyDto) objectDto : null;
		CredentialProfile cProfile = (objectVO != null) ? (CredentialProfile) objectVO : null;		
		try
		{
			if (insurancePolicyDtoRes == null || cProfile == null)
				return null;			
			/**
	         * Code added for Document File Upload
	         */
	        insurancePolicyDtoRes.setCredentialDocumentBytes(cProfile.getCredentialDocumentBytes());
	        insurancePolicyDtoRes.setCredentialDocumentExtention(cProfile.getCredentialDocumentExtention());
	        insurancePolicyDtoRes.setCredentialDocumentFileName(cProfile.getCredentialDocumentFileName());
	        insurancePolicyDtoRes.setCredentialDocumentFileSize(cProfile.getCredentialDocumentFileSize());
	        insurancePolicyDtoRes.setCredentialDocumentId(cProfile.getCurrentDocumentID());
			
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
		}
		return insurancePolicyDtoRes;
	}
	
	/**
	 * @param sAmount
	 * @return
	 */
	private String convertStringToDollar(String sAmount) {
		String retVal = "";
		double dConversion = Double.parseDouble(sAmount);
		retVal = java.text.NumberFormat.getCurrencyInstance().format(dConversion);
		retVal = retVal.replace("$", "");
		retVal = retVal.replace(",", "");
		return retVal;
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
