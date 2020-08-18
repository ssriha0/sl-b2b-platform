package com.servicelive.routingrulesweb.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import com.newco.marketplace.auth.SecurityContext;
import static com.servicelive.common.util.ServiceLiveStringUtils.isNotBlank;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.vo.JobCodeVO;
import com.servicelive.routingrulesengine.vo.RoutingRuleErrorVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesHdrHistVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RuleConflictDisplayVO;
import com.servicelive.routingrulesengine.vo.RuleErrorDisplayVO;

import flexjson.JSONSerializer;

public class RoutingRulesJsonAction
	extends RoutingRulesBaseAction
	implements ModelDriven<Map<String, Object>>
{
	private static final long serialVersionUID = 20090820L;
	Logger logger = Logger.getLogger(RoutingRulesJsonAction.class);

	private Map<String, Object> model = new HashMap<String, Object>();

    public Map<String, Object> getModel(){
		return model;
	}

	public void setModel(Map<String,Object> model) {
		this.model = model;
	}
	
	//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000 
	@SuppressWarnings( { "unchecked", "null" })
	public String forceFulActiveRule() {
		String msgDsp = "";
		SecurityContext securityContext = getSecurityContext();
		String modifiedBy = securityContext.getRoles().getUsername();// +"("+securityContext.getRoles().getCompanyId()+")";
		// //getUsername();
		String ruleComment = (String) getRequest().getParameter("ruleComment");
		
		String carId = (String) getRequest().getParameter("CarRuleId");
		String carName = (String) getRequest().getParameter("CarRuleName");

		logger.info("carId: " + carId);
		logger.info("carName: " + carName);
		logger.info("modifiedBy: " + modifiedBy);
		logger.info("ruleComment: " + ruleComment);
		Map forceFulCarActiveMap = new HashMap(4);

		if (isNotBlank(carName) && isNotBlank(ruleComment) && isNotBlank(modifiedBy)
				&& isNotBlank(carId)) {
			forceFulCarActiveMap.put("carName", carName);
			forceFulCarActiveMap.put("carId", carId);
			forceFulCarActiveMap.put("ruleComment", "RULE Forced Active with Comment: "+ruleComment);
			forceFulCarActiveMap.put("modifiedBy", modifiedBy);
		}

		if (null != forceFulCarActiveMap || forceFulCarActiveMap.size() > 0) {
			Integer sucessInd = null;
			try {
				sucessInd = getRoutingRulesService().forceFulActiveRule(
						forceFulCarActiveMap);
			} catch (Exception e) {
				logger.error("Returning back without activating the car rule: "+e.toString());
				msgDsp = "Unable to forceful active the rule <b>" + carName+" "+carId
						+ "</b>";
				getSession().setAttribute("msgString", msgDsp);
				return SUCCESS;
			}
			if (null != sucessInd) {
				logger.info("Sucessfully forcefully activated the car rule:"
						+ carId);
				msgDsp = "The rule <b>"
						+ carName
						+ "</b> has been successfully forceful <b>activated</b> ";
			}
		}

		else {
			logger.info("Returning back without activating the car rule: "
					+ carId);
			logger
					.info(" Unable to call Service Layer: forceFulCarActiveMap is null because any of these fields is null or empty carName,carId   ");
			msgDsp = "Unable to forceful active the rule <b>" + carName
					+ "</b>";
		}
		getSession().setAttribute("msgString", msgDsp);
		return SUCCESS;
	}
	// End of Force Active CAR
	
	/**
	 * Get all RoutingRuleHdr rules for the currently logged in buyer.
	 */
	public String getRoutingRuleHdr()
	{
		try {
			List<RoutingRuleHdr> list = getRoutingRulesService().getRoutingRulesHeaders(getContextBuyerId());			
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(list));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.error(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * Return all ruleHdr for the list of ruleId.
	 * @return String
	 */
	public String getRoutingRulesHeadersForRuleIds() {
		try {
			String ruleHdrIds = getRequest().getParameter("id");
			String ruleAction = getRequest().getParameter("ruleAction");
			String[] ruleIds = ruleHdrIds.split(",");
			String modifiedBy = getLoggedInName();
			List<String> ruleIdList = Arrays.asList(ruleIds);
			
			List<RoutingRuleHdr> list = getRoutingRulesService()
					.getRoutingRulesHeadersForRuleIds(ruleIdList);
			
			if ((RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE
					.equalsIgnoreCase(ruleAction))
					|| (RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED
							.equalsIgnoreCase(ruleAction))) {			
				model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(list));
			}
			if ((RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE.equalsIgnoreCase(ruleAction))) {
				Map<Integer,Map<Integer,List<RoutingRuleError>>> persistentRuleMap=getRoutingRulesService()
					.getRoutingRulesHeaderListForRuleAction(list,ruleAction,modifiedBy);
			
				Map<Integer,List<RoutingRuleError>> activeConflict=persistentRuleMap.get(1);
				Map<Integer,List<RoutingRuleError>> inactiveConflict=persistentRuleMap.get(2);
				for (RoutingRuleHdr ruleHdr : list) {
					if(ruleHdr!=null){
						List<RoutingRuleError> activeError=activeConflict.get(ruleHdr.getRoutingRuleHdrId());
						List<RoutingRuleError> inactiveError=inactiveConflict.get(ruleHdr.getRoutingRuleHdrId());

					if((activeError!=null && activeError.size()>0) || 
							(inactiveError!=null && inactiveError.size()>0)){
						ruleHdr.setInactiveConflict(RoutingRulesConstants.ROUTING_RULE_SUBSTATUS_CONFLICT);
						}
					}
				}
				getRequest().getSession().setAttribute("persistentRuleMap", persistentRuleMap);
				model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(list));
			}
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.error(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}
	
	/**
	 * To remove non persistent errors for a ruleId
	 */
	public String removeErrors()
	{
		try {
			
			String ruleHdrId = getRequest().getParameter("id");
			getRoutingRulesService().removeErrors(Integer.parseInt(ruleHdrId));		
			model.put(S_JSON_RESULT, S_NULL);
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.error(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}
	
	/**
	 * Return all ruleConflicts for a ruleId.
	 * @return String
	 */
	public String getRulesConflictsForRuleId() {
		try {
			String ruleHdrId = getRequest().getParameter("id");
			List<RuleConflictDisplayVO> ruleConflictlist = getRoutingRulesService()
					.getRoutingRulesConflictsForRuleId(Integer.parseInt(ruleHdrId));
	
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(ruleConflictlist));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.error(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}
	
	/**
	 * Return all ruleError for a ruleId.
	 * @return String
	 */
	public String getRulesErrorForRuleId() {
		try {
			String ruleHdrId = getRequest().getParameter("id");
			List<RoutingRuleErrorVO> ruleErrorlist = getRoutingRulesService()
					.getRoutingRuleErrors(Integer.parseInt(ruleHdrId));
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(ruleErrorlist));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.error(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}
	/**
	 * Get all RoutingRuleHdrHist rows for the currently logged in buyer.
	 */
	public String getRoutingRuleHdrHist() {
		try {
			// Handle Pagination
			RoutingRulesPaginationVO pagingCriteria = (RoutingRulesPaginationVO)getSession().getAttribute("ruleHistPagination");
			String pageNumber[] = (String[])getRequest().getAttribute("pageNo");
			if (pagingCriteria != null) {
				if (pageNumber != null) {
					Integer pageNo = Integer.parseInt(pageNumber[0]);
					pagingCriteria.setPageRequested(pageNo);
				} else {
					pagingCriteria = null;
				}
			}
			boolean archiveIndicator = false;
			String[] archiveInd = (String[])getRequest().getAttribute("archive");
			if (archiveInd!= null && Integer.parseInt(archiveInd[0])==1)
			{
				archiveIndicator = true;
				// pagingCriteria = null;
				getRequest().setAttribute("archiveInd",1);				
			}
			else
			{
				archiveIndicator = false;
				// pagingCriteria = null;
				getRequest().setAttribute("archiveInd",0);
			}
			Integer buyerId = getContextBuyerId();
			pagingCriteria = getRoutingRulesPaginationService().setRuleHistoryPagination(buyerId, pagingCriteria, archiveIndicator);
			// Handle Sorting Parameters
			String[] sortCol = (String[])getRequest().getAttribute("sortCol");
			if (sortCol != null) {
				pagingCriteria.setSortCol(Integer.parseInt(sortCol[0]));
			}
			String[] sortOrd = (String[])getRequest().getAttribute("sortOrd");
			if (sortOrd != null) {
				pagingCriteria.setSortOrd(Integer.parseInt(sortOrd[0]));
			}
			
			
			//logger.info("SortCol = " + pagingCriteria.getSortCol() + " SortOrd = " + pagingCriteria.getSortOrd());
			List<String> comments = new ArrayList<String>();
			List<RoutingRuleHdrHist> domainObjs = getRoutingRulesService().getRoutingRuleHeadersHist(buyerId, pagingCriteria, archiveIndicator);
			getSession().setAttribute("ruleHistPagination", pagingCriteria);
			for(int i=0;i<domainObjs.size();i++){
				RoutingRuleHdrHist hist = domainObjs.get(i);
				String comment = hist.getRuleComment();
				if(comment != null){
					comment = comment.replaceAll("\n","<br />");
					comments.add(comment);
				}else{
					comments.add("");
				}
			}

			List<RoutingRulesHdrHistVO> vos = RoutingRulesHdrHistVO.getVOFromDomainObjects(domainObjs, comments);
			model.put("listRoutingRuleHdrHist",vos);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(vos));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.error(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}

	
	/**
	 * Query DB for details on ProviderFirm for given firm ID and return as JSON. 'id' is passed in
	 * request.
	 */
	public String getFirmDetails()
	{
		try {
			String sFirmId = getRequest().getParameter("id");
			Integer firmId = Integer.valueOf(sFirmId);
			model.put(S_JSON_RESULT, getRoutingRulesService().getProviderFirmsJson(firmId));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}


	/**
	 * Return city/state for given zip. Used when adding a single zipcode.
	 * 
	 * @return Stuffs a string into model.jsonResult, with value of the format "00000 - City, ST"
	 */
	public String getCityByZipcode()
	{
		try {
			String zip = getRequest().getParameter("zip");
			String bundle = getRoutingRulesService().getCityByZipcode(zip);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(bundle));			
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;

	}


	/**
	 * Return list of cities with their state abbrev.
	 * 
	 * @return Stuffs list of LabelValueBeans into model.jsonResult, where label is city.
	 */
	public String getZipcodesByStateAbbrev()
	{
		try {
			String stateAbbrev = getRequest().getParameter("state").toString();  // fail fast			
			List<LabelValueBean> list = getRoutingRulesService().getZipcodesByStateAbbrev(stateAbbrev);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(list));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}


	/**
	 * 
	 * @return
	 */
	public String getJobcodesForSpecialtyCode()
	{
		try {
			String spec = getRequest().getParameter("spec");
			List<LabelValueBean> list = getRoutingRulesService().getJobcodesForSpecialtyCode(getContextBuyerId(), spec);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(list));
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;
	}
	
	
	public String getRuleCriterion() 
	{
		try {
			String rid = getRequest().getParameter("rid");
			Integer ruleId= new Integer(rid);
			String jsonResult= getRoutingRulesService().getRuleCriteriaJson(ruleId);
			model.put(S_JSON_RESULT, jsonResult);
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;		
	}


	public String getRuleSpecsJobs() 
	{
		try {
			String rid = getRequest().getParameter("rid");
			Integer ruleId= new Integer(rid);
			/*
			 * Commented as part of SL-15175
			 */
			//String jsonResult= getRoutingRulesService().getRuleSpecsJobsJson(ruleId);
			String jsonResult="";
			model.put(S_JSON_RESULT, jsonResult);
		} catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn(ERR_JSON_GENERATE, e);
		}
		return SUCCESS;		
	}

	
	public String persistErrors(){
		try {
			String ruleHdrIds = getRequest().getParameter("ruleId");
			String[] ruleIds = ruleHdrIds.split(",");
			List<String> ruleIdList = Arrays.asList(ruleIds);
			String modifiedBy = getLoggedInName();
			String comment = getRequest().getParameter("comment");
			Map<Integer,Map<Integer,List<RoutingRuleError>>> errorMap =  (Map<Integer, Map<Integer, List<RoutingRuleError>>>) 
				getRequest().getSession().getAttribute("persistentRuleMap");
			if(errorMap != null && errorMap.size()>0){
				getRoutingRulesService().persistErrors(errorMap, ruleIdList, comment, modifiedBy);
			}
			getRequest().getSession().removeAttribute("persistentRuleMap");
			model.put(S_JSON_RESULT, S_NULL);
		}catch (Throwable e) {
			model.put(S_JSON_RESULT, S_NULL);
			log.warn("Exception while persisting the rule errors", e);
		}
		return SUCCESS;
	}
		
	


	/**
	 * Set status of rule <tt>ruleId</tt> to <tt>newState</tt>, and update
	 * <tt>comment</tt>.
	 * 
	 * @return null on success, or else an error string.
	 */
	public String setRuleStatus() {
		String jsonResult = S_NULL;
		final JSONSerializer serializer = new JSONSerializer();
		try {
			String comment = getRequest().getParameter("comment");
			String newState = getRequest().getParameter("newState");
			final String status;
			String ruleHdrIds = getRequest().getParameter("ruleId");
			String[] ruleIds = ruleHdrIds.split(",");
			List<String> ruleIdList = Arrays.asList(ruleIds);
			if (RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE
					.equalsIgnoreCase(newState)) {
				status = RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE;
			} else if (RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE
					.equalsIgnoreCase(newState)) {
				status = RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE;
			} else if (RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED
					.equalsIgnoreCase(newState)) {
				status = RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED;
			} else {
				status = null;
			}
			if (ruleHdrIds == null || status == null) {
				throw new Exception("Required:  ruleId: " + ruleHdrIds
						+ ", newState: " + newState);
			}
			String modifiedBy = getLoggedInName();
			getRoutingRulesService().updateRoutingRuleHdrStatusAndComment(
					ruleIdList, comment, status, modifiedBy);
		} catch (Throwable e) {
			log.error(ERR_JSON_GENERATE, e);
			jsonResult = serializer.serialize(e.getMessage());
		}
		model.put(S_JSON_RESULT, jsonResult);
		return SUCCESS;
	}
	
	/**
	 * To find invalid Job Prices
	 */
	public String jobPriceCheck()
	{
		try {
			try {
				List<JobCodeVO> jobMainList = (List<JobCodeVO>) getSession().getAttribute("jobPriceMainList");
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
						if(null!=jobMainList && jobMainList.size()>0){
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
				}
				boolean invalidPriceFound = false;
				if(null!=jobMainList && jobMainList.size()>0){
					for(JobCodeVO jobPriceVO:jobMainList){
						if(jobPriceVO.getPrice()==null){
							invalidPriceFound = true;
							break;
						}
					}
				}
				if(null!=jobMainList && jobMainList.size()>0){
					getSession().setAttribute("jobPriceMainList",jobMainList);
				}
				model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(invalidPriceFound));
				return SUCCESS;
				
			} catch (Exception ex) {
				log.error("UNABLE TO PERSIST DATA.", ex);
				return ERROR;
			}
			
		} catch (Exception ex) {
			log.error("UNABLE TO PERSIST DATA.", ex);
			return ERROR;
		}

	}
	
	/**
	 * To find conflicts for a rule
	 */
	public String conflictCheck()
	{
		try {
			try {
				HttpServletRequest request = getRequest();
				//Boolean isEdit= Boolean.FALSE;
				Integer contextBuyerId = getContextBuyerId();
				String modifiedBy = getLoggedInName();
				List<String> zipMainList = (List<String>) getSession().getAttribute("zipMainList");
				List<String> custMainList = (List<String>) getSession().getAttribute("custRefMainList");
				List<JobCodeVO> jobMainList = (List<JobCodeVO>) getSession().getAttribute("jobPriceMainList");
				Map<String, Object> map = new HashMap<String, Object>();
				String ruleId= request.getParameter("rid");
				if (null != ruleId && ruleId.length()>0) {
					map.put("ruleId", ruleId);
					//isEdit= Boolean.TRUE;
				}
				//Add code for updating jobPrice
				map.put("chosenCustomRefs", custMainList);
				map.put("chosenMarketsZips", zipMainList);
				map.put("chosenJobCodes", jobMainList);
				String errorListJson = null;
				List<RuleConflictDisplayVO> errorList = new ArrayList<RuleConflictDisplayVO>();
				errorList = getRoutingRulesService().updateRoutingRuleAndCheckConflicts(map, contextBuyerId, modifiedBy);
				errorListJson = getRoutingRulesService().getJSON(errorList);
				model.put(S_JSON_RESULT, errorListJson);
				return SUCCESS;
				
			} catch (Exception ex) {
				log.error("UNABLE TO PERSIST DATA.", ex);
				return ERROR;
			}
			
		} catch (Exception ex) {
			log.error("UNABLE TO PERSIST DATA.", ex);
			return ERROR;
		}

	}
}



