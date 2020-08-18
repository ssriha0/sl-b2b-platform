/**
 * 
 */
package com.servicelive.spn.buyer.membermanagement;

import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.detached.MemberManageSearchResultVO;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.common.util.PropertyManagerUtil;
import com.servicelive.spn.constants.SPNActionConstants;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.buyer.SPNBuyerServices;
import com.servicelive.spn.services.membermanage.MemberManageSearchService;

/**
 * @author hoza
 *
 */
public class SPNMemberManageSearchTabAction extends SPNBaseAction implements Preparable, ModelDriven<SPNMemberManageSearchModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SPNMemberManageSearchModel model = new SPNMemberManageSearchModel();
	private  LookupService lookupService;
	private  SPNBuyerServices spnBuyerServices;
	private  MemberManageSearchService memberManageSearchService;
	private PropertyManagerUtil propertyManagerUtil;
	
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		model.setStateList(lookupService.findStatesNotBlackedOut());
		
		model.setUserName(getLoggedInUser().getUsername());
		if(getLoggedInSPNUser().getBuyer() != null)
			model.setBuyerId(getLoggedInSPNUser().getBuyer().getBuyerId());
		
	}
	
	public String display() {
		return SUCCESS;
	}
	
	public String searchMembersAjax() throws Exception
	{
		User loggedInUser = getLoggedInUser();
		
		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}
		
		Integer buyerId = loggedInUser.getUserId();
		
		MemberManageSearchCriteriaVO memberManageCriteriaVO = model.getSearchCriteriaVO();
		if(memberManageCriteriaVO.getMaxResultsDisplayNumber() == null ) {
			memberManageCriteriaVO.setMaxResultsDisplayNumber(getPropertyManagerUtil().getMemberManageSearchMaxResultCount());
		}
		memberManageCriteriaVO.setBuyerId(buyerId);
		model.setSpnList(spnBuyerServices.getSPNListForBuyer(buyerId));
		List<MemberManageSearchResultVO> results = new ArrayList<MemberManageSearchResultVO>(0);
		if(memberManageCriteriaVO.getSearchByType() != null  && !("-1".equalsIgnoreCase(memberManageCriteriaVO.getSearchByType())) ) {
			Integer searchType = Integer.valueOf(memberManageCriteriaVO.getSearchByType().trim());
			if(SPNActionConstants.MM_SEARCH_TYPE_PROVIDER_FIRM_ID.intValue() == searchType.intValue() || SPNActionConstants.MM_SEARCH_TYPE_PROVIDER_FIRM_NAME.intValue() == searchType.intValue()) {
				updateCriteriaWithIdList(memberManageCriteriaVO, searchType);
				results = memberManageSearchService.getMemberSearchForProviderFirms(memberManageCriteriaVO);
			}
			else if(SPNActionConstants.MM_SEARCH_TYPE_SERVICE_PROVIDER_ID.intValue() == searchType.intValue() || SPNActionConstants.MM_SEARCH_TYPE_SERVICE_PROVIDER_NAME.intValue() == searchType.intValue()) {
				updateCriteriaWithIdList(memberManageCriteriaVO, searchType);
				results = memberManageSearchService.getMemberSearchForServiceProviders(memberManageCriteriaVO);
			}
			else {
				//TODO define this in Ml or set as error or warning or whatever
				return "no_search_type";
			}
			
		}
		memberManageCriteriaVO.setTotalResultsCount(results.size());
		model.setSearchResultsList(getUpdateResultDisplay(memberManageCriteriaVO,results));
		return SUCCESS;
	}
	
	private List<MemberManageSearchResultVO>   getUpdateResultDisplay(MemberManageSearchCriteriaVO criteria,List<MemberManageSearchResultVO> results) {
		
		if(!criteria.getIsViewAll() && results.size() > criteria.getMaxResultsDisplayNumber()) {
			List<MemberManageSearchResultVO> newresults = new ArrayList<MemberManageSearchResultVO>(0);
			for(int i = 0 ; i < criteria.getMaxResultsDisplayNumber() ; i++) {
				newresults.add(results.get(i));
				
			}
			return newresults;
		}
		else {
			return results;
		}
	}

	/**
	 * @param memberManageCriteriaVO
	 * @param searchType
	 */
	private void updateCriteriaWithIdList(MemberManageSearchCriteriaVO memberManageCriteriaVO,Integer searchType) {
		String idString;
		if(searchType.intValue() == SPNActionConstants.MM_SEARCH_TYPE_SERVICE_PROVIDER_ID.intValue()){
			idString = memberManageCriteriaVO.getServiceProviderIdsStr();
			memberManageCriteriaVO.setServiceProviderIds(parseTheIdString(idString));}
		else if(searchType.intValue() == SPNActionConstants.MM_SEARCH_TYPE_PROVIDER_FIRM_ID.intValue()) {	
			idString = memberManageCriteriaVO.getProviderFirmIdsStr();
			memberManageCriteriaVO.setProviderFirmIds(parseTheIdString(idString));
		}
	}
	
	/**
	 * 
	 * @param idString
	 * @return
	 */
	private List <Integer> parseTheIdString(String idString) {
		List<Integer> ids = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(idString)){
			// Parse the string into individual IDs
			StringTokenizer tokenizer = new StringTokenizer(idString, ";");
			while(tokenizer.hasMoreTokens()) {
				String id = tokenizer.nextToken().trim();
				if(StringUtils.isNumeric(id)) { 
					ids.add(Integer.valueOf(id));
				}
			}
		}
		return ids;
	}

	

	/**
	 * @return the model
	 */
	public SPNMemberManageSearchModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(SPNMemberManageSearchModel model) {
		this.model = model;
	}

	/**
	 * @return the lookupService
	 */
	public LookupService getLookupService() {
		return lookupService;
	}

	/**
	 * @param lookupService the lookupService to set
	 */
	public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}

	/**
	 * @return the spnBuyerServices
	 */
	public SPNBuyerServices getSpnBuyerServices() {
		return spnBuyerServices;
	}

	/**
	 * @param spnBuyerServices the spnBuyerServices to set
	 */
	public void setSpnBuyerServices(SPNBuyerServices spnBuyerServices) {
		this.spnBuyerServices = spnBuyerServices;
	}

	/**
	 * @return the memberManageSearchService
	 */
	public MemberManageSearchService getMemberManageSearchService() {
		return memberManageSearchService;
	}

	/**
	 * @param memberManageSearchService the memberManageSearchService to set
	 */
	public void setMemberManageSearchService(
			MemberManageSearchService memberManageSearchService) {
		this.memberManageSearchService = memberManageSearchService;
	}

	/**
	 * @return the propertyManagerUtil
	 */
	public PropertyManagerUtil getPropertyManagerUtil() {
		return propertyManagerUtil;
	}

	/**
	 * @param propertyManagerUtil the propertyManagerUtil to set
	 */
	public void setPropertyManagerUtil(PropertyManagerUtil propertyManagerUtil) {
		this.propertyManagerUtil = propertyManagerUtil;
	}
	
	

}
