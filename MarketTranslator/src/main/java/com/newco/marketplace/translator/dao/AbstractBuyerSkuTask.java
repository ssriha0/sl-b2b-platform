package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractBuyerSkuTask entity provides the base persistence definition of the
 * BuyerSkuTask entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerSkuTask implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3083248023431056920L;
	private Integer skuTaskId;
	private LuServiceTypeTemplate luServiceTypeTemplate;
	private SkillTree skillTree;
	private BuyerSku buyerSku;
	private String taskName;
	private String taskComments;

	// Constructors

	/** default constructor */
	public AbstractBuyerSkuTask() {
	}

	/** minimal constructor */
	public AbstractBuyerSkuTask(LuServiceTypeTemplate luServiceTypeTemplate,
			SkillTree skillTree, BuyerSku buyerSku) {
		this.luServiceTypeTemplate = luServiceTypeTemplate;
		this.skillTree = skillTree;
		this.buyerSku = buyerSku;
	}

	/** full constructor */
	public AbstractBuyerSkuTask(LuServiceTypeTemplate luServiceTypeTemplate,
			SkillTree skillTree, BuyerSku buyerSku, String taskName,
			String taskComments) {
		this.luServiceTypeTemplate = luServiceTypeTemplate;
		this.skillTree = skillTree;
		this.buyerSku = buyerSku;
		this.taskName = taskName;
		this.taskComments = taskComments;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sku_task_id", unique = true, nullable = false)
	public Integer getSkuTaskId() {
		return this.skuTaskId;
	}

	public void setSkuTaskId(Integer skuTaskId) {
		this.skuTaskId = skuTaskId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_type_template_id", nullable = false)
	public LuServiceTypeTemplate getLuServiceTypeTemplate() {
		return this.luServiceTypeTemplate;
	}

	public void setLuServiceTypeTemplate(
			LuServiceTypeTemplate luServiceTypeTemplate) {
		this.luServiceTypeTemplate = luServiceTypeTemplate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_node_id", nullable = false)
	public SkillTree getSkillTree() {
		return this.skillTree;
	}

	public void setSkillTree(SkillTree skillTree) {
		this.skillTree = skillTree;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sku_id", nullable = false)
	public BuyerSku getBuyerSku() {
		return this.buyerSku;
	}

	public void setBuyerSku(BuyerSku buyerSku) {
		this.buyerSku = buyerSku;
	}

	@Column(name = "task_name")
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "task_comments", length = 5000)
	public String getTaskComments() {
		return this.taskComments;
	}

	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments;
	}

}