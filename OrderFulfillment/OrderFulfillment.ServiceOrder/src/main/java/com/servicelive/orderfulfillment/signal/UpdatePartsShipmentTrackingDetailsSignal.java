package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.bus.EventAdapter;
import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * 
 * @author <a href="mailto:slbuyeradmin@gmail.com">slbuyeradmin@gmail.com</a>
 *
 */
public class UpdatePartsShipmentTrackingDetailsSignal extends Signal {
    private EventAdapter eventAdapter;

    @Override
    protected void update(SOElement soe, ServiceOrder so) {
    	SOElementCollection soec = (SOElementCollection)soe;
    	Map<Integer, SOPart> soPartsMap = createSoPartsMapFromList(so.getParts());
    	List<SOElement> elements = soec.getElements();

    	for (SOElement element : elements) {
    		SOPart source = (SOPart) element;
    		SOPart target = soPartsMap.get(source.getPartId());
    		if (target != null) {
    			updatePart(source, target);
    		}
    	}
    	fireEvent(so);
    }
    
    private void fireEvent(ServiceOrder so) {
        ServiceOrderEvent event = new ServiceOrderEvent(so.getSoId(), so, OrderEventType.PARTS_SHIPPED);
        event.addHeader(EventHeader.BUYER_COMPANY_ID, String.valueOf(so.getBuyerId()));
        event.addHeader(EventHeader.BUYER_RESOURCE_ID, String.valueOf(so.getBuyerResourceId()));
        eventAdapter.raiseEvent(event);
    }
    
    private void updatePart(SOPart source, SOPart target) {
    	if (source.getShipCarrierId() != null) target.setShipCarrierId(source.getShipCarrierId());
    	if (source.getShipTrackNo() != null) target.setShipTrackNo(source.getShipTrackNo());
    	if (source.getShipDate() != null) target.setShipDate(source.getShipDate());
    	if (source.getReturnCarrierId() != null) target.setReturnCarrierId(source.getReturnCarrierId());
    	if (source.getReturnTrackNo() != null) target.setReturnTrackNo(source.getReturnTrackNo());
    	if (source.getReturnTrackDate() != null) target.setReturnTrackDate(source.getReturnTrackDate());
    	if (source.getCoreReturnCarrierId() != null) target.setCoreReturnCarrierId(source.getCoreReturnCarrierId());
    	if (source.getCoreReturnTrackNo() != null) target.setCoreReturnTrackNo(source.getCoreReturnTrackNo());
    	/*
    	UPDATE so_parts SET modified_date = NOW()
		<isNotNull prepend=", " property="shippingCarrier.carrierId">
			ship_carrier_id = #shippingCarrier.carrierId# 	
		</isNotNull>
		<isNotNull prepend=", " property="shippingCarrier.trackingNumber">
			ship_track_no = #shippingCarrier.trackingNumber#
		</isNotNull>
		<isNotNull prepend=", " property="shipDate">
			ship_date = #shipDate#
		</isNotNull>
		<isNotNull prepend=", " property="returnCarrier.carrierId">
			return_carrier_id = #returnCarrier.carrierId#
		</isNotNull>
		<isNotNull prepend=", " property="returnCarrier.trackingNumber">
			return_track_no = #returnCarrier.trackingNumber#
		</isNotNull>
		<isNotNull prepend=", " property="returnTrackDate">
			return_track_date = #returnTrackDate#
		</isNotNull>
		<isNotNull prepend=", " property="coreReturnCarrier.carrierId">
			core_return_carrier_id = #coreReturnCarrier.carrierId#
		</isNotNull>
		<isNotNull prepend=", " property="returnCarrier.trackingNumber">
			core_return_track_no = #coreReturnCarrier.trackingNumber#
		</isNotNull>
		WHERE
		so_id = #soId# AND part_id = #partId#
    */	
		
	}

	private Map<Integer, SOPart> createSoPartsMapFromList(List<SOPart> parts) {
    	if (parts == null) return Collections.emptyMap();
    	
    	Map<Integer, SOPart> soPartsMap = new HashMap<Integer, SOPart>(parts.size());
    	for (SOPart part : parts) {
    		soPartsMap.put(part.getPartId(), part);
    	}
		return soPartsMap;
	}

	@Override
	protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> errors = new ArrayList<String>();
        if (soe == null) {
        	errors.add("Received null value for soe parameter when object is required to update shipped parts details");
        }
        else if (!(soe instanceof SOElementCollection)){
			errors.add(String.format("Expected SOElementCollection to update shipped parts details but found '%s' instead", soe.getTypeName()));
		}
        else {
        	SOElementCollection elements = (SOElementCollection)soe;
        	if (elements.getElements() == null) {
            	errors.add("Received SOElementCollection.getElements returned null value when List<SOElement> is required to update shipped parts details");
        	}
        	else {
	        	for (SOElement element : elements.getElements()) {
	        		if (element == null) {
	                	errors.add("Encountered a null value within SOElementCollection.getElements when SOElement is required to update shipped parts details");
	        		}
	        		else if (!(element instanceof SOPart)) {
	        			errors.add(String.format("Expected all children of received SOElementCollection to be SOPart instances to update shipped parts details, but found '%s' instead", element.getTypeName()));
	        		}
	        	}
        	}
        }
        return errors;
    }

	public void setEventAdapter(EventAdapter eventAdapter) {
		this.eventAdapter = eventAdapter;
	}
}
