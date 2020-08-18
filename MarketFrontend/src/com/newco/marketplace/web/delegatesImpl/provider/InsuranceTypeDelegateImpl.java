package com.newco.marketplace.web.delegatesImpl.provider;



import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.web.dto.provider.AdditionalInsurancePolicyDTO;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.newco.marketplace.web.utils.AdditionalInsuranceTypeMapper;
import com.newco.marketplace.web.utils.InsuranceListMapper;
import com.newco.marketplace.web.utils.InsuranceTypeMapper;
import com.newco.marketplace.web.utils.InsuranceTypeSaveMapper;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:52 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class InsuranceTypeDelegateImpl implements IInsuranceTypeDelegate {

	private IInsuranceTypePolicyBO iInsurancePolicyBO;
	private InsuranceTypeMapper insuranceTypeMapper;
	private AdditionalInsuranceTypeMapper additionalInsuranceTypeMapper;
	
	private InsuranceListMapper insuranceListMapper;

	private static final Logger localLogger = Logger
	.getLogger(InsuranceTypeDelegateImpl.class.getName());

	/**
	 * @param loginBO
	 */
	public InsuranceTypeDelegateImpl(IInsuranceTypePolicyBO iInsurancePolicyBO,
			InsuranceTypeMapper insuranceTypeMapper) {
		this.iInsurancePolicyBO = iInsurancePolicyBO;
		this.insuranceTypeMapper = insuranceTypeMapper;
		
	}
	
	public InsurancePolicyDto loadInsuranceDetails(InsurancePolicyDto objLoginDto) throws DelegateException{
		
		try {
			
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
			
			objLoginDto.setInsuranceTypeList(iInsurancePolicyBO.getInsuranceTypeList(cProfile));

			cProfile=iInsurancePolicyBO.loadInsuranceDetails(cProfile);
			objLoginDto = insuranceTypeMapper.convertVOtoDTO(cProfile, objLoginDto);
			
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
		
		return objLoginDto;
	}
	
	
	/* 
	 * method to load Insurance Details and last updated Document for the vendor
	 * 
	 */
	public InsurancePolicyDto loadInsuranceDetailsWithMaxVendorDocument(InsurancePolicyDto objLoginDto,String buttonType) throws DelegateException{
			
			try {
				
				CredentialProfile cProfile = new CredentialProfile();
				cProfile = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
				
				objLoginDto.setInsuranceTypeList(iInsurancePolicyBO.getInsuranceTypeList(cProfile));

				cProfile=iInsurancePolicyBO.loadInsuranceDetailsWithMaxVendorDocument(cProfile,buttonType);
				objLoginDto = insuranceTypeMapper.convertVOtoDTO(cProfile, objLoginDto);
				
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
			
			return objLoginDto;
		}
	

	//SL-21233: Document Retrieval Code Starts
	/* 
	 * method to load Insurance Details and old Documents for the vendor
	 * 
	 */
	public ArrayList<InsurancePolicyDto> loadInsuranceDetailsWithVendorDocuments(InsurancePolicyDto objLoginDto,String buttonType) throws DelegateException{

		
		ArrayList<InsurancePolicyDto> objLoginDto1 = new ArrayList<InsurancePolicyDto>();
		
			try {
				
				ArrayList<CredentialProfile> cProfile1 = new ArrayList<CredentialProfile>();
				
				CredentialProfile cProfile2 = new CredentialProfile();
				
				
				
				CredentialProfile cProfile = new CredentialProfile();
				
				cProfile = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
				
				objLoginDto.setInsuranceTypeList(iInsurancePolicyBO.getInsuranceTypeList(cProfile));

				cProfile=iInsurancePolicyBO.loadInsuranceDetailsWithMaxVendorDocument(cProfile,buttonType);
				
				objLoginDto = insuranceTypeMapper.convertVOtoDTO(cProfile, objLoginDto);
				
				
				
				objLoginDto1.add(objLoginDto);
				
				if(OrderConstants.BUTTON_TYPE_EDIT.equals(buttonType)){
					
				cProfile2 = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
				
				cProfile1=iInsurancePolicyBO.loadInsuranceDetailsWithVendorDocuments(cProfile2,buttonType);
				
				
				for(int i=0; i<cProfile1.size(); i++){
				
				InsurancePolicyDto objLoginDto2 = new InsurancePolicyDto();
				
				objLoginDto2 = insuranceTypeMapper.convertVOtoDTO(cProfile1.get(i), objLoginDto2);
				
				objLoginDto1.add(objLoginDto2);
				}
				}
				
				
				
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
			
			return objLoginDto1;
		}

	//SL-21233: Document Retrieval Code Ends
	

	public InsurancePolicyDto saveInsuranceDetails(InsurancePolicyDto objLoginDto) throws DelegateException{
		
		try {
			InsuranceTypeSaveMapper insuranceTypeSaveMapper = new InsuranceTypeSaveMapper();
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeSaveMapper.convertDTOtoVO(objLoginDto, cProfile);
			cProfile=iInsurancePolicyBO.saveInsurance(cProfile);
			//iInsurancePolicyBO.auditVendorCredentials(cProfile.getVendorId(), cProfile.getCurrentDocumentID(), cProfile.getCredentialId());
			objLoginDto.setVendorCredentialId(cProfile.getCredentialId());
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.saveInsuranceDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.saveInsuranceDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.saveInsuranceDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.saveInsuranceDetails() due to "
							+ ex.getMessage());
		}
		
		return objLoginDto;
	}

	public InsurancePolicyDto deleteInsuranceDetails(InsurancePolicyDto objLoginDto) throws DelegateException{
		
		try {
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
			iInsurancePolicyBO.deleteInsurance(cProfile);
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.deleteInsuranceDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.deleteInsuranceDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.deleteInsuranceDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.deleteInsuranceDetails() due to "
							+ ex.getMessage());
		}
		
		return objLoginDto;
	}
	
	public InsurancePolicyDto removeDocumentDetails(InsurancePolicyDto objLoginDto) throws DelegateException{
		
		try {
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
			iInsurancePolicyBO.removeDocument(cProfile);
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.removeDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.removeDocumentDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.removeDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.removeDocumentDetails() due to "
							+ ex.getMessage());
		}
		return objLoginDto;
	}
	
	public InsurancePolicyDto viewDocumentDetails(InsurancePolicyDto objLoginDto) throws DelegateException{
		
		try {
			InsuranceTypeSaveMapper insuranceTypeSaveMapper = new InsuranceTypeSaveMapper();
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeSaveMapper.convertDTOtoVO(objLoginDto, cProfile);
			cProfile=iInsurancePolicyBO.viewDocument(cProfile);
			objLoginDto = insuranceTypeMapper.convertVOtoDTO4Display(cProfile, objLoginDto);
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.viewDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.viewDocumentDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.viewDocumentDetails() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.viewDocumentDetails() due to "
							+ ex.getMessage());
		}
		
		return objLoginDto;
	}
	
