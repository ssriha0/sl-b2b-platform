package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.domain.lookup.LookupWfStates;
import com.servicelive.domain.routingrules.RoutingRuleVendor;

/**
 * Provider Firm VO.
 */
public class RoutingRulesFirmVO 
	implements Serializable, Comparable<RoutingRulesFirmVO> 
{
	private static final long serialVersionUID = 4209518599795591722L;

	
	private String businessName;
	private String id;
	private LookupWfStates state;	
    private RoutingRuleVendor autoAcceptStatusInfo;
	/**
	 * 
	 */
	public RoutingRulesFirmVO() {
		super();
	}

	/**
	 * 
	 * @param businessName
	 * @param id
	 * @param status
	 * @param autoAcceptStatusInfo
	 */
	public RoutingRulesFirmVO(String businessName, String id, LookupWfStates state,RoutingRuleVendor autoAcceptStatusInfo) {
		this.businessName = businessName;
		this.id= id;
		this.state= state;
		this.autoAcceptStatusInfo=autoAcceptStatusInfo;
	}

	/**
	 * @return the autoAcceptStatusInfo
	 */
	public RoutingRuleVendor getAutoAcceptStatusInfo() {
		return autoAcceptStatusInfo;
	}

	/**
	 * @param autoAcceptStatusInfo the autoAcceptStatusInfo to set
	 */
	public void setAutoAcceptStatusInfo(RoutingRuleVendor autoAcceptStatusInfo) {
		this.autoAcceptStatusInfo = autoAcceptStatusInfo;
	}

	/**
	 * 
	 * @return String
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * 
	 * @param label
	 */
	public void setBusinessName(String label) {
		this.businessName = label;
	}

	/**
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public LookupWfStates getState()
	{
		return state;
	}

	/**
	 * 
	 * @param state
	 */
	public void setState(LookupWfStates state)
	{
		this.state= state;		
	}

	
	/**
	 * Return comparison based on all fields.
	 */
	public int compareTo(RoutingRulesFirmVO myClass) {
		return new CompareToBuilder()
			.append(this.id, myClass.id)
			.append(this.businessName, myClass.businessName)
			.toComparison();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof RoutingRulesFirmVO)) 
			return false;
		
		RoutingRulesFirmVO rhs = (RoutingRulesFirmVO) object;
		return new EqualsBuilder()
			.appendSuper(super.equals(object))
			.append(this.id, rhs.id)
			.append(this.businessName, rhs.businessName)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(-76077825, -1291754049)
			.appendSuper(super.hashCode())
			.append(this.id)
			.append(this.businessName)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("label", this.businessName)
			.append("state", this.state == null ? null : this.state.getWfState())
			.append("id", this.id)
			.toString();
	}

}
