package com.servicelive.marketplatform.common.vo;

import com.servicelive.domain.common.NameValuePair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement()
public class CondRoutingRuleVO implements Serializable {
	private static final long serialVersionUID = 1L;

    Integer buyerId;
    String mainJobCode;
    String serviceLocationZip;
    List<NameValuePair> customRefNameValuePairs;

    private Integer ruleId;
    private String ruleName;
    private String soId;
    
    private boolean isUpdate;   
    //public ServiceOrder soForUpdateComparison;

    @XmlElement
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    @XmlElement
    public void setMainJobCode(String mainJobCode) {
        this.mainJobCode = mainJobCode;
    }

    public String getMainJobCode() {
        return mainJobCode;
    }

    @XmlElement
    public void setServiceLocationZip(String serviceLocationZip) {
        this.serviceLocationZip = serviceLocationZip;
    }

    public String getServiceLocationZip() {
        return serviceLocationZip;
    }

    @XmlElement
    public void setCustomRefNameValuePairs(List<NameValuePair> customRefNameValuePairs) {
        this.customRefNameValuePairs = customRefNameValuePairs;
    }

    public List<NameValuePair> getCustomRefNameValuePairs() {
        return customRefNameValuePairs;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }	

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public boolean isUpdate() {
		return isUpdate;
	}
	
	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSoId() {
		return soId;
	} 
	
	/*public ServiceOrder getSoForUpdateComparison() {
		return soForUpdateComparison;
	}

	public void setSoForUpdateComparison(ServiceOrder soForUpdateComparison) {
		this.soForUpdateComparison = soForUpdateComparison;
	}*/
}
