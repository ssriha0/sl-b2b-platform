package com.servicelive.domain.buyer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * SimpleBuyerFeature used for quick lookups. No lazy loaded entities.
 */
@Entity
@Table(name="buyer_feature_set" )
@XmlRootElement()
public class SimpleBuyerFeature implements Serializable {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="buyer_feature_id", unique=true, nullable=false)
    private Integer id;

    @Column( name="buyer_id")
    private Integer buyerId;

    @Column( name="feature")
    private String feature;

    @Column(name = "active_ind")
    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
