package com.servicelive.spn.buyer.network;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupReleaseTier;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.detached.LookupCriteriaDTO;
import com.servicelive.domain.spn.detached.SPNCoverageDTO;
import com.servicelive.domain.spn.detached.SPNPerformanceCriteria;
import com.servicelive.domain.spn.network.ReleaseTiersVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.domain.spn.network.SPNTierPK;
import com.servicelive.domain.spn.network.SPNTierPerformanceLevel;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.buyer.SPNBuyerServices;
import com.servicelive.spn.services.network.CreateNetworkServices;
import com.servicelive.spn.services.network.SPNRoutingPriorityServices;
import com.servicelive.domain.spn.detached.SPNTierDTO;
import com.servicelive.domain.spn.detached.SPNRoutingDTO;

public class SPNReleaseTiersTabAction extends SPNBaseAction implements ModelDriven<SPNReleaseTierModel> {

	private static final long serialVersionUID = 20090529L;

	private SPNBuyerServices spnBuyerServices;
	protected CreateNetworkServices createSPNServices;	
	private LookupService lookupService;
	private SPNReleaseTierModel model = new SPNReleaseTierModel();
	private static String TIER_LIST = "tierList";
	private static String SHOW_PERF_LEVEL_LIST = "showPerfLevelList";
	private static String MODEL = "MODEL_TIER";
	private static String TR_MIN_DELAY_TIME = "TR_MIN_DELAY_TIME";
	private Integer minMinutes = null;
	
	private SPNRoutingPriorityServices spnRoutingPriorityService;
	private static String SAVED_DTO = "savedRoutingDTO";
	private static String FILTERED_COVERAGE_DTO = "filteredCoverageDTO";
	private static String SAVE = "save";
	private static String CALCULATE = "calculate";
	private static String UPDATE = "update";
	
