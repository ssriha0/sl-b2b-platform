package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractUserRoles entity provides the base persistence definition of the
 * UserRoles entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractUserRoles implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5142386145307716186L;
	private Integer roleId;
	private String roleName;
	private String roleDescr;
	private Byte internalRole;
	private Date createdDate;
	private Date modifiedDate;
	private Set<UserProfileRole> userProfileRoles = new HashSet<UserProfileRole>(
			0);
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

	// Constructors

	/** default constructor */
	public AbstractUserRoles() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractUserRoles(Integer roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public AbstractUserRoles(Integer roleId, String roleName, String roleDescr,
			Byte internalRole, Date createdDate, Date modifiedDate,
			Set<UserProfileRole> userProfileRoles, Set<UserProfile> userProfiles) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescr = roleDescr;
		this.internalRole = internalRole;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.userProfileRoles = userProfileRoles;
		this.userProfiles = userProfiles;
	}

	// Property accessors
	@Id
	@Column(name = "role_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_descr", unique = false, nullable = true, insertable = true, updatable = true)
	public String getRoleDescr() {
		return this.roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

	@Column(name = "internal_role", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getInternalRole() {
		return this.internalRole;
	}

	public void setInternalRole(Byte internalRole) {
		this.internalRole = internalRole;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userRoles")
	public Set<UserProfileRole> getUserProfileRoles() {
		return this.userProfileRoles;
	}

	public void setUserProfileRoles(Set<UserProfileRole> userProfileRoles) {
		this.userProfileRoles = userProfileRoles;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userRoles")
	public Set<UserProfile> getUserProfiles() {
		return this.userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

}