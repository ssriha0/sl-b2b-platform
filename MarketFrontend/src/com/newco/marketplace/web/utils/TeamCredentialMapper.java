package com.newco.marketplace.web.utils;




import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.web.dto.provider.TeamCredentialsDto;

public class TeamCredentialMapper extends ObjectMapper{

	public Object convertDTOtoVO(Object objectDto, Object objectVO) {
		TeamCredentialsDto teamCredentialsDto = (TeamCredentialsDto) objectDto;
		TeamCredentialsVO teamCredentialsVO=null;
		VendorResource vendorResource=null;
		TeamCredentialsLookupVO teamCredentialsLookupVO=null;
		
		if(objectVO instanceof TeamCredentialsVO){
			teamCredentialsVO = (TeamCredentialsVO) objectVO;
			
			/**
			 * Added for Document Upload.
			 */
			teamCredentialsVO.setCredentialDocumentBytes(teamCredentialsDto.getCredentialDocumentBytes());
			teamCredentialsVO.setCredentialDocumentExtention(teamCredentialsDto.getCredentialDocumentExtention());
			teamCredentialsVO.setCredentialDocumentFileName(teamCredentialsDto.getCredentialDocumentFileName());
			teamCredentialsVO.setCredentialDocumentFileSize(teamCredentialsDto.getCredentialDocumentFileSize());
			teamCredentialsVO.setCredentialDocumentId(teamCredentialsDto.getCredentialDocumentId());
			
			teamCredentialsVO.setVendorId(teamCredentialsDto.getVendorId());
			
			teamCredentialsVO.setResourceId(teamCredentialsDto.getResourceId());
			teamCredentialsVO.setIsFileUploaded(teamCredentialsDto.getIsFileUploaded());
			teamCredentialsVO.setResourceCredId(teamCredentialsDto.getResourceCredId());
			String format="yyyy-MM-dd";
			teamCredentialsVO.setTypeId(teamCredentialsDto.getTypeId());
			teamCredentialsVO.setCategoryId(teamCredentialsDto.getCategoryId());
			teamCredentialsVO.setIssuerName(teamCredentialsDto.getIssuerName());
			teamCredentialsVO.setLicenseName(teamCredentialsDto.getLicenseName());
			teamCredentialsVO.setCity(teamCredentialsDto.getCity());
			if(teamCredentialsDto.getState()!=null&&!"".equals(teamCredentialsDto.getState())){
				teamCredentialsVO.setState(teamCredentialsDto.getState());
			}
			
			teamCredentialsVO.setCounty(teamCredentialsDto.getCounty());
			teamCredentialsVO.setResourceCredId(teamCredentialsDto.getResourceCredId());
			teamCredentialsVO.setCredentialNumber(teamCredentialsDto.getCredentialNumber());
            if (teamCredentialsDto.getCredentialDocument() != null) {
                teamCredentialsVO.setCredentialDocumentId(teamCredentialsDto.getCredentialDocumentId());
            }
            
            System.out.println("issue date in the mapper *******"+teamCredentialsDto.getIssueDate());
            try {
            	if(teamCredentialsDto.getIssueDate()!=null&&teamCredentialsDto.getIssueDate().trim().length()>0){
            		teamCredentialsVO.setIssueDate((convertDate(teamCredentialsDto.getIssueDate(), format)));
            	}
                if (teamCredentialsDto.getExpirationDate() != null && teamCredentialsDto.getExpirationDate().length() > 1){
                	teamCredentialsVO.setExpirationDate((convertDate(teamCredentialsDto.getExpirationDate(), format)));
                }
            }catch(Exception ex){
            	//ex.printStackTrace();
            }
			return teamCredentialsVO;
		}
		if(objectVO instanceof VendorResource){
			vendorResource = (VendorResource) objectVO;
			vendorResource.setResourceId(teamCredentialsDto.getResourceId());
			vendorResource.setNoCredInd(teamCredentialsDto.isPassCredentials());
			return vendorResource;
		}
		if(objectVO instanceof TeamCredentialsLookupVO){
			teamCredentialsLookupVO = (TeamCredentialsLookupVO) objectVO;
			teamCredentialsLookupVO.setTypeId(teamCredentialsDto.getTypeId());
			return teamCredentialsLookupVO;
		}
		return null;
	}