	public String display() throws Exception {
		if(model.getValidatefail().booleanValue() != false) {
			
			getRequest().getSession().removeAttribute(SAVED_DTO);
			getRequest().getSession().removeAttribute(FILTERED_COVERAGE_DTO);
		    //if this is null then revalidate your logic whereever u set it
			SPNReleaseTierModel sessionModel = ( SPNReleaseTierModel) getRequest().getSession().getAttribute(MODEL);
		    this.setActionMessages(sessionModel.getActionErrors());
		   // model.setSpnBeingEdited(sesiionmodel.getSpnId());
		    getModel().setSpnBeingEdited(sessionModel.getSpnBeingEdited());
		    getModel().setSpnId(sessionModel.getSpnId());
		    getModel().setTiersToSave(sessionModel.getTiersToSave());
		    setTierList(sessionModel.getTiersToSave());
		    getRequest().getSession().removeAttribute(MODEL);
		}
		initDropdownMenus();
		
		if(getRequest().getSession().getAttribute("ALL_PERF_LEVELS") == null ) 
		getRequest().getSession().setAttribute("ALL_PERF_LEVELS", getLookupService().getAllPerformanceLevels());
		
		//resetShowPerfLevelList(); //  to make sure we have consumed all available per Levels
		//setPerfLevelAvailable();  //to diaply  Assign tiers Button

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private List<LabelValueBean> calculateBalancePerfLevels() throws Exception {
		List<LabelValueBean> balancePerflevels = new ArrayList<LabelValueBean>();
		List<LookupPerformanceLevel> allAvailable = (List<LookupPerformanceLevel>) getRequest()
				.getSession().getAttribute("ALL_PERF_LEVELS");
		List<ReleaseTiersVO> tiers = getTierList();

		for (LookupPerformanceLevel level : allAvailable)
		{
			LabelValueBean lvBean = new LabelValueBean();
			lvBean.setLabel((String)level.getDescription());
			lvBean.setValue(String.valueOf(level.getId()));
			
			balancePerflevels.add(lvBean);
		}
		
		// Find the performance levels we have used and remove them from the balancePerflevels list
		List<LabelValueBean> perfLevelsToDelete = new ArrayList<LabelValueBean>();
		if (tiers != null) {
			Integer perfLevelId;
			for (ReleaseTiersVO tier : tiers) {
				List<Integer> pflevels = tier.getTierPerformanceLevels();
				for (Integer level : pflevels)
				{
					for(LabelValueBean bal : balancePerflevels)
					{
						perfLevelId = Integer.valueOf(bal.getValue());
						if (perfLevelId.intValue() == level.intValue())
						{
							// Cannot directly delete from balancePerfLevels
							// since we are looping thru this right now.
							// Delete outside of this loop.
							perfLevelsToDelete.add(bal);
						}
					}
				}
			}
			
			for(LabelValueBean deleteMe :perfLevelsToDelete)
			{
				balancePerflevels.remove(deleteMe);
			}
			
		}
		
		Collections.reverse(balancePerflevels);
		
		
		
		return balancePerflevels;
	}
	
	
/*	private List<Integer> getAlreadyConsumedPerflevels() { 
		
	}*/
	
	private void resetShowPerfLevelList() throws Exception{
		List<LabelValueBean> showPerfLevelList = calculateBalancePerfLevels();
		setAvailablePerfList(showPerfLevelList);
		
		if(getRequest().getSession().getAttribute(TR_MIN_DELAY_TIME) == null)
		{
			getRequest().getSession().setAttribute(TR_MIN_DELAY_TIME, Integer.valueOf(getSpnTierService().getMinimumDelayMinutes()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public String buttonAddTierAjax() throws Exception {

		/*List <Integer> checekdPerfLevels = model.getAvPfCb();
		Integer spnId = model.getSpnId();
		if(!validateForSPNId(spnId).booleanValue()) {
			return INPUT;
		}
		List<ReleaseTiersVO> tierList = getModel().getTiersToSave();
		if(tierList == null)
		{
			tierList = new ArrayList<ReleaseTiersVO>();
		}
		
		setMinMinutes(Integer.valueOf(getSpnTierService().getMinimumDelayMinutes()));
		
		// Handle the available performance levels
		setShowPerformanceLevelList(tierList,checekdPerfLevels);
		setTierList(tierList);
		getModel().setTiersToSave(getTierList());
		resetShowPerfLevelList();
		setPerfLevelAvailable();*/
		getRequest().getSession().removeAttribute(FILTERED_COVERAGE_DTO);
		boolean saveInd = Boolean.parseBoolean(getRequest().getParameter("saveInd"));
		boolean calculate = Boolean.parseBoolean(getRequest().getParameter("calculate"));
		boolean updateStatus = Boolean.parseBoolean(getRequest().getParameter("updateStatus"));
		boolean edit = Boolean.parseBoolean(getRequest().getParameter("edit"));
		boolean coverage = Boolean.parseBoolean(getRequest().getParameter("coverage"));
		String returnValue = "";
		if(coverage){
			returnValue = showCoverage();
		}
		else if(saveInd){
			returnValue = saveRoutingPriority();
		}
		else if(calculate){
			returnValue = calculateScores();
		}
		else if(updateStatus){
			returnValue = updateRoutingStatus();
		}
		else if(edit){
			returnValue = editRoutingPriority();
		}
		else if(!edit){
			returnValue = editRoutingPriority();
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	private void  setShowPerformanceLevelList(List<ReleaseTiersVO> tierList ,  List <Integer> checekdPerfLevels ) throws Exception {
	//  for(Integer level :checekdPerfLevels  ){
		  ReleaseTiersVO tier = new ReleaseTiersVO();
		  tier.setTierId(Integer.valueOf(getMaxTierId(tierList)+ 1));
		  tier.setTierPerformanceLevels(checekdPerfLevels);
		  SPNTierMinutes spnMinutes = new SPNTierMinutes();
		  spnMinutes.setAdvancedMinutes(Integer.valueOf(getSpnTierService().getMinimumDelayMinutes()));
		  tier.setTierMinute(spnMinutes);
		  tierList.add(tier);
	 // }
		
	}
	
	private int getMaxTierId(List<ReleaseTiersVO> tierList ){
		 return tierList.size();
	}
	
	
	

	
	@SuppressWarnings("unchecked")
		@Transactional ( propagation = Propagation.REQUIRED)
	public String buttonDeleteAllAjax() throws Exception {
		
		
		/*Integer spnId = model.getSpnId();		
		//Integer spnBeingEdited = model.getSpnBeingEdited();
		model.setDeleteAllFlag(Boolean.TRUE);
		getCreateSPNServices().removeExistingTiers(spnId);
		//If deletd is zero.. we need to send information message
		setTierList(new ArrayList<ReleaseTiersVO>());
		return SUCCESS;*/
		boolean reload = Boolean.parseBoolean(getRequest().getParameter("reloadInd"));
		String returnValue="";
		if(reload){
			return "footer";
		}
		returnValue = filterCoverage();
		return returnValue;
	}
	
	private void setTierList(List<ReleaseTiersVO> tierList) throws Exception
	{
		List<ReleaseTiersVO> sorted = new ArrayList<ReleaseTiersVO>(tierList);
		if(sorted.size()> 0 ) Collections.sort(sorted);
		getRequest().getSession().setAttribute(TIER_LIST, sorted);
	}
	
	@SuppressWarnings("unchecked")
	private List<ReleaseTiersVO> getTierList()
	{
		return (List<ReleaseTiersVO>)getRequest().getSession().getAttribute(TIER_LIST);
	}
	
	@SuppressWarnings("unchecked")
	private void setAvailablePerfList(List<LabelValueBean> availableperfList) {
		getRequest().getSession().setAttribute(SHOW_PERF_LEVEL_LIST,availableperfList);
	}
	
	@SuppressWarnings("unchecked")
	private List<LabelValueBean> getAvailablePerList() {
		return (List<LabelValueBean>) getRequest().getSession().getAttribute(SHOW_PERF_LEVEL_LIST);
	}

	@SuppressWarnings("unchecked")
	private void  setPerfLevelAvailable()
	{
		/*List<Integer> showPerfList = (List<Integer>)getRequest().getSession().getAttribute(SHOW_PERF_LEVEL_LIST);*/
		
		List<LabelValueBean> perfList = getAvailablePerList();
		
		if( perfList.size() > 0 ) {
			getRequest().setAttribute("atLeastOnePerfLevelAvail", Boolean.TRUE);
		}

	}
	


	
	public String buttonLoadTierAjax() throws Exception {
		/*Integer spnId =  getModel().getSpnId();
		SPNHeader hdr = getCreateSPNServices().getNetwork(spnId);
		List<ReleaseTiersVO> tiersFromBE =  getTierList();
		if((getModel().getDeleteAllFlag() != null && (getModel().getDeleteAllFlag().booleanValue() == false))&& getModel().getValidatefail().booleanValue() == false){
			 tiersFromBE = getCreateSPNServices().getReleaseTiers(spnId);
			if(tiersFromBE != null && tiersFromBE.size() > 0 ) {
				getModel().setSpnBeingEdited(spnId);
			}
		}
		
		setMinMinutes(Integer.valueOf(getSpnTierService().getMinimumDelayMinutes()));
		
		setTierList(tiersFromBE);
		getModel().setTiersToSave(getTierList());
		getModel().setMarketplaceOverflow(hdr.getMarketPlaceOverFlow() == Boolean.TRUE ? new Integer(1) : new Integer(0));
		resetShowPerfLevelList();
		setPerfLevelAvailable();*/
		
		String returnValue = checkRoutingPriority();
		return returnValue;
	}
		

	public String saveAndDone() throws Exception {
		

		model = getModel();
		Integer spnId = model.getSpnId();
		if(!validateForSPNId(spnId).booleanValue()) {
			return INPUT;
		}
		if(!validateForMinutesAndPeflevel(model.getTiersToSave()).booleanValue()){
			return INPUT;
		}

		saveTiers();
		return SUCCESS;

	}
	
	
	private Boolean validateForSPNId(Integer spnId){
		if (spnId == null || spnId.equals("-1")) {
			this.addActionError(this.getText("errors.network.routingtiers.select.spn"));
			updateModelForvalidationErrors();
			
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	private Boolean validateForMinutesAndPeflevel(List<ReleaseTiersVO> tiersToSave) throws Exception{
		if (tiersToSave !=  null && tiersToSave.size() > 0 ) {
			boolean iserror = false;
			for(ReleaseTiersVO tier : tiersToSave) {
				if(tier.getTierPerformanceLevels().size() <=0 ) {
					this.addActionError(this.getText("errors.network.routingtiers.select.perf.level"));
					iserror = true;
					break;
				}
			}
			for(ReleaseTiersVO tier : tiersToSave){
				SPNTierMinutes minutes = tier.getTierMinute();
				if(!isMinutesSelectionAboveMinMinutes(minutes).booleanValue()){					
					String msg = getText("errors.network.routingtiers.select.release.please.enter") +
					this.getSpnTierService().getMinimumDelayMinutes() + " " +
					getText("errors.network.routingtiers.select.release.minutes");
					
					this.addActionError(msg);
					iserror = true;
				}
			}
			if(iserror) {
				updateModelForvalidationErrors();
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
		else if ( tiersToSave != null && tiersToSave.size()  == 0 ){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@SuppressWarnings("unchecked")
	private void updateModelForvalidationErrors() {
		//why I m adding this after adding to struts actionErrorList cuz its posible the struct action error may have some thing before this one we want all of it
		model.getActionErrors().addAll(this.getActionErrors());
		model.setSpnBeingEdited(model.getSpnId());
		model.setValidatefail(Boolean.TRUE);
		getRequest().getSession().setAttribute(MODEL,model);
	}
	
	private Boolean isMinutesSelectionAboveMinMinutes(SPNTierMinutes minutes) throws Exception {
		Boolean result = Boolean.FALSE;
		int minDelayMinutes = this.getSpnTierService().getMinimumDelayMinutes();
		if(minutes != null ) {
			if( minutes.getAdvancedDays().intValue() > 0 ) return Boolean.TRUE;
			else if ( minutes.getAdvancedHours().intValue() > 0 )  return Boolean.TRUE;
			else if ( minutes.getAdvancedMinutes().intValue() >= minDelayMinutes ) return Boolean.TRUE;
		}
		return result;
		
	}
	
	private void   saveTiers() throws Exception {
		
		//First delet all the tiers
		getCreateSPNServices().removeExistingTiers(model.getSpnId());
		SPNHeader spnHeader = getCreateSPNServices().getNetwork(model.getSpnId());
		spnHeader.setTierMinutes(new ArrayList<SPNTierMinutes>());
		spnHeader.setPerformanceLevels(new ArrayList<SPNTierPerformanceLevel>());

		List<ReleaseTiersVO> tiers = model.getTiersToSave();
			for ( ReleaseTiersVO tier : tiers) {

				SPNTierMinutes minute = tier.getTierMinute();
				LookupReleaseTier lu_tier = new LookupReleaseTier();
				lu_tier.setId(tier.getTierId());
				SPNTierPK pk = new SPNTierPK();
				pk.setSpnId(spnHeader);
				pk.setTierId(lu_tier);
				minute.setSpnTierPK(pk);
				minute.setModifiedBy(getCurrentBuyerResourceUserName());
				spnHeader.getTierMinutes().add(minute);
				for(Integer  pflevel : tier.getTierPerformanceLevels()) {
					
					SPNTierPerformanceLevel level = new SPNTierPerformanceLevel();
					LookupPerformanceLevel lu_level = new LookupPerformanceLevel();
					lu_level.setId(pflevel);
					level.setModifiedBy(getCurrentBuyerResourceUserName());
					level.setTierId(lu_tier);
					level.setSpnId(spnHeader);
					level.setPerformanceLevelId(lu_level);
					spnHeader.getPerformanceLevels().add(level);
				}
				
			}
			
			spnHeader.setMarketPlaceOverFlow(model.getMarketplaceOverflow()!= null && (model.getMarketplaceOverflow().intValue() >= 1) ? Boolean.TRUE : Boolean.FALSE);
			getCreateSPNServices().updateSPN(spnHeader);
	}

	
	private void initDropdownMenus() throws Exception {

		Integer buyerId = getLoggedInUser().getUserId();

		// Set the SPN dropdown list
		List<SPNHeader> spnList = spnBuyerServices.getSPNListForBuyer(buyerId);
		
		List<LabelValueBean> lvBeanList = new ArrayList<LabelValueBean>();
		LabelValueBean header;
		for(SPNHeader spn : spnList)
		{
			header = new LabelValueBean();
			header.setLabel(spn.getSpnName());
			header.setValue(spn.getSpnId() + "");
			lvBeanList.add(header);
		}

		Collections.sort(lvBeanList);
		getModel().setSpnList(lvBeanList);
		
	}
	
	//checks whether routing priority is configured for the SPN or not 
	public String checkRoutingPriority()throws Exception {
		
		Integer spnId = model.getSpnId();
		SPNRoutingDTO routingDTO = new SPNRoutingDTO();
		SPNHeader spnHdr = null;
		String returnValue = "";
		try{
			getRequest().getSession().removeAttribute(SAVED_DTO);
			getRequest().getSession().removeAttribute(FILTERED_COVERAGE_DTO);
			spnHdr = spnRoutingPriorityService.checkForCriteria(spnId);
			routingDTO.setSpnHdr(spnHdr);
			
			//to fetch look up criteria
			List<LookupCriteriaDTO> lookUpCriteria = spnRoutingPriorityService.fetchLookupCriteria();
			routingDTO.setCriteriaDTO(lookUpCriteria);		
			routingDTO.setCriteriaCount(lookUpCriteria.size());
			
			//to fetch the date of performance data modification
			Date modifiedDate = spnRoutingPriorityService.fetchModifiedDate();
			routingDTO.setModifiedDate(modifiedDate);
			
			//check for alias
			Integer aliasSpnId = spnRoutingPriorityService.checkForAlias(spnId);
			if(null != aliasSpnId){
				routingDTO.setAliasSpnId(aliasSpnId);
			}
			
			if(null != spnHdr){
				String priorityStatus = spnHdr.getRoutingPriorityStatus();
				if(SPNBackendConstants.NA.equals(priorityStatus)){
					returnValue = SPNBackendConstants.EDIT;
					routingDTO.setAction(returnValue);
					getModel().setRoutingDTO(routingDTO);
					getRequest().getSession().setAttribute(SAVED_DTO, routingDTO);
					
				}else{	
					//fetch the spn performance criteria
					List<SPNPerformanceCriteria> criteria = spnRoutingPriorityService.fetchCriteria(spnId);
					//setting the tiers
					List<SPNTierDTO> tiers = spnRoutingPriorityService.fetchTiers(spnId);
					List<SPNTierDTO> tierList = new ArrayList<SPNTierDTO>();
					if(null != tiers && tiers.size()>0){
						int tierCount = SPNBackendConstants.NO_OF_TIERS;
						for(int i=1; i<=tierCount; i++){
							int ind = 0;
							for(SPNTierDTO tier : tiers){
								if(null != tier){
									if(i == tier.getTierId()){
										if(tier.getHours() != 0){
											tier.setTime(tier.getHours());
											tier.setUnit(SPNBackendConstants.HOURS);
										}
										else if(tier.getDays() != 0){
											tier.setTime(tier.getDays());
											tier.setUnit(SPNBackendConstants.DAYS);
										}
										else{
											tier.setTime(tier.getMinutes());
											tier.setUnit(SPNBackendConstants.MINUTES);
										}	
										ind = 1;
										tierList.add(tier);
										break;
									}
									
								}
							}
							if(0 == ind){
								SPNTierDTO tier = new SPNTierDTO();
								tier.setTierId(i);
								tier.setUnit(SPNBackendConstants.MINUTES);
								tierList.add(tier);
							}
						}
					}
					//fetching the coverage details
					List<SPNCoverageDTO> coverageList = new ArrayList<SPNCoverageDTO>();
					if(SPNBackendConstants.PROVIDER.equals(spnHdr.getCriteriaLevel())){
						//need to modify to check for alias
						long start = System.currentTimeMillis();
						coverageList = spnRoutingPriorityService.getProviderCoverage(spnId, aliasSpnId);
						long end = System.currentTimeMillis();
						logger.info("checkRoutingPriority >> getProviderCoverage"+(end-start));
						if(null != coverageList && !coverageList.isEmpty()){
							routingDTO.setProviderCount(coverageList.size());
						}
					}
					else if(SPNBackendConstants.FIRM.equals(spnHdr.getCriteriaLevel())){
						//need to modify to check for alias
						long start = System.currentTimeMillis();
						coverageList = spnRoutingPriorityService.getFirmCoverage(spnId, aliasSpnId);
						long end = System.currentTimeMillis();
						logger.info("checkRoutingPriority >> getFirmCoverage"+(end-start));
						if(null != coverageList && coverageList.size()>0){
							routingDTO.setFirmCount(coverageList.size());
							//to get the eligible provider count
							Map<Integer, Integer> firmMap = new HashMap<Integer, Integer>();
							for(SPNCoverageDTO coverage : coverageList){
								if(null != coverage){
									firmMap.put(coverage.getMemberId(),coverage.getSpnId());
								}
							}
							//setting the eligible providers count
							//need to modify to check for alias
							long start1 = System.currentTimeMillis();
							List<SPNCoverageDTO> provCountList = spnRoutingPriorityService.getProvCount(firmMap, spnId, routingDTO.getAliasSpnId());
							long end1 = System.currentTimeMillis();
							logger.info("checkRoutingPriority >> getProvCount"+(end1-start1));
							int providerCount = 0;
							for(SPNCoverageDTO coverage : coverageList){
								if(null != coverage){
									for(SPNCoverageDTO provCount : provCountList){
										if(null != provCount && coverage.getMemberId().intValue() == provCount.getFirmId().intValue()){
											coverage.setNoOfEligibleProvs(provCount.getNoOfEligibleProvs());
											break;
										}
									}
									providerCount = providerCount + coverage.getNoOfEligibleProvs();
								}
							}
							routingDTO.setProviderCount(providerCount);
						}
					}
					//setting market and state lists
					if(null != coverageList && coverageList.size() != 0 ){
						Map<String, String> states = new TreeMap<String, String>();
						Map<String, Integer> markets = new TreeMap<String, Integer>();
						for(SPNCoverageDTO coverage : coverageList){
							if(null != coverage){
								states.put(coverage.getStateName(),coverage.getState());
								markets.put(coverage.getMarket(), coverage.getMarketId());
							}
						}
						routingDTO.setStateList(states);
						routingDTO.setMarketList(markets);
					}
					routingDTO.setPerformanceCriteria(criteria);
					routingDTO.setSpnTiers(tierList);
					routingDTO.setCoveragelist(coverageList);
					returnValue = SPNBackendConstants.VIEW;
					routingDTO.setAction(returnValue);
					getModel().setRoutingDTO(routingDTO);
					getRequest().getSession().setAttribute(SAVED_DTO, routingDTO);
					
				}
			}
		}
		catch(BusinessServiceException e){
			logger.error("Exception in SPNReleaseTiersTabAction checkRoutingPriority() due to "+ e.getMessage());
		}
		return returnValue;
	}
	
	//calculates the performance score for SPN firms/providers
	public String calculateScores()throws Exception {
		
		SPNRoutingDTO routingDTO = getModel().getRoutingDTO();
		Integer spnId = routingDTO.getSpnHdr().getSpnId();
		String criteriaLevel = routingDTO.getSpnHdr().getCriteriaLevel();
		String criteriaTimeframe = routingDTO.getSpnHdr().getCriteriaTimeframe();
		List<SPNPerformanceCriteria> performanceCriteria = routingDTO.getPerformanceCriteria();
		int criteriaCount = 1;
		List<SPNCoverageDTO> coveragelist = null;
		List<Integer> memberList = new ArrayList<Integer>();
		Map<Integer, Integer> memberMap = null;
		List<LookupCriteriaDTO> generalScores = null;
		List<SPNCoverageDTO> provCountList = null;
		boolean calculate = true;
		
		try{
			if(null != performanceCriteria && performanceCriteria.size() != 0){
				performanceCriteria = getCriteriaList(performanceCriteria, criteriaTimeframe);
				criteriaCount = performanceCriteria.size();
			}
			
			SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);
			
			if(calculate){				
				if(null != savedDTO && null != savedDTO.getSpnTiers()){
					routingDTO.setSpnTiers(savedDTO.getSpnTiers());
					if(savedDTO.getSavedCriteria().size() == 0 ){
						routingDTO.setSavedCriteria(savedDTO.getPerformanceCriteria());
						routingDTO.setSavedCriteriaLevel(savedDTO.getSpnHdr().getCriteriaLevel());
						routingDTO.setSavedCriteriaTimeframe(savedDTO.getSpnHdr().getCriteriaTimeframe());
						routingDTO.setSavedCoverage(savedDTO.getCoveragelist());
						routingDTO.setSavedProvCount(savedDTO.getProviderCount());
						routingDTO.setSavedFirmCount(savedDTO.getFirmCount());
						routingDTO.setSavedMarkets(savedDTO.getMarketList());
						routingDTO.setSavedStates(savedDTO.getStateList());
						routingDTO.setSavedZips(savedDTO.getZipList());
					}
					else{
						routingDTO.setSavedCriteria(savedDTO.getSavedCriteria());
						routingDTO.setSavedCriteriaLevel(savedDTO.getSavedCriteriaLevel());
						routingDTO.setSavedCriteriaTimeframe(savedDTO.getSavedCriteriaTimeframe());
						routingDTO.setSavedCoverage(savedDTO.getSavedCoverage());
						routingDTO.setSavedProvCount(savedDTO.getSavedProvCount());
						routingDTO.setSavedFirmCount(savedDTO.getSavedFirmCount());
						routingDTO.setSavedMarkets(savedDTO.getSavedMarkets());
						routingDTO.setSavedStates(savedDTO.getSavedStates());
						routingDTO.setSavedZips(savedDTO.getSavedZips());
					}
					
					routingDTO.setCriteriaDTO(savedDTO.getCriteriaDTO());
					routingDTO.setCriteriaCount(savedDTO.getCriteriaCount());
					routingDTO.getSpnHdr().setMarketPlaceOverFlow(savedDTO.getSpnHdr().getMarketPlaceOverFlow());
					routingDTO.setAction(savedDTO.getAction());
					routingDTO.setModifiedDate(savedDTO.getModifiedDate());
					routingDTO.setAliasSpnId(savedDTO.getAliasSpnId());
				}
				if(SPNBackendConstants.PROVIDER.equals(criteriaLevel)){
					//fetch approved providers
					//need to modify to check for alias
					long start = System.currentTimeMillis();
					memberList = spnRoutingPriorityService.fetchSPNProviders(spnId, routingDTO.getAliasSpnId());
					long end = System.currentTimeMillis();
					logger.info("calculateScores >> fetchSPNProviders"+(end - start));
					if(null != memberList && memberList.size() != 0){
						//get the general score for each criteria for all providers
						long start1 = System.currentTimeMillis();
						generalScores = spnRoutingPriorityService.getProviderScores(performanceCriteria, memberList);
						long end1 = System.currentTimeMillis();
						logger.info("calculateScores >> getProviderScores"+(end1 - start1));
						//fetch the coverage details
						//need to modify to check for alias
						long start2 = System.currentTimeMillis();
						coveragelist = spnRoutingPriorityService.getProviderCoverage(spnId, routingDTO.getAliasSpnId());
						long end2 = System.currentTimeMillis();
						logger.info("calculateScores >> getProviderCoverage"+(end2 - start2));
						if(null != coveragelist){
							routingDTO.setProviderCount(coveragelist.size());							
						}
					}
				}
				else if (SPNBackendConstants.FIRM.equals(criteriaLevel)) {
					//fetch approved firms
					//need to modify to check for alias
					long start = System.currentTimeMillis();
					memberMap = spnRoutingPriorityService.fetchSPNFirms(spnId, routingDTO.getAliasSpnId());
					long end = System.currentTimeMillis();
					logger.info("calculateScores >> fetchSPNFirms"+(end - start));
					if(null != memberMap && memberMap.size() > 0){
						for(Entry<Integer, Integer> member : memberMap.entrySet()){
							if(null != member){
								memberList.add(member.getKey());
							}
						}
					}
					if(null != memberList && memberList.size() != 0){
						//get the general score for each criteria for each firm
						long start1 = System.currentTimeMillis();
						generalScores = spnRoutingPriorityService.getFirmScores(performanceCriteria, memberList);
						long end1 = System.currentTimeMillis();
						logger.info("calculateScores >> getFirmScores"+(end1 - start1));
						//fetch the coverage details
						//need to modify to check for alias
						long start2 = System.currentTimeMillis();
						coveragelist = spnRoutingPriorityService.getFirmCoverage(spnId, routingDTO.getAliasSpnId());
						long end2 = System.currentTimeMillis();
						logger.info("calculateScores >> getFirmCoverage"+(end2 - start2));
						//need to modify to check for alias
						long start3 = System.currentTimeMillis();
						provCountList = spnRoutingPriorityService.getProvCount(memberMap, spnId, routingDTO.getAliasSpnId());
						long end3 = System.currentTimeMillis();
						logger.info("calculateScores >> getProvCount"+(end3 - start3));
						if(null != coveragelist){
							routingDTO.setFirmCount(coveragelist.size());
						}
					}
				}	
				//calculate scores	
				if(null != coveragelist && coveragelist.size() != 0 ){
					Map<String, String> states = new TreeMap<String, String>();
					Map<String, Integer> markets = new TreeMap<String, Integer>();
					for(SPNCoverageDTO coverage : coveragelist){
						int ind = 0;
						Double score = 0.0;					
						if(null != coverage){
							//setting state list						
							states.put(coverage.getStateName(),coverage.getState());
							//setting market list
							markets.put(coverage.getMarket(), coverage.getMarketId());
							
							//calculating scores
							if(null != generalScores && generalScores.size() != 0){
								for(LookupCriteriaDTO scores : generalScores){
									if(coverage.getMemberId().intValue() == scores.getMemberId().intValue()){
										score = score + (scores.getPerformanceValue()/criteriaCount);
										ind = 1;
									}
								}
								if(0 == ind){
									coverage.setNoScoreInd(1);
								}
							}
							else{
								coverage.setNoScoreInd(1);
							}
						}
						BigDecimal perfScore= new BigDecimal(String.valueOf(score)).setScale(2, BigDecimal.ROUND_UP);
						coverage.setScore(perfScore.doubleValue());
					}
					
					//to get total #eligible providers
					if (SPNBackendConstants.FIRM.equals(criteriaLevel) && null != provCountList){
						int providerCount = 0;
						for(SPNCoverageDTO coverage : coveragelist){
							if(null != coverage){
								for(SPNCoverageDTO provCount : provCountList){
									if(null != provCount && coverage.getMemberId().intValue() == provCount.getFirmId().intValue()){
										coverage.setNoOfEligibleProvs(provCount.getNoOfEligibleProvs());
										break;
									}
								}
								providerCount = providerCount + coverage.getNoOfEligibleProvs();
							}
						}
						routingDTO.setProviderCount(providerCount);
					}
					
					//to sort the coverage list based on score
					Comparator<SPNCoverageDTO> compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_DESCENDING);
					Collections.sort(coveragelist, compare);
					
					routingDTO.setCoveragelist(coveragelist);
					routingDTO.setStateList(states);
					routingDTO.setMarketList(markets);
					routingDTO.setSelectedStates(null);
					routingDTO.setSelectedMarkets(null);
					routingDTO.setSelectedZip(null);
				}
				getModel().setRoutingDTO(routingDTO);
				getRequest().getSession().setAttribute(SAVED_DTO, routingDTO);
			}
		}
		catch(BusinessServiceException e){
			logger.error("Exception in SPNReleaseTiersTabAction calculateScores() due to "+ e.getMessage());
		}
		return CALCULATE;
	}
	
	//filter coverage details
	public String filterCoverage()throws Exception {
		
		getRequest().getSession().removeAttribute(FILTERED_COVERAGE_DTO);
		boolean resetInd = Boolean.parseBoolean(getRequest().getParameter("resetInd"));
		SPNRoutingDTO routingDTO = getModel().getRoutingDTO();
		List<String> selectedStates = routingDTO.getSelectedStates();
		List<Integer> selectedMarkets = routingDTO.getSelectedMarkets();
		String selectedZip = routingDTO.getSelectedZip();	
		String criteriaLevel = routingDTO.getSpnHdr().getCriteriaLevel();
		List<SPNCoverageDTO> coverageList = new ArrayList<SPNCoverageDTO>();
		
		List<String> zipList = new ArrayList<String>();		
		List<SPNCoverageDTO> newCoverageList = new ArrayList<SPNCoverageDTO>();
		SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);
		
		if(null != savedDTO){
			if(null == criteriaLevel){
				criteriaLevel = savedDTO.getSpnHdr().getCriteriaLevel();
				routingDTO.getSpnHdr().setCriteriaLevel(criteriaLevel);
			}
			if(!resetInd){
				coverageList = copyCoverage(savedDTO.getCoveragelist());
				//filtering based on states
				if(null != selectedStates && selectedStates.size() != 0){
					for(String state : selectedStates){
						if(StringUtils.isNotBlank(state)){
							for(SPNCoverageDTO coverage : coverageList){
								if(state.equals(coverage.getState())){
									newCoverageList.add(coverage);
								}
							}
						}
					}
					routingDTO.setSelectedStates(selectedStates);
				}
				//setting zip codes based on markets
				if(null != selectedMarkets && selectedMarkets.size() != 0){
					for(Integer market : selectedMarkets){
						if(null != market){
							for(SPNCoverageDTO coverage : coverageList){
								if(market.intValue() == coverage.getMarketId().intValue()){
									if(!zipList.contains(coverage.getZip())){
										zipList.add(coverage.getZip());
									}
								}
							}
						}
					}
					routingDTO.setZipList(zipList);
					routingDTO.setSelectedMarkets(selectedMarkets);
					
					if(StringUtils.isBlank(selectedZip)){
						newCoverageList = copyCoverage(savedDTO.getCoveragelist());					
					}
				}
							
				if(StringUtils.isNotBlank(selectedZip)){
					for(SPNCoverageDTO coverage : coverageList){
						if(selectedZip.equals(coverage.getZip())){
							newCoverageList.add(coverage);
						}
					}
					//rank the providers or firms based on score
					newCoverageList = rankMembers(newCoverageList, criteriaLevel);
					routingDTO.setSelectedZip(selectedZip);
				}
			}
			else{				
				newCoverageList = copyCoverage(savedDTO.getCoveragelist());	
				routingDTO.setSelectedMarkets(savedDTO.getSelectedMarkets());
				routingDTO.setSelectedStates(savedDTO.getSelectedStates());
				routingDTO.setSelectedZip(savedDTO.getSelectedZip());
			}
			
			//to set the count of firms/providers
			if(SPNBackendConstants.PROVIDER.equals(criteriaLevel)){
				routingDTO.setProviderCount(newCoverageList.size());
			}
			else if(SPNBackendConstants.FIRM.equals(criteriaLevel)){
				routingDTO.setFirmCount(newCoverageList.size());
				int provCount = 0;
				for(SPNCoverageDTO coverage : newCoverageList){
					if(null != coverage){
						provCount = provCount + coverage.getNoOfEligibleProvs();
					}
				}
				routingDTO.setProviderCount(provCount);
			}
			routingDTO.setMarketList(savedDTO.getMarketList());
			routingDTO.setStateList(savedDTO.getStateList());
			routingDTO.setCoveragelist(newCoverageList);
			getModel().setRoutingDTO(routingDTO);
			getRequest().getSession().setAttribute(FILTERED_COVERAGE_DTO, routingDTO);
		}

		return SUCCESS;
	}

	//save routing priority
	public String saveRoutingPriority()throws Exception {
		
		SPNRoutingDTO routingDTO = getModel().getRoutingDTO();	
		Integer spnId = routingDTO.getSpnHdr().getSpnId();
		String userName = getLoggedInUser().getUsername();
		try{
			if(null != routingDTO){
				//setting the coverage details			
				SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);				
				if(null != savedDTO){
					routingDTO.setCoveragelist(savedDTO.getCoveragelist());
					routingDTO.setMarketList(savedDTO.getMarketList());
					routingDTO.setStateList(savedDTO.getStateList());	
					routingDTO.setFirmCount(savedDTO.getFirmCount());
					routingDTO.setProviderCount(savedDTO.getProviderCount());				
					routingDTO.setCriteriaDTO(savedDTO.getCriteriaDTO());
					routingDTO.setCriteriaCount(savedDTO.getCriteriaCount());
					routingDTO.setSelectedMarkets(null);
					routingDTO.setSelectedStates(null);
					routingDTO.setSelectedZip(null);
					routingDTO.setAction(savedDTO.getAction());
					routingDTO.setModifiedDate(savedDTO.getModifiedDate());
					routingDTO.setAliasSpnId(savedDTO.getAliasSpnId());
				}
				
				//setting the criteria
				List<SPNPerformanceCriteria> performanceCriteria = new ArrayList<SPNPerformanceCriteria>();
				for(SPNPerformanceCriteria criteria : routingDTO.getPerformanceCriteria()){
					if(null != criteria){
						if (null != criteria.getCriteriaId()&& !StringUtils.isBlank(criteria.getCriteriaScope())) {
							performanceCriteria.add(criteria);
						}
					}
				}
				routingDTO.setPerformanceCriteria(performanceCriteria);
				
				//setting the tiers
				List<SPNTierDTO> tiers = new ArrayList<SPNTierDTO>();
				int tierId = 0;
				for(SPNTierDTO tier : routingDTO.getSpnTiers()){
					if(null != tier){	
						tierId = tierId + 1;
						tier.setTierId(tierId);
						tier.setSpnId(spnId);
						tier.setModifiedBy(userName);
						if(SPNBackendConstants.MINUTES.equals(tier.getUnit())){
							tier.setMinutes(tier.getTime());
						}
						else if(SPNBackendConstants.HOURS.equals(tier.getUnit())){
							tier.setHours(tier.getTime());
						}
						else if(SPNBackendConstants.DAYS.equals(tier.getUnit())){
							tier.setDays(tier.getTime());
						}					
						tiers.add(tier);
					}					
				}
				routingDTO.setSpnTiers(tiers);
				
				//setting the action for network history
				if(SPNBackendConstants.EDIT.equals(routingDTO.getAction())){
					routingDTO.setAction(SPNBackendConstants.PRIORITY_CREATED);
				}
				else if(SPNBackendConstants.VIEW.equals(routingDTO.getAction())){
					routingDTO.setAction(SPNBackendConstants.PRIORITY_EDITED);
				}
				
				//save routing priority
				spnRoutingPriorityService.saveRoutingPriorty(routingDTO, spnId);
				//save routing priority history
				spnRoutingPriorityService.saveRoutingPriortyHistory(routingDTO, spnId, userName);	
				if(null != routingDTO.getAliasSpnId()){
					spnRoutingPriorityService.saveRoutingPriortyForAlias(routingDTO);
				}
				
				routingDTO.setAction(SPNBackendConstants.VIEW);
				getModel().setRoutingDTO(routingDTO);
				getRequest().getSession().setAttribute(SAVED_DTO, routingDTO);
			}
		}
		catch(BusinessServiceException e){
			logger.info("Exception in SPNReleaseTiersTabAction savePriority() due to "+ e.getMessage());
		}
		return SAVE;
	}
	
	//update the routing priority status
	public String updateRoutingStatus() {
		
		Integer spnId = model.getSpnId();
		String routingStatus = getRequest().getParameter("routingStatus").toString();
		String userName = getLoggedInUser().getUsername();
		SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);	
		SPNHeader spnHeader = new SPNHeader();
		String action = "";
		try{
			if(null != savedDTO){
				spnHeader.setSpnId(spnId);
				spnHeader.setCriteriaLevel(savedDTO.getSpnHdr().getCriteriaLevel());
				spnHeader.setCriteriaTimeframe(savedDTO.getSpnHdr().getCriteriaTimeframe());
				spnHeader.setRoutingPriorityStatus(routingStatus);
				savedDTO.getSpnHdr().setRoutingPriorityStatus(routingStatus);
				if(SPNBackendConstants.ACTIVE.equals(routingStatus)){
					action = SPNBackendConstants.PRIORITY_ACTIVE;
				}
				else if(SPNBackendConstants.INACTIVE.equals(routingStatus)){
					action = SPNBackendConstants.PRIORITY_INACTIVE;
				}
			}
			spnRoutingPriorityService.updateRoutingStatus(spnHeader, userName, action);
			if(null != savedDTO.getAliasSpnId()){
				spnRoutingPriorityService.updateStatusForAlias(spnHeader, savedDTO.getAliasSpnId());
			}
			getRequest().getSession().setAttribute(SAVED_DTO, savedDTO);
		}
		catch(BusinessServiceException e){
			logger.info("Exception in SPNReleaseTiersTabAction updateRoutingStatus() due to "+ e.getMessage());
		}
		return UPDATE;
	}
	
	//redirect to the edit/view page with user entered inputs
	public String editRoutingPriority() {
		
		boolean edit = Boolean.parseBoolean(getRequest().getParameter("edit"));
		SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);	
		try{
			if(null != savedDTO){
				if(null != savedDTO.getSavedCriteria() && !savedDTO.getSavedCriteria().isEmpty()){
					savedDTO.setPerformanceCriteria(savedDTO.getSavedCriteria());
				}
				if(StringUtils.isNotBlank(savedDTO.getSavedCriteriaLevel())){
					savedDTO.getSpnHdr().setCriteriaLevel(savedDTO.getSavedCriteriaLevel());
				}
				if(StringUtils.isNotBlank(savedDTO.getSavedCriteriaTimeframe())){
					savedDTO.getSpnHdr().setCriteriaTimeframe(savedDTO.getSavedCriteriaTimeframe());
				}
				if(null != savedDTO.getSavedCoverage() && !savedDTO.getSavedCoverage().isEmpty()){
					savedDTO.setCoveragelist(savedDTO.getSavedCoverage());
				}
				if(-1 != savedDTO.getSavedProvCount()){
					savedDTO.setProviderCount(savedDTO.getSavedProvCount());
				}
				if(-1 != savedDTO.getSavedFirmCount()){
					savedDTO.setFirmCount(savedDTO.getSavedFirmCount());
				}
				if(null != savedDTO.getSavedMarkets() && !savedDTO.getSavedMarkets().isEmpty()){
					savedDTO.setMarketList(savedDTO.getSavedMarkets());
				}
				if(null != savedDTO.getSavedStates() && !savedDTO.getSavedStates().isEmpty()){
					savedDTO.setStateList(savedDTO.getSavedStates());
				}
				if(null != savedDTO.getSavedZips() && !savedDTO.getSavedZips().isEmpty()){
					savedDTO.setZipList(savedDTO.getSavedZips());
				}
				getModel().setRoutingDTO(savedDTO);
			}
		}
		catch(Exception e){
			logger.info("Exception in SPNReleaseTiersTabAction editRoutingPriority() due to "+ e.getMessage());
		}
		if(edit){
			return SPNBackendConstants.EDIT;
		}
		return SPNBackendConstants.VIEW;
		
	}
	
	public String showCoverage() {
		SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);
		getModel().setRoutingDTO(savedDTO);
		return "coverage";
	}
	
	//to set the buyerId and time frame in criteria list
	private List<SPNPerformanceCriteria> getCriteriaList(List<SPNPerformanceCriteria> performanceCriteria, String criteriaTimeframe) {
		
		List<SPNPerformanceCriteria> criteriaList = new ArrayList<SPNPerformanceCriteria>();
		Integer buyerId = getLoggedInUser().getUserId();
		
		if(null != performanceCriteria){
			for(SPNPerformanceCriteria criteria : performanceCriteria){
				if(null != criteria){
					if (null != criteria.getCriteriaId()&& !StringUtils.isBlank(criteria.getCriteriaScope())) {
						if(SPNBackendConstants.ALL.equals(criteria.getCriteriaScope())){
							criteria.setBuyerId(null);
						}
						else if(SPNBackendConstants.SINGLE.equals(criteria.getCriteriaScope())){
							criteria.setBuyerId(buyerId);
						}
						criteria.setTimeFrame(criteriaTimeframe);
						criteriaList.add(criteria);
					}
				}
			}
		}
		return criteriaList;
	}
	
	//to rank the members based o performance score
	private List<SPNCoverageDTO> rankMembers(List<SPNCoverageDTO> coverageList, String criteriaLevel) {
		
		Integer buyerId = getLoggedInUser().getUserId();
		//to get the elements with the same scores
		List<Integer> duplicateList = getDuplicates(coverageList);	
		//get the date of last completed order for prov/firm with same score
		try{
			if(null != duplicateList && duplicateList.size() > 0){
				List<SPNCoverageDTO> dateList = spnRoutingPriorityService.getCompletedDate(duplicateList, criteriaLevel, buyerId);
				if(null != dateList){
					for(SPNCoverageDTO coverage : coverageList){
						for(SPNCoverageDTO dto : dateList){
							if(coverage.getMemberId().intValue() == dto.getMemberId().intValue()){
								coverage.setCompletedDate(dto.getCompletedDate());
							}
						}
					}
				}
			}
			//sort the list based on score, date and name
			Comparator<SPNCoverageDTO> compare = null;
			if(SPNBackendConstants.FIRM == criteriaLevel){
				compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_DESCENDING,
						SPNCoverageDTO.SortParameter.DATE_ASCENDING, SPNCoverageDTO.SortParameter.FIRM_NAME_ASCENDING);
			}
			else{
				compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_DESCENDING,
						SPNCoverageDTO.SortParameter.DATE_ASCENDING, SPNCoverageDTO.SortParameter.PROVIDER_NAME_ASCENDING);
			}
			Collections.sort(coverageList, compare);
			
			//set the rank
			if(null != coverageList && coverageList.size()>0){
				int size = coverageList.size();
				int rank = 0;
				for(SPNCoverageDTO coverage : coverageList){
					if(null != coverage){
						rank = rank + 1;
						coverage.setRank("("+rank+"/"+size+")");
					}
				}
			}
		}
		catch(BusinessServiceException e){
			logger.info("Exception in SPNReleaseTiersTabAction rankMembers() due to "+ e.getMessage());
		}
		return coverageList;
	}
	
	//to get the elements with the same scores
	private List<Integer> getDuplicates(List<SPNCoverageDTO> coveragelist){		
		List<Integer> duplicateList = new ArrayList<Integer>();
		for(SPNCoverageDTO list : coveragelist){
			for(SPNCoverageDTO coverage : coveragelist){
				if(list.getScore().doubleValue() == coverage.getScore().doubleValue()){
					if(!duplicateList.contains(coverage.getMemberId())){
						duplicateList.add(coverage.getMemberId());
					}
				}
			}
		}
		return duplicateList;		
	}
	
	//copy coverage list
	private List<SPNCoverageDTO> copyCoverage(List<SPNCoverageDTO> coverage) {
		List<SPNCoverageDTO> results = new ArrayList<SPNCoverageDTO>();
		for (SPNCoverageDTO original : coverage) {
			SPNCoverageDTO copy = new SPNCoverageDTO();
			copy.setProvFirstName(original.getProvFirstName());
			copy.setProvLastName(original.getProvLastName());
			copy.setMemberId(original.getMemberId());
			copy.setFirmName(original.getFirmName());
			copy.setFirmId(original.getFirmId());
			copy.setScore(original.getScore());
			copy.setRank(original.getRank());
			copy.setJobTitle(original.getJobTitle());
			copy.setSlStatus(original.getSlStatus());
			copy.setNoOfEligibleProvs(original.getNoOfEligibleProvs());
			copy.setSpnId(original.getSpnId());
			copy.setState(original.getState());
			copy.setStateName(original.getStateName());
			copy.setZip(original.getZip());
			copy.setMarketId(original.getMarketId());
			copy.setMarket(original.getMarket());
			copy.setNoScoreInd(original.getNoScoreInd());
			results.add(copy);
		}
		return results;
	}
	
	public SPNBuyerServices getSpnBuyerServices() {
		return spnBuyerServices;
	}

	public void setSpnBuyerServices(SPNBuyerServices spnBuyerServices) {
		this.spnBuyerServices = spnBuyerServices;
	}

	public SPNReleaseTierModel getModel() {
		return model;
	}

	public void setModel(SPNReleaseTierModel model) {
		this.model = model;
	}

	public CreateNetworkServices getCreateSPNServices() {
		return createSPNServices;
	}

	public void setCreateSPNServices(CreateNetworkServices createSPNServices) {
		this.createSPNServices = createSPNServices;
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

	public Integer getMinMinutes()
	{
		return minMinutes;
	}

	public void setMinMinutes(Integer minMinutes)
	{
		this.minMinutes = minMinutes;
	}

	public SPNRoutingPriorityServices getSpnRoutingPriorityService() {
		return spnRoutingPriorityService;
	}

	public void setSpnRoutingPriorityService(
			SPNRoutingPriorityServices spnRoutingPriorityService) {
		this.spnRoutingPriorityService = spnRoutingPriorityService;
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

}
