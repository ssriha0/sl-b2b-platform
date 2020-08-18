package com.servicelive.autoclose.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.autoclose.AutoCloseRuleDTO;
import com.newco.marketplace.dto.autoclose.AutoCloseRuleExclusionDTO;
import com.newco.marketplace.dto.autoclose.ManageAutoCloseRulesDTO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.servicelive.autoclose.dao.AutoCloseRuleHdrDao;
import com.servicelive.autoclose.services.AutoCloseService;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteria;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteriaHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssoc;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleHdr;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssoc;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssocHistory;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;


public class AutoCloseServiceImpl implements AutoCloseService {

	private static final Logger logger = Logger.getLogger(AutoCloseServiceImpl.class);
	private AutoCloseRuleHdrDao autoCloseRuleHdrDao;

	//private RoutingRuleHdrDao routingRuleHdrDao;

	
	/*@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdr> getRoutingRulesHeaderList(Integer buyerId, RoutingRulesPaginationVO pagingCriteria){
		List<RoutingRuleHdr> result = routingRuleHdrDao.getRoutingRulesHeadersforBuyer(buyerId, pagingCriteria);
		return result;
	}*/
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public ManageAutoCloseRulesDTO getAutoCloseRulesForBuyer(Integer buyerId){
		List<AutoCloseRuleHdr> autoCloseRuleHdrList = autoCloseRuleHdrDao.findByBuyerId(buyerId);
		ManageAutoCloseRulesDTO manageAutoCloseRulesDTO = new ManageAutoCloseRulesDTO();
		List<AutoCloseRuleDTO> autoCloseRuleDTOList = new ArrayList<AutoCloseRuleDTO>();
		
		for(AutoCloseRuleHdr autoCloseRuleHdr : autoCloseRuleHdrList){
			//Firm listed and Provider listed need not be displayed in the rules page
			//if(autoCloseRuleHdr.getAutoCloseRules().getRuleName().indexOf("LISTED") == -1){
				AutoCloseRuleDTO autoCloseRuleDTO = new AutoCloseRuleDTO();
				autoCloseRuleDTO.setAutoCloseRuleHdrId(autoCloseRuleHdr.getAutoCloseRuleHdrId());
				autoCloseRuleDTO.setAutoCloseRuleId(autoCloseRuleHdr.getAutoCloseRules().getAutoCloseRuleId());
				autoCloseRuleDTO.setAutoCloseRuleName(autoCloseRuleHdr.getAutoCloseRules().getRuleName());
				autoCloseRuleDTO.setAutoCloseRuleDescription(autoCloseRuleHdr.getAutoCloseRules().getRuleDescription());
				if(autoCloseRuleHdr.getAutoCloseRules().getRuleName().equals("MAX PRICE")){
					//removing dollar from front-end display - quick fix
					autoCloseRuleDTO.setAutoCloseRuleDescription(autoCloseRuleDTO.getAutoCloseRuleDescription().replace("$", ""));
					AutoCloseRuleCriteria autoCloseRuleCriteria = autoCloseRuleHdrDao.findAutoCloseRuleCriteriaValue(autoCloseRuleHdr.getAutoCloseRuleHdrId());
					autoCloseRuleDTO.setAutoCloseRuleCriteriaValue(autoCloseRuleCriteria.getAutoCloseRuleCriteriaValue());
					autoCloseRuleDTO.setAutoCloseRuleCriteriaId(autoCloseRuleCriteria.getAutoCloseRuleCriteriaId());
					
					List<AutoCloseRuleCriteriaHistory> criteriaHistory = autoCloseRuleHdrDao.findAutoCloseRuleCriteriaHistoryByCriteria(autoCloseRuleCriteria.getAutoCloseRuleCriteriaId());
					try {
						correctHistoryDateFormat(criteriaHistory);
					} catch (ParseException e) {
						logger.error("AutoCloseServiceImpl-getAutoCloseRulesForBuyer:Error while parsing date:"+e);
					}
					autoCloseRuleDTO.setAutoCloseRuleCriteriaHistoryList(criteriaHistory);
				}else if(autoCloseRuleHdr.getAutoCloseRules().getRuleName().indexOf("FIRM LISTED") != -1){
					List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = getFirmExclusionList(autoCloseRuleHdr.getAutoCloseRuleHdrId(),false);
					manageAutoCloseRulesDTO.setAutoCloseRuleExclusionList(autoCloseFirmExclusionList);
				}
				autoCloseRuleDTOList.add(autoCloseRuleDTO);
			//}
		}
		manageAutoCloseRulesDTO.setAutoCloseRuleDTOList(autoCloseRuleDTOList);
		
		return manageAutoCloseRulesDTO;
	}
	
