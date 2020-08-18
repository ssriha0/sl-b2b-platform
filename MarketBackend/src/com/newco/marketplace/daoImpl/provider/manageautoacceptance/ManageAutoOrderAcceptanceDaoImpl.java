package com.newco.marketplace.daoImpl.provider.manageautoacceptance;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dao.provider.manageautoacceptance.ManageAutoOrderAcceptanceDao;
import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.sears.os.dao.impl.ABaseImplDao;


public class ManageAutoOrderAcceptanceDaoImpl extends ABaseImplDao implements 
	ManageAutoOrderAcceptanceDao{
	
	/**fetch new pending CAR rules
	 * @param vendorId
	 * @return
	 */
	public List<ManageAutoOrderAcceptanceDTO> fetchNewCARRuleList(Integer vendorId) throws DataServiceException{
		try{
			return (List<ManageAutoOrderAcceptanceDTO>)getSqlMapClient().queryForList("autoAcceptance.fetchNewCARRuleList", vendorId);
			
		} catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.fetchNewCARRuleList due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}	
	}

	/**fetch updated pending CAR rules
	 * @param vendorId
	 * @return
	 */
	public List<ManageAutoOrderAcceptanceDTO> fetchUpdatedCARRuleList(Integer vendorId) throws DataServiceException{
		try{
			return (List<ManageAutoOrderAcceptanceDTO>)getSqlMapClient().queryForList("autoAcceptance.fetchUpdatedCARRuleList", vendorId);
			
		} catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.fetchUpdatedCARRuleList due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}	
	}

	public void saveRules(ManageAutoOrderAcceptanceDTO acceptanceDTO) throws DataServiceException {
		try{
			//modify routing_rule_vendor
			update("autoAcceptance.update_ruleVendor",acceptanceDTO);
			//new entry in auto_accept_history
			insert("autoAcceptance.insert_history",acceptanceDTO);
		}
		catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.saveRules due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}	
	}

	/**fetch CAR enabled buyers for provider
	 * @param vendorId
	 * @return Map<String, String>
	 */
	public Map<String, String> fetchCARBuyers(Integer vendorId) throws DataServiceException {
		Map<String, String> carBuyers = new LinkedHashMap<String, String>();
		try{
			List<ManageAutoOrderAcceptanceDTO> buyers = (List<ManageAutoOrderAcceptanceDTO>)getSqlMapClient().queryForList("autoAcceptance.fetchCARBuyers", vendorId);
			for(ManageAutoOrderAcceptanceDTO buyer : buyers){
				carBuyers.put(buyer.getBuyerId().toString(), buyer.getBuyer());
			}
				
		} catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.fetchCARBuyers due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return carBuyers;
	}

	/**fetch CAR rules of a buyer
	 * @param buyerId
	 * @param vendorId
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	public List<ManageAutoOrderAcceptanceDTO> fetchRulesForBuyer(Integer buyerId, Integer vendorId) throws DataServiceException {
		Map<String,Integer> params = new HashMap<String, Integer>();
		params.put("buyerId", buyerId);
		params.put("vendorId", vendorId);
		
		try{
			return (List<ManageAutoOrderAcceptanceDTO>)getSqlMapClient().queryForList("autoAcceptance.fetchRulesForBuyer", params);
			
		} catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.fetchRulesForBuyer due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}	
	}

	/**check whether mail needs to be send
	 * @param ruleIds
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	public List<ManageAutoOrderAcceptanceDTO> checkIfMailRequired(List<Integer> ruleIds) throws DataServiceException{
		try{
			return (List<ManageAutoOrderAcceptanceDTO>)getSqlMapClient().queryForList("autoAcceptance.checkIfMailRequired", ruleIds);
			
		}catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.checkIfMailRequired due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}

	/**save auto acceptance buyer mail changes in alert task
	 * @param alertTask
	 * @return void
	 */
	public void saveAutoAcceptChangeBuyerMailInfo(AlertTask alertTask) {
		//insert an entry into alert task
		insert("autoAcceptance.insert_alertTask",alertTask);
		
	}
	
	/**fetch ServiceLive status for a provider
	 * @param alertTask
	 * @return void
	 */
	public String getServiceLiveStatus(Integer resourceId)throws DataServiceException{
		try{
			return (String)getSqlMapClient().queryForObject("autoAcceptance.getServiceLiveStatus", resourceId);
			
		}catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.getServiceLiveStatus due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**fetch first & last name of provider
	 * @param resourceId
	 * @return ManageAutoOrderAcceptanceDTO
	 */
	public ManageAutoOrderAcceptanceDTO getProviderDetails(Integer resourceId)throws DataServiceException{
		try{
			return (ManageAutoOrderAcceptanceDTO)getSqlMapClient().queryForObject("autoAcceptance.getProviderDetails", resourceId);
			
		}catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.getProviderDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**fetch first & last name of buyer
	 * @param buyerId
	 * @return void
	 */
	public String getBuyerName(Integer buyerId)throws DataServiceException{
		try{
			return (String)getSqlMapClient().queryForObject("autoAcceptance.getBuyerName", buyerId);
			
		}catch(Exception e) {
			logger.debug("Exception in ManageAutoOrderAcceptanceDaoImpl.getBuyerName due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
}
