package com.servicelive.inhome.autoclose.servicesImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.inhome.autoclose.InHomeAutoCloseDTO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssoc;
import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.inhome.autoclose.dao.HSRAutoCloseDao;
import com.servicelive.inhome.autoclose.services.InHomeAutoCloseService;





/**
 * 
 *
 */
public  class InHomeAutoCloseServiceImpl  implements InHomeAutoCloseService{
	private static final Logger LOGGER = Logger.getLogger(InHomeAutoCloseServiceImpl.class);
	private HSRAutoCloseDao hsrAutoCloseDao;

	
	public HSRAutoCloseDao getHsrAutoCloseDao() {
		return hsrAutoCloseDao;
	}

	public void setHsrAutoCloseDao(HSRAutoCloseDao hsrAutoCloseDao) {
		this.hsrAutoCloseDao = hsrAutoCloseDao;
	}
	//To display the overridden list
	public List<InHomeAutoCloseDTO> getOverrideFirmList(boolean activeInd) {
		
		List<InHomeAutoCloseDTO> autoCloseFirmOverrideList = new ArrayList<InHomeAutoCloseDTO> ();
		
		
		List<InHomeAutoCloseRuleFirmAssoc> autoCloseRuleFirmAssocList = hsrAutoCloseDao.getAutoCloseFirmOverrideList(activeInd);
		
		if(null!= autoCloseRuleFirmAssocList && !autoCloseRuleFirmAssocList.isEmpty()){
		for(InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc : autoCloseRuleFirmAssocList){
			InHomeAutoCloseDTO autoCloseOverride = new InHomeAutoCloseDTO();
			autoCloseOverride.setProviderFirm(autoCloseFirmAssoc.getFirm());
			autoCloseOverride.setExistingOverrideReason(autoCloseFirmAssoc.getOverridedReason());
			autoCloseOverride.setInhomeAutoCloseRuleFirmAssocId(autoCloseFirmAssoc.getInhomeAutoCloseRuleFirmAssocId());
			autoCloseOverride.setOverrideDate(autoCloseFirmAssoc.getModifiedDate());
			autoCloseOverride.setModifiedBy(autoCloseFirmAssoc.getModifiedBy());
			autoCloseOverride.setReimbursementRate(autoCloseFirmAssoc.getReimbursementRate());
			Date overrideDate = autoCloseFirmAssoc.getModifiedDate();
			try {
				autoCloseOverride.setOverrideDateFormatted(toCorrectFormat(overrideDate));
			} catch (ParseException e) {
				LOGGER.error("InHomeAutoCloseServiceImpl-getOverrideFirmList:Error while parsing date:"+e);
			}
			//get firm association history if changed from exclusion list to unlisted and back again
			List<InHomeAutoCloseRuleFirmAssocHistory> firmAssocHistory = hsrAutoCloseDao.getAutoCloseFirmOverrideHistory(autoCloseOverride.getInhomeAutoCloseRuleFirmAssocId());
			if(null!= firmAssocHistory && !firmAssocHistory.isEmpty() && firmAssocHistory.size() >= 3){
				try {
					correctFirmAssocHistoryDateFormat(firmAssocHistory);
				} catch (ParseException e) {
					LOGGER.error("InHomeAutoCloseServiceImpl-getOverrideFirmList:Error while parsing history date:"+e);
				}				
				autoCloseOverride.setFirmAssocHistory(firmAssocHistory);
			}
			autoCloseFirmOverrideList.add(autoCloseOverride);
		}
	}
		return autoCloseFirmOverrideList;
	}
	

	@Transactional
	public void updateOverriddenList(Integer ruleAssocId, boolean activeInd,String[] comment, String modifiedBy) {
		//Fetch the autoCloseRuleFirmAssoc
		InHomeAutoCloseRuleFirmAssoc inHomeautoCloseRuleFirmAssoc = hsrAutoCloseDao.getAutoCloseFirmAssocById(ruleAssocId);
		inHomeautoCloseRuleFirmAssoc.setActive(activeInd);
		inHomeautoCloseRuleFirmAssoc.setOverridedReason(comment[0]);
		inHomeautoCloseRuleFirmAssoc.setModifiedDate(getCurrentDateinGMT());
		hsrAutoCloseDao.saveAutoCloseFirmAssoc(inHomeautoCloseRuleFirmAssoc);
		hsrAutoCloseDao.saveAutoCloseFirmAssocHistory(inHomeautoCloseRuleFirmAssoc);
		
	}

	
	public List<Double> getReimbursementRateList() {
		
		List<Double> reimbursementRateList = new ArrayList<Double> ();
		reimbursementRateList = hsrAutoCloseDao.getReimbursementRateList();
		return reimbursementRateList;
	}
	

