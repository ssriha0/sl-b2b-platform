package com.servicelive.domain.lookup;
// default package


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * LookupStates entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="lu_state_cds" )

public class LookupStates  implements java.io.Serializable {


    // Fields    

	  /**
	 * 
	 */
	private static final long serialVersionUID = 2304058026022473457L;

	@Id 
	    @Column(name="state_cd", unique=true, nullable=false, length=2)
     private String id;
	  
	  @Column(name="state_name", nullable=false, length=50)
     private String description;
	  
	  @Column(name="blackout_ind", nullable=false)
     private Boolean blackoutInd;
	  
	  @Temporal(TemporalType.DATE)
	    @Column(name="blackout_date", length=19)
     private Date blackoutDate;
   


    // Constructors

    /** default constructor */
    public LookupStates() {
    	super();
    }

    /**
     * 
     * @return Date
     */
    public Date getBlackoutDate() {
        return this.blackoutDate;
    }
    /**
     * 
     * @param blackoutDate
     */
    public void setBlackoutDate(Date blackoutDate) {
        this.blackoutDate = blackoutDate;
    }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the blackoutInd
	 */
	public Boolean getBlackoutInd() {
		return blackoutInd;
	}

	/**
	 * @param blackoutInd the blackoutInd to set
	 */
	public void setBlackoutInd(Boolean blackoutInd) {
		this.blackoutInd = blackoutInd;
	}
}