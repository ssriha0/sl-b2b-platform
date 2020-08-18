package com.servicelive.orderfulfillment.notification;

import java.util.Date;
import java.util.List;

import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.marketplatform.notification.domain.NotificationTemplate;
import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public abstract class AbstractNotificationInfoCollector implements INotificationInfoCollector {

	protected IServiceOrderDao serviceOrderDao;
	protected IMarketPlatformNotificationBO notificationBO;
	private IMarketPlatformBuyerBO mktPlatformBuyerBO;
	private IMarketPlatformProviderBO mktPlatformProviderBO;
    private QuickLookupCollection quickLookupCollection;

	public AbstractNotificationInfoCollector() {
		super();
	}

	protected void fetchBuyerContactInfo(NotificationInfo notificationInfo, ServiceOrder serviceOrder) {
	    Contact buyerContactInfo = mktPlatformBuyerBO.retrieveBuyerContactInfo(serviceOrder.getBuyerId());
	    if (buyerContactInfo == null) {
	        buyerContactInfo = new Contact();
	    }
	    notificationInfo.setBuyerContactInfo(buyerContactInfo);
	}

	protected void fetchAcceptedProviderContactInfo(NotificationInfo notificationInfo, ServiceOrder serviceOrder) {
	    if (serviceOrder.getAcceptedProviderResourceId()!=null) {
	        Contact providerContactInfo = mktPlatformProviderBO.retrieveProviderResourceContactInfo(serviceOrder.getAcceptedProviderResourceId());
	        if (providerContactInfo == null) {
	            providerContactInfo = new Contact();
	        }
	        notificationInfo.setAcceptedProviderContactInfo(providerContactInfo);
	    }
	}

	protected void fetchMainSkillCategory(NotificationInfo notificationInfo, ServiceOrder serviceOrder) {
	
	    if (serviceOrder.getPrimarySkillCatId()!=null) {
            SkillNode skillNode = quickLookupCollection.getSkillTreeLookup().getSkillNodeById(serviceOrder.getPrimarySkillCatId().longValue());
	        if (skillNode == null) {
	            skillNode = new SkillNode();
	        }
	        notificationInfo.setMainSkillCategory(skillNode);
	    }
	}

    protected String getExternalOrderId(ServiceOrder so){
        if(null == so.getCustomReferences()){
            List<BuyerReferenceType> buyerRefs = mktPlatformBuyerBO.getBuyerReferenceTypes(so.getBuyerId());
            Integer buyerRefId = null;
            for(BuyerReferenceType brt : buyerRefs){
                if(null != brt.getSoIdentifier() && brt.getSoIdentifier().booleanValue()){
                    buyerRefId = brt.getBuyerRefTypeId();
                    break;
                }
            }
            if(null != buyerRefId){
                for(SOCustomReference cr : so.getCustomReferences()){
                    if(cr.getBuyerRefTypeId().equals(buyerRefId)){
                        return cr.getBuyerRefValue();
                    }
                }
            }
        }
        return "";
    }

    protected void setNotificationType(Long notificationTemplateId, NotificationInfo notificationInfo) {
        NotificationTemplate notificationTemplate = notificationBO.retrieveNotificationTemplate(notificationTemplateId);
        notificationInfo.setNotificationType(NotificationType.getType(notificationTemplate.getTypeId().intValue()));
    }
    
    protected void fetchSystemProperties(NotificationInfo notificationInfo) {
	    notificationInfo.setSystemProperties(System.getProperties());
	}
	
    protected void loadServiceOrderCollections(ServiceOrder serviceOrder) {
        // nothing to do if using workflow persistence session/manager ... 
    }

    protected void setValuesFromServiceOrder(NotificationInfo notificationInfo, ServiceOrder serviceOrder) {
        notificationInfo.setServiceOrder(serviceOrder);

        Date now = new Date();
        notificationInfo.setCurrentDate(now);

        RoutedProvider routedProvider = serviceOrder.getAcceptedResource();
        if (routedProvider == null) {
            routedProvider = new RoutedProvider();
        }
        notificationInfo.setAcceptedProvider(routedProvider);
    }
    
	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
	    this.serviceOrderDao = serviceOrderDao;
	}

	public void setNotificationBO(IMarketPlatformNotificationBO notificationBO) {
	    this.notificationBO = notificationBO;
	}

	public void setMktPlatformBuyerBO(IMarketPlatformBuyerBO mktPlatformBuyerBO) {
	    this.mktPlatformBuyerBO = mktPlatformBuyerBO;
	}

	public void setMktPlatformProviderBO(IMarketPlatformProviderBO mktPlatformProviderBO) {
	    this.mktPlatformProviderBO = mktPlatformProviderBO;
	}

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }

    protected NotificationInfo getNotification(ServiceOrder so){
		NotificationInfo notificationInfo = new NotificationInfo();
        loadServiceOrderCollections(so);
        setValuesFromServiceOrder(notificationInfo, so);
        fetchBuyerContactInfo(notificationInfo, so);
        fetchAcceptedProviderContactInfo(notificationInfo, so);
        fetchMainSkillCategory(notificationInfo, so);
        notificationInfo.setOrderId(so.getSoId());
        notificationInfo.setLaborSpendLimit(so.getSpendLimitLabor());
        notificationInfo.setPartsSpendLimit(so.getSpendLimitParts());
        notificationInfo.setPriceModel(getPriceModel(so.getPriceModel()));
		return notificationInfo;
	}

    private String getPriceModel(PriceModelType priceModelType){
        switch (priceModelType){
            case BULLETIN: return "Bulletin Board";
            case NAME_PRICE: return "Name your Price";
            case ZERO_PRICE_BID: return "Bid Order";
            default: return "";
        }
    }
}