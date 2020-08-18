package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.provider.IInsurancePolicyBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.web.delegates.provider.IInsuranceDelegate;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.web.utils.InsuranceListMapper;
import com.newco.marketplace.web.utils.InsuranceMapper;
import com.opensymphony.xwork2.ActionContext;
/**
 * @author LENOVO USER
 * 
 */
public class InsuranceDelegateImpl implements IInsuranceDelegate {

	  private IInsurancePolicyBO iInsurancePolicyBO;
//	    private InsuranceInfoDto insuranceInfoDto;
	    private InsuranceMapper insuranceMapper;
//	    private VendorHdr vendorHdr;
	    private InsuranceListMapper insuranceListMapper;
//	    private InsuranceTypesVO insuranceTypesVO;
	    private static final Logger localLogger = Logger
		.getLogger(InsuranceTypeDelegateImpl.class.getName());

		
	    public InsuranceDelegateImpl(IInsurancePolicyBO iInsurancePolicyBO,
	    		InsuranceMapper insuranceMapper, 
	    		InsuranceListMapper insuranceListMapper) {
			this.iInsurancePolicyBO = iInsurancePolicyBO;
			this.insuranceMapper = insuranceMapper;
//			this.vendorHdr = vendorHdr;
			this.insuranceListMapper = insuranceListMapper;
//			this.insuranceTypesVO = insuranceTypesVO;
		}
		/**
		 *  Gets the insuranceInfo attribute of the RegistrationRequestDispatcher object
		 *
		 * @param  iReq                          Description of the Parameter
		 * @return                               The insuranceInfo value
		 * @exception  BusinessServiceException  Description of the Exception
		 * @exception  DataServiceException      Description of the Exception
		 */
		public InsuranceInfoDto getInsuranceInfo(InsuranceInfoDto insuranceInfoDto) throws DelegateException {
			VendorHdr vendorHdr = new VendorHdr();
			InsuranceTypesVO insuranceVO = new InsuranceTypesVO();
			CredentialProfile cProfile = new CredentialProfile();
            insuranceVO.setVendorId(insuranceInfoDto.getVendorId());
            cProfile.setVendorId(insuranceInfoDto.getVendorId());
			vendorHdr = insuranceMapper.convertDTOtoVO(insuranceInfoDto,vendorHdr);
			try {
				vendorHdr = iInsurancePolicyBO.getInsuranceTypesInformation(vendorHdr);
				InsuranceTypesVO insuranceTypesVO = iInsurancePolicyBO.getInsuranceType(insuranceVO);
				cProfile=iInsurancePolicyBO.getAdditionalInsuranceList(cProfile);
                insuranceInfoDto = insuranceMapper.convertVOtoDTO(vendorHdr, insuranceInfoDto,insuranceTypesVO,cProfile);
			} catch (BusinessServiceException ex) {
				localLogger
				.info("Business Service Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"Business Service @InsuranceDelegateImpl.loadRegistration() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		localLogger
				.info("General Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"General Exception @InsuranceDelegateImpl.loadRegistration() due to "
						+ ex.getMessage());

	}
			return insuranceInfoDto;
		}
		public boolean saveInsuranceInfo(InsuranceInfoDto insuranceInfoDto) throws DelegateException {
			boolean saveInsurance = false;
			VendorHdr vendorHdr = new VendorHdr();
			vendorHdr = insuranceMapper.convertDTOtoVO(insuranceInfoDto, vendorHdr);
			SecurityContext securityContext = getSecurityContext();		
			try {
				int indicator=iInsurancePolicyBO.getEmailIndicator(insuranceInfoDto.getVendorId(),insuranceInfoDto.getWCI());
				saveInsurance = iInsurancePolicyBO.UpdateDbInsuranceSelection(vendorHdr);
				if(indicator==1){					
				iInsurancePolicyBO.sendWCINotRequiredAlert(securityContext);
				}
				//saveInsurance = iInsurancePolicyBO.UpdateDbInsuranceSelection(vendorHdr);
			} catch (BusinessServiceException ex) {
				localLogger
				.info("Business Service Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"Business Service @InsuranceDelegateImpl.loadRegistration() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		localLogger
				.info("General Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"General Exception @InsuranceDelegateImpl.loadRegistration() due to "
						+ ex.getMessage());

	}
			return saveInsurance;
		}
		
		@SuppressWarnings("unchecked")
		private SecurityContext getSecurityContext(){
			Map<String,Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityContext = null;
			if(sessionMap != null){
				securityContext = (SecurityContext)sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
			}
			return securityContext;
		}
		
		public InsuranceInfoDto getInsuranceType(InsuranceInfoDto insuranceInfoDto) throws DelegateException {
			InsuranceTypesVO insuranceTypesVO = new InsuranceTypesVO();
			insuranceTypesVO = insuranceListMapper.convertDTOtoVO(insuranceInfoDto, insuranceTypesVO);
			try {
				insuranceTypesVO = iInsurancePolicyBO.getInsuranceType(insuranceTypesVO);
				insuranceInfoDto = insuranceListMapper.convertVOtoDTO(insuranceTypesVO, insuranceInfoDto);
			} catch (BusinessServiceException ex) {
				localLogger
				.info("Business Service Exception @InsuranceDelegateImpl.getInsuranceType() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"Business Service @InsuranceDelegateImpl.getInsuranceType() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		localLogger
				.info("General Exception @InsuranceDelegateImpl.getInsuranceType() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"General Exception @InsuranceDelegateImpl.getInsuranceType() due to "
						+ ex.getMessage());

	}
			return insuranceInfoDto;
		}
		public String isFirstTimeVisit(Integer vendorId) throws DelegateException{
			String result="0";
			try {
				result = iInsurancePolicyBO.isFirstTimeVisit(vendorId);
				
			} catch (BusinessServiceException ex) {
				localLogger.info("Business Service Exception @InsuranceDelegateImpl.getInsuranceType() due to"
						+ ex.getMessage());
				throw new DelegateException(
				"Business Service @InsuranceDelegateImpl.getInsuranceType() due to "
						+ ex.getMessage());
			} catch (Exception ex) {
				localLogger.info("General Exception @InsuranceDelegateImpl.getInsuranceType() due to"
						+ ex.getMessage());
				throw new DelegateException(
				"General Exception @InsuranceDelegateImpl.getInsuranceType() due to "
						+ ex.getMessage());
			}
			return result;
		}
		public IInsurancePolicyBO getiInsurancePolicyBO() {
			return iInsurancePolicyBO;
		}
		public void setiInsurancePolicyBO(IInsurancePolicyBO insurancePolicyBO) {
			iInsurancePolicyBO = insurancePolicyBO;
		}
		
		public boolean removeInsuranceInfo(InsuranceInfoDto insuranceInfoDto)throws DelegateException
		{
			boolean saveInsurance = false;
			VendorHdr vendorHdr = new VendorHdr();
			vendorHdr.setVendorId(insuranceInfoDto.getVendorId());
			if(insuranceInfoDto.getWCI().equals("false"))
			{
				vendorHdr.setWCI(0);
			}
			if(StringUtils.isBlank(insuranceInfoDto.getWCIAmount()))
			{
				vendorHdr.setWCIAmount("0.0");
			}
			SecurityContext securityContext = getSecurityContext();		
			try {
				int indicator=iInsurancePolicyBO.getEmailIndicator(insuranceInfoDto.getVendorId(),insuranceInfoDto.getWCI());
				saveInsurance = iInsurancePolicyBO.UpdateDbInsuranceSelection(vendorHdr);
				if(indicator==1){					
				iInsurancePolicyBO.sendWCINotRequiredAlert(securityContext);
				}
				//saveInsurance = iInsurancePolicyBO.UpdateDbInsuranceSelection(vendorHdr);
			} catch (BusinessServiceException ex) {
				localLogger
				.info("Business Service Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"Business Service @InsuranceDelegateImpl.loadRegistration() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		localLogger
				.info("General Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ ex.getMessage());
		throw new DelegateException(
				"General Exception @InsuranceDelegateImpl.loadRegistration() due to "
						+ ex.getMessage());

	}
			return saveInsurance;
		}
		
}
