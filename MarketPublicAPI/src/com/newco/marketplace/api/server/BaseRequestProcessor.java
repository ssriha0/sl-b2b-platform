package com.newco.marketplace.api.server;

import com.newco.marketplace.api.services.D2CProviderPortalService.D2CFirmDetailsServiceAPI;
import com.newco.marketplace.api.services.search.AddProviderReviewService;
import com.newco.marketplace.api.services.search.ProviderCountService;
import com.newco.marketplace.api.services.search.ProviderReviewsService;
import com.newco.marketplace.api.services.search.SOSearchTemplateService;
import com.newco.marketplace.api.services.search.SearchFirmsService;
import com.newco.marketplace.api.services.search.SearchProviderByIdService;
import com.newco.marketplace.api.services.search.SearchProviderByZipCodeService;
import com.newco.marketplace.api.services.search.SearchProviderByskillTreeService;
import com.newco.marketplace.api.services.search.SearchProvidersByZipService;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.validators.ResponseValidator;

public abstract class BaseRequestProcessor {
	protected ResponseValidator responseValidator;
	protected SearchProviderByZipCodeService zipCodeService;
	protected SearchProviderByIdService providerByIdService;
	protected ProviderCountService providerCountService;
	protected SearchProviderByskillTreeService providerByskillTreeService;
	protected SearchProviderByZipCodeService mockZipCodeService;
	protected SearchProviderByIdService mockProviderByIdService;
	protected SearchProviderByskillTreeService mockProviderByskillTreeService;
	protected ProviderReviewsService providerReviewsService;
	protected CommonUtility commonUtility;
	protected AddProviderReviewService addProviderReviewService;
	protected SOSearchTemplateService soSearchTemplateService;
	protected SearchFirmsService searchFirmsService;
	//R 16_2_0_1: SL-21376- Search provider firms by zip and category and skill, SKU
	protected SearchProvidersByZipService searchProvidersService;
	
	// Search provider firms by search criteria: buyer = 777
	protected D2CFirmDetailsServiceAPI d2CFirmDetailsServiceAPI;
	
	public D2CFirmDetailsServiceAPI getD2CFirmDetailsServiceAPI() {
		return d2CFirmDetailsServiceAPI;
	}

	public void setD2CFirmDetailsServiceAPI(D2CFirmDetailsServiceAPI d2cFirmDetailsServiceAPI) {
		d2CFirmDetailsServiceAPI = d2cFirmDetailsServiceAPI;
	}
	
	public ResponseValidator getResponseValidator() {
		return responseValidator;
	}
	public void setResponseValidator(ResponseValidator responseValidator) {
		this.responseValidator = responseValidator;
	}
	public SearchProviderByZipCodeService getZipCodeService() {
		return zipCodeService;
	}
	public void setZipCodeService(SearchProviderByZipCodeService zipCodeService) {
		this.zipCodeService = zipCodeService;
	}
	public SearchProviderByIdService getProviderByIdService() {
		return providerByIdService;
	}
	public void setProviderByIdService(SearchProviderByIdService providerByIdService) {
		this.providerByIdService = providerByIdService;
	}
	public ProviderReviewsService getProviderReviewsService() {
		return providerReviewsService;
	}
	public void setProviderReviewsService(
			ProviderReviewsService providerReviewsService) {
		this.providerReviewsService = providerReviewsService;
	}
	public CommonUtility getCommonUtility() {
		return commonUtility;
	}
	public void setCommonUtility(CommonUtility commonUtility) {
		this.commonUtility = commonUtility;
	}
	public AddProviderReviewService getAddProviderReviewService() {
		return addProviderReviewService;
	}
	public void setAddProviderReviewService(
			AddProviderReviewService addProviderReviewService) {
		this.addProviderReviewService = addProviderReviewService;
	}
	public SOSearchTemplateService getSoSearchTemplateService() {
		return soSearchTemplateService;
	}
	public void setSoSearchTemplateService(
			SOSearchTemplateService soSearchTemplateService) {
		this.soSearchTemplateService = soSearchTemplateService;
	}
	public SearchProviderByskillTreeService getProviderByskillTreeService() {
		return providerByskillTreeService;
	}
	public void setProviderByskillTreeService(
			SearchProviderByskillTreeService providerByskillTreeService) {
		this.providerByskillTreeService = providerByskillTreeService;
	}
	public SearchProviderByZipCodeService getMockZipCodeService() {
		return mockZipCodeService;
	}
	public void setMockZipCodeService(
			SearchProviderByZipCodeService mockZipCodeService) {
		this.mockZipCodeService = mockZipCodeService;
	}
	public SearchProviderByIdService getMockProviderByIdService() {
		return mockProviderByIdService;
	}
	public void setMockProviderByIdService(
			SearchProviderByIdService mockProviderByIdService) {
		this.mockProviderByIdService = mockProviderByIdService;
	}
	public SearchProviderByskillTreeService getMockProviderByskillTreeService() {
		return mockProviderByskillTreeService;
	}
	public void setMockProviderByskillTreeService(
			SearchProviderByskillTreeService mockProviderByskillTreeService) {
		this.mockProviderByskillTreeService = mockProviderByskillTreeService;
	}
	public ProviderCountService getProviderCountService() {
		return providerCountService;
	}
	public void setProviderCountService(ProviderCountService providerCountService) {
		this.providerCountService = providerCountService;
	}
	public SearchFirmsService getSearchFirmsService() {
		return searchFirmsService;
	}
	public void setSearchFirmsService(SearchFirmsService searchFirmsService) {
		this.searchFirmsService = searchFirmsService;
	}
	public SearchProvidersByZipService getSearchProvidersService() {
		return searchProvidersService;
	}
	public void setSearchProvidersService(
			SearchProvidersByZipService searchProvidersService) {
		this.searchProvidersService = searchProvidersService;
	}
}
