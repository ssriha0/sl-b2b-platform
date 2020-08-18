package com.newco.marketplace.web.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.web.dto.provider.LicensesAndCertDto;

/**
 * @author LENOVO USER
 *
 */
public class LicensesAndCertMapper extends ObjectMapper {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object, java.lang.Object)
	 */
	public LicensesAndCertVO convertDTOtoVO(Object objectDto, Object objectVO) {
		LicensesAndCertDto licensesAndCertDto = (LicensesAndCertDto) objectDto;
		LicensesAndCertVO licensesAndCertVO = (LicensesAndCertVO) objectVO;
		String format="yyyy-MM-dd";
		licensesAndCertVO.setCredentialTypeId(licensesAndCertDto.getCredentialTypeId());
		licensesAndCertVO.setCategoryId(licensesAndCertDto.getCategoryId());
		licensesAndCertVO.setCity(licensesAndCertDto.getCity());
		licensesAndCertVO.setCounty(licensesAndCertDto.getCounty());
		licensesAndCertVO.setCredentialNum(licensesAndCertDto.getCredentialNum());
		licensesAndCertVO.setIssuerOfCredential(licensesAndCertDto.getIssuerOfCredential());
		licensesAndCertVO.setLicenseName(licensesAndCertDto.getLicenseName());
		licensesAndCertVO.setStateId(licensesAndCertDto.getStateId());
		licensesAndCertVO.setVendorId(licensesAndCertDto.getVendorId());
		licensesAndCertVO.setMapCategory(licensesAndCertDto.getMapCategory());
		licensesAndCertVO.setMapCredentialType(licensesAndCertDto.getMapCredentialType());
		licensesAndCertVO.setMapState(licensesAndCertDto.getMapState());
		licensesAndCertVO.setDataList(licensesAndCertDto.getDataList());
		licensesAndCertVO.setCredentials(licensesAndCertDto.getCredentials());
		licensesAndCertVO.setVendorCredId(licensesAndCertDto.getVendorCredId());
		licensesAndCertVO.setCredTypeDesc(licensesAndCertDto.getCredTypeDesc());
		licensesAndCertVO.setAddCredentialToFile(licensesAndCertDto.getAddCredentialToFile());
		licensesAndCertDto.setResourceName(licensesAndCertVO.getResourceName());
		/**
		 * Code added for Document File Upload
		 */
		licensesAndCertVO.setCredentialDocumentBytes(licensesAndCertDto.getCredentialDocumentBytes());
		licensesAndCertVO.setCredentialDocumentExtention(licensesAndCertDto.getCredentialDocumentExtention());
		licensesAndCertVO.setCredentialDocumentFileName(licensesAndCertDto.getCredentialDocumentFileName());
		licensesAndCertVO.setCredentialDocumentFileSize(licensesAndCertDto.getCredentialDocumentFileSize());
		licensesAndCertVO.setCurrentDocumentID (licensesAndCertDto.getCredentialDocumentId());
		licensesAndCertVO.setCredSize(licensesAndCertDto.getCredSize());
		System.out.println("issue date in the mapper *******"+licensesAndCertDto.getIssueDate());
        try {
        	if(licensesAndCertDto.getIssueDate()!=null&&licensesAndCertDto.getIssueDate().trim().length()>0){
        		licensesAndCertVO.setIssueDate((convertDate(licensesAndCertDto.getIssueDate(), format)));
        	}
        	else{
        		licensesAndCertVO.setIssueDate(null);
        	}
        	
            if (licensesAndCertDto.getExpirationDate() != null && licensesAndCertDto.getExpirationDate().length() > 1){
            	licensesAndCertVO.setExpirationDate((convertDate(licensesAndCertDto.getExpirationDate(), format)));
            }else{
            	licensesAndCertVO.setExpirationDate(null);
            }
        }catch(Exception ex){
        	//ex.printStackTrace();
        }
		
		
		
		return licensesAndCertVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public LicensesAndCertDto convertVOtoDTO(Object objectVO, Object objectDto) {
		LicensesAndCertDto licensesAndCertDto = (LicensesAndCertDto) objectDto;
		LicensesAndCertVO licensesAndCertVO = (LicensesAndCertVO) objectVO;
		
		/**
         * Code added for Document File Upload
         */
		licensesAndCertDto.setCredentialDocumentBytes(licensesAndCertVO.getCredentialDocumentBytes());
		licensesAndCertDto.setCredentialDocumentExtention(licensesAndCertVO.getCredentialDocumentExtention());
		licensesAndCertDto.setCredentialDocumentFileName(licensesAndCertVO.getCredentialDocumentFileName());
		licensesAndCertDto.setCredentialDocumentFileSize(licensesAndCertVO.getCredentialDocumentFileSize());
		licensesAndCertDto.setCredentialDocumentId(licensesAndCertVO.getCurrentDocumentID());
		
		licensesAndCertDto.setCredentialTypeId(licensesAndCertVO.getCredentialTypeId());
		licensesAndCertDto.setCategoryId(licensesAndCertVO.getCategoryId());
		licensesAndCertDto.setCity(licensesAndCertVO.getCity());
		licensesAndCertDto.setCounty(licensesAndCertVO.getCounty());
		licensesAndCertDto.setCredentialNum(licensesAndCertVO.getCredentialNum());
		licensesAndCertDto.setIssuerOfCredential(licensesAndCertVO.getIssuerOfCredential());
		licensesAndCertDto.setLicenseName(licensesAndCertVO.getLicenseName());
		licensesAndCertDto.setStateId(licensesAndCertVO.getStateId());
		licensesAndCertDto.setVendorId(licensesAndCertVO.getVendorId());
		licensesAndCertDto.setMapCategory(licensesAndCertVO.getMapCategory());
		licensesAndCertDto.setMapCredentialType(licensesAndCertVO.getMapCredentialType());
		licensesAndCertDto.setMapState(licensesAndCertVO.getMapState());
		licensesAndCertDto.setDataList(licensesAndCertVO.getDataList());
		licensesAndCertDto.setCredentials(licensesAndCertVO.getCredentials());
		licensesAndCertDto.setVendorCredId(licensesAndCertVO.getVendorCredId());
		licensesAndCertDto.setCredTypeDesc(licensesAndCertVO.getCredTypeDesc());
		licensesAndCertDto.setAddCredentialToFile(licensesAndCertVO.getAddCredentialToFile());
		
		if(licensesAndCertVO.getIssueDate()!=null){
			licensesAndCertDto.setIssueDate(formatDate(licensesAndCertVO.getIssueDate()));
        }else{
        	licensesAndCertDto.setIssueDate(null);
        }
        if(licensesAndCertVO.getExpirationDate()!=null){
        	licensesAndCertDto.setExpirationDate(formatDate(licensesAndCertVO.getExpirationDate()));
        }else{
        	licensesAndCertDto.setExpirationDate(null);
        }
		
		return licensesAndCertDto;
	}
	
	public LicensesAndCertDto convertVOtoDTO4Display(Object objectVO, Object objectDto) {
		LicensesAndCertDto licencesDto = (objectDto != null) ? (LicensesAndCertDto) objectDto : null;
		LicensesAndCertVO licencesVO = (objectVO != null) ? (LicensesAndCertVO) objectVO : null;
		
		try
		{
			if (licencesDto == null || licencesVO == null)
				return null;
			
			/**
	         * Code added for Document File Upload
	         */
	        licencesDto.setCredentialDocumentBytes(licencesVO.getCredentialDocumentBytes());
	        licencesDto.setCredentialDocumentExtention(licencesVO.getCredentialDocumentExtention());
	        licencesDto.setCredentialDocumentFileName(licencesVO.getCredentialDocumentFileName());
	        licencesDto.setCredentialDocumentFileSize(licencesVO.getCredentialDocumentFileSize());
	        licencesDto.setCredentialDocumentId(licencesVO.getCurrentDocumentID());
			
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
		}
		return licencesDto;
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

}
