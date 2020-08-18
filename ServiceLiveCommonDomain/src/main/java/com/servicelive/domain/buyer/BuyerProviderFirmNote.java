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
import com.servicelive.domain.provider.ProviderFirm;

/**
 * @author hoza
 *
 */
@Entity
@Table(name="buyer_provider_firm_note" )
public class BuyerProviderFirmNote extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5531012937528181320L;
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer	id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn ( name="buyer_id")
	private Buyer buyerId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn ( name="prov_firm_id", referencedColumnName="vendor_id")
	private ProviderFirm providerFirmId;
	
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
	 * @return the providerFirmId
	 */
	public ProviderFirm getProviderFirmId() {
		return providerFirmId;
	}

	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(ProviderFirm providerFirmId) {
		this.providerFirmId = providerFirmId;
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