	private void correctFirmAssocHistoryDateFormat(
			List<AutoCloseRuleFirmAssocHistory> firmAssocHistory) throws ParseException {
		for(AutoCloseRuleFirmAssocHistory firmAssoc : firmAssocHistory){
			firmAssoc.setModifiedDateFormatted(toCorrectFormat(firmAssoc.getModifiedDate()));
		}
	}
	
	private void correctProviderAssocHistoryDateFormat(
			List<AutoCloseRuleProviderAssocHistory> providerAssocHistory) throws ParseException {
		for(AutoCloseRuleProviderAssocHistory providerAssoc : providerAssocHistory){
			providerAssoc.setModifiedDateFormatted(toCorrectFormat(providerAssoc.getModifiedDate()));
		}
	}	
	
	private void correctHistoryDateFormat(
			List<AutoCloseRuleCriteriaHistory> criteriaHistoryList) throws ParseException {
		for(AutoCloseRuleCriteriaHistory criteriaHistory : criteriaHistoryList){
			criteriaHistory.setModifiedDateFormatted(toCorrectFormat(criteriaHistory.getModifiedDate()));
		}
	}
	
	private static String toCorrectFormat(Date gmtDate) throws ParseException{
		/*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("CST"));
		String date = sdf.format(toFormat);*/
		String timezoneDate=null;
		Timestamp timeStamp = new Timestamp(gmtDate.getTime());
		TimeZone timeZone = TimeZone.getTimeZone(OrderConstants.SERVICELIVE_ZONE);
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		String timeInGMT= TimeUtils.getTimePartforTimeonsite(format.format(gmtDate));
		
		if(timeStamp!= null && timeInGMT!=null)
		{ 
			timezoneDate = TimeUtils.convertGMTToGivenTimeZoneInString(timeStamp,timeInGMT, null);
			if (timezoneDate!=null) 
			{	
				if(timeZone.inDaylightTime(gmtDate)){
					timezoneDate = timezoneDate.replace("CST", "CDT");
	}
				return timezoneDate.replace("(", "").replace(")", "");
			}
			else
			{
				timezoneDate="";
				logger.error("AutoCloseServiceImpl: unable to convert the date to timezone date");
			}
		}
		return timezoneDate.replace("(", "").replace(")", "");		
	}

	/**
	 * 
	 * @param searchCriteriaVO
	 * @return
	 * @throws Exception
	 */
	public List<ProviderFirm> getProviderFirms
	(String firmName, List<Integer> firmIds , String buyerId){
		List<ProviderFirm> firmList = null;
		List<Integer> excludedFirmIds = null;
		if(firmName!=null){
			firmList = autoCloseRuleHdrDao.searchByFirmName(firmName);
		}
		if(firmIds!=null){
			firmList = autoCloseRuleHdrDao.searchByFirmIds(firmIds);
		}
		if(firmList != null && firmList.size() > 0){
			excludedFirmIds = autoCloseRuleHdrDao.getExcludedFirmIds(firmList , buyerId);
		}
		
		if(excludedFirmIds!=null){
			for(ProviderFirm firm:firmList){
				if(excludedFirmIds.contains(firm.getId())){
					firm.setAutoCloseExcluded(true);
				}
			}			
		}

		return firmList;
	}

	/**
	 * 
	 * @param searchCriteriaVO
	 * @return
	 * @throws Exception
	 */
	public List<ServiceProvider> getServiceProviders
	(String provName, List<Integer> provIds ,  String buyerId){
		List<ServiceProvider> provList = null;
		List<Integer> excludedProvIds = null;
		if(provName!=null){
			provList = autoCloseRuleHdrDao.searchByProvName(provName);
		}
		if(provIds!=null){
			provList = autoCloseRuleHdrDao.searchByProvIds(provIds);
		}
		
		if(provList != null && provList.size() > 0){
			excludedProvIds = autoCloseRuleHdrDao.getExcludedProvIds(provList,buyerId);
		}

		if(excludedProvIds!=null){
			for(ServiceProvider provider:provList){
				if(excludedProvIds.contains(provider.getId())){
					provider.setAutoCloseExcluded(true);
				}
			}			
		}		
		
		return provList;
	}
	

	public AutoCloseRuleCriteria getAutoCloseRuleCriteria(Integer criteriaId) {
		AutoCloseRuleCriteria autoCloseRuleCriteria = autoCloseRuleHdrDao.findAutoCloseRuleCriteriaById(criteriaId);
		return autoCloseRuleCriteria;
	}

