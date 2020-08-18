/**
 *
 */
package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;
import com.servicelive.domain.LookupDomain;

/**
 * @author hoza
 *
 */
@LookupDomain
@Entity
@Table (name = "lu_service_type_template")
public class LookupServiceType extends AbstractLookupDomain {


	/**
	 *
	 */
	private static final long serialVersionUID = -7319774231166395969L;



	@Id
	@Column(name = "service_type_template_id")
	private Integer id;



	 @ManyToOne(optional=false)
     @JoinColumn(name="node_id",referencedColumnName="node_id")
	private LookupSkills skillNodeId;

	 @Column (name = "descr")
	private String description;


	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the skillNodeId
	 */
	/**
	 * @return the skillNodeId
	 */
	/**
	 * @return the skillNodeId
	 */
	//@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	//@JoinColumn(name = "node_id", unique = false, nullable = true, insertable = true, updatable = true)
/*	 @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=LookupSkills.class , fetch = FetchType.EAGER)
    @JoinColumn(name="node_id")
*/	public LookupSkills getSkillNodeId() {
		return skillNodeId;
	}
	/**
	 * @param skillNodeId the skillNodeId to set
	 */
	public void setSkillNodeId(LookupSkills skillNodeId) {
		this.skillNodeId = skillNodeId;
	}
	/**
	 * @param skillNodeId
	 */
	public LookupServiceType(LookupSkills skillNodeId) {
		super();
		this.skillNodeId = skillNodeId;
	}
	/**
	 * 
	 */
	public LookupServiceType() {
		super();
	}
	/**
	 * @return the id
	 */
	@Override
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
