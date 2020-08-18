package com.servicelive.routingrulesengine;

/** @see lu_routing_rule_type table */
public enum RoutingRuleType {
	ZIP_MARKET(1), CUSTOM_REF(2), JOB_SPEC(3);

	private int id;
	private RoutingRuleType(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @param id2
	 * @return RoutingRuleType
	 */
	public static RoutingRuleType findById(int id2) {
		for(RoutingRuleType temp:values()) {
			if(temp.id == id2) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return Integer.valueOf(id);
	}
}
