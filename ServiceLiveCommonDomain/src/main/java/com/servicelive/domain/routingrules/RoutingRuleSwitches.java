package com.servicelive.domain.routingrules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "routing_rule_switches")
public class RoutingRuleSwitches {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4392775288702045983L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "switch",length=30)
	private String switchName;
	
	@Column(name = "value",length=30)
	private Boolean value;

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}