	public TeamCredentialsDto convertVOtoDTO(Object objectVO, Object objectDto) {
		TeamCredentialsDto teamCredentialsDto = (TeamCredentialsDto) objectDto;
		TeamCredentialsVO teamCredentialsVO=null;
		if(objectVO!=null&&objectVO instanceof TeamCredentialsVO){
			teamCredentialsVO = (TeamCredentialsVO) objectVO;
			teamCredentialsDto.setVendorId(teamCredentialsVO.getVendorId());
			teamCredentialsDto.setResourceCredId(teamCredentialsVO.getResourceCredId());
            teamCredentialsDto.setTypeId(teamCredentialsVO.getTypeId());
            teamCredentialsDto.setCategoryId(teamCredentialsVO.getCategoryId());
            teamCredentialsDto.setIssuerName(teamCredentialsVO.getIssuerName());
            teamCredentialsDto.setLicenseName(teamCredentialsVO.getLicenseName());
            teamCredentialsDto.setState(teamCredentialsVO.getState());
            teamCredentialsDto.setCounty(teamCredentialsVO.getCounty());
            teamCredentialsDto.setCity(teamCredentialsVO.getCity());
            teamCredentialsDto.setCredentialNumber(teamCredentialsVO.getCredentialNumber());
            if(teamCredentialsVO.getIssueDate()!=null){
            	teamCredentialsDto.setIssueDate(formatDate(teamCredentialsVO.getIssueDate()));
            }
            if(teamCredentialsVO.getExpirationDate()!=null){
            	teamCredentialsDto.setExpirationDate(formatDate(teamCredentialsVO.getExpirationDate()));
            }
            teamCredentialsDto.setIsFileUploaded(teamCredentialsVO.getIsFileUploaded());
            
            /**
             * Document related details
             */
            teamCredentialsDto.setCredentialDocumentId(teamCredentialsVO.getCredentialDocumentId());
            teamCredentialsDto.setCredentialDocumentFileName(teamCredentialsVO.getCredentialDocumentFileName());
            teamCredentialsDto.setCredentialDocumentFileSize(teamCredentialsVO.getCredentialDocumentFileSize());
            
            teamCredentialsDto.setCredentialDocumentId(teamCredentialsVO.getCredentialDocumentId());
		}
		return teamCredentialsDto;
	}
	
	public TeamCredentialsDto convertVOtoDTO4Display(Object objectVO, Object objectDto) {
		TeamCredentialsDto teamCredentialDTO = (objectDto != null) ? (TeamCredentialsDto) objectDto : null;
		TeamCredentialsVO teamVO = (objectVO != null) ? (TeamCredentialsVO) objectVO : null;
		
		try
		{
			if (teamCredentialDTO == null || teamVO == null)
				return null;
			
			teamCredentialDTO.setVendorId(teamVO.getVendorId());
			/**
	         * Code added for Document File Upload
	         */
	        teamCredentialDTO.setCredentialDocumentBytes(teamVO.getCredentialDocumentBytes());
	        teamCredentialDTO.setCredentialDocumentExtention(teamVO.getCredentialDocumentExtention());
	        teamCredentialDTO.setCredentialDocumentFileName(teamVO.getCredentialDocumentFileName());
	        teamCredentialDTO.setCredentialDocumentFileSize(teamVO.getCredentialDocumentFileSize());
	        teamCredentialDTO.setCredentialDocumentId(teamVO.getCredentialDocumentId());
			
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
		}
		return teamCredentialDTO;
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
