package com.newco.marketplace.web.action.inhome.autoclose;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.inhome.autoclose.InHomeAutoCloseDTO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.AutoCloseConstants;
import com.newco.marketplace.web.dto.AutoCloseSearchCriteriaDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.inhome.autoclose.services.InHomeAutoCloseService;

public class InHomeAutoCloseManagerAction extends SLDetailsBaseAction implements Preparable,
ModelDriven<InHomeAutoCloseDTO> {

	private static final long serialVersionUID = 1L;
	private InHomeAutoCloseDTO inHomeAutoCloseDTO= new InHomeAutoCloseDTO();
	private InHomeAutoCloseService inHomeAutoCloseService;
	
	
	public InHomeAutoCloseService getInHomeAutoCloseService() {
		return inHomeAutoCloseService;
	}

	public void setInHomeAutoCloseService(
			InHomeAutoCloseService inHomeAutoCloseService) {
		this.inHomeAutoCloseService = inHomeAutoCloseService;
	}

	public InHomeAutoCloseDTO getModel() {
		return inHomeAutoCloseDTO;
	}

	public void setInHomeAutoCloseDTO(InHomeAutoCloseDTO inHomeAutoCloseDTO) {
		this.inHomeAutoCloseDTO = inHomeAutoCloseDTO;
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		
	}
//To display the initial page 
	public String displayPage(){
			// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return "homepage";
		}
		boolean activeInd = true;
		List<Double> reimbursementRateList = new ArrayList<Double>();
		Double defaultReimursementRate = 0.0;
		inHomeAutoCloseDTO = getModel();		
		List<InHomeAutoCloseDTO> inHomeAutoCloseDTOList = inHomeAutoCloseService.getOverrideFirmList(activeInd);
		reimbursementRateList = inHomeAutoCloseService.getReimbursementRateList();
		defaultReimursementRate = inHomeAutoCloseService.getDefaultReimursementRate();
	
		getRequest().setAttribute("resultSize", inHomeAutoCloseDTOList.size());
		getRequest().setAttribute("autoCloseFirmOverrideList", getUpdatedResultDisplay(AutoCloseConstants.DEFAULT_PAGE_DISPLAY,inHomeAutoCloseDTOList));	
		getSession().setAttribute("reimbursementRateList", reimbursementRateList);
		getSession().setAttribute("defaultReimursementRate", defaultReimursementRate);
		return SUCCESS;		

	}
	private List<InHomeAutoCloseDTO> getUpdatedResultDisplay(Integer defaultPageDisplay,
			List<InHomeAutoCloseDTO> results) {
		if(null!= results && !results.isEmpty()){
			if(defaultPageDisplay != -1 && results.size() > defaultPageDisplay) {
				List<InHomeAutoCloseDTO> updatedResults = new ArrayList<InHomeAutoCloseDTO>(0);
					for(int ctr = 0 ; ctr < defaultPageDisplay ; ctr++) {
							updatedResults.add(results.get(ctr));
				
					}
					return updatedResults;
			}
			else {
					return results;
			}
		}
		return results;
	}
	
	/**
	 * Method fetches the list of firms either added or removed from the overrided list
	 * @return
	 */
	public String getFirmOverrideList() {
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		String overrideInd = getRequest().getParameter("activeInd");
		boolean activeInd = (overrideInd.equals(AutoCloseConstants.ACTIVE)?true:false);
		
		String displayCount = getRequest().getParameter(AutoCloseConstants.DISPLAY_COUNT);
		
		List<InHomeAutoCloseDTO> inHomeAutoCloseDTOList = inHomeAutoCloseService.getOverrideFirmList(activeInd);
		
		getRequest().setAttribute("resultSize", inHomeAutoCloseDTOList.size());
		getRequest().setAttribute("autoCloseFirmOverrideList", getUpdatedResultDisplay(Integer.parseInt(displayCount),inHomeAutoCloseDTOList));
		
		if(!activeInd){
			return "removed";
		}else{
			return "override";
		}
	}
	/**
	 * Method performs search for providers/firms as the case may be
	 * @return
	 */
	public String searchMembers(){
		AutoCloseSearchCriteriaDTO autoCloseSearchCriteriaVO = populateSearchCriteria();
		if(autoCloseSearchCriteriaVO.getSearchByType() != null  && !("-1".equalsIgnoreCase(autoCloseSearchCriteriaVO.getSearchByType())) ) {
			Integer searchType = Integer.valueOf(autoCloseSearchCriteriaVO.getSearchByType().trim());
			if(searchType.intValue() == 1 || searchType.intValue() == 2) {
				updateCriteriaWithIdList(autoCloseSearchCriteriaVO, searchType);
				List<ProviderFirm> resultsFirm = inHomeAutoCloseService.getProviderFirms(autoCloseSearchCriteriaVO.getProviderFirmName(),autoCloseSearchCriteriaVO.getProviderFirmIds());
				getSession().setAttribute(AutoCloseConstants.SEARCH_RESULTS,resultsFirm);
			}
			getSession().setAttribute(AutoCloseConstants.SEARCH_CRITERIA,autoCloseSearchCriteriaVO);
		}
		return SUCCESS;
	}
	
	private AutoCloseSearchCriteriaDTO populateSearchCriteria() {
		AutoCloseSearchCriteriaDTO autoCloseSearchCriteriaVO = new AutoCloseSearchCriteriaDTO();
		autoCloseSearchCriteriaVO.setMaxResultsDisplayNumber(AutoCloseConstants.DEFAULT_PAGE_DISPLAY);
		autoCloseSearchCriteriaVO.setSearchByType(getRequest().getParameter(AutoCloseConstants.CRITERIA_TYPE));
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
		 if(searchType.intValue() == 1) {	
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
	/**
	 * Method updates the list of firms either added or removed from the overridden list
	 * @return
	 */
	public String updateFirmOverrideList() {
		// Not logged in? Redirect to homepage
		ServiceOrdersCriteria criteria = get_commonCriteria();
		if (criteria == null) {
			return AutoCloseConstants.HOME_PAGE;
		}
		Integer ruleAssocId = 0;
		List<BigDecimal> reimbursementRate = new LinkedList<BigDecimal>();	
		
		String ruleAssocIdString = getRequest().getParameter(AutoCloseConstants.RULE_ASSOC_ID);
		String removedAssocInd = getRequest().getParameter(AutoCloseConstants.REMOVE_ASSOC_IND);
		String comment[] = getRequest().getParameterValues(AutoCloseConstants.COMMENT);
		String firmIdsToAdd[] = getRequest().getParameterValues(AutoCloseConstants.ID_ARRAY);
		String reimbursementValue[] = getRequest().getParameterValues("reimbursementvalue");		
		
		//Getting values from req parameters to persist
		boolean activeInd = (removedAssocInd.equals(AutoCloseConstants.ACTIVE)?true:false);
		
		if(StringUtils.isNotBlank(ruleAssocIdString)){
			ruleAssocId = Integer.parseInt(ruleAssocIdString);
		}	
		
		if(null!= comment[0]){
			try {
				comment[0]= URLDecoder.decode( comment[0] , "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			comment = comment[0].split(",");
		}
		
		if(null!= firmIdsToAdd[0]){
			firmIdsToAdd = firmIdsToAdd[0].split(",");
		}
		
		if(null!= reimbursementValue[0]){
			try {
				reimbursementValue[0]= URLDecoder.decode(reimbursementValue[0] , "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			reimbursementValue = reimbursementValue[0].split(",");
		}
		for (String value : reimbursementValue) {
			if(StringUtils.isNotBlank(value)){
				reimbursementRate.add(new BigDecimal(value));
			}
		}
		//getting back the commas if any in comments
		for(String commentData : comment){
			commentData.replaceAll("~*#", ",");
		}
		
		String modifiedBy = criteria.getFName()+" "+criteria.getLName()+"(ID #"+criteria.getVendBuyerResId()+")";
		
		if(ruleAssocIdString.equals("0")){
			inHomeAutoCloseService.addToFirmOverrideList(reimbursementRate, activeInd, comment, firmIdsToAdd, modifiedBy);
		}else{
			inHomeAutoCloseService.updateOverriddenList(ruleAssocId, activeInd, comment, modifiedBy);
		}
		
		List<InHomeAutoCloseDTO> inHomeAutoCloseDTOList = inHomeAutoCloseService.getOverrideFirmList(activeInd);
		
		getRequest().setAttribute("resultSize", inHomeAutoCloseDTOList.size());
		getRequest().setAttribute("autoCloseFirmOverrideList", getUpdatedResultDisplay(AutoCloseConstants.DEFAULT_PAGE_DISPLAY,inHomeAutoCloseDTOList));
		
		if(activeInd){
			return "override";			
		}else{
			return "removed";
		}
	}	
}
