package com.newco.marketplace.serviceImpl.provider.manageautoacceptance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dao.provider.manageautoacceptance.ManageAutoOrderAcceptanceDao;
import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.service.provider.manageautoacceptance.ManageAutoOrderAcceptanceService;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.RoutingRuleVendorDao;

public class ManageAutoOrderAcceptanceServiceImpl implements ManageAutoOrderAcceptanceService{

	private Log logger = LogFactory.getLog(this.getClass());
	private ManageAutoOrderAcceptanceDao manageAutoOrderAcceptanceDao;
	private RoutingRuleVendorDao routingRuleVendorDao;
	
	/**fetch new & updated pending CAR rules
	 * @param vendorId
	 * @return
	 */
	public List<ManageAutoOrderAcceptanceDTO> fetchPendingCARRuleList(Integer vendorId) throws BusinessServiceException{
		
		List<ManageAutoOrderAcceptanceDTO> pendingRules = new ArrayList<ManageAutoOrderAcceptanceDTO>();
		try{			
			//fetch new pending CAR rules
			List<ManageAutoOrderAcceptanceDTO> newRules = manageAutoOrderAcceptanceDao.fetchNewCARRuleList(vendorId);
			
			//fetch updated pending CAR rules
			List<ManageAutoOrderAcceptanceDTO> updatedRules = manageAutoOrderAcceptanceDao.fetchUpdatedCARRuleList(vendorId);
			
			if(null != newRules && newRules.size()>0){
				pendingRules.addAll(newRules);
			}
			if(null != updatedRules && updatedRules.size()>0){
				pendingRules.addAll(updatedRules);
			}
		}
		catch(DataServiceException e){
			logger.debug("Exception in ManageAutoOrderAcceptanceServiceImpl.fetchPendingCARRuleList due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}		
		return pendingRules;
	}
	
	/**save rule status
	 * @param acceptanceDTO
	 * @return String
	 */
	public void saveRules(ManageAutoOrderAcceptanceDTO acceptanceDTO) throws BusinessServiceException {
		try{
			int count = 0;
			List<ManageAutoOrderAcceptanceDTO> offRules = new ArrayList<ManageAutoOrderAcceptanceDTO>();
			if(null != acceptanceDTO){
				count = acceptanceDTO.getRuleId().size();
			
				for(int i=1; i<count; i++){
					if(acceptanceDTO.getUpdated().get(i)){
						acceptanceDTO.setRoutingRuleId(acceptanceDTO.getRuleId().get(i));
						acceptanceDTO.setAutoAcceptStatus(acceptanceDTO.getStatus().get(i));
						if(RoutingRulesConstants.AUTO_ACCEPT_ON_STATUS.equals(acceptanceDTO.getStatus().get(i))){
							acceptanceDTO.setTurnOffReason(null);
						//SL-20436
							//sets the value of opportunityEmailInd
							if (null != acceptanceDTO.getOpportunityEmailIndList()){
							acceptanceDTO.setOpportunityEmailInd(acceptanceDTO.getOpportunityEmailIndList().get(i));
						}
						}
						else{
							acceptanceDTO.setTurnOffReason(acceptanceDTO.getReason().get(i));
							//SL-20436
							acceptanceDTO.setOpportunityEmailInd(true);
													//setting data for sending email
							ManageAutoOrderAcceptanceDTO offRule = new ManageAutoOrderAcceptanceDTO();
							offRule.setRoutingRuleId(acceptanceDTO.getRoutingRuleId());
							offRule.setRuleName(acceptanceDTO.getRuleNames().get(i));
							offRule.setTurnOffReason(acceptanceDTO.getReason().get(i));
							offRule.setCreatedDate(new Date());
							offRule.setBuyerId(acceptanceDTO.getBuyerIds().get(i));
							offRules.add(offRule);
						}
						//save the updated rules
						manageAutoOrderAcceptanceDao.saveRules(acceptanceDTO);
					}				
				}
			}
			//send email to buyer admin if any rule is turned off
			if(null != offRules && 0 != offRules.size()){
				this.sendAutoAcceptStatusUpdateEmailBuyer(offRules,acceptanceDTO.getVendorId(),acceptanceDTO.getResourceId());
			}
		}
		catch(DataServiceException e){
			logger.debug("Exception in ManageAutoOrderAcceptanceServiceImpl.saveRules due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}

	/**fetch CAR enabled buyers for provider
	 * @param vendorId
	 * @return Map<String, Integer>
	 */
	public Map<String, String> fetchCARBuyers(Integer vendorId)throws BusinessServiceException{
		try{
			return manageAutoOrderAcceptanceDao.fetchCARBuyers(vendorId);
		}
		catch(DataServiceException e){
			logger.debug("Exception in ManageAutoOrderAcceptanceServiceImpl.fetchCARBuyers due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	
	/**fetch CAR rules of a buyer
	 * @param buyerId
	 * @param vendorId
	 * @return List<ManageAutoOrderAcceptanceDTO>
	 */
	public List<ManageAutoOrderAcceptanceDTO> fetchRulesForBuyer(Integer buyerId, Integer vendorId) throws BusinessServiceException {
		try{
			return manageAutoOrderAcceptanceDao.fetchRulesForBuyer(buyerId, vendorId);
		}
		catch(DataServiceException e){
			logger.debug("Exception in ManageAutoOrderAcceptanceServiceImpl.fetchRulesForBuyer due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	
	/**send email to buyer admin if auto acceptance status id turned OFF
	 * @param offRules
	 * @param vendorId
	 * @return void
	 */
	private void sendAutoAcceptStatusUpdateEmailBuyer(List<ManageAutoOrderAcceptanceDTO> offRules, Integer vendorId, Integer resourceId) throws BusinessServiceException {
		try{
			List<Integer> ruleIds = new ArrayList<Integer>();
			for(ManageAutoOrderAcceptanceDTO offRule : offRules){
				ruleIds.add(offRule.getRoutingRuleId());
			}
			//get the buyer-admins to whom mail need to be send
			List<ManageAutoOrderAcceptanceDTO> buyers = manageAutoOrderAcceptanceDao.checkIfMailRequired(ruleIds);
			//sorting the rules based on buyerId
			if(null != buyers && 0 != buyers.size()){
				Map<ManageAutoOrderAcceptanceDTO, List<ManageAutoOrderAcceptanceDTO>> buyerMap = 
								new HashMap<ManageAutoOrderAcceptanceDTO, List<ManageAutoOrderAcceptanceDTO>>();
				for(ManageAutoOrderAcceptanceDTO buyer : buyers){
					List<ManageAutoOrderAcceptanceDTO> rulesForBuyer = new ArrayList<ManageAutoOrderAcceptanceDTO>();
					for(ManageAutoOrderAcceptanceDTO offRule : offRules){
						if(buyer.getBuyerId().intValue() == offRule.getBuyerId().intValue()){
							rulesForBuyer.add(offRule);
						}
					}
					if(null != rulesForBuyer && 0 != rulesForBuyer.size()){
						buyerMap.put(buyer, rulesForBuyer);
					}					
				}
				
				//set the values in alert task
				if(null != buyerMap && 0 != buyerMap.size()){
					this.updateAlertTask(buyerMap,vendorId,resourceId);
				}					
			}			
		}
		catch(DataServiceException e){
			logger.debug("Exception in ManageAutoOrderAcceptanceServiceImpl.sendAutoAcceptStatusUpdateEmailBuyer due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}

	/**set the values in Alert task object
	 * @param buyerMap
	 * @param vendorId
	 * @return void
	 */
	private void updateAlertTask(Map<ManageAutoOrderAcceptanceDTO, List<ManageAutoOrderAcceptanceDTO>> buyerMap, Integer vendorId, Integer resourceId) {
		Iterator<Map.Entry<ManageAutoOrderAcceptanceDTO, List<ManageAutoOrderAcceptanceDTO>>> iterator = buyerMap.entrySet().iterator();
		Date currentdate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		
		while(iterator.hasNext()){
			try{
				Map.Entry<ManageAutoOrderAcceptanceDTO, List<ManageAutoOrderAcceptanceDTO>> entry = iterator.next();
				ManageAutoOrderAcceptanceDTO buyer = entry.getKey();
				List<ManageAutoOrderAcceptanceDTO> ruleList = entry.getValue();				
				
				//method to get firm name				
				ServiceProvider firm = routingRuleVendorDao.getProviderDetail(vendorId);
				//get ServiceLive status
				String status = manageAutoOrderAcceptanceDao.getServiceLiveStatus(resourceId);
				//get provider name & ph.no:
				ManageAutoOrderAcceptanceDTO provider = manageAutoOrderAcceptanceDao.getProviderDetails(resourceId);
				//formatting phone-no.s
				String primaryPh = provider.getPrimaryPhNo();
				if(10 == primaryPh.length()){
					primaryPh = primaryPh.substring(0,3)+"-"+primaryPh.substring(3,6)+"-"+primaryPh.substring(6,10);
				}				
				String altPh = provider.getAltPhNo();
				altPh = altPh.substring(0,3)+"-"+altPh.substring(3,6)+"-"+altPh.substring(6,10);
				//get provider first & last name
				String buyerName = manageAutoOrderAcceptanceDao.getBuyerName(buyer.getBuyerId());
				
				//setting the table to show rule details
				StringBuilder ruleData = new StringBuilder(Constants.RULE_TABLE_HEADER);
				for(ManageAutoOrderAcceptanceDTO rule : ruleList){
					ruleData.append(Constants.TABLE_BODY_TR);
					ruleData.append(Constants.TABLE_BODY_TD);
					ruleData.append(rule.getRuleName());
					ruleData.append(Constants.TABLE_BODY_TD_CLOSE);
					ruleData.append(Constants.TABLE_BODY_TD);
					ruleData.append(rule.getTurnOffReason());
					ruleData.append(Constants.TABLE_BODY_TD_CLOSE);
					ruleData.append(Constants.TABLE_BODY_TD);
					ruleData.append(format.format(rule.getCreatedDate()));
					ruleData.append(Constants.TABLE_BODY_TD_CLOSE);
					ruleData.append(Constants.TABLE_BODY_TR_CLOSE);
				}				
				ruleData.append(Constants.TABLE_FOOTER);
				
				//setting template input values to map
				Map<String, Object> alertMap = new HashMap<String, Object>();
				alertMap.put(Constants.FIRM_ID, Integer.toString(vendorId));				
				alertMap.put(Constants.FIRM_NAME, firm.getProviderFirm().getBusinessName());
				alertMap.put(Constants.BUYER_EMAIL, buyer.getEmailId());
				alertMap.put(Constants.BUYER_NAME, buyerName);
				alertMap.put(Constants.SERVICE_LIVE_STATUS, status);
				alertMap.put(Constants.PROVIDER_NAME, provider.getProviderName());
				alertMap.put(Constants.PRIMARY_PH, primaryPh);
				alertMap.put(Constants.ALT_PH, altPh);
				alertMap.put(Constants.RULES, ruleData.toString());
				
				//convert the map values to pipe separated key-value pairs
				String templateInputValue = createKeyValueStringFromMap(alertMap);
			
				//create AlertTask object
				AlertTask alertTask = new AlertTask();		
				alertTask.setAlertedTimestamp(null);
				alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
				alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_ID);
				//setting template_id
				alertTask.setTemplateId(AlertConstants.TEMPLATE_AUTO_ACCPET_CHANGE_BUYER_EMAIL);
				alertTask.setPriority(AlertConstants.PRIORITY);				
				alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
				alertTask.setAlertTo(buyer.getEmailId());
				alertTask.setAlertBcc(Constants.EMPTY_STRING);
				alertTask.setAlertCc(Constants.EMPTY_STRING);				
				alertTask.setCreatedDate(currentdate);
				alertTask.setModifiedDate(currentdate);
				alertTask.setTemplateInputValue(templateInputValue);
				
				//inserting an entry into alert_task
				manageAutoOrderAcceptanceDao.saveAutoAcceptChangeBuyerMailInfo(alertTask);
			}
			catch(Exception e){
				logger.debug("Exception in ManageAutoOrderAcceptanceServiceImpl.updateAlertTask due to "+e.getMessage());
			}			
		}	
	}

	/**convert map to '|' separated key-value pairs
	 * @param alertMap
	 * @return String
	 */
	private String createKeyValueStringFromMap(Map<String, Object> alertMap) {
		StringBuilder stringBuilder = new StringBuilder("");
		boolean isFirstKey = true;
		if (null != alertMap) {
			Set<String> keySet = alertMap.keySet();
			for (String key : keySet) {
				if (isFirstKey) {
					isFirstKey = !isFirstKey;
				} else {
					stringBuilder.append("|");
				}

				stringBuilder.append(key).append("=").append(alertMap.get(key));
			}
		}
		return stringBuilder.toString();
	}

	public ManageAutoOrderAcceptanceDao getManageAutoOrderAcceptanceDao() {
		return manageAutoOrderAcceptanceDao;
	}

	public void setManageAutoOrderAcceptanceDao(
			ManageAutoOrderAcceptanceDao manageAutoOrderAcceptanceDao) {
		this.manageAutoOrderAcceptanceDao = manageAutoOrderAcceptanceDao;
	}

	public RoutingRuleVendorDao getRoutingRuleVendorDao() {
		return routingRuleVendorDao;
	}

	public void setRoutingRuleVendorDao(RoutingRuleVendorDao routingRuleVendorDao) {
		this.routingRuleVendorDao = routingRuleVendorDao;
	}
}
