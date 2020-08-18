package com.newco.marketplace.dao.provider.manageautoacceptance;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ManageAutoOrderAcceptanceDao {

	/**fetch new pending CAR rules
	 * @param vendorId
	 * @return
	 */
	List<ManageAutoOrderAcceptanceDTO> fetchNewCARRuleList(Integer vendorId)throws DataServiceException;

	/**fetch updated pending CAR rules
	 * @param vendorId
	 * @return
	 */
	List<ManageAutoOrderAcceptanceDTO> fetchUpdatedCARRuleList(Integer vendorId)throws DataServiceException;

	/**save rule status
	 * @param acceptanceDTO
	 * @return 
	 */
	void saveRules(ManageAutoOrderAcceptanceDTO acceptanceDTO) throws DataServiceException;

	/**fetch CAR enabled buyers for provider
	 * @param vendorId
	 * @return Map<String, Integer>
	 */
	Map<String, String> fetchCARBuyers(Integer vendorId) throws DataServiceException;

	/**fetch CAR rules of a buyer
	 * @param buyerId
	 * @param vendorId
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	List<ManageAutoOrderAcceptanceDTO> fetchRulesForBuyer(Integer buyerId, Integer vendorId)throws DataServiceException;

	/**check whether mail needs to be send
	 * @param ruleIds
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	List<ManageAutoOrderAcceptanceDTO> checkIfMailRequired(List<Integer> ruleIds) throws DataServiceException;

	/**save auto acceptance buyer mail changes
	 * @param alertTask
	 * @return void
	 */
	void saveAutoAcceptChangeBuyerMailInfo(AlertTask alertTask)throws DataServiceException;

	/**fetch ServiceLive status for a provider
	 * @param alertTask
	 * @return void
	 */
	String getServiceLiveStatus(Integer resourceId)throws DataServiceException;
	
	/**fetch name & ph.no: of provider
	 * @param resourceId
	 * @return ManageAutoOrderAcceptanceDTO
	 */
	ManageAutoOrderAcceptanceDTO getProviderDetails(Integer resourceId)throws DataServiceException;
	
	/**fetch first & last name of buyer
	 * @param alertTask
	 * @return void
	 */
	String getBuyerName(Integer buyerId)throws DataServiceException;
	
}
