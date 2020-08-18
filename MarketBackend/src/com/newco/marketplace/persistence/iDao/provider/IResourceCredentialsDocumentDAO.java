package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.ResourceCredentialsDocumentVO;

public interface IResourceCredentialsDocumentDAO {

	public Integer insert(
			ResourceCredentialsDocumentVO myResourceCredentialsDocument)
			throws DBException;

	public java.util.List getResourceCredentialDocuments(
			ResourceCredentialsDocumentVO myResourceCredentialsDocument)
			throws DBException;

	public ResourceCredentialsDocumentVO getMaxResourceCredentialDocument(
			ResourceCredentialsDocumentVO resourceCredentialsDocumentVo)
			throws DBException;

	public int deleteResourceCredentials(
			ResourceCredentialsDocumentVO resourceCredentialsDocumentVo)
			throws DBException;

	public int deleteResourceCredentialDocument(
			ResourceCredentialsDocumentVO resourceCredentialsDocumentVo)
			throws DBException;
	
	public ResourceCredentialsDocumentVO isVendorDocumentExists(
			ResourceCredentialsDocumentVO credentialsDocumentVO) 
			throws DBException;

	public void updateResourceCredentialDocument(
			ResourceCredentialsDocumentVO credentialsDocumentVO) 
			throws DBException; 
}
