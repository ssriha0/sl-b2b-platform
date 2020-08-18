package com.servicelive.orderfulfillment.lookup;

import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.RemoteServiceStartupDependentInitializer;

public class QuickLookupCollection implements RemoteServiceStartupDependentInitializer {
    protected final Logger logger = Logger.getLogger(getClass());
    private List<IQuickLookup> quickLookupList;

    private SkillTreeLookup skillTreeLookup;
    private BuyerFeatureLookup buyerFeatureLookup;
    private ServiceTypeLookup serviceTypeLookup;
	private BuyerHoldTimeLookup buyerHoldTimeLookup;
    private TierReleaseInfoLookup tierReleaseInfoLookup;
    private ApplicationPropertyLookup applicationPropertyLookup;
    private ApplicationFlagLookup applicationFlagLookup;
    private InHomeOutboundNotificationMessageLookup  inHomeOutboundNotificationMessageLookup;
    private TimeZoneLookup timeZoneLookup;

    public QuickLookupCollection(List<IQuickLookup> quickLookupList) {
        this.quickLookupList = quickLookupList;
        initializeLocalNodeLookups();
    }

    public void initializeAllLookups() {
        initializeLocalNodeLookups();
        doRemoteServiceDependentInitialization();
    }

    public void initializeLocalNodeLookups() {
        for (IQuickLookup quickLookup : quickLookupList) {
            setupConvenienceLookupField(quickLookup);
            if (!(quickLookup instanceof IRemoteServiceDependentLookup)) {
                quickLookup.initialize();
                logger.info("QuickLookupCollection initialized " + quickLookup.getClass());
            }
        }

        logger.debug("QuickLookupCollection local node lookups initialized.");
    }

    public void doRemoteServiceDependentInitialization() {
        for (IQuickLookup quickLookup : quickLookupList) {
            if (quickLookup instanceof IRemoteServiceDependentLookup) {
                try {
                    quickLookup.initialize();
                    logger.info("QuickLookupCollection initialized " + quickLookup.getClass());
                } catch (Exception e) {
                    logger.error("QuickLookupCollection failed to initialize " + quickLookup.getClass(), e);
                }
            }
        }
        logger.debug("QuickLookupCollection remote service dependent lookups initialized.");
    }

    private void setupConvenienceLookupField(IQuickLookup quickLookup) {
        if (quickLookup instanceof SkillTreeLookup) {
            skillTreeLookup = (SkillTreeLookup) quickLookup;
        } else if (quickLookup instanceof BuyerFeatureLookup) {
            buyerFeatureLookup = (BuyerFeatureLookup) quickLookup;
        } else if (quickLookup instanceof ServiceTypeLookup) {
            serviceTypeLookup = (ServiceTypeLookup) quickLookup;
        } else if (quickLookup instanceof BuyerHoldTimeLookup) {
        	buyerHoldTimeLookup = (BuyerHoldTimeLookup) quickLookup;
        } else if (quickLookup instanceof TierReleaseInfoLookup) {
            tierReleaseInfoLookup = (TierReleaseInfoLookup) quickLookup;
        } else if(quickLookup instanceof ApplicationPropertyLookup){
            applicationPropertyLookup = (ApplicationPropertyLookup) quickLookup;
        } else if(quickLookup instanceof TimeZoneLookup){
            timeZoneLookup = (TimeZoneLookup) quickLookup;
        } else if(quickLookup instanceof ApplicationFlagLookup){
        	applicationFlagLookup = (ApplicationFlagLookup) quickLookup;
        } else if(quickLookup instanceof InHomeOutboundNotificationMessageLookup){
        	inHomeOutboundNotificationMessageLookup = (InHomeOutboundNotificationMessageLookup) quickLookup;
        }
    }

    public SkillTreeLookup getSkillTreeLookup() {
        return skillTreeLookup;
    }

    public BuyerFeatureLookup getBuyerFeatureLookup() {
        return buyerFeatureLookup;
    }

    public ServiceTypeLookup getServiceTypeLookup() {
        return serviceTypeLookup;
    }

	public BuyerHoldTimeLookup getBuyerHoldTimeLookup() {
		return buyerHoldTimeLookup;
	}

    public TierReleaseInfoLookup getTierReleaseInfoLookup() {
        return tierReleaseInfoLookup;
    }

    public ApplicationPropertyLookup getApplicationPropertyLookup() {
        return applicationPropertyLookup;
    }

    public TimeZoneLookup getTimeZoneLookup(){
        return timeZoneLookup;
    }

	public ApplicationFlagLookup getApplicationFlagLookup() {
		return applicationFlagLookup;
	}

	public InHomeOutboundNotificationMessageLookup getInHomeOutboundNotificationMessageLookup() {
		return inHomeOutboundNotificationMessageLookup;
	}
}
