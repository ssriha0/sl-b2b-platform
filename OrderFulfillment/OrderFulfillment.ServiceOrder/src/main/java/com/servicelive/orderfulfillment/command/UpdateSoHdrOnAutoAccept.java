/**
 * 
 */
package com.servicelive.orderfulfillment.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleVendorVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOScheduleHistory;
import com.servicelive.orderfulfillment.domain.SOScheduleStatus;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


/**
 * @author madhup_chand
 *
 */
public class UpdateSoHdrOnAutoAccept extends SOCommand {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	
	/**
	 * @return the autoRouteHelper
	 */
	public AutoRouteHelper getAutoRouteHelper() {
		return autoRouteHelper;
	}
	/**
	 * @param autoRouteHelper the autoRouteHelper to set
	 */
	public void setAutoRouteHelper(AutoRouteHelper autoRouteHelper) {
		this.autoRouteHelper = autoRouteHelper;
	}
	AutoRouteHelper autoRouteHelper;
	//SL 15642 Start Constants for So logging cmd
	public static final String AUTO_ACCEPT_LOGGING_FIRM_NAME="AUTO_ACCEPT_LOGGING_FIRM_NAME";
	public static final String AUTO_ACCEPT_LOGGING_FIRM_ID="AUTO_ACCEPT_LOGGING_FIRM_ID";
	public static final String AUTO_ACCEPT_LOGGING_RULE_NAME="AUTO_ACCEPT_LOGGING_RULE_NAME";
	public static final String AUTO_ACCEPT_LOGGING_RULE_ID="AUTO_ACCEPT_LOGGING_RULE_ID";
	//SL 15642 End Constants for So logging cmd
	public void execute(Map<String, Object> processVariables) {
		logger.info("Auto Accept execute method invocation");
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		if(null!=serviceOrder){
				List<ProviderIdVO>  prVO=new ArrayList<ProviderIdVO>();
				prVO=autoRouteHelper.getProviderForAutoAcceptance(serviceOrder);
	    	    if(null!=prVO && prVO.size()>0){
	    	    	for(ProviderIdVO provVO: prVO){
	    			  if(null !=provVO.getResourceId()){	
	    				  
	    				    // TODO [Arun] : Why are we iterating????
	    				    // Firm Name, vendor id etc will be same for all providers in the same firm? 
	    				  
	    				  	serviceOrder.setAcceptedProviderId(Long.valueOf(provVO.getVendorId()));
	    				  	
	    				  	// Auto Acceptance is happening at firm level so no updation of accepted_resource_id
	    				    // serviceOrder.setAcceptedProviderResourceId(Long.valueOf(provVO.getResourceId()));
	    				  	// SL 15642 Setting firm id and firm name for so logging cmd
	    				  	
	    				  	processVariables.put(AUTO_ACCEPT_LOGGING_FIRM_NAME, provVO.getFirmName());
	    				  	processVariables.put(AUTO_ACCEPT_LOGGING_RULE_ID, serviceOrder.getCondAutoRouteRuleId());
	    				  	processVariables.put(AUTO_ACCEPT_LOGGING_RULE_NAME, serviceOrder.getCondAutoRouteRuleName());
	    				  	processVariables.put(AUTO_ACCEPT_LOGGING_FIRM_ID, provVO.getVendorId());
	    				  	serviceOrder.setAssignmentType("FIRM");
	    			  	}
	    		  	}
	    	    	
	    	    	// SL-18659 there is no need to insert the data multiple times
	    	    	//inserting values in so_schedule
	    	    	SOScheduleStatus soSchedule =  new SOScheduleStatus();
			        soSchedule.setSoId(serviceOrder.getSoId());
			        soSchedule.setScheduleStatusId(OFConstants.SCHEDULE_NEEDED);
			        soSchedule.setVendorId(serviceOrder.getAcceptedProviderId().intValue());
			        soSchedule.setCreatedDate(new Date());
			        soSchedule.setModifiedDate(new Date());
			        soSchedule.setModifiedBy(OFConstants.MODIFIED_BY);
			        serviceOrder.setSoScheduleStatus(soSchedule);
			        
			        //inserting values in so_schedule_history
			        SOScheduleHistory scheduleHistory = new SOScheduleHistory();
			        scheduleHistory.setServiceOrder(serviceOrder);
			        scheduleHistory.setVendorId(serviceOrder.getAcceptedProviderId().intValue());
			        scheduleHistory.setScheduleStatusId(OFConstants.SCHEDULE_NEEDED);
			        scheduleHistory.setModifiedBy(OFConstants.MODIFIED_BY);
			        serviceOrder.getScheduleHistory().add(scheduleHistory);
			        
				  	serviceOrderDao.update(serviceOrder);
				  	if(serviceOrder.getSoServiceDatetimeSlot()!=null && serviceOrder.getSoServiceDatetimeSlot().size()>0){
				  		logger.info("Auto Accept preference slot updation call start");
				  		serviceOrderDao.updateSohdrAndSlotSelectedValues(serviceOrder.getSoId(), OFConstants.PREFERENCEIND);
				  		logger.info("Auto Accept preference slot updation executed successfully");
				  	}
	    	   }
	    	    
	    	    
	    	    
		}
	}
}
