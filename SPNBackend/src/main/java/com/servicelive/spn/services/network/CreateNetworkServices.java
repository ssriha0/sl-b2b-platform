/**
 *
 */
package com.servicelive.spn.services.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.network.ReleaseTiersVO;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.domain.spn.network.SPNTierPerformanceLevel;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.campaign.CampaignHeaderDao;
import com.servicelive.spn.dao.network.SPNDocumentDao;
import com.servicelive.spn.dao.network.SPNHeaderDao;
import com.servicelive.spn.dao.network.SPNTierMinutesDao;
import com.servicelive.spn.dao.network.SPNTierPerformanceLevelDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.buyer.SPNBuyerServices;

/**
 * @author hoza
 *
 */

public class CreateNetworkServices extends BaseServices {
	private SPNHeaderDao spnheaderDao;
	private SPNDocumentDao spnDocumentDao;
	private SPNBuyerServices spnBuyerServices;
	private EditNetworkService editNetworkService;
	private CampaignHeaderDao campaignHeaderDao;
	private SPNTierMinutesDao spnTierMinutesDao;	
	private SPNTierPerformanceLevelDao spnTierPerformanceLevelDao;
	
	
	

	/**
	 * 
	 * @param entity
	 * @return SPNHeader
	 * @throws Exception
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public SPNHeader updateSPN(SPNHeader entity) throws Exception {
		return spnheaderDao.update(entity);
	}


	/**
	 * 
	 * @param entity
	 * @return SPNHeader
	 * @throws Exception
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public SPNHeader saveSPN(SPNHeader entity) throws Exception {
			return saveSPN(entity, null,null,false);
	}

	/**
	 * 
	 * @param entity
	 * @param docEditted
	 * @return SPNHeader
	 * @throws Exception
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public SPNHeader saveSPN(SPNHeader entity, Boolean docEditted, Integer memberStatusAffected, Boolean auditRequired) throws Exception {
			handleDates(entity);
			
			if(entity.getSpnId() != null) {
				boolean hasCampaign = hasCampaign(entity.getSpnId());
				if(hasCampaign && memberStatusAffected==1) {
					editNetworkService.save(entity, docEditted, auditRequired);
				} else {
					// added for SL-19025 to copy exceptions and reset exceptions ind if no exceptions exist after edit.
					if(entity.getExceptionsInd()){
						List<SPNExclusionsVO> exceptions = editNetworkService.getSPNExclusions(entity.getSpnId());
						List<SPNExclusionsVO> excList = editNetworkService.copySpnOriginalExceptions(exceptions, entity.getApprovalCriterias());
						entity.setSpnExceptions(excList);
						if(excList.isEmpty()){
							entity.setExceptionsInd(false);
						}
					}
					editNetworkService.removeExistingCriteria(entity);
				
					//spnheaderDao.removeExistingApprovalCriteria(entity.getSpnId());
				}
			}
			
			return spnheaderDao.update(entity);
	}
	
	/**
	 * 
	 * @param spnId
	 * @return SPNHeader
	 * @throws Exception
	 */
	public SPNHeader findAlias(Integer spnId) throws Exception
	{
		List<SPNHeader> headers = spnheaderDao.findAliases(spnId);
		if(headers.size() == 0) {
			return null;
		}
		return headers.iterator().next(); 
	}

	/**
	 * 
	 * @param spnId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean hasCampaign(Integer spnId) throws Exception {
		List<CampaignHeader> campaignHeaders = campaignHeaderDao.findBySpnId(spnId);
		return campaignHeaders.size() > 0;
	}
	
	/**
	 * 
	 * @param id
	 * @return SPNHeader
	 * @throws Exception
	 */
	public SPNHeader getNetwork(Integer id) throws Exception {
		 return spnheaderDao.findById(id);
	}
	
