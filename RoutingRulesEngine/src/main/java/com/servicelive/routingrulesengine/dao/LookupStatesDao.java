package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.lookup.LookupStates;

public interface LookupStatesDao {

	/**
	 * 
	 * @return List<LookupStates>
	 */
	public List<LookupStates> findActive();
}
