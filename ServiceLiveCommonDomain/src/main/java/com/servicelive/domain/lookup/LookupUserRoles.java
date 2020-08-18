package com.servicelive.domain.lookup;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.AbstractLookupDomain;
/**
 * LookupUserRoles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="user_roles")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LookupUserRoles  extends AbstractLookupDomain {


	private static final long serialVersionUID = 119478560982373047L;
	// Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="role_id", unique=true, nullable=false)
     private Integer id;

    @Column(name="role_name", length=50)
     private String roleName;

     @Column(name="role_descr")
     private String description;


     @Column(name="internal_role")
     private Byte internalRole;



    // Constructors

    /** default constructor */
    public LookupUserRoles() {
    	super();
    }
    /**
     * 
     * @return String
     */
    public String getRoleName() {
        return this.roleName;
    }
    /**
     * 
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    /**
     * 
     * @return Byte
     */
    public Byte getInternalRole() {
        return this.internalRole;
    }
    /**
     * 
     * @param internalRole
     */
    public void setInternalRole(Byte internalRole) {
        this.internalRole = internalRole;
    }





	/**
	 * @return the id
	 */
	@Override
	public Integer getId() {
		return id;
	}





	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}





	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}





	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}










}