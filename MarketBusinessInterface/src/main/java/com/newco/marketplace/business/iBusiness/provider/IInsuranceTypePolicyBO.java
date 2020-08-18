package com.newco.marketplace.business.iBusiness.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.CredentialProfile;

public interface IInsuranceTypePolicyBO {
	
	public CredentialProfile loadInsuranceDetails(
			CredentialProfile credentialProfile)
			throws BusinessServiceException;
	
	public List getInsuranceTypeList(CredentialProfile cProfile)
	throws BusinessServiceException;
	
	public List getStateList()
	throws BusinessServiceException;
	
	public CredentialProfile saveInsurance(
			CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException;
	
	public void auditVendorCredentials(int vendorId, int docId, int credentialId);
	
	public void auditVendorCredentialsInsurance(int vendorId, int docId, int credentialId);
	
	public void deleteInsurance(
			CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException ;
	
	public CredentialProfile viewDocument(
			CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException ;
	public void removeDocument(CredentialProfile credentialProfile)
			throws BusinessServiceException, FileNotFoundException,
			IOException, ParseException;
	public CredentialProfile loadInsuranceDetailsWithMaxVendorDocument(CredentialProfile cProfile,String buttonType)
		throws BusinessServiceException ;
		
		
	//SL-21233: Document Retrieval Code Starts
	
	public ArrayList<CredentialProfile> loadInsuranceDetailsWithVendorDocuments(CredentialProfile cProfile,String buttonType)
			throws BusinessServiceException ;
			
	//SL-21233: Document Retrieval Code Ends
	
	
	public CredentialProfile loadInsuranceDetailsForSelectedDocument(CredentialProfile cProfile,Integer docId)
	throws BusinessServiceException ;

	public CredentialProfile loadAdditonalInsuranceDetailsForSelectedDocument(
			CredentialProfile cProfile, Integer docId)throws BusinessServiceException;
	
}
