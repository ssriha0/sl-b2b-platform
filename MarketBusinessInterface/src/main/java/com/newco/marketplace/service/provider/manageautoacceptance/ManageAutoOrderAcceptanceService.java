package com.newco.marketplace.service.provider.manageautoacceptance;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ManageAutoOrderAcceptanceService {

	/**fetch new & updated active pending CRA rules
	 * @param vendorId
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	List<ManageAutoOrderAcceptanceDTO> fetchPendingCARRuleList(Integer vendorId) throws BusinessServiceException;

	/**save rule status
	 * @param acceptanceDTO
	 * @return void
	 */
	void saveRules(ManageAutoOrderAcceptanceDTO acceptanceDTO) throws BusinessServiceException;

	/**fetch CAR enabled buyers for provider
	 * @param vendorId
	 * @return Map<String, Integer>
	 */
	Map<String, String> fetchCARBuyers(Integer vendorId) throws BusinessServiceException;

	/**fetch CAR rules of a buyer
	 * @param buyerId
	 * @param vendorId
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	List<ManageAutoOrderAcceptanceDTO> fetchRulesForBuyer(Integer buyerId, Integer vendorId)throws BusinessServiceException;

}