	public AutoCloseRuleHdrDao getAutoCloseRuleHdrDao() {
		return autoCloseRuleHdrDao;
	}

	public void setAutoCloseRuleHdrDao(AutoCloseRuleHdrDao autoCloseRuleHdrDao) {
		this.autoCloseRuleHdrDao = autoCloseRuleHdrDao;
	}

	public void saveAutoCloseRuleCriteria(
			AutoCloseRuleCriteria autoCloseRuleCriteria,
			AutoCloseRuleCriteria autoCloseRuleCriteriaChanged) {

		autoCloseRuleHdrDao.saveAutoCloseRuleCriteria(autoCloseRuleCriteriaChanged);
	}

	@Transactional
	public List<AutoCloseRuleCriteriaHistory> updateMaxprice(Integer criteriaId, String changedPrice, String modifiedBy) {
		//Fetch the autoCloseRuleCriteria
		AutoCloseRuleCriteria autoCloseRuleCriteria = autoCloseRuleHdrDao.findAutoCloseRuleCriteriaById(criteriaId);
		//Save the updated autoCloseRuleCriteria
		autoCloseRuleCriteria.setAutoCloseRuleCriteriaValue(changedPrice);
		
		autoCloseRuleCriteria.setModifiedDate(getCurrentDateinGMT());
		autoCloseRuleCriteria.setModifiedBy(modifiedBy);
		autoCloseRuleHdrDao.saveAutoCloseRuleCriteria(autoCloseRuleCriteria);
		//Persist in autoCloseRuleCriteria history
		autoCloseRuleHdrDao.saveAutoCloseRuleCriteriaHistory(autoCloseRuleCriteria);		
		//Fecth all the autoCloseRuleCriteria history
		List<AutoCloseRuleCriteriaHistory> criteriaHistory = autoCloseRuleHdrDao.findAutoCloseRuleCriteriaHistoryByCriteria(autoCloseRuleCriteria.getAutoCloseRuleCriteriaId());
		try {
			correctHistoryDateFormat(criteriaHistory);
		} catch (ParseException e) {
			logger.error("AutoCloseServiceImpl-getAutoCloseRulesForBuyer:Error while parsing date:"+e);
		}
		return criteriaHistory;
	}
	
	private Date getCurrentDateinGMT(){
		GregorianCalendar cal = new GregorianCalendar();
		Timestamp ts = new Timestamp(cal.getTime().getTime());
		Date gmtDate= (Timestamp)TimeUtils.convertToGMT(ts, cal.getTimeZone().toString());
		return gmtDate;
	}


	public List<AutoCloseRuleExclusionDTO> getFirmExclusionList(Integer autoCloseRuleHdrId,
			boolean activeInd) {
		List<AutoCloseRuleFirmAssoc> autoCloseRuleFirmAssocList = autoCloseRuleHdrDao.findAutoCloseRuleFirmExclusionList(autoCloseRuleHdrId, activeInd);
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = new ArrayList<AutoCloseRuleExclusionDTO> ();
		for(AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc : autoCloseRuleFirmAssocList){
			AutoCloseRuleExclusionDTO autoCloseRuleExclusion = new AutoCloseRuleExclusionDTO();
			autoCloseRuleExclusion.setProviderFirm(autoCloseRuleFirmAssoc.getFirm());
			autoCloseRuleExclusion.setExistingReason(autoCloseRuleFirmAssoc.getAutoCloseRuleFirmExcludeReason());
			autoCloseRuleExclusion.setAutoCloseRuleAssocId(autoCloseRuleFirmAssoc.getAutoCloseRuleFirmAssocId());
			autoCloseRuleExclusion.setExcludedDate(autoCloseRuleFirmAssoc.getModifiedDate());
			autoCloseRuleExclusion.setModifiedBy(autoCloseRuleFirmAssoc.getModifiedBy());
			Date excludedDate = autoCloseRuleFirmAssoc.getModifiedDate();
			try {
				autoCloseRuleExclusion.setExcludedDateFormatted(toCorrectFormat(excludedDate));
			} catch (ParseException e) {
				logger.error("AutoCloseServiceImpl-getFirmExclusionList:Error while parsing date:"+e);
			}
			//get firm association history if changed from exclusion list to unlisted and back again
			List<AutoCloseRuleFirmAssocHistory> firmAssocHistory = autoCloseRuleHdrDao.findAutoCloseRuleFirmExclusionHistory(autoCloseRuleExclusion.getAutoCloseRuleAssocId());
			if(firmAssocHistory.size() >= 3){
				try {
					correctFirmAssocHistoryDateFormat(firmAssocHistory);
				} catch (ParseException e) {
					logger.error("AutoCloseServiceImpl-getFirmExclusionList:Error while parsing date:"+e);
				}				
				autoCloseRuleExclusion.setFirmAssocHistory(firmAssocHistory);
			}
			autoCloseFirmExclusionList.add(autoCloseRuleExclusion);
		}
		return autoCloseFirmExclusionList;
	}
	
