/**
 *
 */
package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.ApprovalCriteria;
import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "spnet_approval_criteria")
public class SPNApprovalCriteria extends LoggableBaseDomain implements ApprovalCriteria {
	private static final long serialVersionUID = 4053070401679732992L;


	@Id @GeneratedValue(strategy=IDENTITY)
	private Integer id ;

	@ManyToOne
    @JoinColumn(name="spn_id", insertable=false, updatable=false)
    @ForeignKey(name="FK_APP_CRI_SPN_ID")
	private SPNHeader spnId;

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "criteria_id", unique = false, nullable = true, insertable = true, updatable = true)
	@ForeignKey(name="FK_CAMP_APP_CRITERIA_CRITERIAID1")
	private LookupSPNApprovalCriteria criteriaId;

	@Column ( name = "value" , unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
	private String value;

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




	/**
	 * @return the criteriaId
	 */
	public LookupSPNApprovalCriteria getCriteriaId() {
		return criteriaId;
	}


	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(LookupSPNApprovalCriteria criteriaId) {
		this.criteriaId = criteriaId;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
