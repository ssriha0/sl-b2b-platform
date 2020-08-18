package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
/**
 * RoutingRuleHdr entity.
 * 
 */
@Entity
@Table(name = "routing_rule_hdr")
public class RoutingRuleHdr extends LoggableBaseDomain {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7205715512174635301L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_hdr_id", unique = true, nullable = false)
	private Integer routingRuleHdrId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_buyer_assoc_id")
    @ForeignKey(name="FK_routing_rule_hdr_routing_rule_buyer_assoc_id")
	private RoutingRuleBuyerAssoc routingRuleBuyerAssoc;
	
	@OneToMany(cascade= { CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<RoutingRulePrice> RoutingRulePrice;
	
	@OneToMany(cascade= { CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<RoutingRuleCriteria> RoutingRuleCriteria;
	 
	@OneToMany(fetch=FetchType.LAZY, cascade= { CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<RoutingRuleVendor> RoutingRuleVendor;
	
	

	@OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<RoutingRuleAlert> routingRuleAlerts;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<RoutingRuleError> routingRuleError;
	
	@Column(name = "rule_name", length = 30)
	private String ruleName;
	
	@Column(name = "rule_status")
	private String ruleStatus;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
    @JoinColumn(name="contact_id")
    @ForeignKey(name="FK_routing_rule_hdr_contact_id")
	private Contact contact;
	
	@Column(name = "rule_comment")
	private String ruleComment;
	
	@Column(name = "rule_substatus")
	private String ruleSubstatus;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<RoutingRuleHdrHist> routingRuleHdrHist;
	
	@Transient
	private RoutingRuleQuickViewVO ruleQuickView;
	
	@Transient
	private List<String> fileNames;
	
	@Transient
	private String inactiveConflict; 
	
	@Transient
	private Boolean ruleUpdated = false;
	
	// Constructors
	
	// SL 15642 Adding auto accept history table 
	@OneToMany(fetch=FetchType.LAZY, cascade= { CascadeType.ALL})
	@JoinColumn(name="routing_rule_hdr_id")
	private List<AutoAcceptHistory> AutoAcceptHistory;
	
	//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000 
	@Transient
	private Boolean forceActiveInd=false;
			
			
	/**
	 * @return the autoAcceptHistory
	 */
	public List<AutoAcceptHistory> getAutoAcceptHistory() {
		return AutoAcceptHistory;
	}

	/**
	* @param autoAcceptHistory the autoAcceptHistory to set
	 */
	public void setAutoAcceptHistory(List<AutoAcceptHistory> autoAcceptHistory) {
		AutoAcceptHistory = autoAcceptHistory;
	}
	public RoutingRuleQuickViewVO getRuleQuickView() {
		return ruleQuickView;
	}

	public void setRuleQuickView(RoutingRuleQuickViewVO ruleQuickView) {
		this.ruleQuickView = ruleQuickView;
	}

	/** default constructor */
	public RoutingRuleHdr() {
		super();
	}

	// Property accessors
	
	public Integer getRoutingRuleHdrId() {
		return this.routingRuleHdrId;
	}

	public void setRoutingRuleHdrId(Integer routingRuleHdrId) {
		this.routingRuleHdrId = routingRuleHdrId;
	}

	public RoutingRuleBuyerAssoc getRoutingRuleBuyerAssoc() {
		return this.routingRuleBuyerAssoc;
	}

	public void setRoutingRuleBuyerAssocId(RoutingRuleBuyerAssoc routingRuleBuyerAssoc) {
		this.routingRuleBuyerAssoc = routingRuleBuyerAssoc;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleStatus() {
		return this.ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getRuleComment() {
		return this.ruleComment;
	}

	public void setRuleComment(String ruleComment) {
		this.ruleComment = ruleComment;
	}

	public List<RoutingRuleCriteria> getRoutingRuleCriteria() {
		return RoutingRuleCriteria;
	}

	public void setRoutingRuleCriteria(List<RoutingRuleCriteria> routingRuleCriteria) {
		RoutingRuleCriteria = routingRuleCriteria;
	}

	public List<RoutingRuleVendor> getRoutingRuleVendor() {
		return RoutingRuleVendor;
	}

	public void setRoutingRuleVendor(List<RoutingRuleVendor> routingRuleVendor) {
		RoutingRuleVendor = routingRuleVendor;
	}

	public List<RoutingRuleAlert> getRoutingRuleAlerts() {
		return routingRuleAlerts;
	}

	public void setRoutingRuleAlerts(List<RoutingRuleAlert> routingRuleAlert) {
		this.routingRuleAlerts = routingRuleAlert;
	}

	public List<RoutingRulePrice> getRoutingRulePrice() {
		return RoutingRulePrice;
	}

	public void setRoutingRulePrice(List<RoutingRulePrice> routingRulePrice) {
		RoutingRulePrice = routingRulePrice;
	}

	public List<RoutingRuleError> getRoutingRuleError() {
		return routingRuleError;
	}

	public void setRoutingRuleError(List<RoutingRuleError> routingRuleError) {
		this.routingRuleError = routingRuleError;
	}

	public String getRuleSubstatus() {
		return ruleSubstatus;
	}

	public void setRuleSubstatus(String ruleSubstatus) {
		this.ruleSubstatus = ruleSubstatus;
	}

	public void setRoutingRuleHdrHist(List<RoutingRuleHdrHist> routingRuleHdrHist) {
		this.routingRuleHdrHist = routingRuleHdrHist;
	}

	public List<RoutingRuleHdrHist> getRoutingRuleHdrHist() {
		return routingRuleHdrHist;
	}

	public String getInactiveConflict() {
		return inactiveConflict;
	}

	public void setInactiveConflict(String inactiveConflict) {
		this.inactiveConflict = inactiveConflict;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public Boolean getRuleUpdated() {
		return ruleUpdated;
	}

	public void setRuleUpdated(Boolean ruleUpdated) {
		this.ruleUpdated = ruleUpdated;
	}
	
	public Boolean getForceActiveInd() {
		return forceActiveInd;
	}

	public void setForceActiveInd(Boolean forceActiveInd) {
		this.forceActiveInd = forceActiveInd;
	}
	
}