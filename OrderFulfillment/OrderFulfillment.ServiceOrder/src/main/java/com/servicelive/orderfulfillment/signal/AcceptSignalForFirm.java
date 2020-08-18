package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOScheduleHistory;
import com.servicelive.orderfulfillment.domain.SOScheduleStatus;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.AcceptanceMethod;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

/**
 * @author Mustafa Motiwala
 *
 */
public class AcceptSignalForFirm extends Signal {

    /**
     * @param soe
     * @param so
     */
    protected void update(SOElement soe, ServiceOrder so)  {
        logger.debug("Begin Method:");
        ServiceOrder tmpSo = (ServiceOrder) soe;
        so.setSoTermsCondId(tmpSo.getSoTermsCondId());
        so.setProviderSOTermsCondInd(tmpSo.getProviderSOTermsCondInd());
        so.setProviderTermsCondDate(tmpSo.getProviderTermsCondDate());
        so.setAcceptedProviderId(tmpSo.getAcceptedProviderId());
        so.setAcceptedProviderResourceId(null);
        so.setAssignmentType(tmpSo.getAssignmentType());
        
        //logger.debug("so.getAcceptedResource() >>" + so.getAcceptedResource());
        //so.getAcceptedResource().setProviderResponse(ProviderResponseType.ACCEPTED);

      //  so.addContact(tmpSo.getVendorResourceContact());
       // so.addLocation(tmpSo.getVendorResourceLocation());
        so.getSOWorkflowControls().setMethodOfAcceptance(AcceptanceMethod.MANUAL.name());
      //inserting values in so_schedule
        SOScheduleStatus soSchedule =  new SOScheduleStatus();
        soSchedule.setSoId(so.getSoId());
        soSchedule.setScheduleStatusId(OFConstants.SCHEDULE_NEEDED);
        soSchedule.setVendorId(tmpSo.getAcceptedProviderId().intValue());
        soSchedule.setCreatedDate(new Date());
        soSchedule.setModifiedDate(new Date());
        soSchedule.setModifiedBy(OFConstants.MODIFIED_BY);
        so.setSoScheduleStatus(soSchedule);
        //inserting values in so_schedule_history
        SOScheduleHistory scheduleHistory = new SOScheduleHistory();
        scheduleHistory.setServiceOrder(so);
        scheduleHistory.setVendorId(tmpSo.getAcceptedProviderId().intValue());
        scheduleHistory.setScheduleStatusId(OFConstants.SCHEDULE_NEEDED);
        scheduleHistory.setModifiedBy(OFConstants.MODIFIED_BY);
        so.getScheduleHistory().add(scheduleHistory);
        
        serviceOrderDao.update(so);
        
        // send relay notification
        boolean relayServicesNotifyFlag = isRelayServicesNotificationAllowed(so);
        if (relayServicesNotifyFlag) {
        	List<Integer> acceptedVendorList = new ArrayList<Integer>();
        	acceptedVendorList.add(tmpSo.getAcceptedProviderId().intValue());
        	
        	String vendorDetails = serviceOrderDao.getVendorBNameList(acceptedVendorList);

			Map<String, String> param = new HashMap<String, String>();

			if (StringUtils.isNotEmpty(vendorDetails)) {
				param.put("firmsdetails", vendorDetails.substring(1, vendorDetails.length()-1));
			}
        	sendRelayNotification(so, OrderfulfillmentConstants.ORDER_ACCEPTED_BY_PROVIDER, param);
        }
        
        logger.debug("End method.");
    }

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if(!(soe instanceof ServiceOrder)){
            returnVal.add("Invalid type! Expected ServiceOrder, received:" + soe.getTypeName());
        }
       
        return returnVal;
    }
}
