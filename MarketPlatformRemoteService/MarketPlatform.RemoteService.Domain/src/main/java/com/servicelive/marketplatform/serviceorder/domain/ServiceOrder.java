package com.servicelive.marketplatform.serviceorder.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "so_hdr")
public class ServiceOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@NaturalId
    @Column(name="so_id")
    private String id;
	
//	@ManyToOne(fetch=FetchType.EAGER, cascade={})
//  @JoinColumn(name="buyer_id")
//    @Transient
//	private Buyer buyer;
//	@Column(name="buyer_id")
//    private Long buyerId;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "so_id")
    private List<ServiceOrderRoutedProvider> routedProviders = new ArrayList<ServiceOrderRoutedProvider>();

	@Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date lastModifiedDate;

    @Column(name = "modified_by")
    private String lastModifiedBy;
    
    public String getId() {
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
	/*
	public Long getBuyerId() {
		return buyerId;
	}
	
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

    public Buyer getBuyer() {
    	return buyer;
    }
    
    public void setBuyer(Buyer buyer) {
    	this.buyer = buyer;
    }
	*/

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

	public void setRoutedProviders(List<ServiceOrderRoutedProvider> routedProviders) {
		this.routedProviders = routedProviders;
	}

	public List<ServiceOrderRoutedProvider> getRoutedProviders() {
		return routedProviders;
	}

}
