package com.servicelive.spn.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupReleaseTier;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.domain.spn.network.SPNTierPK;
import com.servicelive.domain.spn.network.SPNTierPerformanceLevel;
import com.servicelive.spn.common.util.PropertyManagerUtil;
import com.servicelive.spn.dao.network.SPNHeaderDao;
import com.servicelive.spn.dao.network.SPNTierMinutesDao;
import com.servicelive.spn.dao.network.SPNTierPerformanceLevelDao;

@Transactional
@Service
public class SPNTierServiceImpl extends BaseServices implements SPNTierService
{
	private SPNHeaderDao spnheaderDao;
	private SPNTierMinutesDao spnTierMinutesDao;	
	private SPNTierPerformanceLevelDao spnTierPerformanceLevelDao;
	private PropertyManagerUtil propertyManagerUtil;
	

	
	
	
	public void saveTier(Integer spnId, Integer tierId, Integer minutes, List<Integer> performanceLevels, String modifiedBy) throws Exception
	{
		// Save Minutes
		SPNTierMinutes tierMinutes = new SPNTierMinutes();
		tierMinutes.setCreatedDate(new Date());
		tierMinutes.setModifiedBy(modifiedBy);
		tierMinutes.setAdvancedMinutes(minutes);
		
		SPNTierPK tierPK = new SPNTierPK();
		SPNHeader spnHeader = new SPNHeader();
		spnHeader.setSpnId(spnId);
		tierPK.setSpnId(spnHeader);
		
		LookupReleaseTier luReleaseTier = new LookupReleaseTier();
		luReleaseTier.setId(tierId);
		tierPK.setTierId(luReleaseTier);
		tierMinutes.setSpnTierPK(tierPK);		
		
		spnTierMinutesDao.save(tierMinutes);

		
		// Clear out all existing Performance Level rows for this spnId and tierId
		spnTierPerformanceLevelDao.deletePerformanceLevels(spnId, tierId);
		
		// Save Performance Levels
		for(Integer perfLevel : performanceLevels)
		{
			SPNTierPerformanceLevel performanceLevel = new SPNTierPerformanceLevel();
			performanceLevel.setModifiedBy(modifiedBy);
			performanceLevel.setModifiedDate(new Date());			
			performanceLevel.setSpnId(spnHeader);
			performanceLevel.setTierId(luReleaseTier);
			performanceLevel.setCreatedDate( new Date());
			
			
			LookupPerformanceLevel luPerfLevel = new LookupPerformanceLevel();
			luPerfLevel.setId(perfLevel);
			performanceLevel.setPerformanceLevelId(luPerfLevel);
			spnTierPerformanceLevelDao.update(performanceLevel);
		}
	}
	
	public void deleteTier(Integer spnId, Integer tierId) throws Exception
	{		
		spnTierMinutesDao.deleteTierMinutes(spnId, tierId);
		
		spnTierPerformanceLevelDao.deletePerformanceLevels(spnId, tierId);
	}
	
	
	
	public void saveTierMinutes(SPNTierMinutes tierMinutes) throws Exception
	{
		spnTierMinutesDao.save(tierMinutes);
	}

	public void saveTierPerformanceLevel(SPNTierPerformanceLevel performanceLevel) throws Exception
	{
		spnTierPerformanceLevelDao.save(performanceLevel);
	}
	
	public List<SPNTierMinutes> getAllTierMinutes() throws Exception
	{
		return spnTierMinutesDao.findAll();
	}
	
	public int getMinimumDelayMinutes()
	{
		return this.propertyManagerUtil.getSpnTieredRoutingMinDelayMinutes();
	}
	
	public SPNTierMinutesDao getSpnTierMinutesDao()
	{
		return spnTierMinutesDao;
	}
	public void setSpnTierMinutesDao(SPNTierMinutesDao spnTierMinutesDao)
	{
		this.spnTierMinutesDao = spnTierMinutesDao;
	}
	public SPNTierPerformanceLevelDao getSpnTierPerformanceLevelDao()
	{
		return spnTierPerformanceLevelDao;
	}
	public void setSpnTierPerformanceLevelDao(SPNTierPerformanceLevelDao spnTierPerformanceLevelDao)
	{
		this.spnTierPerformanceLevelDao = spnTierPerformanceLevelDao;
	}

	@Override
	protected void handleDates(Object entity) {
		// do Nothing here.. No need
		
	}

	/**
	 * @return the spnheaderDao
	 */
	public SPNHeaderDao getSpnheaderDao() {
		return spnheaderDao;
	}

	/**
	 * @param spnheaderDao the spnheaderDao to set
	 */
	public void setSpnheaderDao(SPNHeaderDao spnheaderDao) {
		this.spnheaderDao = spnheaderDao;
	}

	public PropertyManagerUtil getPropertyManagerUtil() {
		return propertyManagerUtil;
	}

	public void setPropertyManagerUtil(PropertyManagerUtil propertyManagerUtil) {
		this.propertyManagerUtil = propertyManagerUtil;
	}
	
	
	
}
