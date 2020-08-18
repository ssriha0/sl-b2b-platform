package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderListCriteriaForAutoRoutingVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3995914856766336141L;
	private Long buyerId;
    private String buyerTemplateName;
    private String serviceLocationZip;
    private Integer primarySkillCategoryId;
    private List<Integer> skillNodeIds;
    private List<Integer> skillServiceTypes;
    private Integer tierId;
    private String serviceOrderId;
    private boolean conditionalAutoRoute;
    private Integer conditionalRuleId;
    private boolean marketProviderSearchOff;
    private Integer spnId;
    private String saveAndAutoPost;

    public String getSaveAndAutoPost() {
		return saveAndAutoPost;
	}

	public void setSaveAndAutoPost(String saveAndAutoPost) {
		this.saveAndAutoPost = saveAndAutoPost;
	}

	public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerTemplateName(String buyerTemplateName) {
        this.buyerTemplateName = buyerTemplateName;
    }

    public String getBuyerTemplateName() {
        return buyerTemplateName;
    }

    public void setServiceLocationZip(String serviceLocationZip) {
        this.serviceLocationZip = serviceLocationZip;
    }

    public String getServiceLocationZip() {
        return serviceLocationZip;
    }

    public void setPrimarySkillCategoryId(Integer primarySkillCategoryId) {
        this.primarySkillCategoryId = primarySkillCategoryId;
    }

    public Integer getPrimarySkillCategoryId() {
        return primarySkillCategoryId;
    }

    public void setSkillNodeIds(List<Integer> skillNodeIds) {
        this.skillNodeIds = skillNodeIds;
    }

    public List<Integer> getSkillNodeIds() {
        return skillNodeIds;
    }

    public void setSkillServiceTypes(List<Integer> skillServiceTypes) {
        this.skillServiceTypes = skillServiceTypes;
    }

    public List<Integer> getSkillServiceTypes() {
        return skillServiceTypes;
    }

    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }

    public Integer getTierId() {
        return tierId;
    }

    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

     public void setConditionalAutoRoute(boolean conditionalAutoRoute) {
        this.conditionalAutoRoute = conditionalAutoRoute;
    }

    public boolean isConditionalAutoRoute() {
        return conditionalAutoRoute;
    }

    public void setMarketProviderSearchOff(boolean marketProviderSearchOff) {
        this.marketProviderSearchOff = marketProviderSearchOff;
    }

    public boolean isMarketProviderSearchOff() {
        return marketProviderSearchOff;
    }
    
    public Integer getConditionalRuleId() {
        return conditionalRuleId;
    }

    public void setConditionalRuleId(Integer conditionalRuleId) {
        this.conditionalRuleId = conditionalRuleId;
    }

    public void addSkillServiceType(Integer skillServiceType){
        if(null == skillServiceTypes) skillServiceTypes = new ArrayList<Integer>();
        skillServiceTypes.add(skillServiceType);
    }

    public void addSkillNodeId(Integer skillNodeId){
        if(null == skillNodeIds) skillNodeIds = new ArrayList<Integer>();
        skillNodeIds.add(skillNodeId);
    }
    public Integer getSpnId() {
        return spnId;
    }

    public void setSpnId(Integer spnId) {
        this.spnId = spnId;
    }
    
}
