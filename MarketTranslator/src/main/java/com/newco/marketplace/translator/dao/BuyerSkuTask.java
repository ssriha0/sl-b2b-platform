package com.newco.marketplace.translator.dao;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * BuyerSkuTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="buyer_sku_task", uniqueConstraints = {})
public class BuyerSkuTask extends AbstractBuyerSkuTask implements java.io.Serializable {

    // Constructors

    /**
	 * 
	 */
	private static final long serialVersionUID = -8042379940997830726L;

	/** default constructor */
    public BuyerSkuTask() {
    }

	/** minimal constructor */
    public BuyerSkuTask(LuServiceTypeTemplate luServiceTypeTemplate, SkillTree skillTree, BuyerSku buyerSku) {
        super(luServiceTypeTemplate, skillTree, buyerSku);        
    }
    
    /** full constructor */
    public BuyerSkuTask(LuServiceTypeTemplate luServiceTypeTemplate, SkillTree skillTree, BuyerSku buyerSku, String taskName, String taskComments) {
        super(luServiceTypeTemplate, skillTree, buyerSku, taskName, taskComments);        
    }
   
}
