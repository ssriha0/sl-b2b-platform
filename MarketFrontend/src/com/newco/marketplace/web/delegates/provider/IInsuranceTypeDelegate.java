package com.newco.marketplace.web.delegates.provider;

import java.util.ArrayList;
import java.util.List;


import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.web.dto.provider.AdditionalInsurancePolicyDTO;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;

/**
 * @author LENOVO USER
 *
 */
public interface IInsuranceTypeDelegate {
	
	public InsurancePolicyDto loadInsuranceDetails(InsurancePolicyDto objLoginDto) throws DelegateException;
	public InsurancePolicyDto saveInsuranceDetails(InsurancePolicyDto objLoginDto) throws DelegateException;
	public InsurancePolicyDto saveInsurancePolicyDetails(InsurancePolicyDto objLoginDto) throws DelegateException;
	public InsurancePolicyDto deleteInsuranceDetails(InsurancePolicyDto objLoginDto) throws DelegateException;
	public InsurancePolicyDto removeDocumentDetails(InsurancePolicyDto objLoginDto) throws DelegateException;
	public InsurancePolicyDto viewDocumentDetails(InsurancePolicyDto objLoginDto) throws DelegateException;
	public InsurancePolicyDto loadInsuranceDetailsForSelectedDocument(InsurancePolicyDto objLoginDto,Integer docId) throws DelegateException;
	public InsurancePolicyDto loadInsuranceDetailsWithMaxVendorDocument(InsurancePolicyDto objLoginDto,String buttonType) throws DelegateException;
	
	
	//SL-21233: Document Retrieval Code Starts
	
	public ArrayList<InsurancePolicyDto> loadInsuranceDetailsWithVendorDocuments(InsurancePolicyDto objLoginDto,String buttonType) throws DelegateException;
	
	//SL-21233: Document Retrieval Code Ends
	
	
	public AdditionalInsurancePolicyDTO loadAdditonalInsuranceDetailsForSelectedDocument(AdditionalInsurancePolicyDTO additionalInsurancePolicyDTO,Integer docId) throws DelegateException;
	
	//public LoginDto converVoToDto(LoginVO loginVO, LoginDto loginDto);
	//public LoginVO converDtoToVo(LoginDto loginDto,LoginVO loginVO);
		
}
