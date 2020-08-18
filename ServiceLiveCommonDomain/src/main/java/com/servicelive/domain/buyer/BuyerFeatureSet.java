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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.servicelive.domain.BaseDomain;


/**
 * @author hoza
 *
 */
@Entity
@Table(name="buyer_feature_set" )
@XmlRootElement()
@XmlAccessorType(XmlAccessType.NONE)
public class BuyerFeatureSet extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3222155742091028865L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="buyer_feature_id", unique=true, nullable=false)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn ( name="buyer_id")
	@XmlTransient
	private Buyer buyerId;
	
	@Column ( name = "feature" )
	@XmlElement
	private String featureSet;
	
	@Column ( name = "active_ind")
	@XmlElement
	private Boolean isActive;

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
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the featureSet
	 */
	public String getFeatureSet() {
		return featureSet;
	}

	/**
	 * @param featureSet the featureSet to set
	 */
	public void setFeatureSet(String featureSet) {
		this.featureSet = featureSet;
	}
	
}
