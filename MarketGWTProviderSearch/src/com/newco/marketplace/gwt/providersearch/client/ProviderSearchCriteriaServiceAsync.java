package com.newco.marketplace.gwt.providersearch.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProviderSearchCriteriaServiceAsync {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	
	// get the verticals as SkillNode types
	public void getVerticals(AsyncCallback callback);
	
	public void setDTOinSession(GwtFindProvidersDTO avo, AsyncCallback callback);
	
	public void getDTOFromSession(AsyncCallback callback);
	
	// get the verticals as SkillNode types
	public void getCatagories(SimpleProviderSearchSkillNodeVO inSkillNodeVO, AsyncCallback callback);
	
	// get the verticals as SkillNode types
	public void getSubCatagories(SimpleProviderSearchSkillNodeVO inSkillNodeVO, AsyncCallback callback);
	
	// get the skill types, could be by vertical or sub if we want
	public void getSkillTypes(SimpleProviderSearchSkillNodeVO inSkillNodeVO, AsyncCallback callback);

	public void getProviderResults(SimpleProviderSearchCriteraVO inSearchVO, AsyncCallback callback);
	
	public void getAllLanguages(AsyncCallback callback);

	public void execute(AsyncCallback callback);
	
	
}
