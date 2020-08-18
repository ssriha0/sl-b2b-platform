package com.servicelive.domain.autoclose;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * AutoCloseRuleCriteria entity.
 * 
 */
@Entity
@Table(name = "so_auto_close_rule_status")
public class SoAutoCloseRuleStatus  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9077177299188337164L;



	// Fields
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "autoclose_rule_status_id", unique = true, nullable = false)
	private Integer autoRuleStatusId;
	
    
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="autoclose_id")
    @ForeignKey(name="FK_so_auto_close_rule_status")
	private SoAutoCloseAction SoAutoCloseAction;
    
    @Column(name = "rule_hdr_id")
    private Integer autoCloseRuleHdrId;
    
    
	
    @Column(name = "rule_status")
    private String autoCloseRuleStatus;
    
    @Column(name = "rule_value")
    private String autoCloseRuleValue;

	// Constructors

	/** default constructor */
	public SoAutoCloseRuleStatus() {
		super();
	}

	public Integer getAutoRuleStatusId() {
		return autoRuleStatusId;
	}

	public void setAutoRuleStatusId(Integer autoRuleStatusId) {
		this.autoRuleStatusId = autoRuleStatusId;
	}

	

	public SoAutoCloseAction getSoAutoCloseAction() {
		return SoAutoCloseAction;
	}

	public void setSoAutoCloseAction(SoAutoCloseAction soAutoCloseAction) {
		SoAutoCloseAction = soAutoCloseAction;
	}

	public Integer getAutoCloseRuleHdrId() {
		return autoCloseRuleHdrId;
	}

	public void setAutoCloseRuleHdrId(Integer autoCloseRuleHdrId) {
		this.autoCloseRuleHdrId = autoCloseRuleHdrId;
	}

	public String getAutoCloseRuleStatus() {
		return autoCloseRuleStatus;
	}

	public void setAutoCloseRuleStatus(String autoCloseRuleStatus) {
		this.autoCloseRuleStatus = autoCloseRuleStatus;
	}

	public String getAutoCloseRuleValue() {
		return autoCloseRuleValue;
	}

	public void setAutoCloseRuleValue(String autoCloseRuleValue) {
		this.autoCloseRuleValue = autoCloseRuleValue;
	}

	
	
	
}