package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "buyer_so_template", uniqueConstraints = { @UniqueConstraint(columnNames = {"buyer_id", "template_name" }) })
public class BuyerOrderTemplateRecord {

    @Id
    @Column(name="template_id")
    private Integer templateID;

    @Column(name="template_name", nullable=false)
    private String templateName;

    @Column(name="buyer_id", nullable=false)
    private Long buyerID;

    @Column(name="primary_skill_category_id")
    private Integer primarySkillCategoryId;

    @Column(name="template_data")
    private String templateXmlData;

    public Integer getTemplateID() {
        return templateID;
    }

    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Long getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(Long buyerID) {
        this.buyerID = buyerID;
    }

    public Integer getPrimarySkillCategoryId() {
        return primarySkillCategoryId;
    }

    public void setPrimarySkillCategoryId(Integer primarySkillCategoryId) {
        this.primarySkillCategoryId = primarySkillCategoryId;
    }

    public String getTemplateXmlData() {
        return templateXmlData;
    }

    public void setTemplateXmlData(String templateXmlData) {
        this.templateXmlData = templateXmlData;
    }
}
