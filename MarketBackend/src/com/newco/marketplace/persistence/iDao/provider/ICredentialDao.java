package com.newco.marketplace.persistence.iDao.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.CredentialProfile;

public interface ICredentialDao {
	public int update(CredentialProfile credentialProfile) throws DBException;
    public CredentialProfile query(CredentialProfile credentialProfile)throws DBException;
    public CredentialProfile insert(CredentialProfile credentialProfile)throws DBException;
    public List queryList(CredentialProfile credentialProfile)throws DBException;
    public int queryForCredTypeId(String str,CredentialProfile credentialProfile)throws DBException;
        // for finding the credentialTypeId
    public List queryList(String str,CredentialProfile credentialTypeIdProfile)throws DBException;
    public int queryForCredCategoryId(String str,CredentialProfile credentialProfile) throws DBException;
    public List queryForInsuranceType(CredentialProfile credentialProfile)throws DBException;
    public List queryList1(String str,CredentialProfile credentialProfile)throws DBException;
	public CredentialProfile query1(CredentialProfile credentialProfile)throws DBException;
	public CredentialProfile insertInsuranceTypes(CredentialProfile credentialProfile)throws DBException;
	public CredentialProfile queryInsurance(CredentialProfile credentialProfile)throws DBException;
    public int updateInsurance(CredentialProfile credentialProfile)throws DBException;
    public CredentialProfile queryInsuranceSec(CredentialProfile credentialProfile)throws DBException;
	
    //SL-21233: Document Retrieval Code Starts
	
    public List getVendorUploadedDocuments(CredentialProfile credentialProfile)throws DBException;
	
    //SL-21233: Document Retrieval Code Ends
	
    public void delete(CredentialProfile credentialProfile)throws DBException;
    public CredentialProfile queryInsuranceDetailsForSelectedDocument(CredentialProfile credentialProfile,Integer docId)throws DBException;
	public CredentialProfile queryAdditonalInsuranceDetailsForSelectedDocument(
			CredentialProfile cProfile, Integer docId)throws DBException;
	public CredentialProfile isInsuranceExist(CredentialProfile generalLiability) throws DBException;
}
