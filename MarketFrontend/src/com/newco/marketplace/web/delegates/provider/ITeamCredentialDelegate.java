package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.TeamCredentialsDto;

public interface ITeamCredentialDelegate {
	public TeamCredentialsDto getCredentialList(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;

	public void saveNoCred(TeamCredentialsDto teamCredentialsDto)
			throws DelegateException;

	public TeamCredentialsDto getCredentialDetails(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;

	public TeamCredentialsDto commitCredentialData(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;
	
	public TeamCredentialsDto deleteCredentialData(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;

	public TeamCredentialsDto getCatListByTypeId(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;
	
	public TeamCredentialsDto removeDocumentDetails(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;
	
	public TeamCredentialsDto viewDocumentDetails(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;
	public TeamCredentialsDto loadListValues(
			TeamCredentialsDto teamCredentialsDto) throws DelegateException;
}
