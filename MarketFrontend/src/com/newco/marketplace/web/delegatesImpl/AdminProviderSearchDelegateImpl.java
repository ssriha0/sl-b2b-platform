package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.providerSearch.SearchResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.AdminProviderSearchDelegate;
import com.newco.marketplace.web.dto.AdminSearchFormDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsAllDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsDTO;
import com.newco.marketplace.web.utils.SLStringUtils;

/**
 * $Revision: 1.27 $ $Author: glacy $ $Date: 2008/04/26 01:13:49 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class AdminProviderSearchDelegateImpl implements AdminProviderSearchDelegate{

    private static final Logger logger = Logger.getLogger("AdminProviderSearchDelegateImpl");
	private IProviderSearchBO providerSearchBO;

	public AdminSearchResultsAllDTO getProviderSearchResults(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap){
		logger.debug("Entering getProviderSearchResults in AdminProviderSearchDelegateImpl.");
		ProviderResultForAdminVO criteria = new ProviderResultForAdminVO();
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBusinessName())){
			criteria.setBusinessName(null);
		}else{
			criteria.setBusinessName(searchCriteria.getBusinessName());
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getProviderFirmStatus()) ||
				searchCriteria.getProviderFirmStatus().trim().equals("-1")){
			criteria.setProviderStatusId(null);
		}else{
			criteria.setProviderStatusId(Integer.valueOf(searchCriteria.getProviderFirmStatus()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getCity()))
			{
			criteria.setCity(null);
		}else{
			criteria.setCity(searchCriteria.getCity());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getEmail()))
		{
			criteria.setEmail(null);
		}else{
			criteria.setEmail(searchCriteria.getEmail());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getOrderNumber())){
			criteria.setSoId(null);
		}else{
			criteria.setSoId(searchCriteria.getOrderNumber());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getPhone())){
			criteria.setPhone(null);
		}else{
			criteria.setPhone(searchCriteria.getPhone());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getState1()) ||
				searchCriteria.getState1().trim().equals("-1")
			)
		{
			criteria.setState(null);
		}else
		{
			criteria.setState(searchCriteria.getState1());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getUsername()))
		{
			criteria.setUsername(null);
		}else{
			criteria.setUsername(searchCriteria.getUsername());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getZipPart1()))
		{
			criteria.setZip(null);
		}else{
			criteria.setZip(searchCriteria.getZipPart1());
		}

		
		Integer id = null;
		if(SLStringUtils.isNullOrEmpty(searchCriteria.getCompanyId()) == false)
		{
			try
			{
				id = Integer.parseInt(searchCriteria.getCompanyId());
			}
			catch(Exception a_Ex)
			{
				id = 0;
			}
		}
		criteria.setVendorId(id);
		id = null;
		if(SLStringUtils.isNullOrEmpty(searchCriteria.getUserId()) == false)
		{
			try
			{
				id = Integer.parseInt(searchCriteria.getUserId());
			}catch(Exception a_Ex)
			{
				id = 0;
			}
		}
		criteria.setResourceId(id);
		
		List<ProviderResultForAdminVO> providers = new ArrayList<ProviderResultForAdminVO>();
		AdminSearchResultsAllDTO allResults = new AdminSearchResultsAllDTO();
		try {
			providers = providerSearchBO.getProviderListForAdmin(criteria, criteriaMap);
			logger.debug("Got the list of providers.");
			// Map the returned VO to the DTO
			List<AdminSearchResultsDTO> results = new ArrayList<AdminSearchResultsDTO>();
			logger.debug("About to map VO to the DTO lists.");
			
			for (ProviderResultForAdminVO provider : providers) {
				AdminSearchResultsDTO result = new AdminSearchResultsDTO();
				result.setId(provider.getVendorId().toString());
				result.setStatus(provider.getVendorStatus());
				result.setName(provider.getBusinessName());
				result.setPrimaryIndustry(provider.getPrimaryIndustry());
				result.setMarket(provider.getMarketName());
				result.setState(provider.getState());
				result.setZip(provider.getZip());
				result.setLastActivityDate(provider.getLastActivityDate());
				result.setCompanyCity(provider.getCompanyCity());
				result.setCompanyState(provider.getCompanyState());
				results.add(result);
			}

			allResults.setTooManyRows(false);
			
			allResults.setListOfProviders(results);
			allResults.setNumberOfRows(providers.size());
			
			logger.debug("Completed mapping VO to DTO lists.  Returning results.");
		}catch(BusinessServiceException bse){
			logger.error("Could not retrieve a list of matching providers - database error. ", bse);
			bse.printStackTrace();	
		}
		
		return allResults;
	}

	public AdminSearchResultsAllDTO getProviderSearchResultsBySkill(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap){
		logger.debug("----Start of getProviderSearchResultsBySkill in AdminProviderSearchDelegateImpl----");
		ProviderResultForAdminVO criteria = new ProviderResultForAdminVO();
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getMarket()) ||
				searchCriteria.getMarket().trim().equals("-1")){
			criteria.setMarketId(null);
		}else{
			criteria.setMarketId(Integer.valueOf(searchCriteria.getMarket()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getState2()) ||
				searchCriteria.getState2().trim().equals("-1")
			)
		{
			criteria.setState(null);
		}else
		{
			criteria.setState(searchCriteria.getState2());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getZipPart2()))
		{
			criteria.setZip(null);
		}else{
			criteria.setZip(searchCriteria.getZipPart2());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getDistrict()) ||
				searchCriteria.getDistrict().trim().equals("-1")){
			criteria.setDistrict(null);
		}else{
			criteria.setDistrict(Integer.valueOf(searchCriteria.getDistrict()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getRegion()) ||
				searchCriteria.getRegion().trim().equals("-1")){
			criteria.setRegion(null);
		}else{
			criteria.setRegion(Integer.valueOf(searchCriteria.getRegion()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getPrimarySkill()) ||
				searchCriteria.getPrimarySkill().trim().equals("-1")){
			criteria.setResourcePrimarySkill(null);
		}else{
			criteria.setResourcePrimarySkill(Integer.valueOf(searchCriteria.getPrimarySkill()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getAuditableItems()) ||
				searchCriteria.getAuditableItems().trim().equals("-1")){
			criteria.setAuditable(null);
		}else{
			criteria.setAuditable(Integer.valueOf(searchCriteria.getAuditableItems()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBackgroundCheckStatus()) ||
				searchCriteria.getBackgroundCheckStatus().trim().equals("-1")){
			criteria.setResourceBackgroundStatusId(null);
		}else{
			criteria.setResourceBackgroundStatusId(Integer.valueOf(searchCriteria.getBackgroundCheckStatus()));
		}
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getSelectProviderNetwork()) ||
				searchCriteria.getSelectProviderNetwork().trim().equals("-1")){
			criteria.setSelectProviderNetworkId(null);
		}else{
			criteria.setSelectProviderNetworkId(Integer.valueOf(searchCriteria.getSelectProviderNetwork()));
		}
		
		
		List<ProviderResultForAdminVO> providers = new ArrayList<ProviderResultForAdminVO>();
		AdminSearchResultsAllDTO allResults = new AdminSearchResultsAllDTO();
		try {
			providers = providerSearchBO.getProviderListBySkillForAdmin(criteria, criteriaMap);
			// Map the returned VO to the DTO
			List<AdminSearchResultsDTO> results = new ArrayList<AdminSearchResultsDTO>();
			logger.debug("About to map VO to the DTO lists.");
			
			for (ProviderResultForAdminVO provider : providers) {
				AdminSearchResultsDTO result = new AdminSearchResultsDTO();
				result.setId(provider.getVendorId().toString());
				result.setStatus(provider.getVendorStatus());
				result.setName(provider.getBusinessName());
				result.setPrimaryIndustry(provider.getPrimaryIndustry());
				result.setMarket(provider.getMarketName());
				result.setState(provider.getState());
				result.setZip(provider.getZip());
				result.setLastActivityDate(provider.getLastActivityDate());
				result.setCompanyCity(provider.getCompanyCity());
				result.setCompanyState(provider.getCompanyState());
				result.setResourceStatus("(" + provider.getResourceStatus() + ")");
				result.setMemberName("(" + provider.getMemberName() + ")");
				results.add(result);
			}

			allResults.setTooManyRows(false);
			allResults.setListOfProviders(results);
			allResults.setNumberOfRows(providers.size());
			
			logger.debug("Completed mapping VO to DTO lists.  Returning results.");
		}catch(BusinessServiceException bse){
			logger.error("Could not retrieve a list of matching providers - database error. ", bse);
			bse.printStackTrace();	
		}
		logger.info("----End of getProviderSearchResultsBySkill in AdminProviderSearchDelegateImpl----");
		return allResults;
	}
	
	public IProviderSearchBO getProviderSearchBO() {
		return providerSearchBO;
	}
	public void setProviderSearchBO(IProviderSearchBO providerSearchBO) {
		this.providerSearchBO = providerSearchBO;
	}
	
	public AdminSearchResultsAllDTO getSearchResultsForAdmin(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap){
		
		logger.debug("Entering getSearchResultsForAdmin in AdminProviderSearchDelegateImpl.");
		SearchResultForAdminVO criteria = new SearchResultForAdminVO();
		
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBusinessName())){
			criteria.setBusinessName(null);
		}else{
			criteria.setBusinessName("%" + searchCriteria.getBusinessName() + "%");
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getCity()))
			{
			criteria.setCity(null);
		}else{
			criteria.setCity(searchCriteria.getCity());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getEmail()))
		{
			criteria.setEmail(null);
		}else{
			criteria.setEmail(searchCriteria.getEmail());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getOrderNumber())){
			criteria.setSoId(null);
		}else{
			criteria.setSoId(searchCriteria.getOrderNumber());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getPhone())){
			criteria.setPhone(null);
		}else{
			criteria.setPhone(searchCriteria.getPhone());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getState2()) ||
				searchCriteria.getState2().trim().equals("-1")
			)
		{
			criteria.setState(null);
		}else
		{
			criteria.setState(searchCriteria.getState2());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getUsername()))
		{
			criteria.setUsrName(null);
		}else{
			criteria.setUsrName("%" + searchCriteria.getUsername() + "%");
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getZipPart1()))
		{
			criteria.setZip(null);
		}else{
			criteria.setZip(searchCriteria.getZipPart1());
		}

		
		Integer id = null;
		if(SLStringUtils.isNullOrEmpty(searchCriteria.getCompanyId()) == false)
		{
			try
			{
				id = Integer.parseInt(searchCriteria.getCompanyId());
			}catch(Exception a_Ex)
			{
				id = 0;
			}
		}
		criteria.setBuyerId(id);
		id = null;
		if(SLStringUtils.isNullOrEmpty(searchCriteria.getUserId()) == false)
		{
			try
			{
				id = Integer.parseInt(searchCriteria.getUserId());
			}
			catch(Exception a_Ex)
			{
				id = 0;
			}
		}
		criteria.setResourceId(id);
		
		
		List<SearchResultForAdminVO> providers = new ArrayList<SearchResultForAdminVO>();
		try {
			providers = providerSearchBO.getSearchListForAdmin(criteria, criteriaMap);
		}catch(BusinessServiceException bse){
			logger.error("Could not retrieve a list of matching providers - database error. ", bse);
			bse.printStackTrace();	
		}
		logger.info("Got the list of providers.");
		// Map the returned VO to the DTO
		List<AdminSearchResultsDTO> results = new ArrayList<AdminSearchResultsDTO>();
		logger.debug("About to map VO to the DTO lists.");
		for (int i = 0; i < providers.size(); i++){
			SearchResultForAdminVO provider = providers.get(i);
			AdminSearchResultsDTO result = new AdminSearchResultsDTO();
			result.setId(provider.getBuyerId().toString());
			result.setCity(provider.getCity());
			result.setName(provider.getBusinessName());
			result.setState(provider.getState());
			result.setStatus(provider.getVendorStatus());
			result.setZip(provider.getZip());
			result.setUserName(provider.getUsrName());
			result.setBuyerName(provider.getName());
			result.setPhone(provider.getPhone());
			result.setResourceId(provider.getResourceId() + "");
			results.add(result);
		}
		AdminSearchResultsAllDTO allResults = new AdminSearchResultsAllDTO();

		allResults.setTooManyRows(false);
		allResults.setListOfProviders(results);
		allResults.setNumberOfRows(providers.size());
		
		
		logger.debug("Completed mapping VO to DTO lists.  Returning results.");
		return allResults;
	}

	public ProviderResultForAdminVO getProviderAdmin(Integer vendorId) {
		ProviderResultForAdminVO toReturn = null;
		try {
			toReturn = providerSearchBO.getProviderAdmin(vendorId);
		} catch(BusinessServiceException bse){
			logger.error("Could not retrieve a list of matching providers - database error. ", bse);
			bse.printStackTrace();	
		}
		return toReturn;
	}
	
	
}
/*
 * Maintenance History
 * $Log: AdminProviderSearchDelegateImpl.java,v $
 * Revision 1.27  2008/04/26 01:13:49  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.25.6.1  2008/04/23 11:41:36  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.26  2008/04/23 05:19:42  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.25  2008/02/26 18:18:13  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.24.2.1  2008/02/21 21:58:40  mhaye05
 * added logic to all for the display of tech name and status on the service live admin provider search pages
 *
 * Revision 1.24  2008/02/15 17:18:05  mhaye05
 * updated sl admin search to ensure that the company city and state are displayed
 *
 * Revision 1.23  2008/02/12 22:08:27  usawant
 * Moved the Provider Firm Status field from skill search to company search
 *
 * Revision 1.22  2008/02/12 17:33:20  mhaye05
 * removed limit and fixed city state display
 *
 * Revision 1.21  2008/02/04 19:52:17  usawant
 * Added functionality for clear button and on click of skill/company radio selection logic.
 *
 * Revision 1.20  2008/02/04 19:23:52  mhaye05
 * returning java.util.Date for lastActivityDate so it can be formatted on the JSP page
 *
 * Revision 1.19  2008/02/03 03:26:25  usawant
 * Story 22600: Added skillset search logic
 *
 * Revision 1.18  2008/02/01 21:59:47  mhaye05
 * update to make call to TimeUtils.getAgeOfOrder to convert last activity date to days and hours format
 *
 * Revision 1.17  2008/02/01 17:48:29  mhaye05
 * updated to use new provider search view
 *
 */