	public Double getDefaultReimursementRate() {
		Double defaultReimbursementRateList = 0.0;
		String appKey = "auto_adjudication_default_reimbursement_rate";
		defaultReimbursementRateList = hsrAutoCloseDao.getDefaultReimursementRate(appKey);
		return defaultReimbursementRateList;
	}
	
	@Transactional
	public void addToFirmOverrideList(List<BigDecimal> reimbursementRate,boolean activeInd, String[] comment, String[] firmIdsToAdd,
			String modifiedBy) {
		int counter = 0;
		List<Integer> firmIdList = new ArrayList<Integer>();
			
		if(firmIdsToAdd[0]!=""){
			for(String firmId:firmIdsToAdd){
				firmIdList.add(Integer.parseInt(firmId));
			}
			List<ProviderFirm> firmList = hsrAutoCloseDao.searchByFirmIds(firmIdList);
			if(null!= firmList && !firmList.isEmpty()){
				for(ProviderFirm firm:firmList){
					boolean isNew = false;
					InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc = hsrAutoCloseDao.findAutoCloseRuleFirmAssocByFirmId(firm.getId());
					if(autoCloseFirmAssoc == null){
						autoCloseFirmAssoc = new InHomeAutoCloseRuleFirmAssoc();
						isNew = !isNew;
					}
					if(isNew){
						autoCloseFirmAssoc.setFirm(firm);
						autoCloseFirmAssoc.setCreatedDate(getCurrentDateinGMT());
					}
					//R12_1: Adjudication Phase II: Fixing internal issue of reimbursement rate not getting overridden when a firm which was earlier
					//added and then removed , was again added to the list with  a new reimbursement rate.
					    autoCloseFirmAssoc.setReimbursementRate(reimbursementRate.get(counter));
						autoCloseFirmAssoc.setActive(activeInd);
						autoCloseFirmAssoc.setOverridedReason(comment[counter]==null?"":comment[counter]);
						autoCloseFirmAssoc.setModifiedBy(modifiedBy);
						autoCloseFirmAssoc.setModifiedDate(getCurrentDateinGMT());
						autoCloseFirmAssoc = hsrAutoCloseDao.saveAutoCloseFirmAssoc(autoCloseFirmAssoc);
						//Persist in InHomeAutoCloseRuleFirmAssoc History 
						hsrAutoCloseDao.saveAutoCloseFirmAssocHistory(autoCloseFirmAssoc);
				}
			}
		}
		
	}
	//To get the formatted date
	private static String toCorrectFormat(Date gmtDate) throws ParseException{
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
				LOGGER.error("InHomeAutoCloseServiceImpl: unable to convert the date to timezone date");
			}
		}
		return timezoneDate.replace("(", "").replace(")", "");		
	}
	
	//To get the formatted date for history
	private void correctFirmAssocHistoryDateFormat(List<InHomeAutoCloseRuleFirmAssocHistory> firmAssocHistory) throws ParseException {
		for(InHomeAutoCloseRuleFirmAssocHistory firmAssoc : firmAssocHistory){
			firmAssoc.setModifiedDateFormatted(toCorrectFormat(firmAssoc.getModifiedDate()));
		}
	}
	//To get the current date in GMT format
		private Date getCurrentDateinGMT(){
			GregorianCalendar cal = new GregorianCalendar();
			Timestamp ts = new Timestamp(cal.getTime().getTime());
			Date gmtDate= (Timestamp)TimeUtils.convertToGMT(ts, cal.getTimeZone().toString());
			return gmtDate;
		}

		public List<ProviderFirm> getProviderFirms(String providerFirmName,List<Integer> providerFirmIds) {
			List<ProviderFirm> firmList = null;
			List<Integer> overrideFirmIds = null;
			if(providerFirmName!=null){
				firmList = hsrAutoCloseDao.searchByFirmName(providerFirmName);
			}
			if(providerFirmIds!=null){
				firmList = hsrAutoCloseDao.searchByFirmIds(providerFirmIds);
			}
			if(firmList != null && firmList.size() > 0){
				overrideFirmIds = hsrAutoCloseDao.getOverriddenFirmIds(firmList);
			}
			
			if(overrideFirmIds!=null){
				for(ProviderFirm firm:firmList){
					if(overrideFirmIds.contains(firm.getId())){
						firm.setAutoCloseExcluded(true);
					}
				}			
			}

			return firmList;
		}

}
