package com.servicelive.marketplatform.serviceorder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

@Entity
@Table(name = "so_routed_providers")
public class ServiceOrderRoutedProvider extends AbstractDomainEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="so_routed_provider_id")
	private Long id;
	
	@Column(name="so_id")
	private String serviceOrderId;
	
	@Column(name="resource_id")
	private Long resourceId;
	

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public String getServiceOrderId() {
		return serviceOrderId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getResourceId() {
		return resourceId;
	}

}