	public List<AutoCloseRuleExclusionDTO> getProviderExclusionList(Integer autoCloseRuleHdrId,
			boolean activeInd) {
		List<AutoCloseRuleProviderAssoc> autoCloseRuleFirmAssocList = autoCloseRuleHdrDao.findAutoCloseRuleProvExclusionList(autoCloseRuleHdrId, activeInd);
		List<AutoCloseRuleExclusionDTO> autoCloseFirmExclusionList = new ArrayList<AutoCloseRuleExclusionDTO> ();
		for(AutoCloseRuleProviderAssoc autoCloseRuleProvAssoc : autoCloseRuleFirmAssocList){
			AutoCloseRuleExclusionDTO autoCloseRuleExclusion = new AutoCloseRuleExclusionDTO();
			//autoCloseRuleExclusion.setProviderFirm(autoCloseRuleProvAssoc.getProvider().getProviderFirm());
			autoCloseRuleExclusion.setServiceProvider(autoCloseRuleProvAssoc.getProvider());
			autoCloseRuleExclusion.setExistingReason(autoCloseRuleProvAssoc.getAutoCloseRuleProvExcludeReason());
			autoCloseRuleExclusion.setAutoCloseRuleAssocId(autoCloseRuleProvAssoc.getAutoCloseRuleProvAssocId());
			autoCloseRuleExclusion.setModifiedBy(autoCloseRuleProvAssoc.getModifiedBy());
			Date excludedDate = autoCloseRuleProvAssoc.getModifiedDate();
			try {
				autoCloseRuleExclusion.setExcludedDateFormatted(toCorrectFormat(excludedDate));
			} catch (ParseException e) {
				logger.error("AutoCloseServiceImpl-getProviderExclusionList:Error while parsing date:"+e);
			}
			//get provider association history if changed from exclusion list to unlisted and back again
			List<AutoCloseRuleProviderAssocHistory> providerAssocHistory = autoCloseRuleHdrDao.findAutoCloseRuleProviderExclusionHistory(autoCloseRuleExclusion.getAutoCloseRuleAssocId());
			if(providerAssocHistory.size() >= 3){
				try {
					correctProviderAssocHistoryDateFormat(providerAssocHistory);
				} catch (ParseException e) {
					logger.error("AutoCloseServiceImpl-getFirmExclusionList:Error while parsing date:"+e);
				}				
				autoCloseRuleExclusion.setProviderAssocHistory(providerAssocHistory);
			}			
			autoCloseFirmExclusionList.add(autoCloseRuleExclusion);
		}
		return autoCloseFirmExclusionList;
	}

	@Transactional
	public void updateFirmExclusionList(Integer ruleAssocId, boolean activeInd, String[] autoCloseRuleFirmExcludeReasons, String modifiedBy) {
			//Fetch the autoCloseRuleFirmAssoc
			AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc = autoCloseRuleHdrDao.findAutoCloseRuleFirmAssocById(ruleAssocId);
			autoCloseRuleFirmAssoc.setActive(activeInd);
			autoCloseRuleFirmAssoc.setAutoCloseRuleFirmExcludeReason(autoCloseRuleFirmExcludeReasons[0]);
			autoCloseRuleFirmAssoc.setModifiedDate(getCurrentDateinGMT());
			autoCloseRuleHdrDao.saveAutoCloseRuleFirmAssoc(autoCloseRuleFirmAssoc);
			//Persist in AutoCloseRuleFirmAssoc History 
			autoCloseRuleHdrDao.saveAutoCloseRuleFirmAssocHistory(autoCloseRuleFirmAssoc);			
	}
	
