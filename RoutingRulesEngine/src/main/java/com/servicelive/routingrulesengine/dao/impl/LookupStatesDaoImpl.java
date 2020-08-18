package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.routingrulesengine.dao.LookupStatesDao;

public class LookupStatesDaoImpl extends AbstractBaseDao implements LookupStatesDao {

	@SuppressWarnings("unchecked")
	public List<LookupStates> findActive() {
		List <LookupStates> orderList = (List <LookupStates>)super.findAllOrderByDesc("LookupStates", "state_name", null);
		return orderList;
	}
}
