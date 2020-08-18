package com.newco.marketplace.api.beans.survey;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by kjain on 1/2/2019.
 */
@XStreamAlias("buyerDetails")
public class BuyerDetails {
    @XStreamAlias("name")
    String name;
    @XStreamAlias("logo")
    String logo;
    @XStreamAlias("buyerId")
    Integer buyerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }
}
