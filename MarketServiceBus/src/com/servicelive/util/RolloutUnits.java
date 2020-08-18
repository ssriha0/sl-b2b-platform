package com.servicelive.util;

import java.util.List;
import java.util.Map;

public class RolloutUnits {
	private boolean allUnits;
	private Map<String, Boolean> rolloutUnitsMap;
	private List<RolloutUnitRange> rolloutUnitRanges;
	
	public boolean containsUnit(String unitNumber) {
		if (allUnits) {
			// If all units are sent to the new OF process, always return true
			return true;
		} else if(unitNumber != null) {
			if (rolloutUnitsMap.containsKey(unitNumber)) {
				// First check to see if the store is in the rolloutStoresMap
				return true;
			} else {
				// If not, check to see if the store number falls into any specified ranges
				for (RolloutUnitRange range : rolloutUnitRanges) {
					if (unitNumber.compareTo(range.getLowerBound()) >= 0 && unitNumber.compareTo(range.getUpperBound()) <= 0) {
						return true;
					}
				}
			}
		}
		return false;
	}	
	
	public void setAllUnits(boolean allUnits) {
		this.allUnits = allUnits;
	}
	public boolean isAllUnits() {
		return allUnits;
	}
	public void setRolloutUnitsMap(Map<String, Boolean> rolloutUnitsMap) {
		this.rolloutUnitsMap = rolloutUnitsMap;
	}
	public Map<String, Boolean> getRolloutUnitsMap() {
		return rolloutUnitsMap;
	}
	public void setRolloutUnitRanges(List<RolloutUnitRange> rolloutUnitRanges) {
		this.rolloutUnitRanges = rolloutUnitRanges;
	}
	public List<RolloutUnitRange> getRolloutUnitRanges() {
		return rolloutUnitRanges;
	}
}
