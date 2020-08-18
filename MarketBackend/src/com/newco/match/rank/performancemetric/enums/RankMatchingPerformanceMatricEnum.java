package com.newco.match.rank.performancemetric.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RankMatchingPerformanceMatricEnum {
	
	// refer `vendor_ranking_buyer` table
	TIME_ON_SITE_ARRIVAL("TIME_ON_SITE_ARRIVAL")
	, APPOINTMENT_COMMITMENT("APPOINTMENT_COMMITMENT")
	, ORDER_RESPONSE("ORDER_RESPONSE")
	, CUSTOMER_RATING_CSAT("CUSTOMER_RATING_CSAT")
	, PERFORMANCE_METRIC("PERFORMANCE_METRIC")
	, NEW_SIGNUP("NEW_SIGNUP")
	, MATCHING_PERCENTAGE("MATCHING_PERCENTAGE")
	, ENUM_CRITERIA_NONE("NONE");

	private final String perfMetricEnumDescr;
	private static final Map<String, RankMatchingPerformanceMatricEnum> lookupPerfMatrixEnum = new HashMap<String, RankMatchingPerformanceMatricEnum>();

	static {
		for (RankMatchingPerformanceMatricEnum c : EnumSet.allOf(RankMatchingPerformanceMatricEnum.class)) {
			lookupPerfMatrixEnum.put(c.getPerfMetricEnumDescr(), c);
		}
	}

	// constructor
	RankMatchingPerformanceMatricEnum(String desc) {
		this.perfMetricEnumDescr = desc;
	}

	public String getPerfMetricEnumDescr() {
		return perfMetricEnumDescr;
	}

	public static RankMatchingPerformanceMatricEnum stringToPerformanceMatricEnum(
			String str) {
		try {
			return lookupPerfMatrixEnum.get(str);
		} catch (Exception ex) {
			return ENUM_CRITERIA_NONE;
		}
	}

}