	/**
	 * Check if same spn name exist for the specified buyer
	 * @param buyerId
	 * @param spnName
	 * @return true if this spn name exists for this buyer, otherwise false
	 */
	public boolean isSpnNameExistForBuyer(Integer buyerId, String spnName){
		try {
			List<SPNHeader> sPnBuyers = spnBuyerServices.getSPNListForBuyer(buyerId);
			for (SPNHeader spnHeader : sPnBuyers) {
				if (spnHeader.getSpnName().equals(spnName)) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Error finding SPN List for the buyer ID " + buyerId, e);
		}
		return false;
	}
	
	/**
	 * Gets the list of all the SPN documents for this buyer.
	 * @param buyerId
	 * @return Map<Integer, SPNDocument>
	 * @throws Exception
	 */
	public Map<Integer, SPNDocument> getSPNDocumentsForBuyer(Integer buyerId) throws Exception{
		Map<Integer, SPNDocument> spnDocumentsMap = new Hashtable<Integer, SPNDocument>();
		List<SPNHeader> spnList = spnBuyerServices.getSPNListForBuyer(buyerId);
		for (SPNHeader spnHeader : spnList) {
			List<SPNDocument> spnDocuments = spnDocumentDao.findByProperty("spn", spnHeader);
			for (SPNDocument document : spnDocuments) {
				spnDocumentsMap.put(document.getDocument().getDocumentId(), document);
			}
		}
	  //spnDocumentsMap=sortHashMapByValuesD(spnDocumentsMap);
		return spnDocumentsMap;
		
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


	/**
	 * @param lookupService the lookupService to set
	 */
	/*public void setLookupService(LookupService lookupService) {
		this.lookupService = lookupService;
	}
	*/

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		SPNHeader camp = (SPNHeader) entity;
		if(camp.getSpnId() == null) {
			camp.setCreatedDate(CalendarUtil.getNow());
		}
		camp.setModifiedDate(CalendarUtil.getNow());
	}


	/**
	 * @param spnBuyerServices the spnBuyerServices to set
	 */
	public void setSpnBuyerServices(SPNBuyerServices spnBuyerServices) {
		this.spnBuyerServices = spnBuyerServices;
	}


	/**
	 * @param spnDocumentDao the spnDocumentDao to set
	 */
	public void setSpnDocumentDao(SPNDocumentDao spnDocumentDao) {
		this.spnDocumentDao = spnDocumentDao;
	}


	/**
	 * @param campaignHeaderDao the campaignHeaderDao to set
	 */
	public void setCampaignHeaderDao(CampaignHeaderDao campaignHeaderDao) {
		this.campaignHeaderDao = campaignHeaderDao;
	}

	/**
	 * @param editNetworkService the editNetworkService to set
	 */
	public void setEditNetworkService(EditNetworkService editNetworkService) {
		this.editNetworkService = editNetworkService;
	}
	/**
	 * 
	 * @param spnId
	 * @return
	 * @throws Exception
	 */
	public int removeExistingTiers(Integer spnId) throws Exception {
		int miniutesDeleted  = spnTierMinutesDao.deleteTierMinutes(spnId);
		int performanceDeletd = spnTierPerformanceLevelDao.deletePerformanceLevels(spnId);
		/* once all tiers are deleted set market overflow to false */
		SPNHeader spnHeader = this.getNetwork(spnId);
		spnHeader.setMarketPlaceOverFlow(Boolean.FALSE);
		updateSPN(spnHeader);
		
		return miniutesDeleted + performanceDeletd;
	}

	/**
	 * @param spnTierMinutesDao the spnTierMinutesDao to set
	 */
	public void setSpnTierMinutesDao(SPNTierMinutesDao spnTierMinutesDao) {
		this.spnTierMinutesDao = spnTierMinutesDao;
	}

	/**
	 * @param spnTierPerformanceLevelDao the spnTierPerformanceLevelDao to set
	 */
	public void setSpnTierPerformanceLevelDao(
			SPNTierPerformanceLevelDao spnTierPerformanceLevelDao) {
		this.spnTierPerformanceLevelDao = spnTierPerformanceLevelDao;
	}
	
	public List<ReleaseTiersVO> getReleaseTiers(Integer spnId) throws Exception
	{
		SPNHeader spnHeader = spnheaderDao.findById(spnId);
		return getReleaseTiersFromSPNHeader(spnHeader);
	}
	
	private List<ReleaseTiersVO> getReleaseTiersFromSPNHeader(SPNHeader spnHeader ) throws Exception  { 
		List<ReleaseTiersVO> tiers = new ArrayList<ReleaseTiersVO>(0);
		Map<Integer, ReleaseTiersVO> tierMap = new HashMap<Integer, ReleaseTiersVO>();
		
		List<SPNTierMinutes> minutes = spnHeader.getTierMinutes();
		List<SPNTierPerformanceLevel> plevels = spnHeader.getPerformanceLevels();
		updateTierMapWithMinutes(tierMap, minutes);
		
		
		updateTierMapWithPerformanceLevel(tierMap, plevels);
		
		tiers.addAll(tierMap.values());
		
		return tiers;
	}

	private void updateTierMapWithPerformanceLevel(
			Map<Integer, ReleaseTiersVO> tierMap,
			List<SPNTierPerformanceLevel> plevels) throws Exception {
		for( SPNTierPerformanceLevel plevel : plevels) {
			Integer tierId = (Integer)plevel.getTierId().getId();
			if(!tierMap.containsKey(tierId)) throw new Exception("Unknown Tier found");
			ReleaseTiersVO vo = tierMap.get(tierId);
			vo.addPerformanceLevel((Integer)plevel.getPerformanceLevelId().getId());
		}
	}

	/**
	 * This methos requires to have Map filled with TierId prior to call of this method.
	 * @param tierMap
	 * @param minutes
	 */
	private void updateTierMapWithMinutes(Map<Integer, ReleaseTiersVO> tierMap,
			List<SPNTierMinutes> minutes) {
		for( SPNTierMinutes minute : minutes) {
			Integer tierId = (Integer)  minute.getSpnTierPK().getTierId().getId();
			if(!tierMap.containsKey(tierId)) {
				ReleaseTiersVO vo = new ReleaseTiersVO();
				vo.setTierId(new Integer(minute.getSpnTierPK().getTierId().getId().toString()));
				vo.setTierMinute(minute);
				tierMap.put(tierId, vo);
			}
			
		}
	}


	public Boolean hasExceptions(Integer spnId) throws Exception{
		
		return spnheaderDao.hasExceptions(spnId);
	}
	/**
	 * R11_0 SL_19387
	 * This method returns the value of that feature set.
	 * @param buyerId
	 * @param feature
	 */
	public Boolean getBuyerFeatureSet(Integer buyerId, String feature) {
		Boolean featureValue = null;
		try{
			String buyerFeature = spnBuyerServices.getBuyerFeatureSetValue(buyerId,feature);
			if(buyerFeature != null){
				featureValue = new Boolean(true);
			}else{
				featureValue = new Boolean(false);
			}
		}
		catch(Exception e){
			logger.error("Error finding buyer feature set for the buyer ID " + buyerId, e);
		}
		return featureValue;
	}

}