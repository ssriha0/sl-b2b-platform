package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.bus.EventAdapter;
import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class NotifyBuyerSignal extends Signal {

	private EventAdapter eventAdapter;
	
	/**
     * Only support updating the SubStatus on the ServiceOrder.
     * @param element - Input ServiceOrder with attributes which need to be updated.
     * @param dst - Target/Destination. This is JPA managed Entity
     */
    @Override
    protected void update(SOElement element, ServiceOrder so) {
    	logger.debug("Begin NotifyBuyerSignal.update");
		SONote note = (SONote)element;
		note.setServiceOrder(so);
		
		String assurantStatusId = removeAndReturnAssurantStatusIdFromSubject(note);
		
		//creating a brand new note object
		serviceOrderDao.save(note);
		
		logger.debug("NotifyBuyerSignal.update: Saved new note, now firing event.");
    	
        fireEvent(so, assurantStatusId, note.getNote());
        logger.debug("End NotifyBuyerSignal.update");
    }
    
    // The assurantStatusId was prepended to the subject in AssurantIncidentEventTrackerAction
    private String removeAndReturnAssurantStatusIdFromSubject(SONote note) {
    	// This path will not end up going through the MarketESB, so remove the prepended
		// assurantStatusId from the subject
		String subject = note.getSubject();
		String assurantStatusId = "";
		int index = subject.indexOf(")");
		if (index > -1) {
			assurantStatusId = subject.substring(0, index);
			if (subject.length() > index + 2) {
				note.setSubject(subject.substring(index + 1));
			} else {
				note.setSubject("");
			}
		}
		return assurantStatusId;
    }

    private void fireEvent(ServiceOrder so, String assurantStatusId, String note) {
	    ServiceOrderEvent event = new ServiceOrderEvent(so.getSoId(),so, OrderEventType.BUYER_NOTIFICATION);
	    event.addHeader(EventHeader.BUYER_COMPANY_ID, String.valueOf(so.getBuyerId()));
	    event.addHeader(EventHeader.BUYER_RESOURCE_ID, String.valueOf(so.getBuyerResourceId()));
	    event.addHeader(EventHeader.SUBSTATUS_ACTION, assurantStatusId);
		event.addHeader(EventHeader.SUBSTATUS_REASON, note);
	    eventAdapter.raiseEvent(event);
    }
    
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if(!(soe instanceof SONote)){
			returnVal.add("Invalid Type! Expected SONote received:" + soe.getTypeName());
		}
        return returnVal;
    }


    public void setEventAdapter(EventAdapter eventAdapter) {
        this.eventAdapter = eventAdapter;
    }

}
