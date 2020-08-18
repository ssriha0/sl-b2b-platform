
package com.newco.marketplace.web.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;

/**
 * 
 * $Revision: 1.9 $ $Author: glacy $ $Date: 2008/04/26 01:13:46 $
 * 
 */
public class InsuranceTypeSaveMapper extends ObjectMapper {
	
	private static final Logger logger = Logger.getLogger(InsuranceTypeSaveMapper.class.getName());
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public CredentialProfile convertDTOtoVO(Object objectDto, Object objectVO) {
		
		InsurancePolicyDto insuranceRequest = (InsurancePolicyDto) objectDto;
		CredentialProfile credentialProfile = (CredentialProfile) objectVO;
		credentialProfile.setCredentialId(insuranceRequest
				.getVendorCredentialId());
		
		credentialProfile.setVendorId(insuranceRequest.getVendorId());
		
			try {
				credentialProfile = new CredentialProfile();

				/**
				 * Code added for Document File Upload
				 */
				credentialProfile.setCredentialDocumentBytes(insuranceRequest.getCredentialDocumentBytes());
				credentialProfile.setCredentialDocumentExtention(insuranceRequest.getCredentialDocumentExtention());
				credentialProfile.setCredentialDocumentFileName(insuranceRequest.getCredentialDocumentFileName());
				credentialProfile.setCredentialDocumentFileSize(insuranceRequest.getCredentialDocumentFileSize());
				credentialProfile.setCurrentDocumentID(insuranceRequest.getCredentialDocumentId());
				
				credentialProfile.setVendorId(insuranceRequest.getVendorId());
				credentialProfile.setCredentialId(insuranceRequest
						.getVendorCredentialId());
				credentialProfile.setCredentialCategoryIdName(insuranceRequest
						.getCategoryId());
				String combinedId = insuranceRequest.getCombinedKey();
				StringTokenizer tempStringTokenizer = new StringTokenizer(
						combinedId, "-");
				
				String credentialTypeId = "0";
				if (tempStringTokenizer.countTokens() > 0)
					credentialTypeId = tempStringTokenizer.nextToken().trim();
				
				String credCategoryId = "0";
				if (tempStringTokenizer.countTokens() > 0)
					credCategoryId = tempStringTokenizer.nextToken().trim(); 
						
				credentialProfile.setCredentialTypeId(Integer
						.parseInt(credentialTypeId));
				credentialProfile.setCredentialCategoryId(Integer
						.parseInt(credCategoryId));
				credentialProfile.setCredentialSource(insuranceRequest
						.getCarrierName().trim());
				credentialProfile.setCredentialName(insuranceRequest
						.getAgencyName().trim());
				credentialProfile.setCredentialNumber(insuranceRequest
						.getPolicyNumber().trim());
				credentialProfile.setCredentialState((insuranceRequest)
						.getAgencyState().trim());
				
				String issueDate = "";
				String issDate = insuranceRequest.getPolicyIssueDate();
				if (issDate != null)
					issueDate = issDate;
				SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
				if(StringUtils.isNotEmpty(issueDate)){
					java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
					System.out.println("ISSUE PARSED DATE" + issueParsedDate);
					Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
					System.out.println("ISSUE PARSED DATE RESULT" + issueDateResult);
					credentialProfile.setCredentialIssueDate(issueDateResult);
					Date issueDate1 = new Date(issueParsedDate.getTime());
				}else{
					credentialProfile.setCredentialIssueDate(null);
					Date issueDate1 = null;
				}
				String expirationDate = "";
				String expDate = insuranceRequest.getPolicyExpirationDate();
				if (expDate != null)
					expirationDate = expDate; 
				SimpleDateFormat expirationDateformater = new SimpleDateFormat(
						"yyyy-MM-dd");
				if(StringUtils.isNotEmpty(expirationDate)){
					java.util.Date expirationParsedDate = expirationDateformater
						.parse(expirationDate);
					System.out.println("EXPIRATION DATE" + expirationDate);
					Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
					System.out.println("EXPIRATION PARSED DATE" + expirationDateResult);
					credentialProfile.setCredentialExpirationDate(expirationDateResult);
					Date policyDate1 = new Date(expirationParsedDate.getTime());
				}else{
					credentialProfile.setCredentialExpirationDate(null);
					Date policyDate1 = null;
				}
				// SETTING THE VALUES ON THE FORM. The jsp uses the
				// policyIssueDate on the form
				// to display the values instead of the issue Date
				//Date issueDate1 = new Date(issueParsedDate.getTime());

				//Date policyDate1 = new Date(expirationParsedDate.getTime());

				insuranceRequest.setPolicyIssueDate(issueDate);

				insuranceRequest.setPolicyExpirationDate(expDate);

				
			/*	credentialProfile
						.setCredentialExpirationDate(expirationDateResult);*/
				credentialProfile.setCredentialCounty((insuranceRequest)
						.getAgencyCountry().trim());

			String sAmount = insuranceRequest.getAmount();
			
				
				if(StringUtils.isNotEmpty(sAmount)){
					String sRetVal = convertStringToDollar(sAmount);
					credentialProfile.setCredAmount(sRetVal);
				}else{
					credentialProfile.setCredAmount("");
				}
				
				
			} catch (NumberFormatException e) {
				logger.info("Caught Exception and ignoring",e);
			} catch (ParseException e) {
				logger.info("Caught Exception and ignoring",e);
			}

		return credentialProfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public InsurancePolicyDto convertVOtoDTO(Object objectVO, Object objectDto) {
		InsurancePolicyDto insurancePolicyDtoRes = (InsurancePolicyDto) objectDto;
		CredentialProfile cProfile = (CredentialProfile) objectVO;

		if(cProfile!=null&& cProfile.getCredentialId()>0){
		insurancePolicyDtoRes.setCategoryId(cProfile.getCredentialCategoryIdName());

		insurancePolicyDtoRes.setCarrierName(cProfile.getCredentialSource());

		insurancePolicyDtoRes.setAgencyName(cProfile.getCredentialName());
		insurancePolicyDtoRes.setCredentialDocumentId(cProfile.getCurrentDocumentID());

        Timestamp aSTimestamp = cProfile.getCurrentDocumentTS();

        /**
         * Code added for Document File Upload
         */
        insurancePolicyDtoRes.setCredentialDocumentBytes(cProfile.getCredentialDocumentBytes());
        insurancePolicyDtoRes.setCredentialDocumentExtention(cProfile.getCredentialDocumentExtention());
        insurancePolicyDtoRes.setCredentialDocumentFileName(cProfile.getCredentialDocumentFileName());
        insurancePolicyDtoRes.setCredentialDocumentFileSize(cProfile.getCredentialDocumentFileSize());
        insurancePolicyDtoRes.setCredentialDocumentId(cProfile.getCurrentDocumentID());
        
        SimpleDateFormat asdf = new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a zzz");

        if (aSTimestamp != null) insurancePolicyDtoRes.setCredentialDocumentUploadTime(asdf.format(aSTimestamp));
        Timestamp issueDate = (cProfile.getCredentialIssueDate());
        Timestamp expirationDate = (cProfile.getCredentialExpirationDate());
        
        if (issueDate != null){
        	Date dtIssue = new Date(issueDate.getTime());
        	if (dtIssue != null)
        		insurancePolicyDtoRes.setPolicyIssueDate(formatDate(dtIssue));
        }
        if (expirationDate != null){
        	Date expDate = new Date(expirationDate.getTime());
        	if (expDate != null)
        		insurancePolicyDtoRes.setPolicyExpirationDate(formatDate(expDate));
        }
        
        insurancePolicyDtoRes.setPolicyNumber(cProfile.getCredentialNumber());
        insurancePolicyDtoRes.setAgencyCountry(cProfile.getCredentialCounty());
        insurancePolicyDtoRes.setAgencyState(cProfile.getCredentialState());

        insurancePolicyDtoRes.setAmount(cProfile.getCredAmount());
        insurancePolicyDtoRes.setTypeId(cProfile.getCredentialTypeId());
        insurancePolicyDtoRes.setCatId(cProfile.getCredentialCategoryId());
        int typeId = (cProfile.getCredentialTypeId());
        int cateId = (cProfile.getCredentialCategoryId());
        String key = typeId + "-" + cateId;
        insurancePolicyDtoRes.setCombinedKey(key);
        insurancePolicyDtoRes.setKey(key);
        
        
        
		}
		return insurancePolicyDtoRes;
	}
	
	private String convertStringToDollar(String sAmount) {
		String retVal = "";
		double dConversion = Double.parseDouble(sAmount);

		retVal = java.text.NumberFormat.getCurrencyInstance().format(
				dConversion);

		//logger.info(retVal);
		retVal = retVal.replace("$", "");
		//logger.info(retVal);
		retVal = retVal.replace(",", "");
		//logger.info(retVal);

		return retVal;
	}
	
	private final Date convertDate(final String date1,final String format){
	      Date dateObj1=null;
	      try {
	      if(date1!=null){
	        DateFormat dateFormat = new SimpleDateFormat(format);
	        dateObj1 = dateFormat.parse(date1);
	      
	        }
	      }//end of try
	      catch (Exception errorexcep) {
	        //logger.log(ExceptionConstants.ERROR,"The Parse Error in compare date is:" + errorexcep);
	    	  System.out.println("-------------------Exception ex in parsing the date in mapper-----");
	      }//end of catch
	      return dateObj1;
	  }
		
		private String formatDate(Date dateString){
			
			 Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			 System.out.println("------------------insie format date-----"+dateString);
	         String stringDate1=null;
	          if(dateString!=null) {
	              stringDate1= formatter.format(dateString);
	          }
			return stringDate1;
		}
	
		public CredentialProfile convertPolicyDetailsDTOtoVO(Object objectDto, Object objectVO) {
			SimpleDateFormat issueDateformater;
			SimpleDateFormat expirationDateformater;
			InsurancePolicyDto insuranceRequest = (InsurancePolicyDto) objectDto;
			CredentialProfile credentialProfile = (CredentialProfile) objectVO;
			credentialProfile.setCredentialId(insuranceRequest
					.getVendorCredentialId());
			
			credentialProfile.setVendorId(insuranceRequest.getVendorId());
			
				try {
					credentialProfile = new CredentialProfile();

					/**
					 * Code added for Document File Upload
					 */
					credentialProfile.setCredentialDocumentBytes(insuranceRequest.getCredentialDocumentBytes());
					credentialProfile.setCredentialDocumentExtention(insuranceRequest.getCredentialDocumentExtention());
					credentialProfile.setCredentialDocumentFileName(insuranceRequest.getCredentialDocumentFileName());
					credentialProfile.setCredentialDocumentFileSize(insuranceRequest.getCredentialDocumentFileSize());
					credentialProfile.setCurrentDocumentID(insuranceRequest.getCredentialDocumentId());
					if(insuranceRequest.getLastUploadedDocumentId()>0){
						credentialProfile.setCurrentDocumentID(insuranceRequest.getLastUploadedDocumentId());
					}
					credentialProfile.setVendorId(insuranceRequest.getVendorId());
					credentialProfile.setCredentialId(insuranceRequest
							.getVendorCredentialId());
					credentialProfile.setCredentialCategoryIdName(insuranceRequest
							.getCategoryId());
					/*String combinedId = insuranceRequest.getCombinedKey();
					StringTokenizer tempStringTokenizer = new StringTokenizer(
							combinedId, "-");
					
					String credentialTypeId = "0";
					if (tempStringTokenizer.countTokens() > 0)
						credentialTypeId = tempStringTokenizer.nextToken().trim();
					
					String credCategoryId = "0";
					if (tempStringTokenizer.countTokens() > 0)
						credCategoryId = tempStringTokenizer.nextToken().trim(); 
							
					credentialProfile.setCredentialTypeId(Integer
							.parseInt(credentialTypeId));
					credentialProfile.setCredentialCategoryId(Integer
							.parseInt(credCategoryId));*/
					credentialProfile.setCredentialTypeId(insuranceRequest.getCredentialTypeId());
					credentialProfile.setCredentialCategoryId(insuranceRequest.getCredentialCategoryId());
					credentialProfile.setCredentialSource(insuranceRequest
							.getCarrierName().trim());
					credentialProfile.setCredentialName(insuranceRequest
							.getAgencyName().trim());
					credentialProfile.setCredentialNumber(insuranceRequest
							.getPolicyNumber().trim());
					credentialProfile.setCredentialState((insuranceRequest)
							.getAgencyState().trim());
					
					String issueDate = "";
					String expDate="";
					//SL:10809 Additional Insurance
					//If the insurance is of type General Liability or Vehicle Liability or Workers compensation, change the date format to MM/dd/yyyy.
					//For all other types set the date format to yyyy-MM-dd.
					//This is because of the different Datepickers used in these two cases.
			if (insuranceRequest.getCredentialCategoryId() == OrderConstants.GL_CREDENTIAL_CATEGORY_ID
					|| insuranceRequest.getCredentialCategoryId() == OrderConstants.AL_CREDENTIAL_CATEGORY_ID || insuranceRequest
						.getCredentialCategoryId() == OrderConstants.WC_CREDENTIAL_CATEGORY_ID) {
						
						
						String issDate = insuranceRequest.getPolicyIssueDate();
						if (issDate != null)
							issueDate = issDate;
						issueDateformater = new SimpleDateFormat("MM/dd/yyyy");
						if(StringUtils.isNotEmpty(issueDate)){
							java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
							System.out.println("ISSUE PARSED DATE" + issueParsedDate);
							Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
							System.out.println("ISSUE PARSED DATE RESULT" + issueDateResult);
							credentialProfile.setCredentialIssueDate(issueDateResult);
							Date issueDate1 = new Date(issueParsedDate.getTime());
						}else{
							credentialProfile.setCredentialIssueDate(null);
							Date issueDate1 = null;
						}
						String expirationDate = "";
						expDate = insuranceRequest.getPolicyExpirationDate();
						if (expDate != null)
							expirationDate = expDate; 
						expirationDateformater = new SimpleDateFormat(
								"MM/dd/yyyy");
						if(StringUtils.isNotEmpty(expirationDate)){
							java.util.Date expirationParsedDate = expirationDateformater
								.parse(expirationDate);
							System.out.println("EXPIRATION DATE" + expirationDate);
							Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
							System.out.println("EXPIRATION PARSED DATE" + expirationDateResult);
							credentialProfile.setCredentialExpirationDate(expirationDateResult);
							Date policyDate1 = new Date(expirationParsedDate.getTime());
						}else{
							credentialProfile.setCredentialExpirationDate(null);
							Date policyDate1 = null;
						}
					}
			else
			{
				
				String issDate = insuranceRequest.getPolicyIssueDate();
				if (issDate != null)
					issueDate = issDate;
				issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
				if(StringUtils.isNotEmpty(issueDate)){
					java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
					System.out.println("ISSUE PARSED DATE" + issueParsedDate);
					Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
					System.out.println("ISSUE PARSED DATE RESULT" + issueDateResult);
					credentialProfile.setCredentialIssueDate(issueDateResult);
					Date issueDate1 = new Date(issueParsedDate.getTime());
				}else{
					credentialProfile.setCredentialIssueDate(null);
					Date issueDate1 = null;
				}
				String expirationDate = "";
				expDate = insuranceRequest.getPolicyExpirationDate();
				if (expDate != null)
					expirationDate = expDate; 
				expirationDateformater = new SimpleDateFormat(
						"yyyy-MM-dd");
				if(StringUtils.isNotEmpty(expirationDate)){
					java.util.Date expirationParsedDate = expirationDateformater
						.parse(expirationDate);
					System.out.println("EXPIRATION DATE" + expirationDate);
					Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
					System.out.println("EXPIRATION PARSED DATE" + expirationDateResult);
					credentialProfile.setCredentialExpirationDate(expirationDateResult);
					Date policyDate1 = new Date(expirationParsedDate.getTime());
				}else{
					credentialProfile.setCredentialExpirationDate(null);
					Date policyDate1 = null;
				}
				
				//Set Name for other Insurance Types
				credentialProfile.setCredentialCategoryDesc(insuranceRequest.getCredentialCategoryDesc().trim());
			}
					
					
					
			
					// SETTING THE VALUES ON THE FORM. The jsp uses the
					// policyIssueDate on the form
					// to display the values instead of the issue Date
					//Date issueDate1 = new Date(issueParsedDate.getTime());

					//Date policyDate1 = new Date(expirationParsedDate.getTime());

					insuranceRequest.setPolicyIssueDate(issueDate);

					insuranceRequest.setPolicyExpirationDate(expDate);

					
				/*	credentialProfile
							.setCredentialExpirationDate(expirationDateResult);*/
					credentialProfile.setCredentialCounty((insuranceRequest)
							.getAgencyCountry().trim());

				String sAmount = insuranceRequest.getAmount();
				
					
					if(StringUtils.isNotEmpty(sAmount)){
						String sRetVal = convertStringToDollar(sAmount);
						credentialProfile.setCredAmount(sRetVal);
					}else{
						credentialProfile.setCredAmount("");
					}
					
					
				} catch (NumberFormatException e) {
					logger.info("Caught Exception and ignoring",e);
				} catch (ParseException e) {
					logger.info("Caught Exception and ignoring",e);
				}

			return credentialProfile;
		}
		
		
	
}
