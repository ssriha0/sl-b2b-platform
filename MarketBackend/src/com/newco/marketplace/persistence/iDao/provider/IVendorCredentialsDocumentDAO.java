package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;

public interface IVendorCredentialsDocumentDAO{
	public void insert(VendorCredentialsDocumentVO myVendorCredentialsDocument )throws DBException;
	public java.util.List getVendorCredentialDocuments(VendorCredentialsDocumentVO myVendorCredentialsDocument )throws DBException;
	public VendorCredentialsDocumentVO getMaxVendorCredentialDocument(VendorCredentialsDocumentVO myVendorCredentialsDocument )throws DBException;
	public void removeCredentialVendorDocument(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException;
	public void insertVendorCredentialDocument(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException;
	public VendorCredentialsDocumentVO isVendorDocumentExists(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException;
	public VendorCredentialsDocumentVO getLastUploadedDocument(int vendorId) throws DBException; 
	public VendorCredentialsDocumentVO isVendorDocumentExistsForInsurance(VendorCredentialsDocumentVO credentialsDocumentVO) throws DBException;
	public void updateDocumentInLicenseCredentialDocument(LicensesAndCertVO licensesAndCertVO)throws DBException;
	public void updateDocumentInInsuranceCredentialDocument(VendorCredentialsDocumentVO vendorCredentialsDocumentVO)throws DBException;
}
