package com.servicelive.orderfulfillment.orderprep.processor.ri;


import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LocationClassification;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderContactAndLocationEnhancer;

public class OrderContactAndLocationEnhancer extends AbstractOrderContactAndLocationEnhancer {
    @Override
    public void setMiscContactsAndLocations(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        setPickupLocationForFirstPart(serviceOrder);
    }

    private void setPickupLocationForFirstPart(ServiceOrder serviceOrder) {
        if (serviceOrder.getLocations() != null) {
            for (SOLocation location : serviceOrder.getLocations()) {
                if (location.getSoLocationTypeId() == LocationType.PICKUP) {
                    SOContact pickupContact = new SOContact();
                    pickupContact.setContactTypeId(ContactType.PRIMARY);
                    pickupContact.addContactLocation(ContactLocationType.PICKUP);
                    pickupContact.setLocation(location);

                    serviceOrder.addContact(pickupContact);
                    SOPart part = getFirstSOPart(serviceOrder);
                    part.setPickupLocation(location);
                    part.setPickupContact(pickupContact);
                    break;
                }
            }
        }
        
        //added for SL-16791
        SOLocation svcLocation = serviceOrder.getServiceLocation();
        if(svcLocation.getSoLocationClassId()==null){
        svcLocation.setSoLocationClassId(LocationClassification.RESIDENTIAL);
        }
    }

    private SOPart getFirstSOPart(ServiceOrder serviceOrder) {
        if (serviceOrder.getParts() == null || serviceOrder.getParts().size() == 0) {
            //if there is a pickup location than there should be at least one part
            throw new ServiceOrderException("no part for pickup location");
        }
        return serviceOrder.getParts().get(0);
    }
}
