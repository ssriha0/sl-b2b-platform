/**
 * 
 */
package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * @author madhup_chand
 *
 */

@Entity
@Table(name="auto_accept_history")
public class AutoAcceptHistory  {

	private static final long serialVersionUID = 20091005L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auto_accept_history_id", unique = true, nullable = false)
	private Integer autoAcceptHistoryId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_hdr_id", nullable = false)
    @ForeignKey(name="FK_auto_accept_history_routing_rule_hdr_id")
	private RoutingRuleHdr routingRuleHdr;
	
	@Column(name= "modified_by", nullable = false, length =50)
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "created_date", nullable = false)
	private Date createdDate = new Date();
	
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}


	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the routingRuleHdr
	 */
	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}

	/**
	 * @param routingRuleHdr the routingRuleHdr to set
	 */
	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}

	@Column(name = "auto_accept_status", unique = false, nullable = false)
	private String autoAcceptStatus;
	
	public String getAutoAcceptStatus() {
		return autoAcceptStatus;
	}
	
	@Column(name = "action", unique = false)
	private String action;
	
	@Column(name = "role_id", unique = false,nullable = false)
	private Integer roledId;
	
	@Column(name = "vendor_id", unique = false,nullable = true)
	private Integer vendorId;
	
	
	@Column(name = "adopted_by", unique = false,nullable = true)
	private String adoptedBy;
	
	
	
	/**
	 * @return the adoptedBy
	 */
	public String getAdoptedBy() {
		return adoptedBy;
	}


	/**
	 * @param adoptedBy the adoptedBy to set
	 */
	public void setAdoptedBy(String adoptedBy) {
		this.adoptedBy = adoptedBy;
	}

	@Column(name = "turn_off_reason", unique = false)
	private String turnOffReason;

	/**
	 * @return the autoAcceptHistoryId
	 */
	public Integer getAutoAcceptHistoryId() {
		return autoAcceptHistoryId;
	}

	/**
	 * @param autoAcceptHistoryId the autoAcceptHistoryId to set
	 */
	public void setAutoAcceptHistoryId(Integer autoAcceptHistoryId) {
		this.autoAcceptHistoryId = autoAcceptHistoryId;
	}

	

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the roledId
	 */
	public Integer getRoledId() {
		return roledId;
	}

	/**
	 * @param roledId the roledId to set
	 */
	public void setRoledId(Integer roledId) {
		this.roledId = roledId;
	}

	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	

	

	/**
	 * @return the turnOffReason
	 */
	public String getTurnOffReason() {
		return turnOffReason;
	}

	/**
	 * @param turnOffReason the turnOffReason to set
	 */
	public void setTurnOffReason(String turnOffReason) {
		this.turnOffReason = turnOffReason;
	}

	/**
	 * @param autoAcceptStatus the autoAcceptStatus to set
	 */
	public void setAutoAcceptStatus(String autoAcceptStatus) {
		this.autoAcceptStatus = autoAcceptStatus;
	}
	

}
