/**
 * 
 */
package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table (name = "lu_spnet_workflow_state")
public class LookupSPNWorkflowState extends AbstractLookupDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -929029232511538057L;

	/**
	 * 
	 */
	public LookupSPNWorkflowState() {
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public LookupSPNWorkflowState(String id) {
		super();
		setId(id);
	}

	@Id 
	@Column ( name = "id", unique = true, insertable = true, length = 30, updatable =true , nullable = false)
	private String id;

	@Column ( name = "descr", insertable = true, length = 50, updatable =true , nullable = false)
	private String description;
	
	@Column ( name = "membership_status", insertable = true, length = 20, updatable =true , nullable = true)
	private String membershipStatusDescr;
	
	@Column ( name = "communication_subject", insertable = true, length = 100, updatable =true , nullable = true)
	private String communicationSubjectDescr;
	
	@Column ( name = "group_type", insertable = true, length = 100, updatable =true , nullable = true)
	private String groupType;
	
	
	@Column ( name = "membership_status_index", insertable = true,  updatable =true , nullable = true, length = 11)
	private Integer membershipStatusIndex;
	
	@Column ( name = "status_image_icon", insertable = true, length = 100, updatable =true , nullable = true)
	private String statusIcon;

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getId()
	 */
	@Override
	public Object getId() {
		return this.id;
	}
	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the membershipStatusDescr
	 */
	public String getMembershipStatusDescr() {
		return membershipStatusDescr;
	}

	/**
	 * @param membershipStatusDescr the membershipStatusDescr to set
	 */
	public void setMembershipStatusDescr(String membershipStatusDescr) {
		this.membershipStatusDescr = membershipStatusDescr;
	}

	/**
	 * @return the communicationSubjectDescr
	 */
	public String getCommunicationSubjectDescr() {
		return communicationSubjectDescr;
	}

	/**
	 * @param communicationSubjectDescr the communicationSubjectDescr to set
	 */
	public void setCommunicationSubjectDescr(String communicationSubjectDescr) {
		this.communicationSubjectDescr = communicationSubjectDescr;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Integer getMembershipStatusIndex() {
		return membershipStatusIndex;
	}

	public void setMembershipStatusIndex(Integer membershipStatusIndex) {
		this.membershipStatusIndex = membershipStatusIndex;
	}

	/**
	 * @return the statusIcon
	 */
	public String getStatusIcon() {
		return statusIcon;
	}

	/**
	 * @param statusIcon the statusIcon to set
	 */
	public void setStatusIcon(String statusIcon) {
		this.statusIcon = statusIcon;
	}
	

}
