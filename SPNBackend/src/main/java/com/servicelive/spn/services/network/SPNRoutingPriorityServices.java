/**
 *
 */
package com.servicelive.spn.services.network;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.spn.detached.LookupCriteriaDTO;
import com.servicelive.domain.spn.detached.SPNCoverageDTO;
import com.servicelive.domain.spn.detached.SPNPerformanceCriteria;
import com.servicelive.domain.spn.detached.SPNTierDTO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.detached.SPNRoutingDTO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.dao.routingPriority.SPNRoutingPriorityDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author hoza
 *
 */

public class SPNRoutingPriorityServices extends BaseServices {
	
	private SPNRoutingPriorityDao spnRoutingPriorityDao;
	
	/**check for routing priority
	 * @param spnId
	 * @return spnHdr
	 * @throws BusinessServiceException
	 */
	public SPNHeader checkForCriteria(Integer spnId)throws BusinessServiceException{
		SPNHeader spnHdr = null;
		try{
			spnHdr = spnRoutingPriorityDao.checkForCriteria(spnId);
			
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices checkForCriteria() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return spnHdr;
	}
	
	/**fetch look up criteria
	 * @return criteriaDTO
	 * @throws BusinessServiceException
	 */
	public List<LookupCriteriaDTO> fetchLookupCriteria() throws BusinessServiceException{
		List<LookupCriteriaDTO> criteriaDTO = new ArrayList<LookupCriteriaDTO>();
		try{
			criteriaDTO = spnRoutingPriorityDao.fetchLookupCriteria();
			
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices fetchLookupCriteria() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return criteriaDTO;
		
	}
	
	/**fetch modified date
	 * @return Date
	 * @throws BusinessServiceException
	 */
	public Date fetchModifiedDate() throws BusinessServiceException{
		Date modifiedDate = null;
		try{
			modifiedDate = spnRoutingPriorityDao.fetchModifiedDate();
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices fetchModifiedDate() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return modifiedDate;
	}
	
	/**fetch spn performance criteria
	 * @param spnId
	 * @return criteria
	 * @throws BusinessServiceException
	 */
	public List<SPNPerformanceCriteria> fetchCriteria(Integer spnId) throws BusinessServiceException{
		List<SPNPerformanceCriteria> criteria = new ArrayList<SPNPerformanceCriteria>();
		try{
			criteria = spnRoutingPriorityDao.fetchCriteria(spnId);
			
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices fetchCriteria() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return criteria;
	}

	/**fetch spn performance criteria
	 * @param spnId
	 * @return tiers
	 * @throws BusinessServiceException
	 */
	public List<SPNTierDTO> fetchTiers(Integer spnId) throws BusinessServiceException{
		List<SPNTierDTO> tiers = new ArrayList<SPNTierDTO>();
		try{
			tiers = spnRoutingPriorityDao.fetchTiers(spnId);
			
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices fetchTiers() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return tiers;
	}

	
	/**fetch approved SPN providers
	 * @param spnId
	 * @return providerList
	 * @throws BusinessServiceException
	 */
	public List<Integer> fetchSPNProviders(Integer spnId, Integer aliasSpnId) throws BusinessServiceException{
		List<Integer> providerList = null;
		try{
			providerList = spnRoutingPriorityDao.fetchSPNProviders(spnId, aliasSpnId);
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices fetchSPNProviders() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return providerList;
	}

	/**fetch approved SPN firms
	 * @param spnId
	 * @return firmList
	 * @throws BusinessServiceException
	 */
	public Map<Integer, Integer> fetchSPNFirms(Integer spnId, Integer aliasSpnId) throws BusinessServiceException{
		Map<Integer, Integer> firmMap = new HashMap<Integer, Integer>();
		try{
			List<SPNCoverageDTO> coverageList = spnRoutingPriorityDao.fetchSPNFirms(spnId, aliasSpnId);
			if(null != coverageList){
				for(SPNCoverageDTO coverage : coverageList){
					if(null != coverage){
						firmMap.put(coverage.getFirmId(), coverage.getSpnId());
					}
				}
			}
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices fetchSPNFirms() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return firmMap;
	}
	
	
	/**fetch general scores of provider
	 * @param performanceCriteria
	 * @param providerList
	 * @return providerScores
	 * @throws BusinessServiceException
	 */
	public List<LookupCriteriaDTO> getProviderScores(List<SPNPerformanceCriteria> performanceCriteria, 
			List<Integer> providerList) throws BusinessServiceException{

		List<LookupCriteriaDTO> providerScores = null;
		try{
			providerScores = spnRoutingPriorityDao.getProviderScores(performanceCriteria, providerList);
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices getProviderScores() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return providerScores;
	}
	
	/**fetch general scores of firm
	 * @param performanceCriteria
	 * @param firmList
	 * @return firmScores
	 * @throws BusinessServiceException
	 */
	public List<LookupCriteriaDTO> getFirmScores(List<SPNPerformanceCriteria> performanceCriteria, 
			List<Integer> firmList) throws BusinessServiceException{

		List<LookupCriteriaDTO> firmScores = null;
		try{
			firmScores = spnRoutingPriorityDao.getFirmScores(performanceCriteria, firmList);
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices getFirmScores() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return firmScores;
	}
	
	/**fetch provider coverage details
	 * @param spnId
	 * @param aliasSpnId
	 * @return coverage
	 * @throws BusinessServiceException
	 */
	public List<SPNCoverageDTO> getProviderCoverage(Integer spnId, Integer aliasSpnId) throws BusinessServiceException{
		List<SPNCoverageDTO> coverage = null;
		try{
			coverage = spnRoutingPriorityDao.getProviderCoverage(spnId, aliasSpnId);
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices getProviderCoverage() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return coverage;
	}
	
	/**fetch firm coverage details
	 * @param spnId
	 * @return coverage
	 * @throws BusinessServiceException
	 */
	public List<SPNCoverageDTO> getFirmCoverage(Integer spnId, Integer aliasSpnId) throws BusinessServiceException{
		List<SPNCoverageDTO> coverage = null;
		List<SPNCoverageDTO> finalCoverage = new ArrayList<SPNCoverageDTO>();
		try{
			coverage = spnRoutingPriorityDao.getFirmCoverage(spnId, aliasSpnId);
			//Removing duplicate entries in coverage list while EDIT SPN
			//Removing firm in alias SPN if the same firm exists in original.
			if(null != coverage && null != aliasSpnId){
				for(SPNCoverageDTO coverageDTO : coverage){
					if(coverageDTO.getSpnId().equals(spnId)){
						finalCoverage.add(coverageDTO);
					}
					else if(coverageDTO.getSpnId().equals(aliasSpnId)){
						int count = 0;
						for(SPNCoverageDTO spnCoverage : coverage){
							if(coverageDTO.getMemberId().equals(spnCoverage.getMemberId())){
								count++;
								if(count == 2){
									break;
								}
							}
						}
						if(count == 1){
							finalCoverage.add(coverageDTO);
						}
					}
				}
				return finalCoverage;
			}
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices getFirmCoverage() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return coverage;
	}
	
	/**fetch eligible prov count for all firms
	 * @param memberList
	 * @param spnId
	 * @return coverage
	 * @throws BusinessServiceException
	 */
	public List<SPNCoverageDTO> getProvCount(Map<Integer, Integer> memberList, Integer spnId, Integer aliasSpnId) throws BusinessServiceException{
		List<SPNCoverageDTO> count = null;
		try{
			count = spnRoutingPriorityDao.getProvCount(memberList, spnId, aliasSpnId);
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices getProvCount() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return count;
	}
	
	/**save
	 * @param spnId
	 * @param routingDTO
	 * @throws BusinessServiceException
	 */	
	public void saveRoutingPriorty(SPNRoutingDTO routingDTO, Integer spnId) throws BusinessServiceException{
		try{
			//save routing details in hdr
			spnRoutingPriorityDao.saveHdrDetails(routingDTO.getSpnHdr());
			//save selected criteria
			spnRoutingPriorityDao.saveCriteria(routingDTO.getPerformanceCriteria(), spnId);
			//save routing tiers
			List<SPNTierDTO> tiers = new ArrayList<SPNTierDTO>();
			if(null != routingDTO.getSpnTiers() && routingDTO.getSpnTiers().size() > 0){
				for(SPNTierDTO tier : routingDTO.getSpnTiers()){
					if(null != tier){
						if(tier.getNoOfMembers() != 0 || SPNBackendConstants.NO_OF_TIERS == tier.getTierId()){
							tiers.add(tier);
						}
					}
				}
			}
			spnRoutingPriorityDao.saveTiers(tiers, spnId);
			//save provider/firm score
			if(SPNBackendConstants.PROVIDER.equals(routingDTO.getSpnHdr().getCriteriaLevel())){
				spnRoutingPriorityDao.saveProviderScore(routingDTO.getCoveragelist(), spnId);
			}
			else if(SPNBackendConstants.FIRM.equals(routingDTO.getSpnHdr().getCriteriaLevel())){
				spnRoutingPriorityDao.saveFirmScore(routingDTO.getCoveragelist(), spnId);
			}			
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices saveRoutingPriorty() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}

	/**save history
	 * @param spnId
	 * @param routingDTO
	 * @param userName
	 * @throws BusinessServiceException
	 */	
	public void saveRoutingPriortyHistory(SPNRoutingDTO routingDTO,Integer spnId, String userName) throws BusinessServiceException{
		try{
			//save spnet_hdr history
			spnRoutingPriorityDao.saveHdrHistory(routingDTO.getSpnHdr());
			//save network history
			spnRoutingPriorityDao.saveNetworkHistory(spnId, userName, routingDTO.getAction());
			//save selected criteria history
			spnRoutingPriorityDao.saveCriteriaHistory(routingDTO.getPerformanceCriteria(), spnId);
			//save score history
			//Save history is done by triggers 'spnpfsh_upd_after' and 'spnspsh_upd_after'
			/*if(SPNBackendConstants.PROVIDER.equals(routingDTO.getSpnHdr().getCriteriaLevel())){
				spnRoutingPriorityDao.saveProviderScoreHistory(routingDTO.getCoveragelist(), spnId);
			}
			else if(SPNBackendConstants.FIRM.equals(routingDTO.getSpnHdr().getCriteriaLevel())){
				spnRoutingPriorityDao.saveFirmScoreHistory(routingDTO.getCoveragelist(), spnId);
			}*/			
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices saveRoutingPriorty() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
 	}

	/**update routing priority status
	 * @param spnHeader
	 * @param userName
	 * @throws BusinessServiceException
	 */	
	public void updateRoutingStatus(SPNHeader spnHeader, String userName, String action) throws BusinessServiceException{
		try{
			// update spn_hdr
			spnRoutingPriorityDao.saveHdrDetails(spnHeader);
			//save spnet_hdr history
			spnRoutingPriorityDao.saveHdrHistory(spnHeader);
			//save network history
			spnRoutingPriorityDao.saveNetworkHistory(spnHeader.getSpnId(), userName, action);
		}
		catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices updateRoutingStatus() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	
	/**fetch last completed date
	 * @param duplicateList
	 * @param criteriaLevel
	 * @throws BusinessServiceException
	 */	
	public List<SPNCoverageDTO> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId) throws BusinessServiceException{
		List<SPNCoverageDTO> dateList = null;
		try{
			dateList = spnRoutingPriorityDao.getCompletedDate(duplicateList, criteriaLevel, buyerId);
		}
		catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices getCompletedDate() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return dateList;
	}

	/**check for alias SPN
	 * @param spnId
	 * @param aliasSpnId
	 * @throws BusinessServiceException
	 */	
	public Integer checkForAlias(Integer spnId)  throws BusinessServiceException{
		Integer aliasSpnId = null;
		try{
			aliasSpnId = spnRoutingPriorityDao.checkForAlias(spnId);
				
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices checkForAlias() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return aliasSpnId;
	}
	
	/**save
	 * @param spnId
	 * @param routingDTO
	 * @throws BusinessServiceException
	 */	
	public void saveRoutingPriortyForAlias(SPNRoutingDTO routingDTO) throws BusinessServiceException{
		try{
			//save routing details in hdr
			spnRoutingPriorityDao.saveHdrDetailsForAlias(routingDTO.getSpnHdr(), routingDTO.getAliasSpnId());	
			//save selected criteria
			spnRoutingPriorityDao.saveCriteria(routingDTO.getPerformanceCriteria(), routingDTO.getAliasSpnId());
			
		}catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices saveRoutingPriortyForAlias() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}
	
	/**update routing priority status
	 * @param spnHeader
	 * @param userName
	 * @throws BusinessServiceException
	 */	
	public void updateStatusForAlias(SPNHeader spnHeader, Integer aliasSpnId) throws BusinessServiceException{
		try{
			// update spn_hdr
			spnRoutingPriorityDao.saveHdrDetailsForAlias(spnHeader, aliasSpnId);
		}
		catch(DataServiceException e){
			logger.error("Exception in SPNRoutingPriorityServices updateStatusForAlias() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}
	
	@Override
	protected void handleDates(Object entity) {
		// TODO Auto-generated method stub
		
	}

	public SPNRoutingPriorityDao getSpnRoutingPriorityDao() {
		return spnRoutingPriorityDao;
	}

	public void setSpnRoutingPriorityDao(SPNRoutingPriorityDao spnRoutingPriorityDao) {
		this.spnRoutingPriorityDao = spnRoutingPriorityDao;
	}


}