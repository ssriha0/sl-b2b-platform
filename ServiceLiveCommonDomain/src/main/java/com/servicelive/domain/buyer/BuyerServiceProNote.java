/**
 * 
 */
package com.servicelive.domain.buyer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.provider.ServiceProvider;

/**
 * @author hoza
 *
 */
@Entity
@Table(name="buyer_service_pro_note" )
public class BuyerServiceProNote extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7037438914434742953L;
	
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer	id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn ( name="buyer_id")
	private Buyer buyerId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn ( name="service_pro_id", referencedColumnName="resource_id")
	private ServiceProvider serviceProId;
	
	@Column(name="comments", unique=false, nullable=false)
	private String comments;

	/**
	 * @return the id
	 */
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
	 * @return the buyerId
	 */
	public Buyer getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Buyer buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the serviceProId
	 */
	public ServiceProvider getServiceProId() {
		return serviceProId;
	}

	/**
	 * @param serviceProId the serviceProId to set
	 */
	public void setServiceProId(ServiceProvider serviceProId) {
		this.serviceProId = serviceProId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
