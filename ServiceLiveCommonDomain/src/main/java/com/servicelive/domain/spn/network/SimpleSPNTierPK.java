package com.servicelive.domain.spn.network;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class SimpleSPNTierPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "spn_id", unique = false, nullable = false, insertable = false, updatable = false)
    private SimpleSPNHeader spnId;

    @Column(name="tier_id", unique=true, nullable=false)
    private Integer tierId;

    public SimpleSPNHeader getSpnId() {
        return spnId;
    }

    public void setSpnId(SimpleSPNHeader spnId) {
        this.spnId = spnId;
    }

    public Integer getTierId() {
        return tierId;
    }

    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }
}
