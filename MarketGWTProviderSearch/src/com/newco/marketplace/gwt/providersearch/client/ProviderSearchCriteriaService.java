package com.newco.marketplace.gwt.providersearch.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ProviderSearchCriteriaService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	
	// get the verticals as SkillNode types
	public GwtRemoteServiceResponse getVerticals();
	
	public GwtRemoteServiceResponse setDTOinSession(GwtFindProvidersDTO avo);
	
	public GwtRemoteServiceResponse getDTOFromSession();
	
	// get the verticals as SkillNode types
	public GwtRemoteServiceResponse getCatagories(SimpleProviderSearchSkillNodeVO inSkillNodeVO);
	
	// get the verticals as SkillNode types
	public GwtRemoteServiceResponse getSubCatagories(SimpleProviderSearchSkillNodeVO inSkillNodeVO);
	
	// get the skill types, could be by vertical or sub if we want
	public GwtRemoteServiceResponse getSkillTypes(SimpleProviderSearchSkillNodeVO inSkillNodeVO);

	public GwtRemoteServiceResponse getProviderResults(SimpleProviderSearchCriteraVO inSearchVO);
	
	public GwtRemoteServiceResponse getAllLanguages();

	public static class Util {
		private static ProviderSearchCriteriaServiceAsync instance;
		public static ProviderSearchCriteriaServiceAsync getInstance(){
			if (instance == null) {
				instance = (ProviderSearchCriteriaServiceAsync) GWT.create(ProviderSearchCriteriaService.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				//String x = GWT.getHostPageBaseURL();
				target.setServiceEntryPoint(GWT.getHostPageBaseURL() +  "/ProviderSearchCriteriaService");
				//target.setServiceEntryPoint( "http://localhost:8080/MarketFrontend/ProviderSearchCriteriaService");
				//target.setServiceEntryPoint( "http://localhost:8084/provsearch/ProviderSearchCriteriaService");
			}
			return instance;
		}
	}
	
	public String execute();
	
	
}
