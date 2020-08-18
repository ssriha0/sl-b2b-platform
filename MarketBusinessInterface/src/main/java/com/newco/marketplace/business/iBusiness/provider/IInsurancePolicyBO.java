package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.vo.provider.VendorHdr;
public interface IInsurancePolicyBO  { 
		
        public VendorHdr getInsuranceTypesInformation(
        		VendorHdr vHdr)throws BusinessServiceException;
        
        public boolean UpdateDbInsuranceSelection(VendorHdr vHdr)throws BusinessServiceException;
        
        public InsuranceTypesVO getInsuranceType(InsuranceTypesVO insuranceTypesVO)throws BusinessServiceException;
        public void sendWCINotRequiredAlert(SecurityContext security);
 		public int getEmailIndicator(Integer vendorId,String wci) throws BusinessServiceException;
 		public String isFirstTimeVisit(Integer vendorId) throws BusinessServiceException;
 		public CredentialProfile getAdditionalInsuranceList(CredentialProfile credProfile) throws BusinessServiceException;
}
