package com.servicelive.routingrulesweb.action;

import static com.servicelive.routingrulesengine.RoutingRulesConstants.PREFIX_AUTO_PULL;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.PREFIX_CHOSEN_JOB_PRICE;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.services.RoutingRuleCriteriaService;
import com.servicelive.routingrulesengine.services.ValidationService;
import com.servicelive.routingrulesengine.vo.JobCodeVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RuleConflictDisplayVO;
import com.servicelive.routingrulesweb.action.RoutingRulesCreateModel.UserAction;


public class RoutingRulesCreateRuleAction
	extends RoutingRulesBaseAction
	implements ModelDriven<RoutingRulesCreateModel>, Preparable
{

	private static final long serialVersionUID = 20090831L;
	private RoutingRulesCreateModel model;
	private RoutingRuleCriteriaService criteriaService;
	private ValidationService validationService;
	private RoutingRuleHdr myrule;
	private Integer buyerId;
   private SecurityContext securityContext;
	/**
	 * Assert that we have a buyer ID, and populate the model.
	 */
	public void prepare()
	{

		HttpServletRequest request = getRequest();

		String sRuleId = request.getParameter("rid");

		if (null != sRuleId && !"".equals(sRuleId))
			try {
				Integer ruleId = new Integer(sRuleId);
				myrule = getRoutingRulesService().getRoutingRulesHeader(ruleId);
			} catch (NumberFormatException nfe) {
				log.error("Unable to parse rid=" + sRuleId, nfe);
			}

		buyerId = getContextBuyerId();
		if (buyerId == null) {
			log.warn("Unable to get buyer ID.");
		}

		populateModel();
	}
	
	
	/**
	 * If viewing/editing/[copying?], use this to populate RHS ChosenAttrs.
	 */
	private void populateModel()
	{
		if (model == null) {
			model = new RoutingRulesCreateModel();
		}
		if (myrule != null)
		{
			try {
				model.setRuleId (myrule.getRoutingRuleHdrId().toString());
				model.setRulename(myrule.getRuleName());
				model.setFirstname(myrule.getContact().getFirstName());
				model.setLastname(myrule.getContact().getLastName());
				model.setEmail(myrule.getContact().getEmail());
				model.setRuleStatus(myrule.getRuleStatus());
				// populate RHS widgets on copy/view/edit
				String save = getRequest().getParameter("save");
				if(save==null || save.equalsIgnoreCase("")){
					populateCriteria();
				}
				List<RuleConflictDisplayVO> conflictVO = new ArrayList<RuleConflictDisplayVO> ();
                conflictVO = getRoutingRulesService().getRoutingRuleConflicts(myrule.getRoutingRuleHdrId());
                model.setRuleConflictDisplayVO(conflictVO); 
				String escFirms=  StringEscapeUtils.escapeJavaScript(getRoutingRulesService().getProviderFirmsJson(myrule));
				model.setChosenProviderFirmsJson(escFirms);
				
			} catch (Exception e) {
				log.warn("Unable to populate model for RoutingRulesCreateRuleAction.  So far: " + model, e);
			}
		}
	}
	
	/**
	 * The method is used to populate CAR rule criteria like zip codes, market, job code
	 * and custom reference
	 * 
	 * @param 
	 * @return 
	 */
	private void populateCriteria()
	{
		Map<String,List<String>> criteriaMap= getRoutingRulesService().getRuleCriteria(myrule);
		List<JobCodeVO> jobPriceList= getRoutingRulesService().getRuleJobCodes(myrule);
		List<String> zipMarketList = criteriaMap.get("ZIP_LIST");
		List<String> custRefList = criteriaMap.get("CUST_REF_LIST");
		List<String> markets = criteriaMap.get("MARKETS");
		List<String> zips = criteriaMap.get("ZIPS");
		getSession().setAttribute("zipMainList", zipMarketList);
		getSession().setAttribute("custRefMainList", custRefList);
		getSession().setAttribute("jobPriceMainList", jobPriceList);
		getSession().setAttribute("zipsList", zips);
		getSession().setAttribute("marketsList", markets);
		
		displayZipAndMarketCriteria(zipMarketList,zips,markets, 1);
		displayJobCodeCriteria(jobPriceList, 1);
		displayCustRefCriteria(custRefList, 1);
		
	}
	/**
	 * The method is used to populate CAR rule criteria like zip codes, market, job code
	 * and custom reference
	 * 
	 * @param 
	 * @return 
	 */
	public String loadCriteriaForRule()
	{
		String rule = getRequest().getParameter("rid");
		Map<String,List<String>> criteriaMap= getRoutingRulesService().getRuleCriteriaForRuleId(Integer.parseInt(rule));
		List<JobCodeVO> jobPriceList= getRoutingRulesService().getRuleJobCodes(myrule);
		List<String> zipMarketList = criteriaMap.get("ZIP_LIST");
		List<String> custRefList = criteriaMap.get("CUST_REF_LIST");
		List<String> markets = criteriaMap.get("MARKETS");
		List<String> zips = criteriaMap.get("ZIPS");
		getSession().setAttribute("zipMainList", zipMarketList);
		getSession().setAttribute("custRefMainList", custRefList);
		getSession().setAttribute("jobPriceMainList", jobPriceList);
		getSession().setAttribute("zipsList", zips);
		getSession().setAttribute("marketsList", markets);
		
		loadZipCodeList();
		return SUCCESS;
	}
	/**
	 * The method is to set pagination VO for different CAR rule criteria
	 * 
	 * @param totalCount
	 * @param pagenum
	 * @return RoutingRulesPaginationVO
	 */
	private RoutingRulesPaginationVO getPaginationVO(int totalCount, int pagenum){
		RoutingRulesPaginationVO paginationVO = new RoutingRulesPaginationVO();
		
		int noOfPages = totalCount/15;
		if(noOfPages * 15 < totalCount){
			noOfPages = noOfPages + 1;
		}
		int startIndex = (pagenum - 1) * 15;
		int endIndex = startIndex + 14;
		if(endIndex > totalCount){
			endIndex = totalCount;
		}
		paginationVO.setCurrentIndex(pagenum);
		paginationVO.setPaginetStartIndex(startIndex);
		paginationVO.setTotalPages(noOfPages);
		paginationVO.setPaginetEndIndex(endIndex);
		paginationVO.setTotalRecords(totalCount);
		return paginationVO;
	}
	
	/**
	 * The method is for loading zip code/market criteria for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String loadZipCodeList()
	{
		List<String> zipMarketList = (List<String>) getSession().getAttribute("zipMainList");
		List<String> markets = (List<String>) getSession().getAttribute("markets");
		List<String> zips = (List<String>) getSession().getAttribute("zips");
		String pageNumber = getRequest().getParameter("pageNo");
		int number = 1;
		if(null!=pageNumber ){
			try{
				number = Integer.parseInt(pageNumber);
			}catch(NumberFormatException e){
				log.error("Invalid page number");
			}
		}
		displayZipAndMarketCriteria(zipMarketList,zips,markets,number);
		return SUCCESS;
		
	}
	
	/**
	 * The method is for loading Custom Reference criteria for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	public String loadCustRefList()
	{
		List<String> custRefList = (List<String>) getSession().getAttribute("custRefMainList");
		String pageNumber = getRequest().getParameter("pageNo");
		int number = 1;
		if(null!=pageNumber ){
			try{
				number = Integer.parseInt(pageNumber);
			}catch(NumberFormatException e){
				log.error("Invalid page number");
			}
		}
		displayCustRefCriteria(custRefList, number);
		return SUCCESS;
	}
	
	/**
	 * The method is for loading Job Code criteria for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	public String loadJobCodeList()
	{
		String pageNumber = getRequest().getParameter("pageNo");
		List<JobCodeVO> jobPriceList = (List<JobCodeVO>) getSession().getAttribute("jobPriceMainList");
		if (getRequest().getParameter("job_price")!=null && getRequest().getParameter("job_price")!= ""){
			String jobPriceValues = getRequest().getParameter("job_price");
			List<String> jobCodeList = Arrays.asList(jobPriceValues.split(","));
			for(String jobPrice :jobCodeList){
				String jobCode = "";
				String price = null;
				String [] jobPriceCode = jobPrice.split("@@");
				if(jobPriceCode!=null && jobPriceCode.length==1){
					jobCode = jobPriceCode[0];
					price = null;
				}else if(jobPriceCode!=null && jobPriceCode.length==2){
					jobCode = jobPriceCode[0];
					price = jobPriceCode[1];
				}
				
				for(JobCodeVO jobPriceVO:jobPriceList){
					if(jobCode.equals(jobPriceVO.getJobCode())){
						if(price==null){
							jobPriceVO.setPrice(null);
						}else{
							jobPriceVO.setPrice(Double.parseDouble(price));
						}
					}
				}
			}
		}
		int number = 1;
		if(null!=pageNumber ){
			try{
				number = Integer.parseInt(pageNumber);
			}catch(NumberFormatException e){
				log.error("Invalid page number");
			}
		}
		getSession().setAttribute("jobPriceMainList",jobPriceList);
		displayJobCodeCriteria(jobPriceList, number);
		return SUCCESS;
	}
	
	/**
	 * The method is to add zip codes/ markets to the existing Zip Code/Market criteria for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String addZipcodes()
	{
		try {
			String zip = getRequest().getParameter("zip");
			String market = getRequest().getParameter("markets");
			List<String> zipMainList = (List<String>) getSession().getAttribute("zipMainList");
			List<String> marketsFromSession = (List<String>) getSession().getAttribute("marketsList");
			List<String> zipsFromSession = (List<String>) getSession().getAttribute("zipsList");
			if(zipMainList==null){
				zipMainList = new ArrayList<String>();
			}
			if(marketsFromSession == null){
				marketsFromSession = new ArrayList<String>();
			}
			if(zipsFromSession == null ){
				zipsFromSession = new ArrayList<String>();
			}
			String duplicateZipMarkets ="";
			String invalidZips ="";
			String validZipAdded ="";
			int validZipCount = 0;
			if(zip!=null){
				String[] zipList = zip.split(",");
				List<String> zips = Arrays.asList(zipList); 
				HashMap<String,List<String>> validatedZipList = validationService.validateZipCodes(zips);
				List<String> validZips =  validatedZipList.get(RoutingRulesConstants.VALID_ZIPS);
				List<String> inValidZips =  validatedZipList.get(RoutingRulesConstants.INVALID_ZIPS);
				for (String validZip : validZips) {
					if (zipMainList.contains("zip" + validZip)) {
						if (duplicateZipMarkets == "") {
							duplicateZipMarkets = duplicateZipMarkets
									+ validZip;
						} else {
							duplicateZipMarkets = duplicateZipMarkets + ","
									+ validZip;
						}
					} else {
						zipMainList.add("zip" + validZip);
						if(!zipsFromSession.contains(validZip)){
							zipsFromSession.add(validZip);
						}
						if(validZipAdded == ""){
							validZipAdded = validZip;
						}else{
							validZipAdded = validZipAdded + "," + validZip;
						}
						validZipCount++;
					}

				}
				for(String invalidZip:inValidZips){
					if(invalidZips == ""){
						invalidZips = invalidZip;
					}else{
						invalidZips = invalidZips + "," + invalidZip;
					}
					
				}
			}
			if(market!=null){
				String[] marketsList = market.split(",");
				List<String> markets = Arrays.asList(marketsList);
				for(String addedMarket : markets){
					if (addedMarket != "") {
						if (zipMainList.contains(addedMarket)) {
							if (duplicateZipMarkets == "") {
								duplicateZipMarkets = duplicateZipMarkets
										+ addedMarket;
							} else {
								duplicateZipMarkets = duplicateZipMarkets + ","
										+ addedMarket;
							}
						} else {
							zipMainList.add(addedMarket);
							if(addedMarket.startsWith("market")){
								String marketId = addedMarket.substring(6);
								if(!marketsFromSession.contains(addedMarket)){
									marketsFromSession.add(marketId);
								}
							}
							
						}
					}
				}
			}
			if(zipMainList==null){
				zipMainList = new ArrayList<String>();
			}
			getSession().setAttribute("zipMainList",zipMainList);
			getSession().setAttribute("zipsList",zipsFromSession);
			getSession().setAttribute("marketsList",marketsFromSession);
			getRequest().setAttribute("invalidZips", invalidZips);
			getRequest().setAttribute("validZipAdded", validZipAdded);
			getRequest().setAttribute("duplicateZipMarkets", duplicateZipMarkets);
			getRequest().setAttribute("validZipCount", validZipCount);
			
			displayZipAndMarketCriteria(zipMainList,zipsFromSession,marketsFromSession,1);
					
		} catch (Throwable e) {
			log.warn("Error while adding zip codes", e);
		}
		return SUCCESS;
	}
	
	/**
	 * The method is to add custom references to the existing Custom Reference criteria for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	public String addCustReference()
	{
		try {
			String custRefValues = getRequest().getParameter("custom_ref_value");
			String custRefName = getRequest().getParameter("custom_ref_name");
			int validCustRefCount = 0;
			String validCustRef = "";
			validCustRefCount = Integer.parseInt(getRequest().getParameter("validCustRefCount"));
			List<String> custRefList = Arrays.asList(custRefValues.split(","));
			String duplicateCustRefs = "";
			List<String> custMainList = (List<String>) getSession().getAttribute("custRefMainList");
			if(custMainList==null){
				custMainList = new ArrayList<String>();
			}
			for (String custRefVal : custRefList) {
				if (custMainList.contains(custRefName
						+ RoutingRulesConstants.DELIMITER + custRefVal.toLowerCase().trim())||custMainList.contains(custRefName
								+ RoutingRulesConstants.DELIMITER + custRefVal.toUpperCase().trim())) {
					if (duplicateCustRefs == "") {
						duplicateCustRefs = duplicateCustRefs + custRefVal;
					} else {
						duplicateCustRefs = duplicateCustRefs + ","
								+ custRefVal;
					}
				} else {
					custMainList.add(custRefName
							+ RoutingRulesConstants.DELIMITER + custRefVal.trim());
					if(validCustRef==""){
						validCustRef = custRefName
						+ RoutingRulesConstants.DELIMITER + custRefVal.trim();
					}else{
						validCustRef = validCustRef + "," +custRefName
						+ RoutingRulesConstants.DELIMITER + custRefVal.trim();
					}
					
					validCustRefCount ++;
				}
			}
			getRequest().setAttribute("validCustRefCount", validCustRefCount);
			getRequest().setAttribute("validCustRef", validCustRef);
			getRequest().setAttribute("duplicateCustRefs", duplicateCustRefs);
			getSession().setAttribute("custRefMainList",custMainList);
			displayCustRefCriteria(custMainList, 1);
					
		} catch (Throwable e) {
			log.warn("Error while adding custom references", e);
		}
		return SUCCESS;

	}
	
	/**
	 * The method is add job codes to the existing JobCodes for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	public String addJobCodes()
	{
		try {
			String jobCodeValues = getRequest().getParameter("jobCodes");
			List<String> newJobCodeList = Arrays.asList(jobCodeValues.split(","));
			List<JobCodeVO> newJobCodes = new ArrayList<JobCodeVO>();
			int dupInd = 0;
			int validJobCodeCount = 0;
			validJobCodeCount = Integer.parseInt(getRequest().getParameter("validjobCount"));
			for(String jobCodeVal : newJobCodeList){
				JobCodeVO jobPriceVO = new JobCodeVO(jobCodeVal,0.0);
				newJobCodes.add(jobPriceVO);
			}
			HashMap<String,List<String>> validatedJobCodeList = validationService.validateJobCodeList(newJobCodeList, buyerId);
			List<String> validJobCodes = new ArrayList<String>();
			List<String> inValidJobCodes = new ArrayList<String>();
			//R12_2 : SL-20643
			List<String> permitJobCodes = new ArrayList<String>();
			validJobCodes = validatedJobCodeList.get(RoutingRulesConstants.VALID_JOBCODES);
			inValidJobCodes = validatedJobCodeList.get(RoutingRulesConstants.INVALID_JOBCODES);
			//R12_2 : SL-20643
			permitJobCodes = validatedJobCodeList.get(RoutingRulesConstants.PERMIT_JOBCODES);
			List<JobCodeVO> jobMainList = (List<JobCodeVO>) getSession().getAttribute("jobPriceMainList");
			String duplicateJobCodes = "";
			String inValidJobCode = "";
			String validJobCode = "";
			if(jobMainList==null){
				jobMainList = new ArrayList<JobCodeVO>();
			}
			for(String jobCode:validJobCodes){	
				JobCodeVO jobCodeVO = new JobCodeVO(jobCode,null);
				dupInd = 0;
				for (JobCodeVO jobVO : jobMainList) {
					if (jobVO.getJobCode().equals(jobCode)) {
						if(duplicateJobCodes == ""){
							duplicateJobCodes = duplicateJobCodes + jobCode;
						}else{
							duplicateJobCodes = duplicateJobCodes + "," + jobCode;
						}
						dupInd = 1;
						break;
					}
				}
				if(dupInd == 0){
					validJobCodeCount++;
					if(validJobCode==""){
						validJobCode = jobCode;
					}else{
						validJobCode = validJobCode + "," +jobCode;
					}
					
					jobMainList.add(jobCodeVO);
				}
			}
			for(String jobCode:inValidJobCodes){
				if(inValidJobCode == ""){
					inValidJobCode = inValidJobCode + jobCode;
				}else{
					inValidJobCode = inValidJobCode +","+ jobCode;
				}
			}
			String jobPriceValues = getRequest().getParameter("job_price");
			if(jobPriceValues!=null && jobPriceValues != ""){
				List<String> jobCodeList = Arrays.asList(jobPriceValues.split(","));
				for(String jobPrice :jobCodeList){
					String jobCode = "";
					String price = null;
					String [] jobPriceCode = jobPrice.split("@@");
					if(jobPriceCode!=null && jobPriceCode.length==1){
						jobCode = jobPriceCode[0];
						price = null;
					}else if(jobPriceCode!=null && jobPriceCode.length==2){
						jobCode = jobPriceCode[0];
						price = jobPriceCode[1];
					}
					
					for(JobCodeVO jobPriceVO:jobMainList){
						if(jobCode.equals(jobPriceVO.getJobCode())){
							if(price==null){
								jobPriceVO.setPrice(null);
							}else{
								jobPriceVO.setPrice(Double.parseDouble(price));
							}
						}
					}
				}
			}
			getRequest().setAttribute("inValidJobCode", inValidJobCode);
			getRequest().setAttribute("duplicateJobCodes", duplicateJobCodes);
			getSession().setAttribute("jobPriceMainList",jobMainList);
			getRequest().setAttribute("validJobCodeCount", validJobCodeCount);
			getRequest().setAttribute("validJobCode", validJobCode);
			//R12_2 : SL-20643
			if (null != permitJobCodes && !permitJobCodes.isEmpty()) {
				getRequest().setAttribute("hasPermit", "true");
			}
			displayJobCodeCriteria(jobMainList, 1);			
		} catch (Throwable e) {
			log.warn("Error while adding custom references", e);
		}
		return SUCCESS;

	}
	
	/**
	 * The method is delete Zip/Markets for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String deleteZipMarkets()
	{
		try {
			String deletedZipMarketValues = getRequest().getParameter("deleteZipMarkets");
			List<String> zipMarkets = Arrays.asList(deletedZipMarketValues.split(","));
			List<String> zipMarketMainList = (List<String>) getSession().getAttribute("zipMainList");
			List<String> marketsFromSession = (List<String>) getSession().getAttribute("marketsList");
			List<String> zipsFromSession = (List<String>) getSession().getAttribute("zipsList");
			for(String zipMarket :zipMarkets){
				if (zipMarketMainList.contains(zipMarket)){
					zipMarketMainList.remove(zipMarket);				
					if(zipMarket.startsWith("market")){
						String marketVal = zipMarket.substring(6);
						if(null!=marketsFromSession && marketsFromSession.size()>0 
								&& marketsFromSession.contains(marketVal)){
							marketsFromSession.remove(marketVal);
						}
					}else if(zipMarket.startsWith("zip") || zipMarket.startsWith("#zip")){
						String zipVal = "";
						if(zipMarket.startsWith("zip")){
							zipVal = zipMarket.substring(3);
						}else if(zipMarket.startsWith("#zip")){
							zipVal = zipMarket.substring(4);
						}
						if(null!=zipsFromSession && zipsFromSession.size()>0 
								&& zipsFromSession.contains(zipVal)){
							zipsFromSession.remove(zipVal);
						}
					}
				}
			}
			getSession().setAttribute("zipMainList",zipMarketMainList);
			getSession().setAttribute("marketsList",marketsFromSession);
			getSession().setAttribute("zipsList",zipsFromSession);
			displayZipAndMarketCriteria(zipMarketMainList,zipsFromSession,marketsFromSession,1);
				
		} catch (Throwable e) {
			log.warn("Error while adding deleting zip markets", e);
		}
		return SUCCESS;

	}
	
	/**
	 * The method is delete Custom References for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	public String deleteCustReference()
	{
		try {
			String deletedCustRefValues = getRequest().getParameter("deleteCustRefs");
			deletedCustRefValues = URLDecoder.decode( deletedCustRefValues , "UTF-8");
			List<String> custRefs = Arrays.asList(deletedCustRefValues.split(","));
			List<String> custMainList = (List<String>) getSession().getAttribute("custRefMainList");
			for(String custRef :custRefs){
				if (custMainList.contains(custRef)){
					custMainList.remove(custRef);
				}
			}
			getSession().setAttribute("custRefMainList",custMainList);
			displayCustRefCriteria(custMainList, 1);	
		} catch (Throwable e) {
			log.warn("Error while adding deleting custom references", e);
		}
		return SUCCESS;

	}
	
	/**
	 * The method is delete Job codes for the CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	public String deleteJobCodes()
	{
		try {
			List<JobCodeVO> jobMainList = (List<JobCodeVO>) getSession().getAttribute("jobPriceMainList");
			String jobCodeValues = getRequest().getParameter("jobCodes");
			List<String> jobCodeList = Arrays.asList(jobCodeValues.split(","));
			List<JobCodeVO> removeList = new ArrayList<JobCodeVO>();
			for(String jobPrice :jobCodeList){
				for(JobCodeVO jobPriceVO:jobMainList){
					if(jobPrice.equals(jobPriceVO.getJobCode())){
						removeList.add(jobPriceVO);
					}
				}
				
			}
			jobMainList.removeAll(removeList);
			String jobPriceValues = getRequest().getParameter("job_price");
			
			if(jobPriceValues!=null && jobPriceValues != ""){
				List<String> jobCodePriceList = Arrays.asList(jobPriceValues.split(","));
				for(String jobPrice :jobCodePriceList){
					String jobCode = "";
					String price = null;
					String [] jobPriceCode = jobPrice.split("@@");
					if(jobPriceCode!=null && jobPriceCode.length==1){
						jobCode = jobPriceCode[0];
						price = null;
					}else if(jobPriceCode!=null && jobPriceCode.length==2){
						jobCode = jobPriceCode[0];
						price = jobPriceCode[1];
					}
					
					for(JobCodeVO jobPriceVO:jobMainList){
						if(jobCode.equals(jobPriceVO.getJobCode())){
							if(price==null){
								jobPriceVO.setPrice(null);
							}else{
								jobPriceVO.setPrice(Double.parseDouble(price));
							}
						}
					}
				}
			}
			getSession().setAttribute("jobPriceMainList",jobMainList);
			displayJobCodeCriteria(jobMainList, 1);		
		} catch (Throwable e) {
			log.warn("Error while adding deleting job codes", e);
		}
		return SUCCESS;

	}
	
	/**
	 * The method is set attributes for display of Zip/Markets criteria for a CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	private void displayZipMarketCriteria(List<String> zipMarketList, int number){
		List<Map<String,String>> zipMarketMap = criteriaService.sortZipsMarkets(zipMarketList);
		List<Map<String,String>> zipMarketPaginatedList = criteriaService.setCriteriaPagination(zipMarketMap, "zipMainList", number);
		int pageSize = 0;
		if(null!=zipMarketList){
			pageSize = zipMarketList.size();
		}
		RoutingRulesPaginationVO zipMarketPagination = getPaginationVO(pageSize,number);
		getRequest().setAttribute("zipList", zipMarketPaginatedList);
		getRequest().setAttribute("zipMarketPagination", zipMarketPagination);
		
	}
	
	
	
	/**
	 * The method is set attributes for display of Zip/Markets criteria for a CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	private void displayZipAndMarketCriteria(List<String> zipMarketList,List<String> zips,List<String> markets, int number){
		List<Map<String,String>> zipMarketMap = criteriaService.sortZipsAndMarkets(zipMarketList,zips,markets);
		List<Map<String,String>> zipMarketPaginatedList = criteriaService.setCriteriaPagination(zipMarketMap, "zipMainList", number);
		int pageSize = 0;
		if(null!=zipMarketList){
			pageSize = zipMarketList.size();
		}
		RoutingRulesPaginationVO zipMarketPagination = getPaginationVO(pageSize,number);
		getRequest().setAttribute("zipList", zipMarketPaginatedList);
		getRequest().setAttribute("zipMarketPagination", zipMarketPagination);
		
	}
	/**
	 * The method is set attributes for display of Job code criteria for a CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	private void displayJobCodeCriteria(List<JobCodeVO> jobPriceList, int number){
		List<JobCodeVO> jobSortList = criteriaService.sortJobCodes(jobPriceList);
		List<JobCodeVO> jobList = criteriaService.setJobCodeCriteriaPagination(jobSortList, number);
		int pageSize = 0;
		if(null!=jobSortList){
			pageSize = jobSortList.size();
		}
		RoutingRulesPaginationVO jobPricePagination = getPaginationVO(pageSize,number);
		getRequest().setAttribute("jobPriceList", jobList);
		getRequest().setAttribute("jobPagination", jobPricePagination);
	}
	
	/**
	 * The method is set attributes for display of Custom Reference criteria for a CAR Rule
	 * 
	 * @param 
	 * @return 
	 */
	private void displayCustRefCriteria(List<String> custRefList, int number){
		List<Map<String,String>> custRefMap = criteriaService.sortCustReference(custRefList);
		List<Map<String,String>> custRefPaginatedList = criteriaService.setCriteriaPagination(custRefMap, "custRefMainList", number);
		int pageSize = 0;
		if(null!=custRefList){
			pageSize = custRefList.size();
		}
		RoutingRulesPaginationVO custPagination = getPaginationVO(pageSize,number);
		getRequest().setAttribute("custRefList", custRefPaginatedList);
		getRequest().setAttribute("custPagination", custPagination);
		
	}
	/**
	 * Edit existing rule
	 */
	public String edit()
		throws Exception
	{
		HttpServletRequest request = getRequest();
		String tabType = request.getParameter("tabType");
		getSession().setAttribute("tabType", tabType);
		model.setTabType(tabType);
		model.setUserAction(UserAction.EDIT);
		return INPUT;
	}

	
	
	/**
	 * View existing rule
	 */
	public String view()
		throws Exception
	{
		HttpServletRequest request = getRequest();
		String tabType = request.getParameter("tabType");
		getSession().setAttribute("tabType", tabType);
		model.setTabType(tabType);
		model.setUserAction(UserAction.VIEW);
		return INPUT;
	}


	/**
	 * Create a new rule
	 */
	public String create()
		throws Exception
	{
		HttpServletRequest request = getRequest();
		String tabType = request.getParameter("tabType");
		getSession().setAttribute("tabType", tabType);
		model.setTabType(tabType);
		model.setUserAction(UserAction.CREATE);
		getSession().setAttribute("zipMainList", null);
		getSession().setAttribute("custRefMainList", null);
		getSession().setAttribute("jobPriceMainList", null);
		return INPUT;
	}


	/**
	 * Persist data, forward to dashboard
	 */
	public String savedone()
	{
		try {
			HttpServletRequest request = getRequest();
			//Boolean isEdit= Boolean.FALSE;
			String[] firms= request.getParameterValues("firms-chosen-checks");
			String check = request.getParameter("conflictCheck");
			Integer contextBuyerId = getContextBuyerId();
			String modifiedBy = getLoggedInName();
			List<String> zipMainList = (List<String>) getSession().getAttribute("zipMainList");
			List<String> custMainList = (List<String>) getSession().getAttribute("custRefMainList");
			List<JobCodeVO> jobMainList = (List<JobCodeVO>) getSession().getAttribute("jobPriceMainList");
			String jobPriceValues = getRequest().getParameter("job_price_save");
			if(jobPriceValues!=null && !"".equals(jobPriceValues)){
				List<String> jobCodeList = Arrays.asList(jobPriceValues.split(","));
				for(String jobPrice :jobCodeList){
					String jobCode = "";
					String price = null;
					String [] jobPriceCode = jobPrice.split("@@");
					if(jobPriceCode!=null && jobPriceCode.length==1){
						jobCode = jobPriceCode[0];
						price = null;
					}else if(jobPriceCode!=null && jobPriceCode.length==2){
						jobCode = jobPriceCode[0];
						price = jobPriceCode[1];
					}
					
					for(JobCodeVO jobPriceVO:jobMainList){
						if(jobCode.equals(jobPriceVO.getJobCode())){
							if(price==null){
								jobPriceVO.setPrice(null);
							}else{
								jobPriceVO.setPrice(Double.parseDouble(price));
							}
						}
					}
				}
			}
			getSession().setAttribute("jobPriceMainList",jobMainList);
			Map<String, Object> map = new HashMap<String, Object>();

			String ruleId= request.getParameter("rid");
			if (null != ruleId && ruleId.length()>0) {
				map.put("ruleId", ruleId);
				//isEdit= Boolean.TRUE;
			}
			//Add code for updating jobPrice
			map.put("ruleName", request.getParameter("ruleName"));
			map.put("makeInactive", request.getParameter("makeInactive"));
			map.put("ruleComment", request.getParameter("ruleComment"));
			map.put("firstName", request.getParameter("firstName"));
			map.put("lastName", request.getParameter("lastName"));
			map.put("email", request.getParameter("email"));
			map.put("chosenProviderFirms", firms);
			map.put("chosenCustomRefs", custMainList);
			map.put("chosenMarketsZips", zipMainList);
			map.put("chosenJobCodes", jobMainList);
			map.put("autoPullMap", createAutoPullMap(request));
			map.put("chosenJobPriceMap", createJobPriceMap(request));
			map.put("securityContextForRule",getSecurityContext());
			String ruleName = request.getParameter("ruleName");
			String tabType = (String)getSession().getAttribute("tabType");
			//SL-15642 Saving auto_accept_status in the routing_rule_vendor table during Creation of CAR rule
			if(getSession().getAttribute("carFeatureOn")!=null && getSession().getAttribute("carFeatureOn").equals(true))  
			{
				
				if(getSession().getAttribute("autoAcceptStatus")!=null && getSession().getAttribute("autoAcceptStatus").equals(true))
				{
					map.put("autoAcceptStatCarRuleCreation",RoutingRulesConstants.AUTO_ACCEPT_PENDING_STATUS);
				}
				else
				{
					map.put("autoAcceptStatCarRuleCreation",RoutingRulesConstants.AUTO_ACCEPT_NA_STATUS);
				}
			//	getRoutingRulesService().saveRoutingRuleVendor(autoAcceptStatCarRuleCreation,Integer.parseInt(ruleId));
			}
			if(ruleId.equals("")){
				map.put("userAction", "CREATE");
				String msgDsp = "The rule <b>"+ ruleName +"</b> has been <b>saved</b> successfully";
				getSession().setAttribute("msgString", msgDsp);
			}
			else{
				//map.put("actionMade", "EDIT");
				//SL 15642 Setting the user action in map to update the auto accept status for a already created rule
				map.put("userAction","EDIT");
				if(tabType.equals("manageTab")){
				String msgDsp = "The rule <b>"+ ruleName +"</b> has been <b>updated</b> successfully";
				getSession().setAttribute("msgString", msgDsp);
				}else{
				String msgDsp = "The rule <b>"+ ruleName +"</b> has been <b>updated</b> successfully";
				getSession().setAttribute("msgString1", msgDsp);
				}
			}
		
			
			getRoutingRulesService().updateRoutingRule(map, contextBuyerId, modifiedBy);
			
			if(getSession().getAttribute("tabType").equals("searchTab")){
				getSession().setAttribute("searchSession","true");
			return "search";
			}
			return SUCCESS;
			
		} catch (Exception ex) {
			log.error("UNABLE TO PERSIST DATA.", ex);
			return ERROR;
		}

	}

	

	public RoutingRulesCreateModel getModel()
	{
		return model;
	}


	public List<LabelValueBean> getUsStates()
	{
		return getRoutingRulesService().getStates();
	}


	/**
	 * Used to populate LHS combo box choices.
	 */
	public List<LabelValueBean> getCustomReferenceChoices()
	{
		return getRoutingRulesService().getCustomReferences(buyerId);
	}


	/**
	 * Used to populate LHS combo box choices.
	 */
	public List<LabelValueBean> getMarketsChoices()
	{
		return getRoutingRulesService().getMarkets();
	}


	/**
	 * Used to populate LHS combo box choices.
	 */
	public List<LabelValueBean> getSpecialtyChoices()
	{
		return getRoutingRulesService().getSpecialtyCodes(buyerId);
	}


	/**
	 * @return list of all rules belonging to this buyer, with ID's
	 */
	public List<LabelValueBean> getBuyerRules()
	{
		
		List<LabelValueBean> lvbs = getRoutingRulesService().getRulesForBuyer(buyerId) ;
		if (lvbs == null || lvbs.isEmpty()) {
			lvbs = new ArrayList<LabelValueBean>(1);
		}
		/*List<RoutingRuleHdr> buyerRules = getRoutingRulesService().getRoutingRulesHeaders(buyerId);
		if (buyerRules == null || buyerRules.isEmpty()) {
			lvbs = new ArrayList<LabelValueBean>(1);
		} else {
			lvbs = new ArrayList<LabelValueBean>(buyerRules.size());
			for (RoutingRuleHdr rule : buyerRules) {
				if(!rule.getRuleStatus().equals(RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED)){
					lvbs.add(new LabelValueBean(rule.getRuleName(), rule.getRoutingRuleHdrId().toString()));
				}
			}
		}*/
		Collections.sort(lvbs);
		return lvbs;
	}


	private Map<String, String> createAutoPullMap(HttpServletRequest request)
	{
		return createMap(request, PREFIX_AUTO_PULL);
	}


	private Map<String, String> createJobPriceMap(HttpServletRequest request)
	{
		return createMap(request, PREFIX_CHOSEN_JOB_PRICE);
	}


	private Map<String, String> createMap(HttpServletRequest request, String prefix)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (Object key : request.getParameterMap().keySet()) {
			String name = key.toString();
			if (name.startsWith(prefix)) {
				Object valueObject = request.getParameterMap().get(key);
				String value;
				if (valueObject instanceof String) {
					value = (String) valueObject;
				} else if (valueObject instanceof String[]) {
					value = ((String[]) valueObject)[0];
				} else {
					value = valueObject.toString();
				}
				map.put(name, value);
			}
		}
		return map;
	}

	public void setCriteriaPaginationService(
			RoutingRuleCriteriaService criteriaService) {
		this.criteriaService = criteriaService;
	}
	
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}


	public RoutingRuleCriteriaService getCriteriaService() {
		return criteriaService;
	}


	public void setCriteriaService(RoutingRuleCriteriaService criteriaService) {
		this.criteriaService = criteriaService;
	}


	public ValidationService getValidationService() {
		return validationService;
	}



}
