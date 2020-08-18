package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class TierReleaseInfoVO {
    private Integer spnId;
    private String networkName;
    private Integer currentTier;
    private Integer nextTier;
    private Long minutesUntilNextTier;
    private boolean marketOverflowIndicator;
    private Integer numberOfProviders;
    
    public TierReleaseInfoVO() {}

    public TierReleaseInfoVO(Integer spnId, Integer currentTier){
        this.spnId = spnId;
        this.currentTier = currentTier;        
    }

    public TierReleaseInfoVO(Integer spnId, String networkName, Integer currentTier) {
        this.spnId = spnId;
        this.networkName = networkName;
        this.currentTier = currentTier;
    }

    public Integer getSpnId() {
        return spnId;
    }

    public void setSpnId(Integer spnId) {
        this.spnId = spnId;
    }

    public Integer getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(Integer currentTier) {
        this.currentTier = currentTier;
    }

    public Integer getNextTier() {
        return nextTier;
    }

    public void setNextTier(Integer nextTier) {
        this.nextTier = nextTier;
    }

    public Long getMinutesUntilNextTier() {
        return minutesUntilNextTier;
    }

    public void setMinutesUntilNextTier(Long minutesUntilNextTier) {
        this.minutesUntilNextTier = minutesUntilNextTier;
    }

    public boolean hasMarketOverflowIndicator() {
        return marketOverflowIndicator;
    }

    public boolean isMarketOverflowIndicator() {
        return marketOverflowIndicator;
    }

    public void setMarketOverflowIndicator(boolean marketOverflowIndicator) {
        this.marketOverflowIndicator = marketOverflowIndicator;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }
    
    public Integer getNumberOfProviders() {
        return numberOfProviders;
    }

    public void setNumberOfProviders(Integer numberOfProviders) {
        this.numberOfProviders = numberOfProviders;
    }
    
}