public InsurancePolicyDto saveInsurancePolicyDetails(InsurancePolicyDto objLoginDto) throws DelegateException{
		
		try {
			InsuranceTypeSaveMapper insuranceTypeSaveMapper = new InsuranceTypeSaveMapper();
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeSaveMapper.convertPolicyDetailsDTOtoVO(objLoginDto, cProfile);
			cProfile=iInsurancePolicyBO.saveInsurance(cProfile);
			//iInsurancePolicyBO.auditVendorCredentials(cProfile.getVendorId(), cProfile.getCurrentDocumentID(), cProfile.getCredentialId());
			objLoginDto.setVendorCredentialId(cProfile.getCredentialId()); 
		} catch (BusinessServiceException ex) {
			localLogger
					.warn("Business Service Exception @InsuranceDelegateImpl.saveInsurancePolicyDetails() due to",ex);
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.saveInsurancePolicyDetails() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.warn("General Exception @InsuranceDelegateImpl.saveInsurancePolicyDetails() due to"
							,ex);
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.saveInsurancePolicyDetails() due to "
							+ ex.getMessage());
		}
		
		return objLoginDto;
	}
	
/**
 * Method to fetch the credential details associated with the selected insurance certificate
 * @param InsurancePolicyDto objLoginDto
 * @param Integer docId
 * @return InsurancePolicyDto
 * @throws Exception
 */
	public InsurancePolicyDto loadInsuranceDetailsForSelectedDocument(InsurancePolicyDto objLoginDto,Integer docId) throws DelegateException{
		try {			
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = insuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);			
			cProfile=iInsurancePolicyBO.loadInsuranceDetailsForSelectedDocument(cProfile,docId);
			objLoginDto = insuranceTypeMapper.convertVOtoDTO(cProfile, objLoginDto);			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
			
		}		
		return objLoginDto;
	}
	
	/*Changes related to SL-20301: Details based on LastUploadedDocument*/
	public AdditionalInsurancePolicyDTO loadAdditonalInsuranceDetailsForSelectedDocument(AdditionalInsurancePolicyDTO objLoginDto,Integer docId) throws DelegateException{
		try {			
			additionalInsuranceTypeMapper = new AdditionalInsuranceTypeMapper();
			CredentialProfile cProfile = new CredentialProfile();
			cProfile = additionalInsuranceTypeMapper.convertDTOtoVO(objLoginDto, cProfile);
			cProfile=iInsurancePolicyBO.loadAdditonalInsuranceDetailsForSelectedDocument(cProfile,docId);
			objLoginDto = additionalInsuranceTypeMapper.convertVOtoDTO(cProfile, objLoginDto);
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @InsuranceDelegateImpl.loadInsuranceDetailsForSelectedDocument() due to "
							+ ex.getMessage());
			
		}		
		return objLoginDto;
	}
}





/*
 * Maintenance History
 * $Log: InsuranceTypeDelegateImpl.java,v $
 * Revision 1.8  2008/04/26 01:13:52  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.12.1  2008/04/23 11:41:43  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:19:44  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.6  2008/02/11 21:31:06  mhaye05
 * removed statesList attributes
 *
 */