package com.servicelive.orderfulfillment.orderprep.processor.common;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import org.apache.commons.lang.StringUtils;


public abstract class AbstractOrderContactAndLocationEnhancer extends AbstractOrderEnhancer {
    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        initializeServiceContactFields(serviceOrder);
        
        //We will if these are needed than turn them on otherwise no need to create empty records
        //addBuyerSupportContactAndLocation(serviceOrder, orderEnhancementContext);
        //setEmptyEndUserContactAndLocation(serviceOrder);

        setMiscContactsAndLocations(serviceOrder, orderEnhancementContext);
    }

    protected abstract void setMiscContactsAndLocations(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext);

    private void initializeServiceContactFields(ServiceOrder serviceOrder) {
        SOLocation svcLocation = serviceOrder.getServiceLocation();
        if (svcLocation != null) {
            svcLocation.setAptNumber(StringUtils.EMPTY);
            if (svcLocation.getZip4() == null) {
            	svcLocation.setZip4(StringUtils.EMPTY);
            }
            
            svcLocation.setCountry("US");
            if (StringUtils.isBlank(svcLocation.getStreet1()) && !StringUtils.isBlank(svcLocation.getStreet2())) {
                validationUtil.addWarnings(serviceOrder.getValidationHolder(), ProblemType.MissingStreetName);
                svcLocation.setStreet1(svcLocation.getStreet2());
            }
        }
    }

    @SuppressWarnings("unused")
	private void addBuyerSupportContactAndLocation(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        SOLocation buyerSupportLocation = createSOBuyerLocationFromBuyerInfo(orderEnhancementContext.getOrderBuyer().getBuyerInfo());
        buyerSupportLocation.setServiceOrder(serviceOrder);
        serviceOrder.addLocation(buyerSupportLocation);

        SOContact buyerSupportContact = new SOContact();
        buyerSupportContact.setContactTypeId(ContactType.PRIMARY);
        buyerSupportContact.addContactLocation(ContactLocationType.BUYER_SUPPORT);
        serviceOrder.addContact(buyerSupportContact);
    }

    private SOLocation createSOBuyerLocationFromBuyerInfo(Buyer buyer) {
        // yes, we are ignoring buyer location info ...
        SOLocation buyerSupportLocation = new SOLocation();
        buyerSupportLocation.setAptNumber(StringUtils.EMPTY);
        buyerSupportLocation.setStreet1(StringUtils.EMPTY);
        buyerSupportLocation.setStreet2(StringUtils.EMPTY);
        buyerSupportLocation.setCity(StringUtils.EMPTY);
        buyerSupportLocation.setState(StringUtils.EMPTY);
        buyerSupportLocation.setZip(StringUtils.EMPTY);
        buyerSupportLocation.setZip4(StringUtils.EMPTY);
        buyerSupportLocation.setCountry(StringUtils.EMPTY);
        buyerSupportLocation.setSoLocationTypeId(LocationType.BUYER_SUPPORT);
        return buyerSupportLocation;
    }

    @SuppressWarnings("unused")
	private void setEmptyEndUserContactAndLocation(ServiceOrder serviceOrder) {
        SOContact endUserContact = new SOContact();
        endUserContact.setContactTypeId(ContactType.PRIMARY);
        endUserContact.addContactLocation(ContactLocationType.END_USER);
        serviceOrder.addContact(endUserContact);
    }
}
