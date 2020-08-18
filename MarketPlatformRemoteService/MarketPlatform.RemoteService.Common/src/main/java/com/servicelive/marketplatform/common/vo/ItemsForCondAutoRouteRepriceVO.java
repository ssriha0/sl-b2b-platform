package com.servicelive.marketplatform.common.vo;

import com.servicelive.domain.common.NameValuePair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement()
public class ItemsForCondAutoRouteRepriceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String specialtyCode;
    private Integer condAutoRouteRuleId;
    private List<String> skus;
    private List<NameValuePair> skuPricePairList;

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    @XmlElement
    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public Integer getCondAutoRouteRuleId() {
        return condAutoRouteRuleId;
    }

    @XmlElement
    public void setCondAutoRouteRuleId(Integer condAutoRouteRuleId) {
        this.condAutoRouteRuleId = condAutoRouteRuleId;
    }

    public List<String> getSkus() {
        return skus;
    }

    @XmlElement
    public void setSkus(List<String> skus) {
        this.skus = skus;
    }

    public List<NameValuePair> getSkuPricePairList() {
        return skuPricePairList;
    }

    @XmlElement
    public void setSkuPricePairList(List<NameValuePair> skuPricePairList) {
        this.skuPricePairList = skuPricePairList;
    }
}
