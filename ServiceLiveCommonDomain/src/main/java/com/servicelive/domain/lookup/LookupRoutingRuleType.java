package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * 
 * @author yshulm0
 *
 */

@Entity
@Table(name="lu_routing_rule_type")

public class LookupRoutingRuleType extends AbstractLookupDomain {

	private static final long serialVersionUID = 3030457475142444614L;
	private Integer id;
	private String description;
	private String operand;
 
	/**
	 * 
	 */
	public LookupRoutingRuleType() {
		super();
	}

	/**
	 * 
	 */
	public LookupRoutingRuleType(Integer id) {
		super();
		setId(id);
	}

	@Override
	@Column(name="descr", nullable=false, length=30)
	public String getDescription() {
		return this.description;
	}

	@Override
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rule_type_id", unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	@Column(name="internal_operand", nullable=false, length=30)
	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
