package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.util.*;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.NoteType;
import com.servicelive.orderfulfillment.domain.type.RoleType;
import com.servicelive.orderfulfillment.notification.INotificationTaskProcessor;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class AddNoteSignal extends Signal {
    protected INotificationTaskProcessor notificationTaskProcessor;
    protected IMarketPlatformBuyerBO marketPlatformBuyerBO;

    @Override
	protected void update(SOElement soe, ServiceOrder so){
        logger.debug("Begin AddNoteSignal.update");
		SONote note = (SONote)soe;
		note.setServiceOrder(so);
        so.addNote(note);
        serviceOrderDao.save(note);

        sendEmail(so, note);
        logger.debug("End AddNoteSignal.update");
	}

    private void sendEmail(ServiceOrder so, SONote note) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ProcessVariableUtil.PVKEY_SVC_ORDER_ID, so.getSoId());
        if (note.getNoteTypeId() == NoteType.SUPPORT.getId()){
            notificationTaskProcessor.processNotificationTask("emailForSupportNote", params);
        } else if(note.isSendEmail()){
            //Other parameters needed for the emails are ROLE_IND and CONSUMER
            //These flags are used mainly to select the URL for the application
            //In case of professional buyer and providers it is MarketFrontend but in
            //case of simple buyer is B2C application
            //if buyer has created the note than email will go to provider
            //For emails going to provider it is configured in the notification context
            String emailContextName = "emailRoutedProvidersForAddNote";
            if(null != so.getAcceptedProviderId()){
                emailContextName = "emailAcceptedProvidersForAddNote";
            }
            //if provider has created the note that email will go to buyer
            //For the professional buyer the default will be picked up but for the simple buyer
            //we need to see what we can do to set the ROLE_IND and CONSUMER flag for email
            if(note.getRoleId()== RoleType.Provider.getId()){
                emailContextName = "emailBuyerForAddNote";    
                //We just need a way to find if this order belongs to B2C buyer and if so than
                if(getBuyerRoleId(so.getBuyerId()) == RoleType.SimpleBuyer.getId()){
                  params.put(ProcessVariableUtil.PVKEY_SVC_ORDER_TYPE, "CB");
                }
            }
            notificationTaskProcessor.processNotificationTask(emailContextName, params);
        }
    }

    private int getBuyerRoleId(Long buyerId) {
        int buyerRole = RoleType.Buyer.getId();//default assign it professional buyer role        
        Buyer buyer = marketPlatformBuyerBO.retrieveBuyer(buyerId);
        if(null != buyer && null != buyer.getUser() && null != buyer.getUser().getRole()){
            buyerRole = buyer.getUser().getRole().getId();
        }
        return buyerRole;
    }

    @Override
    public void logServiceOrder(ServiceOrder so, SOElement request, Identification id, Map<String, Serializable> miscParams) throws ServiceOrderException {
        SONote note = (SONote) request;
        if(!note.isPrivate()){
            super.logServiceOrder(so, request, id, miscParams);
        }
    }

    @Override
    protected HashMap<String,Object> getLogMap(SOElement request, Identification id, ServiceOrder target) throws ServiceOrderException {
		HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("ROLE_TYPE", (id.isAdmin()? " an Admin" : (id.isBuyer())? "Buyer" : "Provider"));
        return map;
	}

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if(!(soe instanceof SONote)){
			returnVal.add("Invalid Type! Expected SONote received:" + soe.getTypeName());
		}
        return returnVal;
    }

    public void setNotificationTaskProcessor(INotificationTaskProcessor notificationTaskProcessor) {
        this.notificationTaskProcessor = notificationTaskProcessor;
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }
}