	@Transactional
	public void addToFirmExclusionList(Integer exclusionId, boolean activeInd, String[] autoCloseRuleFirmExcludeReasons, String[] firmIds, String modifiedBy) {
		int counter = 0;
		List<Integer> firmIdList = new ArrayList<Integer>();
		if(firmIds[0]!=""){
			for(String firmId:firmIds){
				firmIdList.add(Integer.parseInt(firmId));
			}
			List<ProviderFirm> firmList = autoCloseRuleHdrDao.searchByFirmIds(firmIdList);
			
			for(ProviderFirm firm:firmList){
				boolean isNew = false;
				AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc = autoCloseRuleHdrDao.findAutoCloseRuleFirmAssocByFirmId(exclusionId, firm.getId());
				if(autoCloseRuleFirmAssoc == null){
					autoCloseRuleFirmAssoc = new AutoCloseRuleFirmAssoc();
					isNew = !isNew;
				}
				if(isNew){
					autoCloseRuleFirmAssoc.setAutoCloseRuleHdrId(exclusionId);
					autoCloseRuleFirmAssoc.setFirm(firm);
					autoCloseRuleFirmAssoc.setCreatedDate(getCurrentDateinGMT());
				}
				autoCloseRuleFirmAssoc.setActive(activeInd);
				autoCloseRuleFirmAssoc.setAutoCloseRuleFirmExcludeReason(autoCloseRuleFirmExcludeReasons[counter]==null?"":autoCloseRuleFirmExcludeReasons[counter]);
				autoCloseRuleFirmAssoc.setModifiedBy(modifiedBy);
				autoCloseRuleFirmAssoc.setModifiedDate(getCurrentDateinGMT());
				autoCloseRuleFirmAssoc = autoCloseRuleHdrDao.saveAutoCloseRuleFirmAssoc(autoCloseRuleFirmAssoc);
				//Persist in AutoCloseRuleFirmAssoc History 
				autoCloseRuleHdrDao.saveAutoCloseRuleFirmAssocHistory(autoCloseRuleFirmAssoc);				
				counter++;
			}
		}
	}	

	@Transactional
	public void updateProviderExclusionList(Integer ruleAssocId, boolean activeInd, String[] autoCloseRuleProvExcludeReason, String modifiedBy) {
		//Fetch the autoCloseRuleProviderAssoc
		AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc = autoCloseRuleHdrDao.findAutoCloseRuleProviderAssocById(ruleAssocId);
		autoCloseRuleProviderAssoc.setActive(activeInd);
		autoCloseRuleProviderAssoc.setAutoCloseRuleProvExcludeReason(autoCloseRuleProvExcludeReason[0]);
		autoCloseRuleProviderAssoc.setModifiedBy(modifiedBy);
		autoCloseRuleProviderAssoc.setModifiedDate(getCurrentDateinGMT());
		autoCloseRuleHdrDao.saveAutoCloseRuleProviderAssoc(autoCloseRuleProviderAssoc);
		//Persist in autoCloseRuleCriteria history
		autoCloseRuleHdrDao.saveAutoCloseRuleProviderAssocHistory(autoCloseRuleProviderAssoc);		
	}
	
	@Transactional
	public void addToProviderExclusionList(Integer exclusionId, boolean activeInd, String[] autoCloseRuleProvExcludeReasons, String[] provIds, String modifiedBy) {
		int counter = 0;
		List<Integer> provIdList = new ArrayList<Integer>();
		if(provIds[0]!=""){
			for(String provId:provIds){
				provIdList.add(Integer.parseInt(provId));
			}
			List<ServiceProvider> provList = autoCloseRuleHdrDao.searchByProvIds(provIdList);
			
			for(ServiceProvider provider:provList){
				boolean isNew = false;
				AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc = autoCloseRuleHdrDao.findAutoCloseRuleProvAssocByProvId(exclusionId, provider.getId());
				if(autoCloseRuleProviderAssoc == null){
					autoCloseRuleProviderAssoc = new AutoCloseRuleProviderAssoc();
					isNew = !isNew;
				}
				if(isNew){
					autoCloseRuleProviderAssoc.setAutoCloseRuleHdrId(exclusionId);
					autoCloseRuleProviderAssoc.setProvider(provider);
					autoCloseRuleProviderAssoc.setCreatedDate(getCurrentDateinGMT());
				}
				autoCloseRuleProviderAssoc.setActive(activeInd);
				autoCloseRuleProviderAssoc.setAutoCloseRuleProvExcludeReason(autoCloseRuleProvExcludeReasons[counter]==null?"":autoCloseRuleProvExcludeReasons[counter]);
				autoCloseRuleProviderAssoc.setModifiedBy(modifiedBy);
				autoCloseRuleProviderAssoc.setModifiedDate(getCurrentDateinGMT());
				autoCloseRuleProviderAssoc = autoCloseRuleHdrDao.saveAutoCloseRuleProviderAssoc(autoCloseRuleProviderAssoc);
				//Persist in autoCloseRuleCriteria history
				autoCloseRuleHdrDao.saveAutoCloseRuleProviderAssocHistory(autoCloseRuleProviderAssoc);				
				counter++;
			}
		}
	}
	
}
