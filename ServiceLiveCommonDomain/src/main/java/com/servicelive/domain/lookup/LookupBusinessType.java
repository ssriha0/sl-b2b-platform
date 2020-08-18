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
 * LookupBusinessType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="lu_business_type" )
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class LookupBusinessType   extends AbstractLookupDomain {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -3132087184167554140L;
	private Integer id;
     private String type;
     private String description;
     private Integer sortOrder;
    


    // Constructors

    /** default constructor */
    public LookupBusinessType() {
    	super();
    }

	/** 
	 * minimal constructor 
	 * @param descr
	 * @param sortOrder
	 */
    public LookupBusinessType(String descr, Integer sortOrder) {
        this.description = descr;
        this.sortOrder = sortOrder;
    }
    
    

    @Override
	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
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
    @Column(name="type", length=30)
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
    
    @Override
	@Column(name="descr", nullable=false, length=50)

    public String getDescription() {
        return this.description;
    }
    /**
     * 
     * @param descr
     */
    public void setDescription(String descr) {
        this.description = descr;
    }
    
    /**
     * 
     * @return Integer
     */
    @Column(name="sort_order", nullable=false)
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


}