package com.servicelive.domain.spn.network;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "spnet_hdr")
public class SimpleSPNHeader {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name = "spn_id", unique = true, nullable = false, insertable = true, updatable = true)
    private Integer spnId;

    @Column(name = "spn_name")
    private String networkName;

    @OneToMany( fetch= FetchType.LAZY, cascade= { CascadeType.ALL} , mappedBy="spnTierReleasePK.spnId")
    @JoinColumn(name = "spn_id",nullable = false)
    private List<SimpleSPNTierReleaseInfo> tierReleaseInfo = new ArrayList<SimpleSPNTierReleaseInfo>(0);

    @Column(name="mp_overflow" , nullable = false, insertable = true, updatable = true)
    private Boolean marketPlaceOverFlow = Boolean.FALSE;

    public Integer getSpnId() {
        return spnId;
    }

    public void setSpnId(Integer spnId) {
        this.spnId = spnId;
    }

    public List<SimpleSPNTierReleaseInfo> getTierReleaseInfo() {
        return tierReleaseInfo;
    }

    public void setTierReleaseInfo(List<SimpleSPNTierReleaseInfo> tierReleaseInfo) {
        this.tierReleaseInfo = tierReleaseInfo;
    }

    public Boolean getMarketPlaceOverFlow() {
        return marketPlaceOverFlow;
    }

    public boolean hasMarketPlaceOverFlowIndicator() {
        return marketPlaceOverFlow != null && marketPlaceOverFlow.booleanValue();
    }

    public void setMarketPlaceOverFlow(Boolean marketPlaceOverFlow) {
        this.marketPlaceOverFlow = marketPlaceOverFlow;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }
}
