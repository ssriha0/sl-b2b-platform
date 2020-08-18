package com.servicelive.domain.lookup;
// default package


import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.servicelive.domain.AbstractLookupDomain;
import com.servicelive.domain.buyer.Buyer;


/**
 * LookupSalesVolume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="lu_sales_volume")
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class LookupSalesVolume extends AbstractLookupDomain {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -6960043269481800137L;
	private Integer id;
     private String type;
     private String description;
     private Integer sortOrder;
     @XmlTransient
     private Set<Buyer> buyers = new HashSet<Buyer>(0);


    // Constructors

    /** default constructor */
    public LookupSalesVolume() {
    	super();
    }

    
    /** 
     * full constructor
     * @param type
     * @param descr
     * @param sortOrder
     * @param buyers
     */
    public LookupSalesVolume(String type, String descr, Integer sortOrder, Set<Buyer> buyers) {
        this.type = type;
        this.description = descr;
        this.sortOrder = sortOrder;
        this.buyers = buyers;
    }

   
    // Property accessors
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
	@Column(name="descr", length=50)

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
    @Column(name="sort_order")
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
    /**
     * 
     * @return Set
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="lookupSalesVolume")
    public Set<Buyer> getBuyers() {
        return this.buyers;
    }
    /**
     * 
     * @param buyers
     */
    public void setBuyers(Set<Buyer> buyers) {
        this.buyers = buyers;
    }

}