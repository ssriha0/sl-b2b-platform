package com.newco.marketplace.web.action.autoclose;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.autoclose.AutoCloseRuleExclusionDTO;
import com.newco.marketplace.dto.autoclose.ManageAutoCloseRulesDTO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.AutoCloseConstants;
import com.newco.marketplace.web.dto.AutoCloseSearchCriteriaDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.autoclose.services.AutoCloseService;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteriaHistory;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;

public class AutoCloseManagerAction extends SLBaseAction implements Preparable,
		ModelDriven<ManageAutoCloseRulesDTO> 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AutoCloseManagerAction.class);
	private AutoCloseService autoCloseService;
	private ManageAutoCloseRulesDTO model = new ManageAutoCloseRulesDTO();

	public AutoCloseManagerAction() {
		// Do nothing for now
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		getSession().removeAttribute(AutoCloseConstants.FIRM_LIST);
		getSession().removeAttribute(AutoCloseConstants.CRITERIA_HISTORY);
	}	
	
	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT 
	/*public String execute() throws Exception
	{
		return SUCCESS;
	}*/
	/**
	 * Method to display the auto close page and load all the required auto close rules
	 * @return
	 */
	public String displayPage() {
		
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}		

		//Need to have the code for permission and feature check
		ManageAutoCloseRulesDTO manageAutoCloseRulesDTO = autoCloseService.getAutoCloseRulesForBuyer(criteria.getCompanyId());
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = manageAutoCloseRulesDTO.getAutoCloseRuleExclusionList();
		getSession().setAttribute(AutoCloseConstants.RESULT_SIZE, autoCloseFirmExclusionList.size());
		getSession().setAttribute(AutoCloseConstants.FIRM_LIST, getUpdatedResultDisplay(AutoCloseConstants.DEFAULT_PAGE_DISPLAY,autoCloseFirmExclusionList));
		setModel(manageAutoCloseRulesDTO);


		return SUCCESS;
	}
	
	private List<AutoCloseRuleExclusionDTO> getUpdatedResultDisplay(Integer defaultPageDisplay,
			List<AutoCloseRuleExclusionDTO> results) {
		if(defaultPageDisplay != -1 && results.size() > defaultPageDisplay) {
			List<AutoCloseRuleExclusionDTO> updatedResults = new ArrayList<AutoCloseRuleExclusionDTO>(0);
			for(int ctr = 0 ; ctr < defaultPageDisplay ; ctr++) {
				updatedResults.add(results.get(ctr));
				
			}
			return updatedResults;
		}
		else {
			return results;
		}
	}

	/**
	 * Method to update the max price entered, persist in history table also
	 * @return
	 */
	public String updateMaxprice() {

		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		String criteriaId = getRequest().getParameter(AutoCloseConstants.CRITERIA_ID);
		String changedPrice = getRequest().getParameter(AutoCloseConstants.CHANGED_PRICE);
		String modifiedBy = criteria.getFName()+" "+criteria.getLName()+"(ID #"+criteria.getVendBuyerResId()+")";
		List<AutoCloseRuleCriteriaHistory> criteriaHistoryList = autoCloseService.updateMaxprice(Integer.parseInt(criteriaId),changedPrice,modifiedBy);
		getSession().setAttribute(AutoCloseConstants.CRITERIA_HISTORY, criteriaHistoryList);
		return SUCCESS;
	}
	
	/**
	 * Method fetches the list of firms either added or removed from the exclusion list
	 * @return
	 */
	public String getFirmExclusionList() {
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		String exclusionId = getRequest().getParameter(AutoCloseConstants.EXCLUSION_ID);
		String removedInd = getRequest().getParameter(AutoCloseConstants.REMOVED_IND);
		boolean activeInd = (removedInd.equals(AutoCloseConstants.ACTIVE)?true:false);
		
		String displayCount = getRequest().getParameter(AutoCloseConstants.DISPLAY_COUNT);
		
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = autoCloseService.getFirmExclusionList(Integer.parseInt(exclusionId), activeInd);
		getSession().setAttribute(AutoCloseConstants.RESULT_SIZE, autoCloseFirmExclusionList.size());
		getSession().setAttribute(AutoCloseConstants.FIRM_LIST, getUpdatedResultDisplay(Integer.parseInt(displayCount),autoCloseFirmExclusionList));
		if(!activeInd){
			return AutoCloseConstants.EXCLUDED;
		}else{
			return AutoCloseConstants.REMOVED;
		}
	}
	
	/**
	 * Method updates the list of firms either added or removed from the exclusion list
	 * @return
	 */
	public String updateFirmExclusionList() {
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		String ruleAssocId = getRequest().getParameter(AutoCloseConstants.RULE_ASSOC_ID);
		String removedAssocInd = getRequest().getParameter(AutoCloseConstants.REMOVE_ASSOC_IND);
		String exclusionId = getRequest().getParameter(AutoCloseConstants.EXCLUSION_ID);
		String comment[] = getRequest().getParameterValues(AutoCloseConstants.COMMENT);
		String idsToAdd[] = getRequest().getParameterValues(AutoCloseConstants.ID_ARRAY);
		boolean activeInd = (removedAssocInd.equals(AutoCloseConstants.ACTIVE)?true:false);
		
		 try {
			comment[0]= URLDecoder.decode( comment[0] , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		comment = comment[0].split(",");
		idsToAdd = idsToAdd[0].split(",");
		
		//getting back the commas if any in comments
		for(String commentData : comment){
			commentData.replaceAll("~*#", ",");
		}
		
		String modifiedBy = criteria.getFName()+" "+criteria.getLName()+"(ID #"+criteria.getVendBuyerResId()+")";
		
		if(ruleAssocId.equals("0")){
			autoCloseService.addToFirmExclusionList(Integer.parseInt(exclusionId), activeInd, comment, idsToAdd, modifiedBy);
			//changing active indicator to load the exclusion list
			activeInd = !activeInd;
		}else{
			autoCloseService.updateFirmExclusionList(Integer.parseInt(ruleAssocId), activeInd, comment, modifiedBy);
		}
		
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = autoCloseService.getFirmExclusionList(Integer.parseInt(exclusionId), !activeInd);
		getSession().setAttribute(AutoCloseConstants.RESULT_SIZE, autoCloseFirmExclusionList.size());
		getSession().setAttribute(AutoCloseConstants.FIRM_LIST, getUpdatedResultDisplay(AutoCloseConstants.DEFAULT_PAGE_DISPLAY,autoCloseFirmExclusionList));
		
		if(!activeInd){
			return AutoCloseConstants.REMOVED;
		}else{
			return AutoCloseConstants.EXCLUDED;
		}
	}	
	
	/**
	 * Method fetches the list of providers either added or removed from the exclusion list
	 * @return
	 */
	public String getProviderExclusionList() {
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		String exclusionId = getRequest().getParameter(AutoCloseConstants.EXCLUSION_ID);
		String removedInd = getRequest().getParameter(AutoCloseConstants.REMOVED_IND);
		boolean activeInd = (removedInd.equals(AutoCloseConstants.ACTIVE)?true:false);
		
		String displayCount = getRequest().getParameter(AutoCloseConstants.DISPLAY_COUNT);
		
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = autoCloseService.getProviderExclusionList(Integer.parseInt(exclusionId), activeInd);
		getSession().setAttribute(AutoCloseConstants.RESULT_SIZE, autoCloseFirmExclusionList.size());
		getSession().setAttribute(AutoCloseConstants.FIRM_LIST, getUpdatedResultDisplay(Integer.parseInt(displayCount),autoCloseFirmExclusionList));
		if(!activeInd){
			return AutoCloseConstants.EXCLUDED;
		}else{
			return AutoCloseConstants.REMOVED;
		}
	}
	
	/**
	 * Method updates the list of providers either added or removed from the exclusion list
	 * @return
	 */
	public String updateProviderExclusionList() {
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		String ruleAssocId = getRequest().getParameter(AutoCloseConstants.RULE_ASSOC_ID);
		String removedAssocInd = getRequest().getParameter(AutoCloseConstants.REMOVE_ASSOC_IND);
		String exclusionId = getRequest().getParameter(AutoCloseConstants.EXCLUSION_ID);
		String comment[] = getRequest().getParameterValues(AutoCloseConstants.COMMENT);
		String idsToAdd[] = getRequest().getParameterValues(AutoCloseConstants.ID_ARRAY);
		boolean activeInd = (removedAssocInd.equals(AutoCloseConstants.ACTIVE)?true:false);
		
		 try {
			comment[0]= URLDecoder.decode( comment[0] , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		comment = comment[0].split(",");
		idsToAdd = idsToAdd[0].split(",");
		
		//getting back the commas if any in comments
		for(String commentData : comment){
			commentData.replaceAll("~*#", ",");
		}		
		
		String modifiedBy = criteria.getFName()+" "+criteria.getLName()+"(ID #"+criteria.getVendBuyerResId()+")";
		
		if(ruleAssocId.equals("0")){
			autoCloseService.addToProviderExclusionList(Integer.parseInt(exclusionId), activeInd, comment, idsToAdd, modifiedBy);
			//changing active indicator to load the exclusion list
			activeInd = !activeInd;
		}else{		
			autoCloseService.updateProviderExclusionList(Integer.parseInt(ruleAssocId), activeInd, comment, modifiedBy);
		}	
		
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = autoCloseService.getProviderExclusionList(Integer.parseInt(exclusionId), !activeInd);
		getSession().setAttribute(AutoCloseConstants.RESULT_SIZE, autoCloseFirmExclusionList.size());
		getSession().setAttribute(AutoCloseConstants.FIRM_LIST, getUpdatedResultDisplay(AutoCloseConstants.DEFAULT_PAGE_DISPLAY,autoCloseFirmExclusionList));
		
		if(!activeInd){
			return AutoCloseConstants.REMOVED;
		}else{
			return AutoCloseConstants.EXCLUDED;
		}
	}
	
	/**
	 * Method performs search for providers/firms as the case may be
	 * @return
	 */
	public String searchMembers(){
		
		AutoCloseSearchCriteriaDTO autoCloseSearchCriteriaVO = populateSearchCriteria();
		
		String buyerId = "";
		
		if(null != get_commonCriteria().getCompanyId()){
			buyerId = get_commonCriteria().getCompanyId().toString();
		}

		if(autoCloseSearchCriteriaVO.getSearchByType() != null  && !("-1".equalsIgnoreCase(autoCloseSearchCriteriaVO.getSearchByType())) ) {
			Integer searchType = Integer.valueOf(autoCloseSearchCriteriaVO.getSearchByType().trim());
			if(AutoCloseConstants.SEARCH_TYPE_PROVIDER_FIRM_ID.intValue() == searchType.intValue() || AutoCloseConstants.SEARCH_TYPE_PROVIDER_FIRM_NAME.intValue() == searchType.intValue()) {
				updateCriteriaWithIdList(autoCloseSearchCriteriaVO, searchType);
				List<ProviderFirm> resultsFirm = autoCloseService.getProviderFirms(autoCloseSearchCriteriaVO.getProviderFirmName(),autoCloseSearchCriteriaVO.getProviderFirmIds(),buyerId);
				getSession().setAttribute(AutoCloseConstants.SEARCH_RESULTS,resultsFirm);
			}
			else if(AutoCloseConstants.SEARCH_TYPE_SERVICE_PROVIDER_ID.intValue() == searchType.intValue() || AutoCloseConstants.SEARCH_TYPE_SERVICE_PROVIDER_NAME.intValue() == searchType.intValue()) {
				updateCriteriaWithIdList(autoCloseSearchCriteriaVO, searchType);
				List<ServiceProvider> resultsProv = autoCloseService.getServiceProviders(autoCloseSearchCriteriaVO.getServiceProviderName(), autoCloseSearchCriteriaVO.getServiceProviderIds(),buyerId);
				getSession().setAttribute(AutoCloseConstants.SEARCH_RESULTS,resultsProv);
			}
			getSession().setAttribute(AutoCloseConstants.SEARCH_CRITERIA,autoCloseSearchCriteriaVO);
		}
		return SUCCESS;
	}


	private AutoCloseSearchCriteriaDTO populateSearchCriteria() {
		AutoCloseSearchCriteriaDTO autoCloseSearchCriteriaVO = new AutoCloseSearchCriteriaDTO();
		autoCloseSearchCriteriaVO.setMaxResultsDisplayNumber(AutoCloseConstants.DEFAULT_PAGE_DISPLAY);
		autoCloseSearchCriteriaVO.setSearchByType(getRequest().getParameter(AutoCloseConstants.CRITERIA_TYPE));
		autoCloseSearchCriteriaVO.setServiceProviderName(getRequest().getParameter(AutoCloseConstants.CRITERIA_PROVIDER_NAME));
		autoCloseSearchCriteriaVO.setServiceProviderIdsStr(getRequest().getParameter(AutoCloseConstants.CRITERIA_PROVIDER_IDS));
		autoCloseSearchCriteriaVO.setProviderFirmName(getRequest().getParameter(AutoCloseConstants.CRITERIA_FIRM_NAME));
		autoCloseSearchCriteriaVO.setProviderFirmIdsStr(getRequest().getParameter(AutoCloseConstants.CRITERIA_FIRM_IDS));
		return autoCloseSearchCriteriaVO;
	}
	
	/**
	 * @param memberManageCriteriaVO
	 * @param searchType
	 */
	private void updateCriteriaWithIdList(AutoCloseSearchCriteriaDTO autoCloseSearchCriteriaVO,Integer searchType) {
		String idString;
		if(searchType.intValue() == AutoCloseConstants.SEARCH_TYPE_SERVICE_PROVIDER_ID.intValue()){
			idString = autoCloseSearchCriteriaVO.getServiceProviderIdsStr();
			autoCloseSearchCriteriaVO.setServiceProviderIds(parseTheIdString(idString));}
		else if(searchType.intValue() == AutoCloseConstants.SEARCH_TYPE_PROVIDER_FIRM_ID.intValue()) {	
			idString = autoCloseSearchCriteriaVO.getProviderFirmIdsStr();
			autoCloseSearchCriteriaVO.setProviderFirmIds(parseTheIdString(idString));
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

	public AutoCloseService getAutoCloseService() {
		return autoCloseService;
	}

	public void setAutoCloseService(AutoCloseService autoCloseService) {
		this.autoCloseService = autoCloseService;
	}

	public ManageAutoCloseRulesDTO getModel() {
		return this.model;
	}

	public void setModel(ManageAutoCloseRulesDTO model) {
		this.model = model;
	}


}
