package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.buyer.BuyerResource;

/**
 * RoutingRuleHdrHist entity.
 * 
 */
@Entity
@Table(name = "routing_rule_hdr_hist")
public class RoutingRuleHdrHist extends BaseDomain {

	private static final long serialVersionUID = -20091016L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_hdr_hist_id", unique = true, nullable = false)
	private Integer routingRuleHdrHistId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_hdr_id", nullable = false)
    private RoutingRuleHdr routingRuleHdr;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_buyer_assoc_id")
    @ForeignKey(name="FK_routing_rule_hdr_routing_rule_hist_buyer_assoc_id")
	private RoutingRuleBuyerAssoc routingRuleBuyerAssoc;

	@Column(name = "rule_name", length = 30)
	private String ruleName;
	
	@Column(name = "rule_status")
	private String ruleStatus;
	
	@Column(name = "rule_comment")
	private String ruleComment;
	
	@Column(name = "rule_action")
	private String ruleAction;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_by", referencedColumnName="user_name")
	private BuyerResource modifiedBy;	
	
	@Column(name = "file_name")
	private String fileName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "archive_date")
	private Date archiveDate = new Date();
	
	
	/** default constructor */
	public RoutingRuleHdrHist() {
		super();
	}

	

	/**
	 * @return the routingRuleHdrHistId
	 */
	public Integer getRoutingRuleHdrHistId()
	{
		return routingRuleHdrHistId;
	}


	/**
	 * @param routingRuleHdrHistId the routingRuleHdrHistId to set
	 */
	public void setRoutingRuleHdrHistId(Integer routingRuleHdrHistId)
	{
		this.routingRuleHdrHistId = routingRuleHdrHistId;
	}



	/**
	 * @return the ruleName
	 */
	public String getRuleName()
	{
		return ruleName;
	}


	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName)
	{
		this.ruleName = ruleName;
	}


	/**
	 * @return the ruleStatus
	 */
	public String getRuleStatus()
	{
		return ruleStatus;
	}


	/**
	 * @param ruleStatus the ruleStatus to set
	 */
	public void setRuleStatus(String ruleStatus)
	{
		this.ruleStatus = ruleStatus;
	}


	/**
	 * @return the ruleComment
	 */
	public String getRuleComment()
	{
		return ruleComment;
	}


	/**
	 * @param ruleComment the ruleComment to set
	 */
	public void setRuleComment(String ruleComment)
	{
		this.ruleComment = ruleComment;
	}


	/**
	 * @return the ruleAction
	 */
	public String getRuleAction()
	{
		return ruleAction;
	}


	/**
	 * @param ruleAction the ruleAction to set
	 */
	public void setRuleAction(String ruleAction)
	{
		this.ruleAction = ruleAction;
	}

	/**
	 * 
	 * @return modifiedBy
	 */
	public BuyerResource getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * 
	 * @param modifiedBy returns the BuyerResource
	 */
	public void setModifiedBy(BuyerResource modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the routingRuleBuyerAssoc
	 */
	public RoutingRuleBuyerAssoc getRoutingRuleBuyerAssoc()
	{
		return routingRuleBuyerAssoc;
	}

	/**
	 * @param routingRuleBuyerAssoc the routingRuleBuyerAssoc to set
	 */
	public void setRoutingRuleBuyerAssoc(RoutingRuleBuyerAssoc routingRuleBuyerAssoc)
	{
		this.routingRuleBuyerAssoc = routingRuleBuyerAssoc;
	}

	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}

	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public Date getArchiveDate() {
		return archiveDate;
	}



	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	

}