/**
 * 
 */
package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.buyer.Buyer;

/**
 * @author hoza
 *
 */
@Entity
@Table (name = "spnet_buyer")
public class SPNBuyer extends LoggableBaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column ( name = "id" , unique= true,nullable = false, insertable = true, updatable = true )
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id", unique = false, nullable = true, insertable = true, updatable = true)
	@ForeignKey(name="FK_SPNBUYER_BUYERID")
	private Buyer buyerId;
	
	
		
	@ManyToOne
    @JoinColumn(name="spn_id", unique = false,insertable=false, updatable=false)
    @ForeignKey(name="FK_SPNBUYER_SPNID")
	private SPNHeader spnId;

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
	 * @return the spnId
	 */
	public SPNHeader getSpnId() {
		return spnId;
	}

	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(SPNHeader spnId) {
		this.spnId = spnId;
	}
	
}
