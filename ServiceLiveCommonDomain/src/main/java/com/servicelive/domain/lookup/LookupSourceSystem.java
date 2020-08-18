package com.servicelive.domain.lookup;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;


/**
 * LookupSourceSystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="lu_source_system" )
public class LookupSourceSystem  extends AbstractLookupDomain {


    /**
	 *
	 */
	private static final long serialVersionUID = -1082354840113755387L;
	// Fields
	 @Id
	 @Column(name="id", unique=true, nullable=false)
     private Integer id;


	 @Column(name="type", length=30)
     private String type;

     @Column(name="descr", length=50)
     private String description;

     @Column(name="sort_order")
     private Integer sortOrder;



    // Constructors

    /** default constructor */
    public LookupSourceSystem() {
    	super();
    }

	/**
	 * minimal constructor
	 * @param id
	 */
    public LookupSourceSystem(Integer id) {
        this.id = id;
    }




    // Property accessors


    @Override
	public Integer getId() {
        return this.id;
    }
    /**
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * 
     * @return String
     */
    public String getType() {
        return this.type;
    }
    /**
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return Integer
     */
    public Integer getSortOrder() {
        return this.sortOrder;
    }
    /**
     * 
     * @param sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}