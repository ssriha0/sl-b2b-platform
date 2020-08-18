package com.servicelive.domain.oldspn;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.provider.ServiceProvider;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table(name="spn_network" )
public class OldSPNNetwork {

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="spn_network_id", unique=true, nullable=false)
	private Integer spnNetworkId;

	@Column(name="spn_id", nullable=false)
	private Integer spnId;

	@ManyToOne
	@JoinColumn(name="resource_id", nullable=false)
	private ServiceProvider serviceProvider;

	@Column(name="spn_status_id", nullable=false)
	private Integer spnStatusId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="invite_datetime", nullable=false)
	private Date inviteDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="application_datetime", nullable=true)
	private Date applicationDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="member_datetime", nullable=true)
	private Date memberDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_inactive_datetime", nullable=true)
	private Date lastInactiveDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="removed_datetime", nullable=true)
	private Date removedDateTime;

	@Column(name="skill_percent_match", nullable=true)
	private Double skillPercentMatch;

	@Column(name="resource_zipcode", nullable=false)
	private String resourceZipCode;

	/**
	 * @return the spnNetworkId
	 */
	public Integer getSpnNetworkId() {
		return spnNetworkId;
	}

	/**
	 * @param spnNetworkId the spnNetworkId to set
	 */
	public void setSpnNetworkId(Integer spnNetworkId) {
		this.spnNetworkId = spnNetworkId;
	}

	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}

	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	/**
	 * @return the spnStatusId
	 */
	public Integer getSpnStatusId() {
		return spnStatusId;
	}

	/**
	 * @param spnStatusId the spnStatusId to set
	 */
	public void setSpnStatusId(Integer spnStatusId) {
		this.spnStatusId = spnStatusId;
	}

	/**
	 * @return the inviteDateTime
	 */
	public Date getInviteDateTime() {
		return inviteDateTime;
	}

	/**
	 * @param inviteDateTime the inviteDateTime to set
	 */
	public void setInviteDateTime(Date inviteDateTime) {
		this.inviteDateTime = inviteDateTime;
	}

	/**
	 * @return the applicationDateTime
	 */
	public Date getApplicationDateTime() {
		return applicationDateTime;
	}

	/**
	 * @param applicationDateTime the applicationDateTime to set
	 */
	public void setApplicationDateTime(Date applicationDateTime) {
		this.applicationDateTime = applicationDateTime;
	}

	/**
	 * @return the memberDateTime
	 */
	public Date getMemberDateTime() {
		return memberDateTime;
	}

	/**
	 * @param memberDateTime the memberDateTime to set
	 */
	public void setMemberDateTime(Date memberDateTime) {
		this.memberDateTime = memberDateTime;
	}

	/**
	 * @return the lastInactiveDateTime
	 */
	public Date getLastInactiveDateTime() {
		return lastInactiveDateTime;
	}

	/**
	 * @param lastInactiveDateTime the lastInactiveDateTime to set
	 */
	public void setLastInactiveDateTime(Date lastInactiveDateTime) {
		this.lastInactiveDateTime = lastInactiveDateTime;
	}

	/**
	 * @return the removedDateTime
	 */
	public Date getRemovedDateTime() {
		return removedDateTime;
	}

	/**
	 * @param removedDateTime the removedDateTime to set
	 */
	public void setRemovedDateTime(Date removedDateTime) {
		this.removedDateTime = removedDateTime;
	}

	/**
	 * @return the skillPercentMatch
	 */
	public Double getSkillPercentMatch() {
		return skillPercentMatch;
	}

	/**
	 * @param skillPercentMatch the skillPercentMatch to set
	 */
	public void setSkillPercentMatch(Double skillPercentMatch) {
		this.skillPercentMatch = skillPercentMatch;
	}

	/**
	 * @return the resourceZipCode
	 */
	public String getResourceZipCode() {
		return resourceZipCode;
	}

	/**
	 * @param resourceZipCode the resourceZipCode to set
	 */
	public void setResourceZipCode(String resourceZipCode) {
		this.resourceZipCode = resourceZipCode;
	}

	/**
	 * @return the serviceProvider
	 */
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	} 
}
