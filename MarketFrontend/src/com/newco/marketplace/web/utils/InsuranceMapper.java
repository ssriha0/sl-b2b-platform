/**
 * 
 */
package com.newco.marketplace.web.utils;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.vo.provider.VendorHdr;


/**
 * @author LENOVO USER
 * 
 */
public class InsuranceMapper extends ObjectMapper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public VendorHdr convertDTOtoVO(Object objectDto, Object objectVO) {
		InsuranceInfoDto insuranceDto = (InsuranceInfoDto) objectDto;
		VendorHdr vendorHdr = (VendorHdr) objectVO;
		
		if(insuranceDto.getVLI() != null  
	        	&& (insuranceDto.getVLI().equalsIgnoreCase("true") || insuranceDto.getVLI().equalsIgnoreCase("1")))
				vendorHdr.setVLI(1);
	    else
	        	vendorHdr.setVLI(0);
		
		if(insuranceDto.getWCI() != null 
	        	&& (insuranceDto.getWCI().equalsIgnoreCase("true") || insuranceDto.getWCI().equalsIgnoreCase("1")))
			vendorHdr.setWCI(1);
	    else
	    		vendorHdr.setWCI(0);
		
		if(insuranceDto.getCBGLI() != null 
	        	&& (insuranceDto.getCBGLI().equalsIgnoreCase("true") || insuranceDto.getCBGLI().equalsIgnoreCase("1")))
			vendorHdr.setCBGLI(1);
	    else
	    	vendorHdr.setCBGLI(0);
		
		System.out.println("insuranceDto.getVendorId()----------------"+insuranceDto.getVendorId());
		vendorHdr.setVendorId(insuranceDto.getVendorId());		
		
		
		if(StringUtils.isBlank(insuranceDto.getVLIAmount())){
			vendorHdr.setVLIAmount("0.0");
		}else{
			vendorHdr.setVLIAmount(insuranceDto.getVLIAmount());
		}
		
		if(StringUtils.isBlank(insuranceDto.getWCIAmount())){
			vendorHdr.setWCIAmount("0.0");
		}else{
			vendorHdr.setWCIAmount(insuranceDto.getWCIAmount());			
		}
		
		if(StringUtils.isBlank(insuranceDto.getCBGLIAmount())){
			vendorHdr.setCBGLIAmount("0.0");
		}else{
			vendorHdr.setCBGLIAmount(insuranceDto.getCBGLIAmount());
		}
		
		return vendorHdr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public InsuranceInfoDto convertVOtoDTO(Object objectVO, Object objectDto,Object insuranceListObject, Object credentialProfile) {
        InsuranceInfoDto insuranceDto = (InsuranceInfoDto) objectDto;
        VendorHdr vendorHdr = (VendorHdr) objectVO;
        InsuranceTypesVO insuranceTypesVO = (InsuranceTypesVO) insuranceListObject;
        CredentialProfile cProfile=(CredentialProfile)credentialProfile;
        System.out.println("----------vendorHdr.getVLI()----"+vendorHdr.getVLI());
        System.out.println("----------vendorHdr.getWCI()----"+vendorHdr.getWCI());
        System.out.println("----------vendorHdr.getCBGLI()----"+vendorHdr.getCBGLI());
        System.out.println("----------vendorHdr.getVLIAmount()----"+vendorHdr.getVLIAmount());
        System.out.println("----------vendorHdr.getWCIAmount()----"+vendorHdr.getWCIAmount());
        System.out.println("----------vendorHdr.getCBGLIAmount()----"+vendorHdr.getCBGLIAmount());
        System.out.println("----------vendorHdr.getVendorId()----"+vendorHdr.getVendorId());
       
        if(vendorHdr.getVLI()!=null){
              insuranceDto.setVLI(vendorHdr.getVLI().toString());
        }

        if(vendorHdr.getWCI()!=null){
              insuranceDto.setWCI(vendorHdr.getWCI().toString());
        }

        if(vendorHdr.getCBGLI()!=null){
              insuranceDto.setCBGLI(vendorHdr.getCBGLI().toString());
        }
        if(cProfile.getAdditionalInsuranceList()!=null)
        {
        	insuranceDto.setAdditionalInsuranceList(cProfile.getAdditionalInsuranceList());
        	insuranceDto.setAdditionalInsuranceListSize(new Integer(cProfile.getAdditionalInsuranceList().size()));
        }
        else
        {
        	insuranceDto.setAdditionalInsuranceListSize(new Integer(0));
        }

        //insuranceDto.setVLI(vendorHdr.getVLI().toString());
        //insuranceDto.setWCI(vendorHdr.getWCI().toString());
        //insuranceDto.setCBGLI(vendorHdr.getCBGLI().toString());
        insuranceDto.setVLIAmount(vendorHdr.getVLIAmount());
        insuranceDto.setWCIAmount(vendorHdr.getWCIAmount());
        insuranceDto.setCBGLIAmount(vendorHdr.getCBGLIAmount());
        insuranceDto.setVendorId(vendorHdr.getVendorId());
       
        if(insuranceTypesVO.getInsuranceList() != null){
              insuranceDto.setInsuranceList(insuranceTypesVO.getInsuranceList());
        }
        return insuranceDto;
  }




	
}
