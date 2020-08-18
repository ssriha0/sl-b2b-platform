/**
 *
 */
package com.servicelive.domain.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "lu_spnet_approval_criteria")
public class LookupSPNApprovalCriteria extends AbstractLookupDomain {

	/**
	 *
	 */
	private static final long serialVersionUID = -1565855340075174340L;

	@Id  @GeneratedValue(strategy=IDENTITY)
	@Column ( name = "id", unique = true, insertable = true, scale = 10, updatable =true , nullable = false)
	private Integer id;

	@Column ( name = "descr", insertable = true, length = 50, updatable =true , nullable = false)
	private String description;


	@Column ( name = "criteria_level", insertable = true, scale = 3, updatable =true , nullable = false)
	private Integer criteriaLevel; // is this at Service Pro level or Provider Firm level

	@Column ( name = "value_type", insertable = true, length = 50, updatable =true , nullable = false)
	private String valueType;

	@Column ( name = "lookup_table_name", insertable = true, length = 150, updatable =true , nullable = false)
	private String lookupTableName;

	@Column ( name = "column_name", insertable = true, length = 150, updatable =true , nullable = false)
	private String columnName;

	@Column ( name = "group_name", insertable = true,  updatable =true , nullable = true)
	private String groupName;

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getDescription()
	 */
	@Override
	public Object getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.domain.AbstractLookupDomain#getId()
	 */
	@Override
	public Object getId() {
		return id;
	}

	/**
	 * @return the criteriaLevel
	 */
	public Integer getCriteriaLevel() {
		return criteriaLevel;
	}

	/**
	 * @param criteriaLevel the criteriaLevel to set
	 */
	public void setCriteriaLevel(Integer criteriaLevel) {
		this.criteriaLevel = criteriaLevel;
	}

	/**
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * @param valueType the valueType to set
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	/**
	 * @return the lookupTableName
	 */
	public String getLookupTableName() {
		return lookupTableName;
	}

	/**
	 * @param lookupTableName the lookupTableName to set
	 */
	public void setLookupTableName(String lookupTableName) {
		this.lookupTableName = lookupTableName;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
