package com.servicelive.spn.dao.routingPriority;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.spn.detached.LookupCriteriaDTO;
import com.servicelive.domain.spn.detached.SPNCoverageDTO;
import com.servicelive.domain.spn.detached.SPNPerformanceCriteria;
import com.servicelive.domain.spn.detached.SPNTierDTO;
import com.servicelive.domain.spn.network.SPNHeader;


/**
 * @author
 *
 */
public interface SPNRoutingPriorityDao {

	/**check for routing priority
	 * @param spnId
	 * @return spnHdr
	 * @throws DataServiceException
	 */
	SPNHeader checkForCriteria(Integer spnId) throws DataServiceException;

	/**fetch look up criteria
	 * @return spnHdr
	 * @throws DataServiceException
	 */
	List<LookupCriteriaDTO> fetchLookupCriteria() throws DataServiceException;

	/**fetch modified date
	 * @return Date
	 * @throws DataServiceException
	 */
	Date fetchModifiedDate() throws DataServiceException;

	/**fetch spn performance criteria
	 * @param spnId
	 * @return criteria
	 * @throws BusinessServiceException
	 */
	List<SPNPerformanceCriteria> fetchCriteria(Integer spnId) throws DataServiceException;

	/**fetch spn performance criteria
	 * @param spnId
	 * @return tiers
	 * @throws BusinessServiceException
	 */
	List<SPNTierDTO> fetchTiers(Integer spnId) throws DataServiceException;
	
	/**fetch approved SPN providers
	 * @param spnId
	 * @return coveragelist
	 * @throws DataServiceException
	 */
	List<Integer> fetchSPNProviders(Integer spnId, Integer aliasSpnId) throws DataServiceException;
	
	/**fetch approved SPN firms
	 * @param spnId
	 * @return coveragelist
	 * @throws DataServiceException
	 */
	List<SPNCoverageDTO> fetchSPNFirms(Integer spnId, Integer aliasSpnId) throws DataServiceException;

	/**fetch general scores of provider
	 * @param performanceCriteria
	 * @param providerList
	 * @return providerScores
	 * @throws DataServiceException
	 */
	List<LookupCriteriaDTO> getProviderScores(List<SPNPerformanceCriteria> performanceCriteria, 
			List<Integer> providerList)throws DataServiceException;

	/**fetch general scores of firm
	 * @param performanceCriteria
	 * @param firmList
	 * @return firmScores
	 * @throws DataServiceException
	 */
	List<LookupCriteriaDTO> getFirmScores(List<SPNPerformanceCriteria> performanceCriteria,
			List<Integer> firmList)throws DataServiceException;

	/**fetch provider coverage details
	 * @param spnId
	 * @return coverage
	 * @throws DataServiceException
	 */
	List<SPNCoverageDTO> getProviderCoverage(Integer spnId, Integer aliasSpnId)throws DataServiceException;
	
	/**fetch firm coverage details
	 * @param spnId
	 * @return coverage
	 * @throws DataServiceException
	 */
	List<SPNCoverageDTO> getFirmCoverage(Integer spnId, Integer aliasSpnId)throws DataServiceException;

	/**save spnet_hdr criteria
	 * @param spnHdr
	 * @return
	 * @throws DataServiceException
	 */
	void saveHdrDetails(SPNHeader spnHdr) throws DataServiceException;

	/**save spn criteria
	 * @param performanceCriteria
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveCriteria(List<SPNPerformanceCriteria> performanceCriteria,
			Integer spnId) throws DataServiceException;

	/**save priority tiers
	 * @param spnTiers
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveTiers(List<SPNTierDTO> spnTiers, Integer spnId) throws DataServiceException;

	/**save provider score
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveProviderScore(List<SPNCoverageDTO> coveragelist, Integer spnId) throws DataServiceException;
	
	/**save firm score
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveFirmScore(List<SPNCoverageDTO> coveragelist, Integer spnId) throws DataServiceException;

	/**save spnet_hdr criteria history
	 * @param spnHdr
	 * @return
	 * @throws DataServiceException
	 */
	void saveHdrHistory(SPNHeader spnHdr) throws DataServiceException;

	/**save network history
	 * @param spnId	 
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	void saveNetworkHistory(Integer spnId, String userName, String action) throws DataServiceException;

	/**save spn criteria history
	 * @param performanceCriteria
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveCriteriaHistory(List<SPNPerformanceCriteria> performanceCriteria,
			Integer spnId) throws DataServiceException;

	/**save provider score history
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveProviderScoreHistory(List<SPNCoverageDTO> coveragelist,
			Integer spnId) throws DataServiceException;
	
	/**save firm score history
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	void saveFirmScoreHistory(List<SPNCoverageDTO> coveragelist,
			Integer spnId) throws DataServiceException;

	/**fetch eligible prov count for all firms
	 * @param memberList
	 * @param spnId
	 * @return coverage
	 * @throws DataServiceException
	 */
	List<SPNCoverageDTO> getProvCount(Map<Integer, Integer> memberList, Integer spnId, Integer aliasSpnId)throws DataServiceException;

	/**fetch last completed date
	 * @param duplicateList
	 * @param criteriaLevel
	 * @throws DataServiceException
	 */	
	List<SPNCoverageDTO> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId)throws DataServiceException;

	/**check for alias SPN
	 * @param spnId
	 * @throws DataServiceException
	 */
	Integer checkForAlias(Integer spnId) throws DataServiceException;

	/**save hdr details for alias SPN
	 * @param spnId
	 * @throws DataServiceException
	 */
	void saveHdrDetailsForAlias(SPNHeader spnHdr, Integer aliasSpnId) throws DataServiceException;

}
